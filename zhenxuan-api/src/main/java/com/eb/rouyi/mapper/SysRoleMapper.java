package com.eb.rouyi.mapper;

import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.rouyi.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapperX<SysRoleEntity> {
    default List<SysRoleEntity> selectRoleList(@Nullable Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }

        LambdaQueryWrapperX<SysRoleEntity> queryWrapperX = build();
        queryWrapperX.in(SysRoleEntity::getRoleId, roleIds);

        return selectList(queryWrapperX);
    }
}
