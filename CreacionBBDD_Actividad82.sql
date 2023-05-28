# Creación de la base de datos para el ejercicio 8.2
DROP DATABASE IF EXISTS campeonato;
CREATE DATABASE IF NOT EXISTS campeonato CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# Creamos las tablas correspondientes
USE campeonato;
CREATE TABLE IF NOT EXISTS equipos (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(35),
    estadio VARCHAR(35),
    ciudad VARCHAR(35),
    num_socios INT UNSIGNED
);

CREATE TABLE IF NOT EXISTS jugadores(
id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(20),
    apellidos VARCHAR(35),
    fechaNacimiento DATE,
    equipo_id INT UNSIGNED
);