CREATE DATABASE  IF NOT EXISTS `summer_job` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `summer_job`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: summer_job
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `summer_job_driverslicensetype`
--

DROP TABLE IF EXISTS `summer_job_driverslicensetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_driverslicensetype` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `summer_job_driverslicensetype`
--

LOCK TABLES `summer_job_driverslicensetype` WRITE;
/*!40000 ALTER TABLE `summer_job_driverslicensetype` DISABLE KEYS */;
INSERT INTO `summer_job_driverslicensetype` VALUES (1,'AM','Moped klass 1'),(2,'A1','Lätt motorcykel'),(3,'A2','Mellanstor motorcykel'),(4,'A','Tung motorcykel'),(5,'B','Personbil och lätt lastbil'),(6,'BE','Personbil med tungt släp'),(7,'C1/C1E','Medeltung lastbil'),(8,'C/CE','Tung lastbil'),(9,'D1/D1E','Mellanstor buss'),(10,'D/DE','Buss');
/*!40000 ALTER TABLE `summer_job_driverslicensetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'summer_job'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-28 10:21:22
