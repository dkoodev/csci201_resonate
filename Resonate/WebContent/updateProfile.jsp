<%@ include file="includes/global_header.jsp" %><br /><br />

	<p id="update"><font color="00b4a8">Edit Your Profile</font></p><br />

<head>
	<link rel="stylesheet" type="text/css" href="updateProfile.css">
</head>
<%
String name = u.getName();
String username = u.getUsername();
String email = u.getEmail();
String password = u.getPassword();

%>
<form method="POST" action="UpdateAccount" >
	<div>
		<input class="centerinput inputs" type="text" name="name" value=<%=name%> required/> <br />
		<input class="centerinput inputs" type="text" name="username" value=<%=username%> required/> <br />
		<input class="centerinput inputs" type="text" name="email" value=<%=email%> required/> <br />
		<input class="centerinput inputs" type="text" name="password" value=<%=password%> required/> <br />
		<input class="centerinput inputs" type="text" name="cpassword" value=<%=password%> required/> <br />
		<% if (error != null && error.equals("User Exists")) { %>
			<button class="button2 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Sorry!" data-content="That username already exists. Please try a different one.">Sign Up</button>
		<% } else if (error != null && error.equals("SQL Error")) { %>
			<button class="button2 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Uh Oh!" data-content="Something went wrong. Please try again.">Sign Up</button>
		<% } else { %>
			<button class="button2 errorNotifier">Update</button>
		<% } %>
		<br />
	</div>
</form>
<%
if (error != null && !error.equals("")) {
	//session.invalidate();
	session.removeAttribute("errorMessage");
}
%>
<%@ include file="includes/global_footer.jsp" %>