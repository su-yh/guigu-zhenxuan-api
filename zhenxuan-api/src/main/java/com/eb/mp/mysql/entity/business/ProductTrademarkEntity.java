package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.web.validation.groups.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@TableName(value = "b_product_trademark", autoResultMap = true)
public class ProductTrademarkEntity {
    @TableId(value = "id", type = IdType.AUTO)
    @Null(groups = ValidationGroups.Req.Create.class)
    @NotNull(groups = ValidationGroups.Req.Update.class)
    private Long id;

    @TableField("name")
    @NotNull(groups = ValidationGroups.Req.Create.class)
    @Size(max = 64)
    private String name;

    @TableField("logo")
    @NotNull(groups = ValidationGroups.Req.Create.class)
    @Size(max = 255)
    private String logo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated;
}
