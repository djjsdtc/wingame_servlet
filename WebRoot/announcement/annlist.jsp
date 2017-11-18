<%@ page language="java" import="org.wingame.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int meetingId = BaseSet.parseInt(request.getParameter("meeting"), -1);
int pageNum = BaseSet.parseInt(request.getParameter("page"),1);
java.sql.Connection conn = (java.sql.Connection) request.getServletContext().getAttribute("conn");
AnnouncementSet set = new AnnouncementSet(conn,pageNum,10,meetingId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_top" />
  </head>
  <body>
   <table border="1" width="100%" cellpadding="0" cellspacing="0">
<%while(set.moveToNext()){
BaseSet.Summary summary = set.getCurrentItem(); %>
<tr>
<td style="vertical-align: middle;"><a href=<%="announcement/detail.jsp?id=" + summary.id %>><%=summary.title %></a></td>
</tr>
<%	}%>
</table><%
	if(pageNum > 1){
	%>
		<a target="_self" href=<%="announcement/annlist.jsp?meeting=" + meetingId + "&page=" + (pageNum-1) %>>上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	}
	int totalPage = set.getTotalPages();
	%>
	第<%=pageNum %>页/共<%=totalPage %>页&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(pageNum < totalPage){ 
	%>
		<a target="_self" href=<%="announcement/annlist.jsp?meeting=" + meetingId + "&page=" + (pageNum+1) %> >下一页</a>
	<%
	}
	set.close();
	%>
  </body>
</html>
