package com.pcs.guava.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Sekhar (PCSEG336)
 * @date 1 Jun 2016
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public static final String JWT_ASSERTION = "JWT Token for Authorization";
	public static final String JWT_ASSERTION_SAMPLE = "header.ew0KICAic3Vic2NyaWJlckRvbWFpbiI6ICJhbHBpbmUuY29tIiwNCiAgImVuZFVzZXJEb21haW4iOiAicGFjaWZpY2NvbnRyb2xzLmFscGluZS5jb20iLA0KICAic3Vic2NyaWJlck5hbWUiOiAiYXZvY2FkbyIsDQogICJlbmRVc2VyTmFtZSI6ICJwY3NhZG1pbiIsDQogICJDb25zdW1lcktleSI6ICJmdDVmcUtlcWZ3RjBiRWdxZ0ZacWxTOXdDNElhIiwNCiAgInN1YnNjcmliZXJBcHAiOiAiYXZvY2FkbyIsDQogICJpc1N1cGVyQWRtaW4iOnRydWUsDQogICAidGVuYW50TmFtZSI6InBhY2lmaWNjb250cm9scyINCn0=.signature";

	public static final String GENERAL_SUCCESS_RESP = "{\"status\": \"SUCCESS\"}";
	public static final String GENERAL_FAILURE_RESP = "{\"status\": \"FAILURE\"}";
	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String SPECIFIC_DATA_NOT_AVAILABLE = "{mandatory-field} data is not available";
	public static final String FIELD_ALREADY_EXIST = "{mandatory-field} already exists";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_START_END_TIME = "Start date should be after end date";
	public static final String LIMIT_EXCEEDED = "{field} limit exceeded";
	public static final String DOMAIN = "Domain Name";
	public static final String DOMAIN_SAMPLE = "jll.alpine.com";

	public static final String JWT_HEADER_INVALID = "JWT Header parameter is invalid";

	public final static String VIEW_ROUTE_DESC = "This service is used to view the route details: ";
	public final static String VIEW_ROUTE_SUMMARY = "View Route";
	public final static String VIEW_ROUTE_SUCC_RESP = "{\"routeName\":\"KochiToMunnar1\",\"routeDescription\":\"route from kochi to munnar\",\"totalDistance\":20000,\"status\":\"ACTIVE\",\"startAddress\":\"Ernakulam\",\"endAddress\":\"Marayur\",\"routeString\":\"the string to plot route on the map\","
			+ "\"destinationPoints\":[{\"address\":\"South\",\"index\":0,\"latitude\":\"25.1236\",\"longitude\":\"55.1236\",\"radius\":\"5\",\"name\":\"SouthStation1\"},{\"address\":\"Marayur\",\"index\":2,\"latitude\":\"25.9876\",\"longitude\":\"55.9876\",\"radius\":\"5\",\"name\":\"Marayur1\"},"
			+ "{\"address\":\"South1\",\"index\":1,\"latitude\":\"25.258\",\"longitude\":\"55.258\",\"radius\":\"10\",\"name\":\"South2\"}]}";

	public final static String LIST_ROUTE_DESC = "This service is used to list the routes under a domain ";
	public final static String LIST_ROUTE_SUMMARY = "List all routes ";
	public final static String LIST_ROUTE_SUCC_RESP = "[{\"routeName\":\"KochiToMunnar\",\"routeDescription\":\"route from kochi to munnar\",\"totalDistance\":20000,\"status\":\"ACTIVE\",\"startAddress\":\"Ernakulam\",\"endAddress\":\"Marayur\"},"
			+ "{\"routeName\":\"DiscoveryToIbn7\",\"routeDescription\":\"route from discovery to pcs\",\"totalDistance\":20000,\"status\":\"Active\",\"startAddress\":\"Street 3,Building 43,Disocvery\",\"endAddress\":\"Pacific Controls,Techno Park\"}]";

	public final static String DELETE_ROUTE_DESC = "This service is used to list the routes under a domain ";
	public final static String DELETE_ROUTE_SUMMARY = "List all routes ";
	public final static String DELETE_ROUTE_SUCC_RESP = "[{\"routeName\":\"KochiToMunnar\",\"routeDescription\":\"route from kochi to munnar\",\"totalDistance\":20000,\"status\":\"ACTIVE\",\"startAddress\":\"Ernakulam\",\"endAddress\":\"Marayur\"},"
			+ "{\"routeName\":\"DiscoveryToIbn7\",\"routeDescription\":\"route from discovery to pcs\",\"totalDistance\":20000,\"status\":\"Active\",\"startAddress\":\"Street 3,Building 43,Disocvery\",\"endAddress\":\"Pacific Controls,Techno Park\"}]";

	public final static String CREATE_ROUTE_DESC = "This service is used to Create a route under a domain sample request :{\"routeName\":\"KochiToMunnar1\",\"routeDescription\":\"route from kochi to munnar\",\"totalDistance\":\"20000\",\"totalDuration\":\"20000\",\"status\":\"ACTIVE\",\"startAddress\":\"Ernakulam\",\"mapProvider\":\"Google\",\"endAddress\":\"Marayur\",\"routeString\":\"the string to plot route on the map\","
			+ "\"destinationPoints\":[{\"latitude\":\"25.1236\",\"longitude\":\"55.1236\",\"name\":\"SouthStation1\",\"radius\":\"5\",\"address\":\"South\",\"index\":\"0\"},{\"latitude\":\"25.258\",\"longitude\":\"55.258\",\"name\":\"South2\",\"radius\":\"10\",\"address\":\"South1\",\"index\":\"1\"},{\"latitude\":\"25.9876\",\"longitude\":\"55.9876\",\"name\":\"Marayur1\",\"radius\":\"5\",\"address\":\"Marayur\",\"index\":\"2\"}]}";
	public final static String CREATE_ROUTE_SUMMARY = "Create Route";

	public final static String CHECK_ROUTE_EXIST_DESC = "This service is used to check whether the route exist";
	public final static String CHECK_ROUTE_EXIST_SUMMARY = "Route exist";
	public final static String CHECK_ROUTE_EXIST_RESP = "{\"errorMessage\":\"Route Exist\"}";

	public final static String UPDATE_ROUTE_DESC = "This service is used to update the route sample request :{\"routeName\":\"KochiToMunnar1\",\"routeDescription\":\"route from kochi to munnar\",\"totalDistance\":\"20000\",\"totalDuration\":\"20000\",\"status\":\"ACTIVE\",\"startAddress\":\"Ernakulam\",\"mapProvider\":\"Google\",\"endAddress\":\"Marayur\",\"routeString\":\"the string to plot route on the map\","
			+ "\"destinationPoints\":[{\"latitude\":\"25.1236\",\"longitude\":\"55.1236\",\"name\":\"SouthStation1\",\"radius\":\"5\",\"address\":\"South\",\"index\":\"0\"},{\"latitude\":\"25.258\",\"longitude\":\"55.258\",\"name\":\"South2\",\"radius\":\"10\",\"address\":\"South1\",\"index\":\"1\"},{\"latitude\":\"25.9876\",\"longitude\":\"55.9876\",\"name\":\"Marayur1\",\"radius\":\"5\",\"address\":\"Marayur\",\"index\":\"2\"}]}";
	public final static String UPDATE_ROUTE_SUMMARY = "Update route";

	public final static String CREATE_SCHEDULE_DESC = "This service is used to create a schedule for route sample request :{\"scheduleName\":\"BinManagement11\",\"description\":\"DXBtoSHJ\",\"routeName\":\"DubaiToSharjah30\",\"startTime\":\"22:30:00\",\"endTime\":\"22:00:00\",\"daySpan\":2,\"destinationPoints\":[{\"arrivalTime\":\"00:55:00\",\"timeTolerance\":\"00:05:00\",\"departureTime\":\"13:55:00\",\"poiName\":\"Discovery30\"},"
			+ "{\"arrivalTime\":\"00:05:00\",\"timeTolerance\":\"00:05:00\",\"departureTime\":\"13:05:00\",\"poiName\":\"AlKarama30\"}]}";

	public final static String CREATE_SCHEDULE_SUMMARY = "Create Schedule";

	public final static String VIEW_SCHEDULE_DESC = "This service is used to view the schedule";

	public final static String VIEW_SCHEDULE_SUMMARY = "View Schedule";

	public final static String VIEW_SCHEDULE_SUCC_RESP = "{\"scheduleName\":\"BinManagement11\",\"description\":\"DXBtoSHJ\",\"routeName\":\"DubaiToSharjah30\",\"startTime\":\"22:30:00\",\"endTime\":\"22:00:00\",\"daySpan\":2,\"destinationPoints\":[{\"arrivalTime\":\"00:55:00\",\"timeTolerance\":\"00:05:00\",\"departureTime\":\"13:55:00\",\"poiName\":\"Discovery30\"},"
			+ "{\"arrivalTime\":\"00:05:00\",\"timeTolerance\":\"00:05:00\",\"departureTime\":\"13:05:00\",\"poiName\":\"AlKarama30\"}]}";

	public final static String ROUTE_NAME = "Route Name";

}
