<%@ page import="model.IoTDevice" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) session.getAttribute("user");
    boolean isStaff = user != null && "staff".equalsIgnoreCase(user.getRole());

    if (!isStaff) {
        response.sendRedirect(request.getContextPath() + "/devices");
        return;
    }

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
        }

        h2 {
            color: #007bff;
            margin-bottom: 20px;
        }

        form {
            background-color: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 8px 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[type="submit"] {
            margin-top: 20px;
            background-color: #007bff;
            color: #fff;
            padding: 10px 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Edit IoT Device</h2>

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

    <a href="<%= request.getContextPath() %>/devices" class="back-link">← Back to Catalogue</a>
</body>
</html>
