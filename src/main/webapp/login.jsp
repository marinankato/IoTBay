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
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 300px;
        }

        .login h2 {
            margin-bottom: 20px;
            color: #333;
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
            color: #555;
        }
    </style>
</head>
<body>
    <div class="login">
        <h2>Login</h2>
        <form action="loginCheck.jsp" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <input type="submit" value="Login">
        </form>
    </div>
</body>
</html>