
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InterchangeData
 * Description:  This is a ValueObject class wrapping the database table CLW_INTERCHANGE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InterchangeDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InterchangeData</code> is a ValueObject class wrapping of the database table CLW_INTERCHANGE.
 */
public class InterchangeData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4791323860643816533L;
    private int mInterchangeId;// SQL type:NUMBER, not null
    private int mTradingProfileId;// SQL type:NUMBER
    private String mInterchangeType;// SQL type:VARCHAR2
    private String mInterchangeSender;// SQL type:VARCHAR2
    private String mInterchangeReceiver;// SQL type:VARCHAR2
    private int mInterchangeControlNum;// SQL type:NUMBER, not null
    private String mTestInd;// SQL type:VARCHAR2
    private String mEdiFileName;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InterchangeData ()
    {
        mInterchangeType = "";
        mInterchangeSender = "";
        mInterchangeReceiver = "";
        mTestInd = "";
        mEdiFileName = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public InterchangeData(int parm1, int parm2, String parm3, String parm4, String parm5, int parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12)
    {
        mInterchangeId = parm1;
        mTradingProfileId = parm2;
        mInterchangeType = parm3;
        mInterchangeSender = parm4;
        mInterchangeReceiver = parm5;
        mInterchangeControlNum = parm6;
        mTestInd = parm7;
        mEdiFileName = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        
    }

    /**
     * Creates a new InterchangeData
     *
     * @return
     *  Newly initialized InterchangeData object.
     */
    public static InterchangeData createValue ()
    {
        InterchangeData valueData = new InterchangeData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InterchangeData object
     */
    public String toString()
    {
        return "[" + "InterchangeId=" + mInterchangeId + ", TradingProfileId=" + mTradingProfileId + ", InterchangeType=" + mInterchangeType + ", InterchangeSender=" + mInterchangeSender + ", InterchangeReceiver=" + mInterchangeReceiver + ", InterchangeControlNum=" + mInterchangeControlNum + ", TestInd=" + mTestInd + ", EdiFileName=" + mEdiFileName + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Interchange");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInterchangeId));

        node =  doc.createElement("TradingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileId)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeType");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeType)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeSender");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeSender)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeReceiver");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeReceiver)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeControlNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeControlNum)));
        root.appendChild(node);

        node =  doc.createElement("TestInd");
        node.appendChild(doc.createTextNode(String.valueOf(mTestInd)));
        root.appendChild(node);

        node =  doc.createElement("EdiFileName");
        node.appendChild(doc.createTextNode(String.valueOf(mEdiFileName)));
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
    * creates a clone of this object, the InterchangeId field is not cloned.
    *
    * @return InterchangeData object
    */
    public Object clone(){
        InterchangeData myClone = new InterchangeData();
        
        myClone.mTradingProfileId = mTradingProfileId;
        
        myClone.mInterchangeType = mInterchangeType;
        
        myClone.mInterchangeSender = mInterchangeSender;
        
        myClone.mInterchangeReceiver = mInterchangeReceiver;
        
        myClone.mInterchangeControlNum = mInterchangeControlNum;
        
        myClone.mTestInd = mTestInd;
        
        myClone.mEdiFileName = mEdiFileName;
        
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

        if (InterchangeDataAccess.INTERCHANGE_ID.equals(pFieldName)) {
            return getInterchangeId();
        } else if (InterchangeDataAccess.TRADING_PROFILE_ID.equals(pFieldName)) {
            return getTradingProfileId();
        } else if (InterchangeDataAccess.INTERCHANGE_TYPE.equals(pFieldName)) {
            return getInterchangeType();
        } else if (InterchangeDataAccess.INTERCHANGE_SENDER.equals(pFieldName)) {
            return getInterchangeSender();
        } else if (InterchangeDataAccess.INTERCHANGE_RECEIVER.equals(pFieldName)) {
            return getInterchangeReceiver();
        } else if (InterchangeDataAccess.INTERCHANGE_CONTROL_NUM.equals(pFieldName)) {
            return getInterchangeControlNum();
        } else if (InterchangeDataAccess.TEST_IND.equals(pFieldName)) {
            return getTestInd();
        } else if (InterchangeDataAccess.EDI_FILE_NAME.equals(pFieldName)) {
            return getEdiFileName();
        } else if (InterchangeDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InterchangeDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InterchangeDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InterchangeDataAccess.MOD_BY.equals(pFieldName)) {
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
        return InterchangeDataAccess.CLW_INTERCHANGE;
    }

    
    /**
     * Sets the InterchangeId field. This field is required to be set in the database.
     *
     * @param pInterchangeId
     *  int to use to update the field.
     */
    public void setInterchangeId(int pInterchangeId){
        this.mInterchangeId = pInterchangeId;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeId field.
     *
     * @return
     *  int containing the InterchangeId field.
     */
    public int getInterchangeId(){
        return mInterchangeId;
    }

    /**
     * Sets the TradingProfileId field.
     *
     * @param pTradingProfileId
     *  int to use to update the field.
     */
    public void setTradingProfileId(int pTradingProfileId){
        this.mTradingProfileId = pTradingProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingProfileId field.
     *
     * @return
     *  int containing the TradingProfileId field.
     */
    public int getTradingProfileId(){
        return mTradingProfileId;
    }

    /**
     * Sets the InterchangeType field.
     *
     * @param pInterchangeType
     *  String to use to update the field.
     */
    public void setInterchangeType(String pInterchangeType){
        this.mInterchangeType = pInterchangeType;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeType field.
     *
     * @return
     *  String containing the InterchangeType field.
     */
    public String getInterchangeType(){
        return mInterchangeType;
    }

    /**
     * Sets the InterchangeSender field.
     *
     * @param pInterchangeSender
     *  String to use to update the field.
     */
    public void setInterchangeSender(String pInterchangeSender){
        this.mInterchangeSender = pInterchangeSender;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeSender field.
     *
     * @return
     *  String containing the InterchangeSender field.
     */
    public String getInterchangeSender(){
        return mInterchangeSender;
    }

    /**
     * Sets the InterchangeReceiver field.
     *
     * @param pInterchangeReceiver
     *  String to use to update the field.
     */
    public void setInterchangeReceiver(String pInterchangeReceiver){
        this.mInterchangeReceiver = pInterchangeReceiver;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeReceiver field.
     *
     * @return
     *  String containing the InterchangeReceiver field.
     */
    public String getInterchangeReceiver(){
        return mInterchangeReceiver;
    }

    /**
     * Sets the InterchangeControlNum field. This field is required to be set in the database.
     *
     * @param pInterchangeControlNum
     *  int to use to update the field.
     */
    public void setInterchangeControlNum(int pInterchangeControlNum){
        this.mInterchangeControlNum = pInterchangeControlNum;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeControlNum field.
     *
     * @return
     *  int containing the InterchangeControlNum field.
     */
    public int getInterchangeControlNum(){
        return mInterchangeControlNum;
    }

    /**
     * Sets the TestInd field.
     *
     * @param pTestInd
     *  String to use to update the field.
     */
    public void setTestInd(String pTestInd){
        this.mTestInd = pTestInd;
        setDirty(true);
    }
    /**
     * Retrieves the TestInd field.
     *
     * @return
     *  String containing the TestInd field.
     */
    public String getTestInd(){
        return mTestInd;
    }

    /**
     * Sets the EdiFileName field.
     *
     * @param pEdiFileName
     *  String to use to update the field.
     */
    public void setEdiFileName(String pEdiFileName){
        this.mEdiFileName = pEdiFileName;
        setDirty(true);
    }
    /**
     * Retrieves the EdiFileName field.
     *
     * @return
     *  String containing the EdiFileName field.
     */
    public String getEdiFileName(){
        return mEdiFileName;
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
