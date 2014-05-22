
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserData
 * Description:  This is a ValueObject class wrapping the database table CLW_USER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UserDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UserData</code> is a ValueObject class wrapping of the database table CLW_USER.
 */
public class UserData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 770654028782702017L;
    private int mUserId;// SQL type:NUMBER, not null
    private String mFirstName;// SQL type:VARCHAR2
    private String mLastName;// SQL type:VARCHAR2
    private String mUserName;// SQL type:VARCHAR2
    private String mPassword;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mUserStatusCd;// SQL type:VARCHAR2, not null
    private String mUserRoleCd;// SQL type:VARCHAR2, not null
    private String mUserTypeCd;// SQL type:VARCHAR2, not null
    private Date mRegisterDate;// SQL type:DATE
    private Date mLastActivityDate;// SQL type:DATE
    private String mPrefLocaleCd;// SQL type:VARCHAR2, not null
    private String mWorkflowRoleCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public UserData ()
    {
        mFirstName = "";
        mLastName = "";
        mUserName = "";
        mPassword = "";
        mUserStatusCd = "";
        mUserRoleCd = "";
        mUserTypeCd = "";
        mPrefLocaleCd = "";
        mWorkflowRoleCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UserData(int parm1, String parm2, String parm3, String parm4, String parm5, Date parm6, Date parm7, String parm8, String parm9, String parm10, Date parm11, Date parm12, String parm13, String parm14, Date parm15, String parm16, Date parm17, String parm18)
    {
        mUserId = parm1;
        mFirstName = parm2;
        mLastName = parm3;
        mUserName = parm4;
        mPassword = parm5;
        mEffDate = parm6;
        mExpDate = parm7;
        mUserStatusCd = parm8;
        mUserRoleCd = parm9;
        mUserTypeCd = parm10;
        mRegisterDate = parm11;
        mLastActivityDate = parm12;
        mPrefLocaleCd = parm13;
        mWorkflowRoleCd = parm14;
        mAddDate = parm15;
        mAddBy = parm16;
        mModDate = parm17;
        mModBy = parm18;
        
    }

    /**
     * Creates a new UserData
     *
     * @return
     *  Newly initialized UserData object.
     */
    public static UserData createValue ()
    {
        UserData valueData = new UserData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserData object
     */
    public String toString()
    {
        return "[" + "UserId=" + mUserId + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", UserName=" + mUserName + ", Password=" + mPassword + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", UserStatusCd=" + mUserStatusCd + ", UserRoleCd=" + mUserRoleCd + ", UserTypeCd=" + mUserTypeCd + ", RegisterDate=" + mRegisterDate + ", LastActivityDate=" + mLastActivityDate + ", PrefLocaleCd=" + mPrefLocaleCd + ", WorkflowRoleCd=" + mWorkflowRoleCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("User");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUserId));

        node =  doc.createElement("FirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mFirstName)));
        root.appendChild(node);

        node =  doc.createElement("LastName");
        node.appendChild(doc.createTextNode(String.valueOf(mLastName)));
        root.appendChild(node);

        node =  doc.createElement("UserName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserName)));
        root.appendChild(node);

        node =  doc.createElement("Password");
        node.appendChild(doc.createTextNode(String.valueOf(mPassword)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("UserStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("UserRoleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserRoleCd)));
        root.appendChild(node);

        node =  doc.createElement("UserTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("RegisterDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRegisterDate)));
        root.appendChild(node);

        node =  doc.createElement("LastActivityDate");
        node.appendChild(doc.createTextNode(String.valueOf(mLastActivityDate)));
        root.appendChild(node);

        node =  doc.createElement("PrefLocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPrefLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRoleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRoleCd)));
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
    * creates a clone of this object, the UserId field is not cloned.
    *
    * @return UserData object
    */
    public Object clone(){
        UserData myClone = new UserData();
        
        myClone.mFirstName = mFirstName;
        
        myClone.mLastName = mLastName;
        
        myClone.mUserName = mUserName;
        
        myClone.mPassword = mPassword;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mUserStatusCd = mUserStatusCd;
        
        myClone.mUserRoleCd = mUserRoleCd;
        
        myClone.mUserTypeCd = mUserTypeCd;
        
        if(mRegisterDate != null){
                myClone.mRegisterDate = (Date) mRegisterDate.clone();
        }
        
        if(mLastActivityDate != null){
                myClone.mLastActivityDate = (Date) mLastActivityDate.clone();
        }
        
        myClone.mPrefLocaleCd = mPrefLocaleCd;
        
        myClone.mWorkflowRoleCd = mWorkflowRoleCd;
        
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

        if (UserDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (UserDataAccess.FIRST_NAME.equals(pFieldName)) {
            return getFirstName();
        } else if (UserDataAccess.LAST_NAME.equals(pFieldName)) {
            return getLastName();
        } else if (UserDataAccess.USER_NAME.equals(pFieldName)) {
            return getUserName();
        } else if (UserDataAccess.PASSWORD.equals(pFieldName)) {
            return getPassword();
        } else if (UserDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (UserDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (UserDataAccess.USER_STATUS_CD.equals(pFieldName)) {
            return getUserStatusCd();
        } else if (UserDataAccess.USER_ROLE_CD.equals(pFieldName)) {
            return getUserRoleCd();
        } else if (UserDataAccess.USER_TYPE_CD.equals(pFieldName)) {
            return getUserTypeCd();
        } else if (UserDataAccess.REGISTER_DATE.equals(pFieldName)) {
            return getRegisterDate();
        } else if (UserDataAccess.LAST_ACTIVITY_DATE.equals(pFieldName)) {
            return getLastActivityDate();
        } else if (UserDataAccess.PREF_LOCALE_CD.equals(pFieldName)) {
            return getPrefLocaleCd();
        } else if (UserDataAccess.WORKFLOW_ROLE_CD.equals(pFieldName)) {
            return getWorkflowRoleCd();
        } else if (UserDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UserDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UserDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UserDataAccess.MOD_BY.equals(pFieldName)) {
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
        return UserDataAccess.CLW_USER;
    }

    
    /**
     * Sets the UserId field. This field is required to be set in the database.
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
     * Sets the FirstName field.
     *
     * @param pFirstName
     *  String to use to update the field.
     */
    public void setFirstName(String pFirstName){
        this.mFirstName = pFirstName;
        setDirty(true);
    }
    /**
     * Retrieves the FirstName field.
     *
     * @return
     *  String containing the FirstName field.
     */
    public String getFirstName(){
        return mFirstName;
    }

    /**
     * Sets the LastName field.
     *
     * @param pLastName
     *  String to use to update the field.
     */
    public void setLastName(String pLastName){
        this.mLastName = pLastName;
        setDirty(true);
    }
    /**
     * Retrieves the LastName field.
     *
     * @return
     *  String containing the LastName field.
     */
    public String getLastName(){
        return mLastName;
    }

    /**
     * Sets the UserName field.
     *
     * @param pUserName
     *  String to use to update the field.
     */
    public void setUserName(String pUserName){
        this.mUserName = pUserName;
        setDirty(true);
    }
    /**
     * Retrieves the UserName field.
     *
     * @return
     *  String containing the UserName field.
     */
    public String getUserName(){
        return mUserName;
    }

    /**
     * Sets the Password field.
     *
     * @param pPassword
     *  String to use to update the field.
     */
    public void setPassword(String pPassword){
        this.mPassword = pPassword;
        setDirty(true);
    }
    /**
     * Retrieves the Password field.
     *
     * @return
     *  String containing the Password field.
     */
    public String getPassword(){
        return mPassword;
    }

    /**
     * Sets the EffDate field.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the UserStatusCd field. This field is required to be set in the database.
     *
     * @param pUserStatusCd
     *  String to use to update the field.
     */
    public void setUserStatusCd(String pUserStatusCd){
        this.mUserStatusCd = pUserStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the UserStatusCd field.
     *
     * @return
     *  String containing the UserStatusCd field.
     */
    public String getUserStatusCd(){
        return mUserStatusCd;
    }

    /**
     * Sets the UserRoleCd field. This field is required to be set in the database.
     *
     * @param pUserRoleCd
     *  String to use to update the field.
     */
    public void setUserRoleCd(String pUserRoleCd){
        this.mUserRoleCd = pUserRoleCd;
        setDirty(true);
    }
    /**
     * Retrieves the UserRoleCd field.
     *
     * @return
     *  String containing the UserRoleCd field.
     */
    public String getUserRoleCd(){
        return mUserRoleCd;
    }

    /**
     * Sets the UserTypeCd field. This field is required to be set in the database.
     *
     * @param pUserTypeCd
     *  String to use to update the field.
     */
    public void setUserTypeCd(String pUserTypeCd){
        this.mUserTypeCd = pUserTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the UserTypeCd field.
     *
     * @return
     *  String containing the UserTypeCd field.
     */
    public String getUserTypeCd(){
        return mUserTypeCd;
    }

    /**
     * Sets the RegisterDate field.
     *
     * @param pRegisterDate
     *  Date to use to update the field.
     */
    public void setRegisterDate(Date pRegisterDate){
        this.mRegisterDate = pRegisterDate;
        setDirty(true);
    }
    /**
     * Retrieves the RegisterDate field.
     *
     * @return
     *  Date containing the RegisterDate field.
     */
    public Date getRegisterDate(){
        return mRegisterDate;
    }

    /**
     * Sets the LastActivityDate field.
     *
     * @param pLastActivityDate
     *  Date to use to update the field.
     */
    public void setLastActivityDate(Date pLastActivityDate){
        this.mLastActivityDate = pLastActivityDate;
        setDirty(true);
    }
    /**
     * Retrieves the LastActivityDate field.
     *
     * @return
     *  Date containing the LastActivityDate field.
     */
    public Date getLastActivityDate(){
        return mLastActivityDate;
    }

    /**
     * Sets the PrefLocaleCd field. This field is required to be set in the database.
     *
     * @param pPrefLocaleCd
     *  String to use to update the field.
     */
    public void setPrefLocaleCd(String pPrefLocaleCd){
        this.mPrefLocaleCd = pPrefLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the PrefLocaleCd field.
     *
     * @return
     *  String containing the PrefLocaleCd field.
     */
    public String getPrefLocaleCd(){
        return mPrefLocaleCd;
    }

    /**
     * Sets the WorkflowRoleCd field. This field is required to be set in the database.
     *
     * @param pWorkflowRoleCd
     *  String to use to update the field.
     */
    public void setWorkflowRoleCd(String pWorkflowRoleCd){
        this.mWorkflowRoleCd = pWorkflowRoleCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRoleCd field.
     *
     * @return
     *  String containing the WorkflowRoleCd field.
     */
    public String getWorkflowRoleCd(){
        return mWorkflowRoleCd;
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
