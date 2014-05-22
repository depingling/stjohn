
package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowDescData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

public class WorkflowDescData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -7491516184552729255L;
    
    private WorkflowData mWorkflow;
    private String mBusEntityShortDesc = new String("");
    private String mBusEntityTypeCd = new String("");
    
    /**
     * Constructor.
     */
    private WorkflowDescData()
    {
        mBusEntityShortDesc = "";
    }

    /**
     * Constructor. 
     */
    public WorkflowDescData(WorkflowData parm1, String parm2)
    {
        mWorkflow = parm1;
        mBusEntityShortDesc = parm2;
    }

    /**
     * Creates a new WorkflowDescData
     *
     * @return
     *  Newly initialized WorkflowDescData object.
     */
    public static WorkflowDescData createValue () 
    {
        WorkflowDescData valueData = new WorkflowDescData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowDescData object
     */
    public String toString()
    {
        return "[" + "Workflow=" + mWorkflow.toString() + ", BusEntityShortDesc=" + mBusEntityShortDesc  + "]";
    }

    
    /**
     * Sets the Workflow field. 
     *
     * @param pWorkflow
     *  int to use to update the field.
     */
    public void setWorkflow(WorkflowData pWorkflow){
        this.mWorkflow = pWorkflow;
    }
    /**
     * Retrieves the Workflow field.
     *
     * @return
     *  WorkflowData containing the Workflow field.
     */
    public WorkflowData getWorkflow(){
        return mWorkflow;
    }

    /**
     * Sets the BusEntityShortDesc field. 
     *
     * @param pBusEntity
     *  String to use to update the field.
     */
    public void setBusEntityShortDesc(String pBusEntityShortDesc){
        this.mBusEntityShortDesc = pBusEntityShortDesc;
    }

    /**
     * Retrieves the BusEntityShortDesc field.
     *
     * @return
     *  String containing the BusEntityShortDesc field.
     */
    public String getBusEntityShortDesc(){
        return mBusEntityShortDesc;
    }


    /**
     * Sets the BusEntityTypeCd field. 
     *
     * @param v
     *  String to use to update the field.
     */
    public void setBusEntityTypeCd(String v){
        this.mBusEntityTypeCd = v;
    }

    /**
     * Retrieves the BusEntityTypeCd field.
     *
     * @return
     *  String containing the BusEntityTypeCd field.
     */
    public String getBusEntityTypeCd(){
        return mBusEntityTypeCd;
    }

    
    
}
