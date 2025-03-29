<%@ page import="model.User" %>
<html>
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
.message-container {
    text-align: center;
    padding: 20px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.message-container h1 {
    color: #28a745; /* Green color for success */
    font-size: 24px;
    margin-bottom: 20px;
}
.tick-icon {
    font-size: 50px;
    color: #28a745;
    margin-bottom: 20px;
}
.message-container img {
    width: 40px;
    height: 40px;
    margin-top: 10px;
}
.message-container a {
    color: #007bff;
    text-decoration: none;
}
.message-container a:hover {
    text-decoration: underline;
}
</style>
<%
    User user = (User) session.getAttribute("user"); // Check if user is already in session
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username != null && password != null) {
        // Check the credentials
        if ("admin".equals(username) && "password123".equals(password)) {
            user = new User(username, password);
            session.setAttribute("user", user);
            // Redirect after a login success message
            %>       

            <body>
                <div class="message-container">
                    <img src="./images/green_tick.jpg">
                    <h1>Login successful! Redirecting you to the dashboard...</h1>
                    <!-- Optional link if user clicks it before the redirect -->
                    <h2>Click <a href="dashboard.jsp">here</a> if page does not load.</h2>
                </div>

                <script>
                    setTimeout(function() { 
                        window.location.href = 'dashboard.jsp'; 
                    }, 3000);
                </script>
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