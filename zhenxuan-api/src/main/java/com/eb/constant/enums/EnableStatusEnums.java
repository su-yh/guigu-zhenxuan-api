package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum EnableStatusEnums {
    ENABLE(1),
    DISABLE(0),
    ;

    @EnumValue
    private final int code;

    EnableStatusEnums(int code) {
        this.code = code;
    }
}
