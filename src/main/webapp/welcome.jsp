<%@ page import="model.User" %>
<html>
<title>IoTBay</title>

<style>
    body {
        font-family: Arial, sans-serif;                    
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        background-color: #f4f4f4;
    }

    .container {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        text-align: center;
        width: 400px;
    }

    .container img {
        width: 40px;
        height: 40px;
        margin-top: 10px;
    }

    .container h1 {
        color: #28a745; 
        font-size: 24px;
        margin-top: 10px;
        margin-bottom: 15px;
    }
    .container p {
        color: #333333; 
        font-size: 16px;
        margin-bottom: 15px;
    }

    .container a {
        color: #ffffff;
        text-decoration: none;
        display: inline-block;
        background-color: #007bff;
        padding: 12px 20px;
        text-decoration: none;
        border-radius: 5px;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    .container a:hover {
        background-color: #0056b3;
    }

    h1 {
        text-align: center; 
        margin: 0; 
    }
</style>

<%
    User user = (User) session.getAttribute("user"); // Check if user is already in session
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if (email != null && password != null) {
        // Check the credentials
        if ("admin".equals(email) && "password123".equals(password)) {
            user = new User(email, password);
            session.setAttribute("user", user);
            %>       
            <body>
                <div class="container">
                    <img src="./images/green_tick.jpg">

                    <div class="welcomeMessage">
                    <h1>Welcome <%=user.getEmail() %>!</h1>
                    <p>Your password is <%=user.getPassword()%></p>
                    <a href="dashboard.jsp">Continue</a>
                    </div>
                </div>
            </body>
         
       <% } else { %>
       <%-- Login failed, show error message and redirect to login page --%>
            <script type="text/javascript">
                alert('Login Failed! Invalid credentials please try again.');
                setTimeout(function() { window.location.href = 'login.jsp'; }, 300);
            </script>
       <% }
    }
%>
</html>