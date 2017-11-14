<%@ include file="includes/global_header.jsp" %><br /><br /><br />
<% String authName = request.getParameter("authenticate"); %>
<div>
	<img id="loginlogo" src="images/logo.png" /><br />
</div>

<form method="POST" action="Login" >
	<div>
		<input class="centerinput inputs" type="text" name="username" placeholder="Username" required /> <br />
		<input class="centerinput inputs" type="password" name="password" placeholder="Password" required /> <br />
	</div>
	<div>	
	<% if (authName != null && !authName.equals("")) { %>
		<button class="button1 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="You're In!" data-content="Congratulations, <%= authName %>! Feel free to log in now.">Log In</button><br />
	<% } else if (error != null && error.equals("Login Failed")) { %>
		<button class="button1 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Uh Oh!" data-content="Sorry, that username and password doesn't exist.">Log In</button><br />
	<% } else if (error != null && error.equals("SQL Error")) { %>
		<button class="button1 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Uh Oh!" data-content="Something went wrong. Please try again.">Log In</button><br />
	<% } else { %>
		<button class="button1 errorNotifier">Log In</button><br />
	<% } %>
	</div>
	<p id="redirect">
		Don't have an account? <a href="signup.jsp" ><font color="00b4a8">Sign Up</font></a>
	</p>
</form>


<%@ include file="includes/global_footer.jsp" %>
