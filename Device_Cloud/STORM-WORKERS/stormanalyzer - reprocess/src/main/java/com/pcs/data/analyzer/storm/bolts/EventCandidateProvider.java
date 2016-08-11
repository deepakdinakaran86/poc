package com.pcs.data.analyzer.storm.bolts;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.storm.event.candidate.beans.EventfulParameter;
import com.pcs.data.analyzer.storm.event.candidate.beans.EventfulParameters;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;

public class EventCandidateProvider extends BaseBasicBolt {


    private static final long serialVersionUID = -3147510395523273491L;

	private static Logger LOGGER = LoggerFactory.getLogger(PublishBolt.class);

	private Publisher publisher;
	
	private String publisherConfFile;

	private final PublisherConfig publisherConfig;
	
	private final String eventQueue;
	
	
	 public EventCandidateProvider(String publisherConfFilePath,String eventQueue) {
		 LOGGER.info("Starting publisher bolt {}, {}",publisherConfFilePath);
	    	this.publisherConfFile = publisherConfFilePath;
	    	this.publisherConfig = PublisherConfig.getNewInstance(publisherConfFile);
	    	this.eventQueue = eventQueue;
	    	 LOGGER.info("Publisher ready, waiting for messages");
	    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
    	if (input.getValues().size() >= 2) {
    		processCandidate(String.valueOf(input.getValues().get(1)));
    	}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    
    }
    
    public void processCandidate(String message){
    	try {
    		if(!StringUtils.isBlank(eventQueue)){
        		if(publisher == null){
        			publisher = new Publisher(publisherConfig);
        		}
        		ObjectMapper mapper = new ObjectMapper();
        	    Message dataMessage = mapper.readValue(message, Message.class);
        		List<Point> points = dataMessage.getPoints();
    			EventfulParameters eventParameters = new EventfulParameters(dataMessage.getSourceId().toString(),dataMessage.getTime());
    			for (Point point : points) {
    				eventParameters.addParameter(new EventfulParameter(point.getDisplayName(), point.getData(), null));
    			}
    			publisher.publishToJMSQueue(eventQueue,eventParameters.getJSON());
    			mapper = null;
    			eventParameters = null;
        	}else{
        		LOGGER.error("Unable to publish , empty queue name");
        	}
		} catch (Exception e) {
			LOGGER.error("Exception in event candidate processing",e);
		}
    	
    }
    

}
