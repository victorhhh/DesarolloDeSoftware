-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-03-2017 a las 13:16:47
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
  `IDPago` int(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`IDMatricula`, `nombre`, `primerApellido`, `segundoApellido`, `numeroDeCelular`, `direccion`, `fechaNacimiento`, `estado`, `IDPago`) VALUES
(1, 'Carmen', 'Lucio', 'Carreto', '2299334115', 'La revo', '1998-03-01', 1, 1),
(2, 'Rodrigo', 'Ruiz', 'Hoyos', '2288213986', 'La progreso', '1994-11-27', 1, 2);

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
  `IDAlumno` int(22) DEFAULT NULL,
  `IDMaestro` int(22) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clase`
--

INSERT INTO `clase` (`IDClase`, `nombre`, `estado`, `dia`, `hora`, `IDAlumno`, `IDMaestro`) VALUES
(1, 'Salsa', 1, 'Lunes', '13:00-15.00', 1, 1),
(2, 'Arabe', 1, 'Martes', '18:30-20:00', 2, 2),
(3, 'Cumbia', 0, 'Jueves', '10:00-12:00', NULL, NULL);

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

--
-- Volcado de datos para la tabla `inscripcion`
--

INSERT INTO `inscripcion` (`IDInscripcion`, `monto`, `fechaPago`, `IDAlumno`, `IDpago`) VALUES
(1, 350, '2017-03-13 00:00:00.000000', 1, 1),
(2, 280, '2017-03-10 00:00:00.000000', 2, 2);

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

--
-- Volcado de datos para la tabla `maestro`
--

INSERT INTO `maestro` (`IDMaestro`, `nombre`, `primerApellido`, `segundoApellido`, `numeroDeCelular`, `fechaNacimiento`, `direccion`, `estado`, `IDClase`, `IDPagoEgreso`) VALUES
(1, 'Karina', 'Gonzalez', 'Martinez', '2288965432', '1993-03-01', 'Animas #1209', 1, 1, 1),
(2, 'Vianey', 'Hernandez', 'Juarez', '2288915511', '1996-07-16', 'Benito Juarez #133', 1, 2, 2);

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

--
-- Volcado de datos para la tabla `mensualidad`
--

INSERT INTO `mensualidad` (`IDMensualidad`, `monto`, `fechaPago`, `IDAlumno`, `IDPago`) VALUES
(1, 250, '2017-03-18 00:00:00.000000', 2, 3),
(2, 320, '2017-03-20 00:00:00.000000', 3, 4);

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

--
-- Volcado de datos para la tabla `pagoegreso`
--

INSERT INTO `pagoegreso` (`IDEgreso`, `monto`, `fechaPago`, `IDMaestro`) VALUES
(1, 200, '2017-03-01 00:00:00.000000', 1),
(2, 250, '2017-03-16 06:20:29.000000', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoingreso`
--

CREATE TABLE IF NOT EXISTS `pagoingreso` (
  `IDIngreso` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` datetime(6) NOT NULL,
  `IDAlumno` int(22) NOT NULL,
  `IDPromocion` int(22) DEFAULT NULL,
  `IDInscripcion` int(22) DEFAULT NULL,
  `IDMensualidad` int(22) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pagoingreso`
--

INSERT INTO `pagoingreso` (`IDIngreso`, `monto`, `fechaPago`, `IDAlumno`, `IDPromocion`, `IDInscripcion`, `IDMensualidad`) VALUES
(1, 350, '2017-03-13 00:00:00.000000', 1, 1, 1, NULL),
(2, 280, '2017-03-10 00:00:00.000000', 2, NULL, 2, NULL);

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
-- Volcado de datos para la tabla `promocion`
--

INSERT INTO `promocion` (`IDPromocion`, `nombre`, `descripcion`, `IDPago`) VALUES
(1, 'desc30Porc', 'se descuenta el 30 porciento', 1),
(2, 'mitadDePrecio', 'pagas la mitad de la inscrip', 2);

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
