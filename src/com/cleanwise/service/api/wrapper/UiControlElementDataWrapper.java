package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UiControlElementData;
import com.cleanwise.view.taglibs.UiSelectTag;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class UiControlElementDataWrapper {

    private UiControlElementData mUiControElement;

    public UiControlElementDataWrapper(UiControlElementData pUiControElement) {
        this.mUiControElement = pUiControElement;
    }

    public void setValues(String[] pValues) {
        String value = null;
        if (pValues != null) {
            value = "";
            for (String sValue : pValues) {
                if (Utility.isSet(value)) {
                    value += UiSelectTag.MULTIPLE_DELIM;
                }
                value += sValue;
            }
        }
        mUiControElement.setValue(value);
    }

    public String[] getValues() {
        ArrayList<String> values = new ArrayList<String>();
        String val = mUiControElement.getValue();
        if (Utility.isSet(val)) {
            StringTokenizer st = new StringTokenizer(val, UiSelectTag.MULTIPLE_DELIM);
            while (st.hasMoreElements()) {
                values.add((String) st.nextElement());
            }
        }
        return values.toArray(new String[0]);
    }

    public String getValue() {
        return this.mUiControElement.getValue();
    }

    public void setValue(String pValue) {
        this.mUiControElement.setValue(pValue);
    }

    public String getShortDesc() {
        return this.mUiControElement.getShortDesc();
    }

    public void setShortDesc(String pShortDesc) {
        this.mUiControElement.setShortDesc(pShortDesc);
    }

    public String getTypeCd() {
        return this.mUiControElement.getTypeCd();

    }

    public void setTypeCd(String pTypeCd) {
        this.mUiControElement.setTypeCd(pTypeCd);

    }
}

