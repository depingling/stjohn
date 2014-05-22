
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportColumnView
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
 * <code>GenericReportColumnView</code> is a ViewObject class for UI.
 */
public class GenericReportColumnView
extends ValueObject
{
   
    private static final long serialVersionUID = -7701654083609843045L;
    private String mColumnClass;
    private String mColumnName;
    private String mColumnType;
    private int mColumnPrecision;
    private int mColumnScale;
    private String mColumnWidth;
    private boolean mTotalRequestFlag;
    private String mColumnFormat;
    private String mColumnHeaderStyleName;
    private String mColumnDataStyleName;

    /**
     * Constructor.
     */
    public GenericReportColumnView ()
    {
        mColumnClass = "";
        mColumnName = "";
        mColumnType = "";
        mColumnWidth = "";
        mColumnFormat = "";
        mColumnHeaderStyleName = "";
        mColumnDataStyleName = "";
    }

    /**
     * Constructor. 
     */
    public GenericReportColumnView(String parm1, String parm2, String parm3, int parm4, int parm5, String parm6, boolean parm7, String parm8, String parm9, String parm10)
    {
        mColumnClass = parm1;
        mColumnName = parm2;
        mColumnType = parm3;
        mColumnPrecision = parm4;
        mColumnScale = parm5;
        mColumnWidth = parm6;
        mTotalRequestFlag = parm7;
        mColumnFormat = parm8;
        mColumnHeaderStyleName = parm9;
        mColumnDataStyleName = parm10;
        
    }

    /**
     * Creates a new GenericReportColumnView
     *
     * @return
     *  Newly initialized GenericReportColumnView object.
     */
    public static GenericReportColumnView createValue () 
    {
        GenericReportColumnView valueView = new GenericReportColumnView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportColumnView object
     */
    public String toString()
    {
        return "[" + "ColumnClass=" + mColumnClass + ", ColumnName=" + mColumnName + ", ColumnType=" + mColumnType + ", ColumnPrecision=" + mColumnPrecision + ", ColumnScale=" + mColumnScale + ", ColumnWidth=" + mColumnWidth + ", TotalRequestFlag=" + mTotalRequestFlag + ", ColumnFormat=" + mColumnFormat + ", ColumnHeaderStyleName=" + mColumnHeaderStyleName + ", ColumnDataStyleName=" + mColumnDataStyleName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GenericReportColumn");
	root.setAttribute("Id", String.valueOf(mColumnClass));

	Element node;

        node = doc.createElement("ColumnName");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnName)));
        root.appendChild(node);

        node = doc.createElement("ColumnType");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnType)));
        root.appendChild(node);

        node = doc.createElement("ColumnPrecision");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnPrecision)));
        root.appendChild(node);

        node = doc.createElement("ColumnScale");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnScale)));
        root.appendChild(node);

        node = doc.createElement("ColumnWidth");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnWidth)));
        root.appendChild(node);

        node = doc.createElement("TotalRequestFlag");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalRequestFlag)));
        root.appendChild(node);

        node = doc.createElement("ColumnFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnFormat)));
        root.appendChild(node);

        node = doc.createElement("ColumnHeaderStyleName");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnHeaderStyleName)));
        root.appendChild(node);

        node = doc.createElement("ColumnDataStyleName");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnDataStyleName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GenericReportColumnView copy()  {
      GenericReportColumnView obj = new GenericReportColumnView();
      obj.setColumnClass(mColumnClass);
      obj.setColumnName(mColumnName);
      obj.setColumnType(mColumnType);
      obj.setColumnPrecision(mColumnPrecision);
      obj.setColumnScale(mColumnScale);
      obj.setColumnWidth(mColumnWidth);
      obj.setTotalRequestFlag(mTotalRequestFlag);
      obj.setColumnFormat(mColumnFormat);
      obj.setColumnHeaderStyleName(mColumnHeaderStyleName);
      obj.setColumnDataStyleName(mColumnDataStyleName);
      
      return obj;
    }

    
    /**
     * Sets the ColumnClass property.
     *
     * @param pColumnClass
     *  String to use to update the property.
     */
    public void setColumnClass(String pColumnClass){
        this.mColumnClass = pColumnClass;
    }
    /**
     * Retrieves the ColumnClass property.
     *
     * @return
     *  String containing the ColumnClass property.
     */
    public String getColumnClass(){
        return mColumnClass;
    }


    /**
     * Sets the ColumnName property.
     *
     * @param pColumnName
     *  String to use to update the property.
     */
    public void setColumnName(String pColumnName){
        this.mColumnName = pColumnName;
    }
    /**
     * Retrieves the ColumnName property.
     *
     * @return
     *  String containing the ColumnName property.
     */
    public String getColumnName(){
        return mColumnName;
    }


    /**
     * Sets the ColumnType property.
     *
     * @param pColumnType
     *  String to use to update the property.
     */
    public void setColumnType(String pColumnType){
        this.mColumnType = pColumnType;
    }
    /**
     * Retrieves the ColumnType property.
     *
     * @return
     *  String containing the ColumnType property.
     */
    public String getColumnType(){
        return mColumnType;
    }


    /**
     * Sets the ColumnPrecision property.
     *
     * @param pColumnPrecision
     *  int to use to update the property.
     */
    public void setColumnPrecision(int pColumnPrecision){
        this.mColumnPrecision = pColumnPrecision;
    }
    /**
     * Retrieves the ColumnPrecision property.
     *
     * @return
     *  int containing the ColumnPrecision property.
     */
    public int getColumnPrecision(){
        return mColumnPrecision;
    }


    /**
     * Sets the ColumnScale property.
     *
     * @param pColumnScale
     *  int to use to update the property.
     */
    public void setColumnScale(int pColumnScale){
        this.mColumnScale = pColumnScale;
    }
    /**
     * Retrieves the ColumnScale property.
     *
     * @return
     *  int containing the ColumnScale property.
     */
    public int getColumnScale(){
        return mColumnScale;
    }


    /**
     * Sets the ColumnWidth property.
     *
     * @param pColumnWidth
     *  String to use to update the property.
     */
    public void setColumnWidth(String pColumnWidth){
        this.mColumnWidth = pColumnWidth;
    }
    /**
     * Retrieves the ColumnWidth property.
     *
     * @return
     *  String containing the ColumnWidth property.
     */
    public String getColumnWidth(){
        return mColumnWidth;
    }


    /**
     * Sets the TotalRequestFlag property.
     *
     * @param pTotalRequestFlag
     *  boolean to use to update the property.
     */
    public void setTotalRequestFlag(boolean pTotalRequestFlag){
        this.mTotalRequestFlag = pTotalRequestFlag;
    }
    /**
     * Retrieves the TotalRequestFlag property.
     *
     * @return
     *  boolean containing the TotalRequestFlag property.
     */
    public boolean getTotalRequestFlag(){
        return mTotalRequestFlag;
    }


    /**
     * Sets the ColumnFormat property.
     *
     * @param pColumnFormat
     *  String to use to update the property.
     */
    public void setColumnFormat(String pColumnFormat){
        this.mColumnFormat = pColumnFormat;
    }
    /**
     * Retrieves the ColumnFormat property.
     *
     * @return
     *  String containing the ColumnFormat property.
     */
    public String getColumnFormat(){
        return mColumnFormat;
    }


    /**
     * Sets the ColumnHeaderStyleName property.
     *
     * @param pColumnHeaderStyleName
     *  String to use to update the property.
     */
    public void setColumnHeaderStyleName(String pColumnHeaderStyleName){
        this.mColumnHeaderStyleName = pColumnHeaderStyleName;
    }
    /**
     * Retrieves the ColumnHeaderStyleName property.
     *
     * @return
     *  String containing the ColumnHeaderStyleName property.
     */
    public String getColumnHeaderStyleName(){
        return mColumnHeaderStyleName;
    }


    /**
     * Sets the ColumnDataStyleName property.
     *
     * @param pColumnDataStyleName
     *  String to use to update the property.
     */
    public void setColumnDataStyleName(String pColumnDataStyleName){
        this.mColumnDataStyleName = pColumnDataStyleName;
    }
    /**
     * Retrieves the ColumnDataStyleName property.
     *
     * @return
     *  String containing the ColumnDataStyleName property.
     */
    public String getColumnDataStyleName(){
        return mColumnDataStyleName;
    }


    
}
