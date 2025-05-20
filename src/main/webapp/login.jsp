<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay | Log in</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #e3f2fd, #ffffff);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            width: 350px;
        }

        .login h2 {
            margin-bottom: 25px;
            color: #007bff;
            font-weight: bold;
        }

        .login label {
            display: block;
            margin-bottom: 6px;
            color: #333;
            font-size: 14px;
            text-align: left;
        }

        .login input[type="text"],
        .login input[type="password"] {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 15px;
            background-color: #fdfdfd;
        }

        .login input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .login input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .toggle-container {
            margin-top: -5px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
            color: #333333;
        }

        .create-account {
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
            color: #444;
        }

        .create-account a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        .create-account a:hover {
            text-decoration: underline;
        }

        .errorMessage {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px 12px;
            margin-bottom: 20px;
            border-left: 5px solid #f5c6cb;
            border-radius: 4px;
            font-size: 14px;
            text-align: left;
        }
    </style>
</head>

<body>
    <div class="login">
        <h2>Login</h2>

        <%-- show the error message if invalid login attempt --%>
        <% 
        String errMsg = (String)session.getAttribute("errorMsg");
        if (errMsg != null) { 
        %>
            <div class="errorMessage"><%= errMsg %></div>
        <% } %>
        
        <form action="LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="text" name="email" placeholder="example@gmail.com" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="password123" required>

            <div class="toggle-container">
                <label for="showPassword">Show Password</label>
                <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()">
            </div>

            <input type="submit" value="Login">
        </form>

        <div class="create-account">
            Don't have an account? <a href="register.jsp">Create one</a >
            <p>Continue as a Guest? <a href="GuestLoginServlet">Click here</a></p>
        </div>
    </div>

    <script>
        function togglePasswordVisibility() {
      var passwordField = document.getElementById("password");
      var checkbox = document.getElementById("showPassword");

      if (checkbox.checked) {
        passwordField.type = "text"; // Unmask the password
      } else {
        passwordField.type = "password"; // Mask the password
      }
    }
    </script>
</body>
</html>