-- Insert sample visits
INSERT INTO Visit (patient_id, doctor_id, start_Time, end_Time, visit_type, price)
VALUES
  (1L, 1L, TIMESTAMP '2023-11-01 10:00:00', TIMESTAMP '2023-11-01 11:00:00', 'CREATED', 100.00);
INSERT INTO Visit (patient_id, doctor_id, start_Time, end_Time, visit_type, price)
VALUES
  (1, 2, TIMESTAMP '2023-11-02 14:30:00', TIMESTAMP '2023-11-02 15:30:00', 'CREATED', 120.00);
INSERT INTO Visit (patient_id, doctor_id, start_Time, end_Time, visit_type, price)
VALUES
  (2, 3, TIMESTAMP '2023-11-03 16:45:00', TIMESTAMP '2023-11-03 17:45:00', 'CREATED', 80.00);