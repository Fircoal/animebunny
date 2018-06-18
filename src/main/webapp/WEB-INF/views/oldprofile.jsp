<%@ page import="fluffybunny.malbunny.entity.*"%>
<%@ page import="fluffybunny.malbunny.utils.UtilityBunny"%>
<%@ page import="java.util.*"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="jquery.watable.js"></script>
<link rel="stylesheet" href="watable.css">
<link rel="stylesheet" href="style.css">
<style>
.watching{
	background-color: #33FF77
}
.onhold{
	background-color: #FF7733
}
.dropped{
	background-color: #FF3333
}
.completed{
	background-color: #6666FF
}
</style>
<body>
	<table>
		<tr>
			<td>Username</td>
			<td><%=bunny.getUsername() %></td>
		</tr>
				<tr>
			<td>Average</td>
			<td><%=bunny.getAverage() %></td>
		</tr>
				<tr>
			<td>Completed</td>
			<td><%=bunny.getCompleted() %></td>
		</tr>
				<tr>
			<td>Watching</td>
			<td><%=bunny.getWatching() %></td>
		</tr>
						<tr>
			<td>On-Hold</td>
			<td><%=bunny.getOnHold() %></td>
		</tr>
						<tr>
			<td>Dropped</td>
			<td><%=bunny.getDropped() %></td>
		</tr>
	</table>		
		<table class="table">
		<tr>
		<th>Score</th>
		<% for(int i = 0; i < UtilityBunny.popGuideLabel.length; i++){
			%>
			<th><%=UtilityBunny.popGuideLabel[i] %></th>
		<% } %>
		</tr>
		<% int[][] poptable = bunny.getPopTable(); 
			for(int i = 10; i > -1; i--){
				%>
				<tr>
				<td><b><%=i %></b></td>
				<%
				for(int k = 0; k < poptable[i].length; k++){
				%>
				<td><%=poptable[i][k] %></td>
				<% } %>
				</tr>
		<% } %>
		<tr>
		<td><b>Average</b>
		<% for(int k = 0; k < poptable[11].length; k++){
		%> 
		<td><b><%=String.format("%1$,.2f", (double)poptable[11][k]/100) %></b></td>
		<%} %>
		</tr>
		<tr>
		<td><b>Count</b>
		<% for(int k = 0; k < poptable[12].length; k++){
		%> 
		<td><b><%=poptable[12][k] %></b></td>
		<%} %>
		</tr>
		</table>

	
	
	
	
	<table class="watable table table-striped table-hover table-bordered">
	<thead>
		<tr class="sort">
		<th><a class="pull left" href="#">Rank</a></th>
		<th><a class="pull left" href="#">Name</a></th>
		<th>Score</th>
		<th>Percent</th>
		<th>MAL Percent</th>
		<th>Rank Change</th>
		<th>Score Change</th>
		<th>Percent Change</th>
		<th>MAL Percent Change</th>
		<th>MAL Ranking</th>
		<th>MAL Score</th>
		<th>MAL Popularity</th>
		</tr>
		</thead>
		<% 
			for (Entry entry : entries) {
				Anime ani = entry.getAnime();
				
		%>
		<tr class="odd <%=entry.getStatus()%>">
			<td><%=entry.displayRank()%></td>
			<td><%=entry.getName()%></td>
			<td><%=entry.displayScore()%></td>
			<td><%=entry.displayPer()%></td>
			<td><%=entry.displayMalPer()%></td>
			<td><%=entry.displayChaNum()%></td>
			<td><%=entry.displayChaScr()%></td>
			<td><%=entry.displayChaPer()%></td>
			<td><%=entry.displayChaMalPer()%></td>
			<td><%=ani.displayRanking()%></td>
			<td><%=ani.displayScore()%></td>
			<td><%=ani.displayPop()%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>