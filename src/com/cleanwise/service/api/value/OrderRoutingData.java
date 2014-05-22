
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderRoutingData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_ROUTING.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderRoutingDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderRoutingData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ROUTING.
 */
public class OrderRoutingData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2770327466016263993L;
    private int mOrderRoutingId;// SQL type:NUMBER, not null
    private String mZip;// SQL type:VARCHAR2, not null
    private int mDistributorId;// SQL type:NUMBER, not null
    private int mFreightHandlerId;// SQL type:NUMBER, not null
    private int mAccountId;// SQL type:NUMBER
    private int mContractId;// SQL type:NUMBER
    private int mFinalDistributorId;// SQL type:NUMBER
    private int mFinalContractId;// SQL type:NUMBER
    private int mFinalFreightHandlerId;// SQL type:NUMBER
    private int mLtlFreightHandlerId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderRoutingData ()
    {
        mZip = "";
    }

    /**
     * Constructor.
     */
    public OrderRoutingData(int parm1, String parm2, int parm3, int parm4, int parm5, int parm6, int parm7, int parm8, int parm9, int parm10)
    {
        mOrderRoutingId = parm1;
        mZip = parm2;
        mDistributorId = parm3;
        mFreightHandlerId = parm4;
        mAccountId = parm5;
        mContractId = parm6;
        mFinalDistributorId = parm7;
        mFinalContractId = parm8;
        mFinalFreightHandlerId = parm9;
        mLtlFreightHandlerId = parm10;
        
    }

    /**
     * Creates a new OrderRoutingData
     *
     * @return
     *  Newly initialized OrderRoutingData object.
     */
    public static OrderRoutingData createValue ()
    {
        OrderRoutingData valueData = new OrderRoutingData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderRoutingData object
     */
    public String toString()
    {
        return "[" + "OrderRoutingId=" + mOrderRoutingId + ", Zip=" + mZip + ", DistributorId=" + mDistributorId + ", FreightHandlerId=" + mFreightHandlerId + ", AccountId=" + mAccountId + ", ContractId=" + mContractId + ", FinalDistributorId=" + mFinalDistributorId + ", FinalContractId=" + mFinalContractId + ", FinalFreightHandlerId=" + mFinalFreightHandlerId + ", LtlFreightHandlerId=" + mLtlFreightHandlerId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderRouting");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderRoutingId));

        node =  doc.createElement("Zip");
        node.appendChild(doc.createTextNode(String.valueOf(mZip)));
        root.appendChild(node);

        node =  doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node =  doc.createElement("FreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandlerId)));
        root.appendChild(node);

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node =  doc.createElement("FinalDistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalDistributorId)));
        root.appendChild(node);

        node =  doc.createElement("FinalContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalContractId)));
        root.appendChild(node);

        node =  doc.createElement("FinalFreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mFinalFreightHandlerId)));
        root.appendChild(node);

        node =  doc.createElement("LtlFreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mLtlFreightHandlerId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderRoutingId field is not cloned.
    *
    * @return OrderRoutingData object
    */
    public Object clone(){
        OrderRoutingData myClone = new OrderRoutingData();
        
        myClone.mZip = mZip;
        
        myClone.mDistributorId = mDistributorId;
        
        myClone.mFreightHandlerId = mFreightHandlerId;
        
        myClone.mAccountId = mAccountId;
        
        myClone.mContractId = mContractId;
        
        myClone.mFinalDistributorId = mFinalDistributorId;
        
        myClone.mFinalContractId = mFinalContractId;
        
        myClone.mFinalFreightHandlerId = mFinalFreightHandlerId;
        
        myClone.mLtlFreightHandlerId = mLtlFreightHandlerId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderRoutingDataAccess.ORDER_ROUTING_ID.equals(pFieldName)) {
            return getOrderRoutingId();
        } else if (OrderRoutingDataAccess.ZIP.equals(pFieldName)) {
            return getZip();
        } else if (OrderRoutingDataAccess.DISTRIBUTOR_ID.equals(pFieldName)) {
            return getDistributorId();
        } else if (OrderRoutingDataAccess.FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getFreightHandlerId();
        } else if (OrderRoutingDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (OrderRoutingDataAccess.CONTRACT_ID.equals(pFieldName)) {
            return getContractId();
        } else if (OrderRoutingDataAccess.FINAL_DISTRIBUTOR_ID.equals(pFieldName)) {
            return getFinalDistributorId();
        } else if (OrderRoutingDataAccess.FINAL_CONTRACT_ID.equals(pFieldName)) {
            return getFinalContractId();
        } else if (OrderRoutingDataAccess.FINAL_FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getFinalFreightHandlerId();
        } else if (OrderRoutingDataAccess.LTL_FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getLtlFreightHandlerId();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return OrderRoutingDataAccess.CLW_ORDER_ROUTING;
    }

    
    /**
     * Sets the OrderRoutingId field. This field is required to be set in the database.
     *
     * @param pOrderRoutingId
     *  int to use to update the field.
     */
    public void setOrderRoutingId(int pOrderRoutingId){
        this.mOrderRoutingId = pOrderRoutingId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderRoutingId field.
     *
     * @return
     *  int containing the OrderRoutingId field.
     */
    public int getOrderRoutingId(){
        return mOrderRoutingId;
    }

    /**
     * Sets the Zip field. This field is required to be set in the database.
     *
     * @param pZip
     *  String to use to update the field.
     */
    public void setZip(String pZip){
        this.mZip = pZip;
        setDirty(true);
    }
    /**
     * Retrieves the Zip field.
     *
     * @return
     *  String containing the Zip field.
     */
    public String getZip(){
        return mZip;
    }

    /**
     * Sets the DistributorId field. This field is required to be set in the database.
     *
     * @param pDistributorId
     *  int to use to update the field.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorId field.
     *
     * @return
     *  int containing the DistributorId field.
     */
    public int getDistributorId(){
        return mDistributorId;
    }

    /**
     * Sets the FreightHandlerId field. This field is required to be set in the database.
     *
     * @param pFreightHandlerId
     *  int to use to update the field.
     */
    public void setFreightHandlerId(int pFreightHandlerId){
        this.mFreightHandlerId = pFreightHandlerId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightHandlerId field.
     *
     * @return
     *  int containing the FreightHandlerId field.
     */
    public int getFreightHandlerId(){
        return mFreightHandlerId;
    }

    /**
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the ContractId field.
     *
     * @param pContractId
     *  int to use to update the field.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractId field.
     *
     * @return
     *  int containing the ContractId field.
     */
    public int getContractId(){
        return mContractId;
    }

    /**
     * Sets the FinalDistributorId field.
     *
     * @param pFinalDistributorId
     *  int to use to update the field.
     */
    public void setFinalDistributorId(int pFinalDistributorId){
        this.mFinalDistributorId = pFinalDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the FinalDistributorId field.
     *
     * @return
     *  int containing the FinalDistributorId field.
     */
    public int getFinalDistributorId(){
        return mFinalDistributorId;
    }

    /**
     * Sets the FinalContractId field.
     *
     * @param pFinalContractId
     *  int to use to update the field.
     */
    public void setFinalContractId(int pFinalContractId){
        this.mFinalContractId = pFinalContractId;
        setDirty(true);
    }
    /**
     * Retrieves the FinalContractId field.
     *
     * @return
     *  int containing the FinalContractId field.
     */
    public int getFinalContractId(){
        return mFinalContractId;
    }

    /**
     * Sets the FinalFreightHandlerId field.
     *
     * @param pFinalFreightHandlerId
     *  int to use to update the field.
     */
    public void setFinalFreightHandlerId(int pFinalFreightHandlerId){
        this.mFinalFreightHandlerId = pFinalFreightHandlerId;
        setDirty(true);
    }
    /**
     * Retrieves the FinalFreightHandlerId field.
     *
     * @return
     *  int containing the FinalFreightHandlerId field.
     */
    public int getFinalFreightHandlerId(){
        return mFinalFreightHandlerId;
    }

    /**
     * Sets the LtlFreightHandlerId field.
     *
     * @param pLtlFreightHandlerId
     *  int to use to update the field.
     */
    public void setLtlFreightHandlerId(int pLtlFreightHandlerId){
        this.mLtlFreightHandlerId = pLtlFreightHandlerId;
        setDirty(true);
    }
    /**
     * Retrieves the LtlFreightHandlerId field.
     *
     * @return
     *  int containing the LtlFreightHandlerId field.
     */
    public int getLtlFreightHandlerId(){
        return mLtlFreightHandlerId;
    }


}
