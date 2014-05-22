
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        KeywordData
 * Description:  This is a ValueObject class wrapping the database table CLW_KEYWORD.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.KeywordDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>KeywordData</code> is a ValueObject class wrapping of the database table CLW_KEYWORD.
 */
public class KeywordData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 9118853333885225243L;
    private int mKeywordId;// SQL type:NUMBER, not null
    private String mKeyword;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private boolean mGeneratedInd;// SQL type:NUMBER
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mLocale;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public KeywordData ()
    {
        mKeyword = "";
        mValue = "";
        mModBy = "";
        mLocale = "";
    }

    /**
     * Constructor.
     */
    public KeywordData(int parm1, String parm2, String parm3, boolean parm4, Date parm5, String parm6, String parm7)
    {
        mKeywordId = parm1;
        mKeyword = parm2;
        mValue = parm3;
        mGeneratedInd = parm4;
        mModDate = parm5;
        mModBy = parm6;
        mLocale = parm7;
        
    }

    /**
     * Creates a new KeywordData
     *
     * @return
     *  Newly initialized KeywordData object.
     */
    public static KeywordData createValue ()
    {
        KeywordData valueData = new KeywordData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this KeywordData object
     */
    public String toString()
    {
        return "[" + "KeywordId=" + mKeywordId + ", Keyword=" + mKeyword + ", Value=" + mValue + ", GeneratedInd=" + mGeneratedInd + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Locale=" + mLocale + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Keyword");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mKeywordId));

        node =  doc.createElement("Keyword");
        node.appendChild(doc.createTextNode(String.valueOf(mKeyword)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("GeneratedInd");
        node.appendChild(doc.createTextNode(String.valueOf(mGeneratedInd)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("Locale");
        node.appendChild(doc.createTextNode(String.valueOf(mLocale)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the KeywordId field is not cloned.
    *
    * @return KeywordData object
    */
    public Object clone(){
        KeywordData myClone = new KeywordData();
        
        myClone.mKeyword = mKeyword;
        
        myClone.mValue = mValue;
        
        myClone.mGeneratedInd = mGeneratedInd;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mLocale = mLocale;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (KeywordDataAccess.KEYWORD_ID.equals(pFieldName)) {
            return getKeywordId();
        } else if (KeywordDataAccess.KEYWORD.equals(pFieldName)) {
            return getKeyword();
        } else if (KeywordDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (KeywordDataAccess.GENERATED_IND.equals(pFieldName)) {
            return getGeneratedInd();
        } else if (KeywordDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (KeywordDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (KeywordDataAccess.LOCALE.equals(pFieldName)) {
            return getLocale();
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
        return KeywordDataAccess.CLW_KEYWORD;
    }

    
    /**
     * Sets the KeywordId field. This field is required to be set in the database.
     *
     * @param pKeywordId
     *  int to use to update the field.
     */
    public void setKeywordId(int pKeywordId){
        this.mKeywordId = pKeywordId;
        setDirty(true);
    }
    /**
     * Retrieves the KeywordId field.
     *
     * @return
     *  int containing the KeywordId field.
     */
    public int getKeywordId(){
        return mKeywordId;
    }

    /**
     * Sets the Keyword field. This field is required to be set in the database.
     *
     * @param pKeyword
     *  String to use to update the field.
     */
    public void setKeyword(String pKeyword){
        this.mKeyword = pKeyword;
        setDirty(true);
    }
    /**
     * Retrieves the Keyword field.
     *
     * @return
     *  String containing the Keyword field.
     */
    public String getKeyword(){
        return mKeyword;
    }

    /**
     * Sets the Value field. This field is required to be set in the database.
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
     * Sets the GeneratedInd field.
     *
     * @param pGeneratedInd
     *  boolean to use to update the field.
     */
    public void setGeneratedInd(boolean pGeneratedInd){
        this.mGeneratedInd = pGeneratedInd;
        setDirty(true);
    }
    /**
     * Retrieves the GeneratedInd field.
     *
     * @return
     *  boolean containing the GeneratedInd field.
     */
    public boolean getGeneratedInd(){
        return mGeneratedInd;
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

    /**
     * Sets the Locale field.
     *
     * @param pLocale
     *  String to use to update the field.
     */
    public void setLocale(String pLocale){
        this.mLocale = pLocale;
        setDirty(true);
    }
    /**
     * Retrieves the Locale field.
     *
     * @return
     *  String containing the Locale field.
     */
    public String getLocale(){
        return mLocale;
    }


}
