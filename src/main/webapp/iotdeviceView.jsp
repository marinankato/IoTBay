<%@ page import="java.util.List" %>
<%@ page import="model.IoTDevice" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
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

        .action-form {
            display: inline-block;
            margin-right: 5px;
        }

        .message {
            color: green;
            font-weight: bold;
            margin-top: 10px;
        }

        .form-section {
            margin-bottom: 40px;
        }
    </style>
</head>
<body>

    <h2>IoT Device Catalogue</h2>

    <div class="form-section">
        <h3>Search Devices</h3>
        <form action="<%= request.getContextPath() %>/devices" method="post">
            <input type="hidden" name="action" value="search" />
            <input type="text" name="name" placeholder="Device name" />
            <input type="text" name="type" placeholder="Device type" />
            <input type="submit" value="Search" />
        </form>
    </div>

    <% String msg = (String) request.getAttribute("message"); %>
    <% if (msg != null) { %>
        <p class="message"><%= msg %></p>
    <% } %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Price</th>
                <th>Quantity</th>
                <% if (isStaff) { %><th>Actions</th><% } %>
            </tr>
        </thead>
        <tbody>
            <%
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
                    <form class="action-form" action="<%= request.getContextPath() %>/devices" method="get">
                        <input type="hidden" name="action" value="edit" />
                        <input type="hidden" name="id" value="<%= device.getId() %>" />
                        <input type="submit" value="Edit" />
                    </form>

                    <form class="action-form" action="<%= request.getContextPath() %>/devices" method="post">
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
    <div class="form-section">
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