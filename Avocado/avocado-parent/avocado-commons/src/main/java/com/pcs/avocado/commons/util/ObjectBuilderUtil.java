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
package com.pcs.avocado.commons.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ObjectBuilderUtil will be used for Object creations during Application
 * startup.
 *
 * @author Twinkle(PCSEG297)
 * @date December 2014
 * @since galaxy-1.0.0
 */
@Component
@Lazy
public class ObjectBuilderUtil {

	private Gson gson;

	private static final Gson lowerCaseGson = new GsonBuilder()
	        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

	/**
	 * Returns same Gson object during the course of Application. The Object
	 * will be lazily instantiated on startup.
	 *
	 * @return an instance of Gson
	 */
	public Gson getGson() {
		if (gson == null) {
			final GsonBuilder gsonBuilder = new GsonBuilder();
			gson = gsonBuilder.create();
		}

		return gson;
	}

	public Gson getLowerCaseUnderScoreGson() {
		return lowerCaseGson;
	}
}
