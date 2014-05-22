
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;




/**
 * <code>WarrantyView</code> is a ViewObject class for UI.
 */
public class WarrantyView
extends ValueObject
{
   
    private static final long serialVersionUID = -5350711185255361034L;
    private int mWarrantyId;
    private String mWarrantyNumber;
    private AssetDataVector mAssets;
    private String mType;
    private String mStatus;
    private BusEntityData mWarrantyProvider;
    private Date mStartDate;
    private Date mExpDate;

    /**
     * Constructor.
     */
    public WarrantyView ()
    {
        mWarrantyNumber = "";
        mType = "";
        mStatus = "";
    }

    /**
     * Constructor. 
     */
    public WarrantyView(int parm1, String parm2, AssetDataVector parm3, String parm4, String parm5, BusEntityData parm6, Date parm7, Date parm8)
    {
        mWarrantyId = parm1;
        mWarrantyNumber = parm2;
        mAssets = parm3;
        mType = parm4;
        mStatus = parm5;
        mWarrantyProvider = parm6;
        mStartDate = parm7;
        mExpDate = parm8;
        
    }

    /**
     * Creates a new WarrantyView
     *
     * @return
     *  Newly initialized WarrantyView object.
     */
    public static WarrantyView createValue () 
    {
        WarrantyView valueView = new WarrantyView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyView object
     */
    public String toString()
    {
        return "[" + "WarrantyId=" + mWarrantyId + ", WarrantyNumber=" + mWarrantyNumber + ", Assets=" + mAssets + ", Type=" + mType + ", Status=" + mStatus + ", WarrantyProvider=" + mWarrantyProvider + ", StartDate=" + mStartDate + ", ExpDate=" + mExpDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Warranty");
	root.setAttribute("Id", String.valueOf(mWarrantyId));

	Element node;

        node = doc.createElement("WarrantyNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyNumber)));
        root.appendChild(node);

        node = doc.createElement("Assets");
        node.appendChild(doc.createTextNode(String.valueOf(mAssets)));
        root.appendChild(node);

        node = doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        node = doc.createElement("Status");
        node.appendChild(doc.createTextNode(String.valueOf(mStatus)));
        root.appendChild(node);

        node = doc.createElement("WarrantyProvider");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyProvider)));
        root.appendChild(node);

        node = doc.createElement("StartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStartDate)));
        root.appendChild(node);

        node = doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WarrantyView copy()  {
      WarrantyView obj = new WarrantyView();
      obj.setWarrantyId(mWarrantyId);
      obj.setWarrantyNumber(mWarrantyNumber);
      obj.setAssets(mAssets);
      obj.setType(mType);
      obj.setStatus(mStatus);
      obj.setWarrantyProvider(mWarrantyProvider);
      obj.setStartDate(mStartDate);
      obj.setExpDate(mExpDate);
      
      return obj;
    }

    
    /**
     * Sets the WarrantyId property.
     *
     * @param pWarrantyId
     *  int to use to update the property.
     */
    public void setWarrantyId(int pWarrantyId){
        this.mWarrantyId = pWarrantyId;
    }
    /**
     * Retrieves the WarrantyId property.
     *
     * @return
     *  int containing the WarrantyId property.
     */
    public int getWarrantyId(){
        return mWarrantyId;
    }


    /**
     * Sets the WarrantyNumber property.
     *
     * @param pWarrantyNumber
     *  String to use to update the property.
     */
    public void setWarrantyNumber(String pWarrantyNumber){
        this.mWarrantyNumber = pWarrantyNumber;
    }
    /**
     * Retrieves the WarrantyNumber property.
     *
     * @return
     *  String containing the WarrantyNumber property.
     */
    public String getWarrantyNumber(){
        return mWarrantyNumber;
    }


    /**
     * Sets the Assets property.
     *
     * @param pAssets
     *  AssetDataVector to use to update the property.
     */
    public void setAssets(AssetDataVector pAssets){
        this.mAssets = pAssets;
    }
    /**
     * Retrieves the Assets property.
     *
     * @return
     *  AssetDataVector containing the Assets property.
     */
    public AssetDataVector getAssets(){
        return mAssets;
    }


    /**
     * Sets the Type property.
     *
     * @param pType
     *  String to use to update the property.
     */
    public void setType(String pType){
        this.mType = pType;
    }
    /**
     * Retrieves the Type property.
     *
     * @return
     *  String containing the Type property.
     */
    public String getType(){
        return mType;
    }


    /**
     * Sets the Status property.
     *
     * @param pStatus
     *  String to use to update the property.
     */
    public void setStatus(String pStatus){
        this.mStatus = pStatus;
    }
    /**
     * Retrieves the Status property.
     *
     * @return
     *  String containing the Status property.
     */
    public String getStatus(){
        return mStatus;
    }


    /**
     * Sets the WarrantyProvider property.
     *
     * @param pWarrantyProvider
     *  BusEntityData to use to update the property.
     */
    public void setWarrantyProvider(BusEntityData pWarrantyProvider){
        this.mWarrantyProvider = pWarrantyProvider;
    }
    /**
     * Retrieves the WarrantyProvider property.
     *
     * @return
     *  BusEntityData containing the WarrantyProvider property.
     */
    public BusEntityData getWarrantyProvider(){
        return mWarrantyProvider;
    }


    /**
     * Sets the StartDate property.
     *
     * @param pStartDate
     *  Date to use to update the property.
     */
    public void setStartDate(Date pStartDate){
        this.mStartDate = pStartDate;
    }
    /**
     * Retrieves the StartDate property.
     *
     * @return
     *  Date containing the StartDate property.
     */
    public Date getStartDate(){
        return mStartDate;
    }


    /**
     * Sets the ExpDate property.
     *
     * @param pExpDate
     *  Date to use to update the property.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
    }
    /**
     * Retrieves the ExpDate property.
     *
     * @return
     *  Date containing the ExpDate property.
     */
    public Date getExpDate(){
        return mExpDate;
    }


    
}
