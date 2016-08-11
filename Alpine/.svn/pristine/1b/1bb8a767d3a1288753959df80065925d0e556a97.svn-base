package com.pcs.web.services;

import static com.pcs.web.constants.WebConstants.DATASOURCE_NAME;
import static com.pcs.web.constants.WebConstants.DESTINATION;
import static com.pcs.web.constants.WebConstants.INVALID_ASSETS;
import static com.pcs.web.constants.WebConstants.SOURCE_ID;
import static com.pcs.web.constants.WebConstants.VALID_ASSETS;
import static com.pcs.web.constants.WebConstants.WEB_SOCKET_URL;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.reflect.TypeToken;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.ErrorDTO;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.LiveFieldsDTO;
import com.pcs.web.dto.ReturnFieldsDTO;
import com.pcs.web.dto.SearchFieldsDTO;
import com.pcs.web.dto.ValidAssetModelDTO;
import com.pcs.web.dto.WebsocketInfoDTO;

/**
 * @author pcseg129
 * 
 */
@Service
public class LiveTrackingService extends BaseService {

	@Value("${cummins.services.websocket.subscribe}")
	private String websocketSubscribeEndpointUri;

	@Value("${cummins.services.markerDatasource}")
	private String markerDatasourceEndpointUri;

	public ModelAndView getLiveTracking(List<LiveFieldsDTO> searchFieldsDTO) {

		//TODO construct invalid list
		String assetDatasourceUri = getServiceURI(markerDatasourceEndpointUri);

		List<String> validListWithDataSource = new ArrayList<String>();
		List<String> invalidList = new ArrayList<String>();
		List<String> allAssets = new ArrayList<String>();
		List<String> validSourceIds = new ArrayList<String>();
		List<ValidAssetModelDTO> validAssetModels = new ArrayList<ValidAssetModelDTO>();


		//for each source id fetch the details
		for (LiveFieldsDTO liveFieldsDTO : searchFieldsDTO) {

			if(StringUtils.isNotBlank(liveFieldsDTO.getSourceId())){
				SearchFieldsDTO searchField = createLiveTrackingInput(liveFieldsDTO);
				//fetch datasource name 
				Type returnFieldsType = new TypeToken<List<ReturnFieldsDTO>>() {
					private static final long serialVersionUID = 5936335989523954928L;
				}.getType();

				CumminsResponse dataSrcRes = getPlatformClient().postResource(assetDatasourceUri,
						searchField, returnFieldsType);


				//This is the service response			
				List<ReturnFieldsDTO> returnFieldsListDTO = new ArrayList<ReturnFieldsDTO>();
				if (dataSrcRes == null || dataSrcRes.getEntity() == null
						|| dataSrcRes.getEntity() instanceof ErrorDTO) {

				} else //if (dataSrcRes.getEntity() instanceof ReturnFieldsDTO)
				{
					returnFieldsListDTO = (List<ReturnFieldsDTO>) dataSrcRes.getEntity();

					//Iterate to check if valid or invalid
					for (ReturnFieldsDTO returnFieldsDTO : returnFieldsListDTO) {	
						//fetch the datasource name from the response
						FieldMapDTO datasourceMap = new FieldMapDTO();
						datasourceMap.setKey(DATASOURCE_NAME);
						datasourceMap = fetchField(returnFieldsDTO.getReturnFields(), datasourceMap);

						if(StringUtils.isBlank(datasourceMap.getValue())){
							//Add asset to invalid list
							invalidList.add(liveFieldsDTO.getAsset());
						}else{
							//Add the valid datasources to the valid list
							validListWithDataSource.add(datasourceMap.getValue());

							ValidAssetModelDTO validAssetModelDTO = constructValidAssetModelDTO(datasourceMap.getValue(),liveFieldsDTO.getAsset(),liveFieldsDTO.getSourceId());
							validAssetModels.add(validAssetModelDTO);
							//list of valid source ids
							validSourceIds.add(liveFieldsDTO.getSourceId());
						}
					}
				}
			}else{
				//Source id is empty , include it invalid list
				invalidList.add(liveFieldsDTO.getAsset());
			}
			allAssets.add(liveFieldsDTO.getAsset());
		}
		return manageLiveTracking(validListWithDataSource,invalidList,validSourceIds,allAssets,validAssetModels);
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private SearchFieldsDTO createLiveTrackingInput(LiveFieldsDTO liveField) {
		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		List<String> returnFields = new ArrayList<String>();
		//sourceId and datasource name are required
		returnFields.add(SOURCE_ID);
		returnFields.add(DATASOURCE_NAME);
		searchFieldsDTO.setReturnFields(returnFields);

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO sourceIdMap = new FieldMapDTO();
		sourceIdMap.setKey(SOURCE_ID);
		sourceIdMap.setValue(liveField.getSourceId());
		searchFields.add(sourceIdMap);
		searchFieldsDTO.setSearchFields(searchFields);
		return searchFieldsDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ModelAndView manageLiveTracking(List<String> validDatasources, List<String> invalidAssets, List<String> sourceIdList, List<String> allAssets,List<ValidAssetModelDTO> validAssetModelDTOs) {

		CumminsResponse webSocketInfoRes = null;
		if (!CollectionUtils.isEmpty(validDatasources)) {

			SortedSet<String> validSet = new TreeSet<String>(
					String.CASE_INSENSITIVE_ORDER);
			validSet.addAll(validDatasources);

			String websocketSubscribeUri = getServiceURI(websocketSubscribeEndpointUri);
			webSocketInfoRes = getPlatformClient().postResource(
					websocketSubscribeUri, validSet, WebsocketInfoDTO.class);
		}

		ModelAndView mav = null;
		WebsocketInfoDTO websocketInfoDTO = null;
		if (webSocketInfoRes == null || webSocketInfoRes.getEntity() == null
				|| webSocketInfoRes.getEntity() instanceof ErrorDTO) {

		} else if (webSocketInfoRes.getEntity() instanceof WebsocketInfoDTO) {
			websocketInfoDTO = (WebsocketInfoDTO) webSocketInfoRes.getEntity();

		}
		// create model for response
		mav = createResponseForLiveTracking(allAssets, invalidAssets,validAssetModelDTOs, websocketInfoDTO);
		return mav;
	}

	private ValidAssetModelDTO constructValidAssetModelDTO(String datasourceName, String assetName, String sourceId){
		ValidAssetModelDTO validAssetModelDTO = new ValidAssetModelDTO();
		validAssetModelDTO.setEntityName(assetName);
		validAssetModelDTO.setDatasourceName(datasourceName);
		validAssetModelDTO.setSourceId(sourceId);
		return validAssetModelDTO;
	}


	@SuppressWarnings("rawtypes")
	private ModelAndView createResponseForLiveTracking(List<String> requestedAssets,List<String> inValidAssets,List<ValidAssetModelDTO>validAssetsForModel,
			WebsocketInfoDTO webSocketInfo) {

		for (Object obj : validAssetsForModel) {
			ValidAssetModelDTO validAssetModelDTO = (ValidAssetModelDTO) obj;
			requestedAssets.remove(validAssetModelDTO.getEntityName());
		}
		requestedAssets.removeAll(validAssetsForModel);
		ModelAndView mav = new ModelAndView();
		if (webSocketInfo != null) {
			mav.addObject(WEB_SOCKET_URL, webSocketInfo.getWebSocketUrl());
			mav.addObject(DESTINATION, webSocketInfo.getDestination());
		}
		mav.addObject(VALID_ASSETS, validAssetsForModel);
		if(!CollectionUtils.isEmpty(requestedAssets)){
			List<String> invalidAssets = requestedAssets;
			if(invalidAssets.containsAll(inValidAssets)){
				mav.addObject(INVALID_ASSETS, invalidAssets);
			}else{
				invalidAssets.addAll(inValidAssets);
				mav.addObject(INVALID_ASSETS, invalidAssets);
			}

			mav.addObject(WEB_SOCKET_URL, StringUtils.EMPTY);
			mav.addObject(DESTINATION, StringUtils.EMPTY);
		}
		return mav;
	}


}
