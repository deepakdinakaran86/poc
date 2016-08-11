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
package com.pcs.alpine.services;

import com.pcs.alpine.services.dto.ConfigurationDTO;
import com.pcs.alpine.services.dto.ConfigurationListDTO;
import com.pcs.alpine.services.dto.DomainListDTO;
import com.pcs.alpine.services.dto.TemplateListDTO;

/**
 * 
 * @author pcseg369
 *
 */
public interface AlarmConfigurationServices {
	
	public DomainListDTO getDomains();
	
	public TemplateListDTO getTemplates(String domainname);
	
	public boolean addConfiguration(String domainname, String templateName, ConfigurationDTO configDTO);
	
	public ConfigurationDTO getConfiguration(String domainname, String configName);
	
	public boolean updateConfiguration(String domainname, String configName, ConfigurationDTO configDTO);

	public ConfigurationListDTO getAllConfigurations(String domainname);

	
}
