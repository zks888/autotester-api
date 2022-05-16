/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.7
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 192.168.1.7:3406
 Source Schema         : testcenter

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 16/05/2022 08:47:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_account_name` (`name`),
  UNIQUE KEY `ix_account_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES (1, '278506089@qq.com', 'admin', '$2a$10$OGtSaHGio1c2KCNuHJ8UXOFZpsV0HbNHgiTBy9DWbFcRbiwralwLO', '2019-07-01 00:00:00', '2022-05-15 13:57:56');
COMMIT;

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `account_id` bigint(20) unsigned NOT NULL COMMENT '用户Id',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_role_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `account_role_fk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Records of account_role
-- ----------------------------
BEGIN;
INSERT INTO `account_role` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'DeployUnitId',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接口名',
  `visittype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '访问方式，字典表获取',
  `apistyle` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'restful,普通方式',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'url访问路径',
  `requestcontenttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求数据格式，form表单，json',
  `responecontenttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '响应数据格式，form表单，json',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `requesttype` varchar(20) DEFAULT 'Body传值' COMMENT '请求传值方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='发布单元表';

-- ----------------------------
-- Records of api
-- ----------------------------
BEGIN;
INSERT INTO `api` VALUES (1, 1, '测试百度', '百度首页', 'GET', '传统方式', '', 'Form表单', '', '百度首页API', '2022-03-28 12:42:14', '2022-03-28 12:42:14', 'admin', '');
INSERT INTO `api` VALUES (3, 2, '测试用户中心服务', '登陆获取token', 'POST', 'Restful', '/account/token', 'JSON', '', '登陆获取token接口', '2022-03-28 12:50:00', '2022-05-06 10:51:18', 'admin', '');
INSERT INTO `api` VALUES (4, 2, 'testcenterservice', '新增环境', 'POST', 'Restful', '/enviroment', 'JSON', '', '新增环境API', '2022-03-28 12:58:16', '2022-03-28 12:59:25', 'admin', '');
INSERT INTO `api` VALUES (5, 3, '测试本机', 'phpinfo', 'GET', '传统方式', '/info.php', 'Form表单', '', '测试phpinfo', '2022-05-04 15:04:22', '2022-05-04 15:06:28', 'admin', '');
INSERT INTO `api` VALUES (6, 3, '测试本机', '测试数据库查询', 'GET', '传统方式', '/selectfromdb.php', 'Form表单', '', '测试本机数据库查询', '2022-05-04 15:30:33', '2022-05-07 20:48:25', 'admin', '');
INSERT INTO `api` VALUES (7, 4, '医道天年', '直播落地页', 'GET', '传统方式', '/keti', 'Form表单', '', '直播落地页', '2022-05-07 23:41:43', '2022-05-07 23:41:43', 'admin', '');
COMMIT;

-- ----------------------------
-- Table structure for api_casedata
-- ----------------------------
DROP TABLE IF EXISTS `api_casedata`;
CREATE TABLE `api_casedata` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `apiparam` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api参数',
  `apiparamvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例参数值',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `paramstype` varchar(20) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='api用例数据表';

-- ----------------------------
-- Records of api_casedata
-- ----------------------------
BEGIN;
INSERT INTO `api_casedata` VALUES (1, 2, '正常登陆', 'Body', '{\n  \"name\": \"admin\",\n  \"password\": \"admin123\"\n}', 'Body', '', '2022-03-28 12:51:15', '2022-03-28 12:51:15', 'JSON');
INSERT INTO `api_casedata` VALUES (7, 7, '测试本机数据库查询', 'id', '33', 'Params', '', '2022-05-07 20:05:10', '2022-05-07 20:05:10', 'Number');
INSERT INTO `api_casedata` VALUES (8, 10, '压测直播落地页', 'bid', '330', 'Params', '', '2022-05-07 23:44:48', '2022-05-07 23:44:48', 'Number');
INSERT INTO `api_casedata` VALUES (9, 10, '压测直播落地页', 'keti_id', '22', 'Params', '', '2022-05-07 23:44:48', '2022-05-07 23:44:48', 'Number');
INSERT INTO `api_casedata` VALUES (10, 10, '压测直播落地页', 'User-Agent', 'Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255 ———————————————— 版权声明：本文为CSDN博主「qq_41984953」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。 原文链接：https://blog.csdn.net/qq_41984953/article/details/119570641', 'Header', '', '2022-05-07 23:44:48', '2022-05-07 23:44:48', '');
COMMIT;

-- ----------------------------
-- Table structure for api_params
-- ----------------------------
DROP TABLE IF EXISTS `api_params`;
CREATE TABLE `api_params` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiId',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api名',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `keyname` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'key名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `keydefaultvalue` text COMMENT 'Key默认值',
  `keytype` varchar(20) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='api参数表';

-- ----------------------------
-- Records of api_params
-- ----------------------------
BEGIN;
INSERT INTO `api_params` VALUES (1, 4, '新增环境', 2, 'testcenterservice', 'Body', '{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', '2022-03-28 12:03:58', '2022-03-28 13:00:49', NULL, 'NoForm', 'JSON');
INSERT INTO `api_params` VALUES (2, 4, '新增环境', 2, 'testcenterservice', 'Header', 'Authorization', '2022-03-28 13:03:49', '2022-03-28 13:03:49', 'admin', '<token>', '');
INSERT INTO `api_params` VALUES (3, 6, '测试数据库查询', 3, '测试本机', 'Params', 'id', '2022-05-07 20:05:10', '2022-05-07 20:05:10', 'admin', '33', 'Number');
INSERT INTO `api_params` VALUES (4, 7, '直播落地页', 4, '医道天年', 'Params', 'bid', '2022-05-07 23:05:33', '2022-05-08 00:18:25', 'admin', '330', 'Number');
INSERT INTO `api_params` VALUES (5, 7, '直播落地页', 4, '医道天年', 'Params', 'keti_id', '2022-05-07 23:05:33', '2022-05-08 00:18:25', 'admin', '22', 'Number');
INSERT INTO `api_params` VALUES (6, 7, '直播落地页', 4, '医道天年', 'Header', 'User-Agent', '2022-05-07 23:05:38', '2022-05-08 00:18:25', 'admin', 'Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255', '');
COMMIT;

-- ----------------------------
-- Table structure for apicases
-- ----------------------------
DROP TABLE IF EXISTS `apicases`;
CREATE TABLE `apicases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'API',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `casejmxname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例jmx名，和jmx文件名对齐',
  `casetype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类型，功能，性能',
  `threadnum` bigint(20) unsigned NOT NULL COMMENT '线程数',
  `loops` bigint(20) unsigned NOT NULL COMMENT '循环数',
  `casecontent` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例内容，以英文逗号分开，提供jar获取自定义期望结果A：1的值，入参为冒号左边的内容',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '期望值',
  `middleparam` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中间变量',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `level` bigint(20) unsigned NOT NULL COMMENT '优先级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='api用例表';

-- ----------------------------
-- Records of apicases
-- ----------------------------
BEGIN;
INSERT INTO `apicases` VALUES (1, 1, '百度首页', 1, '测试百度', '百度首页性能测试', 'httpapi', '性能', 2, 100, '百度首页性能测试', '', '', '', '2022-03-28 12:43:12', '2022-05-15 01:51:28', 'admin', 1);
INSERT INTO `apicases` VALUES (2, 3, '登陆', 2, 'testcenterservice', '正常登陆', 'httpapi', '功能', 1, 1, '正常登陆返回数据', '', '', '正常登陆返回数据', '2022-03-28 12:50:42', '2022-03-28 12:50:42', 'admin', 1);
INSERT INTO `apicases` VALUES (7, 6, '测试本机数据库查询', 3, '测试本机', '测试本机数据库查询', 'httpapi', '性能', 10, 100, '测试本机数据库查询', '', '', '测试本机数据库查询', '2022-05-04 16:03:50', '2022-05-04 16:03:50', 'admin', 2);
INSERT INTO `apicases` VALUES (9, 5, 'phpinfo', 3, '测试本机', '检查php版本', 'httpapi', '功能', 1, 1, '检查php版本', '', '', '检查php版本', '2022-05-07 21:56:30', '2022-05-07 21:56:30', 'admin', 1);
INSERT INTO `apicases` VALUES (10, 7, '直播落地页', 4, '医道天年', '压测直播落地页', 'httpapi', '性能', 20, 10000, '压测直播落地页', '', '', '压测直播落地页', '2022-05-07 23:44:48', '2022-05-08 00:21:29', 'admin', 1);
INSERT INTO `apicases` VALUES (11, 1, '百度首页', 1, '测试百度', '测试百度首页', 'httpapi', '功能', 1, 1, '百度首页功能用例测试', '', '', '', '2022-05-12 17:35:58', '2022-05-13 01:37:09', 'admin', 1);
COMMIT;

-- ----------------------------
-- Table structure for apicases_assert
-- ----------------------------
DROP TABLE IF EXISTS `apicases_assert`;
CREATE TABLE `apicases_assert` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '断言Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `asserttype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言类型',
  `assertsubtype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言子类型',
  `assertvalues` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `expression` varchar(20) DEFAULT NULL COMMENT '断言表达式',
  `assertcondition` varchar(20) DEFAULT NULL COMMENT '断言条件',
  `assertvaluetype` varchar(20) DEFAULT NULL COMMENT '断言值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='断言表';

-- ----------------------------
-- Records of apicases_assert
-- ----------------------------
BEGIN;
INSERT INTO `apicases_assert` VALUES (1, 7, 'Json', '', 'bigbrother12', '2022-05-04 16:10:58', '2022-05-04 16:23:44', 'admin', '$.username', '=', 'String');
INSERT INTO `apicases_assert` VALUES (3, 9, 'Respone', '文本', '<title>PHP 7.3.33 - phpinfo()</title>', '2022-05-07 21:57:25', '2022-05-07 21:57:25', 'admin', '', 'Contain', 'String');
INSERT INTO `apicases_assert` VALUES (4, 10, 'Respone', '文本', '<title>医道天年</title>', '2022-05-08 14:11:21', '2022-05-08 14:11:21', 'admin', '', 'Contain', 'String');
COMMIT;

-- ----------------------------
-- Table structure for apicases_condition
-- ----------------------------
DROP TABLE IF EXISTS `apicases_condition`;
CREATE TABLE `apicases_condition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `execplanid` bigint(20) unsigned DEFAULT NULL COMMENT '执行计划Id',
  `execplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行计划名',
  `target` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件目标',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '用例id',
  `envassemid` bigint(20) unsigned DEFAULT NULL COMMENT '环境组件id',
  `casedeployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例所属发布单元',
  `caseapiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例所属api',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `basetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `conditionbasetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库，接口',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库：mysql，oracle，sqlserver，接口：http,https,dubbo',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '包含调用接口的发布单元',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '调用接口的api',
  `conditionvalue` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件值，如果是数据库为sql，如果是接口为用例名',
  `conditioncaseid` bigint(20) unsigned DEFAULT NULL COMMENT '条件值id，如果是数据库为空，如果是接口为用例id',
  `connectstr` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '连接字',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例条件表';

-- ----------------------------
-- Records of apicases_condition
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for apicases_condition_report
-- ----------------------------
DROP TABLE IF EXISTS `apicases_condition_report`;
CREATE TABLE `apicases_condition_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `casetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '功能，性能',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `errorinfo` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例前后置条件运行报告表';

-- ----------------------------
-- Records of apicases_condition_report
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for apicases_debug_condition
-- ----------------------------
DROP TABLE IF EXISTS `apicases_debug_condition`;
CREATE TABLE `apicases_debug_condition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) unsigned DEFAULT NULL COMMENT '发布单元Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `conditionname` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件名',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例调试全局条件表';

-- ----------------------------
-- Records of apicases_debug_condition
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for apicases_performanceapdex
-- ----------------------------
DROP TABLE IF EXISTS `apicases_performanceapdex`;
CREATE TABLE `apicases_performanceapdex` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `apdex` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'apdex',
  `toleration` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'toleration',
  `frustration` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'frustration',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例性能apdex表';

-- ----------------------------
-- Records of apicases_performanceapdex
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for apicases_performancestatistics
-- ----------------------------
DROP TABLE IF EXISTS `apicases_performancestatistics`;
CREATE TABLE `apicases_performancestatistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `runtime` double(11,2) NOT NULL COMMENT '运行时长,秒',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `samples` bigint(20) unsigned NOT NULL COMMENT '样本',
  `errorcount` bigint(20) unsigned NOT NULL COMMENT '错误次数',
  `errorrate` double(11,2) NOT NULL COMMENT '错误率',
  `average` double(11,2) NOT NULL COMMENT '平均数',
  `min` double(11,2) NOT NULL COMMENT '最小值',
  `max` double(11,2) NOT NULL COMMENT '最大值',
  `median` double(11,2) NOT NULL COMMENT '中间值',
  `nzpct` double(11,2) NOT NULL COMMENT '90pct',
  `nfpct` double(11,2) NOT NULL COMMENT '95pct',
  `nnpct` double(11,2) NOT NULL COMMENT '99pct',
  `tps` double(11,2) NOT NULL COMMENT 'tps',
  `receivekbsec` double(11,2) NOT NULL COMMENT '每秒接受Kb数',
  `sendkbsec` double(11,2) NOT NULL COMMENT '每秒发送Kb数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COMMENT='api用例性能统计表';

-- ----------------------------
-- Records of apicases_performancestatistics
-- ----------------------------
BEGIN;
INSERT INTO `apicases_performancestatistics` VALUES (1, 5, 6, '2022-05-07-003', 20.09, 1, 1000, 0, 0.00, 13.04, 6.00, 365.00, 11.00, 18.00, 22.95, 47.97, 50.70, 0.00, 0.00, '2022-05-07 14:02:20', '2022-05-07 14:02:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (2, 7, 6, '2022-05-07-003', 3.96, 1, 1000, 0, 0.00, 22.24, 4.00, 469.00, 20.00, 33.00, 38.00, 58.98, 281.90, 0.00, 0.00, '2022-05-07 14:02:20', '2022-05-07 14:02:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (3, 5, 6, '2022-05-07-03', 11.41, 1, 1000, 0, 0.00, 18.32, 6.00, 315.00, 13.00, 32.00, 45.00, 85.91, 91.20, 0.00, 0.00, '2022-05-07 16:58:40', '2022-05-07 16:58:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (4, 7, 6, '2022-05-07-03', 4.14, 1, 1000, 0, 0.00, 23.90, 3.00, 364.00, 22.00, 36.00, 45.00, 80.93, 269.60, 0.00, 0.00, '2022-05-07 16:59:00', '2022-05-07 16:59:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (5, 5, 6, '2022-05-07-004', 11.49, 1, 1000, 0, 0.00, 17.16, 7.00, 340.00, 13.00, 27.00, 35.00, 64.98, 90.90, 0.00, 0.00, '2022-05-07 17:11:40', '2022-05-07 17:11:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (6, 7, 6, '2022-05-07-004', 4.14, 1, 1000, 0, 0.00, 23.70, 3.00, 399.00, 22.00, 36.00, 41.00, 71.98, 269.80, 0.00, 0.00, '2022-05-07 17:11:59', '2022-05-07 17:11:59', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (7, 5, 6, '2022-05-07-005', 13.28, 1, 1000, 0, 0.00, 20.18, 7.00, 315.00, 15.00, 36.00, 44.00, 98.84, 76.70, 0.00, 0.00, '2022-05-07 17:30:00', '2022-05-07 17:30:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (8, 7, 6, '2022-05-07-005', 5.32, 1, 1000, 0, 0.00, 29.89, 4.00, 387.00, 26.00, 48.00, 59.00, 94.99, 194.80, 0.00, 0.00, '2022-05-07 17:30:00', '2022-05-07 17:30:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (9, 5, 6, '2022-05-07-006', 13.92, 1, 1000, 0, 0.00, 22.88, 7.00, 370.00, 17.00, 42.00, 54.95, 104.90, 73.20, 0.00, 0.00, '2022-05-07 17:37:00', '2022-05-07 17:37:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (10, 7, 6, '2022-05-07-006', 5.99, 1, 1000, 0, 0.00, 35.62, 5.00, 648.00, 31.00, 56.00, 66.00, 103.92, 176.40, 0.00, 0.00, '2022-05-07 17:37:00', '2022-05-07 17:37:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (11, 5, 6, '2022-05-07-007', 12.64, 1, 1000, 0, 0.00, 21.28, 6.00, 339.00, 15.00, 40.00, 54.95, 91.98, 80.00, 0.00, 0.00, '2022-05-07 17:56:00', '2022-05-07 17:56:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (12, 7, 6, '2022-05-07-007', 5.47, 1, 1000, 0, 0.00, 35.63, 5.00, 685.00, 32.00, 50.00, 62.95, 95.98, 188.90, 0.00, 0.00, '2022-05-07 17:56:00', '2022-05-07 17:56:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (13, 5, 6, '2022-05-07-008', 11.60, 1, 1000, 0, 0.00, 18.15, 6.00, 397.00, 13.00, 31.90, 43.00, 73.99, 87.70, 0.00, 0.00, '2022-05-07 18:37:00', '2022-05-07 18:37:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (14, 7, 6, '2022-05-07-008', 5.12, 1, 1000, 0, 0.00, 32.03, 4.00, 411.00, 29.00, 49.00, 59.95, 104.00, 202.30, 0.00, 0.00, '2022-05-07 18:37:00', '2022-05-07 18:37:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (15, 10, 7, '2022-05-07-009', 9.40, 2, 500, 0, 0.00, 146.02, 99.00, 1246.00, 127.00, 171.70, 194.00, 817.94, 54.20, 0.00, 0.00, '2022-05-08 00:16:40', '2022-05-08 00:16:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (16, 10, 7, '2022-05-07-009', 29.84, 1, 500, 0, 0.00, 191.19, 99.00, 21447.00, 125.00, 174.00, 203.00, 1062.02, 16.80, 0.00, 0.00, '2022-05-08 00:17:00', '2022-05-08 00:17:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (17, 10, 7, '2022-05-08-001', 742.11, 2, 50000, 11, 0.02, 131.29, 93.00, 24646.00, 118.00, 143.00, 157.95, 193.00, 67.30, 0.00, 0.00, '2022-05-08 00:34:40', '2022-05-08 00:34:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (18, 10, 7, '2022-05-08-001', 781.39, 1, 50000, 9, 0.02, 131.27, 93.00, 24798.00, 118.00, 142.00, 158.00, 191.00, 64.00, 0.00, 0.00, '2022-05-08 00:35:20', '2022-05-08 00:35:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (19, 10, 7, '2022-05-11-001', 403.76, 3, 12500, 0, 0.00, 131.18, 97.00, 24555.00, 120.00, 135.00, 144.00, 211.99, 30.90, 0.00, 0.00, '2022-05-11 19:24:40', '2022-05-11 19:24:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (20, 10, 7, '2022-05-11-001', 424.24, 4, 12500, 0, 0.00, 134.26, 96.00, 24412.00, 119.00, 135.00, 143.00, 206.00, 29.40, 0.00, 0.00, '2022-05-11 19:25:00', '2022-05-11 19:25:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (21, 10, 7, '2022-05-11-001', 448.63, 1, 12500, 0, 0.00, 135.35, 95.00, 24432.00, 120.00, 135.00, 144.00, 223.00, 27.80, 0.00, 0.00, '2022-05-11 19:25:40', '2022-05-11 19:25:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (22, 10, 7, '2022-05-11-001', 559.83, 2, 12500, 0, 0.00, 148.08, 96.00, 24534.00, 119.00, 135.00, 143.00, 222.99, 22.30, 0.00, 0.00, '2022-05-11 19:27:20', '2022-05-11 19:27:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (23, 10, 7, '2022-05-12-001', 412.94, 4, 12500, 0, 0.00, 133.95, 95.00, 24431.00, 122.00, 140.00, 148.00, 237.99, 30.20, 0.00, 0.00, '2022-05-12 00:35:20', '2022-05-12 00:35:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (24, 10, 7, '2022-05-12-001', 433.26, 1, 12500, 1, 0.01, 135.23, 95.00, 24499.00, 122.00, 140.00, 150.00, 230.00, 28.80, 0.00, 0.00, '2022-05-12 00:35:40', '2022-05-12 00:35:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (25, 10, 7, '2022-05-12-001', 433.72, 3, 12500, 1, 0.01, 139.53, 94.00, 24623.00, 121.00, 140.00, 149.00, 249.00, 28.80, 0.00, 0.00, '2022-05-12 00:35:40', '2022-05-12 00:35:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (26, 10, 7, '2022-05-12-001', 486.35, 2, 12500, 1, 0.01, 143.15, 93.00, 24640.00, 121.00, 141.00, 151.00, 243.00, 25.70, 0.00, 0.00, '2022-05-12 00:36:40', '2022-05-12 00:36:40', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (27, 1, 17, '2022-05-15-001', 18.53, 1, 200, 0, 0.00, 163.12, 130.00, 1188.00, 138.00, 143.00, 146.90, 1186.97, 10.80, 0.00, 0.00, '2022-05-15 01:54:20', '2022-05-15 01:54:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (28, 1, 17, '2022-05-15-002', 16.87, 1, 200, 0, 0.00, 155.48, 130.00, 1186.00, 139.00, 145.00, 149.00, 1178.17, 11.90, 0.00, 0.00, '2022-05-15 02:31:20', '2022-05-15 02:31:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (29, 1, 17, '2022-05-15-003', 9.62, 1, 200, 0, 0.00, 83.62, 75.00, 503.00, 81.00, 85.00, 86.00, 148.46, 21.20, 0.00, 0.00, '2022-05-15 13:05:20', '2022-05-15 13:05:20', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (30, 1, 17, '2022-05-15-004', 22.03, 1, 200, 0, 0.00, 200.29, 130.00, 2230.00, 139.00, 153.70, 553.35, 1200.87, 9.10, 0.00, 0.00, '2022-05-15 15:15:00', '2022-05-15 15:15:00', 'admin');
INSERT INTO `apicases_performancestatistics` VALUES (31, 1, 17, '2022-05-15-005', 32.00, 1, 200, 0, 0.00, 308.46, 131.00, 2190.00, 139.00, 1158.90, 1174.95, 1582.66, 6.20, 0.00, 0.00, '2022-05-15 20:16:20', '2022-05-15 20:16:20', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for apicases_report
-- ----------------------------
DROP TABLE IF EXISTS `apicases_report`;
CREATE TABLE `apicases_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '返回结果',
  `assertvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '期望值',
  `errorinfo` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `requestheader` text COMMENT '请求头数据',
  `requestdatas` text COMMENT '请求数据',
  `url` varchar(200) DEFAULT NULL COMMENT '地址',
  `requestmethod` varchar(20) DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';

-- ----------------------------
-- Records of apicases_report
-- ----------------------------
BEGIN;
INSERT INTO `apicases_report` VALUES (1, 1, 1, '百度测试000001', 1, '成功', '<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\"></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=http://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \" name=\"tj_login\" class=\"lb\">登录</a>);</script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>\r\n', '', 198, '', '', '2022-03-28 12:56:21', '2022-03-28 12:56:21', 'admin', 'Content-Type ：application/x-www-form-urlencoded', '', 'http://www.baidu.com/', 'GET');
INSERT INTO `apicases_report` VALUES (2, 3, 2, '测试中心回归测试00001', 1, '成功', '{\"code\":4002,\"message\":\"认证异常\"}\n', '', 147, '', '', '2022-03-28 13:06:21', '2022-03-28 13:06:21', 'admin', 'Authorization ：Content-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (3, 3, 2, '测试中心回归00002', 1, '成功', '{\"code\":4002,\"message\":\"认证异常\"}\n', '', 133, '', '', '2022-03-28 13:15:45', '2022-03-28 13:15:45', 'admin', 'Authorization ：<token>Content-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (4, 3, 2, '000003', 1, '成功', '{\"code\":4002,\"message\":\"认证异常\"}\n', '', 140, '', '', '2022-03-28 14:49:06', '2022-03-28 14:49:06', 'admin', 'Authorization ：Content-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (5, 3, 2, '000004', 1, '成功', '{\"code\":4002,\"message\":\"认证异常\"}\n', '', 128, '', '', '2022-03-28 14:51:12', '2022-03-28 14:51:12', 'admin', 'Authorization ：<token>Content-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (6, 3, 2, '00005', 1, '成功', '{\n \"code\":200\n}', '', 148, '', '', '2022-03-28 15:01:06', '2022-03-28 15:01:06', 'admin', 'Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4s_7UzXwJ0y2GwbpY81fAjGtZhhprZmpkZmjg-1jXxxDpbmhoNpJzuvZ8B4aV2g8nvw-n80mNs11ONye1h0uUrzarlTf-tXJWDZPxZzWON9IdzWSC6Q6CslNnMFkfOrY3yulLZ7Bvo4oEpfW6LyHVoDXCi0xIkglEAuNJUuhmITD-S2FpCUFxCSFxQQFxWX44VaUEECWGNGWBpkBQm3Jq9kUXCugGdafXZbTBrksFZXaDryZVETapKjG_KcMmrJYo1KvSKyHxPXAHCWdGPFQFlg0ioUEYlIWBBgMY92Z1s1q0cWZbXcgsMWDYziw3ELNcRkxYLzHrVvRBhZhmte_UZMNu1Wreqcvmncps2q3N5qV6faTiHMXhNScmjkmcDRiJzWrXLv54fXvF1RX3trm04saK65qN1Y3G1RSw2mRw63jw5vUw4_pu6fn71rrYaBzqPXKNu6yt1IZbqQo3Osm423E4queC6ww72ieCWwg72meBWwQz275YuEi0JkiWAEAJ4SgoGCWFWv1_Q8q9h9wiKbo4EFc0JRYnh_pNBX3BSBQ5MGsoSxrjUeVw1BhtlsO6dTb4MDKJ8ZWTAMxJCKYRwhyBuVPemzl-h4ZXfAHmo6ZBGTVVykB0KqNSblb6YpfciWSpUhxqQA6BSRceOYS5N3oPZpsU_CZxTaDKAhKzAMUMYjFLwoZ8dYQpsQO_SsJvCwz8agkfq8Rj4L9qTCK-chKYOQnJNMKYUzD5Ezbfr_l41ZdrPlvzzTa3vjUaitMaQ4tMoSRTKAkMJYmhbsXbnxYYh-uUldYpKK0wJa0xwmtnt1D_z9x6GCztxGjthGtNJLc2lZjUgW8SLglJCkigAABrygWFcUHl3-MQX_6MY-99z0dmf5QK9EYPYrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff399uv7-cf35_frl85-v3-KfrOZ-O50fP799-ezpiye3jz7-AwAA__8.qhRtIUk8aVvo_8kmViqPp32PoCKHX02WtdOykt0B0s-_36hTW-3e9NC-dRQ8XQ9ussLGOKjpYRPTrB17ieiCxwContent-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (7, 8, 5, '2022-05-05-005', 2, '成功', '<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\"></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=http://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \" name=\"tj_login\" class=\"lb\">登录</a>);</script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>\r\n', '', 177, '', '', '2022-03-28 15:25:36', '2022-03-28 15:25:36', 'admin', 'Content-Type ：application/x-www-form-urlencoded', '', 'http://192.168.1.7/info.php', 'GET');
INSERT INTO `apicases_report` VALUES (8, 3, 2, '00008', 1, '成功', '{\n  \"code\":200\n}', '', 202, '', '', '2022-03-28 16:09:36', '2022-03-28 16:09:36', 'admin', 'Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrdhCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetTrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCrvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZpO13unz-8OD_cPzufP_wFAAD__w.SoqYO24kUPJixCZpzcc82QgINB87iiYupneu1k153_3lhEk1FopYOE9gHICQlWjDByPRM77-t3HSf8MwX0NGRwContent-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"XRlfZ\",\n  \"envtype\": \"功能\",\n  \"memo\": \"<< usernamefromdb >>\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (9, 3, 2, '00009', 1, '成功', '{\n \"code\":200\n}', '', 199, '', '', '2022-03-28 16:11:00', '2022-03-28 16:11:00', 'admin', 'Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrZhGajEuQrtxuozHqH8t2N4sQIcSQkRKRERJwHzQcg8ZVZZddJsLvlfZ9z24v1r4_hW04XU5qnO1yujupLV4P-dJZrYIJr5S3aphMuKhxvJPuaCYTTXcQlZ06g8mG2LGDUV5fO4PNjeogKK3XbYmpBq0RXmRCkkwgEhhPkkKdhcDjXwpLSwg6lhByLCjgWJYfTlUpAUSJIU1ZoCkQlFNezaHoQgHdoB71uow22nWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIOHMiIeqwLJBJDQIg7Iw0GAA41-vflaLNt641cfMEgOG7cxyAzHLZcSE9RKzbsUQVTzSrA6dmmzYrVrNO3XZvFOZTbu12bxUr49UnKM4vObExDGJswEjsVnt2sUfr2-vuLri3jaXVtxYcV2zsfrR-JoCVpsMbh0P3rzuZlzfLj1_c62Ljcah3iPXuMvaSm24lapwo5OMux2HvXouuM6wvX0iuIWwvX0WuEUw47bFwkWiNUGyBABKCEdBwSgp1Op_G1L-HeQWSdHFgbiiKbE4OTQ4FfUVI1HkwKyhLGmMR5XDUWO0WXbr19ngw8gkxldOAjAnIZhGCHME5lGFYObjOzS84gswHzUNyqipUgaiUxmVcrPSV7vkTiRLleJQA3IITLrwyCHMk9FbNG5S8JvENYEqC0jMAhQziMUsCRvy1RGmxA78Kgm_LTDwqyV8rHIcA_9VYxLxlZPAzElIphHGnILJn7D5fs3Hq75c89mab-b8-sZoKE5rDC0yhZJMoSQwlCSG-hVvf1pgHK5TVlqnoLTClLTGiKC9dbH-n7n1MFjaidHaCdeaSG5tKjGpHd8kXBKSFJBAAQDWlAsK46LKv8fxePkzjr33PR-Z_VEq0Bv9F4PVeiPqeYxw27hkFcq2UWVQ2TYqFle_erXDwsXLV5sZJV6_2uyBdzVtRtLB7lWgiewBE1kG_F4jmGCt-izEOYgzaPYv9i72_efnx9uv77cf326fP_3-8vX4k9U8udPl_vn5xcP5_vzs4cNfAAAA__8.DsWj1Fgs8rnX4nt5kVF4f0RVMd0D_EaSQ8E-I9D9N10VItYvGOhG1vxt18-R2U8PN-WW2P_cJDgTTtFgB-zQBQContent-Type ：application/json;charset=utf-8', '{\n  \"id\": \"\",\n  \"enviromentname\": \"8RRAl\",\n  \"envtype\": \"功能\",\n  \"memo\": \"admin\",\n  \"creator\": \"admin\"\n}', 'http://127.0.0.1:8080/enviroment', 'POST');
INSERT INTO `apicases_report` VALUES (10, 8, 5, '2022-05-06-002', 2, '成功', '<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>\n<style type=\"text/css\">\nbody {background-color: #fff; color: #222; font-family: sans-serif;}\npre {margin: 0; font-family: monospace;}\na:link {color: #009; text-decoration: none; background-color: #fff;}\na:hover {text-decoration: underline;}\ntable {border-collapse: collapse; border: 0; width: 934px; box-shadow: 1px 2px 3px #ccc;}\n.center {text-align: center;}\n.center table {margin: 1em auto; text-align: left;}\n.center th {text-align: center !important;}\ntd, th {border: 1px solid #666; font-size: 75%; vertical-align: baseline; padding: 4px 5px;}\nh1 {font-size: 150%;}\nh2 {font-size: 125%;}\n.p {text-align: left;}\n.e {background-color: #ccf; width: 300px; font-weight: bold;}\n.h {background-color: #99c; font-weight: bold;}\n.v {background-color: #ddd; max-width: 300px; overflow-x: auto;}\n.v i {color: #999;}\nimg {float: right; border: 0;}\nhr {width: 93', '【期望值】：PHP Version 5.6.40 , 【实际值】：<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>\n<style type=\"text/css\">\nbody {background-color: #fff; color: #222; font-family: sans-serif;}\npre {margin: 0; font-family: monospace;}\na:link {color: #009; text-decoration: none; background-color: #fff;}\na:hover {text-decoration: underline;}\ntable {border-collapse: collapse; border: 0; width: 934px; box-shadow: 1px 2px 3px #ccc;}\n.center {text-align: center;}\n.center table {margin: 1em auto; text-align: left;}\n.center th {text-align: center !important;}\ntd, th {border: 1px solid #666; font-size: 75%; vertical-align: baseline; padding: 4px 5px;}\nh1 {font-size: 150%;}\nh2 {font-size: 125%;}\n.p {text-align: left;}\n.e {background-color: #ccf; width: 300px; font-weight: bold;}\n.h {background-color: #99c; font-weight: bold;}\n.v {background-color: #ddd; max-width: 300px; overflow-x: auto;}\n.v i {color: #999;}\nimg {float:', 264, '【断言类型：Respone， 断言子类型：文本， 断言条件：Contain， 断言值：PHP Version 5.6.40， 断言值类型：String】', '', '2022-05-05 17:07:47', '2022-05-05 17:07:47', 'admin', 'Content-Type ：application/x-www-form-urlencoded', '', 'http://192.168.1.7:80/info.php', 'GET');
INSERT INTO `apicases_report` VALUES (11, 11, 16, '2022-05-13-003', 2, '成功', '<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su va', '', 496, '', '', '2022-05-13 01:40:38', '2022-05-13 01:40:38', 'admin', 'Content-Type ：application/x-www-form-urlencoded', '', 'http://www.baidu.com/', 'GET');
INSERT INTO `apicases_report` VALUES (12, 11, 16, '2022-05-13-004', 2, '成功', '<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su va', '', 463, '', '', '2022-05-13 01:41:57', '2022-05-13 01:41:57', 'admin', 'Content-Type ：application/x-www-form-urlencoded', '', 'http://www.baidu.com/', 'GET');
INSERT INTO `apicases_report` VALUES (13, 11, 16, '2022-05-13-005', 2, '成功', '<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su va', '', 395, '', '', '2022-05-13 01:43:57', '2022-05-13 01:43:57', 'admin', 'Content-Type ：application/x-www-form-urlencoded', '', 'http://www.baidu.com/', 'GET');
COMMIT;

-- ----------------------------
-- Table structure for apicases_report_performance
-- ----------------------------
DROP TABLE IF EXISTS `apicases_report_performance`;
CREATE TABLE `apicases_report_performance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '返回结果',
  `assertvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '期望值',
  `errorinfo` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `requestheader` text COMMENT '请求头数据',
  `requestdatas` text COMMENT '请求数据',
  `url` varchar(200) DEFAULT NULL COMMENT '地址',
  `requestmethod` varchar(20) DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';

-- ----------------------------
-- Records of apicases_report_performance
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for apicases_reportstatics
-- ----------------------------
DROP TABLE IF EXISTS `apicases_reportstatics`;
CREATE TABLE `apicases_reportstatics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发单单元id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例总数',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='api计划批量用例统计报告表';

-- ----------------------------
-- Records of apicases_reportstatics
-- ----------------------------
BEGIN;
INSERT INTO `apicases_reportstatics` VALUES (1, 16, 1, '2022-05-13-003', 2, 1, 1, 0, 496, '2022-05-12 01:40:38', '2022-05-12 01:40:38', 'admin');
INSERT INTO `apicases_reportstatics` VALUES (2, 16, 1, '2022-05-13-004', 2, 1, 1, 0, 463, '2022-05-11 01:41:57', '2022-05-11 01:41:57', 'admin');
INSERT INTO `apicases_reportstatics` VALUES (3, 16, 1, '2022-05-13-005', 2, 1, 1, 0, 395, '2022-05-10 01:43:57', '2022-05-10 01:43:57', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for apicases_variables
-- ----------------------------
DROP TABLE IF EXISTS `apicases_variables`;
CREATE TABLE `apicases_variables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) unsigned NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `deployunitname` varchar(64) DEFAULT NULL COMMENT '发布单元',
  `apiname` varchar(64) DEFAULT NULL COMMENT 'api',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='api用例变量表';

-- ----------------------------
-- Records of apicases_variables
-- ----------------------------
BEGIN;
INSERT INTO `apicases_variables` VALUES (1, 2, '正常登陆', 1, 'token', '绑定正常登陆获取token', '2022-03-28 13:03:45', '2022-03-28 13:03:45', 'admin', 'testcenterservice', '登陆');
COMMIT;

-- ----------------------------
-- Table structure for condition_api
-- ----------------------------
DROP TABLE IF EXISTS `condition_api`;
CREATE TABLE `condition_api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id',
  `deployunitid` bigint(20) unsigned DEFAULT NULL COMMENT '发布单元id',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '接口caseid',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `apiid` int(11) NOT NULL COMMENT 'apiid',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `deployunitname` varchar(64) DEFAULT NULL COMMENT '发布单元名',
  `apiname` varchar(64) DEFAULT NULL COMMENT 'api名',
  `casename` varchar(64) DEFAULT NULL COMMENT '接口名',
  `subconditionname` varchar(64) DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='接口条件表';

-- ----------------------------
-- Records of condition_api
-- ----------------------------
BEGIN;
INSERT INTO `condition_api` VALUES (1, 1, 2, 2, '', '2022-03-28 13:05:12', '2022-03-28 13:05:12', 'admin', 3, '测试中心回归测试前置父条件', 'testcenterservice', '登陆', '正常登陆', '前置登陆');
INSERT INTO `condition_api` VALUES (3, 3, 2, 2, '', '2022-03-28 15:09:57', '2022-03-28 15:09:57', 'admin', 3, '测试中心性能测试前置父条件', 'testcenterservice', '登陆', '正常登陆', '测试中心性能测试前置登陆');
COMMIT;

-- ----------------------------
-- Table structure for condition_db
-- ----------------------------
DROP TABLE IF EXISTS `condition_db`;
CREATE TABLE `condition_db` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id',
  `enviromentid` bigint(20) unsigned DEFAULT NULL COMMENT '环境id',
  `dbtype` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库类型',
  `dbcontent` text COMMENT 'db执行内容',
  `connectstr` varchar(200) DEFAULT NULL COMMENT 'db连接字',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `subconditionname` varchar(64) DEFAULT NULL COMMENT '子条件名',
  `assembleid` bigint(20) unsigned DEFAULT NULL COMMENT '组件id',
  `assemblename` varchar(64) DEFAULT NULL COMMENT '组件名',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `enviromentname` varchar(64) DEFAULT NULL COMMENT '环境名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='db条件表';

-- ----------------------------
-- Records of condition_db
-- ----------------------------
BEGIN;
INSERT INTO `condition_db` VALUES (1, 1, 1, 'Select', 'select name,password from account', '', '', '2022-03-28 15:30:40', '2022-03-28 15:30:40', 'admin', '获取用户表用户名', 1, 'mysql组件', '测试中心回归测试前置父条件', '功能测试环境');
COMMIT;

-- ----------------------------
-- Table structure for condition_delay
-- ----------------------------
DROP TABLE IF EXISTS `condition_delay`;
CREATE TABLE `condition_delay` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '父条件id',
  `conditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '父条件名',
  `subconditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件名',
  `delaytime` bigint(20) unsigned DEFAULT NULL COMMENT '延时时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='延时子条件表';

-- ----------------------------
-- Records of condition_delay
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for condition_order
-- ----------------------------
DROP TABLE IF EXISTS `condition_order`;
CREATE TABLE `condition_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '父条件id',
  `subconditionid` bigint(20) unsigned DEFAULT NULL COMMENT '子条件id',
  `subconditiontype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件类型',
  `subconditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件名',
  `conditionorder` bigint(20) unsigned DEFAULT NULL COMMENT '条件顺序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '父条件名',
  `orderstatus` varchar(20) DEFAULT NULL COMMENT '顺序状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='条件顺序表';

-- ----------------------------
-- Records of condition_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for condition_script
-- ----------------------------
DROP TABLE IF EXISTS `condition_script`;
CREATE TABLE `condition_script` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id，只取类型为用例',
  `script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '脚本',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `subconditionname` varchar(64) DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='脚本条件表';

-- ----------------------------
-- Records of condition_script
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for dbcondition_variables
-- ----------------------------
DROP TABLE IF EXISTS `dbcondition_variables`;
CREATE TABLE `dbcondition_variables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `dbconditionid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `dbconditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) unsigned NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `fieldname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字段名',
  `roworder` bigint(20) unsigned NOT NULL COMMENT '行序号',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='数据库条件变量表';

-- ----------------------------
-- Records of dbcondition_variables
-- ----------------------------
BEGIN;
INSERT INTO `dbcondition_variables` VALUES (1, 1, '获取用户表用户名', 1, 'usernamefromdb', 'name', 1, '获取用户表第一行的name字段值', '2022-03-28 15:31:18', '2022-03-28 15:31:18');
COMMIT;

-- ----------------------------
-- Table structure for dbvariables
-- ----------------------------
DROP TABLE IF EXISTS `dbvariables`;
CREATE TABLE `dbvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `dbvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablesdes` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量值类型',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='数据库变量表';

-- ----------------------------
-- Records of dbvariables
-- ----------------------------
BEGIN;
INSERT INTO `dbvariables` VALUES (1, 'usernamefromdb', '数据库获取用户数据', 'String', 'admin', '数据库获取用户表的数据', '2022-03-28 15:27:48', '2022-03-28 15:27:48');
COMMIT;

-- ----------------------------
-- Table structure for deployunit
-- ----------------------------
DROP TABLE IF EXISTS `deployunit`;
CREATE TABLE `deployunit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `protocal` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '协议',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问端口',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='发布单元表';

-- ----------------------------
-- Records of deployunit
-- ----------------------------
BEGIN;
INSERT INTO `deployunit` VALUES (1, '测试百度', 'http', '80', '测试百度例子发布单元', '2022-03-28 12:38:25', '2022-03-28 12:38:40', 'admin');
INSERT INTO `deployunit` VALUES (2, '用户中心', 'http', '8080', '测试用户中心服务', '2022-03-28 12:40:23', '2022-05-06 10:51:34', 'admin');
INSERT INTO `deployunit` VALUES (3, '测试本机', 'http', '80', '测试本机发布单元', '2022-05-04 15:05:52', '2022-05-04 15:06:06', 'admin');
INSERT INTO `deployunit` VALUES (4, '医道天年', 'http', '80', '医道天年', '2022-05-07 23:37:46', '2022-05-07 23:38:34', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `dicname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典类名',
  `diccode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典编码',
  `dicitemname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项名',
  `dicitmevalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of dictionary
-- ----------------------------
BEGIN;
INSERT INTO `dictionary` VALUES (1, '测试环境', 'testenviroment1', '功能测试环境1', 'te112', '2019-07-01 00:00:00', '2020-04-21 17:04:47');
INSERT INTO `dictionary` VALUES (2, '测试环境', 'testenviroment', '功能测试环境2', 'te2', '2019-07-01 00:00:00', '2021-10-10 10:51:08');
INSERT INTO `dictionary` VALUES (5, 'http请求方式', 'httpmethod', '查询', 'get', '2020-04-17 17:57:30', '2021-10-10 10:55:55');
INSERT INTO `dictionary` VALUES (6, 'http请求方式', 'httpmethod', '提交数据', 'post', '2020-04-17 17:59:02', '2020-11-15 15:11:35');
INSERT INTO `dictionary` VALUES (9, '测试环境', '测试环境', '测试环境', '测试环境', '2020-04-20 15:42:10', '2021-09-24 16:09:53');
INSERT INTO `dictionary` VALUES (17, '访问方式', 'httpvisittype', 'http访问方式', 'GET', '2020-05-18 21:05:24', '2021-09-24 16:09:55');
INSERT INTO `dictionary` VALUES (18, '访问方式', 'httpvisittype', 'http访问方式', 'POST', '2020-05-18 21:05:55', '2021-09-24 16:09:57');
INSERT INTO `dictionary` VALUES (19, '服务器作用', 'machineuse', '部署', '部署发布单元', '2020-09-09 12:26:21', '2020-09-09 12:09:53');
INSERT INTO `dictionary` VALUES (20, '服务器作用', 'machineuse', '执行测试', '功能测试用例执行机', '2020-09-09 12:27:40', '2020-09-09 12:09:26');
INSERT INTO `dictionary` VALUES (21, '服务器作用', 'machineuse', '执行测试', '性能测试执行机', '2020-09-09 12:29:25', '2021-09-24 16:09:01');
INSERT INTO `dictionary` VALUES (27, '数据库类型', 'DBType', '用例前后置数据库类型', 'Mysql', '2020-11-02 08:29:29', '2021-10-10 10:56:03');
INSERT INTO `dictionary` VALUES (28, '数据库类型', 'DBType', '用例前后置数据库类型', 'Oracle', '2020-11-02 08:30:29', '2021-09-24 16:09:01');
INSERT INTO `dictionary` VALUES (29, '数据库类型', 'DBType', '用例前后置数据库类型', 'Sqlserver', '2020-11-02 08:31:05', '2021-09-24 16:09:03');
INSERT INTO `dictionary` VALUES (34, '环境部署内容', 'machinedeploy', '数据库', 'mysql', '2020-11-06 15:43:18', '2021-09-24 16:09:06');
INSERT INTO `dictionary` VALUES (35, '环境部署内容', 'machinedeploy', '数据库', 'oracle', '2020-11-06 15:43:36', '2021-09-24 16:09:08');
INSERT INTO `dictionary` VALUES (39, 'api请求格式', 'requestcontentype', '请求数据格式', 'Form表单', '2020-11-10 08:43:55', '2021-09-24 16:09:15');
INSERT INTO `dictionary` VALUES (40, 'api请求格式', 'requestcontentype', '请求数据格式', 'JSON', '2020-11-10 08:44:19', '2021-09-24 16:09:17');
INSERT INTO `dictionary` VALUES (41, 'api响应格式', 'responecontenttype', '响应数据格式', 'json', '2020-11-10 08:50:28', '2021-09-24 16:09:19');
INSERT INTO `dictionary` VALUES (42, 'api响应格式', 'responecontenttype', '响应数据格式', 'html', '2020-11-10 08:55:14', '2021-09-24 16:09:33');
INSERT INTO `dictionary` VALUES (43, 'http请求方式', 'httpmethod', '更新', 'put', '2020-11-15 15:41:55', '2021-09-24 16:09:35');
INSERT INTO `dictionary` VALUES (44, 'http请求方式', 'httpmethod', '删除', 'delete', '2020-11-15 15:42:14', '2021-09-24 16:09:37');
INSERT INTO `dictionary` VALUES (45, '访问方式', 'httpvisittype', '更新', 'PUT', '2020-11-15 15:47:03', '2021-09-24 16:09:39');
INSERT INTO `dictionary` VALUES (46, '访问方式', 'httpvisittype', '删除', 'DELETE', '2020-11-15 15:47:20', '2021-09-24 16:09:41');
INSERT INTO `dictionary` VALUES (47, '功能执行机最大并发数', 'FunctionJmeterProcess', '功能执行机并发Jmeter进程', '2', '2020-11-28 17:02:39', '2021-04-02 12:04:57');
INSERT INTO `dictionary` VALUES (52, '性能执行机Jmeter并发数', 'PerformanceJmeterProcess', '性能测试Jmeter并行数', '1', '2021-04-07 09:08:10', '2021-05-26 23:05:05');
INSERT INTO `dictionary` VALUES (53, '执行计划业务类型', 'planbusinesstype', '执行计划业务类型', '常规测试', '2021-04-20 17:24:17', '2021-09-24 16:09:08');
INSERT INTO `dictionary` VALUES (54, '执行计划业务类型', 'planbusinesstype', '执行计划业务类型', 'CI自动化测试', '2021-04-20 17:24:48', '2021-09-24 16:09:10');
INSERT INTO `dictionary` VALUES (55, '环境部署内容', 'machinedeploy', '数据库', 'pgsql', '2020-11-06 15:43:36', '2021-09-24 16:09:08');
INSERT INTO `dictionary` VALUES (56, '钉钉通知', 'DingDing', 'DingDingToken', 'http://testtoken配置钉钉机器人token', '2022-03-04 15:17:07', '2022-03-04 16:05:57');
INSERT INTO `dictionary` VALUES (57, '邮件通知', 'Mail', 'MailInfo', 'smtp.qq.com,587,1320120114@qq.com,mail,ixagnskznblqfidf', '2022-03-04 16:16:04', '2022-03-04 16:16:04');
COMMIT;

-- ----------------------------
-- Table structure for dispatch
-- ----------------------------
DROP TABLE IF EXISTS `dispatch`;
CREATE TABLE `dispatch` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `execplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划Id',
  `execplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行计划名',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次Id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次名',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机Id',
  `slavername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行机名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `testcasename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `casejmxname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待分配，已分配，已结束，调度异常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `plantype` varchar(20) DEFAULT NULL COMMENT '计划类型',
  `threadnum` bigint(20) DEFAULT NULL COMMENT '线程数',
  `loops` bigint(20) DEFAULT NULL COMMENT '循环数',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COMMENT='调度表';

-- ----------------------------
-- Records of dispatch
-- ----------------------------
BEGIN;
INSERT INTO `dispatch` VALUES (44, 6, '本机性能测试集合1', 36, '2022-05-07-003', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 14:01:39', '2022-05-07 06:02:01', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (45, 6, '本机性能测试集合1', 36, '2022-05-07-003', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 14:01:39', '2022-05-07 06:02:10', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (46, 6, '本机性能测试集合1', 37, '2022-05-07-03', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 16:58:17', '2022-05-07 08:58:33', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (47, 6, '本机性能测试集合1', 37, '2022-05-07-03', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 16:58:17', '2022-05-07 08:58:41', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (48, 6, '本机性能测试集合1', 38, '2022-05-07-004', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:11:21', '2022-05-07 09:11:38', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (49, 6, '本机性能测试集合1', 38, '2022-05-07-004', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:11:21', '2022-05-07 09:11:46', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (50, 6, '本机性能测试集合1', 39, '2022-05-07-005', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:29:28', '2022-05-07 09:29:45', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (51, 6, '本机性能测试集合1', 39, '2022-05-07-005', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:29:28', '2022-05-07 09:29:57', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (52, 6, '本机性能测试集合1', 40, '2022-05-07-006', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:36:20', '2022-05-07 09:36:40', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (53, 6, '本机性能测试集合1', 40, '2022-05-07-006', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:36:20', '2022-05-07 09:36:53', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (54, 6, '本机性能测试集合1', 41, '2022-05-07-007', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:55:27', '2022-05-07 09:55:44', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (55, 6, '本机性能测试集合1', 41, '2022-05-07-007', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 17:55:27', '2022-05-07 09:55:54', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (56, 6, '本机性能测试集合1', 42, '2022-05-07-008', 1, '执行机173.20.1.9', 5, '本机phpinfo性能测试', 'httpapi', '测试本机', '', '已完成', '2022-05-07 18:36:33', '2022-05-07 10:36:48', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (57, 6, '本机性能测试集合1', 42, '2022-05-07-008', 1, '执行机173.20.1.9', 7, '测试本机数据库查询', 'httpapi', '测试本机', '', '已完成', '2022-05-07 18:36:33', '2022-05-07 10:36:58', '性能', 10, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (58, 7, '医道天年压测', 43, '2022-05-07-009', 1, '执行机173.20.1.9', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-07 23:49:03', '2022-05-07 16:16:52', '性能', 10, 50, NULL, 'admin');
INSERT INTO `dispatch` VALUES (59, 7, '医道天年压测', 43, '2022-05-07-009', 2, '执行机173.20.1.5', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-07 23:49:03', '2022-05-07 16:16:31', '性能', 10, 50, NULL, 'admin');
INSERT INTO `dispatch` VALUES (62, 7, '医道天年压测', 45, '2022-05-11-001', 1, '执行机173.20.1.9', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-11 19:17:43', '2022-05-11 19:25:18', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (63, 7, '医道天年压测', 45, '2022-05-11-001', 2, '执行机173.20.1.5', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-11 19:17:43', '2022-05-11 19:27:09', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (64, 7, '医道天年压测', 45, '2022-05-11-001', 3, '执行机173.20.1.10', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-11 19:17:43', '2022-05-11 19:24:33', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (65, 7, '医道天年压测', 45, '2022-05-11-001', 4, '执行机173.20.1.11', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-11 19:17:43', '2022-05-11 19:24:54', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (66, 7, '医道天年压测', 46, '2022-05-12-001', 1, '执行机173.20.1.9', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-12 00:28:07', '2022-05-12 00:35:27', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (67, 7, '医道天年压测', 46, '2022-05-12-001', 2, '执行机173.20.1.5', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-12 00:28:07', '2022-05-12 00:36:20', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (68, 7, '医道天年压测', 46, '2022-05-12-001', 3, '执行机173.20.1.10', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-12 00:28:07', '2022-05-12 00:35:29', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (69, 7, '医道天年压测', 46, '2022-05-12-001', 4, '执行机173.20.1.11', 10, '压测直播落地页', 'httpapi', '医道天年', '', '已完成', '2022-05-12 00:28:07', '2022-05-12 00:35:09', '性能', 5, 2500, NULL, 'admin');
INSERT INTO `dispatch` VALUES (74, 17, '百度首页性能测试', 53, '2022-05-15-002', 1, '执行机173.20.1.9', 1, '百度首页性能测试', 'httpapi', '测试百度', '', '已完成', '2022-05-15 02:30:40', '2022-05-15 02:31:03', '性能', 2, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (75, 17, '百度首页性能测试', 54, '2022-05-15-003', 1, '执行机173.20.1.9', 1, '百度首页性能测试', 'httpapi', '测试百度', '', '已完成', '2022-05-15 13:04:45', '2022-05-15 13:05:01', '性能', 2, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (76, 17, '百度首页性能测试', 55, '2022-05-15-004', 1, '执行机173.20.1.9', 1, '百度首页性能测试', 'httpapi', '测试百度', '', '已完成', '2022-05-15 15:14:21', '2022-05-15 15:14:49', '性能', 2, 100, NULL, 'admin');
INSERT INTO `dispatch` VALUES (77, 17, '百度首页性能测试', 56, '2022-05-15-005', 1, '执行机173.20.1.9', 1, '百度首页性能测试', 'httpapi', '测试百度', '', '已完成', '2022-05-15 20:15:30', '2022-05-15 20:16:08', '性能', 2, 100, NULL, 'admin');
COMMIT;

-- ----------------------------
-- Table structure for enviroment
-- ----------------------------
DROP TABLE IF EXISTS `enviroment`;
CREATE TABLE `enviroment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `envtype` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境类型',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='环境表';

-- ----------------------------
-- Records of enviroment
-- ----------------------------
BEGIN;
INSERT INTO `enviroment` VALUES (1, '功能测试环境', '功能', '功能测试环境', '2022-03-28 12:37:35', '2022-03-28 12:37:35', 'admin');
INSERT INTO `enviroment` VALUES (6, '性能测试环境', '性能', '性能测试环境', '2022-03-28 15:08:26', '2022-03-28 15:08:26', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for enviroment_assemble
-- ----------------------------
DROP TABLE IF EXISTS `enviroment_assemble`;
CREATE TABLE `enviroment_assemble` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `assemblename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境组件名',
  `assembletype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'mysql，oracle，redis',
  `connectstr` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '连接字',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='环境组件表';

-- ----------------------------
-- Records of enviroment_assemble
-- ----------------------------
BEGIN;
INSERT INTO `enviroment_assemble` VALUES (1, 'mysql组件', 'mysql', 'root,123456,3406,testcenter', 'mysql中间件访问连接字', '2022-03-28 15:29:03', '2022-05-04 11:25:42', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for envmachine
-- ----------------------------
DROP TABLE IF EXISTS `envmachine`;
CREATE TABLE `envmachine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `envid` bigint(20) unsigned NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `machineid` bigint(20) unsigned NOT NULL COMMENT '服务器Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境服务器表';

-- ----------------------------
-- Records of envmachine
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for executeplan
-- ----------------------------
DROP TABLE IF EXISTS `executeplan`;
CREATE TABLE `executeplan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行计划Id',
  `envid` bigint(20) unsigned NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `executeplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行计划名',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，new，waiting，running，pause，finish',
  `usetype` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行类型，function，performance，来区分分配什么slaver',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `businesstype` varchar(80) DEFAULT NULL COMMENT '业务类型，常规测试，CI自动化测试',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `runmode` varchar(20) DEFAULT NULL COMMENT '运行模式，单机运行，多机并发',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';

-- ----------------------------
-- Records of executeplan
-- ----------------------------
BEGIN;
INSERT INTO `executeplan` VALUES (12, 6, '性能测试环境', '医道天年性能测试', 'new', '性能', '', '2022-05-12 00:48:27', '2022-05-12 00:48:27', '常规测试', 'admin', '多机并行');
INSERT INTO `executeplan` VALUES (16, 1, '功能测试环境', '百度首页功能测试', 'new', '功能', '', '2022-05-13 01:26:54', '2022-05-13 01:38:21', '常规测试', 'admin', '单机运行');
INSERT INTO `executeplan` VALUES (17, 6, '性能测试环境', '百度首页性能测试', 'new', '性能', '', '2022-05-13 01:39:13', '2022-05-13 01:39:13', '常规测试', 'admin', '多机并行');
COMMIT;

-- ----------------------------
-- Table structure for executeplan_params
-- ----------------------------
DROP TABLE IF EXISTS `executeplan_params`;
CREATE TABLE `executeplan_params` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `paramstype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '参数类型',
  `keyname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Key',
  `keyvalue` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试集合全局参数表';

-- ----------------------------
-- Records of executeplan_params
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for executeplan_testcase
-- ----------------------------
DROP TABLE IF EXISTS `executeplan_testcase`;
CREATE TABLE `executeplan_testcase` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '发布单元',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划用例表';

-- ----------------------------
-- Records of executeplan_testcase
-- ----------------------------
BEGIN;
INSERT INTO `executeplan_testcase` VALUES (11, 12, 7, 4, '医道天年', '直播落地页', 10, '压测直播落地页', '2022-05-12 00:49:36', '2022-05-12 00:49:36', 'admin');
INSERT INTO `executeplan_testcase` VALUES (12, 16, 1, 1, '测试百度', '百度首页', 11, '测试百度首页', '2022-05-13 01:38:41', '2022-05-13 01:38:41', 'admin');
INSERT INTO `executeplan_testcase` VALUES (13, 17, 1, 1, '测试百度', '百度首页', 1, '百度首页性能测试', '2022-05-13 01:39:34', '2022-05-13 01:39:34', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for executeplanbatch
-- ----------------------------
DROP TABLE IF EXISTS `executeplanbatch`;
CREATE TABLE `executeplanbatch` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行计划Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '批次名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `status` varchar(10) DEFAULT NULL COMMENT '状态，new，waiting，running，pause，finish',
  `source` varchar(10) DEFAULT NULL COMMENT '来源，平台，ci,其他',
  `executeplanname` varchar(100) DEFAULT NULL COMMENT '计划名',
  `exectype` varchar(20) DEFAULT NULL COMMENT '执行类型，立即，某天定时，每天定时',
  `execdate` varchar(20) DEFAULT NULL COMMENT '执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';

-- ----------------------------
-- Records of executeplanbatch
-- ----------------------------
BEGIN;
INSERT INTO `executeplanbatch` VALUES (36, 6, '2022-05-07-003', '2022-05-07 14:01:38', '2022-05-07 14:01:38', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (37, 6, '2022-05-07-03', '2022-05-07 16:58:16', '2022-05-07 16:58:16', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (38, 6, '2022-05-07-004', '2022-05-07 17:11:21', '2022-05-07 17:11:21', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (39, 6, '2022-05-07-005', '2022-05-07 17:29:28', '2022-05-07 17:29:28', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (40, 6, '2022-05-07-006', '2022-05-07 17:36:20', '2022-05-07 17:36:20', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (41, 6, '2022-05-07-007', '2022-05-07 17:55:27', '2022-05-07 17:55:27', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (42, 6, '2022-05-07-008', '2022-05-07 18:36:33', '2022-05-07 18:36:33', 'admin', '已完成', '平台', '本机性能测试集合1', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (43, 7, '2022-05-07-009', '2022-05-07 23:49:02', '2022-05-07 23:49:02', 'admin', '已完成', '平台', '医道天年压测', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (45, 7, '2022-05-11-001', '2022-05-11 19:17:42', '2022-05-11 19:17:42', 'admin', '已完成', '平台', '医道天年压测', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (46, 7, '2022-05-12-001', '2022-05-12 00:28:06', '2022-05-12 00:28:06', 'admin', '已完成', '平台', '医道天年压测', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (53, 17, '2022-05-15-002', '2022-05-15 02:30:40', '2022-05-15 02:30:40', 'admin', '已完成', '平台', '百度首页性能测试', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (54, 17, '2022-05-15-003', '2022-05-15 13:04:44', '2022-05-15 13:04:44', 'admin', '已完成', '平台', '百度首页性能测试', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (55, 17, '2022-05-15-004', '2022-05-15 15:14:21', '2022-05-15 15:14:21', 'admin', '已完成', '平台', '百度首页性能测试', '立即执行', ':00');
INSERT INTO `executeplanbatch` VALUES (56, 17, '2022-05-15-005', '2022-05-15 20:08:35', '2022-05-15 20:08:35', 'admin', '已完成', '平台', '百度首页性能测试', '某天定时', '2022-05-15 20:15:00');
INSERT INTO `executeplanbatch` VALUES (57, 17, '2022-05-15-006', '2022-05-15 20:09:40', '2022-05-15 20:09:40', 'admin', '待执行', '平台', '百度首页性能测试', '每天定时', '20:35:00');
COMMIT;

-- ----------------------------
-- Table structure for macdepunit
-- ----------------------------
DROP TABLE IF EXISTS `macdepunit`;
CREATE TABLE `macdepunit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `depunitid` bigint(20) unsigned DEFAULT NULL COMMENT '发布单元Id',
  `assembleid` bigint(20) unsigned DEFAULT NULL COMMENT '组件Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `assembletype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '组件类型',
  `machineid` bigint(20) unsigned NOT NULL COMMENT '服务器Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `envid` bigint(20) unsigned NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `visittype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '访问方式，ip,域名',
  `domain` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '服务域名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='服务器发布单元表';

-- ----------------------------
-- Records of macdepunit
-- ----------------------------
BEGIN;
INSERT INTO `macdepunit` VALUES (1, 1, NULL, '测试百度', '发布单元', 1, '测试服务器', 1, '功能测试环境', '域名', 'www.baidu.com', '2022-03-28 12:39:02', '2022-03-28 12:41:25', 'admin');
INSERT INTO `macdepunit` VALUES (2, 2, NULL, 'testcenterservice', '发布单元', 1, '测试服务器', 1, '功能测试环境', 'ip', '', '2022-03-28 12:40:40', '2022-03-28 12:41:21', 'admin');
INSERT INTO `macdepunit` VALUES (3, 2, NULL, 'testcenterservice', '发布单元', 1, '测试服务器', 6, '性能测试环境', 'ip', '/', '2022-03-28 15:10:33', '2022-03-28 15:10:33', 'admin');
INSERT INTO `macdepunit` VALUES (4, NULL, 1, 'mysql组件', '组件', 1, '测试服务器', 1, '功能测试环境', 'ip', '/', '2022-03-28 16:08:56', '2022-03-28 16:08:56', 'admin');
INSERT INTO `macdepunit` VALUES (7, 4, NULL, '医道天年', '发布单元', 1, '测试服务器', 6, '性能测试环境', '域名', 'www.syjf1.cn', '2022-05-07 23:39:34', '2022-05-07 23:39:34', 'admin');
INSERT INTO `macdepunit` VALUES (8, 1, NULL, '测试百度', '发布单元', 1, '测试服务器', 6, '性能测试环境', '域名', 'www.baidu.com', '2022-05-15 01:52:51', '2022-05-15 01:53:11', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for machine
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `cpu` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'cpu',
  `disk` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'disk',
  `mem` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内存',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';

-- ----------------------------
-- Records of machine
-- ----------------------------
BEGIN;
INSERT INTO `machine` VALUES (1, '测试服务器', '192.168.1.7', '4核', '500G', '16G', '2022-03-28 12:37:21', '2022-05-04 13:06:23', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for performancereportfilelog
-- ----------------------------
DROP TABLE IF EXISTS `performancereportfilelog`;
CREATE TABLE `performancereportfilelog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `execplanid` bigint(20) unsigned NOT NULL COMMENT 'execplanid',
  `batchid` bigint(20) unsigned NOT NULL COMMENT 'batchid',
  `caseid` bigint(20) unsigned NOT NULL COMMENT 'caseid',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT 'slaverid',
  `filename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名，planid+batchid+slaverid',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4 COMMENT='性能报告文件记录表';

-- ----------------------------
-- Records of performancereportfilelog
-- ----------------------------
BEGIN;
INSERT INTO `performancereportfilelog` VALUES (155, 7, 46, 10, 1, '7-46-10-1-23', '已完成', '2022-05-12 00:28:15', '2022-05-12 00:35:27');
INSERT INTO `performancereportfilelog` VALUES (156, 7, 46, 10, 1, '7-46-10-1-26', '已完成', '2022-05-12 00:28:15', '2022-05-12 00:35:27');
INSERT INTO `performancereportfilelog` VALUES (157, 7, 46, 10, 1, '7-46-10-1-27', '已完成', '2022-05-12 00:28:15', '2022-05-12 00:35:27');
INSERT INTO `performancereportfilelog` VALUES (158, 7, 46, 10, 1, '7-46-10-1-25', '已完成', '2022-05-12 00:28:15', '2022-05-12 00:35:27');
INSERT INTO `performancereportfilelog` VALUES (159, 7, 46, 10, 1, '7-46-10-1-24', '已完成', '2022-05-12 00:28:15', '2022-05-12 00:35:27');
INSERT INTO `performancereportfilelog` VALUES (160, 7, 46, 10, 2, '7-46-10-2-23', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:36:20');
INSERT INTO `performancereportfilelog` VALUES (161, 7, 46, 10, 2, '7-46-10-2-22', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:36:20');
INSERT INTO `performancereportfilelog` VALUES (162, 7, 46, 10, 2, '7-46-10-2-27', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:36:20');
INSERT INTO `performancereportfilelog` VALUES (163, 7, 46, 10, 2, '7-46-10-2-25', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:36:20');
INSERT INTO `performancereportfilelog` VALUES (164, 7, 46, 10, 2, '7-46-10-2-26', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:36:20');
INSERT INTO `performancereportfilelog` VALUES (165, 7, 46, 10, 3, '7-46-10-3-24', '已完成', '2022-05-12 00:28:17', '2022-05-12 00:35:29');
INSERT INTO `performancereportfilelog` VALUES (166, 7, 46, 10, 3, '7-46-10-3-25', '已完成', '2022-05-12 00:28:17', '2022-05-12 00:35:29');
INSERT INTO `performancereportfilelog` VALUES (167, 7, 46, 10, 3, '7-46-10-3-26', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:35:29');
INSERT INTO `performancereportfilelog` VALUES (168, 7, 46, 10, 3, '7-46-10-3-23', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:35:29');
INSERT INTO `performancereportfilelog` VALUES (169, 7, 46, 10, 3, '7-46-10-3-22', '已完成', '2022-05-12 00:28:16', '2022-05-12 00:35:29');
INSERT INTO `performancereportfilelog` VALUES (170, 7, 46, 10, 4, '7-46-10-4-26', '已完成', '2022-05-12 00:28:18', '2022-05-12 00:35:09');
INSERT INTO `performancereportfilelog` VALUES (171, 7, 46, 10, 4, '7-46-10-4-25', '已完成', '2022-05-12 00:28:18', '2022-05-12 00:35:09');
INSERT INTO `performancereportfilelog` VALUES (172, 7, 46, 10, 4, '7-46-10-4-24', '已完成', '2022-05-12 00:28:18', '2022-05-12 00:35:09');
INSERT INTO `performancereportfilelog` VALUES (173, 7, 46, 10, 4, '7-46-10-4-22', '已完成', '2022-05-12 00:28:18', '2022-05-12 00:35:09');
INSERT INTO `performancereportfilelog` VALUES (174, 7, 46, 10, 4, '7-46-10-4-23', '已完成', '2022-05-12 00:28:19', '2022-05-12 00:35:09');
INSERT INTO `performancereportfilelog` VALUES (175, 17, 52, 1, 1, '17-52-1-1-23', '已完成', '2022-05-15 01:53:47', '2022-05-15 01:54:05');
INSERT INTO `performancereportfilelog` VALUES (176, 17, 53, 1, 1, '17-53-1-1-22', '已完成', '2022-05-15 02:30:47', '2022-05-15 02:31:03');
INSERT INTO `performancereportfilelog` VALUES (177, 17, 53, 1, 1, '17-53-1-1-23', '已完成', '2022-05-15 02:30:47', '2022-05-15 02:31:03');
INSERT INTO `performancereportfilelog` VALUES (178, 17, 54, 1, 1, '17-54-1-1-23', '已完成', '2022-05-15 13:04:52', '2022-05-15 13:05:01');
INSERT INTO `performancereportfilelog` VALUES (179, 17, 54, 1, 1, '17-54-1-1-22', '已完成', '2022-05-15 13:04:52', '2022-05-15 13:05:01');
INSERT INTO `performancereportfilelog` VALUES (180, 17, 55, 1, 1, '17-55-1-1-22', '已完成', '2022-05-15 15:14:27', '2022-05-15 15:14:49');
INSERT INTO `performancereportfilelog` VALUES (181, 17, 55, 1, 1, '17-55-1-1-23', '初始化', '2022-05-15 15:14:28', '2022-05-15 15:14:28');
INSERT INTO `performancereportfilelog` VALUES (182, 17, 56, 1, 1, '17-56-1-1-23', '已完成', '2022-05-15 20:15:37', '2022-05-15 20:16:08');
INSERT INTO `performancereportfilelog` VALUES (183, 17, 56, 1, 1, '17-56-1-1-22', '已完成', '2022-05-15 20:15:38', '2022-05-15 20:16:08');
COMMIT;

-- ----------------------------
-- Table structure for performancereportsource
-- ----------------------------
DROP TABLE IF EXISTS `performancereportsource`;
CREATE TABLE `performancereportsource` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `planid` bigint(20) unsigned NOT NULL COMMENT '执行计划Id',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次Id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `testclass` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '解析文件匹配sample',
  `runtime` double(11,2) NOT NULL COMMENT '运行时长',
  `source` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '解析文件目录',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待解析，已解析',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `totalcasenums` bigint(20) unsigned NOT NULL COMMENT '用例数',
  `totalcasepassnums` bigint(20) unsigned NOT NULL COMMENT '用例成功数',
  `totalcasefailnums` bigint(20) unsigned NOT NULL COMMENT '用例失败数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COMMENT='性能报告路径表';

-- ----------------------------
-- Records of performancereportsource
-- ----------------------------
BEGIN;
INSERT INTO `performancereportsource` VALUES (1, 6, 36, '2022-05-07-003', 1, 5, 'HttpApiPerformance', 20.09, '/home/slaverservice/performancereport/1-6-5-2022-05-07-003', '已解析', '2022-05-07 06:02:01', '2022-05-07 06:02:01', 'admin', 0, 0, 0);
INSERT INTO `performancereportsource` VALUES (2, 6, 36, '2022-05-07-003', 1, 7, 'HttpApiPerformance', 3.96, '/home/slaverservice/performancereport/1-6-7-2022-05-07-003', '已解析', '2022-05-07 06:02:10', '2022-05-07 06:02:10', 'admin', 0, 0, 0);
INSERT INTO `performancereportsource` VALUES (3, 6, 37, '2022-05-07-03', 1, 5, 'HttpApiPerformance', 11.41, '/home/slaverservice/performancereport/1-6-5-2022-05-07-03', '已解析', '2022-05-07 08:58:33', '2022-05-07 08:58:33', 'admin', 0, 0, 0);
INSERT INTO `performancereportsource` VALUES (4, 6, 37, '2022-05-07-03', 1, 7, 'HttpApiPerformance', 4.14, '/home/slaverservice/performancereport/1-6-7-2022-05-07-03', '已解析', '2022-05-07 08:58:41', '2022-05-07 08:58:41', 'admin', 0, 0, 0);
INSERT INTO `performancereportsource` VALUES (5, 6, 38, '2022-05-07-004', 1, 5, 'HttpApiPerformance', 11.49, '/home/slaverservice/performancereport/1-6-5-2022-05-07-004', '已解析', '2022-05-07 09:11:38', '2022-05-07 09:11:38', 'admin', 0, 0, 0);
INSERT INTO `performancereportsource` VALUES (6, 6, 38, '2022-05-07-004', 1, 7, 'HttpApiPerformance', 4.14, '/home/slaverservice/performancereport/1-6-7-2022-05-07-004', '已解析', '2022-05-07 09:11:46', '2022-05-07 09:11:46', 'admin', 0, 0, 0);
INSERT INTO `performancereportsource` VALUES (7, 6, 39, '2022-05-07-005', 1, 5, 'HttpApiPerformance', 13.28, '/home/slaverservice/performancereport/1-6-5-2022-05-07-005', '已解析', '2022-05-07 09:29:45', '2022-05-07 09:29:45', 'admin', 200, 200, 0);
INSERT INTO `performancereportsource` VALUES (8, 6, 39, '2022-05-07-005', 1, 7, 'HttpApiPerformance', 5.32, '/home/slaverservice/performancereport/1-6-7-2022-05-07-005', '已解析', '2022-05-07 09:29:57', '2022-05-07 09:29:57', 'admin', 100, 100, 0);
INSERT INTO `performancereportsource` VALUES (9, 6, 40, '2022-05-07-006', 1, 5, 'HttpApiPerformance', 13.92, '/home/slaverservice/performancereport/1-6-5-2022-05-07-006', '已解析', '2022-05-07 09:36:40', '2022-05-07 09:36:40', 'admin', 100, 100, 0);
INSERT INTO `performancereportsource` VALUES (10, 6, 40, '2022-05-07-006', 1, 7, 'HttpApiPerformance', 5.99, '/home/slaverservice/performancereport/1-6-7-2022-05-07-006', '已解析', '2022-05-07 09:36:53', '2022-05-07 09:36:53', 'admin', 100, 100, 0);
INSERT INTO `performancereportsource` VALUES (11, 6, 41, '2022-05-07-007', 1, 5, 'HttpApiPerformance', 12.64, '/home/slaverservice/performancereport/1-6-5-2022-05-07-007', '已解析', '2022-05-07 09:55:44', '2022-05-07 09:55:44', 'admin', 900, 900, 0);
INSERT INTO `performancereportsource` VALUES (12, 6, 41, '2022-05-07-007', 1, 7, 'HttpApiPerformance', 5.47, '/home/slaverservice/performancereport/1-6-7-2022-05-07-007', '已解析', '2022-05-07 09:55:54', '2022-05-07 09:55:54', 'admin', 1000, 1000, 0);
INSERT INTO `performancereportsource` VALUES (13, 6, 42, '2022-05-07-008', 1, 5, 'HttpApiPerformance', 11.60, '/home/slaverservice/performancereport/1-6-5-2022-05-07-008', '已解析', '2022-05-07 10:36:48', '2022-05-07 10:36:48', 'admin', 1000, 1000, 0);
INSERT INTO `performancereportsource` VALUES (14, 6, 42, '2022-05-07-008', 1, 7, 'HttpApiPerformance', 5.12, '/home/slaverservice/performancereport/1-6-7-2022-05-07-008', '已解析', '2022-05-07 10:36:58', '2022-05-07 10:36:58', 'admin', 1000, 1000, 0);
INSERT INTO `performancereportsource` VALUES (15, 7, 43, '2022-05-07-009', 2, 10, 'HttpApiPerformance', 9.40, '/home/slaverservice/performancereport/2-7-10-2022-05-07-009', '已解析', '2022-05-07 16:16:32', '2022-05-07 16:16:32', 'admin', 500, 500, 0);
INSERT INTO `performancereportsource` VALUES (16, 7, 43, '2022-05-07-009', 1, 10, 'HttpApiPerformance', 29.84, '/home/slaverservice/performancereport/1-7-10-2022-05-07-009', '已解析', '2022-05-07 16:16:52', '2022-05-07 16:16:52', 'admin', 450, 450, 0);
INSERT INTO `performancereportsource` VALUES (17, 7, 44, '2022-05-08-001', 2, 10, 'HttpApiPerformance', 742.11, '/home/slaverservice/performancereport/2-7-10-2022-05-08-001', '已解析', '2022-05-07 16:34:19', '2022-05-07 16:34:19', 'admin', 50000, 49989, 11);
INSERT INTO `performancereportsource` VALUES (19, 7, 45, '2022-05-11-001', 3, 10, 'HttpApiPerformance', 403.76, '/home/slaverservice/performancereport/3-7-10-2022-05-11-001', '已解析', '2022-05-11 19:24:34', '2022-05-11 19:24:34', 'admin', 12500, 12500, 0);
INSERT INTO `performancereportsource` VALUES (20, 7, 45, '2022-05-11-001', 4, 10, 'HttpApiPerformance', 424.24, '/home/slaverservice/performancereport/4-7-10-2022-05-11-001', '已解析', '2022-05-11 19:24:55', '2022-05-11 19:24:55', 'admin', 12500, 12500, 0);
INSERT INTO `performancereportsource` VALUES (21, 7, 45, '2022-05-11-001', 1, 10, 'HttpApiPerformance', 448.63, '/home/slaverservice/performancereport/1-7-10-2022-05-11-001', '已解析', '2022-05-11 19:25:18', '2022-05-11 19:25:18', 'admin', 12500, 12500, 0);
INSERT INTO `performancereportsource` VALUES (22, 7, 45, '2022-05-11-001', 2, 10, 'HttpApiPerformance', 559.83, '/home/slaverservice/performancereport/2-7-10-2022-05-11-001', '已解析', '2022-05-11 19:27:09', '2022-05-11 19:27:09', 'admin', 12500, 12500, 0);
INSERT INTO `performancereportsource` VALUES (23, 7, 46, '2022-05-12-001', 4, 10, 'HttpApiPerformance', 412.94, '/home/slaverservice/performancereport/4-7-10-2022-05-12-001', '已解析', '2022-05-12 00:35:09', '2022-05-12 00:35:09', 'admin', 12500, 12500, 0);
INSERT INTO `performancereportsource` VALUES (24, 7, 46, '2022-05-12-001', 1, 10, 'HttpApiPerformance', 433.26, '/home/slaverservice/performancereport/1-7-10-2022-05-12-001', '已解析', '2022-05-12 00:35:27', '2022-05-12 00:35:27', 'admin', 12500, 12499, 1);
INSERT INTO `performancereportsource` VALUES (25, 7, 46, '2022-05-12-001', 3, 10, 'HttpApiPerformance', 433.72, '/home/slaverservice/performancereport/3-7-10-2022-05-12-001', '已解析', '2022-05-12 00:35:29', '2022-05-12 00:35:29', 'admin', 12500, 12499, 1);
INSERT INTO `performancereportsource` VALUES (26, 7, 46, '2022-05-12-001', 2, 10, 'HttpApiPerformance', 486.35, '/home/slaverservice/performancereport/2-7-10-2022-05-12-001', '已解析', '2022-05-12 00:36:21', '2022-05-12 00:36:21', 'admin', 12500, 12499, 1);
INSERT INTO `performancereportsource` VALUES (27, 17, 52, '2022-05-15-001', 1, 1, 'HttpApiPerformance', 18.53, '/home/slaverservice/performancereport/1-17-1-2022-05-15-001', '已解析', '2022-05-15 01:54:05', '2022-05-15 01:54:05', 'admin', 100, 100, 0);
INSERT INTO `performancereportsource` VALUES (28, 17, 53, '2022-05-15-002', 1, 1, 'HttpApiPerformance', 16.87, '/home/slaverservice/performancereport/1-17-1-2022-05-15-002', '已解析', '2022-05-15 02:31:03', '2022-05-15 02:31:03', 'admin', 200, 200, 0);
INSERT INTO `performancereportsource` VALUES (29, 17, 54, '2022-05-15-003', 1, 1, 'HttpApiPerformance', 9.62, '/home/slaverservice/performancereport/1-17-1-2022-05-15-003', '已解析', '2022-05-15 13:05:01', '2022-05-15 13:05:01', 'admin', 200, 200, 0);
INSERT INTO `performancereportsource` VALUES (30, 17, 55, '2022-05-15-004', 1, 1, 'HttpApiPerformance', 22.03, '/home/slaverservice/performancereport/1-17-1-2022-05-15-004', '已解析', '2022-05-15 15:14:49', '2022-05-15 15:14:49', 'admin', 100, 100, 0);
INSERT INTO `performancereportsource` VALUES (31, 17, 56, '2022-05-15-005', 1, 1, 'HttpApiPerformance', 32.00, '/home/slaverservice/performancereport/1-17-1-2022-05-15-005', '已解析', '2022-05-15 20:16:09', '2022-05-15 20:16:09', 'admin', 200, 200, 0);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限Id',
  `resource` varchar(256) NOT NULL COMMENT '权限对应的资源',
  `code` varchar(256) NOT NULL COMMENT '权限的代码/通配符,对应代码中@hasAuthority(xx)',
  `handle` varchar(256) NOT NULL COMMENT '对应的资源操作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, '账号', 'account:list', '列表');
INSERT INTO `permission` VALUES (2, '账号', 'account:detail', '详情');
INSERT INTO `permission` VALUES (3, '账号', 'account:add', '添加');
INSERT INTO `permission` VALUES (4, '账号', 'account:update', '更新');
INSERT INTO `permission` VALUES (5, '账号', 'account:delete', '删除');
INSERT INTO `permission` VALUES (6, '账号', 'account:search', '搜索');
INSERT INTO `permission` VALUES (7, '角色', 'role:list', '列表');
INSERT INTO `permission` VALUES (8, '角色', 'role:detail', '详情');
INSERT INTO `permission` VALUES (9, '角色', 'role:add', '添加');
INSERT INTO `permission` VALUES (10, '角色', 'role:update', '更新');
INSERT INTO `permission` VALUES (11, '角色', 'role:delete', '删除');
INSERT INTO `permission` VALUES (12, '角色', 'role:search', '搜索');
INSERT INTO `permission` VALUES (13, '测试表', 'table:list', '列表');
INSERT INTO `permission` VALUES (14, '字典', 'dictionary:list', '列表');
INSERT INTO `permission` VALUES (15, '字典', 'dictionary:add', '添加');
INSERT INTO `permission` VALUES (16, '字典', 'dictionary:search', '搜索');
INSERT INTO `permission` VALUES (17, '字典', 'dictionary:update', '修改');
INSERT INTO `permission` VALUES (18, '字典', 'dictionary:delete', '删除');
INSERT INTO `permission` VALUES (19, '发布单元', 'depunit:list', '列表');
INSERT INTO `permission` VALUES (20, '发布单元', 'depunit:detail', '详情');
INSERT INTO `permission` VALUES (21, '发布单元', 'depunit:add', '添加');
INSERT INTO `permission` VALUES (22, '发布单元', 'depunit:update', '更新');
INSERT INTO `permission` VALUES (23, '发布单元', 'depunit:delete', '删除');
INSERT INTO `permission` VALUES (24, '发布单元', 'depunit:search', '搜索');
INSERT INTO `permission` VALUES (25, '测试环境', 'enviroment:list', '列表');
INSERT INTO `permission` VALUES (26, '测试环境', 'enviroment:detail', '详情');
INSERT INTO `permission` VALUES (27, '测试环境', 'enviroment:add', '添加');
INSERT INTO `permission` VALUES (28, '测试环境', 'enviroment:update', '更新');
INSERT INTO `permission` VALUES (29, '测试环境', 'enviroment:delete', '删除');
INSERT INTO `permission` VALUES (30, '测试环境', 'enviroment:search', '搜索');
INSERT INTO `permission` VALUES (31, '服务器', 'machine:list', '列表');
INSERT INTO `permission` VALUES (32, '服务器', 'machine:detail', '详情');
INSERT INTO `permission` VALUES (33, '服务器', 'machine:add', '添加');
INSERT INTO `permission` VALUES (34, '服务器', 'machine:update', '更新');
INSERT INTO `permission` VALUES (35, '服务器', 'machine:delete', '删除');
INSERT INTO `permission` VALUES (36, '服务器', 'machine:search', '搜索');
INSERT INTO `permission` VALUES (37, '测试人员', 'tester:list', '列表');
INSERT INTO `permission` VALUES (38, '测试人员', 'tester:detail', '详情');
INSERT INTO `permission` VALUES (39, '测试人员', 'tester:add', '添加');
INSERT INTO `permission` VALUES (40, '测试人员', 'tester:update', '更新');
INSERT INTO `permission` VALUES (41, '测试人员', 'tester:delete', '删除');
INSERT INTO `permission` VALUES (42, '测试人员', 'tester:search', '搜索');
INSERT INTO `permission` VALUES (43, 'api', 'api:list', '列表');
INSERT INTO `permission` VALUES (44, 'api', 'api:detail', '详情');
INSERT INTO `permission` VALUES (45, 'api', 'api:add', '添加');
INSERT INTO `permission` VALUES (46, 'api', 'api:update', '更新');
INSERT INTO `permission` VALUES (47, 'api', 'api:delete', '删除');
INSERT INTO `permission` VALUES (48, 'api', 'api:search', '搜索');
INSERT INTO `permission` VALUES (49, 'api参数', 'apiparams:list', '列表');
INSERT INTO `permission` VALUES (50, 'api参数', 'apiparams:detail', '详情');
INSERT INTO `permission` VALUES (51, 'api参数', 'apiparams:add', '添加');
INSERT INTO `permission` VALUES (52, 'api参数', 'apiparams:update', '更新');
INSERT INTO `permission` VALUES (53, 'api参数', 'apiparams:delete', '删除');
INSERT INTO `permission` VALUES (54, 'api参数', 'apiparams:search', '搜索');
INSERT INTO `permission` VALUES (55, '环境服务器', 'envmachine:list', '列表');
INSERT INTO `permission` VALUES (56, '环境服务器', 'envmachine:detail', '详情');
INSERT INTO `permission` VALUES (57, '环境服务器', 'envmachine:add', '添加');
INSERT INTO `permission` VALUES (58, '环境服务器', 'envmachine:update', '更新');
INSERT INTO `permission` VALUES (59, '环境服务器', 'envmachine:delete', '删除');
INSERT INTO `permission` VALUES (60, '环境服务器', 'envmachine:search', '搜索');
INSERT INTO `permission` VALUES (61, '服务器发布单元', 'macdepunit:list', '列表');
INSERT INTO `permission` VALUES (62, '服务器发布单元', 'macdepunit:detail', '详情');
INSERT INTO `permission` VALUES (63, '服务器发布单元', 'macdepunit:add', '添加');
INSERT INTO `permission` VALUES (64, '服务器发布单元', 'macdepunit:update', '更新');
INSERT INTO `permission` VALUES (65, '服务器发布单元', 'macdepunit:delete', '删除');
INSERT INTO `permission` VALUES (66, '服务器发布单元', 'macdepunit:search', '搜索');
INSERT INTO `permission` VALUES (67, 'API用例库', 'apicases:list', '列表');
INSERT INTO `permission` VALUES (68, 'API用例库', 'apicases:detail', '详情');
INSERT INTO `permission` VALUES (69, 'API用例库', 'apicases:add', '增加');
INSERT INTO `permission` VALUES (70, 'API用例库', 'apicases:update', '更新');
INSERT INTO `permission` VALUES (71, 'API用例库', 'apicases:delete', '删除');
INSERT INTO `permission` VALUES (72, 'API用例库', 'apicases:search', '查询');
INSERT INTO `permission` VALUES (73, '执行机', 'slaver:list', '列表');
INSERT INTO `permission` VALUES (74, '执行机', 'slaver:detail', '详情');
INSERT INTO `permission` VALUES (75, '执行机', 'slaver:add', '增加');
INSERT INTO `permission` VALUES (76, '执行机', 'slaver:update', '更新');
INSERT INTO `permission` VALUES (77, '执行机', 'slaver:delete', '删除');
INSERT INTO `permission` VALUES (78, '执行机', 'slaver:search', '查询');
INSERT INTO `permission` VALUES (79, '执行计划', 'executeplan:list', '列表');
INSERT INTO `permission` VALUES (80, '执行计划', 'executeplan:detail', '详情');
INSERT INTO `permission` VALUES (81, '执行计划', 'executeplan:add', '增加');
INSERT INTO `permission` VALUES (82, '执行计划', 'executeplan:update', '更新');
INSERT INTO `permission` VALUES (83, '执行计划', 'executeplan:delete', '删除');
INSERT INTO `permission` VALUES (84, '执行计划', 'executeplan:search', '查询');
INSERT INTO `permission` VALUES (85, 'api报告', 'apireport:list', '列表');
INSERT INTO `permission` VALUES (86, 'api报告', 'apireport:detail', '详情');
INSERT INTO `permission` VALUES (87, 'api报告', 'apireport:add', '增加');
INSERT INTO `permission` VALUES (88, 'api报告', 'apireport:update', '更新');
INSERT INTO `permission` VALUES (89, 'api报告', 'apireport:delete', '删除');
INSERT INTO `permission` VALUES (90, 'api报告', 'apireport:search', '查询');
INSERT INTO `permission` VALUES (91, 'API用例库', 'apicases:params', '参数');
INSERT INTO `permission` VALUES (92, '用例前后条件', 'apicases_condition:list', '列表');
INSERT INTO `permission` VALUES (93, '用例前后条件', 'apicases_condition:detail', '详情');
INSERT INTO `permission` VALUES (94, '用例前后条件', 'apicases_condition:add', '增加');
INSERT INTO `permission` VALUES (95, '用例前后条件', 'apicases_condition:update', '更新');
INSERT INTO `permission` VALUES (96, '用例前后条件', 'apicases_condition:delete', '删除');
INSERT INTO `permission` VALUES (97, '用例前后条件', 'apicases_condition:search', '查询');
INSERT INTO `permission` VALUES (98, '环境组件', 'enviroment_assemble:list', '列表');
INSERT INTO `permission` VALUES (99, '环境组件', 'enviroment_assemble:detail', '详情');
INSERT INTO `permission` VALUES (100, '环境组件', 'enviroment_assemble:add', '增加');
INSERT INTO `permission` VALUES (101, '环境组件', 'enviroment_assemble:update', '更新');
INSERT INTO `permission` VALUES (102, '环境组件', 'enviroment_assemble:delete', '删除');
INSERT INTO `permission` VALUES (103, '环境组件', 'enviroment_assemble:search', '查询');
INSERT INTO `permission` VALUES (104, '调度', 'dispatch:list', '列表');
INSERT INTO `permission` VALUES (105, '调度', 'dispatch:detail', '详情');
INSERT INTO `permission` VALUES (106, '调度', 'dispatch:add', '增加');
INSERT INTO `permission` VALUES (107, '调度', 'dispatch:update', '更新');
INSERT INTO `permission` VALUES (108, '调度', 'dispatch:delete', '删除');
INSERT INTO `permission` VALUES (109, '调度', 'dispatch:search', '查询');
INSERT INTO `permission` VALUES (110, '性能报告', 'apiperformancereport:list', '列表');
INSERT INTO `permission` VALUES (111, '性能报告', 'apiperformancereport:detail', '列表');
INSERT INTO `permission` VALUES (112, '性能报告', 'apiperformancereport:add', '列表');
INSERT INTO `permission` VALUES (113, '性能报告', 'apiperformancereport:update', '列表');
INSERT INTO `permission` VALUES (114, '性能报告', 'apiperformancereport:delete', '列表');
INSERT INTO `permission` VALUES (115, '性能报告', 'apiperformancereport:search', '列表');
INSERT INTO `permission` VALUES (116, '性能报告', 'apiperformancestatistics:list', '列表');
INSERT INTO `permission` VALUES (117, '性能报告', 'apiperformancestatistics:detail', '详情');
INSERT INTO `permission` VALUES (118, '性能报告', 'apiperformancestatistics:add', '增加');
INSERT INTO `permission` VALUES (119, '性能报告', 'apiperformancestatistics:update', '更新');
INSERT INTO `permission` VALUES (120, '性能报告', 'apiperformancestatistics:delete', '删除');
INSERT INTO `permission` VALUES (121, '性能报告', 'apiperformancestatistics:search', '查询');
INSERT INTO `permission` VALUES (122, '功能报告统计', 'apireportstatics:list', '列表');
INSERT INTO `permission` VALUES (123, '功能报告统计', 'apireportstatics:search', '查询');
INSERT INTO `permission` VALUES (124, '执行计划批次', 'executeplanbatch:list', '列表');
INSERT INTO `permission` VALUES (125, '执行计划批次', 'executeplanbatch:detail', '详情');
INSERT INTO `permission` VALUES (126, '执行计划批次', 'executeplanbatch:add', '增加');
INSERT INTO `permission` VALUES (127, '执行计划批次', 'executeplanbatch:update', '更新');
INSERT INTO `permission` VALUES (128, '执行计划批次', 'executeplanbatch:delete', '删除');
INSERT INTO `permission` VALUES (129, '执行计划批次', 'executeplanbatch:search', '查询');
INSERT INTO `permission` VALUES (130, '条件管理', 'condition:list', '列表');
INSERT INTO `permission` VALUES (131, '条件管理', 'condition:search', '查询');
INSERT INTO `permission` VALUES (132, '条件管理', 'condition:add', '增加');
INSERT INTO `permission` VALUES (133, '条件管理', 'condition:detail', '详情');
INSERT INTO `permission` VALUES (134, '条件管理', 'condition:update', '更新');
INSERT INTO `permission` VALUES (135, '条件管理', 'condition:delete', '删除');
INSERT INTO `permission` VALUES (136, 'api条件', 'apicondition:list', '列表');
INSERT INTO `permission` VALUES (137, 'api条件', 'apicondition:search', '查询');
INSERT INTO `permission` VALUES (138, 'api条件', 'apicondition:add', '增加');
INSERT INTO `permission` VALUES (139, 'api条件', 'apicondition:detail', '详情');
INSERT INTO `permission` VALUES (140, 'api条件', 'apicondition:update', '更新');
INSERT INTO `permission` VALUES (141, 'api条件', 'apicondition:delete', '删除');
INSERT INTO `permission` VALUES (142, '变量管理', 'testvariables:list', '列表');
INSERT INTO `permission` VALUES (143, '变量管理', 'testvariables:search', '查询');
INSERT INTO `permission` VALUES (144, '变量管理', 'testvariables:add', '增加');
INSERT INTO `permission` VALUES (145, '变量管理', 'testvariables:detail', '详情');
INSERT INTO `permission` VALUES (146, '变量管理', 'testvariables:update', '更新');
INSERT INTO `permission` VALUES (147, '变量管理', 'testvariables:delete', '删除');
INSERT INTO `permission` VALUES (148, '用例变量', 'ApicasesVariables:list', '列表');
INSERT INTO `permission` VALUES (149, '用例变量', 'ApicasesVariables:search', '查询');
INSERT INTO `permission` VALUES (150, '用例变量', 'ApicasesVariables:add', '增加');
INSERT INTO `permission` VALUES (151, '用例变量', 'ApicasesVariables:detail', '详情');
INSERT INTO `permission` VALUES (152, '用例变量', 'ApicasesVariables:update', '更新');
INSERT INTO `permission` VALUES (153, '用例变量', 'ApicasesVariables:delete', '删除');
INSERT INTO `permission` VALUES (154, '变量值', 'testvariablesvalue:list', '列表');
INSERT INTO `permission` VALUES (155, '变量值', 'testvariablesvalue:search', '查询');
INSERT INTO `permission` VALUES (156, '变量值', 'testvariablesvalue:add', '增加');
INSERT INTO `permission` VALUES (157, '变量值', 'testvariablesvalue:detail', '详情');
INSERT INTO `permission` VALUES (158, '变量值', 'testvariablesvalue:update', '更新');
INSERT INTO `permission` VALUES (159, '变量值', 'testvariablesvalue:delete', '删除');
INSERT INTO `permission` VALUES (160, '条件报告', 'testconditionreport:list', '列表');
INSERT INTO `permission` VALUES (161, '条件报告', 'testconditionreport:search', '查询');
INSERT INTO `permission` VALUES (162, '条件报告', 'testconditionreport:add', '增加');
INSERT INTO `permission` VALUES (163, '条件报告', 'testconditionreport:detail', '详情');
INSERT INTO `permission` VALUES (164, '条件报告', 'testconditionreport:update', '更新');
INSERT INTO `permission` VALUES (165, '条件报告', 'testconditionreport:delete', '删除');
INSERT INTO `permission` VALUES (166, '脚本条件', 'scriptcondition:list', '列表');
INSERT INTO `permission` VALUES (167, '脚本条件', 'scriptcondition:detail', '详情');
INSERT INTO `permission` VALUES (168, '脚本条件', 'scriptcondition:add', '添加');
INSERT INTO `permission` VALUES (169, '脚本条件', 'scriptcondition:update', '更新');
INSERT INTO `permission` VALUES (170, '脚本条件', 'scriptcondition:delete', '删除');
INSERT INTO `permission` VALUES (171, '脚本条件', 'scriptcondition:search', '搜索');
INSERT INTO `permission` VALUES (172, '数据库条件', 'dbcondition:list', '列表');
INSERT INTO `permission` VALUES (173, '数据库条件', 'dbcondition:detail', '详情');
INSERT INTO `permission` VALUES (174, '数据库条件', 'dbcondition:add', '添加');
INSERT INTO `permission` VALUES (175, '数据库条件', 'dbcondition:update', '更新');
INSERT INTO `permission` VALUES (176, '数据库条件', 'dbcondition:delete', '删除');
INSERT INTO `permission` VALUES (177, '数据库条件', 'dbcondition:search', '搜索');
INSERT INTO `permission` VALUES (178, '条件顺序', 'conditionorder:list', '列表');
INSERT INTO `permission` VALUES (179, '条件顺序', 'conditionorder:detail', '详情');
INSERT INTO `permission` VALUES (180, '条件顺序', 'conditionorder:add', '添加');
INSERT INTO `permission` VALUES (181, '条件顺序', 'conditionorder:moveup', '上移');
INSERT INTO `permission` VALUES (182, '条件顺序', 'conditionorder:movedown', '下移');
INSERT INTO `permission` VALUES (183, '条件顺序', 'conditionorder:search', '搜索');
INSERT INTO `permission` VALUES (185, '随机变量', 'variables:list', '列表');
INSERT INTO `permission` VALUES (186, '随机变量', 'variables:detail', '详情');
INSERT INTO `permission` VALUES (187, '随机变量', 'variables:update', '修改');
INSERT INTO `permission` VALUES (188, '随机变量', 'variables:delete', '删除');
INSERT INTO `permission` VALUES (189, '随机变量', 'variables:add', '添加');
INSERT INTO `permission` VALUES (190, '随机变量', 'variables:search', '搜索');
INSERT INTO `permission` VALUES (191, '数据库变量', 'dbvariables:search', '搜索');
INSERT INTO `permission` VALUES (192, '数据库变量', 'dbvariables:add', '添加');
INSERT INTO `permission` VALUES (193, '数据库变量', 'dbvariables:delete', '删除');
INSERT INTO `permission` VALUES (194, '数据库变量', 'dbvariables:update', '更新');
INSERT INTO `permission` VALUES (195, '数据库变量', 'dbvariables:detail', '修改');
INSERT INTO `permission` VALUES (196, '数据库变量', 'dbvariables:list', '查询');
INSERT INTO `permission` VALUES (197, '项目迭代', 'project:search', '搜索');
INSERT INTO `permission` VALUES (198, '项目迭代', 'project:add', '添加');
INSERT INTO `permission` VALUES (199, '项目迭代', 'project:delete', '删除');
INSERT INTO `permission` VALUES (200, '项目迭代', 'project:update', '更新');
INSERT INTO `permission` VALUES (201, '项目迭代', 'project:detail', '修改');
INSERT INTO `permission` VALUES (202, '项目迭代', 'project:list', '查询');
INSERT INTO `permission` VALUES (203, '延时子条件', 'delaycondition:search', '搜索');
INSERT INTO `permission` VALUES (204, '延时子条件', 'delaycondition:add', '添加');
INSERT INTO `permission` VALUES (205, '延时子条件', 'delaycondition:delete', '删除');
INSERT INTO `permission` VALUES (206, '延时子条件', 'delaycondition:update', '更新');
INSERT INTO `permission` VALUES (207, '延时子条件', 'delaycondition:detail', '修改');
INSERT INTO `permission` VALUES (208, '延时子条件', 'delaycondition:list', '查询');
INSERT INTO `permission` VALUES (209, '脚本变量', 'scriptvariables:search', '搜索');
INSERT INTO `permission` VALUES (210, '脚本变量', 'scriptvariables:add', '添加');
INSERT INTO `permission` VALUES (211, '脚本变量', 'scriptvariables:delete', '删除');
INSERT INTO `permission` VALUES (212, '脚本变量', 'scriptvariables:update', '更新');
INSERT INTO `permission` VALUES (213, '脚本变量', 'scriptvariables:detail', '修改');
INSERT INTO `permission` VALUES (214, '脚本变量', 'scriptvariables:list', '查询');
COMMIT;

-- ----------------------------
-- Table structure for planbantchexeclog
-- ----------------------------
DROP TABLE IF EXISTS `planbantchexeclog`;
CREATE TABLE `planbantchexeclog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次Id',
  `exec_time` varchar(20) DEFAULT NULL COMMENT '执行时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `memo` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='计划执行日志表';

-- ----------------------------
-- Records of planbantchexeclog
-- ----------------------------
BEGIN;
INSERT INTO `planbantchexeclog` VALUES (1, 56, '2022-05-15 20:15:00', '2022-05-15 20:05:30', '2022-05-15 20:05:30', NULL);
COMMIT;

-- ----------------------------
-- Table structure for process_testcase
-- ----------------------------
DROP TABLE IF EXISTS `process_testcase`;
CREATE TABLE `process_testcase` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '发布单元',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
  `ordernum` bigint(20) unsigned NOT NULL COMMENT '顺序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程用例表';

-- ----------------------------
-- Records of process_testcase
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '项目Id',
  `projectname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目状态',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目描述',
  `creator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目迭代表';

-- ----------------------------
-- Records of project
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '超级管理员', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `role` VALUES (2, '普通用户', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `role` VALUES (3, '测试', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色Id',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_fk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (3, 1);
INSERT INTO `role_permission` VALUES (3, 5);
COMMIT;

-- ----------------------------
-- Table structure for routeperformancereport
-- ----------------------------
DROP TABLE IF EXISTS `routeperformancereport`;
CREATE TABLE `routeperformancereport` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '路由Id',
  `executeplanid` bigint(20) unsigned DEFAULT NULL COMMENT '集合id',
  `tablename` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '表名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='性能结果路由表';

-- ----------------------------
-- Records of routeperformancereport
-- ----------------------------
BEGIN;
INSERT INTO `routeperformancereport` VALUES (3, 7, 'apicases_report_performance_7', '2022-05-07 23:47:38', '2022-05-07 23:47:38');
INSERT INTO `routeperformancereport` VALUES (4, 12, 'apicases_report_performance_12', '2022-05-12 00:48:27', '2022-05-12 00:48:27');
INSERT INTO `routeperformancereport` VALUES (5, 17, 'apicases_report_performance_17', '2022-05-13 01:39:13', '2022-05-13 01:39:13');
COMMIT;

-- ----------------------------
-- Table structure for scriptvariables
-- ----------------------------
DROP TABLE IF EXISTS `scriptvariables`;
CREATE TABLE `scriptvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `scriptvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablesdes` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量值类型',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='脚本变量表';

-- ----------------------------
-- Records of scriptvariables
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for slaver
-- ----------------------------
DROP TABLE IF EXISTS `slaver`;
CREATE TABLE `slaver` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行机Id',
  `slavername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '端口',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，idle，running',
  `stype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '执行机类型，功能机，性能机',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `macaddress` varchar(100) DEFAULT NULL COMMENT 'mac地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';

-- ----------------------------
-- Records of slaver
-- ----------------------------
BEGIN;
INSERT INTO `slaver` VALUES (1, '执行机173.20.1.9', '173.20.1.9', '8081', '空闲', '性能', '执行机173.20.1.9', '2022-05-04 11:21:00', '2022-05-16 08:43:24', '02-42-AD-14-01-09');
INSERT INTO `slaver` VALUES (2, '执行机173.20.1.5', '173.20.1.5', '8081', '空闲', '功能', '执行机173.20.1.5', '2022-05-04 11:21:02', '2022-05-16 08:43:24', '02-42-AD-14-01-05');
COMMIT;

-- ----------------------------
-- Table structure for statics_deployunitandcases
-- ----------------------------
DROP TABLE IF EXISTS `statics_deployunitandcases`;
CREATE TABLE `statics_deployunitandcases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划名',
  `passrate` double(11,2) NOT NULL COMMENT '成功率',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例成功总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例失败总数',
  `runtime` bigint(50) NOT NULL COMMENT '运行时长',
  `statics_date` date NOT NULL COMMENT '统计日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `deployunitid` (`deployunitid`,`statics_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='api发布单元用例统计报告表';

-- ----------------------------
-- Records of statics_deployunitandcases
-- ----------------------------
BEGIN;
INSERT INTO `statics_deployunitandcases` VALUES (1, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-04-19', '2022-04-19 00:00:00', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (2, 3, '测试本机', 85.00, 10, 8, 2, 2602, '2022-04-20', '2022-04-20 00:00:00', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (3, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-04-21', '2022-04-21 00:00:00', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (4, 3, '测试本机', 85.00, 10, 8, 2, 2602, '2022-04-22', '2022-04-22 00:00:00', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (5, 3, '测试本机', 88.00, 10, 8, 2, 2602, '2022-04-23', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (6, 3, '测试本机', 90.00, 10, 8, 2, 2602, '2022-04-24', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (7, 3, '测试本机', 91.00, 10, 8, 2, 2602, '2022-04-25', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (8, 3, '测试本机', 82.00, 10, 8, 2, 2602, '2022-04-26', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (9, 3, '测试本机', 84.00, 10, 8, 2, 2602, '2022-04-27', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (10, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-04-28', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (11, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-04-29', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (12, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-04-30', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (13, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-05-01', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (14, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-05-02', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (15, 3, '测试本机', 80.00, 10, 8, 2, 2602, '2022-05-03', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (16, 3, '测试本机', 95.00, 5, 5, 0, 1302, '2022-05-04', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (17, 3, '测试本机', 80.00, 5, 5, 0, 1302, '2022-05-05', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (18, 1, '测试百度', 100.00, 1, 1, 0, 496, '2022-05-11', '2022-05-13 01:05:23', '2022-05-13 01:05:23');
COMMIT;

-- ----------------------------
-- Table structure for statics_planandcases
-- ----------------------------
DROP TABLE IF EXISTS `statics_planandcases`;
CREATE TABLE `statics_planandcases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `testplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划名',
  `passrate` double(11,2) NOT NULL COMMENT '成功率',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例成功总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例失败总数',
  `runtime` bigint(50) NOT NULL COMMENT '运行时长',
  `statics_date` date NOT NULL COMMENT '统计日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `testplanid` (`testplanid`,`statics_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='api计划用例统计报告表';

-- ----------------------------
-- Records of statics_planandcases
-- ----------------------------
BEGIN;
INSERT INTO `statics_planandcases` VALUES (1, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-21', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (2, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-22', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (3, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-23', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (4, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-24', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (5, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-25', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (6, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-26', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (7, 5, '本机功能测试集合', 80.00, 5, 4, 1, 1302, '2022-04-27', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (8, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-28', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (9, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-29', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (10, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-04-30', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (11, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-05-01', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (12, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-05-02', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (13, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-05-03', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (14, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-05-04', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (15, 5, '本机功能测试集合', 100.00, 5, 5, 0, 1302, '2022-05-05', '2022-05-06 14:05:59', '2022-05-06 14:05:59');
INSERT INTO `statics_planandcases` VALUES (16, 16, '百度首页功能测试', 100.00, 1, 1, 0, 496, '2022-05-11', '2022-05-13 01:05:23', '2022-05-13 01:05:23');
INSERT INTO `statics_planandcases` VALUES (19, 17, '百度首页功能测试', 100.00, 1, 1, 0, 496, '2022-05-10', '2022-05-13 01:46:53', '2022-05-13 01:46:53');
COMMIT;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_account_name` (`name`),
  UNIQUE KEY `ix_account_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='测试表';

-- ----------------------------
-- Records of test
-- ----------------------------
BEGIN;
INSERT INTO `test` VALUES (1, 'admin11111@qq.com', 'admin', '$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `test` VALUES (2, 'editor@qq.com', 'editor', '$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu', '2019-07-02 00:00:00', '2019-07-02 00:00:00');
INSERT INTO `test` VALUES (3, 'test@qq.com', 'test', '$2a$10$.0gBYBHAtdkxUUQNg3kII.fqGOngF4BLe8JavthZFalt2QIXHlrhm', '2019-07-03 00:00:00', '2019-07-03 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for testcondition
-- ----------------------------
DROP TABLE IF EXISTS `testcondition`;
CREATE TABLE `testcondition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件名',
  `objectid` bigint(20) unsigned DEFAULT NULL COMMENT '目标Id，计划，用例的id',
  `objectname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目标名',
  `objecttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目标类型',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件类型，前置，后置，其他',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api名',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'deployunitid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='条件表';

-- ----------------------------
-- Records of testcondition
-- ----------------------------
BEGIN;
INSERT INTO `testcondition` VALUES (1, '测试中心回归测试前置父条件', 2, '测试中心回归测试集合', '测试集合', '前置条件', '测试中心回归测试集合前置操作', '2022-03-28 13:04:44', '2022-03-28 13:04:44', 'admin', NULL, NULL, 0, 0);
INSERT INTO `testcondition` VALUES (3, '测试中心性能测试前置父条件', 3, '测试中心性能测试集合', '测试集合', '前置条件', '测试中心性能测试前置父条件', '2022-03-28 15:09:30', '2022-03-28 15:09:30', 'admin', NULL, NULL, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for testcondition_report
-- ----------------------------
DROP TABLE IF EXISTS `testcondition_report`;
CREATE TABLE `testcondition_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) DEFAULT NULL COMMENT '条件id',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `subconditionid` bigint(20) DEFAULT NULL COMMENT '子条件id，接口，db，nosql条件id',
  `conditionresult` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '接口返回，数据库返回结果等等',
  `conditionstatus` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件完成状态，成功，失败',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `batchname` varchar(64) DEFAULT NULL COMMENT '批次',
  `planname` varchar(64) DEFAULT NULL COMMENT '计划名',
  `testplanid` bigint(20) DEFAULT NULL COMMENT '计划id',
  `subconditiontype` varchar(20) DEFAULT NULL COMMENT '子条件类型，接口，数据库，脚本其他',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `subconditionname` varchar(20) DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='条件报告表';

-- ----------------------------
-- Records of testcondition_report
-- ----------------------------
BEGIN;
INSERT INTO `testcondition_report` VALUES (1, 1, '前置条件', 1, '{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8QagZRCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetDrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCjvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gr0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZxO12ePn_24nz_5P58_vAXAAD__w.QkA36JqO3bRFeOrcs1cyNuX0-K23A1rv_8ACU6-IkOssyW21Pvh_AaLJqvL0UV0zUdH58HsSB2iqGWmgtcV8rg\"\n}', '成功', 169, '2022-03-28 13:05:55', '2022-03-28 13:05:55', NULL, '测试中心回归测试00001', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (2, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4X-7NfAnTLYbBuljzV8CMa1mGGmtmamRmaOD7WNfHEOluaGg2knO69nwHhpXaDye_D6fzSY2zXU43J7WHS5SvNquVN_61clYNk_FnNY430h3NZILpDoKyU2cwWR86tjfK6UtnsG-jigSl9bovIdWgNcKLTEiSCUQC40lS6GYhMP5LYWkJQXEJIXFBAXFZfjhVpQQQJYY0ZYGmQFCbcmr2RRcK6AZ1p9dltMGuSwVldoOvJlURNqkqMb8pwyaslijUq9IrIfE9cAcJZ0Y8VAWWDSKhQRiUhYEGAxj3ZnWzWrRxZltdyCwxYNjOLDcQs1xGTFgvMetW9EGFmGa179Rkw27Vat6py-adymzarc3mpXp9pOIcxeE1JyaOSZwNGInNatcu_nh9e8XVFfe2ubTixorrmo3VjcbVFLDaZHDrePDm9TDj-m7p-fvWuthoHOo9co27rK3UhlupCjc6ybjbcTiq54LrDDvaJ4JbCDvaZ4FbBDPbvli4SLQmSJYAQAnhKCgYJYVa_X9Dyr2H3CIpujgQVzQlFieH-k0FfcFIFDkwayhLGuNR5XDUGG2Ww7p1NvgwMonxlZMAzEkIphHCHIG5U96bOX6Hhld8AeajpkEZNVXKQHQqo1JuVvpil9yJZKlSHGpADoFJFx45hLk3eg9mmxT8JnFNoMoCErMAxQxiMUvChnx1hCmxA79Kwm8LDPxqCR-rxGPgv2pMIr5yEpg5Cck0wphTMPkTNt-v-XjVl2s-W_PNNre-NRqK0xpDi0yhJFMoCQwliaFuxdufFhiH65SV1ikorTAlrTHCa2e3UP_P3HoYLO3EaO2Ea00ktzaVmNSBbxIuCUkKSKAAAGvKBYVxQeXf4xBf_oxj733PR2Z_lAr0Rg9isFpvRD3jCLeNS1ahbBtVBpVto2Jx9atXOyxcvHy1mVHi9avNHvhQ024kHexeBZrIHjCRZcDvNYIJ1qrPQpyDOINm_2LvYt9_f326_v5x_fn9-uXzn6_f4p-s5n47nR-_ePby-dNHt09uP_4DAAD__w.VSj-WqkyqiXc8ki_6yCN-K9vVcKGFgV3zSrQG0LXY3tBGNuF106opN-NGCPuqe9EnKnuiOhNu1KPirShUi21kQ\"\n}', '成功', 199, '2022-03-28 13:15:23', '2022-03-28 13:15:23', NULL, '测试中心回归00002', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (3, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4X-7NfAnTLYbBuljzV8CMa1mGGmtmamRmaOD7WNfHEOluaGg2knO69nwHhpXaDye_D6fzSY2zXU43J7WHS5SvNquVN_61clYNk_FnNY430h3NZILpDoKyU2cwWR86tjfK6UtnsG-jigSl9bovIdWgNcKLTEiSCUQC40lS6GYhMP5LYWkJQXEJIXFBAXFZfjhVpQQQJYY0ZYGmQFCbcmr2RRcK6AZ1p9dltMGuSwVldoOvJlURNqkqMb8pwyaslijUq9IrIfE9cAcJZ0Y8VAWWDSKhQRiUhYEGAxj3ZnWzWrRxZltdyCwxYNjOLDcQs1xGTFgvMetW9EGFmGa179Rkw27Vat6py-adymzarc3mpXp9pOIcxeE1JyaOSZwNGInNatcu_nh9e8XVFfe2ubTixorrmo3VjcbVFLDaZHDrePDm9TDj-m7p-fvWuthoHOo9co27rK3UhlupCjc6ybjbcTiq54LrDDvaJ4JbCDvaZ4FbBDPbvli4SLQmSJYAQAnhKCgYJYVa_X9Dyr2H3CIpujgQVzQlFieH-k0FfcFIFDkwayhLGuNR5XDUGG2Ww7p1NvgwMonxlZMAzEkIphHCHIG5U96bOX6Hhld8AeajpkEZNVXKQHQqo1JuVvpil9yJZKlSHGpADoFJFx45hLk3eg9mmxT8JnFNoMoCErMAxQxiMUvChnx1hCmxA79Kwm8LDPxqCR-rxGPgv2pMIr5yEpg5Cck0wphTMPkTNt-v-XjVl2s-W_PNNre-NRqK0xpDi0yhJFMoCQwliaFuxdufFhiH65SV1ikorTAlrTHCa2e3UP_P3HoYLO3EaO2Ea00ktzaVmNSBbxIuCUkKSKAAAGvKBYVxQeXf4xBf_oxj733PR2Z_lAr0Rg9isFpvRD3jCLeNS1ahbBtVBpVto2Jx9atXOyxcvHy1mVHi9avNHvhQ024kHexeBZrIHjCRZcDvNYIJ1qrPQpyDOINm_2LvYt9_f326_v5x_fn9-uXzn6_f4p-s5n47nR-_ePby-dNHt09uP_4DAAD__w.VSj-WqkyqiXc8ki_6yCN-K9vVcKGFgV3zSrQG0LXY3tBGNuF106opN-NGCPuqe9EnKnuiOhNu1KPirShUi21kQ\"\n}', '成功', 27360, '2022-03-28 13:15:29', '2022-03-28 13:15:29', NULL, '测试中心回归00002', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (4, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1in4S4Rux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5P7168vH1-d_vsycd_AAAA__8.Vq4Uj94ugR9_TQJjUSHnPnns31thnqr4UbRJ7X5yGUEjAwEWTKhhdrXFs9iOGh0J7tVML4DgWWtUHzkNVZPYzg\"\n}', '成功', 163, '2022-03-28 14:48:40', '2022-03-28 14:48:40', NULL, '000003', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (5, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrYASTcQnSldttNEb9J9vdLEKEEENGSkRGSMB90HAMjKvKLrtMhN8r7fue3V6sfX_y-3C6nNQ42-V0d1J7uEb5crNaeeNfKWfVMBl_UeN4J93RTCaY7iAoO3UGk_WhY3ujnL52Bvs2qkhQWq_7ElINWiO8yIQkmUAkMJ4khW4WAuO_FJaWEBSXEBIXFBCX5YdTVUoAUWJIUxZoCgS1KadmX3ShgG5QD3pdRhvsulRQZjf4alIVYZOqEvObMmzCaolCvSq9EhLfA3eQcGbEQ1Vg2SASGoRBWRhoMIBxr1c3q0UbZ7bVhcwSA4btzHIDMctlxIT1ErNuRR9UiGlW-05NNuxWreadumzeqcym3dpsXqrXRyrOURxec2LimMTZgJHYrHbt4o_Xt1dcXXFvm0srbqy4rtlY3WhcTQGrTQa3jgdvXg8zrm-Xnr9vrYuNxqHeI9e4y9pKbbiVqnCjk4y7HYejei64zrCjfSK4hbCjfRa4RTCz7YuFi0RrgmQJAJQQjoKCUVKo1f82pNw7yC2SoosDcUVTYnFyqN9U0FeMRJEDs4aypDEeVQ5HjdFmOaxbZ4MPI5MYXzkJwJyEYBohzBGYB-W9meN3aHjFF2A-ahqUUVOlDESnMirlZqWvdsmdSJYqxaEG5BCYdOGRQ5hHo_dgtknBbxLXBKosIDELUMwgFrMkbMhXR5gSO_CrJPy2wMCvlvCxSjwG_qvGJOIrJ4GZk5BMI4w5BZM_YfP9mo9XfbnmszXfbHPrG6OhOK0xtMgUSjKFksBQkhjqVrz9aYFxuE5ZaZ2C0gpT0hojvHZ2C_X_zK2HwdJOjNZOuNZEcmtTiUkd-CbhkpCkgAQKALCmXFAYF1T-PQ7x5c849t73fGT2R6lAb_RfDFbrjahnHOG2cckqlG2jyqCybVQsrn71aoeFi5evNjNKvH612QMfatqNpIPdq0AT2QMmsgz4vUYwwVr1WYhzEGfQ7F_sXez7z8-Pt1_fbz--3T5_-v3la_yT1Txup8v9-enzZ0_O5xf3H_4CAAD__w.oZqFSeVk2JHR0YJY7L3lGkN_4JBBN-91uBiOBJqqfapcc072dOJaP8mVlkTwAOpbtyoLhxYrtNpRRZSxglFIPQ\"\n}', '成功', 109458, '2022-03-28 14:50:50', '2022-03-28 14:50:50', NULL, '000004', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (6, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4s_7UzXwJ0y2GwbpY81fAjGtZhhprZmpkZmjg-1jXxxDpbmhoNpJzuvZ8B4aV2g8nvw-n80mNs11ONye1h0uUrzarlTf-tXJWDZPxZzWON9IdzWSC6Q6CslNnMFkfOrY3yulLZ7Bvo4oEpfW6LyHVoDXCi0xIkglEAuNJUuhmITD-S2FpCUFxCSFxQQFxWX44VaUEECWGNGWBpkBQm3Jq9kUXCugGdafXZbTBrksFZXaDryZVETapKjG_KcMmrJYo1KvSKyHxPXAHCWdGPFQFlg0ioUEYlIWBBgMY92Z1s1q0cWZbXcgsMWDYziw3ELNcRkxYLzHrVvRBhZhmte_UZMNu1Wreqcvmncps2q3N5qV6faTiHMXhNScmjkmcDRiJzWrXLv54fXvF1RX3trm04saK65qN1Y3G1RSw2mRw63jw5vUw4_pu6fn71rrYaBzqPXKNu6yt1IZbqQo3Osm423E4queC6ww72ieCWwg72meBWwQz275YuEi0JkiWAEAJ4SgoGCWFWv1_Q8q9h9wiKbo4EFc0JRYnh_pNBX3BSBQ5MGsoSxrjUeVw1BhtlsO6dTb4MDKJ8ZWTAMxJCKYRwhyBuVPemzl-h4ZXfAHmo6ZBGTVVykB0KqNSblb6YpfciWSpUhxqQA6BSRceOYS5N3oPZpsU_CZxTaDKAhKzAMUMYjFLwoZ8dYQpsQO_SsJvCwz8agkfq8Rj4L9qTCK-chKYOQnJNMKYUzD5Ezbfr_l41ZdrPlvzzTa3vjUaitMaQ4tMoSRTKAkMJYmhbsXbnxYYh-uUldYpKK0wJa0xwmtnt1D_z9x6GCztxGjthGtNJLc2lZjUgW8SLglJCkigAABrygWFcUHl3-MQX_6MY-99z0dmf5QK9EYPYrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff399uv7-cf35_frl85-v3-KfrOZ-O50fP799-ezpiye3jz7-AwAA__8.qhRtIUk8aVvo_8kmViqPp32PoCKHX02WtdOykt0B0s-_36hTW-3e9NC-dRQ8XQ9ussLGOKjpYRPTrB17ieiCxw\"\n}', '成功', 161, '2022-03-28 15:00:40', '2022-03-28 15:00:40', NULL, '00005', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (7, 3, '前置条件', 3, '{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6w\"\n}', '成功', 114, '2022-03-28 15:10:50', '2022-03-28 15:10:50', NULL, '性能测试0001', '测试中心性能测试集合', 3, '接口', '已完成', '测试中心性能测试前置登陆');
INSERT INTO `testcondition_report` VALUES (8, 1, '前置条件', 1, '未找到环境组件部署，请检查是否存在或已被删除', '失败', 0, '2022-03-28 15:32:30', '2022-03-28 15:32:30', NULL, '测试数据库变量', '测试中心回归测试集合', 2, '数据库', '已完成', '获取用户表用户名');
INSERT INTO `testcondition_report` VALUES (9, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1imIgAhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc63d89ePH_68vbuycd_AAAA__8.Gfu75QgV7jKFAEQCzToRmw0BCAOiumz6YzXXfNx59IYN_Qo77MNqTwT-0MjhJubjIJ78gf7AutGksc8DbFJ-iQ\"\n}', '成功', 206, '2022-03-28 15:32:40', '2022-03-28 15:32:40', NULL, '测试数据库变量', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (10, 1, '前置条件', 1, '未找到环境部署组件mysql组件，请检查是否部署存在或已被删除', '失败', 0, '2022-03-28 16:00:25', '2022-03-28 16:00:25', NULL, '000006', '测试中心回归测试集合', 2, '数据库', '已完成', '获取用户表用户名');
INSERT INTO `testcondition_report` VALUES (11, 1, '前置条件', 1, '{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8QYgBrSajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetDrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCjvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gr0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZxO12evjjfPz8_uX92_vAXAAD__w.c1HyDniStoxGCHIleoaeNbVGKYdZ3uSComHWPuN_wnVMJppaLmtxhQThQK9V4R0_a8UdNxh0WZ1MOLtcXoop-w\"\n}', '成功', 181, '2022-03-28 16:00:34', '2022-03-28 16:00:34', NULL, '000006', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (12, 1, '前置条件', 1, '未找到环境部署组件：mysql组件，请检查是否部署存在或已被删除', '失败', 0, '2022-03-28 16:05:50', '2022-03-28 16:05:50', NULL, '00007', '测试中心回归测试集合', 2, '数据库', '已完成', '获取用户表用户名');
INSERT INTO `testcondition_report` VALUES (13, 1, '前置条件', 1, '{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYr7SA0GZcgXbndRmPUf7Ldza4QIcSQkRKRERJwHzQcA-OqsssuE-H3Svu-Z7cXa9-f_D6cLic1znY53Z3UHq5RvtqsVt7418pZNUzGX9Q43kl3NJMJpjsIyk6dwWR96NjeKKevncG-jSoSlNbrvoRUg9YILzIhSSYQCYwnSaGbhcD4L4WlJQTFJYTEBQXEZfnhVJUSQJQY0pQFmgJBbcqp2RddKKAb1KNel9EGuy4VlNkNvppURdikqsT8pgybsFqiUK9Kr4TE98AdJJwZ8VAVWDaIhAZhUBYGGgxg3JvVzWrRxpltdSGzxIBhO7PcQMxyGTFhvcSsW9EHFWKa1b5Tkw27Vat5py6bdyqzabc2m5fq9ZGKcxSH15yYOCZxNmAkNqtdu_jj9e0VV1fc2-bSihsrrms2VjcaV1PAapPBrePBm9fDjOu7pefvW-tio3Go98g17rK2UhtupSrc6CTjbsfhqJ4LrjPsaJ8IbiHsaJ8FbhHMbPti4SLRmiBZAgAlhKOgYJQUavW_DSn3DLlFUnRxIK5oSixODvWbCvqKkShyYNZQljTGo8rhqDHaLId162zwYWQS4ysnAZiTEEwjhDkC86i8N3P8Dg2v-ALMR02DMmqqlIHoVEal3Kz01S65E8lSpTjUgBwCky48cgjzZPQezDYp-E3imkCVBSRmAYoZxGKWhA356ghTYgd-lYTfFhj41RI-VonHwH_VmER85SQwcxKSaYQxp2DyJ2y-X_Pxqi_XfLbmm21ufWs0FKc1hhaZQkmmUBIYShJD3Yq3Py0wDtcpK61TUFphSlpjhNfObqH-n7n1MFjaidHaCdeaSG5tKjGpA98kXBKSFJBAAQDWlAsK44LKv8chvvwZx977no_M_igV6I3-i8FqvRH1jCPcNi5ZhbJtVBlUto2KxdWvXu2wcPHy1WZGidevNnvgQ027kXSwexVoInvARJYBv9cIJlirPgtxDuIMmv2LvYt9__n58fbr--3Ht9vnT7-_fI1_spqn7XS5f_Hw8vxwf38-f_gLAAD__w.pXlxNBB208eNSd1gIyWe8y1Eh2u_Wt6HubPwXyMC1S8rhaLEonoBC6uzf4zfm9aEudKgh-1sVq1ma5BaPRhtjw\"\n}', '成功', 181, '2022-03-28 16:05:55', '2022-03-28 16:05:55', NULL, '00007', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (14, 1, '前置条件', 1, '成功获取 数据库变量名：usernamefromdb 值:admin', '成功', 62, '2022-03-28 16:09:15', '2022-03-28 16:09:15', NULL, '00008', '测试中心回归测试集合', 2, '数据库', '已完成', '获取用户表用户名');
INSERT INTO `testcondition_report` VALUES (15, 1, '前置条件', 1, '{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrdhCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetTrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCrvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZpO13unz-8OD_cPzufP_wFAAD__w.SoqYO24kUPJixCZpzcc82QgINB87iiYupneu1k153_3lhEk1FopYOE9gHICQlWjDByPRM77-t3HSf8MwX0NGRw\"\n}', '成功', 89, '2022-03-28 16:09:15', '2022-03-28 16:09:15', NULL, '00008', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (16, 2, '前置条件', 2, '{\n \"code\":500,\n \"message\":\"/account/token => Required request body is missing: public com.zoctan.api.core.response.Result com.zoctan.api.controller.AccountController.login(com.zoctan.api.entity.Account)\"\n}', '成功', 31, '2022-03-28 16:09:36', '2022-03-28 16:09:36', 'admin', '00008', '正常新增环境', 2, '接口', '已完成', '新增测试环境前置登陆');
INSERT INTO `testcondition_report` VALUES (17, 1, '前置条件', 1, '成功获取 数据库变量名：usernamefromdb 值:admin', '成功', 20, '2022-03-28 16:10:35', '2022-03-28 16:10:35', NULL, '00009', '测试中心回归测试集合', 2, '数据库', '已完成', '获取用户表用户名');
INSERT INTO `testcondition_report` VALUES (18, 1, '前置条件', 1, '{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrZhGajEuQrtxuozHqH8t2N4sQIcSQkRKRERJwHzQcg8ZVZZddJsLvlfZ9z24v1r4_hW04XU5qnO1yujupLV4P-dJZrYIJr5S3aphMuKhxvJPuaCYTTXcQlZ06g8mG2LGDUV5fO4PNjeogKK3XbYmpBq0RXmRCkkwgEhhPkkKdhcDjXwpLSwg6lhByLCjgWJYfTlUpAUSJIU1ZoCkQlFNezaHoQgHdoB71uow22nWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIOHMiIeqwLJBJDQIg7Iw0GAA41-vflaLNt641cfMEgOG7cxyAzHLZcSE9RKzbsUQVTzSrA6dmmzYrVrNO3XZvFOZTbu12bxUr49UnKM4vObExDGJswEjsVnt2sUfr2-vuLri3jaXVtxYcV2zsfrR-JoCVpsMbh0P3rzuZlzfLj1_c62Ljcah3iPXuMvaSm24lapwo5OMux2HvXouuM6wvX0iuIWwvX0WuEUw47bFwkWiNUGyBABKCEdBwSgp1Op_G1L-HeQWSdHFgbiiKbE4OTQ4FfUVI1HkwKyhLGmMR5XDUWO0WXbr19ngw8gkxldOAjAnIZhGCHME5lGFYObjOzS84gswHzUNyqipUgaiUxmVcrPSV7vkTiRLleJQA3IITLrwyCHMk9FbNG5S8JvENYEqC0jMAhQziMUsCRvy1RGmxA78Kgm_LTDwqyV8rHIcA_9VYxLxlZPAzElIphHGnILJn7D5fs3Hq75c89mab-b8-sZoKE5rDC0yhZJMoSQwlCSG-hVvf1pgHK5TVlqnoLTClLTGiKC9dbH-n7n1MFjaidHaCdeaSG5tKjGpHd8kXBKSFJBAAQDWlAsK46LKv8fxePkzjr33PR-Z_VEq0Bv9F4PVeiPqeYxw27hkFcq2UWVQ2TYqFle_erXDwsXLV5sZJV6_2uyBdzVtRtLB7lWgiewBE1kG_F4jmGCt-izEOYgzaPYv9i72_efnx9uv77cf326fP_3-8vX4k9U8udPl_vn5xcP5_vzs4cNfAAAA__8.DsWj1Fgs8rnX4nt5kVF4f0RVMd0D_EaSQ8E-I9D9N10VItYvGOhG1vxt18-R2U8PN-WW2P_cJDgTTtFgB-zQBQ\"\n}', '成功', 113, '2022-03-28 16:10:35', '2022-03-28 16:10:35', NULL, '00009', '测试中心回归测试集合', 2, '接口', '已完成', '前置登陆');
INSERT INTO `testcondition_report` VALUES (19, 2, '前置条件', 2, '{\n \"code\":500,\n \"message\":\"/account/token => Required request body is missing: public com.zoctan.api.core.response.Result com.zoctan.api.controller.AccountController.login(com.zoctan.api.entity.Account)\"\n}', '成功', 30, '2022-03-28 16:11:00', '2022-03-28 16:11:00', 'admin', '00009', '正常新增环境', 2, '接口', '已完成', '新增测试环境前置登陆');
COMMIT;

-- ----------------------------
-- Table structure for tester
-- ----------------------------
DROP TABLE IF EXISTS `tester`;
CREATE TABLE `tester` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '测试人员姓名',
  `testertitle` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '测试人员职务',
  `testerorg` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属组织',
  `testermemo` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试人员表';

-- ----------------------------
-- Records of tester
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for testvariables
-- ----------------------------
DROP TABLE IF EXISTS `testvariables`;
CREATE TABLE `testvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `testvariablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量类型，用例变量，全局变量',
  `variablesexpress` varchar(210) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量表达',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `variablesdes` varchar(100) DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(20) DEFAULT 'String' COMMENT 'String，Number，Array,Bool,其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='变量表';

-- ----------------------------
-- Records of testvariables
-- ----------------------------
BEGIN;
INSERT INTO `testvariables` VALUES (1, 'token', '', '$.data', '测试中心登陆返回的登陆认证给其他接口使用', '2022-03-28 13:03:26', '2022-03-28 13:03:26', 'admin', '测试中心登陆返回的登陆认证', 'String');
COMMIT;

-- ----------------------------
-- Table structure for testvariables_value
-- ----------------------------
DROP TABLE IF EXISTS `testvariables_value`;
CREATE TABLE `testvariables_value` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `planid` bigint(20) unsigned NOT NULL COMMENT '计划Id',
  `planname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划名',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) unsigned NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablesvalue` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量值',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `batchname` varchar(64) DEFAULT NULL COMMENT '批次',
  `variablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='变量值表';

-- ----------------------------
-- Records of testvariables_value
-- ----------------------------
BEGIN;
INSERT INTO `testvariables_value` VALUES (1, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', '', 'test', '2022-03-28 13:05:55', '2022-03-28 13:05:55', '测试中心回归测试00001', '接口');
INSERT INTO `testvariables_value` VALUES (2, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', '', 'test', '2022-03-28 13:15:57', '2022-03-28 13:15:57', '测试中心回归00002', '接口');
INSERT INTO `testvariables_value` VALUES (3, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', '', 'test', '2022-03-28 14:47:14', '2022-03-28 14:47:14', '测试中心回归00002', '接口');
INSERT INTO `testvariables_value` VALUES (4, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', '', 'test', '2022-03-28 14:48:40', '2022-03-28 14:48:40', '000003', '接口');
INSERT INTO `testvariables_value` VALUES (5, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', '', 'test', '2022-03-28 14:56:35', '2022-03-28 14:56:35', '000004', '接口');
INSERT INTO `testvariables_value` VALUES (6, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4s_7UzXwJ0y2GwbpY81fAjGtZhhprZmpkZmjg-1jXxxDpbmhoNpJzuvZ8B4aV2g8nvw-n80mNs11ONye1h0uUrzarlTf-tXJWDZPxZzWON9IdzWSC6Q6CslNnMFkfOrY3yulLZ7Bvo4oEpfW6LyHVoDXCi0xIkglEAuNJUuhmITD-S2FpCUFxCSFxQQFxWX44VaUEECWGNGWBpkBQm3Jq9kUXCugGdafXZbTBrksFZXaDryZVETapKjG_KcMmrJYo1KvSKyHxPXAHCWdGPFQFlg0ioUEYlIWBBgMY92Z1s1q0cWZbXcgsMWDYziw3ELNcRkxYLzHrVvRBhZhmte_UZMNu1Wreqcvmncps2q3N5qV6faTiHMXhNScmjkmcDRiJzWrXLv54fXvF1RX3trm04saK65qN1Y3G1RSw2mRw63jw5vUw4_pu6fn71rrYaBzqPXKNu6yt1IZbqQo3Osm423E4queC6ww72ieCWwg72meBWwQz275YuEi0JkiWAEAJ4SgoGCWFWv1_Q8q9h9wiKbo4EFc0JRYnh_pNBX3BSBQ5MGsoSxrjUeVw1BhtlsO6dTb4MDKJ8ZWTAMxJCKYRwhyBuVPemzl-h4ZXfAHmo6ZBGTVVykB0KqNSblb6YpfciWSpUhxqQA6BSRceOYS5N3oPZpsU_CZxTaDKAhKzAMUMYjFLwoZ8dYQpsQO_SsJvCwz8agkfq8Rj4L9qTCK-chKYOQnJNMKYUzD5Ezbfr_l41ZdrPlvzzTa3vjUaitMaQ4tMoSRTKAkMJYmhbsXbnxYYh-uUldYpKK0wJa0xwmtnt1D_z9x6GCztxGjthGtNJLc2lZjUgW8SLglJCkigAABrygWFcUHl3-MQX_6MY-99z0dmf5QK9EYPYrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff399uv7-cf35_frl85-v3-KfrOZ-O50fP799-ezpiye3jz7-AwAA__8.qhRtIUk8aVvo_8kmViqPp32PoCKHX02WtdOykt0B0s-_36hTW-3e9NC-dRQ8XQ9ussLGOKjpYRPTrB17ieiCxw', 'test', '2022-03-28 15:00:40', '2022-03-28 15:00:40', '00005', '接口');
INSERT INTO `testvariables_value` VALUES (7, 3, '测试中心性能测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6w', 'test', '2022-03-28 15:10:50', '2022-03-28 15:10:50', '性能测试0001', '接口');
INSERT INTO `testvariables_value` VALUES (8, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1imIgAhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc63d89ePH_68vbuycd_AAAA__8.Gfu75QgV7jKFAEQCzToRmw0BCAOiumz6YzXXfNx59IYN_Qo77MNqTwT-0MjhJubjIJ78gf7AutGksc8DbFJ-iQ', 'test', '2022-03-28 15:32:40', '2022-03-28 15:32:40', '测试数据库变量', '接口');
INSERT INTO `testvariables_value` VALUES (9, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8QYgBrSajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetDrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCjvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gr0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZxO12evjjfPz8_uX92_vAXAAD__w.c1HyDniStoxGCHIleoaeNbVGKYdZ3uSComHWPuN_wnVMJppaLmtxhQThQK9V4R0_a8UdNxh0WZ1MOLtcXoop-w', 'test', '2022-03-28 16:00:34', '2022-03-28 16:00:34', '000006', '接口');
INSERT INTO `testvariables_value` VALUES (10, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYr7SA0GZcgXbndRmPUf7Ldza4QIcSQkRKRERJwHzQcA-OqsssuE-H3Svu-Z7cXa9-f_D6cLic1znY53Z3UHq5RvtqsVt7418pZNUzGX9Q43kl3NJMJpjsIyk6dwWR96NjeKKevncG-jSoSlNbrvoRUg9YILzIhSSYQCYwnSaGbhcD4L4WlJQTFJYTEBQXEZfnhVJUSQJQY0pQFmgJBbcqp2RddKKAb1KNel9EGuy4VlNkNvppURdikqsT8pgybsFqiUK9Kr4TE98AdJJwZ8VAVWDaIhAZhUBYGGgxg3JvVzWrRxpltdSGzxIBhO7PcQMxyGTFhvcSsW9EHFWKa1b5Tkw27Vat5py6bdyqzabc2m5fq9ZGKcxSH15yYOCZxNmAkNqtdu_jj9e0VV1fc2-bSihsrrms2VjcaV1PAapPBrePBm9fDjOu7pefvW-tio3Go98g17rK2UhtupSrc6CTjbsfhqJ4LrjPsaJ8IbiHsaJ8FbhHMbPti4SLRmiBZAgAlhKOgYJQUavW_DSn3DLlFUnRxIK5oSixODvWbCvqKkShyYNZQljTGo8rhqDHaLId162zwYWQS4ysnAZiTEEwjhDkC86i8N3P8Dg2v-ALMR02DMmqqlIHoVEal3Kz01S65E8lSpTjUgBwCky48cgjzZPQezDYp-E3imkCVBSRmAYoZxGKWhA356ghTYgd-lYTfFhj41RI-VonHwH_VmER85SQwcxKSaYQxp2DyJ2y-X_Pxqi_XfLbmm21ufWs0FKc1hhaZQkmmUBIYShJD3Yq3Py0wDtcpK61TUFphSlpjhNfObqH-n7n1MFjaidHaCdeaSG5tKjGpA98kXBKSFJBAAQDWlAsK44LKv8chvvwZx977no_M_igV6I3-i8FqvRH1jCPcNi5ZhbJtVBlUto2KxdWvXu2wcPHy1WZGidevNnvgQ027kXSwexVoInvARJYBv9cIJlirPgtxDuIMmv2LvYt9__n58fbr--3Ht9vnT7-_fI1_spqn7XS5f_Hw8vxwf38-f_gLAAD__w.pXlxNBB208eNSd1gIyWe8y1Eh2u_Wt6HubPwXyMC1S8rhaLEonoBC6uzf4zfm9aEudKgh-1sVq1ma5BaPRhtjw', 'test', '2022-03-28 16:05:55', '2022-03-28 16:05:55', '00007', '接口');
INSERT INTO `testvariables_value` VALUES (11, 2, '测试中心回归测试集合', 1, '测试中心回归测试前置父条件', 1, 'usernamefromdb', 'admin', 'test', '2022-03-28 16:09:15', '2022-03-28 16:09:15', '00008', '数据库');
INSERT INTO `testvariables_value` VALUES (12, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrdhCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetTrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCrvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZpO13unz-8OD_cPzufP_wFAAD__w.SoqYO24kUPJixCZpzcc82QgINB87iiYupneu1k153_3lhEk1FopYOE9gHICQlWjDByPRM77-t3HSf8MwX0NGRw', 'test', '2022-03-28 16:09:15', '2022-03-28 16:09:15', '00008', '接口');
INSERT INTO `testvariables_value` VALUES (13, 2, '测试中心回归测试集合', 1, '测试中心回归测试前置父条件', 1, 'usernamefromdb', 'admin', 'test', '2022-03-28 16:10:35', '2022-03-28 16:10:35', '00009', '数据库');
INSERT INTO `testvariables_value` VALUES (14, 2, '测试中心回归测试集合', 2, '正常登陆', 1, 'token', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrZhGajEuQrtxuozHqH8t2N4sQIcSQkRKRERJwHzQcg8ZVZZddJsLvlfZ9z24v1r4_hW04XU5qnO1yujupLV4P-dJZrYIJr5S3aphMuKhxvJPuaCYTTXcQlZ06g8mG2LGDUV5fO4PNjeogKK3XbYmpBq0RXmRCkkwgEhhPkkKdhcDjXwpLSwg6lhByLCjgWJYfTlUpAUSJIU1ZoCkQlFNezaHoQgHdoB71uow22nWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIOHMiIeqwLJBJDQIg7Iw0GAA41-vflaLNt641cfMEgOG7cxyAzHLZcSE9RKzbsUQVTzSrA6dmmzYrVrNO3XZvFOZTbu12bxUr49UnKM4vObExDGJswEjsVnt2sUfr2-vuLri3jaXVtxYcV2zsfrR-JoCVpsMbh0P3rzuZlzfLj1_c62Ljcah3iPXuMvaSm24lapwo5OMux2HvXouuM6wvX0iuIWwvX0WuEUw47bFwkWiNUGyBABKCEdBwSgp1Op_G1L-HeQWSdHFgbiiKbE4OTQ4FfUVI1HkwKyhLGmMR5XDUWO0WXbr19ngw8gkxldOAjAnIZhGCHME5lGFYObjOzS84gswHzUNyqipUgaiUxmVcrPSV7vkTiRLleJQA3IITLrwyCHMk9FbNG5S8JvENYEqC0jMAhQziMUsCRvy1RGmxA78Kgm_LTDwqyV8rHIcA_9VYxLxlZPAzElIphHGnILJn7D5fs3Hq75c89mab-b8-sZoKE5rDC0yhZJMoSQwlCSG-hVvf1pgHK5TVlqnoLTClLTGiKC9dbH-n7n1MFjaidHaCdeaSG5tKjGpHd8kXBKSFJBAAQDWlAsK46LKv8fxePkzjr33PR-Z_VEq0Bv9F4PVeiPqeYxw27hkFcq2UWVQ2TYqFle_erXDwsXLV5sZJV6_2uyBdzVtRtLB7lWgiewBE1kG_F4jmGCt-izEOYgzaPYv9i72_efnx9uv77cf326fP_3-8vX4k9U8udPl_vn5xcP5_vzs4cNfAAAA__8.DsWj1Fgs8rnX4nt5kVF4f0RVMd0D_EaSQ8E-I9D9N10VItYvGOhG1vxt18-R2U8PN-WW2P_cJDgTTtFgB-zQBQ', 'test', '2022-03-28 16:10:35', '2022-03-28 16:10:35', '00009', '接口');
COMMIT;

-- ----------------------------
-- Table structure for variables
-- ----------------------------
DROP TABLE IF EXISTS `variables`;
CREATE TABLE `variables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量类型',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '变量描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `variablecondition` varchar(64) DEFAULT NULL COMMENT '变量条件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='变量表';

-- ----------------------------
-- Records of variables
-- ----------------------------
BEGIN;
INSERT INTO `variables` VALUES (1, 'enviromentname', '随机字符串', '随机环境名', '2022-03-28 15:04:32', '2022-03-28 15:05:40', '5');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
