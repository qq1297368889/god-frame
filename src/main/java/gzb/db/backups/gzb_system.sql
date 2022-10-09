/*
 Navicat Premium Data Transfer

 Source Server         : localhost-root
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : gzb_system

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 09/10/2022 18:41:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content_manager
-- ----------------------------
DROP TABLE IF EXISTS `content_manager`;
CREATE TABLE `content_manager`  (
  `content_manager_id` bigint(19) NOT NULL,
  `content_manager_varchar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_manager_varchar1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_manager_varchar2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_manager_varchar3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_manager_text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `content_manager_text1` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `content_manager_text2` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `content_manager_text3` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `content_manager_time` datetime(0) NULL DEFAULT NULL,
  `content_manager_time1` datetime(0) NULL DEFAULT NULL,
  `content_manager_time2` datetime(0) NULL DEFAULT NULL,
  `content_manager_time3` datetime(0) NULL DEFAULT NULL,
  `content_manager_int` int(10) NULL DEFAULT NULL,
  `content_manager_int1` int(10) NULL DEFAULT NULL,
  `content_manager_int2` int(10) NULL DEFAULT NULL,
  `content_manager_int3` int(10) NULL DEFAULT NULL,
  `content_manager_double` double(10, 2) NULL DEFAULT NULL,
  `content_manager_double1` double(10, 2) NULL DEFAULT NULL,
  `content_manager_double2` double(10, 2) NULL DEFAULT NULL,
  `content_manager_double3` double(10, 2) NULL DEFAULT NULL,
  `content_manager_state` int(10) NULL DEFAULT NULL,
  `content_manager_users_id` bigint(19) NULL DEFAULT NULL,
  PRIMARY KEY (`content_manager_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_manager
-- ----------------------------
DROP TABLE IF EXISTS `file_manager`;
CREATE TABLE `file_manager`  (
  `file_manager_id` bigint(19) NOT NULL,
  `file_manager_md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_manager_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_manager_time` datetime(0) NULL DEFAULT NULL,
  `file_manager_state` int(10) NULL DEFAULT NULL,
  `file_manager_read_num` int(10) NULL DEFAULT NULL,
  `file_manager_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`file_manager_id`) USING BTREE,
  INDEX `suoyin_file_manager_md5`(`file_manager_md5`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gzb_api
-- ----------------------------
DROP TABLE IF EXISTS `gzb_api`;
CREATE TABLE `gzb_api`  (
  `gzb_api_id` bigint(19) NOT NULL,
  `gzb_api_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzb_api_state` int(10) NULL DEFAULT NULL,
  `gzb_api_code` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`gzb_api_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gzb_cache
-- ----------------------------
DROP TABLE IF EXISTS `gzb_cache`;
CREATE TABLE `gzb_cache`  (
  `gzb_cache_id` bigint(19) NOT NULL,
  `gzb_cache_key` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzb_cache_val` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `gzb_cache_end_time` datetime(0) NULL DEFAULT NULL,
  `gzb_cache_new_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`gzb_cache_id`) USING BTREE,
  INDEX `suoyin_gzb_cache_key`(`gzb_cache_key`) USING BTREE,
  INDEX `suoyin_gzb_cache_end_time`(`gzb_cache_end_time`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gzb_right
-- ----------------------------
DROP TABLE IF EXISTS `gzb_right`;
CREATE TABLE `gzb_right`  (
  `gzb_right_id` bigint(19) NOT NULL,
  `gzb_right_users_id` bigint(19) NULL DEFAULT NULL,
  `gzb_right_api_id` bigint(19) NULL DEFAULT NULL,
  PRIMARY KEY (`gzb_right_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for gzb_users
-- ----------------------------
DROP TABLE IF EXISTS `gzb_users`;
CREATE TABLE `gzb_users`  (
  `gzb_users_id` bigint(19) NOT NULL,
  `gzb_users_acc` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzb_users_pwd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzb_users_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzb_users_mailbox` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzb_users_time` datetime(0) NULL DEFAULT NULL,
  `gzb_users_state` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`gzb_users_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gzbtest
-- ----------------------------
DROP TABLE IF EXISTS `gzbtest`;
CREATE TABLE `gzbtest`  (
  `gzbTestId` bigint(19) NOT NULL,
  `gzbTestAcc` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gzbTestPwd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`gzbTestId`) USING BTREE,
  INDEX `sy_gzbTestAcc`(`gzbTestAcc`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `test_id` bigint(19) NOT NULL,
  `test_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `test_state` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`test_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
