-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: db_titactoe
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `roomid` int(11) NOT NULL AUTO_INCREMENT,
  `roomname` varchar(45) DEFAULT NULL,
  `creater_id` varchar(45) DEFAULT NULL,
  `creater_name` varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `joiner_id` varchar(45) DEFAULT NULL,
  `joiner_name` varchar(45) DEFAULT NULL,
  `join_date` datetime DEFAULT NULL,
  `data` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`roomid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'111111','13','111111','2019-06-14 15:30:49','111111','111111','2019-06-14 17:27:05',NULL),(2,'111111','13','111111','2019-06-14 15:47:59','111111','111111','2019-06-14 16:31:09',NULL);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) CHARACTER SET utf8 NOT NULL,
  `created_date` datetime NOT NULL,
  `token` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `point_vote` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test user 1','2019-01-02 00:00:00','adakdkdalfa',100),(2,'test name 222','2019-01-22 00:00:00','TUt5WEkyQUdGR01XbngxNEd5QVdwMHlmRndNdm95YjFGSmNpbkl5S29VcVpveU1pSndPUFpUSWdBR1d5SG1JM0pKNWluSGtRRnlBdVp4NGxKeXVYclJ5ZG8zY0JuelowRzBFTVowNVJHS3VaRDBjRU1TdVhxMklTSno5eW5IeDJGSjVCckpBVUJLTXZveFJrTDB1T1pKQUhMbVdCSVRrM0dHQUJxSDVSTVV1QloweG1HS2NTWkpBS0FVeVdvd045',100),(3,'test name 222','2019-06-11 00:00:00','TUt5WEkyQUdGR01XbngxNEd5QVdwMHlmRndNdm95YjFGSmNpbkl5S29VcVpveU1pSndPUFpUSWdBR1d5SG1JM0pKNWluSGtRRnlBdVp4NGxKeXVYclJ5ZG8zY0JuelowRzBFTVowNVJHS3VaRDBjRU1TdVhxMklTSno5eW5IeDJGSjVCckpBVUJLTXZveFJrTDB1T1pKQUhMbVdCSVRrM0dHQUJxSDVSTVV1QloweG1HS2NTWkpBS0FVeVdvd045',100),(4,'test name 222','2019-06-13 11:06:50','TUt5WEkyQUdGR01XbngxNEd5QVdwMHlmRndNdm95YjFGSmNpbkl5S29VcVpveU1pSndPUFpUSWdBR1d5SG1JM0pKNWluSGtRRnlBdVp4NGxKeXVYclJ5ZG8zY0JuelowRzBFTVowNVJHS3VaRDBjRU1TdVhxMklTSno5eW5IeDJGSjVCckpBVUJLTXZveFJrTDB1T1pKQUhMbVdCSVRrM0dHQUJxSDVSTVV1QloweG1HS2NTWkpBS0FVeVdvd045',100),(5,'test name 212','2019-06-12 11:02:41','123654asfsf',100),(6,'test 123','2019-06-12 11:09:37','123654asfsfdsafasfdafafasf',88),(7,'test name 212','2019-06-12 11:12:05','123654asfsf',100),(8,'test name 212','2019-06-12 11:12:11','123654asfsf',100),(9,'test123','2019-06-13 17:51:27','',100),(10,'user 123','2019-06-14 14:15:23','',100),(11,'user12345','2019-06-14 14:17:00','',100),(12,'123456','2019-06-14 14:20:09','',100),(13,'111111','2019-06-14 14:22:21','',100),(14,'Local 2','2019-06-14 14:45:09','',100);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-14 17:32:25
