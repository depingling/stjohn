
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContactView
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
 * <code>ContactView</code> is a ViewObject class for UI.
 */
public class ContactView
extends ValueObject
{
   
    private static final long serialVersionUID = -4441346962642091911L;
    private int mBusEntityId;
    private int mAddressId;
    private int mContactId;
    private String mContactTypeCd;
    private String mFirstName;
    private String mLastName;
    private String mPhone;
    private String mCellPhone;
    private String mFax;
    private String mEmail;

    /**
     * Constructor.
     */
    public ContactView ()
    {
        mContactTypeCd = "";
        mFirstName = "";
        mLastName = "";
        mPhone = "";
        mCellPhone = "";
        mFax = "";
        mEmail = "";
    }

    /**
     * Constructor. 
     */
    public ContactView(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10)
    {
        mBusEntityId = parm1;
        mAddressId = parm2;
        mContactId = parm3;
        mContactTypeCd = parm4;
        mFirstName = parm5;
        mLastName = parm6;
        mPhone = parm7;
        mCellPhone = parm8;
        mFax = parm9;
        mEmail = parm10;
        
    }

    /**
     * Creates a new ContactView
     *
     * @return
     *  Newly initialized ContactView object.
     */
    public static ContactView createValue () 
    {
        ContactView valueView = new ContactView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContactView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", AddressId=" + mAddressId + ", ContactId=" + mContactId + ", ContactTypeCd=" + mContactTypeCd + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", Phone=" + mPhone + ", CellPhone=" + mCellPhone + ", Fax=" + mFax + ", Email=" + mEmail + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Contact");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("AddressId");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressId)));
        root.appendChild(node);

        node = doc.createElement("ContactId");
        node.appendChild(doc.createTextNode(String.valueOf(mContactId)));
        root.appendChild(node);

        node = doc.createElement("ContactTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContactTypeCd)));
        root.appendChild(node);

        node = doc.createElement("FirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mFirstName)));
        root.appendChild(node);

        node = doc.createElement("LastName");
        node.appendChild(doc.createTextNode(String.valueOf(mLastName)));
        root.appendChild(node);

        node = doc.createElement("Phone");
        node.appendChild(doc.createTextNode(String.valueOf(mPhone)));
        root.appendChild(node);

        node = doc.createElement("CellPhone");
        node.appendChild(doc.createTextNode(String.valueOf(mCellPhone)));
        root.appendChild(node);

        node = doc.createElement("Fax");
        node.appendChild(doc.createTextNode(String.valueOf(mFax)));
        root.appendChild(node);

        node = doc.createElement("Email");
        node.appendChild(doc.createTextNode(String.valueOf(mEmail)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ContactView copy()  {
      ContactView obj = new ContactView();
      obj.setBusEntityId(mBusEntityId);
      obj.setAddressId(mAddressId);
      obj.setContactId(mContactId);
      obj.setContactTypeCd(mContactTypeCd);
      obj.setFirstName(mFirstName);
      obj.setLastName(mLastName);
      obj.setPhone(mPhone);
      obj.setCellPhone(mCellPhone);
      obj.setFax(mFax);
      obj.setEmail(mEmail);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the AddressId property.
     *
     * @param pAddressId
     *  int to use to update the property.
     */
    public void setAddressId(int pAddressId){
        this.mAddressId = pAddressId;
    }
    /**
     * Retrieves the AddressId property.
     *
     * @return
     *  int containing the AddressId property.
     */
    public int getAddressId(){
        return mAddressId;
    }


    /**
     * Sets the ContactId property.
     *
     * @param pContactId
     *  int to use to update the property.
     */
    public void setContactId(int pContactId){
        this.mContactId = pContactId;
    }
    /**
     * Retrieves the ContactId property.
     *
     * @return
     *  int containing the ContactId property.
     */
    public int getContactId(){
        return mContactId;
    }


    /**
     * Sets the ContactTypeCd property.
     *
     * @param pContactTypeCd
     *  String to use to update the property.
     */
    public void setContactTypeCd(String pContactTypeCd){
        this.mContactTypeCd = pContactTypeCd;
    }
    /**
     * Retrieves the ContactTypeCd property.
     *
     * @return
     *  String containing the ContactTypeCd property.
     */
    public String getContactTypeCd(){
        return mContactTypeCd;
    }


    /**
     * Sets the FirstName property.
     *
     * @param pFirstName
     *  String to use to update the property.
     */
    public void setFirstName(String pFirstName){
        this.mFirstName = pFirstName;
    }
    /**
     * Retrieves the FirstName property.
     *
     * @return
     *  String containing the FirstName property.
     */
    public String getFirstName(){
        return mFirstName;
    }


    /**
     * Sets the LastName property.
     *
     * @param pLastName
     *  String to use to update the property.
     */
    public void setLastName(String pLastName){
        this.mLastName = pLastName;
    }
    /**
     * Retrieves the LastName property.
     *
     * @return
     *  String containing the LastName property.
     */
    public String getLastName(){
        return mLastName;
    }


    /**
     * Sets the Phone property.
     *
     * @param pPhone
     *  String to use to update the property.
     */
    public void setPhone(String pPhone){
        this.mPhone = pPhone;
    }
    /**
     * Retrieves the Phone property.
     *
     * @return
     *  String containing the Phone property.
     */
    public String getPhone(){
        return mPhone;
    }


    /**
     * Sets the CellPhone property.
     *
     * @param pCellPhone
     *  String to use to update the property.
     */
    public void setCellPhone(String pCellPhone){
        this.mCellPhone = pCellPhone;
    }
    /**
     * Retrieves the CellPhone property.
     *
     * @return
     *  String containing the CellPhone property.
     */
    public String getCellPhone(){
        return mCellPhone;
    }


    /**
     * Sets the Fax property.
     *
     * @param pFax
     *  String to use to update the property.
     */
    public void setFax(String pFax){
        this.mFax = pFax;
    }
    /**
     * Retrieves the Fax property.
     *
     * @return
     *  String containing the Fax property.
     */
    public String getFax(){
        return mFax;
    }


    /**
     * Sets the Email property.
     *
     * @param pEmail
     *  String to use to update the property.
     */
    public void setEmail(String pEmail){
        this.mEmail = pEmail;
    }
    /**
     * Retrieves the Email property.
     *
     * @return
     *  String containing the Email property.
     */
    public String getEmail(){
        return mEmail;
    }


    
}
