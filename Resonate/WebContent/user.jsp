<%@ include file="includes/banner.jsp" %>
	</table>
</div> <!--  end of banner -->
<div id="background">
<div id="navbar">
	<table>
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

<%@ include file="includes/global_footer.jsp" %>