package com.tulun.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tulun.bean.User;
import com.tulun.dao.IUserDao;
import com.tulun.util.C3p0Utils;

/**
 * 描述:用户user在dao层的具体实现
 * @author shilei
 * @date 2018年5月29日
 */
public class UserDaoImpl implements IUserDao {

	@Override
	public User selectByNameAndPwd(String name, String pwd) {
		// TODO Auto-generated method stub
		User user = null;
		
		try {
			Connection con = C3p0Utils.getConnection();
			String sql = "select * from user where name=? and pwd=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, pwd);
	
			ResultSet rs = pst.executeQuery();
			user = new User();
			/**
			 * 此处rs.next()不能省略，调用结果集的next，才能把游标放在表的第一行
			 */
			if(rs.next()){
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
				user.setEmail(rs.getString("email"));
			}
			System.out.println("user:" + user);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

}
