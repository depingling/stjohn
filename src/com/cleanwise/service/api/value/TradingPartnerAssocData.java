
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_TRADING_PARTNER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TradingPartnerAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TradingPartnerAssocData</code> is a ValueObject class wrapping of the database table CLW_TRADING_PARTNER_ASSOC.
 */
public class TradingPartnerAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1136723880568143745L;
    private int mTradingPartnerAssocId;// SQL type:NUMBER, not null
    private int mTradingPartnerId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mTradingPartnerAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null
    private String mGroupSenderOverride;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TradingPartnerAssocData ()
    {
        mTradingPartnerAssocCd = "";
        mAddBy = "";
        mModBy = "";
        mGroupSenderOverride = "";
    }

    /**
     * Constructor.
     */
    public TradingPartnerAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9)
    {
        mTradingPartnerAssocId = parm1;
        mTradingPartnerId = parm2;
        mBusEntityId = parm3;
        mTradingPartnerAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mGroupSenderOverride = parm9;
        
    }

    /**
     * Creates a new TradingPartnerAssocData
     *
     * @return
     *  Newly initialized TradingPartnerAssocData object.
     */
    public static TradingPartnerAssocData createValue ()
    {
        TradingPartnerAssocData valueData = new TradingPartnerAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPartnerAssocData object
     */
    public String toString()
    {
        return "[" + "TradingPartnerAssocId=" + mTradingPartnerAssocId + ", TradingPartnerId=" + mTradingPartnerId + ", BusEntityId=" + mBusEntityId + ", TradingPartnerAssocCd=" + mTradingPartnerAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", GroupSenderOverride=" + mGroupSenderOverride + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TradingPartnerAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTradingPartnerAssocId));

        node =  doc.createElement("TradingPartnerId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("TradingPartnerAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerAssocCd)));
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

        node =  doc.createElement("GroupSenderOverride");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupSenderOverride)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TradingPartnerAssocId field is not cloned.
    *
    * @return TradingPartnerAssocData object
    */
    public Object clone(){
        TradingPartnerAssocData myClone = new TradingPartnerAssocData();
        
        myClone.mTradingPartnerId = mTradingPartnerId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mTradingPartnerAssocCd = mTradingPartnerAssocCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mGroupSenderOverride = mGroupSenderOverride;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_ID.equals(pFieldName)) {
            return getTradingPartnerAssocId();
        } else if (TradingPartnerAssocDataAccess.TRADING_PARTNER_ID.equals(pFieldName)) {
            return getTradingPartnerId();
        } else if (TradingPartnerAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD.equals(pFieldName)) {
            return getTradingPartnerAssocCd();
        } else if (TradingPartnerAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TradingPartnerAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TradingPartnerAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TradingPartnerAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TradingPartnerAssocDataAccess.GROUP_SENDER_OVERRIDE.equals(pFieldName)) {
            return getGroupSenderOverride();
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
        return TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC;
    }

    
    /**
     * Sets the TradingPartnerAssocId field. This field is required to be set in the database.
     *
     * @param pTradingPartnerAssocId
     *  int to use to update the field.
     */
    public void setTradingPartnerAssocId(int pTradingPartnerAssocId){
        this.mTradingPartnerAssocId = pTradingPartnerAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerAssocId field.
     *
     * @return
     *  int containing the TradingPartnerAssocId field.
     */
    public int getTradingPartnerAssocId(){
        return mTradingPartnerAssocId;
    }

    /**
     * Sets the TradingPartnerId field. This field is required to be set in the database.
     *
     * @param pTradingPartnerId
     *  int to use to update the field.
     */
    public void setTradingPartnerId(int pTradingPartnerId){
        this.mTradingPartnerId = pTradingPartnerId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerId field.
     *
     * @return
     *  int containing the TradingPartnerId field.
     */
    public int getTradingPartnerId(){
        return mTradingPartnerId;
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
     * Sets the TradingPartnerAssocCd field. This field is required to be set in the database.
     *
     * @param pTradingPartnerAssocCd
     *  String to use to update the field.
     */
    public void setTradingPartnerAssocCd(String pTradingPartnerAssocCd){
        this.mTradingPartnerAssocCd = pTradingPartnerAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerAssocCd field.
     *
     * @return
     *  String containing the TradingPartnerAssocCd field.
     */
    public String getTradingPartnerAssocCd(){
        return mTradingPartnerAssocCd;
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
     * Sets the AddBy field. This field is required to be set in the database.
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
     * Sets the ModBy field. This field is required to be set in the database.
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
     * Sets the GroupSenderOverride field.
     *
     * @param pGroupSenderOverride
     *  String to use to update the field.
     */
    public void setGroupSenderOverride(String pGroupSenderOverride){
        this.mGroupSenderOverride = pGroupSenderOverride;
        setDirty(true);
    }
    /**
     * Retrieves the GroupSenderOverride field.
     *
     * @return
     *  String containing the GroupSenderOverride field.
     */
    public String getGroupSenderOverride(){
        return mGroupSenderOverride;
    }


}
