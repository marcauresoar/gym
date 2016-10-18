-- phpMyAdmin SQL Dump
-- version 4.6.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 28, 2016 at 08:35 AM
-- Server version: 5.7.13-0ubuntu0.16.04.2
-- PHP Version: 7.0.8-0ubuntu0.16.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gym`
--

-- --------------------------------------------------------

--
-- Table structure for table `exercicio`
--

CREATE TABLE `exercicio` (
  `id` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL,
  `grupo_muscular` varchar(256) NOT NULL,
  `ficha_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `exercicio`
--

INSERT INTO `exercicio` (`id`, `nome`, `grupo_muscular`, `ficha_id`) VALUES
(1, 'Exercicio 1', 'Abdominal', 1),
(2, 'Exercicio 2', 'Bíceps', 2),
(3, 'Exercicio 3', 'Costas', 1),
(4, 'Exercicio 4', 'Peito', 1),
(5, 'Exercicio 5', 'Perna', 2),
(6, 'Exercicio 6', 'Triceps', 2),
(7, 'Exercicio 7', 'Ombro', 1),
(8, 'Exercicio 8', 'Bíceps', 1),
(9, 'Exercicio 9', 'Peito', 2),
(10, 'Exercicio 10', 'Perna', 1);

-- --------------------------------------------------------

--
-- Table structure for table `exercicio_treino`
--

CREATE TABLE `exercicio_treino` (
  `id` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL,
  `grupo_muscular` varchar(256) NOT NULL,
  `treino_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `exercicio_treino` (`id`, `nome`, `grupo_muscular`, `treino_id`) VALUES
(1, 'Exercicio 1', 'Abdominal', 1),
(2, 'Exercicio 2', 'Bíceps', 2),
(3, 'Exercicio 3', 'Costas', 1),
(4, 'Exercicio 4', 'Peito', 1),
(5, 'Exercicio 5', 'Perna', 2),
(6, 'Exercicio 6', 'Triceps', 2),
(7, 'Exercicio 7', 'Ombro', 1),
(8, 'Exercicio 8', 'Bíceps', 1),
(9, 'Exercicio 9', 'Peito', 2),
(10, 'Exercicio 10', 'Perna', 1);

-- --------------------------------------------------------

--
-- Table structure for table `ficha`
--

CREATE TABLE `ficha` (
  `id` int(11) NOT NULL,
  `nome` varchar(64) NOT NULL,
  `dias_semana` varchar(256) NOT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ficha`
--

INSERT INTO `ficha` (`id`, `nome`, `dias_semana`, `usuario_id`) VALUES
(1, 'Ficha A', 'Segunda,Quarta,Sexta', 1),
(2, 'Ficha B', 'Terça,Quinta', 1);

-- --------------------------------------------------------

--
-- Table structure for table `serie`
--

CREATE TABLE `serie` (
  `id` int(11) NOT NULL,
  `tipo` enum('peso','tempo') NOT NULL,
  `repeticoes` int(11) DEFAULT NULL,
  `peso` int(11) DEFAULT NULL,
  `tempo` int(11) DEFAULT NULL,
  `exercicio_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `serie`
--

INSERT INTO `serie` (`id`, `tipo`, `repeticoes`, `peso`, `tempo`, `exercicio_id`) VALUES
(1, 'peso', 10, 8, NULL, 1),
(2, 'peso', 10, 8, NULL, 1),
(3, 'peso', 10, 8, NULL, 1),
(4, 'peso', 10, 12, NULL, 2),
(5, 'peso', 10, 12, NULL, 2),
(6, 'peso', 10, 12, NULL, 2),
(7, 'peso', 5, 12, NULL, 3),
(8, 'peso', 5, 12, NULL, 3),
(9, 'peso', 5, 12, NULL, 3),
(10, 'peso', 10, 10, NULL, 4),
(11, 'peso', 10, 10, NULL, 4),
(12, 'peso', 10, 10, NULL, 4),
(13, 'peso', 10, 20, NULL, 5),
(14, 'peso', 10, 20, NULL, 5),
(15, 'peso', 10, 20, NULL, 5),
(16, 'peso', 10, 4, NULL, 6),
(17, 'peso', 10, 4, NULL, 6),
(18, 'peso', 10, 4, NULL, 6),
(19, 'tempo', NULL, NULL, 20, 7),
(20, 'tempo', NULL, NULL, 20, 7),
(21, 'tempo', NULL, NULL, 20, 7),
(22, 'tempo', NULL, NULL, 30, 8),
(23, 'tempo', NULL, NULL, 30, 8),
(24, 'tempo', NULL, NULL, 30, 8),
(25, 'tempo', NULL, NULL, 20, 9),
(26, 'tempo', NULL, NULL, 20, 9),
(27, 'tempo', NULL, NULL, 20, 9),
(28, 'tempo', NULL, NULL, 10, 10),
(29, 'tempo', NULL, NULL, 10, 10),
(30, 'tempo', NULL, NULL, 10, 10);

-- --------------------------------------------------------

--
-- Table structure for table `serie_treino`
--

CREATE TABLE `serie_treino` (
  `id` int(11) NOT NULL,
  `tipo` enum('peso','tempo') NOT NULL,
  `repeticoes` int(11) DEFAULT NULL,
  `peso` int(11) DEFAULT NULL,
  `tempo` int(11) DEFAULT NULL,
  `feito` boolean NOT NULL,
  `exercicio_treino_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `serie_treino` (`id`, `tipo`, `repeticoes`, `peso`, `tempo`, `exercicio_treino_id`, `feito`) VALUES
(1, 'peso', 10, 8, NULL, 1, 1),
(2, 'peso', 10, 8, NULL, 1, 1),
(3, 'peso', 10, 8, NULL, 1, 0),
(4, 'peso', 10, 12, NULL, 2, 1),
(5, 'peso', 10, 12, NULL, 2, 0),
(6, 'peso', 10, 12, NULL, 2, 0),
(7, 'peso', 5, 12, NULL, 3, 1),
(8, 'peso', 5, 12, NULL, 3, 1),
(9, 'peso', 5, 12, NULL, 3, 1),
(10, 'peso', 10, 10, NULL, 4, 0),
(11, 'peso', 10, 10, NULL, 4, 0),
(12, 'peso', 10, 10, NULL, 4, 0),
(13, 'peso', 10, 20, NULL, 5, 1),
(14, 'peso', 10, 20, NULL, 5, 1),
(15, 'peso', 10, 20, NULL, 5, 1),
(16, 'peso', 10, 4, NULL, 6, 1),
(17, 'peso', 10, 4, NULL, 6, 1),
(18, 'peso', 10, 4, NULL, 6, 0),
(19, 'tempo', NULL, NULL, 20, 7, 1),
(20, 'tempo', NULL, NULL, 20, 7, 1),
(21, 'tempo', NULL, NULL, 20, 7, 1),
(22, 'tempo', NULL, NULL, 30, 8, 0),
(23, 'tempo', NULL, NULL, 30, 8, 1),
(24, 'tempo', NULL, NULL, 30, 8, 1),
(25, 'tempo', NULL, NULL, 20, 9, 0),
(26, 'tempo', NULL, NULL, 20, 9, 1),
(27, 'tempo', NULL, NULL, 20, 9, 1),
(28, 'tempo', NULL, NULL, 10, 10, 0),
(29, 'tempo', NULL, NULL, 10, 10, 1),
(30, 'tempo', NULL, NULL, 10, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `treino`
--

CREATE TABLE `treino` (
  `id` int(11) NOT NULL,
  `data` date NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fim` time DEFAULT NULL,
  `usuario_id` int(11) NOT NULL,
  `ficha_id` int(11) NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `treino` (`id`, `data`, `hora_inicio`, `hora_fim`, `usuario_id`) VALUES
(1, '2016-10-06', '06:20:00', '08:10:00', 1),
(2, '2016-10-10', '15:00:00', '18:20:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nome` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `senha` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`) VALUES
(1, 'marco', 'marco@gmail.com', '202cb962ac59075b964b07152d234b70'),
(2, 'aurelio', 'aurelio@gmail.com', '202cb962ac59075b964b07152d234b70');


-- -----------------------------------------------------
-- Table `gym`.`avaliacao`
-- -----------------------------------------------------
CREATE TABLE `avaliacao` (
  `id` int(11) NOT NULL,
  `data` DATE NOT NULL,
  `altura` FLOAT NOT NULL,
  `peso` FLOAT NOT NULL,
  `peitoral` FLOAT NULL,
  `biceps` FLOAT NULL,
  `triceps` FLOAT NULL,
  `subescapular` FLOAT NULL,
  `supra_iliaca` FLOAT NULL,
  `axiliar_media` FLOAT NULL,
  `abdominal` FLOAT NULL,
  `coxa` FLOAT NULL,
  `panturrilha_media` FLOAT NULL,
  `torax` FLOAT NULL,
  `abdomen` FLOAT NULL,
  `cintura` FLOAT NULL,
  `quadril` FLOAT NULL,
  `braco_direito` FLOAT NULL,
  `braco_esquerdo` FLOAT NULL,
  `antebraco_direito` FLOAT NULL,
  `antebraco_esquerdo` FLOAT NULL,
  `coxa_direita` FLOAT NULL,
  `coxa_esquerda` FLOAT NULL,
  `perna_direita` FLOAT NULL,
  `perna_esquerda` FLOAT NULL,
  `ombro` FLOAT NULL,
  `pescoco` FLOAT NULL,
  `punho` FLOAT NULL,
  `joelho` FLOAT NULL,
  `tornozelo` FLOAT NULL,
  `usuario_id` int(11) NOT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Dumping data for table `avaliacao`
--

INSERT INTO `avaliacao` (`id`, `data`, `altura`, `peso`, `peitoral`, `biceps`, `triceps`, `subescapular`, `supra_iliaca`, `axiliar_media`, `abdominal`, `coxa`, `panturrilha_media`, `torax`, `abdomen`, `cintura`, `quadril`, `braco_direito`, `braco_esquerdo`, `antebraco_direito`, `antebraco_esquerdo`, `coxa_direita`, `coxa_esquerda`, `perna_direita`, `perna_esquerda`, `ombro`, `pescoco`, `punho`, `joelho`, `tornozelo`, `usuario_id`) VALUES
(1, '2012-12-12', 160, 60, 63, 67, 23, 24, 75, 44, 67, 12, 12, 23, 44, 54, 67, 34, 66, 34, 54, 76, 57, 87, 76, 88, 54, 23, 44, 67, 1),
(2, '2014-11-20', 161, 70, 89, 34, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
(3, '2016-10-17', 162, 80, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);


--
-- Indexes for dumped tables
--

--
-- Indexes for table `exercicio`
--
ALTER TABLE `exercicio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_exercicio_ficha1_idx` (`ficha_id`);

--
-- Indexes for table `exercicio_treino`
--
ALTER TABLE `exercicio_treino`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_exercicio_treino_treino1_idx` (`treino_id`);

--
-- Indexes for table `ficha`
--
ALTER TABLE `ficha`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ficha_usuario1_idx` (`usuario_id`);

--
-- Indexes for table `serie`
--
ALTER TABLE `serie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_serie_exercicio_idx` (`exercicio_id`);

--
-- Indexes for table `serie_treino`
--
ALTER TABLE `serie_treino`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_serie_treino_exercicio_treino1_idx` (`exercicio_treino_id`);

--
-- Indexes for table `treino`
--
ALTER TABLE `treino`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_treino_usuario1_idx` (`usuario_id`);


ALTER TABLE `avaliacao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_avaliacao_usuario1_idx` (`usuario_id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `exercicio`
--
ALTER TABLE `exercicio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `exercicio_treino`
--
ALTER TABLE `exercicio_treino`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ficha`
--
ALTER TABLE `ficha`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `serie`
--
ALTER TABLE `serie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `serie_treino`
--
ALTER TABLE `serie_treino`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `treino`
--
ALTER TABLE `treino`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `avaliacao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `exercicio`
--
ALTER TABLE `exercicio`
  ADD CONSTRAINT `fk_exercicio_ficha1` FOREIGN KEY (`ficha_id`) REFERENCES `ficha` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `exercicio_treino`
--
ALTER TABLE `exercicio_treino`
  ADD CONSTRAINT `fk_exercicio_treino_treino1` FOREIGN KEY (`treino_id`) REFERENCES `treino` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `ficha`
--
ALTER TABLE `ficha`
  ADD CONSTRAINT `fk_ficha_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `serie`
--
ALTER TABLE `serie`
  ADD CONSTRAINT `fk_serie_exercicio` FOREIGN KEY (`exercicio_id`) REFERENCES `exercicio` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `serie_treino`
--
ALTER TABLE `serie_treino`
  ADD CONSTRAINT `fk_serie_treino_exercicio_treino1` FOREIGN KEY (`exercicio_treino_id`) REFERENCES `exercicio_treino` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `treino`
--
ALTER TABLE `treino`
  ADD CONSTRAINT `fk_treino_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

  ALTER TABLE `avaliacao`
  ADD CONSTRAINT `fk_avaliacao_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
