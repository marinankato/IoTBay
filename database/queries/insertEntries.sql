INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('Bob', 'Watts','0411222333','admin@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44');
INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('luck', 'Watts','0411222333','customer@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44');


INSERT INTO Orders (orderID, userID, orderDate, totalPrice, orderStatus) VALUES
  ( 1, 2, '2025-05-01',  49.99, 0),  -- Saved
  ( 2, 2, '2025-05-02', 129.95, 1),  -- Submitted
  ( 3, 2, '2025-05-03',  75.50, 0),
  ( 4, 2, '2025-05-04', 199.99, 1),
  ( 5, 2, '2025-05-05',  39.95, 0),
  ( 6, 2, '2025-05-06',  89.20, 1),
  ( 7, 2, '2025-05-07', 149.49, 0),
  ( 8, 2, '2025-05-08',  19.99, 1),
  ( 9, 2, '2025-05-09',  59.00, 0),
  (10, 2, '2025-05-10', 109.99, 1),
  (11, 2, '2025-05-11',  24.75, 0),
  (12, 2, '2025-05-12',  79.99, 1),
  (13, 2, '2025-05-13',  42.00, 0),
  (14, 2, '2025-05-14', 120.00, 1),
  (15, 2, '2025-05-15',  55.55, 0),
  (16, 2, '2025-05-16', 199.95, 1),
  (17, 2, '2025-05-17',  15.00, 0),
  (18, 2, '2025-05-18',  99.99, 1),
  (19, 2, '2025-05-19',  30.30, 0),
  (20, 2, '2025-05-20',  65.65, 1);

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
