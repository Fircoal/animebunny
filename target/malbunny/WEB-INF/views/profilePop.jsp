<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@include file="profileheader.jsp"%>
	<table class="table">
		<tr>
			<th>Score</th>
			<%
				for (int i = 0; i < UtilityBunny.popGuideLabel.length; i++) {
			%>
			<th><%=UtilityBunny.popGuideLabel[i]%></th>
			<%
				}
			%>
		</tr>
		<%
			int[][] poptable = bunny.getPopTable();
			for (int i = 10; i > -1; i--) {
		%>
		<tr>
			<td><b><%=i%></b></td>
			<%
				for (int k = 0; k < poptable[i].length; k++) {
			%>
			<td><%=poptable[i][k]%></td>
			<%
				}
			%>
		</tr>
		<%
			}
		%>
		<tr>
			<td><b>Average</b> <%
 	for (int k = 0; k < poptable[11].length; k++) {
 %>
			<td><b><%=String.format("%1$,.2f", (double) poptable[11][k] / 100)%></b></td>
			<%
				}
			%>
		</tr>
		<tr>
			<td><b>Count</b> <%
 	for (int k = 0; k < poptable[12].length; k++) {
 %>
			<td><b><%=poptable[12][k]%></b></td>
			<%
				}
			%>
		</tr>
	</table>
</body>
</html>