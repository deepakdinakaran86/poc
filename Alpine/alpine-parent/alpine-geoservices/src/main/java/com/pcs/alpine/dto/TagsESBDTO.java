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
package com.pcs.alpine.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GeoTagsListESBResponseDTO
 *
 * @description DTO for tags
 * @author Daniela (PCSEG191)
 * @date 24 Apr 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class TagsESBDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TagInfoESBDTO> tagInfo;

	public List<TagInfoESBDTO> getTagInfo() {
	    return tagInfo;
    }

	public void setTagInfo(List<TagInfoESBDTO> tagInfo) {
	    this.tagInfo = tagInfo;
    }

}
