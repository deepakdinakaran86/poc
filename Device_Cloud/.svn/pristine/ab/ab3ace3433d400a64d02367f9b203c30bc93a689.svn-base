
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
package com.pcs.alarm.flink.schema;

import org.apache.flink.streaming.util.serialization.SerializationSchema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.cipher.data.message.AlarmMessage;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Aug 1, 2016
 */
public class AlarmMessageSchema implements SerializationSchema<AlarmMessage, byte[]>{

    private static final long serialVersionUID = 6056501267137825222L;
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); 

	@Override
    public byte[] serialize(AlarmMessage alarmMessage) {
		if(alarmMessage!=null){
			try {
	            return OBJECT_MAPPER.writeValueAsBytes(alarmMessage);
            } catch (JsonProcessingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
	    return null;
    }


}
