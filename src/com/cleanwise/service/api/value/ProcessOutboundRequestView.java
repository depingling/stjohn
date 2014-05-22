
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProcessOutboundRequestView
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
 * <code>ProcessOutboundRequestView</code> is a ViewObject class for UI.
 */
public class ProcessOutboundRequestView
extends ValueObject
{
   
    private static final long serialVersionUID = 3043479627283688991L;
    private TradingProfileConfigData mProfileConfigD;
    private TradingProfileData mProfileD;
    private TradingPartnerData mPartnerD;
    private TradingPartnerAssocData mProfileOveride;
    private int mIncommingProfileId;
    private OutboundEDIRequestDataVector mTransactionsToProcess;
    private String mDistErpNum;
    private TradingPartnerDescView mTradingPartnerDescView;
    private String mKey;

    /**
     * Constructor.
     */
    public ProcessOutboundRequestView ()
    {
        mDistErpNum = "";
        mKey = "";
    }

    /**
     * Constructor. 
     */
    public ProcessOutboundRequestView(TradingProfileConfigData parm1, TradingProfileData parm2, TradingPartnerData parm3, TradingPartnerAssocData parm4, int parm5, OutboundEDIRequestDataVector parm6, String parm7, TradingPartnerDescView parm8, String parm9)
    {
        mProfileConfigD = parm1;
        mProfileD = parm2;
        mPartnerD = parm3;
        mProfileOveride = parm4;
        mIncommingProfileId = parm5;
        mTransactionsToProcess = parm6;
        mDistErpNum = parm7;
        mTradingPartnerDescView = parm8;
        mKey = parm9;
        
    }

    /**
     * Creates a new ProcessOutboundRequestView
     *
     * @return
     *  Newly initialized ProcessOutboundRequestView object.
     */
    public static ProcessOutboundRequestView createValue () 
    {
        ProcessOutboundRequestView valueView = new ProcessOutboundRequestView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProcessOutboundRequestView object
     */
    public String toString()
    {
        return "[" + "ProfileConfigD=" + mProfileConfigD + ", ProfileD=" + mProfileD + ", PartnerD=" + mPartnerD + ", ProfileOveride=" + mProfileOveride + ", IncommingProfileId=" + mIncommingProfileId + ", TransactionsToProcess=" + mTransactionsToProcess + ", DistErpNum=" + mDistErpNum + ", TradingPartnerDescView=" + mTradingPartnerDescView + ", Key=" + mKey + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ProcessOutboundRequest");
	root.setAttribute("Id", String.valueOf(mProfileConfigD));

	Element node;

        node = doc.createElement("ProfileD");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileD)));
        root.appendChild(node);

        node = doc.createElement("PartnerD");
        node.appendChild(doc.createTextNode(String.valueOf(mPartnerD)));
        root.appendChild(node);

        node = doc.createElement("ProfileOveride");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileOveride)));
        root.appendChild(node);

        node = doc.createElement("IncommingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mIncommingProfileId)));
        root.appendChild(node);

        node = doc.createElement("TransactionsToProcess");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionsToProcess)));
        root.appendChild(node);

        node = doc.createElement("DistErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpNum)));
        root.appendChild(node);

        node = doc.createElement("TradingPartnerDescView");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerDescView)));
        root.appendChild(node);

        node = doc.createElement("Key");
        node.appendChild(doc.createTextNode(String.valueOf(mKey)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProcessOutboundRequestView copy()  {
      ProcessOutboundRequestView obj = new ProcessOutboundRequestView();
      obj.setProfileConfigD(mProfileConfigD);
      obj.setProfileD(mProfileD);
      obj.setPartnerD(mPartnerD);
      obj.setProfileOveride(mProfileOveride);
      obj.setIncommingProfileId(mIncommingProfileId);
      obj.setTransactionsToProcess(mTransactionsToProcess);
      obj.setDistErpNum(mDistErpNum);
      obj.setTradingPartnerDescView(mTradingPartnerDescView);
      obj.setKey(mKey);
      
      return obj;
    }

    
    /**
     * Sets the ProfileConfigD property.
     *
     * @param pProfileConfigD
     *  TradingProfileConfigData to use to update the property.
     */
    public void setProfileConfigD(TradingProfileConfigData pProfileConfigD){
        this.mProfileConfigD = pProfileConfigD;
    }
    /**
     * Retrieves the ProfileConfigD property.
     *
     * @return
     *  TradingProfileConfigData containing the ProfileConfigD property.
     */
    public TradingProfileConfigData getProfileConfigD(){
        return mProfileConfigD;
    }


    /**
     * Sets the ProfileD property.
     *
     * @param pProfileD
     *  TradingProfileData to use to update the property.
     */
    public void setProfileD(TradingProfileData pProfileD){
        this.mProfileD = pProfileD;
    }
    /**
     * Retrieves the ProfileD property.
     *
     * @return
     *  TradingProfileData containing the ProfileD property.
     */
    public TradingProfileData getProfileD(){
        return mProfileD;
    }


    /**
     * Sets the PartnerD property.
     *
     * @param pPartnerD
     *  TradingPartnerData to use to update the property.
     */
    public void setPartnerD(TradingPartnerData pPartnerD){
        this.mPartnerD = pPartnerD;
    }
    /**
     * Retrieves the PartnerD property.
     *
     * @return
     *  TradingPartnerData containing the PartnerD property.
     */
    public TradingPartnerData getPartnerD(){
        return mPartnerD;
    }


    /**
     * Sets the ProfileOveride property.
     *
     * @param pProfileOveride
     *  TradingPartnerAssocData to use to update the property.
     */
    public void setProfileOveride(TradingPartnerAssocData pProfileOveride){
        this.mProfileOveride = pProfileOveride;
    }
    /**
     * Retrieves the ProfileOveride property.
     *
     * @return
     *  TradingPartnerAssocData containing the ProfileOveride property.
     */
    public TradingPartnerAssocData getProfileOveride(){
        return mProfileOveride;
    }


    /**
     * Sets the IncommingProfileId property.
     *
     * @param pIncommingProfileId
     *  int to use to update the property.
     */
    public void setIncommingProfileId(int pIncommingProfileId){
        this.mIncommingProfileId = pIncommingProfileId;
    }
    /**
     * Retrieves the IncommingProfileId property.
     *
     * @return
     *  int containing the IncommingProfileId property.
     */
    public int getIncommingProfileId(){
        return mIncommingProfileId;
    }


    /**
     * Sets the TransactionsToProcess property.
     *
     * @param pTransactionsToProcess
     *  OutboundEDIRequestDataVector to use to update the property.
     */
    public void setTransactionsToProcess(OutboundEDIRequestDataVector pTransactionsToProcess){
        this.mTransactionsToProcess = pTransactionsToProcess;
    }
    /**
     * Retrieves the TransactionsToProcess property.
     *
     * @return
     *  OutboundEDIRequestDataVector containing the TransactionsToProcess property.
     */
    public OutboundEDIRequestDataVector getTransactionsToProcess(){
        return mTransactionsToProcess;
    }


    /**
     * Sets the DistErpNum property.
     *
     * @param pDistErpNum
     *  String to use to update the property.
     */
    public void setDistErpNum(String pDistErpNum){
        this.mDistErpNum = pDistErpNum;
    }
    /**
     * Retrieves the DistErpNum property.
     *
     * @return
     *  String containing the DistErpNum property.
     */
    public String getDistErpNum(){
        return mDistErpNum;
    }


    /**
     * Sets the TradingPartnerDescView property.
     *
     * @param pTradingPartnerDescView
     *  TradingPartnerDescView to use to update the property.
     */
    public void setTradingPartnerDescView(TradingPartnerDescView pTradingPartnerDescView){
        this.mTradingPartnerDescView = pTradingPartnerDescView;
    }
    /**
     * Retrieves the TradingPartnerDescView property.
     *
     * @return
     *  TradingPartnerDescView containing the TradingPartnerDescView property.
     */
    public TradingPartnerDescView getTradingPartnerDescView(){
        return mTradingPartnerDescView;
    }


    /**
     * Sets the Key property.
     *
     * @param pKey
     *  String to use to update the property.
     */
    public void setKey(String pKey){
        this.mKey = pKey;
    }
    /**
     * Retrieves the Key property.
     *
     * @return
     *  String containing the Key property.
     */
    public String getKey(){
        return mKey;
    }


    
}
