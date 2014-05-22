package com.cleanwise.view.forms;

/**
 *  Title: OrderGuidesMgrDetailForm Description: This is the Struts
 *  ActionForm class for user management page. Purpose: Strut support
 *  to search for distributors. Copyright: Copyright (c) 2001 Company:
 *  CleanWise, Inc.
 *
 *@author     veronika
 */

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;
import java.util.*;

/**
 *  Form bean for the user manager page.
 *
 *@author     veronika
 *@created    October 13, 2006
 */
public final class StoreSiteOrderGuideDetForm extends StorePortalBaseForm {

    // Items to be removed.
    private String [] mSelectItemsCollection = { " " };

    private OrderGuideInfoData mOrderGuideInfoData;
    private ArrayList mItemsCollection = null;
    private String mCatalogName;
    private String mContractId = new String("");
    private String mContractName = new String("");
    private String mViewMode = new String("viewbycatalog");

    private String searchField;
    private String searchType;

    /** Holds value of property usingCatalogsContract. */
    private boolean usingCatalogsContract;

    private int siteId;

    private boolean shareBuyerOrderGuides;

    private ItemViewVector mItemFilter;


    public ArrayList getOrderGuideItemCollection() {
        if ( null == mItemsCollection ) {
            mItemsCollection = new ArrayList();
        }
        return mItemsCollection;
    }

    public void setOrderGuideItemCollection(ArrayList pCol) {
        mItemsCollection = pCol;
    }

    public OrderGuideItemDescData getOrderGuideItemDesc(int idx) {

        if (mItemsCollection == null) {
            mItemsCollection = new ArrayList();
        }
        while (idx >= mItemsCollection.size()) {
            mItemsCollection.add(new OrderGuideItemDescData());
        }

        return (OrderGuideItemDescData) mItemsCollection.get(idx);
    }

    public String [] getSelectItems() {
        return mSelectItemsCollection;
    }

    public void setSelectItems(String [] pItemIds) {
        mSelectItemsCollection = pItemIds;
    }

    /**
     * Get the value of mCatalogName.
     * @return value of mCatalogName.
     */
    public String getCatalogName() {
        return mCatalogName;
    }

    /**
     * Set the value of mCatalogName.
     * @param v  Value to assign to mCatalogName.
     */
    public void setCatalogName(String  v) {
        this.mCatalogName = v;
    }

    /**
     * Get the value of mContractId.
     * @return value of mContractId.
     */
    public String getContractId() {
        return mContractId;
    }

    /**
     * Set the value of mContractId.
     * @param v  Value to assign to mContractId.
     */
    public void setContractId(String  v) {
        this.mContractId = v;
    }


    /**
     * Get the value of mContractName.
     * @return value of mContractName.
     */
    public String getContractName() {
        return mContractName;
    }

    /**
     * Set the value of mContractName.
     * @param v  Value to assign to mContractName.
     */
    public void setContractName(String  v) {
        this.mContractName = v;
    }


    /**
     * Get the value of mViewMode.
     * @return value of mViewMode.
     */
    public String getViewMode() {
        return mViewMode;
    }

    /**
     * Set the value of mViewMode.
     * @param v  Value to assign to mViewMode.
     */
    public void setViewMode(String  v) {
        this.mViewMode = v;
    }



    public StoreSiteOrderGuideDetForm() {
        mOrderGuideInfoData = OrderGuideInfoData.createValue();
    }

    /**
     *  Sets the OrderGuideInfoData attribute of the
     *  OrderGuidesMgrDetailForm object
     *
     *@param  pOrderGuideInfoData  The new OrderGuideInfoData value
     */
    public void setOrderGuideInfoData
        (OrderGuideInfoData pOrderGuideInfoData) {
        mItemsCollection = new ArrayList();
        OrderGuideItemDescDataVector ogidDV = pOrderGuideInfoData.getOrderGuideItems();
        for(Iterator iter = ogidDV.iterator(); iter.hasNext();){
          OrderGuideItemDescData ogidD = (OrderGuideItemDescData) iter.next();
          mItemsCollection.add(ogidD.clone());
        }
        //oriD.s
        this.mOrderGuideInfoData = pOrderGuideInfoData;
    }

    /**
     *  Gets the OrderGuideInfoData attribute of the
     *  OrderGuidesMgrDetailForm object
     *
     *@return    The OrderGuideInfoData value
     */
    public OrderGuideInfoData getOrderGuideInfoData() {
        return mOrderGuideInfoData;
    }


    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        mSelectItemsCollection = new String[0];
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

    /** Getter for property usingCatalogsContract.
     * @return Value of property usingCatalogsContract.
     *
     */
    public boolean isUsingCatalogsContract() {
        return this.usingCatalogsContract;
    }

    /** Setter for property usingCatalogsContract.
     * @param usingCatalogsContract New value of property usingCatalogsContract.
     *
     */
    public void setUsingCatalogsContract(boolean usingCatalogsContract) {
        this.usingCatalogsContract = usingCatalogsContract;
    }

    public void setShareBuyerOrderGuides(boolean val) {
      this.shareBuyerOrderGuides = val;
    }

    public boolean getShareBuyerOrderGuides() {
      return this.shareBuyerOrderGuides;
    }

    public int getSiteId() {
      return this.siteId;
    }

    public void setSiteId(int val) {
      this.siteId = val;
    }

    public String getSearchField() {
        return this.searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchType() {
        return this.searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public ItemViewVector getItemFilter() {return mItemFilter;}
    public void setItemFilter(ItemViewVector pVal) {
      mItemFilter = pVal;
      if (pVal != null) {
        mSelectItemsCollection = new String[pVal.size()];
        for (int i=0; i<pVal.size(); i++) {
          ItemView item = (ItemView)pVal.get(i);
          mSelectItemsCollection[i] = "" + item.getItemId();
        }
      }
    }

}

