/**
 * 
 */
package com.pcs.saffron.connectivity.tcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.pcs.saffron.connectivity.client.handler.DefaultClientHandler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.exception.InvalidConnectorException;

public class TCPClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPClient.class);
	private String hostIP;

	private Integer hostPort;

	private byte[] clientMessage;

	public TCPClient() {

	}

	public TCPClient(String hostIP,Integer hostPort,byte[] clientMessage) {
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
		EventLoopGroup listenerGroup = new NioEventLoopGroup();
		EventLoopGroup processorGroup = new NioEventLoopGroup();
		try {
			LOGGER.info("Starting TCP transaction...");
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(processorGroup).channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<NioSocketChannel>() {
				@Override
				public void initChannel(NioSocketChannel ch) throws Exception {
					ch.pipeline().addLast(DefaultClientHandler.TCP_CLIENT_HANDLER, new DefaultClientHandler());
				}
			});

			ChannelFuture future = bootstrap.connect(hostIP,hostPort).awaitUninterruptibly();
			ChannelFuture writeAndFlush = future.channel().writeAndFlush(Unpooled.wrappedBuffer(clientMessage));
			writeAndFlush.addListener(new ChannelFutureListener() {

				public void operationComplete(ChannelFuture future) throws Exception {
					future.channel().close();
				}
			});
			future.channel().closeFuture().sync();
			LOGGER.info("TCP transaction completed !!");
		} catch (Exception e) {
			throw new InvalidConnectorException(e);
		} finally {
			listenerGroup.shutdownGracefully();
			processorGroup.shutdownGracefully();
		}
	}
}
