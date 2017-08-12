/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50527
Source Host           : 127.0.0.1:3306
Source Database       : newm

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-12-18 11:26:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_app_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_user`;
CREATE TABLE `sys_app_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `PHONE` varchar(100) DEFAULT NULL,
  `SFID` varchar(100) DEFAULT NULL,
  `START_TIME` varchar(100) DEFAULT NULL,
  `END_TIME` varchar(100) DEFAULT NULL,
  `YEARS` int(10) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app_user
-- ----------------------------
INSERT INTO `sys_app_user` VALUES ('4b2043281bd94a3b90e0780d82831fa1', 'k1', 'c4ca4238a0b923820dcc509a6f75849b', 'K', '', 'a5676c010a2548a988dbc061db270614', '', '', '1', 'K', '123', '123', '2014-12-03', '2014-12-25', '1', '001', '123@admin.com');

-- ----------------------------
-- Table structure for `sys_gl_qx`
-- ----------------------------
DROP TABLE IF EXISTS `sys_gl_qx`;
CREATE TABLE `sys_gl_qx` (
  `GL_ID` varchar(100) NOT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `FX_QX` int(10) DEFAULT NULL,
  `FW_QX` int(10) DEFAULT NULL,
  `QX1` int(10) DEFAULT NULL,
  `QX2` int(10) DEFAULT NULL,
  `QX3` int(10) DEFAULT NULL,
  `QX4` int(10) DEFAULT NULL,
  PRIMARY KEY (`GL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_gl_qx
-- ----------------------------
INSERT INTO `sys_gl_qx` VALUES ('c364b218fb49449c92d3f1561d71297d', '4', '1', '1', '1', '1', '0', '0');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  `MENU_URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `MENU_ORDER` varchar(100) DEFAULT NULL,
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

-- ----------------------------
-- Table structure for `sys_role`
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
INSERT INTO `sys_role` VALUES ('3', '管理员A', '246', '1', '1', '0', '1', '1', null);
INSERT INTO `sys_role` VALUES ('4', '组1', '26', '0', '0', '0', '0', '0', null);
INSERT INTO `sys_role` VALUES ('556dcffbe49c45edb52d7119aa216816', '会员A', '18', '6', '1', '0', '0', '0', 'afe7fcb8bcfc4a83a205222f3330ad07');
INSERT INTO `sys_role` VALUES ('6', '组2', '18', '0', '1', '1', '1', '1', null);
INSERT INTO `sys_role` VALUES ('7', '会员组', '50', '0', '0', '0', '0', '1', null);
INSERT INTO `sys_role` VALUES ('a5676c010a2548a988dbc061db270614', '初级会员', '50', '7', '1', '0', '0', '0', '');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `SKIN` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('01288b98674a420b988c38b342af5e7e', 'a2', 'c4ca4238a0b923820dcc509a6f75849b', 'A', '', '3', '2014-12-16 03:02:03', '127.0.0.1', '0', 'A', 'default', 'admin@qq.com', '002');
INSERT INTO `sys_user` VALUES ('1', 'admin', 'c4ca4238a0b923820dcc509a6f75849b', '系统管理员', '1133671055321055258374707980945218933803269864762743594642571294', '1', '2014-12-18 11:10:46', '127.0.0.1', '0', '最高统治者', 'skin-1', 'admin@main.com', '001');
INSERT INTO `sys_user` VALUES ('ba7ef0fc82ab40219ac8048c14787e8c', 'a1', 'c4ca4238a0b923820dcc509a6f75849b', 'A', '', '2', '2014-11-04 02:58:29', '127.0.0.1', '0', 'A', null, '003@qq.com', '003');

-- ----------------------------
-- Table structure for `sys_user_qx`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_qx`;
CREATE TABLE `sys_user_qx` (
  `U_ID` varchar(100) NOT NULL,
  `C1` int(10) DEFAULT NULL,
  `C2` int(10) DEFAULT NULL,
  `C3` int(10) DEFAULT NULL,
  `C4` int(10) DEFAULT NULL,
  `Q1` int(10) DEFAULT NULL,
  `Q2` int(10) DEFAULT NULL,
  `Q3` int(10) DEFAULT NULL,
  `Q4` int(10) DEFAULT NULL,
  PRIMARY KEY (`U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_qx
-- ----------------------------
INSERT INTO `sys_user_qx` VALUES ('afe7fcb8bcfc4a83a205222f3330ad07', '1', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_zidian`
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
