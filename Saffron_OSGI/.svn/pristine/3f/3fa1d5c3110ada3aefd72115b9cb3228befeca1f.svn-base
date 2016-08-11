package com.pcs.event.scanner.beans;

public class EventfulParameter {

	private String name;
	private Object value;
	private String time;



	public EventfulParameter(){

	}

	public EventfulParameter(String paramName, Object object,String time) {
		this.name = paramName;
		if(object == null)
			this.value = "";
		else
			this.value = object;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if(value == null)
			value = "";
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		EventfulParameter newObj = (EventfulParameter) obj;
		if(newObj.getName().equals(name)){
			return true;
		}else{
			return false;
		}
	}





}
