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
package com.pcs.datasource.services;

import java.util.List;

import com.pcs.datasource.dto.PhyQuantityResponse;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This service interface is responsible for defining all the services related
 * to physical quantity in which stores device data and also manages cache for
 * the same. This class is responsible for retrieving columnfamily details of
 * particular physical quantity from the device data. If this mapping
 * information already present cache then retrieve from cache otherwise access
 * DB for the same and also support basic CRUD operation on physical quantity
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public interface PhyQuantityService {

	/**
	 * Method to get details of all active physical quantities by datatype
	 * @return {@link PhysicalQuantity}
	 */
	public List<PhysicalQuantity> getAllPhyQunatity(String dataType);
	
	/**
	 * Service method to get mapping of physical quantity and columnfamily in DB
	 *
	 * @param phyQuantityName
	 * @return {@link String}
	 *
	 */
	public PhysicalQuantity getPhyQuantityByName(String phyQuantityName);

	/**
	 * Method to persist physical quantities, units are not manadatory
	 *
	 * @param PhysicalQuantity
	 * @return {@link StatusMessageDTO}
	 */
	public PhyQuantityResponse createPhyQuantity(PhysicalQuantity phyQuantity);

	/**
	 * Method to get ACTIVE physical quantity details
	 *
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 */
	public PhysicalQuantity getActivePhyQuantity(String phyQuantityName);

	/**
	 * Method to get physical quantity details
	 *
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 */
	public PhysicalQuantity getPhyQuantity(String phyQuantityName);

	/**
	 * Method to update physical quantities, units are not mandatory
	 *
	 * @param PhysicalQuantity
	 * @return {@link PhysicalQuantity}
	 */
	public StatusMessageDTO updatePhyQuantity(String phyQuantityName,
	        PhysicalQuantity phyQuantity);

	/**
	 * Method to delete (soft delete) physical quantities.
	 *
	 * @param uuid
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO deletePhyQuantity(String uuid);
	
	/**
	 * Method to check a physical quantity exists or not
	 * 
	 * @param physicalQuantity
	 * @return - true if physical quantity exists else false
	 */
	public boolean isPhysicalQuantityExist(String physicalQuantity);
	
	/**
	 *  Method to check a physical quantity is valid for a unit
	 *  
	 * @param physicalQuantity
	 * @param unit
	 * @return - true if physical quantity is exist for the unit else false
	 */
	public boolean isPhysicalQuantityValid(String physicalQuantity,String unit);
	
	/**
	 * Method to check a physical quantity is valid for a unit and a data type
	 * 
	 * @param physicalQuantity - physical quantity name
	 * @param unit - unit name
	 * @param dataType - data type of the physical quantity
	 * @return - true if physical quantity is exist for the unit and data type else false
	 */
	public boolean isPhysicalQuantityValid(String physicalQuantity,String unit,DataTypes dataType);
	
	/**
	 * Method to check a physical quantity is valid by checking the data type heirarchy
	 * @param physicalQuantity
	 * @param unit
	 * @param dataType
	 * @return
	 */
	public boolean isPhysicalQuantityValidByDataType(String physicalQuantity,String unit,DataTypes dataType);

}
