<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int id = BaseSet.parseInt(request.getParameter("id"), -1);
session.setAttribute("id", id);
Announcement announcement;
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
if(id == -1) announcement = new Announcement();
else announcement = new Announcement(conn,id);
MeetingSet set = new MeetingSet(conn);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>创建或修改公告 - Windows 小游戏研究学会</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  
  <body><table id="pagetable" align="center">
<tbody><tr>
<td id="top" colspan="2" align="center"><jsp:include page="../head.jsp"></jsp:include><br></td>
			</tr>
<tr>
<td id="sidebar"><jsp:include page="../sidebar.jsp"></jsp:include></td>
<td id="context">创建或修改公告<br><form method="post" action="announcement/announce.do">
<p>公告标题：<input type="text" size="40" name="title" value=<%=announcement.getAnn_title() %>></p>
<p>关联会议：<select name="meeting">
				<%
				while(set.moveToNext()){
				BaseSet.Summary summary = set.getCurrentItem();
				if(summary.title != null){%>
			<option value=<%=summary.id %> <%if(summary.id == announcement.m_id) out.println("selected=\"selected\""); %>><%=summary.title %></option>
				 <%}}
				 set.close();%>
				</select><br></p><p>公告内容：<br></p><p><textarea wrap="virtual" cols="40" rows="10" name="context"><%=announcement.getAnn_text() %></textarea></p>
<p><input type="submit" value="确认">&nbsp;&nbsp;&nbsp;
<input type="reset" value="清空" onclick="return confirm('确定要清空吗？');">&nbsp;&nbsp;&nbsp;
<input type="button" value="返回" onclick="window.history.back()"></p></form><br></td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table>
  </body>
</html>
