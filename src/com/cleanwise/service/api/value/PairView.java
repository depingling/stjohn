
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PairView
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
 * <code>PairView</code> is a ViewObject class for UI.
 */
public class PairView
extends ValueObject
{
   
    private static final long serialVersionUID = 3886938018923923480L;
    private Object mObject1;
    private Object mObject2;

    /**
     * Constructor.
     */
    public PairView ()
    {
    }

    /**
     * Constructor. 
     */
    public PairView(Object parm1, Object parm2)
    {
        mObject1 = parm1;
        mObject2 = parm2;
        
    }

    /**
     * Creates a new PairView
     *
     * @return
     *  Newly initialized PairView object.
     */
    public static PairView createValue () 
    {
        PairView valueView = new PairView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PairView object
     */
    public String toString()
    {
        return "[" + "Object1=" + mObject1 + ", Object2=" + mObject2 + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Pair");
	root.setAttribute("Id", String.valueOf(mObject1));

	Element node;

        node = doc.createElement("Object2");
        node.appendChild(doc.createTextNode(String.valueOf(mObject2)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PairView copy()  {
      PairView obj = new PairView();
      obj.setObject1(mObject1);
      obj.setObject2(mObject2);
      
      return obj;
    }

    
    /**
     * Sets the Object1 property.
     *
     * @param pObject1
     *  Object to use to update the property.
     */
    public void setObject1(Object pObject1){
        this.mObject1 = pObject1;
    }
    /**
     * Retrieves the Object1 property.
     *
     * @return
     *  Object containing the Object1 property.
     */
    public Object getObject1(){
        return mObject1;
    }


    /**
     * Sets the Object2 property.
     *
     * @param pObject2
     *  Object to use to update the property.
     */
    public void setObject2(Object pObject2){
        this.mObject2 = pObject2;
    }
    /**
     * Retrieves the Object2 property.
     *
     * @return
     *  Object containing the Object2 property.
     */
    public Object getObject2(){
        return mObject2;
    }


    
}
