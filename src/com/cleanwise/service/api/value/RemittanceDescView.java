
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RemittanceDescView
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
 * <code>RemittanceDescView</code> is a ViewObject class for UI.
 */
public class RemittanceDescView
extends ValueObject
{
   
    private static final long serialVersionUID = 8941309956517050642L;
    private RemittanceData mRemittanceData;
    private RemittanceDetailDataVector mRemittanceDetailDataVector;
    private RemittancePropertyDataVector mRemittanceProperties;

    /**
     * Constructor.
     */
    public RemittanceDescView ()
    {
    }

    /**
     * Constructor. 
     */
    public RemittanceDescView(RemittanceData parm1, RemittanceDetailDataVector parm2, RemittancePropertyDataVector parm3)
    {
        mRemittanceData = parm1;
        mRemittanceDetailDataVector = parm2;
        mRemittanceProperties = parm3;
        
    }

    /**
     * Creates a new RemittanceDescView
     *
     * @return
     *  Newly initialized RemittanceDescView object.
     */
    public static RemittanceDescView createValue () 
    {
        RemittanceDescView valueView = new RemittanceDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RemittanceDescView object
     */
    public String toString()
    {
        return "[" + "RemittanceData=" + mRemittanceData + ", RemittanceDetailDataVector=" + mRemittanceDetailDataVector + ", RemittanceProperties=" + mRemittanceProperties + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("RemittanceDesc");
	root.setAttribute("Id", String.valueOf(mRemittanceData));

	Element node;

        node = doc.createElement("RemittanceDetailDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailDataVector)));
        root.appendChild(node);

        node = doc.createElement("RemittanceProperties");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceProperties)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public RemittanceDescView copy()  {
      RemittanceDescView obj = new RemittanceDescView();
      obj.setRemittanceData(mRemittanceData);
      obj.setRemittanceDetailDataVector(mRemittanceDetailDataVector);
      obj.setRemittanceProperties(mRemittanceProperties);
      
      return obj;
    }

    
    /**
     * Sets the RemittanceData property.
     *
     * @param pRemittanceData
     *  RemittanceData to use to update the property.
     */
    public void setRemittanceData(RemittanceData pRemittanceData){
        this.mRemittanceData = pRemittanceData;
    }
    /**
     * Retrieves the RemittanceData property.
     *
     * @return
     *  RemittanceData containing the RemittanceData property.
     */
    public RemittanceData getRemittanceData(){
        return mRemittanceData;
    }


    /**
     * Sets the RemittanceDetailDataVector property.
     *
     * @param pRemittanceDetailDataVector
     *  RemittanceDetailDataVector to use to update the property.
     */
    public void setRemittanceDetailDataVector(RemittanceDetailDataVector pRemittanceDetailDataVector){
        this.mRemittanceDetailDataVector = pRemittanceDetailDataVector;
    }
    /**
     * Retrieves the RemittanceDetailDataVector property.
     *
     * @return
     *  RemittanceDetailDataVector containing the RemittanceDetailDataVector property.
     */
    public RemittanceDetailDataVector getRemittanceDetailDataVector(){
        return mRemittanceDetailDataVector;
    }


    /**
     * Sets the RemittanceProperties property.
     *
     * @param pRemittanceProperties
     *  RemittancePropertyDataVector to use to update the property.
     */
    public void setRemittanceProperties(RemittancePropertyDataVector pRemittanceProperties){
        this.mRemittanceProperties = pRemittanceProperties;
    }
    /**
     * Retrieves the RemittanceProperties property.
     *
     * @return
     *  RemittancePropertyDataVector containing the RemittanceProperties property.
     */
    public RemittancePropertyDataVector getRemittanceProperties(){
        return mRemittanceProperties;
    }


    
}
