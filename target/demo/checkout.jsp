<html>
<head>
	<meta charset="UTF-8">
    <title>IoTBay | Checkout</title>
    <style>
		body {
		font-family: Arial, sans-serif;
		background-color: #f9f9f9;
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100vh;
		margin: 0;
		flex-direction: column; /* Stack the title and form */
		}
	
		h1 {
			font-size: 36px;
			color: #007bff;
			margin-bottom: 20px; /* Adds space between title and form */
			text-align: center;
		}


		form {
			background-color: #ffffff;
			padding: 20px 40px;
			border-radius: 8px;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
			width: 300px;
		}
		
		label {
			display: block;
			margin-top: 10px;
			color: #333;
		}
		
		input, select {
			width: 100%;
			padding: 10px;
			margin-top: 5px;
			border: 1px solid #ccc;
			border-radius: 4px;
			font-size: 16px;
		}
		
		input[type="submit"] {
			background-color: #007bff;
			color: white;
			border: none;
			cursor: pointer;
			margin-top: 20px;
		}
		
		input[type="submit"]:hover {
			background-color: #0056b3;
		}
	</style>
</head>

<body>
<h1>Checkout</h1>
<!--still need to add the order summary on top or something once we have that---->
<form action="order" method="post">
    <input type="hidden" name="action" value="checkout">
<label for="nameOnCard">Name on Card: </label>
<input type="text" id="nameOnCard" name="nameOnCard" required>
<br><br>
<label for="cardNumber">Card Number: </label>
<input type="text" id="cardNumber" name="cardNumber" required>
<br><br>
<label for = "cardSecurityCode">Card Security Code: </label>
<input type="text" id="cardSecurityCode" name="cardSecurityCode" required>
<br><br>
<label for = "streetName">Street Name: </label>
<input type="text" id="streetName" name="streetName" required>
<br><br>
<label for="cityName">City: </label>
<input type="text" id="cityName" name="cityName" required>
<br><br>
<label for="state">State: </label>
<select id="state" name="state" required>
	<option value="NSW">NSW</option>
	<option value="VIC">VIC</option>
	<option value="ACT">ACT</option>
	<option value="QLD">QLD</option>
	<option value="TAS">TAS</option>
	<option value="SA">SA</option>
	<option value="NT">NT</option>
	<option value="WA">WA</option>

</select>
<br><br>
<label for="postcode">Postcode: </label>
<input type="text" id="postcode" name="postcode" required>
<br><br>
<input type="submit" value="Submit"><br>
</form>

</body>
</html>