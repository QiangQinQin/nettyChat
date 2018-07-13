package com.tulun.netty.client;

import java.util.Scanner;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tulun.constant.Constant;
import com.tulun.constant.MsgType;
import com.tulun.util.JsonUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;

/**
 * 描述:
 * @author shilei
 * @date 2018年5月29日
 */
public class ChatClientSendHandler {

	/**
	 * client和server通信用的channel
	 */
	private ChannelFuture f;
	/**
	 * 获取用户输入命令文字
	 */
	private Scanner in = new Scanner(System.in);
	
	
	public ChatClientSendHandler(ChannelFuture f) {
		// TODO Auto-generated constructor stub
		this.f = f;
	}
	
	public void showBeginMenu(){
		System.out.println("==============");
		System.out.println("1.登录");
		System.out.println("2.注册");
		System.out.println("3.忘记密码");
		System.out.println("4.退出系统");
		System.out.println("==============");
	}
	
	public void showMainMenu(){
		System.out.println("====================系统使用说明====================");
		System.out.println("1.输入modifypwd:username 表示该用户要修改密码");
		System.out.println("2.输入getallusers 表示用户要查询所有在线人员信息");
		System.out.println("3.输入username:xxx 表示一对一聊天");
		System.out.println("4.输入all:xxx 表示发送群聊消息");
		System.out.println("5.输入 sendfile:xxx 表示发送文件请求");
		System.out.println("6.输入quit 表示该用户下线，退出系统");
		System.out.println("================================================");
	}

	public void startSendHandler() {
		// TODO Auto-generated method stub
		while(true){
			showBeginMenu();
			System.out.print("请选择:");
			int choice = Integer.parseInt(in.nextLine());
			
			switch(choice){
			case 1:
				doLogin();
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				System.exit(0);
				break;
			default:
				System.out.println("无效的输入，请重新输入!");
				break;
			}
		}
	}

	/**
	 * 客户端的登录操作
	 */
	private void doLogin() {
		// TODO Auto-generated method stub
		System.out.print("用户名:");
		String username = in.nextLine();
		System.out.print("密码:");
		String password = in.nextLine();
		
		//组装登录json字符串
		ObjectNode jsonNode = JsonUtils.getObjectNode();
		jsonNode.put("msgtype", MsgType.EN_MSG_LOGIN.toString());
		jsonNode.put("name", username);
		jsonNode.put("pwd", password);
		String json = jsonNode.toString();
		
		ByteBuf buf = Unpooled.buffer(Constant.MSG_DEFAULT_SIZE);
		buf.writeBytes(json.getBytes());
		f.channel().writeAndFlush(buf);
	}
}
