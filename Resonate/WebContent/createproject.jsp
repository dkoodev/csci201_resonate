<%@ include file="includes/global_header.jsp" %>
<script type="text/javascript">
var numFiles = 0;
function addFileBtn() {
	$('#filesHolder').append("<input type=\"file\" name=\"file_"+ numFiles + "\" value=\"New File\" />");
	numFiles++;
}
</script>
<p>Change the world, one note at a time. It all starts with a good project.</p>
<form action="CreateProject" method="POST" enctype="multipart/form-data">
	<input type="text" name="name" placeholder="Name" /><br />
	<input type="text" name="description" placeholder="A short description" />
	<p>Add some resources for others to build from:</p>
	<input type="button" onClick="addFileBtn()" value="Add A File" />
	<div id="filesHolder">
	</div>
	<input type="submit" value="Create Project!" />
</form>
<%@ include file="includes/global_footer.jsp" %>