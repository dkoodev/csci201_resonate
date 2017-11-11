<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="com.resonate.*" %>
<%@ page import="com.resonate.objects.*" %>

<%
User u = (User)session.getAttribute("validatedUser");
String referer = request.getRequestURL().toString();
String[] refParts = referer.split("/");
String pageName = refParts[refParts.length - 1].split("\\.")[0];
if (pageName.equals("Resonate")) pageName = "index";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Resonate</title>
		<link rel="stylesheet" type="text/css" href="includes/css/main.css" />
		<% // Links a stylesheet based on the name of the page we're on, to make css per page seperated and (hopefully) neater. %>
		<link rel="stylesheet" type="text/css" href="includes/css/<%= pageName %>.css" />
		<script type="text/javascript" src="includes/js/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="includes/js/main.js"></script>
		
		<!-- Bootstrap -->
		<script src="includes/js/popper.min.js"></script>
		<link rel="stylesheet" href="includes/css/bootstrap/bootstrap.min.css" />
		<script src="includes/js/bootstrap/bootstrap.min.js"></script>
	</head>
	<body>
		<div id="navbar" class="row">
			<div class="col-sm-11 col-md-2 col-lg-2">
				<img id="logo" src="images/logo.png" />
			</div>
			<div class="col-sm-1 col-md-10 col-lg-10"> <!-- TODO: if we care, switch to a menu button on small devices -->
			<ul>
				<!-- <li id="loginBtn" onClick="loginPopup();"><a href="#">Login</a></li>-->
				<% if (u != null) { %>
					<li id="accountBtn"><a href="user.jsp">My Account</a></li>
				<% } else { %>	
					<li id="loginBtn"><a href="login.jsp">Login</a></li>
				<% } %>
				<li>Browse Projects</li>
			</ul>
			</div>
			<!-- <div id="loginPopup">
				<form>
					<div><p>Username: </p><input type="text" name="uname" /></div>
					<div class="spacer"></div>
					<div><p>Password: </p><input type="password" name="pw" /></div>
					<div class="spacer"></div>
					<div><input type="submit" value="Login" /></div>
				</form>
			</div>-->
		</div>