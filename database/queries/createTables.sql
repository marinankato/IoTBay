CREATE TABLE "Users" (
    "userID"    INTEGER,
    "firstName"    VARCHAR(50),
    "lastName"    VARCHAR(50),
    "phoneNo" VARCHAR(10),
    "email" VARCHAR(150),
    "password" VARCHAR(50),
    "role" VARCHAR(25),
    "loginTime" DATETIME,
    "logoutTime" DATETIME,
    PRIMARY KEY("userID" AUTOINCREMENT)
);

CREATE TABLE "Orders" (
    "userID"    INTEGER,
    "orderID"   INTEGER PRIMARY KEY,
    "orderDate" DATE,
    "totalPrice" NUMERIC(7),
    "itemPrice" NUMERIC(7), 
    FOREIGN KEY("userID") REFERENCES Users(userID)
);

-- Enable: User can view their access logs and search the log records based on the date
