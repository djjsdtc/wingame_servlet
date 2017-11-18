package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wingame.util.PasswordUtil;

public class ResetPasswordServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2576578352642139465L;

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
		response.sendRedirect(request.getContextPath() + "/userlogin/forget.jsp");
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
		String username = request.getParameter("name");
		String email = request.getParameter("mail");
		
		String correctmail = util.getMailAddress(username);
		if(correctmail != null && correctmail.equals(email)){
			String newpass = util.getRandomPassword();
			if(util.sendRandomPassword(email, newpass) && util.ChangePassword(username, newpass)){
				success(request,response);
			}
			else{
				showMessage(request,response,"密码修改失败，如果收到提示新密码的邮件请无视，您的密码尚未被更改。");
			}
		}
		else{
			showMessage(request,response,"用户名与邮箱地址不匹配。");
		}
	}
	private void success(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('新密码已经发送到邮箱了，请重新登录。')</script>");
		
		out.print("<script>window.location.href='"+request.getContextPath()+"/userlogin/'</script>");
	}

	private void showMessage(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		
		out.print("<script>window.history.back()</script>");
	}
}
