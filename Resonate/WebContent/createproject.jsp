<%@ include file="includes/global_header.jsp" %>
<script type="text/javascript">
var numFiles = 0;
function addFileBtn() {
	$('#filesHolder').append("<input type=\"file\" name=\"file_"+ numFiles + "\" value=\"New File\" multiple />");
	numFiles++;
}

</script>
<p>Change the world, one note at a time. It all starts with a good project.</p>
<form name="cp" id="cp" action="CreateProject" method="POST" enctype="multipart/form-data">
	<input class="inputs" type="text" name="name" placeholder="Name" /><br />
	<input class="inputs" type="text" name="description" placeholder="A short description" /><br />
  	Add a Project Photo: <input class="inputs" type="file" id="photoFile" name="photo" accept=".png, .jpg, .jpeg" /><br />
	<p>Add some resources for others to build from:</p>
	<input class="inputs" type="button" onClick="addFileBtn()" value="Add A File" />
	<div id="filesHolder">
	</div>
	<% if (error != null && error.equals("SQL Error")) { %>
		<input type="submit" value="Create Project!" class="errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Uh Oh!" data-content="Something went wrong. Please try again." />
	<% } else if (error != null && error.equals("Not Logged In")) { %>
		<input type="submit" value="Create Project!" class="errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="How did you get here?" data-content="You are not logged in..." />
	<% } else { %>
		<input type="submit" value="Create Project!" class="errorNotifier" /><!-- Create Project!</button>-->
	<% } %>

</form>
<%
if (error != null && !error.equals("")) {
	//session.invalidate();
	session.removeAttribute("errorMessage");
} 
%>
<%@ include file="includes/global_footer.jsp" %>