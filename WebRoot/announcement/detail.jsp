<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*,org.wingame.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int id = BaseSet.parseInt(request.getParameter("id"), 0);
if(id == 0) response.sendRedirect(path + "/announcement/");
session.setAttribute("id", id);
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
Announcement announcement = new Announcement(conn,id);
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><%=announcement.getAnn_title() + " - Windows 小游戏研究学会" %></title>
    <base href=<%=basePath%> >
    <link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  
  <body> <table id="pagetable" align="center">
<tbody><tr>
<td id="top" colspan="2" align="center"><jsp:include page="../head.jsp"></jsp:include><br></td>
			</tr>
<tr>
<td id="sidebar"><jsp:include page="../sidebar.jsp"></jsp:include></td>
<td id="context"><%=announcement.getAnn_title() %>
<br>关联会议：<a href=<%="meeting/detail.jsp?id=" + announcement.m_id %>><%=announcement.getMTitle() %></a><br>
<%if(new UserUtil(conn).getCurrentRole(request) == UserUtil.Role.ADMIN){ %>
<form method="post" action="announcement/remove.do">
<input type="button" onclick=<%="window.location.href='announcement/edit.jsp?id=" + id + "'"%> value="修改">
<input type="submit" onclick="return confirm('确实要删除此公告吗？');" value="删除"></form>
<%} %>
<hr><%=announcement.ann_text.replace("\n", "<br>").replace(" ","&nbsp;") %>
</td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table></body>
</html>
