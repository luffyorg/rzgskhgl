/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : rzgskhgl

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2016-10-10 16:54:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `company` int(2) DEFAULT NULL COMMENT '公司 1:有,0：无',
  `credit_conditions` varchar(50) DEFAULT NULL COMMENT '征信情况',
  `estate` int(2) DEFAULT NULL COMMENT '房产 1:有,0：无',
  `industry` varchar(65) DEFAULT NULL COMMENT '行业',
  `movable` int(2) DEFAULT NULL COMMENT '动产 1:有,0：无',
  `name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `product_no` varchar(50) DEFAULT NULL COMMENT '产品编号',
  `solid_surfacing` int(2) DEFAULT NULL COMMENT '实体铺面 1:有,0：无',
  `total_asset` double DEFAULT NULL COMMENT '总资产',
  `total_liability` double DEFAULT NULL COMMENT '总负债',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', null, null, null, '1', null, null, null, '1', null, '0', '', '0', '产品1', '1', '1', '0', '0');
INSERT INTO `product` VALUES ('2', null, null, null, '1', null, null, null, '0', null, '0', null, '0', '产品2', '2', '0', '0', '0');
INSERT INTO `product` VALUES ('3', null, null, null, '1', null, null, null, '0', null, '0', null, '0', '产品3', '3', '0', '0', '0');
INSERT INTO `product` VALUES ('4', null, null, null, '1', null, null, null, '0', null, '0', null, '0', '产品4', '4', '0', '0', '0');
INSERT INTO `product` VALUES ('5', null, null, null, '1', null, null, null, '0', null, '0', null, '0', '产品5', '5', '0', '0', '0');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

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
INSERT INTO `resource` VALUES ('13', null, null, null, null, null, null, null, '全部权限', '', '/**');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
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
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `resource_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8mxwcdjdftg3le1dp65o0md3e` (`resource_id`),
  KEY `FK_pi1uyvvdin7grcttpe2bq9lox` (`role_id`),
  CONSTRAINT `FK_8mxwcdjdftg3le1dp65o0md3e` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`),
  CONSTRAINT `FK_pi1uyvvdin7grcttpe2bq9lox` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('2', null, null, null, null, null, null, null, '13', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `address` varchar(255) DEFAULT '' COMMENT '地址',
  `company` int(2) DEFAULT NULL COMMENT '公司 1:有,0：无',
  `credit_conditions` varchar(50) DEFAULT NULL COMMENT '征信情况',
  `estate` int(2) DEFAULT NULL COMMENT '房产 1:有,0：无',
  `gender` varchar(4) DEFAULT '' COMMENT '性别',
  `industry` varchar(65) DEFAULT NULL COMMENT '行业',
  `movable` int(2) DEFAULT NULL COMMENT '动产 1:有,0：无',
  `name` varchar(50) DEFAULT '' COMMENT '姓名',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `solid_surfacing` int(2) DEFAULT NULL COMMENT '实体铺面 1:有,0：无',
  `tel` bigint(20) DEFAULT NULL COMMENT '电话',
  `total_asset` double DEFAULT NULL COMMENT '总资产',
  `total_liability` double DEFAULT NULL COMMENT '总负债',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', '2016-10-10 14:36:52', 'admin', '0', null, null, null, '长江师范学院', '1', null, '1', '男', '外包', '1', 'admin', 'e00cf25ad42683b3df678c61f42c6bda', '0', '13100000000', '12333343.5', '123.3');

-- ----------------------------
-- Table structure for user_product
-- ----------------------------
DROP TABLE IF EXISTS `user_product`;
CREATE TABLE `user_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
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
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `is_enable` int(11) DEFAULT NULL COMMENT '是否逻辑删除 0:已删除 1：未删除',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', null, null, null, null, null, null, null, '1', '1');
