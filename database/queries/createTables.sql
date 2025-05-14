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
    "loginDate" DATETIME,
    "logoutDate" DATETIME,
    PRIMARY KEY("userID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS Orders;
CREATE TABLE "Orders" (
    "orderID"     INTEGER PRIMARY KEY AUTOINCREMENT,
    "userID"      INTEGER,
    "orderDate"   DATE,
    "totalPrice"  NUMERIC(7, 2),
    "orderStatus" VARCHAR(20), 
    FOREIGN KEY("userID") REFERENCES Users("userID")
);

DROP TABLE IF EXISTS Access_Logs;
CREATE TABLE Access_Logs (
    "logId"     INTEGER PRIMARY KEY AUTOINCREMENT,
    "userID"      INTEGER,
    "loginDate" DATETIME,
    "logoutDate" DATETIME,
    FOREIGN KEY("userID") REFERENCES Users("userID")
); 