package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-06
 */
@Getter
public enum ReviewStatusEnums {
    @Schema(description = "1 待审核")
    WAITING(1),
    @Schema(description = "2 审核通过")
    APPROVED(2),
    @Schema(description = "3 审核拒绝")
    REJECTED(3),
    ;

    @EnumValue
    private final int code;

    ReviewStatusEnums(int code) {
        this.code = code;
    }
}
