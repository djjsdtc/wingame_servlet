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
<title>注册 - Windows 小游戏研究学会</title>
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
				<table border="1" width="439" height="240" bgColor="#ffffff">
<tbody><tr>
<td colspan="2" bgColor="#0080ff"><font color="#ffffff"><strong>用户注册</strong></font><a href="login.jsp"><font color="#ffffff"><strong></strong></font></a><br></td>
</tr>
<tr>
<td valign="top"><form method="post" action="userlogin/register.do" name="registerform">
用户名：<input type="text" maxlength="20" size="20" name="name"><br>
密码：<input type="password" size="20" name="pswd1"><br>
确认密码：<input type="password" name="pswd2"><br>
电子邮箱：<input type="text" name="email"><br>
真实姓名：<input type="text" name="realname"><br>
单位：<input type="text" name="company" size="40"><br><br>
<input type="submit" name="" value="注册">&nbsp;&nbsp;&nbsp;
<input type="reset" value="重新填写">&nbsp;&nbsp;
<input type="button" value="返回" onclick='window.location="userlogin/"'></p></form></td>
<td valign="top" width="40%">填表说明：<br>1、所有选项均为必填项<br>2、密码长度至少为6<br>3、电子邮箱请认真填写，此为找回密码时的惟一依据<br></td></tr>

</tbody></table></td>
			</tr>
			<tr>
				<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
			</tr>
		</tbody>
	</table>
	<br>
</body>
</html>
