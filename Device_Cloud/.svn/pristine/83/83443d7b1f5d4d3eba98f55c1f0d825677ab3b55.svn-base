/**
 * 
 */
package com.pcs.analytics.util.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.pcs.saffron.manager.api.configuration.bean.Status;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.enums.MessageType;

/**
 * @author pcseg129
 *
 */
public class ModelBuilding {
	public static void main(String[] args) {
		datasourceDTO();
	}

	private static void datasourceDTO(){
		DatasourceDTO d = new  DatasourceDTO();
		d.setDatasourceName("Testdatasource12");
		d.setMessageType(MessageType.ALARM);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1455537158l);
		d.setReceivedTime(cal.getTime());
		d.setStatus(Status.ACTIVE);
		d.setStatusMessage("Engine oil temperature is high");
		d.setTime(1455537158l);
		List<Parameter>parameters = new ArrayList<Parameter>();
		Parameter p1=new Parameter();
		p1.setName("Fuel Level");
		p1.setDataType("Double");
		p1.setTime(1455537158l);
		p1.setUnit("Litre");
		p1.setValue("67.67");
		parameters.add(p1);
		d.setParameters(parameters);
		Gson gson = new Gson();
		System.out.println(gson.toJson(d));
	}
}
