
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        KnowledgePropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_KNOWLEDGE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.KnowledgePropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>KnowledgePropertyData</code> is a ValueObject class wrapping of the database table CLW_KNOWLEDGE_PROPERTY.
 */
public class KnowledgePropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5633502527086635905L;
    private int mKnowledgePropertyId;// SQL type:NUMBER, not null
    private int mKnowledgeId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mKnowledgePropertyStatusCd;// SQL type:VARCHAR2, not null
    private String mKnowledgePropertyTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private Date mAddTime;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public KnowledgePropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mKnowledgePropertyStatusCd = "";
        mKnowledgePropertyTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public KnowledgePropertyData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mKnowledgePropertyId = parm1;
        mKnowledgeId = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mKnowledgePropertyStatusCd = parm5;
        mKnowledgePropertyTypeCd = parm6;
        mAddDate = parm7;
        mAddTime = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new KnowledgePropertyData
     *
     * @return
     *  Newly initialized KnowledgePropertyData object.
     */
    public static KnowledgePropertyData createValue ()
    {
        KnowledgePropertyData valueData = new KnowledgePropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this KnowledgePropertyData object
     */
    public String toString()
    {
        return "[" + "KnowledgePropertyId=" + mKnowledgePropertyId + ", KnowledgeId=" + mKnowledgeId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", KnowledgePropertyStatusCd=" + mKnowledgePropertyStatusCd + ", KnowledgePropertyTypeCd=" + mKnowledgePropertyTypeCd + ", AddDate=" + mAddDate + ", AddTime=" + mAddTime + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("KnowledgeProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mKnowledgePropertyId));

        node =  doc.createElement("KnowledgeId");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgeId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("KnowledgePropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgePropertyStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("KnowledgePropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgePropertyTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddTime");
        node.appendChild(doc.createTextNode(String.valueOf(mAddTime)));
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
    * creates a clone of this object, the KnowledgePropertyId field is not cloned.
    *
    * @return KnowledgePropertyData object
    */
    public Object clone(){
        KnowledgePropertyData myClone = new KnowledgePropertyData();
        
        myClone.mKnowledgeId = mKnowledgeId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mKnowledgePropertyStatusCd = mKnowledgePropertyStatusCd;
        
        myClone.mKnowledgePropertyTypeCd = mKnowledgePropertyTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        if(mAddTime != null){
                myClone.mAddTime = (Date) mAddTime.clone();
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

        if (KnowledgePropertyDataAccess.KNOWLEDGE_PROPERTY_ID.equals(pFieldName)) {
            return getKnowledgePropertyId();
        } else if (KnowledgePropertyDataAccess.KNOWLEDGE_ID.equals(pFieldName)) {
            return getKnowledgeId();
        } else if (KnowledgePropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (KnowledgePropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (KnowledgePropertyDataAccess.KNOWLEDGE_PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getKnowledgePropertyStatusCd();
        } else if (KnowledgePropertyDataAccess.KNOWLEDGE_PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getKnowledgePropertyTypeCd();
        } else if (KnowledgePropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (KnowledgePropertyDataAccess.ADD_TIME.equals(pFieldName)) {
            return getAddTime();
        } else if (KnowledgePropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (KnowledgePropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (KnowledgePropertyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return KnowledgePropertyDataAccess.CLW_KNOWLEDGE_PROPERTY;
    }

    
    /**
     * Sets the KnowledgePropertyId field. This field is required to be set in the database.
     *
     * @param pKnowledgePropertyId
     *  int to use to update the field.
     */
    public void setKnowledgePropertyId(int pKnowledgePropertyId){
        this.mKnowledgePropertyId = pKnowledgePropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgePropertyId field.
     *
     * @return
     *  int containing the KnowledgePropertyId field.
     */
    public int getKnowledgePropertyId(){
        return mKnowledgePropertyId;
    }

    /**
     * Sets the KnowledgeId field.
     *
     * @param pKnowledgeId
     *  int to use to update the field.
     */
    public void setKnowledgeId(int pKnowledgeId){
        this.mKnowledgeId = pKnowledgeId;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgeId field.
     *
     * @return
     *  int containing the KnowledgeId field.
     */
    public int getKnowledgeId(){
        return mKnowledgeId;
    }

    /**
     * Sets the ShortDesc field.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
    }

    /**
     * Sets the KnowledgePropertyStatusCd field. This field is required to be set in the database.
     *
     * @param pKnowledgePropertyStatusCd
     *  String to use to update the field.
     */
    public void setKnowledgePropertyStatusCd(String pKnowledgePropertyStatusCd){
        this.mKnowledgePropertyStatusCd = pKnowledgePropertyStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgePropertyStatusCd field.
     *
     * @return
     *  String containing the KnowledgePropertyStatusCd field.
     */
    public String getKnowledgePropertyStatusCd(){
        return mKnowledgePropertyStatusCd;
    }

    /**
     * Sets the KnowledgePropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pKnowledgePropertyTypeCd
     *  String to use to update the field.
     */
    public void setKnowledgePropertyTypeCd(String pKnowledgePropertyTypeCd){
        this.mKnowledgePropertyTypeCd = pKnowledgePropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgePropertyTypeCd field.
     *
     * @return
     *  String containing the KnowledgePropertyTypeCd field.
     */
    public String getKnowledgePropertyTypeCd(){
        return mKnowledgePropertyTypeCd;
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
     * Sets the AddTime field. This field is required to be set in the database.
     *
     * @param pAddTime
     *  Date to use to update the field.
     */
    public void setAddTime(Date pAddTime){
        this.mAddTime = pAddTime;
        setDirty(true);
    }
    /**
     * Retrieves the AddTime field.
     *
     * @return
     *  Date containing the AddTime field.
     */
    public Date getAddTime(){
        return mAddTime;
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
