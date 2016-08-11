package com.pcs.device.gateway.ruptela.bootstrap;




public class DiagnosticListenerTest {/*

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
//		((G2021DeviceManager)G2021DeviceManager.instance()).evictDeviceConfiguration(33);
		System.out.println("Do you want to start the application by providing settings ?");
		System.out.println("Y for console settings");
		System.out.println("N to start with pre-configured settings");
		
		switch (scanner.next().toUpperCase()) {
		case "Y":
			startFromCommandline();
			break;
		case "N":
			startFromConfiguration();
			break;
		default:
			break;
		}
		
		scanner.close();
		scanner = null;
		
	}
	
	private static void startFromConfiguration(){
		Integer mode = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.START_MODE));
		String delayed = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.START_WITH_DELAY).toUpperCase();
		String dataserverIP = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATA_SERVER_IP);
		Integer port = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATA_SERVER_PORT));
		Object[] argts = new Object[4];
		argts[1] = dataserverIP;
		argts[2] = port;
		startServers(argts, mode, delayed);
	}

	private static void startFromCommandline() {
		final Object[] argts = new Object[4];
		Scanner scanner = new Scanner(System.in);
		System.out.println(" Select Mode ");
		System.out.println("=============");
		System.out.println("1. Control Server");
		System.out.println("2. Data Server");
		System.out.println("3. Mixed (Both on the same console)");
		//scanner.nextInt();
		int mode = 3;
		
		System.out.println("Do you want to start the data server with delay after acknowledgement? (Y/N)");
		String choice = scanner.next().toUpperCase();
		
		System.out.println("Enter dataserver ip (please prefer the same ip)");
		argts[1] = scanner.next();
		
		System.out.println("Enter dataserver port");
		argts[2] = scanner.nextInt();
		scanner.close();
		scanner = null;
		startServers(argts, mode, choice);
		CommandProcessorDiagnosisEx.getInstance().doProcess();
	}

	private static void startServers(final Object[] argts, int mode,
			String choice) {
		switch (choice) {
		case "Y":
			argts[0] = true;
			break;
		case "N":
			argts[0] = false;
			break;
		default:
			break;
		}
		switch (mode) {
		case 1:
			startControlServer(argts);
			break;
			
		case 2:
			startDataServer(argts);
			break;
			
		case 3:
			startControlServer(argts);
			startDataServer(argts);
			break;

		default:
			System.err.println("Invalid option(s) provided!!!");
			break;
		}
	}

	private static void startDataServer(final Object[] argts) {
		
		System.err.println(" Starting Data Server ");
		System.err.println("==========================");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				TCPConnector g2021Listener1 = new TCPConnector();
				ConnectorConfiguration configuration1 = new ConnectorConfiguration();
				Handler frame1 = new Handler();
				Handler handler1 = new Handler();
				//
				configuration1.addDeviceHandler(handler1);
				//configuration.addDeviceHandlers(frame);
				frame1.setName("G2021Decoder");
				frame1.setChannelHandlerProvider(G2021Frame.class.getName(), null);
				handler1.setName("G2021DiagnosticHandler");
				handler1.setChannelHandlerProvider(G2021DiagnosticHandler.class.getName(), argts);
				configuration1.setModel("G2021");
				configuration1.setName("G2021");
				configuration1.setPort((int)argts[2]);
				configuration1.setVendor("PCS");
				g2021Listener1.setConfiguration(configuration1);
				try {
					g2021Listener1.connect();
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		System.err.println("  Data Server Started ");
		System.err.println("==========================");
	}

	private static void startControlServer(final Object[] argts) {
		
		System.err.println(" Starting Control Server ");
		System.err.println("==========================");
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
							
				TCPConnector g2021Listener = new TCPConnector();
				ConnectorConfiguration configuration = new ConnectorConfiguration();
				Handler frame = new Handler();
				Handler handler = new Handler();
				//
				configuration.addDeviceHandler(handler);
				//configuration.addDeviceHandlers(frame);
				frame.setName("G2021Decoder");
				frame.setChannelHandlerProvider(G2021Frame.class.getName(), null);
				handler.setName("G2021DiagnosticHandler");
				handler.setChannelHandlerProvider(G2021DiagnosticHandler.class.getName(), argts);
				configuration.setModel("G2021");
				configuration.setName("G2021");
				configuration.setPort(91);
				configuration.setVendor("PCS");
				g2021Listener.setConfiguration(configuration);
				try {
					g2021Listener.connect();
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		
		System.err.println(" Control Server Started ");
		System.err.println("==========================");
	}
	
*/}
