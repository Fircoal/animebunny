<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="fluffybunny.malbunny.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home</title>
</head>
<body>
<div id="headerdiv">
	<table id="table">
		<tr><td>User Links</td></tr>
		<tr><td><a href="${pageContext.request.contextPath}/user/reorder">Rank your list!</a></td></tr>
		<tr><td><a href="${pageContext.request.contextPath}/user/combine">Combine a previous ranking!</a></td></tr>
		<tr><td><a href="${pageContext.request.contextPath}/user/past">Give a previous ranking for our database!</a></td></tr>
		<tr><td><a href="${pageContext.request.contextPath}/user/prevReset">Reset data to the previous snapshot!</a></td></tr>
	</table>
	</div>

</body>
</html>