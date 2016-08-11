package com.pcs.event.scanner.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventfulParameters {


	private static final Logger LOGGER = LoggerFactory.getLogger(EventfulParameters.class);

	private String sourceId;
	private Long time;
	private List<EventfulParameter> parameters = new ArrayList<EventfulParameter>();


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

	public List<EventfulParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<EventfulParameter> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(EventfulParameter gitexParameter){
		this.parameters.add(gitexParameter);
	}

	public String getJSON(){
		if(!parameters.isEmpty()){
			Set<String> residue = GitexParameterNames.getParameters();
			for (EventfulParameter parameter : parameters) {
				if(residue.contains(parameter.getName())){
					LOGGER.info("Parameter found {}",parameter.getName());
					residue.remove(parameter.getName());
				}
			}
			
			for (String paramName : residue) {
				LOGGER.info("Parameter not found {}",paramName);
				parameters.add(new EventfulParameter(paramName,null,parameters.get(0).getTime()));
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
}
