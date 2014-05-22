
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkflowAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkflowAssocData</code> is a ValueObject class wrapping of the database table CLW_WORKFLOW_ASSOC.
 */
public class WorkflowAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2346090719632280628L;
    private int mWorkflowAssocId;// SQL type:NUMBER, not null
    private int mWorkflowId;// SQL type:NUMBER, not null
    private int mWorkflowRuleId;// SQL type:NUMBER
    private String mWorkflowAssocCd;// SQL type:VARCHAR2, not null
    private int mGroupId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkflowAssocData ()
    {
        mWorkflowAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkflowAssocData(int parm1, int parm2, int parm3, String parm4, int parm5, int parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mWorkflowAssocId = parm1;
        mWorkflowId = parm2;
        mWorkflowRuleId = parm3;
        mWorkflowAssocCd = parm4;
        mGroupId = parm5;
        mUserId = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new WorkflowAssocData
     *
     * @return
     *  Newly initialized WorkflowAssocData object.
     */
    public static WorkflowAssocData createValue ()
    {
        WorkflowAssocData valueData = new WorkflowAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowAssocData object
     */
    public String toString()
    {
        return "[" + "WorkflowAssocId=" + mWorkflowAssocId + ", WorkflowId=" + mWorkflowId + ", WorkflowRuleId=" + mWorkflowRuleId + ", WorkflowAssocCd=" + mWorkflowAssocCd + ", GroupId=" + mGroupId + ", UserId=" + mUserId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkflowAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkflowAssocId));

        node =  doc.createElement("WorkflowId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowId)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRuleId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRuleId)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("GroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
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

        return root;
    }

    /**
    * creates a clone of this object, the WorkflowAssocId field is not cloned.
    *
    * @return WorkflowAssocData object
    */
    public Object clone(){
        WorkflowAssocData myClone = new WorkflowAssocData();
        
        myClone.mWorkflowId = mWorkflowId;
        
        myClone.mWorkflowRuleId = mWorkflowRuleId;
        
        myClone.mWorkflowAssocCd = mWorkflowAssocCd;
        
        myClone.mGroupId = mGroupId;
        
        myClone.mUserId = mUserId;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (WorkflowAssocDataAccess.WORKFLOW_ASSOC_ID.equals(pFieldName)) {
            return getWorkflowAssocId();
        } else if (WorkflowAssocDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (WorkflowAssocDataAccess.WORKFLOW_RULE_ID.equals(pFieldName)) {
            return getWorkflowRuleId();
        } else if (WorkflowAssocDataAccess.WORKFLOW_ASSOC_CD.equals(pFieldName)) {
            return getWorkflowAssocCd();
        } else if (WorkflowAssocDataAccess.GROUP_ID.equals(pFieldName)) {
            return getGroupId();
        } else if (WorkflowAssocDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (WorkflowAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkflowAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkflowAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkflowAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
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
        return WorkflowAssocDataAccess.CLW_WORKFLOW_ASSOC;
    }

    
    /**
     * Sets the WorkflowAssocId field. This field is required to be set in the database.
     *
     * @param pWorkflowAssocId
     *  int to use to update the field.
     */
    public void setWorkflowAssocId(int pWorkflowAssocId){
        this.mWorkflowAssocId = pWorkflowAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowAssocId field.
     *
     * @return
     *  int containing the WorkflowAssocId field.
     */
    public int getWorkflowAssocId(){
        return mWorkflowAssocId;
    }

    /**
     * Sets the WorkflowId field. This field is required to be set in the database.
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
     * Sets the WorkflowRuleId field.
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
     * Sets the WorkflowAssocCd field. This field is required to be set in the database.
     *
     * @param pWorkflowAssocCd
     *  String to use to update the field.
     */
    public void setWorkflowAssocCd(String pWorkflowAssocCd){
        this.mWorkflowAssocCd = pWorkflowAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowAssocCd field.
     *
     * @return
     *  String containing the WorkflowAssocCd field.
     */
    public String getWorkflowAssocCd(){
        return mWorkflowAssocCd;
    }

    /**
     * Sets the GroupId field.
     *
     * @param pGroupId
     *  int to use to update the field.
     */
    public void setGroupId(int pGroupId){
        this.mGroupId = pGroupId;
        setDirty(true);
    }
    /**
     * Retrieves the GroupId field.
     *
     * @return
     *  int containing the GroupId field.
     */
    public int getGroupId(){
        return mGroupId;
    }

    /**
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
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


}
