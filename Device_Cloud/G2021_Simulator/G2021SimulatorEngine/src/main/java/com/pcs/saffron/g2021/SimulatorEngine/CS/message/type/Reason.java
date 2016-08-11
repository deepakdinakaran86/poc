package com.pcs.saffron.g2021.SimulatorEngine.CS.message.type;

public enum Reason {

	POWER_UP(0,"POWER UP"),
	REBOOT(1,"REBOOT"),
	RESUME_FROM_LOW_POWER(2,"RESUME FROM LOW POWER"),
	APP_CHANGED(3,"APPLICATION CHANGED"),
	WATCHDOG_RESET(4,"WATCHDOG RESET"),
	SESSION_TERMINATED_DATA_SERVER(5,"SESSION TERMINATED BY THE DATA SERVER"),
	CREDIT_EXPIRED(6,"CREDIT EXPIRED"),
	PANIC_INCIDENT(7,"PANIC INCIDENT"),
	SERVICE_AUTHORIZED(8,"SERVICE AUTHORIZED");
	
	
	private Integer type;
	private String desc;
	private static final Reason[] values = values();

	Reason(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Reason valueOfType(Integer type) {
		if (type == null) {return null;}
		for (Reason reason : values) {
			if (type.equals(reason.getType()))
			{
				return reason;
			}
		}
		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
