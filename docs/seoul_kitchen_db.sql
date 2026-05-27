CREATE DATABASE IF NOT EXISTS seoul_kitchen
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE seoul_kitchen;

CREATE TABLE IF NOT EXISTS usuarios (
    id            INT          NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100) NOT NULL,
    apellido      VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    username      VARCHAR(80)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo        TINYINT(1)   NOT NULL DEFAULT 1,
    creado_en     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

INSERT IGNORE INTO usuarios (nombre, apellido, email, username, password_hash)
VALUES ('Administrador', 'Seoul', 'admin@seoulkitchen.com', 'admin', 'admin123');

CREATE TABLE IF NOT EXISTS platillos (
    id         INT            NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(150)   NOT NULL,
    categoria  VARCHAR(100)   NOT NULL,
    precio     DECIMAL(10, 2) NOT NULL,
    estado     ENUM('Activo','Agotado','Inactivo') NOT NULL DEFAULT 'Activo',
    creado_en  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

INSERT IGNORE INTO platillos (id, nombre, categoria, precio, estado) VALUES
(1, 'Bibimbap Especial',  'Plato Principal', 185.00, 'Activo'),
(2, 'Tteokbokki',         'Entradas',        120.00, 'Agotado'),
(3, 'Kimbap de Atún',     'Entradas',         95.00, 'Activo');

CREATE TABLE IF NOT EXISTS clientes (
    id         INT          NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(100) NOT NULL,
    apellido   VARCHAR(100) NOT NULL,
    email      VARCHAR(150)          UNIQUE,
    telefono   VARCHAR(20),
    activo     TINYINT(1)   NOT NULL DEFAULT 1,
    creado_en  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

INSERT IGNORE INTO clientes (id, nombre, apellido, email, telefono) VALUES
(1, 'Carlos', 'López',   'carlos.lopez@mail.com',  '555-1001'),
(2, 'María',  'García',  'maria.garcia@mail.com',   '555-1002'),
(3, 'Luis',   'Martínez','luis.martinez@mail.com',  '555-1003');

CREATE TABLE IF NOT EXISTS inventario (
    id          INT          NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(150) NOT NULL,
    cantidad    INT          NOT NULL DEFAULT 0,
    unidad      VARCHAR(50)  NOT NULL DEFAULT 'unidad',
    nivel_alerta INT         NOT NULL DEFAULT 5,
    creado_en   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

INSERT IGNORE INTO inventario (id, nombre, cantidad, unidad, nivel_alerta) VALUES
(1, 'Arroz Coreano',   50, 'kg',    10),
(2, 'Gochujang',        8, 'litro',  5),
(3, 'Algas Nori',      30, 'paquete',5);

CREATE TABLE IF NOT EXISTS ordenes (
    id           INT            NOT NULL AUTO_INCREMENT,
    id_cliente   INT            NOT NULL,
    total        DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    estado       ENUM('Pendiente','Completada','Cancelada') NOT NULL DEFAULT 'Pendiente',
    creado_en    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_orden_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS orden_detalle (
    id           INT            NOT NULL AUTO_INCREMENT,
    id_orden     INT            NOT NULL,
    id_platillo  INT            NOT NULL,
    cantidad     INT            NOT NULL DEFAULT 1,
    precio_unit  DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_detalle_orden    FOREIGN KEY (id_orden)    REFERENCES ordenes(id),
    CONSTRAINT fk_detalle_platillo FOREIGN KEY (id_platillo) REFERENCES platillos(id)
) ENGINE=InnoDB;
