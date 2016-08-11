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
package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author PCSEG129(Seena Jyothish) Jul 16, 2015
 */
public class AlarmPointData implements Serializable {
	private static final long serialVersionUID = 5359173028956482148L;
	
	String pointName;
	List<AlarmDateData> dates;
	
	public String getPointName() {
		return pointName;
	}
	
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	public List<AlarmDateData> getDates() {
		return dates;
	}
	
	public void setDates(List<AlarmDateData> alarmDatesData) {
		this.dates = alarmDatesData;
	}
	
	public void addDateData(AlarmDateData alarmDateData){
		dates.add(alarmDateData);
	}
	
}
