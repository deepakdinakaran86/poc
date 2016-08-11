package com.pcs.saffron.g2021.SimulatorEngine.DS.schedular;

import java.io.IOException;
import java.util.TimerTask;

import com.pcs.saffron.g2021.SimulatorEngine.CS.listener.ServerMessageListener;
import com.pcs.saffron.g2021.SimulatorEngine.CS.serverMessageHandler.ServerMessageNotifier;
import com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient.TCPClient;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.LockUtil;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DataServerProcessor.DataServerProcessor;
import com.pcs.saffron.g2021.SimulatorEngine.DS.eventQueue.EventQueueData;
import com.pcs.saffron.g2021.SimulatorEngine.DS.eventQueue.EventQueueDispatcher;

public class DataPublisher implements Runnable,ServerMessageListener  {


	private EventQueueDispatcher dispatcher = EventQueueDispatcher.getInstance();	
	
	public static Integer seqNumber = 0;
	
	public DataPublisher() {
		ServerMessageNotifier.getInstance().addListener(this);
	}
	
	public void run() {
		synchronized (LockUtil.LOCK) {
			//System.out.println("Data publisher iterating through the queue..");				
			startInitialStep();						
		}
	}

	private void startInitialStep() {

		if(dispatcher.getData() != null && dispatcher.getData().size() >0){	
			//System.out.println("size of the queue.. "+dispatcher.getData().size());
			EventQueueData data = dispatcher.getData().peek();
			startProcess1(data);	
		}else{
			System.out.println("No messages in the queue to process...");
		}

	}

	private void startProcess1(EventQueueData data) {

		if(data != null){		
			if(!checkRetrialExceeds(data)){
				if(sendData(data.getDataBuffer())){
					System.out.println("message delivered successfully..");
					startInitialStep();
				}else{
					System.out.println("Came for retrail task...");
					data.setRetrialCount(data.getRetrialCount()+1);
					startProcess1( data);
				}
			}else{
				System.out.println("Retrails exceeded...processing next message..");
				dispatcher.getData().poll();
				startInitialStep();
			}
		}

	}

	private boolean checkRetrialExceeds(EventQueueData data){
		if(data.getRetrialCount() > DataServerProcessor.retrailCount){		
			return true;
		}		
		return false;
	}

	private boolean sendData(byte[] byteData){		

		int seqNo = ConversionUtils.getSequenceNoFromBuffer(byteData);
		ServerMessageNotifier.getInstance().notifyDataServerRequests(seqNo, byteData);		
		try {
			System.out.println("requested bytebuffer "+ConversionUtils.getHex(byteData));
			ServerMessageNotifier.getInstance().notifyDataServerRequests(ConversionUtils.getSequenceNoFromBuffer(byteData), byteData);
			TCPClient.sendMessageToServer(byteData);
			LockUtil.LOCK.wait(DataServerProcessor.retrailTime);
			//System.out.println("request buffer seq no "+seqNo+" server seq no "+seqNumber.intValue());
			if(seqNumber.intValue() == seqNo){
				dispatcher.removeOnAck();
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Network down...");
			e.printStackTrace();
		}

		return false;
	}

	

	public void notifyAcknowledgement(String message, byte[] serverResponse) {
		//System.out.println("message arrived in notigfier "+ConversionUtils.getHex(serverResponse));
		seqNumber = ConversionUtils.getSequenceNoFromBuffer(serverResponse);		
	}

	public void notifyDataServerRequests(Integer seqNo, byte[] dsServerReq) {

	}


}
