
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AccountUIView
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
 * <code>AccountUIView</code> is a ViewObject class for UI.
 */
public class AccountUIView
extends ValueObject
{
   
    private static final long serialVersionUID = 6922773314562679635L;
    private String mAccountDimIds;
    private BusEntityData mBusEntity;
    private AddressData mPrimaryAddress;
    private PropertyData mAccountType;

    /**
     * Constructor.
     */
    public AccountUIView ()
    {
        mAccountDimIds = "";
    }

    /**
     * Constructor. 
     */
    public AccountUIView(String parm1, BusEntityData parm2, AddressData parm3, PropertyData parm4)
    {
        mAccountDimIds = parm1;
        mBusEntity = parm2;
        mPrimaryAddress = parm3;
        mAccountType = parm4;
        
    }

    /**
     * Creates a new AccountUIView
     *
     * @return
     *  Newly initialized AccountUIView object.
     */
    public static AccountUIView createValue () 
    {
        AccountUIView valueView = new AccountUIView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AccountUIView object
     */
    public String toString()
    {
        return "[" + "AccountDimIds=" + mAccountDimIds + ", BusEntity=" + mBusEntity + ", PrimaryAddress=" + mPrimaryAddress + ", AccountType=" + mAccountType + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AccountUI");
	root.setAttribute("Id", String.valueOf(mAccountDimIds));

	Element node;

        node = doc.createElement("BusEntity");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntity)));
        root.appendChild(node);

        node = doc.createElement("PrimaryAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mPrimaryAddress)));
        root.appendChild(node);

        node = doc.createElement("AccountType");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountType)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AccountUIView copy()  {
      AccountUIView obj = new AccountUIView();
      obj.setAccountDimIds(mAccountDimIds);
      obj.setBusEntity(mBusEntity);
      obj.setPrimaryAddress(mPrimaryAddress);
      obj.setAccountType(mAccountType);
      
      return obj;
    }

    
    /**
     * Sets the AccountDimIds property.
     *
     * @param pAccountDimIds
     *  String to use to update the property.
     */
    public void setAccountDimIds(String pAccountDimIds){
        this.mAccountDimIds = pAccountDimIds;
    }
    /**
     * Retrieves the AccountDimIds property.
     *
     * @return
     *  String containing the AccountDimIds property.
     */
    public String getAccountDimIds(){
        return mAccountDimIds;
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
     * Sets the PrimaryAddress property.
     *
     * @param pPrimaryAddress
     *  AddressData to use to update the property.
     */
    public void setPrimaryAddress(AddressData pPrimaryAddress){
        this.mPrimaryAddress = pPrimaryAddress;
    }
    /**
     * Retrieves the PrimaryAddress property.
     *
     * @return
     *  AddressData containing the PrimaryAddress property.
     */
    public AddressData getPrimaryAddress(){
        return mPrimaryAddress;
    }


    /**
     * Sets the AccountType property.
     *
     * @param pAccountType
     *  PropertyData to use to update the property.
     */
    public void setAccountType(PropertyData pAccountType){
        this.mAccountType = pAccountType;
    }
    /**
     * Retrieves the AccountType property.
     *
     * @return
     *  PropertyData containing the AccountType property.
     */
    public PropertyData getAccountType(){
        return mAccountType;
    }


    
}
