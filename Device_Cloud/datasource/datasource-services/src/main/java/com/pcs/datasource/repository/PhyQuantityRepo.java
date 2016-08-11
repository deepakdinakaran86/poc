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
package com.pcs.datasource.repository;

import java.util.List;

import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.devicecloud.enums.Status;

/**
 * This repository interface is responsible for defining all the services
 * related to datastore in which stores device data and also manages cache for
 * the same. This class is responsible for retrieving columnfamily details of
 * particular physical quantity from the device data. If this mapping
 * information already present cache then retrieve from cache otherwise access
 * DB for the same and also support basic CRUD operation on physical quantity
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public interface PhyQuantityRepo {

	/**
	 * Method to check a physical quantity already exists or not
	 * 
	 * @param phyQuantityName - name of the physical quantity
	 * @return - true if the physical quantity exists else false
	 */
	public boolean isPhyQuantityExist(String phyQuantityName);
	
	/**
	 *  Method to check a physical quantity is valid for a unit
	 *  
	 * @param physicalQuantity - name of the physical quantity
	 * @param unit - name of the unit
	 * @return - true if physical quantity is exist for the unit else false
	 */
	public boolean isPhysicalQuantityValid(String physicalQuantity,String unit);
	
	/**
	 * Method to check a physical quantity is valid for a unit and data type
	 * 
	 * @param physicalQuantity - name of the physical quantity
	 * @param unit - name of the unit
	 * @param dataType - data type of the physical quantity
	 * @return - true if physical quantity is exist for the unit else false
	 */
	public boolean isPhysicalQuantityValid(String physicalQuantity,String unit,DataTypes dataType);
	
	/**
	 * Method to get details of all active physical quantities
	 * optional filter by datatype
	 * 
	 * @return {@link PhysicalQuantity}
	 */
	public List<PhysicalQuantity> getAllPhysicalQuantity(String dataType);
	
	/**
	 * Method to get mapping of datastore and physical quantity
	 *
	 * @param phyQuantityName
	 * @param active
	 * @return {@link PhysicalQuantity}
	 *
	 */
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName,
	        Status status);

	/**
	 * Method to persist physical quantities, units are not manadatory
	 *
	 * @param phyQuantityDTO
	 */
	public void savePhyQuantity(PhysicalQuantity phyQuantity);

	/**
	 * Method to get ACTIVE physical quantity details by name
	 *
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 */
	public PhysicalQuantity getPhyQuantityDetails(String phyQuantityName);

	/**
	 * Method to get ACTIVE physical quantity details by uuid
	 *
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 */
	//public PhysicalQuantity getPhyQuantityDetails(UUID uid);

	/**
	 * Method to update physical quantity
	 * 
	 * @param uniqueId - physical quantity
	 * @param newPhyQty TODO
	 */
	public void updatePhyQuantity(String uniqueId, PhysicalQuantity newPhyQty);

	/**
	 * @param phyQuantity
	 */
	public void deletePhyQuantity(PhysicalQuantity phyQuantity);
	
	
	/**
	 * Method to check a physical quantity is valid by checking the data type heirarchy
	 * @param physicalQuantity
	 * @param unit
	 * @param dataType
	 * @return
	 */
	public boolean isPhysicalQuantityValidByDataType(String physicalQuantity,String unit,DataTypes dataType);

}
