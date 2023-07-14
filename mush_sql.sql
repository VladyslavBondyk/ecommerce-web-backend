USE bookstore;
-- MySQL dump 10.13  Distrib 8.0.33, for macos13 (arm64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(512) NOT NULL,
  `address_line_2` varchar(512) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(75) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkb7b5aavt0mlydpvdiuesa9r8` (`user_id`),
  CONSTRAINT `FKkb7b5aavt0mlydpvdiuesa9r8` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'The Great Gatsby','F.Scott Fitzgerald',12.99),(2,'Nineteen Eighty-Four','George Orwell',8.99),(3,'The Wind-Up Bird Chronicle','Haruki Murakami',7.99);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ce3rbi3bfstbvvyne34c1dvyv` (`product_id`),
  CONSTRAINT `FKp7gj4l80fx8v0uap3b2crjwp5` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `local_user`
--

DROP TABLE IF EXISTS `local_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `local_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(320) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46f7ufu7j9nkhuyfly98to4u1` (`email`),
  UNIQUE KEY `UK_93d93k106ik2383youkc9bixl` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local_user`
--

LOCK TABLES `local_user` WRITE;
/*!40000 ALTER TABLE `local_user` DISABLE KEYS */;
INSERT INTO `local_user` VALUES (13,'Lillia@test.com','Dave','Tester','Password!12','LilliaTest'),(14,'encryptr@test.com','Dave','Tester','$2a$10$cPv1WGb7Qs3Tf9YvbxJ9ve5rkGtcTm0sa77co8gdAmISepedPeaOa','EncryptionTest'),(15,'encryasdptr@test.com','Dave','Tester','$2a$10$XgZ5tPkth/Cbvl1RMYHgwenHni6YSSYyFlqlHpnZS2SNyWe5nZCpS','EncryasdptionTest'),(16,'encryasasdptr@test.com','Dave','Tester','$2a$10$qfPNvW5zx7Ejd1sFSGvyn.pJKpYBaLxfQD1CUfg5oIJuDTGjsn/o6','EncrasdgsdptionTest'),(17,'encrdyasasdptr@test.com','Dave','Tester','$2a$10$d0BH566iw0heCmdPn4VgpuunhjS6FTKz6nkBrIZaINsxHPMGFHFey','EncrassadgsdptionTest'),(18,'encdawrdyasasdptr@test.com','Dave','Tester','$2a$10$R3w5w31F7D6w8WGz/ebV9eU6vqbg7/4qVKdkgutZH57OVZl3YPW6i','EncraasssadgsdptionTest'),(19,'sdyasasdptr@test.com','Dave','Tester','$2a$10$lE8pxioe8fcUKyRFYcbGIOQRvVPfYb4OArTv4dfdm3DI8V8P6K8xS','EgsdptionTest'),(20,'lalaland@test.com','Dave','Tester','$2a$10$VVvkKcZLPk.5ibXWH4qC..Ck/4E6/ku4c5mEuEgZWASquXVXiXvWK','lalaland'),(21,'lalaland1@test.com','Dave','Tester','$2a$10$fOSVTLXdktya0DYNevP9aOiHfmnEAB9sG9aFE.WSFJQ52AeC1N/e.','lalaland1'),(22,'lalaland11@test.com','Dave','Tester','$2a$10$c/Fm.vQMUR1MRiPRrvF0zObkIFczuPlt.HQCONgNWLqE2.ig/hLba','lalaland11'),(23,'lalaland111@test.com','Dave','Tester','$2a$10$8Cvm5Z5I3I74uYqdhhY8JeLUoBhV66CWvB/J0yddLoKjtMzarSeou','lalaland111');
/*!40000 ALTER TABLE `local_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `long_description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `short_description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jmivyxk9rmgysrmsqw15lqr5b` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_order`
--

DROP TABLE IF EXISTS `web_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65jlvhv84w95l6dimcc1p6hqr` (`address_id`),
  KEY `FK8mvneqqd44higf18x0m67bg29` (`user_id`),
  CONSTRAINT `FK65jlvhv84w95l6dimcc1p6hqr` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK8mvneqqd44higf18x0m67bg29` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_order`
--

LOCK TABLES `web_order` WRITE;
/*!40000 ALTER TABLE `web_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `web_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_order_quantities`
--

DROP TABLE IF EXISTS `web_order_quantities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_order_quantities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK654x9lb2ii9jrhsriicg518iw` (`order_id`),
  KEY `FKi7eexulg463xqvxgykc3qqx0a` (`product_id`),
  CONSTRAINT `FK654x9lb2ii9jrhsriicg518iw` FOREIGN KEY (`order_id`) REFERENCES `web_order` (`id`),
  CONSTRAINT `FKi7eexulg463xqvxgykc3qqx0a` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_order_quantities`
--

LOCK TABLES `web_order_quantities` WRITE;
/*!40000 ALTER TABLE `web_order_quantities` DISABLE KEYS */;
/*!40000 ALTER TABLE `web_order_quantities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-02  5:59:52
