package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;
import org.wingame.model.Announcement;
import org.wingame.util.AnnouncementUtil;

public class AnnouncementServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1567539286735731023L;

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
		response.sendRedirect(request.getContextPath() + "/announcement/edit.jsp");
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
		AnnouncementUtil util = new AnnouncementUtil((Connection) request.getServletContext().getAttribute("conn"));
		request.setCharacterEncoding("UTF-8");
		int id = (Integer) request.getSession().getAttribute("id");
		if(((String)request.getParameter("title")).trim().equals("")){
			showMessage(request,response,"标题不能为空哦！");
			return;
		}
		String title = HtmlUtils.htmlEscape((String)request.getParameter("title"));
		String context =  HtmlUtils.htmlEscape((String)request.getParameter("context"));
		int meeting;
		try{
			meeting = Integer.parseInt(request.getParameter("meeting"));
		}catch(Exception e){
			showMessage(request,response,"系统中还没有会议呢，先创建一个吧！");
			return;
		}
		Announcement announcement = new Announcement(id,title,context,meeting);
		boolean retValue = util.addOrUpdate(announcement);
		if(retValue){
			if(id == -1) success(request,response,"发布新公告成功！");
			else success(request,response,"修改成功！");
		}else{
			showMessage(request,response,"数据库出现异常");
		}
	}

	private void showMessage(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		out.print("<script>window.history.back()</script>");
	}
	
	private void success(HttpServletRequest request,HttpServletResponse response, String message) throws IOException, ServletException{
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		out.print("<script>alert('"+message+"')</script>");
		out.print("<script>window.location.href='"+request.getContextPath()+"/announcement/'</script>");
	}
}
