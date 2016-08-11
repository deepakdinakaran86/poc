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

import org.json.JSONArray;

import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.DeviceCommand;
import com.pcs.datasource.dto.writeback.WriteBackCommand;
import com.pcs.datasource.dto.writeback.WriteBackLog;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
public interface WriteBackRepository {

	/**
	 * Repository Method for fetching all the writeBack Logs of a
	 * subscription(fetch data specific to Grid)
	 * 
	 * @param subId
	 * @param startTime
	 * @param endTime
	 * @return {@link List<WriteBackLog>}
	 */
	public List<WriteBackLog> getAllLogs(String subId, Long startTime,
	        Long endTime);

	/**
	 * Repository Method for fetching all the writeBack Logs of a subscription
	 * (fetch data with relationship)
	 * 
	 * @param subId
	 * @param startTime
	 * @param endTime
	 * @return {@link JSONArray}
	 */
	@Deprecated
	public JSONArray getAllLogsWithRelation(String subId, Long startTime,
	        Long endTime);

	/**
	 * Repository Method for fetching all the writeBack Logs of the specified
	 * devices of the logged in subscription
	 * 
	 * @param sourceIds
	 * @param sub
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WriteBackLog> getLogsOfSelectedDevices(List<String> sourceIds,
	        String subId, Long startTime, Long endTime);

	/**
	 * Repository Method for inserting writeBackLog
	 * 
	 * @param writeBackLog
	 */
	public DeviceCommand insertWriteBackLog(WriteBackLog writeBackLog);

	/**
	 * Repository Method for updating status of the writeBack
	 * 
	 * @param command
	 */
	public void updateWriteBack(String sourceId,
	        WriteBackCommand writeBackCommand);

	/**
	 * Repository Method for inserting batch
	 * 
	 * @param writeBackLog
	 */
	public BatchCommand insertBatch(String sourceId);

	/**
	 * Repository Method for deleting a batch
	 * 
	 * @param batchCommand
	 */
	public void deleteBatch(BatchCommand batchCommand);

	/**
	 * Repository Method for fetching all commands with status 'QUEUED'
	 * 
	 * @param sourceId
	 * @return
	 */
	public List<DeviceCommand> getCurrentExecuting(String sourceId);
}
