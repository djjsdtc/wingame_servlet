<%@ page language="java" pageEncoding="UTF-8"%>
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
<title>找回密码 - Windows 小游戏研究学会</title>
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
				<form method="post" action="userlogin/resetpass.do" name="resetform"><table border="1" width="516" height="227" background="resetbg.jpg">
<tbody><tr>
<td bgColor="#0080ff"><strong><font color="#ffffff">找回密码</font></strong></td></tr>
<tr>
<td valign="top"><p>如果想重置您的密码，请输入您的用户名和注册时所留的邮箱：</p><p>用户名：<input type="text" maxlength="20" size="20" name="name"></p><p>邮箱：<input type="text" size="30" name="mail"></p><p>系统将会验证您的用户名与邮箱是否匹配，如匹配则会发送一封含有随机密码的电子邮件到您的邮箱。</p></td></tr>
<tr>
<td><input type="submit" value="发送重置邮件">&nbsp;&nbsp;&nbsp; <input type="reset" value="重填">&nbsp;&nbsp; <input type="button" onclick='window.location="userlogin/"' value="返回"></td></tr>
</tbody></table></form>
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
