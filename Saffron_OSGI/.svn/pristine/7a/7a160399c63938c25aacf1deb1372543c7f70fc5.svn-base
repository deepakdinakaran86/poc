/**
 * 
 */
package com.pcs.saffron.connectivity.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.exception.InvalidConnectorException;

public class TCPConnector implements Connector {

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPConnector.class);
	
	private ConnectorConfiguration configuration;
	private ServerBootstrap bootstrap = null;
	public TCPConnector() {
	}

	public boolean connect() throws ConfigurationException {

		if (configuration == null) {
			throw new ConfigurationException("Configuration should be properly set before attempting to connect");
		}
		init();

		return true;
	}

	private void init() throws ConfigurationException {
		if (configuration.getDeviceHandlers() == null || configuration.getDeviceHandlers().size() == 0) {
			throw new ConfigurationException("Could not find handlers for this connector."
			        + this.getClass().getSimpleName() + " failed to initialize.");
		} else {
			EventLoopGroup listenerGroup = new NioEventLoopGroup();
			EventLoopGroup processorGroup = new NioEventLoopGroup();
			try {
				bootstrap = new ServerBootstrap();
				bootstrap.group(listenerGroup, processorGroup).channel(NioServerSocketChannel.class)
				        .childHandler(new ChannelInitializer<NioSocketChannel>() {
					        @Override
					        public void initChannel(NioSocketChannel ch) throws Exception {
						        for (Handler deviceHandler : configuration.getDeviceHandlers()) {
							        ch.pipeline().addLast(deviceHandler.getName(), deviceHandler.getChannelHandler());
						        }
					        }
				        });
				bootstrap.option(ChannelOption.TCP_NODELAY, true);
				bootstrap.childOption(ChannelOption.SO_RCVBUF, 1048576);
			    bootstrap.childOption(ChannelOption.SO_SNDBUF, 1048576);
				ChannelFuture channelFuture = bootstrap.bind(configuration.getPort()).sync();
				LOGGER.info("Channel Future is {}",channelFuture);
				channelFuture.channel().closeFuture().sync();
				LOGGER.info("Channel Future is getting closed {}",channelFuture);
			} catch (Exception e) {
				throw new InvalidConnectorException(e);
			} finally {
				listenerGroup.shutdownGracefully();
				processorGroup.shutdownGracefully();
			}

		}
	}
	
	public void disconect(){
		if(bootstrap != null){
			if( !bootstrap.group().isShutdown() && !bootstrap.group().isShuttingDown()){
				bootstrap.group().shutdownGracefully();
			}
		}
	}
	
	public boolean isConnected(){
		if(bootstrap == null){
			return false;
		}else{
			return bootstrap.group().isShutdown();
		}
	}

	public void setConfiguration(ConnectorConfiguration configuration) {
		this.configuration = configuration;
	}

	public ConnectorConfiguration getConfiguration() {
		return this.configuration;
	}

}
