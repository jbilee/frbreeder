DROP TABLE IF EXISTS "users";
DROP TABLE IF EXISTS "dragons";
DROP TABLE IF EXISTS "goals";

CREATE TABLE "users" (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE "dragons" (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL DEFAULT 'Unnamed',
    breed VARCHAR(32) NOT NULL,
    gender ENUM('M', 'F') NOT NULL,
    primary_gene VARCHAR(32) NOT NULL,
    secondary_gene VARCHAR(32) NOT NULL,
    tertiary_gene VARCHAR(32) NOT NULL,
    primary_color VARCHAR(32) NOT NULL,
    secondary_color VARCHAR(32) NOT NULL,
    tertiary_color VARCHAR(32) NOT NULL,
    eye_type VARCHAR(32) NOT NULL,
    flight VARCHAR(16) NOT NULL,
    last_bred TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE "goals" (
    id BIGINT NOT NULL AUTO_INCREMENT,
    breed VARCHAR(32) NOT NULL,
    gender ENUM('M', 'F') NOT NULL,
    primary_gene VARCHAR(32) NOT NULL,
    secondary_gene VARCHAR(32) NOT NULL,
    tertiary_gene VARCHAR(32) NOT NULL,
    primary_color VARCHAR(32) NOT NULL,
    secondary_color VARCHAR(32) NOT NULL,
    tertiary_color VARCHAR(32) NOT NULL,
    eye_type VARCHAR(32) NOT NULL,
    flight VARCHAR(16) NOT NULL,
    PRIMARY KEY(id)
);
