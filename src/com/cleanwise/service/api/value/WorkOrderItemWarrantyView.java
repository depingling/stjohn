
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderItemWarrantyView
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
 * <code>WorkOrderItemWarrantyView</code> is a ViewObject class for UI.
 */
public class WorkOrderItemWarrantyView
extends ValueObject
{
   
    private static final long serialVersionUID = -4449934561685799912L;
    private WorkOrderData mWorkOrder;
    private WorkOrderItemDataVector mWorkOrderItems;

    /**
     * Constructor.
     */
    public WorkOrderItemWarrantyView ()
    {
    }

    /**
     * Constructor. 
     */
    public WorkOrderItemWarrantyView(WorkOrderData parm1, WorkOrderItemDataVector parm2)
    {
        mWorkOrder = parm1;
        mWorkOrderItems = parm2;
        
    }

    /**
     * Creates a new WorkOrderItemWarrantyView
     *
     * @return
     *  Newly initialized WorkOrderItemWarrantyView object.
     */
    public static WorkOrderItemWarrantyView createValue () 
    {
        WorkOrderItemWarrantyView valueView = new WorkOrderItemWarrantyView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderItemWarrantyView object
     */
    public String toString()
    {
        return "[" + "WorkOrder=" + mWorkOrder + ", WorkOrderItems=" + mWorkOrderItems + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderItemWarranty");
	root.setAttribute("Id", String.valueOf(mWorkOrder));

	Element node;

        node = doc.createElement("WorkOrderItems");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItems)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderItemWarrantyView copy()  {
      WorkOrderItemWarrantyView obj = new WorkOrderItemWarrantyView();
      obj.setWorkOrder(mWorkOrder);
      obj.setWorkOrderItems(mWorkOrderItems);
      
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
     * Sets the WorkOrderItems property.
     *
     * @param pWorkOrderItems
     *  WorkOrderItemDataVector to use to update the property.
     */
    public void setWorkOrderItems(WorkOrderItemDataVector pWorkOrderItems){
        this.mWorkOrderItems = pWorkOrderItems;
    }
    /**
     * Retrieves the WorkOrderItems property.
     *
     * @return
     *  WorkOrderItemDataVector containing the WorkOrderItems property.
     */
    public WorkOrderItemDataVector getWorkOrderItems(){
        return mWorkOrderItems;
    }


    
}
