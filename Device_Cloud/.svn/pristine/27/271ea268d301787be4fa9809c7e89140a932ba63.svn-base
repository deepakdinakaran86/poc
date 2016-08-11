package com.pcs.datasource.test;

import com.google.gson.Gson;
import com.pcs.datasource.dto.ConfigPoint;

public class Test {
	public static void main(String[] args) {
		configDevice();
	}

	private static void configDevice() {
		ConfigPoint cp = new ConfigPoint();
		cp.setType("Boolean");
		cp.setPointId("Latitude");
		Gson gson = new Gson();
		System.out.println(gson.toJson(cp));
	}

}
