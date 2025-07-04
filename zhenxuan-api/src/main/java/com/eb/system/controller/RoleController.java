package com.eb.system.controller;

import com.base.web.response.dto.R;
import com.base.web.validation.groups.ValidationGroups;
import com.eb.business.dto.base.IdBody;
import com.eb.rouyi.entity.SysRoleEntity;
import com.eb.system.dto.req.UserRoleListReqDto;
import com.eb.system.service.SysRoleService;
import com.eb.system.service.SysUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;
import java.util.List;

@Tag(name = "角色")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RoleController {
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;

    @Operation(summary = "【角色管理】查询(所有)")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public R<List<SysRoleEntity>> listAll() {
        List<SysRoleEntity> roleEntities = sysRoleService.listAll();

        return R.ofSuccess(roleEntities);
    }

    @Operation(summary = "【角色管理】更新用户角色列表")
    @RequestMapping(value = "/update/user/roleList", method = RequestMethod.POST)
    public R<Long> updateUserRoleList(
            @RequestBody @Validated UserRoleListReqDto userRoleListReqDto) {
        sysUserRoleService.updateUserRoleList(userRoleListReqDto);

        return R.ofSuccess(userRoleListReqDto.getUserId());
    }

    @Operation(summary = "【角色管理】创建角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R<Long> create(
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) SysRoleEntity entity) {
        Long id = sysRoleService.create(entity);

        return R.ofSuccess(id);
    }

    @Operation(summary = "【角色管理】修改角色")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<Long> update(
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) SysRoleEntity entity) {
        sysRoleService.update(entity);

        return R.ofSuccess(entity.getRoleId());
    }

    @Operation(summary = "【角色管理】删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R<Long> delete(
            @RequestBody IdBody idBody) {
        sysRoleService.delete(idBody.getId());

        return R.ofSuccess(idBody.getId());
    }

}
