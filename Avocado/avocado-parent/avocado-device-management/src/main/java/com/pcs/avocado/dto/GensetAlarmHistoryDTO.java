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
package com.pcs.avocado.dto;

import java.io.Serializable;
import java.util.List;

import com.pcs.avocado.commons.dto.FieldMapDTO;

/**
 * 
 * @author PCSEG362
 * @date Feb 2016
 * @since Alpine-1.0.0
 */
public class GensetAlarmHistoryDTO implements Serializable {

	private static final long serialVersionUID = 8485163637348748650L;


	private String datasourceName;
	
	private String equipName;
	
	private FieldMapDTO equipIdentifier;
	
	private String equipTemplate;

	private String sourceId;
	
	private List<AlarmMessage> alarmMessages;

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public FieldMapDTO getEquipIdentifier() {
		return equipIdentifier;
	}

	public void setEquipIdentifier(FieldMapDTO equipIdentifier) {
		this.equipIdentifier = equipIdentifier;
	}

	public String getEquipTemplate() {
		return equipTemplate;
	}

	public void setEquipTemplate(String equipTemplate) {
		this.equipTemplate = equipTemplate;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public List<AlarmMessage> getAlarmMessages() {
		return alarmMessages;
	}

	public void setAlarmMessages(List<AlarmMessage> alarmMessages) {
		this.alarmMessages = alarmMessages;
	}
	
	
}
