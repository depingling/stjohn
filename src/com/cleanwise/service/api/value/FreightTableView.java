
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FreightTableView
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
 * <code>FreightTableView</code> is a ViewObject class for UI.
 */
public class FreightTableView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mFreightTableId;
    private String mShortDesc;
    private int mStoreId;
    private String mFreightTableStatusCd;
    private String mFreightTableTypeCd;
    private int mDistributorId;
    private String mDistributorName;
    private String mFreightTableChargeCd;

    /**
     * Constructor.
     */
    public FreightTableView ()
    {
        mShortDesc = "";
        mFreightTableStatusCd = "";
        mFreightTableTypeCd = "";
        mDistributorName = "";
        mFreightTableChargeCd = "";
    }

    /**
     * Constructor. 
     */
    public FreightTableView(int parm1, String parm2, int parm3, String parm4, String parm5, int parm6, String parm7, String parm8)
    {
        mFreightTableId = parm1;
        mShortDesc = parm2;
        mStoreId = parm3;
        mFreightTableStatusCd = parm4;
        mFreightTableTypeCd = parm5;
        mDistributorId = parm6;
        mDistributorName = parm7;
        mFreightTableChargeCd = parm8;
        
    }

    /**
     * Creates a new FreightTableView
     *
     * @return
     *  Newly initialized FreightTableView object.
     */
    public static FreightTableView createValue () 
    {
        FreightTableView valueView = new FreightTableView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FreightTableView object
     */
    public String toString()
    {
        return "[" + "FreightTableId=" + mFreightTableId + ", ShortDesc=" + mShortDesc + ", StoreId=" + mStoreId + ", FreightTableStatusCd=" + mFreightTableStatusCd + ", FreightTableTypeCd=" + mFreightTableTypeCd + ", DistributorId=" + mDistributorId + ", DistributorName=" + mDistributorName + ", FreightTableChargeCd=" + mFreightTableChargeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FreightTable");
	root.setAttribute("Id", String.valueOf(mFreightTableId));

	Element node;

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("FreightTableStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableStatusCd)));
        root.appendChild(node);

        node = doc.createElement("FreightTableTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableTypeCd)));
        root.appendChild(node);

        node = doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node = doc.createElement("DistributorName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorName)));
        root.appendChild(node);

        node = doc.createElement("FreightTableChargeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableChargeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public FreightTableView copy()  {
      FreightTableView obj = new FreightTableView();
      obj.setFreightTableId(mFreightTableId);
      obj.setShortDesc(mShortDesc);
      obj.setStoreId(mStoreId);
      obj.setFreightTableStatusCd(mFreightTableStatusCd);
      obj.setFreightTableTypeCd(mFreightTableTypeCd);
      obj.setDistributorId(mDistributorId);
      obj.setDistributorName(mDistributorName);
      obj.setFreightTableChargeCd(mFreightTableChargeCd);
      
      return obj;
    }

    
    /**
     * Sets the FreightTableId property.
     *
     * @param pFreightTableId
     *  int to use to update the property.
     */
    public void setFreightTableId(int pFreightTableId){
        this.mFreightTableId = pFreightTableId;
    }
    /**
     * Retrieves the FreightTableId property.
     *
     * @return
     *  int containing the FreightTableId property.
     */
    public int getFreightTableId(){
        return mFreightTableId;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
    }


    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the FreightTableStatusCd property.
     *
     * @param pFreightTableStatusCd
     *  String to use to update the property.
     */
    public void setFreightTableStatusCd(String pFreightTableStatusCd){
        this.mFreightTableStatusCd = pFreightTableStatusCd;
    }
    /**
     * Retrieves the FreightTableStatusCd property.
     *
     * @return
     *  String containing the FreightTableStatusCd property.
     */
    public String getFreightTableStatusCd(){
        return mFreightTableStatusCd;
    }


    /**
     * Sets the FreightTableTypeCd property.
     *
     * @param pFreightTableTypeCd
     *  String to use to update the property.
     */
    public void setFreightTableTypeCd(String pFreightTableTypeCd){
        this.mFreightTableTypeCd = pFreightTableTypeCd;
    }
    /**
     * Retrieves the FreightTableTypeCd property.
     *
     * @return
     *  String containing the FreightTableTypeCd property.
     */
    public String getFreightTableTypeCd(){
        return mFreightTableTypeCd;
    }


    /**
     * Sets the DistributorId property.
     *
     * @param pDistributorId
     *  int to use to update the property.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
    }
    /**
     * Retrieves the DistributorId property.
     *
     * @return
     *  int containing the DistributorId property.
     */
    public int getDistributorId(){
        return mDistributorId;
    }


    /**
     * Sets the DistributorName property.
     *
     * @param pDistributorName
     *  String to use to update the property.
     */
    public void setDistributorName(String pDistributorName){
        this.mDistributorName = pDistributorName;
    }
    /**
     * Retrieves the DistributorName property.
     *
     * @return
     *  String containing the DistributorName property.
     */
    public String getDistributorName(){
        return mDistributorName;
    }


    /**
     * Sets the FreightTableChargeCd property.
     *
     * @param pFreightTableChargeCd
     *  String to use to update the property.
     */
    public void setFreightTableChargeCd(String pFreightTableChargeCd){
        this.mFreightTableChargeCd = pFreightTableChargeCd;
    }
    /**
     * Retrieves the FreightTableChargeCd property.
     *
     * @return
     *  String containing the FreightTableChargeCd property.
     */
    public String getFreightTableChargeCd(){
        return mFreightTableChargeCd;
    }


    
}
