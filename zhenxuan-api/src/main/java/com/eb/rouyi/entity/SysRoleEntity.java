package com.eb.rouyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.web.validation.groups.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@TableName("sys_role")
@Data
public class SysRoleEntity {

    /** 角色ID */
    @TableId(type = IdType.AUTO)
    @Null(groups = ValidationGroups.Req.Create.class)
    @NotNull(groups = ValidationGroups.Req.Update.class)
    private Long roleId;

    /** 角色名称 */
    @NotBlank(groups = ValidationGroups.Req.Create.class)
    private String roleName;

    /** 角色权限 */
    private String roleKey;

    /** 角色排序 */
    private Integer roleSort;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
    private String dataScope;

    /** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
    private boolean menuCheckStrictly;

    /** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
    private boolean deptCheckStrictly;

    /** 角色状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private String remark;

    private String createBy;

    private String updateBy;

    @TableField("create_time")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created;

    @TableField("update_time")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated;
}
