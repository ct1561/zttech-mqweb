package com.zttech.mq.web.springboot.send.netty;

import java.util.Map;

import com.zttech.mq.send.builder.RabbitProducerBuilder;
import com.zttech.mq.send.service.IMessageSend;
import com.zttech.mq.web.springboot.common.entity.NettySend;
import com.zttech.mq.web.springboot.common.util.HttpRequestParseUtil;
import com.zttech.mq.web.springboot.common.util.LoggerBuilderUtils;

import ch.qos.logback.classic.Logger;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	public HttpRequestHandler(RabbitProducerBuilder builder, NettySend nettySend) {
//		this.servletContext = servlet.getServletConfig().getServletContext();
		this.builder = builder;
		this.nettySend = nettySend;
//		this.servlet = servlet;
	}
	
//	private final ServletContext servletContext;
	
	private RabbitProducerBuilder builder;
	
	private NettySend nettySend;
	
//	private Servlet servlet;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		
//		MockHttpServletRequest servletRequest = createServletRequest(msg);
//		MockHttpServletResponse servletResponse = new MockHttpServletResponse();
//		DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//		servlet.service(servletRequest, servletResponse);
//		
//		String contentAsString = servletResponse.getContentAsString(CharsetUtil.UTF_8);
//		byte[] contentAsByteArray = servletResponse.getContentAsByteArray();
//		
//		servletResponse.encodeRedirectUrl(servletResponse.getForwardedUrl());
//		
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
//				HttpResponseStatus.OK, Unpooled.copiedBuffer(contentAsString, CharsetUtil.UTF_8));
//		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
//		
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
//				HttpResponseStatus.OK, Unpooled.copiedBuffer("success", CharsetUtil.UTF_8));
//		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
//		ctx.writeAndFlush(servletResponse).addListener(ChannelFutureListener.CLOSE);
		
		
//		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
//
//		for (String name : servletResponse.getHeaderNames()) {
//			for (Object value : servletResponse.getHeaderValues(name)) {
//				
//				((MockHttpServletRequest) response).addHeader(name, value);
//			}
//		}
//		ctx.writeAndFlush(response);
//		
//		InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());
//		// Write the content.
//        ChannelFuture writeFuture = ctx.write(new ChunkedStream(contentStream));
//        writeFuture.addListener(ChannelFutureListener.CLOSE);
//		String uri = msg.uri();
		Map<String, String> parse = HttpRequestParseUtil.parse(msg);
		Logger logger = LoggerBuilderUtils.getLogger(nettySend);
		produceMessage(parse.get("content"), parse.get("exchangeName"), parse.get("routingKey"));
		logger.info("from: " + ctx.channel() + "; exchangeName: " + parse.get("exchangeName") + "; routingKet" + 
				parse.get("routingKey") + "; content: " + parse.get("content"));
		response(ctx);
 
	}
	
	private void produceMessage(String content, String exchangeName, String routingKey) {
		if(content != null && exchangeName != null && routingKey != null) {
			this.builder.sendMessage(content, exchangeName, routingKey);
		}
	}
	
//	private void responseRemoveCmd(ChannelHandlerContext ctx, Map<String, String> parse) {
//		consumeThread.removeCmdListeneQueue(parse.get("queueName"));
//		response(ctx);
//	}
//	
//	private void responseAddCmd(ChannelHandlerContext ctx, Map<String, String> parse) {
//		
//		consumeThread.addListeneQueue(new CmdConfItem(parse.get("queueName"), parse.get("dllName"), 
//				parse.get("entryPoint"), parse.get("logFolder")));
//		response(ctx);
//	}
//	
//	private void responseRemoveNetty(ChannelHandlerContext ctx, Map<String, String> parse) {
//		consumeThread.removeNettyListeneQueue(parse.get("queueName"));
//		response(ctx);
//	}
//	
//	private void responseAddNetty(ChannelHandlerContext ctx, Map<String, String> parse) {
//		consumeThread.addListeneQueue(new NettyReceiveItem(parse.get("host"), Integer.valueOf(parse.get("port")), 
//				parse.get("queueName"), parse.get("logFolder")));
//		response(ctx);
//	}
//	
//	private void responseRemoveFolder(ChannelHandlerContext ctx, Map<String, String> parse) {
//		consumeThread.removeFolderListeneQueue(parse.get("queueName"));
//		response(ctx);
//	}
//	
//	private void responseAddFolder(ChannelHandlerContext ctx, Map<String, String> parse) {
//		consumeThread.addListeneQueue(new FolderReceiveItem(parse.get("queueName"), parse.get("fileSaveRoute"), 
//				parse.get("logFolder")));
//		response(ctx);
//	}
//	
//	private void responseGetCmd(ChannelHandlerContext ctx) {
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
//				HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(consumeThread.getCmdItemMap())
//						, CharsetUtil.UTF_8));
//		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
//		response.headers().set("Access-Control-Allow-Origin", "*");
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//	}
//	
//	private void responseGetNetty(ChannelHandlerContext ctx) {
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
//				HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(consumeThread.getNettyItemMap()), CharsetUtil.UTF_8));
//		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
//		response.headers().set("Access-Control-Allow-Origin", "*");
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//	}
//	
//	private void responseGetFolder(ChannelHandlerContext ctx) {
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
//				HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(consumeThread.getFolderItemMap()), CharsetUtil.UTF_8));
//		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
//		response.headers().set("Access-Control-Allow-Origin", "*");
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//	}
	
	private void response(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
				HttpResponseStatus.OK, Unpooled.copiedBuffer("success", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		response.headers().set("Access-Control-Allow-Origin", "*");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		ctx.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("服务端 channelRegistered:" + ctx.channel());
		NettyThread.CLIENTS.put(getChannelId(ctx.channel()), new ChannelWraper(ctx.channel()));
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("服务端 channelActive:" + ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause.getMessage() != null) {
			System.out.println("服务端 exceptionCaught:" + cause.getMessage());
		} else {
			System.out.println("断开空闲客户端:" + ctx.channel().toString());
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("服务端 channelInactive :" + ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		System.out.println("服务端 channelUnregistered :" + ctx.channel());
	}
	
	/**
	 * 获取管道id
	 * @param channel
	 * @return
	 */
	private String getChannelId(Channel channel) {
		return channel.id().asLongText();
	}
	
	
//	private MockHttpServletRequest createServletRequest(FullHttpRequest httpRequest) {
//        UriComponents uriComponents = UriComponentsBuilder.fromUriString(httpRequest.uri()).build();
//
//		MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
//		servletRequest.setRequestURI(uriComponents.getPath());
//		servletRequest.setPathInfo(uriComponents.getPath());
//		servletRequest.setMethod(httpRequest.method().name());
//
//		if (uriComponents.getScheme() != null) {
//			servletRequest.setScheme(uriComponents.getScheme());
//		}
//		if (uriComponents.getHost() != null) {
//			servletRequest.setServerName(uriComponents.getHost());
//		}
//		if (uriComponents.getPort() != -1) {
//			servletRequest.setServerPort(uriComponents.getPort());
//		}
//
//		
//		for (String name : httpRequest.headers().names()) {
//			for (String value : httpRequest.headers().getAll(name)) {
//				servletRequest.addHeader(name, value);
//			}
//		}
//
//		ByteBuf content = httpRequest.content();
//		content.readerIndex(0);
//		byte[] data = new byte[content.readableBytes()];
//		content.readBytes(data);
//		
//		servletRequest.setContent(data);
//
//		if (uriComponents.getQuery() != null) {
//			String query = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
//			servletRequest.setQueryString(query);
//		}
//
//		for (Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
//			for (String value : entry.getValue()) {
//				servletRequest.addParameter(
//						UriUtils.decode(entry.getKey(), "UTF-8"),
//						UriUtils.decode(value, "UTF-8"));
//			}
//		}
//
//		return servletRequest;
//	}
	
}
