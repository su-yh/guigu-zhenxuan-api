package com.eb.system.service;

import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
import com.base.web.exception.ExceptionUtil;
import com.eb.rouyi.entity.SysRoleEntity;
import com.eb.rouyi.entity.SysUserEntity;
import com.eb.rouyi.entity.SysUserRoleEntity;
import com.eb.rouyi.mapper.SysUserZhenXuanMapper;
import com.eb.system.dto.rsp.UserRspDto;
import com.web.sys.constants.enums.SysWebErrorCodeEnums;
import com.web.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ZhenXuanUserService extends UserService implements IZhenXuanUserService {
    private final SysUserZhenXuanMapper userMapper;

    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    @Override
    @Transactional
    public void updateUser(SysUserEntity sysUserEntity) {
        {
            SysUserEntity historyEntity = userMapper.selectById(sysUserEntity.getId());
            if (historyEntity == null) {
                throw ExceptionUtil.business(SysWebErrorCodeEnums.USER_NOT_EXISTS, "id: " + sysUserEntity.getId());
            }
        }

        {
            SysUserEntity historyEntity = userMapper.selectByUni(sysUserEntity.getUsername());
            if (historyEntity != null && !historyEntity.getId().equals(sysUserEntity.getId())) {
                throw ExceptionUtil.business(SysWebErrorCodeEnums.USER_EXISTS, "username: " + sysUserEntity.getUsername());
            }
        }

        userMapper.updateById(sysUserEntity);
    }

    @Override
    public PageResult<UserRspDto> listPage(PageParam pageParam, @Nullable String nameLike) {
        PageResult<SysUserEntity> pageResult = userMapper.listPage(pageParam, nameLike);
        if (pageResult.getTotal() <= 0) {
            return new PageResult<>(null, 0L);
        }

        List<UserRspDto> rspDtoList = new ArrayList<>();
        List<SysUserEntity> userEntities = pageResult.getList();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (SysUserEntity userEntity : userEntities) {
                UserRspDto rspDto = new UserRspDto();
                rspDto.setSysUserEntity(userEntity);

                List<SysUserRoleEntity> userRoleEntities = sysUserRoleService.selectRoleList(userEntity.getId());
                if (userRoleEntities != null && !userRoleEntities.isEmpty()) {
                    List<Long> roleIds = userRoleEntities.stream().map(SysUserRoleEntity::getRoleId).filter(Objects::nonNull).collect(Collectors.toList());
                    List<SysRoleEntity> roleEntities = sysRoleService.selectRoleList(roleIds);

                    rspDto.setRoleEntities(roleEntities);
                }

                rspDtoList.add(rspDto);
            }
        }

        return new PageResult<>(rspDtoList, pageResult.getTotal().longValue());
    }

    @Override
    @Transactional
    public void deleteUser(@Nullable Long id) {
        if (id == null) {
            return;
        }

        userMapper.deleteById(id);

        // TODO: suyh - 这里应该发一个删除用户事件，让用户相关的关联数据一起删除。
        //   但是这里就懒得做了，有空再说吧。
    }
}
