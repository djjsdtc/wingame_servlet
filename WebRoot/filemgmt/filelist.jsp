<%@ page language="java" import="org.wingame.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int meetingId = BaseSet.parseInt(request.getParameter("meeting"), -1);
int pageNum = BaseSet.parseInt(request.getParameter("page"),1);
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
FileSet set = new FileSet(conn,pageNum,10,meetingId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_self" />
  </head>
  <body>
   <table border="1" width="100%" cellpadding="0" cellspacing="0">
   <tr>
<td style="vertical-align: middle;">文件名</td>
<td style="vertical-align: middle;">上传用户</td>
<td style="vertical-align: middle;" width="100px">文档状态</td>
<td width="200px">操作</td></tr>
<%while(set.moveToNext()){
FileSet.FileItem item = set.getCurrentFile(); %>
<tr>
<td style="vertical-align: middle;"><a target="_top" href=<%="upload/" + item.filename %>><%=item.filename %></a></td>
<td style="vertical-align: middle;"><%=item.username %></td>
<td style="vertical-align: middle;" width="100px"><%=item.status %></td>
<td width="200px"><%if(item.status.equals("待审核")){ %>
<form method="post" action="filemgmt/verify.do"><input type="hidden" name="m_id" value=<%=item.m_id %>>
<input type="hidden" name="username" value=<%=item.username %>><input type="radio" name="status" value="p" checked="checked">通过
<input type="radio" name="status" value="f">不通过<input type="submit" value="提交"></form><%} %></td>
</tr>
<%	}%>
</table><%
	if(pageNum > 1){
	%>
		<a target="_self" href=<%="filemgmt/filelist.jsp?meeting=" + meetingId + "&page=" + (pageNum-1) %>>上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	}
	int totalPage = set.getTotalPages();
	%>
	第<%=pageNum %>页/共<%=totalPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(pageNum < totalPage){ 
	%>
		<a target="_self" href=<%="filemgmt/filelist.jsp?meeting=" + meetingId + "&page=" + (pageNum+1) %> >下一页</a>
	<%
	}
	set.close();
	%>
  </body>
</html>
