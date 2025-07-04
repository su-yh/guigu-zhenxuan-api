package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum OnlineStateEnums {
    ONLINE(1),
    OFFLINE(0),
    ;

    @EnumValue
    private final int code;

    OnlineStateEnums(int code) {
        this.code = code;
    }
}
