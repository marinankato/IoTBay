CREATE TABLE Users (
    UserID numeric(8),
    FirstName varchar(50),
    LastName varchar(50),
    PhoneNumber varchar(10),
    Email varchar(150),
    Password varchar(50),
    Role varchar(50)
);
-- To change to store login and logout date/time
-- User can view their access logs and search the log records based on the date


-- To delete all rows from table, 
-- or specific row:

-- DELETE FROM User;
-- WHERE UserID=1;