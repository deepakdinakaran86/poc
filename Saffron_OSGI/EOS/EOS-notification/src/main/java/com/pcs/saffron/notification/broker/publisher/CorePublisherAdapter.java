
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
package com.pcs.saffron.notification.broker.publisher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 23 2015
 */
public class CorePublisherAdapter implements CorePublisher {

    private static final long serialVersionUID = 3663757646983433336L;

	/* (non-Javadoc)
	 * @see com.pcs.datadist.publisher.CorePublisher#setUrl(java.lang.String)
	 */
   
    public void setUrl(String url) {
    }

	/* (non-Javadoc)
	 * @see com.pcs.datadist.publisher.CorePublisher#getUrl()
	 */
   
    public String getUrl() {
	    return null;
    }

	/* (non-Javadoc)
	 * @see com.pcs.datadist.publisher.CorePublisher#publish(java.lang.Object)
	 */
   
    public void publish(Serializable serializable) {
    }

	/* (non-Javadoc)
	 * @see com.pcs.datadist.publisher.CorePublisher#setProperties(java.util.Properties)
	 */
   
    public void setProperties(Properties properties) {
    }

	/* (non-Javadoc)
	 * @see com.pcs.datadist.publisher.CorePublisher#getProperties()
	 */
   
    public Properties getProperties() {
	    return null;
    }

	/* (non-Javadoc)
	 * @see com.pcs.deviceframework.datadist.publisher.CorePublisher#publish(java.lang.String, java.io.Serializable)
	 */
   
    public void publish(String topicName, Serializable serializable)
            throws RemoteException {
    }

	/* (non-Javadoc)
	 * @see com.pcs.deviceframework.datadist.publisher.CorePublisher#publish(java.util.List)
	 */
   
    public void publish(List<Serializable> messageList) throws RemoteException {
    }

	/* (non-Javadoc)
	 * @see com.pcs.deviceframework.datadist.publisher.CorePublisher#publishToQueue(java.lang.String, java.io.Serializable)
	 */
   
    public void publishToQueue(String queueName, Serializable serializable)
            throws RemoteException {
    }

	/* (non-Javadoc)
	 * @see com.pcs.deviceframework.datadist.publisher.CorePublisher#publishToTopic(java.lang.String, java.io.Serializable)
	 */
   
    public void publishToTopic(String topicName, Serializable serializable)
            throws RemoteException {
    }

	/* (non-Javadoc)
	 * @see com.pcs.deviceframework.datadist.publisher.CorePublisher#publishAvro(java.io.Serializable)
	 */
   
    public void publishAvro(Serializable serializable, String topicName, String schema) throws RemoteException {
	    // TODO Auto-generated method stub
	    
    }

	
}
