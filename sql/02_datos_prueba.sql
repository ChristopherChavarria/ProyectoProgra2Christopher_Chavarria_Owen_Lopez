-- =============================================================
-- Datos de prueba para la defensa (ficticios). Ejecutar después de 01_esquema.sql
-- Usuarios: admin / admin123  y  operador / operador123
-- =============================================================
SET NAMES utf8mb4;

INSERT INTO persona (id, identificacion, nombre, apellidos, correo_electronico, telefono) VALUES
(1, '1-1111-1111', 'Laura', 'Mora Jiménez', 'laura.mora@ejemplo.com', '8811-1111'),
(2, '2-2222-2222', 'Carlos', 'Vargas Solís', 'carlos.vargas@ejemplo.com', '8822-2222'),
(3, '3-3333-3333', 'Ana', 'Rojas Quesada', 'ana.rojas@ejemplo.com', '8833-3333'),
(4, '4-4444-4444', 'Diego', 'Castro Herrera', 'diego.castro@ejemplo.com', '8844-4444'),
(5, '5-5555-5555', 'Mariela', 'Chaves Alvarado', 'mariela.chaves@ejemplo.com', '8855-5555'),
(6, '1-0101-0101', 'José', 'Araya Méndez', 'jose.araya@correo.com', '6001-0001'),
(7, '1-0202-0202', 'María', 'Brenes Soto', 'maria.brenes@correo.com', '6001-0002'),
(8, '1-0303-0303', 'Andrés', 'Calderón Ruiz', 'andres.calderon@correo.com', '6001-0003'),
(9, '1-0404-0404', 'Sofía', 'Delgado Mora', 'sofia.delgado@correo.com', '6001-0004'),
(10, '1-0505-0505', 'Luis', 'Esquivel Pérez', 'luis.esquivel@correo.com', '6001-0005'),
(11, '1-0606-0606', 'Valeria', 'Fallas Núñez', 'valeria.fallas@correo.com', '6001-0006'),
(12, '1-0707-0707', 'Daniel', 'Gómez Salas', 'daniel.gomez@correo.com', '6001-0007'),
(13, '1-0808-0808', 'Camila', 'Hernández Ulate', 'camila.hernandez@correo.com', '6001-0008'),
(14, '1-0909-0909', 'Fernando', 'Jiménez Lobo', 'fernando.jimenez@correo.com', '6001-0009'),
(15, '1-1010-1010', 'Gabriela', 'León Campos', 'gabriela.leon@correo.com', '6001-0010'),
(16, '1-1212-1212', 'Ricardo', 'Montero Vega', 'ricardo.montero@correo.com', '6001-0011'),
(17, '1-1313-1313', 'Paola', 'Navarro Blanco', 'paola.navarro@correo.com', '6001-0012'),
(18, '1-1414-1414', 'Esteban', 'Ortiz Zúñiga', 'esteban.ortiz@correo.com', '6001-0013'),
(19, '1-1515-1515', 'Natalia', 'Porras Arias', 'natalia.porras@correo.com', '6001-0014'),
(20, '1-1616-1616', 'Kevin', 'Quirós Madrigal', 'kevin.quiros@correo.com', '6001-0015');

INSERT INTO expositor (id, especialidad, descripcion) VALUES
(1, 'Inteligencia Artificial', 'Investigadora en aprendizaje automático con 10 años de experiencia.'),
(2, 'Desarrollo Web', 'Ingeniero full stack, líder técnico en una empresa de software.'),
(3, 'Ciberseguridad', 'Consultora en seguridad informática y hacking ético.'),
(4, 'Emprendimiento', 'Fundador de dos startups tecnológicas costarricenses.'),
(5, 'Educación', 'Docente universitaria especialista en innovación educativa.');

INSERT INTO participante (id, fecha_registro, estado) VALUES
(6, '2026-08-01', 'activo'),
(7, '2026-08-01', 'activo'),
(8, '2026-08-03', 'activo'),
(9, '2026-08-05', 'activo'),
(10, '2026-08-10', 'activo'),
(11, '2026-08-12', 'activo'),
(12, '2026-08-15', 'activo'),
(13, '2026-08-20', 'activo'),
(14, '2026-09-01', 'activo'),
(15, '2026-09-02', 'activo'),
(16, '2026-09-10', 'activo'),
(17, '2026-09-15', 'activo'),
(18, '2026-10-01', 'activo'),
(19, '2026-10-05', 'activo'),
(20, '2026-10-10', 'inactivo');

INSERT INTO evento (id, nombre, descripcion, fecha_inicio, fecha_fin, lugar, estado) VALUES
(1, 'Jornada Académica de Educación 2026', 'Jornada sobre innovación educativa.', '2026-09-18', '2026-09-19', 'Auditorio Clodomiro Picado', 'finalizado'),
(2, 'Congreso de Tecnología 2026', 'Congreso anual de tecnología e innovación.', '2026-11-16', '2026-11-18', 'Centro de Convenciones', 'abierto'),
(3, 'Seminario de Emprendimiento 2027', 'Ciclo de charlas para emprendedores.', '2027-02-10', '2027-02-11', 'Sala de Conferencias Norte', 'planificacion');

INSERT INTO tipo_actividad (id, nombre) VALUES
(1, 'Conferencia'), (2, 'Taller'), (3, 'Charla'), (4, 'Mesa redonda'), (5, 'Capacitación');

INSERT INTO actividad (id, evento_id, expositor_id, tipo_actividad_id, nombre, descripcion, fecha, hora_inicio, hora_finalizacion, ubicacion, capacidad_max, estado) VALUES
(1, 1, 5, 1, 'Conferencia: El aula del futuro', 'Tendencias en educación.', '2026-09-18', '08:00', '09:30', 'Auditorio principal', 50, 'finalizada'),
(2, 1, 1, 2, 'Taller: IA para docentes', 'Herramientas de IA en el aula.', '2026-09-18', '10:00', '12:00', 'Laboratorio 3', 10, 'finalizada'),
(3, 1, 4, 4, 'Mesa redonda: Educación y empleo', 'Panel con empleadores.', '2026-09-19', '09:00', '11:00', 'Auditorio principal', 40, 'finalizada'),
(4, 2, 1, 1, 'Conferencia inaugural', 'Apertura del congreso.', '2026-11-16', '08:00', '09:00', 'Salón A', 100, 'abierta'),
(5, 2, 1, 2, 'Taller de Inteligencia Artificial', 'Modelos de lenguaje en la práctica.', '2026-11-16', '10:00', '12:00', 'Laboratorio 1', 5, 'abierta'),
(6, 2, 2, 2, 'Taller de Desarrollo Web', 'Aplicaciones web modernas.', '2026-11-17', '08:00', '11:00', 'Laboratorio 2', 20, 'abierta'),
(7, 2, 3, 3, 'Charla sobre Ciberseguridad', 'Cómo protegerse en internet.', '2026-11-17', '13:00', '14:30', 'Salón B', 30, 'abierta'),
(8, 2, 4, 4, 'Mesa redonda sobre innovación', 'Panel de líderes tecnológicos.', '2026-11-18', '10:00', '12:00', 'Salón A', 60, 'cancelada'),
(9, 3, 4, 3, 'Charla: De la idea al negocio', 'Primeros pasos para emprender.', '2027-02-10', '09:00', '10:30', 'Sala Norte', 40, 'programada'),
(10, 3, NULL, 5, 'Capacitación: Finanzas para emprendedores', 'Costos, precios y flujo de caja.', '2027-02-11', '09:00', '12:00', 'Sala Norte', 25, 'programada');

INSERT INTO inscripcion (id, participante_id, actividad_id, fecha_hora, estado) VALUES
(1, 6, 1, '2026-09-01 10:00:00', 'completada'),
(2, 7, 1, '2026-09-01 10:05:00', 'completada'),
(3, 8, 1, '2026-09-02 09:00:00', 'completada'),
(4, 9, 1, '2026-09-02 11:30:00', 'completada'),
(5, 6, 2, '2026-09-03 08:00:00', 'completada'),
(6, 10, 2, '2026-09-03 08:10:00', 'completada'),
(7, 11, 2, '2026-09-04 14:00:00', 'completada'),
(8, 12, 2, '2026-09-05 16:00:00', 'cancelada'),
(9, 7, 3, '2026-09-06 09:00:00', 'completada'),
(10, 13, 3, '2026-09-06 09:30:00', 'completada'),
(11, 6, 4, '2026-10-20 10:00:00', 'activa'),
(12, 7, 4, '2026-10-20 10:15:00', 'activa'),
(13, 14, 4, '2026-10-21 08:00:00', 'activa'),
(14, 15, 4, '2026-10-21 09:00:00', 'activa'),
(15, 8, 5, '2026-10-21 10:00:00', 'activa'),
(16, 9, 5, '2026-10-21 10:30:00', 'activa'),
(17, 10, 5, '2026-10-22 08:00:00', 'activa'),
(18, 11, 5, '2026-10-22 09:00:00', 'activa'),
(19, 12, 5, '2026-10-22 11:00:00', 'activa'),
(20, 13, 5, '2026-10-23 07:45:00', 'cancelada'),
(21, 16, 6, '2026-10-23 10:00:00', 'activa'),
(22, 17, 6, '2026-10-24 12:00:00', 'activa'),
(23, 18, 6, '2026-10-25 15:00:00', 'activa'),
(24, 6, 7, '2026-10-25 16:00:00', 'activa'),
(25, 19, 7, '2026-10-26 09:00:00', 'activa'),
(26, 14, 7, '2026-10-26 10:00:00', 'activa');

INSERT INTO asistencia (inscripcion_id, asistio, fecha_registro) VALUES
(1, 1, '2026-09-19 18:00:00'),
(2, 1, '2026-09-19 18:00:00'),
(3, 0, '2026-09-19 18:00:00'),
(4, 1, '2026-09-19 18:00:00'),
(5, 1, '2026-09-19 18:00:00'),
(6, 1, '2026-09-19 18:00:00'),
(7, 0, '2026-09-19 18:00:00'),
(9, 1, '2026-09-19 18:00:00'),
(10, 0, '2026-09-19 18:00:00');

INSERT INTO usuario (persona_id, username, password, rol) VALUES
(NULL, 'admin',    '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'admin'),
(NULL, 'operador', '1725165c9a0b3698a3d01016e0d8205155820b8d7f21835ca64c0f81c728d880', 'operador');
