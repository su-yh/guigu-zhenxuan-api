package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.web.validation.groups.ValidationGroups;
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
@TableName(value = "b_product_attr_name", autoResultMap = true)
public class ProductAttrNameEntity {
    @TableId(type = IdType.AUTO)
    @Null(groups = ValidationGroups.Req.Create.class)
    @NotNull(groups = ValidationGroups.Req.Update.class)
    private Long id;

    @NotNull(groups = ValidationGroups.Req.Create.class)
    @Null(groups = ValidationGroups.Req.Update.class)
    @Schema(description = "分类ID")
    private Long categoryId;

    @NotNull
    @Schema(description = "属性名称")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated;
}
