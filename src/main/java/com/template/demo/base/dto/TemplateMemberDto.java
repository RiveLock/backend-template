package com.template.demo.base.dto;

import com.template.demo.base.domain.TemplateMember;

/**
 * Created jixinshi on 2018-12-03.
 */
public class TemplateMemberDto extends TemplateMember {
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
