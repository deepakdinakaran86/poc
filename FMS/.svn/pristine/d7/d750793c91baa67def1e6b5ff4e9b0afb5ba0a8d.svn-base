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
package com.pcs.fms.web.services;

import static com.pcs.fms.web.constants.FMSWebConstants.MARKER;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.AttachHierarchySearchDTO;
import com.pcs.fms.web.dto.AttachTags;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.PlatformEntityDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.Tag;
import com.pcs.fms.web.model.MapTagToTemplateModel;
import com.pcs.fms.web.model.TagModel;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class TagService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TagService.class);

	@Value("${fms.services.attachEntitiesTag}")
	private String attachEntitiesTagsEndpointUri;

	@Value("${fms.services.findTagsForEntities}")
	private String findTagsForEntitiesEndpointUri;

	@Value("${fms.services.findTag}")
	private String findTagEndpointUri;

	@Value("${fms.services.createTag}")
	private String createTagEndpointUri;

	@Value("${fms.services.mapTagToTemplates}")
	private String mapTagToTemplatesEndpointUri;
	
	@Value("${fms.services.taggedTemplates}")
	private String taggedTemplatesEndpointUri;

	/**
	 * Method to get all tags of an entity
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<List<Tag>> getTagsForEntities(
			AttachHierarchySearchDTO attachHierarchySearchDTO) {

		Type tagsType = new TypeToken<List<Tag>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		String findTagServiceURI = getServiceURI(findTagsForEntitiesEndpointUri);
		return getPlatformClient().postResource(findTagServiceURI,
				attachHierarchySearchDTO, tagsType);
	}

	public FMSResponse<StatusMessageDTO> attachTagsToEntity(
			AttachTags tagConfiguration) {

		// Invoke tag api
		String createTenantTagServiceURI = getServiceURI(attachEntitiesTagsEndpointUri);

		return getPlatformClient().postResource(createTenantTagServiceURI,
				tagConfiguration, StatusMessageDTO.class);
	}

	/**
	 * Method to create a tag
	 * 
	 * @param TagModel
	 * @return StatusMessageDTO
	 */
	public FMSResponse<EntityDTO> createTag(TagModel tagModel, String domainName) {
		String createTagServiceURI = getServiceURI(createTagEndpointUri);
		
		Type type = new TypeToken<List<FieldMapDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		Gson gson = new Gson();
		
		List<FieldMapDTO> fieldValues= gson.fromJson(tagModel.getTagFieldValues(),type);
		
		EntityDTO tagEntity = new EntityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		tagEntity.setDomain(domain);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(tagModel.getTagType());
		tagEntity.setEntityTemplate(entityTemplateDTO);

		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		tagEntity.setPlatformEntity(platformEntityDTO);

		tagEntity.setFieldValues(fieldValues);

		return getPlatformClient().postResource(createTagServiceURI, tagEntity,
				EntityDTO.class);
	}

	/**
	 * Method to get a tag
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> viewTag(TagModel tagModel, String currDomain) {
		String findTagServiceURI = getServiceURI(findTagEndpointUri);
		Tag tagDto = new Tag();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(currDomain);
		tagDto.setDomain(domain);
		tagDto.setName(tagModel.getTagName());
		tagDto.setTagType(tagModel.getTagType());

		return getPlatformClient().postResource(findTagServiceURI, tagDto,
				EntityDTO.class);
	}

	/**
	 * Method to map a tag to Templates
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> mapTagToTemplates(
			MapTagToTemplateModel mapTagToTemplateModel) {
		String mapTagToTemplatesServiceURI = getServiceURI(mapTagToTemplatesEndpointUri);
		return getPlatformClient().postResource(mapTagToTemplatesServiceURI,
				mapTagToTemplateModel, StatusMessageDTO.class);
	}
	
	/**
	 * Method to get a templates that are mapped to a tag
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<List<EntityTemplateDTO>> viewTaggedTemplates(TagModel tagModel, String currDomain) {
		
		String taggedTemplatesServiceURI = getServiceURI(taggedTemplatesEndpointUri);
		
		Type templateList = new TypeToken<List<EntityTemplateDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		
		Tag tagDto = new Tag();
		
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(currDomain);
		tagDto.setDomain(domain);
		tagDto.setName(tagModel.getTagName());
		tagDto.setTagType(tagModel.getTagType());

		return getPlatformClient().postResource(taggedTemplatesServiceURI, tagDto,
				templateList);
	}

}
