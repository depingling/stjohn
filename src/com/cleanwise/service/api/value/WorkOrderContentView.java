
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderContentView
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
 * <code>WorkOrderContentView</code> is a ViewObject class for UI.
 */
public class WorkOrderContentView
extends ValueObject
{
   
    private static final long serialVersionUID = -5859489922039115270L;
    private ContentDetailView mContent;
    private WorkOrderContentData mWorkOrderContentData;

    /**
     * Constructor.
     */
    public WorkOrderContentView ()
    {
    }

    /**
     * Constructor. 
     */
    public WorkOrderContentView(ContentDetailView parm1, WorkOrderContentData parm2)
    {
        mContent = parm1;
        mWorkOrderContentData = parm2;
        
    }

    /**
     * Creates a new WorkOrderContentView
     *
     * @return
     *  Newly initialized WorkOrderContentView object.
     */
    public static WorkOrderContentView createValue () 
    {
        WorkOrderContentView valueView = new WorkOrderContentView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderContentView object
     */
    public String toString()
    {
        return "[" + "Content=" + mContent + ", WorkOrderContentData=" + mWorkOrderContentData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderContent");
	root.setAttribute("Id", String.valueOf(mContent));

	Element node;

        node = doc.createElement("WorkOrderContentData");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderContentData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderContentView copy()  {
      WorkOrderContentView obj = new WorkOrderContentView();
      obj.setContent(mContent);
      obj.setWorkOrderContentData(mWorkOrderContentData);
      
      return obj;
    }

    
    /**
     * Sets the Content property.
     *
     * @param pContent
     *  ContentDetailView to use to update the property.
     */
    public void setContent(ContentDetailView pContent){
        this.mContent = pContent;
    }
    /**
     * Retrieves the Content property.
     *
     * @return
     *  ContentDetailView containing the Content property.
     */
    public ContentDetailView getContent(){
        return mContent;
    }


    /**
     * Sets the WorkOrderContentData property.
     *
     * @param pWorkOrderContentData
     *  WorkOrderContentData to use to update the property.
     */
    public void setWorkOrderContentData(WorkOrderContentData pWorkOrderContentData){
        this.mWorkOrderContentData = pWorkOrderContentData;
    }
    /**
     * Retrieves the WorkOrderContentData property.
     *
     * @return
     *  WorkOrderContentData containing the WorkOrderContentData property.
     */
    public WorkOrderContentData getWorkOrderContentData(){
        return mWorkOrderContentData;
    }


    
}
