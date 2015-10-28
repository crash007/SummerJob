CREATE DATABASE  IF NOT EXISTS `summer_job` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `summer_job`;
-- MySQL dump 10.13  Distrib 5.5.44, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: summer_job
-- ------------------------------------------------------
-- Server version	5.5.44-0ubuntu0.14.04.1

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
-- Dumping data for table `summer_job_municipality_job_application`
--

LOCK TABLES `summer_job_municipality_job_application` WRITE;
/*!40000 ALTER TABLE `summer_job_municipality_job_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_municipality_job_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_municipality_managers`
--

LOCK TABLES `summer_job_municipality_managers` WRITE;
/*!40000 ALTER TABLE `summer_job_municipality_managers` DISABLE KEYS */;
INSERT INTO `summer_job_municipality_managers` VALUES (13,'el','boss','el@el.el','0703242352435'),(15,'el','boss','el@el.el','0703242352435'),(16,'organisation','organisation','organisation','organisation'),(29,'el','boss','el@el.el','0703242352435'),(30,'organisation','boss','el@el.el','0703242352435'),(31,'organisation','boss','el@el.el','0703242352435'),(32,'organisation','boss','el@el.el','0703242352435'),(33,'organisation','boss','el@el.el','0703242352435'),(34,'organisation','boss','el@el.el','0703242352435'),(35,'organisation','boss','el@el.el','0703242352435'),(36,'organisation','boss','el@el.el','0703242352435'),(37,'organisation','boss','el@el.el','0703242352435'),(38,'organisation','boss','el@el.el','0703242352435'),(39,'el boss','boss','el@el.el','0703242352435'),(40,'el boss','boss','el@el.el','0703242352435'),(41,'el boss','boss','el@el.el','0703242352435'),(42,'el boss','boss','el@el.el','0703242352435'),(43,'el boss','boss','el@el.el','0703242352435'),(44,'el','boss','el@el.el','0703242352435');
/*!40000 ALTER TABLE `summer_job_municipality_managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_municipality_person`
--

LOCK TABLES `summer_job_municipality_person` WRITE;
/*!40000 ALTER TABLE `summer_job_municipality_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_municipality_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_business_sector_application`
--

LOCK TABLES `summer_job_business_sector_application` WRITE;
/*!40000 ALTER TABLE `summer_job_business_sector_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_business_sector_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_areas`
--

LOCK TABLES `summer_job_areas` WRITE;
/*!40000 ALTER TABLE `summer_job_areas` DISABLE KEYS */;
INSERT INTO `summer_job_areas` VALUES (1,'Barnomsorg','Du kan exempelvis arbeta inom förskola, fritids.',1),(2,'Äldreomsorg','Du kan exempelvis arbeta inom hemtjänst, servicehus och äldreboenden.',1),(3,'Omsorg','Du kommer att vara delaktig i den dagliga verksamheten med vuxna eller barn med funktionsnedsättning.',1),(4,'Städ','Du tillhör en lokalvårdsgrupp och kan få göra allt från daglig lokalvård på kontor till grovstädning vid renoveringar.',1),(5,'Kök','Kan innefatta förberedelse, tillredning och paketering av mat inom barnomsorg, äldreomsorg eller storkök. ',1),(6,'Utemiljö','Du arbetar utomhus med exempelvis: gräsklippning, renhållning av parker, skötsel av planteringar, städ/skräpplockning.',1),(7,'Turism','Du kan exempelvis arbeta med guidning, värdskap, marknadsundersökningar, informationsarbete.',1),(8,'Miljöarbete','Kan innebära att ta vattenprover, göra kontroller av livsmedelshantering, mätningar av partiklar i luften.',1),(9,'Vaktmästeri / Fastighetsskötsel / Fordon','Du arbetar med fastighetsskötsel eller fordon. Ange i ditt personliga brev vilken inriktning du helst vill ha. Arbete med fordon kräver B-körkort. Flytt av möbler och annat.',1),(10,'Kommunservice','Detta arbete är mer åt det administrativa hållet och kan bland annat vara på bibliotek, göra inventeringar, marknadsundersökningar, statistik m.m. ',1),(11,'Förening','Ej valbar av ungdom.',0);
/*!40000 ALTER TABLE `summer_job_areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_municipality_job`
--

LOCK TABLES `summer_job_municipality_job` WRITE;
/*!40000 ALTER TABLE `summer_job_municipality_job` DISABLE KEYS */;
INSERT INTO `summer_job_municipality_job` VALUES (11,'2015-10-27',NULL,27,4,'rubrik','skotta snö',NULL,1,6,0,NULL,NULL,13,0,0,NULL),(13,'2015-10-27',NULL,29,3,'Snöskottare','skotta sand',NULL,4,8,0,NULL,NULL,15,0,0,NULL),(14,'2015-10-27',NULL,30,5,'organisation','organisation',NULL,6,8,0,NULL,NULL,16,1,1,NULL),(24,'2015-10-27',NULL,42,5,'organisation','sdfdsfgdfdzgfgs',NULL,6,6,0,NULL,NULL,29,1,1,NULL),(25,'2015-10-27',NULL,43,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,30,1,1,NULL),(26,'2015-10-27',NULL,44,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,31,1,1,NULL),(27,'2015-10-27',NULL,45,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,32,1,1,NULL),(28,'2015-10-27',NULL,46,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,33,1,1,NULL),(30,'2015-10-27',NULL,47,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,34,1,1,NULL),(32,'2015-10-27',NULL,48,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,35,1,1,NULL),(34,'2015-10-27',NULL,49,6,'organisation','fdfgdfgdafg',NULL,8,6,0,NULL,NULL,36,1,1,NULL),(35,'2015-10-27',NULL,49,6,'organisation','fdfgdfgdafg',NULL,8,7,0,NULL,NULL,37,1,1,NULL),(36,'2015-10-27',NULL,49,6,'organisation','fdfgdfgdafg',NULL,8,8,0,NULL,NULL,38,1,1,NULL),(37,'2015-10-27',NULL,50,5,'organisation','beskrivning',NULL,5,6,0,NULL,NULL,39,1,1,NULL),(38,'2015-10-27',NULL,50,5,'organisation','beskrivning',NULL,5,7,0,NULL,NULL,40,1,1,NULL),(39,'2015-10-27',NULL,50,5,'organisation','beskrivning',NULL,5,8,0,NULL,NULL,41,1,1,NULL),(40,'2015-10-27',NULL,51,5,'organisation','beskrivning',NULL,5,6,0,NULL,NULL,42,1,1,NULL),(42,'2015-10-27',NULL,52,5,'organisation','beskrivning',NULL,5,6,0,NULL,NULL,43,1,1,NULL),(44,'2015-10-27',NULL,53,2,'skotta snö','sdfsdfasdfasdf',NULL,6,6,0,NULL,NULL,44,1,1,NULL);
/*!40000 ALTER TABLE `summer_job_municipality_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_periods`
--

LOCK TABLES `summer_job_periods` WRITE;
/*!40000 ALTER TABLE `summer_job_periods` DISABLE KEYS */;
INSERT INTO `summer_job_periods` VALUES (6,'Period1','2015-01-23','2015-10-23',NULL),(7,'Period2','2015-10-23','2016-08-29',NULL),(8,'Period 3','2016-06-23','2016-07-15',NULL);
/*!40000 ALTER TABLE `summer_job_periods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_business_sector_job`
--

LOCK TABLES `summer_job_business_sector_job` WRITE;
/*!40000 ALTER TABLE `summer_job_business_sector_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_business_sector_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_business_sector_person`
--

LOCK TABLES `summer_job_business_sector_person` WRITE;
/*!40000 ALTER TABLE `summer_job_business_sector_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_business_sector_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_municipality_mentors`
--

LOCK TABLES `summer_job_municipality_mentors` WRITE;
/*!40000 ALTER TABLE `summer_job_municipality_mentors` DISABLE KEYS */;
INSERT INTO `summer_job_municipality_mentors` VALUES (10,11,'hand1','hand efter','ee','3345'),(11,11,'hand2','hand efter2','ee222','345346457567'),(14,13,'fdsgdfg','dfgdfsg','dfg@sdfsdf.com','dsfg'),(15,14,'organisation','organisation','organisation','organisation'),(20,24,'sdfdfghgfd','hgfd','',''),(21,25,'dfdsfg','dfg','','534653465'),(22,26,'','','',''),(23,27,'','','',''),(24,28,'','','',''),(25,30,'','','',''),(26,32,'','','',''),(27,34,'','','',''),(28,35,'','','',''),(29,36,'','','',''),(30,37,'gfhfgh','fghfghhfsg','','34456456'),(31,38,'gfhfgh','fghfghhfsg','','34456456'),(32,39,'gfhfgh','fghfghhfsg','','34456456'),(33,40,'gfhfgh','fghfghhfsg','','34456456'),(34,42,'gfhfgh','fghfghhfsg','','34456456'),(35,44,'Förnamn','Efternamn','','3445457');
/*!40000 ALTER TABLE `summer_job_municipality_mentors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_business_sector_mentors`
--

LOCK TABLES `summer_job_business_sector_mentors` WRITE;
/*!40000 ALTER TABLE `summer_job_business_sector_mentors` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_business_sector_mentors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_business_sector_managers`
--

LOCK TABLES `summer_job_business_sector_managers` WRITE;
/*!40000 ALTER TABLE `summer_job_business_sector_managers` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_business_sector_managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_business_sector_workplace`
--

LOCK TABLES `summer_job_business_sector_workplace` WRITE;
/*!40000 ALTER TABLE `summer_job_business_sector_workplace` DISABLE KEYS */;
/*!40000 ALTER TABLE `summer_job_business_sector_workplace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `summer_job_municipality_workplace`
--

LOCK TABLES `summer_job_municipality_workplace` WRITE;
/*!40000 ALTER TABLE `summer_job_municipality_workplace` DISABLE KEYS */;
INSERT INTO `summer_job_municipality_workplace` VALUES (27,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','Sundsvall'),(29,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','Sundsvall'),(30,'organisation','organisation',NULL,'organisation','organisation','organisation','organisation'),(42,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','organisation'),(43,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(44,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(45,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(46,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(47,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(48,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(49,'sdfsfd','organisation',NULL,'avdelning xyz','organisation','organisation','Sundsvall'),(50,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','Sundsvall'),(51,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','Sundsvall'),(52,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','Sundsvall'),(53,'Sundsvalls elnät','El forvaltning',NULL,'avdelning xyz','streeet 4','2343245345','Sundsvall');
/*!40000 ALTER TABLE `summer_job_municipality_workplace` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-28  9:11:10
