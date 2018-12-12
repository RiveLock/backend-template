package com.template.response;

/**
 * @Title: Success
 * @Description: Success 信息
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 16:59
 */
public enum Success {

    USER_LOGIN_SUCCESS("Success_001","login success"),
    USER_REGISTER_SUCCESS("Success_002","register success"),
    LOGOUT_SUCCESS("Success_003","logout success")
    ;

    public final String code;
    public final String description;

    Success(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
