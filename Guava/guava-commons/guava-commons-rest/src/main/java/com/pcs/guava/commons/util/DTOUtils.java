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
package com.pcs.guava.commons.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
/**
 * Utility Class to make use of BeanUtils and throws GalaxyException
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date November 2014
 * @since galaxy-1.0.0
 */
public class DTOUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DTOUtils.class);

	/**
	 * Utility method for copying properties of the source object into the
	 * destination object
	 * 
	 * @param destination
	 * @param source
	 */
	public static void copyProperties(Object destination, Object source) {
		try {
			BeanUtils.copyProperties(destination, source);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error("Error in copyProperties",e);
			throw new GalaxyException(GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
		}
	}

	/**
	 * Utility method for copying properties of the source List into the
	 * destination List
	 * 
	 * @param destinationClass
	 * @param destination
	 * @param source
	 */

	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	public static void copyProperties(Class destinationClass, List destination,
	        List source) {
		for (Object object : source) {
			Object destObject;
			try {
				destObject = destinationClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error("Error in copyProperties");
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
			}
			DTOUtils.copyProperties(destObject, object);
			destination.add(destObject);
		}
	}

	/**
	 * Utility method for copying properties of the source object into a new
	 * object and return the same
	 * 
	 * @param destination
	 * @param source
	 */
	public static Object copyProperties(Object source) {
		try {

			Object destination = new Object();
			BeanUtils.copyProperties(destination, source);
			return destination;
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error("Error in copyProperties",e);
			throw new GalaxyException(GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
		}
	}
}
