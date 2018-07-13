package com.tulun.dao;

import com.tulun.bean.User;

/**
 * 描述:用户数据层操作
 * @author shilei
 * @date 2018年5月29日
 */
public interface IUserDao {

	/**
	 * 根据用户名和密码查询密码
	 * @param name
	 * @param pwd
	 * @return
	 */
	User selectByNameAndPwd(String name, String pwd);
}
