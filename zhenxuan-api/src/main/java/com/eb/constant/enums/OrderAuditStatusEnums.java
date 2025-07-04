package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-06
 */
@Getter
public enum OrderAuditStatusEnums {
    @Schema(description = "0-正常")
    NORMAL(0),
    @Schema(description = "1-待审核")
    WAITING_PROCESS(1),
    @Schema(description = "2 存疑")
    DOUBT(2),
    @Schema(description = "3 审核通过")
    SUCCESS(3),
    @Schema(description = "4 拒绝")
    REJECTED(4),
    ;

    @EnumValue
    private final int code;

    OrderAuditStatusEnums(int code) {
        this.code = code;
    }
}
