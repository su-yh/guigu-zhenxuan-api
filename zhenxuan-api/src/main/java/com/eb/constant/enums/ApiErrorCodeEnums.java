package com.eb.constant.enums;

import com.base.web.error.IErrorCode;

/**
 * @author suyh
 * @since 2025-07-04
 */
public enum ApiErrorCodeEnums implements IErrorCode {
    RECORD_NOT_EXISTS(3001001, "记录({0}) 不存在"),
    WEIGHT_CONFIG_ID_NOT_EXISTS(3001002, "Weight config ID ({0}) does not exist"),
    FILE_TOO_LARGE(3001003, "文件太大了"),

    ;

    private final int code;
    private final String msg;

    ApiErrorCodeEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
