
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

import java.util.ArrayList;
import java.util.List;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * This class is responsible for identifying the partion
 * 
 * @author pcseg129(Seena Jyothish)
 */
public class MessagePartitioner implements Partitioner {
	
	private List<Integer> configuredPartitions = new ArrayList<Integer>();
	
	public MessagePartitioner(VerifiableProperties verifiableProperties){
		
	}

	public int partition(Object key,int a_numPartitions){
		Integer part = Integer.parseInt(key.toString());
		return part;
	}
	
	public MessagePartitioner(){
		
	}
	
	public static List<Integer> getPartitions(String topicName){
		
		return null;		
	}
	
	
}
