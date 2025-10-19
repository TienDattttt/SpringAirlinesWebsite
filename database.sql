-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: swipeflight
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `airport`
--

DROP TABLE IF EXISTS `airport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airport` (
  `id` bigint NOT NULL,
  `code` varchar(3) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `airport_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airport`
--

LOCK TABLES `airport` WRITE;
/*!40000 ALTER TABLE `airport` DISABLE KEYS */;
INSERT INTO `airport` VALUES (1,'HAN','Sân bay quốc tế Nội Bài',1),(2,'SGN','Sân bay quốc tế Tân Sơn Nhất',2),(3,'DAD','Sân bay quốc tế Đà Nẵng',3),(4,'CXR','Sân bay quốc tế Cam Ranh',4),(5,'DAD','Sân bay Đà Nẵng',5),(6,'HUI','Sân bay Phú Bài',6),(7,'PQC','Sân bay quốc tế Phú Quốc',7),(8,'VDO','Sân bay Vân Đồn',9),(9,'VCA','Sân bay quốc tế Cần Thơ',10),(10,'DLI','Sân bay Liên Khương',11),(11,'HPH','Sân bay Cát Bi',12),(12,'THD','Sân bay Thọ Xuân',13),(13,'VII','Sân bay Vinh',14),(15,'UIH','Sân bay Phù Cát',16);
/*!40000 ALTER TABLE `airport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `route_number` bigint DEFAULT NULL,
  `num_of_tickets` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `booking_date` datetime DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  `is_canceled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,1,1,150,'2024-12-07 11:51:01','2024-12-07 17:01:02',1),(2,1,4,3,161,'2024-12-07 17:34:14','2024-12-07 17:34:14',0),(3,4,6,1,19,'2024-12-08 01:30:06','2024-12-08 01:30:06',0),(4,4,6,1,44,'2024-12-08 01:45:05','2024-12-08 01:45:05',0),(5,1,4,2,24,'2024-12-09 15:27:09','2024-12-09 15:27:09',0);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` bigint NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country_id` bigint DEFAULT NULL,
  `description` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  CONSTRAINT `city_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Hà Nội',1,'Thủ đô Hà Nội - Trái tim văn hóa và chính trị của Việt Nam. Khám phá Phố Cổ, thưởng thức phở và trải nghiệm cuộc sống đô thị sôi động. aaaaaa','/images/cities/Hanoi.jpg'),(2,'Thành phố Hồ Chí Minh',1,'Sài Gòn - Trung tâm kinh tế lớn nhất Việt Nam với nhịp sống năng động và cửa ngõ giao thương quốc tế.','/images/cities/HCM.jpg'),(3,'Đà Nẵng',1,'Đà Nẵng - Thành phố của những cây cầu, bãi biển xinh đẹp và một trong những thành phố du lịch hàng đầu Việt Nam.','/images/cities/DaNang.jpg'),(4,'Nha Trang',1,'Nha Trang - Thành phố biển với bãi biển đẹp và nổi tiếng với nền văn hóa Chăm pa cổ kính.','/images/cities/NhaTrang.jpg'),(5,'Hội An',1,'Hội An - Di sản thế giới với phố cổ yên bình, lồng đèn và nghệ thuật thủ công mỹ nghệ truyền thống.','/images/cities/HoiAn.jpg'),(6,'Huế',1,'Huế - Thành phố cố đô với Đại Nội Huế, những ngôi chùa cổ kính và sự trầm mặc của dòng sông Hương thơ mộng.','/images/cities/Hue.jpg'),(7,'Phú Quốc',1,'Phú Quốc - Hòn đảo thiên đường với bãi biển trắng xóa, nước biển trong xanh và những khu nghỉ dưỡng sang trọng.','/images/cities/PhuQuoc.jpg'),(8,'Sapa',1,'Sapa - Nơi bản sắc văn hóa các dân tộc thiểu số và những ruộng bậc thang ngoạn mục tựa như tranh vẽ.','/images/cities/Sapa.jpg'),(9,'Hạ Long',1,'Hạ Long - Thành phố biển nổi tiếng với Vịnh Hạ Long, di sản thiên nhiên thế giới với hàng ngàn hòn đảo lớn nhỏ.','/images/cities/HaLong.jpg'),(10,'Cần Thơ',1,'Cần Thơ - Trung tâm của vùng đồng bằng sông Cửu Long, nổi tiếng với chợ nổi Cái Răng và vẻ đẹp sông nước. ','/images/cities/CanTho.jpg'),(11,'Đà Lạt',1,'Đà Lạt - Thành phố ngàn hoa với khí hậu mát mẻ quanh năm, nổi tiếng với những đồi thông và vườn hoa đầy màu sắc.','/images/cities/DaLat.jpg'),(12,'Hải Phòng',1,'Hải Phòng - Thành phố cảng lớn nhất miền Bắc Việt Nam, nổi tiếng với nền văn hóa phong phú và các lễ hội truyền thống.','/images/cities/HaiPhong.jpg'),(13,'Thanh Hóa',1,'Thanh Hóa - Một tỉnh của Việt Nam với bãi biển Sầm Sơn, cùng nhiều di tích lịch sử và văn hóa phong phú.','/images/cities/ThanhHoa.jpg'),(14,'Vinh',1,'Vinh - Thành phố lớn của miền Trung Việt Nam, là cửa ngõ giao lưu kinh tế, văn hóa giữa miền Bắc và miền Nam.','/images/cities/Vinh.jpg'),(16,'Quy Nhơn',1,'Quy Nhơn - Thành phố biển với bờ biển đẹp và lịch sử phong phú, là điểm du lịch nổi bật của tỉnh Bình Định.','/images/cities/QuyNhon.jpg');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id` bigint NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Việt Nam');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `user_id` bigint DEFAULT NULL,
  `route_number` bigint DEFAULT NULL,
  `cleaning_rating` int DEFAULT NULL,
  `convenience_rating` int DEFAULT NULL,
  `service_rating` int DEFAULT NULL,
  UNIQUE KEY `user_id` (`user_id`,`route_number`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plane_id` bigint DEFAULT NULL,
  `departure_airport_id` bigint DEFAULT NULL,
  `arrival_airport_id` bigint DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `arrival_date` date DEFAULT NULL,
  `arrival_time` time DEFAULT NULL,
  `ticket_price` double DEFAULT NULL,
  `duration` bigint DEFAULT NULL,
  `status_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `plane_id` (`plane_id`),
  KEY `departure_airport_id` (`departure_airport_id`),
  KEY `arrival_airport_id` (`arrival_airport_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `flight_ibfk_1` FOREIGN KEY (`plane_id`) REFERENCES `plane` (`id`),
  CONSTRAINT `flight_ibfk_2` FOREIGN KEY (`departure_airport_id`) REFERENCES `airport` (`id`),
  CONSTRAINT `flight_ibfk_3` FOREIGN KEY (`arrival_airport_id`) REFERENCES `airport` (`id`),
  CONSTRAINT `flight_ibfk_4` FOREIGN KEY (`status_id`) REFERENCES `flight_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES (1,1,3,2,'2024-12-19','15:00:00','2024-12-20','11:50:00',100,75000,3),(2,9,3,2,'2024-12-19','07:00:00','2024-12-20','19:30:00',20,45000,3),(3,1,3,2,'2024-12-12','07:00:00','2024-12-13','19:00:00',12000000,43200,3),(4,4,4,3,'2024-12-27','11:00:00','2024-12-27','11:50:00',12,3000,1),(5,7,1,11,'2024-12-17','07:00:00','2024-12-17','07:40:00',13,2400,1),(6,3,10,4,'2024-12-30','00:00:00','2024-12-30','00:20:00',19,1200,1),(7,3,1,11,'2024-12-22','23:00:00','2024-12-22','23:20:00',99,1200,1),(8,7,13,4,'2024-12-26','07:00:00','2024-12-26','07:50:00',22,3000,4);
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight_status`
--

DROP TABLE IF EXISTS `flight_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight_status` (
  `id` bigint NOT NULL,
  `description` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight_status`
--

LOCK TABLES `flight_status` WRITE;
/*!40000 ALTER TABLE `flight_status` DISABLE KEYS */;
INSERT INTO `flight_status` VALUES (1,'Đã lên lịch'),(2,'Bị trễ'),(3,'Đã hủy'),(4,'Được đẩy lên sớm');
/*!40000 ALTER TABLE `flight_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luggage`
--

DROP TABLE IF EXISTS `luggage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `luggage` (
  `id` bigint NOT NULL,
  `description` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `weight_limit` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luggage`
--

LOCK TABLES `luggage` WRITE;
/*!40000 ALTER TABLE `luggage` DISABLE KEYS */;
INSERT INTO `luggage` VALUES (1,'Túi xách tay',8,0),(2,'Va li nhỏ',8,25),(3,'Va li lớn',20,50);
/*!40000 ALTER TABLE `luggage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger` (
  `booking_id` bigint DEFAULT NULL,
  `passport_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  UNIQUE KEY `booking_id` (`booking_id`,`passport_id`),
  CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` VALUES (2,'123456798','Dattt','Ng','M'),(2,'123457689','Dat','Ng','F'),(2,'123456765','Datt','Ng','M'),(3,'123456788','datttttttt','ng','M'),(4,'345353645','aaaaaaa','b','M'),(5,'122235354','Test','one','M'),(5,'878746524','Test','two','M');
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger_flight`
--

DROP TABLE IF EXISTS `passenger_flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger_flight` (
  `booking_id` bigint DEFAULT NULL,
  `passport_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `flight_id` bigint DEFAULT NULL,
  `seat_num` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `luggage_ids` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  KEY `booking_id` (`booking_id`),
  KEY `flight_id` (`flight_id`),
  CONSTRAINT `passenger_flight_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
  CONSTRAINT `passenger_flight_ibfk_2` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger_flight`
--

LOCK TABLES `passenger_flight` WRITE;
/*!40000 ALTER TABLE `passenger_flight` DISABLE KEYS */;
INSERT INTO `passenger_flight` VALUES (2,'123456798',4,'4-B','1,3'),(2,'123457689',4,'5-D','1,2'),(2,'123456765',4,'8-C','1,3'),(3,'123456788',6,'6-D','1'),(4,'345353645',6,'4-C','1,2'),(5,'122235354',4,'6-C','1'),(5,'878746524',4,'6-D','1');
/*!40000 ALTER TABLE `passenger_flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plane`
--

DROP TABLE IF EXISTS `plane`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plane` (
  `id` bigint NOT NULL,
  `description` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `num_of_rows` int DEFAULT NULL,
  `num_of_seats_per_row` int DEFAULT NULL,
  `total_quantity` int DEFAULT NULL,
  `available_quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plane`
--

LOCK TABLES `plane` WRITE;
/*!40000 ALTER TABLE `plane` DISABLE KEYS */;
INSERT INTO `plane` VALUES (1,'Boeing 787-9 Dreamliner',20,6,10,10),(2,'Boeing 737-800',25,6,9,9),(3,'Boeing 777-200ER',30,6,3,1),(4,'Boeing 737-900ER',40,4,2,1),(5,'Boeing 787-8 Dreamliner',40,6,3,3),(6,'Airboss A319-100',15,4,4,4),(7,'Airboss A320-200',40,4,10,8),(8,'Airboss A320neo',50,6,1,1),(9,'Gulfstream G150',7,6,3,3);
/*!40000 ALTER TABLE `plane` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserved_seats`
--

DROP TABLE IF EXISTS `reserved_seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserved_seats` (
  `passport_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `flight_id` bigint DEFAULT NULL,
  `seat_num` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reservation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `flight_id` (`flight_id`,`seat_num`),
  CONSTRAINT `reserved_seats_ibfk_1` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserved_seats`
--

LOCK TABLES `reserved_seats` WRITE;
/*!40000 ALTER TABLE `reserved_seats` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserved_seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `route_number` bigint DEFAULT NULL,
  `flight_id` bigint DEFAULT NULL,
  `sequence_number` bigint DEFAULT NULL,
  `is_booking_allowed` tinyint(1) DEFAULT '0',
  KEY `flight_id` (`flight_id`),
  CONSTRAINT `route_ibfk_1` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,1,1,1),(2,2,1,1),(3,3,1,0),(4,4,1,1),(5,5,1,1),(6,6,1,1),(7,7,1,1),(8,8,1,0);
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES ('8b7e7549-d05f-48ad-ad27-e5be12e45ef0','0dfdd792-e5e2-4d1f-9460-51cc39f4f954',1734310960215,1734310971283,900,1734311871283,'admin');
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
INSERT INTO `spring_session_attributes` VALUES ('8b7e7549-d05f-48ad-ad27-e5be12e45ef0','org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN',_binary '�\�\0sr\06org.springframework.security.web.csrf.DefaultCsrfTokenZ\�\�/��\�\0L\0\nheaderNamet\0Ljava/lang/String;L\0\rparameterNameq\0~\0L\0tokenq\0~\0xpt\0X-CSRF-TOKENt\0_csrft\0$2f7926b6-8028-494b-bd1a-1e0486f96550'),('8b7e7549-d05f-48ad-ad27-e5be12e45ef0','SPRING_SECURITY_CONTEXT',_binary '�\�\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0l\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0l\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList�%1�\�\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0�\�^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx�\��\�a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0l\0L\0rolet\0Ljava/lang/String;xpt\0ADMINxq\0~\0\rpt\0<$2a$10$6bUAPEjfH6U2KzpXJLYzuuOI9tpx12eDil3hIjKEv0./8eGfalg.Kt\0admin');
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_admin` tinyint(1) DEFAULT NULL,
  `last_seen` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'client','client@gmail.com','$2a$10$yzmWTxg4GrwWx6t/u9NHQOxERmxfQ0r7gfcz.Mky6JtjxMvBJnY6m',0,'2024-12-15 08:55:03'),(3,'admin','admin@gmail.com','$2a$10$6bUAPEjfH6U2KzpXJLYzuuOI9tpx12eDil3hIjKEv0./8eGfalg.K',1,'2024-12-16 08:02:48'),(4,'client2','4.etsuko46060@fumemail.com','$2a$10$UVSDldP6SkcR7sZlsfFD0..E2mQVvk6PxdENcAFvP8nBO.X6gNS3.',0,'2024-12-09 15:38:35');
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

-- Dump completed on 2024-12-16  8:13:26
