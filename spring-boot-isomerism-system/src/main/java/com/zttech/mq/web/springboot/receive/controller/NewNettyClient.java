package com.zttech.mq.web.springboot.receive.controller;

import com.zttech.mq.send.netty.ByteToStringDecoder;
import com.zttech.mq.send.netty.JsonEncoder;
import com.zttech.mq.send.netty.StringToJSONObjectDecoder;
import com.zttech.mq.send.netty.TestEvent;
import com.zttech.mq.web.springboot.common.entity.NettyReceiveItem;
import com.zttech.mq.web.springboot.receive.handler.NettyConsumeHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class NewNettyClient {

	private ChannelFuture channelFuture = null;
	
	private EventLoopGroup group = new NioEventLoopGroup();
	
	private Bootstrap bootstrap = new Bootstrap();
	
	private NettyReceiveItem receive;
	
	public NewNettyClient() {
		
	}
	
	public NewNettyClient(NettyReceiveItem receive) {
		this.receive = receive;
		init();
	}
	
	public NettyReceiveItem getReceive() {
		return receive;
	}

	public void setReceive(NettyReceiveItem receive) {
		this.receive = receive;
	}

	private ChannelFuture getChannelFuture() throws InterruptedException {
		
		if(channelFuture == null || !channelFuture.channel().isActive()) {
			channelFuture = doRequest();
		} 
		
		return channelFuture;
	}
	
	private ChannelFuture doRequest() throws InterruptedException {
		bootstrap.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4));
				pipeline.addLast("tcpDecoder", new ByteToStringDecoder());
				pipeline.addLast("tcpDecoderTwo", new StringToJSONObjectDecoder());
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast("tcpEncoder", new JsonEncoder());
				
				pipeline.addLast("handler", new NettyConsumeHandler());
				
			}
		});
		return bootstrap.connect(receive.getHost(), receive.getPort()).sync();
	}
	
	private void init() {
		bootstrap.group(group).channel(NioSocketChannel.class).option(
				ChannelOption.TCP_NODELAY, true);
	}
	
	public void release() {
		group.shutdownGracefully();
	}
	
	public void sendMessage(TestEvent msg) throws InterruptedException {
		getChannelFuture().channel().writeAndFlush(msg);
	}
}
