package com.pcs.gateway.teltonika.bundle.utils;

import com.pcs.saffron.expressions.engine.ExpressionEngineUtil;

/**
 * @author pcseg171
 *
 */
public class ExpressionUtil {

	private static ExpressionEngineUtil expressionEngine = null;

	/**
	 * @return
	 */
	public static ExpressionEngineUtil getExpressionEngine() {
		return expressionEngine;
	}

	/**
	 * @param expressionEngine
	 */
	public static void setExpressionEngine(ExpressionEngineUtil expressionEngine) {
		if(ExpressionUtil.expressionEngine == null)
			ExpressionUtil.expressionEngine = expressionEngine;
	}
	
	/**
	 * @param expressionEngine
	 */
	public static void resetExpressionEngine(ExpressionEngineUtil expressionEngine) {
		if(expressionEngine == null)
			ExpressionUtil.expressionEngine = null;
	}

}
