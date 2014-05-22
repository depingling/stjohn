package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

/**
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 13:50:54
 *
 */
public class StoreAccountConfigForm extends ActionForm {

    private String mCatalogId = "";
    private String mOldCatalogId = "";

    /**
     *  Sets the CatalogId attribute of the StoreAccountConfigForm object
     *
     *@param  pCatalogId  The new CatalogId value
     */
    public void setCatalogId(String pCatalogId) {
        this.mCatalogId = pCatalogId;
    }

    /**
     *  Gets the CatalogId attribute of the StoreAccountConfigForm object
     *
     *@return    The CatalogId value
     */
    public String getCatalogId() {
        return this.mCatalogId;
    }

    /**
     *  Sets the OldCatalogId attribute of the StoreAccountConfigForm object
     *
     *@param  pOldCatalogId  The new OldCatalogId value
     */
    public void setOldCatalogId(String pOldCatalogId) {
        this.mOldCatalogId = pOldCatalogId;
    }

    /**
     *  Gets the OldCatalogId attribute of the StoreAccountConfigForm object
     *
     *@return    The OldCatalogId value
     */
    public String getOldCatalogId() {
        return this.mOldCatalogId;
    }
}

