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
package com.pcs.guava.rest.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Custom mapper. The constructor sets the JsonInclude.Include.NON_NULL to the
 * serialization config and The constructor sets the
 * DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES to the _deserialization
 * config
 * 
 * @author Greeshma (pcseg323)
 * 
 */
public class GalaxyObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -5110857759366163529L;

	public GalaxyObjectMapper() {

		super();

		_serializationConfig = _serializationConfig
		        .withSerializationInclusion(JsonInclude.Include.NON_NULL);

		_deserializationConfig = _deserializationConfig
		        .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	}
}
