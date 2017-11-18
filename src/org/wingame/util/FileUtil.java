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
				return "待审核";
			case 'p':
				return "已通过";
			case 'f':
				return "未通过";
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
			String context = "尊敬的用户"+username+"：\n您好！\n您为会议"+meetingTitle+"上传的文档";
			if(status.charAt(0) == 'p'){
				context += "已经通过了管理员的审核，您现在已经获得了参加本次会议的资格。\n"+
						"请您前往本次会议的预报名系统进行会议预报名，地址如下：\n"+
						"http://localhost:8080/newj2eework/application/?id="+m_id+"\n"+
						"进入后您需要登录您的帐号才能进行会议的预报名。\n"+
						"如果您不小心删除了本邮件，您可以在文档上传系统中找到预报名系统的链接。\n"+
						"谢谢您的参与！\nWindows 小游戏研究学会\n\n" +
						"注意：该邮件为系统自动发出，请勿回复。";
			}else{
				context += "没有能够通过管理员的审核。您可以在上传文档截止日期前上传新的文档参加审核。\n"+
						"谢谢您的参与！\nWindows 小游戏研究学会\n\n" +
						"注意：该邮件为系统自动发出，请勿回复。";
			}
			
			MailUtil.SendMail(emailAddress, "【Windows 小游戏研究学会】文档审核结果", context);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
