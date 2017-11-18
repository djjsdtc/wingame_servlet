<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*,org.wingame.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
	MeetingSet set = new MeetingSet(conn);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href=<%=basePath%>>
<title>公告一览 - Windows 小游戏研究学会</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<script>
function ChangeMeeting(){
	var selectBox = document.getElementById("meeting");
	var meetingId = selectBox.options[selectBox.selectedIndex].value;
	document.getElementById("annlist").src = "announcement/annlist.jsp?meeting=" + meetingId;
}
</script>
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
				公告一览<br>按会议筛选：
				<select id="meeting" onchange="ChangeMeeting()">
				<option value="all">--------全部--------</option>
				<%
				while(set.moveToNext()){
				BaseSet.Summary summary = set.getCurrentItem();%>
			<option value=<%=summary.id %>><%=summary.title %></option>
				 <%}
				 set.close();%>
				</select>
				<br>
				<%if(new UserUtil(conn).getCurrentRole(request) == UserUtil.Role.ADMIN){ %>
				<a href="announcement/edit.jsp">添加新公告</a><%} %><hr>
<iframe id="annlist" src="announcement/annlist.jsp" width="100%" height="500px" frameborder="0"></iframe>
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
