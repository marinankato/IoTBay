<%@ page language="java" contentType="text/html; charset=UTF-8"
     import="model.User,model.Order,model.CartItem,java.util.List,java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>IoTBay | Order Details</title>
  <style>
    * { margin:0; padding:0; box-sizing:border-box; }
    body {
      font-family: Arial, sans-serif;
      background-color: #f7f9fc;
      padding: 40px;
      color: #333;
    }
    .header {
      width: 100%;
      background-color: #fff;
      padding: 20px 40px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      position: fixed; top:0; left:0;
      display:flex; align-items:center;
      z-index:1000;
    }
    .logo {
      font-size:2em; font-weight:bold;
      color:#007bff; text-decoration:none;
    }
    .logo:hover { color:#0056b3; }
    .welcomeText {
      margin-left:auto; color:#555;
      font-size:1.1em;
    }
    .container {
      max-width:900px; margin:100px auto 0;
      padding:0 20px;
    }
    h2 { color:#007bff; margin-bottom:15px; }
    table {
      width:100%; border-collapse:collapse;
      background:#fff; box-shadow:0 2px 8px rgba(0,0,0,0.05);
      border-radius:8px; overflow:hidden; margin-bottom:30px;
    }
    th,td {
      padding:12px 16px; border-bottom:1px solid #e0e0e0;
      text-align:left;
    }
    th { background:#007bff; color:#fff; }
    tr:hover { background:#f1f5ff; }
    .btn-new-order {
      display:inline-block; padding:8px 14px;
      background:#007bff; color:#fff; border-radius:6px;
      text-decoration:none; transition:background 0.2s;
    }
    .btn-new-order:hover { background:#0056b3; }

    /* our new red cancel */
    .btn-cancel {
      display:inline-block; padding:8px 14px;
      background:#dc3545; color:#fff; border-radius:6px;
      cursor:pointer; border:none; transition:background 0.2s;
    }
    .btn-cancel:hover { background:#c82333; }
  </style>
</head>
<body>
<%
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
  Order order      = (Order) request.getAttribute("order");
  List<CartItem> items = (List<CartItem>) request.getAttribute("items");
%>
<div class="header">
    <a href="dashboard.jsp" class="logo">IoTBay</a>
    <span class="welcomeText">Logged in as: <%= user.getEmail() %></span>
  </div>

<div class="container">
  <h2>Order #<%= order.getOrderID() %> Details</h2>
  <p><strong>Date:</strong>
     <%= new SimpleDateFormat("yyyy-MM-dd")
            .format(order.getOrderDate()) %>
  </p>
  <p><strong>Total:</strong>
     $<%= String.format("%.2f", order.getTotalPrice()) %>
  </p>

  <table>
    <thead>
      <tr>
        <th>Item</th>
        <th>Unit Price</th>
        <th>Quantity</th>
        <th>Line Total</th>
      </tr>
    </thead>
    <tbody>
    <% for (CartItem ci : items) { %>
      <tr>
        <td><%= ci.getName() %></td>
        <td>$<%= String.format("%.2f", ci.getUnitPrice()) %></td>
        <td><%= ci.getQuantity() %></td>
        <td>
          $<%= String.format("%.2f",
                ci.getUnitPrice() * ci.getQuantity()) %>
        </td>
      </tr>
    <% } %>
    </tbody>
  </table>

  <a href="<%= request.getContextPath() %>/order"
     class="btn-new-order">
    « Back to Orders
  </a>
</div>
</body>
</html>