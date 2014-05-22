package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *  Description of the Class
 *
 *@author     tbesser
 *@created    August 27, 2001
 */
public class AccountConfigForm extends ActionForm {
    
    private String mCatalogId = "";
    private String mOldCatalogId = "";

    /**
     *  Sets the CatalogId attribute of the AccountConfigForm object
     *
     *@param  pCatalogId  The new CatalogId value
     */
    public void setCatalogId(String pCatalogId) {
        this.mCatalogId = pCatalogId;
    }

    /**
     *  Gets the CatalogId attribute of the AccountConfigForm object
     *
     *@return    The CatalogId value
     */
    public String getCatalogId() {
        return this.mCatalogId;
    }

    /**
     *  Sets the OldCatalogId attribute of the AccountConfigForm object
     *
     *@param  pOldCatalogId  The new OldCatalogId value
     */
    public void setOldCatalogId(String pOldCatalogId) {
        this.mOldCatalogId = pOldCatalogId;
    }

    /**
     *  Gets the OldCatalogId attribute of the AccountConfigForm object
     *
     *@return    The OldCatalogId value
     */
    public String getOldCatalogId() {
        return this.mOldCatalogId;
    }
}

