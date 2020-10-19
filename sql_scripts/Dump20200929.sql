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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Coffee traditional'),(2,'Espresso'),(3,'Freeze'),(4,'Tea'),(5,'Other Drinks'),(6,'Extra'),(10,'test1');
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
  PRIMARY KEY (`customer_id`),
  KEY `membershio_FK_idx` (`membership_id`),
  CONSTRAINT `membershio_FK` FOREIGN KEY (`membership_id`) REFERENCES `membership` (`membership_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'duong@gmail.com','{noop}test123','Duong','777667414','118 duong 222',10000000,13,'ROLE_CUSTOMER'),(2,'kien@gmail.com','{noop}test123','Kien','0111111111','asfsf',1000000,2,'ROLE_CUSTOMER'),(3,'long@gmail.com','test123','Long','0222222222','asfasf',2000000,1,'ROLE_CUSTOMER');
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
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ingredient_name` varchar(45) DEFAULT NULL,
  `vendor_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'Traditional Dark Roast','N/A'),(2,'Raw Sugar','Test'),(3,'Jelly Powder','N/A'),(4,'Condensed Milk','Ong Tho'),(5,'Sugar Bag','Duong Bien Hoa'),(6,'Banh Cookie','Givral');
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
  `SKU` varchar(45) DEFAULT NULL,
  `UMO` varchar(45) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `available` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ingredientid_FK_1_idx` (`ingredient_id`),
  CONSTRAINT `ingredientid_FK_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,1,NULL,'N/A',20,50),(2,2,NULL,'N/A',30,60),(3,3,NULL,'N/A',40,70),(4,4,NULL,'N/A',50,80),(5,5,NULL,'N/A',60,90),(6,6,NULL,'N/A',70,100);
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
  PRIMARY KEY (`membership_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership`
--

LOCK TABLES `membership` WRITE;
/*!40000 ALTER TABLE `membership` DISABLE KEYS */;
INSERT INTO `membership` VALUES (1,'Copper','Discount 5%'),(2,'Silver','Discount 10%'),(3,'Gold','Discount 20%'),(4,'Diamond','Discount 30%'),(13,'Ruby','Discount 40%');
/*!40000 ALTER TABLE `membership` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,1,10,35000,1),(2,2,2,2,35000,2),(3,4,2,33,35000,3),(4,5,2,5,35000,1);
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
  `notes` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1000,'2020-01-02 00:00:00',1,'hahah','40/5adb',1,NULL),(2,50000,'2020-01-03 00:00:00',2,'sfsdfs','aaaa',1,NULL),(3,3000,'2020-01-04 00:00:00',3,'sqwer','zxcvv',2,NULL),(4,6000,'2020-01-05 00:00:00',4,'uyiyiu','oiuoiu',3,NULL),(5,70000,'2020-01-06 00:00:00',1,'sfsdf','adsfsdf',3,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_ingredient`
--

LOCK TABLES `product_ingredient` WRITE;
/*!40000 ALTER TABLE `product_ingredient` DISABLE KEYS */;
INSERT INTO `product_ingredient` VALUES (10,3,1),(11,3,4),(17,3,2),(18,4,1);
/*!40000 ALTER TABLE `product_ingredient` ENABLE KEYS */;
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
  CONSTRAINT `FK_category_Id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Coffee & condensed milk','Ice/Hot',1,'suada.png'),(2,'Black Coffee','Ice/Hot',1,'denda.png'),(3,'White Coffee & Condensed Milk','Ice/Hot',1,'bacxiu.png.png'),(4,'Espresso','Ice/Hot',2,'vq.jpg'),(5,'Americano','Ice/Hot',2,NULL),(6,'Cappuccino','Ice/Hot',2,NULL),(7,'Latte','Ice/Hot',2,NULL),(8,'Mocha','Ice/Hot',2,NULL),(9,'Caramel Macchiato','Ice/Hot',2,NULL),(10,'Green Tea Freeze','Ice',3,NULL),(11,'Chocolate Freeze','Ice',3,'freezechoco.png'),(12,'Cookies & Cream','Ice',3,NULL),(13,'Caramel Freeze','Ice',3,NULL),(14,'Classic Freeze','Ice',3,NULL),(15,'Olong Tea,Lotus Seeds & Milk Foam','Ice/Hot',4,NULL),(16,'Peach Tea,Peach Jelly & Milk','Ice/Hot',4,NULL),(17,'Peach Tea & Lemongrass','Ice/Hot',4,NULL),(18,'Red Tea & Lychee Jelly','Ice/Hot',4,'TRATHACHVAI.jpg.png'),(19,'Green Tea & Red Beans','Ice/Hot',4,NULL),(20,'Lime ','Ice/Hot',5,NULL),(21,'Kumquat','Ice',5,'maucoffee.png'),(22,'Chocolate','Ice/Hot',5,NULL),(23,'Extra Shot',NULL,6,NULL),(24,'Lotus',NULL,6,NULL),(25,'Peach',NULL,6,NULL),(26,'Lychee',NULL,6,NULL),(27,'Red Beans',NULL,6,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_size`
--

DROP TABLE IF EXISTS `products_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_size` (
  `ProductSizeId` int NOT NULL AUTO_INCREMENT,
  `ProductsId` int NOT NULL,
  `SizeId` int NOT NULL,
  `Price` int DEFAULT NULL,
  PRIMARY KEY (`ProductSizeId`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_size`
--

LOCK TABLES `products_size` WRITE;
/*!40000 ALTER TABLE `products_size` DISABLE KEYS */;
INSERT INTO `products_size` VALUES (1,1,1,29000),(2,1,2,35000),(3,1,3,39000),(4,2,1,29000),(5,2,2,35000),(6,2,3,39000),(7,3,1,29000),(8,3,2,35000),(9,3,3,39000),(10,4,1,35000),(11,4,2,39000),(12,4,3,45000),(13,5,1,35000),(14,5,2,39000),(15,5,3,45000),(16,6,1,55000),(17,6,2,65000),(18,6,3,69000),(19,7,1,55000),(20,7,2,65000),(21,7,3,69000),(22,8,1,59000),(23,8,2,69000),(24,8,3,75000),(25,9,1,59000),(26,9,2,69000),(27,9,3,75000),(28,10,1,49000),(29,10,2,59000),(30,10,3,65000),(31,11,1,49000),(32,11,2,59000),(33,11,3,65000),(34,12,1,49000),(35,12,2,59000),(36,12,3,65000),(37,13,1,49000),(38,13,2,59000),(39,13,3,65000),(40,14,1,49000),(41,14,2,65000),(42,14,3,65000),(43,15,1,39000),(44,15,2,49000),(45,15,3,55000),(46,16,1,39000),(47,16,2,49000),(48,16,3,55000),(49,17,1,39000),(50,17,2,49000),(51,17,3,55000),(52,18,1,39000),(53,18,2,49000),(54,18,3,55000),(55,19,1,39000),(56,19,2,49000),(57,19,3,55000),(58,20,1,39000),(59,20,2,49000),(60,20,3,55000),(61,21,1,39000),(62,21,2,49000),(63,21,3,55000),(64,22,1,39000),(65,22,2,49000),(66,22,3,55000);
/*!40000 ALTER TABLE `products_size` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (12,10,1,25),(13,11,1,30),(23,17,1,50),(24,18,1,25);
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
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

-- Dump completed on 2020-09-29 11:15:11
