package com.tulun.service;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tulun.bean.User;

/**
 * 描述:
 * @author shilei
 * @date 2018年5月29日
 */
public interface IUserService {

	/**
	 * 登录服务
	 * @param user
	 * @return
	 */
	User doLogin(String name, String pwd);
}
