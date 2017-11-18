package org.wingame.util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class FileUtil extends BaseUtil {
	
	public FileUtil(Connection conn) {
		super(conn);
	}

	public String getFileName(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("select doc_filename from t_document where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) return null;
			else return rs.getString("doc_filename");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getFileStatus(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("select doc_status from t_document where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) return "";
			switch(rs.getString("doc_status").charAt(0)){
			case 'w':
				return "�����";
			case 'p':
				return "��ͨ��";
			case 'f':
				return "δͨ��";
			default:
				return "";	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean removeFile(HttpServletRequest request, File file){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("delete from t_document where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			stmt.execute();
			file.delete();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createFileRecord(HttpServletRequest request, String filename){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into t_document values(?,?,?,?)");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			stmt.setString(3, filename);
			stmt.setString(4, "w");
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean verifyFile(HttpServletRequest request){
		String username = request.getParameter("username");
		int m_id = Integer.parseInt(request.getParameter("m_id"));
		String status = request.getParameter("status");
		try {
			PreparedStatement stmt = conn.prepareStatement("update t_document set doc_status=? where username=? and m_id=?");
			stmt.setString(1, status);
			stmt.setString(2, username);
			stmt.setInt(3, m_id);
			stmt.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void sendMail(HttpServletRequest request){
		String username = request.getParameter("username");
		int m_id = Integer.parseInt(request.getParameter("m_id"));
		String status = request.getParameter("status");
		
		String emailAddress;
		String meetingTitle;
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from v_mailsending where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			emailAddress = rs.getString("email");
			meetingTitle = rs.getString("m_title");
			String context = "�𾴵��û�"+username+"��\n���ã�\n��Ϊ����"+meetingTitle+"�ϴ����ĵ�";
			if(status.charAt(0) == 'p'){
				context += "�Ѿ�ͨ���˹���Ա����ˣ��������Ѿ�����˲μӱ��λ�����ʸ�\n"+
						"����ǰ�����λ����Ԥ����ϵͳ���л���Ԥ��������ַ���£�\n"+
						"http://localhost:8080/newj2eework/application/?id="+m_id+"\n"+
						"���������Ҫ��¼�����ʺŲ��ܽ��л����Ԥ������\n"+
						"�������С��ɾ���˱��ʼ������������ĵ��ϴ�ϵͳ���ҵ�Ԥ����ϵͳ�����ӡ�\n"+
						"лл���Ĳ��룡\nWindows С��Ϸ�о�ѧ��\n\n" +
						"ע�⣺���ʼ�Ϊϵͳ�Զ�����������ظ���";
			}else{
				context += "û���ܹ�ͨ������Ա����ˡ����������ϴ��ĵ���ֹ����ǰ�ϴ��µ��ĵ��μ���ˡ�\n"+
						"лл���Ĳ��룡\nWindows С��Ϸ�о�ѧ��\n\n" +
						"ע�⣺���ʼ�Ϊϵͳ�Զ�����������ظ���";
			}
			
			MailUtil.SendMail(emailAddress, "��Windows С��Ϸ�о�ѧ�᡿�ĵ���˽��", context);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
