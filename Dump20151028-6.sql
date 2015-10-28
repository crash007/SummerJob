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
-- Table structure for table `blog_comments`
--

DROP TABLE IF EXISTS `blog_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_comments` (
  `commentID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `added` datetime NOT NULL,
  `updated` datetime DEFAULT NULL,
  `message` text NOT NULL,
  `posterID` int(10) unsigned DEFAULT NULL,
  `editorID` int(10) unsigned DEFAULT NULL,
  `posterName` varchar(255) DEFAULT NULL,
  `posterEmail` varchar(255) DEFAULT NULL,
  `posterWebsite` varchar(255) DEFAULT NULL,
  `postID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`commentID`),
  KEY `FK_comments_1` (`postID`),
  CONSTRAINT `FK_comments_1` FOREIGN KEY (`postID`) REFERENCES `blog_posts` (`postID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `blog_posts`
--

DROP TABLE IF EXISTS `blog_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_posts` (
  `postID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `added` datetime NOT NULL,
  `updated` datetime DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `message` mediumtext NOT NULL,
  `posterID` int(10) unsigned NOT NULL,
  `editorID` int(10) unsigned DEFAULT NULL,
  `readCount` int(10) unsigned NOT NULL DEFAULT '0',
  `split` tinyint(1) NOT NULL,
  `blogID` varchar(255) NOT NULL,
  PRIMARY KEY (`postID`),
  UNIQUE KEY `Index_2` (`alias`,`blogID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `blog_tags`
--

DROP TABLE IF EXISTS `blog_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_tags` (
  `postID` int(10) unsigned NOT NULL,
  `tag` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`postID`,`tag`) USING BTREE,
  CONSTRAINT `FK_tags_1` FOREIGN KEY (`postID`) REFERENCES `blog_posts` (`postID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `e_korren_psi_waste_data`
--

DROP TABLE IF EXISTS `e_korren_psi_waste_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `e_korren_psi_waste_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL DEFAULT '0000-00-00',
  `building` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(255) NOT NULL DEFAULT '',
  `weight` double NOT NULL DEFAULT '0',
  `nyko` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `weight` (`weight`),
  KEY `building` (`building`),
  KEY `type` (`type`),
  KEY `nyko` (`nyko`),
  KEY `date` (`date`),
  KEY `date_id` (`date`,`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hddtemp_drives`
--

DROP TABLE IF EXISTS `hddtemp_drives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hddtemp_drives` (
  `driveID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device` varchar(255) NOT NULL,
  `serverID` int(10) unsigned NOT NULL,
  `maxTemp` int(10) unsigned DEFAULT NULL,
  `minTemp` int(10) unsigned DEFAULT NULL,
  `lastAlarm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`driveID`),
  UNIQUE KEY `Index_3` (`device`,`serverID`),
  KEY `FK_hddtempdrives_1` (`serverID`),
  CONSTRAINT `FK_hddtempdrives_1` FOREIGN KEY (`serverID`) REFERENCES `hddtemp_servers` (`serverID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hddtemp_servers`
--

DROP TABLE IF EXISTS `hddtemp_servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hddtemp_servers` (
  `serverID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `host` varchar(255) NOT NULL,
  `port` varchar(5) NOT NULL,
  `monitor` tinyint(1) NOT NULL,
  `missingDriveWarning` tinyint(1) NOT NULL,
  PRIMARY KEY (`serverID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `minimal_user_attributes`
--

DROP TABLE IF EXISTS `minimal_user_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minimal_user_attributes` (
  `userID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(1024) NOT NULL,
  PRIMARY KEY (`userID`,`name`),
  KEY `Index_2` (`name`),
  CONSTRAINT `FK_minimal_user_attributes_1` FOREIGN KEY (`userID`) REFERENCES `minimal_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `minimal_user_groups`
--

DROP TABLE IF EXISTS `minimal_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minimal_user_groups` (
  `userID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  CONSTRAINT `FK_minimal_user_groups_1` FOREIGN KEY (`userID`) REFERENCES `minimal_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `minimal_users`
--

DROP TABLE IF EXISTS `minimal_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minimal_users` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastlogin` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `language` varchar(76) DEFAULT NULL,
  `preferedDesign` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=2005 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `new_table`
--

DROP TABLE IF EXISTS `new_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `new_table` (
  `date` time NOT NULL,
  `value` double NOT NULL,
  `parameter` varchar(45) NOT NULL,
  `station` varchar(45) NOT NULL,
  PRIMARY KEY (`date`,`parameter`,`station`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_module_aliases`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_backgroundmodulealiases_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_module_attributes`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_attributes` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`moduleID`,`name`),
  CONSTRAINT `FK_openhierarchy_background_module_attributes_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_module_groups`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_backgroundmodulegroups_2` (`groupID`),
  CONSTRAINT `FK_backgroundmodulegroups_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_module_settings`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_backgroundmodulesettings_1` (`moduleID`),
  CONSTRAINT `FK_backgroundmodulesettings_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_module_slots`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_slots` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `slot` varchar(255) NOT NULL,
  PRIMARY KEY (`moduleID`,`slot`),
  CONSTRAINT `FK_backgroundmoduleslots_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_module_users`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_backgroundmoduleusers_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_background_modules`
--

DROP TABLE IF EXISTS `openhierarchy_background_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `xslPath` text,
  `xslPathType` varchar(255) DEFAULT NULL,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `dataSourceID` int(10) unsigned DEFAULT NULL,
  `staticContentPackage` varchar(255) DEFAULT NULL,
  `priority` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`),
  KEY `FK_backgroundmodules_1` (`sectionID`),
  KEY `FK_backgroundmodules_2` (`dataSourceID`),
  CONSTRAINT `FK_backgroundmodules_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_backgroundmodules_2` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_data_sources`
--

DROP TABLE IF EXISTS `openhierarchy_data_sources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_data_sources` (
  `dataSourceID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(45) NOT NULL DEFAULT '',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `driver` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `logAbandoned` tinyint(1) DEFAULT '0',
  `removeAbandoned` tinyint(1) DEFAULT '0',
  `removeTimeout` int(10) unsigned DEFAULT '30',
  `testOnBorrow` tinyint(1) DEFAULT '0',
  `validationQuery` varchar(255) DEFAULT 'SELECT 1',
  `maxActive` int(10) unsigned DEFAULT '30',
  `maxIdle` int(10) unsigned DEFAULT '8',
  `minIdle` int(10) unsigned DEFAULT '0',
  `maxWait` int(10) unsigned DEFAULT '0',
  `defaultCatalog` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dataSourceID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_filter_module_aliases`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_filtermodulealiases_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_filter_module_attributes`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_attributes` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`moduleID`,`name`),
  CONSTRAINT `FK_openhierarchy_filter_module_attributes_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_filter_module_groups`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_filtermodulegroups_2` (`groupID`),
  CONSTRAINT `FK_filtermodulegroups_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_filter_module_settings`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_filtermodulesettings_1` (`moduleID`),
  CONSTRAINT `FK_filtermodulesettings_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_filter_module_users`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_filtermoduleusers_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_filter_modules`
--

DROP TABLE IF EXISTS `openhierarchy_filter_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `dataSourceID` int(10) unsigned DEFAULT NULL,
  `priority` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`),
  KEY `FK_filtermodules_1` (`dataSourceID`),
  CONSTRAINT `FK_filtermodules_1` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_foreground_module_attributes`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_attributes` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`moduleID`,`name`),
  CONSTRAINT `FK_openhierarchy_foreground_module_attributes_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_foreground_module_groups`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_modulegroups_2` (`groupID`),
  CONSTRAINT `FK_modulegroups_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_foreground_module_settings`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_modulesettings_1` (`moduleID`),
  CONSTRAINT `FK_modulesettings_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4714 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_foreground_module_users`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_moduleusers_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_foreground_modules`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `alias` varchar(45) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `description` text NOT NULL,
  `xslPath` text,
  `xslPathType` varchar(255) DEFAULT NULL,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `visibleInMenu` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `dataSourceID` int(10) unsigned DEFAULT NULL,
  `staticContentPackage` varchar(255) DEFAULT NULL,
  `requiredProtocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`moduleID`),
  UNIQUE KEY `Index_3` (`sectionID`,`alias`),
  KEY `FK_modules_1` (`sectionID`),
  KEY `FK_modules_2` (`dataSourceID`),
  CONSTRAINT `FK_modules_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_modules_2` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_menu_index`
--

DROP TABLE IF EXISTS `openhierarchy_menu_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_menu_index` (
  `menuIndexID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `menuIndex` int(10) unsigned NOT NULL DEFAULT '0',
  `moduleID` int(10) unsigned DEFAULT NULL,
  `uniqueID` varchar(255) DEFAULT NULL,
  `subSectionID` int(10) unsigned DEFAULT NULL,
  `menuItemID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`menuIndexID`),
  UNIQUE KEY `UniqueID / ModuleID` (`moduleID`,`uniqueID`,`sectionID`) USING BTREE,
  UNIQUE KEY `Index_5` (`sectionID`,`subSectionID`),
  KEY `FK_menuindex_3` (`subSectionID`),
  KEY `FK_menuindex_4` (`menuItemID`),
  CONSTRAINT `FK_menuindex_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_2` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_3` FOREIGN KEY (`subSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_4` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 206848 kB; (`sectionID`) REFER `foraldramotet-o';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_section_attributes`
--

DROP TABLE IF EXISTS `openhierarchy_section_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_section_attributes` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`sectionID`,`name`),
  CONSTRAINT `FK_openhierarchy_section_attributes_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_section_groups`
--

DROP TABLE IF EXISTS `openhierarchy_section_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_section_groups` (
  `sectionID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`sectionID`,`groupID`),
  CONSTRAINT `FK_sectiongroups_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_section_users`
--

DROP TABLE IF EXISTS `openhierarchy_section_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_section_users` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`sectionID`,`userID`),
  CONSTRAINT `FK_sectionusers_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_sections`
--

DROP TABLE IF EXISTS `openhierarchy_sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_sections` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentSectionID` int(10) unsigned DEFAULT NULL,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `visibleInMenu` tinyint(1) NOT NULL DEFAULT '0',
  `breadCrumb` tinyint(1) DEFAULT '1',
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `anonymousDefaultURI` varchar(255) DEFAULT NULL,
  `userDefaultURI` varchar(255) DEFAULT NULL,
  `requiredProtocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sectionID`),
  UNIQUE KEY `Index_2` (`parentSectionID`,`alias`),
  CONSTRAINT `FK_sections_1` FOREIGN KEY (`parentSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_virtual_menu_item_groups`
--

DROP TABLE IF EXISTS `openhierarchy_virtual_menu_item_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_virtual_menu_item_groups` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`menuItemID`,`groupID`) USING BTREE,
  CONSTRAINT `FK_virtualmenuitemgroups_1` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_virtual_menu_item_users`
--

DROP TABLE IF EXISTS `openhierarchy_virtual_menu_item_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_virtual_menu_item_users` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`menuItemID`,`userID`),
  CONSTRAINT `FK_virtualmenuitemusers_1` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `openhierarchy_virtual_menu_items`
--

DROP TABLE IF EXISTS `openhierarchy_virtual_menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_virtual_menu_items` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `itemtype` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(45) DEFAULT NULL,
  `description` text,
  `url` text,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`menuItemID`) USING BTREE,
  KEY `FK_menuadmin_1` (`sectionID`),
  CONSTRAINT `FK_virtualmenuitems_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page_groups`
--

DROP TABLE IF EXISTS `page_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_groups` (
  `pageID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`pageID`,`groupID`),
  CONSTRAINT `FK_pagegroups_1` FOREIGN KEY (`pageID`) REFERENCES `pages` (`pageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page_users`
--

DROP TABLE IF EXISTS `page_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_users` (
  `pageID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pageID`,`userID`),
  CONSTRAINT `FK_pageusers_1` FOREIGN KEY (`pageID`) REFERENCES `pages` (`pageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pages` (
  `pageID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `text` mediumtext NOT NULL,
  `enabled` varchar(45) NOT NULL DEFAULT '',
  `visibleInMenu` tinyint(1) NOT NULL DEFAULT '0',
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `breadCrumb` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`pageID`),
  UNIQUE KEY `Index_3` (`sectionID`,`alias`),
  KEY `FK_pages_1` (`sectionID`),
  CONSTRAINT `FK_pages_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 191488 kB; (`sectionID`) REFER `fkdb-system/sec';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `simple_group_attributes`
--

DROP TABLE IF EXISTS `simple_group_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_group_attributes` (
  `groupID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`groupID`,`name`),
  CONSTRAINT `FK_simple_group_attributes_1` FOREIGN KEY (`groupID`) REFERENCES `simple_groups` (`groupID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `simple_groups`
--

DROP TABLE IF EXISTS `simple_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_groups` (
  `groupID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`groupID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `simple_user_attributes`
--

DROP TABLE IF EXISTS `simple_user_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_user_attributes` (
  `userID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`userID`,`name`) USING BTREE,
  KEY `Index_2` (`name`),
  CONSTRAINT `FK_simple_user_attributes_1` FOREIGN KEY (`userID`) REFERENCES `simple_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `simple_user_groups`
--

DROP TABLE IF EXISTS `simple_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_user_groups` (
  `userID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  KEY `FK_usergroups_2` (`groupID`),
  CONSTRAINT `FK_usergroups_1` FOREIGN KEY (`userID`) REFERENCES `simple_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `simple_users`
--

DROP TABLE IF EXISTS `simple_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_users` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL DEFAULT '',
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL DEFAULT '',
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastlogin` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `language` varchar(76) DEFAULT NULL,
  `preferedDesign` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COMMENT='Users table;InnoDB free: 274432 kB';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_areas`
--

DROP TABLE IF EXISTS `summer_job_areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_areas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `canBeChosenInApplication` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_business_sector_application`
--

DROP TABLE IF EXISTS `summer_job_business_sector_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_business_sector_application` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` int(10) unsigned NOT NULL,
  `personId` int(10) unsigned NOT NULL,
  `enrolledHighSchool` tinyint(1) unsigned DEFAULT '0',
  `highSchoolProgram` varchar(64) DEFAULT NULL,
  `ranking` int(10) unsigned DEFAULT '0',
  `hasDriversLicense` tinyint(1) unsigned DEFAULT '0',
  `driversLicenseType` varchar(45) DEFAULT NULL,
  `hasAccessToVehicle` tinyint(1) unsigned DEFAULT '0',
  `experienceInCare` tinyint(1) unsigned DEFAULT '0',
  `personalLetter` mediumtext,
  `cvLocation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_summer_job_business_sector_application_1_idx` (`jobId`),
  KEY `fk_summer_job_business_sector_application_2_idx` (`personId`),
  CONSTRAINT `fk_summer_job_business_sector_application_2` FOREIGN KEY (`personId`) REFERENCES `summer_job_business_sector_person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_summer_job_business_sector_application_1` FOREIGN KEY (`jobId`) REFERENCES `summer_job_business_sector_job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_business_sector_job`
--

DROP TABLE IF EXISTS `summer_job_business_sector_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_business_sector_job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` date NOT NULL,
  `updated` date DEFAULT NULL,
  `workplaceId` int(10) unsigned NOT NULL,
  `areaId` int(10) unsigned NOT NULL,
  `workTitle` varchar(255) NOT NULL,
  `workDescription` mediumtext NOT NULL,
  `requirementsFreeText` mediumtext,
  `numberOfWorkersNeeded` int(10) unsigned NOT NULL,
  `approvedWorkplace` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `approvedDate` date DEFAULT NULL,
  `approvedByUser` varchar(45) DEFAULT NULL,
  `manager_id` int(10) unsigned DEFAULT NULL,
  `hasDriversLicense` tinyint(1) unsigned NOT NULL,
  `isOverEighteen` tinyint(1) unsigned NOT NULL,
  `freeTextRequirements` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_summer_job_business_sector_job_1_idx` (`workplaceId`),
  KEY `fk_summer_job_business_sector_job_2_idx` (`areaId`),
  KEY `fk_summer_job_business_sector_job_3_idx` (`manager_id`),
  CONSTRAINT `fk_summer_job_business_sector_job_3` FOREIGN KEY (`manager_id`) REFERENCES `summer_job_business_sector_managers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_summer_job_business_sector_job_1` FOREIGN KEY (`workplaceId`) REFERENCES `summer_job_municipality_workplace` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_summer_job_business_sector_job_2` FOREIGN KEY (`areaId`) REFERENCES `summer_job_areas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_business_sector_managers`
--

DROP TABLE IF EXISTS `summer_job_business_sector_managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_business_sector_managers` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_business_sector_mentors`
--

DROP TABLE IF EXISTS `summer_job_business_sector_mentors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_business_sector_mentors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` int(10) unsigned NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_summer_job_business_sector_mentors_1_idx` (`jobId`),
  CONSTRAINT `fk_summer_job_business_sector_mentors_1` FOREIGN KEY (`jobId`) REFERENCES `summer_job_business_sector_job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_business_sector_person`
--

DROP TABLE IF EXISTS `summer_job_business_sector_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_business_sector_person` (
  `id` int(10) unsigned NOT NULL,
  `socialSecurityNumber` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `streetAddress` varchar(255) NOT NULL,
  `zipCode` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `jobApplicationId` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_summer_job_business_sector_person_1_idx` (`jobApplicationId`),
  CONSTRAINT `fk_summer_job_business_sector_person_1` FOREIGN KEY (`jobApplicationId`) REFERENCES `summer_job_business_sector_application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_business_sector_workplace`
--

DROP TABLE IF EXISTS `summer_job_business_sector_workplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_business_sector_workplace` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `streetAddress` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_municipality_job`
--

DROP TABLE IF EXISTS `summer_job_municipality_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_municipality_job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` date NOT NULL,
  `updated` date DEFAULT NULL,
  `workplaceId` int(10) unsigned NOT NULL,
  `areaId` int(10) unsigned NOT NULL,
  `workTitle` varchar(255) NOT NULL,
  `workDescription` mediumtext NOT NULL,
  `requirementsFreeText` mediumtext,
  `numberOfWorkersNeeded` int(10) unsigned NOT NULL,
  `periodId` int(10) unsigned NOT NULL,
  `approvedWorkplace` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `approvedDate` date DEFAULT NULL,
  `approvedByUser` varchar(45) DEFAULT NULL,
  `manager_id` int(10) unsigned DEFAULT NULL,
  `hasDriversLicense` tinyint(1) unsigned NOT NULL,
  `isOverEighteen` tinyint(1) unsigned NOT NULL,
  `freeTextRequirements` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_municipalityjob_period_idx` (`periodId`),
  KEY `fk_municipalityjob_workplace_idx` (`workplaceId`),
  KEY `fk_municipalityjob_area_idx` (`areaId`),
  KEY `fk_summer_job_municipality_job_1_idx` (`manager_id`),
  CONSTRAINT `fk_summer_job_municipality_job_1` FOREIGN KEY (`manager_id`) REFERENCES `summer_job_municipality_managers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_municipalityjob_area` FOREIGN KEY (`areaId`) REFERENCES `summer_job_areas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_municipalityjob_period` FOREIGN KEY (`periodId`) REFERENCES `summer_job_periods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_municipalityjob_workplace` FOREIGN KEY (`workplaceId`) REFERENCES `summer_job_municipality_workplace` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_municipality_job_application`
--

DROP TABLE IF EXISTS `summer_job_municipality_job_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_municipality_job_application` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` int(10) unsigned NOT NULL,
  `personId` int(10) unsigned NOT NULL,
  `enrolledHighSchool` tinyint(1) unsigned DEFAULT '0',
  `highSchoolProgram` varchar(64) DEFAULT NULL,
  `ranking` int(10) unsigned DEFAULT '0',
  `hasDriversLicense` tinyint(1) unsigned DEFAULT '0',
  `driversLicenseType` varchar(45) DEFAULT NULL,
  `hasAccessToVehicle` tinyint(1) unsigned DEFAULT '0',
  `experienceInCare` tinyint(1) unsigned DEFAULT '0',
  `preferedAreasId` tinyint(1) unsigned NOT NULL,
  `personalLetter` mediumtext,
  `cvLocation` varchar(255) DEFAULT NULL,
  `prefered_area_1` int(10) unsigned NOT NULL,
  `prefered_area_2` int(10) unsigned NOT NULL,
  `prefered_area_3` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_application_municipalityJob_idx` (`jobId`),
  KEY `fk_application_person_idx` (`personId`),
  CONSTRAINT `fk_application_municipalityJob` FOREIGN KEY (`jobId`) REFERENCES `summer_job_municipality_job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_application_person` FOREIGN KEY (`personId`) REFERENCES `summer_job_person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_municipality_managers`
--

DROP TABLE IF EXISTS `summer_job_municipality_managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_municipality_managers` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_municipality_mentors`
--

DROP TABLE IF EXISTS `summer_job_municipality_mentors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_municipality_mentors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `jobId` int(10) unsigned NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mentor_municipality_idx` (`jobId`),
  CONSTRAINT `fk_mentor_municipality` FOREIGN KEY (`jobId`) REFERENCES `summer_job_municipality_job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_municipality_person`
--

DROP TABLE IF EXISTS `summer_job_municipality_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_municipality_person` (
  `id` int(10) unsigned NOT NULL,
  `socialSecurityNumber` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `streetAddress` varchar(255) NOT NULL,
  `zipCode` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `jobApplicationId` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_summer_job_municipality_person_1_idx` (`jobApplicationId`),
  CONSTRAINT `fk_summer_job_municipality_person_1` FOREIGN KEY (`jobApplicationId`) REFERENCES `summer_job_municipality_job_application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_municipality_workplace`
--

DROP TABLE IF EXISTS `summer_job_municipality_workplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_municipality_workplace` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `organization` varchar(255) DEFAULT NULL,
  `administration` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `streetAddress` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summer_job_periods`
--

DROP TABLE IF EXISTS `summer_job_periods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summer_job_periods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `sortIndex` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `table_versions`
--

DROP TABLE IF EXISTS `table_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_versions` (
  `tableGroupName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tableGroupName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_invitation_attributes`
--

DROP TABLE IF EXISTS `user_invitation_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitation_attributes` (
  `invitationID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`invitationID`,`name`) USING BTREE,
  CONSTRAINT `FK_user_invitation_attributes_1` FOREIGN KEY (`invitationID`) REFERENCES `user_invitations` (`invitationID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_invitation_type_groups`
--

DROP TABLE IF EXISTS `user_invitation_type_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitation_type_groups` (
  `invitationTypeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`invitationTypeID`,`groupID`) USING BTREE,
  CONSTRAINT `FK_invitationtypegroups_1` FOREIGN KEY (`invitationTypeID`) REFERENCES `user_invitation_types` (`invitationTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_invitation_types`
--

DROP TABLE IF EXISTS `user_invitation_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitation_types` (
  `invitationTypeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `senderName` varchar(255) NOT NULL,
  `senderEmail` varchar(255) NOT NULL,
  `registrationText` text NOT NULL,
  `registeredText` text NOT NULL,
  PRIMARY KEY (`invitationTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_invitations`
--

DROP TABLE IF EXISTS `user_invitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitations` (
  `invitationID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invitationTypeID` int(10) unsigned NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `linkID` varchar(36) NOT NULL,
  `sendCount` int(10) unsigned NOT NULL,
  `lastSent` datetime DEFAULT NULL,
  PRIMARY KEY (`invitationID`),
  UNIQUE KEY `Index_3` (`email`),
  KEY `FK_Invitations_1` (`invitationTypeID`),
  CONSTRAINT `FK_Invitations_1` FOREIGN KEY (`invitationTypeID`) REFERENCES `user_invitation_types` (`invitationTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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

-- Dump completed on 2015-10-28  9:20:55
