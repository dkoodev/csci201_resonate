
<%@ include file="includes/banner.jsp" %>
	</table>
</div> <!--  end of banner -->
<div id="background">
	<table id="user">
		<tr>
			<td id="navbar2">
				<ul id="nav">
				  <li><a id="li" href="user3.jsp">My Projects</a></li>
				  <li><a id="li" href="user2.jsp">Liked Projects</a></li>
				  <li><a id="li" class="active" href="#">About Me</a></li>
				</ul>
			</td>
		    <td id="td1">
		    		<div id="table3">
		        		 <p id="bio">
						<font id="font"><u>About Me</u></font><br>
						<%= u.getBio() %>
					 </p>
	            </div>    
		     </td>
	     </tr>
	</table>
</div>

<%@ include file="includes/global_footer.jsp" %>