
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
		        		<div id="likedprojects">
		        			<table id="projects">
							<tr class="border_bottom">
								<td>
									<p id="long1">
										</br>
										<font id="title"> Projects liked by <%= u.getUsername() %></font>
									</p>
								</td>
							</tr>
							<% Vector<Project> projects = u.getLikedProjects();
								if (projects.size() == 0) {
								%>
									Looks like you haven't liked any projects yet!
									Explore the Resonate Community! <br><br>
									<a href="browseProjects.jsp" style="text-decoration: none;"><button class="button2"> <span>Browse Projects </span></button> </a><br />
								<%
								} else {
									for(int i=0; i < projects.size(); i++) { 
									Project p = projects.elementAt(i); 				
								%>
									<tr class="border_bottom">
										<td>
											<img id="tableImg" src="<%= p.getPhoto() %>" />
										</td>
										<td >
											<p id="long">
												<br><br><font id="ProjectTitle"> <%= p.getName() %></font>
											</p>
											<font id="ProjectInfo"> <%= p.getTracks().size() %> Tracks </font><br>								
											<i class="material-icons" style="font-size:24px; color: #008CBA">arrow_upward</i> <%= p.getUpvoteCount() %>									
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
					 </div>
	            </div>     
		    </td>
	     </tr>
	</table>
</div>

<%@ include file="includes/global_footer.jsp" %>