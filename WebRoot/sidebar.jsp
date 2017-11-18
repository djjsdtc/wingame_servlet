<%@ page language="java" pageEncoding="UTF-8" import="org.wingame.util.UserUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String) request.getSession().getAttribute("username");
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
UserUtil util = new UserUtil(conn);
UserUtil.Role role = util.getCurrentRole(request);
String query = request.getQueryString();
if(query == null) query = ""; else query = "?" + query;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href=<%=basePath%> >
  </head>
  
  <body><table border="0" width="230" cellspacing="0px">
<tbody><tr>
<td background="images/side_top.jpg" height="29"><br></td></tr>
<tr>
<td background="images/side_main.jpg">
<%if(username == null) out.println("&nbsp;&nbsp;&nbsp;&nbsp;欢迎！初来乍到？<br>&nbsp;&nbsp;&nbsp;&nbsp;注册一个帐号吧！"); 
else out.println("&nbsp;&nbsp;&nbsp;&nbsp;欢迎您回来！<br>&nbsp;&nbsp;&nbsp;&nbsp;"+username);%>
<ul><li><a href="">首页</a></li>
<li><a href="announcement/">查看公告</a></li>
<li><a href="meeting/">查看会议</a></li><br>
<%switch(role){
	case ADMIN:%>
<li>管理工具<ul>
<li><a href="announcement/edit.jsp">新增公告</a></li>
<li><a href="meeting/edit.jsp">新增会议</a></li>
<li><a href="filemgmt/">文档审核</a></li>
<li><a href="usermgmt/deleteuser.jsp">用户管理</a></li></ul><br>
<li>帐号维护<ul>
<li><a href="usermgmt/changemail.jsp">修改邮箱</a></li>
<li><a href="usermgmt/changepassword.jsp">修改密码</a></li>
<li><a href="usermgmt/logout.do">注销登陆</a></li></ul>
<%	break;
	case USER:%>
<li><a href="fileupload/">文档上传</a></li><br>
<li>帐号维护<ul>
<li><a href="usermgmt/changemail.jsp">修改邮箱</a></li>
<li><a href="usermgmt/changepassword.jsp">修改密码</a></li>
<li><a href="usermgmt/logout.do">注销登陆</a></li></ul>
<%	break;
	case GUEST:%>
<li><a href=<%="userlogin/?redirect="+request.getServletPath()+query %>>登陆</a></li>
<li><a href="userlogin/register.jsp">注册新用户</a></li>
<li><a href="userlogin/forget.jsp">找回密码</a></li>
<%	break;
} %>
</ul></td></tr>
<tr>
<td background="images/side_bottom.jpg" height="24"><br></td></tr>
</tbody></table></body>
</html>
