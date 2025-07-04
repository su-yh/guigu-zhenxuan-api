package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 商品属性分类对应的实体类
 *
 * @author suyh
 * @since 2025-04-17
 */
@Data
@TableName(value = "b_product_attr_category", autoResultMap = true)
public class ProductAttrCategoryEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "上级分类ID，一级分类时为0")
    private Long parentId;

    @Schema(description = "分类名称")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated;
}
