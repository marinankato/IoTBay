<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         import="model.ShoppingCart,model.User,model.Order,model.IoTDevice,java.util.List,java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>IoTBay | Order History</title>
  <style>
    * {
       margin: 0;
       padding: 0;
        box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
      background-color: #f7f9fc;
      margin: 0;
      padding: 40px;
      color: #333;
    }

    .header {
      width: 100%;
      background-color: #ffffff;
      padding: 20px 40px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      position: fixed;
      top: 0; left: 0;
      display: flex;
      justify-content: space-between;
      align-items: center;
      z-index: 1000;
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
        margin-left: auto;
    }

    .container {
      max-width: 900px;
      margin: 100px auto 0;
      padding: 0 20px;
    }

    h2 {
      color: #007bff;
      margin-bottom: 15px;
    }

    .search-add {
      margin-bottom: 30px;
    }
    form.search {
      display: flex;
      gap: 10px;
      flex-wrap: wrap;
      margin-bottom: 20px;
    }
    input[type="text"],
    input[type="date"] {
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 6px;
      width: 180px;
    }
    input[type="submit"] {
      padding: 8px 14px;
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      transition: background-color 0.2s ease;
    }
    input[type="submit"]:hover {
      background-color: #0056b3;
    }

    /* messages */
    .message {
      color: green;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .error {
      color: red;
      font-weight: bold;
      margin-bottom: 20px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      background-color: #fff;
      box-shadow: 0 2px 8px rgba(0,0,0,0.05);
      border-radius: 8px;
      overflow: hidden;
      margin-bottom: 30px;
    }
    th, td {
      padding: 12px 16px;
      border-bottom: 1px solid #e0e0e0;
      text-align: left;
    }
    th {
      background-color: #007bff;
      color: #fff;
    }
    tr:hover {
      background-color: #f1f5ff;
    }

    .operation {
      display: inline-block;
      margin-right: 5px;
    }
    .btn-new-order {
      display: inline-block;
      padding: 8px 14px;
      background-color: #007bff;
      color: #fff;
      border-radius: 6px;
      text-decoration: none;
      margin-bottom: 20px;
      transition: background-color 0.2s;
    }
    .btn-new-order:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
  <%
  // Must be logged in
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
  // Pull orders list from session (set by OrderServlet)

  List<Order> orders = (List<Order>) session.getAttribute("orders");
    // Get cart count
  ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
  int cartCount = (cart == null ? 0 : cart.getItems().size());
%>

  <div class="header">        
    <a href="dashboard.jsp" class="logo">IoTBay</a>
    <span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
  </div>

<div class="container">
  <h2>Your Orders</h2>
  <div class="search-add">
    <a href="devices" class="btn-new-order">New Order</a>
    <a href="<%= request.getContextPath() %>/cart" class="btn-new-order">
      View Cart (<%= cartCount %>)
    </a>
  </div>

  <form class="search"
        action="<%= request.getContextPath() %>/order"
        method="get">
    <label>Order ID:
      <input type="text" name="orderID"
             value="<%= request.getParameter("orderID")==null?"":request.getParameter("orderID") %>"/>
    </label>
    <label>Date:
      <input type="date" name="orderDate"
             value="<%= request.getParameter("orderDate")==null?"":request.getParameter("orderDate") %>"/>
    </label>
    <button type="submit">Search</button>
  </form>

  <% 
     String error = (String) request.getAttribute("error");
     if (error != null) {
  %>
    <p class="error"><%= error %></p>
  <%
     }
  %>
  <% if (orders != null && !orders.isEmpty()) { %>
    <table> 
      <tr><th>ID</th><th>Date</th><th>Total</th><th>Status</th><th>Actions</th></tr>
      <% for (Order o : orders) {
           String fmt = new java.text.SimpleDateFormat("yyyy-MM-dd")
                           .format(o.getOrderDate());
      %>
        <tr>
          <td><%= o.getOrderID() %></td>
          <td><%= fmt %></td>
          <td>$<%= String.format("%.2f", o.getTotalPrice()) %></td>
          <td><%= o.getOrderStatus() ? "Submitted" : "Saved" %></td>
          <td>
            <% if (!o.getOrderStatus()) { %>
            <form action="<%= request.getContextPath() %>/order" method="post" style="display:inline">
              <input type="hidden" name="action"    value="update"/>
              <input type="hidden" name="orderID"   value="<%= o.getOrderID() %>"/>
              <!-- keep status=false so still "saved" -->
              <input type="hidden" name="orderStatus" value="false"/>
              <input type="date"   name="orderDate"  value="<%= fmt %>" required/>
              <input type="text"   name="totalPrice"
                     value="<%= String.format("%.2f", o.getTotalPrice()) %>" required/>
              <button type="submit">Update</button>
            </form>
            <form action="<%= request.getContextPath() %>/order" method="post" style="display:inline">
              <input type="hidden" name="action"  value="cancel"/>
              <input type="hidden" name="orderID" value="<%= o.getOrderID() %>"/>
              <button type="submit">Cancel</button>
            </form>
          <% } %>
        </td>
      </tr>
      <% } %>
    </table>
  <% } else { %>
    <p>No orders found.</p>
  <% } %>
</div>
</body>
</html>