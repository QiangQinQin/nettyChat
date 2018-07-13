package com.tulun.web.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 描述: NIO服务器，基于netty实现，接收聊天json内容，分发Controller<br/>
 * 使用Netty网络库
 * 
 * @author shilei
 * @date 2018年5月23日
 */
public class ChatServer {

	/**
	 * 启动netty服务器
	 * @param ip
	 * @param port
	 */
	public void startServer(String ip, int port) throws Exception{
		/**
		 * 创建主事件循环线程，监听新用户的连接
		 */
		EventLoopGroup mainGroup = new NioEventLoopGroup(1);
		/**
		 * 创建工作线程，个数为4，处理已建立连接用户的可读写事件
		 */
		EventLoopGroup workerGroup = new NioEventLoopGroup(4);
		
		try{
			/**
			 * 创建服务器端的通信对象
			 */
			ServerBootstrap sbtp = new ServerBootstrap();
			/**
			 * 给服务器端通信对象设置参数
			 * 设置主线程和工程线程组
			 * 设置底层采用Java Nio来实现网络服务
			 * 设置日志log级别
			 * 设置事件驱动的事件处理器
			 */
			sbtp.group(mainGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new ChatServerHandler());
                }
            });
			
			/**
			 * 绑定端口号，同步等待用户连接
			 */
			ChannelFuture f = sbtp.bind(port).sync();
			
			/**
			 * 通道关闭，结束整个服务器应用
			 */
			f.channel().closeFuture().sync();
		}finally{
			/**
			 * 关闭主线程和4个工作线程
			 */
			mainGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
