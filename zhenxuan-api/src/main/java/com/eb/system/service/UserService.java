package com.eb.system.service;

import com.eb.config.base.properties.BaseProperties;
import com.eb.constant.ErrorCodeConstants;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.entity.SysRoleEntity;
import com.eb.rouyi.entity.SysUserEntity;
import com.eb.rouyi.entity.SysUserRoleEntity;
import com.eb.rouyi.mapper.SysUserMapper;
import com.eb.system.dto.rsp.UserRspDto;
import com.eb.util.TokenUtils;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();

    private final BaseProperties baseProperties;
    private final PasswordEncoder passwordEncoder;

    private final SysUserMapper userMapper;

    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    public String login(@NonNull String username, @NonNull String password, @NonNull Integer code) {
        SysUserEntity historyEntity = userMapper.selectByUni(username);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_BAD_CREDENTIALS);
        }

        validUser2Fa(historyEntity.getTwoFactorAuthKey(), code);

        String sourcePassword = password + historyEntity.getSalt();
        boolean matchFlag = passwordEncoder.matches(sourcePassword, historyEntity.getPassword());
        if (!matchFlag) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_BAD_CREDENTIALS);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenUtils.NICK_NAME_KEY, historyEntity.getNickname());
        return TokenUtils.createToken(claims, historyEntity.getId(), username, baseProperties.getTokenSeconds());
    }

    @Transactional
    public int createUser(SysUserEntity sysUserEntity) {
        SysUserEntity historyEntity = userMapper.selectByUni(sysUserEntity.getUsername());
        if (historyEntity != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_EXISTS, sysUserEntity.getUsername());
        }

        String password = sysUserEntity.getPassword();
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);

        String sourcePassword = password + salt;
        String ciphertextPwd = passwordEncoder.encode(sourcePassword);

        String twoFactorAuthkey = googleAuth.createCredentials().getKey();

        sysUserEntity.setPassword(ciphertextPwd);
        sysUserEntity.setSalt(salt);
        sysUserEntity.setTwoFactorAuthKey(twoFactorAuthkey);

        return userMapper.insert(sysUserEntity);
    }

    @Transactional
    public void updateUser(SysUserEntity sysUserEntity) {
        {
            SysUserEntity historyEntity = userMapper.selectById(sysUserEntity.getId());
            if (historyEntity == null) {
                throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
            }
        }

        {
            SysUserEntity historyEntity = userMapper.selectByUni(sysUserEntity.getUsername());
            if (historyEntity != null && !historyEntity.getId().equals(sysUserEntity.getId())) {
                throw ExceptionUtil.business(ErrorCodeConstants.USER_EXISTS, sysUserEntity.getUsername());
            }
        }

        userMapper.updateById(sysUserEntity);
    }


    private void validUser2Fa(String twoFactorAuthKey, Integer codeFa) {
        if (!baseProperties.getCaptcha().isTwoFactorAuthEnabled()) {
            return;
        }

        boolean isCodeValid = googleAuth.authorize(twoFactorAuthKey, codeFa);
        if (!isCodeValid) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_LOGIN_CAPTCHA_ERROR);
        }
    }

    public SysUserEntity obtainUserById(Long userId) {
        if (userId == null) {
            return null;
        }

        return userMapper.selectUserById(userId);
    }

    @Transactional
    public void updateUserPwd(@NonNull Long userId, @NonNull String password) {
        SysUserEntity historyEntity = obtainUserById(userId);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String ciphertextPwd = passwordEncoder.encode(password + salt);

        SysUserEntity user = new SysUserEntity();
        user.setId(userId);
        user.setPassword(ciphertextPwd);
        user.setSalt(salt);

        userMapper.updateById(user);
    }

    @Transactional
    public String resetTwoFactorAuthKey(@NonNull Long id) {
        SysUserEntity historyEntity = obtainUserById(id);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        String twoFactorAuthkey = googleAuth.createCredentials().getKey();

        SysUserEntity updateEntity = new SysUserEntity();
        updateEntity.setId(id).setTwoFactorAuthKey(twoFactorAuthkey);

        userMapper.updateById(updateEntity);

        return twoFactorAuthkey;
    }

    public SysUserEntity queryEntityById(@NonNull Long id) {
        SysUserEntity entity = userMapper.selectUserById(id);
        if (entity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        return entity;
    }

    @Transactional
    public void updatePwdByOldValue(
            @NonNull Long userId, @NonNull String oldPassword, @NonNull String newPassword) {
        SysUserEntity historyEntity = obtainUserById(userId);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        String sourcePassword = oldPassword + historyEntity.getSalt();
        boolean matchFlag = passwordEncoder.matches(sourcePassword, historyEntity.getPassword());
        if (!matchFlag) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_OLD_PASSWORD_NOT_MATCH);
        }

        updateUserPwd(userId, newPassword);
    }

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
