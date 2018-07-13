package com.tulun.service.impl;

import com.tulun.bean.User;
import com.tulun.dao.IUserDao;
import com.tulun.dao.impl.UserDaoImpl;
import com.tulun.service.IUserService;

/**
 * 描述:用户服务，主要管理用户的登录，名字和密码管理，离线消息管理，用户上下线管理等
 * @author shilei
 * @date 2018年5月29日
 */
public class UserServiceImpl implements IUserService {
	
	/**
	 * user的dao层接口操作
	 */
	private IUserDao iud;
	
	/**
	 * 初始化所有dao层对象
	 */
	public UserServiceImpl(){
		iud = new UserDaoImpl();
	}
	
	@Override
	public User doLogin(String name, String pwd) {
		// TODO Auto-generated method stub
		User user = iud.selectByNameAndPwd(name, pwd);
		return user;
	}
}
