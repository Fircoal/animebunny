<%@ page import="fluffybunny.malbunny.entity.*"%>
<%@ page import="fluffybunny.malbunny.utils.*"%>
<%@ page import="java.util.*"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
#sortable {
	list-style-type: none;
	margin: 2;
	padding: 2;
	width: 60%;
}

#sortable li {
	margin: 0 3px 3px 3px;
	padding: 0.4em;
	padding-left: 1.5em;
	font-size: 1.4em;
	height: 18px;
}

#sortable li span {
	position: absolute;
	margin-left: -1.3em;
}

.trueRow {
	background-color: #99FF99;
}

.falseRow {
	background-color: #FF9999;
}
.hiddenbutton {
	 visibility: hidden;
}
#ranking    {
	width: 80%;
	float: left;
	background-color: #CCF1FF;
}

#sidebar    {
	height: 100%;
	width: 20%;
	margin-left: 80%;
	background-color: #D0C1FF;
    position: fixed;
}

table, th, td {
	border: 1px solid black;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/sorttable.js"></script>
<script>
var context = "${pageContext.request.contextPath}";
var csrfParam = "${_csrf.parameterName}";
var csrfToken = "${_csrf.token}";
	$(function() {
		$("#sortable").sortable();
		$("#sortable").disableSelection();
	});
$(document).ready(function() {
	/*function getCookie(c_name) {
        if(document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=");
            if(c_start != -1) {
                c_start = c_start + c_name.length + 1;
                c_end = document.cookie.indexOf(";", c_start);
                if(c_end == -1) c_end = document.cookie.length;
                return unescape(document.cookie.substring(c_start,c_end));
            }
        }
        return "";
    }

    $(function () {
        $.ajaxSetup({
            headers: {
                "X-CSRFToken": getCookie("csrftoken")
            }
        });
    });*/
	/*$("body").bind("ajaxSend", function(elm, xhr, s){
		   if (s.type == "POST") {
		      xhr.setRequestHeader(csrfParam, csrfToken);
		   }
		});*/
	$("#draftbutton").click(function(){
		update();
		$("#buttonbutton").click();
		/*var purl = context + "/user/draft";
		console.log(JSON.stringify(draftary));
		$.ajax({
			url : purl,
			method : 'POST',
		    headers: {
		        'Csrf-Token': csrfToken
		    },
			data : JSON.stringify(draftary),
			contentType : 'text/html',
			success : function(pdata){
				$('#savemessage').html(pdata)
			},
			error : function(data, status, er) {
				$('#savemessage').html("Error in draft saving.")
			}
		});//end of ajax call*/


	});

		$("#finishbutton").click(function(){
			update();
			$("#submitbutton").click();
		});

	$("#updatebutton").click(function(){
		update();
	});


	$(".falseRow").click(function(event){
		console.log("change");
		console.log($(this).attr("id") + "f");
		if(document.getElementById($(this).attr("id")).classList.contains('falseRow')){
			document.getElementById($(this).attr("id")).classList.add('trueRow');
			document.getElementById($(this).attr("id")).classList.remove('falseRow');
			$("#bool" + $(this).attr("id")).val("true");
		} else {
			document.getElementById($(this).attr("id")).classList.add('falseRow');
			document.getElementById($(this).attr("id")).classList.remove('trueRow');
			$("#bool" + $(this).attr("id")).val("false");
		}
		
	});

	$(".trueRow").click(function(event){
		console.log("change");
		console.log($(this).attr("id") + "t");
		if(document.getElementById($(this).attr("id")).classList.contains('trueRow')){
			document.getElementById($(this).attr("id")).classList.add('falseRow');
			document.getElementById($(this).attr("id")).classList.remove('trueRow');
			$("#bool" + $(this).attr("id")).val("false");
		} else {
			document.getElementById($(this).attr("id")).classList.add('trueRow');
			document.getElementById($(this).attr("id")).classList.remove('falseRow');
			$("#bool" + $(this).attr("id")).val("true");
		}
	});
});

function update(){
	var x = document.getElementsByTagName("tr");
	var draftary = [];
	var k = 1;
	var i = 0;
	while(i < x.length){
		if($("#bool"+x[i].id).val() == "true"){
			console.log(x[i].id);
			$("#rank"+x[i].id).html(k);
			draftary.push(x[i].id);
			k++;
		}
		console.log(x[i]);
		i++;
	}
	$("#data").val(JSON.stringify(draftary));
	$("#data2").val(JSON.stringify(draftary));
}
</script>
</head>
<body>
	<div id="page">
		<div id="ranking">
			<table>
				<tr>
					<th>Rank</th>
					<th>Name</th>
					<th>Score</th>
				</tr>
				<tbody id="sortable">
					<%
						List<Entry> entries = ((Profile) request.getAttribute("profileData")).getEntries();
						for (Entry entry : entries) {
					%>
					<tr id=<%=entry.getId() %> class="<%=UtilityBunny.ranked(entry.getDraft())%>Row" data-href='url://'>
						<td id="rank<%=entry.getId() %>"><%=entry.displayDraft()%></td>
						<td><%=entry.getName()%></td>
						<td><%=entry.displayScore()%></td>
						<input type="hidden" id="bool<%=entry.getId() %>" value="<%=UtilityBunny.ranked(entry.getDraft())%>">
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
		<div id="sidebar">
		<table>
			<tr><td rowspan="5" ><img src="https://myanimelist.cdn-dena.com/images/animeimageplz.jpg"></td>
			<tr></tr><tr></tr><tr></tr><tr></tr>
			<tr><td><button id="updatebutton">Refresh Numbers</button></td></tr>
			<tr><td><button id="draftbutton">Save Draft</button></td></tr>
			<tr><td><button id="minorbutton">Save Minor Update</button></td></tr>
			<tr><td><button id="finishbutton">Finalize Ranking</button></td></tr>
		</table>
		<form action="draft" method="post">
		<input type="hidden" id="data" name="data" value="null" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <br> 
			<input type="submit" id="buttonbutton" class="hiddenbutton" value="Draft"/>
	</form>
	<form action="finalize" method="post">
		<input type="hidden" id="data2" name="data2" value="null" />
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <br> 
			<input type="submit" id="submitbutton" class="hiddenbutton" value="Submit"/>
	</form>
	
		<div id="savemessage"></div>
		</div>
	</div>

</body>
</html>