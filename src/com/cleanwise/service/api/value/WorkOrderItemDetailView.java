
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderItemDetailView
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
 * <code>WorkOrderItemDetailView</code> is a ViewObject class for UI.
 */
public class WorkOrderItemDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -4395890385498174161L;
    private WorkOrderItemData mWorkOrderItem;
    private WoiStatusHistDataVector mStatusHistories;
    private WarrantyData mWarranty;
    private WorkOrderAssetViewVector mAssetAssoc;
    private WorkOrderContentViewVector mContents;
    private WorkOrderPropertyDataVector mProperties;

    /**
     * Constructor.
     */
    public WorkOrderItemDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public WorkOrderItemDetailView(WorkOrderItemData parm1, WoiStatusHistDataVector parm2, WarrantyData parm3, WorkOrderAssetViewVector parm4, WorkOrderContentViewVector parm5, WorkOrderPropertyDataVector parm6)
    {
        mWorkOrderItem = parm1;
        mStatusHistories = parm2;
        mWarranty = parm3;
        mAssetAssoc = parm4;
        mContents = parm5;
        mProperties = parm6;
        
    }

    /**
     * Creates a new WorkOrderItemDetailView
     *
     * @return
     *  Newly initialized WorkOrderItemDetailView object.
     */
    public static WorkOrderItemDetailView createValue () 
    {
        WorkOrderItemDetailView valueView = new WorkOrderItemDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderItemDetailView object
     */
    public String toString()
    {
        return "[" + "WorkOrderItem=" + mWorkOrderItem + ", StatusHistories=" + mStatusHistories + ", Warranty=" + mWarranty + ", AssetAssoc=" + mAssetAssoc + ", Contents=" + mContents + ", Properties=" + mProperties + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderItemDetail");
	root.setAttribute("Id", String.valueOf(mWorkOrderItem));

	Element node;

        node = doc.createElement("StatusHistories");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusHistories)));
        root.appendChild(node);

        node = doc.createElement("Warranty");
        node.appendChild(doc.createTextNode(String.valueOf(mWarranty)));
        root.appendChild(node);

        node = doc.createElement("AssetAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetAssoc)));
        root.appendChild(node);

        node = doc.createElement("Contents");
        node.appendChild(doc.createTextNode(String.valueOf(mContents)));
        root.appendChild(node);

        node = doc.createElement("Properties");
        node.appendChild(doc.createTextNode(String.valueOf(mProperties)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderItemDetailView copy()  {
      WorkOrderItemDetailView obj = new WorkOrderItemDetailView();
      obj.setWorkOrderItem(mWorkOrderItem);
      obj.setStatusHistories(mStatusHistories);
      obj.setWarranty(mWarranty);
      obj.setAssetAssoc(mAssetAssoc);
      obj.setContents(mContents);
      obj.setProperties(mProperties);
      
      return obj;
    }

    
    /**
     * Sets the WorkOrderItem property.
     *
     * @param pWorkOrderItem
     *  WorkOrderItemData to use to update the property.
     */
    public void setWorkOrderItem(WorkOrderItemData pWorkOrderItem){
        this.mWorkOrderItem = pWorkOrderItem;
    }
    /**
     * Retrieves the WorkOrderItem property.
     *
     * @return
     *  WorkOrderItemData containing the WorkOrderItem property.
     */
    public WorkOrderItemData getWorkOrderItem(){
        return mWorkOrderItem;
    }


    /**
     * Sets the StatusHistories property.
     *
     * @param pStatusHistories
     *  WoiStatusHistDataVector to use to update the property.
     */
    public void setStatusHistories(WoiStatusHistDataVector pStatusHistories){
        this.mStatusHistories = pStatusHistories;
    }
    /**
     * Retrieves the StatusHistories property.
     *
     * @return
     *  WoiStatusHistDataVector containing the StatusHistories property.
     */
    public WoiStatusHistDataVector getStatusHistories(){
        return mStatusHistories;
    }


    /**
     * Sets the Warranty property.
     *
     * @param pWarranty
     *  WarrantyData to use to update the property.
     */
    public void setWarranty(WarrantyData pWarranty){
        this.mWarranty = pWarranty;
    }
    /**
     * Retrieves the Warranty property.
     *
     * @return
     *  WarrantyData containing the Warranty property.
     */
    public WarrantyData getWarranty(){
        return mWarranty;
    }


    /**
     * Sets the AssetAssoc property.
     *
     * @param pAssetAssoc
     *  WorkOrderAssetViewVector to use to update the property.
     */
    public void setAssetAssoc(WorkOrderAssetViewVector pAssetAssoc){
        this.mAssetAssoc = pAssetAssoc;
    }
    /**
     * Retrieves the AssetAssoc property.
     *
     * @return
     *  WorkOrderAssetViewVector containing the AssetAssoc property.
     */
    public WorkOrderAssetViewVector getAssetAssoc(){
        return mAssetAssoc;
    }


    /**
     * Sets the Contents property.
     *
     * @param pContents
     *  WorkOrderContentViewVector to use to update the property.
     */
    public void setContents(WorkOrderContentViewVector pContents){
        this.mContents = pContents;
    }
    /**
     * Retrieves the Contents property.
     *
     * @return
     *  WorkOrderContentViewVector containing the Contents property.
     */
    public WorkOrderContentViewVector getContents(){
        return mContents;
    }


    /**
     * Sets the Properties property.
     *
     * @param pProperties
     *  WorkOrderPropertyDataVector to use to update the property.
     */
    public void setProperties(WorkOrderPropertyDataVector pProperties){
        this.mProperties = pProperties;
    }
    /**
     * Retrieves the Properties property.
     *
     * @return
     *  WorkOrderPropertyDataVector containing the Properties property.
     */
    public WorkOrderPropertyDataVector getProperties(){
        return mProperties;
    }


    
}
