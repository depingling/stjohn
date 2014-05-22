
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        KnowledgeMetaData
 * Description:  This is a ValueObject class wrapping the database table CLW_KNOWLEDGE_META.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.KnowledgeMetaDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>KnowledgeMetaData</code> is a ValueObject class wrapping of the database table CLW_KNOWLEDGE_META.
 */
public class KnowledgeMetaData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4426519602663315211L;
    private int mKnowledgeMetaId;// SQL type:NUMBER, not null
    private int mKnowledgeId;// SQL type:NUMBER, not null
    private int mValueId;// SQL type:NUMBER, not null
    private String mNameValue;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public KnowledgeMetaData ()
    {
        mNameValue = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public KnowledgeMetaData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mKnowledgeMetaId = parm1;
        mKnowledgeId = parm2;
        mValueId = parm3;
        mNameValue = parm4;
        mValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new KnowledgeMetaData
     *
     * @return
     *  Newly initialized KnowledgeMetaData object.
     */
    public static KnowledgeMetaData createValue ()
    {
        KnowledgeMetaData valueData = new KnowledgeMetaData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this KnowledgeMetaData object
     */
    public String toString()
    {
        return "[" + "KnowledgeMetaId=" + mKnowledgeMetaId + ", KnowledgeId=" + mKnowledgeId + ", ValueId=" + mValueId + ", NameValue=" + mNameValue + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("KnowledgeMeta");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mKnowledgeMetaId));

        node =  doc.createElement("KnowledgeId");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgeId)));
        root.appendChild(node);

        node =  doc.createElement("ValueId");
        node.appendChild(doc.createTextNode(String.valueOf(mValueId)));
        root.appendChild(node);

        node =  doc.createElement("NameValue");
        node.appendChild(doc.createTextNode(String.valueOf(mNameValue)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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
    * creates a clone of this object, the KnowledgeMetaId field is not cloned.
    *
    * @return KnowledgeMetaData object
    */
    public Object clone(){
        KnowledgeMetaData myClone = new KnowledgeMetaData();
        
        myClone.mKnowledgeId = mKnowledgeId;
        
        myClone.mValueId = mValueId;
        
        myClone.mNameValue = mNameValue;
        
        myClone.mValue = mValue;
        
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

        if (KnowledgeMetaDataAccess.KNOWLEDGE_META_ID.equals(pFieldName)) {
            return getKnowledgeMetaId();
        } else if (KnowledgeMetaDataAccess.KNOWLEDGE_ID.equals(pFieldName)) {
            return getKnowledgeId();
        } else if (KnowledgeMetaDataAccess.VALUE_ID.equals(pFieldName)) {
            return getValueId();
        } else if (KnowledgeMetaDataAccess.NAME_VALUE.equals(pFieldName)) {
            return getNameValue();
        } else if (KnowledgeMetaDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (KnowledgeMetaDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (KnowledgeMetaDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (KnowledgeMetaDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (KnowledgeMetaDataAccess.MOD_BY.equals(pFieldName)) {
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
        return KnowledgeMetaDataAccess.CLW_KNOWLEDGE_META;
    }

    
    /**
     * Sets the KnowledgeMetaId field. This field is required to be set in the database.
     *
     * @param pKnowledgeMetaId
     *  int to use to update the field.
     */
    public void setKnowledgeMetaId(int pKnowledgeMetaId){
        this.mKnowledgeMetaId = pKnowledgeMetaId;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgeMetaId field.
     *
     * @return
     *  int containing the KnowledgeMetaId field.
     */
    public int getKnowledgeMetaId(){
        return mKnowledgeMetaId;
    }

    /**
     * Sets the KnowledgeId field. This field is required to be set in the database.
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
     * Sets the ValueId field. This field is required to be set in the database.
     *
     * @param pValueId
     *  int to use to update the field.
     */
    public void setValueId(int pValueId){
        this.mValueId = pValueId;
        setDirty(true);
    }
    /**
     * Retrieves the ValueId field.
     *
     * @return
     *  int containing the ValueId field.
     */
    public int getValueId(){
        return mValueId;
    }

    /**
     * Sets the NameValue field.
     *
     * @param pNameValue
     *  String to use to update the field.
     */
    public void setNameValue(String pNameValue){
        this.mNameValue = pNameValue;
        setDirty(true);
    }
    /**
     * Retrieves the NameValue field.
     *
     * @return
     *  String containing the NameValue field.
     */
    public String getNameValue(){
        return mNameValue;
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
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
