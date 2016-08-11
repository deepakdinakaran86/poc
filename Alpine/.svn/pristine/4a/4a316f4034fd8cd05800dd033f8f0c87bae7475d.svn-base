package com.pcs.web.client;

import org.springframework.stereotype.Component;

import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;

@Component
public final class AccessManager {

	public static boolean hasNoAccess(String permission) {
		boolean flag = true;
		Token token = TokenManager.getToken(TokenHolder.getBearer());
		if (token.getRoleNames().contains("admin")) {
			flag = false;
		} else if (token.getPremissions().contains(permission)) {
			flag = false;
		}
		return flag;
	}

	public static boolean isAdminUser() {
		boolean flag = false;
		Token token = TokenManager.getToken(TokenHolder.getBearer());
		if (token.getRoleNames().contains("admin")) {
			flag = true;
		}
		return flag;
	}

}
