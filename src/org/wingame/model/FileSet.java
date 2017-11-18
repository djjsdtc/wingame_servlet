package org.wingame.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wingame.util.BaseUtil;

public class FileSet extends BaseUtil {
	private ResultSet rs;
	private int itemsPerPage;
	private String chooseMeeting;

	public static class FileItem {
		public String filename;
		public String username;
		public String status;
		public int m_id;
	}

	public FileSet(Connection conn, int page, int itemsPerPage, int meeting) {
		super(conn);
		this.itemsPerPage = itemsPerPage;
		this.chooseMeeting = (meeting == -1) ? "" : ("where m_id=" + meeting);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from t_document "
							+ chooseMeeting
							+ " order by doc_status asc limit ?,?;");
			stmt.setInt(1, (page - 1) * itemsPerPage);
			stmt.setInt(2, itemsPerPage);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			rs = null;
		}
	}
	
	public FileItem getCurrentFile(){
		if(rs == null) return null;
		else{
			FileItem file = new FileItem();
			try {
				file.filename = rs.getString("doc_filename");
				file.username = rs.getString("username");
				file.status = getStatus(rs.getString("doc_status"));
				file.m_id = rs.getInt("m_id");
				return file;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public int getTotalPages() {
		try {
			PreparedStatement stmt = conn.prepareStatement("select count(*) from t_document " + chooseMeeting);
			ResultSet myrs = stmt.executeQuery();
			myrs.next();
			int totalCount = myrs.getInt(1);
			myrs.close();
			return totalCount/itemsPerPage + (totalCount%itemsPerPage==0?0:1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void close() {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public boolean moveToNext() {
		if (rs == null)
			return false;
		else
			try {
				return rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	private String getStatus(String src){
		switch(src.charAt(0)){
		case 'w':
			return "待审核";
		case 'p':
			return "已通过";
		case 'f':
			return "未通过";
		default:
			return "";	
		}
	}
}
