-- Delete data from the 'Patient' table
DELETE FROM Patient;

-- For the 'Patient' table
ALTER TABLE Patient ALTER COLUMN id RESTART WITH 1;