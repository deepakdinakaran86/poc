/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.services;

import static com.pcs.datasource.enums.DataSourceFields.DATASOURCE_NAME;
import static com.pcs.datasource.enums.DataSourceFields.PARAMETERS;
import static com.pcs.datasource.enums.DataSourceFields.WESOCKET_CLIENT;
import static com.pcs.datasource.enums.ParameterFields.DATA_TYPE;
import static com.pcs.datasource.enums.ParameterFields.NAME;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.randname.RandomNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.DatasourceStatusDTO;
import com.pcs.datasource.dto.MessageDTO;
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.SubscriptionContext;
import com.pcs.datasource.dto.SubscriptionDTO;
import com.pcs.datasource.enums.WebsocketClient;
import com.pcs.datasource.model.DatasourceRegistration;
import com.pcs.datasource.publisher.MessagePublisher;
import com.pcs.datasource.repository.DatasourceRepository;
import com.pcs.datasource.services.utils.CacheUtility;
import com.pcs.datasource.subscribe.DatasourceSubscriber;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * @description Implementation Class for DatasourceRegistration
 * @author pcseg129(Seena Jyothish)
 * @date 22 Jan 2015
 */
@Service
public class DatasourceRegistrationServiceImpl implements
		DatasourceRegistrationService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DatasourceRegistrationServiceImpl.class);

	@Autowired
	private DatasourceRepository registrationRepository;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private DatasourceSubscriber subscriber;

	@Autowired
	private CacheUtility cacheUtility;

	@Value("${datasource.cache.sub.context}")
	private String subscriptionContext;

	@Override
	public DatasourceStatusDTO registerDatasource(DatasourceDTO datasourceDTO) {
		// validate mandatory field
		validateMandatoryField(DATASOURCE_NAME.getFieldName(),
				datasourceDTO.getDatasourceName());
		DatasourceStatusDTO datasourceStatusDTO = new DatasourceStatusDTO();
		datasourceStatusDTO.setStatus(Status.SUCCESS);
		boolean isUnique = true;
		if (isDatasourceExist(datasourceDTO.getDatasourceName())) {
			isUnique = false;
			datasourceDTO.setDatasourceName(generateUniqueDsName(null));// - an
																		// algorithm
																		// to
																		// generate
																		// a
																		// unique
																		// name
																		// for
																		// datasource
		}
		DatasourceRegistration datasourceRegistration = createDataRegistration(datasourceDTO);
		registrationRepository.saveDatasource(datasourceRegistration,
				datasourceDTO.getParameters());
		if (!isUnique) { // to do
			// create a unique datasource name
			// datasourceDTO.setDatasourceName(createUniqueDatasourceName(datasourceRegistration));
		}
		datasourceStatusDTO.setUniqueDatasourceName(datasourceDTO
				.getDatasourceName());
		return datasourceStatusDTO;
	}

	/*
	 * get all datasources
	 */
	@Override
	public List<DatasourceDTO> getAllDatasource() {
		List<DatasourceDTO> datasourceDTOs = new ArrayList<DatasourceDTO>();
		datasourceDTOs = registrationRepository.getAllDatasource();
		return datasourceDTOs;
	}

	public boolean isDatasourceExist(String datasourceName) {
		validateMandatoryField(DATASOURCE_NAME, datasourceName);
		return registrationRepository.isDatasourceExist(datasourceName);
	}

	public String createUniqueDatasourceName(
			DatasourceRegistration datasourceRegistration) {
		datasourceRegistration.setDatasourceName(datasourceRegistration
				.getDatasourceName() + "_" + datasourceRegistration.getId());
		// registrationRepository.saveDatasourceRegistration(datasourceRegistration);
		// //to do
		return datasourceRegistration.getDatasourceName();
	}

	@Override
	public List<ParameterDTO> getDatasourceParameters(String datasourceName) {
		validateMandatoryField(DATASOURCE_NAME, datasourceName);
		List<ParameterDTO> parameterDTOs = null;
		if (isDatasourceExist(datasourceName)) {
			parameterDTOs = registrationRepository
					.getParameters(datasourceName);
		} else {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
		}
		return parameterDTOs;
	}

	public SubscriptionDTO subscribe(String datasourceName,
			WebsocketClient wsClient) {
		validateMandatoryField(DATASOURCE_NAME, datasourceName);
		SubscriptionDTO subscription = null;
		if (isDatasourceExist(datasourceName)) {
			subscription = subscriber.getSubscription(datasourceName, wsClient);
		} else {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
		}
		return subscription;
	}

	@Override
	public SubscriptionDTO subscribe(SortedSet<String> datasourceNames,
			String wsClient) {
		WebsocketClient websocketClient;
		if (StringUtils.isEmpty(wsClient)) {
			websocketClient = WebsocketClient.WEBORB;
		} else {
			websocketClient = WebsocketClient.getWebsocketClient(wsClient);
			if (websocketClient == null) {
				throw new DeviceCloudException(
						DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
						WESOCKET_CLIENT.getDescription());
			}
		}
		SubscriptionDTO subscription = null;
		if (!CollectionUtils.isEmpty(datasourceNames)) {
			if (datasourceNames.size() == 1) {
				return subscribe(datasourceNames.first(), websocketClient);
			}
			for (String datasourceName : datasourceNames) {
				validateMandatoryField(DATASOURCE_NAME, datasourceName);
				if (!isDatasourceExist(datasourceName)) {
					throw new DeviceCloudException(
							DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
				}
			}
			String contextName = registrationRepository
					.createContext(datasourceNames);
			if (contextName != null && !StringUtils.isBlank(contextName)) {
				subscription = subscriber.getSubscription(contextName,
						websocketClient);
			} else {
				throw new DeviceCloudException(
						DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
			}
		} else {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED);
		}
		return subscription;
	}

	/**
	 * Method to create DatasourceRegistration Model from DatasourceDTO
	 */
	private DatasourceRegistration createDataRegistration(
			DatasourceDTO datasourceDTO) {
		DatasourceRegistration datasourceRegistration = new DatasourceRegistration();
		datasourceRegistration.setDatasourceName(datasourceDTO
				.getDatasourceName());
		// datasourceRegistration.setDatasourceUniqueName(datasourceDTO.getDatasourceName());
		return datasourceRegistration;
	}

	/**
	 * Method to publish a message from a datasource
	 */
	@Override
	public StatusMessageDTO publish(String datasourceName, MessageDTO messageDTO) {
		validateMandatoryField(DATASOURCE_NAME, datasourceName);
		try {
			datasourceName = URLDecoder.decode(datasourceName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		if (isDatasourceExist(datasourceName)) {
			StatusMessageDTO status = new StatusMessageDTO();
			if (messageDTO != null) {
				messageDTO.setDatasourceName(datasourceName);
			}
			status = messagePublisher.publishToJMSTopic(datasourceName,
					messageDTO);
			return status;
		} else {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
		}
	}

	@Override
	public SubscriptionContext getSubscriptionContexts(String datasourceName) {
		try {
			datasourceName = URLDecoder.decode(datasourceName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		SubscriptionContext context = new SubscriptionContext();
		List<String> contexts = null;
		validateMandatoryField(DATASOURCE_NAME, datasourceName);
		if (isDatasourceExist(datasourceName)) {
			contexts = registrationRepository
					.getSubscribedContexts(datasourceName);
			if (contexts == null) {
				context.setStatus(Status.FAILURE);
			} else {
				context.setStatus(Status.SUCCESS);
				context.setContexts(contexts);
			}
			return context;
		} else {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
		}
	}

	/**
	 * Method to deregister a datasource This method will update datasource's
	 * is_active flag to false and remove the datasource from all the contexts
	 * it belongs
	 */
	@Override
	public StatusMessageDTO deRegister(String datasourceName) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		validateMandatoryField(DATASOURCE_NAME, datasourceName);
		if (isDatasourceExist(datasourceName)) {
			registrationRepository.deregisterDatasource(datasourceName);
		} else {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
		}
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * Method to update parameters of a datasource
	 */
	@Override
	public StatusMessageDTO updateDatasource(DatasourceDTO datasourceDTO) {
		// validate mandatory fields
		validateMandatoryFields(datasourceDTO, DATASOURCE_NAME, PARAMETERS);

		if (!isDatasourceExist(datasourceDTO.getDatasourceName())) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.DATASOURCE_DOES_NOT_EXIST);
		}

		if (datasourceDTO.getParameters() != null
				&& !datasourceDTO.getParameters().isEmpty()) {
			for (ParameterDTO parameterDTO : datasourceDTO.getParameters()) {
				validateMandatoryFields(parameterDTO, NAME, DATA_TYPE);
			}
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		try {
			registrationRepository.updateDatasource(datasourceDTO);
			statusMessageDTO.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			LOGGER.error("Error updating datasource parameters for {}",
					datasourceDTO.getDatasourceName());
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		return statusMessageDTO;
	}

	private String generateUniqueDsName(Integer datasourceId) {
		if (datasourceId == null) {
			Random random = new Random();
			datasourceId = random.nextInt();
		}
		RandomNameGenerator nameGenerator = new RandomNameGenerator(
				datasourceId);
		return nameGenerator.next();
	}
}
