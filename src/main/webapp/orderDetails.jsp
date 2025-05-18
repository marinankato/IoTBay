<%@ page language="java" contentType="text/html; charset=UTF-8"
     import="model.User,model.IoTDevice,model.Order,model.CartItem,java.util.List,java.text.SimpleDateFormat" %>
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

  </style>
</head>
<body>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
  response.sendRedirect("login.jsp");
  return;
}
Order order = (Order) request.getAttribute("order");
List<CartItem> items = (List<CartItem>) request.getAttribute("items");
List<IoTDevice> all  = (List<IoTDevice>) request.getAttribute("allDevices");
%>
<div class="header">
<a href="dashboard.jsp" class="logo">IoTBay</a>
<span class="welcomeText">Logged in as: <%= user.getFirstName() %></span>
</div>

<div class="container">
    <h2>Order #<%=order.getOrderID()%> Details</h2>
    <p><strong>Total:</strong> $<%=order.getTotalPrice()%></p>
  
    <table>
      <tr><th>Item</th><th>Price</th><th>Qty</th><th>Line</th><th>Actions</th></tr>
      <% for(CartItem ci: items) { %>
        <tr>
          <td><%=ci.getName()%></td>
          <td>$<%=ci.getUnitPrice()%></td>
          <td><%=ci.getQuantity()%></td>
          <td>$<%=ci.getUnitPrice()*ci.getQuantity()%></td>
          <td>
            <form style="display:inline" method="post" action="orderDetails">
              <input type="hidden" name="orderID" value="<%=order.getOrderID()%>"/>
              <input type="hidden" name="action" value="remove"/>
              <input type="hidden" name="deviceID" value="<%=ci.getDeviceId()%>"/>
              <button type="submit">🗑️</button>
            </form>
            <form style="display:inline" method="post" action="orderDetails">
              <input type="hidden" name="orderID"    value="<%=order.getOrderID()%>"/>
              <input type="hidden" name="action"     value="updateQty"/>
              <input type="hidden" name="deviceID"   value="<%=ci.getDeviceId()%>"/>
              <input type="number" name="quantity"   min="1"
                     value="<%=ci.getQuantity()%>"/>
              <input type="hidden" name="unitPrice"
                     value="<%=ci.getUnitPrice()%>"/>
              <button type="submit">↺</button>
            </form>
          </td>
        </tr>
      <% } %>
      <tr><td colspan="5">
        <strong>Add another product:</strong>
          <form method="post" action="orderDetails" style="display:inline">
            <input type="hidden" name="orderID"  value="<%=order.getOrderID()%>"/>
            <input type="hidden" name="action"   value="add"/>
            <select name="deviceID">
              <% for (IoTDevice d : all) {
                   boolean already =
                     items.stream().anyMatch(ci -> ci.getDeviceId() == d.getId());
              %>
                <option value="<%=d.getId()%>"
                        <%= already ? "disabled" : "" %>>
                  <%=d.getName()%> (stock: <%=d.getQuantity()%>)
                </option>
              <% } %>
            </select>
            <input type="number" name="quantity" min="1" value="1"/>
            <%-- you could set unitPrice via JS onchange --%>
            <input type="hidden" name="unitPrice" value=""/>
            <button type="submit">➕ Add</button>
          </form>
        </td>
      </tr>
    </table>

    <a href="order" class="btn-new-order">« Back to Orders</a>
  </div>
</body>
</html>