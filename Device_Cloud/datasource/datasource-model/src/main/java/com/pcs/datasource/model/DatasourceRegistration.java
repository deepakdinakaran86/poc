
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
 * The persistent class for the ds_registration database table.
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 24 Jan 2015
 */
//@Entity
//@Table(name = "ds_registration")
public class DatasourceRegistration implements Serializable {

    private static final long serialVersionUID = 3378139880305402891L;
    private int id;
    private String datasourceName;
    //private String datasourceUniqueName;
    private boolean isActive;
    
    
   // @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // @Column(unique = true, nullable = false)
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//@Column(name = "ds_name", nullable = false, length = 45)
	public String getDatasourceName() {
		return datasourceName;
	}
	
	public void setDatasourceName(String ds_name) {
		this.datasourceName = ds_name;
	}
	
	/*@Column(name = "ds_unique_name", nullable = false, length = 45)
	public String getDatasourceUniqueName() {
		return datasourceUniqueName;
	}*/
	
	/*public void setDatasourceUniqueName(String ds_unique_name) {
		this.datasourceUniqueName = ds_unique_name;
	}*/
	
	//@Column(name = "is_active",columnDefinition = "TINYINT(1)")
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
