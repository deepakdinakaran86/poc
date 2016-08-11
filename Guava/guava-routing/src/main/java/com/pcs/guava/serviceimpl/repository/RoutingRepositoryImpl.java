package com.pcs.guava.serviceimpl.repository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.guava.commons.util.ObjectBuilderUtil;
import com.pcs.guava.dto.routing.PoiDeSerializeDTO;
import com.pcs.guava.dto.routing.PoiIdentifiersDTO;
import com.pcs.guava.dto.routing.PoiListDTO;
import com.pcs.guava.dto.routing.PoiResult;
import com.pcs.guava.dto.routing.RouteEntityDTO;
import com.pcs.guava.dto.scheduling.ScheduleDeSerializeDTO;
import com.pcs.guava.dto.scheduling.ScheduleResult;
import com.pcs.guava.service.repository.RoutingRepository;
import com.pcs.guava.serviceImpl.repository.utils.Neo4jExecuter;

import static com.pcs.guava.services.enums.HMDataFields.ROW;


@Repository("RoutingNeo")
public class RoutingRepositoryImpl implements RoutingRepository{
	
	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;
	  
	
	private static final String INDENTIFIER = "{identifier}";
	private static final String POI_TEMPLATE = "{poi_template}";
	private static final String POI_TYPE = "{poi_type}";
	private static final String SCHEDULE_TEMPLATE="{schedule_dest}";
	
	private static final String FIND_ROUTE_CHILDREN ="MATCH (n:`MARKER` {identifierValue:'{identifier}'})-[r:Child]->(m1:MARKER {templateName:'{poi_template}'})-[r1:Child]->(m2:MARKER {templateName:'{poi_type}'}) RETURN {tempName:n.templateName,result:collect({poiData:m1.dataprovider,poiTypeData:m2.dataprovider})}";
	
	
	private static final String FIND_SCHEDULE_CHILDREN ="MATCH (n:`MARKER` {identifierValue:'{identifier}'})-[r:Child]->(m1:MARKER {templateName:'{schedule_dest}'})RETURN {tempName:n.templateName,result:collect({scheduleDest:m1.dataprovider})}";
	
	
	private static final String FIND_POI_IDENTIFIERS ="MATCH (n:`MARKER` {identifierValue:'{identifier}'})-[r:Child]->(m1:MARKER {templateName:'{poi_template}'})-[r1:Child]->(m2:MARKER {templateName:'{poi_type}'}) RETURN {poiData:collect(m1.identifierValue)}";

	@Override
	public PoiResult findRouteChild(String identifier, String poiTemplate,
			String poiType) {
		
		StringBuffer parentQuery = new StringBuffer(FIND_ROUTE_CHILDREN
				.replace(INDENTIFIER, identifier)
				.replace(POI_TEMPLATE, poiTemplate)
				.replace(POI_TYPE, poiType));
		JSONArray jsonArray = null;
		try {
			
			jsonArray=Neo4jExecuter.exexcuteQuery(neo4jURI, parentQuery.toString(), null,
			        ROW.toString());
		} catch (IOException e) {
			throw new RuntimeException("Error in HierarchyRepository" + e);
		}
		
		PoiResult poiTemplates = null ;
		if (jsonArray != null) {
			
			JSONObject resultJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONObject(0);
			
			poiTemplates= deSerializeConfigPoint(resultJson);
		}
		return poiTemplates;
	}
		
		private PoiResult deSerializeConfigPoint(JSONObject resultJson) {
			
			
			PoiResult poiTemplate;

			GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
			builder.registerTypeAdapter(PoiDeSerializeDTO.class, new PointExtTrnaslator());

			Gson gson = builder.create();
			poiTemplate = gson.fromJson(resultJson.toString(),
					PoiResult.class);
			return poiTemplate;
		}

		@Override
		public PoiIdentifiersDTO getPoiIdentifiers(String identifier,
				String poiTemplate, String poiType) {
			

			StringBuffer parentQuery = new StringBuffer(FIND_POI_IDENTIFIERS
					.replace(INDENTIFIER, identifier)
					.replace(POI_TEMPLATE, poiTemplate)
					.replace(POI_TYPE, poiType));
			JSONArray jsonArray = null;
			try {
				
				jsonArray=Neo4jExecuter.exexcuteQuery(neo4jURI, parentQuery.toString(), null,
				        ROW.toString());
			} catch (IOException e) {
				throw new RuntimeException("Error in HierarchyRepository" + e);
			}
			
			JSONObject resultJson = jsonArray.getJSONObject(0)
					.getJSONArray(ROW.getFieldName()).getJSONObject(0);
			
			
			Gson gson = new Gson();
			PoiIdentifiersDTO identifiers = gson.fromJson(resultJson.toString(), PoiIdentifiersDTO.class);			
			
			return identifiers;
		}

		@Override
		public ScheduleResult findScheduleChild(String identifier,
				String scheduleDestTemplate) {
			StringBuffer parentQuery = new StringBuffer(FIND_SCHEDULE_CHILDREN
					.replace(INDENTIFIER, identifier)
					.replace(SCHEDULE_TEMPLATE, scheduleDestTemplate));
			JSONArray jsonArray = null;
			try {
				
				jsonArray=Neo4jExecuter.exexcuteQuery(neo4jURI, parentQuery.toString(), null,
				        ROW.toString());
			} catch (IOException e) {
				throw new RuntimeException("Error in HierarchyRepository" + e);
			}
			
			ScheduleResult schedTemplates = null ;
			if (jsonArray != null) {
				
				JSONObject resultJson = jsonArray.getJSONObject(0)
						.getJSONArray(ROW.getFieldName()).getJSONObject(0);
				
				schedTemplates= deSerializeConfigSchedule(resultJson);
			}
			return schedTemplates;
		}
		
		private ScheduleResult deSerializeConfigSchedule(JSONObject resultJson) {
			
			
			ScheduleResult schedTemplate;

			GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
			builder.registerTypeAdapter(ScheduleDeSerializeDTO.class, new ScheduleExtTrnaslator());

			Gson gson = builder.create();
			schedTemplate = gson.fromJson(resultJson.toString(),
					ScheduleResult.class);
			return schedTemplate;
		}

}
