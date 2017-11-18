<%@ include file="includes/banner.jsp" %>
		<tr>
			<a href="createproject.jsp"><button class="button"> + Create A New Project!</button> </a><br />
		</tr>
	</table>
</div> <!--  end of banner -->
<div id="background">
<br /><br /><br />
	<table id="projects">
			<tr class="border_bottom">
				<td>
					<p id="long1">
						<font id="title"> <%= u.getUsername() %>'s Projects </font>
					</p>
				</td>
				<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				<td></td><td></td><td></td><td></td><td></td><td></td>
			</tr>
			<% Vector<Project> projects = u.getProjects();
				if (projects.size() == 0) {
				%>
					You have no projects yet. <a href="createproject.jsp">Create one!</a>
				<%
				} else {
					for(int i=0; i < projects.size(); i++) { 
					Project p = projects.get(i); 				%>
					<tr class="border_bottom">
						<td>
							<img id="tableImg" src="<% p.getPhoto(); %>" />
						</td>
						<td >
							<p id="long">
								<br><br><font id="ProjectTitle"> <% p.getName(); %></font>
							</p>
							<font id="ProjectInfo"> <% p.getTracks().size(); %> Tracks </font><br>								
							<i class="material-icons" style="font-size:24px; color: #008CBA">arrow_upward</i> <% p.getUpvoteCount(); %>									
						</td>	
						<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
						<td>
							<p id="long2">	
								<!--  Replace with data from database -->							
								<font id="ProjectUpdate">Last Updated: Two days ago</font>
							</p>
						</td><td></td><td></td><td></td><td></td>
					</tr>
					<% } //end of for loop
				} //end of else statement%>
	</table>
<br /><br /><br />
</div>
<%@ include file="includes/global_footer.jsp" %>