INSERT INTO eventos (nombre, descripcion, fecha, estado, imagen_url, estadio_id) VALUES

-- Fecha 8
('Titanes FC vs Cóndores AC',        'Fecha 8 - Liga Nacional', '2026-04-05 16:00:00', 'ACTIVO', null, 1),
('Dragones del Este vs Lobos FC',    'Fecha 8 - Liga Nacional', '2026-04-05 19:00:00', 'ACTIVO', null, 3),

-- Fecha 9
('Lobos FC vs Titanes FC',           'Fecha 9 - Liga Nacional', '2026-04-12 16:00:00', 'ACTIVO', null, 4),
('Cóndores AC vs Dragones del Este', 'Fecha 9 - Liga Nacional', '2026-04-12 19:00:00', 'ACTIVO', null, 2),

-- Fecha 10
('Titanes FC vs Dragones del Este',  'Fecha 10 - Liga Nacional', '2026-04-19 16:00:00', 'ACTIVO', null, 1),
('Cóndores AC vs Lobos FC',          'Fecha 10 - Liga Nacional', '2026-04-19 19:00:00', 'ACTIVO', null, 2),

-- Fecha 11
('Dragones del Este vs Titanes FC',  'Fecha 11 - Liga Nacional', '2026-04-26 16:00:00', 'ACTIVO', null, 3),
('Lobos FC vs Cóndores AC',          'Fecha 11 - Liga Nacional', '2026-04-26 19:00:00', 'ACTIVO', null, 4),

-- Fecha 12
('Cóndores AC vs Titanes FC',        'Fecha 12 - Liga Nacional', '2026-05-03 16:00:00', 'ACTIVO', null, 2),
('Lobos FC vs Dragones del Este',    'Fecha 12 - Liga Nacional', '2026-05-03 19:00:00', 'ACTIVO', null, 4),

-- Fecha 13
('Titanes FC vs Lobos FC',           'Fecha 13 - Liga Nacional', '2026-05-10 16:00:00', 'ACTIVO', null, 1),
('Dragones del Este vs Cóndores AC', 'Fecha 13 - Liga Nacional', '2026-05-10 19:00:00', 'ACTIVO', null, 3);

-- =============================================
-- PRECIOS POR EVENTO/ZONA
-- =============================================

-- Fecha 8
-- Titanes vs Cóndores (evento 1) - Estadio 1
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(1, 1, 24000, 14000),(1, 2, 20000, 11000),(1, 3, 11000, 22000),(1, 4, 11000, 22000),(1, 5, 75000, 100);

-- Dragones vs Lobos (evento 2) - Estadio 3
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(2, 10, 14000, 10000),(2, 11, 6000, 13000),(2, 12, 6000, 9000),(2, 13, 45000, 55);

-- Fecha 9
-- Lobos vs Titanes (evento 3) - Estadio 4
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(3, 14, 20000, 9000),(3, 15, 8000, 12000),(3, 16, 8000, 7500),(3, 17, 62000, 45);

-- Cóndores vs Dragones (evento 4) - Estadio 2
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(4, 6, 16000, 9000),(4, 7, 14000, 11000),(4, 8, 7000, 24000),(4, 9, 52000, 70);

-- Fecha 10
-- Titanes vs Dragones (evento 5) - Estadio 1
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(5, 1, 18000, 14000),(5, 2, 15000, 11000),(5, 3, 8000, 22000),(5, 4, 8000, 22000),(5, 5, 60000, 100);

-- Cóndores vs Lobos (evento 6) - Estadio 2
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(6, 6, 16000, 9000),(6, 7, 14000, 11000),(6, 8, 7000, 24000),(6, 9, 52000, 70);

-- Fecha 11
-- Dragones vs Titanes (evento 7) - Estadio 3
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(7, 10, 20000, 10000),(7, 11, 8000, 13000),(7, 12, 8000, 9000),(7, 13, 65000, 55);

-- Lobos vs Cóndores (evento 8) - Estadio 4
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(8, 14, 16000, 9000),(8, 15, 6000, 12000),(8, 16, 6000, 7500),(8, 17, 50000, 45);

-- Fecha 12
-- Cóndores vs Titanes (evento 9) - Estadio 2
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(9, 6, 22000, 9000),(9, 7, 19000, 11000),(9, 8, 10000, 24000),(9, 9, 70000, 70);

-- Lobos vs Dragones (evento 10) - Estadio 4
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(10, 14, 16000, 9000),(10, 15, 6000, 12000),(10, 16, 6000, 7500),(10, 17, 50000, 45);

-- Fecha 13
-- Titanes vs Lobos (evento 11) - Estadio 1
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(11, 1, 18000, 14000),(11, 2, 15000, 11000),(11, 3, 8000, 22000),(11, 4, 8000, 22000),(11, 5, 60000, 100);

-- Dragones vs Cóndores (evento 12) - Estadio 3
INSERT INTO evento_zonas (evento_id, zona_id, precio, capacidad_disponible) VALUES
(12, 10, 18000, 10000),(12, 11, 7000, 13000),(12, 12, 7000, 9000),(12, 13, 58000, 55);