<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay | Register</title>
    
    <style>
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
        .register {
            background-color: #ffffff;
            padding: 20px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 10px color: #0000001A;;
            text-align: center;
            width: 300px;
        }

        .register h2 {
            margin-bottom: 20px;
            color: #333333;
        }

        /* input style */
        .register input[type="text"],
        .register input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }

        /* submit button style */
        .register input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .register input[type="submit"]:hover {
            background-color: #0056b3;
        }

        /* label style */
        .register label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            color: #555555;
        }

    </style>
</head>
<body>
    <div class="register">
        <h2>Register</h2>
        <form action="welcome.jsp" method="post">
            <label for="email">Email:</label>
            <input type="text" name="email" placeholder="Email" required>
            
            <label for="password">Password:</label>
            <input type="password" name="password" placeholder="Password" required>
            
            <input type="submit" value="Register">
        </form>
        <p>Already have an account? <a href="login.jsp">Login</a></p>
    </div>

</body>
</html>
