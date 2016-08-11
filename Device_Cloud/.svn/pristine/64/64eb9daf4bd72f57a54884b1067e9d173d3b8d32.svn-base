
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.deviceframework.datadist.testing;

import java.io.Serializable;
import java.util.Calendar;

import com.google.gson.Gson;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129
 */
/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129
 */
public class EventMessage implements Serializable{
	
	/**
	 *
	 */
    private static final long serialVersionUID = -4788576853831973691L;
	String eventId;
	String eventSource;
	String eventValue;
	
	public EventMessage(){
		
	}
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventSource() {
		return eventSource;
	}
	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
	public String getEventValue() {
		return eventValue;
	}
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}
	
	@Override
	public String toString(){
		Gson gson = new Gson();
		String jsonString = gson.toJson(this);
		return jsonString;
	}
	
public static void main(String[] args) {
	EventMessage eventMessage = new EventMessage();
	eventMessage.setEventId("154");
	eventMessage.setEventSource("Time");
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DATE, -3);
	eventMessage.setEventValue(calendar.getTime().toString());
	System.out.println(eventMessage.toString());

}
}
