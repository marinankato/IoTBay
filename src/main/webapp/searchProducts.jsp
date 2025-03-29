<%@page import="model.User"%>
<html>
<title>IoTBay | Order Products</title>

<style>
    /* Style the container to center the search bar on the page */
    .searchbar {
    	display: flex;
    	justify-content: center;
    	align-items: center;
    	margin-top: 50px; /* Add some space at the top */
    }

    /* Style the input field */
    .searchbar input[type="text"] {
    	padding: 10px;
    	font-size: 16px;
    	border: 1px solid #ccc;
    	border-radius: 80px 0 0 80px; /* Rounded corners */
    	width: 500px; /* Set the width of the input field */
    	outline: none;
    }

    /* Style the button */
    .searchbar button {
		padding: 10;
    	background-color: #007bff; 
    	border: none;
    	border-radius: 0 80px 80px 0; /* Rounded corners */
    	cursor: pointer;
    }

    /* Style the image inside the button */
    .searchbar button img {
    	width: 20px;
    	height: 20px;
    	filter: brightness(0) invert(1); /* Make the icon white */
		margin: 0;
		vertical-align: middle;
    }

    /* Add a hover effect for the button */
    .searchbar button:hover {
    	background-color: #0056b3; /* Darker green */
    }

    /* Optional: Add a focus effect for the input */
    .searchbar input[type="text"]:focus {
    	border-color: #0056b3; /* Green border on focus */
    }
</style>

<% User user = (User)session.getAttribute("user"); %>

<a href="dashboard.jsp" class="logo">	
	<span class="text">IoTBay - Return to Dashboard</span>
</a>

<form action="#"> 
<%-- maybe lead to productPage.jsp or something like that to show search result --%>
<div class="searchbar">
	<input type="text" placeholder="Search for devices...">
	<button type="submit" class="search_button">
		<img src="./images/search_icon.jpg">
	</button>
</div>
</html>