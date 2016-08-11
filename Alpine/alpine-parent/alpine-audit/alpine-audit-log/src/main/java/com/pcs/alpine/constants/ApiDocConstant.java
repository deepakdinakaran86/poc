package com.pcs.alpine.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Twinkle (PCSEG297)
 * @date Oct 2015
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public static final String FIELD_DATA_NOT_AVAILABLE = "{field} data is not available";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	

	public final static String GET_ALL_LOGS_DESC = "This service is used to get all the logs of the platform, request payload is not required for this service";
	public final static String GET_ALL_LOGS_SUMMARY = "List of of Platform Logs";
	public final static String GET_ALL_LOGS_SUCCESS_RESP = "[{\"userName\":\"pcsadmin@pcs.galaxy\",\"activityTimeStamp\":\"07-10-2016 08:51:11\",\"eventDomain\":\"pcs\",\"affectedModule\":\"Avocado_User_Management\",\"auditSummary\":\"User is created\",\"ipAddress\":\"10.234.31.158\",\"eventLocale\":\"nil\"}]";
	
	public final static String GET_APP_LOGS_DESC = "This service is used to get all the logs of the platform, request payload is not required for this service";
	public final static String GET_APP_LOGS_SUMMARY = "List of of Platform Logs";
	public final static String GET_APP_LOGS_SUCCESS_RESP = "[{\"userName\":\"pcsadmin@pcs.galaxy\",\"activityTimeStamp\":\"07-10-2016 08:51:11\",\"eventDomain\":\"pcs\",\"affectedModule\":\"Avocado_User_Management\",\"auditSummary\":\"User is created\",\"ipAddress\":\"10.234.31.158\",\"eventLocale\":\"nil\"}]";



}
