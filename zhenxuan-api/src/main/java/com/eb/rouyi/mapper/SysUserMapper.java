package com.eb.rouyi.mapper;

import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.rouyi.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

@Mapper
public interface SysUserMapper extends BaseMapperX<SysUserEntity> {
    default SysUserEntity selectByUni(String username) {
        return selectUserByUserName(username);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    default SysUserEntity selectUserByUserName(String userName) {
        LambdaQueryWrapperX<SysUserEntity> queryWrapperX = build();
        queryWrapperX.eq(SysUserEntity::getUsername, userName);
        return selectOne(queryWrapperX);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    default SysUserEntity selectUserById(Long userId) {
        LambdaQueryWrapperX<SysUserEntity> queryWrapperX = build();
        queryWrapperX.eq(SysUserEntity::getId, userId);
        return selectOne(queryWrapperX);
    }

    default PageResult<SysUserEntity> listPage(PageParam pageParam, @Nullable String nameLike) {
        LambdaQueryWrapperX<SysUserEntity> queryWrapperX = build();
        queryWrapperX.likeIfPresent(SysUserEntity::getNickname, nameLike);

        return selectPage(pageParam, queryWrapperX);
    }
}
