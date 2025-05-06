<%@page import="model.User"%>
<html>
<title>IoTBay | Order History</title>

<style>
	* {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        background-color: #f4f4f4;
        font-family: 'Arial', sans-serif;
        display: flex;
        flex-direction: column;
        align-items: center;
        min-height: 100vh;
        justify-content: flex-start;
        padding: 40px 0;
    }

    .header {
        width: 100%;
        background-color: #ffffff;
        padding: 20px 0;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); 
        position: fixed;
        top: 0;
        left: 0;
        z-index: 1000;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 40px;
    }

    .logo {
        font-size: 2em;
        font-weight: bold;
        color: #007bff;
        text-decoration: none;
    }

    .logo:hover {
        color: #0056b3;
    }

    .welcomeText {
        font-size: 1.1em;
        font-weight: normal;
        color: #555555;
        margin-left: auto;
    }
</style>

<% User user = (User)session.getAttribute("user"); %>

<body>
 	<!-- Header Section with Logo and Welcome Text -->
    <div class="header">
        <a href="dashboard.jsp" class="logo">IoTBay</a>
        <span class="welcomeText">Logged in as: <%= user.getEmail() %></span>
    </div>

</body>
</html>