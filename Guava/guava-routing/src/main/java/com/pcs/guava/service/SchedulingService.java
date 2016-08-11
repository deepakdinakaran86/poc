package com.pcs.guava.service;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.scheduling.SchedulingDTO;

public interface SchedulingService {

	StatusMessageDTO createSchedule(SchedulingDTO schedule);

	SchedulingDTO viewSchedule(String scheduleName);

}
