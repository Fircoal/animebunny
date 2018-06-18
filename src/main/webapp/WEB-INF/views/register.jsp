<html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<body>
<%@include file="header.jsp" %>
<h2>Register!</h2>
<div id="results"></div>
<form id="regform" action="registeruser" method="post">
Username: <input type="text" name="username"><br>
Password: <input type="text" name="password"><br>
MalId: <input type="text" name="malId"><br>
<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <br> 
<input id="regbutton" type="submit" value="Register" />
</form>
</body>
</html>