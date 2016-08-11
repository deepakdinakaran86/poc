package com.pcs.device.gateway.commons.http.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;

import com.pcs.device.gateway.commons.http.netty.payload.ClientRequest;
import com.pcs.device.gateway.commons.http.netty.payload.ClientResponse;

/**
 * @author pcseg310
 *
 */
public class HttpClient {

	static final String URL = System
			.getProperty(
					"url",
					"http://10.234.60.30:9763/galaxy-registration-api-1.0.0/services/registration/countries");

	public static <R, T> void execute(ClientRequest<R> request, ClientResponse<T> response) throws Exception {
		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.handler(
							new HttpClientInitializer<T>(null,
									response));

			// Make the connection attempt.
			Channel ch = b.connect(request.getHost(), request.getPort()).sync().channel();

			// Prepare the HTTP request.
			URI uri = request.buildUri();
			HttpRequest httpRequest = new DefaultFullHttpRequest(
					HttpVersion.HTTP_1_1, request.getHttpMethod(), uri.getRawPath());
			httpRequest.headers().set("host", request.getHost());
			httpRequest.headers().set("connection", "close");
			httpRequest.headers().set("accept-encoding", "gzip");

			// Send the HTTP request.
			ch.writeAndFlush(httpRequest);

			// Wait for the server to close the connection.
			ch.closeFuture().sync();
			
		} finally {
			// Shut down executor threads to exit.
			group.shutdownGracefully();
		}
	}
	
}
