
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FiscalCalenderView
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
 * <code>FiscalCalenderView</code> is a ViewObject class for UI.
 */
public class FiscalCalenderView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private FiscalCalenderData mFiscalCalender;
    private FiscalCalenderDetailDataVector mFiscalCalenderDetails;

    /**
     * Constructor.
     */
    public FiscalCalenderView ()
    {
    }

    /**
     * Constructor. 
     */
    public FiscalCalenderView(FiscalCalenderData parm1, FiscalCalenderDetailDataVector parm2)
    {
        mFiscalCalender = parm1;
        mFiscalCalenderDetails = parm2;
        
    }

    /**
     * Creates a new FiscalCalenderView
     *
     * @return
     *  Newly initialized FiscalCalenderView object.
     */
    public static FiscalCalenderView createValue () 
    {
        FiscalCalenderView valueView = new FiscalCalenderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FiscalCalenderView object
     */
    public String toString()
    {
        return "[" + "FiscalCalender=" + mFiscalCalender + ", FiscalCalenderDetails=" + mFiscalCalenderDetails + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FiscalCalender");
	root.setAttribute("Id", String.valueOf(mFiscalCalender));

	Element node;

        node = doc.createElement("FiscalCalenderDetails");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalCalenderDetails)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public FiscalCalenderView copy()  {
      FiscalCalenderView obj = new FiscalCalenderView();
      obj.setFiscalCalender(mFiscalCalender);
      obj.setFiscalCalenderDetails(mFiscalCalenderDetails);
      
      return obj;
    }

    
    /**
     * Sets the FiscalCalender property.
     *
     * @param pFiscalCalender
     *  FiscalCalenderData to use to update the property.
     */
    public void setFiscalCalender(FiscalCalenderData pFiscalCalender){
        this.mFiscalCalender = pFiscalCalender;
    }
    /**
     * Retrieves the FiscalCalender property.
     *
     * @return
     *  FiscalCalenderData containing the FiscalCalender property.
     */
    public FiscalCalenderData getFiscalCalender(){
        return mFiscalCalender;
    }


    /**
     * Sets the FiscalCalenderDetails property.
     *
     * @param pFiscalCalenderDetails
     *  FiscalCalenderDetailDataVector to use to update the property.
     */
    public void setFiscalCalenderDetails(FiscalCalenderDetailDataVector pFiscalCalenderDetails){
        this.mFiscalCalenderDetails = pFiscalCalenderDetails;
    }
    /**
     * Retrieves the FiscalCalenderDetails property.
     *
     * @return
     *  FiscalCalenderDetailDataVector containing the FiscalCalenderDetails property.
     */
    public FiscalCalenderDetailDataVector getFiscalCalenderDetails(){
        return mFiscalCalenderDetails;
    }


    
}
