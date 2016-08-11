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
package com.pcs.galaxy.autoclaim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.galaxy.dto.AssetDTO;
import com.pcs.galaxy.dto.DomainDTO;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.EntityTemplateDTO;
import com.pcs.galaxy.dto.FieldMapDTO;
import com.pcs.galaxy.dto.IdentityDTO;
import com.pcs.galaxy.dto.ReturnFieldsDTO;
import com.pcs.galaxy.dto.SearchFieldsDTO;
import com.pcs.galaxy.dto.StatusMessageDTO;
import com.pcs.galaxy.dto.TagConfiguration;
import com.pcs.galaxy.token.TokenProvider;

/**
 * CreateAsset
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class AssetServices {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AssetServices.class);

	@Value("${autoclaim.login.clientid}")
	private String clientId;
	@Value("${autoclaim.login.clientsecret}")
	private String secret;
	@Value("${autoclaim.login.username}")
	private String userName;
	@Value("${autoclaim.login.password}")
	private String password;
	@Value("${autoclaim.alpine.asset.create}")
	private String createAssetURL;
	@Value("${autoclaim.alpine.marker.search}")
	private String getMarkerURL;
	@Value("${autoclaim.alpine.tagmng.tag}")
	private String tagAssetURL;

	@Autowired
	@Qualifier("alpineClient")
	private BaseClient alpineClient;

	// Fix me :From deviceAsset we are taking only tag.
	public void createAsset(AssetDTO asset, AssetDTO deviceAsset) {
		LOGGER.info("Enter into creteAsset");
		IdentityDTO assetResp = createAssetAvocado(asset);
		if (deviceAsset != null) {
			List<String> tagNames = deviceAsset.getTags();
			if (CollectionUtils.isNotEmpty(tagNames)) {
				LOGGER.info("Tag names are available : {}", tagNames.toString());
				List<IdentityDTO> tags = getIdentitiesOfTag(tagNames,
				        asset.getDomainName());
				tagAsset(tags, assetResp);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private List<IdentityDTO> getIdentitiesOfTag(List<String> tagNames,
	        String domain) {
		List<IdentityDTO> tags = new ArrayList<IdentityDTO>();
		for (String tagName : tagNames) {
			if (StringUtils.isNotEmpty(tagName)) {
				LOGGER.info("Enter into getIdentitiesOfTag: {}", tagName);
				Map<String, String> header = TokenProvider.getHeader(clientId,
				        secret, userName, password);

				SearchFieldsDTO search = new SearchFieldsDTO();
				List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
				FieldMapDTO field = new FieldMapDTO();
				field.setKey("name");
				field.setValue(tagName);
				fields.add(field);
				search.setSearchFields(fields);

				DomainDTO domainDto = new DomainDTO();
				domainDto.setDomainName(domain);
				search.setDomain(domainDto);

				EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
				entityTemplate.setEntityTemplateName("ASSETTAG");
				search.setEntityTemplate(entityTemplate);

				ArrayList<String> fieldList = new ArrayList<String>();
				fieldList.add("name");
				search.setReturnFields(fieldList);

				List<ReturnFieldsDTO> entities = null;
				try {
					entities = alpineClient.post(getMarkerURL, header, search,
					        List.class, ReturnFieldsDTO.class);
				} catch (Exception e) {
					LOGGER.info("No tag available : {} ", tagName);
				}
				if (entities != null) {
					tags.add(createIdentityDTO(entities));
				}
			}
		}
		return tags;
	}

	private IdentityDTO createIdentityDTO(List<ReturnFieldsDTO> entities) {
		IdentityDTO identity = null;
		for (ReturnFieldsDTO entity : entities) {
			identity = new IdentityDTO();
			identity.setIdentifier(entity.getIdentifier());
			identity.setDomain(entity.getDomain());
			identity.setEntityTemplate(entity.getEntityTemplate());
			identity.setPlatformEntity(entity.getPlatformEntity());
		}
		return identity;
	}

	private IdentityDTO assetIdenity(EntityDTO asset) {
		IdentityDTO identity = null;
		if (asset != null) {
			identity = new IdentityDTO();
			identity.setIdentifier(asset.getIdentifier());
			identity.setDomain(asset.getDomain());
			identity.setEntityTemplate(asset.getEntityTemplate());
			identity.setPlatformEntity(asset.getPlatformEntity());
		}
		return identity;
	}

	private IdentityDTO createAssetAvocado(AssetDTO asset) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		EntityDTO entity = alpineClient.post(createAssetURL, header, asset,
		        EntityDTO.class);
		return assetIdenity(entity);
	}

	private StatusMessageDTO tagAsset(List<IdentityDTO> tags,
	        IdentityDTO assetResp) {
		StatusMessageDTO status = null;
		if (CollectionUtils.isNotEmpty(tags) && assetResp != null) {
			LOGGER.info("Enter into tag asset");
			Map<String, String> header = TokenProvider.getHeader(clientId,
			        secret, userName, password);
			TagConfiguration tagConfig = new TagConfiguration();
			tagConfig.setTags(tags);
			tagConfig.setEntity(assetResp);
			status = alpineClient.post(tagAssetURL, header, tagConfig,
			        StatusMessageDTO.class);
		} else {
			LOGGER.info("Did Not Enter into tag asset");
		}
		return status;
	}
}
