
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderAssocData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_ASSOC.
 */
public class WorkOrderAssocData extends ValueObject implements Cloneable
{
    private static final long serialVersionUID = -964684297252733902L;
    private int mWorkOrderAssocId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mWorkOrderAssocCd;// SQL type:VARCHAR2
    private String mWorkOrderAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkOrderAssocData ()
    {
        mWorkOrderAssocCd = "";
        mWorkOrderAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mWorkOrderAssocId = parm1;
        mWorkOrderId = parm2;
        mBusEntityId = parm3;
        mWorkOrderAssocCd = parm4;
        mWorkOrderAssocStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;

    }

    /**
     * Creates a new WorkOrderAssocData
     *
     * @return
     *  Newly initialized WorkOrderAssocData object.
     */
    public static WorkOrderAssocData createValue ()
    {
        WorkOrderAssocData valueData = new WorkOrderAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderAssocData object
     */
    public String toString()
    {
        return "[" + "WorkOrderAssocId=" + mWorkOrderAssocId + ", WorkOrderId=" + mWorkOrderId + ", BusEntityId=" + mBusEntityId + ", WorkOrderAssocCd=" + mWorkOrderAssocCd + ", WorkOrderAssocStatusCd=" + mWorkOrderAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderAssocId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderAssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderAssocStatusCd)));
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
    * creates a clone of this object, the WorkOrderAssocId field is not cloned.
    *
    * @return WorkOrderAssocData object
    */
    public Object clone(){
        WorkOrderAssocData myClone = new WorkOrderAssocData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mWorkOrderAssocCd = mWorkOrderAssocCd;
        
        myClone.mWorkOrderAssocStatusCd = mWorkOrderAssocStatusCd;
        
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
     * Sets the WorkOrderAssocId field. This field is required to be set in the database.
     *
     * @param pWorkOrderAssocId
     *  int to use to update the field.
     */
    public void setWorkOrderAssocId(int pWorkOrderAssocId){
        this.mWorkOrderAssocId = pWorkOrderAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderAssocId field.
     *
     * @return
     *  int containing the WorkOrderAssocId field.
     */
    public int getWorkOrderAssocId(){
        return mWorkOrderAssocId;
    }

    /**
     * Sets the WorkOrderId field. This field is required to be set in the database.
     *
     * @param pWorkOrderId
     *  int to use to update the field.
     */
    public void setWorkOrderId(int pWorkOrderId){
        this.mWorkOrderId = pWorkOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderId field.
     *
     * @return
     *  int containing the WorkOrderId field.
     */
    public int getWorkOrderId(){
        return mWorkOrderId;
    }

    /**
     * Sets the BusEntityId field. This field is required to be set in the database.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }

    /**
     * Sets the WorkOrderAssocCd field.
     *
     * @param pWorkOrderAssocCd
     *  String to use to update the field.
     */
    public void setWorkOrderAssocCd(String pWorkOrderAssocCd){
        this.mWorkOrderAssocCd = pWorkOrderAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderAssocCd field.
     *
     * @return
     *  String containing the WorkOrderAssocCd field.
     */
    public String getWorkOrderAssocCd(){
        return mWorkOrderAssocCd;
    }

    /**
     * Sets the WorkOrderAssocStatusCd field.
     *
     * @param pWorkOrderAssocStatusCd
     *  String to use to update the field.
     */
    public void setWorkOrderAssocStatusCd(String pWorkOrderAssocStatusCd){
        this.mWorkOrderAssocStatusCd = pWorkOrderAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderAssocStatusCd field.
     *
     * @return
     *  String containing the WorkOrderAssocStatusCd field.
     */
    public String getWorkOrderAssocStatusCd(){
        return mWorkOrderAssocStatusCd;
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
