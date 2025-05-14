<%@ page import="model.User"%>
<html>
<head>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        background-color: #f4f4f4;
        font-family: 'Arial', sans-serif;
        display: flex;
        flex-direction: column;
        align-items: center;
        min-height: 100vh;
        justify-content: flex-start;
        padding: 40px 0;
    }

    .header {
        width: 100%;
        background-color: #ffffff;
        padding: 20px 0;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); 
        position: fixed;
        top: 0;
        left: 0;
        z-index: 1000;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 40px;
    }

    .logo {
        font-size: 2em;
        font-weight: bold;
        color: #007bff;
        text-decoration: none;
    }

    .logo:hover {
        color: #0056b3;
    }

    /* Welcome text on the top-right of the header */
    .welcomeText {
        font-size: 1.1em;
        font-weight: normal;
        color: #555555;
        margin-left: auto;
    }
    
    .editing {
     margin-top: 50px; 
    }
</style>
</head>

<% User user = (User)session.getAttribute("user"); %>

<body>
  <div class="header">
    <a href="dashboard.jsp" class="logo">IoTBay</a>        
    <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
  </div>

  <div class="editing">
    <h2>My Account </h2>
    <!-- Form to display current user parameters and allow password change -->
    <form action="EditUserServlet" method="post">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" value="<%= user.getFirstName() %>">
      <br><br>

      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" value="<%= user.getLastName() %>">
      <br><br>

      <label for="phoneNo">Phone Number:</label>
      <input type="text" id="phoneNo" name="phoneNo" value="<%= user.getPhoneNo() %>">
      <br><br>

      <input type="hidden" name="originalEmail" value="<%= user.getEmail() %>">
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

    <form action="DeleteUserServlet" method="post" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
      <input type="hidden" name="email" value="<%= user.getEmail() %>">
      <input type="submit" value="Delete Account" style="background-color: red; color: white;">
    </form>
  </div>

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