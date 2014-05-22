
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractItemSubstData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTRACT_ITEM_SUBST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContractItemSubstDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContractItemSubstData</code> is a ValueObject class wrapping of the database table CLW_CONTRACT_ITEM_SUBST.
 */
public class ContractItemSubstData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5072127140997743926L;
    private int mContractItemSubstId;// SQL type:NUMBER, not null
    private int mContractId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mSubstItemId;// SQL type:NUMBER, not null
    private String mSubstStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContractItemSubstData ()
    {
        mSubstStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ContractItemSubstData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mContractItemSubstId = parm1;
        mContractId = parm2;
        mItemId = parm3;
        mSubstItemId = parm4;
        mSubstStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new ContractItemSubstData
     *
     * @return
     *  Newly initialized ContractItemSubstData object.
     */
    public static ContractItemSubstData createValue ()
    {
        ContractItemSubstData valueData = new ContractItemSubstData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractItemSubstData object
     */
    public String toString()
    {
        return "[" + "ContractItemSubstId=" + mContractItemSubstId + ", ContractId=" + mContractId + ", ItemId=" + mItemId + ", SubstItemId=" + mSubstItemId + ", SubstStatusCd=" + mSubstStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ContractItemSubst");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContractItemSubstId));

        node =  doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("SubstItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemId)));
        root.appendChild(node);

        node =  doc.createElement("SubstStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ContractItemSubstId field is not cloned.
    *
    * @return ContractItemSubstData object
    */
    public Object clone(){
        ContractItemSubstData myClone = new ContractItemSubstData();
        
        myClone.mContractId = mContractId;
        
        myClone.mItemId = mItemId;
        
        myClone.mSubstItemId = mSubstItemId;
        
        myClone.mSubstStatusCd = mSubstStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ContractItemSubstDataAccess.CONTRACT_ITEM_SUBST_ID.equals(pFieldName)) {
            return getContractItemSubstId();
        } else if (ContractItemSubstDataAccess.CONTRACT_ID.equals(pFieldName)) {
            return getContractId();
        } else if (ContractItemSubstDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ContractItemSubstDataAccess.SUBST_ITEM_ID.equals(pFieldName)) {
            return getSubstItemId();
        } else if (ContractItemSubstDataAccess.SUBST_STATUS_CD.equals(pFieldName)) {
            return getSubstStatusCd();
        } else if (ContractItemSubstDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ContractItemSubstDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ContractItemSubstDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ContractItemSubstDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
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
        return ContractItemSubstDataAccess.CLW_CONTRACT_ITEM_SUBST;
    }

    
    /**
     * Sets the ContractItemSubstId field. This field is required to be set in the database.
     *
     * @param pContractItemSubstId
     *  int to use to update the field.
     */
    public void setContractItemSubstId(int pContractItemSubstId){
        this.mContractItemSubstId = pContractItemSubstId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractItemSubstId field.
     *
     * @return
     *  int containing the ContractItemSubstId field.
     */
    public int getContractItemSubstId(){
        return mContractItemSubstId;
    }

    /**
     * Sets the ContractId field. This field is required to be set in the database.
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
     * Sets the ItemId field. This field is required to be set in the database.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }

    /**
     * Sets the SubstItemId field. This field is required to be set in the database.
     *
     * @param pSubstItemId
     *  int to use to update the field.
     */
    public void setSubstItemId(int pSubstItemId){
        this.mSubstItemId = pSubstItemId;
        setDirty(true);
    }
    /**
     * Retrieves the SubstItemId field.
     *
     * @return
     *  int containing the SubstItemId field.
     */
    public int getSubstItemId(){
        return mSubstItemId;
    }

    /**
     * Sets the SubstStatusCd field. This field is required to be set in the database.
     *
     * @param pSubstStatusCd
     *  String to use to update the field.
     */
    public void setSubstStatusCd(String pSubstStatusCd){
        this.mSubstStatusCd = pSubstStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the SubstStatusCd field.
     *
     * @return
     *  String containing the SubstStatusCd field.
     */
    public String getSubstStatusCd(){
        return mSubstStatusCd;
    }

    /**
     * Sets the AddDate field. This field is required to be set in the database.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field. This field is required to be set in the database.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }


}
