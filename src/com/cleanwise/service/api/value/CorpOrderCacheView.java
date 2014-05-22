
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CorpOrderCacheView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import java.math.BigDecimal;


/**
 * <code>CorpOrderCacheView</code> is a ViewObject class for UI.
 */
public class CorpOrderCacheView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private Boolean mSpecificDatesFl;
    private Date mCutoffDate;
    private Integer mInventoryPeriod;
    private Boolean mUsePhysicalInventoryCart;
    private Integer mStoreId;
    private String mStoreType;
    private AccountData mAccount;
    private FiscalCalenderView mFiscalCalendar;
    private BigDecimal mAutoOrderFactor;
    private Integer mSiteId;
    private Integer mOrderGuideId;
    private UserData mUser;
    private HashMap mAccountInventoryItems;
    private HashMap mCatalogProducts;

    /**
     * Constructor.
     */
    public CorpOrderCacheView ()
    {
        mStoreType = "";
    }

    /**
     * Constructor. 
     */
    public CorpOrderCacheView(Boolean parm1, Date parm2, Integer parm3, Boolean parm4, Integer parm5, String parm6, AccountData parm7, FiscalCalenderView parm8, BigDecimal parm9, Integer parm10, Integer parm11, UserData parm12, HashMap parm13, HashMap parm14)
    {
        mSpecificDatesFl = parm1;
        mCutoffDate = parm2;
        mInventoryPeriod = parm3;
        mUsePhysicalInventoryCart = parm4;
        mStoreId = parm5;
        mStoreType = parm6;
        mAccount = parm7;
        mFiscalCalendar = parm8;
        mAutoOrderFactor = parm9;
        mSiteId = parm10;
        mOrderGuideId = parm11;
        mUser = parm12;
        mAccountInventoryItems = parm13;
        mCatalogProducts = parm14;
        
    }

    /**
     * Creates a new CorpOrderCacheView
     *
     * @return
     *  Newly initialized CorpOrderCacheView object.
     */
    public static CorpOrderCacheView createValue () 
    {
        CorpOrderCacheView valueView = new CorpOrderCacheView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CorpOrderCacheView object
     */
    public String toString()
    {
        return "[" + "SpecificDatesFl=" + mSpecificDatesFl + ", CutoffDate=" + mCutoffDate + ", InventoryPeriod=" + mInventoryPeriod + ", UsePhysicalInventoryCart=" + mUsePhysicalInventoryCart + ", StoreId=" + mStoreId + ", StoreType=" + mStoreType + ", Account=" + mAccount + ", FiscalCalendar=" + mFiscalCalendar + ", AutoOrderFactor=" + mAutoOrderFactor + ", SiteId=" + mSiteId + ", OrderGuideId=" + mOrderGuideId + ", User=" + mUser + ", AccountInventoryItems=" + mAccountInventoryItems + ", CatalogProducts=" + mCatalogProducts + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CorpOrderCache");
	root.setAttribute("Id", String.valueOf(mSpecificDatesFl));

	Element node;

        node = doc.createElement("CutoffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate)));
        root.appendChild(node);

        node = doc.createElement("InventoryPeriod");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryPeriod)));
        root.appendChild(node);

        node = doc.createElement("UsePhysicalInventoryCart");
        node.appendChild(doc.createTextNode(String.valueOf(mUsePhysicalInventoryCart)));
        root.appendChild(node);

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("StoreType");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreType)));
        root.appendChild(node);

        node = doc.createElement("Account");
        node.appendChild(doc.createTextNode(String.valueOf(mAccount)));
        root.appendChild(node);

        node = doc.createElement("FiscalCalendar");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalCalendar)));
        root.appendChild(node);

        node = doc.createElement("AutoOrderFactor");
        node.appendChild(doc.createTextNode(String.valueOf(mAutoOrderFactor)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node = doc.createElement("User");
        node.appendChild(doc.createTextNode(String.valueOf(mUser)));
        root.appendChild(node);

        node = doc.createElement("AccountInventoryItems");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountInventoryItems)));
        root.appendChild(node);

        node = doc.createElement("CatalogProducts");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogProducts)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CorpOrderCacheView copy()  {
      CorpOrderCacheView obj = new CorpOrderCacheView();
      obj.setSpecificDatesFl(mSpecificDatesFl);
      obj.setCutoffDate(mCutoffDate);
      obj.setInventoryPeriod(mInventoryPeriod);
      obj.setUsePhysicalInventoryCart(mUsePhysicalInventoryCart);
      obj.setStoreId(mStoreId);
      obj.setStoreType(mStoreType);
      obj.setAccount(mAccount);
      obj.setFiscalCalendar(mFiscalCalendar);
      obj.setAutoOrderFactor(mAutoOrderFactor);
      obj.setSiteId(mSiteId);
      obj.setOrderGuideId(mOrderGuideId);
      obj.setUser(mUser);
      obj.setAccountInventoryItems(mAccountInventoryItems);
      obj.setCatalogProducts(mCatalogProducts);
      
      return obj;
    }

    
    /**
     * Sets the SpecificDatesFl property.
     *
     * @param pSpecificDatesFl
     *  Boolean to use to update the property.
     */
    public void setSpecificDatesFl(Boolean pSpecificDatesFl){
        this.mSpecificDatesFl = pSpecificDatesFl;
    }
    /**
     * Retrieves the SpecificDatesFl property.
     *
     * @return
     *  Boolean containing the SpecificDatesFl property.
     */
    public Boolean getSpecificDatesFl(){
        return mSpecificDatesFl;
    }


    /**
     * Sets the CutoffDate property.
     *
     * @param pCutoffDate
     *  Date to use to update the property.
     */
    public void setCutoffDate(Date pCutoffDate){
        this.mCutoffDate = pCutoffDate;
    }
    /**
     * Retrieves the CutoffDate property.
     *
     * @return
     *  Date containing the CutoffDate property.
     */
    public Date getCutoffDate(){
        return mCutoffDate;
    }


    /**
     * Sets the InventoryPeriod property.
     *
     * @param pInventoryPeriod
     *  Integer to use to update the property.
     */
    public void setInventoryPeriod(Integer pInventoryPeriod){
        this.mInventoryPeriod = pInventoryPeriod;
    }
    /**
     * Retrieves the InventoryPeriod property.
     *
     * @return
     *  Integer containing the InventoryPeriod property.
     */
    public Integer getInventoryPeriod(){
        return mInventoryPeriod;
    }


    /**
     * Sets the UsePhysicalInventoryCart property.
     *
     * @param pUsePhysicalInventoryCart
     *  Boolean to use to update the property.
     */
    public void setUsePhysicalInventoryCart(Boolean pUsePhysicalInventoryCart){
        this.mUsePhysicalInventoryCart = pUsePhysicalInventoryCart;
    }
    /**
     * Retrieves the UsePhysicalInventoryCart property.
     *
     * @return
     *  Boolean containing the UsePhysicalInventoryCart property.
     */
    public Boolean getUsePhysicalInventoryCart(){
        return mUsePhysicalInventoryCart;
    }


    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  Integer to use to update the property.
     */
    public void setStoreId(Integer pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  Integer containing the StoreId property.
     */
    public Integer getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the StoreType property.
     *
     * @param pStoreType
     *  String to use to update the property.
     */
    public void setStoreType(String pStoreType){
        this.mStoreType = pStoreType;
    }
    /**
     * Retrieves the StoreType property.
     *
     * @return
     *  String containing the StoreType property.
     */
    public String getStoreType(){
        return mStoreType;
    }


    /**
     * Sets the Account property.
     *
     * @param pAccount
     *  AccountData to use to update the property.
     */
    public void setAccount(AccountData pAccount){
        this.mAccount = pAccount;
    }
    /**
     * Retrieves the Account property.
     *
     * @return
     *  AccountData containing the Account property.
     */
    public AccountData getAccount(){
        return mAccount;
    }


    /**
     * Sets the FiscalCalendar property.
     *
     * @param pFiscalCalendar
     *  FiscalCalenderView to use to update the property.
     */
    public void setFiscalCalendar(FiscalCalenderView pFiscalCalendar){
        this.mFiscalCalendar = pFiscalCalendar;
    }
    /**
     * Retrieves the FiscalCalendar property.
     *
     * @return
     *  FiscalCalenderView containing the FiscalCalendar property.
     */
    public FiscalCalenderView getFiscalCalendar(){
        return mFiscalCalendar;
    }


    /**
     * Sets the AutoOrderFactor property.
     *
     * @param pAutoOrderFactor
     *  BigDecimal to use to update the property.
     */
    public void setAutoOrderFactor(BigDecimal pAutoOrderFactor){
        this.mAutoOrderFactor = pAutoOrderFactor;
    }
    /**
     * Retrieves the AutoOrderFactor property.
     *
     * @return
     *  BigDecimal containing the AutoOrderFactor property.
     */
    public BigDecimal getAutoOrderFactor(){
        return mAutoOrderFactor;
    }


    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  Integer to use to update the property.
     */
    public void setSiteId(Integer pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  Integer containing the SiteId property.
     */
    public Integer getSiteId(){
        return mSiteId;
    }


    /**
     * Sets the OrderGuideId property.
     *
     * @param pOrderGuideId
     *  Integer to use to update the property.
     */
    public void setOrderGuideId(Integer pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
    }
    /**
     * Retrieves the OrderGuideId property.
     *
     * @return
     *  Integer containing the OrderGuideId property.
     */
    public Integer getOrderGuideId(){
        return mOrderGuideId;
    }


    /**
     * Sets the User property.
     *
     * @param pUser
     *  UserData to use to update the property.
     */
    public void setUser(UserData pUser){
        this.mUser = pUser;
    }
    /**
     * Retrieves the User property.
     *
     * @return
     *  UserData containing the User property.
     */
    public UserData getUser(){
        return mUser;
    }


    /**
     * Sets the AccountInventoryItems property.
     *
     * @param pAccountInventoryItems
     *  HashMap to use to update the property.
     */
    public void setAccountInventoryItems(HashMap pAccountInventoryItems){
        this.mAccountInventoryItems = pAccountInventoryItems;
    }
    /**
     * Retrieves the AccountInventoryItems property.
     *
     * @return
     *  HashMap containing the AccountInventoryItems property.
     */
    public HashMap getAccountInventoryItems(){
        return mAccountInventoryItems;
    }


    /**
     * Sets the CatalogProducts property.
     *
     * @param pCatalogProducts
     *  HashMap to use to update the property.
     */
    public void setCatalogProducts(HashMap pCatalogProducts){
        this.mCatalogProducts = pCatalogProducts;
    }
    /**
     * Retrieves the CatalogProducts property.
     *
     * @return
     *  HashMap containing the CatalogProducts property.
     */
    public HashMap getCatalogProducts(){
        return mCatalogProducts;
    }


    
}
