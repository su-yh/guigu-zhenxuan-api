
CREATE TABLE b_product_attr_category
(
    id        bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    parent_id bigint      NULL DEFAULT 0 COMMENT '上级分类ID，一级分类时为0',
    name      varchar(64) NOT NULL COMMENT '分类名称',
    created   datetime DEFAULT CURRENT_TIMESTAMP,
    updated   datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT ='商品属性分类';

-- 一级分类：图书/音像/电子书刊
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 1, 0, '图书/音像/电子书刊');


-- 一级分类：手机
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 2, 0, '手机');

INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 6, 2, '手机通讯');
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 7, 2, '运营商');
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 8, 2, '手机配件');

INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 9, 6, '手机');
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES (10, 6, '对讲机');

-- 一级分类：家用电器
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 3, 0, '家用电器');
-- 一级分类：数码
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 4, 0, '数码');
-- 一级分类：家居家装
INSERT INTO b_product_attr_category (id, parent_id, name) VALUES ( 5, 0, '家居家装');




