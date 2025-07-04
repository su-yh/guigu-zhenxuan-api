package com.eb.system.dto.rsp;

import com.eb.rouyi.entity.SysRoleEntity;
import com.eb.rouyi.entity.SysUserEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserRspDto {
    private SysUserEntity sysUserEntity;
    private List<SysRoleEntity> roleEntities;
}
