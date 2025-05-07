<%@ page import="model.User"%>
<html>
<meta charset="UTF-8">
<title>IoTBay</title>
<head>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center; /* Center horizontally */
    }

    h2 {
        text-align: center;
        color: #f4f4f4;
        padding: 40px 0;
        background-color: #007bff;
        margin: 0;
        font-size: 2.5em;
        width: 100%;
    }

    /* Navigation list styles */
    ul {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 8px;
        list-style-type: none;
        margin-top: 20px; 
        text-align: center;
    }

    li {
        margin: 10px 0;
        font-size: 1.2em;
    }

    /* Links styling */
    a {
        text-decoration: none;
        color:rgb(255, 255, 255);
        font-weight: bold;
    }

    /* Logged-in status styling */
    .status {
        color:rgb(0, 0, 0);
    }

    /* Style for buttons inside <ul> */
    ul li button {
        display: inline-block;
        padding: 10px 20px;
        margin: 10px;
        font-size: 1.2em;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    /* Hover effect for buttons */
    ul li button:hover {
        background-color: #0056b3;
    }

    /* Align buttons side by side */
    .buttons-container {
        display: flex;
        justify-content: space-evenly;
        width: 100%;
    }
</style>
</head>

<body>

    <h2>IoTBay - Online Ordering Application</h2>

    <%
        if (session.getAttribute("user") == null) {
    %>
        <ul>
            <li class="status">You are not logged in</li>
            <li class="buttons-container">
                <button><a href="login.jsp"">Login</a></button>
                <button><a href="register.jsp">Register</a></button>
            </li>
        </ul>
    <%
        } else {
            User user = (User)session.getAttribute("user");
    %>
        <ul>
            <li class="status">You are logged in as: <%= user.getEmail() %></li>
            <li class="buttons-container">
                <button><a href="logout.jsp">Logout</a></button>
                <button><a href="editUser.jsp">View Account</a></button>
            </li>
        </ul>
    <%
        }
    %>
    
  <jsp:include page="/ConnServlet" flush="true" />
</body>
</html>
