package com.pcs.guava.service.repository;

import com.pcs.guava.dto.routing.PoiIdentifiersDTO;
import com.pcs.guava.dto.routing.PoiResult;
import com.pcs.guava.dto.scheduling.ScheduleResult;
public interface RoutingRepository {

	PoiResult findRouteChild(String identifier, String poiTemplate,
			String poiType);

	PoiIdentifiersDTO getPoiIdentifiers(String identifier, String poiTemplate,
			String poiType);

	ScheduleResult findScheduleChild(String identifier, String scheduleDestTemplate);

}
