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

import com.pcs.datasource.dto.writeback.Command;
import com.pcs.datasource.dto.writeback.WriteBackDeviceResponse;

/**
 * Interface for Write Back Commands of G2021
 *
 * @author pcseg199
 * @date Apr 21, 2015
 * @since galaxy-1.0.0
 */
public interface G2021CommandService {

	/**
	 * Method for validating and publishing to a message broker through data
	 * distributor
	 *
	 * @param commandDTO
	 * @return
	 */
	public WriteBackDeviceResponse processCommands(String sourceId,
	        List<Command> commands);
}
