package com.pcs.saffron.deviceutil.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.pcs.saffron.deviceutil.api.configuration.ConfigurationService;
import com.pcs.saffron.deviceutil.api.history.DeviceHistoryService;
import com.pcs.saffron.deviceutil.bean.ConfigPoint;
import com.pcs.saffron.deviceutil.bean.ConfigurationBean;
import com.pcs.saffron.deviceutil.bean.HistoryRequestBean;
import com.pcs.saffron.deviceutil.bean.HistoryResponseBean;
import com.pcs.saffron.deviceutil.bean.PointDataBean;
import com.pcs.saffron.deviceutil.bean.PointHistoryBean;
import com.pcs.saffron.deviceutil.config.ManagerConfiguration;


public class ConfigurationFetcher {

	
	public static void main(String[] args) throws Exception {
		
		
		
		String path = ManagerConfiguration.getProperty(ManagerConfiguration.SOURCESTORE);
		FileReader fileReader = new FileReader(new File(path));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String sourceIds = bufferedReader.readLine();
		String dateRange = bufferedReader.readLine();
		String startDate = dateRange.split("-")[0]+" 00:00:00";
		String endDate = dateRange.split("-")[1]+ " 23:59:59";
		String writerPath = path.substring(0,(path.lastIndexOf(File.separatorChar)+1))+File.separatorChar+"Data";
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		
		
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(dateFormat.parse(startDate));
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(dateFormat.parse(endDate));
		
		
		for (int i = 0; i < sourceIds.split(",").length; i++) {
			ConfigurationService configurationService = new ConfigurationService();
			String sourceId = sourceIds.split(",")[i];
			ConfigurationBean configuration = configurationService.getConfiguration(sourceId);
			File dataDir = new File(writerPath);
			if(!dataDir.exists() || !dataDir.isDirectory()){
				if(!dataDir.mkdir()){
					bufferedReader.close();
					bufferedReader = null;
					return;
				}
			}
			String sourcePath = writerPath+File.separatorChar+sourceId;
			new File(sourcePath).mkdir();
			List<String> customTags = new ArrayList<String>();
			for (ConfigPoint point : configuration.getConfigPoints()) {
				customTags.add(point.getDisplayName());
			}
			HistoryRequestBean bean = new HistoryRequestBean();
			bean.setSourceId(sourceId);
			bean.setStartDate(startTime.getTimeInMillis());
			bean.setEndDate(endTime.getTimeInMillis());
			bean.setCustomTags(customTags);
			
			DeviceHistoryService deviceHistoryService = new DeviceHistoryService();
			HistoryResponseBean history = deviceHistoryService.getHistory(bean);
			if(history != null){
				
				for (PointDataBean pointData : history.getCustomTags()) {
					pointData.setCustomTag(pointData.getCustomTag().replaceAll("[^a-zA-Z0-9.-]", "_"));
					FileWriter fileWriter = new FileWriter(new File(sourcePath+File.separatorChar+pointData.getCustomTag()+".csv"));
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					bufferedWriter.write("deviceTime,data \n");
					for (PointHistoryBean pointHistory : pointData.getValues()) {
						Calendar deviceTime = Calendar.getInstance();
						/*Calendar insertTime = Calendar.getInstance();
						insertTime.setTimeInMillis(pointHistory.getInsertTime());*/
						deviceTime.setTimeInMillis(pointHistory.getDeviceTime());
						bufferedWriter.write(dateFormat.format(deviceTime.getTime())+","+pointHistory.getData()+"\n");//","+dateFormat.format(insertTime.getTime())+
					}
					bufferedWriter.flush();
					bufferedWriter.close();
					bufferedWriter = null;
				}
			}
		}
		
		bufferedReader.close();
		bufferedReader = null;
		
		System.exit(0);
	}
}
