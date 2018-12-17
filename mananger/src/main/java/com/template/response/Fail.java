package com.template.response;

/**
 * @Title: Fail
 * @Description: Fail 信息
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 16:59
 */
public enum Fail {
    SYSTEM_FAIL("Fail_000","system error!"),
    USER_NOT_LOGIN_NULL("Fail_001","login name is empty!"),
    USER_NOT_PASSWORD_NULL("Fail_002","login password is empty!"),
    USERNAME_EXIST("Fail_003","login password is empty!"),
    USER_REGISTER_FAIL("Fail_004","register fail!"),
    USERNAME_NOT_EXIST("Fail_005","login name is wrong!"),
    USER_PASSWORD_FAIL("Fail_006","login password is wrong!"),
    PERM_ADD_FAIL("Fail_007","perm add fail!"),
    PERM_UPDATE_FAIL("Fail_008","perm update fail!"),
    PERM_DELETE_FAIL("Fail_009","perm delete fail!"),
    ROLE_ADD_FAIL("Fail_010","role add fail!"),
    ROLE_UPDATE_FAIL("Fail_011","role update fail!"),
    ROLE_DELETE_FAIL("Fail_012","role delete fail!"),
    USER_LOGINNAME_ERROR("Fail_013","loginName is Excited!"),
    USER_UPDATE_FAIL("Fail_014","user update fail!"),
    USER_DELETE_FAIL("Fail_015","user delete fail!"),
    USER_NOT_LOGIN("Fail_016","user not logged in!")
    ;

    public final String code;
    public final String description;

    Fail(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
