/*
 Navicat Premium Data Transfer

 Source Server         : database
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : bookmanagementsys

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 19/12/2024 11:22:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts`  (
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `usertype` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户类型',
  `banuntil` date NULL DEFAULT NULL COMMENT '禁言截止日期',
  UNIQUE INDEX `accountlimit`(`account` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userprofile
-- ----------------------------
DROP TABLE IF EXISTS `userprofile`;
CREATE TABLE `userprofile`  (
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  UNIQUE INDEX `idx_account`(`account` ASC) USING BTREE,
  CONSTRAINT `accountwithprofile` FOREIGN KEY (`account`) REFERENCES `accounts` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for InsertAndUpdateUserProfile
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertAndUpdateUserProfile`;
delimiter ;;
CREATE PROCEDURE `InsertAndUpdateUserProfile`(IN p_account VARCHAR(50),       
    IN p_password VARCHAR(50),      
    IN p_usertype VARCHAR(10),      
    IN p_age INT,                   
    IN p_sex ENUM('male', 'female'), 
    IN p_username VARCHAR(50),      
    IN p_phone VARCHAR(20),         
    IN p_address VARCHAR(255),      
    IN p_email VARCHAR(100),        
    IN p_introduce TEXT)
BEGIN
    
    INSERT INTO accounts (account, password, usertype, banuntil)
    VALUES (p_account, p_password, p_usertype, NULL);

    
    UPDATE UserProfile
    SET
        username = p_username,
        age = p_age,
        sex = p_sex,
        phone = p_phone,
        address = p_address,
        email = p_email,
        introduce = p_introduce,
        updatetime = NOW()
    WHERE account = p_account;

   
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table accounts
-- ----------------------------
DROP TRIGGER IF EXISTS `InsertToUserProfile`;
delimiter ;;
CREATE TRIGGER `InsertToUserProfile` AFTER INSERT ON `accounts` FOR EACH ROW INSERT INTO UserProfile (account, username, age, sex, phone, address, email, introduce, createtime, updatetime)
    VALUES (NEW.account, NEW.account, 0, '', '', '', '', '', NOW(), NOW())
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
