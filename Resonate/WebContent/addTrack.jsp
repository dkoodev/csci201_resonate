<%@ include file="includes/global_header.jsp" %>
<%
if (u == null) {
	response.sendRedirect("/Resonate/login.jsp");
	return;
}
%>

<div id="piano">

	<p id="catchphrase">Only<font color="00b4a8">You</font> can take the project</p>
	<p id="cpTwo">To the <font color="00b4a8">Next Level</font>.</p>
	<div id="inp">
		<form name="cp" id="cp" action="CreateTrack" method="POST" enctype="multipart/form-data">
			<input id = "t" class="inputs" type="text" name="name" placeholder="Name Your Track" /><br /><br />
		  	<div id="photo">Add a sound file: </div><br /> <input id="browse" class="inputs" type="file" name="track" accept=".mp3 .wav" /><br /><br />
			<div id="filesHolder">
			</div>
			<% if (error != null && error.equals("SQL Error")) { %>
				<input type="submit" value="Create Track!" class="button2 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Uh Oh!" data-content="Something went wrong. Please try again." />
			<% } else if (error != null && error.equals("Not Logged In")) { %>
				<input type="submit" value="Create Track!" class="button2 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="How did you get here?" data-content="You are not logged in..." />
			<% } else { %>
				<input type="submit" value="Create Track!" class="button2 errorNotifier" /><!-- Create Project!</button>-->
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