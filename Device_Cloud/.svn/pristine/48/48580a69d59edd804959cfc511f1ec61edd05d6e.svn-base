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
import com.pcs.datasource.dto.Unit;


/**
 * This repository interface is responsible for defining all the services
 * related to units describes physical quantities. this class is responsible for
 * basic CRUD operation on units
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public interface UnitRepo {

	/**
	 * method to save unit information of particular physical quantity
	 *
	 * @param unit
	 */
	public void persistUnit(Unit unit);
	
	/**
	 * Method to persist unit info along with update of status in physical
	 * quantity if any
	 *
	 * @param unit
	 * @param physicalQuantity
	 */
	public void persistUnit(Unit unit, PhysicalQuantity physicalQuantity);

	/**
	 * Method to get Unit information by name for particular physical qunatity
	 *
	 * @param name
	 * @param phyQuantity
	 * @return {@link Unit}
	 */
	public Unit getUnitDetails(String name, String phyQuantity);

	/**
	 * Method to check given unit is an SI unit or not
	 * 
	 * @param unitName - unit name
	 * @return - true if unit is an SI unit else false
	 */
	public boolean isSIUnit(String unitName);

	/**
	 * Method to get unit informations for particular physical quantity by
	 * physical quantity Id
	 *
	 * @param uuid
	 * @return {@link List}
	 */
	public List<Unit> getUnits(String phyQuantityId);

	/**
	 * Method to get si unit information by PQ name
	 *
	 * @param name
	 * @return {@link Unit}
	 */
	public Unit getSIUnitByPQName(String name);

	/**
	 * Method to get all active unit information for particular physical
	 * quantity
	 *
	 * @param phyQuantityName
	 * @param status
	 * @return {@link List}
	 */
	public List<Unit> getActiveUnits(String phyQuantityName, Integer status);

	/**
	 * Method to update unit info along with update of status in physical
	 * quantity if any
	 *
	 * @param unit
	 * @param physicalQuantity
	 */
	public void updateUnit(Unit unit, PhysicalQuantity physicalQuantity);

	/**
	 * Method to update unit info
	 * @param uniqueId TODO
	 * @param unit
	 */
	public void updateUnit(String uniqueId, Unit unit);

	/**
	 * Method to get unit info by uuid
	 *
	 * @param id
	 * @return {@link Unit}
	 */
	public Unit getUnitDetails(String name);

	/**
	 * method to delete unit information (soft delete) of particular physical
	 * quantity
	 *
	 * @param unit
	 */
	public void deleteUnit(Unit unit);

	/**
	 * Method to dele unit info along with update of status in physical quantity
	 * if any
	 *
	 * @param uid
	 * @param physicalQuantity
	 */
	public void deleteUnit(String id, PhysicalQuantity physicalQuantity);
	
	/**
	 * Service method to check a unit exists or not
	 * 
	 * @param unitName - name of the unit
	 * @return - true if unit exists else false
	 */
	public boolean isUnitExist(String unitName);

}
