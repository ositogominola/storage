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
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id_permission` int NOT NULL AUTO_INCREMENT,
  `method` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `perfil_item_id` int DEFAULT NULL,
  `permiso_requerido` bit(1) DEFAULT NULL,
  `pert_recurso` bit(1) DEFAULT NULL,
  `recurso_id` int DEFAULT NULL,
  `url_ignore` bit(1) DEFAULT NULL,
  `grupo_permiso` int DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`),
  KEY `FKj5mu7vg9h3pnqudjn0kgtvqa2` (`perfil_item_id`),
  KEY `FKa68vxb559hi2sp5maswx73kwr` (`recurso_id`),
  CONSTRAINT `FKa68vxb559hi2sp5maswx73kwr` FOREIGN KEY (`recurso_id`) REFERENCES `recursos` (`id_recurso`),
  CONSTRAINT `FKj5mu7vg9h3pnqudjn0kgtvqa2` FOREIGN KEY (`perfil_item_id`) REFERENCES `perfiles_items` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'POST','/user/createRol',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(2,'POST','/user/createPermissions',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(3,'POST','/addPermission/**/rol/**',NULL,_binary '\0',_binary '',1,_binary '\0',6,'Añadir Permiso'),(4,'POST','/factory/create',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(5,'GET','/user',NULL,_binary '',_binary '\0',NULL,_binary '\0',NULL,NULL),(6,'DELETE','/factory/delete/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(7,'GET','/articulo_id_company/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(8,'GET','/articulo_id/**/fact/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(9,'POST','/create_art',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(10,'PUT','/user/rol/**/us/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(11,'GET','/factory/getByid/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(12,'GET','/factory/getall',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(13,'PATCH','/factory/update/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(14,'DELETE','/delete_art/**/factory/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(15,'PATCH','/update_art/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(16,'PATCH','/articulo-filter/**/name/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(17,'POST','/create_vent',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(18,'GET','/vent_all',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(19,'GET','/vent_prod_id/**/empr/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(20,'GET','/vent_fact_id/**/empr/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(21,'GET','/vent_prod_id_empr/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(22,'GET','/vent_id_empresa/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(23,'GET','/delete_vent_id_empresa/**/empr/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(24,'POST','/create_factory',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(25,'GET','/get_factorys',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(26,'GET','/get_by_id/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(27,'PATCH','/update_factory/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(28,'PATCH','/delete_factory/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(29,'POST','/create_user',NULL,_binary '\0',_binary '\0',NULL,_binary '',NULL,NULL),(31,'POST','/addPermission/**/idrol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(32,'POST','/create_role',NULL,_binary '\0',_binary '',1,_binary '\0',1,'Crear rol'),(33,'POST','/create_permission',4,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(34,'PUT','/addRol/**/us/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(36,'PUT','/user/AddPerfilItemUser/**/Perfil/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(37,'POST','/user/addpermission/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(38,'GET','/user/PerfilesUser',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(39,'PUT','/addPerfilItemUser/**/idPerfil/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(40,'GET','/getPerfiles',NULL,_binary '',_binary '\0',NULL,_binary '\0',NULL,NULL),(41,'GET','/user/PermisosPerfilItem/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(42,'GET','/getRecursosPerfil/**/rol/**',NULL,_binary '',_binary '\0',NULL,_binary '\0',NULL,NULL),(43,'GET','/user/getRoles',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(44,'GET','/getRoles',NULL,_binary '\0',_binary '',1,_binary '\0',8,'Visualizar Datos'),(46,'GET','/getPerfilesRoles/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',8,NULL),(47,'GET','/user/getFaltantesRol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(48,'GET','/getRecursosRolesFalt/**/perfil/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',5,NULL),(49,'DELETE','/user/deletePermiso/**/rol/**/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(50,'DELETE','/deletePermisoRol/**/rol/**',NULL,_binary '\0',_binary '',1,_binary '\0',7,'Eliminar Permiso'),(51,'GET','/user/getAllPermission',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(52,'GET','/getAllPermission',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',10,NULL),(55,'DELETE','/user/deletePermision/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(56,'DELETE','/deletePermission/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(57,'GET','/user/getAllUsers',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(58,'GET','/getAllUsers',NULL,_binary '\0',_binary '',4,_binary '\0',1,'Ver Usuarios'),(63,'POST','/login',NULL,_binary '\0',_binary '\0',NULL,_binary '',NULL,NULL),(64,'POST','/user/create',NULL,_binary '\0',_binary '\0',NULL,_binary '',NULL,NULL),(65,'GET','/user/getRecusosPerfilItem/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(66,'GET','/getRecursosPerfilItem/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',8,NULL),(67,'GET','/user/getRecusosPermisos/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(68,'GET','/getRecursosPermisos/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',8,NULL),(70,'GET','/getPerfilesRolFaltante/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',2,NULL),(71,'POST','/user/addPerfilRol/**/perfil/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(72,'POST','/addPerfilRol/**/perfil/**',NULL,_binary '\0',_binary '',1,_binary '\0',2,'Añadir Perfil a Rol'),(73,'DELETE','/user/deleteRol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',NULL,NULL),(74,'DELETE','/deleteRol/**',NULL,_binary '\0',_binary '',1,_binary '\0',3,'Eliminar Rol'),(75,'DELETE','/deletePerfilRol/**/rol/**',NULL,_binary '\0',_binary '',1,_binary '\0',4,'Eliminar perfil'),(76,'POST','/addRecursoRol/**/rol/**',NULL,_binary '\0',_binary '',1,_binary '\0',5,'Añadir Recursos'),(77,'GET','/getPermisosRecursoRolFaltante/**/rol/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',6,NULL),(78,'DELETE','/deleteRecursosRol/**/rol/**',NULL,_binary '\0',_binary '',1,_binary '\0',9,'Eliminar Recurso'),(79,'POST','/getPermisoRecursos/rol/**',NULL,_binary '',_binary '\0',NULL,_binary '\0',NULL,NULL),(80,'GET','/getAllPerfiles',NULL,_binary '\0',_binary '',2,_binary '\0',10,'Visualizar Datos'),(81,'POST','/getRutas/**',NULL,_binary '',_binary '\0',NULL,_binary '\0',NULL,NULL),(82,'GET','/getAllPerfilesItemsByPerfil/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',10,NULL),(83,'GET','/getRecursosByPerfilItem/**',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',10,NULL),(84,'PUT','/updatePermission',NULL,_binary '\0',_binary '',2,_binary '\0',11,'Actualizar Permisos'),(85,'GET','/getAllRecursos',NULL,_binary '\0',_binary '\0',NULL,_binary '\0',11,''),(101,'POST','/createPeril',NULL,_binary '\0',_binary '',2,_binary '\0',12,'Crear Perfil'),(102,'PUT','/updatePerfil',NULL,_binary '\0',_binary '',2,_binary '\0',12,'Actualizar Perfil');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
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
