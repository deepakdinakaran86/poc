/**
 * 
 */
package com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.config.ConnectorConfiguration;
import com.pcs.saffron.g2021.SimulatorEngine.CS.config.Handler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.ConfigurationException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.InvalidConnectorException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author Santhosh
 *
 */

public class TCPClientConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPClientConnector.class);
	private String hostIP;

	private Integer hostPort;

	private byte[] clientMessage;

	private Bootstrap bootstrap = null;
	private ChannelFuture future = null;
	private EventLoopGroup listenerGroup = new NioEventLoopGroup();
	private EventLoopGroup processorGroup = new NioEventLoopGroup();
	private ConnectorConfiguration configuration;

	public TCPClientConnector() {

	}

	public TCPClientConnector(String hostIP,Integer hostPort,byte[] clientMessage) {
		this.hostIP = hostIP;
		this.hostPort = hostPort;
		this.clientMessage = clientMessage; 
	}

	public ConnectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(ConnectorConfiguration configuration) {
		this.configuration = configuration;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public Integer getHostPort() {
		return hostPort;
	}

	public void setHostPort(Integer hostPort) {
		this.hostPort = hostPort;
	}

	public byte[] getClientMessage() {
		return clientMessage;
	}

	public void setClientMessage(byte[] clientMessage) {
		this.clientMessage = clientMessage;
	}


	public boolean connect() throws ConfigurationException {
		init();
		return true;
	}
	
	public boolean disconnect() throws ConfigurationException {
		if(bootstrap != null && future != null){
			try {
				future.channel().close();
				
			} catch (Exception e) {
				LOGGER.error("Error trying to close connection !! Shutting down gracefully.");
			} finally {
				listenerGroup.shutdownGracefully();
				processorGroup.shutdownGracefully();
				future = null;
				bootstrap = null;
			}
		}else{
			LOGGER.error("Trying to close a non existing connection !!!");
		}
		return true;
	}

	private void init() throws ConfigurationException {
		
		try {
			LOGGER.info("Starting TCP transaction...");
			bootstrap = new Bootstrap();
			bootstrap.group(processorGroup).channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<NioSocketChannel>() {
				@Override
				public void initChannel(NioSocketChannel ch) throws Exception {

			        for (Handler deviceHandler : configuration.getDeviceHandlers()) {
				         ch.pipeline().addLast(deviceHandler.getName(), deviceHandler.getChannelHandler());
			        }
		        
				}
			});
			future = bootstrap.connect(hostIP,hostPort).syncUninterruptibly();
			LOGGER.info("TCP connection established successfully !!");
		} catch (Exception e) {
			throw new InvalidConnectorException(e);
		}
	}

	public void sendMessage(byte[] clientMessage ) throws InterruptedException,IOException {
		if(bootstrap != null && future != null){
			
			ChannelFuture writeAndFlush = future.channel().writeAndFlush(Unpooled.wrappedBuffer(clientMessage));
			writeAndFlush.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture future) throws Exception {					
				}
			});
		}else{
			LOGGER.error("Send message fired before establishing connection with the server !!!");
			//System.exit(0);
		}
		
		
	}
}
