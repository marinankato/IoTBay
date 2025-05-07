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


        .have-account {
            margin-top: 15px;
            text-align: center;
            font-size: 14px;
            color: #444444;
        }

        .have-account a {
            color: #007bff;
            text-decoration: none;
            font-weight: 600;
        }

        .have-account a:hover {
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

    </style>
</head>
<body>
    <div class="register">
        <h2>Register</h2>

        <%-- show the error message if invalid attempt --%>
        <% String errMsg = (String)session.getAttribute("errorMsg"); %>
        <% if (errMsg != null) { %>
            <div class="errorMessage"><%= errMsg %></div>
        <% } %>

        <form action="RegisterServlet" method="post">
            <label for="firstName">First Name:</label>
            <input type="text" name="firstName" placeholder="First Name" required>

            <label for="lastname">Last Name:</label>
            <input type="text" name="lastName" placeholder="Last Name" required>
            
            <label for="email">Email:</label>
            <input type="text" name="email" placeholder="Email" required>
            
            <label for="password">Password:</label>
            <input type="password" name="password" placeholder="Password" required>

            <label for="phoneNo">Phone Number:</label>
            <input type="text" name="phoneNo" placeholder="Phone Number" required>
            
            <input type="submit" value="Register">
        </form>

        <div class="have-account">
        <p>Already have an account? <a href="login.jsp">Login</a></p>
        </div>
    </div>

</body>
</html>
