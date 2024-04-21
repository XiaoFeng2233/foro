/*
 Navicat Premium Data Transfer

 Source Server         : 论坛
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : 192.168.31.231:3306
 Source Schema         : iforum

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 02/04/2024 16:41:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '评论者ID',
  `topic_id` bigint NOT NULL COMMENT '帖子ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细内容',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '回复的父评论ID',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `ip_location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip的地理位置',
  `status` int NULL DEFAULT NULL COMMENT '状态 0不可见 1可见',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_comment_entity_id`(`topic_id` ASC) USING BTREE,
  INDEX `idx_comment_status`(`status` ASC) USING BTREE,
  INDEX `idx_comment_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (12, 1, 7, '测试第一条评论', NULL, '127.0.0.1', '本机地址', 1, '2024-03-12 18:19:50', 1, 'admin', NULL, NULL, NULL, 1, 1);
INSERT INTO `t_comment` VALUES (13, 1, 8, '第二条评论', NULL, '127.0.0.1', '本机地址', 1, '2024-03-12 18:39:52', 1, 'admin', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_comment` VALUES (14, 1, 8, '子评论', 13, '127.0.0.1', '本机地址', 1, '2024-03-12 18:40:02', 1, 'admin', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_comment` VALUES (15, 1, 8, 'aaaaa', 13, '127.0.0.1', '本机地址', 1, '2024-03-12 18:59:00', 1, 'admin', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_comment` VALUES (16, 28, 10, '电饭锅森岛帆高森岛帆高', NULL, '127.0.0.1', '本机地址', 1, '2024-03-28 00:17:11', 28, 'guest', NULL, NULL, NULL, 1, 1);
INSERT INTO `t_comment` VALUES (17, 28, 10, '234人 ', 16, '127.0.0.1', '本机地址', 1, '2024-03-28 00:17:34', 28, 'guest', NULL, NULL, NULL, 1, 1);

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段值',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_key`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES (1, 'email.host', 'smtp.163.com', '2024-01-27 19:33:10', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (2, 'email.port', '465', '2024-01-27 19:33:13', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (3, 'email.username', '邮箱账户', '2024-01-27 19:34:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (4, 'email.password', '邮箱密码', '2024-01-27 19:34:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (5, 'email.address', '邮箱账号', '2024-01-27 19:34:55', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (6, 'email.useSSL', '1', '2024-01-27 20:22:40', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (7, 'email.registerTemplate', '<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\" /><meta name=\"viewport\"content=\"width=device-width, initial-scale=1.0\" /><title>邮箱认证</title><style>body{background-color:#F9F9F9;margin:0;padding:0}.block{display:flex;justify-content:center;align-items:center;height:100%;flex-direction:column}.card{background:white;padding:50px;max-width:600px;margin:0 50px;box-shadow:0px 1px 5px rgba(0,0,0,0.1);border-radius:4px;overflow:hidden;text-align:center}.title{color:#4F545C;font-size:20px;font-weight:500;margin-top:0}.confirm{font-size:20px;padding:15px;border-radius:10px;font-weight:700;margin:40px auto;color:white;border:none;cursor:pointer;background-color:rgb(32,107,196);transition-duration:0.3s;display:inline-block;text-decoration:none}.confirm:hover{opacity:0.8}.content{line-height:170%;color:#737F8D}.tip{color:#737F8D;font-size:14px}</style></head><body><div class=\"block\"><div><img src=\"${logoUrl}\"height=\"100\"alt=\"\" /></div><div class=\"card\"><h1 class=\"title\">Hey ${userName}</h1><div class=\"content\">感谢您注册${siteName}，为了防止我们的服务被滥用，我们需要对您的电子邮件账号进行验证，您只需点击下方链接即可验证。验证${activeTime}分钟，请尽快完成注册。</div><a style=\"text-decoration:none\" class=\"confirm\" href=\"${activeUrl}\" target=\"_blank\">点击激活您的账号</a><div class=\"tip\">此邮件由系统自动发送，请勿直接回复，如果您没有注册过本站账号，请无视此邮件。</div></div></div></body></html>', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (8, 'email.forgetTemplate', '<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\" /><meta name=\"viewport\"content=\"width=device-width, initial-scale=1.0\" /><title>重置密码</title><style>body{background-color:#F9F9F9;margin:0;padding:0}.block{display:flex;justify-content:center;align-items:center;height:100%;flex-direction:column}.card{background:white;padding:50px;max-width:600px;margin:0 50px;box-shadow:0px 1px 5px rgba(0,0,0,0.1);border-radius:4px;overflow:hidden;text-align:center}.title{color:#4F545C;font-size:20px;font-weight:500;margin-top:0}.confirm{font-size:20px;padding:15px;border-radius:10px;font-weight:700;margin:40px auto;color:white;border:none;cursor:pointer;background-color:rgb(32,107,196);transition-duration:0.3s;display:inline-block;text-decoration:none}.confirm:hover{opacity:0.8}.content{line-height:170%;color:#737F8D}.tip{color:#737F8D;font-size:14px}</style></head><body><div class=\"block\"><div><img src=\"${logoUrl}\"height=\"100\"alt=\"\" /></div><div class=\"card\"><h1 class=\"title\">Hey ${userName}</h1><div class=\"content\">点击下面的链接可以重置您的密码，如果您没有请求重置密码，请忽略此邮件。</div><a style=\"text-decoration:none\" class=\"confirm\" href=\"${resetUrl}\" target=\"_blank\">点击重置密码</a><div class=\"tip\">此邮件由系统自动发送，请勿直接回复，如果您没有注册过本站账号，请无视此邮件。</div></div></div></body></html>', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (9, 'user.defaultAvatar', '/logo.png', '2024-01-27 22:33:22', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (10, 'user.defaultBackgroundImage', '/logo.png', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (11, 'site.name', 'FORO论坛', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (12, 'site.logo', 'http://localhost/image/LOGO.png', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (13, 'site.favicon', 'http://localhost/image/ico.png', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (82, 'site.url', 'http://localhost', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (83, 'site.open_register', '1', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (85, 'site.open', '1', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (86, 'site.footer', '<strong>页脚代码</strong>', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (87, 'site.max_file_upload_size', '1001', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (88, 'minio.endpoint', 'http://192.168.31.231:9001', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (89, 'minio.accessKey', '你的AccessKey', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_config` VALUES (90, 'minio.secretKey', '你的SecretKey', '2024-01-27 22:33:23', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for t_forbidden_log
-- ----------------------------
DROP TABLE IF EXISTS `t_forbidden_log`;
CREATE TABLE `t_forbidden_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '被封禁的用户ID',
  `start_time` datetime NOT NULL COMMENT '封禁开始时间',
  `finish_time` datetime NOT NULL COMMENT '封禁结束时间',
  `reason` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封禁理由',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_forbidden_log_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '封禁记录表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `type` tinyint NOT NULL COMMENT '事件类型 1 登陆  2退出 3签到',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件发生IP地址',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件发生ip地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_log_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图标地址',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息通知表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名',
  `code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, 'user:add', 'e803f5b7c4f411eeab390242ac120004', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (2, 'user:mute', '5b19b3dcda99985b2951e6466cadcace', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (3, 'user:forbidden', '1d150faba94f0a4005583aa6c1655916', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (4, 'user:delete', '90ce47b0bf1aa09a32dfaf0e0020dd60', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (5, 'user:edit', '8c206c86561747bf71074b721190743e', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (6, 'user:view', '148e3b98603bf1610fb29d0d9557b42d', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (7, 'topic:delete', '8589a69d41325d5506845fcaae9a6918', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (8, 'topic:publish', '11cb0539742ef73b74e5e77da454dcdc', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (9, 'topic:update', '6217a5a0ba6943e4bf6cbcd4e41dac57', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (10, 'comment:publish', 'e2b5ffa9788dfa85a29eb0065e400b19', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (11, 'comment:delete', 'e08b5ef2618e47583746f300fe620eac', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission` VALUES (12, 'comment:edit', '0d93f764327600b00cf48fbf6bec7d9f', '2024-02-06 21:37:51', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for t_permission_role_relate
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_role_relate`;
CREATE TABLE `t_permission_role_relate`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `permission_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission_role_relate
-- ----------------------------
INSERT INTO `t_permission_role_relate` VALUES (1, 1, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (2, 2, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (3, 3, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (4, 4, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (5, 5, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (6, 6, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (7, 7, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (8, 8, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (9, 9, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (10, 10, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (11, 11, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_permission_role_relate` VALUES (12, 12, 1, '2024-02-06 21:38:53', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'admin', 'f540a732c4f411eeab390242ac120004', '管理员用户组', '2024-02-03 14:41:19', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_role` VALUES (2, 'topic_manager', 'fae8f3eec4f411eeab390242ac120004', '版主', '2024-02-03 14:41:21', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_role` VALUES (3, 'user', 'ff9c74c2c4f411eeab390242ac120004', '用户', '2024-02-03 14:41:19', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `status` int NOT NULL COMMENT '状态 0未启用 1启用',
  `color` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE,
  INDEX `idx_tag_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_topic`;
CREATE TABLE `t_topic`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_id` bigint NOT NULL COMMENT '分类标签ID',
  `collect_count` bigint NOT NULL COMMENT '收藏数量',
  `user_id` bigint NOT NULL COMMENT '发布者用户ID',
  `title` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '纯文字内容',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细内容',
  `image_list` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片URl集合 json格式',
  `recommend` tinyint(1) NOT NULL COMMENT '是否被加精 0否1是',
  `recommend_time` datetime NULL DEFAULT NULL COMMENT '加精时间',
  `sticky` tinyint(1) NOT NULL COMMENT '是否置顶 0否1是',
  `sticky_time` datetime NULL DEFAULT NULL COMMENT '置顶时间',
  `view_count` bigint NOT NULL COMMENT '查看数',
  `comment_count` bigint NOT NULL COMMENT '评论数',
  `like_count` bigint NOT NULL COMMENT '点赞数',
  `status` tinyint NOT NULL COMMENT '状态 0不可见 1可见',
  `last_comment_time` datetime NULL DEFAULT NULL COMMENT '最后评论时间',
  `last_comment_user_id` bigint NULL DEFAULT NULL COMMENT '最后评论的用户ID',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者用户IP',
  `ip_location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者用户IP定位',
  `extra_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '额外数据',
  `is_lock` tinyint NULL DEFAULT NULL COMMENT '帖子是否被锁定 0否1是',
  `lock_time` datetime NULL DEFAULT NULL COMMENT '帖子锁定时间',
  `require_score` int NOT NULL COMMENT '积分需要',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_upload_file
-- ----------------------------
DROP TABLE IF EXISTS `t_upload_file`;
CREATE TABLE `t_upload_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件ID',
  `url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件访问地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_upload_file_pk`(`file_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email_verified` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否确认 0否 1是',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像地址',
  `gender` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别 1男 0女 -1保密',
  `birthday` datetime(3) NULL DEFAULT NULL COMMENT '生日',
  `background_image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '背景图片',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户描述',
  `score` int NOT NULL COMMENT '积分',
  `status` int NOT NULL COMMENT '状态 0封禁 1正常 2 禁言',
  `collect_count` int NOT NULL COMMENT '收藏帖子数量',
  `topic_count` int NOT NULL COMMENT '帖子数量',
  `comment_count` int NOT NULL COMMENT '评论数量',
  `follow_count` int NOT NULL COMMENT '关注数量',
  `fans_count` int NOT NULL COMMENT '粉丝数量',
  `forbidden_end_time` datetime NULL DEFAULT NULL COMMENT '封禁结束时间',
  `mute_end_time` datetime NULL DEFAULT NULL COMMENT '禁言截止时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_score`(`score` ASC) USING BTREE,
  INDEX `idx_user_status`(`status` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'admin@qq.com', 1, '超管a', 'http://192.168.31.231:9001/foro/upload/2024-03-05/74320cb98b4348c6a1af8ea9233ad751.png', 1, '2024-01-16 13:00:17.000', 'http://192.168.31.231:9001/foro/upload/2024-03-05/543866c3b8a84b4da7cd4a73d3dda172.png', '$2a$10$UtyjlkiY1U/QrkJvgN1AtOhkEgxrOCnO9VxYDLRuONIYvZqfTzP2K', '凄凄切切', 20, 1, 2, 4, 4, 0, 0, '2024-01-16 13:01:01', '2024-03-20 13:49:51', '2024-01-28 20:37:07', -1, 'SYSTEM', '2024-04-01 18:46:24', -1, 'SYSTEM', 48, 0);

-- ----------------------------
-- Table structure for t_user_collect_relate
-- ----------------------------
DROP TABLE IF EXISTS `t_user_collect_relate`;
CREATE TABLE `t_user_collect_relate`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '发起收藏的用户ID',
  `topic_id` bigint NOT NULL COMMENT '被收藏的帖子ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_user_collect_relate_topic_id_index`(`topic_id` ASC) USING BTREE,
  INDEX `t_user_collect_relate_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户收藏帖子关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_follow_relate
-- ----------------------------
DROP TABLE IF EXISTS `t_user_follow_relate`;
CREATE TABLE `t_user_follow_relate`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `follow_user_id` bigint NOT NULL COMMENT '发起关注者ID',
  `followed_user_id` bigint NOT NULL COMMENT '被关注者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_user_follow_relate_follow_user_id_index`(`follow_user_id` ASC) USING BTREE,
  INDEX `t_user_follow_relate_followed_user_id_index`(`followed_user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_like_relate
-- ----------------------------
DROP TABLE IF EXISTS `t_user_like_relate`;
CREATE TABLE `t_user_like_relate`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '发起点赞的用户ID',
  `topic_id` bigint NOT NULL COMMENT '被点赞的帖子ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_user_like_relate_topic_id_index`(`topic_id` ASC) USING BTREE,
  INDEX `t_user_like_relate_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_role_relate
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role_relate`;
CREATE TABLE `t_user_role_relate`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '权限ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_user_role_relate_role_id_index`(`role_id` ASC) USING BTREE,
  INDEX `t_user_role_relate_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role_relate
-- ----------------------------
INSERT INTO `t_user_role_relate` VALUES (1, 1, 1, '2024-02-06 21:35:38', -1, 'SYSTEM', NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for t_user_tag_manage
-- ----------------------------
DROP TABLE IF EXISTS `t_user_tag_manage`;
CREATE TABLE `t_user_tag_manage`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `tag_id` bigint NOT NULL COMMENT '板块ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_user_tag_manage_tag_id_index`(`tag_id` ASC) USING BTREE,
  INDEX `t_user_tag_manage_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_verify_code
-- ----------------------------
DROP TABLE IF EXISTS `t_user_verify_code`;
CREATE TABLE `t_user_verify_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `token` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '格式:uuid',
  `code` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '格式:随机字符串',
  `type` tinyint NOT NULL COMMENT '验证类型 1注册验证 2找回密码验证',
  `checked` tinyint(1) NOT NULL COMMENT '该验证码是否验证 0否1是',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者用户ID',
  `create_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者用户ID',
  `update_username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者用户名',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_user_verify_code_pk`(`token` ASC) USING BTREE,
  UNIQUE INDEX `t_user_verify_code_pk2`(`code` ASC) USING BTREE,
  INDEX `idx_user_score_log_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
