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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id_user` binary(16) NOT NULL,
  `age` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `subscription` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `roles_id_rol` int DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FKbdbqy82gqlnn1i27we7t2tk1d` (`roles_id_rol`),
  CONSTRAINT `FKbdbqy82gqlnn1i27we7t2tk1d` FOREIGN KEY (`roles_id_rol`) REFERENCES `roles` (`id_rol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (_binary 'bº\⁄tLGVãäèáÜPÄ¨',22,'juan@gmail.com',_binary '','juan','jesus','$2a$10$qUFd3kJdpXbD4TPIcIdtb.zEjxAEsYo0qjylDfbhchv8.WeIBNUZ6',0,'juan',1),(_binary '©æ\ÎÆCA≥µy2\—`8˚',20,'haner@gmail.com',_binary '','ahumada','haner','$2a$10$ZhCusa6fIKSNyn762dKZBOBsYUoPL3QC14pdwoO0nqUtVsTiwQ3YO',0,'haner',2),(_binary '≤y+ºÉfB\ÌÅTQ\·{zdw',22,'jaime@gmail.com',_binary '','jaime','jaime','$2a$10$tYbsuADYlAUyJ2JHnbqgquao2jVCw2/Q18kuiIGqDxOcIo8jQjOHe',0,'jaime',2),(_binary '\Ê\Ê#.›™G’æL≤Ü\ pÖ',21,'nicolas@gmail.com',_binary '','nicolas','nicolas','$2a$10$JEHjZWi4POkmxiyMLOtO7Orl9p8Lvf/6hLToYUErtrhmtBTq9wuFm',0,'nicolas',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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

-- Dump completed on 2024-02-18 21:41:21
