package com.pcs.device.gateway.jace.datapoll.api;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HistoryData {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryData.class);

	@JsonProperty("csid")
	private String unitId;
	@JsonProperty("cnt")
	private String count;
	private String schema;
	@JsonProperty("list")
	private List<String> data;
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		HistoryData readValue = new ObjectMapper().readValue("{\"csid\":8,\"cnt\":4,\"schema\":\"hid,value,status\",\"list\":[\"ba,1,1\",\"be,50.44,1\",\"c0,5,1\",\"c2,50.44,1\"]}", HistoryData.class);
		System.err.println(readValue.getData());
	}
}
