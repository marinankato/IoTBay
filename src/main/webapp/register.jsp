<!-- register.jsp -->
<%@ page import="java.sql.*" %>
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
        .register {
            background-color: #ffffff;
            padding: 20px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 300px;
        }
        .register h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .register input[type="text"],
        .register input[type="email"],
        .register input[type="password"],
        .register input[type="date"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        .register input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .register input[type="submit"]:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="register">
        <h2>Register</h2>
        <form action="register.jsp" method="post">
            <input type="text" name="name" placeholder="Full Name" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="date" name="dob" required>
            <input type="submit" value="Register">
        </form>
        <p>Already have an account? <a href="login.jsp">Login</a></p>
    </div>

    <% 
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");
        
        if(name != null && email != null && password != null && dob != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iotbay", "root", "password");
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users(name, email, password, dob) VALUES (?, ?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, dob);
                ps.executeUpdate();
                out.println("<p style='color:green;'>Registration successful! <a href='login.jsp'>Login</a></p>");
            } catch (Exception e) {
                out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
            }
        }
    %>
</body>
</html>
