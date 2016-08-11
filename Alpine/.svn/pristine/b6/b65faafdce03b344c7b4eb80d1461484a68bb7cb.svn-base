/**
 *
 */
package com.pcs.alpine.serviceImpl;

import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.Status.ACTIVE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.model.PlatformEntity;
import com.pcs.alpine.serviceImpl.repository.PlatformEntityRepository;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.PlatformEntityDTO;

/**
 * GlobalEntityServiceImpl
 * 
 * @description Responsible for providing the service implementations for a
 *              global entity
 * @author Daniela(PCSEG191)
 * @date 21 Aug 2014
 * @since galaxy-1.0.0
 */
@Service
public class PlatformEntityServiceImpl implements PlatformEntityService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PlatformEntityServiceImpl.class);

	@Autowired
	private PlatformEntityRepository globalEntityRepository;

	@Autowired
	private StatusService statusService;

	/**
	 * @Description Responsible for fetching all global entities
	 * @return List<GlobalEntityDTO>
	 */
	@Override
	public List<PlatformEntityDTO> getGlobalEntities() {

		LOGGER.debug("<<--Entry getGlobalEntities-->>");
		List<PlatformEntityDTO> globalEntitiesDTO = new ArrayList<PlatformEntityDTO>();
		Integer statusKey = statusService.getStatus(ACTIVE.name());
		Boolean isDefault = false;
		List<PlatformEntity> globalEntities = globalEntityRepository
		        .getGlobalEntities(statusKey, isDefault);
		if (globalEntities != null) {
			for (PlatformEntity globalEntity : globalEntities) {
				PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
				globalEntityDTO.setPlatformEntityType(globalEntity
				        .getPlatformEntityType());
				globalEntityDTO.setDescription(globalEntity.getDescription());
				globalEntityDTO.setIsDefault(globalEntity.getIsDefault());
				globalEntitiesDTO.add(globalEntityDTO);
			}
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		LOGGER.debug("<<--Exit getGlobalEntities-->>");
		return globalEntitiesDTO;
	}

	@Override
	public PlatformEntityDTO getGlobalEntityWithName(String globalEntityName) {

		LOGGER.debug("<<--Entry getGlobalEntityWithName-->>");

		Integer statusKey = statusService.getStatus(ACTIVE.name());
		PlatformEntity globalEntity = globalEntityRepository
		        .getPlatformEntityWithName(globalEntityName, statusKey);
		if (globalEntity == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.getDescription());
		}
		PlatformEntityDTO plattformEntityDTO = new PlatformEntityDTO();
		plattformEntityDTO.setPlatformEntityType(globalEntity
		        .getPlatformEntityType());
		plattformEntityDTO.setDescription(globalEntity.getDescription());
		plattformEntityDTO.setIsDefault(globalEntity.getIsDefault());

		LOGGER.debug("<<--Exit getGlobalEntityWithName-->>");
		return plattformEntityDTO;
	}

	@Override
	public List<PlatformEntityDTO> getDefaultGlobalEntities() {

		LOGGER.debug("<<--Entry getGlobalEntities-->>");
		List<PlatformEntityDTO> globalEntitiesDTO = new ArrayList<PlatformEntityDTO>();
		Integer statusKey = statusService.getStatus(ACTIVE.name());
		Boolean isDefault = true;
		List<PlatformEntity> globalEntities = globalEntityRepository
		        .getGlobalEntities(statusKey, isDefault);
		if (globalEntities != null) {
			for (PlatformEntity globalEntity : globalEntities) {
				PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
				globalEntityDTO.setPlatformEntityType(globalEntity
				        .getPlatformEntityType());
				globalEntityDTO.setDescription(globalEntity.getDescription());
				globalEntityDTO.setIsDefault(globalEntity.getIsDefault());
				globalEntitiesDTO.add(globalEntityDTO);
			}
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		LOGGER.debug("<<--Exit getGlobalEntities-->>");
		return globalEntitiesDTO;
	}

}
