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

import org.json.JSONArray;

import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.DeviceCommand;
import com.pcs.datasource.dto.writeback.WriteBackCommand;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.dto.writeback.WriteBackResponse;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for services related to write-back logs
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
public interface WriteBackLogService {

	/**
	 * Service Method for fetching all the writeBack Logs of a subscription
	 * (fetch data with relationship)
	 * 
	 * @param subId
	 * @param startTime
	 * @param endTime
	 * @return {@link JSONArray}
	 */
	public JSONArray getAllLogsWithRelation(String subId, Long startTime,
	        Long endTime);

	/**
	 * Service Method for fetching all the writeBack Logs of the specified
	 * devices of the logged in subscription
	 * 
	 * @param sourceIds
	 * @param sub
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WriteBackLog> getLogsOfSubscription(List<String> sourceIds,
	        Subscription sub, Long startTime, Long endTime);

	/**
	 * Service Method for inserting write-back log
	 * 
	 * @param writeBackLog
	 * @return
	 */
	public WriteBackResponse insertLog(WriteBackLog writeBackLog);

	/**
	 * Service Method for inserting write back batch
	 * 
	 * @param sourceId
	 * @return
	 */
	public BatchCommand insertBatch(String sourceId);

	/**
	 * Service Method for delete Batch ,Mainly used for role back
	 * 
	 * @param batchCommand
	 */
	public void deleteBatch(BatchCommand batchCommand);

	/**
	 * Service Method for updating the status of the write back command
	 * 
	 * @param command
	 */
	public StatusMessageDTO updateWriteBack(String sourceId,
	        WriteBackCommand writeBackCommand);

	/**
	 * Service Method for fetching all commands with status 'QUEUED'
	 * 
	 * @param sourceId
	 * @return
	 */
	public List<DeviceCommand> getCurrentExecuting(String sourceId);

}
