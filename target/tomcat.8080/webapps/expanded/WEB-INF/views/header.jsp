<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
.centerheader{
	font-size: 40;
	font-weight: bold;
	font-family: "Times New Roman", Times, serif;
	text-align: center;
}

</style>
</head>
<body>
<div style="background-color: #75BEFF">
<a href="${pageContext.request.contextPath}">Home</a>
Heya <%=request.getAttribute("username")%>~
<% if(request.getAttribute("username").equals("Guest")){ %>
<a href="${pageContext.request.contextPath}/login" style="position: absolute; right: 70px">Log In</a>
<a href="${pageContext.request.contextPath}/register" style="position: absolute; right: 0px">Register</a>
<% } else { %>
<a href="${pageContext.request.contextPath}/user/home" style="position: absolute; right: 70px">Settings</a>
<a href="${pageContext.request.contextPath}/logout" style="position: absolute; right: 0px">Log Out</a>
<% } %>
</div>

</body>
</html>