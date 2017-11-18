<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.util.*,org.wingame.model.*,java.sql.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
	ApplicationUtil util = new ApplicationUtil(conn);
int id = BaseSet.parseInt(request.getParameter("id"), 0);
if(id == 0) response.sendRedirect(path + "/meeting/");
request.getSession().setAttribute("id",id);
	Meeting meeting = new Meeting(conn,id);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href=<%=basePath%>>
<title>会议预报名系统 - Windows 小游戏研究学会</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
	<table id="pagetable" align="center">
		<tbody>
			<tr>
				<td id="top" colspan="2" align="center"><jsp:include
						page="../head.jsp"></jsp:include><br>
				</td>
			</tr>
			<tr>
				<td id="sidebar"><jsp:include page="../sidebar.jsp"></jsp:include></td>
				<td id="context">会议预报名系统<hr>
<%if(!util.checkQuality(request) || !new Timestamp(System.currentTimeMillis()).before(meeting.m_datetime)){ %>
	很抱歉，会议预报名系统不允许通过浏览器直接键入地址进行访问。
<%}else{
	if(util.hasAttended(request)){  %>
	您已经预报名了会议<%=meeting.m_title %>.，请按如下时间地点准时参加。<br>由于您选择了到场支付会费，届时请携带足够的现金。
	<br><br>会议地点：<%=meeting.m_address %><br>会议时间：<%=meeting.m_datetime.toLocaleString() %><br>
	会议费用：<%=meeting.m_fee %>元
<%	}else{ %>
	您正在预报名会议：<%=meeting.m_title %>。<br>请再次确认以下信息以确保您有时间参会：<br>
	会议地点：<%=meeting.m_address %><br>会议时间：<%=meeting.m_datetime.toLocaleString() %><br>
	会议费用：<%=meeting.m_fee %>元<br><br>如果您确认参加此会议，请选择您的参会费用支付方式：<br>
	<form method="post" action="application/apply.do">
	<input type="button" value="在线支付" disabled="disabled">
	&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="到场支付"></form>
<%	}
}%>	</td>
			</tr>
			<tr>
				<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include><br></td>
			</tr>
		</tbody>
	</table>
	<br>
</body>
</html>
