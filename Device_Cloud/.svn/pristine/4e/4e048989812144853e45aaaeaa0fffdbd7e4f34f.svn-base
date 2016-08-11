/**
 * 
 */
package com.pcs.deviceframework.connection.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.pcs.deviceframework.connection.Connector;
import com.pcs.deviceframework.connection.config.ConnectorConfiguration;
import com.pcs.deviceframework.connection.config.Handler;
import com.pcs.deviceframework.connection.exception.ConfigurationException;
import com.pcs.deviceframework.connection.exception.InvalidConnectorException;

public class TCPConnector implements Connector {

	private ConnectorConfiguration configuration;
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
				ServerBootstrap bootstrap = new ServerBootstrap();
				bootstrap.group(listenerGroup, processorGroup).channel(NioServerSocketChannel.class)
				        .childHandler(new ChannelInitializer<NioSocketChannel>() {
					        @Override
					        public void initChannel(NioSocketChannel ch) throws Exception {
						        for (Handler deviceHandler : configuration.getDeviceHandlers()) {
							        ch.pipeline().addLast(deviceHandler.getName(), deviceHandler.getChannelHandler());
						        }
					        }
				        });
				ChannelFuture future = bootstrap.bind(configuration.getPort()).sync();
				future.channel().closeFuture().sync();

			} catch (Exception e) {
				throw new InvalidConnectorException(e);
			} finally {
				listenerGroup.shutdownGracefully();
				processorGroup.shutdownGracefully();
			}

		}
	}

	@Override
	public void setConfiguration(ConnectorConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public ConnectorConfiguration getConfiguration() {
		return this.configuration;
	}

}
