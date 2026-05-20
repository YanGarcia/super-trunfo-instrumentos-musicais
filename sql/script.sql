-- =============================================
-- Super Trunfo - Instrumentos Musicais
-- Script de criação do banco e inserção de dados
-- =============================================

CREATE DATABASE IF NOT EXISTS super_trunfo
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE super_trunfo;

DROP TABLE IF EXISTS cartas;

CREATE TABLE cartas (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    codigo        VARCHAR(10)  NOT NULL,
    ano_criacao   INT          NOT NULL,
    peso_kg       DOUBLE       NOT NULL,
    preco         DOUBLE       NOT NULL,
    popularidade  INT          NOT NULL,
    volume_db     INT          NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11 cartas (número ímpar) de instrumentos musicais
INSERT INTO cartas (nome, codigo, ano_criacao, peso_kg, preco, popularidade, volume_db) VALUES
('Piano de Cauda',        'I1',  1700, 480.0,  85000.0, 82, 90),
('Guitarra Elétrica',     'I2',  1931,   4.0,   4500.0, 97, 110),
('Violino',               'I3',  1550,   0.5,  12000.0, 78, 85),
('Bateria Acústica',      'I4',  1909,  45.0,   7500.0, 88, 120),
('Saxofone',              'I5',  1846,   2.5,   6500.0, 72, 100),
('Harpa',                 'I6',  1810,  38.0,  95000.0, 35, 75),
('Flauta Transversal',    'I7',  1847,   0.4,   3200.0, 55, 78),
('Contrabaixo Acústico',  'I8',  1500,  12.0,  18000.0, 45, 65),
('Trompete',              'I9',  1820,   1.3,   4000.0, 68, 108),
('Acordeão',              'I10', 1829,  11.0,   5500.0, 58, 95),
('Tuba',                  'I11', 1835,  13.5,  15000.0, 28, 105);
