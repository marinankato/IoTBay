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

CREATE TABLE "Orders" (
    "orderID"     INTEGER PRIMARY KEY AUTOINCREMENT,
    "userID"      INTEGER,
    "orderDate"   DATE,
    "totalPrice"  NUMERIC(7, 2),
    "orderStatus" VARCHAR(20), 
    FOREIGN KEY("userID") REFERENCES Users("userID")
);

-- Enable: User can view their access logs and search the log records based on the date
