DROP TABLE IF EXISTS iot_device;
CREATE TABLE iot_device (

    device_id INTEGER PRIMARY KEY AUTOINCREMENT,
    device_name VARCHAR(100) NOT NULL,
    device_type VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL
); 
DROP TABLE IF EXISTS Users;
CREATE TABLE "Users" (
    "userID"    INTEGER,
    "firstName"    VARCHAR(50),
    "lastName"    VARCHAR(50),
    "phoneNo" VARCHAR(10),
    "email" VARCHAR(150) UNIQUE,
    "password" VARCHAR(50),
    "role" VARCHAR(25),
    "loginDate" TEXT,
    "logoutDate" TEXT,
    "status" TEXT DEFAULT 'active' CHECK(status IN ('active', 'inactive')),
    PRIMARY KEY("userID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS Orders;
CREATE TABLE Orders (
    orderID     INTEGER PRIMARY KEY,      
    userID      INTEGER NOT NULL,
    orderDate   DATE        NOT NULL,
    totalPrice  NUMERIC(7,2) NOT NULL,
    orderStatus INTEGER     NOT NULL,
    FOREIGN KEY(userID) REFERENCES Users(userID)
);
DROP TABLE IF EXISTS OrderItems;
CREATE TABLE OrderItems (
    orderItemID   INTEGER PRIMARY KEY AUTOINCREMENT,
    orderID       INTEGER NOT NULL,
    deviceID      INTEGER NOT NULL,
    quantity      INTEGER NOT NULL,
    unitPrice     NUMERIC(7,2) NOT NULL,
    FOREIGN KEY(orderID ) REFERENCES Orders(orderID),
    FOREIGN KEY(deviceID) REFERENCES iot_device(device_id)
);
DROP TABLE IF EXISTS AccessLogs;
CREATE TABLE AccessLogs (
    "logId"     INTEGER PRIMARY KEY AUTOINCREMENT,
    "userId"      INTEGER,
    "action" TEXT CHECK(action IN ('logged in', 'logged out')),
    "accessDate" TEXT,
    FOREIGN KEY("userID") REFERENCES Users("userID")
); 