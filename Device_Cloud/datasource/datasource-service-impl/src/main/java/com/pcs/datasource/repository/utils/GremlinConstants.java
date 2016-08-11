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

/**
 * Constants used in Gremlin
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
public class GremlinConstants {

	public static final String GET_ALL_VERTICES_OF_TYPE = "g.V().hasLabel('{vertexLabel}').valueMap()";

	public static final String DROP_VERTEX_WITH_ID = "g.V({id}).drop()";

	public static final String VERTEX_LABEL = "{vertexLabel}";

	public static final String SUBSCRIPTION = "SUBSCRIPTION";

	public static final String PARAMETER = "PARAMETER";

	public static final String PHYSICAL_QUANTITY = "PHYSICAL_QUANTITY";

	public static final String UNIT = "UNIT";

	public static final String CONFIGURED_POINT = "CONFIGURED_POINT";

	public static final String PROTOCOL_VERSION = "PROTOCOL_VERSION";

	public static final String DATA_TYPE = "DATA_TYPE";

	public static final String MAKE = "MAKE";

	public static final String DEVICE_TYPE = "DEVICE_TYPE";

	public static final String MODEL = "MODEL";

	public static final String DEVICE_PROTOCOL = "DEVICE_PROTOCOL";

	public static final String DEVICE = "DEVICE";

	public static final String NW_PROTOCOL = "NW_PROTOCOL";

	public static final String DEVICE_CONFIG_TEMP = "DEVICE_CONFIG_TEMP";

	public static final String POINT = "POINT";

	public static final String COMMAND = "COMMAND";

	public static final String BATCH = "BATCH";

	public static final String HAS_PARAMETER = "hasParameter";

	public static final String CONTAINED_IN = "containedIn";

	public static final String IS_OF_TYPE = "isOfType";

	public static final String HAS_TYPE = "hasType";

	public static final String HAS_MODEL = "hasModel";

	public static final String TALKSIN = "talksIn";

	public static final String HAS_VERSION = "hasVersion";

	public static final String HAS_POINT = "hasPoint";

	public static final String TRANSPORTS_IN = "transportsIn";

	public static final String IS_TAGGED_WITH = "isTaggedWith";

	public static final String HAS_TAG = "hasTag";

	public static final String HAS_TEMPLATE = "hasTemplate";

	public static final String MEASURES_IN = "measuresIn";

	public static final String CONFIGURED_AS = "configuredAs";

	public static final String EXECUTED = "executed";

	public static final String INCLUDES = "includes";

	public static final String OWNS = "owns";

	public static final String IS_CONFIGURED_WITH = "isConfiguredWith";

	// Fields

	public static final String SOURCE_IDS = "sourceIds";

	public static final String SOURCE_ID = "sourceId";

	public static final String REQUEST_ID = "requestId";

	public static final String STATUS = "status";

	public static final String SUB_ID = "subId";

	public static final String REQUESTED_AT = "requestedAt";

}
