
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
package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * @description DTO for parameters
 * @author pcseg129(Seena Jyothish)
 * @date 22 Jan 2015
 */
@XmlRootElement(name = "data")
public class MessageDTO implements Serializable{

    private static final long serialVersionUID = 6948535153731613058L;

	private String datasourceName;
    private List<ParameterDTO> parameters;
	
	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public List<ParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterDTO> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString(){
		 Gson gson = new Gson();
		 return gson.toJson(this);
	}
	
	public String toJSON(){

		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		for (int i = 0; i < parameters.size(); i++) {
			ParameterDTO parameter = parameters.get(i);
			buffer.append("\"").append(parameter.getName()).append("\"").append(":").append("\"").append(parameter.getValue()).append("\"");
			if(i < parameters.size()-1)
				buffer.append(",");
        }
		 buffer.append("}");
		 return buffer.toString();
	
	}
	
}
