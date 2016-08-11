/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.model;

/**
 * Model Class for Entity Hierarchy Metadata
 * 
 * @author Daniela (pcseg191)
 * @date May 17, 2016
 * @since galaxy-1.0.0
 */
public class TaggedEntity {

	private String geotagDomain;
	private String geotagTemplate;
	private String geotagIdentifierKey;
	private String geotagStatus;
	private String geotagIdentifierValue;
	private String geotagType;
	private String geotagDataprovider;
	private String entityDomain;
	private String entityTemplateName;
	private String entityIdentifierKey;
	private String entityStatus;
	private String entityIdentifierValue;
	private String entityType;
	private String entityDataprovider;
	private String tree;

	public String getGeotagDomain() {
		return geotagDomain;
	}

	public void setGeotagDomain(String geotagDomain) {
		this.geotagDomain = geotagDomain;
	}

	public String getGeotagTemplate() {
		return geotagTemplate;
	}

	public void setGeotagTemplate(String geotagTemplate) {
		this.geotagTemplate = geotagTemplate;
	}

	public String getGeotagIdentifierKey() {
		return geotagIdentifierKey;
	}

	public void setGeotagIdentifierKey(String geotagIdentifierKey) {
		this.geotagIdentifierKey = geotagIdentifierKey;
	}

	public String getGeotagStatus() {
		return geotagStatus;
	}

	public void setGeotagStatus(String geotagStatus) {
		this.geotagStatus = geotagStatus;
	}

	public String getGeotagIdentifierValue() {
		return geotagIdentifierValue;
	}

	public void setGeotagIdentifierValue(String geotagIdentifierValue) {
		this.geotagIdentifierValue = geotagIdentifierValue;
	}

	public String getEntityDomain() {
		return entityDomain;
	}

	public void setEntityDomain(String entityDomain) {
		this.entityDomain = entityDomain;
	}

	public String getEntityTemplateName() {
		return entityTemplateName;
	}

	public void setEntityTemplateName(String entityTemplateName) {
		this.entityTemplateName = entityTemplateName;
	}

	public String getEntityIdentifierKey() {
		return entityIdentifierKey;
	}

	public void setEntityIdentifierKey(String entityIdentifierKey) {
		this.entityIdentifierKey = entityIdentifierKey;
	}

	public String getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(String entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getEntityIdentifierValue() {
		return entityIdentifierValue;
	}

	public void setEntityIdentifierValue(String entityIdentifierValue) {
		this.entityIdentifierValue = entityIdentifierValue;
	}

	public String getGeotagType() {
		return geotagType;
	}

	public void setGeotagType(String geotagType) {
		this.geotagType = geotagType;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getGeotagDataprovider() {
		return geotagDataprovider;
	}

	public void setGeotagDataprovider(String geotagDataprovider) {
		this.geotagDataprovider = geotagDataprovider;
	}

	public String getEntityDataprovider() {
		return entityDataprovider;
	}

	public void setEntityDataprovider(String entityDataprovider) {
		this.entityDataprovider = entityDataprovider;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

}