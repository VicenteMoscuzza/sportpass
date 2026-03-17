CREATE TABLE estadios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(255) NOT NULL,
    capacidad_total INTEGER
);

CREATE TABLE zonas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    capacidad INTEGER,
    estadio_id BIGINT NOT NULL REFERENCES estadios(id)
);

CREATE TABLE asientos (
    id BIGSERIAL PRIMARY KEY,
    fila VARCHAR(10) NOT NULL,
    numero INTEGER NOT NULL,
    zona_id BIGINT NOT NULL REFERENCES zonas(id)
);

CREATE TABLE eventos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha TIMESTAMP NOT NULL,
    estado VARCHAR(50) NOT NULL,
    imagen_url VARCHAR(500),
    estadio_id BIGINT NOT NULL REFERENCES estadios(id)
);

CREATE TABLE evento_zonas (
    id BIGSERIAL PRIMARY KEY,
    evento_id BIGINT NOT NULL REFERENCES eventos(id),
    zona_id BIGINT NOT NULL REFERENCES zonas(id),
    precio DECIMAL(10,2) NOT NULL,
    capacidad_disponible INTEGER
);

CREATE TABLE compras (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    fecha TIMESTAMP DEFAULT NOW(),
    total DECIMAL(10,2),
    estado VARCHAR(50) NOT NULL,
    mercado_pago_id VARCHAR(255)
);

CREATE TABLE entradas (
    id BIGSERIAL PRIMARY KEY,
    compra_id BIGINT NOT NULL REFERENCES compras(id),
    asiento_id BIGINT NOT NULL REFERENCES asientos(id),
    evento_zona_id BIGINT NOT NULL REFERENCES evento_zonas(id),
    codigo_qr VARCHAR(500)
);

CREATE TABLE notificaciones (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    mensaje TEXT NOT NULL,
    leida BOOLEAN DEFAULT FALSE,
    fecha TIMESTAMP DEFAULT NOW()
);