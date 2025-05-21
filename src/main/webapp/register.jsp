<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay | Register</title>
    
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

        .register {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            width: 350px;
        }

        .register h2 {
            margin-bottom: 25px;
            color: #007bff;
            font-weight: bold;
        }

        .register label {
            display: block;
            margin-bottom: 6px;
            color: #333;
            font-size: 14px;
            text-align: left;
        }

        .register input[type="text"],
        .register input[type="password"],
        .register select {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 15px;
            background-color: #fdfdfd;
        }

        .register select {
            background-color: #fefefe;
            cursor: pointer;
        }

        .register input[type="submit"] {
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

        .register input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .have-account {
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
            color: #444;
        }

        .have-account a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        .have-account a:hover {
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

<% 
    // clear existing error message on page load
    session.removeAttribute("errorMsg");
%>

<body>
    <div class="register">
        <h2>Register</h2>

        <%-- show the error message if invalid attempt --%>
        <% String errMsg = (String)request.getAttribute("errorMsg"); %>
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

            <label for="role">Registering as:</label>
            <select name="role" required>
                <option value="">Select Role</option>
                <option value="customer">Customer</option>
                <option value="staff">Staff</option>
            </select>
            
            <input type="submit" value="Register">
        </form>

        <div class="have-account">
        <p>Already have an account? <a href="login.jsp">Login</a></p>
        <p>Continue as a Guest? <a href="GuestLoginServlet">Click here</a></p>
        </div>
    </div>

</body>
</html>
