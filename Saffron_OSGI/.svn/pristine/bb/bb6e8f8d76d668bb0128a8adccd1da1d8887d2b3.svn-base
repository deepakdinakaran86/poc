package com.pcs.saffron.teltonika.simulator.bootstrap;

import java.util.Scanner;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.teltonika.simulator.bean.SimulatorConfig;
import com.pcs.saffron.teltonika.simulator.connector.tcp.TCPSimulator;

public class Bootstrap {

	private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

	public static void main(String[] args) throws Exception {

		SimulatorConfig simulatorConfig = null;
		if (args != null && args.length > 0) {
			simulatorConfig = new SimulatorConfig();
			CmdLineParser parser = null;
			try {
				ParserProperties pp = ParserProperties.defaults();
				pp.withUsageWidth(80);
				parser = new CmdLineParser(simulatorConfig,pp);
				parser.parseArgument(args);
			} catch (CmdLineException e) {
				LOGGER.error("Error occured while parsing the options:" + e.getMessage());
				parser.printUsage(System.err);
			}
		} else {
			simulatorConfig = new SimulatorConfig();
			Scanner scanner = new Scanner(System.in);
			LOGGER.info("Enter server ip");
			simulatorConfig.setServerIP(scanner.next());
			LOGGER.info("Enter server port");
			simulatorConfig.setServerPort(scanner.nextInt());

			LOGGER.info("Enter IMEI");
			simulatorConfig.setImei(scanner.next());
			LOGGER.info("Enter Delay");
			simulatorConfig.setDelay(scanner.nextLong());

			LOGGER.info("Select preffered simulation mode");
			LOGGER.info("1. Tamed mode (stick to file)");
			LOGGER.info("2. Fire Monkey (Use this for simulating real devices)");
			int mode = scanner.nextInt();
			simulatorConfig.setMode(mode);
			switch (mode) {
			case 1:
				LOGGER.info("Enter Simulator Source Path");
				simulatorConfig.setFilePath(scanner.next());
				break;

			case 2:
				LOGGER.info("Enter reference message");
				simulatorConfig.setReferenceMsg(scanner.next());
				break;
			default:
				break;
			}

			scanner.close();
			scanner = null;
		}
		TCPSimulator.startSimulator(simulatorConfig);
	}

}
