start transaction;
drop database if exists `acmeexplorer`;
create database `acmeexplorer`;
use `acmeexplorer`;
create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';
grant select, insert, update, delete 
	on `acmeexplorer`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `acmeexplorer`.* to 'acme-manager'@'%';
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acmeexplorer
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `actor_folder`
--

DROP TABLE IF EXISTS `actor_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_folder` (
  `Actor_id` int(11) NOT NULL,
  `folders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_dp573h40udupcm5m1kgn2bc2r` (`folders_id`),
  CONSTRAINT `FK_dp573h40udupcm5m1kgn2bc2r` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_folder`
--

LOCK TABLES `actor_folder` WRITE;
/*!40000 ALTER TABLE `actor_folder` DISABLE KEYS */;
INSERT INTO `actor_folder` VALUES (1853,1754),(1853,1755),(1853,1756),(1853,1757),(1853,1758),(1853,1759);
/*!40000 ALTER TABLE `actor_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_socialidentity`
--

DROP TABLE IF EXISTS `actor_socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_socialidentity` (
  `Actor_id` int(11) NOT NULL,
  `socialIdentities_id` int(11) NOT NULL,
  UNIQUE KEY `UK_fvjtbijvsijias3suehxiq0jm` (`socialIdentities_id`),
  CONSTRAINT `FK_fvjtbijvsijias3suehxiq0jm` FOREIGN KEY (`socialIdentities_id`) REFERENCES `socialidentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1853,0,'Address 1','Administrator1@mail.com','Administrator1 name','Phone-1','Administrator1 surname','\0',1594);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `rejectingReason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creditCard_id` int(11) DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_itesolowt3qc6vnn6e7gf6e33` (`creditCard_id`),
  KEY `FK_hpdjjcbp26hxonyuhcrnyemi4` (`explorer_id`),
  KEY `FK_aa8ob0qnub9wp8splrk7qoh0t` (`trip_id`),
  CONSTRAINT `FK_aa8ob0qnub9wp8splrk7qoh0t` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_hpdjjcbp26hxonyuhcrnyemi4` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_itesolowt3qc6vnn6e7gf6e33` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_img3kaubb62u0h0jujkh1tejo` (`userAccount_id`),
  CONSTRAINT `FK_img3kaubb62u0h0jujkh1tejo` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auditrecord`
--

DROP TABLE IF EXISTS `auditrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `draftMode` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `auditor_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_byvesjo01ngtojx6qbba2ayth` (`trip_id`),
  KEY `FK_td4sv8mbdf910amh5qj8kskjv` (`auditor_id`),
  CONSTRAINT `FK_byvesjo01ngtojx6qbba2ayth` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_td4sv8mbdf910amh5qj8kskjv` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auditrecord_attachments`
--

DROP TABLE IF EXISTS `auditrecord_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditrecord_attachments` (
  `AuditRecord_id` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_maqb3v04iw2jvggyf0qyd0dtp` (`AuditRecord_id`),
  CONSTRAINT `FK_maqb3v04iw2jvggyf0qyd0dtp` FOREIGN KEY (`AuditRecord_id`) REFERENCES `auditrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `father_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_k8dpq6k9hyb58e8ma51c57cto` (`father_id`),
  CONSTRAINT `FK_k8dpq6k9hyb58e8ma51c57cto` FOREIGN KEY (`father_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1785,0,'CATEGORY',NULL),(1786,0,'Safari',1785),(1787,0,'Ground',1786),(1788,0,'Savana',1787),(1789,0,'Mountain',1787),(1790,0,'Water',1786),(1791,0,'Lake',1790),(1792,0,'River',1790),(1793,0,'Climbing',1785),(1794,0,'River',1793),(1795,0,'Mountain',1793);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvvCode` int(11) NOT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curricula`
--

DROP TABLE IF EXISTS `curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `personalRecord_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb69tfhsf51vub0mq9n3kyeuy` (`personalRecord_id`),
  UNIQUE KEY `UK_k2avf7gsne221guoys68kviqr` (`ticker`),
  CONSTRAINT `FK_sb69tfhsf51vub0mq9n3kyeuy` FOREIGN KEY (`personalRecord_id`) REFERENCES `personalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curricula_educationrecord`
--

DROP TABLE IF EXISTS `curricula_educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_educationrecord` (
  `Curricula_id` int(11) NOT NULL,
  `educationRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_7svxs7dgerojiaytqcpwbgaq0` (`educationRecords_id`),
  KEY `FK_t1wke0qhch7ranmaupik2aldb` (`Curricula_id`),
  CONSTRAINT `FK_t1wke0qhch7ranmaupik2aldb` FOREIGN KEY (`Curricula_id`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_7svxs7dgerojiaytqcpwbgaq0` FOREIGN KEY (`educationRecords_id`) REFERENCES `educationrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curricula_endorserrecord`
--

DROP TABLE IF EXISTS `curricula_endorserrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_endorserrecord` (
  `Curricula_id` int(11) NOT NULL,
  `endorserRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_exe5kb3j7mnedts3ea6jvcsp7` (`endorserRecords_id`),
  KEY `FK_9t3mmgy847vqc8upl8mv706mt` (`Curricula_id`),
  CONSTRAINT `FK_9t3mmgy847vqc8upl8mv706mt` FOREIGN KEY (`Curricula_id`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_exe5kb3j7mnedts3ea6jvcsp7` FOREIGN KEY (`endorserRecords_id`) REFERENCES `endorserrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curricula_miscellaneousrecord`
--

DROP TABLE IF EXISTS `curricula_miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_miscellaneousrecord` (
  `Curricula_id` int(11) NOT NULL,
  `miscellaneousRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ae8caphsf8j19u8cf6q0l5axy` (`miscellaneousRecords_id`),
  KEY `FK_ivld1qhgtgfa4ij8v1a5xh4jf` (`Curricula_id`),
  CONSTRAINT `FK_ivld1qhgtgfa4ij8v1a5xh4jf` FOREIGN KEY (`Curricula_id`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_ae8caphsf8j19u8cf6q0l5axy` FOREIGN KEY (`miscellaneousRecords_id`) REFERENCES `miscellaneousrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curricula_professionalrecord`
--

DROP TABLE IF EXISTS `curricula_professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_professionalrecord` (
  `Curricula_id` int(11) NOT NULL,
  `professionalRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ec8xu2gjxln8ooeqm0ucekv3g` (`professionalRecords_id`),
  KEY `FK_8i0ncl6lxqwuk1bwkbtwakvja` (`Curricula_id`),
  CONSTRAINT `FK_8i0ncl6lxqwuk1bwkbtwakvja` FOREIGN KEY (`Curricula_id`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_ec8xu2gjxln8ooeqm0ucekv3g` FOREIGN KEY (`professionalRecords_id`) REFERENCES `professionalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `educationrecord`
--

DROP TABLE IF EXISTS `educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educationrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `diploma` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `endorserrecord`
--

DROP TABLE IF EXISTS `endorserrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorserrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullNameEndorser` varchar(255) DEFAULT NULL,
  `linkedInProfile` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `explorer`
--

DROP TABLE IF EXISTS `explorer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `finder_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fjs8q8hlbsegiuxw4r2ko6uv6` (`userAccount_id`),
  KEY `FK_gmhjk9b98bjgtxkh0gmck9ly8` (`finder_id`),
  CONSTRAINT `FK_fjs8q8hlbsegiuxw4r2ko6uv6` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_gmhjk9b98bjgtxkh0gmck9ly8` FOREIGN KEY (`finder_id`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `explorer_contact`
--

DROP TABLE IF EXISTS `explorer_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer_contact` (
  `Explorer_id` int(11) NOT NULL,
  `contacts_id` int(11) NOT NULL,
  KEY `FK_3y1w0b2iyp1wh1d92r98ihphl` (`contacts_id`),
  KEY `FK_rqxiiawi37nkq73w14m2i8x6l` (`Explorer_id`),
  CONSTRAINT `FK_rqxiiawi37nkq73w14m2i8x6l` FOREIGN KEY (`Explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_3y1w0b2iyp1wh1d92r98ihphl` FOREIGN KEY (`contacts_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `explorer_survivalclass`
--

DROP TABLE IF EXISTS `explorer_survivalclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer_survivalclass` (
  `Explorer_id` int(11) NOT NULL,
  `survivalClasses_id` int(11) NOT NULL,
  KEY `FK_iuvwa92show6b2iushl7da7w8` (`survivalClasses_id`),
  KEY `FK_6h3ftl2nj5nhkdulr2lmmnx1w` (`Explorer_id`),
  CONSTRAINT `FK_6h3ftl2nj5nhkdulr2lmmnx1w` FOREIGN KEY (`Explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_iuvwa92show6b2iushl7da7w8` FOREIGN KEY (`survivalClasses_id`) REFERENCES `survivalclass` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `keyWord` varchar(255) DEFAULT NULL,
  `lowerPrice` double DEFAULT NULL,
  `maxPrice` double DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `finder_trip`
--

DROP TABLE IF EXISTS `finder_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_trip` (
  `Finder_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  KEY `FK_banf3cvq0wb1mhnq645alwstq` (`trips_id`),
  KEY `FK_5c35eq4ikvgxcjfyq4g1l0g3e` (`Finder_id`),
  CONSTRAINT `FK_5c35eq4ikvgxcjfyq4g1l0g3e` FOREIGN KEY (`Finder_id`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_banf3cvq0wb1mhnq645alwstq` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `systemFolders` bit(1) DEFAULT NULL,
  `father_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5jo727prvl5w7m8sunooousdr` (`father_id`),
  CONSTRAINT `FK_5jo727prvl5w7m8sunooousdr` FOREIGN KEY (`father_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (1754,0,'Root','',NULL),(1755,0,'Inbox','',1754),(1756,0,'Outbox','',1754),(1757,0,'Notification Box','',1754),(1758,0,'TrashBox','',1754),(1759,0,'Spam','',1754);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_message`
--

DROP TABLE IF EXISTS `folder_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_message` (
  `Folder_id` int(11) NOT NULL,
  `messages_id` int(11) NOT NULL,
  KEY `FK_5nh3mwey9bw25ansh2thcbcdh` (`messages_id`),
  KEY `FK_dwna03p0i8so6ov91ouups81r` (`Folder_id`),
  CONSTRAINT `FK_dwna03p0i8so6ov91ouups81r` FOREIGN KEY (`Folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_5nh3mwey9bw25ansh2thcbcdh` FOREIGN KEY (`messages_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext`
--

DROP TABLE IF EXISTS `legaltext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `draftMode` bit(1) NOT NULL,
  `laws` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message_actor`
--

DROP TABLE IF EXISTS `message_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_actor` (
  `messagesReceived_id` int(11) NOT NULL,
  `actors_id` int(11) NOT NULL,
  KEY `FK_apm75cjw83uf1irk3vn5616eq` (`messagesReceived_id`),
  CONSTRAINT `FK_apm75cjw83uf1irk3vn5616eq` FOREIGN KEY (`messagesReceived_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `miscellaneousrecord`
--

DROP TABLE IF EXISTS `miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneousrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `linkAttachment` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `reply` varchar(255) DEFAULT NULL,
  `replyMoment` datetime DEFAULT NULL,
  `auditor_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c3f5vlsshwqtjo5f74rfs8gm4` (`auditor_id`),
  KEY `FK_phngbfwt9o5v49bpgsb9mkv66` (`trip_id`),
  CONSTRAINT `FK_phngbfwt9o5v49bpgsb9mkv66` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_c3f5vlsshwqtjo5f74rfs8gm4` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personalrecord`
--

DROP TABLE IF EXISTS `personalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `professionalrecord`
--

DROP TABLE IF EXISTS `professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professionalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `nameCompany` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ranger`
--

DROP TABLE IF EXISTS `ranger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranger` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `curricula_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_axw07q0mobub2mvr0xeh7al5f` (`userAccount_id`),
  KEY `FK_99m93vds3skhwcjk7o0bghwt2` (`curricula_id`),
  CONSTRAINT `FK_axw07q0mobub2mvr0xeh7al5f` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_99m93vds3skhwcjk7o0bghwt2` FOREIGN KEY (`curricula_id`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `socialidentity`
--

DROP TABLE IF EXISTS `socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialidentity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `socialNetwork` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_okfx8h0cn4eidh8ng563vowjc` (`userAccount_id`),
  CONSTRAINT `FK_okfx8h0cn4eidh8ng563vowjc` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sponsor_sponsorship`
--

DROP TABLE IF EXISTS `sponsor_sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor_sponsorship` (
  `Sponsor_id` int(11) NOT NULL,
  `sponsorships_id` int(11) NOT NULL,
  UNIQUE KEY `UK_s5eojtknbtryqjv4fo29cwv3c` (`sponsorships_id`),
  KEY `FK_h1yhh2g4hcgaqjxgntq1ggnic` (`Sponsor_id`),
  CONSTRAINT `FK_h1yhh2g4hcgaqjxgntq1ggnic` FOREIGN KEY (`Sponsor_id`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_s5eojtknbtryqjv4fo29cwv3c` FOREIGN KEY (`sponsorships_id`) REFERENCES `sponsorship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `infoPage` varchar(255) DEFAULT NULL,
  `cc_id` int(11) DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ax9fccvagpp6w0mvdfhoo35lw` (`cc_id`),
  KEY `FK_p6ydvo1r10eo68cj18xvbx37q` (`trip_id`),
  CONSTRAINT `FK_p6ydvo1r10eo68cj18xvbx37q` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_ax9fccvagpp6w0mvdfhoo35lw` FOREIGN KEY (`cc_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stage`
--

DROP TABLE IF EXISTS `stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `story`
--

DROP TABLE IF EXISTS `story`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gfxb1n7yg00bo5w5rg2gphd82` (`explorer_id`),
  KEY `FK_h0duh0rohxwpscrd7e89cnsgv` (`trip_id`),
  CONSTRAINT `FK_h0duh0rohxwpscrd7e89cnsgv` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_gfxb1n7yg00bo5w5rg2gphd82` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `story_attachments`
--

DROP TABLE IF EXISTS `story_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_attachments` (
  `Story_id` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_ff4kbl6pm6bat35eamsxb9n51` (`Story_id`),
  CONSTRAINT `FK_ff4kbl6pm6bat35eamsxb9n51` FOREIGN KEY (`Story_id`) REFERENCES `story` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `survivalclass`
--

DROP TABLE IF EXISTS `survivalclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalclass` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hns65r4atubks6n3qea96gh0j` (`location_id`),
  CONSTRAINT `FK_hns65r4atubks6n3qea96gh0j` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `systemconfiguration`
--

DROP TABLE IF EXISTS `systemconfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemconfiguration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `VATTax` double NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `countryCode` varchar(255) DEFAULT NULL,
  `currentDay` date DEFAULT NULL,
  `finderCacheTime` int(11) NOT NULL,
  `finderResultsNumber` int(11) NOT NULL,
  `nextTicker` int(11) DEFAULT NULL,
  `welcomeMessageEnglish` varchar(255) DEFAULT NULL,
  `welcomeMessageSpanish` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemconfiguration`
--

LOCK TABLES `systemconfiguration` WRITE;
/*!40000 ALTER TABLE `systemconfiguration` DISABLE KEYS */;
INSERT INTO `systemconfiguration` VALUES (1612,0,0.21,'http://creek-tours.com/wp-content/uploads/Kenya-Tanzania-Family-Safari-banner.jpg','+34','2018-01-20',1,10,0,'Tanzanika is an adventure company that makes your explorer\'s dreams true','Tanzanika es la empresa de aventuras que hará tus sueños de explorador realidad');
/*!40000 ALTER TABLE `systemconfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemconfiguration_spamwords`
--

DROP TABLE IF EXISTS `systemconfiguration_spamwords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemconfiguration_spamwords` (
  `SystemConfiguration_id` int(11) NOT NULL,
  `spamWords` varchar(255) DEFAULT NULL,
  KEY `FK_ksruab59pw9wxdj0kacp7lbf6` (`SystemConfiguration_id`),
  CONSTRAINT `FK_ksruab59pw9wxdj0kacp7lbf6` FOREIGN KEY (`SystemConfiguration_id`) REFERENCES `systemconfiguration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemconfiguration_spamwords`
--

LOCK TABLES `systemconfiguration_spamwords` WRITE;
/*!40000 ALTER TABLE `systemconfiguration_spamwords` DISABLE KEYS */;
INSERT INTO `systemconfiguration_spamwords` VALUES (1612,'sex'),(1612,'cialis'),(1612,'viagra'),(1612,'jes extender');
/*!40000 ALTER TABLE `systemconfiguration_spamwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemconfiguration_tags`
--

DROP TABLE IF EXISTS `systemconfiguration_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemconfiguration_tags` (
  `SystemConfiguration_id` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FK_nixlwt8k8111xj2p0b2nraews` (`SystemConfiguration_id`),
  CONSTRAINT `FK_nixlwt8k8111xj2p0b2nraews` FOREIGN KEY (`SystemConfiguration_id`) REFERENCES `systemconfiguration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemconfiguration_tags`
--

LOCK TABLES `systemconfiguration_tags` WRITE;
/*!40000 ALTER TABLE `systemconfiguration_tags` DISABLE KEYS */;
INSERT INTO `systemconfiguration_tags` VALUES (1612,'country'),(1612,'expertise'),(1612,'dangerousness');
/*!40000 ALTER TABLE `systemconfiguration_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancellationReason` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `explorerRequirements` varchar(255) DEFAULT NULL,
  `publicationDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `legalText_id` int(11) NOT NULL,
  `manager_id` int(11) NOT NULL,
  `ranger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ugq799eb7dgmgmqjoxkjpx5k` (`ticker`),
  KEY `FK_mdljo2c0aixmrcx025mvfkc3x` (`category_id`),
  KEY `FK_ol2sss4qsbxlb5rhdynrl33wy` (`legalText_id`),
  KEY `FK_bt26lls0o7ykesy9ye75887rk` (`manager_id`),
  KEY `FK_65bd0l9xa0l1x5qnjg2su9tig` (`ranger_id`),
  CONSTRAINT `FK_65bd0l9xa0l1x5qnjg2su9tig` FOREIGN KEY (`ranger_id`) REFERENCES `ranger` (`id`),
  CONSTRAINT `FK_bt26lls0o7ykesy9ye75887rk` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_mdljo2c0aixmrcx025mvfkc3x` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_ol2sss4qsbxlb5rhdynrl33wy` FOREIGN KEY (`legalText_id`) REFERENCES `legaltext` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trip_stage`
--

DROP TABLE IF EXISTS `trip_stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_stage` (
  `Trip_id` int(11) NOT NULL,
  `stages_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pypsdnr343h93d9c7hpugcyyv` (`stages_id`),
  KEY `FK_stea9evs7jmvh3yf9i17pw6kn` (`Trip_id`),
  CONSTRAINT `FK_stea9evs7jmvh3yf9i17pw6kn` FOREIGN KEY (`Trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_pypsdnr343h93d9c7hpugcyyv` FOREIGN KEY (`stages_id`) REFERENCES `stage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trip_survivalclass`
--

DROP TABLE IF EXISTS `trip_survivalclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_survivalclass` (
  `Trip_id` int(11) NOT NULL,
  `survivalClasses_id` int(11) NOT NULL,
  UNIQUE KEY `UK_2yjvhqit6owuqqxi1rgwptvwu` (`survivalClasses_id`),
  KEY `FK_7u56sjo42wxw703upnpc9afve` (`Trip_id`),
  CONSTRAINT `FK_7u56sjo42wxw703upnpc9afve` FOREIGN KEY (`Trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_2yjvhqit6owuqqxi1rgwptvwu` FOREIGN KEY (`survivalClasses_id`) REFERENCES `survivalclass` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trip_tag`
--

DROP TABLE IF EXISTS `trip_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_tag` (
  `Trip_id` int(11) NOT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `tag_KEY` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Trip_id`,`tag_KEY`),
  CONSTRAINT `FK_pq248a257f5kdf6d54enn563f` FOREIGN KEY (`Trip_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `accountNonLocked` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (1594,0,'','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (1594,'ADMINISTRATOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-11 17:20:42
commit;