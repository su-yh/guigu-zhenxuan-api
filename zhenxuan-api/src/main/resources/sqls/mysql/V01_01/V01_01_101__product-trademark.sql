

CREATE TABLE b_product_trademark
(
    id      bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    name    varchar(64) NOT NULL COMMENT '品牌名称',
    logo    varchar(255) NOT NULL COMMENT '品牌LOGO',
    created datetime DEFAULT CURRENT_TIMESTAMP,
    updated datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT ='商品品牌';

