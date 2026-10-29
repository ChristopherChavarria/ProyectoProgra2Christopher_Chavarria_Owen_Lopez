-- =============================================================
-- Sistema de Gestión de Eventos y Participantes - EIF204
-- Script de creación de tablas (MySQL 8)
-- Basado en la "Propuesta Inicial de la Base de Datos" del primer avance.
-- Ejecutarlo dentro de la base de datos que vayan a usar.
-- =============================================================
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS usuario, asistencia, inscripcion, actividad, tipo_actividad,
                     evento, expositor, participante, persona;
SET FOREIGN_KEY_CHECKS = 1;

-- Datos comunes de toda persona (herencia: tabla por subclase)
CREATE TABLE persona (
    id                 INT          NOT NULL AUTO_INCREMENT,
    identificacion     VARCHAR(20)  NOT NULL,
    nombre             VARCHAR(60)  NOT NULL,
    apellidos          VARCHAR(80)  NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL,
    telefono           VARCHAR(15)  NOT NULL,
    CONSTRAINT pk_persona PRIMARY KEY (id),
    CONSTRAINT uq_persona_identificacion UNIQUE (identificacion)
);

-- id es llave primaria y foránea hacia persona
CREATE TABLE participante (
    id             INT         NOT NULL,
    fecha_registro DATE        NOT NULL,
    estado         VARCHAR(15) NOT NULL DEFAULT 'activo',
    CONSTRAINT pk_participante PRIMARY KEY (id),
    CONSTRAINT fk_participante_persona FOREIGN KEY (id) REFERENCES persona (id),
    CONSTRAINT ck_participante_estado CHECK (estado IN ('activo', 'inactivo'))
);

CREATE TABLE expositor (
    id           INT          NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    descripcion  VARCHAR(500),
    CONSTRAINT pk_expositor PRIMARY KEY (id),
    CONSTRAINT fk_expositor_persona FOREIGN KEY (id) REFERENCES persona (id)
);

CREATE TABLE evento (
    id           INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(120) NOT NULL,
    descripcion  VARCHAR(500),
    fecha_inicio DATE         NOT NULL,
    fecha_fin    DATE         NOT NULL,
    lugar        VARCHAR(120) NOT NULL,
    estado       VARCHAR(15)  NOT NULL DEFAULT 'planificacion',
    CONSTRAINT pk_evento PRIMARY KEY (id),
    CONSTRAINT ck_evento_fechas CHECK (fecha_fin >= fecha_inicio),
    CONSTRAINT ck_evento_estado CHECK (estado IN
        ('planificacion', 'abierto', 'en_ejecucion', 'finalizado', 'cancelado'))
);

CREATE TABLE tipo_actividad (
    id     INT         NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(40) NOT NULL,
    CONSTRAINT pk_tipo_actividad PRIMARY KEY (id),
    CONSTRAINT uq_tipo_actividad_nombre UNIQUE (nombre)
);

-- Evento 1..N Actividad ; Expositor 1..N Actividad ; TipoActividad 1..N Actividad
CREATE TABLE actividad (
    id                INT          NOT NULL AUTO_INCREMENT,
    evento_id         INT          NOT NULL,
    expositor_id      INT,
    tipo_actividad_id INT          NOT NULL,
    nombre            VARCHAR(120) NOT NULL,
    descripcion       VARCHAR(500),
    fecha             DATE         NOT NULL,
    hora_inicio       TIME         NOT NULL,
    hora_finalizacion TIME         NOT NULL,
    ubicacion         VARCHAR(120) NOT NULL,
    capacidad_max     INT          NOT NULL,
    estado            VARCHAR(15)  NOT NULL DEFAULT 'programada',
    CONSTRAINT pk_actividad PRIMARY KEY (id),
    CONSTRAINT fk_actividad_evento FOREIGN KEY (evento_id) REFERENCES evento (id),
    CONSTRAINT fk_actividad_expositor FOREIGN KEY (expositor_id) REFERENCES expositor (id),
    CONSTRAINT fk_actividad_tipo FOREIGN KEY (tipo_actividad_id) REFERENCES tipo_actividad (id),
    CONSTRAINT ck_actividad_capacidad CHECK (capacidad_max > 0),
    CONSTRAINT ck_actividad_horas CHECK (hora_finalizacion > hora_inicio),
    CONSTRAINT ck_actividad_estado CHECK (estado IN
        ('programada', 'abierta', 'cerrada', 'finalizada', 'cancelada'))
);

-- Participante N..M Actividad, resuelta con inscripcion
CREATE TABLE inscripcion (
    id              INT         NOT NULL AUTO_INCREMENT,
    participante_id INT         NOT NULL,
    actividad_id    INT         NOT NULL,
    fecha_hora      DATETIME    NOT NULL,
    estado          VARCHAR(15) NOT NULL DEFAULT 'activa',
    CONSTRAINT pk_inscripcion PRIMARY KEY (id),
    CONSTRAINT fk_inscripcion_participante FOREIGN KEY (participante_id) REFERENCES participante (id),
    CONSTRAINT fk_inscripcion_actividad FOREIGN KEY (actividad_id) REFERENCES actividad (id),
    CONSTRAINT ck_inscripcion_estado CHECK (estado IN ('activa', 'cancelada', 'completada'))
);

-- Una fila por inscripción cuando ya se tomó asistencia. Sin fila = pendiente.
CREATE TABLE asistencia (
    id             INT        NOT NULL AUTO_INCREMENT,
    inscripcion_id INT        NOT NULL,
    asistio        TINYINT(1) NOT NULL,
    fecha_registro DATETIME   NOT NULL,
    CONSTRAINT pk_asistencia PRIMARY KEY (id),
    CONSTRAINT uq_asistencia_inscripcion UNIQUE (inscripcion_id),
    CONSTRAINT fk_asistencia_inscripcion FOREIGN KEY (inscripcion_id) REFERENCES inscripcion (id)
);

CREATE TABLE usuario (
    id         INT         NOT NULL AUTO_INCREMENT,
    persona_id INT,
    username   VARCHAR(30) NOT NULL,
    password   CHAR(64)    NOT NULL,   -- hash SHA-256, nunca la clave en texto plano
    rol        VARCHAR(10) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id),
    CONSTRAINT uq_usuario_username UNIQUE (username),
    CONSTRAINT fk_usuario_persona FOREIGN KEY (persona_id) REFERENCES persona (id),
    CONSTRAINT ck_usuario_rol CHECK (rol IN ('admin', 'operador'))
);

CREATE INDEX ix_actividad_fecha ON actividad (fecha);
CREATE INDEX ix_inscripcion_actividad_estado ON inscripcion (actividad_id, estado);
