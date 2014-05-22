
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractDetailLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTRACT_DETAIL_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContractDetailLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContractDetailLogData</code> is a ValueObject class wrapping of the database table CLW_CONTRACT_DETAIL_LOG.
 */
public class ContractDetailLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 99923291998159060L;
    private int mContractDetailLogId;// SQL type:NUMBER, not null
    private int mContractItemId;// SQL type:NUMBER, not null
    private Date mDate;// SQL type:DATE, not null
    private Date mTime;// SQL type:DATE, not null
    private String mChange;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContractDetailLogData ()
    {
        mChange = "";
    }

    /**
     * Constructor.
     */
    public ContractDetailLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5)
    {
        mContractDetailLogId = parm1;
        mContractItemId = parm2;
        mDate = parm3;
        mTime = parm4;
        mChange = parm5;
        
    }

    /**
     * Creates a new ContractDetailLogData
     *
     * @return
     *  Newly initialized ContractDetailLogData object.
     */
    public static ContractDetailLogData createValue ()
    {
        ContractDetailLogData valueData = new ContractDetailLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractDetailLogData object
     */
    public String toString()
    {
        return "[" + "ContractDetailLogId=" + mContractDetailLogId + ", ContractItemId=" + mContractItemId + ", Date=" + mDate + ", Time=" + mTime + ", Change=" + mChange + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ContractDetailLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContractDetailLogId));

        node =  doc.createElement("ContractItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractItemId)));
        root.appendChild(node);

        node =  doc.createElement("Date");
        node.appendChild(doc.createTextNode(String.valueOf(mDate)));
        root.appendChild(node);

        node =  doc.createElement("Time");
        node.appendChild(doc.createTextNode(String.valueOf(mTime)));
        root.appendChild(node);

        node =  doc.createElement("Change");
        node.appendChild(doc.createTextNode(String.valueOf(mChange)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ContractDetailLogId field is not cloned.
    *
    * @return ContractDetailLogData object
    */
    public Object clone(){
        ContractDetailLogData myClone = new ContractDetailLogData();
        
        myClone.mContractItemId = mContractItemId;
        
        if(mDate != null){
                myClone.mDate = (Date) mDate.clone();
        }
        
        if(mTime != null){
                myClone.mTime = (Date) mTime.clone();
        }
        
        myClone.mChange = mChange;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ContractDetailLogDataAccess.CONTRACT_DETAIL_LOG_ID.equals(pFieldName)) {
            return getContractDetailLogId();
        } else if (ContractDetailLogDataAccess.CONTRACT_ITEM_ID.equals(pFieldName)) {
            return getContractItemId();
        } else if (ContractDetailLogDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (ContractDetailLogDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (ContractDetailLogDataAccess.CHANGE.equals(pFieldName)) {
            return getChange();
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
        return ContractDetailLogDataAccess.CLW_CONTRACT_DETAIL_LOG;
    }

    
    /**
     * Sets the ContractDetailLogId field. This field is required to be set in the database.
     *
     * @param pContractDetailLogId
     *  int to use to update the field.
     */
    public void setContractDetailLogId(int pContractDetailLogId){
        this.mContractDetailLogId = pContractDetailLogId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractDetailLogId field.
     *
     * @return
     *  int containing the ContractDetailLogId field.
     */
    public int getContractDetailLogId(){
        return mContractDetailLogId;
    }

    /**
     * Sets the ContractItemId field. This field is required to be set in the database.
     *
     * @param pContractItemId
     *  int to use to update the field.
     */
    public void setContractItemId(int pContractItemId){
        this.mContractItemId = pContractItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractItemId field.
     *
     * @return
     *  int containing the ContractItemId field.
     */
    public int getContractItemId(){
        return mContractItemId;
    }

    /**
     * Sets the Date field. This field is required to be set in the database.
     *
     * @param pDate
     *  Date to use to update the field.
     */
    public void setDate(Date pDate){
        this.mDate = pDate;
        setDirty(true);
    }
    /**
     * Retrieves the Date field.
     *
     * @return
     *  Date containing the Date field.
     */
    public Date getDate(){
        return mDate;
    }

    /**
     * Sets the Time field. This field is required to be set in the database.
     *
     * @param pTime
     *  Date to use to update the field.
     */
    public void setTime(Date pTime){
        this.mTime = pTime;
        setDirty(true);
    }
    /**
     * Retrieves the Time field.
     *
     * @return
     *  Date containing the Time field.
     */
    public Date getTime(){
        return mTime;
    }

    /**
     * Sets the Change field.
     *
     * @param pChange
     *  String to use to update the field.
     */
    public void setChange(String pChange){
        this.mChange = pChange;
        setDirty(true);
    }
    /**
     * Retrieves the Change field.
     *
     * @return
     *  String containing the Change field.
     */
    public String getChange(){
        return mChange;
    }


}
