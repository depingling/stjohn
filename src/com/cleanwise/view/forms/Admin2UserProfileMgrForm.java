package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.UserData;

import javax.servlet.http.HttpServletRequest;

public class Admin2UserProfileMgrForm extends ActionForm {

    public UserData user;

    private String password;
    private String confirmPassword;
    private boolean init;
    private String confirmationMessage;

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.confirmPassword = null;
        this.password = null;
        this.confirmationMessage= "";
        super.reset(mapping, request);
    }

    public void init() {
        this.init = true;
    }

    public boolean isInit() {
        return init;
    }

}
