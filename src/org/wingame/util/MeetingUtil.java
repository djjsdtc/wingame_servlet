package org.wingame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.wingame.model.Meeting;

public class MeetingUtil extends BaseUtil {
	public MeetingUtil(Connection conn) {
		super(conn);
	}

	public boolean addOrUpdate(Meeting meeting){
		if(meeting == null) return false;
		if(meeting.m_id == -1) return addMeeting(meeting);
		else return updateMeeting(meeting);
	}
	
	private boolean addMeeting(Meeting meeting){
		
		try {
			PreparedStatement stmt = conn.prepareStatement
					("insert into t_meeting values(null,?,?,?,?,?);");
			stmt.setString(1, meeting.getM_title());
			stmt.setString(2, meeting.getM_address());
			stmt.setTimestamp(3, meeting.m_datetime);
			stmt.setDouble(4, meeting.m_fee);
			stmt.setTimestamp(5, meeting.m_deadline);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updateMeeting(Meeting meeting){
		try {
			PreparedStatement stmt = conn.prepareStatement
					("update t_meeting set m_title=?,m_address=?,m_datetime=?,m_fee=?,m_deadline=? where m_id=?;");
			stmt.setString(1, meeting.getM_title());
			stmt.setString(2, meeting.getM_address());
			stmt.setTimestamp(3, meeting.m_datetime);
			stmt.setDouble(4, meeting.m_fee);
			stmt.setTimestamp(5, meeting.m_deadline);
			stmt.setInt(6, meeting.m_id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeMeetingById(int id){
		try {
			PreparedStatement stmt = conn.prepareStatement
					("delete from t_meeting where m_id=?;");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
