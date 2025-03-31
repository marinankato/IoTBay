<%@page import="model.User"%>
<html>
<title>IoTBay | Order Products</title>

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
    }

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
    	border: 1px solid rgb(184, 184, 184);
    	border-radius: 80px 0 0 80px; /* Rounded corners */
    	width: 500px; /* Set the width of the input field */
    	outline: none;
    }

    /* Style the button */
    .searchbar button {
		padding: 10px;
    	background-color: #007bff; 
    	border: 1px;
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
    	background-color: #0056b3;
    }

    /* Optional: Add a focus effect for the input */
    .searchbar input[type="text"]:focus {
    	border-color: #0056b3; 
    }
</style>

<% User user = (User)session.getAttribute("user"); %>

<body>
    <!-- Header Section with Logo and Welcome Text -->
    <div class="header">
        <a href="dashboard.jsp" class="logo">IoTBay</a>
        <span class="welcomeText">Logged in as: <%= user.getUsername() %></span>
    </div>


	<form action="#"> 
	<%-- maybe lead to productPage.jsp or something like that to show search result --%>
	<div class="searchbar">
		<input type="text" placeholder="Search for devices...">
		<button type="submit" class="search_button">
			<img src="./images/search_icon.jpg">
		</button>
	</div>
</body>
</html>