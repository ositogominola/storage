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
-- Table structure for table `permission_asignados`
--

DROP TABLE IF EXISTS `permission_asignados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_asignados` (
  `id_permission_asign` int NOT NULL AUTO_INCREMENT,
  `permission_id` int DEFAULT NULL,
  `roles_id` int DEFAULT NULL,
  PRIMARY KEY (`id_permission_asign`),
  KEY `FKd4cnopxxca677vkujwnt0ev0m` (`permission_id`),
  KEY `FK7s52gk2is52vejnbd99m44ygf` (`roles_id`),
  CONSTRAINT `FK7s52gk2is52vejnbd99m44ygf` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id_rol`),
  CONSTRAINT `FKd4cnopxxca677vkujwnt0ev0m` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id_permission`)
) ENGINE=InnoDB AUTO_INCREMENT=973 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_asignados`
--

LOCK TABLES `permission_asignados` WRITE;
/*!40000 ALTER TABLE `permission_asignados` DISABLE KEYS */;
INSERT INTO `permission_asignados` VALUES (291,1,1),(292,2,1),(293,3,1),(294,4,1),(295,5,1),(296,6,1),(297,7,1),(298,8,1),(299,9,1),(300,10,1),(301,11,1),(302,12,1),(303,13,1),(304,14,1),(305,15,1),(306,16,1),(307,17,1),(308,18,1),(309,19,1),(310,20,1),(311,21,1),(312,22,1),(313,23,1),(314,24,1),(315,25,1),(316,26,1),(317,27,1),(318,28,1),(319,29,1),(320,31,1),(321,34,1),(322,36,1),(323,37,1),(324,38,1),(325,39,1),(326,40,1),(327,41,1),(328,42,1),(329,43,1),(330,44,1),(331,46,1),(332,47,1),(333,48,1),(334,49,1),(335,50,1),(336,51,1),(337,52,1),(338,55,1),(339,56,1),(340,57,1),(341,58,1),(342,63,1),(343,64,1),(344,65,1),(345,66,1),(346,67,1),(347,68,1),(348,70,1),(349,71,1),(350,72,1),(351,73,1),(354,76,1),(355,32,1),(356,33,1),(418,77,1),(571,78,1),(788,40,2),(809,42,2),(810,79,1),(811,74,1),(812,75,1),(820,79,2),(883,5,20),(884,40,20),(885,42,20),(886,79,20),(890,3,20),(891,77,20),(893,50,20),(894,44,20),(895,74,20),(896,70,20),(897,32,20),(898,72,20),(900,58,20),(902,46,20),(903,66,20),(904,68,20),(908,75,20),(909,48,20),(910,76,20),(912,78,20),(938,81,1),(939,81,2),(940,3,2),(941,77,2),(943,50,2),(944,44,2),(945,46,2),(946,66,2),(947,68,2),(951,32,2),(952,58,2),(954,74,2),(955,70,2),(956,72,2),(958,75,2),(959,48,2),(960,78,2),(961,76,2),(963,80,1),(964,82,1),(966,83,1),(967,84,1),(968,85,1),(969,101,1),(970,101,1),(971,102,1);
/*!40000 ALTER TABLE `permission_asignados` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-27 21:04:30
