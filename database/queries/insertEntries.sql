INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('Bob', 'Watts','0411222333','admin@gmail.com','password123', 'admin', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('John', 'Watson','0411222333','staff@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Mary', 'Clark','0411222333','customer@gmail.com','password123', 'customer', '2025-05-05 10:13:44', '2025-05-05 10:23:44'),
('Jennifer', 'Brown','0411222333','a@gmail.com','password123', 'customer', '2024-02-11 13:23:44', '2024-02-11 13:27:44'),
('Alice', 'White','0411222333','b@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Luck', 'Do','0411222333','c@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Bob', 'Jane','0411222333','d@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Jane', 'Lane','0411222333','e@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Kale', 'Watts','0411222333','veggie@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Brocolli', 'Watts','0411222333','f@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Alana', 'Philip','0411222333','g@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Amelie', 'Watts','0411222333','h@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Carl', 'Chapman','0411222333','i@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Henry', 'Boss','0411222333','j@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Jack', 'Mac','0411222333','k@gmail.com','password123', 'customer', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Lily', 'Wood','0411222333','l@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Bill', 'Wood','0411222333','m@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Sam', 'Clark','0411222334','n@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Mario', 'Kart','0411222333','o@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Princess', 'Peach','0411222333','p@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Marie', 'Antoinette','0411222333','marie@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Coco', 'Chanel','0411222333','test@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44'),
('Marc', 'Jacobs','0411222333','someone@gmail.com','password123', 'staff', '2024-11-11 13:23:44', '2024-11-11 13:23:44');

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
