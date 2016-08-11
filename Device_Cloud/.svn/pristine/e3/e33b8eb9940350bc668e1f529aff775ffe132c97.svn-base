package com.pcs.saffron.g2021.SimulatorEngine.DS.eventQueue;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueueDispatcher{
	
	public static EventQueueDispatcher eventQDispatcher = null;
	
	private Queue<EventQueueData> data = new LinkedList<EventQueueData>();

	
	private EventQueueDispatcher(){
		
	}
	
	public static EventQueueDispatcher getInstance(){
		if(eventQDispatcher == null){			
			eventQDispatcher = new EventQueueDispatcher();
		}
		return eventQDispatcher;
	}

	public Queue<EventQueueData> getData() {
		return data;
	}

	public void setData(Queue<EventQueueData> dataPoints) {
		this.data = dataPoints;
	}
	
	public void pushtoQueue(EventQueueData inputData){
		data.add(inputData);
	}
	
	public void removeOnAck(){
		data.poll();
	}
	
		
}
