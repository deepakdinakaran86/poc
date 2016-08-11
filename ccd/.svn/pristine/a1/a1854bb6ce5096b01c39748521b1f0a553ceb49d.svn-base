
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
package com.pcs.util.faultmonitor.testing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultData;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultDataWrapper;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 20, 2016
 */
public class ModelBuilding {
public static void main(String[] args) throws JsonProcessingException {
	
	DateFormat df = new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss");
	
	try {
	    Date dt = df.parse("Oct/15/2015 18:35:12");
	    System.out.println(dt.getTime());
    } catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	
	FaultData f = new FaultData();
	
	FaultDataWrapper w = new FaultDataWrapper();
	w.setNotificationVersion("1.0");
	w.setMessageType("FC");
	
	f.setFaultDataInfo(w);
	
	ObjectMapper mapper = new ObjectMapper();
	System.out.println(mapper.writeValueAsString(f));
}


}
