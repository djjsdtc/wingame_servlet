<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改邮箱 - Windows 小游戏研究学会</title>
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
    修改邮箱地址<br><form method="post" action="usermgmt/changemail.do" name="mailform"><p>密码：&nbsp; <input type="password" size="30" name="password"></p><p>原邮箱：<input type="text" size="30" name="originmail"></p><p>新邮箱：<input type="text" size="30" name="newmail"></p><p><input type="submit" value="修改邮箱"></p></form><br>
  </td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table></body>
</html>
