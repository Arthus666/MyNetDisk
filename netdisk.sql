/*
 Navicat MySQL Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : netdisk

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 01/06/2022 16:15:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_admin` int(1) UNSIGNED ZEROFILL NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Admin', '123456', 1, '1795571225@qq.com');
INSERT INTO `user` VALUES (2, 'Harry', '123456', 0, '1305663753@qq.com');
INSERT INTO `user` VALUES (5, 'Ron', '123456', 0, '2751871645@qq.com');
INSERT INTO `user` VALUES (6, 'Hermione', '123456', 0, '2841523665@qq.com');

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_time` datetime NOT NULL,
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`user_id`) USING BTREE,
  CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_file
-- ----------------------------
INSERT INTO `user_file` VALUES (1, 2, 'GentleBoy1.jpg', '2022-04-01 17:15:06', 'image/jpeg', 'glyphicon glyphicon-picture', 'D:\\FileUpload\\Harry\\7c152c4d-bf8e-4c7d-9910-1e22d175f37b.jpg', '7.37 KB', '/MyNetDisk/fileDownload?username=Harry&&filename=7c152c4d-bf8e-4c7d-9910-1e22d175f37b.jpg');
INSERT INTO `user_file` VALUES (46, 2, '日出.png', '2022-04-01 17:15:42', 'image/png', 'glyphicon glyphicon-picture', 'D:\\FileUpload\\Harry\\d2194e25-535b-47de-ad04-407da77ba630.png', '40.50 KB', '/MyNetDisk/fileDownload?username=Harry&&filename=d2194e25-535b-47de-ad04-407da77ba630.png');
INSERT INTO `user_file` VALUES (47, 2, 'BaiJia4.mp4', '2022-04-01 17:16:42', 'video/mp4', 'glyphicon glyphicon-film', 'D:\\FileUpload\\Harry\\0990e827-c858-453c-94f9-b818769cec50.mp4', '38.53 MB', '/MyNetDisk/fileDownload?username=Harry&&filename=0990e827-c858-453c-94f9-b818769cec50.mp4');
INSERT INTO `user_file` VALUES (48, 2, '七律·软院十年.docx', '2022-04-01 17:17:11', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'glyphicon glyphicon-file', 'D:\\FileUpload\\Harry\\7b96b8c0-6984-4cc9-89bd-74f26e011b16.docx', '12.75 KB', '/MyNetDisk/fileDownload?username=Harry&&filename=7b96b8c0-6984-4cc9-89bd-74f26e011b16.docx');
INSERT INTO `user_file` VALUES (49, 2, 'Gluttonous_Snake_BGM.mp3', '2022-04-01 17:17:35', 'audio/mpeg', 'glyphicon glyphicon-music', 'D:\\FileUpload\\Harry\\4c11d7b4-c72c-46ff-9bc9-97bfc42a7343.mp3', '776.92 KB', '/MyNetDisk/fileDownload?username=Harry&&filename=4c11d7b4-c72c-46ff-9bc9-97bfc42a7343.mp3');
INSERT INTO `user_file` VALUES (50, 2, '大国崛起PPT.pptx', '2022-04-01 17:18:15', 'application/vnd.openxmlformats-officedocument.presentationml.presentation', 'glyphicon glyphicon-file', 'D:\\FileUpload\\Harry\\3e183153-1796-4b6f-8d70-1c9d726abea6.pptx', '4.26 MB', '/MyNetDisk/fileDownload?username=Harry&&filename=3e183153-1796-4b6f-8d70-1c9d726abea6.pptx');
INSERT INTO `user_file` VALUES (68, 2, '全频带阻塞干扰.pdf', '2022-04-10 11:50:49', 'application/pdf', 'glyphicon glyphicon-file', 'D:\\FileUpload\\Harry\\e62f14a5-e0f0-4f8c-897c-80e7ef36df32.pdf', '484.35 KB', '/MyNetDisk/fileDownload?username=Harry&&filename=e62f14a5-e0f0-4f8c-897c-80e7ef36df32.pdf');

SET FOREIGN_KEY_CHECKS = 1;
