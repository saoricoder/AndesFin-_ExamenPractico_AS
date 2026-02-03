-- Insertar Usuarios
INSERT INTO usuarios (id, nombre, email, capital_disponible) VALUES (UUID(), 'Juan Perez', 'juan.perez@example.com', 10000.00);
INSERT INTO usuarios (id, nombre, email, capital_disponible) VALUES (UUID(), 'Maria Gomez', 'maria.gomez@example.com', 50000.00);
INSERT INTO usuarios (id, nombre, email, capital_disponible) VALUES (UUID(), 'Carlos Lopez', 'carlos.lopez@example.com', 25000.00);
INSERT INTO usuarios (id, nombre, email, capital_disponible) VALUES (UUID(), 'Ana Martinez', 'ana.martinez@example.com', 75000.00);
INSERT INTO usuarios (id, nombre, email, capital_disponible) VALUES (UUID(), 'Luis Rodriguez', 'luis.rodriguez@example.com', 15000.00);

-- Insertar Productos Financieros
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Fondo A', 'Fondo de bajo riesgo', 1000.00, 5.00, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Fondo B', 'Fondo de riesgo medio', 2000.00, 8.50, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Fondo C', 'Fondo de alto riesgo', 5000.00, 12.00, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Acciones Tech', 'Acciones de tecnologia', 1500.00, 10.00, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Bonos Estado', 'Bonos del estado a 5 años', 500.00, 3.00, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Cripto X', 'Criptomoneda volatil', 3000.00, 20.00, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'ETF Global', 'ETF de mercado global', 2500.00, 7.00, true);
INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES (UUID(), 'Bienes Raices', 'Fondo inmobiliario', 10000.00, 6.50, true);
