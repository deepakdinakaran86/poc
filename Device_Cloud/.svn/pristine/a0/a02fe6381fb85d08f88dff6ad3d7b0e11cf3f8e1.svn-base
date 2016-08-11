/**
 * 
 */
package com.pcs.deviceframework.connection.client.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.connection.client.handler.DefaultClientHandler;
import com.pcs.deviceframework.connection.exception.ConfigurationException;
import com.pcs.deviceframework.connection.exception.InvalidConnectorException;

public class UDPClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPClient.class);

	private String hostIP;

	private Integer hostPort;

	private byte[] clientMessage;

	public UDPClient() {

	}

	public UDPClient(String hostIP,Integer hostPort,byte[] clientMessage) {
		this.hostIP = hostIP;
		this.hostPort = hostPort;
		this.clientMessage = clientMessage; 
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

	private void init() throws ConfigurationException {
		final ThreadFactory acceptFactory = new DefaultThreadFactory("accept");
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		final NioEventLoopGroup acceptGroup = new NioEventLoopGroup(1, acceptFactory);
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1, connectFactory);
		try {

			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group(acceptGroup).channel(NioDatagramChannel.class).handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel channel) throws Exception {
						channel.pipeline().addLast(acceptGroup, "defaultudphandler",new DefaultClientHandler());
				}
			}).option(ChannelOption.SO_BROADCAST, true);

			// Start the server.
			LOGGER.info("Writing command to device at "+hostIP+ " on Port :: "+hostPort);
			final ChannelFuture future = bootstrap.connect(hostIP,hostPort).sync();
			// Wait until the server socket is closed.
			 ByteBuf messageBuffer = Unpooled.wrappedBuffer(clientMessage);
				DatagramPacket messagePacket = new DatagramPacket(messageBuffer, new InetSocketAddress(hostIP, hostPort));
				ChannelFuture writeAndFlush = future.channel().writeAndFlush(messagePacket);
				writeAndFlush.addListener(new ChannelFutureListener() {

					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						future.channel().close();
					}
				});
			
			future.channel().closeFuture().sync();
		
		} catch (Exception e) {
			throw new InvalidConnectorException(e);
		} finally {
			acceptGroup.shutdownGracefully();
			connectGroup.shutdownGracefully();
		}
	}
}

/*
 *
			*/
