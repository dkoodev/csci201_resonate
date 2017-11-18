<%@ include file="includes/banner.jsp" %>
		<tr>
			<a href="createproject.jsp"><button class="button"> + Create A New Project!</button> </a><br />
		</tr>
	</table>
</div> <!--  end of banner -->
<div id="background">
<br /><br /><br />
	<table id="projects">
		<!--  Enter for loop, # of rows = # of projects that user has created -->
	
			<tr class="border_bottom">
				<td>
					<p id="long1">
						<!--  replace username with variables  -->
						<font id="title"> <%= u.getUsername() %>'s Projects </font>
					</p>
				</td>
				<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				<td></td><td></td><td></td><td></td><td></td><td></td>
			</tr>
			<% Vector<Project> projects = u.getProjects(); %>
			<% for(int i=0; i < projects.size(); i++) { 
				Project p = projects.get(i); 				%>
				<tr class="border_bottom">
					<td>
						<!--  Replace with project image from database -->
						<img id="tableImg" src="<% p.getPhoto(); %>" />
					</td>
					<td >
						<p id="long">
							<!--  Replace with project title from database -->				
							<br><br><font id="ProjectTitle"> <% p.getName(); %></font>
						</p>
						<!--  Replace with number of tracks used for this project from database -->				
						<font id="ProjectInfo"> <% p.getTracks().size(); %> Tracks </font><br>	
						<!--  Replace with trend data from database -->				
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
			<% } %>
			<tr class="border_bottom">
				<td>
					<!--  Replace with project image from database -->
					<img id="tableImg" src="http://bennettfeely.com/gradients/img/gradient.webp" />
				</td>
				<td>
					<p id="long">
						<!--  Replace with project title from database -->				
						<br><br><font id="ProjectTitle"> Project Title</font>
					</p>				
					<font id="ProjectInfo"> Score </font><br>
					<font id="ProjectInfo"> Num Tracks </font><br>
					<i class="material-icons" style="font-size:24px; color: red">trending_down</i> 12			
				</td>
				<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
				
				<td>
					<p id="long2">				
						<font id="ProjectUpdate">Last Updated: Two days ago</font>
					</p>
				</td>
				<td></td><td></td><td></td><td></td>
			</tr>
	</table>
<br /><br /><br />
</div>
<%@ include file="includes/global_footer.jsp" %>