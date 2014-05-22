package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.UiView;

public class UiMgrForm extends ActionForm {

    private UiView uiView;

    public UiView getUiView() {
        return uiView;
    }

    public void setUiView(UiView uiView) {
        this.uiView = uiView;
    }
}
