-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.0 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for SecondHandGoods
CREATE DATABASE IF NOT EXISTS `SecondHandGoods` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `SecondHandGoods`;

-- Dumping structure for table SecondHandGoods.follow
CREATE TABLE IF NOT EXISTS `follow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `followee_id` int NOT NULL,
  `follower_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjhmtcmoxpgcojx2p3h7lcphsq` (`followee_id`),
  KEY `FKmow2qk674plvwyb4wqln37svv` (`follower_id`),
  CONSTRAINT `FKjhmtcmoxpgcojx2p3h7lcphsq` FOREIGN KEY (`followee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKmow2qk674plvwyb4wqln37svv` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.follow: ~0 rows (approximately)

-- Dumping structure for table SecondHandGoods.image
CREATE TABLE IF NOT EXISTS `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data` longtext,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgpextbyee3uk9u6o2381m7ft1` (`product_id`),
  CONSTRAINT `FKgpextbyee3uk9u6o2381m7ft1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.image: ~0 rows (approximately)

-- Dumping structure for table SecondHandGoods.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_at` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `rejection_reason` varchar(255) DEFAULT NULL,
  `warranty` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `review_id` int DEFAULT NULL,
  `state_id` int DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbvx1bbxgp0yuwtxicy9qwgd4d` (`review_id`),
  KEY `FK979liw4xk18ncpl87u4tygx2u` (`user_id`),
  KEY `FKlaipmic1opn2d4492kovh1pl7` (`state_id`),
  KEY `FKq3fvcsydiaotwy3iqn1erqsfd` (`type_id`),
  CONSTRAINT `FK63qfnwyiuhg8m8ywe84ehhj9` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`),
  CONSTRAINT `FK979liw4xk18ncpl87u4tygx2u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKlaipmic1opn2d4492kovh1pl7` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `FKq3fvcsydiaotwy3iqn1erqsfd` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.product: ~2 rows (approximately)
INSERT INTO `product` (`id`, `create_at`, `description`, `name`, `price`, `quantity`, `rejection_reason`, `warranty`, `user_id`, `review_id`, `state_id`, `type_id`) VALUES
	(1, 1736079364579, '5k/1 vỉ', 'Bông gòn gáy lỗi tai cho bé', 5000, 1, NULL, '3 ngày', 1, NULL, 1, 7),
	(2, 1736079566294, 'Áo len tự may tay cũ', 'Áo ấm cho bé', 30000, 1, NULL, 'Liên hệ', 2, NULL, 1, 7);

-- Dumping structure for table SecondHandGoods.review
CREATE TABLE IF NOT EXISTS `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `buyer_id` int NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `create_at` bigint DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6pjaikgsvldewqorimvx1r7ai` (`product_id`),
  CONSTRAINT `FKiyof1sindb9qiqr9o8npj8klt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.review: ~0 rows (approximately)

-- Dumping structure for table SecondHandGoods.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.role: ~3 rows (approximately)
INSERT INTO `role` (`id`, `role_name`) VALUES
	(1, 'ADMIN'),
	(2, 'STAFF'),
	(3, 'USER');

-- Dumping structure for table SecondHandGoods.state
CREATE TABLE IF NOT EXISTS `state` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.state: ~4 rows (approximately)
INSERT INTO `state` (`id`, `name`) VALUES
	(1, 'PENDING'),
	(2, 'REJECTED'),
	(3, 'ACCEPTED'),
	(4, 'SOLDOUT');

-- Dumping structure for table SecondHandGoods.transaction
CREATE TABLE IF NOT EXISTS `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_at` varchar(255) DEFAULT NULL,
  `fluctuation` double DEFAULT NULL,
  `status` enum('FAILURE','SUCCESS') DEFAULT NULL,
  `transaction_type` enum('DEDUCT','DEPOSIT','REFUND') DEFAULT NULL,
  `user_id` int NOT NULL,
  `txnref` int DEFAULT NULL,
  `bank_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsg7jp0aj6qipr50856wf6vbw1` (`user_id`),
  CONSTRAINT `FKsg7jp0aj6qipr50856wf6vbw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.transaction: ~1 rows (approximately)
INSERT INTO `transaction` (`id`, `create_at`, `fluctuation`, `status`, `transaction_type`, `user_id`, `txnref`, `bank_code`) VALUES
	(7, '20250108132527', 300000, 'SUCCESS', 'DEPOSIT', 3, 12544939, 'NCB');

-- Dumping structure for table SecondHandGoods.type
CREATE TABLE IF NOT EXISTS `type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.type: ~12 rows (approximately)
INSERT INTO `type` (`id`, `type_name`) VALUES
	(1, 'Đồ gia dụng'),
	(2, 'Đồ điện tử'),
	(3, 'Đồ nội thất'),
	(4, 'Điện thoại, Máy tính bảng'),
	(5, 'Laptop'),
	(6, 'Dụng cụ công, nông nghiệp'),
	(7, 'Mẹ và bé'),
	(8, 'Bất động sản'),
	(9, 'Phương tiện'),
	(10, 'Thời trang, phụ kiện, đồ dùng cá nhân'),
	(11, 'Sách'),
	(12, 'Giải trí, thể thao, sở thích');

-- Dumping structure for table SecondHandGoods.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_state` enum('LOCK','ONLINE','PENDING') DEFAULT NULL,
  `activation` bit(1) DEFAULT NULL,
  `activation_code` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.user: ~4 rows (approximately)
INSERT INTO `user` (`id`, `account_state`, `activation`, `activation_code`, `address`, `balance`, `email`, `password`, `phone`, `username`, `role_id`) VALUES
	(1, 'ONLINE', b'0', NULL, 'Thành Phố Bạc Liêu', NULL, 'helllo112233@gmail.com', '$2a$10$8JqEY6WO5kjB3QzoHhhxIOpo41Ftq1u/cMhVQ6xosSFlMPFiFK/.K', '0945046925', 'chieu123222', 1),
	(2, 'PENDING', b'0', NULL, 'Trần Phú', NULL, 'son123@gmail.com', '$2a$10$.RtO.7B/LRWB8gG1SHhS3O3uzxotsDp0Nf.s7mamm2YfGdr4xW38m', '0933212311', 'sonhai123', 3),
	(3, 'PENDING', b'0', NULL, 'Bắc Ninh', 300000, 'son12223@gmail.com', '$2a$10$/gPoEfDO67HmCFCb3YHl8eqMwXTfVioxQObhB1gZCwYjzvcFz3BzS', '093321222', 'xuanson123', 3),
	(5, 'ONLINE', b'1', 'c4f89cb6-f2cf-402e-80e3-781f4dbbf644', 'Thành Phố Bạc Liêu', 0, 'ndchieu73@gmail.com', '$2a$10$Ec0vDVX437.bocK3leiUZeBpqgUl3f/q2olOEC4E3RyfVqBxOo0n.', '0945013925', 'ndchieu73', 3);

-- Dumping structure for table SecondHandGoods.user_wishlist
CREATE TABLE IF NOT EXISTS `user_wishlist` (
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  KEY `FK2oknuoi371ly7gtylfxbwlk9` (`product_id`),
  KEY `FKclurv83y878u3we8ya7yjgd5m` (`user_id`),
  CONSTRAINT `FK2oknuoi371ly7gtylfxbwlk9` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKclurv83y878u3we8ya7yjgd5m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table SecondHandGoods.user_wishlist: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
