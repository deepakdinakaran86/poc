package org.wso2.carbon.test;

import java.util.HashMap;
import java.util.Map;

import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dto.APIKeyValidationInfoDTO;
import org.wso2.carbon.apimgt.impl.token.JWTGenerator;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

public class CustomTokenGenerator extends JWTGenerator {
	public Map populateStandardClaims(
			APIKeyValidationInfoDTO keyValidationInfoDTO, String apiContext,
			String version) throws APIManagementException {
		Map claims = super.populateStandardClaims(keyValidationInfoDTO,
				apiContext, version);
		return claims;
	}

	public Map populateCustomClaims(
			APIKeyValidationInfoDTO keyValidationInfoDTO, String apiContext,
			String version, String accessToken) throws APIManagementException {

		String tenantAwareUserName = keyValidationInfoDTO.getEndUserName();
		String superAdmin = "SuperAdmin";
		//String superAdmin = "admin";
		String dialect = getDialectURI();
		if (MultitenantConstants.SUPER_TENANT_ID == APIUtil
				.getTenantId(tenantAwareUserName)) {
			tenantAwareUserName = MultitenantUtils
					.getTenantAwareUsername(tenantAwareUserName);
		}
		CustomClaimsRetriever claimsRetriever = (CustomClaimsRetriever) getClaimsRetriever();
		Map customClaims = claimsRetriever.getClaims(tenantAwareUserName);
		if (claimsRetriever != null) {
			// Map customClaims =
			// super.populateStandardClaims(keyValidationInfoDTO, apiContext,
			// version);
			
			//Call getClaims method implemented in custom claim retriever class
			try {
                //Call getClaims method implemented in custom claim retriever class
                customClaims =  claimsRetriever.getClaims(tenantAwareUserName);

                String role = (String) customClaims.get(dialect + "/role");
                
                if(role.contains(superAdmin)){
                	customClaims.put("isSuperAdmin", "true");
                }else{
                	customClaims.put("isSuperAdmin", "false");
                }
                String permission = (String) customClaims.get(dialect + "/permission");

                /*claims.put(dialect + "/role", role);
                claims.put(dialect + "/permission", permission);*/
                customClaims.put(dialect + "/role", role);
                customClaims.put(dialect + "/permission", permission);

            } catch (APIManagementException e) {
            }

			boolean isApplicationToken = keyValidationInfoDTO.getUserType()
					.equalsIgnoreCase(
							APIConstants.ACCESS_TOKEN_USER_TYPE_APPLICATION) ? true
					: false;
			if (customClaims.get(dialect + "/enduser") != null) {
				if (isApplicationToken) {
					customClaims.put(dialect + "/enduser", "null");
					customClaims.put(dialect + "/enduserTenantId", "null");
				} else {
					String enduser = (String) customClaims.get(dialect
							+ "/enduser");
					if (enduser.endsWith("@carbon.super")) {
						enduser = enduser.replace("@carbon.super", "");
						customClaims.put(dialect + "/enduser", enduser);
					}
				}
			}
			// HashMap<String, String> map = new HashMap<String, String>();
			customClaims.put("ConsumerKey",
					keyValidationInfoDTO.getConsumerKey());
			customClaims.put("subscriberApp",
					keyValidationInfoDTO.getApplicationName());
			if (keyValidationInfoDTO.getSubscriber().contains("@")) {
				customClaims.put("subscriberName", keyValidationInfoDTO
						.getSubscriber().split("@")[0]);
				customClaims.put("subscriberDomain", keyValidationInfoDTO
						.getSubscriber().split("@")[1]);
			} else {
				customClaims.put("subscriberName",
						keyValidationInfoDTO.getSubscriber());
				customClaims.put("subscriberDomain", null);
			}
			if (keyValidationInfoDTO.getEndUserName().contains("@")) {
				customClaims.put("endUserName", keyValidationInfoDTO
						.getEndUserName().split("@")[0]);
				customClaims.put("endUserDomain", keyValidationInfoDTO
						.getEndUserName().split("@")[1]);
			} else {
				customClaims.put("endUserName",
						keyValidationInfoDTO.getEndUserName());
				customClaims.put("endUserDomain", null);
			}
			String endUserDomainName = keyValidationInfoDTO
					.getEndUserName().split("@")[1];
			String tenantId = endUserDomainName.split("\\.")[0];
			//customClaims.put("tenantName", "pcs.galaxy");
			customClaims.put("tenantId", tenantId);
			
			String apiName = keyValidationInfoDTO.getApiName();
			customClaims.put("API_NAME", apiName);

		}
		
		
		
		return customClaims;
	}
}
