package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UiControlData;
import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.service.api.value.UiControlElementDataVector;
import com.cleanwise.service.api.value.UiControlView;

import java.util.HashSet;

public class UiControlViewWrapper {

    private UiControlView mControlView;

    public UiControlViewWrapper(UiControlView pControlView) {
        this.mControlView = pControlView;
    }

    public UiControlData getUiControlData() {
        return mControlView.getUiControlData();
    }

    public void setUiControlData(UiControlData uiControlData) {
        this.mControlView.setUiControlData(uiControlData);
    }

    public UiControlElementDataVector getUiControlElements() {
        return mControlView.getUiControlElements();
    }

    public void setUiControlElements(UiControlElementDataVector uiControlElements) {
        mControlView.setUiControlElements(uiControlElements);
    }

    public UiControlView getControlView() {
        return mControlView;
    }

    public void setControlView(UiControlView pControlView) {
        this.mControlView = pControlView;
    }

    public UiControlElementDataWrapper getUiControlElementWrapper(String pName) {
        return new UiControlElementDataWrapper(getUiControlElement(pName));
    }

    public UiControlElementData getUiControlElement(String pName) {

        UiControlElementData controlElement = UiControlElementData.createValue();

        UiControlElementDataVector uiControlElements = mControlView.getUiControlElements();
        HashSet<String> controlElementNames = Utility.toShortDesc(uiControlElements);
        if (Utility.isSet(pName) && !controlElementNames.contains(pName)) {
            controlElement.setShortDesc(pName);
            uiControlElements.add(controlElement);
        } else if (Utility.isSet(pName)) {
            for (Object oControlElement : uiControlElements) {
                controlElement = (UiControlElementData) oControlElement;
                if (pName.equals(controlElement.getShortDesc())) {
                    return controlElement;
                }
            }
        }

        return controlElement;

    }

}
