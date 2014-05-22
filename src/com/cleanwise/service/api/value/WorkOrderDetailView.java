
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderDetailView
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
 * <code>WorkOrderDetailView</code> is a ViewObject class for UI.
 */
public class WorkOrderDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -8757732130342876285L;
    private WorkOrderData mWorkOrder;
    private WorkOrderStatusHistDataVector mStatusHistory;
    private WorkOrderContentViewVector mContents;
    private WorkOrderNoteDataVector mNotes;
    private WorkOrderItemDetailViewVector mWorkOrderItems;
    private WorkOrderAssocDataVector mWorkOrderAssocCollection;
    private WorkOrderPropertyDataVector mProperties;
    private ServiceProviderData mServiceProvider;
    private WorkOrderDetailDataVector mItemizedService;

    /**
     * Constructor.
     */
    public WorkOrderDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public WorkOrderDetailView(WorkOrderData parm1, WorkOrderStatusHistDataVector parm2, WorkOrderContentViewVector parm3, WorkOrderNoteDataVector parm4, WorkOrderItemDetailViewVector parm5, WorkOrderAssocDataVector parm6, WorkOrderPropertyDataVector parm7, ServiceProviderData parm8, WorkOrderDetailDataVector parm9)
    {
        mWorkOrder = parm1;
        mStatusHistory = parm2;
        mContents = parm3;
        mNotes = parm4;
        mWorkOrderItems = parm5;
        mWorkOrderAssocCollection = parm6;
        mProperties = parm7;
        mServiceProvider = parm8;
        mItemizedService = parm9;
        
    }

    /**
     * Creates a new WorkOrderDetailView
     *
     * @return
     *  Newly initialized WorkOrderDetailView object.
     */
    public static WorkOrderDetailView createValue () 
    {
        WorkOrderDetailView valueView = new WorkOrderDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderDetailView object
     */
    public String toString()
    {
        return "[" + "WorkOrder=" + mWorkOrder + ", StatusHistory=" + mStatusHistory + ", Contents=" + mContents + ", Notes=" + mNotes + ", WorkOrderItems=" + mWorkOrderItems + ", WorkOrderAssocCollection=" + mWorkOrderAssocCollection + ", Properties=" + mProperties + ", ServiceProvider=" + mServiceProvider + ", ItemizedService=" + mItemizedService + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderDetail");
	root.setAttribute("Id", String.valueOf(mWorkOrder));

	Element node;

        node = doc.createElement("StatusHistory");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusHistory)));
        root.appendChild(node);

        node = doc.createElement("Contents");
        node.appendChild(doc.createTextNode(String.valueOf(mContents)));
        root.appendChild(node);

        node = doc.createElement("Notes");
        node.appendChild(doc.createTextNode(String.valueOf(mNotes)));
        root.appendChild(node);

        node = doc.createElement("WorkOrderItems");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItems)));
        root.appendChild(node);

        node = doc.createElement("WorkOrderAssocCollection");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderAssocCollection)));
        root.appendChild(node);

        node = doc.createElement("Properties");
        node.appendChild(doc.createTextNode(String.valueOf(mProperties)));
        root.appendChild(node);

        node = doc.createElement("ServiceProvider");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceProvider)));
        root.appendChild(node);

        node = doc.createElement("ItemizedService");
        node.appendChild(doc.createTextNode(String.valueOf(mItemizedService)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderDetailView copy()  {
      WorkOrderDetailView obj = new WorkOrderDetailView();
      obj.setWorkOrder(mWorkOrder);
      obj.setStatusHistory(mStatusHistory);
      obj.setContents(mContents);
      obj.setNotes(mNotes);
      obj.setWorkOrderItems(mWorkOrderItems);
      obj.setWorkOrderAssocCollection(mWorkOrderAssocCollection);
      obj.setProperties(mProperties);
      obj.setServiceProvider(mServiceProvider);
      obj.setItemizedService(mItemizedService);
      
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
     * Sets the StatusHistory property.
     *
     * @param pStatusHistory
     *  WorkOrderStatusHistDataVector to use to update the property.
     */
    public void setStatusHistory(WorkOrderStatusHistDataVector pStatusHistory){
        this.mStatusHistory = pStatusHistory;
    }
    /**
     * Retrieves the StatusHistory property.
     *
     * @return
     *  WorkOrderStatusHistDataVector containing the StatusHistory property.
     */
    public WorkOrderStatusHistDataVector getStatusHistory(){
        return mStatusHistory;
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
     * Sets the Notes property.
     *
     * @param pNotes
     *  WorkOrderNoteDataVector to use to update the property.
     */
    public void setNotes(WorkOrderNoteDataVector pNotes){
        this.mNotes = pNotes;
    }
    /**
     * Retrieves the Notes property.
     *
     * @return
     *  WorkOrderNoteDataVector containing the Notes property.
     */
    public WorkOrderNoteDataVector getNotes(){
        return mNotes;
    }


    /**
     * Sets the WorkOrderItems property.
     *
     * @param pWorkOrderItems
     *  WorkOrderItemDetailViewVector to use to update the property.
     */
    public void setWorkOrderItems(WorkOrderItemDetailViewVector pWorkOrderItems){
        this.mWorkOrderItems = pWorkOrderItems;
    }
    /**
     * Retrieves the WorkOrderItems property.
     *
     * @return
     *  WorkOrderItemDetailViewVector containing the WorkOrderItems property.
     */
    public WorkOrderItemDetailViewVector getWorkOrderItems(){
        return mWorkOrderItems;
    }


    /**
     * Sets the WorkOrderAssocCollection property.
     *
     * @param pWorkOrderAssocCollection
     *  WorkOrderAssocDataVector to use to update the property.
     */
    public void setWorkOrderAssocCollection(WorkOrderAssocDataVector pWorkOrderAssocCollection){
        this.mWorkOrderAssocCollection = pWorkOrderAssocCollection;
    }
    /**
     * Retrieves the WorkOrderAssocCollection property.
     *
     * @return
     *  WorkOrderAssocDataVector containing the WorkOrderAssocCollection property.
     */
    public WorkOrderAssocDataVector getWorkOrderAssocCollection(){
        return mWorkOrderAssocCollection;
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


    /**
     * Sets the ServiceProvider property.
     *
     * @param pServiceProvider
     *  ServiceProviderData to use to update the property.
     */
    public void setServiceProvider(ServiceProviderData pServiceProvider){
        this.mServiceProvider = pServiceProvider;
    }
    /**
     * Retrieves the ServiceProvider property.
     *
     * @return
     *  ServiceProviderData containing the ServiceProvider property.
     */
    public ServiceProviderData getServiceProvider(){
        return mServiceProvider;
    }


    /**
     * Sets the ItemizedService property.
     *
     * @param pItemizedService
     *  WorkOrderDetailDataVector to use to update the property.
     */
    public void setItemizedService(WorkOrderDetailDataVector pItemizedService){
        this.mItemizedService = pItemizedService;
    }
    /**
     * Retrieves the ItemizedService property.
     *
     * @return
     *  WorkOrderDetailDataVector containing the ItemizedService property.
     */
    public WorkOrderDetailDataVector getItemizedService(){
        return mItemizedService;
    }


    
}
