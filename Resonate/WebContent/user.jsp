
<%@ include file="includes/global_header.jsp" %>
<%@ include file="includes/banner.jsp" %>

<%@page import="java.util.Vector" %>

<%

if (u == null) {
	response.sendRedirect("/Resonate/login.jsp");
	return;
}

String name = u.getName();
String username = u.getUsername();
int id = u.get_id();
String email = u.getEmail();
Vector<Project> myprojects = u.getProjects();
Vector<Project> likedprojects = u.getLikedProjects();
%>

(This page needs to look like myprojects.jsp. Waiting for Jerry to finish that, then we can make the changes.)
<br /><br />
Welcome, <%=u.getName() %>.

<p class="header">My Projects</p>
<%
if (myprojects.size() == 0) {
%>
You have no projects yet. <a href="createproject.jsp">Create one!</a>
<%
} else {
	for (int i=0; i<myprojects.size(); i++) {
%>
	<div id="project_<%=i %>" class="project"><%= myprojects.elementAt(i).getName() %></div>
<%	
	}
}
%>
<p class="header">Liked Projects</p>
<%
if (likedprojects.size() == 0) {
%>
You haven't liked any projects yet. <a href="browseproject.jsp">Go find one!</a>
<%
} else {
	for (int i=0; i<likedprojects.size(); i++) {
%>
	<div id="project_<%=i %>" class="project"><%= likedprojects.elementAt(i).getName() %></div>
<%	
	}
}
%>

<%@ include file="includes/global_footer.jsp" %>