package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wingame.util.FileUtil;

public class FileVerifyServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 750832450367862717L;

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
		response.sendRedirect(request.getContextPath() + "/filemgmt/");
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
		FileUtil util = new FileUtil((Connection) request.getServletContext().getAttribute("conn"));
		if(util.verifyFile(request)){
			util.sendMail(request);
			success(request,response);
		}
		else showMessage(request,response,"数据库出现异常");
	}
	
	private void success(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('文件审核成功。')</script>");
		
		out.print("<script>window.history.back()</script>");
	}

	private void showMessage(HttpServletRequest request,
			HttpServletResponse response, String message) throws IOException,
			ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('" + message + "')</script>");
		
		out.print("<script>window.history.back()</script>");
	}
}
