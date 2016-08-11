package com.pcs.fault.monitor.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Message;

import kafka.consumer.KafkaStream;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListener;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;

public class FaultMonitorTester {
	public static void main(String[] args) {

		FaultDataTester faultData = new FaultDataTester();
		//FaultResponseTester faultResponse = new FaultResponseTester(); 
	}
}
