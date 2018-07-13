package com.tulun.web.netty;

import java.util.List;

import com.tulun.constant.Constant;
import com.tulun.web.controller.ChatController;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 描述:聊天服务器处理器
 * @author shilei
 * @date 2018年5月28日
 */
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 统一处理用户json的控制器
	 */
	private ChatController cc;
	
	/**
	 * netty的数据缓冲区，比NIO的ByteBuffer更好用
	 * 1.更简单的索引管理
	 * 2.内存可以动态增长
	 */
	private ByteBuf buf = Unpooled.buffer(Constant.MSG_DEFAULT_SIZE);
	
	public ChatServerHandler(){
		cc = new ChatController();
	}
	
	/**
	 * 读数据接口
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 接收客户端发送过来的数据
		 */
		ByteBuf in = (ByteBuf) msg;
		/**
		 * 解析json字符串
		 */
		String json = in.toString(CharsetUtil.UTF_8);
		/**
		 * 传给controller进行统一处理
		 */
		List<String> jsonList = cc.process(json);
		if(jsonList != null){
			/**
			 * 清除数据缓冲区
			 */
			buf.clear();
			/**
			 * 循环发送json字符串响应给客户端
			 */
			for(String response : jsonList){
				byte[] byteBuf = response.getBytes();
				buf.writeBytes(byteBuf);
				ctx.writeAndFlush(buf);
			}
		}
	
		/**
		 * 释放msg占用的内存资源
		 */
		ReferenceCountUtil.release(msg);
	}

	/**
	 * 通道建立成功
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("connection active!" + ctx.channel().remoteAddress());
		System.out.println("active:" + Thread.currentThread().getId());
	}

	/**
	 * 通道断开
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("connection inactive!" + ctx.channel().remoteAddress());
		ctx.close();
	}

	/**
	 * 异常处理接口
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
