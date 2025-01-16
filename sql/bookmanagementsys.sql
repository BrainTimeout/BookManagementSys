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

 Date: 23/12/2024 18:36:56
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
  `banuntil` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '禁言截止日期',
  `balance` int NULL DEFAULT 100 COMMENT '账户余额',
  UNIQUE INDEX `accountlimit`(`account` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of accounts
-- ----------------------------
INSERT INTO `accounts` VALUES ('123456789', '9bb211e546fd6cbf584b02ac4be91873', 'user', '2024-12-23 03:01:00', 155);
INSERT INTO `accounts` VALUES ('234567890', '433f29a8a00ea64d7043145104b88462', 'user', '2024-12-23 03:03:25', 300);
INSERT INTO `accounts` VALUES ('987654321', '433f29a8a00ea64d7043145104b88462', 'user', '2024-12-23 03:02:13', 500);
INSERT INTO `accounts` VALUES ('laoda', 'a9a2aa9d7fe830af9e51875e405895cf', 'user', '2024-12-23 14:48:28', 265);
INSERT INTO `accounts` VALUES ('root', 'b0317912fbf936a45e27d33f0b8d085f', 'admin', '2024-12-23 02:54:13', 135);
INSERT INTO `accounts` VALUES ('tangxiaotian', '83dfdeb4cfc051c0753caea24a758a76', 'user', '2024-12-23 14:41:05', 90);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `publishDate` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '出版日期',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '出版社',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类',
  `score` int NULL DEFAULT NULL COMMENT '所需积分',
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标准码',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `nums` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `bookNo_index`(`bookNo` ASC) USING BTREE,
  INDEX `categoryindex`(`category` ASC) USING BTREE,
  CONSTRAINT `categoryindex` FOREIGN KEY (`category`) REFERENCES `category` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (21, 'JavaScript 从入门到精通', '本书深入讲解了 JavaScript 的基础和高级特性，适合各个层次的开发者学习。', '2023-05-10T16:00:00.000Z', '李明', '电子工业出版社', '计算机技术', 5, 'JS20230501', 'http://localhost:9090/api/File/Download/Cover/JS20230501.jpg?time=1734892329700', '2024-12-23 02:32:16', '2024-12-23 16:33:35', 0000000002);
INSERT INTO `book` VALUES (22, 'Vue.js 权威指南', '一本全面讲解 Vue.js 的书籍，涵盖 Vue.js 框架的各个方面', '2022-12-05T16:00:00.000Z', '王强', '人民邮电出版社', '计算机技术', 5, 'VUE20220815', 'http://localhost:9090/api/File/Download/Cover/VUE20220815.jpg?time=1734892492910', '2024-12-23 02:34:54', '2024-12-23 16:33:40', 0000000004);
INSERT INTO `book` VALUES (23, 'Python 高级编程', '本书深入分析 Python 的高级特性，包括并发编程和内存管理。', '2021-11-19T18:34:59.000Z', '张杰', '机械工业出版社', '计算机技术', 7, 'PY20211120', 'http://localhost:9090/api/File/Download/Cover/PY20211120.jpg?time=1734892600690', '2024-12-23 02:36:48', '2024-12-23 16:33:44', 0000000009);
INSERT INTO `book` VALUES (24, '红楼梦', '《红楼梦》是中国古代四大名著之一，描述了贾宝玉与林黛玉等人物的生活。', '1791-01-01', '曹雪芹', '人民文学出版社', '小说', 5, 'B0001', 'http://localhost:9090/api/File/Download/Cover/B0001.jpg?time=1734892949461', '2024-12-23 02:40:14', '2024-12-23 16:33:49', 0000000007);
INSERT INTO `book` VALUES (25, '活着', '《活着》是余华的长篇小说，讲述了一个普通家庭的悲剧故事。', '1993-01-01', '余华', '作家出版社', '小说', 4, 'B0002', 'http://localhost:9090/api/File/Download/Cover/B0002.jpg?time=1734892977996', '2024-12-23 02:40:14', '2024-12-23 16:33:53', 0000000005);
INSERT INTO `book` VALUES (26, '唐诗三百首', '《唐诗三百首》是唐代诗歌的选集，收录了众多名篇。', '1700-01-01', '佚名', '中华书局', '诗歌', 5, 'B0003', 'http://localhost:9090/api/File/Download/Cover/B0003.jpg?time=1734893007124', '2024-12-23 02:40:14', '2024-12-23 17:11:32', 0000000009);
INSERT INTO `book` VALUES (27, '诗经', '《诗经》是中国最早的诗歌总集，涵盖了从西周至春秋的诗歌。', '600-01-01', '佚名', '中华书局', '诗歌', 4, 'B0004', 'http://localhost:9090/api/File/Download/Cover/B0004.jpg?time=1734893044281', '2024-12-23 02:40:14', '2024-12-23 16:34:04', 0000000015);
INSERT INTO `book` VALUES (28, '大明王朝1566', '《大明王朝1566》是对明朝历史的深刻剖析，讲述了明朝中期的政治风云。', '2012-01-01', '黄仁宇', '三联书店', '现代历史', 5, 'B0005', 'http://localhost:9090/api/File/Download/Cover/B0005.jpg?time=1734893087128', '2024-12-23 02:40:14', '2024-12-23 16:34:07', 0000000008);
INSERT INTO `book` VALUES (29, '二战简史', '《二战简史》是一本简明扼要的二战历史总结，适合历史爱好者阅读。', '2005-01-01', '安东尼·比弗', '人民出版社', '现代历史', 4, 'B0006', 'http://localhost:9090/api/File/Download/Cover/B0006.jpg?time=1734893122450', '2024-12-23 02:40:14', '2024-12-23 16:34:11', 0000000005);
INSERT INTO `book` VALUES (30, '史记', '《史记》是中国古代史学的经典著作，由司马迁所著。', '91-01-01', '司马迁', '中华书局', '古代历史', 5, 'B0007', 'http://localhost:9090/api/File/Download/Cover/B0007.jpg?time=1734893150080', '2024-12-23 02:40:14', '2024-12-23 16:34:16', 0000000008);
INSERT INTO `book` VALUES (31, '大元大一统志', '《大元大一统志》是元代官方编纂的地理志，记载了中国各地的历史和地理。', '1340-01-01', '王珪', '中华书局', '古代历史', 4, 'B0008', 'http://localhost:9090/api/File/Download/Cover/B0008.jpg?time=1734893214087', '2024-12-23 02:40:14', '2024-12-23 16:34:21', 0000000016);
INSERT INTO `book` VALUES (32, '社会心理学', '《社会心理学》是一本经典的社会心理学教材，探讨人类社会行为。', '2001-01-01', '戴维·迈尔斯', '高等教育出版社', '社会心理学', 5, 'B0009', 'http://localhost:9090/api/File/Download/Cover/B0009.jpg?time=1734893244547', '2024-12-23 02:40:14', '2024-12-23 16:34:25', 0000000020);
INSERT INTO `book` VALUES (33, '影响力', '《影响力》是一本研究人类行为与决策的经典之作，讲述了六个影响力法则。', '1984-01-01', '罗伯特·西奥迪尼', '中信出版社', '社会心理学', 5, 'B0010', 'http://localhost:9090/api/File/Download/Cover/B0010.jpg?time=1734893304165', '2024-12-23 02:40:14', '2024-12-23 16:26:10', 0000000002);
INSERT INTO `book` VALUES (34, '思考，快与慢', '《思考，快与慢》讲述了人类决策的两种思维方式：直觉与理性。', '2011-01-01', '丹尼尔·卡尼曼', '中信出版社', '个人成长', 5, 'B0011', 'http://localhost:9090/api/File/Download/Cover/B0011.jpg?time=1734893339752', '2024-12-23 02:40:14', '2024-12-23 17:24:10', 0000000016);
INSERT INTO `book` VALUES (35, '习惯的力量', '《习惯的力量》是一本探索习惯如何影响生活与工作的书籍。', '2012-01-01', '查尔斯·杜希格', '中信出版社', '个人成长', 4, 'B0012', 'http://localhost:9090/api/File/Download/Cover/B0012.jpg?time=1734893371132', '2024-12-23 02:40:14', '2024-12-23 16:34:31', 0000000009);
INSERT INTO `book` VALUES (36, '艺术的故事', '《艺术的故事》是一本艺术史的入门书籍，介绍了从古代到现代的艺术流派。', '1950-01-01', '恩斯特·贡布里希', '上海人民出版社', '绘画艺术', 5, 'B0013', 'http://localhost:9090/api/File/Download/Cover/B0013.jpg?time=1734893396872', '2024-12-23 02:40:14', '2024-12-23 16:26:01', 0000000018);
INSERT INTO `book` VALUES (37, '色彩的秘密', '《色彩的秘密》探讨了色彩在艺术中的应用以及它的心理学意义。', '2003-01-01', '约瑟夫·阿尔伯斯', '高等教育出版社', '绘画艺术', 4, 'B0014', 'http://localhost:9090/api/File/Download/Cover/B0014.jpg?time=1734893447210', '2024-12-23 02:40:14', '2024-12-23 16:27:51', 0000000004);
INSERT INTO `book` VALUES (39, '直升机飞行原理', '书稿为教材，针对直升机操纵和飞行执照要求的基础理论知识，从直升机飞行操纵的角度出发，阐述了旋翼类航空器运动和控制所遵循的基本知识，涉及低速和高速飞行时的旋翼空气动力特性、直升机平衡性和稳操性、不同飞行状态的飞行性能和操纵技术等，通过理解直升机旋翼的挥舞运动，弄清直升机操纵的工作机理，通过改变旋翼挥舞来完成对直升机的操纵控制。这将对直升机飞行技术、通航运行和机务维修等方面的拓展有着积极和深远意义，本书可作为航空院校直升机飞行技术、直升机机务维修专业教材，也可供相关科技人员以及航空爱好者学习参考。', '2017-05-31T16:00:00.000Z', '王永虎', '西南交通大学出版社', '航空航天技术', 5, '9788745', 'http://localhost:9090/api/File/Download/Cover/9788745.jpg?time=1734936929888', '2024-12-23 14:54:45', '2024-12-23 16:42:09', 0000000004);
INSERT INTO `book` VALUES (40, '曾国藩传', '知名历史学者张宏杰代表作，新增万字“曾国藩的遗产”，俞敏洪、马伯庸、李尚龙推荐。一个持续奋斗者的升级之道。从笨人到圣人，经历五次耻辱，如何步步升级，脱胎换骨？附赠曾国藩家书典藏本', '2020-12-07T16:00:00.000Z', '张宏杰', '民主与建设出版社', '传记', 6, 'B9854', 'http://localhost:9090/api/File/Download/Cover/B9854.jpg?time=1734943052542', '2024-12-23 16:37:07', '2024-12-23 16:40:04', 0000000006);
INSERT INTO `book` VALUES (41, '孔子传', '鲍鹏山经典作品。由中国青年出版社2013年1月出版，自出版后每年加印。\n本书以“十五志于学”、“三十而立”、“四十不惑”、“五十知天命”、“六十耳顺”、“七十从心所欲”为题，分析六章，为孔子作传。其*宗旨，是为非专业人士，即普通读者，提供一部精当、博约、生动活泼、有价值的孔子传，叙述其一生行迹、事迹、心迹的同时，阐发作为思想家孔子的现代价值与当下意义。\n本书32开精装本，设计独特。并由中国孔子基金会授权，首次连载明代名臣张楷手绘原本《孔子圣迹图》17幅。这是目前*发现的、中国*早描绘孔子生平故事的连环画长卷。', '2019-12-01T16:00:00.000Z', '鲍鹏山', '中国青年出版社', '传记', 6, 'B4912', 'http://localhost:9090/api/File/Download/Cover/B4912.jpg?time=1734943164013', '2024-12-23 16:39:25', '2024-12-23 17:10:33', 0000000011);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '书籍id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `days` int NULL DEFAULT NULL COMMENT '借阅天数',
  `dueDate` datetime NULL DEFAULT NULL COMMENT '借书到期时间',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (67, '9788745', 'laoda', 10, '2024-12-22 00:00:00', '2024-12-13 16:26:26', '2024-12-13 16:26:26');
INSERT INTO `borrow` VALUES (68, 'B0014', 'laoda', 20, '2024-12-30 00:00:00', '2024-12-10 16:27:51', '2024-12-10 16:27:51');
INSERT INTO `borrow` VALUES (69, 'VUE20220815', 'tangxiaotian', 1, '2024-12-20 00:00:00', '2024-12-19 16:28:16', '2024-12-19 16:28:16');
INSERT INTO `borrow` VALUES (71, 'B0001', '234567890', 15, '2025-01-05 00:00:00', '2024-12-21 11:00:00', '2024-12-21 11:00:00');
INSERT INTO `borrow` VALUES (72, 'B0002', '987654321', 3, '2024-12-25 00:00:00', '2024-12-22 12:15:00', '2024-12-22 12:15:00');
INSERT INTO `borrow` VALUES (73, 'B0003', 'laoda', 10, '2024-12-28 00:00:00', '2024-12-23 13:00:00', '2024-12-23 13:00:00');
INSERT INTO `borrow` VALUES (74, 'B0004', 'root', 5, '2024-12-27 00:00:00', '2024-12-24 14:45:00', '2024-12-24 14:45:00');
INSERT INTO `borrow` VALUES (75, 'B0005', 'tangxiaotian', 14, '2025-01-07 00:00:00', '2024-12-21 09:30:00', '2024-12-21 09:30:00');
INSERT INTO `borrow` VALUES (76, 'B0006', '123456789', 8, '2024-12-31 00:00:00', '2024-12-19 16:20:00', '2024-12-19 16:20:00');
INSERT INTO `borrow` VALUES (77, 'B0007', '234567890', 12, '2025-01-03 00:00:00', '2024-12-18 10:10:00', '2024-12-18 10:10:00');
INSERT INTO `borrow` VALUES (78, 'B0008', '987654321', 6, '2024-12-26 00:00:00', '2024-12-17 14:50:00', '2024-12-17 14:50:00');
INSERT INTO `borrow` VALUES (79, 'B0009', 'laoda', 9, '2024-12-30 00:00:00', '2024-12-22 11:35:00', '2024-12-22 11:35:00');
INSERT INTO `borrow` VALUES (80, 'B4912', 'test', 10, '2025-01-02 00:00:00', '2024-12-23 17:10:33', '2024-12-23 17:10:33');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `pid` int NULL DEFAULT NULL COMMENT '父级id',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name` ASC) USING BTREE,
  INDEX `pid_index`(`pid` ASC) USING BTREE,
  CONSTRAINT `pid_index` FOREIGN KEY (`pid`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2157 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (2131, '计算机科学与技术', '计算机领域', NULL, '2024-12-23 02:28:25', '2024-12-23 02:29:17');
INSERT INTO `category` VALUES (2132, '计算机基础', '计算机基础知识', 2131, '2024-12-23 02:29:24', '2024-12-23 02:29:24');
INSERT INTO `category` VALUES (2133, '人工智能', '人工智能相关书籍', 2131, '2024-12-23 02:29:43', '2024-12-23 02:29:43');
INSERT INTO `category` VALUES (2134, '计算机技术', '计算机相关技术', 2131, '2024-12-23 02:30:16', '2024-12-23 02:30:16');
INSERT INTO `category` VALUES (2135, '文学', '包括各种文学作品和小说', NULL, '2024-12-23 02:38:38', '2024-12-23 02:38:38');
INSERT INTO `category` VALUES (2136, '历史', '历史书籍和历史事件的分析', NULL, '2024-12-23 02:38:38', '2024-12-23 02:38:38');
INSERT INTO `category` VALUES (2137, '心理学', '研究心理学的书籍', NULL, '2024-12-23 02:38:38', '2024-12-23 02:38:38');
INSERT INTO `category` VALUES (2138, '自我提升', '关于个人成长和自我发展的书籍', NULL, '2024-12-23 02:38:38', '2024-12-23 02:38:38');
INSERT INTO `category` VALUES (2139, '艺术', '关于艺术的理论和实践', NULL, '2024-12-23 02:38:38', '2024-12-23 02:38:38');
INSERT INTO `category` VALUES (2145, '小说', '各种类型的小说', 2135, '2024-12-23 02:39:22', '2024-12-23 02:39:22');
INSERT INTO `category` VALUES (2146, '诗歌', '诗歌类文学作品', 2135, '2024-12-23 02:39:22', '2024-12-23 02:39:22');
INSERT INTO `category` VALUES (2147, '现代历史', '20世纪及以后的历史书籍', 2136, '2024-12-23 02:39:22', '2024-12-23 02:39:22');
INSERT INTO `category` VALUES (2148, '古代历史', '关于古代文明的书籍', 2136, '2024-12-23 02:39:22', '2024-12-23 02:39:22');
INSERT INTO `category` VALUES (2149, '社会心理学', '心理学与社会行为交叉的书籍', 2137, '2024-12-23 02:39:22', '2024-12-23 02:39:22');
INSERT INTO `category` VALUES (2150, '个人成长', '提升个人生活质量的书籍', 2138, '2024-12-23 02:39:22', '2024-12-23 02:39:22');
INSERT INTO `category` VALUES (2151, '绘画艺术', '关于绘画技巧和艺术史的书籍', 2139, '2024-12-23 02:39:22', '2024-12-23 02:52:05');
INSERT INTO `category` VALUES (2152, '工程技术', '工程类书籍', NULL, '2024-12-23 15:04:35', '2024-12-23 15:04:35');
INSERT INTO `category` VALUES (2153, '航空航天技术', '航空航天类书籍', 2152, '2024-12-23 15:05:05', '2024-12-23 15:05:05');
INSERT INTO `category` VALUES (2154, '传记', '人物传记', 2135, '2024-12-23 16:39:55', '2024-12-23 16:39:55');
INSERT INTO `category` VALUES (2155, 'TEST', '测试分类', NULL, '2024-12-23 16:40:28', '2024-12-23 16:40:28');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bookBo_index`(`bookNo` ASC) USING BTREE,
  INDEX `fk_account_username`(`account` ASC, `username` ASC) USING BTREE,
  CONSTRAINT `fk_account_username` FOREIGN KEY (`account`, `username`) REFERENCES `userprofile` (`account`, `username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (27, 'root', 'JS20230501', '赞！都来看！！', 'root');
INSERT INTO `comment` VALUES (28, 'tangxiaotian', 'JS20230501', '《JavaScript 从入门到精通》是一本非常受欢迎的学习 JavaScript 的书籍，它通常适合初学者以及有一定编程基础但想进一步提高的开发者。', '唐小天');
INSERT INTO `comment` VALUES (29, 'tangxiaotian', 'B0001', '《红楼梦》真是一本细腻的书。每个角色都有复杂的情感和命运，特别是贾宝玉和林黛玉的关系，看得我心情起伏。虽然有点悲伤，但也很真实，像是人生的一部分。', '唐小天');
INSERT INTO `comment` VALUES (30, 'laoda', '9788745', 'man!what can i say?', '科比·布莱恩特');
INSERT INTO `comment` VALUES (31, 'root', 'B0006', '上岸！\n', 'root');
INSERT INTO `comment` VALUES (32, 'root', 'B0009', '好看！！！', 'root');
INSERT INTO `comment` VALUES (33, 'root', 'B0005', '爱看！', 'root');
INSERT INTO `comment` VALUES (34, 'root', 'B9854', '顶！！！', 'root');
INSERT INTO `comment` VALUES (36, 'root', 'B0010', '影响力！！！', 'root');

-- ----------------------------
-- Table structure for revert
-- ----------------------------
DROP TABLE IF EXISTS `revert`;
CREATE TABLE `revert`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '书籍编号',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `days` int NULL DEFAULT NULL COMMENT '借阅天数',
  `revertDate` datetime NULL DEFAULT NULL COMMENT '实际归还日期',
  `dueDate` datetime NULL DEFAULT NULL COMMENT '借书到期时间',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of revert
-- ----------------------------
INSERT INTO `revert` VALUES (56, '9788745', '123456789', 1, '2024-12-23 00:00:00', '2024-12-29 00:00:00', '2024-12-23 16:42:09', '2024-12-23 16:42:09');
INSERT INTO `revert` VALUES (57, '9788745', '123456789', 1, '2024-12-23 00:00:00', '2024-12-29 00:00:00', '2024-12-23 16:42:09', '2024-12-23 16:42:09');
INSERT INTO `revert` VALUES (58, 'B0001', '234567890', 3, '2024-12-20 00:00:00', '2024-12-23 00:00:00', '2024-12-20 14:30:00', '2024-12-20 14:30:00');
INSERT INTO `revert` VALUES (59, 'B0002', '987654321', 5, '2024-12-22 00:00:00', '2024-12-25 00:00:00', '2024-12-22 16:10:00', '2024-12-22 16:10:00');
INSERT INTO `revert` VALUES (60, 'B0003', 'laoda', 7, '2024-12-18 00:00:00', '2024-12-23 00:00:00', '2024-12-18 12:00:00', '2024-12-18 12:00:00');
INSERT INTO `revert` VALUES (61, 'B0004', 'root', 2, '2024-12-19 00:00:00', '2024-12-21 00:00:00', '2024-12-19 17:00:00', '2024-12-19 17:00:00');
INSERT INTO `revert` VALUES (62, 'B0005', 'tangxiaotian', 4, '2024-12-21 00:00:00', '2024-12-25 00:00:00', '2024-12-21 15:20:00', '2024-12-21 15:20:00');
INSERT INTO `revert` VALUES (63, 'B0006', '123456789', 6, '2024-12-16 00:00:00', '2024-12-22 00:00:00', '2024-12-16 10:40:00', '2024-12-16 10:40:00');
INSERT INTO `revert` VALUES (64, 'B0007', '234567890', 9, '2024-12-18 00:00:00', '2024-12-27 00:00:00', '2024-12-18 13:30:00', '2024-12-18 13:30:00');
INSERT INTO `revert` VALUES (65, 'B0008', '987654321', 8, '2024-12-17 00:00:00', '2024-12-25 00:00:00', '2024-12-17 11:50:00', '2024-12-17 11:50:00');
INSERT INTO `revert` VALUES (66, 'B0009', 'laoda', 10, '2024-12-19 00:00:00', '2024-12-29 00:00:00', '2024-12-19 09:15:00', '2024-12-19 09:15:00');
INSERT INTO `revert` VALUES (67, 'B0010', 'root', 1, '2024-12-21 00:00:00', '2024-12-22 00:00:00', '2024-12-21 14:10:00', '2024-12-21 14:10:00');
INSERT INTO `revert` VALUES (68, 'B0011', 'tangxiaotian', 4, '2024-12-20 00:00:00', '2024-12-24 00:00:00', '2024-12-20 16:25:00', '2024-12-20 16:25:00');
INSERT INTO `revert` VALUES (69, 'B0012', '123456789', 3, '2024-12-17 00:00:00', '2024-12-20 00:00:00', '2024-12-17 12:00:00', '2024-12-17 12:00:00');
INSERT INTO `revert` VALUES (70, 'B0013', '234567890', 5, '2024-12-19 00:00:00', '2024-12-24 00:00:00', '2024-12-19 10:10:00', '2024-12-19 10:10:00');
INSERT INTO `revert` VALUES (71, 'B0014', '987654321', 6, '2024-12-22 00:00:00', '2024-12-28 00:00:00', '2024-12-22 14:30:00', '2024-12-22 14:30:00');
INSERT INTO `revert` VALUES (72, 'B4912', 'laoda', 9, '2024-12-23 00:00:00', '2024-12-31 00:00:00', '2024-12-23 08:45:00', '2024-12-23 08:45:00');
INSERT INTO `revert` VALUES (73, 'B9854', 'root', 4, '2024-12-20 00:00:00', '2024-12-24 00:00:00', '2024-12-20 17:30:00', '2024-12-20 17:30:00');
INSERT INTO `revert` VALUES (74, 'JS20230501', 'tangxiaotian', 2, '2024-12-15 00:00:00', '2024-12-17 00:00:00', '2024-12-15 13:40:00', '2024-12-15 13:40:00');
INSERT INTO `revert` VALUES (75, 'PY20211120', '123456789', 7, '2024-12-22 00:00:00', '2024-12-29 00:00:00', '2024-12-22 11:00:00', '2024-12-22 11:00:00');
INSERT INTO `revert` VALUES (76, 'VUE20220815', '234567890', 11, '2024-12-17 00:00:00', '2024-12-28 00:00:00', '2024-12-17 18:00:00', '2024-12-17 18:00:00');

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
  UNIQUE INDEX `idx_account_username`(`account` ASC, `username` ASC) USING BTREE,
  CONSTRAINT `accountwithprofile` FOREIGN KEY (`account`) REFERENCES `accounts` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userprofile
-- ----------------------------
INSERT INTO `userprofile` VALUES ('123456789', '张伟', 28, 'male', '13800138000', '北京市朝阳区建国路', 'zhangwei@example.com', '喜欢编程和阅读', '2024-12-23 03:01:00', '2024-12-23 03:01:00', NULL);
INSERT INTO `userprofile` VALUES ('234567890', '王强', 32, 'male', '13700137000', '深圳市南山区科技园', 'wangqiang@example.com', '热爱运动，特别喜欢足球', '2024-12-23 03:03:25', '2024-12-23 03:03:25', NULL);
INSERT INTO `userprofile` VALUES ('987654321', '李娜', 28, 'female', '13900139000', '上海市浦东新区陆家嘴', 'lina@example.com', '爱好旅游和美食', '2024-12-23 03:02:13', '2024-12-23 03:02:13', NULL);
INSERT INTO `userprofile` VALUES ('laoda', '科比·布莱恩特', 41, 'male', '13105551234', '美国加利福尼亚州洛杉矶市', 'kobe.bryant@email.com', '前职业篮球运动员，五次NBA总冠军得主，著名的“黑曼巴”精神代表，热爱篮球、家庭和慈善事业。', '2024-12-23 14:48:28', '2024-12-23 14:48:28', NULL);
INSERT INTO `userprofile` VALUES ('root', 'root', 100, 'male', '18764648609', 'root', '34325432563@qq.com', 'root', '2024-12-23 02:54:13', '2024-12-23 16:10:34', NULL);
INSERT INTO `userprofile` VALUES ('tangxiaotian', '唐小天', 18, 'male', '18693535904', '西北工业大学', 'tangxiaotian@nwpu.com', '航小天是2017年“中国航天日”活动吉祥物，于2017年4月17日发布。 [1]形象由西北工业大学学生张静设计。 [2]\n2020年4月24日，“中国航天日”旗帜通过线上方式，由中国航天卡通形象“航小梦”“航小天”从湖南大学传递给厦门大学和福州大学完成了旗帜交接。', '2024-12-23 14:41:05', '2024-12-23 14:45:47', NULL);

-- ----------------------------
-- View structure for borrowprofile
-- ----------------------------
DROP VIEW IF EXISTS `borrowprofile`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `borrowprofile` AS select distinct `book`.`name` AS `name`,`borrow`.`account` AS `account`,`borrow`.`dueDate` AS `dueDate`,`borrow`.`createtime` AS `createtime`,`borrow`.`updatetime` AS `updatetime`,`userprofile`.`phone` AS `phone`,`userprofile`.`username` AS `username`,`borrow`.`id` AS `id`,`book`.`score` AS `score`,`borrow`.`bookNo` AS `bookNo`,`borrow`.`days` AS `days` from ((`book` join `borrow` on((`book`.`bookNo` = `borrow`.`bookNo`))) join `userprofile` on((`borrow`.`account` = `userprofile`.`account`)));

-- ----------------------------
-- View structure for revertprofile
-- ----------------------------
DROP VIEW IF EXISTS `revertprofile`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `revertprofile` AS select `book`.`score` AS `score`,`book`.`name` AS `name`,`revert`.`id` AS `id`,`revert`.`bookNo` AS `bookNo`,`revert`.`account` AS `account`,`revert`.`days` AS `days`,`revert`.`revertDate` AS `revertDate`,`revert`.`dueDate` AS `dueDate`,`revert`.`createtime` AS `createtime`,`revert`.`updatetime` AS `updatetime`,`userprofile`.`phone` AS `phone`,`userprofile`.`username` AS `username` from ((`book` join `revert` on((`book`.`bookNo` = `revert`.`bookNo`))) join `userprofile` on((`revert`.`account` = `userprofile`.`account`)));

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
