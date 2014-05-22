
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        HistoryData
 * Description:  This is a ValueObject class wrapping the database table CLW_HISTORY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.HistoryDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>HistoryData</code> is a ValueObject class wrapping of the database table CLW_HISTORY.
 */
public class HistoryData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mHistoryId;// SQL type:NUMBER, not null
    private String mHistoryTypeCd;// SQL type:VARCHAR2, not null
    private String mUserId;// SQL type:VARCHAR2, not null
    private Date mActivityDate;// SQL type:DATE, not null
    private String mAttribute01;// SQL type:VARCHAR2
    private String mAttribute02;// SQL type:VARCHAR2
    private String mAttribute03;// SQL type:VARCHAR2
    private String mAttribute04;// SQL type:VARCHAR2
    private String mAttribute05;// SQL type:VARCHAR2
    private String mAttribute06;// SQL type:VARCHAR2
    private String mAttribute07;// SQL type:VARCHAR2
    private String mAttribute08;// SQL type:VARCHAR2
    private String mAttribute09;// SQL type:VARCHAR2
    private String mAttribute10;// SQL type:VARCHAR2
    private String mAttribute11;// SQL type:VARCHAR2
    private String mAttribute12;// SQL type:VARCHAR2
    private String mAttribute13;// SQL type:VARCHAR2
    private String mAttribute14;// SQL type:VARCHAR2
    private String mAttribute15;// SQL type:VARCHAR2
    private String mAttribute16;// SQL type:VARCHAR2
    private String mAttribute17;// SQL type:VARCHAR2
    private String mAttribute18;// SQL type:VARCHAR2
    private String mAttribute19;// SQL type:VARCHAR2
    private String mAttribute20;// SQL type:VARCHAR2
    private String mAttribute21;// SQL type:VARCHAR2
    private String mAttribute22;// SQL type:VARCHAR2
    private String mAttribute23;// SQL type:VARCHAR2
    private String mAttribute24;// SQL type:VARCHAR2
    private String mAttribute25;// SQL type:VARCHAR2
    private String mAttribute26;// SQL type:VARCHAR2
    private String mAttribute27;// SQL type:VARCHAR2
    private String mAttribute28;// SQL type:VARCHAR2
    private String mAttribute29;// SQL type:VARCHAR2
    private String mAttribute30;// SQL type:VARCHAR2
    private String mAttribute31;// SQL type:VARCHAR2
    private String mAttribute32;// SQL type:VARCHAR2
    private String mAttribute33;// SQL type:VARCHAR2
    private String mAttribute34;// SQL type:VARCHAR2
    private String mAttribute35;// SQL type:VARCHAR2
    private String mClob1;// SQL type:CLOB
    private String mClob2;// SQL type:CLOB
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public HistoryData ()
    {
        mHistoryTypeCd = "";
        mUserId = "";
        mAttribute01 = "";
        mAttribute02 = "";
        mAttribute03 = "";
        mAttribute04 = "";
        mAttribute05 = "";
        mAttribute06 = "";
        mAttribute07 = "";
        mAttribute08 = "";
        mAttribute09 = "";
        mAttribute10 = "";
        mAttribute11 = "";
        mAttribute12 = "";
        mAttribute13 = "";
        mAttribute14 = "";
        mAttribute15 = "";
        mAttribute16 = "";
        mAttribute17 = "";
        mAttribute18 = "";
        mAttribute19 = "";
        mAttribute20 = "";
        mAttribute21 = "";
        mAttribute22 = "";
        mAttribute23 = "";
        mAttribute24 = "";
        mAttribute25 = "";
        mAttribute26 = "";
        mAttribute27 = "";
        mAttribute28 = "";
        mAttribute29 = "";
        mAttribute30 = "";
        mAttribute31 = "";
        mAttribute32 = "";
        mAttribute33 = "";
        mAttribute34 = "";
        mAttribute35 = "";
        mClob1 = "";
        mClob2 = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public HistoryData(int parm1, String parm2, String parm3, Date parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, String parm28, String parm29, String parm30, String parm31, String parm32, String parm33, String parm34, String parm35, String parm36, String parm37, String parm38, String parm39, String parm40, String parm41, String parm42, Date parm43, String parm44, Date parm45)
    {
        mHistoryId = parm1;
        mHistoryTypeCd = parm2;
        mUserId = parm3;
        mActivityDate = parm4;
        mAttribute01 = parm5;
        mAttribute02 = parm6;
        mAttribute03 = parm7;
        mAttribute04 = parm8;
        mAttribute05 = parm9;
        mAttribute06 = parm10;
        mAttribute07 = parm11;
        mAttribute08 = parm12;
        mAttribute09 = parm13;
        mAttribute10 = parm14;
        mAttribute11 = parm15;
        mAttribute12 = parm16;
        mAttribute13 = parm17;
        mAttribute14 = parm18;
        mAttribute15 = parm19;
        mAttribute16 = parm20;
        mAttribute17 = parm21;
        mAttribute18 = parm22;
        mAttribute19 = parm23;
        mAttribute20 = parm24;
        mAttribute21 = parm25;
        mAttribute22 = parm26;
        mAttribute23 = parm27;
        mAttribute24 = parm28;
        mAttribute25 = parm29;
        mAttribute26 = parm30;
        mAttribute27 = parm31;
        mAttribute28 = parm32;
        mAttribute29 = parm33;
        mAttribute30 = parm34;
        mAttribute31 = parm35;
        mAttribute32 = parm36;
        mAttribute33 = parm37;
        mAttribute34 = parm38;
        mAttribute35 = parm39;
        mClob1 = parm40;
        mClob2 = parm41;
        mAddBy = parm42;
        mAddDate = parm43;
        mModBy = parm44;
        mModDate = parm45;
        
    }

    /**
     * Creates a new HistoryData
     *
     * @return
     *  Newly initialized HistoryData object.
     */
    public static HistoryData createValue ()
    {
        HistoryData valueData = new HistoryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this HistoryData object
     */
    public String toString()
    {
        return "[" + "HistoryId=" + mHistoryId + ", HistoryTypeCd=" + mHistoryTypeCd + ", UserId=" + mUserId + ", ActivityDate=" + mActivityDate + ", Attribute01=" + mAttribute01 + ", Attribute02=" + mAttribute02 + ", Attribute03=" + mAttribute03 + ", Attribute04=" + mAttribute04 + ", Attribute05=" + mAttribute05 + ", Attribute06=" + mAttribute06 + ", Attribute07=" + mAttribute07 + ", Attribute08=" + mAttribute08 + ", Attribute09=" + mAttribute09 + ", Attribute10=" + mAttribute10 + ", Attribute11=" + mAttribute11 + ", Attribute12=" + mAttribute12 + ", Attribute13=" + mAttribute13 + ", Attribute14=" + mAttribute14 + ", Attribute15=" + mAttribute15 + ", Attribute16=" + mAttribute16 + ", Attribute17=" + mAttribute17 + ", Attribute18=" + mAttribute18 + ", Attribute19=" + mAttribute19 + ", Attribute20=" + mAttribute20 + ", Attribute21=" + mAttribute21 + ", Attribute22=" + mAttribute22 + ", Attribute23=" + mAttribute23 + ", Attribute24=" + mAttribute24 + ", Attribute25=" + mAttribute25 + ", Attribute26=" + mAttribute26 + ", Attribute27=" + mAttribute27 + ", Attribute28=" + mAttribute28 + ", Attribute29=" + mAttribute29 + ", Attribute30=" + mAttribute30 + ", Attribute31=" + mAttribute31 + ", Attribute32=" + mAttribute32 + ", Attribute33=" + mAttribute33 + ", Attribute34=" + mAttribute34 + ", Attribute35=" + mAttribute35 + ", Clob1=" + mClob1 + ", Clob2=" + mClob2 + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("History");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mHistoryId));

        node =  doc.createElement("HistoryTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mHistoryTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("ActivityDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActivityDate)));
        root.appendChild(node);

        node =  doc.createElement("Attribute01");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute01)));
        root.appendChild(node);

        node =  doc.createElement("Attribute02");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute02)));
        root.appendChild(node);

        node =  doc.createElement("Attribute03");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute03)));
        root.appendChild(node);

        node =  doc.createElement("Attribute04");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute04)));
        root.appendChild(node);

        node =  doc.createElement("Attribute05");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute05)));
        root.appendChild(node);

        node =  doc.createElement("Attribute06");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute06)));
        root.appendChild(node);

        node =  doc.createElement("Attribute07");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute07)));
        root.appendChild(node);

        node =  doc.createElement("Attribute08");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute08)));
        root.appendChild(node);

        node =  doc.createElement("Attribute09");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute09)));
        root.appendChild(node);

        node =  doc.createElement("Attribute10");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute10)));
        root.appendChild(node);

        node =  doc.createElement("Attribute11");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute11)));
        root.appendChild(node);

        node =  doc.createElement("Attribute12");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute12)));
        root.appendChild(node);

        node =  doc.createElement("Attribute13");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute13)));
        root.appendChild(node);

        node =  doc.createElement("Attribute14");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute14)));
        root.appendChild(node);

        node =  doc.createElement("Attribute15");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute15)));
        root.appendChild(node);

        node =  doc.createElement("Attribute16");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute16)));
        root.appendChild(node);

        node =  doc.createElement("Attribute17");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute17)));
        root.appendChild(node);

        node =  doc.createElement("Attribute18");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute18)));
        root.appendChild(node);

        node =  doc.createElement("Attribute19");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute19)));
        root.appendChild(node);

        node =  doc.createElement("Attribute20");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute20)));
        root.appendChild(node);

        node =  doc.createElement("Attribute21");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute21)));
        root.appendChild(node);

        node =  doc.createElement("Attribute22");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute22)));
        root.appendChild(node);

        node =  doc.createElement("Attribute23");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute23)));
        root.appendChild(node);

        node =  doc.createElement("Attribute24");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute24)));
        root.appendChild(node);

        node =  doc.createElement("Attribute25");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute25)));
        root.appendChild(node);

        node =  doc.createElement("Attribute26");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute26)));
        root.appendChild(node);

        node =  doc.createElement("Attribute27");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute27)));
        root.appendChild(node);

        node =  doc.createElement("Attribute28");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute28)));
        root.appendChild(node);

        node =  doc.createElement("Attribute29");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute29)));
        root.appendChild(node);

        node =  doc.createElement("Attribute30");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute30)));
        root.appendChild(node);

        node =  doc.createElement("Attribute31");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute31)));
        root.appendChild(node);

        node =  doc.createElement("Attribute32");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute32)));
        root.appendChild(node);

        node =  doc.createElement("Attribute33");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute33)));
        root.appendChild(node);

        node =  doc.createElement("Attribute34");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute34)));
        root.appendChild(node);

        node =  doc.createElement("Attribute35");
        node.appendChild(doc.createTextNode(String.valueOf(mAttribute35)));
        root.appendChild(node);

        node =  doc.createElement("Clob1");
        node.appendChild(doc.createTextNode(String.valueOf(mClob1)));
        root.appendChild(node);

        node =  doc.createElement("Clob2");
        node.appendChild(doc.createTextNode(String.valueOf(mClob2)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the HistoryId field is not cloned.
    *
    * @return HistoryData object
    */
    public Object clone(){
        HistoryData myClone = new HistoryData();
        
        myClone.mHistoryTypeCd = mHistoryTypeCd;
        
        myClone.mUserId = mUserId;
        
        if(mActivityDate != null){
                myClone.mActivityDate = (Date) mActivityDate.clone();
        }
        
        myClone.mAttribute01 = mAttribute01;
        
        myClone.mAttribute02 = mAttribute02;
        
        myClone.mAttribute03 = mAttribute03;
        
        myClone.mAttribute04 = mAttribute04;
        
        myClone.mAttribute05 = mAttribute05;
        
        myClone.mAttribute06 = mAttribute06;
        
        myClone.mAttribute07 = mAttribute07;
        
        myClone.mAttribute08 = mAttribute08;
        
        myClone.mAttribute09 = mAttribute09;
        
        myClone.mAttribute10 = mAttribute10;
        
        myClone.mAttribute11 = mAttribute11;
        
        myClone.mAttribute12 = mAttribute12;
        
        myClone.mAttribute13 = mAttribute13;
        
        myClone.mAttribute14 = mAttribute14;
        
        myClone.mAttribute15 = mAttribute15;
        
        myClone.mAttribute16 = mAttribute16;
        
        myClone.mAttribute17 = mAttribute17;
        
        myClone.mAttribute18 = mAttribute18;
        
        myClone.mAttribute19 = mAttribute19;
        
        myClone.mAttribute20 = mAttribute20;
        
        myClone.mAttribute21 = mAttribute21;
        
        myClone.mAttribute22 = mAttribute22;
        
        myClone.mAttribute23 = mAttribute23;
        
        myClone.mAttribute24 = mAttribute24;
        
        myClone.mAttribute25 = mAttribute25;
        
        myClone.mAttribute26 = mAttribute26;
        
        myClone.mAttribute27 = mAttribute27;
        
        myClone.mAttribute28 = mAttribute28;
        
        myClone.mAttribute29 = mAttribute29;
        
        myClone.mAttribute30 = mAttribute30;
        
        myClone.mAttribute31 = mAttribute31;
        
        myClone.mAttribute32 = mAttribute32;
        
        myClone.mAttribute33 = mAttribute33;
        
        myClone.mAttribute34 = mAttribute34;
        
        myClone.mAttribute35 = mAttribute35;
        
        myClone.mClob1 = mClob1;
        
        myClone.mClob2 = mClob2;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (HistoryDataAccess.HISTORY_ID.equals(pFieldName)) {
            return getHistoryId();
        } else if (HistoryDataAccess.HISTORY_TYPE_CD.equals(pFieldName)) {
            return getHistoryTypeCd();
        } else if (HistoryDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (HistoryDataAccess.ACTIVITY_DATE.equals(pFieldName)) {
            return getActivityDate();
        } else if (HistoryDataAccess.ATTRIBUTE01.equals(pFieldName)) {
            return getAttribute01();
        } else if (HistoryDataAccess.ATTRIBUTE02.equals(pFieldName)) {
            return getAttribute02();
        } else if (HistoryDataAccess.ATTRIBUTE03.equals(pFieldName)) {
            return getAttribute03();
        } else if (HistoryDataAccess.ATTRIBUTE04.equals(pFieldName)) {
            return getAttribute04();
        } else if (HistoryDataAccess.ATTRIBUTE05.equals(pFieldName)) {
            return getAttribute05();
        } else if (HistoryDataAccess.ATTRIBUTE06.equals(pFieldName)) {
            return getAttribute06();
        } else if (HistoryDataAccess.ATTRIBUTE07.equals(pFieldName)) {
            return getAttribute07();
        } else if (HistoryDataAccess.ATTRIBUTE08.equals(pFieldName)) {
            return getAttribute08();
        } else if (HistoryDataAccess.ATTRIBUTE09.equals(pFieldName)) {
            return getAttribute09();
        } else if (HistoryDataAccess.ATTRIBUTE10.equals(pFieldName)) {
            return getAttribute10();
        } else if (HistoryDataAccess.ATTRIBUTE11.equals(pFieldName)) {
            return getAttribute11();
        } else if (HistoryDataAccess.ATTRIBUTE12.equals(pFieldName)) {
            return getAttribute12();
        } else if (HistoryDataAccess.ATTRIBUTE13.equals(pFieldName)) {
            return getAttribute13();
        } else if (HistoryDataAccess.ATTRIBUTE14.equals(pFieldName)) {
            return getAttribute14();
        } else if (HistoryDataAccess.ATTRIBUTE15.equals(pFieldName)) {
            return getAttribute15();
        } else if (HistoryDataAccess.ATTRIBUTE16.equals(pFieldName)) {
            return getAttribute16();
        } else if (HistoryDataAccess.ATTRIBUTE17.equals(pFieldName)) {
            return getAttribute17();
        } else if (HistoryDataAccess.ATTRIBUTE18.equals(pFieldName)) {
            return getAttribute18();
        } else if (HistoryDataAccess.ATTRIBUTE19.equals(pFieldName)) {
            return getAttribute19();
        } else if (HistoryDataAccess.ATTRIBUTE20.equals(pFieldName)) {
            return getAttribute20();
        } else if (HistoryDataAccess.ATTRIBUTE21.equals(pFieldName)) {
            return getAttribute21();
        } else if (HistoryDataAccess.ATTRIBUTE22.equals(pFieldName)) {
            return getAttribute22();
        } else if (HistoryDataAccess.ATTRIBUTE23.equals(pFieldName)) {
            return getAttribute23();
        } else if (HistoryDataAccess.ATTRIBUTE24.equals(pFieldName)) {
            return getAttribute24();
        } else if (HistoryDataAccess.ATTRIBUTE25.equals(pFieldName)) {
            return getAttribute25();
        } else if (HistoryDataAccess.ATTRIBUTE26.equals(pFieldName)) {
            return getAttribute26();
        } else if (HistoryDataAccess.ATTRIBUTE27.equals(pFieldName)) {
            return getAttribute27();
        } else if (HistoryDataAccess.ATTRIBUTE28.equals(pFieldName)) {
            return getAttribute28();
        } else if (HistoryDataAccess.ATTRIBUTE29.equals(pFieldName)) {
            return getAttribute29();
        } else if (HistoryDataAccess.ATTRIBUTE30.equals(pFieldName)) {
            return getAttribute30();
        } else if (HistoryDataAccess.ATTRIBUTE31.equals(pFieldName)) {
            return getAttribute31();
        } else if (HistoryDataAccess.ATTRIBUTE32.equals(pFieldName)) {
            return getAttribute32();
        } else if (HistoryDataAccess.ATTRIBUTE33.equals(pFieldName)) {
            return getAttribute33();
        } else if (HistoryDataAccess.ATTRIBUTE34.equals(pFieldName)) {
            return getAttribute34();
        } else if (HistoryDataAccess.ATTRIBUTE35.equals(pFieldName)) {
            return getAttribute35();
        } else if (HistoryDataAccess.CLOB1.equals(pFieldName)) {
            return getClob1();
        } else if (HistoryDataAccess.CLOB2.equals(pFieldName)) {
            return getClob2();
        } else if (HistoryDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (HistoryDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (HistoryDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (HistoryDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return HistoryDataAccess.CLW_HISTORY;
    }

    
    /**
     * Sets the HistoryId field. This field is required to be set in the database.
     *
     * @param pHistoryId
     *  int to use to update the field.
     */
    public void setHistoryId(int pHistoryId){
        this.mHistoryId = pHistoryId;
        setDirty(true);
    }
    /**
     * Retrieves the HistoryId field.
     *
     * @return
     *  int containing the HistoryId field.
     */
    public int getHistoryId(){
        return mHistoryId;
    }

    /**
     * Sets the HistoryTypeCd field. This field is required to be set in the database.
     *
     * @param pHistoryTypeCd
     *  String to use to update the field.
     */
    public void setHistoryTypeCd(String pHistoryTypeCd){
        this.mHistoryTypeCd = pHistoryTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the HistoryTypeCd field.
     *
     * @return
     *  String containing the HistoryTypeCd field.
     */
    public String getHistoryTypeCd(){
        return mHistoryTypeCd;
    }

    /**
     * Sets the UserId field. This field is required to be set in the database.
     *
     * @param pUserId
     *  String to use to update the field.
     */
    public void setUserId(String pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  String containing the UserId field.
     */
    public String getUserId(){
        return mUserId;
    }

    /**
     * Sets the ActivityDate field. This field is required to be set in the database.
     *
     * @param pActivityDate
     *  Date to use to update the field.
     */
    public void setActivityDate(Date pActivityDate){
        this.mActivityDate = pActivityDate;
        setDirty(true);
    }
    /**
     * Retrieves the ActivityDate field.
     *
     * @return
     *  Date containing the ActivityDate field.
     */
    public Date getActivityDate(){
        return mActivityDate;
    }

    /**
     * Sets the Attribute01 field.
     *
     * @param pAttribute01
     *  String to use to update the field.
     */
    public void setAttribute01(String pAttribute01){
        this.mAttribute01 = pAttribute01;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute01 field.
     *
     * @return
     *  String containing the Attribute01 field.
     */
    public String getAttribute01(){
        return mAttribute01;
    }

    /**
     * Sets the Attribute02 field.
     *
     * @param pAttribute02
     *  String to use to update the field.
     */
    public void setAttribute02(String pAttribute02){
        this.mAttribute02 = pAttribute02;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute02 field.
     *
     * @return
     *  String containing the Attribute02 field.
     */
    public String getAttribute02(){
        return mAttribute02;
    }

    /**
     * Sets the Attribute03 field.
     *
     * @param pAttribute03
     *  String to use to update the field.
     */
    public void setAttribute03(String pAttribute03){
        this.mAttribute03 = pAttribute03;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute03 field.
     *
     * @return
     *  String containing the Attribute03 field.
     */
    public String getAttribute03(){
        return mAttribute03;
    }

    /**
     * Sets the Attribute04 field.
     *
     * @param pAttribute04
     *  String to use to update the field.
     */
    public void setAttribute04(String pAttribute04){
        this.mAttribute04 = pAttribute04;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute04 field.
     *
     * @return
     *  String containing the Attribute04 field.
     */
    public String getAttribute04(){
        return mAttribute04;
    }

    /**
     * Sets the Attribute05 field.
     *
     * @param pAttribute05
     *  String to use to update the field.
     */
    public void setAttribute05(String pAttribute05){
        this.mAttribute05 = pAttribute05;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute05 field.
     *
     * @return
     *  String containing the Attribute05 field.
     */
    public String getAttribute05(){
        return mAttribute05;
    }

    /**
     * Sets the Attribute06 field.
     *
     * @param pAttribute06
     *  String to use to update the field.
     */
    public void setAttribute06(String pAttribute06){
        this.mAttribute06 = pAttribute06;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute06 field.
     *
     * @return
     *  String containing the Attribute06 field.
     */
    public String getAttribute06(){
        return mAttribute06;
    }

    /**
     * Sets the Attribute07 field.
     *
     * @param pAttribute07
     *  String to use to update the field.
     */
    public void setAttribute07(String pAttribute07){
        this.mAttribute07 = pAttribute07;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute07 field.
     *
     * @return
     *  String containing the Attribute07 field.
     */
    public String getAttribute07(){
        return mAttribute07;
    }

    /**
     * Sets the Attribute08 field.
     *
     * @param pAttribute08
     *  String to use to update the field.
     */
    public void setAttribute08(String pAttribute08){
        this.mAttribute08 = pAttribute08;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute08 field.
     *
     * @return
     *  String containing the Attribute08 field.
     */
    public String getAttribute08(){
        return mAttribute08;
    }

    /**
     * Sets the Attribute09 field.
     *
     * @param pAttribute09
     *  String to use to update the field.
     */
    public void setAttribute09(String pAttribute09){
        this.mAttribute09 = pAttribute09;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute09 field.
     *
     * @return
     *  String containing the Attribute09 field.
     */
    public String getAttribute09(){
        return mAttribute09;
    }

    /**
     * Sets the Attribute10 field.
     *
     * @param pAttribute10
     *  String to use to update the field.
     */
    public void setAttribute10(String pAttribute10){
        this.mAttribute10 = pAttribute10;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute10 field.
     *
     * @return
     *  String containing the Attribute10 field.
     */
    public String getAttribute10(){
        return mAttribute10;
    }

    /**
     * Sets the Attribute11 field.
     *
     * @param pAttribute11
     *  String to use to update the field.
     */
    public void setAttribute11(String pAttribute11){
        this.mAttribute11 = pAttribute11;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute11 field.
     *
     * @return
     *  String containing the Attribute11 field.
     */
    public String getAttribute11(){
        return mAttribute11;
    }

    /**
     * Sets the Attribute12 field.
     *
     * @param pAttribute12
     *  String to use to update the field.
     */
    public void setAttribute12(String pAttribute12){
        this.mAttribute12 = pAttribute12;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute12 field.
     *
     * @return
     *  String containing the Attribute12 field.
     */
    public String getAttribute12(){
        return mAttribute12;
    }

    /**
     * Sets the Attribute13 field.
     *
     * @param pAttribute13
     *  String to use to update the field.
     */
    public void setAttribute13(String pAttribute13){
        this.mAttribute13 = pAttribute13;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute13 field.
     *
     * @return
     *  String containing the Attribute13 field.
     */
    public String getAttribute13(){
        return mAttribute13;
    }

    /**
     * Sets the Attribute14 field.
     *
     * @param pAttribute14
     *  String to use to update the field.
     */
    public void setAttribute14(String pAttribute14){
        this.mAttribute14 = pAttribute14;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute14 field.
     *
     * @return
     *  String containing the Attribute14 field.
     */
    public String getAttribute14(){
        return mAttribute14;
    }

    /**
     * Sets the Attribute15 field.
     *
     * @param pAttribute15
     *  String to use to update the field.
     */
    public void setAttribute15(String pAttribute15){
        this.mAttribute15 = pAttribute15;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute15 field.
     *
     * @return
     *  String containing the Attribute15 field.
     */
    public String getAttribute15(){
        return mAttribute15;
    }

    /**
     * Sets the Attribute16 field.
     *
     * @param pAttribute16
     *  String to use to update the field.
     */
    public void setAttribute16(String pAttribute16){
        this.mAttribute16 = pAttribute16;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute16 field.
     *
     * @return
     *  String containing the Attribute16 field.
     */
    public String getAttribute16(){
        return mAttribute16;
    }

    /**
     * Sets the Attribute17 field.
     *
     * @param pAttribute17
     *  String to use to update the field.
     */
    public void setAttribute17(String pAttribute17){
        this.mAttribute17 = pAttribute17;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute17 field.
     *
     * @return
     *  String containing the Attribute17 field.
     */
    public String getAttribute17(){
        return mAttribute17;
    }

    /**
     * Sets the Attribute18 field.
     *
     * @param pAttribute18
     *  String to use to update the field.
     */
    public void setAttribute18(String pAttribute18){
        this.mAttribute18 = pAttribute18;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute18 field.
     *
     * @return
     *  String containing the Attribute18 field.
     */
    public String getAttribute18(){
        return mAttribute18;
    }

    /**
     * Sets the Attribute19 field.
     *
     * @param pAttribute19
     *  String to use to update the field.
     */
    public void setAttribute19(String pAttribute19){
        this.mAttribute19 = pAttribute19;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute19 field.
     *
     * @return
     *  String containing the Attribute19 field.
     */
    public String getAttribute19(){
        return mAttribute19;
    }

    /**
     * Sets the Attribute20 field.
     *
     * @param pAttribute20
     *  String to use to update the field.
     */
    public void setAttribute20(String pAttribute20){
        this.mAttribute20 = pAttribute20;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute20 field.
     *
     * @return
     *  String containing the Attribute20 field.
     */
    public String getAttribute20(){
        return mAttribute20;
    }

    /**
     * Sets the Attribute21 field.
     *
     * @param pAttribute21
     *  String to use to update the field.
     */
    public void setAttribute21(String pAttribute21){
        this.mAttribute21 = pAttribute21;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute21 field.
     *
     * @return
     *  String containing the Attribute21 field.
     */
    public String getAttribute21(){
        return mAttribute21;
    }

    /**
     * Sets the Attribute22 field.
     *
     * @param pAttribute22
     *  String to use to update the field.
     */
    public void setAttribute22(String pAttribute22){
        this.mAttribute22 = pAttribute22;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute22 field.
     *
     * @return
     *  String containing the Attribute22 field.
     */
    public String getAttribute22(){
        return mAttribute22;
    }

    /**
     * Sets the Attribute23 field.
     *
     * @param pAttribute23
     *  String to use to update the field.
     */
    public void setAttribute23(String pAttribute23){
        this.mAttribute23 = pAttribute23;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute23 field.
     *
     * @return
     *  String containing the Attribute23 field.
     */
    public String getAttribute23(){
        return mAttribute23;
    }

    /**
     * Sets the Attribute24 field.
     *
     * @param pAttribute24
     *  String to use to update the field.
     */
    public void setAttribute24(String pAttribute24){
        this.mAttribute24 = pAttribute24;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute24 field.
     *
     * @return
     *  String containing the Attribute24 field.
     */
    public String getAttribute24(){
        return mAttribute24;
    }

    /**
     * Sets the Attribute25 field.
     *
     * @param pAttribute25
     *  String to use to update the field.
     */
    public void setAttribute25(String pAttribute25){
        this.mAttribute25 = pAttribute25;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute25 field.
     *
     * @return
     *  String containing the Attribute25 field.
     */
    public String getAttribute25(){
        return mAttribute25;
    }

    /**
     * Sets the Attribute26 field.
     *
     * @param pAttribute26
     *  String to use to update the field.
     */
    public void setAttribute26(String pAttribute26){
        this.mAttribute26 = pAttribute26;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute26 field.
     *
     * @return
     *  String containing the Attribute26 field.
     */
    public String getAttribute26(){
        return mAttribute26;
    }

    /**
     * Sets the Attribute27 field.
     *
     * @param pAttribute27
     *  String to use to update the field.
     */
    public void setAttribute27(String pAttribute27){
        this.mAttribute27 = pAttribute27;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute27 field.
     *
     * @return
     *  String containing the Attribute27 field.
     */
    public String getAttribute27(){
        return mAttribute27;
    }

    /**
     * Sets the Attribute28 field.
     *
     * @param pAttribute28
     *  String to use to update the field.
     */
    public void setAttribute28(String pAttribute28){
        this.mAttribute28 = pAttribute28;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute28 field.
     *
     * @return
     *  String containing the Attribute28 field.
     */
    public String getAttribute28(){
        return mAttribute28;
    }

    /**
     * Sets the Attribute29 field.
     *
     * @param pAttribute29
     *  String to use to update the field.
     */
    public void setAttribute29(String pAttribute29){
        this.mAttribute29 = pAttribute29;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute29 field.
     *
     * @return
     *  String containing the Attribute29 field.
     */
    public String getAttribute29(){
        return mAttribute29;
    }

    /**
     * Sets the Attribute30 field.
     *
     * @param pAttribute30
     *  String to use to update the field.
     */
    public void setAttribute30(String pAttribute30){
        this.mAttribute30 = pAttribute30;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute30 field.
     *
     * @return
     *  String containing the Attribute30 field.
     */
    public String getAttribute30(){
        return mAttribute30;
    }

    /**
     * Sets the Attribute31 field.
     *
     * @param pAttribute31
     *  String to use to update the field.
     */
    public void setAttribute31(String pAttribute31){
        this.mAttribute31 = pAttribute31;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute31 field.
     *
     * @return
     *  String containing the Attribute31 field.
     */
    public String getAttribute31(){
        return mAttribute31;
    }

    /**
     * Sets the Attribute32 field.
     *
     * @param pAttribute32
     *  String to use to update the field.
     */
    public void setAttribute32(String pAttribute32){
        this.mAttribute32 = pAttribute32;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute32 field.
     *
     * @return
     *  String containing the Attribute32 field.
     */
    public String getAttribute32(){
        return mAttribute32;
    }

    /**
     * Sets the Attribute33 field.
     *
     * @param pAttribute33
     *  String to use to update the field.
     */
    public void setAttribute33(String pAttribute33){
        this.mAttribute33 = pAttribute33;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute33 field.
     *
     * @return
     *  String containing the Attribute33 field.
     */
    public String getAttribute33(){
        return mAttribute33;
    }

    /**
     * Sets the Attribute34 field.
     *
     * @param pAttribute34
     *  String to use to update the field.
     */
    public void setAttribute34(String pAttribute34){
        this.mAttribute34 = pAttribute34;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute34 field.
     *
     * @return
     *  String containing the Attribute34 field.
     */
    public String getAttribute34(){
        return mAttribute34;
    }

    /**
     * Sets the Attribute35 field.
     *
     * @param pAttribute35
     *  String to use to update the field.
     */
    public void setAttribute35(String pAttribute35){
        this.mAttribute35 = pAttribute35;
        setDirty(true);
    }
    /**
     * Retrieves the Attribute35 field.
     *
     * @return
     *  String containing the Attribute35 field.
     */
    public String getAttribute35(){
        return mAttribute35;
    }

    /**
     * Sets the Clob1 field.
     *
     * @param pClob1
     *  String to use to update the field.
     */
    public void setClob1(String pClob1){
        this.mClob1 = pClob1;
        setDirty(true);
    }
    /**
     * Retrieves the Clob1 field.
     *
     * @return
     *  String containing the Clob1 field.
     */
    public String getClob1(){
        return mClob1;
    }

    /**
     * Sets the Clob2 field.
     *
     * @param pClob2
     *  String to use to update the field.
     */
    public void setClob2(String pClob2){
        this.mClob2 = pClob2;
        setDirty(true);
    }
    /**
     * Retrieves the Clob2 field.
     *
     * @return
     *  String containing the Clob2 field.
     */
    public String getClob2(){
        return mClob2;
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


}
