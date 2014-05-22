package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.BusEntityFieldsData;
/**
 *  Description of the Class
 *
 *@author     durval
 *@created    October 6, 2001
 */
public class SiteFieldsConfigForm extends ActionForm {

    private BusEntityFieldsData mConfig = new BusEntityFieldsData();
    private int mBusEntityId;

    /**
     *  Sets the BusEntityId attribute of the UIConfigForm object
     *
     *@param  v  The new BusEntityId value
     */
    public void setBusEntityId(int v) {
        mBusEntityId = v;
    }
    /**
     *  Gets the BusEntityId attribute of the UIConfigForm object
     *
     *@return    The BusEntityId value
     */
    public int getBusEntityId() {
        return mBusEntityId;
    }

    public  BusEntityFieldsData getConfig() { 
        return mConfig;
    }

    public void setConfig(BusEntityFieldsData v) {
        mConfig = v;
    }


}

