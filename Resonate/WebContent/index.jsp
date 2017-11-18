<%@ include file="includes/global_header.jsp" %>

<!-- Promotions Page or Home Page -->
<%
 if (request.getParameter("logout") != null) {
	session.invalidate();
	response.sendRedirect("/Resonate/index.jsp");
	return;
}   
%>

<div id="piano">
	<div id="tagline">
		<!-- <p>Create. Collaborate.</p><img src="images/logo.png" /> -->
		
		<!-- added this -->
		<div id="basicTimeline">
			<div id = "create">
				<p>Create.</p>
			</div>
		    <div id = "collaborate">
				<p>Collaborate.</p>
			</div>
			<div id = "resonate">
				<img src="images/logo.png" />
			</div> 
		</div>
		<!--  -->		
	</div>
	
	<div id="buttons">
		<button type="button" class="btn btn-outline-primary btn-lg" onclick="window.location.href='LearnMore.jsp'; return false;">Learn More</button>
		<button type="button" class="btn btn-outline-primary btn-lg" onclick="window.location.href='signup.jsp'; return false;">Sign Up</button> 
	</div>
</div>

<%@ include file="includes/global_footer.jsp" %>		


