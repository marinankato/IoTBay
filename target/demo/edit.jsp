<%@ page import="model.IoTDevice" %>
<%@ page import="model.User" %>

<%
    // Check if the user is logged in and has the role of "staff"
    User user = (User) session.getAttribute("user");
    boolean isStaff = user != null && "staff".equalsIgnoreCase(user.getRole());
    // If not, redirect to the devices page
    if (!isStaff) {
        response.sendRedirect(request.getContextPath() + "/devices");
        return;
    }
    // Get the device object from the request
    IoTDevice device = (IoTDevice) request.getAttribute("device");
%>

<html>
    <head>
        <title>Edit IoT Device</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f9fc;
                color: #333;
                padding: 40px;
                font-size: 18px;
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
                margin-right: 50px;
            }

            .container {
                max-width: 700px;
                margin: 0 auto;
                text-align: left;
            }

            h2 {
                color: #007bff;
                margin-bottom: 25px;
                text-align: center;
                font-size: 28px;
                margin-top: 40px;
            }

            form {
                background-color: #fff;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-top: 20px;
                font-weight: bold;
            }

            input[type="text"],
            input[type="number"] {
                width: 100%;
                padding: 12px;
                margin-top: 8px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 8px;
            }

            input[type="submit"] {
                margin-top: 25px;
                background-color: #007bff;
                color: #fff;
                padding: 12px 20px;
                font-size: 18px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.2s ease;
                width: 100%;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .back-link {
                display: block;
                margin-top: 25px;
                text-align: center;
                text-decoration: none;
                color: #007bff;
                font-size: 16px;
            }

            .back-link:hover {
                text-decoration: underline;
            }

            .error {
                color: red;
                font-weight: bold;
                margin-bottom: 20px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <a href="dashboard.jsp" class="logo">IoTBay</a>
            <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
        </div>
        <div class="container">
            <h2>Edit IoT Device</h2>

            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
                <p class="error"><%= error %></p>
                //% Clear the error message after displaying it
            <% } %>

            <form action="<%= request.getContextPath() %>/devices" method="post">
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="id" value="<%= device.getId() %>" />

                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="<%= device.getName() %>" required />

                <label for="type">Type:</label>
                <input type="text" id="type" name="type" value="<%= device.getType() %>" required />

                <label for="price">Price:</label>
                <input type="number" step="0.01" id="price" name="price" value="<%= device.getPrice() %>" required />

                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" value="<%= device.getQuantity() %>" required />

                <input type="submit" value="Update Device" />
            </form>

            <a href="<%= request.getContextPath() %>/devices" class="back-link"> Back to Catalogue</a>
        </div>
    </body>
</html>


