
CREATE TABLE b_product_attr_value
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    attr_name_id bigint      NOT NULL COMMENT '属性名称主键ID',
    value        varchar(64) NOT NULL COMMENT '属性值',
    created     datetime DEFAULT CURRENT_TIMESTAMP,
    updated     datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT ='商品属性值';

-- 非唯一索引
ALTER TABLE b_product_attr_value
    ADD INDEX idx_n (attr_name_id);




