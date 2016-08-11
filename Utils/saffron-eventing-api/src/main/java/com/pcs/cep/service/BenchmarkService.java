package com.pcs.cep.service;

import static com.pcs.cep.doc.constants.ValueConstansts.SPACE;
import static com.pcs.cep.doc.enums.CEPDataFields.COMPARE_VALUE;
import static com.pcs.cep.doc.enums.CEPDataFields.MAX_VALUE;
import static com.pcs.cep.doc.enums.CEPDataFields.MIN_VALUE;
import static com.pcs.cep.doc.enums.CEPDataFields.PARAMETER;
import static com.pcs.cep.doc.enums.CEPDataFields.SOURCE_ID;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.CUSTOM_ERROR;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.enums.Status.SUCCESS;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.cep.doc.dto.BenchMarkDTO;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.galaxy.rest.client.BaseClient;

/**
 * @author pcseg199
 *
 */
@Service
public class BenchmarkService {

	@Autowired
	@Qualifier("esbClient")
	private BaseClient client;

	private static final String RANGE = "range";

	private static final String HIGH = "high";

	private static final String LOW = "low";

	private static final String COMPARE = "compare";

	public StatusMessageDTO setRangeBenchmark(BenchMarkDTO benchMark) {
		validateMandatoryFields(benchMark, SOURCE_ID, MAX_VALUE, MIN_VALUE,
				PARAMETER);
		return setThresholdToCEP(benchMark, RANGE);
	}

	public StatusMessageDTO setHighBenchmark(BenchMarkDTO benchMark) {
		validateMandatoryFields(benchMark, SOURCE_ID, MAX_VALUE, PARAMETER);
		return setThresholdToCEP(benchMark, HIGH);
	}

	public StatusMessageDTO setLowBenchmark(BenchMarkDTO benchMark) {
		validateMandatoryFields(benchMark, SOURCE_ID, MIN_VALUE, PARAMETER);
		return setThresholdToCEP(benchMark, LOW);
	}

	public StatusMessageDTO setCompareValue(BenchMarkDTO benchMark) {
		validateMandatoryFields(benchMark, SOURCE_ID, COMPARE_VALUE, PARAMETER);
		return setThresholdToCEP(benchMark, COMPARE);
	}

	private StatusMessageDTO setThresholdToCEP(BenchMarkDTO benchMark,
			String url) {

		if (benchMark.getParameter().contains(SPACE)) {
			throw new DeviceCloudException(CUSTOM_ERROR,
					"Parameter Name Cannot contain space");
		}
		//Object post = null;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			//post = 
			client.post(url, headers, benchMark, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO to check for exception
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}
}
