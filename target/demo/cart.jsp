<%@ page import="model.User,model.ShoppingCart,model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>IoTBay | Your Cart</title>
  <style>
    body { font-family:Arial; background:#f7f9fc; margin:0; padding:40px; color:#333; }
    .header { width:100%; background:#fff; padding:20px 40px; box-shadow:0 2px 5px rgba(0,0,0,0.1);
              position:fixed; top:0; left:0; display:flex; justify-content:space-between; align-items:center;}
    .logo { font-size:2em; color:#007bff; text-decoration:none; }
    .logo:hover { color:#0056b3; }
    .welcomeText { font-size:1.1em; color:#555; }
    .container { max-width:900px; margin:100px auto 0; padding:0 20px; }
    h2 { color:#007bff; margin-bottom:15px; }
    table { width:100%; border-collapse:collapse; background:#fff; box-shadow:0 2px 8px rgba(0,0,0,0.05);
             border-radius:8px; overflow:hidden; margin-bottom:30px;}
    th,td { padding:12px 16px; border-bottom:1px solid #e0e0e0; text-align:left; }
    th { background:#007bff; color:#fff; }
    tr:hover { background:#f1f5ff; }
    .btn { padding:8px 14px; background:#007bff; color:#fff; border:none; border-radius:6px;
           cursor:pointer; transition:background 0.2s; }
    .btn:hover { background:#0056b3; }
    .empty { font-style:italic; margin-bottom:20px; }
    form.inline { display:inline; margin-right:5px; }
    form.inline input[type="number"] { width:60px; padding:4px; }
  </style>
</head>
<body>
<%
User user = (User) session.getAttribute("user");
if (user == null) { response.sendRedirect("login.jsp"); return; }

// get or create cart
ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
if (cart == null) {
  cart = new ShoppingCart();
  session.setAttribute("shoppingCart", cart);
}

// show & clear any error/message
String error   = (String) session.getAttribute("error");
String message = (String) session.getAttribute("message");
if (error != null) {
%>
  <p class="error"><%= error %></p>
<%
  session.removeAttribute("error");
}
if (message != null) {
%>
  <p class="message"><%= message %></p>
<%
  session.removeAttribute("message");
}
%>

<div class="header">
  <a href="dashboard.jsp" class="logo">IoTBay</a>
  <span class="welcomeText">Logged in as: <%=user.getFirstName()%></span>
</div>

<div class="container">
  <h2>Your Shopping Cart</h2>

  <% if (cart.isEmpty()) { %>
    <p class="empty">Your cart is empty.</p>
    <a href="<%= request.getContextPath() %>/devices" class="btn">Continue Shopping</a>
  <% } else { %>
    <table>
      <tr><th>Item</th><th>Unit Price</th><th>Quantity</th><th>Line Total</th><th>Actions</th></tr>
      <% for (CartItem item : cart.getItems()) { %>
        <tr>
          <td><%= item.getName() %></td>
          <td>$<%= String.format("%.2f", item.getUnitPrice()) %></td>
          <td><%= item.getQuantity() %></td>
          <td>$<%= String.format("%.2f", item.getLineTotal()) %></td>
          <td>
            <!-- update quantity -->
            <form class="inline"
                  action="<%= request.getContextPath() %>/cart"
                  method="post">
              <input type="hidden" name="action"    value="updateQty"/>
              <input type="hidden" name="deviceId"  value="<%= item.getDeviceId() %>"/>
              <input type="number" name="quantity" min="1" value="<%= item.getQuantity() %>"/>
              <button type="submit" class="btn">Update</button>
            </form>
            <!-- remove item -->
            <form class="inline"
                  action="<%= request.getContextPath() %>/cart"
                  method="post">
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

    <!-- proceed to checkout -->
    <form action="<%= request.getContextPath() %>/checkout" method="post">
      <input type="hidden" name="action"     value="checkout"/>
      <input type="hidden" name="totalPrice" value="<%= cart.getTotal() %>"/>
      <button type="submit" class="btn">Proceed to Checkout</button>
    </form>
  <% } %>
</div>
</body>
</html>