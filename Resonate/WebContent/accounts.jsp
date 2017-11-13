<%@ include file="includes/global_header.jsp" %><br /><br />

	<p id="signup">Change what you want to update</p><br />

<head>
	<link rel="stylesheet" type="text/css" href="accounts.css">
</head>

<form method="POST" action="Signup" >
	<div>
		<input id="centerinput" type="text" name="name" value = u.getName() /> <br />
		<input id="centerinput" type="text" name="username" placeholder="Username" /> <br />
		<input id="centerinput" type="text" name="email" placeholder="Email" /> <br />
		<input id="centerinput" type="password" name="password" placeholder="Password" /> <br />
		<input id="centerinput" type="password" name="cpassword" placeholder="Confirm Password" /> <br />
		<button class="button2">Update</button><br />
	</div>
	<p id="redirect">
		Already have an account? <a href="login.jsp" ><font color="00b4a8">Log in</font></a>
	</p>
</form>
<%@ include file="includes/global_footer.jsp" %>