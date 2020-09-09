-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: web_customer_tracker
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `productId` int NOT NULL,
  `productName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `category` int DEFAULT NULL,
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Coffee & condensed milk','Ice/Hot',1),(2,'Black Coffee','Ice/Hot',1),(3,'White Coffee & Condensed Milk','Ice/Hot',1),(4,'Espresso','Ice/Hot',2),(5,'Americano','Ice/Hot',2),(6,'Cappuccino','Ice/Hot',2),(7,'Latte','Ice/Hot',2),(8,'Mocha','Ice/Hot',2),(9,'Caramel Macchiato','Ice/Hot',2),(10,'Green Tea Freeze','Ice',3),(11,'Chocolate Freeze','Ice',3),(12,'Cookies & Cream','Ice',3),(13,'Caramel Freeze','Ice',3),(14,'Classic Freeze','Ice',3),(15,'Olong Tea,Lotus Seeds & Milk Foam','Ice/Hot',4),(16,'Peach Tea,Peach Jelly & Milk','Ice/Hot',4),(17,'Peach Tea & Lemongrass','Ice/Hot',4),(18,'Red Tea & Lychee Jelly','Ice/Hot',4),(19,'Green Tea & Red Beans','Ice/Hot',4),(20,'Lime ','Ice/Hot',5),(21,'Kumquat','Ice',5),(22,'Chocolate','Ice/Hot',5),(23,'Extra Shot',NULL,6),(24,'Lotus',NULL,6),(25,'Peach',NULL,6),(26,'Lychee',NULL,6),(27,'Red Beans',NULL,6);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-09 15:22:23
