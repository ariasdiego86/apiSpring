CREATE DATABASE db_books;

SHOW TABLES;
SHOW FULL TABLES;
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
('BigResio','{bcrypt}$2a$12$/M57HBsueOyWco6l7Bf2puua9GfYzJ2AeSTij4znluH3vcdTjhkHW',1),
('Resio','{bcrypt}$2a$12$kOmCs/IDuQuBA063N.x6fen46W09THRH.VwiVhFw0GHQP5eG4ZiZ6',1),
('MiniResio','{bcrypt}$2a$12$rcEKP6lYDucmLgPjTW19Iuly0ZRJ3/qL1GqOFrNVMISbiOXvw/R0u',1);


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
('MiniResio','ROLE_SCHOLAR')

SELECT * FROM AUTOR;
SELECT * FROM LIBROS;
SELECT * FROM CATEGORIA;
SELECT * FROM USERS;
SELECT * FROM AUTHORITIES;


