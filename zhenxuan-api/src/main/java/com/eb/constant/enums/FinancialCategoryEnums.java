package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum FinancialCategoryEnums {
    TPP("TPP", 1),
    BANK("BANK", 2),
    ;

    @EnumValue
    private final String code;

    private final int codeEnum;

    FinancialCategoryEnums(String code, int codeEnum) {
        this.code = code;
        this.codeEnum = codeEnum;
    }
}
