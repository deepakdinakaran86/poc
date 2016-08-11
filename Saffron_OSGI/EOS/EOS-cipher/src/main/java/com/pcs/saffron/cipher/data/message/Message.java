/**
 * 
 */
package com.pcs.saffron.cipher.data.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.extension.AlarmExtension;
import com.pcs.saffron.cipher.data.point.extension.OutOfRangeAlarm;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;

/**
 * @author PCSEG171
 * Bean class that stores all the data from the raw message sent by the device.
 */
public class Message implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5414407330234075834L;
	private List<Point> points = new ArrayList<Point>();
	private Object rawData;
	private Object sourceId;
	private Date receivedTime;
	private String reason;
	private Long time;


	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void addPoint(Point point){
		if(this.points == null || this.points.isEmpty()){
			this.points = new ArrayList<Point>();
		}
		this.points.add(point);		
	}

	public Object getRawData() {
		return rawData;
	}

	public void setRawData(Object rawData) {
		this.rawData = rawData;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long messageTime) {
		this.time = messageTime;
	}
	public Object getSourceId() {
		return sourceId;
	}
	public void setSourceId(Object sourceId) {
		this.sourceId = sourceId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("{\"sourceId\":\"").append(sourceId).append("\",");//"sourceId":"231312312"
		messageBuilder.append("\"time\":\"").append(time).append("\",");
		messageBuilder.append("\"receivedTime\":\"").append(receivedTime.getTime()).append("\",");
		if(!CollectionUtils.isEmpty(points)){
			messageBuilder.append("\"points\":[");//points information
			for (Point point : points) {
				messageBuilder.append("{\"pointId\":\"").append(point.getPointId()).append("\",");
				messageBuilder.append("\"pointName\":\"").append(point.getPointName()).append("\",");
				messageBuilder.append("\"displayName\":\"").append(point.getDisplayName()).append("\",");
				messageBuilder.append("\"className\":\"").append(point.getClassName()).append("\",");
				messageBuilder.append("\"systemTag\":\"").append(point.getSystemTag()).append("\",");
				messageBuilder.append("\"physicalQuantity\":\"").append(point.getPhysicalQuantity()).append("\",");
				messageBuilder.append("\"unit\":\"").append(point.getUnit()).append("\",");
				if (point.getData() instanceof String) {
					messageBuilder.append("\"data\":\"").append(point.getData()).append("\",");
				}else{
					messageBuilder.append("\"data\":").append(point.getData()).append(",");
				}
				messageBuilder.append("\"status\":\"").append(point.getStatus()).append("\",");
				messageBuilder.append("\"type\":\"").append(point.getType()).append("\",");


				messageBuilder.append("\"extensions\":").append("[");
				if(!CollectionUtils.isEmpty(point.getExtensions())){
					for (PointExtension extension : point.getExtensions()) {
						messageBuilder.append("{\"extensionName\":\"").append(extension.getExtensionName()).append("\",");
						messageBuilder.append("\"extensionType\":\"").append(extension.getExtensionType()).append("\"},");
					}
					messageBuilder.deleteCharAt(messageBuilder.length()-1);
				}
				messageBuilder.append("],");

				messageBuilder.append("\"alarmExtensions\":").append("[");
				if(!CollectionUtils.isEmpty(point.getAlarmExtensions())){

					for (AlarmExtension alarmExtension : point.getAlarmExtensions()) {
						messageBuilder.append("{\"extensionName\":\"").append(alarmExtension.getExtensionName()).append("\",");
						messageBuilder.append("\"extensionType\":\"").append(alarmExtension.getExtensionType()).append("\",");
						messageBuilder.append("\"state\":\"").append(alarmExtension.getState()).append("\",");
						messageBuilder.append("\"type\":\"").append(alarmExtension.getType()).append("\",");

						if (alarmExtension instanceof OutOfRangeAlarm) {
							messageBuilder.append("\"lowerThreshold\":\"").append(alarmExtension.getLowerThreshold()).append("\",");
							messageBuilder.append("\"lowerThresholdAlarmMessage\":\"").append(alarmExtension.getLowerThresholdAlarmMessage()).append("\",");
							messageBuilder.append("\"lowerThresholdNormalMessage\":\"").append(alarmExtension.getLowerThresholdNormalMessage()).append("\",");
							messageBuilder.append("\"upperThreshold\":\"").append(alarmExtension.getUpperThreshold()).append("\",");
							messageBuilder.append("\"upperThresholdAlarmMessage\":\"").append(alarmExtension.getUpperThresholdAlarmMessage()).append("\",");
							messageBuilder.append("\"upperThresholdNormalMessage\":\"").append(alarmExtension.getUpperThresholdNormalMessage()).append("\",");
						}
						messageBuilder.append("\"alarmMessage\":\"").append(alarmExtension.getAlarmMessage()).append("\",");
						messageBuilder.append("\"criticality\":\"").append(alarmExtension.getCriticality()).append("\",");
						messageBuilder.append("\"normalMessage\":\"").append(alarmExtension.getNormalMessage()).append("\"},");
					}
					messageBuilder.deleteCharAt(messageBuilder.length()-1);
				}
				messageBuilder.append("]").append("},");
			}
			messageBuilder.deleteCharAt(messageBuilder.length()-1);
			messageBuilder.append("]").append("}");//points information completion
		}else{
			messageBuilder.append("\"points\":[]}");
		}

		return messageBuilder.toString();
	}
}