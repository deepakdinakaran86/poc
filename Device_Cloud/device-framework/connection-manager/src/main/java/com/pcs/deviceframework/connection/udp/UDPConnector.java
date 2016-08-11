/**
 * 
 */
package com.pcs.deviceframework.connection.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

import com.pcs.deviceframework.connection.Connector;
import com.pcs.deviceframework.connection.config.ConnectorConfiguration;
import com.pcs.deviceframework.connection.config.Handler;
import com.pcs.deviceframework.connection.exception.ConfigurationException;
import com.pcs.deviceframework.connection.exception.InvalidConnectorException;

public class UDPConnector implements Connector {

	private ConnectorConfiguration configuration = null;

	public UDPConnector() {
	};

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
		}

		final ThreadFactory acceptFactory = new DefaultThreadFactory("accept");
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		final NioEventLoopGroup acceptGroup = new NioEventLoopGroup(1, acceptFactory);
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1, connectFactory);

		// Configure the server.
		try {
			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group(acceptGroup).channel(NioDatagramChannel.class).handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel channel) throws Exception {
					for (Handler deviceHandler : configuration.getDeviceHandlers()) {
						channel.pipeline().addLast(acceptGroup, deviceHandler.getName(),
						        deviceHandler.getChannelHandler());
					}
				}
			}).option(ChannelOption.SO_BROADCAST, true);

			// Start the server.
			final ChannelFuture future = bootstrap.bind(configuration.getPort()).sync();
			// Wait until the server socket is closed.
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			throw new InvalidConnectorException(e);
		} finally {
			acceptGroup.shutdownGracefully();
			connectGroup.shutdownGracefully();
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
