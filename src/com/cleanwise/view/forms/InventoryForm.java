package com.cleanwise.view.forms;

/**
 *  Title: OrderGuidesMgrDetailForm Description: This is the Struts
 *  ActionForm class for user management page. Purpose: Strut support
 *  to search for distributors. Copyright: Copyright (c) 2001 Company:
 *  CleanWise, Inc.
 *
 *@author     durval
 */

import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.wrapper.SiteInventoryConfigViewWrapper;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *  Form bean for the user manager page.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public final class InventoryForm extends ActionForm {

    private SiteInventoryConfigViewVector mInvItems = null;
    
    public SiteInventoryConfigViewVector getInventoryItems() {
        if ( null == mInvItems ) {
            mInvItems = new SiteInventoryConfigViewVector();
        } 
        return mInvItems;
    }

    public void setInventoryItems(SiteInventoryConfigViewVector pCol) {
        setInventoryItems(pCol, true);
    }

    public void setInventoryItems
    (SiteInventoryConfigViewVector pCol, boolean forceSort) {
        mInvItems = pCol;
	if ( null != mInvItems && forceSort) {
	    mInvItems.sort("ItemSku");
	}
    }
///////////////////////////////////////
    private SiteInventoryConfigViewVector mInvItemsCopy = null;
    
    public SiteInventoryConfigViewVector getInvItemsCopy() {
        if ( null == mInvItemsCopy ) {
            mInvItemsCopy = new SiteInventoryConfigViewVector();
        } 
        return mInvItemsCopy;
    }

    public void setInvItemsCopy
	(SiteInventoryConfigViewVector pCol) {
        mInvItemsCopy = pCol;
	if ( null != mInvItemsCopy ) {
	    mInvItemsCopy.sort("ItemSku");
	}
    }
///////////////////////////////////////
    public SiteInventoryConfigView getInventoryItem(int idx) {

        if (mInvItems == null) {
            mInvItems = new SiteInventoryConfigViewVector();
        }
        while (idx >= mInvItems.size()) {
            mInvItems.add(SiteInventoryConfigView.createValue());
        }    
        
        return (SiteInventoryConfigView) mInvItems.get(idx);
    }

    public SiteInventoryConfigView getInventoryItemWrapper(int idx) {
        return new SiteInventoryConfigViewWrapper(getInventoryItem(idx));
    }

    public InventoryForm() { }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setInventoryItems( null );
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

}

