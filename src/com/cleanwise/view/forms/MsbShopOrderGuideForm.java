package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import java.math.BigDecimal;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;

/**
 *  Form bean, supporting methods for MSB order guide shopping. properties:
 *  <ul>
 *    <li> orderGuideName
 *    <li> orderGuideId
 *    <li> siteList
 *  </ul>
 *
 *
 *@author     durval
 *@created    October 10, 2001
 */
public class MsbShopOrderGuideForm extends ActionForm {

    private String[] mSitesSelected = {" "};
    private String mOrderGuideName = "";
    private int mOrderGuideId = 0;
    private int mCatalogId = 0;
    private int mUserId;
    private ArrayList mItemsQty = null;
    private String mMessage = "";
    
    private int mSiteId;
    private String mOperation;
    
    /**
     * Get the value of Operation.
     * @return value of Operation.
     */
    public String getOperation() {
	return mOperation;
    }
    
    /**
     * Set the value of Operation.
     * @param v  Value to assign to Operation.
     */
    public void setOperation(String  v) {
	this.mOperation = v;
    }
    
    
    /**
     * Get the value of SiteId.
     * @return value of SiteId.
     */
    public int getSiteId() {
	return mSiteId;
    }
    
    /**
     * Set the value of SiteId.
     * @param v  Value to assign to SiteId.
     */
    public void setSiteId(int  v) {
	this.mSiteId = v;
    }
    
    /**
     * Get the value of Message.
     * @return value of Message.
     */
    public String getMessage() {
        return mMessage;
    }
    
    /**
     * Set the value of Message.
     * @param v  Value to assign to Message.
     */
    public void setMessage(String  v) {
        this.mMessage = v;
    }
    private String mCostCenterName;
    
    /**
     * Get the value of CostCenterName.
     * @return value of CostCenterName.
     */
    public String getCostCenterName() {
        return mCostCenterName;
    }
    
    /**
     * Set the value of CostCenterName.
     * @param v  Value to assign to CostCenterName.
     */
    public void setCostCenterName(String  v) {
        this.mCostCenterName = v;
    }

    BigDecimal ItemAmountsTotal;
    
    /**
     * Get the value of ItemAmountsTotal.
     * @return value of ItemAmountsTotal.
     */
    public BigDecimal getItemAmtTotal() {
        return ItemAmountsTotal;
    }
    
    /**
     * Set the value of ItemAmountsTotal.
     * @param v  Value to assign to ItemAmountsTotal.
     */
    public void setItemAmtTotal(BigDecimal  v) {
        this.ItemAmountsTotal = v;
    }
    
    private BigDecimal mTotal;
    
    /**
     * Get the value of Total.
     * @return value of Total.
     */
    public BigDecimal getTotal() {
        if ( null == mTotal ) {
            return new BigDecimal(0);
        }
        return mTotal;
    }
    
    /**
     * Set the value of Total.
     * @param v  Value to assign to Total.
     */
    public void setTotal(BigDecimal  v) {
        this.mTotal = v;
    }
    
    private BigDecimal mFreightAmt;
    
    /**
     * Get the value of FreightAmt.
     * @return value of FreightAmt.
     */
    public BigDecimal getFreightAmt() {
        if ( null == mFreightAmt ) {
            return new BigDecimal(0);
        }
        return mFreightAmt;
    }
    
    /**
     * Set the value of FreightAmt.
     * @param v  Value to assign to FreightAmt.
     */
    public void setFreightAmt(BigDecimal  v) {
        this.mFreightAmt = v;
    }
    
    /* Properties to be displayed when an order is being placed. */
    private int mCostCenterId = 0;
    private String mPoNumber = "";
    private String mComments = "";
    private SiteData mCurrentSite = SiteData.createValue();

    public void setSite(SiteData v) {
        mCurrentSite = v;
    }

    public SiteData getSite() {
        return mCurrentSite;
    }

    public void setComments(String v) {mComments = v;}
    public String getComments() {return mComments;}

    public void setPoNumber(String v) {
        mPoNumber = v;
    }
    public String getPoNumber() {
        return mPoNumber;
    }

    public void setCostCenterId(int v) {
        mCostCenterId = v;
    }
    public int getCostCenterId() {
        return mCostCenterId;
    }
    
    public ArrayList getItemQtyCollection() {
        if ( null == mItemsQty ) {
            mItemsQty = new ArrayList();
        }
        return mItemsQty;
    }

    public void setItemQtyCollection(ArrayList pCol) {
        mItemsQty = pCol;
    }

    public FormArrayElement getItemQty(int idx) {

        if (mItemsQty == null) {
            mItemsQty = new ArrayList();
        }
        while (idx >= mItemsQty.size()) {
            mItemsQty.add(new FormArrayElement("N", "0"));
        }    
        
        return (FormArrayElement) mItemsQty.get(idx);
    }

    /**
     * Get the value of UserId.
     * @return value of UserId.
     */
    public int getUserId() {
        return mUserId;
    }
    
    /**
     * Set the value of UserId.
     * @param v  Value to assign to UserId.
     */
    public void setUserId(int  v) {
        this.mUserId = v;
    }
    
    
    /**
     * Get the value of CatalogId.
     * @return value of CatalogId.
     */
    public int getCatalogId() {
        return mCatalogId;
    }
    
    /**
     * Set the value of CatalogId.
     * @param v  Value to assign to CatalogId.
     */
    public void setCatalogId(int  v) {
        this.mCatalogId = v;
    }
    

    /**
     *  Sets the SitesSelected attribute of the MsbShopOrderGuideForm object
     *
     *@param  v  The new SitesSelected value
     */
    public void setSitesSelected(String[] v) {
        mSitesSelected = v;
    }


    /**
     *  Sets the SitesSelected attribute of the MsbShopOrderGuideForm object
     *
     *@return    Description of the Returned Value
     */
    public String[] getSitesSelected() {
        return mSitesSelected;
    }


    /**
     *  Set the value of OrderGuideName.
     *
     *@param  v  Value to assign to OrderGuideName.
     */
    public void setOrderGuideName(String v) {
        this.mOrderGuideName = v;
    }


    /**
     *  Set the value of OrderGuideId.
     *
     *@param  v  Value to assign to OrderGuideId.
     */
    public void setOrderGuideId(int v) {
        this.mOrderGuideId = v;
    }


    /**
     *  Get the value of OrderGuideName.
     *
     *@return    value of OrderGuideName.
     */
    public String getOrderGuideName() {
        return mOrderGuideName;
    }


    /**
     *  Get the value of OrderGuideId.
     *
     *@return    value of OrderGuideId.
     */
    public int getOrderGuideId() {
        return mOrderGuideId;
    }

}

