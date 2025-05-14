<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay | Logged out</title>
</head>
<body>
    <%
        // HttpSession session = request.getSession(false);
        // if (session != null) {
            session.invalidate(); // This clears all session attributes including "user"
// }
    %>
    You have been logged out successfully. Click <a href="index.jsp">here</a> to return to home page.
</body>
</html>