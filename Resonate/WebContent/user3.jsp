
<%@ include file="includes/banner.jsp" %>
	</table>
</div> <!--  end of banner -->
<div id="background">
	<table id="user">
		<tr>
			<td id="navbar2">
				<ul id="nav">
				  <li><a id="li" class="active" href="#">My Projects</a></li>
				  <li><a id="li" href="user2.jsp">Liked Projects</a></li>
				  <li><a id="li" href="user.jsp" >About Me</a></li>
				</ul>
			</td>
		    <td id="td1">
		    		<div id="table1">
		        		<div id="myprojects">
						<table id="projects">
							<tr class="border_bottom">
								<td>
									<p id="long1">
										<font id="title"> <%= u.getName() %>'s Projects </font>
									</p>
								</td>
							</tr>							
							<% Vector<Project> projects = u.getProjects();
								if (projects.size() == 0) {
								%>
							<tr class="border_bottom">

									<td>
										Looks like you haven't created any projects yet!
										<a href="createproject.jsp" style="text-decoration: none;"><button class="button2"> <span>Create a Project</span></button> </a><br />
									</td>
								<%
								} else {
									for(int i=0; i < projects.size(); i++) { 
									Project p = projects.elementAt(i); 
								%>
									<td>
										<%if(p.getPhoto() != null && !p.getPhoto().equals("null")) { %>
											<img id="tableImg" src="<%= p.getPhoto() %>" />
										<%} 
										else{%>
											<img id="tableImg" src="https://youshark.neocities.org/assets/img/default.png"/>
										<%}%>
										
									</td>
									<td >
										<div id="long">
											<br><br><font id="ProjectTitle"> <%= p.getName() %></font>
										</div>
										<%-- <div id="long2">	
												<font id="createDate"> Date Created: <%= p.getCreateDate() %> </font>
										</div> --%>
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