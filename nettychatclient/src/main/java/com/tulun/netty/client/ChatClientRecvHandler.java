package com.tulun.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 描述:
 * @author shilei
 * @date 2018年5月28日
 */
public class ChatClientRecvHandler extends ChannelInboundHandlerAdapter{
	/**
	 * 读数据接口
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		
		  ByteBuf in = (ByteBuf) msg; // 打印客户端输入，传输过来的的字符
		  System.out.print(in.toString(CharsetUtil.UTF_8)); 
		  in.release(); 
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

	/**
	 * 通道建立成功
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("connection active!" + ctx.channel().remoteAddress());
	}

	/**
	 * 通道断开
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("connection inactive!" + ctx.channel().remoteAddress());
		/**
		 * 关闭通道的上下文资源
		 */
		ctx.close(); 
	}
}
