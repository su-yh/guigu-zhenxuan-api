package com.eb.system.controller;

import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
import com.base.web.response.dto.R;
import com.base.web.validation.groups.ValidationGroups;
import com.eb.rouyi.entity.SysUserEntity;
import com.eb.system.dto.rsp.UserRspDto;
import com.eb.system.service.IBizUserService;
import com.web.ruoyi.mybatis.entity.SysUser;
import com.web.sys.authentication.annotation.CurrLoginUser;
import com.web.sys.authentication.user.LoginUser;
import com.web.sys.dto.base.IdBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.groups.Default;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Tag(name = "用户")
@RestController("userZhenXuanController")
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    @Resource
    private IBizUserService userService;

    @Operation(summary = "登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R<SysUser> getInfo(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser) {
        return R.ofSuccess(loginUser.getUser());
    }

    @Operation(summary = "【用户管理】查询(分页)")
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public R<PageResult<UserRspDto>> listPage(
            PageParam pageParam, @RequestParam(required = false) String nameLike) {
        PageResult<UserRspDto> pageResult = userService.listPage(pageParam, nameLike);
        return R.ofSuccess(pageResult);
    }


    @Operation(summary = "【用户管理】创建用户")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R<Long> create(
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) SysUser entity) {
        userService.createUser(entity);

        return R.ofSuccess(entity.getId());
    }

    @Operation(summary = "【用户管理】更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R<Long> update(
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) SysUserEntity entity) {
        userService.updateUser(entity);

        return R.ofSuccess(entity.getId());
    }

    @Operation(summary = "【用户管理】删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R<Long> delete(
            @RequestBody @Validated IdBody idBody) {
        userService.deleteUser(idBody.getId());

        return R.ofSuccess(idBody.getId());
    }
}
