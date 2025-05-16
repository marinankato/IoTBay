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
CREATE TABLE Orders (
    orderID     INTEGER PRIMARY KEY,      
    userID      INTEGER NOT NULL,
    orderDate   DATE        NOT NULL,
    totalPrice  NUMERIC(7,2) NOT NULL,
    orderStatus INTEGER     NOT NULL     DEFAULT 0,
    FOREIGN KEY(userID) REFERENCES Users(userID)
);

-- Enable: User can view their access logs and search the log records based on the date
