
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EmailView
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
 * <code>EmailView</code> is a ViewObject class for UI.
 */
public class EmailView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mToAddress;
    private String mCcAddress;
    private String mFromAddress;
    private String mSubject;
    private String mText;
    private byte[] mLongText;
    private String mImportance;
    private byte[] mAttachments;

    /**
     * Constructor.
     */
    public EmailView ()
    {
        mToAddress = "";
        mCcAddress = "";
        mFromAddress = "";
        mSubject = "";
        mText = "";
        mImportance = "";
    }

    /**
     * Constructor. 
     */
    public EmailView(String parm1, String parm2, String parm3, String parm4, String parm5, byte[] parm6, String parm7, byte[] parm8)
    {
        mToAddress = parm1;
        mCcAddress = parm2;
        mFromAddress = parm3;
        mSubject = parm4;
        mText = parm5;
        mLongText = parm6;
        mImportance = parm7;
        mAttachments = parm8;
        
    }

    /**
     * Creates a new EmailView
     *
     * @return
     *  Newly initialized EmailView object.
     */
    public static EmailView createValue () 
    {
        EmailView valueView = new EmailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EmailView object
     */
    public String toString()
    {
        return "[" + "ToAddress=" + mToAddress + ", CcAddress=" + mCcAddress + ", FromAddress=" + mFromAddress + ", Subject=" + mSubject + ", Text=" + mText + ", LongText=" + mLongText + ", Importance=" + mImportance + ", Attachments=" + mAttachments + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Email");
	root.setAttribute("Id", String.valueOf(mToAddress));

	Element node;

        node = doc.createElement("CcAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mCcAddress)));
        root.appendChild(node);

        node = doc.createElement("FromAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mFromAddress)));
        root.appendChild(node);

        node = doc.createElement("Subject");
        node.appendChild(doc.createTextNode(String.valueOf(mSubject)));
        root.appendChild(node);

        node = doc.createElement("Text");
        node.appendChild(doc.createTextNode(String.valueOf(mText)));
        root.appendChild(node);

        node = doc.createElement("LongText");
        node.appendChild(doc.createTextNode(String.valueOf(mLongText)));
        root.appendChild(node);

        node = doc.createElement("Importance");
        node.appendChild(doc.createTextNode(String.valueOf(mImportance)));
        root.appendChild(node);

        node = doc.createElement("Attachments");
        node.appendChild(doc.createTextNode(String.valueOf(mAttachments)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EmailView copy()  {
      EmailView obj = new EmailView();
      obj.setToAddress(mToAddress);
      obj.setCcAddress(mCcAddress);
      obj.setFromAddress(mFromAddress);
      obj.setSubject(mSubject);
      obj.setText(mText);
      obj.setLongText(mLongText);
      obj.setImportance(mImportance);
      obj.setAttachments(mAttachments);
      
      return obj;
    }

    
    /**
     * Sets the ToAddress property.
     *
     * @param pToAddress
     *  String to use to update the property.
     */
    public void setToAddress(String pToAddress){
        this.mToAddress = pToAddress;
    }
    /**
     * Retrieves the ToAddress property.
     *
     * @return
     *  String containing the ToAddress property.
     */
    public String getToAddress(){
        return mToAddress;
    }


    /**
     * Sets the CcAddress property.
     *
     * @param pCcAddress
     *  String to use to update the property.
     */
    public void setCcAddress(String pCcAddress){
        this.mCcAddress = pCcAddress;
    }
    /**
     * Retrieves the CcAddress property.
     *
     * @return
     *  String containing the CcAddress property.
     */
    public String getCcAddress(){
        return mCcAddress;
    }


    /**
     * Sets the FromAddress property.
     *
     * @param pFromAddress
     *  String to use to update the property.
     */
    public void setFromAddress(String pFromAddress){
        this.mFromAddress = pFromAddress;
    }
    /**
     * Retrieves the FromAddress property.
     *
     * @return
     *  String containing the FromAddress property.
     */
    public String getFromAddress(){
        return mFromAddress;
    }


    /**
     * Sets the Subject property.
     *
     * @param pSubject
     *  String to use to update the property.
     */
    public void setSubject(String pSubject){
        this.mSubject = pSubject;
    }
    /**
     * Retrieves the Subject property.
     *
     * @return
     *  String containing the Subject property.
     */
    public String getSubject(){
        return mSubject;
    }


    /**
     * Sets the Text property.
     *
     * @param pText
     *  String to use to update the property.
     */
    public void setText(String pText){
        this.mText = pText;
    }
    /**
     * Retrieves the Text property.
     *
     * @return
     *  String containing the Text property.
     */
    public String getText(){
        return mText;
    }


    /**
     * Sets the LongText property.
     *
     * @param pLongText
     *  byte[] to use to update the property.
     */
    public void setLongText(byte[] pLongText){
        this.mLongText = pLongText;
    }
    /**
     * Retrieves the LongText property.
     *
     * @return
     *  byte[] containing the LongText property.
     */
    public byte[] getLongText(){
        return mLongText;
    }


    /**
     * Sets the Importance property.
     *
     * @param pImportance
     *  String to use to update the property.
     */
    public void setImportance(String pImportance){
        this.mImportance = pImportance;
    }
    /**
     * Retrieves the Importance property.
     *
     * @return
     *  String containing the Importance property.
     */
    public String getImportance(){
        return mImportance;
    }


    /**
     * Sets the Attachments property.
     *
     * @param pAttachments
     *  byte[] to use to update the property.
     */
    public void setAttachments(byte[] pAttachments){
        this.mAttachments = pAttachments;
    }
    /**
     * Retrieves the Attachments property.
     *
     * @return
     *  byte[] containing the Attachments property.
     */
    public byte[] getAttachments(){
        return mAttachments;
    }


    
}
