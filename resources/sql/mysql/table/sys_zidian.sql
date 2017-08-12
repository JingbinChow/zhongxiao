/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50618
Source Host           : localhost:3306
Source Database       : newm

Target Server Type    : MYSQL
Target Server Version : 50618
File Encoding         : 65001

Date: 2014-12-13 19:17:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_zidian
-- ----------------------------
DROP TABLE IF EXISTS `sys_zidian`;
CREATE TABLE `sys_zidian` (
  `ZD_ID` varchar(100) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `BIANMA` varchar(100) DEFAULT NULL,
  `ORDY_BY` int(10) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `JB` int(10) DEFAULT NULL,
  `P_BM` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ZD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_zidian
-- ----------------------------
INSERT INTO `sys_zidian` VALUES ('c067fdaf51a141aeaa56ed26b70de863', '课程', 'KC', '1', '0', '1', 'KC');
INSERT INTO `sys_zidian` VALUES ('cdba0b5ef20e4fc0a5231fa3e9ae246a', '分类', 'FL', '2', '0', '1', 'FL');
INSERT INTO `sys_zidian` VALUES ('fdf45fe948c94a36af7ccf6d3f5c38c1', '语文', '01', '1', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'KC_01');
