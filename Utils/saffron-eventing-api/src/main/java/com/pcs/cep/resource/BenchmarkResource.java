package com.pcs.cep.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.pcs.cep.doc.dto.BenchMarkDTO;
import com.pcs.cep.service.BenchmarkService;

/**
 * Resource for setting threshold values into CEP
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date Nov 2015
 * @since saffron-1.0.0
 */
@Path("/benchmarks")
public class BenchmarkResource {

	@Autowired
	private BenchmarkService benchmarkService;

	@POST
	@Path("/range")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response setRange(BenchMarkDTO benchMark) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(benchmarkService.setRangeBenchmark(benchMark));
		return responseBuilder.build();
	}

	@POST
	@Path("/high")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response setHigh(BenchMarkDTO benchMark) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(benchmarkService.setHighBenchmark(benchMark));
		return responseBuilder.build();
	}

	@POST
	@Path("/low")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response setLow(BenchMarkDTO benchMark) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(benchmarkService.setLowBenchmark(benchMark));
		return responseBuilder.build();
	}

	@POST
	@Path("/compare")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public Response setCompareValue(BenchMarkDTO benchMark) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(benchmarkService.setCompareValue(benchMark));
		return responseBuilder.build();
	}
}
