package com.pcs.alarm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pcs.analytics.flinktests.beans.Parameter;


public class PointUtility {
	
	private static final String UNDERSCORE = "_";
	private static final Logger logger = Logger.getLogger(PointUtility.class);
	public static List<Parameter> getParameterPoints(String[] parameters,List<Parameter> points)  {
		if(parameters!=null && points!=null){
			List<Parameter> reqParams=new ArrayList<Parameter>();
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter=new Parameter();
				parameter.setDisplayName(parameters[i]);
				int index=points.indexOf(parameter);
				if(index>=0){
					reqParams.add(points.get(index));
				}else{
					logger.error("Invalid parameter name " + parameters[i]);
				}
			}
			return reqParams;
		}else{
			logger.error("Empty paramter list or empty list");
			return new ArrayList<Parameter>();
		}
	}
	
	public static final void updateBeans(List<Parameter> parameters){
		for (Parameter parameter : parameters) {
			parameter.setDisplayName(parameter.getDisplayName().toString().replaceAll(UNDERSCORE, " "));
		}
	}
}
