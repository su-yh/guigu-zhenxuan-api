package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum RuoyiEnableStatusEnums {
    // ruoyi 里面，0 表示 启动，1 表示 禁用
    ENABLE(0),
    DISABLE(1),
    ;

    @EnumValue
    private final int code;

    RuoyiEnableStatusEnums(int code) {
        this.code = code;
    }
}
