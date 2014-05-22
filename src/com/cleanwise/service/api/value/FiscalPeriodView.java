
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FiscalPeriodView
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

import com.cleanwise.service.api.value.*;


/**
 * <code>FiscalPeriodView</code> is a ViewObject class for UI.
 */
public class FiscalPeriodView
extends ValueObject
{
   
    private static final long serialVersionUID = -4914438338156661469L;
    private int mBusEntityId;
    private FiscalCalenderView mFiscalCalenderView;
    private int mCurrentFiscalPeriod;

    /**
     * Constructor.
     */
    public FiscalPeriodView ()
    {
    }

    /**
     * Constructor. 
     */
    public FiscalPeriodView(int parm1, FiscalCalenderView parm2, int parm3)
    {
        mBusEntityId = parm1;
        mFiscalCalenderView = parm2;
        mCurrentFiscalPeriod = parm3;
        
    }

    /**
     * Creates a new FiscalPeriodView
     *
     * @return
     *  Newly initialized FiscalPeriodView object.
     */
    public static FiscalPeriodView createValue () 
    {
        FiscalPeriodView valueView = new FiscalPeriodView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FiscalPeriodView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", FiscalCalenderView=" + mFiscalCalenderView + ", CurrentFiscalPeriod=" + mCurrentFiscalPeriod + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FiscalPeriod");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("FiscalCalenderView");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalCalenderView)));
        root.appendChild(node);

        node = doc.createElement("CurrentFiscalPeriod");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrentFiscalPeriod)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public FiscalPeriodView copy()  {
      FiscalPeriodView obj = new FiscalPeriodView();
      obj.setBusEntityId(mBusEntityId);
      obj.setFiscalCalenderView(mFiscalCalenderView);
      obj.setCurrentFiscalPeriod(mCurrentFiscalPeriod);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the FiscalCalenderView property.
     *
     * @param pFiscalCalenderView
     *  FiscalCalenderView to use to update the property.
     */
    public void setFiscalCalenderView(FiscalCalenderView pFiscalCalenderView){
        this.mFiscalCalenderView = pFiscalCalenderView;
    }
    /**
     * Retrieves the FiscalCalenderView property.
     *
     * @return
     *  FiscalCalenderView containing the FiscalCalenderView property.
     */
    public FiscalCalenderView getFiscalCalenderView(){
        return mFiscalCalenderView;
    }


    /**
     * Sets the CurrentFiscalPeriod property.
     *
     * @param pCurrentFiscalPeriod
     *  int to use to update the property.
     */
    public void setCurrentFiscalPeriod(int pCurrentFiscalPeriod){
        this.mCurrentFiscalPeriod = pCurrentFiscalPeriod;
    }
    /**
     * Retrieves the CurrentFiscalPeriod property.
     *
     * @return
     *  int containing the CurrentFiscalPeriod property.
     */
    public int getCurrentFiscalPeriod(){
        return mCurrentFiscalPeriod;
    }


    
}
