<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.model.*"%>
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
<title>文档管理 - Windows 小游戏研究学会</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<script>
function ChangeMeeting(){
	var selectBox = document.getElementById("meeting");
	var meetingId = selectBox.options[selectBox.selectedIndex].value;
	document.getElementById("annlist").src = "fileupload/selectmeeting.jsp?m_id=" + meetingId;
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
文档管理<br>按会议筛选：
				<select id="meeting" onchange="ChangeMeeting()">
				<%while(set.moveToNext()){
				BaseSet.Summary summary = set.getCurrentItem();%>
				<option value=<%=summary.id %>><%=summary.title %></option>
				 <%}
				 set.close();%>
				</select>
				<br><hr>
<iframe id="annlist" src="" width="100%" height="500px" frameborder="0"></iframe>
<script>ChangeMeeting()</script>
				</td>
			</tr>
			<tr>
				<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
			</tr>
		</tbody>
	</table>
	<br>
</body>
</html>
