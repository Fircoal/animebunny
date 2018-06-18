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
			pageSize : 1000, //Initial pagesize
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
				friendly : "Rank",
				format : "<a href='#' target='_blank'>{0}</a>", //Used to format the data anything you want. Use {0} as placeholder for the actual data.
				sortOrder : "asc" //Data will initially be sorted by this column. Possible are "asc" or "desc"
			},
			name : {
				index : i++,
				type : "string",
				friendly : "Name"
			},
			score : {
				index : i++,
				type : "number",
				friendly : "Score"
			},
			per : {
				index : i++,
				type : "number",
				decimals : 2,
				friendly : "Percent",
				format : "{0}%"
			},
			malper : {
				index : i++,
				type : "number",
				friendly : "MAL Percent",
				decimals : 2,
				format : "{0}%"
			},
			perdiff : {
				index : i++,
				type : "number",
				friendly : "Percent difference",
				decimals : 2,
				format : "{0}%"
			},
			rankcha : {
						index : i++,
						type : "number",
						decimals : 0,
						friendly : "Rank Change"
					},
					scorecha : {
						index : i++,
						type : "number",
						decimals : 0, 
						friendly : "Score Change"
					},
					percha : {
						index : i++,
						type : "number",
						decimals : 2,
						format : "{0}%",
						friendly : "Percent Change"
					},
					malpercha : {
						index : i++,
						type : "number",
						decimals : 2,
						format : "{0}%",
						friendly : "MAL Percent Change"
					},
			malrank : {
				index : i++, //The order this column should appear in the table
				type : "number", //The type. Possible are string, number, bool, date(in milliseconds).
				friendly : "MAL Ranking"
			},
			malscore : {
				index : i++,
				type : "number",
				decimals : 2,
				friendly : "MAL Score"
			},
			pop : {
				index : i++,
				type : "number",
				friendly : "MAL Popularity"
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
		var purl = context + "/json/entriesOutput/" + $('#malid').val();
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
					entries.push(pdata[k]);
					console.log(pdata[k].rank);
					row.rank = pdata[k].rank;
					row.name = pdata[k].name;
					row.score = pdata[k].score;
					row.per = pdata[k].per * 100;
					row.malper = pdata[k].malPer * 100;
					row.perdiff = row.per - row.malper + 0.001;
					row.rankcha = pdata[k].chaNum;
					row.scorecha = pdata[k].chaScr;
					row.percha = pdata[k].chaPer;
					row.malpercha = pdata[k].chaMalPer;
					row.malrank = pdata[k].ranking;
					row.malscore = pdata[k].malScore;
					row.pop = pdata[k].pop;
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

	function changeRetInt(inte, str) {
		if (inte == 2147483647) {
			str = "NEW";
		} else if (inte > 0) {
			str = "+" + inte;
		} else {
			str = inte;
		}
	}
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
	<%@include file="profileheader.jsp"%>
	<div id="fullAnimuTable" style="width: auto"></div>

</body>
</html>