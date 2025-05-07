INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate)
VALUES 
('Bob', 'Watts','0411222333','admin@gmail.com','password123', 'admin', '2024-11-11 13:23:44', '2024-11-11 13:23:44');

INSERT INTO Orders (userID, orderDate, totalPrice, orderStatus)
VALUES (1, '2025-05-07', 199.99, 'saved');