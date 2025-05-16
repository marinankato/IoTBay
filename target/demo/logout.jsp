<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay | Logged out</title>
</head>
<body>
    <%
        session.invalidate(); //clears the user from session
    %>
    You have been logged out successfully. Click <a href="index.jsp">here</a> to return to home page.
</body>
</html>