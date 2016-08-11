/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.data.analyzer.executor.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.expressions.engine.ExpressionEngineUtil;
import com.pcs.saffron.manager.bean.ConfigPoint;

/**
 * This class is responsible for finding the point value by executing the
 * configured class
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 30 2015
 */
public class PointHandler {

	List<Point> points;
	Set<String> dependencyPointIds;
	ConfigPoint pointConfiguration;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Set<String> getDependencyPointIds() {
		return dependencyPointIds;
	}

	public void setDependencyPointIds(Set<String> pointIds) {
		this.dependencyPointIds = pointIds;
	}

	public ConfigPoint getPointConfiguration() {
		return pointConfiguration;
	}

	public void setPointConfiguration(ConfigPoint pointConfiguration) {
		this.pointConfiguration = pointConfiguration;
	}

	public Object execute(String exp, HashMap<String, Object> varMap) {
		new ExpressionEngineUtil().evaluate(exp, varMap);
		return new Object();
	}

}
