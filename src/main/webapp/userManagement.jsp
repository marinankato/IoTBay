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
        
        .container {
            margin-top: 50px;
        }

    </style>

</head>

<% User user = (User)session.getAttribute("user");
List<Map<String, String>> users = (List<Map<String, String>>) request.getAttribute("users");
Map<String, String> editingUser = (Map<String, String>) request.getAttribute("editingUser");
%>
<body>
    <div class="header">
        <a href="dashboard.jsp" class="logo">IoTBay</a>
        <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
    </div>

<div class="container">
    <h2>System Admin - User Management</h2>

    <!-- Create User Form -->
    <h3>Create New User</h3>
    <form action="user-management" method="post">
        <input type="hidden" name="action" value="create" />
        <div class="form-group">
            First Name: <input type="text" name="firstName" required />
            Last Name: <input type="text" name="lastName" required />
            Phone Number: <input type="text" name="phoneNo" required />
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

    <!-- Search User and User List -->
     <!-- combined htem due to issues with the two tables being in sync-->
    <h3>Search Users and Display all Users</h3>
    <form name="searchForm" action="user-management" method="get">
        First Name: <input type="text" name="firstName" />
        Last Name: <input type="text" name="lastName" />
        Phone: <input type="text" name="phoneNo" />
        <button type="submit">Search</button>
        <p>To display all users simply search with a empty input and all users will be displayed.</p>
    </form>

    <%
        if (users != null) {
            if (users.isEmpty()) {
    %>
        <p>No users found matching your search criteria.</p>
    <%
            } else {
    %>
        <p>Found <%= users.size() %> user(s):</p>
        <table>
            <thead>
                <tr>
                    <th>ID</th><th>First Name</th><th>Last Name</th><th>Phone</th><th>Email</th><th>Role</th><th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Map<String, String> u : users) { %>
                    <tr>
                        <td><%= u.get("userID") %></td>
                        <td><%= u.get("firstName") %></td>
                        <td><%= u.get("lastName") %></td>
                        <td><%= u.get("phoneNo") %></td>
                        <td><%= u.get("email") %></td>
                        <td><%= u.get("role") %></td>
                        <td>
                            <form action="user-management" method="get" style="display:inline;">
                                        <input type="hidden" name="action" value="updateUser" />
                                        <input type="hidden" name="id" value="<%= u.get("userID") %>" />
                                        <button type="submit">Update</button>
                            </form>
                            <form action="user-management" method="get" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this user?');">
                                        <input type="hidden" name="action" value="delete" />
                                        <input type="hidden" name="id" value="<%= u.get("userID") %>" />
                                        <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <%
            }
        }
    %>

    <hr/>

    <!-- Update Form (only appears if editingUser is set)  -->

        <% if (editingUser != null) { %>
            <hr />
            <h3>Update User</h3>
            <form action="user-management" method="post">
                <input type="hidden" name="action" value="updateUser" />
                <input type="hidden" name="userID" value="<%= editingUser.get("userID") %>" />
                <div class="form-group">
                    First Name: <input type="text" name="firstName" value="<%= editingUser.get("firstName") %>" required />
                    Last Name: <input type="text" name="lastName" value="<%= editingUser.get("lastName") %>" required />
                    Phone: <input type="text" name="phoneNo" value="<%= editingUser.get("phoneNo") %>" required />
                </div>
                <div class="form-group">
                    Email: <input type="email" name="email" value="<%= editingUser.get("email") %>" required />
                    Role:
                    <select name="role">
                        <option value="customer" <%= "customer".equals(editingUser.get("role")) ? "selected" : "" %>>Customer</option>
                        <option value="staff" <%= "staff".equals(editingUser.get("role")) ? "selected" : "" %>>Staff</option>
                    </select>
                </div>
                <button type="submit">Update User</button>
            </form>
        <% } %>
    
        
</div>
</body>
</html>
