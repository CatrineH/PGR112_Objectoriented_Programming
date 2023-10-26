CREATE DATABASE  IF NOT EXISTS `student_vote`
USE `student_vote`;
-- MySQL dump 10.13  Distrib 8.0.32, for macos13 (x86_64)
--
-- Host: 127.0.0.1    Database: student_vote
-- ------------------------------------------------------
-- Server version	8.0.33



--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nominee_id` int DEFAULT NULL,
  `comment_text` text,
  PRIMARY KEY (`id`),
  KEY `nominee_id` (`nominee_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`nominee_id`) REFERENCES `student_nominees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `student_nominees`
--

DROP TABLE IF EXISTS `student_nominees`;

CREATE TABLE `student_nominees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `votes` int DEFAULT '0',
  `student_program` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping events for database 'student_vote'
--

-- Dump completed on 2023-08-09  8:02:57
