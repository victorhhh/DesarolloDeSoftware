-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-03-2017 a las 02:09:33
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
  `IDInscripcionA` int(22) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`IDAlumno`, `nombre`, `primerApellido`, `segundoApellido`, `numeroDeCelular`, `direccion`, `fechaNacimiento`, `estado`, `rutaImagen`, `IDInscripcionA`) VALUES
(1, 'Rodrigo', 'Hoyos', 'Salmoran', '2288191110', 'la progreso', '1994-10-27', 1, NULL, 1),
(2, 'Luz del Carmen', 'Garcia', 'Tejeda', '2288990911', 'Benito Juarez', '1997-08-10', 1, NULL, NULL),
(3, 'Ramon', 'Acosta', 'Ganzoa', '2281765900', '20 de Noviembre', '1994-03-16', 1, NULL, NULL),
(4, 'Perla', 'Rendo', 'Contreras', '2289953433', '21 de marzo', '1996-03-06', 1, NULL, NULL),
(5, 'Sara', 'Rocha', 'Delgado', '2281234664', 'Ebano', '1994-06-20', 1, NULL, NULL);

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
  `IDMaestroC` int(22) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clase`
--

INSERT INTO `clase` (`IDClase`, `nombre`, `estado`, `dia`, `hora`, `IDMaestroC`) VALUES
(1, 'Salsa', 1, 'Sabado', '10:00-12:30', 1),
(2, 'Arabe', 1, 'Jueves', '15:30-17:30', 2),
(3, 'Salsa', 1, 'Lunes', '16:00-17:00', 1),
(4, 'Salsa', 1, 'Miercoles', '18:00-20:00', 1),
(5, 'Arabe', 1, 'Viernes', '12:00-14:00', 2),
(6, 'Arabe', 1, 'Martes', '17:00-18:30', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE IF NOT EXISTS `grupo` (
  `IDGrupo` int(22) NOT NULL,
  `IDClaseG` int(22) DEFAULT NULL,
  `IDAlumnoG` int(22) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripcion`
--

CREATE TABLE IF NOT EXISTS `inscripcion` (
  `IDInscripcion` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaInscripcion` date NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `inscripcion`
--

INSERT INTO `inscripcion` (`IDInscripcion`, `monto`, `fechaInscripcion`) VALUES
(1, 245, '2017-03-25'),
(2, 250, '2017-03-15'),
(3, 380, '2017-03-21');

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
  `sueldo` double DEFAULT NULL,
  `rutaImagen` varchar(100) DEFAULT NULL,
  `IDClase` int(22) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `maestro`
--

INSERT INTO `maestro` (`IDMaestro`, `nombre`, `primerApellido`, `segundoApellido`, `numeroDeTelefono`, `fechaNacimiento`, `direccion`, `estado`, `sueldo`, `rutaImagen`, `IDClase`) VALUES
(1, 'Karina', 'Cabañas', 'Sanchez', '2281324155', '1993-04-25', 'La rotonda', 1, 540, NULL, 1),
(2, 'Vianey', 'Hernandez', 'Mancilla', '2288990910', '1993-07-13', 'melchor ocampo', 1, 320, NULL, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensualidad`
--

CREATE TABLE IF NOT EXISTS `mensualidad` (
  `IDMensualidad` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` date NOT NULL,
  `IDAlumnoM` int(22) DEFAULT NULL,
  `IDPagoM` int(22) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mensualidad`
--

INSERT INTO `mensualidad` (`IDMensualidad`, `monto`, `fechaPago`, `IDAlumnoM`, `IDPagoM`) VALUES
(1, 250, '2017-03-25', 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoegreso`
--

CREATE TABLE IF NOT EXISTS `pagoegreso` (
  `IDEgreso` int(22) NOT NULL,
  `monto` int(11) NOT NULL,
  `fechaPago` date NOT NULL,
  `IDMaestroPE` int(22) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pagoegreso`
--

INSERT INTO `pagoegreso` (`IDEgreso`, `monto`, `fechaPago`, `IDMaestroPE`) VALUES
(1, 540, '2017-03-28', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoingreso`
--

CREATE TABLE IF NOT EXISTS `pagoingreso` (
  `IDIngreso` int(22) NOT NULL,
  `monto` int(30) NOT NULL,
  `fechaPago` date NOT NULL,
  `IDAlumnoPI` int(22) DEFAULT NULL,
  `IDMensualidad` int(22) DEFAULT NULL,
  `IDPromocion` int(22) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pagoingreso`
--

INSERT INTO `pagoingreso` (`IDIngreso`, `monto`, `fechaPago`, `IDAlumnoPI`, `IDMensualidad`, `IDPromocion`) VALUES
(1, 245, '2017-03-25', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion`
--

CREATE TABLE IF NOT EXISTS `promocion` (
  `IDPromocion` int(22) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`IDAlumno`),
  ADD KEY `IDInscripcion_idx` (`IDInscripcionA`);

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
  ADD PRIMARY KEY (`IDInscripcion`);

--
-- Indices de la tabla `maestro`
--
ALTER TABLE `maestro`
  ADD PRIMARY KEY (`IDMaestro`),
  ADD KEY `IDClase_idx` (`IDClase`);

--
-- Indices de la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  ADD PRIMARY KEY (`IDMensualidad`),
  ADD KEY `IDAlumnoM_idx` (`IDAlumnoM`);

--
-- Indices de la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  ADD PRIMARY KEY (`IDEgreso`),
  ADD KEY `IDMaestroPE_idx` (`IDMaestroPE`);

--
-- Indices de la tabla `pagoingreso`
--
ALTER TABLE `pagoingreso`
  ADD PRIMARY KEY (`IDIngreso`),
  ADD KEY `IDAlumnoPI_idx` (`IDAlumnoPI`),
  ADD KEY `IDMensualidad_idx` (`IDMensualidad`),
  ADD KEY `IDPromocion_idx` (`IDPromocion`);

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
  MODIFY `IDAlumno` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `clase`
--
ALTER TABLE `clase`
  MODIFY `IDClase` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `grupo`
--
ALTER TABLE `grupo`
  MODIFY `IDGrupo` int(22) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  MODIFY `IDInscripcion` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `maestro`
--
ALTER TABLE `maestro`
  MODIFY `IDMaestro` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  MODIFY `IDMensualidad` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  MODIFY `IDEgreso` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `pagoingreso`
--
ALTER TABLE `pagoingreso`
  MODIFY `IDIngreso` int(22) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
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
  ADD CONSTRAINT `IDInscripcion` FOREIGN KEY (`IDInscripcionA`) REFERENCES `inscripcion` (`IDInscripcion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Filtros para la tabla `mensualidad`
--
ALTER TABLE `mensualidad`
  ADD CONSTRAINT `IDAlumnoM` FOREIGN KEY (`IDAlumnoM`) REFERENCES `alumno` (`IDAlumno`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagoegreso`
--
ALTER TABLE `pagoegreso`
  ADD CONSTRAINT `IDMaestroPE` FOREIGN KEY (`IDMaestroPE`) REFERENCES `maestro` (`IDMaestro`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagoingreso`
--
ALTER TABLE `pagoingreso`
  ADD CONSTRAINT `IDAlumnoPI` FOREIGN KEY (`IDAlumnoPI`) REFERENCES `alumno` (`IDAlumno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDMensualidad` FOREIGN KEY (`IDMensualidad`) REFERENCES `mensualidad` (`IDMensualidad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IDPromocion` FOREIGN KEY (`IDPromocion`) REFERENCES `promocion` (`IDPromocion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
