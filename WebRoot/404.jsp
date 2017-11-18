<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href=<%=basePath%>>
<title>找不到您所访问的页面</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
	<table id="pagetable" align="center">
		<tbody>
			<tr>
				<td id="top" colspan="2" align="center"><jsp:include
						page="head.jsp"></jsp:include><br>
				</td>
			</tr>
			<tr>
				<td id="sidebar"><jsp:include page="sidebar.jsp"></jsp:include></td>
				<td id="context">
				<img src="images/404page.jpg" width="100%">
				</td>
			</tr>
			<tr>
				<td colspan="2"><jsp:include page="foot.jsp"></jsp:include></td>
			</tr>
		</tbody>
	</table>
	<br>
</body>
</html>
