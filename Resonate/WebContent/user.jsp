<%@ include file="includes/global_header.jsp" %>

<%
User user = (User) session.getAttribute("user");
String name = user.getName();
String username = user.getUsername();
int id = user.get_id();
String email = user.getEmail();

%>

<h1>
<%= name%> 
<br>
<%= username%> 
<br>
<%= id%> 
<br>
<%= email%> 
<br>

</h1>

<%@ include file="includes/global_footer.jsp" %>