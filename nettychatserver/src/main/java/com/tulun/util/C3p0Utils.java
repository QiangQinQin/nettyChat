package com.tulun.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 描述:MySQL数据库连接用的C3P0连接池
 * @author shilei
 * @date 2018年5月22日
 */
public class C3p0Utils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");

	// 从连接池中取用一个连接
	public static Connection getConnection() {	
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
		}
		return null;
	}

	// 释放连接回连接池
	public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
		if (rs != null) {
			try {
				if(conn != null)
					conn.close();
				if(rs != null)
					rs.close();
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 测试c3p0连接池的代码
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		Connection con = C3p0Utils.getConnection();
		
		PreparedStatement ps = con.prepareStatement("select * from user where username=?");
		ps.setString(1, "zhang san");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString("username") + " " + 
					rs.getString("password") + " " + rs.getString("email"));
			
		}
		
		C3p0Utils.close(con, ps, rs);
	}
}
