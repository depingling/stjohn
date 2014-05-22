package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class UiPageViewWrapper {

    private UiPageView mUiPageView;

    public UiPageViewWrapper(UiPageView pUiPageView) {
        this.mUiPageView = pUiPageView;
    }

    public UiData getUiData() {
        return mUiPageView.getUiData();
    }

    public void setUiData(UiData uiData) {
        mUiPageView.setUiData(uiData);
    }

    public UiPageData getUiPage() {
        return mUiPageView.getUiPage();
    }

    public void setUiPage(UiPageData uiPage) {
        mUiPageView.setUiPage(uiPage);
    }

    public UiControlViewVector getUiControls() {
        return mUiPageView.getUiControls();
    }

    public void setUiControls(UiControlViewVector uiControls) {
        mUiPageView.setUiControls(uiControls);
    }

    public UiControlViewWrapper getUiControlWrapper(String pName) {
         return new UiControlViewWrapper(getUiControl(pName));
    }

    public UiPageView getUiPageView() {
        return mUiPageView;
    }

    public void setUiPageView(UiPageView pUiPageView) {
        this.mUiPageView = pUiPageView;
    }

    public UiControlView getUiControl(String pName) {

        UiControlView control = UiControlView.createValue();

        UiControlViewVector uiControls = mUiPageView.getUiControls();
        HashSet<String> controlNames = Utility.toShortDesc(uiControls);
        if (Utility.isSet(pName) && !controlNames.contains(pName)) {
            control = Utility.createUiControl(mUiPageView.getUiPage(), pName, RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE);
            uiControls.add(control);
            return control;
        } else if (Utility.isSet(pName)) {
            for (Object oControl : uiControls) {
                control = (UiControlView) oControl;
                if (pName.equals(control.getUiControlData().getShortDesc())) {
                    return control;
                }
            }
        }

        return control;

    }

}
