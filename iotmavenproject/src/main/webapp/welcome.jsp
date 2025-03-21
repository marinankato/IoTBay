<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
</head>
<body>
    <%
        // get the username and password frome the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // check if the username and password are correct
        if ("admin".equals(username) && "password123".equals(password)) {
            out.println("<h2>Welcome, " + username + "!</h2>");
        } else {
            out.println("<h2>Invalid username or password. Please try again <a href=login.jsp>here</a>.</h2>");
        }
    %>
</body>
</html>