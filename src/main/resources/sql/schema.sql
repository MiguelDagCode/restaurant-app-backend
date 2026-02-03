
-- 2️⃣ Tabla Roles
CREATE TABLE roles (
  idRolUsuario INT AUTO_INCREMENT PRIMARY KEY,
  rolUsuario VARCHAR(50) NOT NULL
);

-- 3️⃣ Tabla Usuarios 
CREATE TABLE usuarios (
  idUsuario INT AUTO_INCREMENT PRIMARY KEY,
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  correo VARCHAR(100) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL, 
  telefono VARCHAR(20),
  dni VARCHAR(8),
  estado ENUM('activo', 'inactivo') DEFAULT 'activo',
  fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
  idRolUsuario INT NOT NULL,
  direccion VARCHAR(150),
  FOREIGN KEY (idRolUsuario) REFERENCES roles(idRolUsuario)
);

-- 4️⃣ Tabla Categorías de Platos
CREATE TABLE categorias_platos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

-- 5️⃣ Tabla Platos
CREATE TABLE platos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  precio DECIMAL(10,2) NOT NULL,
  categoria_plato_id INT NOT NULL,
  url_imagen VARCHAR(255),
  FOREIGN KEY (categoria_plato_id) REFERENCES categorias_platos(id)
);

-- 6️⃣ Tabla Mesas
CREATE TABLE mesas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  numero INT NOT NULL UNIQUE,
  estado ENUM('LIBRE', 'OCUPADA') NOT NULL DEFAULT 'LIBRE'
);

-- 7️⃣ Tabla Pedidos
CREATE TABLE pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  mozo_id INT NOT NULL,
  mesa_id INT NOT NULL,
  fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado ENUM('PENDIENTE', 'EN_PROCESO', 'LISTO', 'PAGADO') NOT NULL DEFAULT 'PENDIENTE',
  FOREIGN KEY (mozo_id) REFERENCES usuarios(idUsuario),
  FOREIGN KEY (mesa_id) REFERENCES mesas(id)
);

-- 8️⃣ Tabla Detalle de Pedido
CREATE TABLE detalle_pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pedido_id INT NOT NULL,
  plato_id INT NOT NULL,
  cantidad INT NOT NULL,
  estado ENUM('RECIBIDO', 'EN_PROCESO', 'LISTO') NOT NULL DEFAULT 'RECIBIDO',
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
  FOREIGN KEY (plato_id) REFERENCES platos(id)
);

-- 9️⃣ Tabla Pagos
CREATE TABLE pagos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pedido_id INT NOT NULL,
  nombre_cliente VARCHAR(100) NOT NULL,
  dni_ruc VARCHAR(12) NOT NULL,
  tipo_documento ENUM('BOLETA', 'FACTURA') NOT NULL,
  numero_documento VARCHAR(100) NOT NULL UNIQUE,
  fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  monto_total DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);
