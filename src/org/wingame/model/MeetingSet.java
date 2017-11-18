package org.wingame.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.util.HtmlUtils;

public class MeetingSet extends BaseSet {

	public MeetingSet(Connection conn, int page, int itemsPerPage) {
		super(conn, page, itemsPerPage);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from v_meeting order by m_id desc limit ?,?;");
			stmt.setInt(1, (page - 1) * itemsPerPage);
			stmt.setInt(2, itemsPerPage);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			rs = null;
		}
	}
	
	public MeetingSet(Connection conn){
		super(conn,1,1);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from v_meeting order by m_id desc");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			rs = null;
		}
	}

	@Override
	public int getCurrentId() {
		if (rs == null)
			return -1;
		try {
			return rs.getInt("m_id");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int getTotalPages() {
		try {
			PreparedStatement stmt = conn.prepareStatement("select count(*) from v_meeting");
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

	@Override
	public Summary getCurrentItem() {
		if(rs == null) return null;
		else{
			Summary summary = new Summary();
			try {
				summary.id = rs.getInt("m_id");
				summary.title = HtmlUtils.htmlUnescape(rs.getString("m_title"));
				return summary;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
