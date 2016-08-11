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
package com.pcs.guava.dto;

import com.pcs.guava.commons.dto.EntityDTO;

public class PoiESBDTO {
	
	private EntityDTO poiEntity;

	private EntityDTO poiTypeEntity;

	private String poiName;

	private String poiType;

	public EntityDTO getPoiEntity() {
		return poiEntity;
	}

	public void setPoiEntity(EntityDTO poiEntity) {
		this.poiEntity = poiEntity;
	}

	public EntityDTO getPoiTypeEntity() {
		return poiTypeEntity;
	}

	public void setPoiTypeEntity(EntityDTO poiTypeEntity) {
		this.poiTypeEntity = poiTypeEntity;
	}

	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}

}
