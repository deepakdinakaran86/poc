/**
 * 
 */
package com.pcs.device.gateway.jace.message;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pcs.device.gateway.jace.message.beans.PointDetail;

/**
 * @author pcseg171
 *
 */
public class ConfigurationFeed extends JaceMessage {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFeed.class);
	
	private static final String COMMA = ",";
	@JsonProperty("cnt")
	private Integer count;
	@JsonProperty("totSeg")
	private Integer totalSegment;
	@JsonProperty("segCnt")
	private Integer segmentIndex;
	private String schema;
	@JsonProperty("list")
	private List<String> points;
	@JsonProperty("handle")
	private ConfigurationPointHandle handle;
	private List<PointDetail> pointDetails; 
	

	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotalSegment() {
		return totalSegment;
	}
	public void setTotalSegment(Integer totalSegment) {
		this.totalSegment = totalSegment;
	}
	public Integer getSegmentIndex() {
		return segmentIndex;
	}
	public void setSegmentIndex(Integer segmentIndex) {
		this.segmentIndex = segmentIndex;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
		handle = new ConfigurationPointHandle();
		handle.setHandleHeader(this.schema);
	}
	public List<String> getPoints() {
		return points;
	}
	public void setPoints(List<String> points) {
		if(this.handle != null){
			this.handle.setHandles(points);
		}
		this.points = points;
	}

	public ConfigurationPointHandle getHandle() {
		return handle;
	}

	public List<PointDetail> getPointDetails(){
		if(handle == null){
			return null;
		}
		List<String> handleHeaders = handle.getHandleHeaders();
		StringBuffer handleBuffer = new StringBuffer("[");

		for (String point : points) {
			String[] pointSplit = point.split(COMMA);
			int cycleCount = 0;
			handleBuffer.append("{");
			for (String handle : handleHeaders) {
				handleBuffer.append("\"").append(handle).append("\"").append(":")
				.append("\"").append(pointSplit[cycleCount]).append("\"").append(COMMA);
				cycleCount++;
			}
			handleBuffer.deleteCharAt(handleBuffer.length()-1);
			handleBuffer.append("}").append(COMMA);
		}
		handleBuffer.deleteCharAt(handleBuffer.length()-1);
		handleBuffer.append("]");
		String pointDetails = handleBuffer.toString();
		try {
			ObjectMapper mapper = new ObjectMapper();
			this.pointDetails = mapper.readValue(pointDetails, 
								TypeFactory.defaultInstance().constructCollectionType(List.class,PointDetail.class));
		} catch (Exception e) {
			LOGGER.error("Error converting point details",e);
		}
		
		return this.pointDetails;
	}
}
