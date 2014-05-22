package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;


public class UiUserMgrForm extends ActionForm {

    private UiPageViewWrapper uiPage;

    public UiPageViewWrapper getUiPage() {
        return uiPage;
    }

    public void setUiPage(UiPageViewWrapper uiPage) {
        this.uiPage = uiPage;
    }


    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if (uiPage != null) {
            UiControlViewVector controls = uiPage.getUiControls();
            if (controls != null) {
                for (Object oControl : controls) {
                    UiControlView control = (UiControlView) oControl;
                    UiControlElementDataVector controlEls = control.getUiControlElements();
                    if (controlEls != null) {
                        for (Object oControlElement : controlEls) {
                            UiControlElementData ce = (UiControlElementData) oControlElement;
                            ce.setValue(null);
                        }
                    }
                }
            }
        }
    }
}
