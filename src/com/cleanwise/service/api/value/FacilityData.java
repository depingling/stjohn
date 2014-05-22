
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FacilityData
 * Description:  This is a ValueObject class wrapping the database table CLW_FACILITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import org.w3c.dom.*;

/**
 * <code>FacilityData</code> is a ValueObject class wrapping of the database table CLW_FACILITY.
 */
public class FacilityData extends ValueObject implements Cloneable
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5524093918271813089L;
    
    private int mFacilityId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mFacilityStatusCd;// SQL type:VARCHAR2, not null
    private int mFacilityTypeCd;// SQL type:NUMBER, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    private FacilityData ()
    {
        mAddBy = "";
        mFacilityStatusCd = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public FacilityData(int parm1, int parm2, String parm3, Date parm4, String parm5, int parm6, String parm7, Date parm8)
    {
        mFacilityId = parm1;
        mUserId = parm2;
        mAddBy = parm3;
        mAddDate = parm4;
        mFacilityStatusCd = parm5;
        mFacilityTypeCd = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new FacilityData
     *
     * @return
     *  Newly initialized FacilityData object.
     */
    public static FacilityData createValue ()
    {
        FacilityData valueData = new FacilityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FacilityData object
     */
    public String toString()
    {
        return "[" + "FacilityId=" + mFacilityId + ", UserId=" + mUserId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", FacilityStatusCd=" + mFacilityStatusCd + ", FacilityTypeCd=" + mFacilityTypeCd + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Facility");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFacilityId));

        node = doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("FacilityStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityStatusCd)));
        root.appendChild(node);

        node = doc.createElement("FacilityTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityTypeCd)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FacilityId field is not cloned.
    *
    * @return FacilityData object
    */
    public Object clone(){
        FacilityData myClone = new FacilityData();
        
        myClone.mUserId = mUserId;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mFacilityStatusCd = mFacilityStatusCd;
        
        myClone.mFacilityTypeCd = mFacilityTypeCd;
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    
    /**
     * Sets the FacilityId field. This field is required to be set in the database.
     *
     * @param pFacilityId
     *  int to use to update the field.
     */
    public void setFacilityId(int pFacilityId){
        this.mFacilityId = pFacilityId;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityId field.
     *
     * @return
     *  int containing the FacilityId field.
     */
    public int getFacilityId(){
        return mFacilityId;
    }

    /**
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
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
     * Sets the FacilityStatusCd field. This field is required to be set in the database.
     *
     * @param pFacilityStatusCd
     *  String to use to update the field.
     */
    public void setFacilityStatusCd(String pFacilityStatusCd){
        this.mFacilityStatusCd = pFacilityStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityStatusCd field.
     *
     * @return
     *  String containing the FacilityStatusCd field.
     */
    public String getFacilityStatusCd(){
        return mFacilityStatusCd;
    }

    /**
     * Sets the FacilityTypeCd field. This field is required to be set in the database.
     *
     * @param pFacilityTypeCd
     *  int to use to update the field.
     */
    public void setFacilityTypeCd(int pFacilityTypeCd){
        this.mFacilityTypeCd = pFacilityTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityTypeCd field.
     *
     * @return
     *  int containing the FacilityTypeCd field.
     */
    public int getFacilityTypeCd(){
        return mFacilityTypeCd;
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


}
