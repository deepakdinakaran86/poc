/**
 * 
 */
package com.pcs.device.gateway.G2021.command;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.device.gateway.G2021.message.type.MessageType;

/**
 * @author pcseg171
 * 
 */
public class G2021Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5757537685498668255L;

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021Command.class);

	private Object command;
	private String sourceId;
	private Integer unitId;
	private Integer sessionId;
	private Short requestId;
	private Short pointId;
	private Object data;
	private String dataType;
	private Short leaseTime;
	private String commandCode;
	private Short priority;

	public Object getCommand() {
		return command;
	}

	public void setCommand(Object command) {
		if (command!= null && command instanceof String) {
			this.command = command.toString().toUpperCase();
		}else{
			this.command = command;
		}
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public Short getRequestId() {
		return requestId;
	}

	public void setRequestId(Short requestId) {
		this.requestId = requestId;
	}

	public Short getPointId() {
		return pointId;
	}

	public void setPointId(Short pointId) {
		this.pointId = pointId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public G2021DataTypes getDataType() {
		return G2021DataTypes.valueOf(dataType);
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Short getLeaseTime() {
		return leaseTime;
	}

	public void setLeaseTime(Short leaseTime) {
		this.leaseTime = leaseTime;
	}

	public Byte getCommandCode() {
		Byte command = null;
		if(OperationCommandType.valueOfIndicator(commandCode.toUpperCase()) != null){
			command = OperationCommandType.valueOfIndicator(commandCode.toUpperCase()).getCommand();
		}else if(OperationCommandType.valueOf(commandCode.toUpperCase()) != null){
			command = OperationCommandType.valueOf(commandCode.toUpperCase()).getCommand();
		}
		return command;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode.toUpperCase();
	}

	public Short getPriority() {
		return priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}


	public byte[] getServerMessage() throws Exception{
		MessageType messageType = null;

		if (command instanceof Integer) {
			messageType = MessageType.valueOfType((Integer) command);
		}else{
			messageType = MessageType.valueOfIndicator(command.toString().toUpperCase());
			if(messageType == null){
				messageType = MessageType.valueOf(command.toString().toUpperCase());
			}
		}
		
		if(messageType != null){
			switch (messageType) {

			case POINTDISCOVERYREQUEST:
				return PointDiscoveryRequest.getServerMessage(this) ;

			case SYSTEM_COMMAND:
				return OperationCommand.getServerMessage(this);

			case POINTINSTANTSNAPSHOTREQUEST:
				return PointSnapshotRequest.getServerMessage(this);

			case WRITE_COMMAND:
				return PointWrite.getServerMessage(this);

			default:
				LOGGER.error("Invalid command encountered !! "+command);
				break;
			}
		}
		return null;
	}
}
