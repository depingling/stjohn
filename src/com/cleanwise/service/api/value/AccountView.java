
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AccountView
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
 * <code>AccountView</code> is a ViewObject class for UI.
 */
public class AccountView
extends ValueObject
{
   
    private static final long serialVersionUID = 6922773314562679635L;
    private int mStoreId;
    private int mAcctId;
    private String mAcctName;
    private String mAcctStatusCd;

    /**
     * Constructor.
     */
    public AccountView ()
    {
        mAcctName = "";
        mAcctStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public AccountView(int parm1, int parm2, String parm3, String parm4)
    {
        mStoreId = parm1;
        mAcctId = parm2;
        mAcctName = parm3;
        mAcctStatusCd = parm4;
        
    }

    /**
     * Creates a new AccountView
     *
     * @return
     *  Newly initialized AccountView object.
     */
    public static AccountView createValue () 
    {
        AccountView valueView = new AccountView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AccountView object
     */
    public String toString()
    {
        return "[" + "StoreId=" + mStoreId + ", AcctId=" + mAcctId + ", AcctName=" + mAcctName + ", AcctStatusCd=" + mAcctStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Account");
	root.setAttribute("Id", String.valueOf(mStoreId));

	Element node;

        node = doc.createElement("AcctId");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctId)));
        root.appendChild(node);

        node = doc.createElement("AcctName");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctName)));
        root.appendChild(node);

        node = doc.createElement("AcctStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AccountView copy()  {
      AccountView obj = new AccountView();
      obj.setStoreId(mStoreId);
      obj.setAcctId(mAcctId);
      obj.setAcctName(mAcctName);
      obj.setAcctStatusCd(mAcctStatusCd);
      
      return obj;
    }

    
    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the AcctId property.
     *
     * @param pAcctId
     *  int to use to update the property.
     */
    public void setAcctId(int pAcctId){
        this.mAcctId = pAcctId;
    }
    /**
     * Retrieves the AcctId property.
     *
     * @return
     *  int containing the AcctId property.
     */
    public int getAcctId(){
        return mAcctId;
    }


    /**
     * Sets the AcctName property.
     *
     * @param pAcctName
     *  String to use to update the property.
     */
    public void setAcctName(String pAcctName){
        this.mAcctName = pAcctName;
    }
    /**
     * Retrieves the AcctName property.
     *
     * @return
     *  String containing the AcctName property.
     */
    public String getAcctName(){
        return mAcctName;
    }


    /**
     * Sets the AcctStatusCd property.
     *
     * @param pAcctStatusCd
     *  String to use to update the property.
     */
    public void setAcctStatusCd(String pAcctStatusCd){
        this.mAcctStatusCd = pAcctStatusCd;
    }
    /**
     * Retrieves the AcctStatusCd property.
     *
     * @return
     *  String containing the AcctStatusCd property.
     */
    public String getAcctStatusCd(){
        return mAcctStatusCd;
    }


    
}
