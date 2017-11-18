package org.wingame.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wingame.util.BaseUtil;

public abstract class BaseSet extends BaseUtil{
	protected ResultSet rs;
	protected int itemsPerPage;
	
	public static class Summary{
		public String title;
		public int id;
	}
	
	public BaseSet(Connection conn,int page, int itemsPerPage) {
		super(conn);
		this.itemsPerPage = itemsPerPage;
	}

	public void close() {
		if (rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	
	public abstract Summary getCurrentItem();
	
	public abstract int getCurrentId();
	
	public abstract int getTotalPages();
	
	public static int parseInt(String str, int defvalue){
		if(str == null) return defvalue;
		else{
			try{
				int value = Integer.parseInt(str);
				if(value >= 1) return value;
				else return defvalue;
			}catch(NumberFormatException e){
				return defvalue;
			}
		}
	}
}
