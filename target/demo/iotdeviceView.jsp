<%@ page import="java.util.List" %>
<%@ page import="model.IoTDevice" %>
<%@ page import="model.User" %>


<%
    // Check if the user is logged in and has the role of "staff"
    User user = (User) session.getAttribute("user");
    boolean isStaff = user != null && "staff".equalsIgnoreCase(user.getRole());
%>
<html>
    <head>
        <title>IoT Device Catalogue</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f9fc;
                margin: 0;
                padding: 40px;
                color: #333;
            }

            h2, h3 {
                color: #007bff;
                margin-bottom: 15px;
                margin-top: 40px;
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

            th,td { 
                border:1px solid #ccc; padding:8px; 
            }
            
            form.search { 
                margin:20px; 
            }

            table {
                border-collapse: collapse;
                width: 100%;
                background-color: #ffffff;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                border-radius: 8px;
                overflow: hidden;
                margin-bottom: 30px;
            }

            th, td {
                padding: 12px 16px;
                border-bottom: 1px solid #e0e0e0;
            }

            th {
                background-color: #007bff;
                color: white;
                text-align: left;
            }

            tr:hover {
                background-color: #f1f5ff;
            }

            form {
                margin-top: 15px;
            }

            input[type="text"],
            input[type="number"] {
                padding: 8px;
                margin: 5px;
                border: 1px solid #ccc;
                border-radius: 6px;
                width: 200px;
            }

            input[type="submit"] {
                padding: 8px 14px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .operation {
                display: inline-block;
                margin-right: 5px;
            }

            .message {
                color: green;
                font-weight: bold;
                margin-top: 10px;
            }
            .error {
                color: red;
                font-weight: bold;
                margin-top: 10px;
            }
            .search-add {
                margin-bottom: 40px;
            }
        </style> 
    </head>
    <body>
        <div class="header">
            <a href="dashboard.jsp" class="logo">IoTBay</a>
            <span class="welcomeText">Logged in as: <%= user.getEmail() %></span>
        </div>

        <h2>IoT Device Catalogue</h2>

        <!-- error message -->
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>

        <div class="search-add">
            <h3>Search Devices</h3>
            <form action="<%= request.getContextPath() %>/devices" method="post">
                <input type="hidden" name="action" value="search" />
                <input type="text" name="name" placeholder="Device name" />
                <input type="text" name="type" placeholder="Device type" />
                <input type="submit" value="Search" />
            </form>
        </div>


        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <% if (isStaff) { %>
                        <th>Actions</th> 
                        <%-- if is staff can see actions --%>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <%
                    // Retrieve the list of devices from the request attribute
                    // This should be set in the servlet handling the request
                    List<IoTDevice> devices = (List<IoTDevice>) request.getAttribute("devices");
                    if (devices != null && !devices.isEmpty()) {
                        for (IoTDevice device : devices) {
                %>
                    <tr>
                        <td><%= device.getId() %></td>
                        <td><%= device.getName() %></td>
                        <td><%= device.getType() %></td>
                        <td>$<%= String.format("%.2f", device.getPrice()) %></td>
                        <td><%= device.getQuantity() %></td>
                        <% if (isStaff) { %>
                        <td>

                            <form class="operation" action="<%= request.getContextPath() %>/devices" method="get">
                                <input type="hidden" name="action" value="edit" />
                                <input type="hidden" name="id" value="<%= device.getId() %>" />
                                <input type="submit" value="Edit" />
                            </form>

                            <form class="operation" action="<%= request.getContextPath() %>/devices" method="post">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="id" value="<%= device.getId() %>" />
                                <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this device?');" />
                            </form>
                        </td>
                        <% } %>
                    </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="<%= isStaff ? 6 : 5 %>">No matching devices found.</td>
                </tr>
                <% } %>
            </tbody>
        </table>

        <% if (isStaff) { %>
            <div class="search-add">
                <h3>Add New Device</h3>
                <form action="<%= request.getContextPath() %>/devices" method="post">
                    <input type="text" name="name" placeholder="Device name" required />
                    <input type="text" name="type" placeholder="Device type" required />
                    <input type="number" step="0.01" name="price" placeholder="Price" required />
                    <input type="number" name="quantity" placeholder="Quantity" required />
                    <input type="submit" value="Add Device" />
                </form>
            </div>
        <% } %>

    </body>
</html>