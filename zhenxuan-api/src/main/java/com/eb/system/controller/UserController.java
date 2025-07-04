package com.eb.system.controller;

import com.eb.business.dto.base.IdBody;
import com.eb.group.ValidationGroups;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mvc.authentication.CurrLoginUser;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.authentication.annotation.Permit;
import com.eb.mvc.vo.ResponseResult;
import com.eb.rouyi.entity.SysUserEntity;
import com.eb.system.dto.req.UserLoginReqDto;
import com.eb.system.dto.rsp.UserRspDto;
import com.eb.system.service.UserService;
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

import javax.validation.groups.Default;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Tag(name = "用户")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @Operation(summary = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Permit(required = false)
    public ResponseResult<String> login(@RequestBody @Validated UserLoginReqDto userLogin) {
        String token = userService.login(
                userLogin.getUsername(), userLogin.getPassword(), userLogin.getCode());
        return ResponseResult.ofSuccess(token);
    }

    @Operation(summary = "用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @Permit(required = false)
    public ResponseResult<Boolean> logout() {
        return ResponseResult.ofSuccess();
    }

    @Operation(summary = "重置2FA：用户ID")
    @RequestMapping(value = "/reset/twoFactorAuthKey/byId", method = RequestMethod.POST)
    public ResponseResult<String> resetTwoFactorAuthKeyById(@RequestBody @Validated IdBody idBody) {
        String fa = userService.resetTwoFactorAuthKey(idBody.getId());
        return ResponseResult.ofSuccess(fa);
    }

    @Operation(summary = "重置2FA：当前登录用户")
    @RequestMapping(value = "/reset/twoFactorAuthKey/self", method = RequestMethod.POST)
    public ResponseResult<String> resetTwoFactorAuthKeySelf(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser) {
        String key = userService.resetTwoFactorAuthKey(loginUser.getId());
        return ResponseResult.ofSuccess(key);
    }

    @Operation(summary = "登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseResult<SysUserEntity> getInfo(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser) {
        return ResponseResult.ofSuccess(loginUser.getUser());
    }

    @Operation(summary = "【用户管理】查询(分页)")
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ResponseResult<PageResult<UserRspDto>> listPage(
            PageParam pageParam, @RequestParam(required = false) String nameLike) {
        PageResult<UserRspDto> pageResult = userService.listPage(pageParam, nameLike);
        return ResponseResult.ofSuccess(pageResult);
    }


    @Operation(summary = "【用户管理】创建用户")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult<Long> create(
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) SysUserEntity entity) {
        userService.createUser(entity);

        return ResponseResult.ofSuccess(entity.getId());
    }

    @Operation(summary = "【用户管理】更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Long> update(
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) SysUserEntity entity) {
        userService.updateUser(entity);

        return ResponseResult.ofSuccess(entity.getId());
    }

    @Operation(summary = "【用户管理】删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult<Long> delete(
            @RequestBody @Validated IdBody idBody) {
        userService.deleteUser(idBody.getId());

        return ResponseResult.ofSuccess(idBody.getId());
    }

}
