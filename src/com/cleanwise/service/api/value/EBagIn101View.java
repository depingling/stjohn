
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EBagIn101View
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
 * <code>EBagIn101View</code> is a ViewObject class for UI.
 */
public class EBagIn101View
extends ValueObject
{
   
    private static final long serialVersionUID = -2303430467274857541L;
    private EBagIn101ComponentView mAccount;
    private EBagIn101ComponentViewVector mSites;

    /**
     * Constructor.
     */
    public EBagIn101View ()
    {
    }

    /**
     * Constructor. 
     */
    public EBagIn101View(EBagIn101ComponentView parm1, EBagIn101ComponentViewVector parm2)
    {
        mAccount = parm1;
        mSites = parm2;
        
    }

    /**
     * Creates a new EBagIn101View
     *
     * @return
     *  Newly initialized EBagIn101View object.
     */
    public static EBagIn101View createValue () 
    {
        EBagIn101View valueView = new EBagIn101View();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EBagIn101View object
     */
    public String toString()
    {
        return "[" + "Account=" + mAccount + ", Sites=" + mSites + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EBagIn101");
	root.setAttribute("Id", String.valueOf(mAccount));

	Element node;

        node = doc.createElement("Sites");
        node.appendChild(doc.createTextNode(String.valueOf(mSites)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EBagIn101View copy()  {
      EBagIn101View obj = new EBagIn101View();
      obj.setAccount(mAccount);
      obj.setSites(mSites);
      
      return obj;
    }

    
    /**
     * Sets the Account property.
     *
     * @param pAccount
     *  EBagIn101ComponentView to use to update the property.
     */
    public void setAccount(EBagIn101ComponentView pAccount){
        this.mAccount = pAccount;
    }
    /**
     * Retrieves the Account property.
     *
     * @return
     *  EBagIn101ComponentView containing the Account property.
     */
    public EBagIn101ComponentView getAccount(){
        return mAccount;
    }


    /**
     * Sets the Sites property.
     *
     * @param pSites
     *  EBagIn101ComponentViewVector to use to update the property.
     */
    public void setSites(EBagIn101ComponentViewVector pSites){
        this.mSites = pSites;
    }
    /**
     * Retrieves the Sites property.
     *
     * @return
     *  EBagIn101ComponentViewVector containing the Sites property.
     */
    public EBagIn101ComponentViewVector getSites(){
        return mSites;
    }


    
}
