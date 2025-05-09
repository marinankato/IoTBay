INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('Bob', 'Watts','0411222333','admin@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44');
INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('luck', 'Watts','0411222333','customer@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44');

INSERT INTO Orders (userID, orderDate, totalPrice, orderStatus)
VALUES (1, '2025-05-07', 199.99, 'saved');

INSERT INTO iot_device (device_name, device_type, unit_price, quantity)
VALUES
('Smart Sensor', 'Sensor', 99.99, 50),
('IoT Camera', 'Camera', 149.99, 30),
('Thermostat', 'Controller', 199.99, 20),
('Light Bulb', 'Lighting', 19.99, 100),
('Air Quality Monitor', 'Sensor', 129.99, 40);