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
<script type="text/javascript">
	var context = "${pageContext.request.contextPath}";
	var entries;
	$(document).ready(function() {
		var rows = [];
		var i = 0;
		var entries;
		var purl = context + "/json/entries/" + $('#malid').val();
		$.ajax({
			url : purl,
			type : 'GET',
			//contentType : 'application/json',
			//mimeType : 'application/json',
			accept: 'application/json',
			success : function(pdata) {
				var parsedJSON = JSON.parse(pdata.d);
			       for (var i=0;i<parsedJSON.length;i++) {
			            alert(parsedJSON[i].Id);
			         }
			},
			error : function(data, status, er) {
				alert("error: " + data + " status: " + status + " er:" + er);
			}
		});//end of ajax call
		console.log(entries);
		entries = JSON.parse(entries, );
		console.log(entries);
	});
</script>

<style>
.watching {
	background-color: #33FF77
}

.onhold {
	background-color: #FF7733
}

.dropped {
	background-color: #FF3333
}

.completed {
	background-color: #6666FF
}
</style>
<body>
	<%@include file="header.jsp"%>
	<%
		Profile bunny = (Profile) request.getAttribute("profileData");
		List<Entry> entries = bunny.getEntries();
		List<Anime> animes = bunny.getAnime();
	%>
	<input type="hidden" id="malid" value="<%=bunny.getId() %>">
	<table>
		<tr>
			<td>Username</td>
			<td><%=bunny.getUsername()%></td>
		</tr>
		<tr>
			<td>Average</td>
			<td><%=bunny.getAverage()%></td>
		</tr>
		<tr>
			<td>Completed</td>
			<td><%=bunny.getCompleted()%></td>
		</tr>
		<tr>
			<td>Watching</td>
			<td><%=bunny.getWatching()%></td>
		</tr>
		<tr>
			<td>On-Hold</td>
			<td><%=bunny.getOnHold()%></td>
		</tr>
		<tr>
			<td>Dropped</td>
			<td><%=bunny.getDropped()%></td>
		</tr>
	</table>
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

	<h4>Anime Details</h4>
	<div id="fullAnimuTable" style="width: auto"></div>

</body>
</html>