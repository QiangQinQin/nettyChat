package com.tulun.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 描述:聊天应用客户端
 * @author shilei
 * @date 2018年5月28日
 */
public class ChatClient {
	public void startClient(String ip, int port){
		
		/**
		 * 创建事件循环处理线程，专用于连接服务器并且接受服务器返回信息
		 */
		EventLoopGroup eventLoop = new NioEventLoopGroup();
		
		try{
			/**
			 * 创建客户端通信对象
			 */
			Bootstrap btp = new Bootstrap();
			/**
			 * 给客户端通信对象设置eventLoop，以及底层使用的网络模型，
			 * 这里采用Java NIO（NioSocketChannel），并添加
			 * ChatClientRecvHandler处理器到eventLoop当中
			 */
			btp.group(eventLoop)
			.channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception{
                    ch.pipeline().addLast(new ChatClientRecvHandler());
                }
            });
			
			/**
			 * 连接服务器，同步等待返回结果 
			 */
            ChannelFuture f = btp.connect(ip, port).sync();
 
            /**
             * 连接成功后，主线程负责发送数据
             */
            ChatClientSendHandler ccs = new ChatClientSendHandler(f);
            ccs.startSendHandler();
            
            /**
             * 同步等待channel关闭，结束通信
             */
            f.channel().closeFuture().sync();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			/**
			 * 结束事件处理线程
			 */
			eventLoop.shutdownGracefully();
		}
	}
}
