<html>
<title>IoTBay</title>

<% 
	String username = request.getParameter("username"); 
	String password = request.getParameter("password");
%>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	body {
		background-color: #f4f4f4;
		display: flex;
	}
	#sidebar {
		top: 0;
		left: 10;
		width: 250px;
		height: 100%;
		position: fixed;
		background-color: #f9f9f9;
		font-family: 'Arial', sans-serif;
	}
	#sidebar .logo {
		font-size: 24px;
		font-weight: 700;
		margin-left: 20px;
		height: 56px;
		display:flex;
		align-items: center;
		color: #007bff;
	}
	#sidebar .menubar{
		width: 100%;
		margin-top: 48px;
	}
	#sidebar .menubar li {
	height: 48px;
	background: transparent;
	margin-left: 30px;
	}
	#sidebar .menubar li a {
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
		border-radius: 48px;
		font-size: 16px;
		color:#2e2e2e;
	}
	#sidebar .menubar li a:hover {
		color: #3C91E6;
	}
	#content {
		top: 0;
		margin-left: 280px;
		width: calc(100% - 250px);
	}
	#content main {
		width: 100%;
		padding: 36px 24px;
		font-family: 'Arial', sans-serif;
	}
	#content .welcomeText {
		font-family: 'Arial', sans-serif;
	}
</style>

<% 
	// check if the username and password are correct
    if ("admin".equals(username) && "password123".equals(password)) {
%> 
<body>
<!-- SIDEBAR -->
<section id="sidebar">	
		<a href="dashboard.jsp" class="logo">	
		<span class="text">IoTBay</span>
		</a>
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

	<main>
		<h1 class=welcomeText>Hi, <%= username%>!</h1><br>
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
					<%-- Show the user if staff is logged in  --%>
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