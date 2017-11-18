package org.wingame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterUtil extends BaseUtil{
	
	public RegisterUtil(Connection conn) {
		super(conn);
	}

	public boolean register(String username,String password,String email, String realname, String company){
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into t_user values(?,?,?,'u',?,?)");
			stmt.setString(1, username);
			stmt.setString(2, MD5Util.md5(password));
			stmt.setString(3, email);
			stmt.setString(4, realname);
			stmt.setString(5, company);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean exists(String username){
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from t_user where username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean delete(String username){
		try {
			PreparedStatement stmt;
			//TODO:cascade
			stmt = conn.prepareStatement("delete from t_user where username=?");
			stmt.setString(1, username);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
