
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderAssetView
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
 * <code>WorkOrderAssetView</code> is a ViewObject class for UI.
 */
public class WorkOrderAssetView
extends ValueObject
{
   
    private static final long serialVersionUID = 8803320878698406281L;
    private AssetView mAssetView;
    private WorkOrderAssetData mWorkOrderAssetData;

    /**
     * Constructor.
     */
    public WorkOrderAssetView ()
    {
    }

    /**
     * Constructor. 
     */
    public WorkOrderAssetView(AssetView parm1, WorkOrderAssetData parm2)
    {
        mAssetView = parm1;
        mWorkOrderAssetData = parm2;
        
    }

    /**
     * Creates a new WorkOrderAssetView
     *
     * @return
     *  Newly initialized WorkOrderAssetView object.
     */
    public static WorkOrderAssetView createValue () 
    {
        WorkOrderAssetView valueView = new WorkOrderAssetView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderAssetView object
     */
    public String toString()
    {
        return "[" + "AssetView=" + mAssetView + ", WorkOrderAssetData=" + mWorkOrderAssetData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderAsset");
	root.setAttribute("Id", String.valueOf(mAssetView));

	Element node;

        node = doc.createElement("WorkOrderAssetData");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderAssetData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderAssetView copy()  {
      WorkOrderAssetView obj = new WorkOrderAssetView();
      obj.setAssetView(mAssetView);
      obj.setWorkOrderAssetData(mWorkOrderAssetData);
      
      return obj;
    }

    
    /**
     * Sets the AssetView property.
     *
     * @param pAssetView
     *  AssetView to use to update the property.
     */
    public void setAssetView(AssetView pAssetView){
        this.mAssetView = pAssetView;
    }
    /**
     * Retrieves the AssetView property.
     *
     * @return
     *  AssetView containing the AssetView property.
     */
    public AssetView getAssetView(){
        return mAssetView;
    }


    /**
     * Sets the WorkOrderAssetData property.
     *
     * @param pWorkOrderAssetData
     *  WorkOrderAssetData to use to update the property.
     */
    public void setWorkOrderAssetData(WorkOrderAssetData pWorkOrderAssetData){
        this.mWorkOrderAssetData = pWorkOrderAssetData;
    }
    /**
     * Retrieves the WorkOrderAssetData property.
     *
     * @return
     *  WorkOrderAssetData containing the WorkOrderAssetData property.
     */
    public WorkOrderAssetData getWorkOrderAssetData(){
        return mWorkOrderAssetData;
    }


    
}
