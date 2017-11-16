<%@ include file="includes/global_header.jsp" %>
<%@page import="java.util.Vector" %>

<%
String name = u.getName();
String username = u.getUsername();
String email = u.getEmail();
%>

<p id="accountOverview"><font color="00b4a8">Account Overview</font></p><br />

<div id="box">
	<p id="profile"><font color="00b4a8">Profile</font></p>
	<div id="nameTitle"><font color="00b4a8">Name</font></div>
	<div id="name"><p><%=name%></p></div>
	
	<div id="usernameTitle"><font color="00b4a8">Username</font></div>
	<div id="usernamev"><p><%=username%></p></div>
	
	<div id="emailTitle"><font color="00b4a8">Email</font></div>
	<div id="email"><p><%=email%></p></div>

	<div id="edit"><a href="updateProfile.jsp" ><font color="00b4a8">EDIT PROFILE</font></a></div>
</div>
<%@ include file="includes/global_footer.jsp" %>