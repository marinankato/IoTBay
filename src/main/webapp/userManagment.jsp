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
    <form action="CreateUserServlet" method="post">
        <div class="form-group">
            Full Name: <input type="text" name="fullName" required />
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
    <form action="SearchUserServlet" method="get">
        Full Name: <input type="text" name="fullName" />
        Phone: <input type="text" name="phone" />
        <button type="submit">Search</button>
    </form>

    <hr/>

    <!-- User List -->
    <h3>User List</h3>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Phone</th>
            <th>Email</th>
            <th>User Type</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%-- Insert dynamic user rows here using JSTL or Java code --%>
        </tbody>
    </table>
</div>
</body>
</html>