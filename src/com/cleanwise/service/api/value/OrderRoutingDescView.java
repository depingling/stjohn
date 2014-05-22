
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderRoutingDescView
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




/**
 * <code>OrderRoutingDescView</code> is a ViewObject class for UI.
 */
public class OrderRoutingDescView
extends ValueObject
{
   
    private static final long serialVersionUID = 8388672856821872582L;
    private OrderRoutingData mOrderRoutingData;
    private BusEntityData mDistributor;
    private BusEntityData mFreightHandler;
    private BusEntityData mAccount;
    private ContractData mContract;
    private CatalogData mCatalog;
    private BusEntityData mFinalDistributor;
    private ContractData mFinalContract;
    private CatalogData mFinalCatalog;
    private boolean mDelete;
    private BusEntityData mFinalFreightHandler;
    private BusEntityData mLtlFreightHandler;

    /**
     * Constructor.
     */
    public OrderRoutingDescView ()
    {
    }

    /**
     * Constructor. 
     */
    public OrderRoutingDescView(OrderRoutingData parm1, BusEntityData parm2, BusEntityData parm3, BusEntityData parm4, ContractData parm5, CatalogData parm6, BusEntityData parm7, ContractData parm8, CatalogData parm9, boolean parm10, BusEntityData parm11, BusEntityData parm12)
    {
        mOrderRoutingData = parm1;
        mDistributor = parm2;
        mFreightHandler = parm3;
        mAccount = parm4;
        mContract = parm5;
        mCatalog = parm6;
        mFinalDistributor = parm7;
        mFinalContract = parm8;
        mFinalCatalog = parm9;
        mDelete = parm10;
        mFinalFreightHandler = parm11;
        mLtlFreightHandler = parm12;
        
    }

    /**
     * Creates a new OrderRoutingDescView
     *
     * @return
     *  Newly initialized OrderRoutingDescView object.
     */
    public static OrderRoutingDescView createValue () 
    {
        OrderRoutingDescView valueView = new OrderRoutingDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderRoutingDescView object
     */
    public String toString()
    {
        return "[" + "OrderRoutingData=" + mOrderRoutingData + ", Distributor=" + mDistributor + ", FreightHandler=" + mFreightHandler + ", Account=" + mAccount + ", Contract=" + mContract + ", Catalog=" + mCatalog + ", FinalDistributor=" + mFinalDistributor + ", FinalContract=" + mFinalContract + ", FinalCatalog=" + mFinalCatalog + ", Delete=" + mDelete + ", FinalFreightHandler=" + mFinalFreightHandler + ", LtlFreightHandler=" + mLtlFreightHandler + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderRoutingDesc");
	root.setAttribute("Id", String.valueOf(mOrderRoutingData));

	Element node;

        node = doc.createElement("Distributor");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributor)));
        root.appendChild(node);

        node = doc.createElement("FreightHandler");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandler)));
        root.appendChild(node);

        node = doc.createElement("Account");
        node.appendChild(doc.createTextNode(String.valueOf(mAccount)));
        root.appendChild(node);

        node = doc.createElement("Contract");
        node.appendChild(doc.createTextNode(String.valueOf(mContract)));
        root.appendChild(node);

        node = doc.createElement("Catalog");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalog)));
        root.appendChild(node);

        node = doc.createElement("FinalDistributor");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalDistributor)));
        root.appendChild(node);

        node = doc.createElement("FinalContract");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalContract)));
        root.appendChild(node);

        node = doc.createElement("FinalCatalog");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalCatalog)));
        root.appendChild(node);

        node = doc.createElement("Delete");
        node.appendChild(doc.createTextNode(String.valueOf(mDelete)));
        root.appendChild(node);

        node = doc.createElement("FinalFreightHandler");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalFreightHandler)));
        root.appendChild(node);

        node = doc.createElement("LtlFreightHandler");
        node.appendChild(doc.createTextNode(String.valueOf(mLtlFreightHandler)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderRoutingDescView copy()  {
      OrderRoutingDescView obj = new OrderRoutingDescView();
      obj.setOrderRoutingData(mOrderRoutingData);
      obj.setDistributor(mDistributor);
      obj.setFreightHandler(mFreightHandler);
      obj.setAccount(mAccount);
      obj.setContract(mContract);
      obj.setCatalog(mCatalog);
      obj.setFinalDistributor(mFinalDistributor);
      obj.setFinalContract(mFinalContract);
      obj.setFinalCatalog(mFinalCatalog);
      obj.setDelete(mDelete);
      obj.setFinalFreightHandler(mFinalFreightHandler);
      obj.setLtlFreightHandler(mLtlFreightHandler);
      
      return obj;
    }

    
    /**
     * Sets the OrderRoutingData property.
     *
     * @param pOrderRoutingData
     *  OrderRoutingData to use to update the property.
     */
    public void setOrderRoutingData(OrderRoutingData pOrderRoutingData){
        this.mOrderRoutingData = pOrderRoutingData;
    }
    /**
     * Retrieves the OrderRoutingData property.
     *
     * @return
     *  OrderRoutingData containing the OrderRoutingData property.
     */
    public OrderRoutingData getOrderRoutingData(){
        return mOrderRoutingData;
    }


    /**
     * Sets the Distributor property.
     *
     * @param pDistributor
     *  BusEntityData to use to update the property.
     */
    public void setDistributor(BusEntityData pDistributor){
        this.mDistributor = pDistributor;
    }
    /**
     * Retrieves the Distributor property.
     *
     * @return
     *  BusEntityData containing the Distributor property.
     */
    public BusEntityData getDistributor(){
        return mDistributor;
    }


    /**
     * Sets the FreightHandler property.
     *
     * @param pFreightHandler
     *  BusEntityData to use to update the property.
     */
    public void setFreightHandler(BusEntityData pFreightHandler){
        this.mFreightHandler = pFreightHandler;
    }
    /**
     * Retrieves the FreightHandler property.
     *
     * @return
     *  BusEntityData containing the FreightHandler property.
     */
    public BusEntityData getFreightHandler(){
        return mFreightHandler;
    }


    /**
     * Sets the Account property.
     *
     * @param pAccount
     *  BusEntityData to use to update the property.
     */
    public void setAccount(BusEntityData pAccount){
        this.mAccount = pAccount;
    }
    /**
     * Retrieves the Account property.
     *
     * @return
     *  BusEntityData containing the Account property.
     */
    public BusEntityData getAccount(){
        return mAccount;
    }


    /**
     * Sets the Contract property.
     *
     * @param pContract
     *  ContractData to use to update the property.
     */
    public void setContract(ContractData pContract){
        this.mContract = pContract;
    }
    /**
     * Retrieves the Contract property.
     *
     * @return
     *  ContractData containing the Contract property.
     */
    public ContractData getContract(){
        return mContract;
    }


    /**
     * Sets the Catalog property.
     *
     * @param pCatalog
     *  CatalogData to use to update the property.
     */
    public void setCatalog(CatalogData pCatalog){
        this.mCatalog = pCatalog;
    }
    /**
     * Retrieves the Catalog property.
     *
     * @return
     *  CatalogData containing the Catalog property.
     */
    public CatalogData getCatalog(){
        return mCatalog;
    }


    /**
     * Sets the FinalDistributor property.
     *
     * @param pFinalDistributor
     *  BusEntityData to use to update the property.
     */
    public void setFinalDistributor(BusEntityData pFinalDistributor){
        this.mFinalDistributor = pFinalDistributor;
    }
    /**
     * Retrieves the FinalDistributor property.
     *
     * @return
     *  BusEntityData containing the FinalDistributor property.
     */
    public BusEntityData getFinalDistributor(){
        return mFinalDistributor;
    }


    /**
     * Sets the FinalContract property.
     *
     * @param pFinalContract
     *  ContractData to use to update the property.
     */
    public void setFinalContract(ContractData pFinalContract){
        this.mFinalContract = pFinalContract;
    }
    /**
     * Retrieves the FinalContract property.
     *
     * @return
     *  ContractData containing the FinalContract property.
     */
    public ContractData getFinalContract(){
        return mFinalContract;
    }


    /**
     * Sets the FinalCatalog property.
     *
     * @param pFinalCatalog
     *  CatalogData to use to update the property.
     */
    public void setFinalCatalog(CatalogData pFinalCatalog){
        this.mFinalCatalog = pFinalCatalog;
    }
    /**
     * Retrieves the FinalCatalog property.
     *
     * @return
     *  CatalogData containing the FinalCatalog property.
     */
    public CatalogData getFinalCatalog(){
        return mFinalCatalog;
    }


    /**
     * Sets the Delete property.
     *
     * @param pDelete
     *  boolean to use to update the property.
     */
    public void setDelete(boolean pDelete){
        this.mDelete = pDelete;
    }
    /**
     * Retrieves the Delete property.
     *
     * @return
     *  boolean containing the Delete property.
     */
    public boolean getDelete(){
        return mDelete;
    }


    /**
     * Sets the FinalFreightHandler property.
     *
     * @param pFinalFreightHandler
     *  BusEntityData to use to update the property.
     */
    public void setFinalFreightHandler(BusEntityData pFinalFreightHandler){
        this.mFinalFreightHandler = pFinalFreightHandler;
    }
    /**
     * Retrieves the FinalFreightHandler property.
     *
     * @return
     *  BusEntityData containing the FinalFreightHandler property.
     */
    public BusEntityData getFinalFreightHandler(){
        return mFinalFreightHandler;
    }


    /**
     * Sets the LtlFreightHandler property.
     *
     * @param pLtlFreightHandler
     *  BusEntityData to use to update the property.
     */
    public void setLtlFreightHandler(BusEntityData pLtlFreightHandler){
        this.mLtlFreightHandler = pLtlFreightHandler;
    }
    /**
     * Retrieves the LtlFreightHandler property.
     *
     * @return
     *  BusEntityData containing the LtlFreightHandler property.
     */
    public BusEntityData getLtlFreightHandler(){
        return mLtlFreightHandler;
    }


    
}
