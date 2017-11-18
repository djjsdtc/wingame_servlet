package org.wingame.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.util.HtmlUtils;
import org.wingame.util.BaseUtil;

public class Announcement extends BaseUtil{
	public int ann_id;
	public String ann_title = "";
	public String ann_text = "";
	
	public String getAnn_title() {
		return HtmlUtils.htmlUnescape(ann_title);
	}

	public String getAnn_text() {
		return HtmlUtils.htmlUnescape(ann_text);
	}

	public int m_id;

	public Announcement(int ann_id, String ann_title, String ann_text, int m_id) {
		super(null);
		this.ann_id = ann_id;
		this.ann_title = HtmlUtils.htmlEscape(ann_title);
		this.ann_text = HtmlUtils.htmlEscape(ann_text);
		this.m_id = m_id;
	}

	public String getMTitle() {
		try {
			PreparedStatement stmt=conn
					.prepareStatement("select m_title from v_meeting where m_id=?");
			stmt.setInt(1, m_id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getString("m_title");
			}
			else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Announcement(){
		this(-1,"","",-1);
	}
	
	public Announcement(Connection conn,int ann_id) {
		super(conn);
		this.ann_id = ann_id;
		try {
			PreparedStatement stmt=conn
					.prepareStatement("select * from t_announce where ann_id = ?");
			stmt.setInt(1, ann_id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				this.ann_title = rs.getString("ann_title");
				this.ann_text = rs.getString("ann_text");
				this.m_id = rs.getInt("m_id");
			}
			else this.ann_id = -1;
		} catch (SQLException e) {
			e.printStackTrace();
			this.ann_id = -1;
		}
	}
}
