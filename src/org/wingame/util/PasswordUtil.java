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
		String title = "��Windows С��Ϸ�о�ѧ�᡿�һ�����";
		String context = "�𾴵��û���\n" +
				"����������վ�����һ����롣���¼����վΪ���������ɵ�������룺"+ password + "\n" +
				"��������ʹ�ô������¼��վ�����޸��������롣�޸�������뼰ʱɾ�����ʼ���\n" +
				"��л��ʹ�ñ���վ��ף��ʹ����졣\n" +
				"Windows С��Ϸ�о�ѧ��\n\n" +
				"ע�⣺���ʼ�Ϊϵͳ�Զ�����������ظ���";
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
