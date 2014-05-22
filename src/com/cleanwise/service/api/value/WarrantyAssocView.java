
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyAssocView
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
 * <code>WarrantyAssocView</code> is a ViewObject class for UI.
 */
public class WarrantyAssocView
extends ValueObject
{
   
    private static final long serialVersionUID = 4422397901669645926L;
    private BusEntityData mBusEntity;
    private WarrantyAssocData mWarrantyAssoc;

    /**
     * Constructor.
     */
    public WarrantyAssocView ()
    {
    }

    /**
     * Constructor. 
     */
    public WarrantyAssocView(BusEntityData parm1, WarrantyAssocData parm2)
    {
        mBusEntity = parm1;
        mWarrantyAssoc = parm2;
        
    }

    /**
     * Creates a new WarrantyAssocView
     *
     * @return
     *  Newly initialized WarrantyAssocView object.
     */
    public static WarrantyAssocView createValue () 
    {
        WarrantyAssocView valueView = new WarrantyAssocView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyAssocView object
     */
    public String toString()
    {
        return "[" + "BusEntity=" + mBusEntity + ", WarrantyAssoc=" + mWarrantyAssoc + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WarrantyAssoc");
	root.setAttribute("Id", String.valueOf(mBusEntity));

	Element node;

        node = doc.createElement("WarrantyAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyAssoc)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WarrantyAssocView copy()  {
      WarrantyAssocView obj = new WarrantyAssocView();
      obj.setBusEntity(mBusEntity);
      obj.setWarrantyAssoc(mWarrantyAssoc);
      
      return obj;
    }

    
    /**
     * Sets the BusEntity property.
     *
     * @param pBusEntity
     *  BusEntityData to use to update the property.
     */
    public void setBusEntity(BusEntityData pBusEntity){
        this.mBusEntity = pBusEntity;
    }
    /**
     * Retrieves the BusEntity property.
     *
     * @return
     *  BusEntityData containing the BusEntity property.
     */
    public BusEntityData getBusEntity(){
        return mBusEntity;
    }


    /**
     * Sets the WarrantyAssoc property.
     *
     * @param pWarrantyAssoc
     *  WarrantyAssocData to use to update the property.
     */
    public void setWarrantyAssoc(WarrantyAssocData pWarrantyAssoc){
        this.mWarrantyAssoc = pWarrantyAssoc;
    }
    /**
     * Retrieves the WarrantyAssoc property.
     *
     * @return
     *  WarrantyAssocData containing the WarrantyAssoc property.
     */
    public WarrantyAssocData getWarrantyAssoc(){
        return mWarrantyAssoc;
    }


    
}
