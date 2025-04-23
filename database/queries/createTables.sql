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
-- Enable: User can view their access logs and search the log records based on the date
