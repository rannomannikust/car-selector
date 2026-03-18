-- Mercedes-Benz (ID 1)
INSERT INTO car_brands (id, name, parent_id) VALUES (1, 'Mercedes-Benz', NULL);
INSERT INTO car_brands (id, name, parent_id) VALUES (2, 'C klass', 1);
INSERT INTO car_brands (id, name, parent_id) VALUES (3, 'C 160', 2);
INSERT INTO car_brands (id, name, parent_id) VALUES (4, 'C 180', 2);
INSERT INTO car_brands (id, name, parent_id) VALUES (5, 'C 200', 2);
INSERT INTO car_brands (id, name, parent_id) VALUES (6, 'C 220', 2);

-- BMW (ID 10)
INSERT INTO car_brands (id, name, parent_id) VALUES (10, 'BMW', NULL);
INSERT INTO car_brands (id, name, parent_id) VALUES (11, '3 seeria', 10);
INSERT INTO car_brands (id, name, parent_id) VALUES (12, '315', 11);
INSERT INTO car_brands (id, name, parent_id) VALUES (13, '316', 11);
INSERT INTO car_brands (id, name, parent_id) VALUES (14, '317', 11);
INSERT INTO car_brands (id, name, parent_id) VALUES (15, '318', 11);
INSERT INTO car_brands (id, name, parent_id) VALUES (16, '319', 11);
INSERT INTO car_brands (id, name, parent_id) VALUES (17, '4 seeria', 10);
INSERT INTO car_brands (id, name, parent_id) VALUES (18, '5 seeria', 10);
INSERT INTO car_brands (id, name, parent_id) VALUES (19, '518', 18);
INSERT INTO car_brands (id, name, parent_id) VALUES (20, '520', 18);
INSERT INTO car_brands (id, name, parent_id) VALUES (21, '523', 18);
INSERT INTO car_brands (id, name, parent_id) VALUES (22, '524', 18);
INSERT INTO car_brands (id, name, parent_id) VALUES (23, '525', 18);

-- Audi (ID 30)
INSERT INTO car_brands (id, name, parent_id) VALUES (30, 'Audi', NULL);
INSERT INTO car_brands (id, name, parent_id) VALUES (31, 'A seeria', 30);
INSERT INTO car_brands (id, name, parent_id) VALUES (32, 'e-tron', 30);
INSERT INTO car_brands (id, name, parent_id) VALUES (33, 'Q seeria', 30);
INSERT INTO car_brands (id, name, parent_id) VALUES (34, 'Q2', 33);
INSERT INTO car_brands (id, name, parent_id) VALUES (35, 'Q3', 33);
INSERT INTO car_brands (id, name, parent_id) VALUES (36, 'Q4', 33);
INSERT INTO car_brands (id, name, parent_id) VALUES (37, 'Q5', 33);
INSERT INTO car_brands (id, name, parent_id) VALUES (38, 'Q7', 33);
INSERT INTO car_brands (id, name, parent_id) VALUES (39, 'RS seeria', 30);
INSERT INTO car_brands (id, name, parent_id) VALUES (40, 'RS4', 39);
INSERT INTO car_brands (id, name, parent_id) VALUES (41, 'RS5', 39);
INSERT INTO car_brands (id, name, parent_id) VALUES (42, 'RS6', 39);
INSERT INTO car_brands (id, name, parent_id) VALUES (43, 'TT', 30);

-- Citroën ja Muu (ID 50+)
INSERT INTO car_brands (id, name, parent_id) VALUES (50, 'Citroën', NULL);
INSERT INTO car_brands (id, name, parent_id) VALUES (51, 'Muu', NULL);

-- Parandame PostgreSQL jada (sequence)
SELECT setval('car_brands_id_seq', (SELECT MAX(id) FROM car_brands));