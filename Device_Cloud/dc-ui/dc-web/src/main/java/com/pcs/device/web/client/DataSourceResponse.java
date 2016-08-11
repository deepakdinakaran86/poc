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
package com.pcs.device.web.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pcs.device.web.dto.ErrorDTO;

/**
 * DataSourceResponse holds the entity, error message and http response code of
 * the Datasource api call
 * 
 * @author pcseg323 Greeshma
 * @date May 2015
 * @since galaxy-1.0.0
 * @param <T>
 */
public class DataSourceResponse<T> {

	private T entity;

	private ErrorDTO errorMessage;

	@JsonIgnore
	private HttpHeaders headers;

	private HttpStatus status;

	public ErrorDTO getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorDTO errorMessageDto) {
		this.errorMessage = errorMessageDto;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public boolean hasNoErrors() {
		return status != null
		        && !((Series.CLIENT_ERROR.equals(status.series()) || Series.SERVER_ERROR
		                .equals(status.series())));
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
