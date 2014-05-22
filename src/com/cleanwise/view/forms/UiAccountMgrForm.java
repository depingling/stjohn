package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.service.api.value.UiControlElementDataVector;
import com.cleanwise.service.api.value.UiControlView;
import com.cleanwise.service.api.value.UiControlViewVector;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;

public class UiAccountMgrForm extends ActionForm{

    private UiPageViewWrapper uiPage;

    public UiPageViewWrapper getUiPage() {
        return uiPage;
    }

    public void setUiPage(UiPageViewWrapper uiPage) {
        this.uiPage = uiPage;
    }

     /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
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
                             ce.setValue("");
                         }
                     }
                 }
             }
         }
     }
	
}
