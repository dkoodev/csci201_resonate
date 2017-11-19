<%@ include file="global_header.jsp" %>
<%@page import="java.util.Vector" %>
<div id="banner">
	<table>
		<tr>
			<td>
				<div id="userPic">
					<img id="userIcon" src="<%=(u.getPhoto() == null) ? "images/NoUserPhoto.png" : u.getPhoto() %>" /> <br />
			</td>
			<td>
					<br /><br /><br /><p id="fullname">	
						<%=u.getName() %>
					</p>
					<p id="username">
						<%=u.getUsername() %>
					</p><br />
					<a href="createproject.jsp"><button class="create">Create a Project</button></a>
				</div>
			</td>
		</tr>
