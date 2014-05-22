
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CleaningSchedStructJoinView
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
 * <code>CleaningSchedStructJoinView</code> is a ViewObject class for UI.
 */
public class CleaningSchedStructJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = 5146964882644339129L;
    private CleaningSchedStructData mScheduleStep;
    private CleaningProcData mProcedure;
    private ProdApplJoinViewVector mProducts;

    /**
     * Constructor.
     */
    public CleaningSchedStructJoinView ()
    {
    }

    /**
     * Constructor. 
     */
    public CleaningSchedStructJoinView(CleaningSchedStructData parm1, CleaningProcData parm2, ProdApplJoinViewVector parm3)
    {
        mScheduleStep = parm1;
        mProcedure = parm2;
        mProducts = parm3;
        
    }

    /**
     * Creates a new CleaningSchedStructJoinView
     *
     * @return
     *  Newly initialized CleaningSchedStructJoinView object.
     */
    public static CleaningSchedStructJoinView createValue () 
    {
        CleaningSchedStructJoinView valueView = new CleaningSchedStructJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CleaningSchedStructJoinView object
     */
    public String toString()
    {
        return "[" + "ScheduleStep=" + mScheduleStep + ", Procedure=" + mProcedure + ", Products=" + mProducts + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CleaningSchedStructJoin");
	root.setAttribute("Id", String.valueOf(mScheduleStep));

	Element node;

        node = doc.createElement("Procedure");
        node.appendChild(doc.createTextNode(String.valueOf(mProcedure)));
        root.appendChild(node);

        node = doc.createElement("Products");
        node.appendChild(doc.createTextNode(String.valueOf(mProducts)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CleaningSchedStructJoinView copy()  {
      CleaningSchedStructJoinView obj = new CleaningSchedStructJoinView();
      obj.setScheduleStep(mScheduleStep);
      obj.setProcedure(mProcedure);
      obj.setProducts(mProducts);
      
      return obj;
    }

    
    /**
     * Sets the ScheduleStep property.
     *
     * @param pScheduleStep
     *  CleaningSchedStructData to use to update the property.
     */
    public void setScheduleStep(CleaningSchedStructData pScheduleStep){
        this.mScheduleStep = pScheduleStep;
    }
    /**
     * Retrieves the ScheduleStep property.
     *
     * @return
     *  CleaningSchedStructData containing the ScheduleStep property.
     */
    public CleaningSchedStructData getScheduleStep(){
        return mScheduleStep;
    }


    /**
     * Sets the Procedure property.
     *
     * @param pProcedure
     *  CleaningProcData to use to update the property.
     */
    public void setProcedure(CleaningProcData pProcedure){
        this.mProcedure = pProcedure;
    }
    /**
     * Retrieves the Procedure property.
     *
     * @return
     *  CleaningProcData containing the Procedure property.
     */
    public CleaningProcData getProcedure(){
        return mProcedure;
    }


    /**
     * Sets the Products property.
     *
     * @param pProducts
     *  ProdApplJoinViewVector to use to update the property.
     */
    public void setProducts(ProdApplJoinViewVector pProducts){
        this.mProducts = pProducts;
    }
    /**
     * Retrieves the Products property.
     *
     * @return
     *  ProdApplJoinViewVector containing the Products property.
     */
    public ProdApplJoinViewVector getProducts(){
        return mProducts;
    }


    
}
