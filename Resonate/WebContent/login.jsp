<%@ include file="includes/global_header.jsp" %><br /><br /><br />
<head>
	<link rel="stylesheet" type="text/css" href="login.css">
</head>
<div>
	<img id="loginlogo" src="images/logo.png" /><br />
</div>

<form method="POST" action="Login" >
	<div>
		<input id="centerinput" type="text" name="username" placeholder="Username" required/> <br />
		<input id="centerinput" type="password" name="password" placeholder="Password" required/> <br />
	</div>
	<div>	
		<button class="button1">Log In</button><br />
	</div>
	<p id="redirect">
		Don't have an account? <a href="signup.jsp" ><font color="00b4a8">Sign Up</font></a>
	</p>
</form>


<%@ include file="includes/global_footer.jsp" %>
