package com.cleanwise.view.forms;

import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import org.apache.struts.action.ActionForm;

public class Admin2PageForm extends ActionForm {

    private UiPageViewWrapper uiPage;

    public UiPageViewWrapper getUiPage() {
        return uiPage;
    }

    public void setUiPage(UiPageViewWrapper uiPage) {
        this.uiPage = uiPage;
    }

}
