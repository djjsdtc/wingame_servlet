package org.wingame.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.HtmlUtils;
import org.wingame.util.BaseUtil;

public class Meeting extends BaseUtil{
	public int m_id;
	public String m_title = "";
	
	public String getM_title() {
		return HtmlUtils.htmlUnescape(m_title);
	}

	public String getM_address() {
		return HtmlUtils.htmlUnescape(m_address);
	}

	public String m_address = "";
	public Timestamp m_datetime = new Timestamp(System.currentTimeMillis());
	public double m_fee = 0;
	public Timestamp m_deadline = new Timestamp(System.currentTimeMillis());
	private int attendCount = 0;
	
	public Meeting(){
		this(-1,"","",new Timestamp(System.currentTimeMillis()),0,new Timestamp(System.currentTimeMillis()));
	}
	
	public Meeting(Connection conn,int m_id) {
		super(conn);
		this.m_id = m_id;
		try {
			PreparedStatement stmt=conn
					.prepareStatement("select * from v_meeting where m_id=?");
			stmt.setInt(1, m_id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				this.m_title = rs.getString("m_title");
				this.m_address = rs.getString("m_address");
				this.m_datetime = rs.getTimestamp("m_datetime");
				this.m_fee = rs.getDouble("m_fee");
				this.m_deadline = rs.getTimestamp("m_deadline");
				this.attendCount = rs.getInt("m_attend");
			}
			else this.m_id = -1;
		} catch (SQLException e) {
			e.printStackTrace();
			this.m_id = -1;
		}
	}

	public int getAttendCount() {
		return attendCount;
	}

	public Meeting(int m_id, String m_title, String m_address,
			Timestamp m_datetime, double m_fee, Timestamp m_deadline) {
		super(null);
		this.m_id = m_id;
		this.m_title = HtmlUtils.htmlEscape(m_title);
		this.m_address = HtmlUtils.htmlEscape(m_address);
		this.m_datetime = m_datetime;
		this.m_fee = m_fee;
		this.m_deadline = m_deadline;
		this.attendCount = 0;
	}
	
	public boolean isAttending(HttpServletRequest request){
		String username = (String) request.getSession().getAttribute("username");
		try {
			PreparedStatement stmt = conn.prepareStatement
					("select * from t_meeting_status where username=? and m_id=?");
			stmt.setString(1, username);
			stmt.setInt(2, m_id);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String translateTimestamp(Timestamp t, boolean includeTime){
		String s = getDigit(t.getYear()+1900,4) + "-" + getDigit(t.getMonth()+1,2) + "-" + getDigit(t.getDate(),2);
		if(includeTime) s += "T" + getDigit(t.getHours(),2) + ":" + getDigit(t.getMinutes(),2);
		return s;
	}
	
	private static String getDigit(int src,int digit){
		String s = Integer.toString(src);
		for(int i=0 ; i<digit-s.length() ; i++){
			s="0"+s;
		}
		return s;
	}
}
