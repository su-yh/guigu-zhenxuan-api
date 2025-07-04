package com.eb.mvc.authentication;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.entity.SysUserEntity;
import com.eb.system.service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class LoginUser {
    public static final String LOGIN_USER_ATTRIBUTE_KEY = "loginUser";

    public LoginUser(UserService userService, Long id, String username, String nickname) {
        if (userService == null || id == null || username == null || nickname == null) {
            log.error("userService or id or username is null!, id: {}, username: {}, nickname: {}",
                    id, username, nickname);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        this.userService = userService;
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }

    private final UserService userService;

    @Getter
    private final Long id;
    @Getter
    private final String username;
    @Getter
    private final String nickname;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    private volatile SysUserEntity user;

    public SysUserEntity getUser() {
        if (user == null) {
            synchronized (this) {
                if (user == null) {
                    user = userService.obtainUserById(id);
                }
            }
        }

        return user;
    }

    public Set<String> getPermissions() {
        if (permissions == null) {
            // TODO: suyh - 暂时这样吧，懒得处理，这里是标记这个用户有哪些权限的
            permissions = new HashSet<>();
        }
        return permissions;
    }
}
