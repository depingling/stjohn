
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportResultView
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

import java.util.ArrayList;import com.cleanwise.service.api.value.GenericReportColumnViewVector;


/**
 * <code>GenericReportResultView</code> is a ViewObject class for UI.
 */
public class GenericReportResultView
extends ValueObject
{
   
    private static final long serialVersionUID = -3439171118279301496L;
    private int mColumnCount;
    private String mName;
    private GenericReportColumnViewVector mHeader;
    private ArrayList mTable;
    private String mWidth;
    private boolean mFancyDisplay;
    private String mTimeZone;
    private String mPaperOrientation;
    private String mPaperSize;
    private GenericReportColumnViewVector mTitle;
    private HashMap mUserStyle;
    private byte[] mRawOutput;
    private String mReportFormat;
    private double mWidthFactor;
    private int mFreezePositionRow;
    private int mFreezePositionColumn;

    /**
     * Constructor.
     */
    public GenericReportResultView ()
    {
        mName = "";
        mWidth = "";
        mTimeZone = "";
        mPaperOrientation = "";
        mPaperSize = "";
        mReportFormat = "";
    }

    /**
     * Constructor. 
     */
    public GenericReportResultView(int parm1, String parm2, GenericReportColumnViewVector parm3, ArrayList parm4, String parm5, boolean parm6, String parm7, String parm8, String parm9, GenericReportColumnViewVector parm10, HashMap parm11, byte[] parm12, String parm13, double parm14, int parm15, int parm16)
    {
        mColumnCount = parm1;
        mName = parm2;
        mHeader = parm3;
        mTable = parm4;
        mWidth = parm5;
        mFancyDisplay = parm6;
        mTimeZone = parm7;
        mPaperOrientation = parm8;
        mPaperSize = parm9;
        mTitle = parm10;
        mUserStyle = parm11;
        mRawOutput = parm12;
        mReportFormat = parm13;
        mWidthFactor = parm14;
        mFreezePositionRow = parm15;
        mFreezePositionColumn = parm16;
        
    }

    /**
     * Creates a new GenericReportResultView
     *
     * @return
     *  Newly initialized GenericReportResultView object.
     */
    public static GenericReportResultView createValue () 
    {
        GenericReportResultView valueView = new GenericReportResultView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportResultView object
     */
    public String toString()
    {
        return "[" + "ColumnCount=" + mColumnCount + ", Name=" + mName + ", Header=" + mHeader + ", Table=" + mTable + ", Width=" + mWidth + ", FancyDisplay=" + mFancyDisplay + ", TimeZone=" + mTimeZone + ", PaperOrientation=" + mPaperOrientation + ", PaperSize=" + mPaperSize + ", Title=" + mTitle + ", UserStyle=" + mUserStyle + ", RawOutput=" + mRawOutput + ", ReportFormat=" + mReportFormat + ", WidthFactor=" + mWidthFactor + ", FreezePositionRow=" + mFreezePositionRow + ", FreezePositionColumn=" + mFreezePositionColumn + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GenericReportResult");
	root.setAttribute("Id", String.valueOf(mColumnCount));

	Element node;

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("Header");
        node.appendChild(doc.createTextNode(String.valueOf(mHeader)));
        root.appendChild(node);

        node = doc.createElement("Table");
        node.appendChild(doc.createTextNode(String.valueOf(mTable)));
        root.appendChild(node);

        node = doc.createElement("Width");
        node.appendChild(doc.createTextNode(String.valueOf(mWidth)));
        root.appendChild(node);

        node = doc.createElement("FancyDisplay");
        node.appendChild(doc.createTextNode(String.valueOf(mFancyDisplay)));
        root.appendChild(node);

        node = doc.createElement("TimeZone");
        node.appendChild(doc.createTextNode(String.valueOf(mTimeZone)));
        root.appendChild(node);

        node = doc.createElement("PaperOrientation");
        node.appendChild(doc.createTextNode(String.valueOf(mPaperOrientation)));
        root.appendChild(node);

        node = doc.createElement("PaperSize");
        node.appendChild(doc.createTextNode(String.valueOf(mPaperSize)));
        root.appendChild(node);

        node = doc.createElement("Title");
        node.appendChild(doc.createTextNode(String.valueOf(mTitle)));
        root.appendChild(node);

        node = doc.createElement("UserStyle");
        node.appendChild(doc.createTextNode(String.valueOf(mUserStyle)));
        root.appendChild(node);

        node = doc.createElement("RawOutput");
        node.appendChild(doc.createTextNode(String.valueOf(mRawOutput)));
        root.appendChild(node);

        node = doc.createElement("ReportFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mReportFormat)));
        root.appendChild(node);

        node = doc.createElement("WidthFactor");
        node.appendChild(doc.createTextNode(String.valueOf(mWidthFactor)));
        root.appendChild(node);

        node = doc.createElement("FreezePositionRow");
        node.appendChild(doc.createTextNode(String.valueOf(mFreezePositionRow)));
        root.appendChild(node);

        node = doc.createElement("FreezePositionColumn");
        node.appendChild(doc.createTextNode(String.valueOf(mFreezePositionColumn)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GenericReportResultView copy()  {
      GenericReportResultView obj = new GenericReportResultView();
      obj.setColumnCount(mColumnCount);
      obj.setName(mName);
      obj.setHeader(mHeader);
      obj.setTable(mTable);
      obj.setWidth(mWidth);
      obj.setFancyDisplay(mFancyDisplay);
      obj.setTimeZone(mTimeZone);
      obj.setPaperOrientation(mPaperOrientation);
      obj.setPaperSize(mPaperSize);
      obj.setTitle(mTitle);
      obj.setUserStyle(mUserStyle);
      obj.setRawOutput(mRawOutput);
      obj.setReportFormat(mReportFormat);
      obj.setWidthFactor(mWidthFactor);
      obj.setFreezePositionRow(mFreezePositionRow);
      obj.setFreezePositionColumn(mFreezePositionColumn);
      
      return obj;
    }

    
    /**
     * Sets the ColumnCount property.
     *
     * @param pColumnCount
     *  int to use to update the property.
     */
    public void setColumnCount(int pColumnCount){
        this.mColumnCount = pColumnCount;
    }
    /**
     * Retrieves the ColumnCount property.
     *
     * @return
     *  int containing the ColumnCount property.
     */
    public int getColumnCount(){
        return mColumnCount;
    }


    /**
     * Sets the Name property.
     *
     * @param pName
     *  String to use to update the property.
     */
    public void setName(String pName){
        this.mName = pName;
    }
    /**
     * Retrieves the Name property.
     *
     * @return
     *  String containing the Name property.
     */
    public String getName(){
        return mName;
    }


    /**
     * Sets the Header property.
     *
     * @param pHeader
     *  GenericReportColumnViewVector to use to update the property.
     */
    public void setHeader(GenericReportColumnViewVector pHeader){
        this.mHeader = pHeader;
    }
    /**
     * Retrieves the Header property.
     *
     * @return
     *  GenericReportColumnViewVector containing the Header property.
     */
    public GenericReportColumnViewVector getHeader(){
        return mHeader;
    }


    /**
     * Sets the Table property.
     *
     * @param pTable
     *  ArrayList to use to update the property.
     */
    public void setTable(ArrayList pTable){
        this.mTable = pTable;
    }
    /**
     * Retrieves the Table property.
     *
     * @return
     *  ArrayList containing the Table property.
     */
    public ArrayList getTable(){
        return mTable;
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
     * Sets the FancyDisplay property.
     *
     * @param pFancyDisplay
     *  boolean to use to update the property.
     */
    public void setFancyDisplay(boolean pFancyDisplay){
        this.mFancyDisplay = pFancyDisplay;
    }
    /**
     * Retrieves the FancyDisplay property.
     *
     * @return
     *  boolean containing the FancyDisplay property.
     */
    public boolean getFancyDisplay(){
        return mFancyDisplay;
    }


    /**
     * Sets the TimeZone property.
     *
     * @param pTimeZone
     *  String to use to update the property.
     */
    public void setTimeZone(String pTimeZone){
        this.mTimeZone = pTimeZone;
    }
    /**
     * Retrieves the TimeZone property.
     *
     * @return
     *  String containing the TimeZone property.
     */
    public String getTimeZone(){
        return mTimeZone;
    }


    /**
     * Sets the PaperOrientation property.
     *
     * @param pPaperOrientation
     *  String to use to update the property.
     */
    public void setPaperOrientation(String pPaperOrientation){
        this.mPaperOrientation = pPaperOrientation;
    }
    /**
     * Retrieves the PaperOrientation property.
     *
     * @return
     *  String containing the PaperOrientation property.
     */
    public String getPaperOrientation(){
        return mPaperOrientation;
    }


    /**
     * Sets the PaperSize property.
     *
     * @param pPaperSize
     *  String to use to update the property.
     */
    public void setPaperSize(String pPaperSize){
        this.mPaperSize = pPaperSize;
    }
    /**
     * Retrieves the PaperSize property.
     *
     * @return
     *  String containing the PaperSize property.
     */
    public String getPaperSize(){
        return mPaperSize;
    }


    /**
     * Sets the Title property.
     *
     * @param pTitle
     *  GenericReportColumnViewVector to use to update the property.
     */
    public void setTitle(GenericReportColumnViewVector pTitle){
        this.mTitle = pTitle;
    }
    /**
     * Retrieves the Title property.
     *
     * @return
     *  GenericReportColumnViewVector containing the Title property.
     */
    public GenericReportColumnViewVector getTitle(){
        return mTitle;
    }


    /**
     * Sets the UserStyle property.
     *
     * @param pUserStyle
     *  HashMap to use to update the property.
     */
    public void setUserStyle(HashMap pUserStyle){
        this.mUserStyle = pUserStyle;
    }
    /**
     * Retrieves the UserStyle property.
     *
     * @return
     *  HashMap containing the UserStyle property.
     */
    public HashMap getUserStyle(){
        return mUserStyle;
    }


    /**
     * Sets the RawOutput property.
     *
     * @param pRawOutput
     *  byte[] to use to update the property.
     */
    public void setRawOutput(byte[] pRawOutput){
        this.mRawOutput = pRawOutput;
    }
    /**
     * Retrieves the RawOutput property.
     *
     * @return
     *  byte[] containing the RawOutput property.
     */
    public byte[] getRawOutput(){
        return mRawOutput;
    }


    /**
     * Sets the ReportFormat property.
     *
     * @param pReportFormat
     *  String to use to update the property.
     */
    public void setReportFormat(String pReportFormat){
        this.mReportFormat = pReportFormat;
    }
    /**
     * Retrieves the ReportFormat property.
     *
     * @return
     *  String containing the ReportFormat property.
     */
    public String getReportFormat(){
        return mReportFormat;
    }


    /**
     * Sets the WidthFactor property.
     *
     * @param pWidthFactor
     *  double to use to update the property.
     */
    public void setWidthFactor(double pWidthFactor){
        this.mWidthFactor = pWidthFactor;
    }
    /**
     * Retrieves the WidthFactor property.
     *
     * @return
     *  double containing the WidthFactor property.
     */
    public double getWidthFactor(){
        return mWidthFactor;
    }


    /**
     * Sets the FreezePositionRow property.
     *
     * @param pFreezePositionRow
     *  int to use to update the property.
     */
    public void setFreezePositionRow(int pFreezePositionRow){
        this.mFreezePositionRow = pFreezePositionRow;
    }
    /**
     * Retrieves the FreezePositionRow property.
     *
     * @return
     *  int containing the FreezePositionRow property.
     */
    public int getFreezePositionRow(){
        return mFreezePositionRow;
    }


    /**
     * Sets the FreezePositionColumn property.
     *
     * @param pFreezePositionColumn
     *  int to use to update the property.
     */
    public void setFreezePositionColumn(int pFreezePositionColumn){
        this.mFreezePositionColumn = pFreezePositionColumn;
    }
    /**
     * Retrieves the FreezePositionColumn property.
     *
     * @return
     *  int containing the FreezePositionColumn property.
     */
    public int getFreezePositionColumn(){
        return mFreezePositionColumn;
    }


    
}
