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
-- Table structure for table `factory`
--

DROP TABLE IF EXISTS `factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory` (
  `id` binary(16) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28hj2as22s8rxcb7d66j15pgj` (`user_id`),
  CONSTRAINT `FK28hj2as22s8rxcb7d66j15pgj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory`
--

LOCK TABLES `factory` WRITE;
/*!40000 ALTER TABLE `factory` DISABLE KEYS */;
INSERT INTO `factory` VALUES (_binary '\n\Ô…ç\€nLb∏ˆˆô¸±õò','32323','h.jpg','tienda de ropa 2.0',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary 'Cö\€_\ËDQ∂—é≤<\¬\Ê','32323','h.jpg','tienda de ropa',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary '≤1U\‡\…IˆøGD\√cê:','32323','h.jpg','tienda de ropa',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary '∏\n[>\⁄oK∏ã\Àu\\_}','32323','h.jpg','tienda de ropa',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary '\ CóS=@%≥JÅÉ\ÈC&','32323','h.jpg','tienda de ropa 2.0',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary 'Œ¢ìÖ>ICàa&œÇm≠','cll 1 b bis n 6-02','C:\\fakepath\\hongo-1.jpg','carniceria de lujo',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary '\Í06EI≈¶©≥8Ñº\…\›','32323','h.jpg','tienda de ropa 2.0',_binary 'bº\⁄tLGVãäèáÜPÄ¨'),(_binary 'Ù\Î}\Â\‡\ÁLÇ˙\«`\ÈÇc','cll 1 b bis #6-02','C:\\fakepath\\prs-12.jpg','pollos',_binary 'bº\⁄tLGVãäèáÜPÄ¨');
/*!40000 ALTER TABLE `factory` ENABLE KEYS */;
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

-- Dump completed on 2024-02-18 21:41:10
