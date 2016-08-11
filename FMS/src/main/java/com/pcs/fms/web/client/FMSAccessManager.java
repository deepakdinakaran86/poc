package com.pcs.fms.web.client;

import static com.pcs.fms.web.constants.FMSWebConstants.ROLE_SUPER_ADMIN;
import static com.pcs.fms.web.constants.FMSWebConstants.ROLE_TENANT_ADMIN;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.manager.dto.UserInfo;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Component
public final class FMSAccessManager {

	private static Map<String, String> urlMap;

	static {
		urlMap = new HashMap<String, String>();

		// Admin

		urlMap.put("/FMS/tenant_home", "client_management");
		urlMap.put("/FMS/role_home", "role_management");
		urlMap.put("/FMS/user_home", "user_management");
		urlMap.put("/FMS/device_home", "devices");
		urlMap.put("/FMS/auditlog", "audit_logs");

		// Configuration

		urlMap.put("/FMS/vehicle_type_home", "vehicle_types");
		urlMap.put("/FMS/vehicle_list", "vehicle_management");
		urlMap.put("/FMS/poi_type_home", "poi_types");
		urlMap.put("/FMS/poi_def_home", "poi_management");
		urlMap.put("/FMS/tagtype_home", "tag_type");
		urlMap.put("/FMS/tag_home", "tag");
		urlMap.put("/FMS/geofence_list", "geofence");

		// Tracking

		urlMap.put("/FMS/vehicle_tracking", "live_tracking");
		urlMap.put("/FMS/vehicle_details", "vehicle_details");
		urlMap.put("/FMS/vehicle_history", "vehicle_history");

		// Alert Management

		urlMap.put("/FMS/alert_definition", "alarm_definition");
		urlMap.put("/FMS/alert_console", "alarm_console");
		urlMap.put("/FMS/alert_logs", "alarm_logs");

		// Service Maintenance

		urlMap.put("/FMS/item_list", "service_items");
		urlMap.put("/FMS/component_list", "service_components");
		urlMap.put("/FMS/schedule_list", "service_scheduling");

		// Routing & Scheduling

		urlMap.put("/FMS/route_home", "routing");

	}

	public static boolean hasUrlAccess(String url) {
		boolean flag = true;
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		if (isSuperAdmin() || isTenantAdmin()) {
			flag = true;
		} else if (urlMap.containsKey(url)) {
			String permGroup = urlMap.get(url);
			if (token.getPremissions().containsKey(permGroup)) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public static boolean hasMenuAccess(String menu) {
		boolean flag = false;
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		if (isSuperAdmin() || isTenantAdmin()) {
			flag = true;
		} else if (token.getPremissions().containsKey(menu)) {
			flag = true;
		}
		return flag;
	}

	public static boolean hasPermissionAccess(String PGroup, String permission) {
		boolean flag = false;
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		if (isSuperAdmin() || isTenantAdmin()) {
			flag = true;
		} else if (token.getPremissions().containsKey(PGroup)) {
			List<String> permissions = token.getPremissions().get(PGroup);
			if (permissions.contains(permission)) {
				flag = true;
			}
		}
		return flag;
	}

	public static boolean hasPermissionAccess(String PGroup,
	        String[] permissions) {
		boolean flag = false;
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		if (isSuperAdmin() || isTenantAdmin()) {
			flag = true;
		} else if (token.getPremissions().containsKey(PGroup)) {
			List<String> permissionList = token.getPremissions().get(PGroup);
			for (String permission : permissions) {
				if (permissionList.contains(permission)) {
					return true;
				}
			}
		}
		return flag;
	}

	public static boolean hasAnyMenuAccess(String[] menuArray) {
		boolean flag = false;
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		if (isSuperAdmin() || isTenantAdmin()) {
			return true;
		}
		for (String menu : menuArray) {
			if (token.getPremissions().containsKey(menu)) {
				return true;
			}
		}
		return flag;
	}

	public static boolean isSuperAdmin() {
		boolean flag = false;
		String bearer = FMSTokenHolder.getBearer();
		if (StringUtils.isNotBlank(bearer)) {
			Token token = TokenManager.getToken(bearer);
			if (token != null) {
				if (token.getRoleNames().contains(ROLE_SUPER_ADMIN)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public static boolean isTenantAdmin() {
		boolean flag = false;
		String bearer = FMSTokenHolder.getBearer();
		if (StringUtils.isNotBlank(bearer)) {
			Token token = TokenManager.getToken(bearer);
			if (token != null) {
				if (token.getRoleNames().contains(ROLE_TENANT_ADMIN)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public static String userInfotoUI() {
		Gson gson = new Gson();
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(token.getUserName());
		userInfo.setRoleNames(token.getRoleNames());
		userInfo.setPremissions(token.getPremissions());
		userInfo.setTenant(token.getTenant());
		userInfo.setCurrentTenant(token.getCurrentTenant());
		userInfo.setIsSubClientSelected(token.getIsSubClientSelected());
		return gson.toJson(userInfo);
	}

	public static String getCurrentDomain() {
		// Get the current token
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		String domain = null;
		// Check if a subclient is selected
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {
			// get sub tenant selected domain
			domain = token.getCurrentTenant().getCurrentDomain();
		} else {
			domain = token.getTenant().getCurrentDomain();
		}
		return domain;
	}

	public static String getCurrentTenantId() {
		// Get the current token
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		String domain = null;
		// Check if a subclient is selected
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {
			// get sub tenant selected domain
			domain = token.getCurrentTenant().getTenantId();
		} else {
			domain = token.getTenant().getTenantId();
		}
		return domain;
	}

	public static String getCurrentTenantName() {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String tenantName = null;
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {
			tenantName = token.getCurrentTenant().getTenantName();
		} else {
			tenantName = token.getTenant().getTenantName();
		}
		return tenantName;
	}

	public static String getUserName() {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		return token.getUserName();
	}

	public static String getUserImage() {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String userImage = null;
		if (token != null) {
			userImage = token.getUserImage();
		}
		if (StringUtils.isNotEmpty(userImage)) {
			userImage = "data:image/png;base64," + userImage;
		}
		return userImage;
	}

	public static String getTenantImage() {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		String tenantImage = null;
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {
			tenantImage = token.getCurrentTenant().getTenantImage();
		} else {
			tenantImage = token.getTenant().getTenantImage();
		}
		if (StringUtils.isNotEmpty(tenantImage)) {
			tenantImage = "data:image/png;base64," + tenantImage;
		}
		return tenantImage;
	}

	public static boolean getIsSubClientSelected() {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		boolean flag = Boolean.FALSE;
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {

			flag = Boolean.TRUE;
		}
		return flag;
	}
}
