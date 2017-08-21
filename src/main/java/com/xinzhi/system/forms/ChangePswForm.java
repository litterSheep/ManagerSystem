package com.xinzhi.system.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by ly on 2017/8/21 15:45.
 */
public class ChangePswForm {

    @NotEmpty(message = "请输入旧密码")
    private String oldPsw;
    @NotEmpty(message = "请输入新密码")
    private String newPsw;
    @NotEmpty(message = "请再次输入新密码")
    private String newPswAgain;

    public String getOldPsw() {
        return oldPsw;
    }

    public void setOldPsw(String oldPsw) {
        this.oldPsw = oldPsw;
    }

    public String getNewPsw() {
        return newPsw;
    }

    public void setNewPsw(String newPsw) {
        this.newPsw = newPsw;
    }

    public String getNewPswAgain() {
        return newPswAgain;
    }

    public void setNewPswAgain(String newPswAgain) {
        this.newPswAgain = newPswAgain;
    }
}
