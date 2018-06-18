<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="fluffybunny.malbunny.entity.*"%>
<%@ page import="fluffybunny.malbunny.utils.UtilityBunny"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
#headerdiv {
	border-radius: 10px;
	background-color: #CCFDFF;
	margin: auto;
    width: 100%;
    border: 3px solid blue;
    padding: 10px;
    text-align: center;
    font-family: "Times New Roman", Times, serif;
}
img {
	max-width: 100%;
    height: auto;
    border-radius: 8px;
}
.headertable{
	width: 100%;
	margin: auto;
	padding: 8px;
	text-align: center;
}
.headerrow{
	height: 15px;
}
.headerhead{
	font-size: 25;
	font-weight: bold;
	font-family: "Times New Roman", Times, serif;
}
.bigger{
	font-size: 40;
}
.bodyclass{
	background-color: #F4FFFF;
}
</style>
</head>
<body>
	<%@include file="header.jsp"%>
	<%
		Profile bunny = (Profile) request.getAttribute("profileData");
		List<Entry> entries = bunny.getEntries();
		List<Anime> animes = bunny.getAnime();
	%>
	<div id="headerdiv">
	<input type="hidden" id="malid" value="<%=bunny.getId() %>">
	<input type="hidden" id="username" value="<%=bunny.getUsername() %>">
	<table id="headertable" class="headertable">
		<tr class="headerrow">
			<td colspan="4" class="bigger headerhead"><%=bunny.getUsername()%></td>
			<td colspan="4" rowspan="7"><img src="https://myanimelist.cdn-dena.com/images/userimages/<%=bunny.getId() %>.jpg"></td>
		</tr>
		<tr class="headerrow">
			<td colspan="2" class="headerhead">Average</td>
			<td colspan="2" class="headerhead"><%=bunny.getAverage()%></td>
		</tr>
		<tr class="headerrow">
			<td>Completed</td>
			<td><%=bunny.getCompleted()%></td>
			<td>Watching</td>
			<td><%=bunny.getWatching()%></td>
		</tr><tr class="headerrow">
			<td>On-Hold</td>
			<td><%=bunny.getOnHold()%></td>
			<td>Dropped</td>
			<td><%=bunny.getDropped()%></td>
		</tr>
		<tr class="headerrow">
			<td><a href="${pageContext.request.contextPath}/bunny/<%=bunny.getUsername() %>">Full List</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/ranking/<%=bunny.getUsername() %>">Ranked</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/pop">Popularity</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/score">Score</a></td>
		</tr>
		<tr class="headerrow">
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/genres">Genres</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/studio">Studio</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/producers">Producers</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/episodes">Episodes</a></td>
		</tr>
		<tr class="headerrow">
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/duration">Duration</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/source">Source</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/type">Type</a></td>
			<td><a href="${pageContext.request.contextPath}/bunny/cat/<%=bunny.getUsername() %>/favorites">Favorites</a></td>
		</tr>
	</table>
	</div>

</body>
</html>