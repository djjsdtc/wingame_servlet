<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href=<%=basePath%> >
    <title>修改密码 - Windows 小游戏研究学会</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  <body>
   <table id="pagetable" align="center">
<tbody><tr>
<td id="top" colspan="2" align="center"><jsp:include page="../head.jsp"></jsp:include><br></td>
			</tr>
<tr>
<td id="sidebar"><jsp:include page="../sidebar.jsp"></jsp:include></td>
<td id="context">
    修改密码<br><form method="post" action="usermgmt/changepassword.do" name="changeform"><p>原密码：<input type="password" size="20" name="origin"></p><p>新密码：<input type="password" size="20" name="new1"></p><p>确认新密码：<input type="password" size="20" name="new2"></p><p><input type="submit" value="提交"><br></p></form>注：密码长度至少为6<br>
  </td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table></body>
</html>
