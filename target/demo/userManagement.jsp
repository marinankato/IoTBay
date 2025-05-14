<%@ page import="java.util.*, model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management - System Admin</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        .container { max-width: 900px; margin: auto; }
        table, th, td { border: 1px solid #ddd; border-collapse: collapse; padding: 10px; }
        th { background-color: #f2f2f2; }
        .form-group { margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="container">
    <h2>System Admin - User Management</h2>

    <!-- Create User Form -->
    <h3>Create New User</h3>
    <form action="user-management" method="post">
        <input type="hidden" name="action" value="create" />
        <div class="form-group">
            First Name: <input type="text" name="firstName" required />
            Last Name: <input type="text" name="lastName" required />
            Phone Number: <input type="text" name="phone" required />
        </div>
        <div class="form-group">
            Email: <input type="email" name="email" required />
            Password: <input type="password" name="password" required />
        </div>
        <div class="form-group">
            User Type:
            <select name="role">
                <option value="customer">Customer</option>
                <option value="staff">Staff</option>
            </select>
        </div>
        <button type="submit">Create User</button>
    </form>

    <hr/>

    <!-- Search User -->
    <h3>Search Users</h3>
    <form action="user-management" method="get">
        First Name: <input type="text" name="firstName" />
        Last Name: <input type="text" name="lastName" />
        Phone: <input type="text" name="phone" />
        <button type="submit">Search</button>
    </form>

    <hr/>

    <!-- Update User Form -->
    <%
        Map<String, String> editingUser = (Map<String, String>) request.getAttribute("editingUser");
        if (editingUser != null) {
    %>
        <h3>Update User</h3>
        <form action="user-management" method="post">
            <input type="hidden" name="action" value="updateUser" />
            <input type="hidden" name="userId" value="<%= editingUser.get("id") %>" />
            <div class="form-group">
                First Name: <input type="text" name="firstName" value="<%= editingUser.get("firstName") %>" required />
                Last Name: <input type="text" name="lastName" value="<%= editingUser.get("lastName") %>" required />
                Phone: <input type="text" name="phone" value="<%= editingUser.get("phoneNo") %>" required />
            </div>
            <div class="form-group">
                Email: <input type="email" name="email" value="<%= editingUser.get("email") %>" required />
                Role:
                <select name="role">
                    <option value="customer" <%= "customer".equals(editingUser.get("role")) ? "selected" : "" %>>Customer</option>
                    <option value="staff" <%= "staff".equals(editingUser.get("role")) ? "selected" : "" %>>Staff</option>
                </select>
                Status:
                <select name="status">
                    <option value="active" <%= "active".equals(editingUser.get("status")) ? "selected" : "" %>>Active</option>
                    <option value="inactive" <%= "inactive".equals(editingUser.get("status")) ? "selected" : "" %>>Inactive</option>
                </select>
            </div>
            <button type="submit">Update User</button>
        </form>
        <hr/>
    <%
        }
    %>

    <!-- User List Table -->
    <h2>User List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th><th>First Name</th><th>Last Name</th><th>Phone</th><th>Email</th><th>Role</th><th>Status</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<Map<String, String>> users = (List<Map<String, String>>) request.getAttribute("users");
            if (users != null) {
                for (Map<String, String> user : users) {
        %>
            <tr>
                <td><%= user.get("id") %></td>
                <td><%= user.get("firstName") %></td>
                <td><%= user.get("lastName") %></td>
                <td><%= user.get("phoneNo") %></td>
                <td><%= user.get("email") %></td>
                <td><%= user.get("role") %></td>
                <td><%= user.get("status") %></td>
                <td>
                    <a href="user-management?action=update&id=<%= user.get("id") %>">Update</a> |
                    <a href="user-management?action=delete&id=<%= user.get("id") %>">Delete</a>
                </td>
            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
