
<%@ include file="includes/banner.jsp" %>
	</table>
</div> <!--  end of banner -->
<div id="background">
	<table id="user">
		<tr>
			<td id="navbar2">
				<ul id="nav">
				  <li><a id="li" href="user3.jsp">My Projects</a></li>
				  <li><a id="li" class="active">Liked Projects</a></li>
				  <li><a id="li" href="user.jsp" >About Me</a></li>
				</ul>
			</td>
		    <td id="td1"> 
	            <div id="table2">
		        		 <p id="likedprojects">
		        		 	<% Vector<Project> liked = u.getLikedProjects(); %>
		        		 	<% for(int i = 0; i < liked.size(); i++) { %>
		        		 	<div>
		        		 		<%= liked.get(i).getPhoto() %> 
		        		 	</div>

						<br>
						Explore the Resonate Community! <br><br>
						<a href="browseProjects.jsp" style="text-decoration: none;"><button class="button2"> <span>Browse Projects </span></button> </a><br />
					 </p>
	            </div>     
		     </td>
	     </tr>
	</table>
</div>
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