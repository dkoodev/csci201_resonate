<%@ include file="includes/global_header.jsp" %><br /><br />

	<p id="signup">Join a <font color="00b4a8">Universe</font> of Collaborative Artists.</p><br />

<head>
	<link rel="stylesheet" type="text/css" href="signup.css">
</head>

<form method="POST" action="Signup" >
	<div>
		<input id="centerinput" type="text" name="name" placeholder="Name" required/> <br />
		<input id="centerinput" type="text" name="username" placeholder="Username" required/> <br />
		<input id="centerinput" type="text" name="email" placeholder="Email" required/> <br />
		<input id="centerinput" type="password" name="password" placeholder="Password" required/> <br />
		<input id="centerinput" type="password" name="cpassword" placeholder="Confirm Password" required/> <br />
		<button class="button2">Sign Up</button><br />
	</div>
	<p id="redirect">
		Already have an account? <a href="login.jsp" ><font color="00b4a8">Log in</font></a>
	</p>
</form>
<%@ include file="includes/global_footer.jsp" %>