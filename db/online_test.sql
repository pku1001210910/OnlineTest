CREATE DATABASE  IF NOT EXISTS `online_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `online_test`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: online_test
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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `content` longtext,
  `createDate` datetime DEFAULT NULL,
  `submitter` text,
  `title` text,
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `feedbackId` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `quizId` int(11) NOT NULL,
  `scoreFrom` double DEFAULT NULL,
  `scoreTo` double DEFAULT NULL,
  PRIMARY KEY (`feedbackId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz` (
  `quizId` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `description` longtext,
  `needCharge` tinyint(4) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `repeatable` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `submitDate` datetime DEFAULT NULL,
  `title` text,
  PRIMARY KEY (`quizId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_category`
--

DROP TABLE IF EXISTS `quiz_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_category` (
  `categoryId` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` text,
  `description` longtext,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_category`
--

LOCK TABLES `quiz_category` WRITE;
/*!40000 ALTER TABLE `quiz_category` DISABLE KEYS */;
INSERT INTO `quiz_category` VALUES (0,'教育学','分类1',NULL),(1,'社会学','分类2',NULL),(2,'心理学','分类3',NULL);
/*!40000 ALTER TABLE `quiz_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_ownership`
--

DROP TABLE IF EXISTS `quiz_ownership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_ownership` (
  `ownershipId` int(11) NOT NULL AUTO_INCREMENT,
  `buyDate` datetime DEFAULT NULL,
  `expired` tinyint(4) DEFAULT NULL,
  `quizId` int(11) NOT NULL,
  `username` text NOT NULL,
  PRIMARY KEY (`ownershipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_ownership`
--

LOCK TABLES `quiz_ownership` WRITE;
/*!40000 ALTER TABLE `quiz_ownership` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_ownership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_subject`
--

DROP TABLE IF EXISTS `quiz_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_subject` (
  `subjectId` int(11) NOT NULL AUTO_INCREMENT,
  `question` longtext,
  `resourceId` int(11) DEFAULT NULL,
  `subjectOrder` int(11) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `quizId` int(11) NOT NULL,
  PRIMARY KEY (`subjectId`),
  KEY `FK_bg34srr1jub3upbbfkp4h1y3y` (`quizId`),
  CONSTRAINT `FK_bg34srr1jub3upbbfkp4h1y3y` FOREIGN KEY (`quizId`) REFERENCES `quiz` (`quizId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_subject`
--

LOCK TABLES `quiz_subject` WRITE;
/*!40000 ALTER TABLE `quiz_subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_item`
--

DROP TABLE IF EXISTS `subject_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject_item` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `choice` longtext,
  `itemOrder` int(11) DEFAULT NULL,
  `nextSubjectId` int(11) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `subjectId` int(11) NOT NULL,
  PRIMARY KEY (`itemId`),
  KEY `FK_7aesduebjgp6nfm044fk3yhbt` (`subjectId`),
  CONSTRAINT `FK_7aesduebjgp6nfm044fk3yhbt` FOREIGN KEY (`subjectId`) REFERENCES `quiz_subject` (`subjectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_item`
--

LOCK TABLES `subject_item` WRITE;
/*!40000 ALTER TABLE `subject_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userName` varchar(200) NOT NULL,
  `background` longtext,
  `email` text,
  `graduate` text,
  `isAdmin` tinyint(4) DEFAULT NULL,
  `isTeacher` tinyint(4) DEFAULT NULL,
  `major` text,
  `password` varchar(200) DEFAULT NULL,
  `phone` text,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin',NULL,NULL,NULL,1,1,NULL,'admin',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_answer`
--

DROP TABLE IF EXISTS `user_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_answer` (
  `answerId` int(11) NOT NULL AUTO_INCREMENT,
  `answer` text,
  `answerDate` datetime DEFAULT NULL,
  `quizId` int(11) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `subjectId` int(11) NOT NULL,
  `userName` longtext,
  `recordId` int(11) NOT NULL,
  PRIMARY KEY (`answerId`),
  KEY `FK_thhip5pu2br1fl3rf7gmjyhn8` (`recordId`),
  CONSTRAINT `FK_thhip5pu2br1fl3rf7gmjyhn8` FOREIGN KEY (`recordId`) REFERENCES `user_quiz` (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_answer`
--

LOCK TABLES `user_answer` WRITE;
/*!40000 ALTER TABLE `user_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_quiz`
--

DROP TABLE IF EXISTS `user_quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_quiz` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `evaluator` text,
  `feedbackId` int(11) DEFAULT NULL,
  `quizDate` datetime DEFAULT NULL,
  `quizId` int(11) NOT NULL,
  `score` double DEFAULT NULL,
  `userName` text NOT NULL,
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_quiz`
--

LOCK TABLES `user_quiz` WRITE;
/*!40000 ALTER TABLE `user_quiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'online_test'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-30  0:26:18
