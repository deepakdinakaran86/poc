package com.pcs.saffron.expressions.engine;

import java.util.HashMap;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.UnifiedJEXL;

import com.pcs.saffron.expressions.util.ConversionUtils;
import com.pcs.saffron.helper.Utilities;

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

	public static Object evaluate(String expression,
			HashMap<String, Object> varMap) {
		init();
		for (String key : varMap.keySet()) {
			context.set(key, varMap.get(key));
		}

		UnifiedJEXL ujexl = new UnifiedJEXL(jexl);
		UnifiedJEXL.Expression expr = ujexl.parse(expression);
		Object result = expr.evaluate(context);
		return result;
	}

	public static void main(String[] args) {
		HashMap<String, Object> varMap = new HashMap<String, Object>();
		varMap.put("EngineStatus", "ON");
		varMap.put("Analog3", "9C4");
		varMap.put("valueInSI", "90.34");
		try {

			System.out
					.println(evaluate(
							"${	litre;	litre=valueInSI*0.001;	litre=number:formatDouble(litre,2);	litre;}", varMap));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
