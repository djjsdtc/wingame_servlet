package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wingame.util.MD5Util;
import org.wingame.util.UserUtil;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1382608514443494702L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/userlogin/");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserUtil util = new UserUtil((Connection) request.getServletContext().getAttribute("conn"));
		String username = request.getParameter("username");
		String password = MD5Util.md5(request.getParameter("password"));
		String autologin_str = request.getParameter("writecookie");
		boolean autologin = autologin_str != null;
		
		if(util.check(username, password)){
			//写session
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			//写cookie
			if(autologin){
				Cookie nmcookie = new Cookie("j2eeworkname",username);
				nmcookie.setMaxAge(14*24*60*60);
				nmcookie.setPath("/");
				Cookie pwcookie = new Cookie("j2eeworksign",password);
				pwcookie.setMaxAge(14*24*60*60);
				pwcookie.setPath("/");
				response.addCookie(nmcookie);
				response.addCookie(pwcookie);
			}
			//重定向
			
			response.sendRedirect(request.getContextPath() + "/" + request.getParameter("redirect"));
		}
		else{
			response.setHeader("Content-type", "text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.print("<script>alert('登录失败：用户名不存在或密码错误，请检查并重试！')</script>");
			
			out.print("<script>window.history.back()</script>");
		}
	}

}
