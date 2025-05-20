<%@ page import="java.util.*, model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management - System Admin</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .header {
            width: 100%;
            background-color: #fff;
            padding: 20px 40px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
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
            color: #555;
        }

        .container {
            max-width: 900px;
            margin: 100px auto 20px; /* space below fixed header */
        }

        table, th, td {
            border: 1px solid #ddd;
            border-collapse: collapse;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: inline-block;
            width: 120px;
            font-weight: bold;
        }

        input[type="text"], input[type="email"], input[type="password"], select {
            width: 250px;
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        button, input[type="submit"] {
            padding: 8px 16px;
            background-color: #007bff;
            border: none;
            border-radius: 3px;
            color: white;
            cursor: pointer;
        }

        button:hover, input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Map<String, String>> users = (List<Map<String, String>>) request.getAttribute("users");
    Map<String, String> editingUser = (Map<String, String>) request.getAttribute("editingUser");
%>

<body>
    <div class="header">
        <a href="dashboard.jsp" class="logo">IoTBay</a>
        <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
    </div>

    <div class="container">
        <h2>User Management - System Admin</h2>

        <!-- Create User Form -->
        <h3>Create New User</h3>
        <form action="user-management" method="post">
            <input type="hidden" name="action" value="create" />
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input id="firstName" name="firstName" type="text" required />
            </div>
            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input id="lastName" name="lastName" type="text" required />
            </div>
            <div class="form-group">
                <label for="phoneNo">Phone Number:</label>
                <input id="phoneNo" name="phoneNo" type="text" required />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input id="email" name="email" type="email" required />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input id="password" name="password" type="password" required />
            </div>
            <div class="form-group">
                <label for="role">User Type:</label>
                <select id="role" name="role">
                    <option value="customer">Customer</option>
                    <option value="staff">Staff</option>
                </select>
            </div>
            <button type="submit">Create User</button>
        </form>

        <hr />

        <!-- Search Users & Display all Users -->
        <h3>Search Users and Display all Users</h3>
        <form action="user-management" method="get">
            <div class="form-group">
                <label for="searchFirstName">First Name:</label>
                <input id="searchFirstName" name="firstName" type="text" />
            </div>
            <div class="form-group">
                <label for="searchLastName">Last Name:</label>
                <input id="searchLastName" name="lastName" type="text" />
            </div>
            <div class="form-group">
                <label for="searchPhoneNo">Phone:</label>
                <input id="searchPhoneNo" name="phoneNo" type="text" />
            </div>
            <button type="submit">Search</button>
            <p style="font-size: 0.9em; color: #666; margin-top: 10px;">
                To display all users, leave the search inputs empty.
            </p>
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
                <%
                    for (Map<String, String> u : users) {
                        String userID = u.get("userID");
                %>
                    <tr>
                        <td><%= userID %></td>
                        <td><%= u.get("firstName") %></td>
                        <td><%= u.get("lastName") %></td>
                        <td><%= u.get("phoneNo") %></td>
                        <td><%= u.get("email") %></td>
                        <td><%= u.get("role") %></td>
                        <td>
                            <form action="user-management" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="updateUser" />
                                <input type="hidden" name="userID" value="<%= userID %>" />
                                <input type="submit" value="Edit" />
                            </form>
                            <form action="user-management" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this user?');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="userID" value="<%= userID %>" />
                                <input type="submit" value="Delete" />
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <%
                }
            }
        %>

        <%
            if (editingUser != null) {
        %>
        <hr />
        <h3>Edit User</h3>
        <form action="user-management" method="post">
            <input type="hidden" name="action" value="updateUser" />
            <input type="hidden" name="userID" value="<%= editingUser.get("userID") %>" />
            <div class="form-group">
                <label for="editFirstName">First Name:</label>
                <input id="editFirstName" name="firstName" type="text" value="<%= editingUser.get("firstName") %>" required />
            </div>
            <div class="form-group">
                <label for="editLastName">Last Name:</label>
                <input id="editLastName" name="lastName" type="text" value="<%= editingUser.get("lastName") %>" required />
            </div>
            <div class="form-group">
                <label for="editPhoneNo">Phone Number:</label>
                <input id="editPhoneNo" name="phoneNo" type="text" value="<%= editingUser.get("phoneNo") %>" required />
            </div>
            <div class="form-group">
                <label for="editEmail">Email:</label>
                <input id="editEmail" name="email" type="email" value="<%= editingUser.get("email") %>" required />
            </div>
            <div class="form-group">
                <label for="editPassword">Password:</label>
                <input id="editPassword" name="password" type="password" value="<%= editingUser.get("password") %>" />
                <small>Leave blank to keep current password</small>
            </div>
            <div class="form-group">
                <label for="editRole">User Type:</label>
                <select id="editRole" name="role">
                    <option value="customer" <%= "customer".equals(editingUser.get("role")) ? "selected" : "" %>>Customer</option>
                    <option value="staff" <%= "staff".equals(editingUser.get("role")) ? "selected" : "" %>>Staff</option>
                </select>
            </div>
            <button type="submit">Update User</button>
        </form>
        <%
            }
        %>

    </div>
</body>
</html>
