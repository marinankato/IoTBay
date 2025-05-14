<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         import="model.User,model.ShoppingCart,model.CartItem,java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>IoTBay | Your Cart</title>
  <style>
    /* page background & text */
    body {
      font-family: Arial, sans-serif;
      background-color: #f7f9fc;
      margin: 0;
      padding: 40px;
      color: #333;
    }
    /* fixed header */
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
    .logo:hover { color: #0056b3; }
    .welcomeText {
      font-size: 1.1em;
      color: #555555;
      margin-right: 50px;
    }
    /* container below header */
    .container {
      max-width: 900px;
      margin: 100px auto 0;
      padding: 0 20px;
    }
    h2 {
      color: #007bff;
      margin-bottom: 15px;
    }
    /* messages */
    .message {
      color: green;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .empty {
      font-style: italic;
      margin-bottom: 20px;
    }
    /* button style */
    .btn {
      display: inline-block;
      padding: 8px 14px;
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 6px;
      text-decoration: none;
      cursor: pointer;
      transition: background-color 0.2s ease;
    }
    .btn:hover { background-color: #0056b3; }
    /* table styling */
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
    tr:hover { background-color: #f1f5ff; }
    .operation {
      display: inline-block;
      margin-right: 5px;
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
  // Pull (or create) the cart
  ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
  if (cart == null) {
    cart = new ShoppingCart();
    session.setAttribute("shoppingCart", cart);
  }
  // Any success/error message?
  String msg = (String) session.getAttribute("message");
  if (msg != null) {
    out.println("<p class='message'>" + msg + "</p>");
    session.removeAttribute("message");
  }
%>

<div class="header">
  <a href="dashboard.jsp" class="logo">IoTBay</a>
  <span class="welcomeText">Logged in as: <%= user.getEmail() %></span>
</div>

<div class="container">
  <h2>Your Shopping Cart</h2>

  <% if (cart.isEmpty()) { %>
    <p class="empty">Your cart is empty.</p>
    <a href="devices" class="btn">Continue Shopping</a>
  <% } else { %>
    <table>
      <tr>
        <th>Item</th>
        <th>Unit Price</th>
        <th>Quantity</th>
        <th>Line Total</th>
        <th>Actions</th>
      </tr>
      <% for (CartItem item : cart.getItems()) { %>
        <tr>
          <td><%= item.getName() %></td>
          <td>$<%= String.format("%.2f", item.getUnitPrice()) %></td>
          <td><%= item.getQuantity() %></td>
          <td>$<%= String.format("%.2f", item.getLineTotal()) %></td>
          <td>
            <form action="<%= request.getContextPath() %>/cart" method="post" style="display:inline">
              <input type="hidden" name="action"   value="remove"/>
              <input type="hidden" name="deviceId" value="<%= item.getDeviceId() %>"/>
              <button type="submit" class="btn">Remove</button>
            </form>
          </td>
        </tr>
      <% } %>
      <tr>
        <td colspan="3"><strong>Total:</strong></td>
        <td colspan="2">$<%= String.format("%.2f", cart.getTotal()) %></td>
      </tr>
    </table>

    <form action="<%= request.getContextPath() %>/order" method="post">
      <input type="hidden" name="action"     value="checkout"/>
      <input type="hidden" name="totalPrice" value="<%= cart.getTotal() %>"/>
      <button type="submit" class="btn">Proceed to Checkout</button>
    </form>
  <% } %>
</div>
</body>
</html>