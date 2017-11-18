<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8"%>
<%
	String username = (String)request.getSession().getAttribute("username");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int pageNum = request.getParameter("page") != null ? (Integer.parseInt(request.getParameter("page"))) : 1;
int startRecord = (pageNum - 1) * 10;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href=<%=basePath%> >
    <title>用户管理 - Windows 小游戏研究学会</title>
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
    用户管理<br><br>
    <table border="1"><tbody>
		<tr>
			<td width="150px">用户名</td>
			<td width="300px">邮箱</td>
			<td width="150px">真实姓名</td>
			<td width="300px">单位</td>
			<td width="50px">操作</td>
		</tr>
<%
	Connection conn = (Connection) request.getServletContext().getAttribute("conn");
	PreparedStatement stmt = conn.prepareStatement(
		"select * from t_user order by role asc limit ?,10;");
	stmt.setInt(1, startRecord);
	ResultSet rs = stmt.executeQuery();
	while(rs.next()){
		String currUser = rs.getString("username");
		String email = rs.getString("email");
		String realname = rs.getString("realname");
		String company = rs.getString("company");
%>
 	<tr>
		<td><%=currUser %></td>
		<td><a href=<%="mailto:" + email %>><%=email %></a></td>
		<td><%=realname %></td>
		<td><%=company %></td>
		<td><%
		if(!currUser.equals(username)){
		 %>
			<form method="post" action="usermgmt/delete.do">
				<input type="hidden" value=<%=currUser %> name="username">
				<input type="submit" value="删除" onclick="return confirm('确实要删除这个用户吗？该用户的所有相关信息都将被删除。');">
			</form>
		<%
		}
		 %> 
		</td>
	</tr>
 <%
 	}
  %>
	</tbody></table><br>
	<%
	if(pageNum > 1){
	%>
		<a href=<%="usermgmt/deleteuser.jsp?page=" + (pageNum-1) %> >上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	}
	stmt = conn.prepareStatement("select count(*) from t_user;");
	(rs = stmt.executeQuery()).next();
	int totalRecord = rs.getInt(1);
	%>
	第<%=pageNum %>页/共<%=totalRecord / 10 + 1 %>页&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(totalRecord > (startRecord + 9)){
	%>
		<a href=<%="usermgmt/deleteuser.jsp?page=" + (pageNum+1) %> >下一页</a>
	<%
	}
	rs.close();
	%>
	</td></tr>
<tr>
<td colspan="2"><jsp:include page="../foot.jsp"></jsp:include></td>
</tr>
</tbody></table>
  </body>
</html>
