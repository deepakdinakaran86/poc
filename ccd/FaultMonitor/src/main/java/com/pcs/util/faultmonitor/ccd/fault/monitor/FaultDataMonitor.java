package com.pcs.util.faultmonitor.ccd.fault.monitor;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.pcs.util.faultmonitor.ccd.fault.handler.FaultDataHandler;

/**
 * @author pcseg171
 *
 */
public class FaultDataMonitor {
	
	
	private static FileAlterationObserver faultObserver = null;
	private static FileAlterationMonitor faultMonitor = null;
	
	/**
	 * @param file
	 */
	public static final void registerDirectory(File directory,Long syncInterval){
		if( !directory.isDirectory()){
			
		}else{
			faultObserver = new FileAlterationObserver(directory);
			faultObserver.addListener(new FaultDataHandler());
			faultMonitor = new FileAlterationMonitor(syncInterval,faultObserver);
		}
	}
	
	/**
	 * @param file
	 */
	@SuppressWarnings("unused")
	private static final void registerFile(File file){
		//TODO
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public static final void scan() throws Exception{
		if(faultMonitor != null){
			faultMonitor.start();
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public static final void stopScan() throws Exception{
		if(faultMonitor != null){
			faultMonitor.stop();
		}
	}

	
	public static void main(String[] args) throws Exception {
		registerDirectory(new File("D:\\Monitor"), 500l);
		scan();
	}
}
