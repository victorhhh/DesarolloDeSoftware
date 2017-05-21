-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2017 a las 17:13:50
-- Versión del servidor: 5.6.25
-- Versión de PHP: 5.6.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aredespacio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE IF NOT EXISTS `alumno` (
  `IDAlumno` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `primerApellido` varchar(25) NOT NULL,
  `segundoApellido` varchar(25) NOT NULL,
  `numeroDeCelular` varchar(11) NOT NULL,
  `direccion` varchar(30) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `rutaImagen` varchar(100) DEFAULT NULL,
  `IDInscripcionA` int(22) DEFAULT NULL,
  `IDMensualidadA` int(22) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE IF NOT EXISTS `asistencia` (
  `IDAsistencia` int(22) NOT NULL,
  `IDAlumnoAsis` int(22) NOT NULL,
  `IDClaseAsis` int(22) NOT NULL,
  `fecha` date NOT NULL,
  `asistencia` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clase`
--

CREATE TABLE IF NOT EXISTS `clase` (
  `IDClase` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `dia` varchar(12) NOT NULL,
  `hora` varchar(25) NOT NULL,
  `IDMaestroC` int(22) DEFAULT NULL,
  `costo` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE IF NOT EXISTS `grupo` (
  `IDGrupo` int(22) NOT NULL,
  `IDClaseG` int(22) NOT NULL,
  `IDAlumnoG` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripcion`
--

CREATE TABLE IF NOT EXISTS `inscripcion` (
  `IDInscripcion` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaInscripcion` date NOT NULL,
  `IDPromocionI` int(22) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `maestro`
--

CREATE TABLE IF NOT EXISTS `maestro` (
  `IDMaestro` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `primerApellido` varchar(25) NOT NULL,
  `segundoApellido` varchar(25) NOT NULL,
  `numeroDeTelefono` varchar(11) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `direccion` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `sueldo` double NOT NULL,
  `rutaImagen` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensualidad`
--

CREATE TABLE IF NOT EXISTS `mensualidad` (
  `IDMensualidad` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` date NOT NULL,
  `IDPromocionM` int(22) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoegreso`
--

CREATE TABLE IF NOT EXISTS `pagoegreso` (
  `IDEgreso` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` date NOT NULL,
  `IDMaestroPE` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion`
--

CREATE TABLE IF NOT EXISTS `promocion` (
  `IDPromocion` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `tipo` varchar(12) NOT NULL,
  `porcentaje` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`IDAlumno`),
  ADD KEY `IDInscripcionA_idx` (`IDInscripcionA`),
  ADD KEY `IDMensualidadA_idx` (`IDMensualidadA`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`IDAsistencia`),
  ADD KEY `IDAlumnoAsis_idx` (`IDAlumnoAsis`),
  ADD KEY `IDClaseAsis_idx` (`IDClaseAsis`);

--
-- Indices de la tabla `clase`
--
ALTER TABLE `clase`
  ADD PRIMARY KEY (`IDClase`),
  ADD KEY `IDMaestroC_idx` (`IDMaestroC`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`IDGrupo`),
  ADD KEY `IDClaseG_idx` (`IDClaseG`),
  ADD KEY `IDAlumnoG_idx` (`IDAlumnoG`);

--
-- Indices de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD PRIMARY KEY (`IDInscripcion`),
  ADD KEY `IDPromocionI_idx` (`IDPromocionI`);

--
-- Indices de la tabla `maestro`
--
ALTER TABLE `maestro`
  ADD PRIMARY KEY (`IDMaestro`);

--
-- Indices de la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  ADD PRIMARY KEY (`IDMensualidad`),
  ADD KEY `IDPromocionM_idx` (`IDPromocionM`);

--
-- Indices de la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  ADD PRIMARY KEY (`IDEgreso`),
  ADD KEY `IDMaestroPE_idx` (`IDMaestroPE`);

--
-- Indices de la tabla `promocion`
--
ALTER TABLE `promocion`
  ADD PRIMARY KEY (`IDPromocion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumno`
--
ALTER TABLE `alumno`
  MODIFY `IDAlumno` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `IDAsistencia` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `clase`
--
ALTER TABLE `clase`
  MODIFY `IDClase` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `grupo`
--
ALTER TABLE `grupo`
  MODIFY `IDGrupo` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  MODIFY `IDInscripcion` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `maestro`
--
ALTER TABLE `maestro`
  MODIFY `IDMaestro` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  MODIFY `IDMensualidad` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  MODIFY `IDEgreso` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `promocion`
--
ALTER TABLE `promocion`
  MODIFY `IDPromocion` int(22) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `IDInscripcionA` FOREIGN KEY (`IDInscripcionA`) REFERENCES `inscripcion` (`IDInscripcion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDMensualidadA` FOREIGN KEY (`IDMensualidadA`) REFERENCES `mensualidad` (`IDMensualidad`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `IDAlumnoAsis` FOREIGN KEY (`IDAlumnoAsis`) REFERENCES `alumno` (`IDAlumno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDClaseAsis` FOREIGN KEY (`IDClaseAsis`) REFERENCES `clase` (`IDClase`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `clase`
--
ALTER TABLE `clase`
  ADD CONSTRAINT `IDMaestroC` FOREIGN KEY (`IDMaestroC`) REFERENCES `maestro` (`IDMaestro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `IDAlumnoG` FOREIGN KEY (`IDAlumnoG`) REFERENCES `alumno` (`IDAlumno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDClaseG` FOREIGN KEY (`IDClaseG`) REFERENCES `clase` (`IDClase`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD CONSTRAINT `IDPromocionI` FOREIGN KEY (`IDPromocionI`) REFERENCES `promocion` (`IDPromocion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  ADD CONSTRAINT `IDPromocionM` FOREIGN KEY (`IDPromocionM`) REFERENCES `promocion` (`IDPromocion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  ADD CONSTRAINT `IDMaestroPE` FOREIGN KEY (`IDMaestroPE`) REFERENCES `maestro` (`IDMaestro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
