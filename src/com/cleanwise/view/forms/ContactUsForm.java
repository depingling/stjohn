package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *  Form bean, supporting methods to get and set the following processing
 *  properties:
 *  <ul>
 *    <li> firstName
 *    <li> lastName
 *    <li> title
 *    <li> companyName
 *    <li> industry
 *    <li> address1
 *    <li> address2
 *    <li> city
 *    <li> state
 *    <li> zip
 *    <li> phone
 *    <li> email
 *
 *    <li> fax
 *    <li> fedstrip
 *    <li> facility
 *    <li> help
 *    <li> comments
 * 
 *  </ul>
 *
 *
 *@author     durval
 *@created    October 10, 2001
 */
public class ContactUsForm extends ActionForm {

    private String mFirstName = "";

    private String mLastName = "";

    private String mTitle = "";
	
	private String mCompanyName = "";
	
	private String mIndustry = "";
	
	private String mAddress1 = "";
	
	private String mAddress2 = "";
	
	private String mCity = "";
	
	private String mState = "";
	
	private String mZip = "";
	
	private String mPhone = "";
	
	private String mToEmail = "";

	private String mFromEmail = "";
   
    private String mAction = "";
	
	private String mFax = "";
	
	private String mFedstrip = "";
	
	private String mFacility = "";
	
	private String mHelp = "";
	
	private String mComments = "";



    /**
     *  Sets the FirstName attribute of the ContactUsForm object
     *
     *@param  v  The new FirstName value
     */
    public void setFirstName(String v) {
        mFirstName = v;
    }


    /**
     *  Sets the LastName attribute of the ContactUsForm object
     *
     *@param  v  The new LastName value
     */
    public void setLastName(String v) {
        mLastName = v;
    }


    /**
     *  Sets the Title attribute of the ContactUsForm object
     *
     *@param  v  The new Title value
     */
    public void setTitle(String v) {
        mTitle = v;
    }


    /**
     *  Sets the CompanyName attribute of the ContactUsForm object
     *
     *@param  v  The new CompanyName value
     */
    public void setCompanyName(String v) {
        mCompanyName = v;
    }


    /**
     *  Sets the Industry attribute of the ContactUsForm object
     *
     *@param  v  The new Industry value
     */
    public void setIndustry(String v) {
        mIndustry = v;
    }
	
	
	/**
     *  Sets the Address1 attribute of the ContactUsForm object
     *
     *@param  v  The new Address1 value
     */
    public void setAddress1(String v) {
        mAddress1 = v;
    }
	
	
	/**
     *  Sets the Address2 attribute of the ContactUsForm object
     *
     *@param  v  The new Address2 value
     */
    public void setAddress2(String v) {
        mAddress2 = v;
    }
	
	/**
     *  Sets the City attribute of the ContactUsForm object
     *
     *@param  v  The new City value
     */
    public void setCity(String v) {
        mCity = v;
    }
	
	
	/**
     *  Sets the State attribute of the ContactUsForm object
     *
     *@param  v  The new State value
     */
    public void setState(String v) {
        mState = v;
    }
	
	
	
	/**
     *  Sets the Zip attribute of the ContactUsForm object
     *
     *@param  v  The new Zip value
     */
    public void setZip(String v) {
        mZip = v;
    }
	
	
	/**
     *  Sets the Phone attribute of the ContactUsForm object
     *
     *@param  v  The new Phone value
     */
    public void setPhone(String v) {
        mPhone = v;
    }
	
	
	/**
     *  Sets the ToEmail attribute of the ContactUsForm object
     *
     *@param  v  The new ToEmail value
     */
    public void setToEmail(String v) {
        mToEmail = v;
    }
	
	
	/**
     *  Sets the FromEmail attribute of the ContactUsForm object
     *
     *@param  v  The new FromEmail value
     */
    public void setFromEmail(String v) {
        mFromEmail = v;
    }
	
	
	
	/**
     *  Sets the Action attribute of the ContactUsForm object
     *
     *@param  v  The new Action value
     */
    public void setAction(String v) {
        mAction = v;
    }
	
	
	/**
     *  Sets the Fax attribute of the ContactUsForm object
     *
     *@param  v  The new Fax value
     */
    public void setFax(String v) {
        mFax = v;
    }
	
	
	/**
     *  Sets the Fedstrip attribute of the ContactUsForm object
     *
     *@param  v  The new Fedstrip value
     */
    public void setFedstrip(String v) {
        mFedstrip = v;
    }
	
	
	/**
     *  Sets the Facility attribute of the ContactUsForm object
     *
     *@param  v  The new Facility value
     */
    public void setFacility(String v) {
        mFacility = v;
    }
	
	
	/**
     *  Sets the Help attribute of the ContactUsForm object
     *
     *@param  v  The new Help value
     */
    public void setHelp(String v) {
        mHelp = v;
    }
	
	
	/**
     *  Sets the Comments attribute of the ContactUsForm object
     *
     *@param  v  The new Comments value
     */
    public void setComments(String v) {
        mComments = v;
    }


	
	
	
    /**
     *  Gets the FirstName attribute of the ContactUsForm object
     *
     *@return    The FirstName value
     */
    public String getFirstName() {
        return mFirstName;
    }


    /**
     *  Gets the LastName attribute of the ContactUsForm object
     *
     *@return    The LastName value
     */
    public String getLastName() {
        return mLastName;
    }


    /**
     *  Gets the Title attribute of the ContactUsForm object
     *
     *@return    The Title value
     */
    public String getTitle() {
        return mTitle;
    }


    /**
     *  Gets the CompanyName attribute of the ContactUsForm object
     *
     *@return    The CompanyName value
     */
    public String getCompanyName() {
        return mCompanyName;
    }


    /**
     *  Gets the Industry attribute of the ContactUsForm object
     *
     *@return    The Industry value
     */
    public String getIndustry() {
        return mIndustry;
    }
	
	
	/**
     *  Gets the Address1 attribute of the ContactUsForm object
     *
     *@return    The Address1 value
     */
    public String getAddress1() {
        return mAddress1;
    }
	
	
	/**
     *  Gets the Address2 attribute of the ContactUsForm object
     *
     *@return    The Address2 value
     */
    public String getAddress2() {
        return mAddress2;
    }
	
	
	/**
     *  Gets the City attribute of the ContactUsForm object
     *
     *@return    The City value
     */
    public String getCity() {
        return mCity;
    }
	
	
	/**
     *  Gets the State attribute of the ContactUsForm object
     *
     *@return    The State value
     */
    public String getState() {
        return mState;
    }
	
	
	/**
     *  Gets the Zip attribute of the ContactUsForm object
     *
     *@return    The Zip value
     */
    public String getZip() {
        return mZip;
    }
	
	
	/**
     *  Gets the Phone attribute of the ContactUsForm object
     *
     *@return    The Phone value
     */
    public String getPhone() {
        return mPhone;
    }
	
	
	/**
     *  Gets the ToEmail attribute of the ContactUsForm object
     *
     *@return    The ToEmail value
     */
    public String getToEmail() {
        return mToEmail;
    }
	
	
	/**
     *  Gets the FromEmail attribute of the ContactUsForm object
     *
     *@return    The FromEmail value
     */
    public String getFromEmail() {
        return mFromEmail;
    }
	
	
	
	/**
     *  Gets the Action attribute of the ContactUsForm object
     *
     *@return    The Action value
     */
    public String getAction() {
        return mAction;
    }
	
		
	
	/**
     *  Gets the Fax attribute of the ContactUsForm object
     *
     *@return    The Fax value
     */
    public String getFax() {
        return mFax;
    }
	
	
	/**
     *  Gets the Fedstrip attribute of the ContactUsForm object
     *
     *@return    The Fedstrip value
     */
    public String getFedstrip() {
        return mFedstrip;
    }
	
	
	/**
     *  Gets the Facility attribute of the ContactUsForm object
     *
     *@return    The Facility value
     */
    public String getFacility() {
        return mFacility;
    }
	
	
	/**
     *  Gets the Help attribute of the ContactUsForm object
     *
     *@return    The Help value
     */
    public String getHelp() {
        return mHelp;
    }
	
	
	/**
     *  Gets the Comments attribute of the ContactUsForm object
     *
     *@return    The Comments value
     */
    public String getComments() {
        return mComments;
    }
	
	
		
	

}

