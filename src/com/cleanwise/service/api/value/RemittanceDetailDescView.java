
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RemittanceDetailDescView
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
 * <code>RemittanceDetailDescView</code> is a ViewObject class for UI.
 */
public class RemittanceDetailDescView
extends ValueObject
{
   
    private static final long serialVersionUID = 2188324093568515612L;
    private RemittanceDetailData mRemittanceDetailData;
    private RemittancePropertyDataVector mRemittancePropertyDataVector;

    /**
     * Constructor.
     */
    public RemittanceDetailDescView ()
    {
    }

    /**
     * Constructor. 
     */
    public RemittanceDetailDescView(RemittanceDetailData parm1, RemittancePropertyDataVector parm2)
    {
        mRemittanceDetailData = parm1;
        mRemittancePropertyDataVector = parm2;
        
    }

    /**
     * Creates a new RemittanceDetailDescView
     *
     * @return
     *  Newly initialized RemittanceDetailDescView object.
     */
    public static RemittanceDetailDescView createValue () 
    {
        RemittanceDetailDescView valueView = new RemittanceDetailDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RemittanceDetailDescView object
     */
    public String toString()
    {
        return "[" + "RemittanceDetailData=" + mRemittanceDetailData + ", RemittancePropertyDataVector=" + mRemittancePropertyDataVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("RemittanceDetailDesc");
	root.setAttribute("Id", String.valueOf(mRemittanceDetailData));

	Element node;

        node = doc.createElement("RemittancePropertyDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyDataVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public RemittanceDetailDescView copy()  {
      RemittanceDetailDescView obj = new RemittanceDetailDescView();
      obj.setRemittanceDetailData(mRemittanceDetailData);
      obj.setRemittancePropertyDataVector(mRemittancePropertyDataVector);
      
      return obj;
    }

    
    /**
     * Sets the RemittanceDetailData property.
     *
     * @param pRemittanceDetailData
     *  RemittanceDetailData to use to update the property.
     */
    public void setRemittanceDetailData(RemittanceDetailData pRemittanceDetailData){
        this.mRemittanceDetailData = pRemittanceDetailData;
    }
    /**
     * Retrieves the RemittanceDetailData property.
     *
     * @return
     *  RemittanceDetailData containing the RemittanceDetailData property.
     */
    public RemittanceDetailData getRemittanceDetailData(){
        return mRemittanceDetailData;
    }


    /**
     * Sets the RemittancePropertyDataVector property.
     *
     * @param pRemittancePropertyDataVector
     *  RemittancePropertyDataVector to use to update the property.
     */
    public void setRemittancePropertyDataVector(RemittancePropertyDataVector pRemittancePropertyDataVector){
        this.mRemittancePropertyDataVector = pRemittancePropertyDataVector;
    }
    /**
     * Retrieves the RemittancePropertyDataVector property.
     *
     * @return
     *  RemittancePropertyDataVector containing the RemittancePropertyDataVector property.
     */
    public RemittancePropertyDataVector getRemittancePropertyDataVector(){
        return mRemittancePropertyDataVector;
    }


    
}
