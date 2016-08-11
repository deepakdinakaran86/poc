package com.pcs.web.client;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public final class TokenHolder {

	private static String bearer;

	public static String getBearer() {
		return bearer;
	}

	public static void setBearer(String currentBearer) {
		bearer = currentBearer;
	}

}
