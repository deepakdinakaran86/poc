/**
 * 
 */
package com.pcs.data.analyzer.storm.bolts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;

import javax.ws.rs.core.HttpHeaders;

import backtype.storm.tuple.Tuple;

import com.google.gson.Gson;
import com.pcs.avocado.token.TokenInfoDTO;
import com.pcs.avocado.token.TokenManager;
import com.pcs.data.analyzer.bootstrap.ApplicationConfig;
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.util.AnalyticsUtil;
import com.pcs.data.platform.bean.Domain;
import com.pcs.data.platform.bean.Entity;
import com.pcs.data.platform.bean.EntityTemplate;
import com.pcs.data.platform.bean.GlobalEntity;
import com.pcs.data.platform.bean.HierarchySearch;
import com.pcs.data.platform.bean.IdentityEntity;
import com.pcs.data.platform.bean.KeyValueObject;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.api.configuration.bean.Status;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.authentication.bean.DeviceAuthenticationResponse;
import com.pcs.saffron.manager.enums.MessageType;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 05 Dec 2015
 *
 */
public class AlarmPublishBolt extends BaseBasicBolt{
	
	private static final long serialVersionUID = -3231337741914298058L;

	private static Logger LOGGER = LoggerFactory
	        .getLogger(AlarmPublishBolt.class);
	
	private Publisher publisher;

	private final PublisherConfig publisherConfig;
	
	public AlarmPublishBolt(String publisherConfFilePath) {
		this.publisherConfig = PublisherConfig
		        .getNewInstance(publisherConfFilePath);
	}
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		AlarmMessage alarmMessage = (AlarmMessage)input.getValue(1);
		publishData(alarmMessage.getSourceId().toString(), alarmMessage);
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
	
	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
	        TopologyContext context) {
		this.publisher = new Publisher(publisherConfig);
		super.prepare(stormConf, context);
	}
	
	public void publishData(String sourceId, AlarmMessage alarmMessage) {
		LOGGER.info("publishing alarmMessage {}",alarmMessage.toString());
		if (!StringUtils.isBlank(sourceId)) {
			if (publisher == null) {
				publisher = new Publisher(publisherConfig);
			}
			List<String> contexts = AnalyticsUtil
			        .getDeviceContext("alarm bolt",sourceId);
			if (!contexts.isEmpty()){
				DatasourceDTO datasourceDTO = new DatasourceDTO();
				datasourceDTO.setMessageType(MessageType.ALARM);
				datasourceDTO.setDatasourceName(contexts.get(0));
				datasourceDTO.setStatusMessage(alarmMessage.getAlarmMessage());
				datasourceDTO.setStatus(Status.valueOf(alarmMessage.getStatus().toString().toUpperCase()));
				datasourceDTO.setReceivedTime(alarmMessage.getReceivedTime());
				datasourceDTO.setTime(alarmMessage.getTime());
				for (Point point : alarmMessage.getPoints()) {
					Parameter parameter = new Parameter(point.getDisplayName(),
							point.getType());
					parameter.setValue(point.getData());
					parameter.setTime(alarmMessage.getTime());
					datasourceDTO.addParameter(parameter);
				}
				
				Gson gson = new Gson();
				for (String context : contexts) {
					String publishMsg = gson.toJson(datasourceDTO);
					publisher.publishToJMSTopic(context, publishMsg);
					
				}
			}
		} else {
			LOGGER.error("Unable to publish alarm, empty datasource name");
		}
	}
	

}
