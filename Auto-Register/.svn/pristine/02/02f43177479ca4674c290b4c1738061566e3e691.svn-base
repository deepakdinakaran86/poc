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

import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.galaxy.constants.FieldValueConstants.MAKE;
import static com.pcs.galaxy.constants.FieldValueConstants.MODEL;
import static com.pcs.galaxy.constants.FieldValueConstants.SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.galaxy.dto.AssetDTO;
import com.pcs.galaxy.dto.Device;
import com.pcs.galaxy.dto.DeviceManageDTO;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.FieldMapDTO;
import com.pcs.galaxy.dto.IdentityDTO;
import com.pcs.galaxy.dto.Status;
import com.pcs.galaxy.dto.StatusMessageDTO;
import com.pcs.galaxy.dto.Tag;
import com.pcs.galaxy.token.TokenProvider;

/**
 * RegisterDevice
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class RegisterDevice {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AssignDevice.class);

	@Value("${autoclaim.login.clientid}")
	private String clientId;
	@Value("${autoclaim.login.clientsecret}")
	private String secret;
	@Value("${autoclaim.login.username}")
	private String userName;
	@Value("${autoclaim.login.password}")
	private String password;
	@Value("${autoclaim.domain}")
	private String supportDomain;
	@Value("${autoclaim.supertenant}")
	private String superTenantDomain;

	@Value("${autoclaim.saffron.device.register}")
	private String registerURL;

	@Autowired
	private ClaimDevice claimDevice;
	@Autowired
	private AssignDevice assignDevice;
	@Autowired
	private AssetServices createAsset;
	@Autowired
	private UnRecognizedDevices unRecDevice;
	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("saffronClient")
	private BaseClient saffronClient;

	public boolean registerDevice(Device device) {
		LOGGER.info("Enter into registerDevice");

		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		StatusMessageDTO status = null;
		try {
			device.setSourceId(device.getSourceId().toLowerCase());
			device.setDatasourceName(device.getDatasourceName().toLowerCase());
			status = saffronClient.post(registerURL, header, device,
			        StatusMessageDTO.class);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		if (status == null) {
			return false;
		} else {
			if (SUCCESS.equals(status.getStatus().toString())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public StatusMessageDTO onBoardDevice(Device device) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		validateMandatoryFields(device);
		if (registerDevice(device)) {
			LOGGER.info("*** Device Registered Successfully : ***",
			        device.getSourceId());
			IdentityDTO identity = claimDevice.claimDevice(device);
			if (identity != null) {
				LOGGER.info("*** Device Claimed Successfully ***");
				DeviceManageDTO parentDetails = new DeviceManageDTO();
				if (device != null && device.getTags() != null
				        && !device.getTags().isEmpty()) {
					parentDetails = getParentDetails(device.getTags());
					parentDetails.setSourceId(device.getSourceId());
				}
				if (!validateAssignDeviceRequired(device, parentDetails,
				        identity)) {
					status.setStatus(Status.SUCCESS);
					return status;
				} else {
					IdentityDTO realOwner = assignDevice.assignDevice(
					        parentDetails, identity);
					if (realOwner != null) {
						LOGGER.info("*** Device Assigned Successfully ***");
						AssetDTO asset = getAssetDto(parentDetails);
						String domainName = realOwner.getIdentifier()
						        .getValue() + supportDomain;
						try {
							asset.setDomainName(domainName);
							createAsset.createAsset(asset, device.getAsset());
							status.setStatus(Status.SUCCESS);
							LOGGER.info("*** Asset Created Successfully ***");

						} catch (Exception e) {
							LOGGER.info("Asset Creation Failed : "
							        + e.getMessage());
							parentDetails.setDescription(e.getMessage());
						}
						try {
							if (device.getDeviceUser() != null) {
								userService.createUser(device.getDeviceUser(),
								        domainName, device.getSourceId());
							}
							status.setStatus(Status.SUCCESS);
						} catch (Exception e) {
							status.setStatus(Status.FAILURE);
							LOGGER.info("User Creation Failed : "
							        + e.getMessage());
							if (StringUtils.isNotEmpty(parentDetails
							        .getDescription())) {
								parentDetails.setDescription(parentDetails
								        .getDescription()
								        + ", "
								        + e.getMessage());
							} else {
								parentDetails.setDescription(e.getMessage());
							}
						}
						if (StringUtils.isNotEmpty(parentDetails
						        .getDescription())) {
							parentDetails.setDomainName(domainName);
							EntityDTO entity = unRecDevice.prepareUnRecEntity(
							        parentDetails, identity);
							unRecDevice.createUnRecDevice(entity);
						}
					}
				}
			}
		}
		return status;
	}

	private boolean validateAssignDeviceRequired(Device device,
	        DeviceManageDTO parentDetails, IdentityDTO identity) {
		boolean flag = Boolean.FALSE;
		String description = "";
		if (device != null) {
			List<Tag> tags = device.getTags();
			if (tags != null && !tags.isEmpty()) {
				if (tags.size() >= 1) {
					Tag superTenant = tags.get(0);
					if (superTenant != null) {
						if (!superTenant.getName().isEmpty()) {
							flag = Boolean.TRUE;
						} else {
							description = "Super tenant name is not available in Device Tags";
						}
					} else {
						description = "Super tenant name is not available in Device Tags";
					}
				} else {
					description = "Super tenant name is not available in Device Tags";
				}
			} else {
				description = "Device Tags are not Provided";
			}
		}
		if (!description.equals(StringUtils.EMPTY)) {
			LOGGER.info(description);

			parentDetails.setDescription(description);
			parentDetails.setDomainName(superTenantDomain);
			EntityDTO entity = unRecDevice.prepareUnRecEntity(parentDetails,
			        identity);
			unRecDevice.createUnRecDevice(entity);
		}
		return flag;
	}

	private DeviceManageDTO getParentDetails(List<Tag> tags) {
		DeviceManageDTO parentDetail = new DeviceManageDTO();
		if (tags.size() >= 1) {
			if (tags.get(0).getName() != null
			        && !tags.get(0).getName().isEmpty()) {
				parentDetail
				        .setSuperTenant(tags.get(0).getName().toLowerCase());
			}
		}
		if (tags.size() >= 2 && tags.get(1) != null) {
			if (tags.get(1).getName() != null
			        && !tags.get(1).getName().isEmpty()) {
				parentDetail.setParentTenant(tags.get(1).getName()
				        .toLowerCase());
			}
		}
		if (tags.size() >= 3 && tags.get(2) != null) {
			if (tags.get(2).getName() != null
			        && !tags.get(2).getName().isEmpty()) {
				parentDetail
				        .setOwnerTenant(tags.get(2).getName().toLowerCase());
			}
		}

		if (tags.size() >= 5 && tags.get(4) != null)
			parentDetail.setMake(tags.get(4).getName());
		if (tags.size() >= 6 && tags.get(5) != null)
			parentDetail.setModel(tags.get(5).getName());
		if (tags.size() >= 7 && tags.get(6) != null)
			parentDetail.setAssetId(tags.get(6).getName());
		if (tags.size() >= 8 && tags.get(7) != null)
			parentDetail.setSerialNumber(tags.get(7).getName());

		if (tags.size() >= 9 && tags.get(8) != null)
			parentDetail.setAssetType(tags.get(8).getName());
		if (tags.size() >= 10 && tags.get(9) != null)
			parentDetail.setAssetName(tags.get(9).getName());
		if (tags.size() >= 11 && tags.get(10) != null)
			parentDetail.setCreateAsset(tags.get(10).getName());
		return parentDetail;
	}

	private AssetDTO getAssetDto(DeviceManageDTO parentDetails) {
		AssetDTO asset = new AssetDTO();

		if (parentDetails != null) {
			asset.setAssetType(parentDetails.getAssetType());
			asset.setAssetName(parentDetails.getAssetName().toLowerCase());
			if (StringUtils.isEmpty(parentDetails.getAssetId())) {
				asset.setAssetId(parentDetails.getAssetName());
			} else {
				asset.setAssetId(parentDetails.getAssetId());
			}
			asset.setSerialNumber(parentDetails.getSerialNumber());

			List<FieldMapDTO> assetTypeValues = new ArrayList<FieldMapDTO>();
			FieldMapDTO tempMake = new FieldMapDTO();
			tempMake.setKey(MAKE);
			tempMake.setValue(parentDetails.getMake());
			assetTypeValues.add(tempMake);

			FieldMapDTO tempModel = new FieldMapDTO();
			tempModel.setKey(MODEL);
			tempModel.setValue(parentDetails.getModel());
			assetTypeValues.add(tempModel);
			asset.setAssetTypeValues(assetTypeValues);

			if (parentDetails.getOwnerTenant() != null) {
				asset.setDomainName(parentDetails.getOwnerTenant()
				        + supportDomain);
			}
		}
		return asset;
	}

}
