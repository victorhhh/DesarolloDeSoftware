-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-03-2017 a las 22:23:17
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
  `IDMatricula` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `primerApellido` varchar(25) NOT NULL,
  `segundoApellido` varchar(25) NOT NULL,
  `numeroDeCelular` varchar(11) NOT NULL,
  `direccion` varchar(30) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `IDClase` int(22) NOT NULL,
  `IDPago` int(22) NOT NULL
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
  `hora` datetime(6) NOT NULL,
  `IDAlumno` int(22) NOT NULL,
  `IDMaestro` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripcion`
--

CREATE TABLE IF NOT EXISTS `inscripcion` (
  `IDInscripcion` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` datetime(6) NOT NULL,
  `IDAlumno` int(22) NOT NULL,
  `IDpago` int(11) NOT NULL
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
  `numeroDeCelular` varchar(11) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `direccion` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `IDClase` int(22) NOT NULL,
  `IDPagoEgreso` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensualidad`
--

CREATE TABLE IF NOT EXISTS `mensualidad` (
  `IDMensualidad` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` datetime(6) NOT NULL,
  `IDAlumno` int(22) NOT NULL,
  `IDPago` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoegreso`
--

CREATE TABLE IF NOT EXISTS `pagoegreso` (
  `IDEgreso` int(22) NOT NULL,
  `monto` int(11) NOT NULL,
  `fechaPago` datetime(6) NOT NULL,
  `IDMaestro` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoingreso`
--

CREATE TABLE IF NOT EXISTS `pagoingreso` (
  `IDIngreso` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` datetime(6) NOT NULL,
  `IDAlumno` int(22) NOT NULL,
  `IDPromocion` int(22) NOT NULL,
  `IDInscripcion` int(22) NOT NULL,
  `IDMensualidad` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion`
--

CREATE TABLE IF NOT EXISTS `promocion` (
  `IDPromocion` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  `IDPago` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`IDMatricula`),
  ADD KEY `IDPago_idx` (`IDPago`);

--
-- Indices de la tabla `clase`
--
ALTER TABLE `clase`
  ADD PRIMARY KEY (`IDClase`),
  ADD KEY `IDAlumno_idx` (`IDAlumno`),
  ADD KEY `IDMaestro_idx` (`IDMaestro`);

--
-- Indices de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD PRIMARY KEY (`IDInscripcion`);

--
-- Indices de la tabla `maestro`
--
ALTER TABLE `maestro`
  ADD PRIMARY KEY (`IDMaestro`),
  ADD KEY `IDClase_idx` (`IDClase`),
  ADD KEY `IDPagoEgreso_idx` (`IDPagoEgreso`);

--
-- Indices de la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  ADD PRIMARY KEY (`IDMensualidad`);

--
-- Indices de la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  ADD PRIMARY KEY (`IDEgreso`);

--
-- Indices de la tabla `pagoingreso`
--
ALTER TABLE `pagoingreso`
  ADD PRIMARY KEY (`IDIngreso`),
  ADD KEY `IDPromocion_idx` (`IDPromocion`),
  ADD KEY `IDMensualidad_idx` (`IDMensualidad`),
  ADD KEY `IDInscripcion_idx` (`IDInscripcion`);

--
-- Indices de la tabla `promocion`
--
ALTER TABLE `promocion`
  ADD PRIMARY KEY (`IDPromocion`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `IDPago` FOREIGN KEY (`IDPago`) REFERENCES `pagoingreso` (`IDIngreso`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `clase`
--
ALTER TABLE `clase`
  ADD CONSTRAINT `IDAlumno` FOREIGN KEY (`IDAlumno`) REFERENCES `alumno` (`IDMatricula`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDMaestro` FOREIGN KEY (`IDMaestro`) REFERENCES `maestro` (`IDMaestro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `maestro`
--
ALTER TABLE `maestro`
  ADD CONSTRAINT `IDPagoEgreso` FOREIGN KEY (`IDPagoEgreso`) REFERENCES `pagoegreso` (`IDEgreso`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagoingreso`
--
ALTER TABLE `pagoingreso`
  ADD CONSTRAINT `IDInscripcion` FOREIGN KEY (`IDInscripcion`) REFERENCES `inscripcion` (`IDInscripcion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDMensualidad` FOREIGN KEY (`IDMensualidad`) REFERENCES `mensualidad` (`IDMensualidad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDPromocion` FOREIGN KEY (`IDPromocion`) REFERENCES `promocion` (`IDPromocion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;