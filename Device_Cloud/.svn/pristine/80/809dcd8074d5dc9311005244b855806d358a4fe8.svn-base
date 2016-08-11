/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.repository.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * Class for Constants Used in Cyphers
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
public class CypherConstants {

	public static final String NODE_LABEL = "{nodeLabel}";

	public static final String SUB_ID = "{sub_id}";

	public static final String SOURCE_ID = "{source_id}";

	public static final String SOURCE_IDs = "{source_ids}";

	public static final String NAME = "{name}";

	public static final String TAGS = "{tags}";

	public static final String NW_PROTOCOL = "{nw_protocol}";

	public static final String PROPERTY_VALUE = "{propertyValue}";

	public static final String PROPERTY_NAME = "{propertyName}";

	public static final String BATCH_ID = "{batch_id}";

	public static final String START_TIME = "{start_time}";

	public static final String END_TIME = "{end_time}";

	public static final String RELATIONSHIP = "{relationship}";

	public static final String SECOND_PROPERTY_VALUE = "{secondPropertyValue}";

	public static final String SECOND_PROPERTY_NAME = "{secondPropertyName}";

	public static final String SECOND_LABEL = "{secondLabel}";

	public static final String FIRST_PROPERTY_VALUE = "{firstPropertyValue}";

	public static final String FIRST_PROPERTY_NAME = "{firstPropertyName}";

	public static final String FIRST_LABEL = "{firstLabel}";

	public static final String IS_OWNING = "IsOwning";

	public static final String TYPE = "{type}";

	public static final String PROTOCOL = "{protocol}";

	public static final String COMMAND = "{command}";

	public static final String ADDITIONAL_FILTERS = "{additionalFilters}";

	public static final String POINT_ID = "{point_id}";

	public static final String CUSTOM_SPECS = "{custom_specs}";

	public static final String WRITEBACK_ID = "{writeback_id}";

	public static final String STATUS = "{status}";

	public static final String CREATE_NODE_QUERY = "CREATE (n:{nodeLabel} {props}) RETURN id(n)";

	public static final String CREATE_ONWARD_RELATION = "MATCH (a:{firsLabel} { {firstPropertyName} : {firstPropertyValue}}),"
	        + "(a:{firsLabel} { {secondPropertyName} : {secondPropertyValue}}) CREATE (a)-[r:{relationship}]->(b) RETURN r;";

	public static final String GET_ALL_NODES_OF_TYPE = "MATCH (a:{nodeLabel} ) return collect(a);";

	public static final String UNIT_ID = "{unitId}";

	public static final String REMARKS = "{remarks}";

	public static final String WRITEBACK_CONF = "{writebackconf}";

	public static final String OPTIONAL_FILTER = "{optionalFilter}";

	public static final String MAKE = "{make}";

	public static final String MODEL = "{model}";

	public static final String VERSION = "{version}";

	public static final String PHYSICAL_QUANTITY = "{physical_quantity}";

	public static final String DATA_TYPE = "{data_type}";

	public static final String PARAMETER = "{parameter}";

	public static final String TAG_FILTER = "{tagFilter}";

	public static String prepareCypherArray(List<String> namesWithOutQoutes) {
		List<String> namesWithQoutes = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(namesWithOutQoutes)) {
			for (String name : namesWithOutQoutes) {
				namesWithQoutes.add("'" + name + "'");
			}
		}
		return namesWithQoutes.toString();

	}
}
