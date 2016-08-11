
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
package com.pcs.datasource.model;

import java.io.Serializable;

/**
 * This class is responsible for the ds_parameter database table
 * 
 * @author pcseg129
 */
//@Entity
//@Table(name = "ds_parameter")
public class DatasourceParameter implements Serializable{

    private static final long serialVersionUID = -6029562525792933167L;
    private int id;
    private String parameterName;
    private String parameterType;
    private String description;
   // private DatasourceRegistration datasourceRegistration;
	
   // @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // @Column(unique = true, nullable = false)
    public int getId() {
		return id;
	}
	
    public void setId(int id) {
		this.id = id;
	}
	
   // @Column(name = "parameter_name", nullable = false, length = 45)
    public String getParameterName() {
		return parameterName;
	}
	
    public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
   // @Column(name = "parameter_type", nullable = false, length = 45)
    public String getParameterType() {
		return parameterType;
	}
	
    public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

   // @Column(name = "description", nullable = true, length = 45)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// bi-directional many-to-one association to RegistrationLookup
	//@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(name = "ds_id")
	/*public DatasourceRegistration getDatasourceRegistration() {
		return datasourceRegistration;
	}*/

	/*public void setDatasourceRegistration(
	        DatasourceRegistration datasourceRegistration) {
		this.datasourceRegistration = datasourceRegistration;
	}*/

}
