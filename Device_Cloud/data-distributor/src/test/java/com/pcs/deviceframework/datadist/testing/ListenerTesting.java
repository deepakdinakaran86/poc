
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

import javax.jms.Message;
import javax.jms.ObjectMessage;

import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class ListenerTesting extends CoreListenerAdapter{
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = null;
		PublishMessage publishMessage = null;
		try{
			objectMessage = (ObjectMessage) message;
			publishMessage = (PublishMessage) objectMessage.getObject();
			System.out.println("Asset Name : " + publishMessage.getAssetName());
			System.out.println("Temp : " + publishMessage.getTemperature());
		}catch(Exception ex){
			ex.printStackTrace();;
		}
		
	}

}
