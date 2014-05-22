
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StoreMessageView
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
 * <code>StoreMessageView</code> is a ViewObject class for UI.
 */
public class StoreMessageView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mStoreMessageId;
    private int mStoreMessageDetailId;
    private String mShortDesc;
    private String mStoreMessageStatusCd;
    private String mMessageTitle;
    private String mMessageAbstract;
    private String mMessageBody;
    private String mMessageType;
    private Date mPostedDate;
    private Date mEndDate;
    private int mForcedReadCount;
    private int mDisplayOrder;
    private String mLanguageCd;
    private String mCountry;
    private String mMessageAuthor;
    private String mMessageDetailTypeCd;
    private String mAddBy;
    private Date mAddDate;
    private String mModBy;
    private Date mModDate;

    /**
     * Constructor.
     */
    public StoreMessageView ()
    {
        mShortDesc = "";
        mStoreMessageStatusCd = "";
        mMessageTitle = "";
        mMessageAbstract = "";
        mMessageBody = "";
        mMessageType = "";
        mLanguageCd = "";
        mCountry = "";
        mMessageAuthor = "";
        mMessageDetailTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor. 
     */
    public StoreMessageView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, Date parm9, Date parm10, int parm11, int parm12, String parm13, String parm14, String parm15, String parm16, String parm17, Date parm18, String parm19, Date parm20)
    {
        mStoreMessageId = parm1;
        mStoreMessageDetailId = parm2;
        mShortDesc = parm3;
        mStoreMessageStatusCd = parm4;
        mMessageTitle = parm5;
        mMessageAbstract = parm6;
        mMessageBody = parm7;
        mMessageType = parm8;
        mPostedDate = parm9;
        mEndDate = parm10;
        mForcedReadCount = parm11;
        mDisplayOrder = parm12;
        mLanguageCd = parm13;
        mCountry = parm14;
        mMessageAuthor = parm15;
        mMessageDetailTypeCd = parm16;
        mAddBy = parm17;
        mAddDate = parm18;
        mModBy = parm19;
        mModDate = parm20;
        
    }

    /**
     * Creates a new StoreMessageView
     *
     * @return
     *  Newly initialized StoreMessageView object.
     */
    public static StoreMessageView createValue () 
    {
        StoreMessageView valueView = new StoreMessageView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreMessageView object
     */
    public String toString()
    {
        return "[" + "StoreMessageId=" + mStoreMessageId + ", StoreMessageDetailId=" + mStoreMessageDetailId + ", ShortDesc=" + mShortDesc + ", StoreMessageStatusCd=" + mStoreMessageStatusCd + ", MessageTitle=" + mMessageTitle + ", MessageAbstract=" + mMessageAbstract + ", MessageBody=" + mMessageBody + ", MessageType=" + mMessageType + ", PostedDate=" + mPostedDate + ", EndDate=" + mEndDate + ", ForcedReadCount=" + mForcedReadCount + ", DisplayOrder=" + mDisplayOrder + ", LanguageCd=" + mLanguageCd + ", Country=" + mCountry + ", MessageAuthor=" + mMessageAuthor + ", MessageDetailTypeCd=" + mMessageDetailTypeCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("StoreMessage");
	root.setAttribute("Id", String.valueOf(mStoreMessageId));

	Element node;

        node = doc.createElement("StoreMessageDetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageDetailId)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("StoreMessageStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageStatusCd)));
        root.appendChild(node);

        node = doc.createElement("MessageTitle");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageTitle)));
        root.appendChild(node);

        node = doc.createElement("MessageAbstract");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageAbstract)));
        root.appendChild(node);

        node = doc.createElement("MessageBody");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageBody)));
        root.appendChild(node);

        node = doc.createElement("MessageType");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageType)));
        root.appendChild(node);

        node = doc.createElement("PostedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPostedDate)));
        root.appendChild(node);

        node = doc.createElement("EndDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEndDate)));
        root.appendChild(node);

        node = doc.createElement("ForcedReadCount");
        node.appendChild(doc.createTextNode(String.valueOf(mForcedReadCount)));
        root.appendChild(node);

        node = doc.createElement("DisplayOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mDisplayOrder)));
        root.appendChild(node);

        node = doc.createElement("LanguageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLanguageCd)));
        root.appendChild(node);

        node = doc.createElement("Country");
        node.appendChild(doc.createTextNode(String.valueOf(mCountry)));
        root.appendChild(node);

        node = doc.createElement("MessageAuthor");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageAuthor)));
        root.appendChild(node);

        node = doc.createElement("MessageDetailTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageDetailTypeCd)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public StoreMessageView copy()  {
      StoreMessageView obj = new StoreMessageView();
      obj.setStoreMessageId(mStoreMessageId);
      obj.setStoreMessageDetailId(mStoreMessageDetailId);
      obj.setShortDesc(mShortDesc);
      obj.setStoreMessageStatusCd(mStoreMessageStatusCd);
      obj.setMessageTitle(mMessageTitle);
      obj.setMessageAbstract(mMessageAbstract);
      obj.setMessageBody(mMessageBody);
      obj.setMessageType(mMessageType);
      obj.setPostedDate(mPostedDate);
      obj.setEndDate(mEndDate);
      obj.setForcedReadCount(mForcedReadCount);
      obj.setDisplayOrder(mDisplayOrder);
      obj.setLanguageCd(mLanguageCd);
      obj.setCountry(mCountry);
      obj.setMessageAuthor(mMessageAuthor);
      obj.setMessageDetailTypeCd(mMessageDetailTypeCd);
      obj.setAddBy(mAddBy);
      obj.setAddDate(mAddDate);
      obj.setModBy(mModBy);
      obj.setModDate(mModDate);
      
      return obj;
    }

    
    /**
     * Sets the StoreMessageId property.
     *
     * @param pStoreMessageId
     *  int to use to update the property.
     */
    public void setStoreMessageId(int pStoreMessageId){
        this.mStoreMessageId = pStoreMessageId;
    }
    /**
     * Retrieves the StoreMessageId property.
     *
     * @return
     *  int containing the StoreMessageId property.
     */
    public int getStoreMessageId(){
        return mStoreMessageId;
    }


    /**
     * Sets the StoreMessageDetailId property.
     *
     * @param pStoreMessageDetailId
     *  int to use to update the property.
     */
    public void setStoreMessageDetailId(int pStoreMessageDetailId){
        this.mStoreMessageDetailId = pStoreMessageDetailId;
    }
    /**
     * Retrieves the StoreMessageDetailId property.
     *
     * @return
     *  int containing the StoreMessageDetailId property.
     */
    public int getStoreMessageDetailId(){
        return mStoreMessageDetailId;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
    }


    /**
     * Sets the StoreMessageStatusCd property.
     *
     * @param pStoreMessageStatusCd
     *  String to use to update the property.
     */
    public void setStoreMessageStatusCd(String pStoreMessageStatusCd){
        this.mStoreMessageStatusCd = pStoreMessageStatusCd;
    }
    /**
     * Retrieves the StoreMessageStatusCd property.
     *
     * @return
     *  String containing the StoreMessageStatusCd property.
     */
    public String getStoreMessageStatusCd(){
        return mStoreMessageStatusCd;
    }


    /**
     * Sets the MessageTitle property.
     *
     * @param pMessageTitle
     *  String to use to update the property.
     */
    public void setMessageTitle(String pMessageTitle){
        this.mMessageTitle = pMessageTitle;
    }
    /**
     * Retrieves the MessageTitle property.
     *
     * @return
     *  String containing the MessageTitle property.
     */
    public String getMessageTitle(){
        return mMessageTitle;
    }


    /**
     * Sets the MessageAbstract property.
     *
     * @param pMessageAbstract
     *  String to use to update the property.
     */
    public void setMessageAbstract(String pMessageAbstract){
        this.mMessageAbstract = pMessageAbstract;
    }
    /**
     * Retrieves the MessageAbstract property.
     *
     * @return
     *  String containing the MessageAbstract property.
     */
    public String getMessageAbstract(){
        return mMessageAbstract;
    }


    /**
     * Sets the MessageBody property.
     *
     * @param pMessageBody
     *  String to use to update the property.
     */
    public void setMessageBody(String pMessageBody){
        this.mMessageBody = pMessageBody;
    }
    /**
     * Retrieves the MessageBody property.
     *
     * @return
     *  String containing the MessageBody property.
     */
    public String getMessageBody(){
        return mMessageBody;
    }


    /**
     * Sets the MessageType property.
     *
     * @param pMessageType
     *  String to use to update the property.
     */
    public void setMessageType(String pMessageType){
        this.mMessageType = pMessageType;
    }
    /**
     * Retrieves the MessageType property.
     *
     * @return
     *  String containing the MessageType property.
     */
    public String getMessageType(){
        return mMessageType;
    }


    /**
     * Sets the PostedDate property.
     *
     * @param pPostedDate
     *  Date to use to update the property.
     */
    public void setPostedDate(Date pPostedDate){
        this.mPostedDate = pPostedDate;
    }
    /**
     * Retrieves the PostedDate property.
     *
     * @return
     *  Date containing the PostedDate property.
     */
    public Date getPostedDate(){
        return mPostedDate;
    }


    /**
     * Sets the EndDate property.
     *
     * @param pEndDate
     *  Date to use to update the property.
     */
    public void setEndDate(Date pEndDate){
        this.mEndDate = pEndDate;
    }
    /**
     * Retrieves the EndDate property.
     *
     * @return
     *  Date containing the EndDate property.
     */
    public Date getEndDate(){
        return mEndDate;
    }


    /**
     * Sets the ForcedReadCount property.
     *
     * @param pForcedReadCount
     *  int to use to update the property.
     */
    public void setForcedReadCount(int pForcedReadCount){
        this.mForcedReadCount = pForcedReadCount;
    }
    /**
     * Retrieves the ForcedReadCount property.
     *
     * @return
     *  int containing the ForcedReadCount property.
     */
    public int getForcedReadCount(){
        return mForcedReadCount;
    }


    /**
     * Sets the DisplayOrder property.
     *
     * @param pDisplayOrder
     *  int to use to update the property.
     */
    public void setDisplayOrder(int pDisplayOrder){
        this.mDisplayOrder = pDisplayOrder;
    }
    /**
     * Retrieves the DisplayOrder property.
     *
     * @return
     *  int containing the DisplayOrder property.
     */
    public int getDisplayOrder(){
        return mDisplayOrder;
    }


    /**
     * Sets the LanguageCd property.
     *
     * @param pLanguageCd
     *  String to use to update the property.
     */
    public void setLanguageCd(String pLanguageCd){
        this.mLanguageCd = pLanguageCd;
    }
    /**
     * Retrieves the LanguageCd property.
     *
     * @return
     *  String containing the LanguageCd property.
     */
    public String getLanguageCd(){
        return mLanguageCd;
    }


    /**
     * Sets the Country property.
     *
     * @param pCountry
     *  String to use to update the property.
     */
    public void setCountry(String pCountry){
        this.mCountry = pCountry;
    }
    /**
     * Retrieves the Country property.
     *
     * @return
     *  String containing the Country property.
     */
    public String getCountry(){
        return mCountry;
    }


    /**
     * Sets the MessageAuthor property.
     *
     * @param pMessageAuthor
     *  String to use to update the property.
     */
    public void setMessageAuthor(String pMessageAuthor){
        this.mMessageAuthor = pMessageAuthor;
    }
    /**
     * Retrieves the MessageAuthor property.
     *
     * @return
     *  String containing the MessageAuthor property.
     */
    public String getMessageAuthor(){
        return mMessageAuthor;
    }


    /**
     * Sets the MessageDetailTypeCd property.
     *
     * @param pMessageDetailTypeCd
     *  String to use to update the property.
     */
    public void setMessageDetailTypeCd(String pMessageDetailTypeCd){
        this.mMessageDetailTypeCd = pMessageDetailTypeCd;
    }
    /**
     * Retrieves the MessageDetailTypeCd property.
     *
     * @return
     *  String containing the MessageDetailTypeCd property.
     */
    public String getMessageDetailTypeCd(){
        return mMessageDetailTypeCd;
    }


    /**
     * Sets the AddBy property.
     *
     * @param pAddBy
     *  String to use to update the property.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
    }
    /**
     * Retrieves the AddBy property.
     *
     * @return
     *  String containing the AddBy property.
     */
    public String getAddBy(){
        return mAddBy;
    }


    /**
     * Sets the AddDate property.
     *
     * @param pAddDate
     *  Date to use to update the property.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
    }
    /**
     * Retrieves the AddDate property.
     *
     * @return
     *  Date containing the AddDate property.
     */
    public Date getAddDate(){
        return mAddDate;
    }


    /**
     * Sets the ModBy property.
     *
     * @param pModBy
     *  String to use to update the property.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
    }
    /**
     * Retrieves the ModBy property.
     *
     * @return
     *  String containing the ModBy property.
     */
    public String getModBy(){
        return mModBy;
    }


    /**
     * Sets the ModDate property.
     *
     * @param pModDate
     *  Date to use to update the property.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
    }
    /**
     * Retrieves the ModDate property.
     *
     * @return
     *  Date containing the ModDate property.
     */
    public Date getModDate(){
        return mModDate;
    }


    
}
