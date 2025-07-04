package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum SvipAuditStatusEnums {
    @Schema(description = "0 未提交审核")
    NON_SUBMIT(0),
    @Schema(description = "1 审核中")
    APPROVING(1),
    @Schema(description = "2 审核通过")
    APPROVED(2),
    @Schema(description = "3 审核失败")
    REJECTED(3),
    @Schema(description = "4 已注销冻结")
    FROZEN(4),
    ;

    @EnumValue
    private final int code;

    SvipAuditStatusEnums(int code) {
        this.code = code;
    }
}
