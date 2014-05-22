
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerDescView
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
 * <code>TradingPartnerDescView</code> is a ViewObject class for UI.
 */
public class TradingPartnerDescView
extends ValueObject
{
   
    private static final long serialVersionUID = 3367880038326384307L;
    private TradingPartnerData mTradingPartnerData;
    private TradingProfileData mTradingProfileData;
    private TradingProfileConfigData mTradingProfileConfigData;
    private TradingPropertyMapDataVector mTradingPropertyMapDataVector;

    /**
     * Constructor.
     */
    public TradingPartnerDescView ()
    {
    }

    /**
     * Constructor. 
     */
    public TradingPartnerDescView(TradingPartnerData parm1, TradingProfileData parm2, TradingProfileConfigData parm3, TradingPropertyMapDataVector parm4)
    {
        mTradingPartnerData = parm1;
        mTradingProfileData = parm2;
        mTradingProfileConfigData = parm3;
        mTradingPropertyMapDataVector = parm4;
        
    }

    /**
     * Creates a new TradingPartnerDescView
     *
     * @return
     *  Newly initialized TradingPartnerDescView object.
     */
    public static TradingPartnerDescView createValue () 
    {
        TradingPartnerDescView valueView = new TradingPartnerDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPartnerDescView object
     */
    public String toString()
    {
        return "[" + "TradingPartnerData=" + mTradingPartnerData + ", TradingProfileData=" + mTradingProfileData + ", TradingProfileConfigData=" + mTradingProfileConfigData + ", TradingPropertyMapDataVector=" + mTradingPropertyMapDataVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("TradingPartnerDesc");
	root.setAttribute("Id", String.valueOf(mTradingPartnerData));

	Element node;

        node = doc.createElement("TradingProfileData");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileData)));
        root.appendChild(node);

        node = doc.createElement("TradingProfileConfigData");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileConfigData)));
        root.appendChild(node);

        node = doc.createElement("TradingPropertyMapDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPropertyMapDataVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public TradingPartnerDescView copy()  {
      TradingPartnerDescView obj = new TradingPartnerDescView();
      obj.setTradingPartnerData(mTradingPartnerData);
      obj.setTradingProfileData(mTradingProfileData);
      obj.setTradingProfileConfigData(mTradingProfileConfigData);
      obj.setTradingPropertyMapDataVector(mTradingPropertyMapDataVector);
      
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
     * Sets the TradingProfileData property.
     *
     * @param pTradingProfileData
     *  TradingProfileData to use to update the property.
     */
    public void setTradingProfileData(TradingProfileData pTradingProfileData){
        this.mTradingProfileData = pTradingProfileData;
    }
    /**
     * Retrieves the TradingProfileData property.
     *
     * @return
     *  TradingProfileData containing the TradingProfileData property.
     */
    public TradingProfileData getTradingProfileData(){
        return mTradingProfileData;
    }


    /**
     * Sets the TradingProfileConfigData property.
     *
     * @param pTradingProfileConfigData
     *  TradingProfileConfigData to use to update the property.
     */
    public void setTradingProfileConfigData(TradingProfileConfigData pTradingProfileConfigData){
        this.mTradingProfileConfigData = pTradingProfileConfigData;
    }
    /**
     * Retrieves the TradingProfileConfigData property.
     *
     * @return
     *  TradingProfileConfigData containing the TradingProfileConfigData property.
     */
    public TradingProfileConfigData getTradingProfileConfigData(){
        return mTradingProfileConfigData;
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


    
}
