
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowRuleData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW_RULE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkflowRuleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkflowRuleData</code> is a ValueObject class wrapping of the database table CLW_WORKFLOW_RULE.
 */
public class WorkflowRuleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4975690026283822198L;
    private int mWorkflowRuleId;// SQL type:NUMBER, not null
    private int mRuleSeq;// SQL type:NUMBER
    private int mWorkflowId;// SQL type:NUMBER
    private String mRuleTypeCd;// SQL type:VARCHAR2, not null
    private String mRuleExp;// SQL type:VARCHAR2, not null
    private String mRuleExpValue;// SQL type:VARCHAR2, not null
    private String mRuleAction;// SQL type:VARCHAR2
    private String mNextActionCd;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mWorkflowRuleStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mApproverGroupId;// SQL type:NUMBER
    private int mEmailGroupId;// SQL type:NUMBER
    private String mRuleGroup;// SQL type:VARCHAR2
    private String mWarningMessage;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkflowRuleData ()
    {
        mRuleTypeCd = "";
        mRuleExp = "";
        mRuleExpValue = "";
        mRuleAction = "";
        mNextActionCd = "";
        mShortDesc = "";
        mWorkflowRuleStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mRuleGroup = "";
        mWarningMessage = "";
    }

    /**
     * Constructor.
     */
    public WorkflowRuleData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, Date parm11, String parm12, Date parm13, String parm14, int parm15, int parm16, String parm17, String parm18)
    {
        mWorkflowRuleId = parm1;
        mRuleSeq = parm2;
        mWorkflowId = parm3;
        mRuleTypeCd = parm4;
        mRuleExp = parm5;
        mRuleExpValue = parm6;
        mRuleAction = parm7;
        mNextActionCd = parm8;
        mShortDesc = parm9;
        mWorkflowRuleStatusCd = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        mApproverGroupId = parm15;
        mEmailGroupId = parm16;
        mRuleGroup = parm17;
        mWarningMessage = parm18;
        
    }

    /**
     * Creates a new WorkflowRuleData
     *
     * @return
     *  Newly initialized WorkflowRuleData object.
     */
    public static WorkflowRuleData createValue ()
    {
        WorkflowRuleData valueData = new WorkflowRuleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowRuleData object
     */
    public String toString()
    {
        return "[" + "WorkflowRuleId=" + mWorkflowRuleId + ", RuleSeq=" + mRuleSeq + ", WorkflowId=" + mWorkflowId + ", RuleTypeCd=" + mRuleTypeCd + ", RuleExp=" + mRuleExp + ", RuleExpValue=" + mRuleExpValue + ", RuleAction=" + mRuleAction + ", NextActionCd=" + mNextActionCd + ", ShortDesc=" + mShortDesc + ", WorkflowRuleStatusCd=" + mWorkflowRuleStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ApproverGroupId=" + mApproverGroupId + ", EmailGroupId=" + mEmailGroupId + ", RuleGroup=" + mRuleGroup + ", WarningMessage=" + mWarningMessage + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkflowRule");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkflowRuleId));

        node =  doc.createElement("RuleSeq");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleSeq)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowId)));
        root.appendChild(node);

        node =  doc.createElement("RuleTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("RuleExp");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleExp)));
        root.appendChild(node);

        node =  doc.createElement("RuleExpValue");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleExpValue)));
        root.appendChild(node);

        node =  doc.createElement("RuleAction");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleAction)));
        root.appendChild(node);

        node =  doc.createElement("NextActionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mNextActionCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRuleStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRuleStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ApproverGroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mApproverGroupId)));
        root.appendChild(node);

        node =  doc.createElement("EmailGroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailGroupId)));
        root.appendChild(node);

        node =  doc.createElement("RuleGroup");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleGroup)));
        root.appendChild(node);

        node =  doc.createElement("WarningMessage");
        node.appendChild(doc.createTextNode(String.valueOf(mWarningMessage)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WorkflowRuleId field is not cloned.
    *
    * @return WorkflowRuleData object
    */
    public Object clone(){
        WorkflowRuleData myClone = new WorkflowRuleData();
        
        myClone.mRuleSeq = mRuleSeq;
        
        myClone.mWorkflowId = mWorkflowId;
        
        myClone.mRuleTypeCd = mRuleTypeCd;
        
        myClone.mRuleExp = mRuleExp;
        
        myClone.mRuleExpValue = mRuleExpValue;
        
        myClone.mRuleAction = mRuleAction;
        
        myClone.mNextActionCd = mNextActionCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mWorkflowRuleStatusCd = mWorkflowRuleStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mApproverGroupId = mApproverGroupId;
        
        myClone.mEmailGroupId = mEmailGroupId;
        
        myClone.mRuleGroup = mRuleGroup;
        
        myClone.mWarningMessage = mWarningMessage;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (WorkflowRuleDataAccess.WORKFLOW_RULE_ID.equals(pFieldName)) {
            return getWorkflowRuleId();
        } else if (WorkflowRuleDataAccess.RULE_SEQ.equals(pFieldName)) {
            return getRuleSeq();
        } else if (WorkflowRuleDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (WorkflowRuleDataAccess.RULE_TYPE_CD.equals(pFieldName)) {
            return getRuleTypeCd();
        } else if (WorkflowRuleDataAccess.RULE_EXP.equals(pFieldName)) {
            return getRuleExp();
        } else if (WorkflowRuleDataAccess.RULE_EXP_VALUE.equals(pFieldName)) {
            return getRuleExpValue();
        } else if (WorkflowRuleDataAccess.RULE_ACTION.equals(pFieldName)) {
            return getRuleAction();
        } else if (WorkflowRuleDataAccess.NEXT_ACTION_CD.equals(pFieldName)) {
            return getNextActionCd();
        } else if (WorkflowRuleDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkflowRuleDataAccess.WORKFLOW_RULE_STATUS_CD.equals(pFieldName)) {
            return getWorkflowRuleStatusCd();
        } else if (WorkflowRuleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkflowRuleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkflowRuleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkflowRuleDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WorkflowRuleDataAccess.APPROVER_GROUP_ID.equals(pFieldName)) {
            return getApproverGroupId();
        } else if (WorkflowRuleDataAccess.EMAIL_GROUP_ID.equals(pFieldName)) {
            return getEmailGroupId();
        } else if (WorkflowRuleDataAccess.RULE_GROUP.equals(pFieldName)) {
            return getRuleGroup();
        } else if (WorkflowRuleDataAccess.WARNING_MESSAGE.equals(pFieldName)) {
            return getWarningMessage();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return WorkflowRuleDataAccess.CLW_WORKFLOW_RULE;
    }

    
    /**
     * Sets the WorkflowRuleId field. This field is required to be set in the database.
     *
     * @param pWorkflowRuleId
     *  int to use to update the field.
     */
    public void setWorkflowRuleId(int pWorkflowRuleId){
        this.mWorkflowRuleId = pWorkflowRuleId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRuleId field.
     *
     * @return
     *  int containing the WorkflowRuleId field.
     */
    public int getWorkflowRuleId(){
        return mWorkflowRuleId;
    }

    /**
     * Sets the RuleSeq field.
     *
     * @param pRuleSeq
     *  int to use to update the field.
     */
    public void setRuleSeq(int pRuleSeq){
        this.mRuleSeq = pRuleSeq;
        setDirty(true);
    }
    /**
     * Retrieves the RuleSeq field.
     *
     * @return
     *  int containing the RuleSeq field.
     */
    public int getRuleSeq(){
        return mRuleSeq;
    }

    /**
     * Sets the WorkflowId field.
     *
     * @param pWorkflowId
     *  int to use to update the field.
     */
    public void setWorkflowId(int pWorkflowId){
        this.mWorkflowId = pWorkflowId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowId field.
     *
     * @return
     *  int containing the WorkflowId field.
     */
    public int getWorkflowId(){
        return mWorkflowId;
    }

    /**
     * Sets the RuleTypeCd field. This field is required to be set in the database.
     *
     * @param pRuleTypeCd
     *  String to use to update the field.
     */
    public void setRuleTypeCd(String pRuleTypeCd){
        this.mRuleTypeCd = pRuleTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the RuleTypeCd field.
     *
     * @return
     *  String containing the RuleTypeCd field.
     */
    public String getRuleTypeCd(){
        return mRuleTypeCd;
    }

    /**
     * Sets the RuleExp field. This field is required to be set in the database.
     *
     * @param pRuleExp
     *  String to use to update the field.
     */
    public void setRuleExp(String pRuleExp){
        this.mRuleExp = pRuleExp;
        setDirty(true);
    }
    /**
     * Retrieves the RuleExp field.
     *
     * @return
     *  String containing the RuleExp field.
     */
    public String getRuleExp(){
        return mRuleExp;
    }

    /**
     * Sets the RuleExpValue field. This field is required to be set in the database.
     *
     * @param pRuleExpValue
     *  String to use to update the field.
     */
    public void setRuleExpValue(String pRuleExpValue){
        this.mRuleExpValue = pRuleExpValue;
        setDirty(true);
    }
    /**
     * Retrieves the RuleExpValue field.
     *
     * @return
     *  String containing the RuleExpValue field.
     */
    public String getRuleExpValue(){
        return mRuleExpValue;
    }

    /**
     * Sets the RuleAction field.
     *
     * @param pRuleAction
     *  String to use to update the field.
     */
    public void setRuleAction(String pRuleAction){
        this.mRuleAction = pRuleAction;
        setDirty(true);
    }
    /**
     * Retrieves the RuleAction field.
     *
     * @return
     *  String containing the RuleAction field.
     */
    public String getRuleAction(){
        return mRuleAction;
    }

    /**
     * Sets the NextActionCd field. This field is required to be set in the database.
     *
     * @param pNextActionCd
     *  String to use to update the field.
     */
    public void setNextActionCd(String pNextActionCd){
        this.mNextActionCd = pNextActionCd;
        setDirty(true);
    }
    /**
     * Retrieves the NextActionCd field.
     *
     * @return
     *  String containing the NextActionCd field.
     */
    public String getNextActionCd(){
        return mNextActionCd;
    }

    /**
     * Sets the ShortDesc field. This field is required to be set in the database.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
    }

    /**
     * Sets the WorkflowRuleStatusCd field. This field is required to be set in the database.
     *
     * @param pWorkflowRuleStatusCd
     *  String to use to update the field.
     */
    public void setWorkflowRuleStatusCd(String pWorkflowRuleStatusCd){
        this.mWorkflowRuleStatusCd = pWorkflowRuleStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRuleStatusCd field.
     *
     * @return
     *  String containing the WorkflowRuleStatusCd field.
     */
    public String getWorkflowRuleStatusCd(){
        return mWorkflowRuleStatusCd;
    }

    /**
     * Sets the AddDate field. This field is required to be set in the database.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field. This field is required to be set in the database.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }

    /**
     * Sets the ApproverGroupId field.
     *
     * @param pApproverGroupId
     *  int to use to update the field.
     */
    public void setApproverGroupId(int pApproverGroupId){
        this.mApproverGroupId = pApproverGroupId;
        setDirty(true);
    }
    /**
     * Retrieves the ApproverGroupId field.
     *
     * @return
     *  int containing the ApproverGroupId field.
     */
    public int getApproverGroupId(){
        return mApproverGroupId;
    }

    /**
     * Sets the EmailGroupId field.
     *
     * @param pEmailGroupId
     *  int to use to update the field.
     */
    public void setEmailGroupId(int pEmailGroupId){
        this.mEmailGroupId = pEmailGroupId;
        setDirty(true);
    }
    /**
     * Retrieves the EmailGroupId field.
     *
     * @return
     *  int containing the EmailGroupId field.
     */
    public int getEmailGroupId(){
        return mEmailGroupId;
    }

    /**
     * Sets the RuleGroup field.
     *
     * @param pRuleGroup
     *  String to use to update the field.
     */
    public void setRuleGroup(String pRuleGroup){
        this.mRuleGroup = pRuleGroup;
        setDirty(true);
    }
    /**
     * Retrieves the RuleGroup field.
     *
     * @return
     *  String containing the RuleGroup field.
     */
    public String getRuleGroup(){
        return mRuleGroup;
    }

    /**
     * Sets the WarningMessage field.
     *
     * @param pWarningMessage
     *  String to use to update the field.
     */
    public void setWarningMessage(String pWarningMessage){
        this.mWarningMessage = pWarningMessage;
        setDirty(true);
    }
    /**
     * Retrieves the WarningMessage field.
     *
     * @return
     *  String containing the WarningMessage field.
     */
    public String getWarningMessage(){
        return mWarningMessage;
    }


}
