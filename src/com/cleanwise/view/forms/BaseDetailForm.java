package com.cleanwise.view.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 *  Form bean, supporting methods to get and set the following properties:
 *  <ul>
 *    <li> Id
 *    <li> Name
 *    <li> TypeCd
 *    <li> StatusCd
 *  </ul>
 *
 *
 *@author     durval
 *@created    August 8, 2001
 */
public class BaseDetailForm extends ValidatorForm {

    private String mId = "0";
    private String mTypeCd = "";
    private String mTypeDesc = "";
    private String mName = "";
    private String mStatusCd = "";
    private String mAction = "";


    /**
     *  Sets the Action attribute of the BaseDetailForm object
     *
     *@param  mAction  The new Action value
     */
    public void setAction(String mAction) {
        this.mAction = mAction;
    }


    /**
     *  <code>setId</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setId(String pVal) {
        this.mId = pVal;
    }
    public void setId(int pVal) {
        this.mId = String.valueOf(pVal);
    }


    /**
     *  Sets the TypeDesc attribute of the BaseDetailForm object
     *
     *@param  mTypeDesc  The new TypeDesc value
     */
    public void setTypeDesc(String mTypeDesc) {
        this.mTypeDesc = mTypeDesc;
    }


    /**
     *  <code>setTypeCd</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setTypeCd(String pVal) {
        this.mTypeCd = pVal;
    }


    /**
     *  <code>setStatusCd</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setStatusCd(String pVal) {
        this.mStatusCd = pVal;
    }


    /**
     *  <code>setName</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setName(String pVal) {
        this.mName = pVal;
    }


    /**
     *  Gets the Action attribute of the BaseDetailForm object
     *
     *@return    The Action value
     */
    public String getAction() {
	if ( null == mAction ) {
	    return "";
	}
        return mAction;
    }


    /**
     *  Gets the TypeDesc attribute of the BaseDetailForm object
     *
     *@return    The TypeDesc value
     */
    public String getTypeDesc() {
	if ( null == mTypeDesc ) {
	    return "";
	}
        return mTypeDesc;
    }


    /**
     *  <code>getId</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getId() {
	if ( null == mId ) {
	    return "";
	}
        return (this.mId);
    }

    /*
     *  <code>getIntId</code> , get the integer representation
     *  of the id stored.
     *
     *@return    a <code>int</code> value
     */
    public int getIntId() {
        if (null == mId) {
            return 0;
        }
        return Integer.parseInt(this.mId);
    }


    /**
     *  <code>getTypeCd</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getTypeCd() {
	if ( null == mTypeCd)  {
	    return "";
	}
        return (this.mTypeCd);
    }


    /**
     *  <code>getStatusCd</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getStatusCd() {
	if ( null == mStatusCd) {
	    return "";
	}
        return (this.mStatusCd);
    }


    /**
     *  <code>getName</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getName() {
	if ( null == mName ) {
	    return "";
	}
        return (this.mName);
    }

}

