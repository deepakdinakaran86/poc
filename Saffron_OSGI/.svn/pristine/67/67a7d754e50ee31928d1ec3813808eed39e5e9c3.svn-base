package com.pcs.saffron.expressions.engine;

import java.util.HashMap;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.UnifiedJEXL;

import com.pcs.saffron.expressions.util.ConversionUtils;
import com.pcs.saffron.expressions.util.Utilities;

public class ExpressionEngineUtil {

	private static JexlContext context = null;
	private static JexlEngine jexl = null;

	private static void init() {
		if (context == null || jexl == null) {
			context = new MapContext();
			jexl = new JexlEngine();

			HashMap<String, Object> functions = new HashMap<String, Object>();
			functions.put("convert", ConversionUtils.class);
			functions.put("math", Math.class);
			functions.put("number", Utilities.class);
			jexl.setFunctions(functions);
			jexl.setSilent(false);
		}
	}

	public Object evaluate(String expression,
			HashMap<String, Object> varMap) {
		init();
		for (String key : varMap.keySet()) {
			context.set(key, varMap.get(key));
		}
		context.set("pi", Math.PI);

		UnifiedJEXL ujexl = new UnifiedJEXL(jexl);
		UnifiedJEXL.Expression expr = ujexl.parse(expression);
		Object result = expr.evaluate(context);
		return result;
	}

	public static void main(String[] args) {/*
		HashMap<String, Object> varMap = new HashMap<String, Object>();
		varMap.put("Latitude", "\"2.0\"");
		varMap.put("EngineStatus", "ON");
		varMap.put("Longitude", "\"12.23\"");
		try {

			String expression = URLDecoder.decode("%24%7Blocation%3D%5B%5D%3Bfloat+latitude%3DFloat.parseFloat%28Latitude%29%3Bfloat+longitude%3DFloat.parseFloat%28Longitude%29%3Blocation%3D%5Blatitude%2Clongitude%5D%3B%7D","UTF-8").replaceAll("\\p{C}", "");
			Object res = evaluate("${location=[]; float latitude=new Float(Latitude);float longitude=new Float(Longitude);}", varMap);
		
						System.out
								.println(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	*/}
}
