/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50527
Source Host           : 127.0.0.1:3306
Source Database       : newm

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-12-18 15:57:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  `MENU_URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `MENU_ORDER` varchar(32) DEFAULT NULL,
  `MENU_ICON` varchar(30) DEFAULT NULL,
  `MENU_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', null, '0', '1', 'icon-film', '1');
INSERT INTO `sys_menu` VALUES ('2', '组织管理', 'role.do?1=1', '1', '2', null, '1');
INSERT INTO `sys_menu` VALUES ('4', '会员管理', 'happuser/listUsers.do?1=1', '1', '4', null, '1');
INSERT INTO `sys_menu` VALUES ('5', '系统用户', 'user/listUsers.do?1=1', '1', '3', null, '1');
INSERT INTO `sys_menu` VALUES ('6', '信息管理', '', '0', '2', 'icon-list-alt', '2');
INSERT INTO `sys_menu` VALUES ('7', '信息列表', 'a.do?1=1&?1=1&', '6', '1', null, '2');
INSERT INTO `sys_menu` VALUES ('8', '测试2', '222?1=1&', '6', '2', null, '2');
