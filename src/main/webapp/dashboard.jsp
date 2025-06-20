<%@page import="model.User"%>

<html>
<title>IoTBay Dashboard</title>

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

    /* Welcome text on the top-right of the header */
    .welcomeText {
        font-size: 1.1em;
        font-weight: normal;
        color: #555555;
        margin-left: auto;
    }

    #dashboard {
        width: 90%;
        max-width: 1200px;
        padding: 20px;
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        gap: 15px;
        margin-top: 120px; 
    }

    .box {
        background-color: #ffffff;
        width: 250px;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        text-align: center;
        cursor: pointer;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .box:hover {
        transform: translateY(-10px);
        box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
    }

    .box h3 {
        margin-bottom: 15px;
        font-size: 1.5em;
        color: #007bff;
    }

    .box p {
        font-size: 1em;
        color: #555555;
        margin-bottom: 30px;
    }

    .box img {
        width: 50px;
        height: 50px;
        margin: 0 auto 20px;
    }

    /* Button link styles inside the cards */
    .box a {
        text-decoration: none;
        color: #fff;
        background-color: #007bff;
        padding: 10px 20px;
        border-radius: 5px;
        font-weight: bold;
        transition: background-color 0.3s;
    }

    .box a:hover {
        background-color: #0056b3;
    }
</style>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    // check the user's role
    String role = user.getRole();
%>

<body>
    <!-- Header Section with Logo and Welcome Text -->
    <div class="header">
        <a href="dashboard.jsp" class="logo">IoTBay</a>
        <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
    </div>

    <% if ("admin".equalsIgnoreCase(role)) { %>
    <!-- Admin dashboard content -->
        <section id="dashboard">
            <div class="box">
                <img src="./images/user_access.jpg">
                <h3>User Records</h3>
                <p>Manage and view all existing user accounts.</p>
                <a href="user-management">View User Accounts</a>
            </div>

            <div class="box">
                <img src="./images/user.jpg">
                <h3>Account Settings</h3>
                <p>Update your personal information and view your access logs.</p>
                <a href="EditUserServlet">View Profile</a>
            </div>

            <div class="box">
                <img src="./images/logout.jpg">
                <h3>Logout</h3>
                <p>Logout from your account securely.</p>
                <a href="LogoutServlet">Logout</a>
            </div>
        </section>

    <% } else if ("customer".equalsIgnoreCase(role)) { %>  
        <!-- Customer dashboard -->
        <%-- browse, search, order, track their orders, manage their accounts, view history logs, save payments and shipments details --%>
        <section id="dashboard">
            <div class="box">
                <img src="./images/cart.jpg">
                <h3>Start Ordering</h3>
                <p>Browse products and start placing your order.</p>
                <a href="<%= request.getContextPath() %>/devices">Go to Shop</a>
            </div>

            <div class="box">
                <img src="./images/history.jpg">
                <h3>Order History</h3>
                <p>View your past orders and track their status.</p>
                <a href="${pageContext.request.contextPath}/order">View Orders</a>
            </div>

            <div class="box">
                <img src="./images/user.jpg">
                <h3>Account Settings</h3>
                <p>Update your personal information and view your access logs.</p>
                <a href="EditUserServlet">View Profile</a>
            </div>

            <div class="box">
                <img src="./images/logout.jpg">
                <h3>Logout</h3>
                <p>Logout from your account securely.</p>
                <a href="LogoutServlet">Logout</a>
            </div>
        </section>

    <% } else if ("staff".equalsIgnoreCase(role)) { %>
    <!-- Staff dashboard -->
    <%-- manage the device collection, track customer orders, and manage users and application access logs --%>
        <section id="dashboard">
            <div class="box">
                <img src="./images/cart.jpg">
                <h3>Start Ordering</h3>
                <p>Browse products and start placing your order.</p>
                <a href="<%= request.getContextPath() %>/devices">Go to Shop</a>
            </div>

            <div class="box">
                <img src="./images/history.jpg">
                <h3>Order History</h3>
                <p>View your past orders and track their status.</p>
                <a href="orderHistory.jsp">View Orders</a>
            </div>

            <div class="box">
                <img src="./images/user.jpg">
                <h3>Account Settings</h3>
                <p>Update your personal information and view your access logs.</p>
                <a href="EditUserServlet">View Profile</a>
            </div>

            <div class="box">
                <img src="./images/logout.jpg">
                <h3>Logout</h3>
                <p>Logout from your account securely.</p>
                <a href="LogoutServlet">Logout</a>
            </div>
        </section>
    <% } else { %>
    <!-- Non-registered user dashboard -->
    <%-- browse, search, purchase and track their orders --%>
    <section id="dashboard">
        <div class="box">
            <img src="./images/cart.jpg">
            <h3>Start Ordering</h3>
            <p>Browse products and start placing your order.</p>
            <a href="<%= request.getContextPath() %>/devices">Go to Shop</a>
        </div>

        <div class="box">
            <img src="./images/history.jpg">
            <h3>Order History</h3>
            <p>View your past orders and track their status.</p>
            <a href="orderHistory.jsp">View Orders</a>
        </div>

        <div class="box">
            <img src="./images/logout.jpg">
            <h3>Logout</h3>
            <p>Logout from your account securely.</p>
            <a href="LogoutServlet">Logout</a>
        </div>
    </section>
    <% } %>

</body>
</html>