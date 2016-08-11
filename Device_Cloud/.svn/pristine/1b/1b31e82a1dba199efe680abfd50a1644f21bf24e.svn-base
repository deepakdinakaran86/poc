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
package com.pcs.alarm.process;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;
import org.apache.flink.streaming.connectors.kafka.KafkaSink;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.alarm.beans.AlarmBean;
import com.pcs.alarm.beans.AlarmDefinition;
import com.pcs.alarm.beans.AlarmDefinitionPoint;
import com.pcs.alarm.enums.AlarmStatus;
import com.pcs.alarm.flink.schema.AlarmDataSchema;
import com.pcs.alarm.flink.schema.AlarmMessageSchema;
import com.pcs.alarm.helper.AlarmProcessHelper;
import com.pcs.alarm.helper.ProcessHelper;
import com.pcs.alarm.util.CacheUtil;
import com.pcs.alarm.util.PointUtility;
import com.pcs.analytics.flinktests.beans.DeviceSnapshot;
import com.pcs.analytics.flinktests.beans.Parameter;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.expressions.engine.ExpressionEngineUtil;

/**
 * This class is responsible for consuming flattened device data from a kafka
 * topic
 * 
 * @author pcseg129(Seena Jyothish) Jul 27, 2016
 */
public class AlarmProcessor {
	private static final Logger logger = LoggerFactory
	        .getLogger(AlarmProcessor.class);

	private static CacheProvider cacheProvider;

	private static final String SPACE = " ";

	public static void main(String[] args) throws Exception {

		CacheUtil.setCacheProvider(cacheProvider);

		// create execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment
		        .getExecutionEnvironment();

		// parse user parameters
		ParameterTool parameterTool = ParameterTool.fromArgs(args);

		DataStream<DeviceSnapshot> msgStream = env
		        .addSource(new FlinkKafkaConsumer082<>(parameterTool
		                .getRequired("topic"), new AlarmDataSchema(),
		                parameterTool.getProperties()));

		msgStream.flatMap(new AlarmFinder()).addSink(
		        new KafkaSink<AlarmMessage>(parameterTool
		                .getRequired("bootstrap.servers"), parameterTool
		                .getRequired("alarm.topic"), new AlarmMessageSchema()));
		env.execute();
	}

	private static class AlarmFinder
	        implements
	            FlatMapFunction<DeviceSnapshot, AlarmMessage> {

		private static final long serialVersionUID = 3330570716171645810L;

		@Override
		public void flatMap(DeviceSnapshot bean, Collector<AlarmMessage> out)
		        throws Exception {
			List<AlarmDefinition> alarmDefinitions = getAlarmDefinitions(bean
			        .getSourceId());
			if (alarmDefinitions != null && !alarmDefinitions.isEmpty()) {
				HashMap<String, Object> parameterMap = new HashMap<String, Object>();
				List<Parameter> devicePoints = null;
				devicePoints = bean.getParameters();
				for (Parameter pointData : devicePoints) {
					parameterMap.put(
					        pointData.getDisplayName().replaceAll(SPACE, ""),
					        pointData.getValue());
				}

				for (AlarmDefinition alarmDefinition : alarmDefinitions) {

					String displayNames[] = new String[alarmDefinition
					        .getAlarmDefinitionPoints().size()];
					int j = 0;
					for (AlarmDefinitionPoint definitionPoint : alarmDefinition
					        .getAlarmDefinitionPoints()) {
						displayNames[j] = definitionPoint.getDisplayName();
						j++;
					}

					List<Parameter> alertPoints = PointUtility
					        .getParameterPoints(displayNames,
					                bean.getParameters());

					AlarmBean resultBean = null;
					AlarmProcessHelper alarmProcessHelper = new AlarmProcessHelper();
					AlarmStatus curAlertStatus = AlarmStatus.NOALARM;
					AlarmBean alarmObject = alarmProcessHelper.getAlarmObject(
					        bean.getSourceId(), alarmDefinition.getName());
					String expression = URLDecoder.decode(
					        alarmDefinition.getExpression(), "UTF-8")
					        .replaceAll(System.lineSeparator(), "");
					expression = expression.replaceAll("[\t]+", "");
					expression = expression.replaceAll("[\n]+", "");
					try {
						Object evaluatedValue = new ExpressionEngineUtil()
						        .evaluate(expression, parameterMap);
						String currentValue = alarmProcessHelper
						        .buildAlarmValue(alertPoints);
						AlarmStatus alarmStatus = null;
						Boolean expEval = new Boolean(evaluatedValue.toString()
						        .toLowerCase());
						if (expEval) {
							curAlertStatus = AlarmStatus.ACTIVE;
							alarmStatus = alarmProcessHelper
							        .getAlarmNextStatus(alarmObject,
							                curAlertStatus);
							resultBean = alarmProcessHelper.buildAlarmObject(
							        alarmObject,
							        alarmDefinition.getAlarmMessage(),
							        currentValue, alarmStatus);
						} else {
							alarmStatus = alarmProcessHelper
							        .getAlarmNextStatus(alarmObject,
							                curAlertStatus);
							resultBean = alarmProcessHelper.buildAlarmObject(
							        alarmObject,
							        alarmDefinition.getNormalMessage(),
							        currentValue, alarmStatus);
						}
						if (resultBean != null) {
							alarmObject.setAlarmStatus(alarmStatus);
							alarmProcessHelper.updateAlarmObject(alarmObject);
							AlarmMessage alarmMessage = alarmProcessHelper
							        .setAlarmValues(resultBean, bean,
							                alarmDefinition, alertPoints,
							                expEval);
							if (alarmStatus == AlarmStatus.ACTIVE
							        || alarmStatus == AlarmStatus.RESOLVED) {
								out.collect(alarmMessage);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Error processing alarm for device {}",
						        bean.getSourceId());
					}
				}
			}
		}

		private List<AlarmDefinition> getAlarmDefinitions(String sourceId) {
			List<AlarmDefinition> alarmDefinitions = ProcessHelper
			        .getDeviceAlarmDefinitions(sourceId);
			return alarmDefinitions;
		}
	}

}
