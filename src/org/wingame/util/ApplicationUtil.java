package org.wingame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class ApplicationUtil extends BaseUtil {
	
	public ApplicationUtil(Connection conn) {
		super(conn);
	}

	public boolean checkQuality(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("select doc_status from t_document where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) return false;
			if(rs.getString("doc_status").charAt(0) == 'p') return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean hasAttended(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from t_meeting_status where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean attendMeeting(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		int m_id = (Integer) request.getSession().getAttribute("id");
		request.getSession().removeAttribute("id");
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into t_meeting_status values(?,?,?)");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			stmt.setString(3, "p");
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
