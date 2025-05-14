INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('Bob', 'Watts','0411222333','admin@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44');
INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('luck', 'Watts','0411222333','customer@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44');

INSERT INTO Orders (userID, orderDate, totalPrice, orderStatus)
VALUES (2, '2025-05-14',  42.42, 'saved');


INSERT INTO iot_device (device_name, device_type, unit_price, quantity) VALUES
('Smart Sensor', 'Sensor', 99.99, 50),
('IoT Camera', 'Camera', 149.99, 30),
('Thermostat', 'Controller', 199.99, 20),
('Light Bulb', 'Lighting', 19.99, 100),
('Air Quality Monitor', 'Sensor', 129.99, 40),
('Motion Detector', 'Sensor', 89.99, 35),
('Smart Plug', 'Controller', 39.99, 80),
('Smoke Detector', 'Sensor', 59.99, 25),
('Smart Door Lock', 'Security', 179.99, 15),
('Water Leak Sensor', 'Sensor', 49.99, 45),
('Garage Door Opener', 'Controller', 159.99, 10),
('Smart Switch', 'Lighting', 24.99, 60),
('Temperature Sensor', 'Sensor', 69.99, 70),
('Surveillance Drone', 'Camera', 299.99, 5),
('Vibration Sensor', 'Sensor', 54.99, 33),
('Smart Curtain Controller', 'Controller', 119.99, 12),
('Outdoor Security Camera', 'Camera', 189.99, 22),
('Ceiling Light Panel', 'Lighting', 89.99, 40),
('CO2 Monitor', 'Sensor', 139.99, 18),
('Wi-Fi Repeater', 'Networking', 49.99, 55);
