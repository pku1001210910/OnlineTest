-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: online_test
-- ------------------------------------------------------
-- Server version	5.7.10

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
  `title` varchar(50) DEFAULT NULL,
  `content` text,
  `createDate` datetime DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `submitter` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`articleId`),
  UNIQUE KEY `articleId_UNIQUE` (`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `feedbackId` int(11) NOT NULL AUTO_INCREMENT,
  `quizId` int(11) NOT NULL,
  `content` text,
  `scoreFrom` double DEFAULT NULL,
  `scoreTo` double DEFAULT NULL,
  PRIMARY KEY (`feedbackId`),
  UNIQUE KEY `feedbackId_UNIQUE` (`feedbackId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz` (
  `quizId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `category` int(11) DEFAULT NULL,
  `needCharge` tinyint(4) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `submitDate` datetime DEFAULT NULL,
  `repeatable` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`quizId`),
  UNIQUE KEY `quizId_UNIQUE` (`quizId`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quiz_category`
--

DROP TABLE IF EXISTS `quiz_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_category` (
  `categoryId` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(50) DEFAULT NULL,
  `description` text,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `categoryId_UNIQUE` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quiz_ownership`
--

DROP TABLE IF EXISTS `quiz_ownership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_ownership` (
  `ownershipId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `quizId` int(11) NOT NULL,
  `expired` tinyint(4) DEFAULT NULL,
  `buyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`ownershipId`),
  UNIQUE KEY `ownershipId_UNIQUE` (`ownershipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quiz_subject`
--

DROP TABLE IF EXISTS `quiz_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz_subject` (
  `subjectId` int(11) NOT NULL AUTO_INCREMENT,
  `quizId` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `resourceId` int(11) DEFAULT NULL,
  `subjectOrder` int(11) DEFAULT NULL,
  `question` text,
  PRIMARY KEY (`subjectId`),
  UNIQUE KEY `subjectId_UNIQUE` (`subjectId`),
  KEY `subject_quizId` (`quizId`),
  CONSTRAINT `subject_quizId` FOREIGN KEY (`quizId`) REFERENCES `quiz` (`quizId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subject_item`
--

DROP TABLE IF EXISTS `subject_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject_item` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `subjectId` int(11) NOT NULL,
  `choice` text,
  `score` double DEFAULT NULL,
  `nextSubjectId` int(11) DEFAULT NULL,
  `itemOrder` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemId`),
  UNIQUE KEY `itemId_UNIQUE` (`itemId`),
  KEY `subjectId_idx` (`subjectId`),
  CONSTRAINT `subjectId` FOREIGN KEY (`subjectId`) REFERENCES `quiz_subject` (`subjectId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `graduate` varchar(50) DEFAULT NULL,
  `major` varchar(50) DEFAULT NULL,
  `background` text,
  `isAdmin` tinyint(4) DEFAULT NULL,
  `isTeacher` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`userName`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
insert into user values('admin', 'test', 'admin@163.com', '13312312312', 'peking university', 'software engineering', '', 1, 0);

--
-- Table structure for table `user_answer`
--

DROP TABLE IF EXISTS `user_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_answer` (
  `answerId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) DEFAULT NULL,
  `quizId` int(11) DEFAULT NULL,
  `subjectId` int(11) NOT NULL,
  `answer` text,
  `answerDate` datetime DEFAULT NULL,
  `score` double DEFAULT NULL,
  `recordId` int(11) NOT NULL,
  PRIMARY KEY (`answerId`),
  UNIQUE KEY `answerId_UNIQUE` (`answerId`),
  KEY `recordId_idx` (`recordId`),
  CONSTRAINT `recordId` FOREIGN KEY (`recordId`) REFERENCES `user_quiz` (`recordId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_quiz`
--

DROP TABLE IF EXISTS `user_quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_quiz` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `quizId` int(11) NOT NULL,
  `score` double DEFAULT NULL,
  `feedbackId` int(11) DEFAULT NULL,
  `quizDate` datetime DEFAULT NULL,
  `evaluator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`recordId`),
  UNIQUE KEY `recordId_UNIQUE` (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-19 21:41:44
