package com.pcs.guava.serviceimpl;

import static com.pcs.guava.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.guava.constants.RoutingConstant.PLATFORM_TYPE;
import static com.pcs.guava.constants.RoutingConstant.POI_STATUS;
import static com.pcs.guava.constants.RoutingConstant.POI_TEMPLATE;
import static com.pcs.guava.constants.RoutingConstant.POI_TYPE;
import static com.pcs.guava.constants.RoutingConstant.ROUTE_TEMPLATE;
import static com.pcs.guava.enums.RouteDataFields.DESTN_POINTS;
import static com.pcs.guava.enums.RouteDataFields.DURATION;
import static com.pcs.guava.enums.RouteDataFields.END_ADDRESS;
import static com.pcs.guava.enums.RouteDataFields.IDENTIFIER;
import static com.pcs.guava.enums.RouteDataFields.LATITUDE;
import static com.pcs.guava.enums.RouteDataFields.LONGITUDE;
import static com.pcs.guava.enums.RouteDataFields.MAP_PROVIDER;
import static com.pcs.guava.enums.RouteDataFields.POI_ADDRESS;
import static com.pcs.guava.enums.RouteDataFields.POI_INDEX;
import static com.pcs.guava.enums.RouteDataFields.POI_NAME;
import static com.pcs.guava.enums.RouteDataFields.RADIUS;
import static com.pcs.guava.enums.RouteDataFields.ROUTE_DESC;
import static com.pcs.guava.enums.RouteDataFields.ROUTE_NAME;
import static com.pcs.guava.enums.RouteDataFields.ROUTE_STRING;
import static com.pcs.guava.enums.RouteDataFields.START_ADDRESS;
import static com.pcs.guava.enums.RouteDataFields.STATUS;
import static com.pcs.guava.enums.RouteDataFields.STATUS_ACTIVE;
import static com.pcs.guava.enums.RouteDataFields.STATUS_INACTIVE;
import static com.pcs.guava.enums.RouteDataFields.TOTAL_DISTANCE;
import static com.pcs.guava.enums.RouteDataFields.TOTAL_DURATION;
import static com.pcs.guava.enums.ScheduleDataFields.POI_STOPPAGE_TIME;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.EntityTemplateDTO;
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.PlatformEntityDTO;
import com.pcs.guava.commons.dto.ReturnFieldsDTO;
import com.pcs.guava.commons.dto.SearchFieldsDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.commons.dto.StatusMessageErrorDTO;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.commons.validation.ValidationUtils;
import com.pcs.guava.dto.routing.PoiDeSerializeDTO;
import com.pcs.guava.dto.routing.PoiIdentifiersDTO;
import com.pcs.guava.dto.routing.PoiResult;
import com.pcs.guava.dto.routing.RoutePoiDTO;
import com.pcs.guava.dto.routing.RoutingDTO;
import com.pcs.guava.dto.routing.RoutingESBDTO;
import com.pcs.guava.dto.routing.RoutingIdentityESBDTO;
import com.pcs.guava.enums.Status;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.rest.exception.GalaxyRestException;
import com.pcs.guava.service.RoutingService;
import com.pcs.guava.service.repository.RoutingRepository;

@Service
public class RoutingServiceImpl implements RoutingService {

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	@Qualifier("guavaESBClient")
	private BaseClient guavaESBClient;

	@Autowired
	@Qualifier("platformESBClient")
	private BaseClient platformESBClient;

	@Value("${guava.esb.route.create}")
	private String createRouteEndpoint;

	@Value("${guava.esb.route.delete}")
	private String deleteRouteEndpoint;

	@Value("${alpine.esb.marker.field}")
	private String checkRouteExist;

	@Value("${alpine.esb.marker.list}")
	private String listRouteEndpoint;

	@Autowired
	RoutingRepository routingRepository;

	@SuppressWarnings("unchecked")
	@Override
	public RoutingDTO viewRoute(String routeName,String domainName) {

		RoutingDTO route = new RoutingDTO();
		RoutingDTO routeDetails = new RoutingDTO();
		route.setRouteName(routeName);
		DomainDTO domainDTO = new DomainDTO();
		if (isBlank(domainName)) {			
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
		}else{
			domainDTO.setDomainName(domainName);
		}
		route.setDomain(domainDTO.getDomainName());
		List<ReturnFieldsDTO> searchList = new ArrayList<ReturnFieldsDTO>();
		SearchFieldsDTO routeNameSearch = new SearchFieldsDTO();

		routeNameSearch = createRouteSearch(routeName);
		routeNameSearch.setDomain(domainDTO);
		searchList = platformESBClient.post(checkRouteExist, getJwtToken(),
				routeNameSearch, List.class, ReturnFieldsDTO.class);

		if (searchList != null) {
			List<FieldMapDTO> routeData = new ArrayList<FieldMapDTO>();
			routeData = searchList.get(0).getDataprovider();
			String identifier = searchList.get(0).getIdentifier().getValue();
			PoiResult poiEntities = routingRepository.findRouteChild(
					identifier, POI_TEMPLATE, POI_TYPE);

			routeDetails = constructRouteFields(poiEntities, routeData);
		}
		return routeDetails;

	}

	private RoutingDTO constructRouteFields(PoiResult entities,
			List<FieldMapDTO> routeData) {
		boolean listView = true;
		RoutingDTO route = new RoutingDTO();

		route = getRouteInfo(routeData, listView);

		List<RoutePoiDTO> destinationPoints = new ArrayList<RoutePoiDTO>();
		for (PoiDeSerializeDTO poi : entities.getResult()) {
			RoutePoiDTO points = new RoutePoiDTO();
			List<FieldMapDTO> poiData = new ArrayList<FieldMapDTO>();
			List<FieldMapDTO> poiTypeData = new ArrayList<FieldMapDTO>();
			poiData = poi.getPoiData();
			poiTypeData = poi.getPoiTypeData();
			for (FieldMapDTO poiField : poiData) {

				if (poiField.getKey().equals(LATITUDE.getFieldName())) {
					points.setLatitude(poiField.getValue());
				} else if (poiField.getKey().equals(LONGITUDE.getFieldName())) {
					points.setLongitude(poiField.getValue());
				} else if (poiField.getKey().equals(POI_NAME.getFieldName())) {
					points.setName(poiField.getValue());
				} else if (poiField.getKey().equals(RADIUS.getFieldName())) {
					points.setRadius(poiField.getValue());
				} else if (poiField.getKey().equals(POI_INDEX.getFieldName())) {
					if (!isBlank(poiField.getValue())) {
						points.setIndex(Integer.parseInt(poiField.getValue()));
					}
				}

			}

			for (FieldMapDTO poiTypeField : poiTypeData) {

				if (poiTypeField.getKey().equals(POI_ADDRESS.getFieldName())) {
					points.setAddress(poiTypeField.getValue());
				} else if (poiTypeField.getKey().equals(
						POI_INDEX.getFieldName())) {
					if (!isBlank(poiTypeField.getValue())) {
						points.setIndex(Integer.parseInt(poiTypeField
								.getValue()));
					}
				}else if (poiTypeField.getKey().equals(
						DURATION.getFieldName())) {
					if (!isBlank(poiTypeField.getValue())) {
						points.setDuration(poiTypeField
								.getValue());
					}else{
						points.setDuration("");						
					}
				}else if (poiTypeField.getKey().equals(
						POI_STOPPAGE_TIME.getFieldName())) {
					if (!isBlank(poiTypeField.getValue())) {
						points.setStopageTime(poiTypeField
								.getValue());
					}
				}

			}

			destinationPoints.add(points);
		}
		route.setDestinationPoints(destinationPoints);
		return route;
	}

	private RoutingDTO getRouteInfo(List<FieldMapDTO> routeData,
			boolean listView) {
		RoutingDTO route = new RoutingDTO();
		for (FieldMapDTO field : routeData) {

			if (field.getKey().equals(END_ADDRESS.getFieldName())) {
				if (!isBlank(field.getValue())) {
					route.setEndAddress(field.getValue());
				}
			} else if (field.getKey().equals(START_ADDRESS.getFieldName())) {
				if (!isBlank(field.getValue())) {
					route.setStartAddress(field.getValue());
				}
			} else if (field.getKey().equals(ROUTE_DESC.getFieldName())) {
				route.setRouteDescription(field.getValue());
			} else if (field.getKey().equals(ROUTE_NAME.getFieldName())) {
				route.setRouteName(field.getValue());
			} else if (field.getKey().equals(ROUTE_STRING.getFieldName())) {
				if (listView) {
					route.setRouteString(field.getValue());
				}
			} else if (field.getKey().equals(STATUS.getFieldName())) {
				route.setStatus(field.getValue());
			} else if (field.getKey().equals(TOTAL_DISTANCE.getFieldName())) {
				route.setTotalDistance(Long.parseLong(field.getValue()));
			} else if (field.getKey().equals(TOTAL_DURATION.getFieldName())) {
				route.setTotalDuration(Long.parseLong(field.getValue()));
			}
		}

		return route;
	}

	@Override
	public StatusMessageDTO createRoute(RoutingDTO route,boolean update) {
		if(!update){
			validateRouteFields(route);
		}	
		RoutingESBDTO routeEsb = new RoutingESBDTO();
		routeEsb = createRoutingESBPayload(route);
		StatusMessageDTO statusMessage = new StatusMessageDTO();
		statusMessage = guavaESBClient.post(createRouteEndpoint, getJwtToken(),
				routeEsb, StatusMessageDTO.class);
		return statusMessage;
	}

	private void validateRouteFields(RoutingDTO route) {
		// validate the mandatory fields
		ValidationUtils.validateMandatoryFields(route, ROUTE_NAME, ROUTE_DESC,
				TOTAL_DISTANCE, START_ADDRESS, END_ADDRESS, ROUTE_STRING,
				TOTAL_DURATION, STATUS,MAP_PROVIDER);

		if (!(route.getStatus().equals(STATUS_ACTIVE.getFieldName()))
				&& !(route.getStatus().equals(STATUS_INACTIVE.getFieldName()))) {

			throw new GalaxyException(
					GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_VALID,
					STATUS.getDescription());

		}

		// validate the size of fieldValues
		ValidationUtils.validateCollection(DESTN_POINTS,
				route.getDestinationPoints());

		if (isBlank(route.getDomain())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());

			route.setDomain(domainDTO.getDomainName());

		}
	}

	private RoutingESBDTO createRoutingESBPayload(RoutingDTO route) {

		RoutingESBDTO routingESB = new RoutingESBDTO();
		StatusMessageDTO statusMessage = new StatusMessageDTO();
		statusMessage = isRouteExist(route.getRouteName(), true);
		if (statusMessage.getStatus().name().equals("SUCCESS")) {
			// set route Entity
			EntityDTO routeEntity = new EntityDTO();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(route.getDomain());
			routeEntity.setDomain(domain);
			EntityTemplateDTO routeTemplate = new EntityTemplateDTO();
			routeTemplate.setEntityTemplateName(ROUTE_TEMPLATE);
			routeEntity.setEntityTemplate(routeTemplate);
			routeEntity.setFieldValues(constuctRouteFields(route));
			routingESB.setRouteEntity(routeEntity);
		}

		// set poi Entity
		routingESB.setWayPointsEntity(createWayPointsEntity(route));

		return routingESB;

	}
	
	private List<FieldMapDTO> constuctRouteFields(RoutingDTO route) {

		List<FieldMapDTO> routeFields = new ArrayList<FieldMapDTO>();

		// Construct route name map
		FieldMapDTO routeName = new FieldMapDTO();
		routeName.setKey(ROUTE_NAME.getFieldName());
		routeName.setValue(route.getRouteName());

		// contruct route desc map
		FieldMapDTO routeDesc = new FieldMapDTO();
		routeDesc.setKey(ROUTE_DESC.getFieldName());
		routeDesc.setValue(route.getRouteDescription());

		// construct total distance map
		FieldMapDTO totalDistance = new FieldMapDTO();
		totalDistance.setKey(TOTAL_DISTANCE.getFieldName());
		totalDistance.setValue(route.getTotalDistance().toString());

		// construct route string map
		FieldMapDTO routeString = new FieldMapDTO();
		routeString.setKey(ROUTE_STRING.getFieldName());
		routeString.setValue(route.getRouteString());

		// construct route startAddr map
		FieldMapDTO startAddress = new FieldMapDTO();
		startAddress.setKey(START_ADDRESS.getFieldName());
		startAddress.setValue(route.getStartAddress());

		// construct route endAddr map
		FieldMapDTO endAddress = new FieldMapDTO();
		endAddress.setKey(END_ADDRESS.getFieldName());
		endAddress.setValue(route.getEndAddress());

		// construct route status map
		FieldMapDTO status = new FieldMapDTO();
		status.setKey(STATUS.getFieldName());
		status.setValue(route.getStatus());

		// construct route duration map
		FieldMapDTO duration = new FieldMapDTO();
		duration.setKey(TOTAL_DURATION.getFieldName());
		duration.setValue(route.getTotalDuration().toString());
		
		// construct route  map provider
		FieldMapDTO mapProvider = new FieldMapDTO();
		mapProvider.setKey(MAP_PROVIDER.getFieldName());
		mapProvider.setValue(route.getMapProvider());

		routeFields.add(routeName);
		routeFields.add(routeDesc);
		routeFields.add(totalDistance);
		routeFields.add(routeString);
		routeFields.add(startAddress);
		routeFields.add(endAddress);
		routeFields.add(status);
		routeFields.add(duration);
		routeFields.add(mapProvider);

		return routeFields;
	}
	
	
	private List<RoutePoiDTO> createWayPointsEntity(RoutingDTO route) {

		List<RoutePoiDTO> wayPoints = new ArrayList<RoutePoiDTO>();

		for (RoutePoiDTO poi : route.getDestinationPoints()) {
			poi.setPoiType(POI_TYPE);
			poi.setDomainName(route.getDomain());
			poi.setStatus(POI_STATUS);
			poi.setPoiName(poi.getName());
			poi.setPoiTypeValues(constuctPointFields(poi));
			wayPoints.add(poi);

		}
		return wayPoints;
	}

	private List<FieldMapDTO> constuctPointFields(RoutePoiDTO poi) {
		List<FieldMapDTO> poiFields = new ArrayList<FieldMapDTO>();
		// construct index
		FieldMapDTO index = new FieldMapDTO();
		index.setKey(POI_INDEX.getFieldName());
		index.setValue(poi.getIndex().toString());

		// construct address
		FieldMapDTO address = new FieldMapDTO();
		address.setKey(POI_ADDRESS.getFieldName());
		address.setValue(poi.getAddress());
		
		FieldMapDTO stoppageTime = new FieldMapDTO();
		stoppageTime.setKey(POI_STOPPAGE_TIME.getFieldName());
		stoppageTime.setValue(poi.getStopageTime());
		
		FieldMapDTO duartion = new FieldMapDTO();
		duartion.setKey(DURATION.getFieldName());
		if(null==poi.getDuration()){
			duartion.setValue("");
		}else{
			duartion.setValue(poi.getDuration());
		}

		poiFields.add(address);
		poiFields.add(index);
		poiFields.add(stoppageTime);
		poiFields.add(duartion);

		return poiFields;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StatusMessageDTO isRouteExist(String routeName, boolean check) {

		// this code can be reused for creating searchFields
		SearchFieldsDTO routeNameSearch = new SearchFieldsDTO();
		List<ReturnFieldsDTO> searchList = new ArrayList<ReturnFieldsDTO>();
		routeNameSearch = createRouteSearch(routeName);

		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		try {
			searchList = platformESBClient.post(checkRouteExist, getJwtToken(),
					routeNameSearch, List.class, ReturnFieldsDTO.class);

			if (searchList != null) {
				if (check) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.ROUTE_EXIST);
				}

				else {
					status.setStatus(Status.SUCCESS);
				}
			}
		} catch (GalaxyRestException e) {
			if (check) {
				if (e.getCode()
						.toString()
						.equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
								.getCode().toString())) {
					status.setStatus(Status.SUCCESS);
				} else {
					throw e;
				}
			} else {
				throw new GalaxyException(GalaxyCommonErrorCodes.NO_ROUTE_EXIST);
			}
		}
		return status;

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingDTO> listRoute(String domainName) {
		DomainDTO domainDTO = new DomainDTO();
		boolean listView = false;
		if(isBlank(domainName)){
			domainDTO.setDomainName(subscriptionProfileService.getSubscription()
					.getEndUserDomain());
		}else{
			domainDTO.setDomainName(domainName);
		}
		
		IdentityDTO route = new IdentityDTO();
		route.setDomain(domainDTO);
		EntityTemplateDTO routeTemplate = new EntityTemplateDTO();
		routeTemplate.setEntityTemplateName(ROUTE_TEMPLATE);
		route.setEntityTemplate(routeTemplate);

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		List<RoutingDTO> routes = new ArrayList<RoutingDTO>();

		entities = platformESBClient.post(listRouteEndpoint, getJwtToken(),
				route, List.class, EntityDTO.class);

		if (entities == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.NO_ROUTE_EXIST);
		} else {

			for (EntityDTO entity : entities) {
				RoutingDTO routeName = new RoutingDTO();
				routeName = getRouteInfo(entity.getDataprovider(), listView);
				routes.add(routeName);
			}
		}

		return routes;
	}

	public SearchFieldsDTO createRouteSearch(String routeName) {

		SearchFieldsDTO routeNameSearch = new SearchFieldsDTO();
		EntityTemplateDTO routeTemplate = new EntityTemplateDTO();
		routeTemplate.setEntityTemplateName(ROUTE_TEMPLATE);
		routeNameSearch.setEntityTemplate(routeTemplate);

		List<FieldMapDTO> routeFields = new ArrayList<FieldMapDTO>();
		// Construct route name map
		FieldMapDTO routeNameField = new FieldMapDTO();
		routeNameField.setKey(ROUTE_NAME.getFieldName());
		routeNameField.setValue(routeName);
		routeFields.add(routeNameField);
		routeNameSearch.setSearchFields(routeFields);
		return routeNameSearch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StatusMessageDTO deleteRoute(String routeName,
			Boolean routeExistCheck,String domain) {
		
		DomainDTO domainDTO = new DomainDTO();
		// Get logged in user's domain
		if(isBlank(domain)){
			domainDTO.setDomainName(subscriptionProfileService.getSubscription()
					.getEndUserDomain());	
		}else{
			domainDTO.setDomainName(domain);
		}


		StatusMessageErrorDTO messageDTO = new StatusMessageErrorDTO();
		StatusMessageDTO statusMessage = new StatusMessageDTO();
		SearchFieldsDTO routeNameSearch = new SearchFieldsDTO();

		List<ReturnFieldsDTO> searchList = new ArrayList<ReturnFieldsDTO>();
		routeNameSearch = createRouteSearch(routeName);
		routeNameSearch.setDomain(domainDTO);
		try{
		searchList = platformESBClient.post(checkRouteExist, getJwtToken(),
				routeNameSearch, List.class, ReturnFieldsDTO.class);
		}catch (GalaxyRestException e) {
				if (e.getCode()
						.toString()
						.equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
								.getCode().toString())) {
					throw new GalaxyException(GalaxyCommonErrorCodes.NO_ROUTE_EXIST);
				} else {
					throw e;
				}
			}
		if (searchList != null) {
			List<FieldMapDTO> routeData = new ArrayList<FieldMapDTO>();
			routeData = searchList.get(0).getDataprovider();
			String routeIdentifier = searchList.get(0).getIdentifier()
					.getValue();
			PoiIdentifiersDTO poiIdentifiers = routingRepository
					.getPoiIdentifiers(routeIdentifier, POI_TEMPLATE, POI_TYPE);

			RoutingIdentityESBDTO routeIdentity = new RoutingIdentityESBDTO();
			routeIdentity = constructIdentityDto(poiIdentifiers,
					routeIdentifier,domainDTO);

			messageDTO = guavaESBClient.post(deleteRouteEndpoint,
					getJwtToken(), routeIdentity, StatusMessageErrorDTO.class);
			statusMessage.setStatus(messageDTO.getStatus());

			if (isNotBlank(messageDTO.getErrorCode())) {
				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
						messageDTO.getErrorMessage());

			}
		}

		return statusMessage;
	}

	private RoutingIdentityESBDTO constructIdentityDto(
			PoiIdentifiersDTO poiIdentifiers, String routeIdentifier, DomainDTO domainDTO) {
		RoutingIdentityESBDTO routeEsb = new RoutingIdentityESBDTO();
		List<IdentityDTO> identities = new ArrayList<IdentityDTO>();
		IdentityDTO route = new IdentityDTO();
		PlatformEntityDTO type = new PlatformEntityDTO();
		type.setPlatformEntityType(PLATFORM_TYPE);

		route.setDomain(domainDTO);
		EntityTemplateDTO routeTemplate = new EntityTemplateDTO();
		routeTemplate.setEntityTemplateName(ROUTE_TEMPLATE);
		route.setEntityTemplate(routeTemplate);
		FieldMapDTO routeField = new FieldMapDTO();
		routeField.setKey(IDENTIFIER.getFieldName());
		routeField.setValue(routeIdentifier);
		route.setIdentifier(routeField);
		route.setPlatformEntity(type);
		routeEsb.setRoute(route);

		for (String identifier : poiIdentifiers.getIdentifier()) {
			IdentityDTO poi = new IdentityDTO();
			poi.setDomain(domainDTO);
			EntityTemplateDTO poiTemplate = new EntityTemplateDTO();
			poiTemplate.setEntityTemplateName(POI_TEMPLATE);
			poi.setEntityTemplate(poiTemplate);
			FieldMapDTO poiField = new FieldMapDTO();
			poiField.setKey(IDENTIFIER.getFieldName());
			poiField.setValue(identifier);
			poi.setIdentifier(poiField);
			poi.setPlatformEntity(type);
			identities.add(poi);

		}
		routeEsb.setPoi(identities);
		return routeEsb;
	}

	@Override
	public StatusMessageDTO updateRoute(RoutingDTO route) {
		validateRouteFields(route);
		StatusMessageDTO deleteStatus= new StatusMessageDTO();
		StatusMessageDTO createStatus= new StatusMessageDTO();
		deleteStatus=deleteRoute(route.getRouteName(),true,route.getDomain());
		if(deleteStatus.getStatus().name().equals("SUCCESS")){
			createStatus=createRoute(route, false);		
		};
		return createStatus;
	}

}
