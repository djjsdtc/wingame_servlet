package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.JXLException;

import org.wingame.util.ExcelTableUtil;

public class ExcelTableServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219352342661059689L;

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
		int mid;
		String midStr = request.getParameter("m_id");
		try{
			mid = Integer.parseInt(midStr);
			ExcelTableUtil util = new ExcelTableUtil((Connection) getServletContext().getAttribute("conn"),mid);
			response.setContentType("application/vnd.ms-excel");
			util.createTable(response.getOutputStream());
		}
		catch(RuntimeException e){
			showMessage(request, response, "会议编号不正确，请确认您的访问地址！");
		} catch (SQLException e) {
			showMessage(request, response, "数据库出现错误。");
		} catch (JXLException e) {
			showMessage(request, response, "无法生成Excel文档。");
		}
		return;
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	private void showMessage(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		out.print("<script>window.history.back()</script>");
	}
}
