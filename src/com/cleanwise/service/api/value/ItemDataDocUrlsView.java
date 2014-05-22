
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemDataDocUrlsView
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
 * <code>ItemDataDocUrlsView</code> is a ViewObject class for UI.
 */
public class ItemDataDocUrlsView
extends ValueObject
{
   
    private static final long serialVersionUID = 1361937021746272838L;
    private int mItemId;
    private String mItemName;
    private String mDed;
    private String mMsds;
    private String mSpec;
    private String mMsdsViaJDWebService;
    private String mMsdsStorageTypeCd;
    private String mDedStorageTypeCd;
    private String mSpecStorageTypeCd;
    

    /**
     * Constructor.
     */
    public ItemDataDocUrlsView ()
    {
        mItemName = "";
        mDed = "";
        mMsds = "";
        mSpec = "";
        mMsdsViaJDWebService = "";
        mMsdsStorageTypeCd = "";
        mDedStorageTypeCd = "";
        mSpecStorageTypeCd = "";
    }

    /**
     * Constructor. 
     */
    public ItemDataDocUrlsView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9)
    {
        mItemId = parm1;
        mItemName = parm2;
        mDed = parm3;
        mMsds = parm4;
        mSpec = parm5;
        mMsdsViaJDWebService = parm6;
        mMsdsStorageTypeCd = parm7;
        mDedStorageTypeCd = parm8;
        mSpecStorageTypeCd = parm9;
        
    }

    /**
     * Creates a new ItemDataDocUrlsView
     *
     * @return
     *  Newly initialized ItemDataDocUrlsView object.
     */
    public static ItemDataDocUrlsView createValue () 
    {
        ItemDataDocUrlsView valueView = new ItemDataDocUrlsView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemDataDocUrlsView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", ItemName=" + mItemName + ", Ded=" + mDed + ", Msds=" + mMsds + ", Spec=" + mSpec + ", mMsdsViaJDWebService=" + mMsdsViaJDWebService + ", mMsdsStorageTypeCd=" + mMsdsStorageTypeCd + ", mDedStorageTypeCd=" + mDedStorageTypeCd + ", mSpecStorageTypeCd=" + mSpecStorageTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ItemDataDocUrls");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("ItemName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemName)));
        root.appendChild(node);

        node = doc.createElement("Ded");
        node.appendChild(doc.createTextNode(String.valueOf(mDed)));
        root.appendChild(node);

        node = doc.createElement("Msds");
        node.appendChild(doc.createTextNode(String.valueOf(mMsds)));
        root.appendChild(node);

        node = doc.createElement("Spec");
        node.appendChild(doc.createTextNode(String.valueOf(mSpec)));
        root.appendChild(node);
        
        node = doc.createElement("MsdsViaJDWebService");
        node.appendChild(doc.createTextNode(String.valueOf(mMsdsViaJDWebService)));
        root.appendChild(node);
        
        node = doc.createElement("mMsdsStorageTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mMsdsStorageTypeCd)));
        root.appendChild(node);
        
        node = doc.createElement("mDedStorageTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mDedStorageTypeCd)));
        root.appendChild(node);
        
        node = doc.createElement("mSpecStorageTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecStorageTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ItemDataDocUrlsView copy()  {
      ItemDataDocUrlsView obj = new ItemDataDocUrlsView();
      obj.setItemId(mItemId);
      obj.setItemName(mItemName);
      obj.setDed(mDed);
      obj.setMsds(mMsds);
      obj.setSpec(mSpec);
      obj.setMsdsViaJDWebService(mMsdsViaJDWebService);
      obj.setMsdsStorageTypeCd(mMsdsStorageTypeCd);
      obj.setDedStorageTypeCd(mDedStorageTypeCd);
      obj.setSpecStorageTypeCd(mSpecStorageTypeCd);
      
      return obj;
    }

    
    /**
     * Sets the ItemId property.
     *
     * @param pItemId
     *  int to use to update the property.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
    }
    /**
     * Retrieves the ItemId property.
     *
     * @return
     *  int containing the ItemId property.
     */
    public int getItemId(){
        return mItemId;
    }


    /**
     * Sets the ItemName property.
     *
     * @param pItemName
     *  String to use to update the property.
     */
    public void setItemName(String pItemName){
        this.mItemName = pItemName;
    }
    /**
     * Retrieves the ItemName property.
     *
     * @return
     *  String containing the ItemName property.
     */
    public String getItemName(){
        return mItemName;
    }


    /**
     * Sets the Ded property.
     *
     * @param pDed
     *  String to use to update the property.
     */
    public void setDed(String pDed){
        this.mDed = pDed;
    }
    /**
     * Retrieves the Ded property.
     *
     * @return
     *  String containing the Ded property.
     */
    public String getDed(){
        return mDed;
    }


    /**
     * Sets the Msds property.
     *
     * @param pMsds
     *  String to use to update the property.
     */
    public void setMsds(String pMsds){
        this.mMsds = pMsds;
    }
    /**
     * Retrieves the Msds property.
     *
     * @return
     *  String containing the Msds property.
     */
    public String getMsds(){
        return mMsds;
    }


    /**
     * Sets the Spec property.
     *
     * @param pSpec
     *  String to use to update the property.
     */
    public void setSpec(String pSpec){
        this.mSpec = pSpec;
    }
    /**
     * Retrieves the Spec property.
     *
     * @return
     *  String containing the Spec property.
     */
    public String getSpec(){
        return mSpec;
    }

    public void setMsdsViaJDWebService(String pMsdsViaJDWebService) {
    	this.mMsdsViaJDWebService = pMsdsViaJDWebService;   
    }    
    
    public String getMsdsViaJDWebService() {
    	return mMsdsViaJDWebService;   
    }
    
	// Methods for E3 Storage vs. Database (for images, docs)
	
	public String getMsdsStorageTypeCd() {
		return mMsdsStorageTypeCd;
	}
	
	public void setMsdsStorageTypeCd(String pMsdsStorageTypeCd) {
		this.mMsdsStorageTypeCd = pMsdsStorageTypeCd;
	}
	
	public String getDedStorageTypeCd() {
		return mDedStorageTypeCd;
	}
	
	public void setDedStorageTypeCd(String pDedStorageTypeCd) {
		this.mDedStorageTypeCd = pDedStorageTypeCd;
	}

	public String getSpecStorageTypeCd() {
		return mSpecStorageTypeCd;
	}
	
	public void setSpecStorageTypeCd(String pSpecStorageTypeCd) {
		this.mSpecStorageTypeCd = pSpecStorageTypeCd;
	}
}
