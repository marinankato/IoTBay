<%@ page import="model.User"%>
<html>

<% User user = (User)session.getAttribute("user"); %>
<body>
  <h2>My Account </h2>
  <!-- Good to display current user parameters -->
  <form action="dashboard.jsp" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <br><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <br><br>
            <input type="submit" value="Login">
        </form>
</body>
</html>