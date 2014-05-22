
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderSiteNameView
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
 * <code>WorkOrderSiteNameView</code> is a ViewObject class for UI.
 */
public class WorkOrderSiteNameView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private WorkOrderData mWorkOrder;
    private String mSiteName;
    private String mDistributorShipToNumber;

    /**
     * Constructor.
     */
    public WorkOrderSiteNameView ()
    {
        mSiteName = "";
        mDistributorShipToNumber = "";
    }

    /**
     * Constructor. 
     */
    public WorkOrderSiteNameView(WorkOrderData parm1, String parm2, String parm3)
    {
        mWorkOrder = parm1;
        mSiteName = parm2;
        mDistributorShipToNumber = parm3;
        
    }

    /**
     * Creates a new WorkOrderSiteNameView
     *
     * @return
     *  Newly initialized WorkOrderSiteNameView object.
     */
    public static WorkOrderSiteNameView createValue () 
    {
        WorkOrderSiteNameView valueView = new WorkOrderSiteNameView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderSiteNameView object
     */
    public String toString()
    {
        return "[" + "WorkOrder=" + mWorkOrder + ", SiteName=" + mSiteName + ", DistributorShipToNumber=" + mDistributorShipToNumber + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderSiteName");
	root.setAttribute("Id", String.valueOf(mWorkOrder));

	Element node;

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node = doc.createElement("DistributorShipToNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorShipToNumber)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderSiteNameView copy()  {
      WorkOrderSiteNameView obj = new WorkOrderSiteNameView();
      obj.setWorkOrder(mWorkOrder);
      obj.setSiteName(mSiteName);
      obj.setDistributorShipToNumber(mDistributorShipToNumber);
      
      return obj;
    }

    
    /**
     * Sets the WorkOrder property.
     *
     * @param pWorkOrder
     *  WorkOrderData to use to update the property.
     */
    public void setWorkOrder(WorkOrderData pWorkOrder){
        this.mWorkOrder = pWorkOrder;
    }
    /**
     * Retrieves the WorkOrder property.
     *
     * @return
     *  WorkOrderData containing the WorkOrder property.
     */
    public WorkOrderData getWorkOrder(){
        return mWorkOrder;
    }


    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
    }


    /**
     * Sets the DistributorShipToNumber property.
     *
     * @param pDistributorShipToNumber
     *  String to use to update the property.
     */
    public void setDistributorShipToNumber(String pDistributorShipToNumber){
        this.mDistributorShipToNumber = pDistributorShipToNumber;
    }
    /**
     * Retrieves the DistributorShipToNumber property.
     *
     * @return
     *  String containing the DistributorShipToNumber property.
     */
    public String getDistributorShipToNumber(){
        return mDistributorShipToNumber;
    }


    
}
