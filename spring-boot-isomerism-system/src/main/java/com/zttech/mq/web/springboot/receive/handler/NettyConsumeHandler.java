package com.zttech.mq.web.springboot.receive.handler;


import com.alibaba.fastjson.JSONObject;
import com.zttech.mq.send.netty.EventType;
import com.zttech.mq.send.netty.MessageConverter;
import com.zttech.mq.send.netty.TestEvent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyConsumeHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		JSONObject jsonObject = (JSONObject) msg;
		EventType eventType = EventType.valueOf(jsonObject.getString("eventType"));
		switch (eventType) {
		case REPLY_EVENT:
			TestEvent testEvent = MessageConverter.converter(jsonObject);
			System.out.println("Client接收到消息: " + testEvent);
			break;

		default:
			System.out.println("other");
			break;
		}
	}

	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		ctx.flush();
		// System.out.println("channelReadComplete:" + ctx + "\n");
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("Client channelRegistered:" + ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client channelActive:" + ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("客户端Client channelInactive:" + ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {// 断开链接事件
		super.channelUnregistered(ctx);
		System.out.println("Client channelUnregistered:" + ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("Client exception：" + cause.getMessage());
	}
}
