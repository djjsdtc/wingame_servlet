<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*,org.wingame.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String strPage = request.getParameter("page");
	int pageNum = BaseSet.parseInt(strPage, 1);
	java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
	MeetingSet set = new MeetingSet(conn,pageNum,10);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href=<%=basePath%>>
<title>会议一览 - Windows 小游戏研究学会</title>
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
				<td id="context">
				会议一览<br><%if(new UserUtil(conn).getCurrentRole(request) == UserUtil.Role.ADMIN){ %>
				<a href="meeting/edit.jsp">添加新会议</a><%} %><hr>
<table border="1" width="100%" cellpadding="0" cellspacing="0">
<%while(set.moveToNext()){
BaseSet.Summary summary = set.getCurrentItem(); %>
<tr>
<td style="vertical-align: middle;"><a href=<%="meeting/detail.jsp?id=" + summary.id %>><%=summary.title %></a></td>
</tr>
<%	}%>
</table><%
	if(pageNum > 1){
	%>
		<a href=<%="meeting/?page=" + (pageNum-1) %> >上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	}
	int totalPage = set.getTotalPages();
	%>
	第<%=pageNum %>页/共<%=totalPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(pageNum < totalPage){ 
	%>
		<a href=<%="meeting/?page=" + (pageNum+1) %> >下一页</a>
	<%
	}
	set.close();
	%>

				</td>
			</tr>
			<tr>
				<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include><br></td>
			</tr>
		</tbody>
	</table>
	<br>
</body>
</html>
