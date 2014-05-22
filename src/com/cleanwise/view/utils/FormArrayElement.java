package com.cleanwise.view.utils;

/**
 *  Title: FormArrayElement Description: Class for struts page use. Purpose:
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     durval
 *@created    August 16, 2001
 */

/*
 * Container bean for general name value array elements.
 */
public final class FormArrayElement {

    private String mLabel = "";
    private String mValue = "";


    /**
     *  Constructor for the FormArrayElement object
     */
    public FormArrayElement() {
        this.mLabel = "";
        this.mValue = "";
    }


    /**
     *  Constructor for the FormArrayElement object
     *
     *@param  pLabel  Description of Parameter
     *@param  pValue  Description of Parameter
     */
    public FormArrayElement(String pLabel, String pValue) {
        this.mLabel = pLabel;
        this.mValue = pValue;
    }


    /**
     *  Sets the Label attribute of the FormArrayElement object
     *
     *@param  pVal  The new Label value
     */
    public void setLabel(String pVal) {
        mLabel = pVal;
    }


    /**
     *  Sets the Value attribute of the FormArrayElement object
     *
     *@param  pVal  The new Value value
     */
    public void setValue(String pVal) {
        mValue = pVal;
    }


    /**
     *  Gets the Label attribute of the FormArrayElement object
     *
     *@return    The Label value
     */
    public String getLabel() {
        return mLabel;
    }


    /**
     *  Gets the Value attribute of the FormArrayElement object
     *
     *@return    The Value value
     */
    public String getValue() {
        return mValue;
    }


    /**
     *  <code>equals</code> method.
     *
     *@param  rhs  object being compared to.
     *@return      true if the value and lable attributes are identical.
     */
    public boolean equals(FormArrayElement rhs) {
        if (this == rhs) {
            return true;
        }
        return this.mLabel.equals(rhs.getLabel()) &&
                this.mValue.equals(rhs.getValue());
    }
    
    public boolean equals(Object rhs) { // for List.contains(o) to work
        if (rhs != null && rhs instanceof FormArrayElement)
        	return equals((FormArrayElement)rhs);
        else return false;
    }

}

