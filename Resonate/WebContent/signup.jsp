<%@ include file="includes/global_header.jsp" %><br /><br />

	<p id="signup">Join A <font color="00b4a8">Universe</font> of Collaborative Artists.</p><br />

<head>
	<link rel="stylesheet" type="text/css" href="login.css">
</head>

<form method="POST" action="Signup" >
	<div>
		<input type="text" name="name" placeholder="Name" /> <br />
		<input type="text" name="username" placeholder="Username" /> <br />
		<input type="text" name="email" placeholder="Email" /> <br />
		<input type="password" name="password" placeholder="Password" /> <br />
		<input type="password" name="cpassword" placeholder="Confirm Password" /> <br />
		<button class="button2">Sign Up</button><br />
	</div>
	<p id="redirect">
		Already have an account? <a href="login.jsp" ><font color="00b4a8">Log in</font></a>
	</p>
</form>
<%@ include file="includes/global_footer.jsp" %>