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
package com.pcs.web.services;

import static com.pcs.web.constants.WebConstants.FALSE;
import static com.pcs.web.constants.WebConstants.PUBLISH;
import static com.pcs.web.constants.WebConstants.TRUE;
import static com.pcs.web.constants.WebConstants.DEVICE_TEMPLATE;
import static com.pcs.web.constants.WebConstants.ACTIVE;

import java.lang.reflect.Type;
import java.util.List;

import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.EntityDTO;
import com.pcs.web.dto.EntityTemplateDTO;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.IdentityDTO;
import com.pcs.web.dto.StatusDTO;
import com.pcs.web.dto.StatusMessageDTO;


/**
 * Device related services
 * 
 * @author Daniela
 * @date 10 Dec 2014
 * @since galaxy-1.0.0
 * 
 */
@Service
public class DeviceService extends BaseService {

	@Value("${cummins.services.createDevice}")
	private String createDeviceEndpointUri;

	@Value("${cummins.services.updateDevice}")
	private String updateDeviceEndpointUri;

	@Value("${cummins.services.findDevice}")
	private String findDeviceEndpointUri;

	@Value("${cummins.services.listFilterDevice}")
	private String listFilterDeviceEndpointUri;

	/**
	 * Method to list all devices based on domain and template
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<List<EntityDTO>> getDevices(IdentityDTO identity) {
		//set the device template
		getDeviceTemplate(identity);

		String getDevicesServiceURI = getServiceURI(listFilterDeviceEndpointUri);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().postResource(getDevicesServiceURI,
				identity, entityListType);
	}

	private void getDeviceTemplate(IdentityDTO identity) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DEVICE_TEMPLATE);
		identity.setEntityTemplate(entityTemplateDTO);
	}

	/**
	 * Method to view device details
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> getDeviceDetails(IdentityDTO identity) {
		getDeviceTemplate(identity);
		String findDeviceDetailsServiceURI = getServiceURI(findDeviceEndpointUri);
		return getPlatformClient().postResource(findDeviceDetailsServiceURI,
				identity, EntityDTO.class);
	}

	/**
	 * Method to create genset
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> createDevice(EntityDTO device) {
		FieldMapDTO publishMap = new FieldMapDTO();
		publishMap.setKey(PUBLISH);

		FieldMapDTO publishMapDTO = fetchField(device.getFieldValues(),publishMap);
		if(!publishMapDTO.getValue().equalsIgnoreCase(TRUE)){
			device.getFieldValues().remove(publishMap);
			publishMapDTO.setValue(FALSE);
			device.getFieldValues().add(publishMapDTO);
		}
		//Set the default device template
		EntityTemplateDTO deviceTemplate = new EntityTemplateDTO();
		deviceTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		device.setEntityTemplate(deviceTemplate);
		String createDeviceDetailsServiceURI = getServiceURI(createDeviceEndpointUri);
		return getPlatformClient().postResource(createDeviceDetailsServiceURI,
				device, EntityDTO.class);
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	/**
	 * Method to update device
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<StatusMessageDTO> update(EntityDTO entityDTO) {
		String updateGensetServiceURI = getServiceURI(updateDeviceEndpointUri);
		

		//Set the default device template
		EntityTemplateDTO deviceTemplate = new EntityTemplateDTO();
		deviceTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		entityDTO.setEntityTemplate(deviceTemplate);
		
		//set status
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName("ACTIVE");
		entityDTO.setEntityStatus(statusDTO);
		
		return getPlatformClient().putResource(updateGensetServiceURI,
				entityDTO, StatusMessageDTO.class);
	}
}
