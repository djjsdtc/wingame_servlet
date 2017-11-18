package org.wingame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.wingame.model.Announcement;

public class AnnouncementUtil extends BaseUtil {
	public AnnouncementUtil(Connection conn) {
		super(conn);
	}

	public boolean addOrUpdate(Announcement announcement){
		if(announcement == null) return false;
		if(announcement.ann_id == -1) return addAnnouncement(announcement);
		else return updateAnnouncement(announcement);
	}
	
	private boolean addAnnouncement(Announcement announcement){
		
		try {
			PreparedStatement stmt = conn.prepareStatement
					("insert into t_announce values(null,?,?,?);");
			stmt.setString(1, announcement.getAnn_title());
			stmt.setString(2, announcement.getAnn_text());
			stmt.setInt(3, announcement.m_id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updateAnnouncement(Announcement announcement){
		try {
			PreparedStatement stmt = conn.prepareStatement
					("update t_announce set ann_title=?,ann_text=?,m_id=? where ann_id=?;");
			stmt.setString(1, announcement.getAnn_title());
			stmt.setString(2, announcement.getAnn_text());
			stmt.setInt(3, announcement.m_id);
			stmt.setInt(4, announcement.ann_id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeAnnouncementById(int id){
		try {
			PreparedStatement stmt = conn.prepareStatement
					("delete from t_announce where ann_id=?;");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
