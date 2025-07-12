package com.eb.rouyi.mapper;

import com.base.mp.mybatis.BaseMapperX;
import com.base.mp.mybatis.LambdaQueryWrapperX;
import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
import com.eb.rouyi.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ApiSysRoleMapper extends BaseMapperX<SysRoleEntity> {
    default List<SysRoleEntity> selectRoleList(@Nullable Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }

        LambdaQueryWrapperX<SysRoleEntity> queryWrapperX = build();
        queryWrapperX.in(SysRoleEntity::getRoleId, roleIds);

        return selectList(queryWrapperX);
    }

    default PageResult<SysRoleEntity> listPage(PageParam pageParam) {
        LambdaQueryWrapperX<SysRoleEntity> queryWrapperX = build();

        return selectPage(pageParam, queryWrapperX);
    }
}
