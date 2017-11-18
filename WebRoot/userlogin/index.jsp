<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String redirectFrom = request.getParameter("redirect");
	if(redirectFrom == null) redirectFrom = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href=<%=basePath%>>
<title>用户登录 - Windows 小游戏研究学会</title>
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
				<form method="post" action="userlogin/login.do">
    <table border="0" width="511" height="252" background="images/login_form.png">
<tbody><tr>
<td rowspan="4" width="47"><img border="0" src="images/login_left.png"></td>
<td><br>用户名：<br><input type="text" maxlength="20" size="30" name="username"></td>
<td rowspan="4" width="151">
    	<img border="0" src="images/login_right.png"><br></td></tr>
<tr>
<td><br>密码：<br><input type="password" size="30" name="password"></td>
</tr>
<tr>

<td><br><input type="checkbox" name="writecookie">14天内保持登陆（公共场所请勿勾选）</td>
</tr>
<tr>

<td align="center"><input type="hidden" name="redirect" value=<%=redirectFrom %>><input type="submit" value="登录" name="submit">
				<input type="button" onclick="window.location.href='userlogin/register.jsp'" value="注册新用户">
				<input type="button" onclick="window.location.href='userlogin/forget.jsp'" value="找回密码">
				</td>
				</tr>
				</tbody>
				</table>
				</form>
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
