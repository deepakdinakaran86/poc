
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
package com.pcs.deviceframework.decoder.data.point;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for derived points
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date April 01 2015
 */
public class DerivedPoint extends Point {

	private static final long serialVersionUID = 2359640187883133231L;

	private Set<String> dependencyPointIds = new HashSet<>(0);
	
	public Set<String> getDependencyPointIds() {
		return dependencyPointIds;
	}

	public void setDependencyPointIds(Set<String> pointId) {
		this.dependencyPointIds = pointId;
	}
	
	public void addDependencyPoint(String pointId){
		this.dependencyPointIds.add(pointId);
	}
	
	
}
