<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*,org.wingame.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int id = BaseSet.parseInt(request.getParameter("id"), 0);
if(id == 0) response.sendRedirect(path + "/meeting/");
session.setAttribute("id", id);
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
Meeting meeting = new Meeting(conn,id);
UserUtil util = new UserUtil(conn);
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><%=meeting.getM_title() + " - Windows 小游戏研究学会" %></title>
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
会议详情<br><hr>
<%if(util.getCurrentRole(request) == UserUtil.Role.ADMIN){ %>
<form method="post" action="meeting/removemeeting.do">
<input type="button" onclick=<%="window.location.href='meeting/edit.jsp?id=" + id + "'"%> value="修改">
<input type="submit" onclick="return confirm('确实要删除此项会议记录吗？');" value="删除"></form>
<%} %>
会议名称：<%=meeting.getM_title() %><br>
会议地点：<%=meeting.getM_address() %><br>
会议时间：<%=meeting.m_datetime.toLocaleString() %><br>
参会费用：<%=meeting.m_fee %>元<br>
预报名人数：<%=meeting.getAttendCount() %><br>
<%if(util.getCurrentRole(request) == UserUtil.Role.ADMIN){%>
<a href=<%="meeting/excelusertable.do?m_id=" + id %>>下载已报名用户列表（Excel 2003 文件格式）</a>
<%}if(util.getCurrentRole(request) == UserUtil.Role.USER){out.print("<br>");
if(meeting.isAttending(request)) out.print("您已经报名参加了本次会议，参会费支付方式是到场支付。");
else out.print("您还没有报名参加本次会议。"); 
out.println("<br>");}%>
<br>参加本次会议需要您上传本次会议公告中所提到的有关材料，材料上传截止时间为：<%=meeting.m_deadline.toLocaleString() %>。<br>
材料审核通过后，您可以进行会议预报名。<%if(util.getCurrentRole(request) == UserUtil.Role.USER){ %>
<a href="fileupload/">现在就去上传材料</a><%} %><br><br>
会议有关公告：<br><iframe frameborder="0" src=<%="announcement/annlist.jsp?meeting=" + id %> width="100%" height="300px"></iframe>
</td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table></body>
</html>
