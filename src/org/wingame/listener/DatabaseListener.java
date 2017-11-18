package org.wingame.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.codec.binary.Base64;

/**
 * Application Lifecycle Listener implementation class DatabaseListener
 *
 */
public class DatabaseListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DatabaseListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	final String jdbcUrl = "jdbc:mysql://localhost:3306/db_newj2ee?useUnicode=true&characterEncoding=UTF-8";
    	final String password = new String(Base64.decodeBase64("MTk5MjEyMTE="));
    	Connection conn = null;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl,"root",password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	ServletContext application = sce.getServletContext();
    	application.setAttribute("conn", conn);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    	ServletContext application = sce.getServletContext();
        Connection conn = (Connection) application.getAttribute("conn");
        if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
	
}
