package com.pcs.avocado.commons.token;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 13 January 2016
 * @since avocado-1.0.0
 * 
 */


@Component
@Scope("prototype")
public class ApplicationTokenHolder {

	private static String bearer;

	public static String getBearer() {
		return bearer;
	}

	public static void setBearer(String currentBearer) {
		bearer = currentBearer;
	}

}