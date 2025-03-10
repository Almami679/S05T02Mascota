CREATE DATABASE IF NOT EXISTS aircraft_fleet_db DEFAULT CHARACTER SET utf8;

USE aircraft_fleet_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL,
    wallet DOUBLE DEFAULT 5000,
    score DOUBLE DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hangars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    time_of_day ENUM('DAY', 'NIGHT') NOT NULL,
    weather ENUM('DESPEJADO', 'NUBLADO', 'TORMENTA') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS plane_accessories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type ENUM('GUN', 'ARMOR') NOT NULL,
    name VARCHAR(100) NOT NULL,
    level INT NOT NULL,
    power INT NOT NULL
);

CREATE TABLE IF NOT EXISTS planes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    health INT NOT NULL,
    base_health INT NOT NULL,
    attack INT NOT NULL,
    fuel INT NOT NULL,
    hangar_id BIGINT NOT NULL,
    equipped_accessory BIGINT DEFAULT NULL,
    FOREIGN KEY (hangar_id) REFERENCES hangars(id) ON DELETE CASCADE,
    FOREIGN KEY (equipped_accessory) REFERENCES plane_accessories(id) ON DELETE SET NULL
);
