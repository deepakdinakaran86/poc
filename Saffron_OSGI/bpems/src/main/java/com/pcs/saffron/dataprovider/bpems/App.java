package com.pcs.saffron.dataprovider.bpems;

import com.pcs.jms.jmshelper.consumers.BaseConsumer;
import com.pcs.jms.jmshelper.consumers.common.DefaultConsumerRegistry;
import com.pcs.jms.jmshelper.enums.common.DefaultConsumerModes;
import com.pcs.saffron.dataprovider.bpems.consumer.MessageConsumer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	BaseConsumer  messageConsumer = new DefaultConsumerRegistry().getConsumer(DefaultConsumerModes.ASYNC);
    	messageConsumer.setUrl("");
    	messageConsumer.setQueue("");
    	messageConsumer.setMessageListener(new MessageConsumer());
    	messageConsumer.listen();
    	
    }
}
