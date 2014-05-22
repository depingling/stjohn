
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProcessOutboundResultView
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
 * <code>ProcessOutboundResultView</code> is a ViewObject class for UI.
 */
public class ProcessOutboundResultView
extends ValueObject
{
   
    private static final long serialVersionUID = 5040288836726816506L;
    private Object mProcessResult;
    private IntegrationRequestsVector mIntegrationRequests;
    private TradingPartnerDescView mTradingPartnerDescView;
    private String mDistErpNum;
    private OutboundEDIRequestDataVector mOutboundEDIRequests;

    /**
     * Constructor.
     */
    public ProcessOutboundResultView ()
    {
        mDistErpNum = "";
    }

    /**
     * Constructor. 
     */
    public ProcessOutboundResultView(Object parm1, IntegrationRequestsVector parm2, TradingPartnerDescView parm3, String parm4, OutboundEDIRequestDataVector parm5)
    {
        mProcessResult = parm1;
        mIntegrationRequests = parm2;
        mTradingPartnerDescView = parm3;
        mDistErpNum = parm4;
        mOutboundEDIRequests = parm5;
        
    }

    /**
     * Creates a new ProcessOutboundResultView
     *
     * @return
     *  Newly initialized ProcessOutboundResultView object.
     */
    public static ProcessOutboundResultView createValue () 
    {
        ProcessOutboundResultView valueView = new ProcessOutboundResultView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProcessOutboundResultView object
     */
    public String toString()
    {
        return "[" + "ProcessResult=" + mProcessResult + ", IntegrationRequests=" + mIntegrationRequests + ", TradingPartnerDescView=" + mTradingPartnerDescView + ", DistErpNum=" + mDistErpNum + ", OutboundEDIRequests=" + mOutboundEDIRequests + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ProcessOutboundResult");
	root.setAttribute("Id", String.valueOf(mProcessResult));

	Element node;

        node = doc.createElement("IntegrationRequests");
        node.appendChild(doc.createTextNode(String.valueOf(mIntegrationRequests)));
        root.appendChild(node);

        node = doc.createElement("TradingPartnerDescView");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerDescView)));
        root.appendChild(node);

        node = doc.createElement("DistErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpNum)));
        root.appendChild(node);

        node = doc.createElement("OutboundEDIRequests");
        node.appendChild(doc.createTextNode(String.valueOf(mOutboundEDIRequests)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProcessOutboundResultView copy()  {
      ProcessOutboundResultView obj = new ProcessOutboundResultView();
      obj.setProcessResult(mProcessResult);
      obj.setIntegrationRequests(mIntegrationRequests);
      obj.setTradingPartnerDescView(mTradingPartnerDescView);
      obj.setDistErpNum(mDistErpNum);
      obj.setOutboundEDIRequests(mOutboundEDIRequests);
      
      return obj;
    }

    
    /**
     * Sets the ProcessResult property.
     *
     * @param pProcessResult
     *  Object to use to update the property.
     */
    public void setProcessResult(Object pProcessResult){
        this.mProcessResult = pProcessResult;
    }
    /**
     * Retrieves the ProcessResult property.
     *
     * @return
     *  Object containing the ProcessResult property.
     */
    public Object getProcessResult(){
        return mProcessResult;
    }


    /**
     * Sets the IntegrationRequests property.
     *
     * @param pIntegrationRequests
     *  IntegrationRequestsVector to use to update the property.
     */
    public void setIntegrationRequests(IntegrationRequestsVector pIntegrationRequests){
        this.mIntegrationRequests = pIntegrationRequests;
    }
    /**
     * Retrieves the IntegrationRequests property.
     *
     * @return
     *  IntegrationRequestsVector containing the IntegrationRequests property.
     */
    public IntegrationRequestsVector getIntegrationRequests(){
        return mIntegrationRequests;
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
     * Sets the OutboundEDIRequests property.
     *
     * @param pOutboundEDIRequests
     *  OutboundEDIRequestDataVector to use to update the property.
     */
    public void setOutboundEDIRequests(OutboundEDIRequestDataVector pOutboundEDIRequests){
        this.mOutboundEDIRequests = pOutboundEDIRequests;
    }
    /**
     * Retrieves the OutboundEDIRequests property.
     *
     * @return
     *  OutboundEDIRequestDataVector containing the OutboundEDIRequests property.
     */
    public OutboundEDIRequestDataVector getOutboundEDIRequests(){
        return mOutboundEDIRequests;
    }


    
}
