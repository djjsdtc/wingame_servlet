<%@ page language="java" import="org.wingame.util.*,org.wingame.model.*,java.sql.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String) request.getSession().getAttribute("username");
session.setAttribute("id",Integer.valueOf(request.getParameter("m_id")));
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
FileUtil util = new FileUtil(conn);
String status = util.getFileStatus(request);
Meeting meeting = new Meeting(conn,Integer.parseInt(request.getParameter("m_id")));
Timestamp deadline = meeting.m_deadline, datetime = meeting.m_datetime;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_self">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body><%if(util.getFileName(request)!=null){ %>
				当前文件：<a target="_top" href=<%="upload/"+util.getFileName(request) %>><%=util.getFileName(request) %></a><br>
				当前文件的状态：<%=status %><%}
				else out.println("您还没有上传本次会议的所需文档。"); %>
				<%if(new Timestamp(System.currentTimeMillis()).before(deadline) && !status.equals("已通过") && !status.equals("")){%>
				<form method="post" action="fileupload/delete.do">
				<input type="submit" value="删除文件">
				</form><%}if(username != null && new Timestamp(System.currentTimeMillis()).before(deadline) && !status.equals("已通过")){%>
<form method="post" action="fileupload/upload.do" enctype="multipart/form-data">
<input type="file" name="abcdefg">
<input type="submit" value="上传">
</form><%}if(new Timestamp(System.currentTimeMillis()).before(datetime) && status != null && status.equals("已通过")){ %>
<br>您已通过本会议的文档审核，可以<a target="_top" href=<%="application/?id="+meeting.m_id %>>进入预报名系统</a>进行会议预报名。
<%} %>
  </body>
</html>
