package com.pcs.device.gateway.commons.http.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.commons.http.netty.payload.ClientResponse;
import com.pcs.device.gateway.commons.http.netty.payload.JsonResponse;
import com.pcs.device.gateway.commons.http.netty.payload.marshal.JsonUnmarshaller;
import com.pcs.device.gateway.commons.http.netty.payload.marshal.Unmarshaller;
import com.pcs.device.gateway.commons.http.netty.payload.marshal.UnmarshallerException;

/**
 * @author pcseg310
 *
 */
public class HttpClientHandler<T> extends
		SimpleChannelInboundHandler<HttpObject> {

	private static final Logger logger = LoggerFactory
			.getLogger(HttpClientHandler.class);
	private ClientResponse<T> response;

	public HttpClientHandler(ClientResponse<T> responseType) {
		this.response = responseType;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {
		HttpContentType httpContentType = null;

		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
			httpContentType = resolveContentType(response);
		}

		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			try {
				unmarshallContent(content, httpContentType);
			} catch (UnmarshallerException e) {
				logger.error(
						"Could not proceed with un marshalling the http contents",
						e);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error(
				"Exception caught while attempting to handle response message",
				cause);
		ctx.close();
	}

	private HttpContentType resolveContentType(HttpResponse response) {
		HttpContentType httpContentType = null;
		if (!response.headers().isEmpty()) {
			String contentTypeStr = response.headers().get("Content-Type");
			httpContentType = HttpContentType.valueOfType(contentTypeStr);
		}

		return httpContentType;
	}

	private void unmarshallContent(HttpContent content,
			HttpContentType httpContentType) throws UnmarshallerException {

		switch (httpContentType) {
		case JSON:
			if (!(this.response instanceof JsonResponse)) {
				logger.error(
						"The excepted response type is {},but recieved type is {},skipping the un marshalling",
						HttpContentType.JSON.getType(),
						httpContentType.getType());
				return;
			}
			Unmarshaller<T> unmarshaller = new JsonUnmarshaller<T>();
			this.response.setResponse(unmarshaller.unmarshall(content,
					this.response.getResponseType()));
			this.response.setReadyState(true);
			break;
		default:
			break;

		}
	}

	/**
	 * @author pcseg310
	 *
	 */
	private enum HttpContentType {

		JSON("application/json");

		private String type;

		HttpContentType(String type) {
			this.setType(type);
		}

		protected static HttpContentType valueOfType(String type) {
			HttpContentType httpContentType = null;
			if (type == null || type.isEmpty()) {
				return httpContentType;
			}
			for (HttpContentType contentType : values()) {
				if (contentType.getType().equals(type)) {
					httpContentType = contentType;
					break;
				}
			}

			return httpContentType;
		}

		protected String getType() {
			return type;
		}

		protected void setType(String type) {
			this.type = type;
		}

	}

}