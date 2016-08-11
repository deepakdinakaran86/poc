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
package com.pcs.alpine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.dto.EntityDTO;
import com.pcs.alpine.commons.dto.EntityTemplateDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.TagTypeTemplate;

/**
 * Service Interface for Tag Type
 * 
 * @author Twinkle (PCSEG297)
 * @date May 2016
 * @since galaxy-1.0.0
 */
@Service
public interface TagTypeTemplateService {

	public StatusMessageDTO createTagType(TagTypeTemplate tagTypeTemplate);

	public List<EntityTemplateDTO> getAllTagType(String domainName);

	public TagTypeTemplate getTagType(String TagTypeName, String domainName);

	public EntityDTO createTagType(EntityDTO tagTypeEntity);
}
