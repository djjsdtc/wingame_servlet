package org.wingame.util;

import java.sql.Connection;

public abstract class BaseUtil {
	protected Connection conn;
	
	public BaseUtil(Connection conn){
		this.conn = conn;
	}
}
