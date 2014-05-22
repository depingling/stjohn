package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.value.UIConfigData;
/**
 *  Description of the Class
 *
 *@author     durval
 *@created    October 6, 2001
 */
public class UIConfigForm extends ActionForm {

    private UIConfigData mUIData;
    private int mBusEntityId;
    private FormFile mLogo1ImageFile;
    private FormFile mLogo2ImageFile;
    private String mLocaleCd = "";


    /**
     *  Constructor for the UIConfigForm object
     */
    public UIConfigForm() {
        mUIData = new UIConfigData();
    }


    /**
     *  Sets the Logo1ImageFile attribute of the UIConfigForm object
     *
     *@param  v  The new Logo1ImageFile value
     */
    public void setLogo1ImageFile(FormFile v) {
        mLogo1ImageFile = v;
    }


    /**
     *  Sets the Logo2ImageFile attribute of the UIConfigForm object
     *
     *@param  v  The new Logo2ImageFile value
     */
    public void setLogo2ImageFile(FormFile v) {
        mLogo2ImageFile = v;
    }


    /**
     *  Sets the BusEntityId attribute of the UIConfigForm object
     *
     *@param  v  The new BusEntityId value
     */
    public void setBusEntityId(int v) {
        mBusEntityId = v;
    }


    /**
     *  Sets the Config attribute of the UIConfigForm object
     *
     *@param  v  The new Config value
     */
    public void setConfig(UIConfigData v) {
        mUIData = v;
    }

    /**
     *  Sets the LocaleCd attribute of the UIConfigForm object
     *
     *@param  v  The new LocaleCd value
     */
    public void setLocaleCd(String v) {
        mLocaleCd = v;
    }

    /**
     *  Gets the Logo1ImageFile attribute of the UIConfigForm object
     *
     *@return    The Logo1ImageFile value
     */
    public FormFile getLogo1ImageFile() {
        return mLogo1ImageFile;
    }


    /**
     *  Gets the Logo2ImageFile attribute of the UIConfigForm object
     *
     *@return    The Logo2ImageFile value
     */
    public FormFile getLogo2ImageFile() {
        return mLogo2ImageFile;
    }


    /**
     *  Gets the BusEntityId attribute of the UIConfigForm object
     *
     *@return    The BusEntityId value
     */
    public int getBusEntityId() {
        return mBusEntityId;
    }


    /**
     *  Gets the Config attribute of the UIConfigForm object
     *
     *@return    The Config value
     */
    public UIConfigData getConfig() {
        return mUIData;
    }
    
    /**
     *  Gets the LocaleCd attribute of the UIConfigForm object
     *
     *@return    The LocaleCd value
     */
    public String getLocaleCd() {
        return mLocaleCd;
    }

    
}

