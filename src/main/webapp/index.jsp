<%@ page import="model.User"%>
<html>
<body>
<h2>IoTBay - Online Ordering Application</h2>

<%-- link to login page --%>
<%
    if (session.getAttribute("user") == null) {
%>
    <p align="right">You are not logged in</p>
    <a style="float:right" align="right" href="login.jsp">Register</a>
  
<%
    } else {
    User user = (User) session.getAttribute("user");
%>

    <p align="right">You are logged in as <%= user.getName() %> &lt<%= user.getEmail() %>&gt</p>
    <a style="float:right" align="right" href="logout.jsp">Logout</a>

    <li>
      <a href="edit_user.jsp">My Account</a>
    </li>

<% } %>

</body>
</html>
