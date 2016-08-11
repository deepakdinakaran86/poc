package com.pcs.datasource.doc.constants;

public class ParameterResourceConstants extends ResourceConstants {

	public static final String SAVE_PARAMETER_SUMMARY = "Create a parametere";
	public static final String SAVE_PARAMETER_DESC = "This is the service to be used to create a new parameter,sample payload:{ \"name\": \"Engine_Heat4\", \"dataType\": \"Boolean\", \"physicalQuantity\": \"temperature\" }";

	public static final String GET_PARAMETERS_SUMMARY = "Get all the paramters of a subscriber";
	public static final String GET_PARAMETERS_DESC = "This is the service to be used to fetch all the parameters of a subscriber";
	public static final String GET_PARAMETERS_SUCCESS_RESPONSE = "[ { \"id\": 0, \"name\": \"Engine_Heat4\", \"dataType\": \"Boolean\", \"physicalQuantity\": \"temperature\" }, { \"id\": 0, \"name\": \"Engine_Heat5\", \"dataType\": \"Boolean\", \"physicalQuantity\": \"temperature\" } ]";
	
	public static final String IS_PARAMETER_EXIST_SUMMARY = "Check if parameter exists";
	public static final String IS_PARAMETER_EXIST_DESC = "This is the service to be used to check if a parameter exists with the specified name";
}
