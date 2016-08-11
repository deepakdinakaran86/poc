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
import static com.pcs.fms.web.constants.FMSWebConstants.PLATFORM_MARKER_TEMPLATE;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.TagTypeTemplate;
import com.pcs.fms.web.model.TagTypeTemplateModel;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class TagTypeService extends BaseService {

	@Value("${fms.services.createTagType}")
	private String createTagTypeEndpointUri;

	@Value("${fms.services.findTagType}")
	private String findTagTypeEndpointUri;

	@Value("${fms.services.listTagTypes}")
	private String listTagTypeEndpointUri;

	@Value("${fms.services.getAllTemplates}")
	private String listTemplatesEndpointUri;

	/**
	 * Method to create a tag type
	 * 
	 * @param TagTypeTemplate
	 * @return StatusMessageDTO
	 */
	public FMSResponse<StatusMessageDTO> createTagType(
			TagTypeTemplateModel tagTypeTemplateModel, String domainName) {
		String createTagTypeServiceURI = getServiceURI(createTagTypeEndpointUri);
		TagTypeTemplate tagTypeTemplateDTO = new TagTypeTemplate();
		tagTypeTemplateDTO.setDomainName(domainName);
		tagTypeTemplateDTO.setTagType(tagTypeTemplateModel.getTagTypeName());
		tagTypeTemplateDTO.setTagTypeValues(tagTypeTemplateModel
				.getTagTypeValues());
		tagTypeTemplateDTO.setStatus("ACTIVE");

		return getPlatformClient().postResource(createTagTypeServiceURI,
				tagTypeTemplateDTO, StatusMessageDTO.class);
	}

	/**
	 * Method to get all tag Types
	 * 
	 * @param (query) domainName
	 * @return List<EntityTemplateDTO>
	 */
	public FMSResponse<List<EntityTemplateDTO>> getAllTagTypes(String currDomain) {
		String listTagTypesServiceURI = getServiceURI(listTagTypeEndpointUri).replace("{domain}", currDomain);
		

		Type tagTypeList = new TypeToken<List<EntityTemplateDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().getResource(listTagTypesServiceURI,
				tagTypeList);
	}

	/**
	 * Method to get a tag type
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<TagTypeTemplate> viewTagType(String tag_type_name,
			String currDomain) {
		String findTagTypeServiceURI = getServiceURI(findTagTypeEndpointUri)
				.replace("{tag_type_name}", tag_type_name).replace("{domain}",
						currDomain);

		return getPlatformClient().getResource(findTagTypeServiceURI,
				TagTypeTemplate.class);
	}

	/**
	 * Method to get all templates of a domain
	 * 
	 * @param (query) domainName
	 * @return List<EntityTemplateDTO>
	 */
	public FMSResponse<List<EntityTemplateDTO>> getAllTemplates(
			String currDomain) {
		String listTemplatesServiceURI = getServiceURI(listTemplatesEndpointUri);

		Type templateList = new TypeToken<List<EntityTemplateDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(currDomain);
		entityTemplateDTO.setDomain(domain);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setParentTemplate(PLATFORM_MARKER_TEMPLATE);

		return getPlatformClient().postResource(listTemplatesServiceURI,
				entityTemplateDTO, templateList);
	}

}
