package com.pcs.util.filemonitor;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitoringService {
	
	public static void main(String[] args) {
		File test = new File("D:\\Monitor");
		FileAlterationObserver alterationObserver= new FileAlterationObserver(test);
		System.err.println(alterationObserver.getDirectory());
		alterationObserver.addListener(new FileModificationHandler());
		long interval = 1000l;
		FileAlterationMonitor alterationMonitor = new FileAlterationMonitor(interval,alterationObserver);
		try {
			alterationMonitor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
