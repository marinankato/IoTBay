<%@ page import="model.User"%>
<%@ page import="model.AccessLogs" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Profile | IoTBay</title>    
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
        padding-top: 100px;
    }

    .header {
        width: 100%;
        background-color: #ffffff;
        padding: 20px 40px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
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
        color: #555555;
    }

    .profile-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        gap: 30px;
        width: 90%;
        max-width: 1200px;
        margin-top: 30px;
    }

    .box {
        background-color: #ffffff;
        flex: 1 1 45%;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }

    h2, h3 {
        margin-bottom: 20px;
        color: #007bff;
    }

    label {
        display: block;
        margin-bottom: 6px;
        font-weight: bold;
        color: #333;
    }

    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 6px;
    }

    .checkbox-container {
        margin-bottom: 15px;
        display: flex;
        align-items: center;
    }

    .checkbox-container label {
        margin-right: 10px;
    }

    .save-button {
        background-color: #007bff;
        color: #ffffff;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        font-weight: bold;
        cursor: pointer;
    }

    .save-button:hover {
        background-color: #0056b3;
    }

    .deactivate-button {
        margin-top: 15px;
        background-color: #dc3545;
        color: #ffffff;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        font-weight: bold;
        cursor: pointer;
    }

    .deactivate-button:hover {
        background-color: #c82333;
    }

    .filter-button {
        background-color: #007bff;
        color: #ffffff;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        font-weight: bold;
        cursor: pointer;
    }

    .filter-button:hover {
        background-color: #0056b3;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    th, td {
        border: 1px solid #dddddd;
        padding: 10px;
        text-align: left;
    }

    th {
        background-color: #f8f9fa;
        color: #333333;
    }

    td {
        color: #555555;
    }

    .no-logs {
        text-align: center;
        color: #999999;
    }

    .errorMessage {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px 12px;
            margin-bottom: 20px;
            border-left: 5px solid #f5c6cb;
            border-radius: 4px;
            font-size: 14px;
            text-align: left;
        }
  </style>
</head>

<%
  User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
  List<AccessLogs> logs = (List<AccessLogs>) request.getAttribute("accessLogs");

    // show the error message if invalid edit/update attempt 
    String errMsg = (String)session.getAttribute("errorMsg");
    if (errMsg != null) { 
%>
    <div class="errorMessage"><%= errMsg %></div>
<%
    session.removeAttribute("errorMsg"); // remove errorMsg from session after displaying
    } 
%>

<body>
  <div class="header">
    <a href="dashboard.jsp" class="logo">IoTBay</a>
    <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
  </div>

  <div class="profile-container">
    <div class="box">
      <h2>Edit Profile</h2>
        <form action="EditUserServlet" method="post">
          <label for="firstName">First Name:</label>
          <input type="text" id="firstName" name="firstName" value="<%= user.getFirstName() %>" required>

          <label for="lastName">Last Name:</label>
          <input type="text" id="lastName" name="lastName" value="<%= user.getLastName() %>" required>

          <label for="phoneNo">Phone Number:</label>
          <input type="text" id="phoneNo" name="phoneNo" value="<%= user.getPhoneNo() %>" required>

          <input type="hidden" name="originalEmail" value="<%= user.getEmail() %>">
          <label for="email">Email:</label>
          <input type="text" id="email" name="email" value="<%= user.getEmail() %>" required>

          <label for="password">Password:</label>
          <input type="password" id="password" name="password" value="<%= user.getPassword() %>" required>

          <div class="checkbox-container">
              <label for="showPassword">Show Password</label>
              <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()">
          </div>

          <input type="submit" class="save-button" value="Save Changes">
        </form>

        <form action="DeactivateUserServlet" method="post"
              onsubmit="return confirm('Are you sure you want to deactivate your account?');">
            <input type="hidden" name="email" value="<%= user.getEmail() %>">
            <input type="submit" class="deactivate-button" value="Cancel Registration">
        </form>
    </div>

    <div class="box">
        <h3>Access Logs</h3>

        <%-- filter the date to view logs --%>
        <form action="EditUserServlet" method="get">
            <label for="filterDate">Filter by Date:</label>
            <%
                String filterDate = request.getParameter("filterDate");
                if (filterDate == null) filterDate = "";
            %>
            <input type="date" id="filterDate" name="filterDate" value="<%= filterDate %>">
            <input type="submit" class="filter-button" value="Filter">
        </form>
        
        <table>
            <tr>
                <th>Action</th>
                <th>Access Date</th>
            </tr>
            <%
                if (logs != null && !logs.isEmpty()) {
                    for (AccessLogs log : logs) {
            %>
                <tr>
                    <td><%= log.getAction() %></td>
                    <td><%= log.getAccessDate().format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")) %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="2" class="no-logs">No access logs found.</td>
                </tr>
            <%
                }
            %>
        </table>
    </div>
  </div>

  <script>
    function togglePasswordVisibility() {
      const passwordField = document.getElementById("password");
      const checkbox = document.getElementById("showPassword");
      passwordField.type = checkbox.checked ? "text" : "password";
    }
  </script>
</body>
</html>
