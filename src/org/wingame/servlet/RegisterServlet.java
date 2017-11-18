package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wingame.util.RegisterUtil;

public class RegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1985819985423538938L;

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
		response.sendRedirect(request.getContextPath() + "/userlogin/register.jsp");
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
		request.setCharacterEncoding("utf-8");
		RegisterUtil util = new RegisterUtil((Connection) request.getServletContext().getAttribute("conn"));
		String psw1 = request.getParameter("pswd1");
		if(psw1 == null || psw1.length()<6){
			showMessage(request,response,"密码长度太短，密码应该为至少6位。");
			return;
		}
		String psw2 = request.getParameter("pswd2");
		if(psw2 != null && psw1.equals(psw2)){
			String name = request.getParameter("name");
			if(name == null || name.trim().equals("")){
				showMessage(request,response,"用户名不能为空。");
				return;
			}
			if(!name.matches("^[^<>]+$")){
				showMessage(request,response,"用户名中不能包含尖括号。");
				return;
			}
			if(!util.exists(name)){
				String email = request.getParameter("email");
				if(email == null || !email.matches("^[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$")){
					showMessage(request,response,"请填写正确的E-mail地址。");
					return;
				}
				String realname = request.getParameter("realname");
				if(realname == null || realname.trim().equals("")){
					showMessage(request,response,"真实姓名不能为空。");
					return;
				}
				String company = request.getParameter("company");
				if(company == null || company.trim().equals("")){
					showMessage(request,response,"单位不能为空。");
					return;
				}
				if(util.register(name, psw1, email,realname,company)){
					success(request,response);
				}
				else{
					showMessage(request,response,"未知错误。");
				}
			}
			else{
				showMessage(request,response,"用户名已存在。");
			}
		}
		else{
			showMessage(request,response,"两次密码输入不一致。");
		}
	}

	private void success(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('注册成功啦，快来登录吧！')</script>");
		out.print("<script>window.location.href='"+request.getContextPath()+"/userlogin/'</script>");
	}

	private void showMessage(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		out.print("<script>window.history.back()</script>");
	}
}
