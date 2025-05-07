<%@ page import="model.User"%>
<html>

<% User user = (User)session.getAttribute("user"); %>
<body>
  <h2>My Account </h2>
  <!-- Form to display current user parameters and allow password change -->
  <form action="dashboard.jsp" method="post">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" value="<%= user.getEmail() %>">
    <br><br>
    
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value="<%= user.getPassword() %>">
    <br><br>
    
    <!-- Checkbox to toggle password visibility -->
    <label for="showPassword">Show Password</label>
    <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()">
    <br><br>
    
    <input type="submit" value="Save">
  </form>

  <script>
    // Function to toggle the password visibility
    function togglePasswordVisibility() {
      var passwordField = document.getElementById("password");
      var checkbox = document.getElementById("showPassword");

      // If the checkbox is checked, show the password, otherwise hide it
      if (checkbox.checked) {
        passwordField.type = "text"; // Unmask the password
      } else {
        passwordField.type = "password"; // Mask the password
      }
    }
  </script>

</body>
</html>