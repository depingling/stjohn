
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowRuleJoinView
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
 * <code>WorkflowRuleJoinView</code> is a ViewObject class for UI.
 */
public class WorkflowRuleJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = 3610890620298819511L;
    private int mWorkflowId;
    private int mRuleSeq;
    private String mRuleTypeCd;
    private WorkflowRuleDataVector mRules;
    private WorkflowAssocDataVector mAssociations;

    /**
     * Constructor.
     */
    public WorkflowRuleJoinView ()
    {
        mRuleTypeCd = "";
    }

    /**
     * Constructor. 
     */
    public WorkflowRuleJoinView(int parm1, int parm2, String parm3, WorkflowRuleDataVector parm4, WorkflowAssocDataVector parm5)
    {
        mWorkflowId = parm1;
        mRuleSeq = parm2;
        mRuleTypeCd = parm3;
        mRules = parm4;
        mAssociations = parm5;
        
    }

    /**
     * Creates a new WorkflowRuleJoinView
     *
     * @return
     *  Newly initialized WorkflowRuleJoinView object.
     */
    public static WorkflowRuleJoinView createValue () 
    {
        WorkflowRuleJoinView valueView = new WorkflowRuleJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowRuleJoinView object
     */
    public String toString()
    {
        return "[" + "WorkflowId=" + mWorkflowId + ", RuleSeq=" + mRuleSeq + ", RuleTypeCd=" + mRuleTypeCd + ", Rules=" + mRules + ", Associations=" + mAssociations + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkflowRuleJoin");
	root.setAttribute("Id", String.valueOf(mWorkflowId));

	Element node;

        node = doc.createElement("RuleSeq");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleSeq)));
        root.appendChild(node);

        node = doc.createElement("RuleTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleTypeCd)));
        root.appendChild(node);

        node = doc.createElement("Rules");
        node.appendChild(doc.createTextNode(String.valueOf(mRules)));
        root.appendChild(node);

        node = doc.createElement("Associations");
        node.appendChild(doc.createTextNode(String.valueOf(mAssociations)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkflowRuleJoinView copy()  {
      WorkflowRuleJoinView obj = new WorkflowRuleJoinView();
      obj.setWorkflowId(mWorkflowId);
      obj.setRuleSeq(mRuleSeq);
      obj.setRuleTypeCd(mRuleTypeCd);
      obj.setRules(mRules);
      obj.setAssociations(mAssociations);
      
      return obj;
    }

    
    /**
     * Sets the WorkflowId property.
     *
     * @param pWorkflowId
     *  int to use to update the property.
     */
    public void setWorkflowId(int pWorkflowId){
        this.mWorkflowId = pWorkflowId;
    }
    /**
     * Retrieves the WorkflowId property.
     *
     * @return
     *  int containing the WorkflowId property.
     */
    public int getWorkflowId(){
        return mWorkflowId;
    }


    /**
     * Sets the RuleSeq property.
     *
     * @param pRuleSeq
     *  int to use to update the property.
     */
    public void setRuleSeq(int pRuleSeq){
        this.mRuleSeq = pRuleSeq;
    }
    /**
     * Retrieves the RuleSeq property.
     *
     * @return
     *  int containing the RuleSeq property.
     */
    public int getRuleSeq(){
        return mRuleSeq;
    }


    /**
     * Sets the RuleTypeCd property.
     *
     * @param pRuleTypeCd
     *  String to use to update the property.
     */
    public void setRuleTypeCd(String pRuleTypeCd){
        this.mRuleTypeCd = pRuleTypeCd;
    }
    /**
     * Retrieves the RuleTypeCd property.
     *
     * @return
     *  String containing the RuleTypeCd property.
     */
    public String getRuleTypeCd(){
        return mRuleTypeCd;
    }


    /**
     * Sets the Rules property.
     *
     * @param pRules
     *  WorkflowRuleDataVector to use to update the property.
     */
    public void setRules(WorkflowRuleDataVector pRules){
        this.mRules = pRules;
    }
    /**
     * Retrieves the Rules property.
     *
     * @return
     *  WorkflowRuleDataVector containing the Rules property.
     */
    public WorkflowRuleDataVector getRules(){
        return mRules;
    }


    /**
     * Sets the Associations property.
     *
     * @param pAssociations
     *  WorkflowAssocDataVector to use to update the property.
     */
    public void setAssociations(WorkflowAssocDataVector pAssociations){
        this.mAssociations = pAssociations;
    }
    /**
     * Retrieves the Associations property.
     *
     * @return
     *  WorkflowAssocDataVector containing the Associations property.
     */
    public WorkflowAssocDataVector getAssociations(){
        return mAssociations;
    }


    
}
