package com.pcs.device.gateway.commons.http.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;

import com.pcs.device.gateway.commons.http.netty.payload.ClientResponse;

/**
 * @author pcseg310
 *
 */
public class HttpClientInitializer<T> extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final ClientResponse<T> callback;
    
    public HttpClientInitializer(SslContext sslCtx, ClientResponse<T> responseCallback) {
        this.sslCtx = sslCtx;
        this.callback = responseCallback;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        // Enable HTTPS if necessary.
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }

        p.addLast(new HttpClientCodec());

        p.addLast(new HttpContentDecompressor());

        p.addLast(new HttpObjectAggregator(1048576));

        p.addLast(new HttpClientHandler<T>(this.callback));
    }
}
