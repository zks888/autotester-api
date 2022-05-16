/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.7
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 192.168.1.7:3406
 Source Schema         : elasticjob

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 16/05/2022 08:47:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for JOB_EXECUTION_LOG
-- ----------------------------
DROP TABLE IF EXISTS `JOB_EXECUTION_LOG`;
CREATE TABLE `JOB_EXECUTION_LOG` (
  `id` varchar(255) NOT NULL,
  `complete_time` datetime DEFAULT NULL,
  `execution_source` varchar(255) DEFAULT NULL,
  `failure_cause` varchar(255) DEFAULT NULL,
  `hostname` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `is_success` bit(1) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `sharding_item` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `task_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of JOB_EXECUTION_LOG
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for JOB_REGISTER_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `JOB_REGISTER_STATISTICS`;
CREATE TABLE `JOB_REGISTER_STATISTICS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `registered_count` int(11) DEFAULT NULL,
  `statistics_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of JOB_REGISTER_STATISTICS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for JOB_RUNNING_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `JOB_RUNNING_STATISTICS`;
CREATE TABLE `JOB_RUNNING_STATISTICS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `running_count` int(11) DEFAULT NULL,
  `statistics_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of JOB_RUNNING_STATISTICS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for JOB_STATUS_TRACE_LOG
-- ----------------------------
DROP TABLE IF EXISTS `JOB_STATUS_TRACE_LOG`;
CREATE TABLE `JOB_STATUS_TRACE_LOG` (
  `id` varchar(255) NOT NULL,
  `creation_time` datetime DEFAULT NULL,
  `execution_type` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `original_task_id` varchar(255) DEFAULT NULL,
  `sharding_item` varchar(255) DEFAULT NULL,
  `slave_id` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `task_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of JOB_STATUS_TRACE_LOG
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for TASK_RESULT_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `TASK_RESULT_STATISTICS`;
CREATE TABLE `TASK_RESULT_STATISTICS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `failed_count` bigint(20) DEFAULT NULL,
  `statistic_interval` varchar(10) DEFAULT NULL,
  `statistics_time` datetime NOT NULL,
  `success_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of TASK_RESULT_STATISTICS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for TASK_RUNNING_STATISTICS
-- ----------------------------
DROP TABLE IF EXISTS `TASK_RUNNING_STATISTICS`;
CREATE TABLE `TASK_RUNNING_STATISTICS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `running_count` int(11) DEFAULT NULL,
  `statistics_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of TASK_RUNNING_STATISTICS
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
