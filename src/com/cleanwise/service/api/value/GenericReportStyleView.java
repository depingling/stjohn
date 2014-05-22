
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportStyleView
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
 * <code>GenericReportStyleView</code> is a ViewObject class for UI.
 */
public class GenericReportStyleView
extends ValueObject
{
   
    private static final long serialVersionUID = -7701654083609843045L;
    private String mStyleName;
    private String mDataCategory;
    private String mDataFormat;
    private String mFontName;
    private int mFontSize;
    private String mFontType;
    private String mFontColor;
    private String mFillColor;
    private String mAlignment;
    private boolean mWrap;
    private int[] mMergeRegion;
    private String mDataClass;
    private String mWidth;
    private int mScale;

    /**
     * Constructor.
     */
    public GenericReportStyleView ()
    {
        mStyleName = "";
        mDataCategory = "";
        mDataFormat = "";
        mFontName = "";
        mFontType = "";
        mFontColor = "";
        mFillColor = "";
        mAlignment = "";
        mDataClass = "";
        mWidth = "";
    }

    /**
     * Constructor. 
     */
    public GenericReportStyleView(String parm1, String parm2, String parm3, String parm4, int parm5, String parm6, String parm7, String parm8, String parm9, boolean parm10, int[] parm11, String parm12, String parm13, int parm14)
    {
        mStyleName = parm1;
        mDataCategory = parm2;
        mDataFormat = parm3;
        mFontName = parm4;
        mFontSize = parm5;
        mFontType = parm6;
        mFontColor = parm7;
        mFillColor = parm8;
        mAlignment = parm9;
        mWrap = parm10;
        mMergeRegion = parm11;
        mDataClass = parm12;
        mWidth = parm13;
        mScale = parm14;
        
    }

    /**
     * Creates a new GenericReportStyleView
     *
     * @return
     *  Newly initialized GenericReportStyleView object.
     */
    public static GenericReportStyleView createValue () 
    {
        GenericReportStyleView valueView = new GenericReportStyleView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportStyleView object
     */
    public String toString()
    {
        return "[" + "StyleName=" + mStyleName + ", DataCategory=" + mDataCategory + ", DataFormat=" + mDataFormat + ", FontName=" + mFontName + ", FontSize=" + mFontSize + ", FontType=" + mFontType + ", FontColor=" + mFontColor + ", FillColor=" + mFillColor + ", Alignment=" + mAlignment + ", Wrap=" + mWrap + ", MergeRegion=" + mMergeRegion + ", DataClass=" + mDataClass + ", Width=" + mWidth + ", Scale=" + mScale + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GenericReportStyle");
	root.setAttribute("Id", String.valueOf(mStyleName));

	Element node;

        node = doc.createElement("DataCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mDataCategory)));
        root.appendChild(node);

        node = doc.createElement("DataFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mDataFormat)));
        root.appendChild(node);

        node = doc.createElement("FontName");
        node.appendChild(doc.createTextNode(String.valueOf(mFontName)));
        root.appendChild(node);

        node = doc.createElement("FontSize");
        node.appendChild(doc.createTextNode(String.valueOf(mFontSize)));
        root.appendChild(node);

        node = doc.createElement("FontType");
        node.appendChild(doc.createTextNode(String.valueOf(mFontType)));
        root.appendChild(node);

        node = doc.createElement("FontColor");
        node.appendChild(doc.createTextNode(String.valueOf(mFontColor)));
        root.appendChild(node);

        node = doc.createElement("FillColor");
        node.appendChild(doc.createTextNode(String.valueOf(mFillColor)));
        root.appendChild(node);

        node = doc.createElement("Alignment");
        node.appendChild(doc.createTextNode(String.valueOf(mAlignment)));
        root.appendChild(node);

        node = doc.createElement("Wrap");
        node.appendChild(doc.createTextNode(String.valueOf(mWrap)));
        root.appendChild(node);

        node = doc.createElement("MergeRegion");
        node.appendChild(doc.createTextNode(String.valueOf(mMergeRegion)));
        root.appendChild(node);

        node = doc.createElement("DataClass");
        node.appendChild(doc.createTextNode(String.valueOf(mDataClass)));
        root.appendChild(node);

        node = doc.createElement("Width");
        node.appendChild(doc.createTextNode(String.valueOf(mWidth)));
        root.appendChild(node);

        node = doc.createElement("Scale");
        node.appendChild(doc.createTextNode(String.valueOf(mScale)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GenericReportStyleView copy()  {
      GenericReportStyleView obj = new GenericReportStyleView();
      obj.setStyleName(mStyleName);
      obj.setDataCategory(mDataCategory);
      obj.setDataFormat(mDataFormat);
      obj.setFontName(mFontName);
      obj.setFontSize(mFontSize);
      obj.setFontType(mFontType);
      obj.setFontColor(mFontColor);
      obj.setFillColor(mFillColor);
      obj.setAlignment(mAlignment);
      obj.setWrap(mWrap);
      obj.setMergeRegion(mMergeRegion);
      obj.setDataClass(mDataClass);
      obj.setWidth(mWidth);
      obj.setScale(mScale);
      
      return obj;
    }

    
    /**
     * Sets the StyleName property.
     *
     * @param pStyleName
     *  String to use to update the property.
     */
    public void setStyleName(String pStyleName){
        this.mStyleName = pStyleName;
    }
    /**
     * Retrieves the StyleName property.
     *
     * @return
     *  String containing the StyleName property.
     */
    public String getStyleName(){
        return mStyleName;
    }


    /**
     * Sets the DataCategory property.
     *
     * @param pDataCategory
     *  String to use to update the property.
     */
    public void setDataCategory(String pDataCategory){
        this.mDataCategory = pDataCategory;
    }
    /**
     * Retrieves the DataCategory property.
     *
     * @return
     *  String containing the DataCategory property.
     */
    public String getDataCategory(){
        return mDataCategory;
    }


    /**
     * Sets the DataFormat property.
     *
     * @param pDataFormat
     *  String to use to update the property.
     */
    public void setDataFormat(String pDataFormat){
        this.mDataFormat = pDataFormat;
    }
    /**
     * Retrieves the DataFormat property.
     *
     * @return
     *  String containing the DataFormat property.
     */
    public String getDataFormat(){
        return mDataFormat;
    }


    /**
     * Sets the FontName property.
     *
     * @param pFontName
     *  String to use to update the property.
     */
    public void setFontName(String pFontName){
        this.mFontName = pFontName;
    }
    /**
     * Retrieves the FontName property.
     *
     * @return
     *  String containing the FontName property.
     */
    public String getFontName(){
        return mFontName;
    }


    /**
     * Sets the FontSize property.
     *
     * @param pFontSize
     *  int to use to update the property.
     */
    public void setFontSize(int pFontSize){
        this.mFontSize = pFontSize;
    }
    /**
     * Retrieves the FontSize property.
     *
     * @return
     *  int containing the FontSize property.
     */
    public int getFontSize(){
        return mFontSize;
    }


    /**
     * Sets the FontType property.
     *
     * @param pFontType
     *  String to use to update the property.
     */
    public void setFontType(String pFontType){
        this.mFontType = pFontType;
    }
    /**
     * Retrieves the FontType property.
     *
     * @return
     *  String containing the FontType property.
     */
    public String getFontType(){
        return mFontType;
    }


    /**
     * Sets the FontColor property.
     *
     * @param pFontColor
     *  String to use to update the property.
     */
    public void setFontColor(String pFontColor){
        this.mFontColor = pFontColor;
    }
    /**
     * Retrieves the FontColor property.
     *
     * @return
     *  String containing the FontColor property.
     */
    public String getFontColor(){
        return mFontColor;
    }


    /**
     * Sets the FillColor property.
     *
     * @param pFillColor
     *  String to use to update the property.
     */
    public void setFillColor(String pFillColor){
        this.mFillColor = pFillColor;
    }
    /**
     * Retrieves the FillColor property.
     *
     * @return
     *  String containing the FillColor property.
     */
    public String getFillColor(){
        return mFillColor;
    }


    /**
     * Sets the Alignment property.
     *
     * @param pAlignment
     *  String to use to update the property.
     */
    public void setAlignment(String pAlignment){
        this.mAlignment = pAlignment;
    }
    /**
     * Retrieves the Alignment property.
     *
     * @return
     *  String containing the Alignment property.
     */
    public String getAlignment(){
        return mAlignment;
    }


    /**
     * Sets the Wrap property.
     *
     * @param pWrap
     *  boolean to use to update the property.
     */
    public void setWrap(boolean pWrap){
        this.mWrap = pWrap;
    }
    /**
     * Retrieves the Wrap property.
     *
     * @return
     *  boolean containing the Wrap property.
     */
    public boolean getWrap(){
        return mWrap;
    }


    /**
     * Sets the MergeRegion property.
     *
     * @param pMergeRegion
     *  int[] to use to update the property.
     */
    public void setMergeRegion(int[] pMergeRegion){
        this.mMergeRegion = pMergeRegion;
    }
    /**
     * Retrieves the MergeRegion property.
     *
     * @return
     *  int[] containing the MergeRegion property.
     */
    public int[] getMergeRegion(){
        return mMergeRegion;
    }


    /**
     * Sets the DataClass property.
     *
     * @param pDataClass
     *  String to use to update the property.
     */
    public void setDataClass(String pDataClass){
        this.mDataClass = pDataClass;
    }
    /**
     * Retrieves the DataClass property.
     *
     * @return
     *  String containing the DataClass property.
     */
    public String getDataClass(){
        return mDataClass;
    }


    /**
     * Sets the Width property.
     *
     * @param pWidth
     *  String to use to update the property.
     */
    public void setWidth(String pWidth){
        this.mWidth = pWidth;
    }
    /**
     * Retrieves the Width property.
     *
     * @return
     *  String containing the Width property.
     */
    public String getWidth(){
        return mWidth;
    }


    /**
     * Sets the Scale property.
     *
     * @param pScale
     *  int to use to update the property.
     */
    public void setScale(int pScale){
        this.mScale = pScale;
    }
    /**
     * Retrieves the Scale property.
     *
     * @return
     *  int containing the Scale property.
     */
    public int getScale(){
        return mScale;
    }


    
}
