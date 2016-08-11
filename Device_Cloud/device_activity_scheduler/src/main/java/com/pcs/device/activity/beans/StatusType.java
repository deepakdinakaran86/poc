/**
 * 
 */
package com.pcs.device.activity.beans;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author pcseg310
 *
 */
@JsonSerialize
public enum StatusType {

	ACTIVE,INACTIVE;
}
