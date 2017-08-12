/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50618
Source Host           : localhost:3306
Source Database       : newm

Target Server Type    : MYSQL
Target Server Version : 50618
File Encoding         : 65001

Date: 2014-12-13 19:17:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(100) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `ADD_QX` varchar(10) DEFAULT NULL,
  `DEL_QX` varchar(10) DEFAULT NULL,
  `EDIT_QX` varchar(10) DEFAULT NULL,
  `CHA_QX` varchar(10) DEFAULT NULL,
  `QX_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '246', '0', '1', '1', '1', '1', null);
INSERT INTO `sys_role` VALUES ('150a3d34286d4e1391093d7331eeb3a5', '会员B', '26', '4', '1', '1', '1', '1', 'c364b218fb49449c92d3f1561d71297d');
INSERT INTO `sys_role` VALUES ('2', '超级管理员', '246', '1', '1', '1', '1', '1', null);
INSERT INTO `sys_role` VALUES ('3', '管理员A', '246', '1', '1', '1', '1', '1', null);
INSERT INTO `sys_role` VALUES ('4', '组1', '26', '0', '0', '0', '0', '0', null);
INSERT INTO `sys_role` VALUES ('475935353d4543af9f6c3013a534e60d', '测试组', '', '0', '0', '0', '0', '0', '');
INSERT INTO `sys_role` VALUES ('556dcffbe49c45edb52d7119aa216816', '会员A', '18', '6', '1', '0', '0', '0', 'afe7fcb8bcfc4a83a205222f3330ad07');
INSERT INTO `sys_role` VALUES ('6', '组2', '18', '0', '1', '1', '1', '1', null);
INSERT INTO `sys_role` VALUES ('7', '会员级别', '50', '0', '0', '0', '0', '1', null);
INSERT INTO `sys_role` VALUES ('a5676c010a2548a988dbc061db270614', '初级会员', '50', '7', '1', '0', '0', '0', '');
INSERT INTO `sys_role` VALUES ('c12be08176384a788f24e84f2f535474', '测试', '', '475935353d4543af9f6c3013a534e60d', '0', '0', '0', '0', '');
