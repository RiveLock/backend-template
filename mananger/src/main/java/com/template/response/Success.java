package com.template.response;

/**
 * @Title: Success
 * @Description: Success 信息
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 16:59
 */
public enum Success {

    USER_LOGIN_SUCCESS("Success_001","login success!"),
    USER_REGISTER_SUCCESS("Success_002","register success!"),
    LOGOUT_SUCCESS("Success_003","logout success!"),
    PERM_ADD_SUCCESS("Success_004","perm add success!"),
    PERM_UPDATE_SUCCESS("Success_005","perm update success!"),
    PERM_DELETE_SUCCESS("Success_006","perm delete success!"),
    ROLE_ADD_SUCCESS("Success_007","role add success!"),
    ROLE_UPDATE_SUCCESS("Success_008","role update success!"),
    ROLE_DELETE_SUCCESS("Success_009","role delete success!"),
    USER_UPDATE_SUCCESS("Success_010","user update success!"),
    USER_DELETE_SUCCESS("Success_011","user delete success!"),

    ;

    public final String code;
    public final String description;

    Success(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
