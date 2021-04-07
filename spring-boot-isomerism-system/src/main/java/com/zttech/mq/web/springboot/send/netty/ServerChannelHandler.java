package com.zttech.mq.web.springboot.send.netty;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;

import org.msgpack.MessagePack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zttech.mq.send.builder.RabbitProducerBuilder;
import com.zttech.mq.send.netty.EventType;
import com.zttech.mq.send.netty.MessageConverter;
import com.zttech.mq.send.netty.TestEvent;
import com.zttech.mq.web.springboot.common.entity.NettySend;
import com.zttech.mq.web.springboot.common.util.LoggerBuilderUtils;

import ch.qos.logback.classic.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

	public ServerChannelHandler(NettySend nettySend, RabbitProducerBuilder builder) {
		super();
		this.builder = builder;
		this.nettySend = nettySend;
	}

	private RabbitProducerBuilder builder;
	
	private WebSocketServerHandshaker handshaker;
	
	private NettySend nettySend;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		//1.先判断这个消息来源是否注册过
		//2.判断是否创建过messageSender
		//3.进行消息发送处理
		Logger logger = LoggerBuilderUtils.getLogger(nettySend);
		JSONObject jsonObject = (JSONObject) msg;
		try {
			
			ChannelWraper channelWraper = NettyThread.CLIENTS.get(getChannelId(ctx.channel()));
			sendMessageContent(ctx, msg, channelWraper);
			
			logger.info("exchangeName: " + jsonObject.getString("exchangeName") + "; routingKey: " 
			+ jsonObject.getString("routingKey") + "; from: " + jsonObject.getString("from") + 
			"message: " + jsonObject.getString("content"));
//			writeBackUpFile(nettyProduceConf.getBackUp().getFileRoute(), msg, nettyProduceConf
//					.getBackUp().getFileSize());
			
//			NettyClientConf clientConf = null;
//			ChannelWraperAndSenderMessage channelWraperAndSenderMessage = NettyThread.CLIENTS
//					.get(getChannelId(ctx.channel()));
//			
//			String string = getClientName(msg);
//			//1-2
//			clientConf = registerClientByName(string, channelWraperAndSenderMessage, ctx);
//			
//			if(clientConf == null) {
//				System.out.println("无效客户端，已被关闭");
//			} else {
//				//3
//				sendMessageContent(ctx, msg, clientConf, channelWraperAndSenderMessage);
//				
//				//进行备份
//				writeBackUpFile(clientConf.getBackUp().getFileRoute(), msg, clientConf
//						.getBackUp().getFileSize());
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exchangeName: " + jsonObject.getString("exchangeName") + "; routingKey: " 
					+ jsonObject.getString("routingKey") + "; from: " + jsonObject.getString("from") + 
					"message: " + jsonObject.getString("content") + "; error: " + e.getMessage());
		}
	}
	
	/**
	 * 对消息进行发送处理
	 * @param ctx
	 * @param msg
	 * @throws IOException 
	 */
	private void sendMessageContent(ChannelHandlerContext ctx, Object msg, ChannelWraper channelWraper) throws IOException {
		if(msg instanceof JSONObject) {
			stringHandleValue(ctx, (JSONObject) msg, channelWraper);
		} else if(msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg, channelWraper);
		} else if(msg instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) msg, channelWraper);
		}
	}
	
	private void stringHandleValue(ChannelHandlerContext ctx, JSONObject jsonObject, ChannelWraper channelWraper) throws IOException {
		EventType valueOf = EventType.valueOf(jsonObject.get("eventType").toString());
		handleEvent(valueOf, jsonObject, null, channelWraper, ctx);
	}
	
	/**
	 * 处理HTTP请求，如果是websocket请求，构造握手响应
	 * @param ctx
	 * @param req
	 */
	@SuppressWarnings("deprecation")
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req, ChannelWraper channelWraper) {
		if(!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}
		
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://localhost:8080/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if(handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}
	
	/**
	 * 处理webSocketFrame
	 * @param ctx
	 * @param frame
	 * @throws IOException 
	 */
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame, ChannelWraper channelWraper) throws IOException {
		if(frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		
		if(frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		
		if(frame instanceof TextWebSocketFrame) {
			String text = ((TextWebSocketFrame) frame).text();
			
			String eventText = (String) JSON.parseObject(text).get("eventType");
			EventType eventType = EventType.valueOf(eventText);
			handleEvent(eventType, null, text, channelWraper, ctx);
			return;
		}
		
		if(frame instanceof BinaryWebSocketFrame) {
			ByteBuf buffer = frame.content();
			final int length = buffer.readableBytes();
			byte[] bytes = new byte[length];
			buffer.getBytes(buffer.readerIndex(), bytes, 0, length);
			
			MessagePack messagePack = new MessagePack();
			TestEvent testEvent = messagePack.read(bytes, TestEvent.class);
			System.out.println("testEvent: " + testEvent);
			
			messagePack = new MessagePack();
			bytes = messagePack.write(testEvent);
			buffer = Unpooled.buffer(128);
			BinaryWebSocketFrame bwsFrame = new BinaryWebSocketFrame(buffer);
			ctx.channel().write(bwsFrame);
		}
	}
	
	
	@SuppressWarnings("deprecation")
	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		if(res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			setContentLength(res, res.content().readableBytes());
		}
		
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if(!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	/**
	 * 统一处理event   分发不同请求
	 * @param eventType
	 * @param value
	 * @param text
	 * @throws IOException
	 */
	private void handleEvent(EventType eventType, Object value, String text, ChannelWraper channelWraper, ChannelHandlerContext ctx
			) throws IOException {
		switch (eventType) {
		case RABBIT_EVENT:
			TestEvent testEvent = null;
			if(value != null) {
				if(value instanceof JSONObject) {
					testEvent = MessageConverter.converter((JSONObject)value);
				}
			} else {
				testEvent = JSON.parseObject(text, TestEvent.class);
			}
			System.out.println("收到新消息：" + testEvent);
			
			//这里进行入队操作
			produceMessage(testEvent, channelWraper, ctx);
			break;
		case OTHER_EVENT:
			System.out.println("其他");
			break;
		default:
			break;
		}
	}
	
	/**
	 * 对接收到的消息进行处理
	 */
	private void produceMessage(TestEvent testEvent, ChannelWraper channelWraper, ChannelHandlerContext ctx) {
		builder.sendMessage(testEvent.getContent(), testEvent.getExchangeName(), testEvent.getRoutingKey());
//		for(ReceiveSign item : receiveSignList) {
//			if(item.getSign().equals(testEvent.getSign())) {
//				messageSend.send(testEvent.getContent(), item.getExchangeName(), item.getRoutingKey());
//				break;
//			}
//		}
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
}
