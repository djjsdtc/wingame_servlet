package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wingame.util.PasswordUtil;
import org.wingame.util.UserUtil;

public class ChangeMailServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7579269662995704918L;

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
		response.sendRedirect(request.getContextPath() + "usermgmt/changemail.jsp");
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
		PasswordUtil util = new PasswordUtil((Connection) request.getServletContext().getAttribute("conn"));
		String password = request.getParameter("password");
		String username = (String) request.getSession().getAttribute("username");
		if(new UserUtil((Connection) request.getServletContext().getAttribute("conn")).check(username, password)){
			String originmail = request.getParameter("originmail");
			if(util.getMailAddress(username).equals(originmail)){
				String newmail = request.getParameter("newmail");
				if(!newmail.matches("^[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$")){
					showMessage(request,response,"«ÎÃÓ–¥’˝»∑µƒE-mailµÿ÷∑°£");
					return;
				}
				if(util.changemail(username, newmail)) success(request,response);
				else showMessage(request,response,"Œ¥÷™¥ÌŒÛ°£");
			}
			else{
				showMessage(request,response,"‘≠” œ‰ ‰»Î¥ÌŒÛ°£");
			}
		}
		else{
			showMessage(request,response,"√‹¬Î ‰»Î¥ÌŒÛ°£");
		}
	}

	private void success(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('” œ‰“—–ﬁ∏ƒ°£')</script>");
		
		out.print("<script>window.location.href='"+request.getContextPath()+"/usermgmt/changemail.jsp'</script>");
	}

	private void showMessage(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		out.print("<script>window.history.back()</script>");
	}
}
