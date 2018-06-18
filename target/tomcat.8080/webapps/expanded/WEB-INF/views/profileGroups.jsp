<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/jquery.watable.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/watable.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/style.css">
<script type="text/javascript">
	var context = "${pageContext.request.contextPath}";
	var entries;
	$(document).ready(function() {
		//First example. No options supplied.
		$('#fullAnimuTable').WATable({
			pageSize : 50, //Initial pagesize
			pageSizePadding : false, //Pads with empty rows when pagesize is not met
			filter : false, //Show filter fields
			sorting : true, //Enable sorting
			sortEmptyLast : true, //Empty values will be shown last
			columnPicker : true, //Show the columnPicker button
			actions : { //This generates a button where you can add elements.
				filter : true, //If true, the filter fields can be toggled visible and hidden.
				columnPicker : true, //if true, the columnPicker can be toggled visible and hidden.
			},
			data : generateData()

		});

		//Generate some data

		waTable.setData(data); //Sets the data.
		//waTable.setData(data, true); //Sets the data but prevents any previously set columns from being overwritten
		//waTable.setData(data, false, false); //Sets the data and prevents any previously checked rows from being reset

		//Get the data
		var allRows = waTable.getData(false); //Returns the data you previously set.
		var checkedRows = waTable.getData(true); //Returns only the checked rows.
		var filteredRows = waTable.getData(false, true); //Returns only the filtered rows.
		var renderedRows = waTable.getData(false, false, true) //Returns only the rendered rows.

		//Set options on the fly
		var pageSize = waTable.option("pageSize"); //Get option
		//waTable.option("pageSize", pageSize); //Set option

	});

	function generateData() {

		var i = 1;
		//First define the columns
		var cols = {
			rank : {
				index : i++, //The order this column should appear in the table
				type : "number", //The type. Possible are string, number, bool, date(in milliseconds).
				friendly : "#",
				sortOrder : "asc" //Data will initially be sorted by this column. Possible are "asc" or "desc"
			},
			group : {
				index : i++,
				type : "string",
				friendly : "Source"
			},
			score : {
				index : i++,
				type : "number",
				decimals : 2,
				friendly : "Weighted"
			},
			mean : {
				index : i++,
				type : "number",
				decimals : 2,
				friendly : "Average"
			},
			median : {
				index : i++,
				type : "number",
				friendly : "Median",
				decimals : 0
			},
			count : {
				index : i++,
				type : "number",
				friendly : "Count",
				decimals : 0
			},
			rankmean : {
				index : i++,
				type : "number",
				friendly : "Rank Mean",
				decimals : 0
			},
			rankmedian : {
				index : i++,
				type : "number",
				friendly : "Rank Median",
				decimals : 0
			}
		};

		/*
		  Create the rows (This step is of course normally done by your web server). 
		  What's worth mentioning is the special row properties. See some examples below.
		  <column>Format allows you to override column format and have it formatted the way you want.
		  <column>Cls allows you to add css classes on the cell(td) element.
		  row-checkable allows you to prevent rows from being checkable.
		  row-checked allows you to pre-check a row.
		  row-cls allows you to add css classes to the row(tr) element.
		 */
		var rows = [];
		var i = 0;
		var entries = [];
		var purl = context + "/json/groupingData/" + $('#username').val() + "/" + $('#group').val();
		$.ajax({
			url : purl,
			type : 'GET',
			async : false,
			//contentType : 'application/json',
			//mimeType : 'application/json',
			accept : 'application/json',
			success : function(pdata) {
				var k = 0;
				while (k < pdata.length) {
					var row = {};
					console.log(pdata[k]);
					//entries.push(pdata[k]);
					row.rank = k + 1;
					row.group = pdata[k].name;
					row.score = pdata[k].weighted;
					row.mean = pdata[k].mean;
					row.median = pdata[k].median;
					row.count = pdata[k].ratesize;
					//row.rankwei = pdata[k].rankwei;
					row.rankmean = pdata[k].rankmean;
					row.rankmedian = pdata[k].rankmedian;
					//row["row-cls"]       = i % 3 == 0 ? "gray, anotherClass" : ""; //apply some row css classes for every 3rd row
					rows.push(row);
					k++;
				}
			},
			error : function(data, status, er) {
				alert("error: " + data + " status: " + status + " er:" + er);
			}
		});//end of ajax call

		//Create the returning object. Besides cols and rows, you can also pass any other object you would need later on.
		var data = {
			cols : cols,
			rows : rows
		};
		return data;
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
#centerdiv {
	background-color: #F8FFFF;
	margin: auto;
	border: 3px solid green;
	padding: 10px;
	max-width: 80%;
	text-align: center;
}
.groupDiv {
	border-radius: 25px;
	background-color: #F0FFFF;
	margin: auto;
	border: 3px solid #007788;
	padding: 10px;
	max-width: 80%;
	text-align: center;
}
.groupHeader {
 	border-radius: 25px;
	background-color: #CCF8FF;
	margin: auto;
	border: 3px solid #008888;
	padding: 10px;
	max-width: 80%;
	text-align: center;
}
</style>
</head>
<body>
<%@include file="profileheader.jsp"%>
	<br>
	<input type="hidden" id="group" value="<%=request.getAttribute("groupName") %>">
	<br>
	<center>
		<div id="centerDiv">
			<div id="fullAnimuTable" style="width: auto"></div>
		</div>
	</center>
	<%
		
	%>
	<%
		List<Grouping> groups = (List<Grouping>) request.getAttribute("groupData");
		for (Grouping group : groups) {
	%>
	<div class="groupDiv">
		<div class="groupHeader">
			<h1><%=group.getName()%> - 
			<%=group.getWeighted()%></h1>
		</div>
		<table class="table">
			<tr>
				<th>Rank</th>
				<th>Name</th>
				<th>Score</th>
				<th>Percent</th>
				<th>Mal Percent</th>
				<th>Mal Score</th>
				<th>Mal Pop</th>
			</tr>
			<%
				List<OutputEntry> outentries = group.getEntries();
					for (OutputEntry ent : outentries) {
			%>
			<tr>
				<td><%=ent.displayRank()%></td>
				<td><%=ent.getName()%></td>
				<td><%=ent.displayScore()%></td>
				<td><%=ent.displayPer()%></td>
				<td><%=ent.displayMalPer()%></td>
				<td><%=ent.getMalScore()%></td>
				<td><%=ent.displayPop()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<br>
	<%
		}
	%>
</body>
</html>