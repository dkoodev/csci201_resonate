<%@ include file="global_header.jsp" %>
<%@page import="java.util.Vector" %>
<div id="banner">
	<table>
		<tr>
			<td>
				<div id="userPic">
					<!--  replace img src with variables  -->
					<img id="userIcon" src="<%=(u.getPhoto() == null) ? "images/NoUserPhoto.png" : u.getPhoto() %>" /> <br />
			</td>
			<td>
					<br /><br /><br /><p id="fullname"> <!--  replace fullname with variables  -->		
						<%=u.getName() %>
					</p>
					<p id="username">	<!--  replace username with variables  -->
						<%=u.getUsername() %>
					</p><br />
				</div>
			</td>
		</tr>
