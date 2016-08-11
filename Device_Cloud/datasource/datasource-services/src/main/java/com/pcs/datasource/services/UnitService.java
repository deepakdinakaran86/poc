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

import com.pcs.datasource.dto.Unit;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This service class is responsible for defining all the services related to
 * units of physical quantity. This class is responsible for
 * persisting,updating,deleting (soft delete) and fetching units the physical
 * quantity details.
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public interface UnitService {

	/**
	 * method to save unit information of particular physical quantity
	 *
	 * @param unit
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO createUnit(Unit unit);

	/**
	 * method to get unit information of particular physical quantity
	 *
	 * @param phyQuantityName
	 * @return {@link List}
	 */
	public List<Unit> getUnitDeatils(String phyQuantityName);

	/**
	 * method to update unit information of particular physical quantity
	 *
	 * @param unit
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO updateUnit(String unitName, String PhyQuantityName,
	        Unit unit);

	/**
	 * method to delete unit information (soft delete) of particular physical
	 * quantity
	 *
	 * @param uuid
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO deleteUnit(String unitName, String PhyQuantityName);

	/**
	 * Method to get unit informations for particular physical quantity by
	 * physical quantity Id
	 *
	 * @param uuid
	 * @return {@link List}
	 */
	public List<Unit> getUnits(String phyQuantityId);

	/**
	 * Method to check the given unit is an SI unit or not
	 * 
	 * @param unitName - unit name
	 * @return - true if the unit is an SI unit else false
	 */
	public StatusMessageDTO isSIUnit(String unitName);

	/**
	 * Method to get Unit information by name for particular physical qunatity
	 *
	 * @param name
	 * @param phyQuantity
	 * @return {@link Unit}
	 */
	public Unit getUnitDetails(String name, String phyQuantity);
	
	/**
	 * Service method to check a unit exists or not
	 * 
	 * @param unitName - name of the unit
	 * @return - true if unit exists else false
	 */
	public boolean isUnitExist(String unitName);

}
