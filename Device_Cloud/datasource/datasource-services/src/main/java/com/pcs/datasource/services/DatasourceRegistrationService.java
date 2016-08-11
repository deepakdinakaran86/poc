
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific
* Controls Software Services.
*
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.datasource.services;

import java.util.List;
import java.util.SortedSet;

import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.DatasourceStatusDTO;
import com.pcs.datasource.dto.MessageDTO;
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.SubscriptionContext;
import com.pcs.datasource.dto.SubscriptionDTO;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for registering datasources
 *
 * @author pcseg129 (Seena Jyothish)
 * @date 22 Jan 2015
 */
public interface DatasourceRegistrationService {

	public DatasourceStatusDTO registerDatasource(DatasourceDTO datasourceDTO);

	public StatusMessageDTO updateDatasource(DatasourceDTO datasourceDTO);

	public List<DatasourceDTO> getAllDatasource();

	public List<ParameterDTO> getDatasourceParameters(String datasourceName);

	public StatusMessageDTO publish(String destination,MessageDTO messageDTO);

	public SubscriptionDTO subscribe(SortedSet<String> datasourceNames, String wsClient);

	public SubscriptionContext getSubscriptionContexts(String datasourceName);

	public StatusMessageDTO deRegister(String registrationId);
	
	public boolean isDatasourceExist(String datasourceName);

}
