<html>
<head>
	<meta charset="UTF-8">
    <title>IoTBay | Checkout</title>
    <style>
		body {
			justify-content: center;

			background-color: #f4f4f4;
		}

		form {
			display: flex;
			flex-direction: column;
			width: 400px;
			align-items: right;
		}
		
		input[type="submit"] {
			border-radius: 12px;
			background-color: white;
		}
		input[type="submit"]:hover {
			background-color: #f9f9f9;
		}
	</style>
</head>
<body>
<h1>IoTBay | Checkout</h1>
<!--still need to add the order summary on top or something once we have that---->
<form>
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