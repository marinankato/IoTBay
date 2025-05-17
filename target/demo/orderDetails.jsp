<%@ page import="model.Order,model.CartItem,java.util.List" %>
<%
  Order order = (Order) request.getAttribute("order");
  @SuppressWarnings("unchecked")
  List<CartItem> items = (List<CartItem>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>…your header/css…</head>
<body>
  <h2>Order #<%=order.getOrderID()%> Details</h2>
  <p>Date: <%= new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(order.getOrderDate()) %></p>
  <p>Total: $<%= String.format("%.2f", order.getTotalPrice()) %></p>

  <table>
    <tr><th>Item</th><th>Unit Price</th><th>Qty</th><th>Line Total</th></tr>
    <% for (CartItem ci : items) { %>
      <tr>
        <td><%= ci.getName() %></td>
        <td>$<%= String.format("%.2f", ci.getUnitPrice()) %></td>
        <td><%= ci.getQuantity() %></td>
        <td>$<%= String.format("%.2f", ci.getLineTotal()) %></td>
      </tr>
    <% } %>
  </table>

  <a href="<%=request.getContextPath()%>/order">« Back to Orders</a>
</body>
</html>