package org.wingame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordUtil extends BaseUtil{

	public PasswordUtil(Connection conn) {
		super(conn);
	}

	public boolean ChangePassword(String username, String password) {
		try {
			PreparedStatement stmt = conn.prepareStatement("update t_user set password=? where username=?");
			stmt.setString(1, MD5Util.md5(password));
			stmt.setString(2, username);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getRandomPassword() {
		final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append(chars.charAt((int) (Math.random() * chars.length())));
		}

		return sb.toString();
	}

	public boolean sendRandomPassword(String mailAddress, String password) {
		String title = "【Windows 小游戏研究学会】找回密码";
		String context = "尊敬的用户：\n" +
				"您已在我网站申请找回密码。请记录本网站为您重新生成的随机密码："+ password + "\n" +
				"请您尽快使用此密码登录本站，并修改您的密码。修改密码后，请及时删除此邮件。\n" +
				"感谢您使用本网站，祝您使用愉快。\n" +
				"Windows 小游戏研究学会\n\n" +
				"注意：该邮件为系统自动发出，请勿回复。";
		return MailUtil.SendMail(mailAddress, title, context);
	}
	
	public String getMailAddress(String username){
		try {
			PreparedStatement stmt = conn.prepareStatement("select email from t_user where username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) return rs.getString("email");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean changemail(String username,String address){
		try {
			PreparedStatement stmt = conn.prepareStatement("update t_user set email=? where username=?");
			stmt.setString(1, address);
			stmt.setString(2, username);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
