-- ----------------------------
-- Table structure for backstage_dept
-- ----------------------------
DROP TABLE IF EXISTS `backstage_dept`;
CREATE TABLE `backstage_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `dept_code` varchar(50) NOT NULL COMMENT '部门代码',
  `dept_level` tinyint(3) NOT NULL COMMENT '部门级别',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `dept_address` varchar(100) DEFAULT NULL COMMENT '部门地址',
  `up_dept_id` int(11) DEFAULT NULL COMMENT '上级部门编号',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) DEFAULT NULL COMMENT '状态：VALID有效/DISABLE失效',
  `inst_user` int(11) DEFAULT NULL COMMENT '初始写入人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始时间',
  `lupd_user` int(11) DEFAULT NULL COMMENT '最后更改人',
  `lupd_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更改时间',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `backstage_dept_dept_code` (`dept_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统-部门表';

-- ----------------------------
-- Table structure for backstage_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `backstage_dictionary`;
CREATE TABLE `backstage_dictionary` (
  `dictionary_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `dictionary_path` varchar(150) NOT NULL COMMENT '字典路径',
  `dictionary_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dictionary_level` tinyint(3) NOT NULL COMMENT '字典级别',
  `parent_dictionary_id` int(11) DEFAULT NULL COMMENT '上级编号',
  `data_type` varchar(50) NOT NULL DEFAULT 'STRING' COMMENT '数据类型STRING/INT/BOOLEAN/BIGDECIMAL..',
  `data_value` varchar(200) NOT NULL COMMENT '字典值',
  `data_value_reserve` varchar(200) DEFAULT NULL COMMENT '字典值(备用)',
  `dictionary_order` smallint(6) DEFAULT NULL COMMENT '顺序',
  `description` varchar(500) DEFAULT NULL COMMENT '字典说明',
  `language` varchar(10) NOT NULL DEFAULT 'zh-CN' COMMENT '语言(中英文，国际化)zh-CN/ru-RU/ja-JP/en-US/...',
  `role_owner` varchar(50) DEFAULT '1' COMMENT '归属权限角色',
  `status` varchar(10) NOT NULL DEFAULT 'VALID' COMMENT '状态：VALID有效/DISABLE失效',
  `inst_user` int(11) NOT NULL DEFAULT '1' COMMENT '创建人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始时间',
  `lupd_user` int(11) DEFAULT NULL COMMENT '最后更改人',
  `lupd_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更改时间',
  PRIMARY KEY (`dictionary_id`),
  UNIQUE KEY `backstage_dictionary_dictionary_path` (`dictionary_path`) USING BTREE,
  KEY `backstage_dictionary_parent_dictionary_id` (`parent_dictionary_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统--数据字典表';

-- ----------------------------
-- Table structure for backstage_function
-- ----------------------------
DROP TABLE IF EXISTS `backstage_function`;
CREATE TABLE `backstage_function` (
  `function_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `function_code` varchar(20) NOT NULL COMMENT '功能代码',
  `function_name` varchar(50) NOT NULL COMMENT '功能名称',
  `parent_function_id` int(11) DEFAULT NULL COMMENT '上级编号',
  `function_level` smallint(6) NOT NULL COMMENT '功能级别',
  `function_url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `function_order` smallint(6) DEFAULT NULL COMMENT '功能排序',
  `is_menu` char(1) NOT NULL COMMENT '是否是菜单Y是/N否',
  `access_type` char(1) DEFAULT NULL COMMENT '是否要授权，Y需要，N不需要',
  `icon_class` varchar(50) DEFAULT NULL COMMENT '图标样式',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) NOT NULL DEFAULT 'VALID' COMMENT '状态：VALID有效/DISABLE失效',
  `inst_user` int(11) DEFAULT NULL COMMENT '初始写入人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始时间',
  `lupd_user` int(11) DEFAULT NULL COMMENT '最后更改人',
  `lupd_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更改时间',
  PRIMARY KEY (`function_id`),
  UNIQUE KEY `backstage_function_function_code` (`function_code`) USING BTREE,
  KEY `backstage_function_parent_function_id` (`parent_function_id`) USING BTREE,
  KEY `backstage_function_function_url` (`function_url`) USING BTREE,
  KEY `backstage_function_status` (`status`) USING BTREE,
  KEY `backstage_function_is_menu` (`is_menu`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统-菜单功能表';

-- ----------------------------
-- Table structure for backstage_role
-- ----------------------------
DROP TABLE IF EXISTS `backstage_role`;
CREATE TABLE `backstage_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `role_code` varchar(20) NOT NULL COMMENT '角色代码',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `memo` varchar(500) DEFAULT NULL COMMENT '角色说明',
  `status` varchar(10) NOT NULL DEFAULT 'VALID' COMMENT '状态：VALID有效/DISABLE失效',
  `inst_user` int(11) DEFAULT NULL COMMENT '初始写入人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始时间',
  `lupd_user` int(11) DEFAULT NULL COMMENT '最后更改人',
  `lupd_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更改时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `backstage_role_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统-角色表';

-- ----------------------------
-- Table structure for backstage_role_function
-- ----------------------------
DROP TABLE IF EXISTS `backstage_role_function`;
CREATE TABLE `backstage_role_function` (
  `rf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `function_id` int(11) NOT NULL COMMENT '功能编号',
  `inst_user` int(11) DEFAULT NULL COMMENT '初始写入人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始时间',
  PRIMARY KEY (`rf_id`),
  UNIQUE KEY `backstage_role_function_role_function` (`role_id`,`function_id`) USING BTREE,
  KEY `backstage_role_function_role_id` (`role_id`) USING BTREE,
  KEY `backstage_role_function_function_id` (`function_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统-角色与菜单对应表';

-- ----------------------------
-- Table structure for backstage_user
-- ----------------------------
DROP TABLE IF EXISTS `backstage_user`;
CREATE TABLE `backstage_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `user_code` varchar(20) NOT NULL COMMENT '登录员工号',
  `user_name` varchar(50) NOT NULL COMMENT '登录用户名',
  `password` varchar(50) NOT NULL COMMENT '登录密码',
  `real_name` varchar(20) NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `dept_id` int(11) NOT NULL COMMENT '部门编号',
  `sex` varchar(5) NOT NULL COMMENT '性别：MEN男/WOMEN女',
  `work_phone` varchar(20) DEFAULT NULL COMMENT '工作电话号码',
  `self_phone` varchar(20) DEFAULT NULL COMMENT '个人手机号码',
  `work_email` varchar(50) DEFAULT NULL COMMENT '工作邮箱',
  `self_email` varchar(50) DEFAULT NULL COMMENT '个人邮箱',
  `status` varchar(10) NOT NULL COMMENT '状态：生效VALID/冻结FREEZE/离职LEAVE',
  `online_flag` varchar(10) NOT NULL COMMENT '在线标记：已登录LOGIN/已登出LOGOUT',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `inst_user` int(11) NOT NULL DEFAULT '0' COMMENT '初始写入人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始写入时间',
  `lupd_user` int(11) DEFAULT NULL COMMENT '最后更改人',
  `lupd_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `backstage_user_user_name` (`user_name`) USING BTREE,
  UNIQUE KEY `backstage_user_user_code` (`user_code`) USING BTREE,
  KEY `backstage_user_dept_id` (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统-用户表';

-- ----------------------------
-- Table structure for backstage_user_role
-- ----------------------------
DROP TABLE IF EXISTS `backstage_user_role`;
CREATE TABLE `backstage_user_role` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '索引编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `inst_user` int(11) DEFAULT NULL COMMENT '初始写入人',
  `inst_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '初始时间',
  PRIMARY KEY (`ur_id`),
  UNIQUE KEY `backstage_user_role_user_role` (`user_id`,`role_id`) USING BTREE,
  KEY `backstage_user_role_user_id` (`user_id`) USING BTREE,
  KEY `backstage_user_role_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='系统-用户与角色对应表';
