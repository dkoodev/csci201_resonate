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
	</table>
</div>
<br /><hr>
<div id="projects">
	<table>
	<!--  Enter for loop, # of rows = # of projects that user has created -->
		<tr class="border_bottom">
			<td>
				<!--  Replace with project image from database -->
				<img id="tableImg" src="http://bennettfeely.com/gradients/img/gradient.webp" />
			</td>
			<td>
				<br><br><font id="ProjectTitle"> Project Title </font><br>
				<font id="ProjectInfo"> Score </font><br>
				<font id="ProjectInfo"> Num Tracks </font><br>			
			</td>	
		</tr>
		<tr>
			<td>
				<!--  Replace with project image from database -->
				<img id="tableImg" src="http://bennettfeely.com/gradients/img/gradient.webp" />
			</td>
			<td>
				<br><br><font id="ProjectTitle"> Project Title </font><br>
				<font id="ProjectInfo"> Score </font><br>
				<font id="ProjectInfo"> Num Tracks </font><br>				
			</td>
		</tr>
		
		<tr>
			<td>
				<!--  Replace with project image from database -->
				<img id="tableImg" src="http://bennettfeely.com/gradients/img/gradient.webp" />
			</td>
			<td>
				<br><br><font id="ProjectTitle"> Project Title </font><br>
				<font id="ProjectInfo"> Score </font><br>
				<font id="ProjectInfo"> Num Tracks </font><br>				
			</td>
		</tr>
	</table>
</div>