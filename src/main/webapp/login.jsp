<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay | Log in</title>
    <style>
        /* whole body style */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* login container style */
        .login {
            background-color: #ffffff;
            padding: 20px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 10px color: #0000001A;;
            text-align: center;
            width: 300px;
        }

        .login h2 {
            margin-bottom: 20px;
            color: #333333;
        }

        /* input style */
        .login input[type="text"],
        .login input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }

        /* submit button style */
        .login input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .login input[type="submit"]:hover {
            background-color: #0056b3;
        }

        /* label style */
        .login label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            color: #555555;
        }

        .create-account {
            margin-top: 15px;
            text-align: center;
            font-size: 14px;
            color: #444444;
        }

        .create-account a {
            color: #007bff;
            text-decoration: none;
            font-weight: 600;
        }

        .create-account a:hover {
            text-decoration: underline;
        }

        .errorMessage {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            margin-bottom: 15px;
            border-left: 5px solid #f5c6cb;
            border-radius: 4px;
        }

        .toggle-container {
            margin-top: 10px;
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
            color: #333333;
        }
    </style>
</head>

<body>
    <div class="login">
        <h2>Login</h2>

        <%-- show the error message if invalid login attempt --%>
        <% String errMsg = (String)session.getAttribute("errorMsg"); %>
        <% if (errMsg != null) { %>
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