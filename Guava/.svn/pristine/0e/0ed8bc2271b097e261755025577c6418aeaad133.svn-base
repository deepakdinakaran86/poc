package com.pcs.guava.serviceimpl;

import static com.pcs.guava.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.guava.constants.RoutingConstant.PLATFORM_TYPE;
import static com.pcs.guava.constants.RoutingConstant.POI_TEMPLATE;
import static com.pcs.guava.constants.RoutingConstant.POI_TYPE;
import static com.pcs.guava.constants.RoutingConstant.ROUTE_TEMPLATE;
import static com.pcs.guava.constants.ScheduleConstant.SCHEDULE_DEST_TEMPLATE;
import static com.pcs.guava.constants.ScheduleConstant.SCHEDULE_TEMPLATE;
import static com.pcs.guava.enums.RouteDataFields.DESTN_POINTS;
import static com.pcs.guava.enums.RouteDataFields.END_ADDRESS;
import static com.pcs.guava.enums.RouteDataFields.IDENTIFIER;
import static com.pcs.guava.enums.RouteDataFields.LATITUDE;
import static com.pcs.guava.enums.RouteDataFields.LONGITUDE;
import static com.pcs.guava.enums.RouteDataFields.POI_ADDRESS;
import static com.pcs.guava.enums.RouteDataFields.POI_INDEX;
import static com.pcs.guava.enums.RouteDataFields.POI_NAME;
import static com.pcs.guava.enums.RouteDataFields.RADIUS;
import static com.pcs.guava.enums.RouteDataFields.ROUTE_DESC;
import static com.pcs.guava.enums.RouteDataFields.ROUTE_NAME;
import static com.pcs.guava.enums.RouteDataFields.ROUTE_STRING;
import static com.pcs.guava.enums.RouteDataFields.START_ADDRESS;
import static com.pcs.guava.enums.RouteDataFields.STATUS;
import static com.pcs.guava.enums.RouteDataFields.TOTAL_DISTANCE;
import static com.pcs.guava.enums.RouteDataFields.TOTAL_DURATION;
import static com.pcs.guava.enums.ScheduleDataFields.ARRIVAL_TIME;
import static com.pcs.guava.enums.ScheduleDataFields.DAY_SPAN;
import static com.pcs.guava.enums.ScheduleDataFields.DEPARTURE_TIME;
import static com.pcs.guava.enums.ScheduleDataFields.END_TIME;
import static com.pcs.guava.enums.ScheduleDataFields.POI_STOPPAGE_TIME;
import static com.pcs.guava.enums.ScheduleDataFields.SCHEDULE_DESC;
import static com.pcs.guava.enums.ScheduleDataFields.SCHEDULE_NAME;
import static com.pcs.guava.enums.ScheduleDataFields.START_TIME;
import static com.pcs.guava.enums.ScheduleDataFields.TOLERANCE_TIME;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.commons.validation.DataFields;
import com.pcs.guava.commons.validation.ValidationUtils;
import com.pcs.guava.dto.routing.PoiDeSerializeDTO;
import com.pcs.guava.dto.routing.PoiResult;
import com.pcs.guava.dto.routing.RoutePoiDTO;
import com.pcs.guava.dto.routing.RoutingDTO;
import com.pcs.guava.dto.scheduling.ScheduleDeSerializeDTO;
import com.pcs.guava.dto.scheduling.SchedulePoiDTO;
import com.pcs.guava.dto.scheduling.ScheduleResult;
import com.pcs.guava.dto.scheduling.SchedulingDTO;
import com.pcs.guava.dto.scheduling.SchedulingESBDTO;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.rest.exception.GalaxyRestException;
import com.pcs.guava.service.SchedulingService;
import com.pcs.guava.service.repository.RoutingRepository;

@Service
public class SchedulingServiceImpl implements SchedulingService {

	private static final Logger logger = LoggerFactory
			.getLogger(SchedulingServiceImpl.class);

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	RoutingRepository routingRepository;

	@Autowired
	RoutingServiceImpl routingServiceImpl;

	@Autowired
	@Qualifier("guavaESBClient")
	private BaseClient guavaESBClient;

	@Autowired
	@Qualifier("platformESBClient")
	private BaseClient platformESBClient;

	@Value("${alpine.esb.marker.field}")
	private String checkRouteExist;

	@Value("${alpine.esb.marker.field}")
	private String checkScheduleExist;

	@Value("${guava.esb.schedule.create}")
	private String createScheduleEndpoint;

	@SuppressWarnings("unchecked")
	@Override
	public StatusMessageDTO createSchedule(SchedulingDTO schedules) {

		RoutingDTO routeDetails = new RoutingDTO();
		SchedulingESBDTO scheduleESB = new SchedulingESBDTO();
		validateScheduleFields(schedules);
		SearchFieldsDTO routeNameSearch = new SearchFieldsDTO();
		routeNameSearch = routingServiceImpl.createRouteSearch(schedules
				.getRouteName());

		List<ReturnFieldsDTO> searchList = new ArrayList<ReturnFieldsDTO>();
		try {
			searchList = platformESBClient.post(checkRouteExist, getJwtToken(),
					routeNameSearch, List.class, ReturnFieldsDTO.class);
		} catch (GalaxyRestException e) {
			if (e.getCode()
					.toString()
					.equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()
							.toString())) {
				throw new GalaxyException(GalaxyCommonErrorCodes.NO_ROUTE_EXIST);

			}

		}
		StatusMessageDTO statusMessage = new StatusMessageDTO();
		if (searchList != null) {

			String identifier = searchList.get(0).getIdentifier().getValue();
			IdentityDTO route = new IdentityDTO();
			PlatformEntityDTO type = new PlatformEntityDTO();
			type.setPlatformEntityType(PLATFORM_TYPE);

			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(schedules.getDomain());
			route.setDomain(domainDTO);
			EntityTemplateDTO routeTemplate = new EntityTemplateDTO();
			routeTemplate.setEntityTemplateName(ROUTE_TEMPLATE);
			route.setEntityTemplate(routeTemplate);
			FieldMapDTO routeField = new FieldMapDTO();
			routeField.setKey(IDENTIFIER.getFieldName());
			routeField.setValue(identifier);
			route.setIdentifier(routeField);
			route.setPlatformEntity(type);

			PoiResult poiEntities = routingRepository.findRouteChild(
					identifier, POI_TEMPLATE, POI_TYPE);
			validatePoiFields(poiEntities, schedules);
			scheduleESB = createESBPayload(schedules);
			scheduleESB.setRouteIdentity(route);

			statusMessage = guavaESBClient.post(createScheduleEndpoint,
					getJwtToken(), scheduleESB, StatusMessageDTO.class);

			// SchedulingRoute
			// ScheduleDestination

		}
		return statusMessage;
	}

	private SchedulingESBDTO createESBPayload(SchedulingDTO schedules) {
		SchedulingESBDTO scheduleESB = new SchedulingESBDTO();
		EntityDTO scheduleEntity = new EntityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(schedules.getDomain());
		scheduleEntity.setDomain(domain);
		EntityTemplateDTO scheduleTemplate = new EntityTemplateDTO();
		scheduleTemplate.setEntityTemplateName(SCHEDULE_TEMPLATE);
		scheduleEntity.setEntityTemplate(scheduleTemplate);
		scheduleEntity.setFieldValues(constuctScheduleFields(schedules));
		scheduleESB.setScheduleEntity(scheduleEntity);
		scheduleESB
				.setScheduleDestination(createScheduleDestination(schedules));
		return scheduleESB;
	}

	private List<EntityDTO> createScheduleDestination(SchedulingDTO schedules) {
		List<EntityDTO> wayPoints = new ArrayList<EntityDTO>();

		for (SchedulePoiDTO poi : schedules.getDestinationPoints()) {
			EntityDTO scheduleDest = new EntityDTO();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(schedules.getDomain());
			scheduleDest.setDomain(domain);
			EntityTemplateDTO scheduleDestTemplate = new EntityTemplateDTO();
			scheduleDestTemplate.setEntityTemplateName(SCHEDULE_DEST_TEMPLATE);
			scheduleDest.setEntityTemplate(scheduleDestTemplate);
			scheduleDest.setFieldValues(constuctPointFields(poi));
			wayPoints.add(scheduleDest);
		}
		return wayPoints;
	}

	private List<FieldMapDTO> constuctPointFields(SchedulePoiDTO poi) {
		List<FieldMapDTO> poiScheduleFields = new ArrayList<FieldMapDTO>();

		FieldMapDTO arrivalTime = new FieldMapDTO();
		arrivalTime.setKey(ARRIVAL_TIME.getFieldName());
		arrivalTime.setValue(poi.getArrivalTime());

		FieldMapDTO toleranceTime = new FieldMapDTO();
		toleranceTime.setKey(TOLERANCE_TIME.getFieldName());
		toleranceTime.setValue(poi.getTimeTolerance());

		FieldMapDTO departureTime = new FieldMapDTO();
		departureTime.setKey(DEPARTURE_TIME.getFieldName());
		departureTime.setValue(poi.getDepartureTime());

		FieldMapDTO poiName = new FieldMapDTO();
		poiName.setKey(POI_NAME.getFieldName());
		poiName.setValue(poi.getPoiName());

		poiScheduleFields.add(arrivalTime);
		poiScheduleFields.add(toleranceTime);
		poiScheduleFields.add(departureTime);
		poiScheduleFields.add(poiName);

		return poiScheduleFields;

	}

	private List<FieldMapDTO> constuctScheduleFields(SchedulingDTO schedules) {
		List<FieldMapDTO> scheduleFields = new ArrayList<FieldMapDTO>();

		FieldMapDTO scheduleName = new FieldMapDTO();
		scheduleName.setKey(SCHEDULE_NAME.getFieldName());
		scheduleName.setValue(schedules.getScheduleName());

		FieldMapDTO scheduleDesc = new FieldMapDTO();
		scheduleDesc.setKey(SCHEDULE_DESC.getFieldName());
		scheduleDesc.setValue(schedules.getDescription());

		FieldMapDTO startTime = new FieldMapDTO();
		startTime.setKey(START_TIME.getFieldName());
		startTime.setValue(schedules.getStartTime());

		FieldMapDTO endTime = new FieldMapDTO();
		endTime.setKey(END_TIME.getFieldName());
		endTime.setValue(schedules.getEndTime());

		FieldMapDTO daySpan = new FieldMapDTO();
		daySpan.setKey(DAY_SPAN.getFieldName());
		daySpan.setValue(schedules.getDaySpan().toString());

		FieldMapDTO routeName = new FieldMapDTO();
		routeName.setKey(ROUTE_NAME.getFieldName());
		routeName.setValue(schedules.getRouteName());

		scheduleFields.add(scheduleName);
		scheduleFields.add(scheduleDesc);
		scheduleFields.add(startTime);
		scheduleFields.add(endTime);
		scheduleFields.add(endTime);
		scheduleFields.add(daySpan);
		scheduleFields.add(routeName);

		return scheduleFields;
	}

	private void validatePoiFields(PoiResult entities, SchedulingDTO schedules) {
		List<PoiDeSerializeDTO> destinationPoints = new ArrayList<PoiDeSerializeDTO>();
		List<SchedulePoiDTO> schedulePoints = new ArrayList<SchedulePoiDTO>();
		destinationPoints = entities.getResult();
		schedulePoints = schedules.getDestinationPoints();
		if (destinationPoints.size() != schedulePoints.size()) {
			throw new GalaxyException(GalaxyCommonErrorCodes.INVALID_NO_POI);
		}

		List<String> poiNames = new ArrayList<String>();
		for (PoiDeSerializeDTO poi : entities.getResult()) {
			List<FieldMapDTO> poiData = new ArrayList<FieldMapDTO>();
			poiData = poi.getPoiData();
			for (FieldMapDTO poiField : poiData) {
				if (poiField.getKey().equals(POI_NAME.getFieldName())) {
					poiNames.add(poiField.getValue());
				}
			}
		}

		for (SchedulePoiDTO schedulePoi : schedules.getDestinationPoints()) {
			if (!poiNames.contains(schedulePoi.getPoiName())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_POI_NAME);
			}
		}

	}

	private void validateScheduleFields(SchedulingDTO schedule) {
		// TODO Auto-generated method stub

		ValidationUtils.validateMandatoryFields(schedule, ROUTE_NAME,
				SCHEDULE_NAME, SCHEDULE_DESC, START_TIME, END_TIME, DAY_SPAN);

		ValidationUtils.validateCollection(DESTN_POINTS,
				schedule.getDestinationPoints());

		validateDateFields(schedule, START_TIME, END_TIME);
		for (SchedulePoiDTO poi : schedule.getDestinationPoints()) {
			validateDateFields(poi, ARRIVAL_TIME, TOLERANCE_TIME);
		}

		if (schedule.getDaySpan() == 2 || schedule.getDaySpan() == 1) {

			compareTime(schedule.getStartTime(), schedule.getEndTime(),
					schedule.getDaySpan());
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.INVALID_DAY_SPAN);
		}

		if (schedule.getDomain() == null) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
			schedule.setDomain(domainDTO.getDomainName());

		}
	}

	public static void validateDateFields(Object dtoObject,
			DataFields... fieldNames) {
		validateDatesField(dtoObject, Arrays.asList(fieldNames));
	}

	public static void validateDatesField(Object dtoObject,
			List<DataFields> fieldNames) {

		if (dtoObject == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.INCOMPLETE_REQUEST);
		}
		for (DataFields dataFields : fieldNames) {

			try {
				Object fieldValueObj = PropertyUtils.getProperty(dtoObject,
						dataFields.getVariableName());
				if (!(dateValidate((String) fieldValueObj))) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.INVALID_TIME_FRMT,
							dataFields.getDescription());
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				logger.error("Error in Reading property of the given Object");
				throw new GalaxyException(
						GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
			}
		}
	}

	private static boolean dateValidate(String date) {
		String pattern = "(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(date);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	private String addTime(String stopageTime, String arrivalTime) {

		int stopTime = convertToSeconds(stopageTime);
		int arriveTime = convertToSeconds(arrivalTime);

		int departureTime = stopTime + arriveTime;
		String departure = convertSecToTime(departureTime);
		return departure;
	}

	private String convertSecToTime(int departureTime) {
		int hr = departureTime / 3600;
		int rem = departureTime % 3600;
		int mn = rem / 60;
		int sec = rem % 60;
		String hrStr = (hr < 10 ? "0" : "") + hr;
		String mnStr = (mn < 10 ? "0" : "") + mn;
		String secStr = (sec < 10 ? "0" : "") + sec;

		String time = hrStr + ":" + mnStr + ":" + secStr;
		return time;
	}

	private int convertToSeconds(String time) {
		String[] timeArr = time.split(":");

		int hour = Integer.parseInt(timeArr[0]);
		int minute = Integer.parseInt(timeArr[1]);
		int second = Integer.parseInt(timeArr[2]);

		int seconds;
		seconds = second + (60 * minute) + (3600 * hour);
		return seconds;
	}

	private void compareTime(String startTime, String endTime, int daySpan) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(startTime);
			date2 = sdf.parse(endTime);
		} catch (ParseException e) {
			logger.error("Error in Reading property of the given Object");
			throw new GalaxyException(GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
		}
		if (date2.compareTo(date1) == 0) {
			throw new GalaxyException(GalaxyCommonErrorCodes.INVALID_TIME_RANGE);
		}
		if (daySpan == 1) {
			if (date2.compareTo(date1) < 0) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_END_TIME);
			}
			if (date1.compareTo(date2) > 0) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_START_TIME);
			}
		}
		if (daySpan == 2) {
			if (date1.compareTo(date2) < 0) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_START_TIME);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SchedulingDTO viewSchedule(String scheduleName) {
		SchedulingDTO schedule = new SchedulingDTO();
		SchedulingDTO scheduleDetails = new SchedulingDTO();
		schedule.setScheduleName(scheduleName);
		if (schedule.getDomain() == null) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());

			schedule.setDomain(domainDTO.getDomainName());

		}
		List<ReturnFieldsDTO> searchList = new ArrayList<ReturnFieldsDTO>();
		SearchFieldsDTO scheduleNameSearch = new SearchFieldsDTO();

		scheduleNameSearch = createScheduleSearch(scheduleName);
		searchList = platformESBClient.post(checkScheduleExist, getJwtToken(),
				scheduleNameSearch, List.class, ReturnFieldsDTO.class);

		if (searchList != null) {
			List<FieldMapDTO> scheduleData = new ArrayList<FieldMapDTO>();
			scheduleData = searchList.get(0).getDataprovider();
			String identifier = searchList.get(0).getIdentifier().getValue();
			ScheduleResult entities = routingRepository.findScheduleChild(
					identifier, SCHEDULE_DEST_TEMPLATE);

			scheduleDetails = constructScheduleFields(entities.getResult(),
					scheduleData);
		}
		return scheduleDetails;
	}

	private SchedulingDTO constructScheduleFields(
			List<ScheduleDeSerializeDTO> dest, List<FieldMapDTO> scheduleData) {
		SchedulingDTO schedule = new SchedulingDTO();

		schedule = getScheduleInfo(scheduleData);

		List<SchedulePoiDTO> destinationPoints = new ArrayList<SchedulePoiDTO>();
		for (ScheduleDeSerializeDTO poi : dest) {
			SchedulePoiDTO points = new SchedulePoiDTO();
			List<FieldMapDTO> poiData = new ArrayList<FieldMapDTO>();
			poiData = poi.getScheduleDest();
			for (FieldMapDTO poiField : poiData) {
				if (poiField.getKey().equals(ARRIVAL_TIME.getFieldName())) {
					points.setArrivalTime(poiField.getValue());
				} else if (poiField.getKey().equals(
						TOLERANCE_TIME.getFieldName())) {
					points.setTimeTolerance(poiField.getValue());
				} else if (poiField.getKey().equals(
						DEPARTURE_TIME.getFieldName())) {
					points.setDepartureTime(poiField.getValue());
				} else if (poiField.getKey().equals(POI_NAME.getFieldName())) {
					points.setPoiName(poiField.getValue());
				}
			}

			destinationPoints.add(points);
		}
		schedule.setDestinationPoints(destinationPoints);
		return schedule;
	}

	private SchedulingDTO getScheduleInfo(List<FieldMapDTO> scheduleData) {
		SchedulingDTO schedule = new SchedulingDTO();
		for (FieldMapDTO field : scheduleData) {

			if (field.getKey().equals(ROUTE_NAME.getFieldName())) {
				if (!isBlank(field.getValue())) {
					schedule.setRouteName(field.getValue());
				}
			} else if (field.getKey().equals(START_TIME.getFieldName())) {
				if (!isBlank(field.getValue())) {
					schedule.setStartTime(field.getValue());
				}
			} else if (field.getKey().equals(SCHEDULE_DESC.getFieldName())) {
				schedule.setDescription(field.getValue());
			} else if (field.getKey().equals(SCHEDULE_NAME.getFieldName())) {
				schedule.setScheduleName(field.getValue());
			} else if (field.getKey().equals(END_TIME.getFieldName())) {
				schedule.setEndTime(field.getValue());
			} else if (field.getKey().equals(DAY_SPAN.getFieldName())) {
				schedule.setDaySpan(Integer.parseInt(field.getValue()));
			}
		}

		return schedule;
	}

	public SearchFieldsDTO createScheduleSearch(String scheduleName) {

		SearchFieldsDTO scheduleNameSearch = new SearchFieldsDTO();
		EntityTemplateDTO routeTemplate = new EntityTemplateDTO();
		routeTemplate.setEntityTemplateName(SCHEDULE_TEMPLATE);
		scheduleNameSearch.setEntityTemplate(routeTemplate);

		List<FieldMapDTO> scheduleFields = new ArrayList<FieldMapDTO>();
		// Construct route name map
		FieldMapDTO scheduleNameField = new FieldMapDTO();
		scheduleNameField.setKey(SCHEDULE_NAME.getFieldName());
		scheduleNameField.setValue(scheduleName);
		scheduleFields.add(scheduleNameField);
		scheduleNameSearch.setSearchFields(scheduleFields);
		return scheduleNameSearch;
	}

}
