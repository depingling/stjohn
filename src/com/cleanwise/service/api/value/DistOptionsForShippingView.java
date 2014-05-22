
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DistOptionsForShippingView
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
 * <code>DistOptionsForShippingView</code> is a ViewObject class for UI.
 */
public class DistOptionsForShippingView
extends ValueObject
{
   
    private static final long serialVersionUID = -8856641963200591614L;
    private BusEntityData mDistBusEntity;
    private BusEntityData mFreightHandlerBusEntity;

    /**
     * Constructor.
     */
    public DistOptionsForShippingView ()
    {
    }

    /**
     * Constructor. 
     */
    public DistOptionsForShippingView(BusEntityData parm1, BusEntityData parm2)
    {
        mDistBusEntity = parm1;
        mFreightHandlerBusEntity = parm2;
        
    }

    /**
     * Creates a new DistOptionsForShippingView
     *
     * @return
     *  Newly initialized DistOptionsForShippingView object.
     */
    public static DistOptionsForShippingView createValue () 
    {
        DistOptionsForShippingView valueView = new DistOptionsForShippingView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistOptionsForShippingView object
     */
    public String toString()
    {
        return "[" + "DistBusEntity=" + mDistBusEntity + ", FreightHandlerBusEntity=" + mFreightHandlerBusEntity + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DistOptionsForShipping");
	root.setAttribute("Id", String.valueOf(mDistBusEntity));

	Element node;

        node = doc.createElement("FreightHandlerBusEntity");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandlerBusEntity)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DistOptionsForShippingView copy()  {
      DistOptionsForShippingView obj = new DistOptionsForShippingView();
      obj.setDistBusEntity(mDistBusEntity);
      obj.setFreightHandlerBusEntity(mFreightHandlerBusEntity);
      
      return obj;
    }

    
    /**
     * Sets the DistBusEntity property.
     *
     * @param pDistBusEntity
     *  BusEntityData to use to update the property.
     */
    public void setDistBusEntity(BusEntityData pDistBusEntity){
        this.mDistBusEntity = pDistBusEntity;
    }
    /**
     * Retrieves the DistBusEntity property.
     *
     * @return
     *  BusEntityData containing the DistBusEntity property.
     */
    public BusEntityData getDistBusEntity(){
        return mDistBusEntity;
    }


    /**
     * Sets the FreightHandlerBusEntity property.
     *
     * @param pFreightHandlerBusEntity
     *  BusEntityData to use to update the property.
     */
    public void setFreightHandlerBusEntity(BusEntityData pFreightHandlerBusEntity){
        this.mFreightHandlerBusEntity = pFreightHandlerBusEntity;
    }
    /**
     * Retrieves the FreightHandlerBusEntity property.
     *
     * @return
     *  BusEntityData containing the FreightHandlerBusEntity property.
     */
    public BusEntityData getFreightHandlerBusEntity(){
        return mFreightHandlerBusEntity;
    }


    
}
