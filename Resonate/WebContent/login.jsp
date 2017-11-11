<%@ include file="includes/global_header.jsp" %><br /><br /><br />
<head>
	<link rel="stylesheet" type="text/css" href="login.css">
</head>
<div>
	<img id="logo1" src="images/logo.png" /><br />
</div>

<form method="POST" action="Login" >
	<div class="center">
		<input type="text" name="username" placeholder="Username"/> <br /><br /><br />
		<input type="password" name="password" placeholder="Password" /> <br /><br />
	</div>
	<div class="center">	
		<button class="button">Log In</button>
	</div>
	
</form>
<%@ include file="includes/global_footer.jsp" %>
