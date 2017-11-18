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

import org.wingame.util.PasswordUtil;
import org.wingame.util.UserUtil;

public class ChangePasswordServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6233131905162883000L;

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
		response.sendRedirect(request.getContextPath() + "usermgmt/changepassword.jsp");
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
		String origin = request.getParameter("origin");
		String username = (String) request.getSession().getAttribute("username");
		if(new UserUtil((Connection) request.getServletContext().getAttribute("conn")).check(username, origin)){
			String new1 = request.getParameter("new1");
			if(new1.length()<6){
				showMessage(request,response,"���볤��̫�̣�����Ӧ��Ϊ����6λ��");
				return;
			}
			String new2 = request.getParameter("new2");
			if(!new1.equals("") && new1.equals(new2)){
				if(util.ChangePassword(username, new1)){
					//ɾsession
					HttpSession session = request.getSession();
					session.removeAttribute("username");
					//ɾcookie
					Cookie nmcookie = new Cookie("j2eeworkname",null);
					nmcookie.setMaxAge(0);
					nmcookie.setPath("/");
					response.addCookie(nmcookie);
					Cookie pwcookie = new Cookie("j2eeworksign",null);
					pwcookie.setMaxAge(0);
					pwcookie.setPath("/");
					response.addCookie(pwcookie);
					//�ض���
					success(request,response);
				}
				else{
					showMessage(request,response,"δ֪����");
				}
			}
			else{
				showMessage(request,response,"����Ϊ�ջ������������벻һ�¡�");
			}
		}
		else{
			showMessage(request,response,"ԭ�����������");
		}
	}
	private void success(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('�������޸ģ������µ�¼��')</script>");
		out.print("<script>window.location.href='"+request.getContextPath()+"/userlogin/'</script>");
	}

	private void showMessage(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		out.print("<script>window.history.back()</script>");
	}
}
