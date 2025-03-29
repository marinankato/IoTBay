<%@ page import="model.User"%>
<html>
<meta charset="UTF-8">
<title>IoTBay</title>

<body>
    <h2>IoTBay - Online Ordering Application</h2>
<%
    if (session.getAttribute("user") == null) {
%>
    <ul>
    <li>You are not logged in</li>
    <li><a href="login.jsp">Login</a></li>
    </ul>
<%
    } else {
    User user = (User)session.getAttribute("user");
%>
    <ul>
    <li>You are logged in with <%= user.getUsername() %></li>
    <li><a href="logout.jsp">Logout</a></li>
    <li><a href="editUser.jsp">My Account</a></li>
    </ul>
<% } %>

</body>
</html>
