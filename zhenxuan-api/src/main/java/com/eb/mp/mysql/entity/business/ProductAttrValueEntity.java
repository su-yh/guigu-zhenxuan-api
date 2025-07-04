package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author suyh
 * @since 2025-04-17
 */
@Data
@TableName(value = "b_product_attr_value", autoResultMap = true)
public class ProductAttrValueEntity {
    @TableId(type = IdType.AUTO)
    @Null
    private Long id;

    @Schema(description = "属性名称ID")
    @Null
    private Long attrNameId;

    @NotNull
    @Schema(description = "属性值")
    private String value;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated;
}
