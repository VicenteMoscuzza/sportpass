-- =============================================
-- ASIENTOS VIP CON FILA Y NUMERO
-- =============================================

-- Palcos VIP Estadio 1 (zona_id = 5) - 20 palcos x 5 asientos
INSERT INTO asientos (fila, numero, zona_id)
SELECT 
    'P' || serie_palco,
    serie_asiento,
    5
FROM generate_series(1, 20) AS serie_palco,
     generate_series(1, 5) AS serie_asiento;

-- Palcos VIP Estadio 2 (zona_id = 9) - 14 palcos x 5 asientos
INSERT INTO asientos (fila, numero, zona_id)
SELECT 
    'P' || serie_palco,
    serie_asiento,
    9
FROM generate_series(1, 14) AS serie_palco,
     generate_series(1, 5) AS serie_asiento;

-- Palcos VIP Estadio 3 (zona_id = 13) - 11 palcos x 5 asientos
INSERT INTO asientos (fila, numero, zona_id)
SELECT 
    'P' || serie_palco,
    serie_asiento,
    13
FROM generate_series(1, 11) AS serie_palco,
     generate_series(1, 5) AS serie_asiento;

-- Palcos VIP Estadio 4 (zona_id = 17) - 9 palcos x 5 asientos
INSERT INTO asientos (fila, numero, zona_id)
SELECT 
    'P' || serie_palco,
    serie_asiento,
    17
FROM generate_series(1, 9) AS serie_palco,
     generate_series(1, 5) AS serie_asiento;

-- =============================================
-- ENTRADA GENERAL (un asiento representativo por zona)
-- =============================================

-- Estadio 1
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 1);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 2);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 3);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 4);

-- Estadio 2
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 6);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 7);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 8);

-- Estadio 3
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 10);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 11);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 12);

-- Estadio 4
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 14);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 15);
INSERT INTO asientos (fila, numero, zona_id) VALUES ('GENERAL', 1, 16);