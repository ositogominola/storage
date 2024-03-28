CREATE DATABASE  IF NOT EXISTS `q15e7l9s6ygp90r6` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `q15e7l9s6ygp90r6`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: q15e7l9s6ygp90r6
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `perfiles_items`
--

DROP TABLE IF EXISTS `perfiles_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfiles_items` (
  `id_perfil` int NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `url_front` varchar(255) DEFAULT NULL,
  `perfiles_id` int DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `FK6icrk41ya1cen5j9fdxburc63` (`perfiles_id`),
  CONSTRAINT `FK6icrk41ya1cen5j9fdxburc63` FOREIGN KEY (`perfiles_id`) REFERENCES `perfiles` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfiles_items`
--

LOCK TABLES `perfiles_items` WRITE;
/*!40000 ALTER TABLE `perfiles_items` DISABLE KEYS */;
INSERT INTO `perfiles_items` VALUES (1,'pi pi-plus','Crear Empresa','/NewFactory',1),(2,'pi pi-user-edit','Usuarios','ItemsUserPerfil',4),(3,'pi pi-eye','Mis Empresas','/factorys',1),(4,'pi pi-sliders-h','Perifiles y Permisos','ItemsUserPerfil',4),(5,'pi pi-book','Resumen Empresas','/ResumenEmpresas',1);
/*!40000 ALTER TABLE `perfiles_items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-27 21:04:29
