package org.wingame.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;
import org.wingame.model.Meeting;
import org.wingame.util.MeetingUtil;

public class MeetingServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3004160354728823354L;

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
		response.sendRedirect(request.getContextPath() + "/meeting/edit.jsp");
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
		MeetingUtil util = new MeetingUtil((Connection) request.getServletContext().getAttribute("conn"));
		request.setCharacterEncoding("UTF-8");
		int id = (Integer) request.getSession().getAttribute("id");
		if(((String)request.getParameter("title")).trim().equals("")){
			showMessage(request,response,"标题不能为空哦！");
			return;
		}
		String title = HtmlUtils.htmlEscape(request.getParameter("title"));
		String address =  HtmlUtils.htmlEscape(request.getParameter("address"));
		Timestamp datetime,deadline;
		double fee;
		try{
			fee = Double.parseDouble(request.getParameter("fee"));
			datetime = createTime(request.getParameter("datetime"));
			deadline = createTime(request.getParameter("deadline"));
		}catch(NumberFormatException e){
			showMessage(request,response,"在日期时间和费用文本框中输入了非数字字符。");
			return;
		}catch (ParseException e) {
			showMessage(request,response,"输入的日期不正确。");
			return;
		}
		Meeting meeting = new Meeting(id,title,address,datetime,fee,deadline);
		boolean retValue = util.addOrUpdate(meeting);
		if(retValue){
			if(id == -1) success(request,response,"添加新会议成功！");
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
		
		out.print("<script>window.location.href='"+request.getContextPath()+"/meeting/'</script>");
	}
	
	private Timestamp createTime(String s) throws ParseException{
		SimpleDateFormat df;
		if(s.length() == 10) df = new SimpleDateFormat("yyyy-MM-dd");
		else df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		df.setLenient(false);
		return new Timestamp(df.parse(s).getTime());
	}
}
