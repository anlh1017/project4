-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: project4
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
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('john','ROLE_EMPLOYEE'),('mary','ROLE_EMPLOYEE'),('mary','ROLE_MANAGER'),('susan','ROLE_ADMIN'),('susan','ROLE_EMPLOYEE');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Coffee traditional'),(2,'Espresso'),(3,'Freeze'),(4,'Tea'),(5,'Other Drinks'),(6,'Extra');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_email` varchar(45) DEFAULT NULL,
  `customer_password` varchar(45) DEFAULT NULL,
  `customer_name` varchar(20) DEFAULT NULL,
  `customer_phone` varchar(10) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `total_expense` int DEFAULT NULL,
  `membership_id` int DEFAULT NULL,
  `authority` varchar(20) DEFAULT NULL,
  `password_token` varchar(30) DEFAULT NULL,
  `auth_provider` varchar(15) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `membershio_FK_idx` (`membership_id`),
  CONSTRAINT `membershio_FK` FOREIGN KEY (`membership_id`) REFERENCES `membership` (`membership_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'duong@gmail.com','{noop}test123','duongPro','0777667414','118 duong 222',10369100,2,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 19:12:45'),(2,'kien@gmail.com','{noop}test123','Kien','0111111111','nguyen thuong hien',2133100,2,'ROLE_CUSTOMER',NULL,NULL,'2020-05-20 18:08:09'),(3,'an@gmail.com','{noop}test123','An','0777667414','le loi',71250,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 20:37:59'),(4,'caodangxuanduong060896@gmail.com','{noop}123','Xuân dương Cao đặng','0777667414','testtest',46550,1,'ROLE_CUSTOMER',NULL,'GOOGLE','2020-12-20 18:08:09'),(5,'long@gmail.com','{noop}long','Long','0934474647','40 Ly Tu Trong',2940250,2,'ROLE_CUSTOMER',NULL,NULL,'2020-09-20 18:08:09'),(6,'test@gmail.com','{noop}test123','test','0123456789','test',114000,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 20:38:05'),(32,'aaa@gmail.com','{noop}test123','aaa','0121212121','test',27550,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 20:37:53'),(35,'nguyengophuoc@gmail.com','{noop}123','ngo phuoc nguyen','0934474647','228/12 ',78850,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-26 18:31:48'),(36,'xuanduong.bee0@gmail.com','{noop}','BeeO','0987654321','hoa hue',57000,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-29 19:00:35'),(37,'duong1@gmail.com','{noop}123','duong duong','0777667414','hoa cuc',2479500,2,'ROLE_CUSTOMER',NULL,NULL,'2020-12-29 19:06:04'),(38,'duongduong@gmail.com','{noop}test123','DuongDuong','0777667414','testtest',3505500,3,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 19:21:51'),(39,'duong123@gmail.com','{noop}123','ahgsd','0777667414','hoa hong',84550,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 18:13:13'),(40,'thanh@gmail.com','{noop}123','thanhmai','0123456789','hoa mai',1015550,1,'ROLE_CUSTOMER',NULL,NULL,'2020-12-30 20:01:27');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL,
  `feedback_email` varchar(100) DEFAULT NULL,
  `feedback_name` varchar(45) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (2);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ingredient_name` varchar(45) DEFAULT NULL,
  `UOM` varchar(20) DEFAULT NULL,
  `available` float DEFAULT NULL,
  `safety_stock` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'Traditional Dark Roast','ml',5500,1000),(2,'Raw Sugar','g',400,150),(3,'Jelly Powder','g',250,50),(4,'Condensed Milk','ml',250,50),(5,'Sugar Bag','piece',35,10),(6,'Banh Cookie','piece',50,10),(8,'test1','g',1,1),(9,'test2','ml',0,0),(10,'test3','g',0,0),(12,'lychee','g',0,0);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ingredient_id` int DEFAULT NULL,
  `vendor_name` varchar(45) DEFAULT NULL,
  `UOM` varchar(45) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `ratio` float DEFAULT NULL,
  `price` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `safety_stock` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ingredientid_FK_1_idx` (`ingredient_id`),
  CONSTRAINT `ingredientid_FK_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,1,'Best Coffe Inc.','bag',50,2,500,110000,'2020-12-22 19:11:02',10),(2,2,'Bien Hoa Inc.','bag',30,2,20,18000,'2020-12-22 19:27:12',10),(3,3,'Best Jelly Inc.','bag',40,2,300,50000,'2020-12-23 23:01:51',10),(4,4,'Ong Tho Milk','can',51,2,200,30000,'2020-12-24 17:44:32',10),(5,5,'Bien Hoa Inc.','bag',60,2,100,40000,'2020-12-25 13:25:53',10),(6,6,'Givral','bag',70,2,50,60000,'2020-10-25 13:25:53',10),(8,8,'ghj','can',13,2,1,100,'2020-10-25 13:25:53',10),(9,9,'N/A','N/A',0,1,1,0,'2020-09-25 13:25:53',0),(10,10,'N/A','N/A',0,1,1,0,'2020-09-25 13:25:53',0),(12,12,'N/A','bag',100,2,1,10000,NULL,10);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membership`
--

DROP TABLE IF EXISTS `membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membership` (
  `membership_id` int NOT NULL AUTO_INCREMENT,
  `membership_name` varchar(45) DEFAULT NULL,
  `membership_description` varchar(45) DEFAULT NULL,
  `discount_value` float DEFAULT NULL,
  PRIMARY KEY (`membership_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership`
--

LOCK TABLES `membership` WRITE;
/*!40000 ALTER TABLE `membership` DISABLE KEYS */;
INSERT INTO `membership` VALUES (1,'Copper','Discount 5%',5),(2,'Silver','Discount 10%',10),(3,'Gold','Discount 20%',20),(4,'Diamond','Discount 30%',30);
/*!40000 ALTER TABLE `membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `Id` int NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `category` int DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'Coffee traditional\n',1),(2,'Espresso',2),(3,'Freeze',3),(4,'Tea',4),(5,'Other Drinks',5),(6,'Extra',6);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `productId` int NOT NULL,
  `order_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `size` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,1,10,35000,1),(7,1,12,1,29000,1),(8,2,12,2,35000,2),(9,8,12,3,75000,3),(10,1,13,1,29000,1),(11,2,13,1,39000,3),(12,16,14,1000,49000,2),(13,3,14,1,39000,3),(14,1,15,1,29000,1),(15,2,15,1,29000,1),(16,1,16,1,29000,1),(18,3,16,1,29000,1),(22,1,18,1,29000,1),(23,2,18,1,29000,1),(24,8,18,1,59000,1),(25,2,19,2,35000,2),(26,4,19,1,45000,3),(27,2,20,1,29000,1),(28,5,20,1,35000,1),(30,4,21,1,39000,2),(31,7,22,10,69000,3),(32,3,22,10,150000,3),(33,9,22,1,75000,3),(34,2,23,1,29000,1),(35,5,23,1,45000,2),(36,1,24,25,30000,2),(37,2,24,1,39000,3),(38,3,24,1,60000,1),(39,2,25,1,29000,1),(40,1,25,1,25000,1),(41,2,26,1,29000,1),(42,1,27,1,25000,1),(43,2,27,1,29000,1),(44,3,27,1,60000,1),(45,2,28,1,29000,1),(46,3,28,1,60000,1),(47,1,29,1,30000,2),(48,2,30,1,29000,1),(49,2,31,1,29000,1),(50,2,32,1,29000,1),(51,2,33,2,29000,1),(52,2,34,1,29000,1),(53,2,35,1,29000,1),(54,2,36,1,29000,1),(55,6,36,5,65000,2),(56,8,36,2,75000,3),(57,2,37,1,29000,1),(58,2,38,1,29000,1),(59,3,38,1,60000,1),(60,2,39,1,29000,1),(61,3,39,1,100000,2),(62,2,40,1,29000,1),(63,15,41,1,39000,1),(64,2,42,2,29000,1),(65,1,42,1,25000,1),(66,6,43,1,55000,1),(67,2,44,10,29000,1),(68,11,44,10,65000,3),(69,22,44,50,55000,3),(70,2,45,90,29000,1),(71,2,46,1,29000,1),(72,3,46,1,60000,1),(73,3,47,10,100000,2),(74,6,47,1,69000,3),(75,3,48,1,60000,1),(76,14,49,1,49000,1),(77,2,50,1,29000,1),(78,5,51,1,20000,1),(79,6,51,1,55000,1),(80,3,52,1,60000,1),(81,9,52,1,60000,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `total` int DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `payment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (22,2151750,'2020-09-24 17:44:32',5,3,'test2','5:44 PM','COD'),(23,66600,'2020-10-25 13:25:53',2,4,'asfsf','1:25 PM','COD'),(24,764100,'2020-10-25 14:33:56',2,3,'ly tu trong','7:30 PM','COD'),(25,48600,'2020-11-25 16:15:01',1,3,'118 duong 222','7:30 PM','COD'),(26,26100,'2020-11-25 18:29:55',1,3,'118 duong 222','6:29 PM','COD'),(27,102600,'2020-11-26 05:56:00',1,3,'118 duong 222','7:30 PM','COD'),(28,80100,'2020-11-26 07:28:26',2,3,'nguyen thuong hien','7:30 PM','COD'),(29,27000,'2020-11-26 12:07:21',5,3,'test2','12:07 PM','COD'),(30,26100,'2020-11-26 12:48:34',5,3,'test2','12:47 PM','COD'),(31,26100,'2020-12-26 12:53:42',5,3,'test2','12:53 PM','PAYPAL'),(32,26100,'2020-12-26 12:54:39',5,4,'test2','12:54 PM','PAYPAL'),(33,52200,'2020-12-26 12:56:40',5,4,'test2','12:56 PM','PAYPAL'),(34,26100,'2020-12-26 12:57:00',5,3,'test2','12:56 PM','COD'),(35,26100,'2020-12-26 13:16:57',5,3,'dinh tien hoang','7:00 PM','COD'),(36,453600,'2020-12-26 15:58:06',5,4,'ly tu trong','4:30 PM','PAYPAL'),(37,26100,'2020-12-26 16:13:21',2,3,'nguyen thuong hien','4:13 PM','COD'),(38,80100,'2020-12-26 16:16:06',2,3,'nguyen thuong hien','4:15 PM','PAYPAL'),(39,116100,'2020-12-26 16:16:36',2,3,'nguyen thuong hien','4:16 PM','COD'),(40,26100,'2020-12-26 16:27:01',5,3,'test2','4:26 PM','PAYPAL'),(41,35100,'2020-12-26 16:34:19',5,3,'40 Ly Tu Trong','4:32 PM','PAYPAL'),(42,78850,'2020-12-26 18:37:00',35,3,'228/12 ','6:36 PM','PAYPAL'),(43,49500,'2020-12-30 19:11:26',1,4,'118 duong 222','7:30 PM','PAYPAL'),(44,3505500,'2020-12-30 19:20:25',38,3,'testtest','7:20 PM','COD'),(45,2479500,'2020-12-30 19:25:58',37,3,'hoa cuc','7:25 PM','COD'),(46,84550,'2020-12-30 19:59:36',39,3,'hoa hong','7:25 PM','COD'),(47,1015550,'2020-12-30 20:04:14',40,3,' 10 hoa mai','8:00 PM','PAYPAL'),(48,57000,'2020-12-30 20:06:10',36,3,'hoa hue','7:25 PM','COD'),(49,46550,'2020-12-30 20:07:36',4,3,'testtest','7:25 PM','COD'),(50,27550,'2020-12-30 20:19:01',32,3,'test','7:25 PM','COD'),(51,71250,'2020-12-30 20:35:13',3,3,'le loi','8:00 PM','COD'),(52,114000,'2020-12-30 20:35:58',6,3,'test','8:00 PM','PAYPAL');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_ingredient`
--

DROP TABLE IF EXISTS `product_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `ingredient_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_product_id_1_idx` (`product_id`),
  KEY `FK_ingredient_id_1_idx` (`ingredient_id`),
  CONSTRAINT `FK_ingredient_id_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`),
  CONSTRAINT `FK_product_id_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_ingredient`
--

LOCK TABLES `product_ingredient` WRITE;
/*!40000 ALTER TABLE `product_ingredient` DISABLE KEYS */;
INSERT INTO `product_ingredient` VALUES (10,3,1),(11,3,4),(17,3,2),(18,4,1),(19,2,1),(20,2,2),(30,5,2),(36,5,1),(41,1,5),(42,1,1);
/*!40000 ALTER TABLE `product_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_size`
--

DROP TABLE IF EXISTS `product_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_size` (
  `fk_product` int DEFAULT NULL,
  `fk_size` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_size_id_idx` (`fk_size`),
  KEY `fk_productid` (`fk_product`),
  CONSTRAINT `fk_productid` FOREIGN KEY (`fk_product`) REFERENCES `products` (`productId`) ON DELETE CASCADE,
  CONSTRAINT `fk_sizeid` FOREIGN KEY (`fk_size`) REFERENCES `size` (`Id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_size`
--

LOCK TABLES `product_size` WRITE;
/*!40000 ALTER TABLE `product_size` DISABLE KEYS */;
INSERT INTO `product_size` VALUES (2,1,29000,1),(2,2,35000,2),(2,3,39000,3),(3,1,60000,4),(3,2,100000,5),(3,3,150000,6),(4,1,35000,7),(4,2,39000,8),(4,3,45000,9),(6,1,55000,10),(6,2,65000,11),(6,3,69000,12),(7,1,55000,13),(7,2,65000,14),(7,3,69000,15),(8,1,59000,16),(8,2,69000,17),(8,3,75000,18),(9,1,60000,19),(9,2,69000,20),(9,3,75000,21),(11,1,49000,25),(11,2,59000,26),(11,3,65000,27),(12,1,49000,28),(12,2,59000,29),(12,3,65000,30),(13,1,49000,31),(13,2,59000,32),(13,3,65000,33),(14,1,49000,34),(14,2,65000,35),(14,3,65000,36),(15,1,39000,37),(15,2,49000,38),(15,3,55000,39),(16,1,39000,40),(16,2,49000,41),(16,3,55000,42),(17,1,39000,43),(17,2,49000,44),(17,3,55000,45),(18,1,39000,46),(18,2,49000,47),(18,3,55000,48),(19,1,39000,49),(19,2,49000,50),(19,3,55000,51),(20,1,39000,52),(20,2,49000,53),(20,3,55000,54),(21,1,39000,55),(21,2,49000,56),(21,3,55000,57),(22,1,39000,58),(22,2,49000,59),(22,3,55000,60),(5,1,20000,91),(5,2,45000,92),(5,3,70000,93),(1,1,25000,97),(1,2,30000,98),(1,3,35000,99);
/*!40000 ALTER TABLE `product_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `productId` int NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`productId`),
  KEY `FK_category_Id_idx` (`category_id`),
  CONSTRAINT `FK_category_Id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Coffee & condensed milk','Ice/Hot',1,'suada.png'),(2,'Black Coffee','Ice/Hot',1,'denda.png'),(3,'White Coffee & Condensed Milk','Ice/Hot',1,'bacxiu.png'),(4,'Espresso','Ice/Hot',2,'esp.png'),(5,'Americano','Ice/Hot',2,'americano.png'),(6,'Cappuccino','Ice/Hot',2,'cappu.png'),(7,'Latte','Ice/Hot',2,'latte.png'),(8,'Mocha','Ice/Hot',2,'mocha.png'),(9,'Caramel Macchiato','Ice/Hot',2,'caramelmacchiato.png'),(11,'Chocolate Freeze','Ice',3,'chocolate.png'),(12,'Cookies & Cream','Ice',3,'cookies.png'),(13,'Caramel Freeze','Ice',3,'cf.png'),(14,'Classic Freeze','Ice',3,'class.png'),(15,'Olong Tea,Lotus Seeds & Milk Foam','Ice/Hot',4,'olong.png'),(16,'hibiscus Blueberries','Ice/Hot',4,'vq.png'),(17,'Peach Tea & Lemongrass','Ice/Hot',4,'thanhdao.png'),(18,'Red Tea & Lychee Jelly','Ice/Hot',4,'travai.png'),(19,'Green Tea & Red Beans','Ice/Hot',4,'dado.png'),(20,'Lime ','Ice/Hot',5,'chanh.png'),(21,'Kumquat','Ice',5,'kum.png'),(22,'Chocolate','Ice/Hot',5,'choda.png'),(23,'Extra Shot','',6,'esp.png'),(24,'Lotus','',6,'senngam.png');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pro_ingre_id` int NOT NULL,
  `size_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_size_id_idx` (`size_id`),
  KEY `FK_pro_ingre_id_idx` (`pro_ingre_id`),
  CONSTRAINT `FK_pro_ingre_id` FOREIGN KEY (`pro_ingre_id`) REFERENCES `product_ingredient` (`id`),
  CONSTRAINT `FK_size_id` FOREIGN KEY (`size_id`) REFERENCES `size` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (12,10,1,20),(13,11,1,30),(23,17,1,50),(24,18,1,25),(25,19,1,25),(27,10,2,40),(28,10,3,50),(29,11,2,20),(30,20,1,20),(35,41,1,1),(36,41,2,0),(37,41,3,0),(38,42,1,1),(39,42,2,0),(40,42,3,0);
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `content` varchar(16000) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `fk_customerId1_idx` (`customer_id`),
  CONSTRAINT `fk_customerId1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (14,1,'test2',5,'2020-12-20 18:08:09'),(15,1,'good',5,'2020-12-22 18:33:07'),(16,5,'perfect',4.5,'2020-12-26 16:31:56'),(17,38,'Good Chops',5,'2020-12-29 20:04:42');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `Id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'S'),(2,'M'),(3,'L');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_type` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `email_id` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'caodangxuanduong060896@gmail.com','https://lh6.googleusercontent.com/-gKjyaRdiUJQ/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuck4z3E0iWwt9ZiHNmy6cHTcpxB55A/s96-c/photo.jpg','Xuân dương Cao đặng',NULL,0,0,NULL,NULL,_binary '\0',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('john','{noop}test123',1),('mary','{noop}test123',1),('susan','{noop}test123',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-30 20:58:01
