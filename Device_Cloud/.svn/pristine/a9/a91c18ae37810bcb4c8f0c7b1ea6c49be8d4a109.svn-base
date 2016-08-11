
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
package com.pcs.datasource.repository;

import java.util.List;
import java.util.SortedSet;

import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.model.DatasourceRegistration;

/**
 * This class is responsible for 'C R U D' operations on the datasource
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date 26 May 2015
 */
public interface DatasourceRepository {
	
	/**
	 * Method to register datasource
	 * 
	 * @param datasourceRegistration
	 */
	public void saveDatasource(DatasourceRegistration datasourceRegistration,List<ParameterDTO> datasourceParameters);
	
	/**
	 * Method to get all parameters of a datasource
	 * 
	 * @param datasourceName
	 * @return
	 */
	public List<ParameterDTO> getParameters(String datasourceName);
	
	/**
	 * Method to update parameters of a datasource
	 * 
	 * @param datasourceDTO
	 * @return
	 */
	public void updateDatasource(DatasourceDTO datasourceDTO);
	
	/**
	 * Method to get all datasources
	 * @return
	 */
	public List<DatasourceDTO> getAllDatasource();
	
	/**
	 * Method to check whether a datasource name exists or not
	 * 
	 * @param datasourceName
	 * @return
	 */
	public boolean isDatasourceExist(String datasourceName);
	
	/**
	 * Method to deregister datasource
	 * 
	 * @param datasourceName
	 * @return
	 */
	public void deregisterDatasource(String datasourceName);
	
	/**
	 * Method to create a context connected to the given datasources
	 * 
	 * @param datasourceNames
	 * @return
	 */
	public String createContext(SortedSet<String> datasourceNames);
	
	/**
	 * Method to get all the contexts a datasource subscribed
	 * @param datasourceName
	 * @return
	 */
	public List<String> getSubscribedContexts(String datasourceName);

}
