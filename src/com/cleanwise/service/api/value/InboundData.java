
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InboundData
 * Description:  This is a ValueObject class wrapping the database table CLW_INBOUND.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InboundDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InboundData</code> is a ValueObject class wrapping of the database table CLW_INBOUND.
 */
public class InboundData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5070238930732720542L;
    private int mInboundId;// SQL type:NUMBER, not null
    private String mFileName;// SQL type:VARCHAR2, not null
    private String mPartnerKey;// SQL type:VARCHAR2
    private String mUrl;// SQL type:VARCHAR2
    private byte[] mEncryptBinaryData;// SQL type:BLOB
    private byte[] mDecryptBinaryData;// SQL type:BLOB
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InboundData ()
    {
        mFileName = "";
        mPartnerKey = "";
        mUrl = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public InboundData(int parm1, String parm2, String parm3, String parm4, byte[] parm5, byte[] parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mInboundId = parm1;
        mFileName = parm2;
        mPartnerKey = parm3;
        mUrl = parm4;
        mEncryptBinaryData = parm5;
        mDecryptBinaryData = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new InboundData
     *
     * @return
     *  Newly initialized InboundData object.
     */
    public static InboundData createValue ()
    {
        InboundData valueData = new InboundData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InboundData object
     */
    public String toString()
    {
        return "[" + "InboundId=" + mInboundId + ", FileName=" + mFileName + ", PartnerKey=" + mPartnerKey + ", Url=" + mUrl + ", EncryptBinaryData=" + mEncryptBinaryData + ", DecryptBinaryData=" + mDecryptBinaryData + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Inbound");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInboundId));

        node =  doc.createElement("FileName");
        node.appendChild(doc.createTextNode(String.valueOf(mFileName)));
        root.appendChild(node);

        node =  doc.createElement("PartnerKey");
        node.appendChild(doc.createTextNode(String.valueOf(mPartnerKey)));
        root.appendChild(node);

        node =  doc.createElement("Url");
        node.appendChild(doc.createTextNode(String.valueOf(mUrl)));
        root.appendChild(node);

        node =  doc.createElement("EncryptBinaryData");
        node.appendChild(doc.createTextNode(String.valueOf(mEncryptBinaryData)));
        root.appendChild(node);

        node =  doc.createElement("DecryptBinaryData");
        node.appendChild(doc.createTextNode(String.valueOf(mDecryptBinaryData)));
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
    * creates a clone of this object, the InboundId field is not cloned.
    *
    * @return InboundData object
    */
    public Object clone(){
        InboundData myClone = new InboundData();
        
        myClone.mFileName = mFileName;
        
        myClone.mPartnerKey = mPartnerKey;
        
        myClone.mUrl = mUrl;
        
        myClone.mEncryptBinaryData = mEncryptBinaryData;
        
        myClone.mDecryptBinaryData = mDecryptBinaryData;
        
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

        if (InboundDataAccess.INBOUND_ID.equals(pFieldName)) {
            return getInboundId();
        } else if (InboundDataAccess.FILE_NAME.equals(pFieldName)) {
            return getFileName();
        } else if (InboundDataAccess.PARTNER_KEY.equals(pFieldName)) {
            return getPartnerKey();
        } else if (InboundDataAccess.URL.equals(pFieldName)) {
            return getUrl();
        } else if (InboundDataAccess.ENCRYPT_BINARY_DATA.equals(pFieldName)) {
            return getEncryptBinaryData();
        } else if (InboundDataAccess.DECRYPT_BINARY_DATA.equals(pFieldName)) {
            return getDecryptBinaryData();
        } else if (InboundDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InboundDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InboundDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InboundDataAccess.MOD_BY.equals(pFieldName)) {
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
        return InboundDataAccess.CLW_INBOUND;
    }

    
    /**
     * Sets the InboundId field. This field is required to be set in the database.
     *
     * @param pInboundId
     *  int to use to update the field.
     */
    public void setInboundId(int pInboundId){
        this.mInboundId = pInboundId;
        setDirty(true);
    }
    /**
     * Retrieves the InboundId field.
     *
     * @return
     *  int containing the InboundId field.
     */
    public int getInboundId(){
        return mInboundId;
    }

    /**
     * Sets the FileName field. This field is required to be set in the database.
     *
     * @param pFileName
     *  String to use to update the field.
     */
    public void setFileName(String pFileName){
        this.mFileName = pFileName;
        setDirty(true);
    }
    /**
     * Retrieves the FileName field.
     *
     * @return
     *  String containing the FileName field.
     */
    public String getFileName(){
        return mFileName;
    }

    /**
     * Sets the PartnerKey field.
     *
     * @param pPartnerKey
     *  String to use to update the field.
     */
    public void setPartnerKey(String pPartnerKey){
        this.mPartnerKey = pPartnerKey;
        setDirty(true);
    }
    /**
     * Retrieves the PartnerKey field.
     *
     * @return
     *  String containing the PartnerKey field.
     */
    public String getPartnerKey(){
        return mPartnerKey;
    }

    /**
     * Sets the Url field.
     *
     * @param pUrl
     *  String to use to update the field.
     */
    public void setUrl(String pUrl){
        this.mUrl = pUrl;
        setDirty(true);
    }
    /**
     * Retrieves the Url field.
     *
     * @return
     *  String containing the Url field.
     */
    public String getUrl(){
        return mUrl;
    }

    /**
     * Sets the EncryptBinaryData field.
     *
     * @param pEncryptBinaryData
     *  byte[] to use to update the field.
     */
    public void setEncryptBinaryData(byte[] pEncryptBinaryData){
        this.mEncryptBinaryData = pEncryptBinaryData;
        setDirty(true);
    }
    /**
     * Retrieves the EncryptBinaryData field.
     *
     * @return
     *  byte[] containing the EncryptBinaryData field.
     */
    public byte[] getEncryptBinaryData(){
        return mEncryptBinaryData;
    }

    /**
     * Sets the DecryptBinaryData field.
     *
     * @param pDecryptBinaryData
     *  byte[] to use to update the field.
     */
    public void setDecryptBinaryData(byte[] pDecryptBinaryData){
        this.mDecryptBinaryData = pDecryptBinaryData;
        setDirty(true);
    }
    /**
     * Retrieves the DecryptBinaryData field.
     *
     * @return
     *  byte[] containing the DecryptBinaryData field.
     */
    public byte[] getDecryptBinaryData(){
        return mDecryptBinaryData;
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
