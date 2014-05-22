package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 *  <code>UIConfigData</code> value object used to carry the various screen
 *  options for a business entity. Both the store and account business entities
 *  can specify ui options.
 *
 *@author     dvieira
 *@created    November 8, 2001
 */
public class UIConfigData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4970154647959602431L;
    
    private String mTipsMsg = "";
    private String mMainMsg = "";
    private String mToolbarStyle = "";
    private String mLogo1 = "", mStoreLogo1;
    private String mLogo2 = "";
    private String mStyleSheet = "";
    private String pageTitle = "";    
    private String footerMsg = "";
    private String homePageButtonLable = "";
    private String customerServiceAlias = "";
    private String localeCd = "";
    private String mContactUsMsg = "";
    private String logo1StorageTypeCd = "";
    private String logo2StorageTypeCd = "";

    /**
     *  Sets the ToolbarStyle attribute of the UIConfigData object Known values
     *  are textOnly or textAndImages.
     *
     *@param  v  The new ToolbarStyle value
     */
    public void setToolbarStyle(String v) {
        if (v==null) v = "";
        mToolbarStyle = v;
    }


    /**
     *  Sets the TipsMsg attribute of the UIConfigData object
     *
     *@param  v  The new TipsMsg value
     */
    public void setTipsMsg(String v) {
        if (v==null) v = "";
        mTipsMsg = v;
    }


    /**
     *  Sets the MainMsg attribute of the UIConfigData object
     *
     *@param  v  The new MainMsg value
     */
    public void setMainMsg(String v) {
        if (v==null) v = "";
        mMainMsg = v;
    }


    /**
     *  Sets the ContactUsMsg attribute of the UIConfigData object
     *
     *@param  v  The new ContactUsMsg value
     */
    public void setContactUsMsg(String v) {
        if (v==null) v = "";
        mContactUsMsg = v;
    }


    /**
     *  Sets the Logo1 attribute of the UIConfigData object
     *
     *@param  v  The new Logo1 value
     */
    public void setLogo1(String v) {
        if (v==null) v = "";
        mLogo1 = v;
    }
    public void setStoreLogo1(String v) {
        if (v==null) v = "";
        mStoreLogo1 = v;
    }


    /**
     *  Sets the Logo2 attribute of the UIConfigData object
     *
     *@param  v  The new Logo2 value
     */
    public void setLogo2(String v) {
        if (v==null) v = "";
        mLogo2 = v;
    }


    /**
     *  Sets the StyleSheet attribute of the UIConfigData object
     *
     *@param  v  The new StyleSheet value
     */
    public void setStyleSheet(String v) {
        if (v==null) v = "";
        mStyleSheet = v;
    }


    /**
     *  Gets the ToolbarStyle attribute of the UIConfigData object
     *
     *@return    The ToolbarStyle value
     */
    public String getToolbarStyle() {
        return mToolbarStyle;
    }


    /**
     *  Gets the ToolbarStylePropName attribute of the UIConfigData object
     *
     *@return    The ToolbarStylePropName value
     */
    public String getToolbarStylePropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_TOOLBAR_STYLE;
    }


    /**
     *  Gets the TipsMsg attribute of the UIConfigData object
     *
     *@return    The TipsMsg value
     */
    public String getTipsMsg() {
        return mTipsMsg;
    }


    /**
     *  Gets the TipsMsgPropName attribute of the UIConfigData object
     *
     *@return    The TipsMsgPropName value
     */
    public String getTipsMsgPropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_TIPSMSG;
    }


    /**
     *  Gets the MainMsg attribute of the UIConfigData object
     *
     *@return    The MainMsg value
     */
    public String getMainMsg() {
        return mMainMsg;
    }


    /**
     *  Gets the MainMsgPropName attribute of the UIConfigData object
     *
     *@return    The MainMsgPropName value
     */
    public String getMainMsgPropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_MAINMSG;
    }


    /**
     *  Gets the ContactUsMsg attribute of the UIConfigData object
     *
     *@return    The ContactUsMsg value
     */
    public String getContactUsMsg() {
        return mContactUsMsg;
    }


    /**
     *  Gets the ContactUsPropName attribute of the UIConfigData object
     *
     *@return    The ContactUsPropName value
     */
    public String getContactUsMsgPropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_CONTACT_MSG;
    }


    /**
     *  Gets the Logo1 attribute of the UIConfigData object
     *
     *@return    The Logo1 value
     */
    public String getLogo1() {
        return mLogo1;
    }
    public String getStoreLogo1() {
        return mStoreLogo1;
    }


    /**
     *  Gets the Logo1PropName attribute of the UIConfigData object
     *
     *@return    The Logo1PropName value
     */
    public String getLogo1PropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO1;
    }


    /**
     *  Gets the Logo2 attribute of the UIConfigData object
     *
     *@return    The Logo2 value
     */
    public String getLogo2() {
        return mLogo2;
    }


    /**
     *  Gets the Logo2PropName attribute of the UIConfigData object
     *
     *@return    The Logo2PropName value
     */
    public String getLogo2PropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO2;
    }


    /**
     *  Gets the StyleSheet attribute of the UIConfigData object
     *
     *@return    The StyleSheet value
     */
    public String getStyleSheet() {
        return mStyleSheet;
    }


    /**
     *  Gets the StyleSheetPropName attribute of the UIConfigData object
     *
     *@return    The StyleSheetPropName value
     */
    public String getStyleSheetPropName() {
        return RefCodeNames.PROPERTY_TYPE_CD.UI_STYLESHEET;
    }


    /**
     *  Store the name value pair in the appropriate variable.
     *
     *@param  pLabel  name
     *@param  pValue  value
     *@param  pLocaleCd locale
     */
    public void populate(String pLabel, String pValue) {

        if (pLabel == null) {
            pLabel = "";
        }
        if (pValue == null) {
            pValue = "";
        }

        if (RefCodeNames.PROPERTY_TYPE_CD.UI_MAINMSG.equals(pLabel)) {
            mMainMsg = pValue;
        }
        if (RefCodeNames.PROPERTY_TYPE_CD.UI_TOOLBAR_STYLE.equals(pLabel)) {
            mToolbarStyle = pValue;
        }
        else if (RefCodeNames.PROPERTY_TYPE_CD.UI_TIPSMSG.equals(pLabel)) {
            mTipsMsg = pValue;
        }
        else if (RefCodeNames.PROPERTY_TYPE_CD.UI_CONTACT_MSG.equals(pLabel)) {
            mContactUsMsg = pValue;
        }
        else if (RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO1.equals(pLabel)) {
            mLogo1 = pValue;
        }
        else if (RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO2.equals(pLabel)) {
            mLogo2 = pValue;
        }
        else if (RefCodeNames.PROPERTY_TYPE_CD.UI_STYLESHEET.equals(pLabel)) {
            mStyleSheet = pValue;
        }else if (RefCodeNames.PROPERTY_TYPE_CD.UI_PAGE_TITLE.equals(pLabel)) {
            pageTitle = pValue;
        }else if (RefCodeNames.PROPERTY_TYPE_CD.UI_FOOTER.equals(pLabel)) {
            footerMsg = pValue;
        }else if (RefCodeNames.PROPERTY_TYPE_CD.UI_HOME_PAGE_BUTTON_LABEL.equals(pLabel)) {
            homePageButtonLable = pValue;
        }else if (RefCodeNames.PROPERTY_TYPE_CD.UI_CUST_SERVICE_ALIAS.equals(pLabel)) {
            customerServiceAlias = pValue;
        }
    }


    /**
     *  Provide a string representation of the object.
     *
     *@return    
     */
    public String toString() {
        return "[" + 
        "MainMsg=" + mMainMsg +
        ",TipsMsg=" + mTipsMsg +
        ",ContactUsMsg=" + mContactUsMsg +
        ", ToolbarStyle=" + mToolbarStyle +
        ", Logo1=" + mLogo1 +
        ", Logo2=" + mLogo2 +
        ", StyleSheet=" + mStyleSheet +
        ", PageTitle=" + pageTitle +
        ", FooterMsg=" + footerMsg +
        ", HomePageButtonLable=" + homePageButtonLable +
        ", LocaleCd=" + localeCd +
        ", logo1StorageTypeCd=" + logo1StorageTypeCd +
        ", logo2StorageTypeCd=" + logo2StorageTypeCd +
        "]";
    }

    /**
     * Getter for property pageTitle.
     * @return Value of property pageTitle.
     */
    public String getPageTitle() {
        return this.pageTitle;
    }
    

    /**
     *Returns the property name of the Page Title for storing in the database
     */
    public String getPageTitlePropName(){
        return RefCodeNames.PROPERTY_TYPE_CD.UI_PAGE_TITLE;
    }
    
    /**
     * Setter for property pageTitle.
     * @param pageTitle New value of property pageTitle.
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
    
    /**
     *Returns the property name of the footer message for storing in the database
     */
    public String getFooterMsgPropName(){
        return RefCodeNames.PROPERTY_TYPE_CD.UI_FOOTER;
    }
    
    /**
     * Getter for property footerMsg.
     * @return Value of property footerMsg.
     */
    public String getFooterMsg() {
        return this.footerMsg;
    }
    
    /**
     * Setter for property footerMsg.
     * @param footerMsg New value of property footerMsg.
     */
    public void setFooterMsg(String footerMsg) {
        this.footerMsg = footerMsg;
    }
    
    
    
     /**
     *Returns the property name of the HomePageButtonLabel for storing in the database
     */
    public String getHomePageButtonLabelName(){
        return RefCodeNames.PROPERTY_TYPE_CD.UI_HOME_PAGE_BUTTON_LABEL;
    }
    
    /**
     * Getter for property HomePageButtonLabel.
     */
    public String getHomePageButtonLabel() {
        return homePageButtonLable;
    }
    
    /**
     * Setter for property HomePageButtonLabel.
     */
    public void setHomePageButtonLabel(String val) {
        homePageButtonLable = val;
    }

     /**
     *Returns the property name of the customerServiceAlias for storing in the database
     */
    public String getCustomerServiceAliasName(){
        return RefCodeNames.PROPERTY_TYPE_CD.UI_CUST_SERVICE_ALIAS;
    }
    
    /**
     * Getter for property customerServiceAlias.
     */
    public String getCustomerServiceAlias() {
        return customerServiceAlias;
    }
    
    /**
     * Setter for property customerServiceAlias.
     */
    public void setCustomerServiceAlias(String val) {
        customerServiceAlias = val;
    }

    /**
     * Getter for property localeCd.
     */
    public String getLocaleCd() {
        return localeCd;
    }
    
    /**
     * Setter for property localeCd.
     */
    public void setLocaleCd(String val) {
        localeCd = val;
    }
    
    public String getLogo1StorageTypeCd() {
    	return logo1StorageTypeCd;
    }
    
    public void setLogo1StorageTypeCd(String val) {
    	logo1StorageTypeCd = val;
    }
    
    public String getLogo2StorageTypeCd() {
    	return logo2StorageTypeCd;
    }
    
    public void setLogo2StorageTypeCd(String val) {
    	logo2StorageTypeCd = val;
    }
}

