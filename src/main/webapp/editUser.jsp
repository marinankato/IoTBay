<%@ page import="model.User"%>
<html>

<% User user = (User)session.getAttribute("user"); %>
<body>
  <h2>My Account </h2>
  <!-- Good to display current user parameters -->
  <form action="welcome.jsp" method="post"> 
    <label>Email: </label><br>
    <input type="text" name="email" value=<%=user.getEmail()%>><br>
    <label>Full Name: </label><br>
    <input type="text" name="name" value=<%=user.getName()%>><br>
    <label>Password: </label><br>
    <input type="password" name="password" value=<%=user.getPassword()%>><br>
    <label>Gender: </label><br>
    <input type="text" name="gender" value=<%=user.getGender()%>><br>
    <label>Favorite Colour: </label><br>
    <input type="text" name="colour" value=<%=user.getFavouriteColour()%>><br>
    <label>Agree to TOS: </label>
    <input type="checkbox" name="tos"><br><br>
    <input type="submit" value="Submit">
  </form>
</body>
</html>