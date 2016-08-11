package com.pcs.data.analyzer.storm.event.candidate.beans;

import java.util.HashSet;
import java.util.Set;

public class EventfulParameterNames {


	private static final Set<String> parameters = new HashSet<String>();

	public static final Set<String> getParameters(){
		parameters.add("speed");
		return parameters;
	}
}
