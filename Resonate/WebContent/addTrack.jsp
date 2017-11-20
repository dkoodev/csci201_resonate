<%@ include file="includes/global_header.jsp" %>
<%
if (u == null) {
	response.sendRedirect("/Resonate/login.jsp");
	return;
}
%>

<div id="piano">

	<p id="catchphrase">Change the <font color="00b4a8">World</font>, One Note at a Time</p>
	<p id="cpTwo">It All Starts With a Good <font color="00b4a8">Track</font></p>
	<div id="inp">
		<form name="cp" id="cp" action="CreateTrack" method="POST" enctype="multipart/form-data">
			<input id = "t" class="inputs" type="text" name="name" placeholder="Name" /><br /><br />
		  	<p id="photo">Add a mp3: </p><input class="inputs" type="file" id="photoFile" name="track" accept=".mp3" /><br />
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
	</div>
</div>	
<%
if (error != null && !error.equals("")) {
	//session.invalidate();
	session.removeAttribute("errorMessage");
} 
%>
<%@ include file="includes/global_footer.jsp" %>