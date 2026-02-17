DROP DATABASE IF EXISTS delifast_db;
CREATE DATABASE delifast_db;
USE delifast_db;

-- ==========================================================
-- MÓDULO 1: SEGURIDAD Y USUARIOS (ADMINISTRADOR DE SISTEMA)
-- ==========================================================
CREATE TABLE tb_roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE tb_usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE, 
    FOREIGN KEY (id_rol) REFERENCES tb_roles(id_rol)
);

-- ==========================================================
-- MÓDULO 2: CATÁLOGO Y CONFIGURACIÓN DE PRODUCTOS
-- ==========================================================
CREATE TABLE tb_categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE tb_producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    img_url VARCHAR(255),
    estado BOOLEAN DEFAULT TRUE,    -- Soft Delete
    FOREIGN KEY (id_categoria) REFERENCES tb_categoria(id_categoria)
);

-- Tabla necesaria para el descuento de inventario, aunque el abastecimiento
-- venga de otro servicio, este servicio necesita saber CUÁNTO hay para validar ventas.
CREATE TABLE tb_insumo (
    id_insumo BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    stock_actual DECIMAL(10,3) NOT NULL DEFAULT 0,
    stock_minimo DECIMAL(10,3) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL -- KG, LT, UNIDAD
);

-- Solo estructura cuantitativa (Sin instrucciones confidenciales)
CREATE TABLE tb_receta (
    id_producto BIGINT NOT NULL,
    id_insumo BIGINT NOT NULL,
    cantidad_insumo DECIMAL(10,3) NOT NULL, -- Cantidad exacta a descontar
    activo BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id_producto, id_insumo),
    FOREIGN KEY (id_producto) REFERENCES tb_producto(id_producto),
    FOREIGN KEY (id_insumo) REFERENCES tb_insumo(id_insumo)
);

-- ==========================================================
-- MÓDULO 3: OPERACIÓN (MESAS, PEDIDOS Y COCINA)
-- ==========================================================
CREATE TABLE tb_mesa (
    id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    numero_mesa VARCHAR(10) NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado ENUM('LIBRE', 'OCUPADA', 'POR_PAGAR', 'MANTENIMIENTO') DEFAULT 'LIBRE'
);

CREATE TABLE tb_pedido (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_mesa INT NOT NULL,
    id_usuario INT NOT NULL, -- Mozo que atiende
    -- Datos Cliente
    tipo_doc ENUM('DNI', 'RUC', 'PASAPORTE') DEFAULT 'DNI',
    num_doc VARCHAR(20),
    nombres_razon_social VARCHAR(150) NOT NULL,
    telefono_cliente VARCHAR(20),
    -- Tiempos de Servicio (SLA Cocina)
    fecha_apertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio_cocina DATETIME NULL,
    fecha_fin_cocina DATETIME NULL,
    -- Flujo de Estado
    estado_pedido ENUM('PENDIENTE', 'EN_PREPARACION', 'LISTO_PARA_SERVIR', 'ENTREGADO', 'PAGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    total DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (id_mesa) REFERENCES tb_mesa(id_mesa),
    FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario)
);

CREATE TABLE tb_detalle_pedido (
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL, -- Precio congelado al momento de la venta
    observaciones TEXT, -- "Sin cebolla", "Bien cocido" (Esto sí lo ve cocina)
    PRIMARY KEY (id_pedido, id_producto),
    FOREIGN KEY (id_pedido) REFERENCES tb_pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES tb_producto(id_producto)
);

-- ==========================================================
-- MÓDULO 4: FACTURACIÓN Y CONTROL DE MERMAS
-- ==========================================================
CREATE TABLE tb_facturacion (
    id_factura BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL UNIQUE,
    tipo_comprobante ENUM('BOLETA', 'FACTURA') NOT NULL,
    fecha_emision DATETIME DEFAULT CURRENT_TIMESTAMP,
    monto_total DECIMAL(10,2) NOT NULL,
    medio_pago ENUM('EFECTIVO', 'TARJETA', 'YAPE', 'PLIN') NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES tb_pedido(id_pedido)
);

CREATE TABLE tb_merma (
    id_merma BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_insumo BIGINT NOT NULL,
    id_usuario_reporta INT NOT NULL,
    id_producto BIGINT, -- Opcional: Si se cayó un plato preparado
    cantidad DECIMAL(10,3) NOT NULL,
    motivo ENUM('QUEMADO', 'CAIDA', 'VENCIDO', 'ERROR_PEDIDO', 'CADUCIDAD') NOT NULL,
    fecha_reporte DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_insumo) REFERENCES tb_insumo(id_insumo),
    FOREIGN KEY (id_usuario_reporta) REFERENCES tb_usuarios(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES tb_producto(id_producto)
);