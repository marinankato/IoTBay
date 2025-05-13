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
            <select name="userType">
                <option value="customer">Customer</option>
                <option value="staff">Staff</option>
            </select>
        </div>
        <button type="submit">Create User</button>
    </form>

    <hr/>

    <!-- Search User -->
    <h3>Search Users</h3>
    <form action="user-management"method="get">
        First Name: <input type="text" name="firstName" />
	    Last Name: <input type="text" name="lastName" /> <br>
        Phone: <input type="text" name="phone" /> <br>
        <button type="submit">Search</button>
    </form>

    <hr/>

    <!-- User List Table -->
    <h2>User List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Phone No</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                UserDAO dao = new UserDAO();
                List<Map<String, String>> users = (List<Map<String, String>>) request.getAttribute("users");
                for (Map<String, String> user : users) {
            %>
            <tr>
                <td><%= user.get("id") %></td>
                <td><%= user.get("firstName") %></td>
                <td><%= user.get("lastName") %></td>
                <td><%= user.get("phoneNo") %></td>
                <td><%= user.get("email") %></td>
                <td><%= user.get("role") %></td>
                <td>
                    <a href="user-management?action=update&id=<%= user.get("id") %>">Update</a> | 
                    <a href="user-management?action=delete&id=<%= user.get("id") %>">Delete</a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>
</body>
</html>