package com.rose.common.mp;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR(10000, "出错啦，后台小哥正在努力修复中..."),
    // ----------- 业务异常状态码 -----------
    PRODUCT_NOT_FOUND(20000, "该产品不存在（测试使用）");

    // 异常码
    private final Integer errorCode;
    // 错误信息
    private final String errorMessage;

    ResponseEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}