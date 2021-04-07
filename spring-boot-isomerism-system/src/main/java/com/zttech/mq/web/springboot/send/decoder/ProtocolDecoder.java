package com.zttech.mq.web.springboot.send.decoder;

import java.util.List;

import com.zttech.mq.web.springboot.send.netty.ChannelWraper;
import com.zttech.mq.web.springboot.send.netty.NettyThread;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtocolDecoder extends ByteToMessageDecoder {

	private static final int PROTOCOL_LENGTH = 16;
	
	private static final String WEBSOCKET_PREFIX = "GET /ws";
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		String protocol = getBufStart(in);
		//要在这里对get post请求进行处理
//		if(protocol.startsWith("POST")) {
//			ctx.pipeline().remove("tcpFrameDecoder");
//			ctx.pipeline().remove("tcpDecoder");
//			ctx.pipeline().remove("tcpDecoderTwo");
//			ctx.pipeline().remove("tcpFrameEncoder");
//			ctx.pipeline().remove("tcpEncoder");
//			
//			ChannelWraper channelWraper = NettyThread.CLIENTS.get(ctx.channel().id().asLongText());
//			if(channelWraper != null) {
//				channelWraper.setProtocol(ChannelWraper.PROTOCOL_TCP);
//			}
//		} else if(protocol.startsWith("GET")) {
//			if(protocol.startsWith("GET /favicon.ico")) {
//				
//				return;
//			}
//			ctx.pipeline().remove("tcpFrameDecoder");
//			ctx.pipeline().remove("tcpDecoder");
//			ctx.pipeline().remove("tcpDecoderTwo");
//			ctx.pipeline().remove("tcpFrameEncoder");
//			ctx.pipeline().remove("tcpEncoder");
//			
//			ChannelWraper channelWraper = NettyThread.CLIENTS.get(ctx.channel().id().asLongText());
//			if(channelWraper != null) {
//				channelWraper.setProtocol(ChannelWraper.PROTOCOL_TCP);
//			}
//		} else 
			if(protocol.startsWith(WEBSOCKET_PREFIX)) {
			ctx.pipeline().remove("tcpFrameDecoder");
			ctx.pipeline().remove("tcpDecoder");
			ctx.pipeline().remove("tcpDecoderTwo");
			ctx.pipeline().remove("tcpFrameEncoder");
			ctx.pipeline().remove("tcpEncoder");
			
			ChannelWraper channelWraper = NettyThread.CLIENTS.get(ctx.channel().id()
					.asLongText());
			if(channelWraper != null) {
				channelWraper.setProtocol(ChannelWraper.PROROCOL_WS);
			}
		} else {
			ctx.pipeline().remove("httpCodec");
			ctx.pipeline().remove("httpAggregator");
			ctx.pipeline().remove("httpChunked");
			
			ChannelWraper channelWraper = NettyThread.CLIENTS.get(ctx.channel().id().asLongText());
			if(channelWraper != null) {
				channelWraper.setProtocol(ChannelWraper.PROTOCOL_TCP);
			}
		}
		
		in.resetReaderIndex();
		
		ctx.pipeline().remove(this.getClass());
	}
	
	private String getBufStart(ByteBuf in) {
		int length = in.readableBytes();
		if(length > PROTOCOL_LENGTH) {
			length = PROTOCOL_LENGTH;
		}
		in.markReaderIndex();
		byte[] content = new byte[length];
		in.readBytes(content);
		return new String(content);
	}

}
