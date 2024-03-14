CREATE DATABASE  IF NOT EXISTS `q15e7l9s6ygp90r6` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `q15e7l9s6ygp90r6`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: u6354r3es4optspf.cbetxkdyhwsb.us-east-1.rds.amazonaws.com    Database: q15e7l9s6ygp90r6
-- ------------------------------------------------------
-- Server version	8.0.33

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_permissions` (
  `roles_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`roles_id`,`permission_id`),
  KEY `FKboeuhl31go7wer3bpy6so7exi` (`permission_id`),
  CONSTRAINT `FKb9gqc5kvla3ijovnihsbb816e` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id_rol`),
  CONSTRAINT `FKboeuhl31go7wer3bpy6so7exi` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_permissions`
--

LOCK TABLES `roles_permissions` WRITE;
/*!40000 ALTER TABLE `roles_permissions` DISABLE KEYS */;
INSERT INTO `roles_permissions` VALUES (1,1),(2,1),(4,1),(5,1),(1,2),(4,2),(5,2),(1,3),(4,3),(5,3),(1,4),(2,4),(4,4),(5,4),(1,5),(2,5),(4,5),(5,5),(1,6),(2,6),(4,6),(5,6),(1,7),(2,7),(5,7),(1,8),(5,8),(1,9),(5,9),(1,10),(5,10),(1,11),(5,11),(1,12),(2,12),(5,12),(1,13),(5,13),(1,14),(2,14),(5,14),(1,15),(5,15),(1,16),(5,16),(1,17),(2,17),(5,17),(1,18),(5,18),(1,19),(5,19),(1,20),(5,20),(1,21),(5,21),(1,22),(5,22),(1,23),(5,23),(1,24),(5,24),(1,25),(5,25),(1,26),(5,26),(1,27),(5,27),(1,28),(5,28),(1,29),(5,29),(1,30),(5,30),(1,31),(5,31),(1,32),(5,32),(1,33),(5,33),(1,34),(5,34),(1,36),(5,36),(1,37),(5,37),(1,38),(5,38),(1,39),(5,39),(1,40),(5,40),(1,41),(5,41),(1,42),(5,42),(1,43),(5,43),(1,44),(5,44),(1,45),(5,45),(1,46),(5,46),(1,47),(5,47),(1,48),(2,48),(5,48);
/*!40000 ALTER TABLE `roles_permissions` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-18 21:41:06
