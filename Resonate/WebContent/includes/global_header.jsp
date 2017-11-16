<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="com.resonate.*" %>
<%@ page import="com.resonate.objects.*" %>

<%
User u = (User)session.getAttribute("user");
String referer = request.getRequestURL().toString();
String[] refParts = referer.split("/");
String pageName = refParts[refParts.length - 1].split("\\.")[0]; 
if (pageName.equals("Resonate")) pageName = "index";

String error = (String)session.getAttribute("errorMessage");

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
		<% if (pageName.equals("user")) { %>
			<link rel="stylesheet" type="text/css" href="includes/css/banner.css" />
		<% } %>
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<script type="text/javascript" src="includes/js/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="includes/js/main.js"></script>
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="includes/css/bootstrap/bootstrap.min.css" />
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
		<!--<script src="includes/js/popper.min.js"></script>
		<link rel="stylesheet" href="includes/css/bootstrap/bootstrap.min.css" />
		<script src="includes/js/bootstrap/bootstrap.min.js"></script>-->
		<!-- Isotope.js -->
		<script src="includes/js/isotope.pkgd.min.js"></script>
		<!-- Interact.js -->
		<script src="http://cdnjs.cloudflare.com/ajax/libs/interact.js/1.2.9/interact.min.js"></script>
	</head>
	<body>
		<div id="navbar" class="row">
			<div class="col-sm-11 col-md-2 col-lg-2">
				<a href="index.jsp"> <img id="logo" src="images/logo.png" /> </a>
			</div>
			<div class="col-sm-1 col-md-10 col-lg-10"> <!-- TODO: if we care, switch to a menu button on small devices -->
			<ul>
				<!-- <li id="loginBtn" onClick="loginPopup();"><a href="#">Login</a></li>-->
				<% if (u != null) { %>
					<%-- <li id="accountBtn"><a href="user.jsp">
					<% if (u.getPhoto() != null && !u.getPhoto().equals("")) { %>
						<img src="<%= u.getPhoto() %>" />
					<% } else { %>
						<img src="images/NoUserPhoto.png" />
					<% } %>
					</a></li> --%>
					<li id="drop">
					<div class="dropdown">
						<% if (u.getPhoto() != null && !u.getPhoto().equals("")) { %>
							<button  class="dropbtn"><p><img id="dropimg" src="<%= u.getPhoto() %>" /></p></button>
						<% } else { %>
							<button  class="dropbtn"><p><img id="dropimg" src="images/NoUserPhoto.png" /></p></button>
						<% } %>
						<div id="drop" class="dropdown-content">
						    <a href="#">Link 1</a>
						    <a href="#">Link 2</a>
						</div>
					</div>
					</li>
					<li id="myProjectsBtn"><a href="myprojects.jsp">My Projects</a></li>
				<% } else { %>	
					<li id="loginBtn"><a href="login.jsp">Login</a></li>
				<% } %>
				<li><a href="browseProjects.jsp">Browse Projects</a></li>
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
		<div id="mainBody">