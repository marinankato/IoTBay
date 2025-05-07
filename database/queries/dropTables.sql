-- To delete an unwanted table
DROP TABLE Users;
DROP TABLE IF EXISTS "Orders";

-- !Since the files now handle adding and removing from tables, it might say locked and not work!

-- To delete all rows from table, 
-- or specific row:

-- DELETE FROM Users;
-- WHERE email="example@gmail.com";