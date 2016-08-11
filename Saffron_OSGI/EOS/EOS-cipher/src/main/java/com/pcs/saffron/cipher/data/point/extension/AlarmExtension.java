package com.pcs.saffron.cipher.data.point.extension;



public class AlarmExtension extends PointExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = 296588490991598736L;

	private static final String ALARM = "ALARM";

	private String type;
	private String criticality;
	private String state;
	private String alarmMessage;
	private String normalMessage;
	private String upperThresholdAlarmMessage;
	private String lowerThresholdAlarmMessage;
	private String upperThresholdNormalMessage;
	private String lowerThresholdNormalMessage;
	private Float lowerThreshold;
	private Float upperThreshold;
	
	protected AlarmExtension(){
		
	}

	public String getType() {
		return type;
	}
	
	public final void setType(String type) {
		setExtensionType(type);
		this.type = type;
	}
	
	public String getCriticality() {
		return criticality;
	}
	
	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state= state;
	}
	
	public String getAlarmMessage() {
		return alarmMessage;
	}
	
	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}
	
	public String getNormalMessage() {
		return normalMessage;
	}
	
	public void setNormalMessage(String normalMessage) {
		this.normalMessage = normalMessage;
	}
	
	public String getUpperThresholdAlarmMessage() {
		return upperThresholdAlarmMessage;
	}
	
	public void setUpperThresholdAlarmMessage(String upperThresholdAlarmMessage) {
		this.upperThresholdAlarmMessage = upperThresholdAlarmMessage;
	}
	
	public String getLowerThresholdAlarmMessage() {
		return lowerThresholdAlarmMessage;
	}
	
	public void setLowerThresholdAlarmMessage(String lowerThresholdAlarmMessage) {
		this.lowerThresholdAlarmMessage = lowerThresholdAlarmMessage;
	}
	
	public String getUpperThresholdNormalMessage() {
		return upperThresholdNormalMessage;
	}
	
	public void setUpperThresholdNormalMessage(String upperThresholdNormalMessage) {
		this.upperThresholdNormalMessage = upperThresholdNormalMessage;
	}
	
	public String getLowerThresholdNormalMessage() {
		return lowerThresholdNormalMessage;
	}
	
	public void setLowerThresholdNormalMessage(String lowerThresholdNormalMessage) {
		this.lowerThresholdNormalMessage = lowerThresholdNormalMessage;
	}
	
	public Float getLowerThreshold() {
		return lowerThreshold;
	}
	
	public void setLowerThreshold(Float lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
	}
	
	public Float getUpperThreshold() {
		return upperThreshold;
	}
	
	public void setUpperThreshold(Float upperThreshold) {
		this.upperThreshold = upperThreshold;
	}
	

	@Override
	public void setExtensionType(String extensionType) {
		super.setExtensionType(ALARM);
	}
	
	
}
