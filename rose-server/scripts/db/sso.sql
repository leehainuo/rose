drop
database if exists rose_sso;
create
database rose_sso charset utf8mb4;
use
rose_sso;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    `id`         char(20)    NOT NULL DEFAULT '' COMMENT '管理员id',
    `phone`      char(15)    NOT NULL DEFAULT '' COMMENT '手机',
    `open_id`    char(100)   NOT NULL DEFAULT '' COMMENT '微信open_id',
    `password`   varchar(65) NOT NULL DEFAULT '' COMMENT '密码',
    `nickname`   varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
    `created_at` DATETIME             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted_at` int(1)      NOT NULL DEFAULT 0 COMMENT '删除时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理员表';


CREATE TABLE `admin_role_rel`
(
    `admin_id` char(20) NOT NULL,
    `role_id`  char(10) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联表';


DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `id`   char(20) NOT NULL,
    `name` char(10) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

INSERT INTO `roles`
VALUES ('1', '超级管理员');

CREATE TABLE `member`
(
    `member_id`       char(20)     NOT NULL DEFAULT '' COMMENT '会员id',
    `phone`           varchar(15)  NOT NULL DEFAULT '' COMMENT '电话',
    `password`        varchar(150) NOT NULL DEFAULT '' COMMENT '密码',
    `last_login_ip`   char(20)     NOT NULL DEFAULT '' COMMENT '最后登录ip',
    `last_login_time` datetime              DEFAULT NULL COMMENT '最后登录时间',
    `open_id`         char(50)     NOT NULL DEFAULT '' COMMENT '微信open_id',
    `union_id`        char(50)     NOT NULL DEFAULT '' COMMENT '微信union_id',
    `status`          char(1)      NOT NULL DEFAULT '1' COMMENT '状态 0-禁用 1-启用',
    `created_at`      DATETIME              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted_at`      int(1)       NOT NULL DEFAULT 0 COMMENT '删除时间',
    PRIMARY KEY (`member_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='会员表';