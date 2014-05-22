/**
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;


/**
 *  Form bean for the user manager page.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public final class BSCDetailForm extends BaseDetailForm {

    private String mFaxPhoneNumber;
    public String getFaxPhoneNumber() { return this.mFaxPhoneNumber; }
    public void setFaxPhoneNumber(String v) { this.mFaxPhoneNumber = v; }

    private String mDescription;

    /**
     * Holds value of property storeId.
     */
    private String storeId;
    public String getDescription() { return this.mDescription; }
    public void setDescription(String v) { this.mDescription = v; }
    
    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        return;
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // Validation happens in the logic bean.
        return null;
    }

    /**
     * Getter for property storeId.
     * @return Value of property storeId.
     */
    public String getStoreId() {

        return this.storeId;
    }

    /**
     * Setter for property storeId.
     * @param storeId New value of property storeId.
     */
    public void setStoreId(String storeId) {

        this.storeId = storeId;
    }

    
    
}

