/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50618
Source Host           : localhost:3306
Source Database       : newm

Target Server Type    : MYSQL
Target Server Version : 50618
File Encoding         : 65001

Date: 2014-12-13 19:17:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
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
INSERT INTO `sys_user` VALUES ('1', 'admin', 'c4ca4238a0b923820dcc509a6f75849b', '系统管理员', '1133671055321055258374707980945218933803269864762743594642571294', '1', '2014-12-13 01:11:03', '127.0.0.1', '0', '最高统治者', 'skin-1', 'admin@main.com', '001');
INSERT INTO `sys_user` VALUES ('ba7ef0fc82ab40219ac8048c14787e8c', 'a1', 'c4ca4238a0b923820dcc509a6f75849b', 'A', '', '2', '2014-11-04 02:58:29', '127.0.0.1', '0', 'A', null, 'a1@main.com', '002');
