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
package com.pcs.device.web.constants;

/**
 * Constants specifies path and view names
 * 
 * @author pcseg310 (Rakesh)
 * @date May 2015
 * @since galaxy-1.0.0
 * 
 */
public interface WebConstants {

	public final static String REDIRECT = "redirect:";

	// Path names
	public final static String LOGOUT_PATH_NAME = "logout";
	public final static String LOGIN_PATH_NAME = "login";
	public final static String AUTHENTICATE_PATH_NAME = "authenticate";
	public final static String DEVICE_PATH_NAME = "device";
	public final static String DEVICE_TAB_PATH_NAME = "devicetab";
	public final static String MANAGE_DEVICE_PATH_NAME = "managedevice";
	public final static String LIVE_TRACKING_PATH_NAME = "livetracking";
	public final static String HISTORY_TRACKING_PATH_NAME = "historytracking";
	public final static String WRITE_BACK_PATH_NAME = "writeback";
	public final static String CREATE_DEVICE_PATH_NAME = "createdevice";
	public final static String WRITE_BACK_BATCH_PATH_NAME = "writebackbatch";
	public final static String WRITE_BACK_LOG_PATH_NAME = "writebacklog";

	public final static String CONFIGURATION_TEMPLATE_PATH = "configTemplate";
	public final static String CREATE_CONFIGURATION_TEMPLATE_PATH = "createConfigTemplate";
	public final static String MAP_PARAMETER_CONFIG_PATH = "mapParameterConfig";
	public final static String MAP_PARAMETER_EDIT_PATH = "mapParameterEdit";
	public final static String PARAMETER_CONFIGURATIONVIEW_PATH = "parameterView";
	public final static String PARAMETER_CONFIGURATION_PATH = "parameterConfiguration";
	public final static String PARAMETER_CONFIGURATION_DEVICE_PATH = "parameterDeviceConfiguration";

	// View names
	final static String LOGIN_VIEW = "login";
	final static String DEVICE_VIEW = "device";
	final static String DEVICE_TAB_VIEW = "devicetab";
	final static String MANAGE_DEVICE_VIEW = "managedevice";
	final static String LIVE_TRACKING_VIEW = "livetracking";
	final static String HISTORY_TRACKING_VIEW = "historytracking";
	final static String WRITE_BACK_VIEW = "writeback";
	final static String CREATE_DEVICE_VIEW = "createdevice";
	final static String WRITE_BACK_BATCH_VIEW = "writebackbatch";
	final static String WRITE_BACK_LOG_VIEW = "writebacklog";

	final static String CONFIGURATION_TEMPLATE_VIEW = "configTemplate";
	final static String CREATE_CONFIGURATION_TEMPLATE_VIEW = "createConfigTemplate";
	final static String MAP_PARAMETER_CONFIG_VIEW = "mapParameterConfig";
	final static String MAP_PARAMETER_EDIT_VIEW = "mapParameterEdit";
	final static String PARAMETER_CONFIGURATIONVIEW_VIEW = "parameterView";
	final static String PARAMETER_CONFIGURATION_VIEW = "parameterConfiguration";
	final static String PARAMETER_CONFIGURATION_DEVICE_VIEW = "parameterDeviceConfiguration";

}
