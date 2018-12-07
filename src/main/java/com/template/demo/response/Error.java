package com.template.demo.response;

/**
 * @Title: Error
 * @Description: Error 信息
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 16:59
 */
public enum Error {

    USER_NOT_LOGIN_NULL("Error_001","login name is empty!"),
    USER_NOT_PASSWORD_NULL("Error_002","login password is empty!"),
    USERNAME_EXIST("Error_003","login password is empty!"),
    USER_REGISTER_FAIL("Error_004","register fail!"),
    USERNAME_NOT_EXIST("Error_005","login name is wrong!"),
    USER_PASSWORD_FAIL("Error_006","login password is wrong!"),
    ;

    public final String code;
    public final String description;

    Error(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
