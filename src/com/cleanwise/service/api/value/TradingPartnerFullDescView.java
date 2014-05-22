
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerFullDescView
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
 * <code>TradingPartnerFullDescView</code> is a ViewObject class for UI.
 */
public class TradingPartnerFullDescView
extends ValueObject
{
   
    private static final long serialVersionUID = 3367880038326384308L;
    private TradingPartnerData mTradingPartnerData;
    private TradingProfileDataVector mTradingProfileDataVector;
    private TradingProfileConfigDataVector mTradingProfileConfigDataVector;
    private TradingPropertyMapDataVector mTradingPropertyMapDataVector;
    private TradingPartnerInfo mTradingPartnerInfo;

    /**
     * Constructor.
     */
    public TradingPartnerFullDescView ()
    {
    }

    /**
     * Constructor. 
     */
    public TradingPartnerFullDescView(TradingPartnerData parm1, TradingProfileDataVector parm2, TradingProfileConfigDataVector parm3, TradingPropertyMapDataVector parm4, TradingPartnerInfo parm5)
    {
        mTradingPartnerData = parm1;
        mTradingProfileDataVector = parm2;
        mTradingProfileConfigDataVector = parm3;
        mTradingPropertyMapDataVector = parm4;
        mTradingPartnerInfo = parm5;
        
    }

    /**
     * Creates a new TradingPartnerFullDescView
     *
     * @return
     *  Newly initialized TradingPartnerFullDescView object.
     */
    public static TradingPartnerFullDescView createValue () 
    {
        TradingPartnerFullDescView valueView = new TradingPartnerFullDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPartnerFullDescView object
     */
    public String toString()
    {
        return "[" + "TradingPartnerData=" + mTradingPartnerData + ", TradingProfileDataVector=" + mTradingProfileDataVector + ", TradingProfileConfigDataVector=" + mTradingProfileConfigDataVector + ", TradingPropertyMapDataVector=" + mTradingPropertyMapDataVector + ", TradingPartnerInfo=" + mTradingPartnerInfo + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("TradingPartnerFullDesc");
	root.setAttribute("Id", String.valueOf(mTradingPartnerData));

	Element node;

        node = doc.createElement("TradingProfileDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileDataVector)));
        root.appendChild(node);

        node = doc.createElement("TradingProfileConfigDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileConfigDataVector)));
        root.appendChild(node);

        node = doc.createElement("TradingPropertyMapDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPropertyMapDataVector)));
        root.appendChild(node);

        node = doc.createElement("TradingPartnerInfo");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerInfo)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public TradingPartnerFullDescView copy()  {
      TradingPartnerFullDescView obj = new TradingPartnerFullDescView();
      obj.setTradingPartnerData(mTradingPartnerData);
      obj.setTradingProfileDataVector(mTradingProfileDataVector);
      obj.setTradingProfileConfigDataVector(mTradingProfileConfigDataVector);
      obj.setTradingPropertyMapDataVector(mTradingPropertyMapDataVector);
      obj.setTradingPartnerInfo(mTradingPartnerInfo);
      
      return obj;
    }

    
    /**
     * Sets the TradingPartnerData property.
     *
     * @param pTradingPartnerData
     *  TradingPartnerData to use to update the property.
     */
    public void setTradingPartnerData(TradingPartnerData pTradingPartnerData){
        this.mTradingPartnerData = pTradingPartnerData;
    }
    /**
     * Retrieves the TradingPartnerData property.
     *
     * @return
     *  TradingPartnerData containing the TradingPartnerData property.
     */
    public TradingPartnerData getTradingPartnerData(){
        return mTradingPartnerData;
    }


    /**
     * Sets the TradingProfileDataVector property.
     *
     * @param pTradingProfileDataVector
     *  TradingProfileDataVector to use to update the property.
     */
    public void setTradingProfileDataVector(TradingProfileDataVector pTradingProfileDataVector){
        this.mTradingProfileDataVector = pTradingProfileDataVector;
    }
    /**
     * Retrieves the TradingProfileDataVector property.
     *
     * @return
     *  TradingProfileDataVector containing the TradingProfileDataVector property.
     */
    public TradingProfileDataVector getTradingProfileDataVector(){
        return mTradingProfileDataVector;
    }


    /**
     * Sets the TradingProfileConfigDataVector property.
     *
     * @param pTradingProfileConfigDataVector
     *  TradingProfileConfigDataVector to use to update the property.
     */
    public void setTradingProfileConfigDataVector(TradingProfileConfigDataVector pTradingProfileConfigDataVector){
        this.mTradingProfileConfigDataVector = pTradingProfileConfigDataVector;
    }
    /**
     * Retrieves the TradingProfileConfigDataVector property.
     *
     * @return
     *  TradingProfileConfigDataVector containing the TradingProfileConfigDataVector property.
     */
    public TradingProfileConfigDataVector getTradingProfileConfigDataVector(){
        return mTradingProfileConfigDataVector;
    }


    /**
     * Sets the TradingPropertyMapDataVector property.
     *
     * @param pTradingPropertyMapDataVector
     *  TradingPropertyMapDataVector to use to update the property.
     */
    public void setTradingPropertyMapDataVector(TradingPropertyMapDataVector pTradingPropertyMapDataVector){
        this.mTradingPropertyMapDataVector = pTradingPropertyMapDataVector;
    }
    /**
     * Retrieves the TradingPropertyMapDataVector property.
     *
     * @return
     *  TradingPropertyMapDataVector containing the TradingPropertyMapDataVector property.
     */
    public TradingPropertyMapDataVector getTradingPropertyMapDataVector(){
        return mTradingPropertyMapDataVector;
    }


    /**
     * Sets the TradingPartnerInfo property.
     *
     * @param pTradingPartnerInfo
     *  TradingPartnerInfo to use to update the property.
     */
    public void setTradingPartnerInfo(TradingPartnerInfo pTradingPartnerInfo){
        this.mTradingPartnerInfo = pTradingPartnerInfo;
    }
    /**
     * Retrieves the TradingPartnerInfo property.
     *
     * @return
     *  TradingPartnerInfo containing the TradingPartnerInfo property.
     */
    public TradingPartnerInfo getTradingPartnerInfo(){
        return mTradingPartnerInfo;
    }


    
}
