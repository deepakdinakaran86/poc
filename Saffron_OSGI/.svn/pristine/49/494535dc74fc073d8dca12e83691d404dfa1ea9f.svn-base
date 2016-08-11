package com.pcs.saffron.teltonika.simulator.connector.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.tcp.client.TCPClientEx;
import com.pcs.saffron.connectivity.utils.CRC16;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.teltonika.simulator.bean.SimulatorConfig;
import com.pcs.saffron.teltonika.simulator.connector.tcp.handler.TeltonikaSimulatorHandlerEx;

public class TCPSimulator {


	public static Boolean isSimulating = true;
	public static Boolean imeiSent = false;
	public static Boolean authorized = false;
	private static Integer lastReadLine = -1;
	private static Boolean EOFReached = false;

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPSimulator.class);
	public static void startSimulator(SimulatorConfig simulatorConfig) throws Exception{
		TCPClientEx clientEx = new TCPClientEx(simulatorConfig.getServerIP(), simulatorConfig.getServerPort(), null);

		ConnectorConfiguration configuration = new ConnectorConfiguration();
		configuration.setModel("FMXXX");
		configuration.setName("Teltonika-Simulator");
		configuration.setVendor("Teltonika");
		Handler handler = new Handler();
		handler.setChannelHandlerProvider(TeltonikaSimulatorHandlerEx.class.getName(), null);
		handler.setName("Teltonika Simulator - "+simulatorConfig.getImei());

		configuration.addDeviceHandler(handler);
		clientEx.setConfiguration(configuration);
		clientEx.connect();



		while (isSimulating) {

			if(!imeiSent){
				ByteBuf imeiBuf = Unpooled.copyInt(15);
				clientEx.sendMessage(imeiBuf.array());
				clientEx.sendMessage(simulatorConfig.getImei().getBytes());
				imeiBuf = null;
				imeiSent = true;
				Thread.sleep(1000l);
				LOGGER.info("Simulator says: {} sent to server as imei",simulatorConfig.getImei());
			}else if(authorized){
				byte[] message = getMessage(simulatorConfig);
				if(!EOFReached || message != null){
					ByteBuf dataSizeBuf = Unpooled.copyInt(message.length);
					clientEx.sendMessage(dataSizeBuf.array());
					dataSizeBuf = null;
					clientEx.sendMessage(message);
					
					ByteBuf dataBuf = Unpooled.copyInt( CRC16.crc_16_rec(message,0xA001));
					clientEx.sendMessage(dataBuf.array());
					dataBuf = null;
					
				}else{
					LOGGER.info("End of  file reached, terminating simulator");
					stopSimulator();
				}
			}else{
				LOGGER.info("Server says: {} device unauthorized!!",simulatorConfig.getImei());
				stopSimulator();

			}
			

			Thread.sleep(simulatorConfig.getDelay());
		}
		clientEx.disconnect();
	}

	public static void stopSimulator(){
		isSimulating = false;
	}


	private static byte[] getMessage(SimulatorConfig simulatorConfig){
		switch (simulatorConfig.getMode()) {
		case 1:
			return messageFromFile(simulatorConfig);
		case 2:
			return messageFromReference(simulatorConfig);

		default:
			return messageFromFile(simulatorConfig);
		}

	}

	private static byte[] messageFromFile(SimulatorConfig simulatorConfig) {
		String currentLineStr = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(simulatorConfig.getFilePath()));
			int nextLine = -1;
			do {
				nextLine++;
				currentLineStr = bufferedReader.readLine();
			} while ((nextLine <= lastReadLine));
			bufferedReader.close();
			LOGGER.info("Reading line {}",lastReadLine);
			lastReadLine = nextLine;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(currentLineStr == null){
			EOFReached = true;
			return null;
		}
		LOGGER.info("Simulator says: {} packets sent to server",currentLineStr.substring(currentLineStr.length()-2));
		return ConversionUtils.hexStringToByteArray(currentLineStr);
	}

	private static byte[] messageFromReference(SimulatorConfig simulatorConfig){

		if(simulatorConfig.getReferenceMsg().length()<52){
			LOGGER.error("Invalid Reference String...Quiting...");
			System.exit(1);
			return null;
		}else{
			while(true){
				String referencePart = simulatorConfig.getReferenceMsg().substring(0,52);
				StringBuffer monkeyMessageBuffer = new StringBuffer();
				monkeyMessageBuffer.append(referencePart.subSequence(0, 4));
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(System.currentTimeMillis()),16)).append("00");//time + priority
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(ThreadLocalRandom.current().nextLong(252048000l, 260000l)),8));//latitude
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(ThreadLocalRandom.current().nextLong(552708000l, 560000l)),8));//longitude
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(ThreadLocalRandom.current().nextLong(0l, 1000l)),4));//altitude
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(ThreadLocalRandom.current().nextLong(0l, 360l)),4));//angle
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(ThreadLocalRandom.current().nextLong(0l, 15l)),2));//satellitecount
				monkeyMessageBuffer.append(formatHexa(Long.toHexString(ThreadLocalRandom.current().nextLong(0l, 200)),4));//speed
				monkeyMessageBuffer.append(simulatorConfig.getReferenceMsg().substring(52));
				String monkeyMessage = monkeyMessageBuffer.toString();
				simulatorConfig.setReferenceMsg(monkeyMessage);
				LOGGER.info("Monkey Hexa Data {}",simulatorConfig.getReferenceMsg());
				LOGGER.info("Simulator says: {} packets sent to server",simulatorConfig.getReferenceMsg().substring(simulatorConfig.getReferenceMsg().length()-2));
				return ConversionUtils.hexStringToByteArray(simulatorConfig.getReferenceMsg());
			}
		}

	}


	private static String formatHexa(String text,Integer format){
		if(text.length()<format){
			int addZeros = format-text.length();
			for (int i = 0; i < addZeros; i++) {
				text = "0"+text;
			}
		}
		return text;
	}
	/*public static void main(String[] args) {
		SimulatorConfig config = new SimulatorConfig();
		config.setReferenceMsg("0801000001520C5457400020F0ED450F07A6C8FFFB000011001E00160901010201030116034703F0011505C800CCFF0BCB035209000F0A34F70B000313045043241B440000B5000AB600064236A118000002F10000A5A246000001EE0001");
		byte[] messageFromReference = messageFromReference(config);

		long currentTimeMillis = System.currentTimeMillis();
		LOGGER.info("Message formatted {}",Long.toHexString(currentTimeMillis),16);
		LOGGER.info("Message formatted {}",formatHexa(Long.toHexString(currentTimeMillis),16));
	}*/


}
