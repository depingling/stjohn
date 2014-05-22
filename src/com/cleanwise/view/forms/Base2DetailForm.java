package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *  Form bean, supporting methods to get and set the following properties:
 *  <ul>
 *    <li> Name1
 *    <li> Name2
 *    <li> EmailAddress
 *    <li> Phone
 *    <li> Fax
 *    <li> StreetAddr1
 *    <li> StreetAddr2
 *    <li> StreetAddr3
 *    <li> StreetAddr4
 *    <li> City
 *    <li> StateOrProv
 *    <li> PostalCode
 *    <li> Country
 *  </ul>
 *
 *
 *@author     durval
 *@created    August 8, 2001
 */
public class Base2DetailForm extends BaseDetailForm {

    private String mEmailAddress = "";
    private String mName1 = "";
    private String mName2 = "";
    private String mPhone = "";
    private String mFax = "";
    private String mStreetAddr1 = "";
    private String mStreetAddr2 = "";
    private String mStreetAddr3 = "";
    private String mStreetAddr4 = "";
    private String mCountry = "";
    private String mCounty = "";
    private String mStateOrProv = "";
    private String mPostalCode = "";
    private String mCity = "";
    private String mComments = "";


    /**
     *  Sets the Comments attribute of the Base2DetailForm object
     *
     *@param  mComments  The new Comments value
     */
    public void setComments(String mComments) {
        this.mComments = mComments;
    }


    /**
     *  Sets the City attribute of the Base2DetailForm object
     *
     *@param  pCity  The new City value
     */
    public void setCity(String pCity) {
        this.mCity = pCity;
    }


    /**
     *  Sets the StreetAddr2 attribute of the Base2DetailForm object
     *
     *@param  mStreetAddr2  The new StreetAddr2 value
     */
    public void setStreetAddr2(String mStreetAddr2) {
        this.mStreetAddr2 = mStreetAddr2;
    }


    /**
     *  Sets the StreetAddr1 attribute of the Base2DetailForm object
     *
     *@param  mStreetAddr1  The new StreetAddr1 value
     */
    public void setStreetAddr1(String mStreetAddr1) {
        this.mStreetAddr1 = mStreetAddr1;
    }


    /**
     *  Sets the Fax attribute of the Base2DetailForm object
     *
     *@param  mFax  The new Fax value
     */
    public void setFax(String mFax) {
        this.mFax = mFax;
    }


    /**
     *  Sets the Phone attribute of the Base2DetailForm object
     *
     *@param  mPhone  The new Phone value
     */
    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }


    /**
     *  Sets the StreetAddr3 attribute of the Base2DetailForm object
     *
     *@param  mStreetAddr3  The new StreetAddr3 value
     */
    public void setStreetAddr3(String mStreetAddr3) {
        this.mStreetAddr3 = mStreetAddr3;
    }

    /**
     *  Sets the StreetAddr4 attribute of the Base2DetailForm object
     *
     *@param  mStreetAddr4  The new StreetAddr3 value
     */
    public void setStreetAddr4(String mStreetAddr4) {
        this.mStreetAddr4 = mStreetAddr4;
    }


    /**
     *  Sets the County attribute of the Base2DetailForm object
     *
     *@param  mCounty  The new Country value
     */
    public void setCounty(String mCounty) {
        this.mCounty = mCounty;
    }

    /**
     *  Sets the Country attribute of the Base2DetailForm object
     *
     *@param  mCountry  The new Country value
     */
    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }


    /**
     *  Sets the StateOrProv attribute of the Base2DetailForm object
     *
     *@param  mStateOrProv  The new StateOrProv value
     */
    public void setStateOrProv(String mStateOrProv) {
        this.mStateOrProv = mStateOrProv;
    }


    /**
     *  Sets the PostalCode attribute of the Base2DetailForm object
     *
     *@param  mPostalCode  The new PostalCode value
     */
    public void setPostalCode(String mPostalCode) {
        this.mPostalCode = mPostalCode;
    }


    /**
     *  Sets the Name1 attribute of the Base2DetailForm object
     *
     *@param  pname1  The new Name1 value
     */
    public void setName1(String pname1) {
        this.mName1 = pname1;
    }


    /**
     *  Sets the Name2 attribute of the Base2DetailForm object
     *
     *@param  pname2  The new Name2 value
     */
    public void setName2(String pname2) {
        this.mName2 = pname2;
    }


    /**
     *  Sets the mEmailAddress attribute of the Base2DetailForm object
     *
     *@param  pEmail  The new EmailAddress value
     */
    public void setEmailAddress(String pEmail) {
    	if(pEmail!=null) {
        this.mEmailAddress = pEmail.trim();
    	}
    }


    /**
     *  Gets the Comments attribute of the Base2DetailForm object
     *
     *@return    The Comments value
     */
    public String getComments() {
	if ( null == mComments ) {
	    return "";
	}
        return mComments;
    }


    /**
     *  Getsthe City attribute of the Base2DetailForm object
     *
     *@return    The City value
     */
    public String getCity() {
	if ( null == mCity ) {
	    return "";
	}
        return mCity;
    }


    /**
     *  Gets the StreetAddr2 attribute of the Base2DetailForm object
     *
     *@return    The StreetAddr2 value
     */
    public String getStreetAddr2() {
	if ( null == mStreetAddr2 ) {
	    return "";
	}
        return mStreetAddr2;
    }


    /**
     *  Gets the StreetAddr1 attribute of the Base2DetailForm object
     *
     *@return    The StreetAddr1 value
     */
    public String getStreetAddr1() {
	if ( null == mStreetAddr1 ) {
	    return "";
	}
        return mStreetAddr1;
    }


    /**
     *  Gets the Fax attribute of the Base2DetailForm object
     *
     *@return    The Fax value
     */
    public String getFax() {
	if ( null == mFax ) {
	    return "";
	}
        return mFax;
    }


    /**
     *  Gets the Phone attribute of the Base2DetailForm object
     *
     *@return    The Phone value
     */
    public String getPhone() {
	if ( null == mPhone ) {
	    return "";
	}
        return mPhone;
    }


    /**
     *  Gets the StreetAddr3 attribute of the Base2DetailForm object
     *
     *@return    The StreetAddr3 value
     */
    public String getStreetAddr3() {
	if ( null == mStreetAddr3 ) {
	    return "";
	}
        return mStreetAddr3;
    }

    /**
     *  Gets the StreetAddr4 attribute of the Base2DetailForm object
     *
     *@return    The StreetAddr4 value
     */
    public String getStreetAddr4() {
	if ( null == mStreetAddr4 ) {
	    return "";
	}
        return mStreetAddr4;
    }


    /**
     *  Gets the County attribute of the Base2DetailForm object
     *
     *@return    The County value
     */
    public String getCounty() {
	if ( null == mCounty ) {
	    return "";
	}
        return mCounty;
    }


    /**
     *  Gets the Country attribute of the Base2DetailForm object
     *
     *@return    The Country value
     */
    public String getCountry() {
	if ( null == mCountry ) {
	    return "";
	}
        return mCountry;
    }

    /**
     *  Gets the StateOrProv attribute of the Base2DetailForm object
     *
     *@return    The StateOrProv value
     */
    public String getStateOrProv() {
	if ( null == mStateOrProv ) {
	    return "";
	}
        return mStateOrProv;
    }


    /**
     *  Gets the PostalCode attribute of the Base2DetailForm object
     *
     *@return    The PostalCode value
     */
    public String getPostalCode() {
	if ( null == mPostalCode ) {
	    return "";
	}
        return mPostalCode;
    }


    /**
     *  Gets the Name1 attribute of the Base2DetailForm object
     *
     *@return    The Name1 value
     */
    public String getName1() {
	if ( null == mName1 ) {
	    return "";
	}
        return mName1;
    }


    /**
     *  Gets the Name2 attribute of the Base2DetailForm object
     *
     *@return    The Name2 value
     */
    public String getName2() {
	if ( null == mName2 ) {
	    return "";
	}
        return mName2;
    }


    /**
     *  Gets the EmailAddress attribute of the Base2DetailForm object
     *
     *@return    The EmailId value
     */
    public String getEmailAddress() {
	if ( null == mEmailAddress ) {
	    return "";
	}
        return mEmailAddress;
    }

}

