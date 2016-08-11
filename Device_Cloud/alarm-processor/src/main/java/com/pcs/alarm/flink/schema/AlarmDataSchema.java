
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

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.analytics.flinktests.beans.DeviceSnapshot;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Jul 27, 2016
 */
public class AlarmDataSchema implements DeserializationSchema<DeviceSnapshot>{

    private static final long serialVersionUID = -8926491336900655060L;

	@Override
    public TypeInformation<DeviceSnapshot> getProducedType() {
		return TypeExtractor.getForClass(DeviceSnapshot.class);
    }

	@Override
    public DeviceSnapshot deserialize(byte[] message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(message, DeviceSnapshot.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

	@Override
    public boolean isEndOfStream(DeviceSnapshot arg0) {
	    return false;
    }

}
