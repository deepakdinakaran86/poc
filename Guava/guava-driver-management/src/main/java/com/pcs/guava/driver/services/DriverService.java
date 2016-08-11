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

package com.pcs.guava.driver.services;

import java.util.List;

import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.driver.dto.Driver;

/**
 * Driver Resource Methods
 * 
 * @author Twinkle (PCSEG297)
 * @date April 2016
 */

public interface DriverService {

	/**
	 * This service will be used to create a driver
	 * 
	 * @param driver
	 * @return {@link Driver}
	 */
	public Driver createDriver(Driver driver);

	/**
	 * This service will be used to fetch a driver
	 * 
	 * @param driverIdentity
	 * @return {@link Driver}
	 */
	public Driver getDriver(IdentityDTO driverIdentity);

	/**
	 * This service will be used to fetch all drivers
	 * 
	 * @param domainName
	 */
	public List<Driver> findAll(String domainName);

	/**
	 * This service will be used to update details of driver
	 * 
	 * @param driver
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO updateDriver(Driver driver);
}
