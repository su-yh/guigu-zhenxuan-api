package com.eb.system.service;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.entity.SysUserRoleEntity;
import com.eb.rouyi.mapper.SysUserRoleMapper;
import com.eb.system.dto.req.UserRoleListReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserRoleService {
    private final SysUserRoleMapper sysUserRoleMapper;

    public List<SysUserRoleEntity> selectRoleList(@Nullable Long userId) {
        return sysUserRoleMapper.selectRoleList(userId);
    }

    @Transactional
    public void updateUserRoleList(UserRoleListReqDto userRoleListReqDto) {
        sysUserRoleMapper.deleteByUserId(userRoleListReqDto.getUserId());

        List<Long> roleIds = userRoleListReqDto.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }

        List<SysUserRoleEntity> userRoleEntities = new ArrayList<>();
        for (Long roleId : roleIds) {
            if (roleId == null) {
                throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR_PARAM, "roleId cannot be null");
            }

            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userRoleListReqDto.getUserId());
            entity.setRoleId(roleId);
            userRoleEntities.add(entity);
        }

        sysUserRoleMapper.insertBatch(userRoleEntities);
    }
}
