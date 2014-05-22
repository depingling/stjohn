
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogFiscalPeriodView
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
 * <code>CatalogFiscalPeriodView</code> is a ViewObject class for UI.
 */
public class CatalogFiscalPeriodView
extends ValueObject
{
   
    private static final long serialVersionUID = 4301372338550747587L;
    private int mCatalogId;
    private BusEntityDataVector mAccounts;
    private FiscalCalenderView mFiscalCalenderView;
    private int mCurrentFiscalPeriod;

    /**
     * Constructor.
     */
    public CatalogFiscalPeriodView ()
    {
    }

    /**
     * Constructor. 
     */
    public CatalogFiscalPeriodView(int parm1, BusEntityDataVector parm2, FiscalCalenderView parm3, int parm4)
    {
        mCatalogId = parm1;
        mAccounts = parm2;
        mFiscalCalenderView = parm3;
        mCurrentFiscalPeriod = parm4;
        
    }

    /**
     * Creates a new CatalogFiscalPeriodView
     *
     * @return
     *  Newly initialized CatalogFiscalPeriodView object.
     */
    public static CatalogFiscalPeriodView createValue () 
    {
        CatalogFiscalPeriodView valueView = new CatalogFiscalPeriodView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogFiscalPeriodView object
     */
    public String toString()
    {
        return "[" + "CatalogId=" + mCatalogId + ", Accounts=" + mAccounts + ", FiscalCalenderView=" + mFiscalCalenderView + ", CurrentFiscalPeriod=" + mCurrentFiscalPeriod + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CatalogFiscalPeriod");
	root.setAttribute("Id", String.valueOf(mCatalogId));

	Element node;

        node = doc.createElement("Accounts");
        node.appendChild(doc.createTextNode(String.valueOf(mAccounts)));
        root.appendChild(node);

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
    public CatalogFiscalPeriodView copy()  {
      CatalogFiscalPeriodView obj = new CatalogFiscalPeriodView();
      obj.setCatalogId(mCatalogId);
      obj.setAccounts(mAccounts);
      obj.setFiscalCalenderView(mFiscalCalenderView);
      obj.setCurrentFiscalPeriod(mCurrentFiscalPeriod);
      
      return obj;
    }

    
    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the Accounts property.
     *
     * @param pAccounts
     *  BusEntityDataVector to use to update the property.
     */
    public void setAccounts(BusEntityDataVector pAccounts){
        this.mAccounts = pAccounts;
    }
    /**
     * Retrieves the Accounts property.
     *
     * @return
     *  BusEntityDataVector containing the Accounts property.
     */
    public BusEntityDataVector getAccounts(){
        return mAccounts;
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
