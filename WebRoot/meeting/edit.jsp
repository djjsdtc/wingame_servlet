<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int id = BaseSet.parseInt(request.getParameter("id"), -1);
session.setAttribute("id", id);
Meeting meeting;
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
if(id == -1) meeting = new Meeting();
else meeting = new Meeting(conn,id);
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>创建或修改会议 - Windows 小游戏研究学会</title>
    <base href=<%=basePath%> >
    <link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  
  <body> <table id="pagetable" align="center">
<tbody><tr>
<td id="top" colspan="2" align="center"><jsp:include page="../head.jsp"></jsp:include><br></td>
			</tr>
<tr>
<td id="sidebar"><jsp:include page="../sidebar.jsp"></jsp:include></td>
<td id="context">
    创建或修改会议<br><form method="post" action="meeting/meeting.do">
<br>
会议名称：<input type="text" name="title" value=<%=meeting.getM_title() %>><br>
会议地址：<input type="text" name="address" value=<%=meeting.getM_address() %>><br>
会议时间：<input type="datetime-local" name="datetime" value=<%=Meeting.translateTimestamp(meeting.m_datetime,true) %>><br>
会议费用：<input type="text" name="fee" value=<%=meeting.m_fee %>>元<br>
文档上传截止时间：<input type="date" name="deadline" value=<%=Meeting.translateTimestamp(meeting.m_deadline,false) %>>（不含）<br><br>
<input type="submit" value="确认">&nbsp;&nbsp;&nbsp;
<input type="reset" value="清空" onclick="return confirm('确定要清空吗？');">&nbsp;&nbsp;&nbsp;
<input type="button" value="返回" onclick="window.history.back()">
</form><br>
  </td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table></body>
</html>
