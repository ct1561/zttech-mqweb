package com.zttech.mq.web.springboot.send.netty;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import com.zttech.mq.send.builder.RabbitProducerBuilder;
import com.zttech.mq.send.netty.ByteToStringDecoder;
import com.zttech.mq.send.netty.JsonEncoder;
import com.zttech.mq.send.netty.StringToJSONObjectDecoder;
import com.zttech.mq.send.service.IMessageSend;
import com.zttech.mq.web.springboot.common.entity.NettySend;
import com.zttech.mq.web.springboot.send.decoder.ProtocolDecoder;
import com.zttech.mq.web.springboot.send.entity.RabbitConf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyThread extends Thread {

	private NettySend nettySend;
	
	private RabbitProducerBuilder builder;
	
	private final static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	
	private final static EventLoopGroup workerGroup = new NioEventLoopGroup();
	
//	private DispatcherServlet dispatcherServlet;
	
	public static HashMap<String, ChannelWraper> CLIENTS = new HashMap<>();
	
	
	public NettyThread(NettySend nettySend, RabbitProperties rabbitProperties) throws ServletException {
//		MockServletContext servletContext = new MockServletContext();
//    	MockServletConfig servletConfig = new MockServletConfig(servletContext);
//        servletConfig.addInitParameter("contextConfigLocation","classpath:/META-INF/spring/root-context.xml");
//        servletContext.addInitParameter("contextConfigLocation","classpath:/META-INF/spring/root-context.xml");
//		
//		XmlWebApplicationContext wac = new XmlWebApplicationContext();
//		wac.setServletContext(servletContext);
//		wac.setServletConfig(servletConfig);
//		wac.setConfigLocation("classpath:/servlet-context.xml");
//		wac.refresh();
//		this.dispatcherServlet = new DispatcherServlet(wac);
//    	this.dispatcherServlet.init(servletConfig);
		this.nettySend = nettySend;
		this.builder = new RabbitProducerBuilder(createProperties(new RabbitConf(rabbitProperties.getHost(), 
				rabbitProperties.getPort(), rabbitProperties.getUsername(), rabbitProperties.getPassword(), 
				rabbitProperties.getVirtualHost())));
	}

	@Override
	public void run() {
		try {
			startServer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void startServer() throws InterruptedException {
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			
			bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					//channelPipeline?????????????????????
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("protocolHandler", new ProtocolDecoder());
					
					//tcp-
					//??????   ???????????????????????????
					//????????????????????????
					//maxFrameLength   ??????????????????????????????
					//lengthFieldOffset ?????????????????????????????????????????????????????????
					//lengthFieldLength  ????????????????????????
					//lengthAdjustment  ???????????????????????????
					//?????????????????????  lengthAdjustment = ???????????????????????? - lengthFieldOffset - lengthFieldLength - ????????????
					//initialBytesToStrip  ?????????????????????????????????
					pipeline.addLast("tcpFrameDecoder", new LengthFieldBasedFrameDecoder(
							65535, 0, 4, 0, 4));
//					pipeline.addLast("tcpDecoder", new MessagePackDecoder());
					
					//??????
					pipeline.addLast("tcpDecoder", new ByteToStringDecoder());
					pipeline.addLast("tcpDecoderTwo", new StringToJSONObjectDecoder());
					
					//????????????????????????
					pipeline.addLast("tcpFrameEncoder", new LengthFieldPrepender(4));
					
					pipeline.addLast("tcpEncoder", new JsonEncoder());
					
					//http
					//??????get
					pipeline.addLast("httpCodec", new HttpServerCodec());
					//??????post
					pipeline.addLast("httpAggregator", new HttpObjectAggregator(65536));
					//??????http  ??????
					pipeline.addLast("httpChunked", new ChunkedWriteHandler());
					
					
					//????????????
					pipeline.addLast("readTimeOutHandler", new ReadTimeoutHandler(10));
					
					
					pipeline.addLast("httpHandler", new HttpRequestHandler(builder, nettySend));
					
					//??????????????????
					//??????
					pipeline.addLast("channelHandler", new ServerChannelHandler(nettySend, builder));
					
				}
			});
			
			ChannelFuture channelFuture = bootstrap.bind(nettySend.getPort()).sync();
			
			channelFuture.channel().closeFuture().sync();
		} finally {
			shutdown();
		}
	}
	
	/**
	 * ????????????properties
	 * @return
	 */
	private Properties createProperties(RabbitConf rabbitConf){
		Properties properties = new Properties();
		properties.setProperty("host", rabbitConf.getHost());
		properties.setProperty("port", String.valueOf(rabbitConf.getPort()) );
		properties.setProperty("username", rabbitConf.getUsername());
		properties.setProperty("password", rabbitConf.getPassword());
		properties.setProperty("virtualHost", rabbitConf.getVirtualHost());
		properties.setProperty("initConnectionPoolNum", "3");
		properties.setProperty("initChannelPoolNum", "3");
		return properties; 
	}
	
	protected static void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}
	
}
