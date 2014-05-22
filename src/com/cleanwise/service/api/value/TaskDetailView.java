
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TaskDetailView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.session.ProcessEngine;
import com.cleanwise.service.api.process.ProcessActive;

import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>TaskDetailView</code> is a ViewObject class for UI.
 */
public class TaskDetailView
extends ValueObject
{
   
    private TaskData mTaskData;
    private TaskPropertyDataVector mTaskPropertyDV;
    private ProcessData mProcessData;
    private ProcessPropertyDataVector mProcessPropertyDV;
    private ProcessActive activeProcess;

    /**
     * Constructor.
     */
    private TaskDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public TaskDetailView(TaskData parm1, TaskPropertyDataVector parm2, ProcessData parm3, ProcessPropertyDataVector parm4)
    {
        mTaskData = parm1;
        mTaskPropertyDV = parm2;
        mProcessData = parm3;
        mProcessPropertyDV = parm4;
        
    }

    /**
     * Creates a new TaskDetailView
     *
     * @return
     *  Newly initialized TaskDetailView object.
     */
    public static TaskDetailView createValue () 
    {
        TaskDetailView valueView = new TaskDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TaskDetailView object
     */
    public String toString()
    {
        return "[" + "TaskData=" + mTaskData + ", TaskPropertyDV=" + mTaskPropertyDV + ", ProcessData=" + mProcessData + ", ProcessPropertyDV=" + mProcessPropertyDV + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("TaskDetail");
	root.setAttribute("Id", String.valueOf(mTaskData));

	Element node;

        node = doc.createElement("TaskPropertyDV");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskPropertyDV)));
        root.appendChild(node);

        node = doc.createElement("ProcessData");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessData)));
        root.appendChild(node);

        node = doc.createElement("ProcessPropertyDV");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessPropertyDV)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public TaskDetailView copy()  {
      TaskDetailView obj = new TaskDetailView();
      obj.setTaskData(mTaskData);
      obj.setTaskPropertyDV(mTaskPropertyDV);
      obj.setProcessData(mProcessData);
      obj.setProcessPropertyDV(mProcessPropertyDV);
      
      return obj;
    }

    
    /**
     * Sets the TaskData property.
     *
     * @param pTaskData
     *  TaskData to use to update the property.
     */
    public void setTaskData(TaskData pTaskData){
        this.mTaskData = pTaskData;
    }
    /**
     * Retrieves the TaskData property.
     *
     * @return
     *  TaskData containing the TaskData property.
     */
    public TaskData getTaskData(){
        return mTaskData;
    }


    /**
     * Sets the TaskPropertyDV property.
     *
     * @param pTaskPropertyDV
     *  TaskPropertyDataVector to use to update the property.
     */
    public void setTaskPropertyDV(TaskPropertyDataVector pTaskPropertyDV){
        this.mTaskPropertyDV = pTaskPropertyDV;
    }
    /**
     * Retrieves the TaskPropertyDV property.
     *
     * @return
     *  TaskPropertyDataVector containing the TaskPropertyDV property.
     */
    public TaskPropertyDataVector getTaskPropertyDV(){
        return mTaskPropertyDV;
    }


    /**
     * Sets the ProcessData property.
     *
     * @param pProcessData
     *  ProcessData to use to update the property.
     */
    public void setProcessData(ProcessData pProcessData){
        this.mProcessData = pProcessData;
    }
    /**
     * Retrieves the ProcessData property.
     *
     * @return
     *  ProcessData containing the ProcessData property.
     */
    public ProcessData getProcessData(){
        return mProcessData;
    }


    /**
     * Sets the ProcessPropertyDV property.
     *
     * @param pProcessPropertyDV
     *  ProcessPropertyDataVector to use to update the property.
     */
    public void setProcessPropertyDV(ProcessPropertyDataVector pProcessPropertyDV){
        this.mProcessPropertyDV = pProcessPropertyDV;
    }
    /**
     * Retrieves the ProcessPropertyDV property.
     *
     * @return
     *  ProcessPropertyDataVector containing the ProcessPropertyDV property.
     */
    public ProcessPropertyDataVector getProcessPropertyDV(){
        return mProcessPropertyDV;
    }


    public void setActiveProcess(ProcessActive activeProcess) {
        this.activeProcess = activeProcess;
    }

    public ProcessActive getActiveProcess() {
        return activeProcess;
    }
}
