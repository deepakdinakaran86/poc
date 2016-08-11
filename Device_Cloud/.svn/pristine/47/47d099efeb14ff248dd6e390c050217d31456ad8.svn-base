/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLS and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.devicecloud.commons.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * 
 * This class is responsible for Mandatory Validations in Device Cloud This
 * throw {@link DeviceCloudException} on violations
 * 
 * @author Riyas PH (pcseg296)
 * 
 */
@Component
public class ValidationUtils {

	private static final Logger logger = LoggerFactory
	        .getLogger(ValidationUtils.class);

	/**
	 * Responsible for validating blank string and throw
	 * {@link DeviceCloudException} with
	 * DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED
	 * 
	 * @param field
	 * @param value
	 */
	public static void validateMandatoryField(String field, String value) {
		if (StringUtils.isBlank(value)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED, field);
		}
	}

	/**
	 * Responsible for validating blank string and throw
	 * {@link DeviceCloudException} with
	 * DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED
	 * 
	 * @param field
	 * @param value
	 */
	public static void validateMandatoryField(DataFields field, String value) {
		if (StringUtils.isBlank(value)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        field.getDescription());
		}
	}

	/**
	 * Responsible for validating blank fields of the dtoObject with supplied
	 * fieldNames and throw {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED
	 * 
	 * @param dtoObject
	 * @param fieldNames
	 */
	public static void validateMandatoryFields(Object dtoObject,
	        List<DataFields> fieldNames) {

		if (dtoObject == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INCOMPLETE_REQUEST);
		}
		for (DataFields dataFields : fieldNames) {

			try {
				Object fieldValueObj = PropertyUtils.getProperty(dtoObject,
				        dataFields.getVariableName());
				if (fieldValueObj == null
				        || StringUtils.isBlank(fieldValueObj.toString())) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        dataFields.getDescription());
				}
			} catch (
			        IllegalAccessException | InvocationTargetException
			        | NoSuchMethodException e) {
				logger.error("Error in Reading property of the given Object");
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.GENERAL_EXCEPTION);
			}
		}
	}

	public static void validateMandatoryInnerFields(DataFields propertyNam,
	        Object dtoObject, List<DataFields> fieldNames) {

		if (dtoObject == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INCOMPLETE_REQUEST);
		}
		for (DataFields dataFields : fieldNames) {

			try {
				Object fieldValueObj = PropertyUtils.getProperty(dtoObject,
				        dataFields.getVariableName());
				if (fieldValueObj == null
				        || StringUtils.isBlank(fieldValueObj.toString())) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        propertyNam.getDescription() + " : "
					                + dataFields.getDescription());
				}
			} catch (
			        IllegalAccessException | InvocationTargetException
			        | NoSuchMethodException e) {
				logger.error("Error in Reading property of the given Object");
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.GENERAL_EXCEPTION);
			}
		}
	}

	/**
	 * Responsible for validating blank fields of the dtoObject with supplied
	 * fieldNames and and throw {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED
	 * 
	 * @param dtoObject
	 * @param fieldNames
	 */
	public static void validateMandatoryFields(Object dtoObject,
	        DataFields... fieldNames) {
		validateMandatoryFields(dtoObject, Arrays.asList(fieldNames));
	}

	public static void validateMandatoryInnerFields(DataFields property,
	        Object dtoObject, DataFields... fieldNames) {
		validateMandatoryInnerFields(property, dtoObject,
		        Arrays.asList(fieldNames));
	}

	/**
	 * Responsible for validating empty with supplied collection and throw
	 * {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED with the specified
	 * collectionName
	 * 
	 * @param collection
	 * @param collectionName
	 */
	public static void validateCollection(DataFields collectionName,
	        @SuppressWarnings("rawtypes") Collection collection) {

		if (CollectionUtils.isEmpty(collection)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        collectionName.getDescription());
		}
	}

	/**
	 * Responsible for validating null with supplied object and throw
	 * {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.DATA_NOT_AVAILABLE
	 * 
	 * @param obj
	 */
	public static void validateResult(Object obj) {
		if (obj == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
	}

	/**
	 * Responsible for validating null with supplied object and throw
	 * {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE
	 * 
	 * @param objectName
	 * @param obj
	 */
	public static void validateSpecificResult(DataFields objectName, Object obj) {
		if (obj == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        objectName.getDescription());
		}
	}

	/**
	 * Responsible for validating empty with supplied collection and throw
	 * {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.DATA_NOT_AVAILABLE
	 * 
	 * @param obj
	 */
	public static void validateResult(
	        @SuppressWarnings("rawtypes") Collection collection) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
	}

	/**
	 * Responsible for validating empty with supplied collection and throw
	 * {@link DeviceCloudException} with Error Code
	 * DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE
	 * 
	 * @param obj
	 */
	public static void validateSpecificResult(DataFields objectName,
	        @SuppressWarnings("rawtypes") Collection collection) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        objectName.getDescription());
		}
	}

}
