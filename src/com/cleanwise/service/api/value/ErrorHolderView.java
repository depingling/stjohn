
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ErrorHolderView
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
 * <code>ErrorHolderView</code> is a ViewObject class for UI.
 */
public class ErrorHolderView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -1L
        ;
    private String mType;
    private String mMessage;

    /**
     * Constructor.
     */
    public ErrorHolderView ()
    {
        mType = "";
        mMessage = "";
    }

    /**
     * Constructor. 
     */
    public ErrorHolderView(String parm1, String parm2)
    {
        mType = parm1;
        mMessage = parm2;
        
    }

    /**
     * Creates a new ErrorHolderView
     *
     * @return
     *  Newly initialized ErrorHolderView object.
     */
    public static ErrorHolderView createValue () 
    {
        ErrorHolderView valueView = new ErrorHolderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ErrorHolderView object
     */
    public String toString()
    {
        return "[" + "Type=" + mType + ", Message=" + mMessage + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ErrorHolder");
	root.setAttribute("Id", String.valueOf(mType));

	Element node;

        node = doc.createElement("Message");
        node.appendChild(doc.createTextNode(String.valueOf(mMessage)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ErrorHolderView copy()  {
      ErrorHolderView obj = new ErrorHolderView();
      obj.setType(mType);
      obj.setMessage(mMessage);
      
      return obj;
    }

    
    /**
     * Sets the Type property.
     *
     * @param pType
     *  String to use to update the property.
     */
    public void setType(String pType){
        this.mType = pType;
    }
    /**
     * Retrieves the Type property.
     *
     * @return
     *  String containing the Type property.
     */
    public String getType(){
        return mType;
    }


    /**
     * Sets the Message property.
     *
     * @param pMessage
     *  String to use to update the property.
     */
    public void setMessage(String pMessage){
        this.mMessage = pMessage;
    }
    /**
     * Retrieves the Message property.
     *
     * @return
     *  String containing the Message property.
     */
    public String getMessage(){
        return mMessage;
    }


    
}
