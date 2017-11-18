package org.wingame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class UserUtil extends BaseUtil{
	
	public UserUtil(Connection conn) {
		super(conn);
	}

	public static enum Role {
		ADMIN,USER,GUEST
	}
	
	public boolean check(String username, String password){
		try {
			System.out.println("进入验证过程");
			PreparedStatement stmt = conn.prepareStatement("select password from t_user where username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next() && rs.getString("password").equals(password)){
				System.out.println("密码审核通过");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Role getCurrentRole(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		if(username == null) return Role.GUEST;
		try {
			PreparedStatement stmt = conn.prepareStatement("select role from t_user where username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				if(rs.getString("role").equals("u")) return Role.USER;
				else return Role.ADMIN;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
