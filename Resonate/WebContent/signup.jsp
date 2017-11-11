<%@ include file="includes/global_header.jsp" %>
<form method="POST" action="Signup" >
	Name: <input type="text" name="name" placeholder="Name" /> <br />
	Username: <input type="text" name="username" placeholder="Username" /> <br />
	Email: <input type="text" name="email" placeholder="Email" /> <br />
	Password: <input type="password" name="password" placeholder="Password" /> <br />
	Confirm Password: <input type="password" name="cpassword" placeholder="Password" /> <br />
	<input type="submit" value="Sign Up" />
</form>
<%@ include file="includes/global_footer.jsp" %>