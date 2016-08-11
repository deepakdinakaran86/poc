/**
 * 
 */
package com.pcs.saffron.manager.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author pcseg310
 *
 */
@JsonSerialize
public enum StatusType {

	ACTIVE,INACTIVE;
}
