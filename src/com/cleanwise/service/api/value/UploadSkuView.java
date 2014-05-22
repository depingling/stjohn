
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UploadSkuView
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

import java.math.BigDecimal;


/**
 * <code>UploadSkuView</code> is a ViewObject class for UI.
 */
public class UploadSkuView
extends ValueObject
{
   
    private static final long serialVersionUID = -7257347606001257422L;
    private UploadSkuData mUploadSku;
    private String mSkuNum;
    private int mCategoryId;
    private boolean mSelectFlag;

    /**
     * Constructor.
     */
    public UploadSkuView ()
    {
        mSkuNum = "";
    }

    /**
     * Constructor. 
     */
    public UploadSkuView(UploadSkuData parm1, String parm2, int parm3, boolean parm4)
    {
        mUploadSku = parm1;
        mSkuNum = parm2;
        mCategoryId = parm3;
        mSelectFlag = parm4;
        
    }

    /**
     * Creates a new UploadSkuView
     *
     * @return
     *  Newly initialized UploadSkuView object.
     */
    public static UploadSkuView createValue () 
    {
        UploadSkuView valueView = new UploadSkuView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UploadSkuView object
     */
    public String toString()
    {
        return "[" + "UploadSku=" + mUploadSku + ", SkuNum=" + mSkuNum + ", CategoryId=" + mCategoryId + ", SelectFlag=" + mSelectFlag + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UploadSku");
	root.setAttribute("Id", String.valueOf(mUploadSku));

	Element node;

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("CategoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryId)));
        root.appendChild(node);

        node = doc.createElement("SelectFlag");
        node.appendChild(doc.createTextNode(String.valueOf(mSelectFlag)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UploadSkuView copy()  {
      UploadSkuView obj = new UploadSkuView();
      obj.setUploadSku(mUploadSku);
      obj.setSkuNum(mSkuNum);
      obj.setCategoryId(mCategoryId);
      obj.setSelectFlag(mSelectFlag);
      
      return obj;
    }

    
    /**
     * Sets the UploadSku property.
     *
     * @param pUploadSku
     *  UploadSkuData to use to update the property.
     */
    public void setUploadSku(UploadSkuData pUploadSku){
        this.mUploadSku = pUploadSku;
    }
    /**
     * Retrieves the UploadSku property.
     *
     * @return
     *  UploadSkuData containing the UploadSku property.
     */
    public UploadSkuData getUploadSku(){
        return mUploadSku;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  String to use to update the property.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  String containing the SkuNum property.
     */
    public String getSkuNum(){
        return mSkuNum;
    }


    /**
     * Sets the CategoryId property.
     *
     * @param pCategoryId
     *  int to use to update the property.
     */
    public void setCategoryId(int pCategoryId){
        this.mCategoryId = pCategoryId;
    }
    /**
     * Retrieves the CategoryId property.
     *
     * @return
     *  int containing the CategoryId property.
     */
    public int getCategoryId(){
        return mCategoryId;
    }


    /**
     * Sets the SelectFlag property.
     *
     * @param pSelectFlag
     *  boolean to use to update the property.
     */
    public void setSelectFlag(boolean pSelectFlag){
        this.mSelectFlag = pSelectFlag;
    }
    /**
     * Retrieves the SelectFlag property.
     *
     * @return
     *  boolean containing the SelectFlag property.
     */
    public boolean getSelectFlag(){
        return mSelectFlag;
    }


    
}
