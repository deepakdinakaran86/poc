/**
 * 
 */
package com.pcs.saffron.deviceutil.api.history;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.deviceutil.api.history.request.DeviceHistoryRequest;
import com.pcs.saffron.deviceutil.bean.HistoryRequestBean;
import com.pcs.saffron.deviceutil.bean.HistoryResponseBean;

/**
 * @author pcseg310
 *
 */
public class DeviceHistoryService  {

	private static final Logger logger = LoggerFactory.getLogger(DeviceHistoryService.class);

	public HistoryResponseBean getHistory(HistoryRequestBean historyRequestBean) {
		boolean registered = false;
		if (historyRequestBean == null || StringUtils.isBlank(historyRequestBean.getSourceId())) {
			logger.warn("Could not register device!!Device or device source id must not be null!!");
			return null;
		}

		String sourceId = historyRequestBean.getSourceId();
		DeviceHistoryRequest request = new DeviceHistoryRequest();
		String pathUrl = request.buildRegistrationUrl(sourceId);
		logger.info("Registration request recieved {}",pathUrl);
		Client client = ApacheRestClient.builder().host(request.getHostIp()).port(request.getPort())
				.scheme(request.getScheme()).build();
		logger.info("Registration request after client recieved {}",pathUrl);
		HistoryResponseBean historyResponseBean = null;
		try {
			historyResponseBean = client.post(pathUrl, null, historyRequestBean, HistoryResponseBean.class);
			logger.info("Device {} registration status : {}", sourceId, historyResponseBean);
			registered = true;
		} catch (ClientException e) {
			logger.error("Exception occured while registring a new device [" + sourceId + "]");
			registered = false;
		}
		return historyResponseBean;
	}

}
