package com.pcs.data.analyzer.storm.event.candidate.beans;

import java.util.HashSet;
import java.util.Set;

public class EventfulParameters {

	

	private String sourceId;
	private Long time;
	private Set<EventfulParameter> parameters = new HashSet<EventfulParameter>();

	
	public EventfulParameters(){
		
	}
	
	public EventfulParameters(String sourceId,Long time){
		this.sourceId = sourceId;
		this.time = time;
	}
	
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Set<EventfulParameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<EventfulParameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(EventfulParameter gitexParameter){
		this.parameters.add(gitexParameter);
	}
	
	public String getJSON(){
		if(!parameters.isEmpty()){
			Set<String> parametersNotAvailableInMessage = EventfulParameterNames.getParameters();
			for (EventfulParameter parameter : parameters) {
				if(parametersNotAvailableInMessage.contains(parameter.getName())){
					parametersNotAvailableInMessage.remove(parameter.getName());
				}
			
			}
			
			for (String paramName : parametersNotAvailableInMessage) {
				parameters.add(new EventfulParameter(paramName,0,null));
			}
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("{");
			builder.append("\"").append("sourceId").append("\":\"").append(sourceId).append("\",");
			builder.append("\"").append("dateTime").append("\":\"").append(time).append("\",");
			for (EventfulParameter parameter : parameters) {
				builder.append("\"").append(parameter.getName()).append("\":\"").append(parameter.getValue().toString()).append("\",");
			}
			builder.deleteCharAt(builder.length()-1);
			builder.append("}");
			return builder.toString();
		}else{
			return null;
		}
	}
	
	
	public static void main(String[] args){
		Set<String> test = EventfulParameterNames.getParameters();
		EventfulParameters eventfulParameters = new EventfulParameters();
		EventfulParameter eventfulParameter = new EventfulParameter("speed",150,null);
		eventfulParameters.addParameter(eventfulParameter);
		for (EventfulParameter parameter : eventfulParameters.getParameters()) {
			if(test.contains(parameter.getName())){
				test.remove(parameter.getName());
			}
		
		}
		
		for (String paramName : test) {
			eventfulParameters.getParameters().add(new EventfulParameter(paramName,0,null));
		}
	}
}
