DELETE FROM Visit;

-- For the 'Visit' table
ALTER TABLE Visit ALTER COLUMN id RESTART WITH 1;