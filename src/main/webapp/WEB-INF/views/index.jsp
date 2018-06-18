<%@ page import="java.util.ArrayList"%>
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
var context = "${pageContext.request.contextPath}";
	$(document).ready(function() {
		$("#loadButton").click(function() {
			var malUser = $("#username").val();
			window.location.href = context + "/load/" + malUser;
		});
		$("#profileButton").click(function() {
			var malUser = $("#username").val();
			window.location.href = context + "/bunny/" + malUser;
		});
	});
</script>
<head>
<title>Malbunny~</title>
<style type="text/css">
#mainloader {
	background-color: #CCFDFF;
	margin: auto;
    width: 50%;
    border: 3px solid green;
    padding: 10px;
    text-align: center;
}

#info {
	background-color: #CCFDFF;
	font-size: 10px;
	margin: auto;
    width: 50%;
    border: 3px solid green;
    padding: 10px;
    text-align: center;
}
img {
	max-width: 100%;
    height: auto;
    border-radius: 8px;
}
</style>
</head>
<body style="background-color: #DDFDFF">
	<%@include file="header.jsp"%>
	<div id="mainloader">
	<form id="inputform" action="profile" method="get">
		<a href="https://imgur.com/KMCLSrl"><img src="https://i.imgur.com/KMCLSrl.jpg" title="source: imgur.com" /></a>
		<br>
		<input type="text" id="username"><br>
		<!--  <input type="button" id="loadButton" value="Load Profile"><br>-->
		<input type="button" id="profileButton" value="View Profile"><br>
	</form>
	<a href="${pageContext.request.contextPath}/insertyourshittastehere">Want to get your anime stats? Insert your MAL xml here!</a>
	</div>
	<div id="info">
		<a href="https://imgur.com/2zi9Fs2"><img src="https://i.imgur.com/2zi9Fs2.jpg" title="source: imgur.com" /></a><br>
		<p>Welcome to Malbunny~ Malbunny is a site where you get various stats about your anime consumption and ratings!</p>
		<p>The process is simple, just type a username in the search bar above and Malbunny with calculate their updated information!</p>
		<p>If you'd like to feel free to sign up. With that you can set your own defaults and the like, as well as rank your anime~</p>
	</div>
	
	
	<br> <a href="http://localhost:8080/bunnytest3/user/reorder">Rank</a>
</body>
</html>