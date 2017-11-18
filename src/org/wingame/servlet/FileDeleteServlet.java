package org.wingame.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wingame.util.FileUtil;

public class FileDeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2588212448616992359L;

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
		response.sendRedirect(request.getContextPath() + "fileupload/");
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
		if (removeOldFile(request)) success(request,response);
		else showMessage(request,response,"文件删除失败。");
	}

	public boolean removeOldFile(HttpServletRequest request) {
		FileUtil util = new FileUtil((Connection) request.getServletContext().getAttribute("conn"));
		String filename = util.getFileName(request);
		if (filename == null)
			return true;
		File file = new File(getServletContext().getRealPath("/upload") + "/"
				+ filename);
		return util.removeFile(request, file);
	}
	
	private void success(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('文件删除成功。')</script>");
		out.print("<script>window.location.href='" + request.getContextPath()
				+ "/fileupload/selectmeeting.jsp?m_id="
				+ request.getSession().getAttribute("id") + "'</script>");
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
