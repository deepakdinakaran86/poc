package com.pcs.device.gateway.G2021.unused;



public class G2021CommandExecutor {/*

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021CommandExecutor.class);
	
	public G2021CommandExecutor() {
		init();
	}

	private void init() {
		BaseConsumer baseConsumer = new AsynchronousConsumer();
		baseConsumer.setQueue("command_pool");
		baseConsumer.setUrl("tcp://localhost:61616");
		baseConsumer.setMessageListener(this);
		baseConsumer.listen();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			Command command = (Command) objectMessage.getObject();
			LOGGER.info(command.getSourceIdentifier().toString());
			LOGGER.info(command.getPayload().toString());
			Cache cache = G2021DeviceManager.instance().getCacheProvider().getCache(G2021GatewayConfiguration.DEVICE_COMMAND_CACHE);
			List<Command> commands = (List<Command>)cache.get(command.getSourceIdentifier().toString()+"_command", List.class);
			if(commands == null){
				commands = new ArrayList<Command>();
			}
			commands.add(command);						
			cache.put(command.getSourceIdentifier().toString()+"_command", commands);
		} catch (Exception e) {
			LOGGER.error("Error receiving message !!!",e);
		}
		
		
	}
	
	public static void main(String[] args) {
		new G2021CommandExecutor();
	}
*/}
