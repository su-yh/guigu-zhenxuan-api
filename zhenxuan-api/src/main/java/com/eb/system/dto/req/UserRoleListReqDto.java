package com.eb.system.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRoleListReqDto {
    @NotNull
    private Long userId;
    // 允许用户没有任何角色
    private List<Long> roleIds;
}
