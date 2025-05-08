<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html>
<title>IoTBay | Order History</title>

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
        justify-content: flex-start;
        padding: 40px 0;
    }

    .header {
        width: 100%;
        background-color: #ffffff;
        padding: 20px 0;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); 
        position: fixed;
        top: 0;
        left: 0;
        z-index: 1000;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 40px;
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
      
      table { 
        border-collapse:collapse; margin:20px auto; 
      }

      th,td { 
        border:1px solid #ccc; padding:8px; 
      }
      
      form.search { 
        margin:20px; 
      }
    }
</style>

  <body>

<% 
User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
List<Order> orders = (List<Order>) session.getAttribute("orders");
%>
 	<!-- Header Section with Logo and Welcome Text -->
    <div class="header">
        <a href="dashboard.jsp" class="logo">IoTBay</a>
        <span class="welcomeText">Logged in as: <%= user.getEmail() %></span>
    </div>
  
<h1>Your Orders</h1>

<form action="order" method="get">
  Search by ID: <input type="text" name="orderID"/>
  Date:         <input type="date" name="orderDate"/>
  <input type="submit" value="Search"/>
</form>
<h1>Your Orders</h1>

<form class="search" action="order" method="get">
  Order ID: <input type="text" name="orderID"/>
  Date:     <input type="date" name="orderDate"/>
  <input type="submit" value="Search"/>
</form>

<% if (orders != null && !orders.isEmpty()) { %>
  <table>
    <tr><th>ID</th><th>Date</th><th>Total</th><th>Status</th><th>Actions</th></tr>
    <% for (Order o : orders) {
         boolean submitted = o.getOrderStatus();
         String fmt = new SimpleDateFormat("yyyy-MM-dd").format(o.getOrderDate());
    %>
      <tr>
        <td><%= o.getOrderID()      %></td>
        <td><%= fmt                 %></td>
        <td>$<%= o.getTotalPrice()  %></td>
        <td><%= submitted ? "Submitted" : "Saved" %></td>
        <td>
          <% if (!submitted) { %>
            <!-- update form -->
            <form action="order" method="post" style="display:inline">
              <input type="hidden" name="action"     value="update"/>
              <input type="hidden" name="orderID"    value="<%= o.getOrderID() %>"/>
              <input type="date"   name="orderDate"  value="<%= fmt %>"/>
              <input type="text"   name="totalPrice" value="<%= o.getTotalPrice() %>"/>
              <select name="orderStatus">
                <option value="false" selected>Saved</option>
                <option value="true">Submitted</option>
              </select>
              <input type="submit" value="Update"/>
            </form>
            <!-- cancel form -->
            <form action="order" method="post" style="display:inline">
              <input type="hidden" name="action"  value="cancel"/>
              <input type="hidden" name="orderID" value="<%= o.getOrderID() %>"/>
              <input type="submit" value="Cancel"/>
            </form>
          <% } %>
        </td>
      </tr>
    <% } %>
  </table>
<% } else { %>
  <p>No orders found.</p>
<% } %>

</body>
</html>