-- Delete data from the 'Doctor' table
DELETE FROM Doctor;

-- For the 'Doctor' table
ALTER TABLE Doctor ALTER COLUMN id RESTART WITH 1;
