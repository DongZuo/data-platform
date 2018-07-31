

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cc_sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `cc_sys_log`;
CREATE TABLE `cc_sys_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_title` varchar(20) DEFAULT NULL COMMENT '日志标题',
  `log_type` varchar(10) DEFAULT NULL COMMENT '日志类型 info error',
  `log_url` varchar(50) DEFAULT NULL COMMENT '日志请求url',
  `log_method` varchar(10) DEFAULT NULL COMMENT '请求方式 get post',
  `log_params` varchar(300) DEFAULT NULL COMMENT '请求参数',
  `log_exception` varchar(200) DEFAULT NULL COMMENT '请求异常',
  `log_user_name` varchar(50) DEFAULT NULL COMMENT '请求用户Id',
  `log_ip` varchar(20) DEFAULT NULL COMMENT '请求IP',
  `log_ip_address` varchar(40) DEFAULT NULL COMMENT '请求ip所在地',
  `log_start_time` datetime DEFAULT NULL COMMENT '请求开始时间',
  `log_elapsed_time` bigint(20) DEFAULT NULL COMMENT '请求耗时',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3116 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cc_sys_log
-- ----------------------------



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cc_user`
-- ----------------------------
DROP TABLE IF EXISTS `cc_user`;
CREATE TABLE `cc_user` (
  `role_ids` varchar(50) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_login_name` varchar(50) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_status` bigint(20) NOT NULL,
  `creator` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `modifier` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1532329092141 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cc_user
-- ----------------------------
INSERT INTO `cc_user` VALUES ('31,1,10,32,2,33,5,8,73,4,34,6,71,72,9', '1530690578531', 'liyunxiang@youxin.com', 'liyunxiang@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'liyunxiang@youxin.com', '2018-07-04 15:49:39', 'liyunxiang@youxin.com', '2018-07-23 14:56:45');
INSERT INTO `cc_user` VALUES ('2', '1530692700017', 'jiesuan@youxin.com', 'jiesuan@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'jiesuan@youxin.com', '2018-07-04 16:25:00', 'jiesuan@youxin.com', '2018-07-04 16:25:00');
INSERT INTO `cc_user` VALUES ('2,1', '1530695914696', 'renyanyan@youxin.com', 'renyanyan@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'renyanyan@youxin.com', '2018-07-04 17:18:35', 'renyanyan@youxin.com', '2018-07-06 11:19:47');
INSERT INTO `cc_user` VALUES ('2', '1530695932520', 'liyongpeng@youxin.com', 'liyongpeng@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'liyongpeng@youxin.com', '2018-07-04 17:18:53', 'liyongpeng@youxin.com', '2018-07-04 17:18:53');
INSERT INTO `cc_user` VALUES ('2', '1530695944610', 'liuhaoyuan@youxin.com', 'liuhaoyuan@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'liuhaoyuan@youxin.com', '2018-07-04 17:19:05', 'liuhaoyuan@youxin.com', '2018-07-04 17:19:05');
INSERT INTO `cc_user` VALUES ('2', '1530695958433', 'yujian@youxin.com', 'yujian@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'yujian@youxin.com', '2018-07-04 17:19:18', 'yujian@youxin.com', '2018-07-04 17:19:18');
INSERT INTO `cc_user` VALUES ('2,1', '1530695973448', 'zhangmengdie@youxin.com', 'zhangmengdie@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'zhangmengdie@youxin.com', '2018-07-04 17:19:33', 'zhangmengdie@youxin.com', '2018-07-06 11:19:52');
INSERT INTO `cc_user` VALUES ('2,1', '1530695987395', 'lijiale@youxin.com', 'lijiale@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'lijiale@youxin.com', '2018-07-04 17:19:47', 'lijiale@youxin.com', '2018-07-06 11:19:56');
INSERT INTO `cc_user` VALUES ('2', '1530695999708', 'chujinnan@youxin.com', 'chujinnan@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'chujinnan@youxin.com', '2018-07-04 17:20:00', 'chujinnan@youxin.com', '2018-07-04 17:20:00');
INSERT INTO `cc_user` VALUES ('2', '1530696010623', 'yunyanru@youxin.com', 'yunyanru@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'yunyanru@youxin.com', '2018-07-04 17:20:11', 'yunyanru@youxin.com', '2018-07-04 17:20:11');
INSERT INTO `cc_user` VALUES ('2', '1530696022318', 'zhuwanming@youxin.com', 'zhuwanming@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'zhuwanming@youxin.com', '2018-07-04 17:20:22', 'zhuwanming@youxin.com', '2018-07-04 17:20:22');
INSERT INTO `cc_user` VALUES ('2', '1530696033436', 'wangyan02@ucredit.com', 'wangyan02@ucredit.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'wangyan02@ucredit.com', '2018-07-04 17:20:33', 'wangyan02@ucredit.com', '2018-07-04 17:20:33');
INSERT INTO `cc_user` VALUES ('2', '1530697606023', 'xingxinrui@youxin.com', 'xingxinrui@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'xingxinrui@youxin.com', '2018-07-04 17:46:46', 'xingxinrui@youxin.com', '2018-07-04 17:46:46');
INSERT INTO `cc_user` VALUES ('31,1,10,32,2,33,5,8,73,4,34,6,71,72,9', '1530704370738', 'zhaoliang01@youxin.com', 'zhaoliang01@youxin.com', '670b14728ad9902aecba32e22fa4f6bd', '0', 'zhaoliang01@youxin.com', '2018-07-04 19:39:31', 'zhaoliang01@youxin.com', '2018-07-06 11:20:02');
INSERT INTO `cc_user` VALUES ('2,1', '1530704370739', 'zuodong@youxin.com', 'zuodong@youxin.com', 'f28016b8a80d4204f8c3a3ee4636ae57', '0', 'zuodong@youxin.com', '2018-07-05 17:07:28', 'zuodong@youxin.com', '2018-07-23 14:52:46');
INSERT INTO `cc_user` VALUES ('1,8,93,92,91', '1530704370741', 'yangxin03@youxin.com', 'yangxin03@youxin.com', 'ac4abfa91aaab99318380d253d8637b4', '0', 'yangxin03@youxin.com', '2018-07-17 14:41:38', 'yangxin03@youxin.com', '2018-07-23 14:52:59');
INSERT INTO `cc_user` VALUES ('31,1,10,32,2,33,5,8,73,4,34,6,71,72,9', '1532329092140', 'yuezhirui@youxin.com', 'yuezhirui@youxin.com', '96e79218965eb72c92a549dd5a330112', '0', 'yuezhirui@youxin.com', '2018-07-23 14:58:12', 'yuezhirui@youxin.com', '2018-07-23 14:58:12');




SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dp_indicator`
-- ----------------------------
DROP TABLE IF EXISTS `dp_indicator`;
CREATE TABLE `dp_indicator` (
  `tag` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `author` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `key` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `sqlInfo` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dp_indicator
-- ----------------------------
INSERT INTO `dp_indicator` VALUES ('', 'eeeeee', 'null', '1532335321859', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('标签11,标签22', 'eeee', 'null', '1532335330789', 'eee', 'ee');
INSERT INTO `dp_indicator` VALUES ('', 'test', 'null', '1532335588925', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '222', 'null', '1532336078215', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '44444', 'null', '1532336951955', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '1', 'null', '1532337185304', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '111111', 'null', '1532337188422', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '22RRRRR', 'null', '1532337192096', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '111111', 'null', '1532337195153', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '2', 'null', '1532337198085', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '2', 'null', '1532337201417', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '2', 'null', '1532337204221', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '1', 'null', '1532337207601', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '2', 'null', '1532337210669', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '1', 'null', '1532337213325', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '1', 'null', '1532337362745', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '111111', 'null', '1532337367954', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '11111/projectlist', 'null', '1532337376945', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', '1', 'null', '1532337379964', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('', 'yue', 'null', '1532337834268', 'null', 'null');
INSERT INTO `dp_indicator` VALUES ('标签33,标签11', '/projectlist', 'null', '1532338812247', 'dccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdccccccccccdcccccccccc', 'ddddddd');



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dp_indicator_tag`
-- ----------------------------
DROP TABLE IF EXISTS `dp_indicator_tag`;
CREATE TABLE `dp_indicator_tag` (
  `tag` varchar(20) NOT NULL,
  PRIMARY KEY (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dp_indicator_tag
-- ----------------------------
INSERT INTO `dp_indicator_tag` VALUES ('市场');
INSERT INTO `dp_indicator_tag` VALUES ('标签1');
INSERT INTO `dp_indicator_tag` VALUES ('标签2');
INSERT INTO `dp_indicator_tag` VALUES ('标签3');
INSERT INTO `dp_indicator_tag` VALUES ('资金');
INSERT INTO `dp_indicator_tag` VALUES ('运营');



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dp_interface`
-- ----------------------------
DROP TABLE IF EXISTS `dp_interface`;
CREATE TABLE `dp_interface` (
  `icon` varchar(10) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `key` varchar(20) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `projectId` varchar(20) NOT NULL,
  PRIMARY KEY (`key`),
  KEY `title` (`title`),
  KEY `projectKey` (`projectId`),
  CONSTRAINT `projectKey` FOREIGN KEY (`projectId`) REFERENCES `dp_project` (`key`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dp_interface
-- ----------------------------
INSERT INTO `dp_interface` VALUES ('null', 'aa interface', '1532919502316', 'aaaa.html', '1532919457882');
INSERT INTO `dp_interface` VALUES ('capital', 'aaa', '1532921137164', 'url', '1532921006779');
INSERT INTO `dp_interface` VALUES ('null', '用于。。。。接口', '22222', '接口1', '12345678909802');






SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dp_param`
-- ----------------------------
DROP TABLE IF EXISTS `dp_param`;
CREATE TABLE `dp_param` (
  `parentParamKey` varchar(20) DEFAULT NULL,
  `returnAPIKey` varchar(20) DEFAULT NULL,
  `commit` varchar(255) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `key` varchar(20) NOT NULL,
  `APIKey` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`key`),
  KEY `type` (`type`),
  KEY `parent` (`parentParamKey`),
  KEY `returnkey` (`returnAPIKey`),
  KEY `api` (`APIKey`),
  CONSTRAINT `api` FOREIGN KEY (`APIKey`) REFERENCES `dp_interface` (`key`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `parent` FOREIGN KEY (`parentParamKey`) REFERENCES `dp_param` (`key`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `returnkey` FOREIGN KEY (`returnAPIKey`) REFERENCES `dp_interface` (`key`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dp_param
-- ----------------------------
INSERT INTO `dp_param` VALUES (null, null, '姓名', 'string', 'name', '1', '22222');
INSERT INTO `dp_param` VALUES (null, null, '课程', 'array', ' subject', '111', '22222');
INSERT INTO `dp_param` VALUES (null, '22222', '姓名', 'string', 'name', '111111', null);
INSERT INTO `dp_param` VALUES (null, '22222', '课程', 'array', 'subject', '11111111', null);
INSERT INTO `dp_param` VALUES (null, null, 'c', 'b', 'a', '1532919478858', '1532919502316');
INSERT INTO `dp_param` VALUES (null, null, 'cc', 'bb', 'aa', '1532919484042', '1532919502316');
INSERT INTO `dp_param` VALUES (null, '1532919502316', 'ccc', 'bbb', 'aaa', '1532919495314', null);
INSERT INTO `dp_param` VALUES ('111', null, '姓名', 'string', 'name', '2', null);
INSERT INTO `dp_param` VALUES ('111', null, '姓名', 'string', 'name', '22', null);
INSERT INTO `dp_param` VALUES ('11111111', null, '姓名', 'string', 'name', '22222', null);
INSERT INTO `dp_param` VALUES ('11111111', null, '姓名', 'string', 'name', '2222222', null);
INSERT INTO `dp_param` VALUES ('2222222', null, '姓名', 'string', 'name', '33333339', null);




SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dp_project`
-- ----------------------------
DROP TABLE IF EXISTS `dp_project`;
CREATE TABLE `dp_project` (
  `coverBg` varchar(255) DEFAULT NULL,
  `headerImg` varchar(255) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `key` varchar(20) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dp_project
-- ----------------------------
INSERT INTO `dp_project` VALUES ('bbb.com', 'http://bbb', 'nnn  mmm', 'yuezhirui@youxin.com', 'project1', '12345678909802');
INSERT INTO `dp_project` VALUES ('null', 'null', 'new', 'liyunxiang@youxin.com', 'new Project', '1532919457882');
INSERT INTO `dp_project` VALUES ('null', 'null', '新', 'liyunxiang@youxin.com', '新接口', '1532921006779');




SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dp_role_resources`
-- ----------------------------
DROP TABLE IF EXISTS `dp_role_resources`;
CREATE TABLE `dp_role_resources` (
  `menu` varchar(20) DEFAULT NULL,
  `roleId` varchar(5) NOT NULL,
  `icon` varchar(30) DEFAULT NULL,
  `key` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dp_role_resources
-- ----------------------------
INSERT INTO `dp_role_resources` VALUES (null, '1', 'reconciliation', '/dailydownload', '日报下载');
INSERT INTO `dp_role_resources` VALUES (null, '10', 'reconciliation', '/permissions', '权限管理');
INSERT INTO `dp_role_resources` VALUES (null, '2', 'queryTransaction', '/datademand', '数据传输');
INSERT INTO `dp_role_resources` VALUES (null, '3', 'reconciliation', '/channelmanage', '渠道管理');
INSERT INTO `dp_role_resources` VALUES (null, '31', 'asset', '/channelmanage/channelhome', '渠道数据报表');
INSERT INTO `dp_role_resources` VALUES (null, '32', 'reconciliation', '/channelmanage/channelfirst', '一级渠道');
INSERT INTO `dp_role_resources` VALUES (null, '33', 'reconciliation', '/channelmanage/channelsecond', '二级渠道');
INSERT INTO `dp_role_resources` VALUES (null, '34', 'reconciliation', '/channelmanage/channeldaily', '渠道日常数据');
INSERT INTO `dp_role_resources` VALUES (null, '4', 'asset', '/databack', '数据回溯');
INSERT INTO `dp_role_resources` VALUES (null, '5', 'capital', '/budget', '预算');
INSERT INTO `dp_role_resources` VALUES (null, '6', 'asset', '/sitemap', '网站地图');
INSERT INTO `dp_role_resources` VALUES (null, '7', 'reconciliation', '/apihome', '信息录入管理');
INSERT INTO `dp_role_resources` VALUES (null, '71', 'asset', '/projectlist', '接口录入');
INSERT INTO `dp_role_resources` VALUES (null, '72', 'reconciliation', '/quotalistedit', '指标录入');
INSERT INTO `dp_role_resources` VALUES (null, '73', 'capital', '/source', '元数据录入');
INSERT INTO `dp_role_resources` VALUES (null, '8', 'reconciliation', '/quotalist', '指标列表');
INSERT INTO `dp_role_resources` VALUES (null, '9', 'reconciliation', '/userbehavior', '用户行为调查');

