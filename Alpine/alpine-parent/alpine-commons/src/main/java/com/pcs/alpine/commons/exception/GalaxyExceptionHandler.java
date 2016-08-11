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
package com.pcs.alpine.commons.exception;

import static com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pcs.alpine.commons.rest.dto.ErrorMessageDTO;
import com.pcs.alpine.rest.exception.GalaxyRestException;

/**
 * GalaxyExceptionHandler
 *
 * @description Handler Class for GalaxyException
 * @see GalaxyException
 * @author Javid Ahammed (PCSEG199)
 * @date 12 October 2014
 * @since galaxy-1.0.0
 */
@Provider
public class GalaxyExceptionHandler implements ExceptionMapper<Throwable> {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GalaxyExceptionHandler.class);

	@Override
	public Response toResponse(Throwable throwable) {

		ErrorMessageDTO errorDto = new ErrorMessageDTO();

		if (throwable instanceof GalaxyException) {
			/**
			 * Exception handler for 400 - Bad Request Business Exception will
			 * be returned with HTTP Status Code 400
			 */
			errorDto = ((GalaxyException)throwable).getErrorMessageDTO();
			LOGGER.debug("GalaxyException Occured", throwable);
			LOGGER.info("GalaxyException Occured :{}",
			        errorDto.getErrorMessage());
			return Response.status(Status.BAD_REQUEST).entity(errorDto).build();
		} else if (throwable instanceof NotAuthorizedException) {
			/**
			 * Exception handler for 401 - Unauthorized Exception will be
			 * returned with HTTP Status Code 401
			 */
			errorDto.setErrorCode(GalaxyCommonErrorCodes.USER_AUTHENTICATION_FAILED
			        .getCode().toString());
			errorDto.setErrorMessage(GalaxyCommonErrorCodes.USER_AUTHENTICATION_FAILED
			        .getMessage());
			LOGGER.info("Authentication Exception Occured", throwable);
			return Response.status(Status.UNAUTHORIZED).entity(errorDto)
			        .build();
		} else if (throwable instanceof NotFoundException) {
			/**
			 * Exception handler for 503 - Service Unavailable Exception will be
			 * returned with HTTP Status Code 503
			 */
			errorDto.setErrorCode(GalaxyCommonErrorCodes.SERVICE_UNAVAILABLE
			        .getCode().toString());
			errorDto.setErrorMessage(GalaxyCommonErrorCodes.SERVICE_UNAVAILABLE
			        .getMessage());
			LOGGER.error("SERVICE_UNAVAILABLE", throwable);
			return Response.status(Status.SERVICE_UNAVAILABLE).entity(errorDto)
			        .build();
		} else if (throwable instanceof GalaxyRestException) {
			/**
			 * Exception handler for 400 - Bad Request Business Exception will
			 * be returned with HTTP Status Code 503
			 */
			errorDto.setErrorMessage(((GalaxyRestException)throwable)
			        .getMessage());
			errorDto.setErrorCode(((GalaxyRestException)throwable).getCode()
			        .toString());
			LOGGER.info("GalaxyRestException Occured", throwable);
			return Response.status(Status.BAD_REQUEST).entity(errorDto).build();
		} else if (throwable instanceof InvalidFormatException) {
			/**
			 * Exception handler for 400 - Bad Request Business Exception will
			 * be returned with HTTP Status Code 503
			 */
			InvalidFormatException invalidFormatException = (InvalidFormatException)throwable;
			errorDto.setErrorCode(INVALID_DATA_SPECIFIED.getCode().toString());
			errorDto.setErrorMessage(invalidFormatException.getValue()
			        + INVALID_DATA_SPECIFIED.getMessage());
			LOGGER.error("InvalidFormatException Occured", throwable);
			return Response.status(Status.BAD_REQUEST).entity(errorDto).build();
		} else if (throwable instanceof JsonMappingException) {
			/**
			 * Exception handler for 400 - Bad Request Business Exception will
			 * be returned with HTTP Status Code 503
			 */
			JsonParseException jsonParseException = (JsonParseException)throwable
			        .getCause();
			errorDto.setErrorCode(INVALID_DATA_SPECIFIED.getCode().toString());

			errorDto.setErrorMessage(jsonParseException.getOriginalMessage());
			LOGGER.error("JsonMappingException Occured", throwable);
			return Response.status(Status.BAD_REQUEST).entity(errorDto).build();
		} else if (throwable instanceof JsonParseException) {
			/**
			 * Exception handler for 400 - Bad Request Business Exception will
			 * be returned with HTTP Status Code 503
			 */
			JsonParseException jsonParseException = (JsonParseException)throwable;
			errorDto.setErrorCode(INVALID_DATA_SPECIFIED.getCode().toString());

			errorDto.setErrorMessage(jsonParseException.getOriginalMessage());
			LOGGER.error("JsonParseException Occured", throwable);
			return Response.status(Status.BAD_REQUEST).entity(errorDto).build();
		} else {
			/**
			 * Exception handler for 500 - Internal Server Error will be
			 * returned with HTTP Status Code 500
			 */
			errorDto.setErrorCode(GalaxyCommonErrorCodes.GENERAL_EXCEPTION
			        .getCode().toString());
			errorDto.setErrorMessage(GalaxyCommonErrorCodes.GENERAL_EXCEPTION
			        .getMessage());
			LOGGER.error("Uncaught Exception Occured", throwable);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
			        .entity(errorDto).build();

		}
	}
}
