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
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id_permission` int NOT NULL AUTO_INCREMENT,
  `method` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `permiso_gateway` bit(1) DEFAULT NULL,
  `name_front` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `perfil_item_id` int DEFAULT NULL,
  `url_front` varchar(255) DEFAULT NULL,
  `color_front` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`),
  KEY `FKj5mu7vg9h3pnqudjn0kgtvqa2` (`perfil_item_id`),
  CONSTRAINT `FKj5mu7vg9h3pnqudjn0kgtvqa2` FOREIGN KEY (`perfil_item_id`) REFERENCES `perfiles_items` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'POST','/user/createRole',_binary '\0',NULL,NULL,NULL,NULL,NULL),(2,'POST','/user/createPermissions',_binary '\0',NULL,NULL,NULL,NULL,NULL),(3,'PUT','/addPermission/**/rol/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(4,'POST','/factory/create',_binary '\0',NULL,NULL,NULL,NULL,NULL),(5,'GET','/user',_binary '\0',NULL,NULL,NULL,NULL,NULL),(6,'DELETE','/factory/delete/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(7,'GET','/articulo_id_company/**',_binary '',NULL,NULL,NULL,NULL,NULL),(8,'GET','/articulo_id/**/fact/**',_binary '',NULL,NULL,NULL,NULL,NULL),(9,'POST','/create_art',_binary '',NULL,NULL,NULL,NULL,NULL),(10,'PUT','/user/rol/**/us/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(11,'GET','/factory/getByid/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(12,'GET','/factory/getall',_binary '\0',NULL,NULL,NULL,NULL,NULL),(13,'Patch','/factory/update/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(14,'DELETE','/delete_art/**/factory/**',_binary '',NULL,NULL,NULL,NULL,NULL),(15,'PATCH','/update_art/**',_binary '',NULL,NULL,NULL,NULL,NULL),(16,'PATCH','/articulo-filter/**/name/**',_binary '',NULL,NULL,NULL,NULL,NULL),(17,'POST','/create_vent',_binary '',NULL,NULL,NULL,NULL,NULL),(18,'GET','/vent_all',_binary '',NULL,NULL,NULL,NULL,NULL),(19,'GET','/vent_prod_id/**/empr/**',_binary '',NULL,NULL,NULL,NULL,NULL),(20,'GET','/vent_fact_id/**/empr/**',_binary '',NULL,NULL,NULL,NULL,NULL),(21,'GET','/vent_prod_id_empr/**',_binary '',NULL,NULL,NULL,NULL,NULL),(22,'GET','/vent_id_empresa/**',_binary '',NULL,NULL,NULL,NULL,NULL),(23,'GET','/delete_vent_id_empresa/**/empr/**',_binary '',NULL,NULL,NULL,NULL,NULL),(24,'POST','/create_factory',_binary '',NULL,NULL,NULL,NULL,NULL),(25,'GET','/get_factorys',_binary '',NULL,NULL,NULL,NULL,NULL),(26,'GET','/get_by_id/**',_binary '',NULL,NULL,NULL,NULL,NULL),(27,'PATCH','/update_factory/**',_binary '',NULL,NULL,NULL,NULL,NULL),(28,'PATCH','/delete_factory/**',_binary '',NULL,NULL,NULL,NULL,NULL),(29,'POST','/create_user',_binary '\0',NULL,NULL,NULL,NULL,NULL),(30,'POST','/create_user',_binary '',NULL,NULL,NULL,NULL,NULL),(31,'POST','/addPermission/**/idrol/**',_binary '',NULL,NULL,NULL,NULL,NULL),(32,'POST','/create_role',_binary '','Administral Roles',NULL,4,'/createRole','#5ce374'),(33,'POST','/create_permission',_binary '','Crear Permiso',NULL,4,'/createPermission','#5ce374'),(34,'PUT','/addRol/**/us/**',_binary '',NULL,NULL,NULL,NULL,NULL),(36,'PUT','/user/AddPerfilItemUser/**/Perfil/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(37,'POST','/user/addpermission/**/rol/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(38,'GET','/user/PerfilesUser',_binary '\0',NULL,NULL,NULL,NULL,NULL),(39,'PUT','/addPerfilItemUser/**/idPerfil/**',_binary '',NULL,NULL,NULL,NULL,NULL),(40,'GET','/getPerfiles',_binary '',NULL,NULL,NULL,NULL,NULL),(41,'GET','/user/PermisosPerfilItem/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(42,'GET','/getPermisosPerfil/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(43,'GET','/user/getRoles',_binary '\0',NULL,NULL,NULL,NULL,NULL),(44,'GET','/getRoles',_binary '\0',NULL,NULL,NULL,NULL,NULL),(45,'GET','/user/getPermissionRol/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(46,'GET','/getPermissionRoles/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(47,'GET','/user/getFaltantesRol/**',_binary '\0',NULL,NULL,NULL,NULL,NULL),(48,'GET','/getPermissionRolesFalt/**',_binary '\0',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
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

-- Dump completed on 2024-02-18 21:41:19
