package com.eb.config.datasource.constant;

import lombok.Getter;

/**
 * 这里枚举的数量应该和 DataSourceNames 的数量一样
 *
 * @author suyh
 * @since 2025-03-18
 */
@Getter
public enum DataSourceEnums {
    MASTER(DataSourceNames.MASTER),
    SLAVE(DataSourceNames.SLAVE),
    ;

    private final String code;

    DataSourceEnums(String code) {
        this.code = code;
    }
}
