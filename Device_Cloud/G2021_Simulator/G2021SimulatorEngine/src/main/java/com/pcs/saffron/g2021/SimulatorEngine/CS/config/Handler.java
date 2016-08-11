
package com.pcs.saffron.g2021.SimulatorEngine.CS.config;

import io.netty.channel.ChannelHandler;

import java.lang.reflect.InvocationTargetException;


/**
 * @author Aneesh
 *
 */
public class Handler {
	
	private ChannelHandler channelHandler = null;
	private String name;
	private String channelHandlerProvider = null;
	private Object[] args = null;
	
	
	/**
	 * @return the channelHandler
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public final ChannelHandler getChannelHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Object channelProviderCandidate = null;
		if(args == null){
			channelProviderCandidate = Class.forName(channelHandlerProvider).newInstance();
		}else{
			channelProviderCandidate = Class.forName(channelHandlerProvider).getConstructor(Object[].class).newInstance(new Object[]{args});
		}
		
		if (channelProviderCandidate instanceof ChannelHandler) {
			channelHandler = (ChannelHandler) channelProviderCandidate;
		}
		return channelHandler;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the channelHandlerProvider
	 */
	public final String getChannelHandlerProvider() {
		return channelHandlerProvider;
	}
	/**
	 * @param channelHandlerProvider the channelHandlerProvider to set
	 * @param args TODO
	 * @throws Exception 
	 */
	public final void setChannelHandlerProvider(String channelHandlerProvider, Object[] args) {
		this.channelHandlerProvider = channelHandlerProvider;
		if(args != null)
			this.args = args;
	}

}
