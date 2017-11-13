<%@ include file="includes/global_header.jsp" %><br /><br />

	<p id="signup">Join a <font color="00b4a8">Universe</font> of Collaborative Artists.</p><br />

<head>
	<link rel="stylesheet" type="text/css" href="signup.css">
</head>

<form method="POST" action="Signup" >
	<div>
		<input class="centerinput inputs" type="text" name="name" placeholder="Name" required/> <br />
		<input class="centerinput inputs" type="text" name="username" placeholder="Username" required/> <br />
		<input class="centerinput inputs" type="text" name="email" placeholder="Email" required/> <br />
		<input class="centerinput inputs" type="password" name="password" placeholder="Password" required/> <br />
		<input class="centerinput inputs" type="password" name="cpassword" placeholder="Confirm Password" required/> <br />
		<% if (error != null && error.equals("User Exists")) { %>
			<button class="button2 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Sorry!" data-content="That username already exists. Please try a different one.">Sign Up</button>
		<% } else if (error != null && error.equals("SQL Error")) { %>
			<button class="button2 errorNotifier" data-toggle="popover" placement="bottom" role="tooltip" trigger="manual" title="Uh Oh!" data-content="Something went wrong. Please try again.">Sign Up</button>
		<% } else { %>
			<button class="button2 errorNotifier">Sign Up</button>
		<% } %>
		<br />
	</div>
	<p id="redirect">
		Already have an account? <a href="login.jsp" ><font color="00b4a8">Log in</font></a>
	</p>
</form>
<%@ include file="includes/global_footer.jsp" %>