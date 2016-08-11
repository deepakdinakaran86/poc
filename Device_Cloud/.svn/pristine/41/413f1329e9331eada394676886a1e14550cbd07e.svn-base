package com.pcs.saffron.g2021.SimulatorUI;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.app.AppEngineImpl;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.PointsDiscoveryMesasge;
import com.pcs.saffron.g2021.SimulatorEngine.CS.session.SessionInfo;
import com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient.TCPClient;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.LockUtil;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ServerResponseStatus;

public class EDCPHandShaking {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulatorGUI.class);
	
	public boolean startHandShakingMechannism() throws InterruptedException{

		boolean helloAckReceived = processHelloMessage();	

		if(helloAckReceived){
			boolean challengeProcessed = processChallengeMessage();
			if(challengeProcessed){
				//System.out.println("send device authentication....");
				if(deviceAuthentication()){
					//System.out.println("proceed to point discovery");
					if(pointDiscoveryProcess()){
						//	System.out.println("send score card");
						if(processScoreCard()){
							System.out.println("ready to establish data server conncetion...");

							// open the connection with data server
							
							return true;
						}
					}else{
						System.out.println("pointDiscoveryProcess has failed...");
						return false;
					}
				}
			}
		}

		/*if (!SimulatorGUI.notifyCheck) {
			return false;
			LOGGER.error("Server is not reachable.. ");
			SimulatorGUI.stopCsServer();
		}	*/
		return false;

	}

	private boolean processHelloMessage(){
		SimulatorGUI.notifyCheck = false;
		byte[] helloResponse = SimulatorGUI.getHelloMsg();

		if (helloResponse != null) {

			SimulatorGUI.txMsgEDCP.setText(ConversionUtils.getHex(helloResponse).toString());
			try {
				TCPClient.sendMessageToServer(helloResponse);
				SimulatorGUI.updateSequenceNo();
				SimulatorGUI.txCurrentState.setText(ServerResponseStatus.HELLO_ACK);


				int i = 0;								
				while (i < SimulatorGUI.retrialCount) {	
					synchronized (LockUtil.LOCK) {
						Thread.sleep(SimulatorGUI.retrailTime);
						LOGGER.info("Thread sleeping for hello acknowlegement........ ");
						if (SimulatorGUI.notifictionMessage != null && SimulatorGUI.notifictionMessage.equals(ServerResponseStatus.HELLO_ACK)) {
							SimulatorGUI.notifictionMessage = null;
							SimulatorGUI.txServerResponse.setText(ConversionUtils.getHex(SimulatorGUI.csServerResponse).toString());
							//LOGGER.info("Hello Acknowledgement received in main after thread sleep ");
							SimulatorGUI.notifyCheck = true;	
							LOGGER.info("Waiting for challenge mesasge... ");		
							LockUtil.LOCK.wait(SimulatorGUI.retrailTime);
							return true;

						}else{
							LOGGER.info("Retrying HELLO message in processHelloMessage");
							SimulatorGUI.updateSequenceNo();
							TCPClient.sendMessageToServer(helloResponse);									
							SimulatorGUI.txCurrentState.setText(ServerResponseStatus.HELLO_ACK);
						}

					}				
					i++;
				}	

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		return false;
	}

	private boolean processChallengeMessage() {	

		try {			
			SimulatorGUI.notifyCheck = false;			
			if (SimulatorGUI.notifictionMessage != null && SimulatorGUI.notifictionMessage.equals(ServerResponseStatus.CHALLENE_MSG)) {
				SimulatorGUI.notifictionMessage = null;
				SimulatorGUI.notifyCheck = true;
				//LOGGER.info("Challenge message received from notifier...thread notified ");					
				SimulatorGUI.txCurrentState.setText(ServerResponseStatus.CHALLENE_MSG);
				SimulatorGUI.txServerResponse.setText(ConversionUtils.getHex(SimulatorGUI.csServerResponse).toString());
				SimulatorGUI.txSessionId.setText(String.valueOf(SessionInfo.getInstance().getSessionId()));
				SimulatorGUI.txUnitId.setText(String.valueOf(SessionInfo.getInstance().getUnitId()));
				return true;
			} else {
				LOGGER.info("Retrying HELLO message processChallengeMessage");
				startHandShakingMechannism();
			}		

		} catch (Exception e) {
			e.printStackTrace();
		}		

		return false;
	}

	private boolean deviceAuthentication(){
		boolean deviceAuthenticateReceived = processDeviceAuthenticateMessage();
		if(deviceAuthenticateReceived){
			boolean welcomeProcessed = processWelcomeMessage();
			if(welcomeProcessed){
				//System.out.println("welcome prcessed succesfully...");
				return true;
			}else{
				System.out.println("welcome prcess failed...");
			}
		}
		return false;
	}

	private boolean processDeviceAuthenticateMessage(){


		byte[] authenticateResponse = AppEngineImpl.getDeviceAuthenticate();
		SimulatorGUI.notifyCheck = false;
		if (authenticateResponse != null) {

			SimulatorGUI.txMsgEDCP.setText(ConversionUtils.getHex(authenticateResponse).toString());
			try {
				TCPClient.sendMessageToServer(authenticateResponse);
				SimulatorGUI.updateSequenceNo();
				SimulatorGUI.txCurrentState.setText(ServerResponseStatus.AUTHENTICATE);


				int j = 0;								
				while (j < SimulatorGUI.retrialCount) {	
					synchronized (LockUtil.LOCK) {
						//LOGGER.info("Thread sleeping for Authenticate acknowlegement........ ");
						Thread.sleep(SimulatorGUI.retrailTime);						
						if (SimulatorGUI.notifictionMessage != null && SimulatorGUI.notifictionMessage.equals(ServerResponseStatus.AUTHENTICATE)) {
							SimulatorGUI.notifictionMessage = null;
							SimulatorGUI.txServerResponse.setText(ConversionUtils.getHex(SimulatorGUI.csServerResponse).toString());
							//LOGGER.info("Authenticate Acknowledgement received in main after thread sleep ");
							SimulatorGUI.notifyCheck = true;	
							LockUtil.LOCK.wait(SimulatorGUI.retrailTime);
							return true;

						}else{
							LOGGER.info("Retrying Authenticate message");
							SimulatorGUI.updateSequenceNo();
							TCPClient.sendMessageToServer(authenticateResponse);									
							SimulatorGUI.txCurrentState.setText(ServerResponseStatus.AUTHENTICATE);
						}

					}				
					j++;
				}	

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return false;

	}

	private boolean processWelcomeMessage() {		
		try {			
			SimulatorGUI.notifyCheck = false;
			//LOGGER.info("Waiting for Welcome mesasge... ");			
			if (SimulatorGUI.notifictionMessage != null && SimulatorGUI.notifictionMessage.equals(ServerResponseStatus.WELCOME)) {
				SimulatorGUI.notifictionMessage = null;
				SimulatorGUI.notifyCheck = true;
				//LOGGER.info("Welcome message received from notifier...thread notified ");					
				SimulatorGUI.txCurrentState.setText(ServerResponseStatus.WELCOME);
				SimulatorGUI.txServerResponse.setText(ConversionUtils.getHex(SimulatorGUI.csServerResponse).toString());
				SimulatorGUI.txSessionId.setText(String.valueOf(SessionInfo.getInstance().getSessionId()));
				SimulatorGUI.txUnitId.setText(String.valueOf(SessionInfo.getInstance().getUnitId()));
				return true;
			} else {
				LOGGER.info("Retrying Authenticate message");
				deviceAuthentication();
			}		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean pointDiscoveryProcess(){
		SimulatorGUI.notifyCheck = false;
		if(SimulatorGUI.fileToBeProcessed){
			//pointDiscovry = AppEngineImpl.readAndConvertJson();
			if(SimulatorGUI.pointDiscovry != null){
				//System.out.println("came to point discovery");
				try {
					return processPoints(SimulatorGUI.pointDiscovry);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Network down...");
					e.printStackTrace();
				}
			}
		}
		return false;		
	}


	private boolean processPoints(PointsDiscoveryMesasge pointsInfo) throws InterruptedException, IOException{
		//System.out.println("processing points..");
		SimulatorGUI.txCurrentState.setText(ServerResponseStatus.POINTDISCOVERYRESPONSE);
		if(pointsInfo != null){														 
			Points[] points = pointsInfo.getPoints();
			int limit = 5;
			int pointsLength = points.length;
			int iterations = pointsLength / limit;
			int remaining = pointsLength % limit;													
			int startPosition = 0,endPosition = 0;
			if(limit < pointsLength)
				endPosition = limit;

			synchronized (LockUtil.LOCK) {
				while(iterations >0){
					byte[] pointsByteMsg = AppEngineImpl.getPointDiscoveryResponse(points, startPosition, endPosition);
					//System.out.println("pointsByteMsg in iteration 1 "+pointsByteMsg);
					if(!sendPointMsgToCS(pointsByteMsg))					
						break;

					iterations--;
					if(iterations > 0){
						startPosition += limit;
						endPosition += limit;
					}

				}
				if(remaining > 0){					
					byte[] pointsByteMsg = AppEngineImpl.getPointDiscoveryResponse(points, startPosition, endPosition+remaining);
					sendPointMsgToCS(pointsByteMsg);				
				}

				if(SimulatorGUI.notifyCheck){
					System.out.println("processed successfully....total sync points "+SimulatorGUI.pointsSync);	
					return true;
				}

			}	

		}
		return false;
	}

	private boolean sendPointMsgToCS(byte[] pointsByteMsg) throws InterruptedException, IOException{
		if (pointsByteMsg != null) {
			SimulatorGUI.txMsgEDCP.setText(ConversionUtils.getHex(pointsByteMsg).toString());
			SimulatorGUI.updateSequenceNo();
			TCPClient.sendMessageToServer(pointsByteMsg);			
			SimulatorGUI.notifyCheck = false;
			int k = 0;
			while (k < SimulatorGUI.retrialCount) {																	
				Thread.sleep(SimulatorGUI.retrailTime);
				//LOGGER.info("Thread sleeping for Point Discovery acknowlegement........ ");
				if (SimulatorGUI.notifictionMessage != null && SimulatorGUI.notifictionMessage.equals(ServerResponseStatus.POINTDISCOVERYRESPONSE)) {
					SimulatorGUI.txServerResponse.setText(ConversionUtils.getHex(SimulatorGUI.csServerResponse).toString());
					//	LOGGER.info("POINTDISCOVERYRESPONSE Acknowledgement received in main after thread sleep ");
					SimulatorGUI.notifictionMessage = null;
					SimulatorGUI.notifyCheck = true;	
					SimulatorGUI.txCurrentState.setText(ServerResponseStatus.POINTDISCOVERYRESPONSE);					
					LockUtil.LOCK.wait(SimulatorGUI.retrailTime);
					SimulatorGUI.pointsSync = SessionInfo.getInstance().getScoreCard();
					//	System.out.println("sync points iterations "+pointsSync);
					return true;
				}else{
					LOGGER.info("Retrying Device POINTDISCOVERYRESPONSE...");
					SimulatorGUI.txMsgEDCP.setText(ConversionUtils.getHex(pointsByteMsg).toString());
					SimulatorGUI.updateSequenceNo();
					TCPClient.sendMessageToServer(pointsByteMsg);
				}
				k++;
			}	

		}
		return false;
	}

	private boolean processScoreCard(){
		SimulatorGUI.notifyCheck = false;
		byte[] pointScoreByte = AppEngineImpl.getPointDiscoveryScordCard(SimulatorGUI.pointsSync,1);

		if (pointScoreByte != null) {

			SimulatorGUI.txMsgEDCP.setText(ConversionUtils.getHex(pointScoreByte).toString());
			try {
				SimulatorGUI.updateSequenceNo();	
				TCPClient.sendMessageToServer(pointScoreByte);

				int n = 0;								
				while (n < SimulatorGUI.retrialCount) {	
					synchronized (LockUtil.LOCK) {
						Thread.sleep(SimulatorGUI.retrailTime);
						//LOGGER.info("Thread sleeping for POINTDISCOVERYSCORECARD acknowlegement........ ");
						if (SimulatorGUI.notifictionMessage != null && SimulatorGUI.notifictionMessage.equals(ServerResponseStatus.POINTDISCOVERYSCORECARD)) {
							SimulatorGUI.notifictionMessage = null;
							SimulatorGUI.txCurrentState.setText(ServerResponseStatus.POINTDISCOVERYSCORECARD);
							SimulatorGUI.txServerResponse.setText(ConversionUtils.getHex(SimulatorGUI.csServerResponse).toString());
							//LOGGER.info("POINTDISCOVERYSCORECARD Acknowledgement received in main after thread sleep ");
							SimulatorGUI.notifyCheck = true;	
							return true;

						}else{
							LOGGER.info("Retrying POINTDISCOVERYSCORECARD message");
							SimulatorGUI.updateSequenceNo();
							TCPClient.sendMessageToServer(pointScoreByte);									
							SimulatorGUI.txCurrentState.setText(ServerResponseStatus.POINTDISCOVERYSCORECARD);
						}

					}				
					n++;
				}	

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Network down...");
				e.printStackTrace();
			}			
		}
		return false;

	}
}
