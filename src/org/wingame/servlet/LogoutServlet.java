package org.wingame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8444048074538977056L;

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
		//…æsession
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		//…æcookie
		Cookie nmcookie = new Cookie("j2eeworkname",null);
		nmcookie.setMaxAge(0);
		nmcookie.setPath("/");
		response.addCookie(nmcookie);
		Cookie pwcookie = new Cookie("j2eeworksign",null);
		pwcookie.setMaxAge(0);
		pwcookie.setPath("/");
		response.addCookie(pwcookie);
		//÷ÿ∂®œÚ
		response.getWriter().print("<script>window.history.back()</script>");
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
		doGet(request,response);
	}

}
