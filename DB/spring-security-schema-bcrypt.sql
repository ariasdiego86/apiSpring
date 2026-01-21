-- CREATE DATABASE db_books; -- Ya se crea automáticamente por docker-compose

-- SHOW TABLES;
-- SHOW FULL TABLES;
USE `db_books`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

-- *************************
-- Estructura de tabla `users`
-- *************************

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ***************************
-- insertando datos en `users`
-- ***************************

INSERT INTO `users` 
VALUES 
('BigResio','{bcrypt}$2a$12$TSKtDQ/1vMdyBqJ4fkTmUOyK0tPBwiFs.PIqEGC6P/.7dycljU4o.',1), -- password: BigResio123
('Resio','{bcrypt}$2a$12$UlF6so6Z8NbW6/CdPNwwjOgPLzkardn6lgCdRMWw55Tt5kpA1flVO',1), -- password: Resio123
('MiniResio','{bcrypt}$2a$12$bBxtLMavu/po6HMsffnbC.YavlDFjQTYvPqO1q2zHTpDauuxi3hFC',1); -- password: MiniResio123


-- *********************************
-- Estructura de tabla `authorities`
-- *********************************

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ***********************************
-- Insertando datos en `authorities`
-- **********************************

INSERT INTO `authorities` 
VALUES 
('Resio','ROLE_EMPLOYEE'),
('Resio','ROLE_SCHOLAR'),
('BigResio','ROLE_BOSS'),
('MiniResio','ROLE_SCHOLAR');

-- Los siguientes SELECT se ejecutarán cuando Spring Boot cree las tablas de entidades
-- SELECT * FROM AUTOR;
-- SELECT * FROM LIBROS;
-- SELECT * FROM CATEGORIA;
-- SELECT * FROM USERS;
-- SELECT * FROM AUTHORITIES;
