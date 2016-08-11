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
package com.pcs.galaxy.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.galaxy.dto.GeofenceAlarmConfigDTO;
import com.pcs.galaxy.services.GeofenceAlarmConfigService;

/**
 * GeofenceAlarmConfigResource
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Path("/geofence")
@Component
public final class GeofenceAlarmConfigResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeofenceAlarmConfigResource.class);

	@Autowired
	GeofenceAlarmConfigService geofenceAlarmConfig;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveGeoFenceConf(GeofenceAlarmConfigDTO geofence) {
		LOGGER.info("Enter into saveGeoFenceConf");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		GeofenceAlarmConfigDTO saveGeoFenceConf = null;
		saveGeoFenceConf = geofenceAlarmConfig.saveGeoFenceConf(geofence);
		responseBuilder.entity(saveGeoFenceConf);
		return responseBuilder.build();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updteGeoFenceConf(GeofenceAlarmConfigDTO geofence) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geofenceAlarmConfig.updteGeoFenceConf(geofence));
		return responseBuilder.build();
	}

	@DELETE
	@Path("/{configId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGeoFenceConf(@PathParam("configId") String configId) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder
		        .entity(geofenceAlarmConfig.deleteGeoFenceConf(configId));
		return responseBuilder.build();
	}

	@DELETE
	@Path("/device/{sourceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGeoFenceOfDevice(
	        @PathParam("sourceId") String sourceId) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geofenceAlarmConfig
		        .deleteGeoFenceOfDevice(sourceId));
		return responseBuilder.build();
	}

	@DELETE
	@Path("/asset/{assetName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGeoFenceOfAsset(
	        @PathParam("assetName") String assetName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geofenceAlarmConfig
		        .deleteGeoFenceOfAsset(assetName));
		return responseBuilder.build();
	}

	@GET
	@Path("/device/{sourceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGeoFenceConfForDevice(
	        @PathParam("sourceId") String sourceId) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<GeofenceAlarmConfigDTO> geoConf = geofenceAlarmConfig
		        .getGeoFenceConfForDevice(sourceId);
		GenericEntity<List<GeofenceAlarmConfigDTO>> geoConfGE = new GenericEntity<List<GeofenceAlarmConfigDTO>>(
		        geoConf) {
		};
		responseBuilder.entity(geoConfGE);
		return responseBuilder.build();
	}

	@GET
	@Path("/asset/{assetName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGeoFenceConfForAsset(
	        @PathParam("assetName") String assetName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<GeofenceAlarmConfigDTO> geoConf = geofenceAlarmConfig
		        .getGeoFenceConfForAsset(assetName);
		GenericEntity<List<GeofenceAlarmConfigDTO>> geoConfGE = new GenericEntity<List<GeofenceAlarmConfigDTO>>(
		        geoConf) {
		};
		responseBuilder.entity(geoConfGE);
		return responseBuilder.build();
	}

}
