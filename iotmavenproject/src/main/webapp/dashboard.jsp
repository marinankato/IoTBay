<html>
<title>IoTBay</title>

<% 
	String username = request.getParameter("username"); 
	String password = request.getParameter("password");
%>
<%-- <style>
</style> --%>

<% 
	// check if the username and password are correct
    if ("admin".equals(username) && "password123".equals(password)) {
%> 
<body>
    <!-- SIDEBAR -->
<section id="sidebar">		
		<label class="text">IoTBay</span>
	<ul class="menubar">
	<li>
			<a href="searchProducts.jsp">
				<span class="text">Start Ordering</span>
			</a>
		</li>
		<li>
			<a href="orderHistory.jsp">
				<span class="text">View Order History</span>
			</a>
		</li>
		<li>
			<a href="#">
				<span class="text">Account Settings</span>
			</a>
		</li>
		<li>
			<a href="login.jsp" class="logout">
				<span class="text">Logout</span>
			</a>
		</li>
	</ul>
</section>	
<!-- SIDEBAR -->


<!-- CONTENT -->
	<section id="content">
		<!-- MAIN -->
	<top>
	<h1>Hi, <%= username%>!</h1>
	</top>
	
	<main>

			<%-- Show something like this for staff view --%>
			<ul class="box-info">
				<li>
					<i class='bx bxs-calendar-check' ></i>
					<span class="text">
						<h3>1020</h3>
						<p>New Order</p>
					</span>
				</li>
				<li>
					<i class='bx bxs-group' ></i>
					<span class="text">
						<h3>2834</h3>
						<p>Visitors</p>
					</span>
				</li>
				<li>
					<i class='bx bxs-dollar-circle' ></i>
					<span class="text">
						<h3>$2543</h3>
						<p>Total Sales</p>
					</span>
				</li>
			</ul> 


	<div class="table-data">
		<div class="order">
			<div class="head">
			<h3>Recent Orders</h3>
			<i class='bx bx-search' ></i>
			<i class='bx bx-filter' ></i>
			</div>
			<table>
				<thead>
					<tr>
					Show the user if staff logged in 
					<th>User</th> 
					<th>Order Date</th>
					<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<tr>
					<td>
						<p>John Doe</p>
					</td>
						<td>01-10-2021</td>
						<td><span class="status completed">Completed</span></td>
					</tr>
					<tr>					
					<td>
						<p>John Doe</p>
					</td>
						<td>01-10-2021</td>
						<td><span class="status pending">Pending</span></td>
					</tr>
					<tr>
					<td>
						<p>John Doe</p>
					</td>
						<td>01-10-2021</td>
						<td><span class="status process">Process</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<a href="orderHistory.jsp"> Show more...</a>

	</main>
	<!-- MAIN -->
	</section>
	<!-- CONTENT -->
</body>
<%     
	} else {
       	out.println("<h2>Invalid username or password. Please try again <a href=login.jsp>here</a>.</h2>");
    }
%>
</html>