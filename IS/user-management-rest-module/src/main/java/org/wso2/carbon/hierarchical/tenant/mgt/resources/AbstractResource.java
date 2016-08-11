/*
 *  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.hierarchical.tenant.mgt.resources;

import org.wso2.carbon.CarbonException;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.StandardResponse;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.service.RealmService;

import javax.ws.rs.core.Response;

public abstract class AbstractResource {

	public enum ResponseStatus {
		SUCCESS, FAILED, INVALID, FORBIDDEN, NOT_EXIST, AUTHENTICATION_FAILED
	}

	public UserRealm getUserRealm(String tenantDomain)
			throws UserStoreException, CarbonException {
		PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext
				.getThreadLocalCarbonContext();
		RealmService realmService = (RealmService) carbonContext
				.getOSGiService(RealmService.class);
		int tenantId = realmService.getTenantManager()
				.getTenantId(tenantDomain);
		return realmService.getTenantUserRealm(tenantId);
	}

	public Response handleResponse(ResponseStatus responseStatus, String message) {
		Response response;
		switch (responseStatus) {
		case SUCCESS:
			StandardResponse success = getResponseMessage(
					Constants.SUCCESS_STATUS, message);
			response = Response.ok().entity(success).build();
			break;
		case FAILED:
			StandardResponse failed = getResponseMessage(
					Constants.FAILED_STATUS, message);
			response = Response.serverError().entity(failed).build();
			break;
		case NOT_EXIST:
			StandardResponse notExist = getResponseMessage(
					Constants.FAILED_STATUS, message);
			response = Response.status(520).entity(notExist).build();
			break;
		case INVALID:
			StandardResponse invalid = getResponseMessage(
					Constants.FAILED_STATUS, message);
			response = Response.status(400).entity(invalid).build();
			break;
		case FORBIDDEN:
			StandardResponse forbidden = getResponseMessage(
					Constants.FAILED_STATUS, message);
			response = Response.status(403).entity(forbidden).build();
			break;
		case AUTHENTICATION_FAILED:
			StandardResponse authenticationFailed = getResponseMessage(
					Constants.FAILED_STATUS, message);
			response = Response.status(401).entity(authenticationFailed)
					.build();
			break;
		default:
			response = Response.noContent().build();
		}
		return response;
	}

	private StandardResponse getResponseMessage(String status, String message) {
		StandardResponse standardResponse = new StandardResponse(status);
		if (message != null) {
			standardResponse.setMessage(message);
		}
		return standardResponse;
	}
}
