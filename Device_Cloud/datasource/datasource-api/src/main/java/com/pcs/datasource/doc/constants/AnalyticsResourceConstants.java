package com.pcs.datasource.doc.constants;

public class AnalyticsResourceConstants extends ResourceConstants {

	public static final String GET_AGGREGATED_DATA_SUMMARY = "Get aggregated values for row data";
	public static final String GET_AGGREGATED_DATA_DESC = "This is the service to be used to get aggregated values for row data by date range,sample payload : "+"{\"startDate\":1458968100000,\"endDate\":1461041400000,\"aggregationFunctions\":[\"ALL\"],\"devicePointsMap\":[{\"sourceId\":\"259642460471175\",\"displayNames\":[\"Engine Crankcase Pressure-23\"]}]}";
	public static final String GET_AGGREGATED_DATA_SUCCESS_RESP = "{\"data\":[{\"sourceId\":\"259642460471175\",\"rows\":[{\"displayName\":\"Engine Crankcase Pressure-23\",\"aggregatedData\":{\"AVG\":\"350.01802110133895\",\"MIN\":\"300.07812\",\"MAX\":\"399.92078\",\"COUNT\":\"7391\",\"SUM\":\"2586983.193959996\",\"RANGE\":\"99.84265999999997\"}}]}]}";

}
