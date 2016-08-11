/**
 * 
 */
package com.pcs.deviceframework.devicemanager.bean;

import java.io.Serializable;

import com.pcs.deviceframework.decoder.data.point.Point;


/**
 * @author pcseg310
 *
 */
public class ConfigPoint extends Point implements Serializable {

    private static final long serialVersionUID = -8703350482177294191L;
	private String precedence;
	private String expression;
	
	public String getPrecedence() {
		return precedence;
	}
	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	

}
