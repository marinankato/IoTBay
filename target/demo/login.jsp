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
    </style>
</head>
<body>
    <div class="login">
        <h2>Login</h2>

        <% String errMsg = (String)session.getAttribute("errorMsg");%>
        <% if (errMsg != null) { %>
        <p><%=errMsg%></p>
        <% } %>
        
        <form action="LoginServlet" method="post">
            <label for="email">Email:</label>
            <input type="text" name="email" placeholder="Email" required>
            
            <label for="password">Password:</label>
            <input type="password" name="password" placeholder="Password" required>

            <input type="submit" value="Login">
        </form>
        <div class="create-account">
            Don't have an account? <a href="register.jsp">Create one</a >
        </div>
    </div>
</body>
</html>