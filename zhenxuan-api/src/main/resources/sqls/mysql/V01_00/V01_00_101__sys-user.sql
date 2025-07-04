

CREATE TABLE sys_user
(
    id                  bigint  NOT NULL AUTO_INCREMENT COMMENT '主键',
    username            varchar(64) NOT NULL COMMENT '用户名',
    password            varchar(64) NOT NULL COMMENT '通过盐值加密后的密码',
    nickname            varchar(64) NOT NULL COMMENT '昵称',
    salt                varchar(32) NOT NULL COMMENT '盐',
    two_factor_auth_key varchar(32) NOT NULL COMMENT 'google 二次认证密钥',
    dept_id             bigint   DEFAULT NULL COMMENT '部门ID',
    user_type           varchar(2)   DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
    email               varchar(50)  DEFAULT '' COMMENT '用户邮箱',
    phonenumber         varchar(20)  DEFAULT '' COMMENT '手机号码',
    sex                 char(1)      DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    avatar              varchar(100) DEFAULT '' COMMENT '头像路径',
    status              char(1)      DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    del_flag            char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    login_ip            varchar(128) DEFAULT '' COMMENT '最后登录IP',
    login_date          datetime     DEFAULT NULL COMMENT '最后登录时间',
    pwd_update_date     datetime     DEFAULT NULL COMMENT '密码最后更新时间',
    create_by           varchar(64)  DEFAULT '' COMMENT '创建者',
    update_by           varchar(64)  DEFAULT '' COMMENT '更新者',
    remark              varchar(500) DEFAULT '' COMMENT '备注',
    created             datetime     DEFAULT CURRENT_TIMESTAMP,
    updated             datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT ='用户信息';

-- 添加唯一索引
ALTER TABLE sys_user ADD UNIQUE INDEX uni_name(username);


-- 初始化一个admin 用户: 密码也是admin
-- admin 用户的ID 必须 是1 ，这在后面的关联关系的时候需要用到
INSERT INTO sys_user (id, username, password, nickname, salt, two_factor_auth_key, avatar)
VALUES (1, 'admin', '$2a$10$RO4jsSW3Xv1d9LMFk5vj/eF9Od2w5eCTroLMhOZCJBsanez/QMWA2', 'admin',
        '837dc2085500480f8b0c2b0222d15f10', 'HMG6NIOZPH65HGKIMLUW7YIDCG2M65XN',
        'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif');


