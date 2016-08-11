
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
package com.pcs.datasource.repository.neo4j;

import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.pcs.datasource.dto.PointTag;
import com.pcs.datasource.repository.PointTagRepository;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for defining all the repo services related to point custom tag
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 08 Jul 2015
 */
@Repository
public class PointTagRepositoryImpl implements PointTagRepository{
	
	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;
	
	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;
	
	private static final String getAllTags = "MATCH (s:SUBSCRIPTION {sub_id:'{sub_id}'} )-[:hasPointTag]->(p:POINT_TAG) return p;";

    @Override
    public List<PointTag> getAllTags(String subId) {
    	JSONArray tagJsonArray = null;
    	String query = getAllTags.replace("{sub_id}", subId);
    	try {
    		tagJsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
        } catch (JSONException | IOException e) {
        	throw new PersistenceException(
			        "Error in fetching point tags of a subscription ", e);
        }
    	if(tagJsonArray == null){
    		return Collections.emptyList();
    	}
    	List<PointTag> customTags = new ArrayList<PointTag>();
    	for(int i = 0;i < tagJsonArray.length();i++){
    		customTags.add(convertToPointTag(tagJsonArray.getJSONObject(i)));
    	}
	    return customTags;
    }
    
    private PointTag convertToPointTag(JSONObject jsonObject){
    	JSONArray jsonArray = jsonObject.getJSONArray(ROW.getFieldName());
    	Gson lowerUnderScore = objectBuilderUtil.getLowerCaseUnderScoreGson1();
    	PointTag customTag = new PointTag();
    	if(!jsonArray.isNull(0)){
    		customTag = lowerUnderScore.fromJson(jsonArray
			        .getJSONObject(0).toString(), PointTag.class);
    	}
    	return customTag;
    }

}
