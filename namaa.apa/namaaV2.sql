CREATE DATABASE  IF NOT EXISTS `namaa` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `namaa`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 127.0.0.1    Database: namaa
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('00000000000001','jhipster','classpath:config/liquibase/changelog/00000000000000_initial_schema.xml','2015-12-23 00:38:37',1,'EXECUTED','7:6f8b16e01166e5004d9909209c06b873','createTable, createIndex (x2), createTable (x2), addPrimaryKey, createTable, addForeignKeyConstraint (x3), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('20150826173318','jhipster','classpath:config/liquibase/changelog/20150826173318_added_entity_Fruit.xml','2015-12-23 00:38:37',2,'EXECUTED','7:bd952e644608cc6adcaaf3c10df44d19','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('20150827090653','jhipster','classpath:config/liquibase/changelog/20150827090653_added_entity_Olive.xml','2015-12-23 00:38:37',3,'EXECUTED','7:631124d42b30093d614660cb11da91f5','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('20150827090823','jhipster','classpath:config/liquibase/changelog/20150827090823_added_entity_Oil.xml','2015-12-23 00:38:37',4,'EXECUTED','7:f67748b42785e461ea0c10c8441901ea','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('20150827091018','jhipster','classpath:config/liquibase/changelog/20150827091018_added_entity_GrainType1.xml','2015-12-23 00:38:37',5,'EXECUTED','7:32c000007f7f2465b60bdab741f8a4cf','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('20150827091131','jhipster','classpath:config/liquibase/changelog/20150827091131_added_entity_GrainType2.xml','2015-12-23 00:38:37',6,'EXECUTED','7:4bc7d1eac98836d10e17a062f319f4e2','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('20150827091259','jhipster','classpath:config/liquibase/changelog/20150827091259_added_entity_GrainType3.xml','2015-12-23 00:38:37',7,'EXECUTED','7:22df7fa00448608f139920323cd98a0b','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,'\0',NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FRUIT`
--

DROP TABLE IF EXISTS `FRUIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRUIT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grapes` double DEFAULT NULL,
  `dates` double DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `watered` double DEFAULT NULL,
  `nonWatered` double DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fruit_owner_id` (`owner_id`),
  CONSTRAINT `fk_fruit_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FRUIT`
--

LOCK TABLES `FRUIT` WRITE;
/*!40000 ALTER TABLE `FRUIT` DISABLE KEYS */;
/*!40000 ALTER TABLE `FRUIT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GRAINTYPE1`
--

DROP TABLE IF EXISTS `GRAINTYPE1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GRAINTYPE1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spelt` double DEFAULT NULL,
  `corn` double DEFAULT NULL,
  `millet` double DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `watered` double DEFAULT NULL,
  `nonWatered` double DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_graintype1_owner_id` (`owner_id`),
  CONSTRAINT `fk_graintype1_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GRAINTYPE1`
--

LOCK TABLES `GRAINTYPE1` WRITE;
/*!40000 ALTER TABLE `GRAINTYPE1` DISABLE KEYS */;
/*!40000 ALTER TABLE `GRAINTYPE1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GRAINTYPE2`
--

DROP TABLE IF EXISTS `GRAINTYPE2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GRAINTYPE2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wheat` double DEFAULT NULL,
  `barley` double DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `watered` double DEFAULT NULL,
  `nonWatered` double DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_graintype2_owner_id` (`owner_id`),
  CONSTRAINT `fk_graintype2_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GRAINTYPE2`
--

LOCK TABLES `GRAINTYPE2` WRITE;
/*!40000 ALTER TABLE `GRAINTYPE2` DISABLE KEYS */;
/*!40000 ALTER TABLE `GRAINTYPE2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GRAINTYPE3`
--

DROP TABLE IF EXISTS `GRAINTYPE3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GRAINTYPE3` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chickpeas` double DEFAULT NULL,
  `beans` double DEFAULT NULL,
  `cowpea` double DEFAULT NULL,
  `lathyrus` double DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `watered` double DEFAULT NULL,
  `nonWatered` double DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_graintype3_owner_id` (`owner_id`),
  CONSTRAINT `fk_graintype3_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GRAINTYPE3`
--

LOCK TABLES `GRAINTYPE3` WRITE;
/*!40000 ALTER TABLE `GRAINTYPE3` DISABLE KEYS */;
/*!40000 ALTER TABLE `GRAINTYPE3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JHI_AUTHORITY`
--

DROP TABLE IF EXISTS `JHI_AUTHORITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JHI_AUTHORITY` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JHI_AUTHORITY`
--

LOCK TABLES `JHI_AUTHORITY` WRITE;
/*!40000 ALTER TABLE `JHI_AUTHORITY` DISABLE KEYS */;
INSERT INTO `JHI_AUTHORITY` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `JHI_AUTHORITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JHI_PERSISTENT_AUDIT_EVENT`
--

DROP TABLE IF EXISTS `JHI_PERSISTENT_AUDIT_EVENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JHI_PERSISTENT_AUDIT_EVENT` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(255) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JHI_PERSISTENT_AUDIT_EVENT`
--

LOCK TABLES `JHI_PERSISTENT_AUDIT_EVENT` WRITE;
/*!40000 ALTER TABLE `JHI_PERSISTENT_AUDIT_EVENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `JHI_PERSISTENT_AUDIT_EVENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JHI_PERSISTENT_AUDIT_EVT_DATA`
--

DROP TABLE IF EXISTS `JHI_PERSISTENT_AUDIT_EVT_DATA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JHI_PERSISTENT_AUDIT_EVT_DATA` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `JHI_PERSISTENT_AUDIT_EVENT` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JHI_PERSISTENT_AUDIT_EVT_DATA`
--

LOCK TABLES `JHI_PERSISTENT_AUDIT_EVT_DATA` WRITE;
/*!40000 ALTER TABLE `JHI_PERSISTENT_AUDIT_EVT_DATA` DISABLE KEYS */;
/*!40000 ALTER TABLE `JHI_PERSISTENT_AUDIT_EVT_DATA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JHI_PERSISTENT_TOKEN`
--

DROP TABLE IF EXISTS `JHI_PERSISTENT_TOKEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JHI_PERSISTENT_TOKEN` (
  `series` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `token_value` varchar(255) NOT NULL,
  `token_date` date DEFAULT NULL,
  `ip_address` varchar(39) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`),
  KEY `fk_user_persistent_token` (`user_id`),
  CONSTRAINT `fk_user_persistent_token` FOREIGN KEY (`user_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JHI_PERSISTENT_TOKEN`
--

LOCK TABLES `JHI_PERSISTENT_TOKEN` WRITE;
/*!40000 ALTER TABLE `JHI_PERSISTENT_TOKEN` DISABLE KEYS */;
/*!40000 ALTER TABLE `JHI_PERSISTENT_TOKEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JHI_USER`
--

DROP TABLE IF EXISTS `JHI_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JHI_USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `PASSWORD` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JHI_USER`
--

LOCK TABLES `JHI_USER` WRITE;
/*!40000 ALTER TABLE `JHI_USER` DISABLE KEYS */;
INSERT INTO `JHI_USER` VALUES (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','','en',NULL,NULL,'system','2015-12-22 23:38:37',NULL,NULL,NULL),(2,'anonymousUser','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','','en',NULL,NULL,'system','2015-12-22 23:38:37',NULL,NULL,NULL),(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','','en',NULL,NULL,'system','2015-12-22 23:38:37',NULL,NULL,NULL),(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','','en',NULL,NULL,'system','2015-12-22 23:38:37',NULL,NULL,NULL);
/*!40000 ALTER TABLE `JHI_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JHI_USER_AUTHORITY`
--

DROP TABLE IF EXISTS `JHI_USER_AUTHORITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JHI_USER_AUTHORITY` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `JHI_AUTHORITY` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JHI_USER_AUTHORITY`
--

LOCK TABLES `JHI_USER_AUTHORITY` WRITE;
/*!40000 ALTER TABLE `JHI_USER_AUTHORITY` DISABLE KEYS */;
INSERT INTO `JHI_USER_AUTHORITY` VALUES (1,'ROLE_ADMIN'),(3,'ROLE_ADMIN'),(1,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER');
/*!40000 ALTER TABLE `JHI_USER_AUTHORITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OIL`
--

DROP TABLE IF EXISTS `OIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OIL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sesame` double DEFAULT NULL,
  `safflower` double DEFAULT NULL,
  `radish` double DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `watered` double DEFAULT NULL,
  `nonWatered` double DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_oil_owner_id` (`owner_id`),
  CONSTRAINT `fk_oil_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OIL`
--

LOCK TABLES `OIL` WRITE;
/*!40000 ALTER TABLE `OIL` DISABLE KEYS */;
/*!40000 ALTER TABLE `OIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OLIVE`
--

DROP TABLE IF EXISTS `OLIVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OLIVE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `olive_oil` double DEFAULT NULL,
  `dateAdded` datetime DEFAULT NULL,
  `watered` double DEFAULT NULL,
  `nonWatered` double DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_olive_owner_id` (`owner_id`),
  CONSTRAINT `fk_olive_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `JHI_USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OLIVE`
--

LOCK TABLES `OLIVE` WRITE;
/*!40000 ALTER TABLE `OLIVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `OLIVE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-23  0:53:58
