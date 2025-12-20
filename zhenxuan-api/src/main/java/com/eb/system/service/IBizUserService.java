package com.eb.system.service;

import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
import com.eb.rouyi.entity.SysUserEntity;
import com.eb.system.dto.rsp.UserRspDto;
import com.web.sys.service.IUserService;

public interface IBizUserService extends IUserService {
    PageResult<UserRspDto> listPage(PageParam pageParam, String nameLike);

    void updateUser(SysUserEntity entity);

    void deleteUser(Long id);
}
