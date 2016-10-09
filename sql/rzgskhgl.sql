/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : rzgskhgl

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2016-10-08 17:46:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `product_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', null, null, null, null, null, null, null, '系统管理', null, '/admin/**');
INSERT INTO `resource` VALUES ('2', null, null, null, null, null, null, null, '用户管理', null, '/admin/user/*');
INSERT INTO `resource` VALUES ('3', null, null, null, null, null, null, null, '用户添加', null, '/admin/user/save');
INSERT INTO `resource` VALUES ('4', null, null, null, null, null, null, null, '用户更新', null, '/admin/user/update');
INSERT INTO `resource` VALUES ('5', null, null, null, null, null, null, null, '用户删除', null, '/admin/user/delete');
INSERT INTO `resource` VALUES ('6', null, null, null, null, null, null, null, '角色管理', null, '/admin/role/*');
INSERT INTO `resource` VALUES ('7', null, null, null, null, null, null, null, '角色添加', null, '/admin/role/save');
INSERT INTO `resource` VALUES ('8', null, null, null, null, null, null, null, '角色修改', null, '/admin/role/update');
INSERT INTO `resource` VALUES ('9', null, null, null, null, null, null, null, '资源管理', null, '/admin/resource/*');
INSERT INTO `resource` VALUES ('10', null, null, null, null, null, null, null, '资源修改', null, '/admin/resource/update');
INSERT INTO `resource` VALUES ('11', null, null, null, null, null, null, null, '资源添加', null, '/admin/resource/save');
INSERT INTO `resource` VALUES ('12', null, null, null, null, null, null, null, '资源删除', null, '/admin/resource/delete');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', null, null, null, '1', null, null, null, '管理员', 'ADMIN');
INSERT INTO `role` VALUES ('2', null, null, null, '1', null, null, null, '会员', 'VIP');
INSERT INTO `role` VALUES ('3', null, null, null, '1', null, null, null, '普通用户', 'EMP');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8mxwcdjdftg3le1dp65o0md3e` (`resource_id`),
  KEY `FK_pi1uyvvdin7grcttpe2bq9lox` (`role_id`),
  CONSTRAINT `FK_8mxwcdjdftg3le1dp65o0md3e` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`),
  CONSTRAINT `FK_pi1uyvvdin7grcttpe2bq9lox` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('1', null, null, null, null, null, null, null, '1', '1');
INSERT INTO `role_resource` VALUES ('2', null, null, null, null, null, null, null, '2', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  `credit_conditions` varchar(255) DEFAULT NULL,
  `estate` int(11) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `movable` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `solid_surfacing` int(11) DEFAULT NULL,
  `tel` bigint(20) NOT NULL,
  `total_asset` double DEFAULT NULL,
  `total_liability` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', '2016-10-08 13:48:18', 'admin', '1', null, null, null, '长江师范学院', '1', null, '1', '男', '外包', '1', 'admin', 'e00cf25ad42683b3df678c61f42c6bda', '0', '13100000000', '12333343.5', '123.3');
INSERT INTO `user` VALUES ('2', null, null, null, '1', null, null, null, null, '0', null, '0', null, null, '0', 'zw', '29117c7c10c2c1d110a09ba9e8dffbe9', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for user_product
-- ----------------------------
DROP TABLE IF EXISTS `user_product`;
CREATE TABLE `user_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dc1y63v3t2xgya5tbcr0po7it` (`product_id`),
  KEY `FK_gbrvdyv4h0adtukip1glaft27` (`user_id`),
  CONSTRAINT `FK_dc1y63v3t2xgya5tbcr0po7it` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_gbrvdyv4h0adtukip1glaft27` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_product
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_enable` int(11) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2', null, null, null, '1', null, null, null, '1', '1');
INSERT INTO `user_role` VALUES ('7', null, null, null, '1', null, null, null, '2', '2');
