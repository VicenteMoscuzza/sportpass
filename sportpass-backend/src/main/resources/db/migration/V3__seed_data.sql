INSERT INTO estadios (nombre, direccion, ciudad, capacidad_total) VALUES
('Estadio Olímpico del Norte', 'Av. Libertadores 1500', 'Buenos Aires', 75000),
('Estadio La Fortaleza', 'Calle Independencia 820', 'Córdoba', 52000),
('Estadio Ciudad del Sur', 'Av. Costanera 3400', 'Rosario', 38000),
('Estadio El Coloso', 'Bv. Deportivo 900', 'Mendoza', 32000);

-- ZONAS ESTADIO 1 - Olímpico del Norte
INSERT INTO zonas (nombre, capacidad, estadio_id) VALUES
('Platea Preferencial', 14000, 1),
('Platea Lateral', 11000, 1),
('Popular Norte', 22000, 1),
('Popular Sur', 22000, 1),
('Palcos VIP', 1800, 1);

-- ZONAS ESTADIO 2 - La Fortaleza
INSERT INTO zonas (nombre, capacidad, estadio_id) VALUES
('Platea Baja', 9000, 2),
('Platea Alta', 11000, 2),
('Popular', 24000, 2),
('Palcos VIP', 1400, 2);

-- ZONAS ESTADIO 3 - Ciudad del Sur
INSERT INTO zonas (nombre, capacidad, estadio_id) VALUES
('Platea', 10000, 3),
('Popular Local', 13000, 3),
('Popular Visitante', 9000, 3),
('Palcos VIP', 1100, 3);

-- ZONAS ESTADIO 4 - El Coloso
INSERT INTO zonas (nombre, capacidad, estadio_id) VALUES
('Platea', 9000, 4),
('Popular Local', 12000, 4),
('Popular Visitante', 7500, 4),
('Palcos VIP', 900, 4);