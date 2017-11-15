<%@ include file="includes/global_header.jsp" %>

<div id="banner">
	<table>
		<tr>
			<td>
				<div id="userPic">
					<!--  replace img src with variables  -->
					 
					<img id="userIcon" src="images/0.JPG" /> <br />
			</td>
			<td>
					<br /><br /><br /><p id="fullname"> <!--  replace fullname with variables  -->		
						Jerry Tejada
					</p>
					<p id="username">	<!--  replace username with variables  -->
						username
					</p><br />
				</div>
			</td>
		</tr>
		<tr>
			<button class="button" href="createproject.jsp"> + Create A New Project!</button><br />
		</tr>
	</table>
</div>
<br /><br /><br />
<table id="projects">
	<!--  Enter for loop, # of rows = # of projects that user has created -->

		<tr class="border_bottom">
			<td id="th">
				<font id="title"> Username's Projects </font>
			</td>
			<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
		</tr>
		<tr class="border_bottom">
			<td>
				<!--  Replace with project image from database -->
				<img id="tableImg" src="http://bennettfeely.com/gradients/img/gradient.webp" />
			</td>
			<td >
				<!--  Replace with project title from database -->				
				<br><br><font id="ProjectTitle"> Project Title</font><br>
				<!--  Replace with project score from database -->				
				<font id="ProjectInfo"> Score </font><br>
				<!--  Replace with number of tracks used for this project from database -->				
				<font id="ProjectInfo"> Num Tracks </font><br>	
				<i class="material-icons" style="font-size:24px; color: #008CBA">arrow_upward</i>	14									
			</td>	
			<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
			<td>
<<<<<<< HEAD
				<font id="ProjectUpdate">Last Updated: Two days ago</font>
			</td><td></td><td></td><td></td><td></td>
=======
				<font id="ProjectUpdate">Last updated: Two days ago</font>

			</td>
>>>>>>> origin/master
		</tr>
		<tr class="border_bottom">
			<td>
				<!--  Replace with project image from database -->
				<img id="tableImg" src="http://bennettfeely.com/gradients/img/gradient.webp" />
			</td>
			<td>
				<br><br><font id="ProjectTitle"> Project Title </font><br>
				<font id="ProjectInfo"> Score </font><br>
				<font id="ProjectInfo"> Num Tracks </font><br>
				<i class="material-icons" style="font-size:24px; color: red">trending_down</i> 12			
			</td>
			<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
			
			<td id="th">
				<font id="ProjectUpdate"> Last updated: Two days ago</font>
			</td><td></td><td></td><td></td><td></td>
		</tr>
</table><br /><br />
<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
<%@ include file="includes/global_footer.jsp" %>