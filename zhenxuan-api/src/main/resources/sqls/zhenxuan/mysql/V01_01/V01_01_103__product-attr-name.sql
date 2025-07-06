
CREATE TABLE b_product_attr_name
(
    id          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    category_id bigint      NOT NULL COMMENT '分类ID',
    name        varchar(64) NOT NULL COMMENT '属性名称',
    created     datetime DEFAULT CURRENT_TIMESTAMP,
    updated     datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT ='商品属性名称';

-- 非唯一索引
ALTER TABLE b_product_attr_name
    ADD UNIQUE INDEX uni_c_n (category_id, name);


