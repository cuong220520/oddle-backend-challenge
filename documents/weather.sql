DROP DATABASE IF EXISTS `oddle-backend-challenge`;

CREATE DATABASE IF NOT EXISTS `oddle-backend-challenge`;

-- `oddle-backend-challenge`.weather definition

DROP TABLE IF EXISTS `oddle-backend-challenge`.`weather`;

CREATE TABLE IF NOT EXISTS `oddle-backend-challenge`.`weather` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cloud` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dt` bigint DEFAULT NULL,
  `feels_like` double NOT NULL,
  `grnd_level` double DEFAULT NULL,
  `humidity` double NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `main_status` varchar(255) DEFAULT NULL,
  `pressure` double NOT NULL,
  `rain` double DEFAULT NULL,
  `sea_level` double DEFAULT NULL,
  `sunrise` varchar(255) DEFAULT NULL,
  `sunset` varchar(255) DEFAULT NULL,
  `temp` double NOT NULL,
  `temp_max` double NOT NULL,
  `temp_min` double NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `wind_deg` double NOT NULL,
  `wind_gust` double NOT NULL,
  `wind_speed` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
