package com.eb.system.service;

import com.eb.rouyi.entity.SysRoleEntity;
import com.eb.rouyi.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysRoleService {
    private final SysRoleMapper sysRoleMapper;


    public List<SysRoleEntity> selectRoleList(@Nullable Collection<Long> roleIds) {
        return sysRoleMapper.selectRoleList(roleIds);
    }

    public List<SysRoleEntity> listAll() {
        return sysRoleMapper.selectList();
    }

    public Long create(SysRoleEntity entity) {
        sysRoleMapper.insert(entity);

        return entity.getRoleId();
    }

    public void update(SysRoleEntity entity) {
        sysRoleMapper.updateById(entity);
    }

    public void delete(@Nullable Long roleId) {
        if (roleId == null) {
            return;
        }

        sysRoleMapper.deleteById(roleId);
    }
}
