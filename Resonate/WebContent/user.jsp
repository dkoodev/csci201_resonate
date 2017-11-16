
<%@ include file="includes/banner.jsp" %>
	</table>
</div> <!--  end of banner -->
<div id="background">
	<table>
		<div id="navbar">
		<tr>
			<td>
				<ul id="nav">
				  <li><a id="li_a_nav" href="#" onclick='show(1);'>My Projects</a></li>
				  <li><a id="li_a_nav" href="#" onclick='show(2);'>Liked Projects</a></li>
				  <li style="float:right"><a id="li_a_nav" class="active" href="#" onclick='show(3);'>About Me</a></li>
				</ul>
			</td>
			<td>
		        &nbsp;
		    </td>
		    <td>
		    		<div id="table1">
		        		 <p id="myprojects">
						Looks like you haven't created any projects yet! Make one "now"
					 </p>
	            </div>  
	            <div id="table2">
		        		 <p id="likedprojects">
						Looks like you haven't liked any projects yet! Make one "now"
					 </p>
	            </div>
	            <div id="table3">
		        		 <p id="bio">
						Bio<br>
						This is my bio. Lalalalalalalalalalal Lalalalalalalalalalal Lalalalalalalalalalal<br>
						Lalalalalalalalalalal Lalalalalalalalalalal Lalalalalalalalalalal
					 </p>
	            </div>     
		     </td>
	     </tr>
	     </div>
	</table>
</div>
<div>
<script>
	function show(nr) {
	    document.getElementById("table1").style.display="none";
	    document.getElementById("table2").style.display="none";
	    document.getElementById("table3").style.display="none";
	    document.getElementById("table"+nr).style.display="block";
	}
</script>
<%-- 
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
 --%>
<%@ include file="includes/global_footer.jsp" %>