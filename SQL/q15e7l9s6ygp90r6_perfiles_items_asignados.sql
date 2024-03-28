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
-- Table structure for table `perfiles_items_asignados`
--

DROP TABLE IF EXISTS `perfiles_items_asignados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfiles_items_asignados` (
  `id_perfil_item_asign` int NOT NULL AUTO_INCREMENT,
  `perfiles_items_id` int DEFAULT NULL,
  `roles_id` int DEFAULT NULL,
  PRIMARY KEY (`id_perfil_item_asign`),
  KEY `FKoitb4byuibx9oracfbrswi4r6` (`perfiles_items_id`),
  KEY `FKj4o889fi3gs1965gqhuicf17u` (`roles_id`),
  CONSTRAINT `FKj4o889fi3gs1965gqhuicf17u` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id_rol`),
  CONSTRAINT `FKoitb4byuibx9oracfbrswi4r6` FOREIGN KEY (`perfiles_items_id`) REFERENCES `perfiles_items` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfiles_items_asignados`
--

LOCK TABLES `perfiles_items_asignados` WRITE;
/*!40000 ALTER TABLE `perfiles_items_asignados` DISABLE KEYS */;
INSERT INTO `perfiles_items_asignados` VALUES (3,4,1),(61,2,1),(62,1,1),(63,3,1),(64,5,1),(108,4,2),(110,2,2),(112,4,20);
/*!40000 ALTER TABLE `perfiles_items_asignados` ENABLE KEYS */;
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
