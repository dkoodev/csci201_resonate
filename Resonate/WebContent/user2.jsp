
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
							<tr>
							
								<td>
									<p id="long1">
										<font id="title"> Projects Liked by <%= u.getName() %></font>
									</p>
								<hr>	
								</td>
								
							</tr>
							
							<% Vector<Project> projects = u.getLikedProjects();
								if (projects.size() == 0) {
								%>
								<tr>
								
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
										<td>
											<font id="ProjectTitle"> <%= p.getName() %></font>
											<%if(p.getTracks() != null){	%>										
												<font id="ProjectInfo"> <%= p.getTracks().size() %> Tracks </font>	
											<% }%>					
											<br>		
											<i class="material-icons" style="font-size:24px; color: #008CBA">arrow_upward</i> <%= p.getUpvoteCount() %>		
											<br><font id="createDate">Date Created: <%= p.getCreateDate() %> </font>
										</td>
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