<%@ page language="java" pageEncoding="UTF-8" %>
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
<div style="background: url(images/top.jpg);width: 980px; height: 224px">
<br><br><font size="20px" color="#ff0000" style="font-style: italic;">&nbsp;&nbsp;&nbsp;Windows 小游戏研究学会</font>
</div>
	
</body>
</html>
