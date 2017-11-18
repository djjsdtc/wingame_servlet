<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href=<%=basePath%> >
  </head>
  
  <body> 
  <div style="background: url(images/bottom.gif);width: 980px;height:146px;text-align: center;">
  版权所有&nbsp;&copy;&nbsp;Windows 小游戏研究学会<br>
  网站制作与技术支持：饼干工作室<br>版权所有&nbsp;&copy;&nbsp;2009～2014&nbsp;饼干工作室。保留所有权利。
  </div>
  </body>
</html>
