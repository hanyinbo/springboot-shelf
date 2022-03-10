/*
 Navicat Premium Data Transfer

 Source Server         : 本地wallUser
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : aison

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 10/03/2022 20:11:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wx_swiper_img
-- ----------------------------
DROP TABLE IF EXISTS `wx_swiper_img`;
CREATE TABLE `wx_swiper_img`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `img_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `navigator_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片跳转地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `updator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wx_swiper_img
-- ----------------------------
INSERT INTO `wx_swiper_img` VALUES (1, '叙利亚', 'http://139.224.248.1:9000/swiper/swiper1.jpg', '/index/index', '2022-03-09 21:34:26', NULL, NULL, NULL);
INSERT INTO `wx_swiper_img` VALUES (2, '乌克兰', 'http://139.224.248.1:9000/swiper/swiper2.jpg', '/search/serach', '2022-03-09 21:35:08', NULL, NULL, NULL);
INSERT INTO `wx_swiper_img` VALUES (3, '俄罗斯', 'http://139.224.248.1:9000/swiper/swiper3.jpg', '/my/my', '2022-03-09 21:35:45', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
