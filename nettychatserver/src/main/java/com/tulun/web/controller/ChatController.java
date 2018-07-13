package com.tulun.web.controller;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tulun.bean.User;
import com.tulun.constant.MsgType;
import com.tulun.service.IChatService;
import com.tulun.service.IUserService;
import com.tulun.service.impl.UserServiceImpl;
import com.tulun.util.JsonUtils;

/**
 * 描述:
 * @author shilei
 * @date 2018年5月29日
 */
public class ChatController {

	/**
	 * 用户管理服务
	 */
	private IUserService ius;
	/**
	 * 聊天管理服务
	 */
	private IChatService ics;
	
	/**
	 * 构造函数，初始化所有service服务对象
	 */
	public ChatController(){
		ius = new UserServiceImpl();
	}
	
	/**
	 * 统一处理nio模块递交过来的json字符串
	 * @param json
	 * @return
	 */
	public List<String> process(String json) {
		// TODO Auto-generated method stub
		/**
		 * 解析json中的消息类型，调用不同的service服务进行处理
		 */
		ObjectNode objectNode = JsonUtils.getObjectNode(json);
		String msgType = objectNode.get("msgtype").asText();
		
		switch(MsgType.valueOf(msgType)){
			case EN_MSG_LOGIN: 
				String name = objectNode.get("name").asText();
				String pwd = objectNode.get("pwd").asText();
				ius.doLogin(name, pwd);
				//登录操作
				break;
			case EN_MSG_REGISTER:
				//注册操作
				break;
			default:
				break;
		}
		
		return null;
	}
}
