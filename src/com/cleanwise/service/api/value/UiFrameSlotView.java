
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiFrameSlotView
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
 * <code>UiFrameSlotView</code> is a ViewObject class for UI.
 */
public class UiFrameSlotView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -3L
        ;
    private int mUiFrameSlotId;
    private int mUiFrameId;
    private String mSlotTypeCd;
    private String mValue;
    private String mUrl;
    private byte[] mImageData;
    private Date mAddDate;
    private String mAddBy;
    private Date mModDate;
    private String mModBy;
    private String mUrlTargetBlank;

    /**
     * Constructor.
     */
    public UiFrameSlotView ()
    {
        mSlotTypeCd = "";
        mValue = "";
        mUrl = "";
        mAddBy = "";
        mModBy = "";
        mUrlTargetBlank = "";
    }

    /**
     * Constructor. 
     */
    public UiFrameSlotView(int parm1, int parm2, String parm3, String parm4, String parm5, byte[] parm6, Date parm7, String parm8, Date parm9, String parm10, String parm11)
    {
        mUiFrameSlotId = parm1;
        mUiFrameId = parm2;
        mSlotTypeCd = parm3;
        mValue = parm4;
        mUrl = parm5;
        mImageData = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mUrlTargetBlank = parm11;
        
    }

    /**
     * Creates a new UiFrameSlotView
     *
     * @return
     *  Newly initialized UiFrameSlotView object.
     */
    public static UiFrameSlotView createValue () 
    {
        UiFrameSlotView valueView = new UiFrameSlotView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiFrameSlotView object
     */
    public String toString()
    {
        return "[" + "UiFrameSlotId=" + mUiFrameSlotId + ", UiFrameId=" + mUiFrameId + ", SlotTypeCd=" + mSlotTypeCd + ", Value=" + mValue + ", Url=" + mUrl + ", ImageData=" + mImageData + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", UrlTargetBlank=" + mUrlTargetBlank + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UiFrameSlot");
	root.setAttribute("Id", String.valueOf(mUiFrameSlotId));

	Element node;

        node = doc.createElement("UiFrameId");
        node.appendChild(doc.createTextNode(String.valueOf(mUiFrameId)));
        root.appendChild(node);

        node = doc.createElement("SlotTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSlotTypeCd)));
        root.appendChild(node);

        node = doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node = doc.createElement("Url");
        node.appendChild(doc.createTextNode(String.valueOf(mUrl)));
        root.appendChild(node);

        node = doc.createElement("ImageData");
        node.appendChild(doc.createTextNode(String.valueOf(mImageData)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("UrlTargetBlank");
        node.appendChild(doc.createTextNode(String.valueOf(mUrlTargetBlank)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UiFrameSlotView copy()  {
      UiFrameSlotView obj = new UiFrameSlotView();
      obj.setUiFrameSlotId(mUiFrameSlotId);
      obj.setUiFrameId(mUiFrameId);
      obj.setSlotTypeCd(mSlotTypeCd);
      obj.setValue(mValue);
      obj.setUrl(mUrl);
      obj.setImageData(mImageData);
      obj.setAddDate(mAddDate);
      obj.setAddBy(mAddBy);
      obj.setModDate(mModDate);
      obj.setModBy(mModBy);
      obj.setUrlTargetBlank(mUrlTargetBlank);
      
      return obj;
    }

    
    /**
     * Sets the UiFrameSlotId property.
     *
     * @param pUiFrameSlotId
     *  int to use to update the property.
     */
    public void setUiFrameSlotId(int pUiFrameSlotId){
        this.mUiFrameSlotId = pUiFrameSlotId;
    }
    /**
     * Retrieves the UiFrameSlotId property.
     *
     * @return
     *  int containing the UiFrameSlotId property.
     */
    public int getUiFrameSlotId(){
        return mUiFrameSlotId;
    }


    /**
     * Sets the UiFrameId property.
     *
     * @param pUiFrameId
     *  int to use to update the property.
     */
    public void setUiFrameId(int pUiFrameId){
        this.mUiFrameId = pUiFrameId;
    }
    /**
     * Retrieves the UiFrameId property.
     *
     * @return
     *  int containing the UiFrameId property.
     */
    public int getUiFrameId(){
        return mUiFrameId;
    }


    /**
     * Sets the SlotTypeCd property.
     *
     * @param pSlotTypeCd
     *  String to use to update the property.
     */
    public void setSlotTypeCd(String pSlotTypeCd){
        this.mSlotTypeCd = pSlotTypeCd;
    }
    /**
     * Retrieves the SlotTypeCd property.
     *
     * @return
     *  String containing the SlotTypeCd property.
     */
    public String getSlotTypeCd(){
        return mSlotTypeCd;
    }


    /**
     * Sets the Value property.
     *
     * @param pValue
     *  String to use to update the property.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
    }
    /**
     * Retrieves the Value property.
     *
     * @return
     *  String containing the Value property.
     */
    public String getValue(){
        return mValue;
    }


    /**
     * Sets the Url property.
     *
     * @param pUrl
     *  String to use to update the property.
     */
    public void setUrl(String pUrl){
        this.mUrl = pUrl;
    }
    /**
     * Retrieves the Url property.
     *
     * @return
     *  String containing the Url property.
     */
    public String getUrl(){
        return mUrl;
    }


    /**
     * Sets the ImageData property.
     *
     * @param pImageData
     *  byte[] to use to update the property.
     */
    public void setImageData(byte[] pImageData){
        this.mImageData = pImageData;
    }
    /**
     * Retrieves the ImageData property.
     *
     * @return
     *  byte[] containing the ImageData property.
     */
    public byte[] getImageData(){
        return mImageData;
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
     * Sets the UrlTargetBlank property.
     *
     * @param pUrlTargetBlank
     *  String to use to update the property.
     */
    public void setUrlTargetBlank(String pUrlTargetBlank){
        this.mUrlTargetBlank = pUrlTargetBlank;
    }
    /**
     * Retrieves the UrlTargetBlank property.
     *
     * @return
     *  String containing the UrlTargetBlank property.
     */
    public String getUrlTargetBlank(){
        return mUrlTargetBlank;
    }


    
}
