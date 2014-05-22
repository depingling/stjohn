
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserJoinView
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
 * <code>UserJoinView</code> is a ViewObject class for UI.
 */
public class UserJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private UserData mUser;
    private PropertyDataVector mProperties;
    private AddressDataVector mAddresses;
    private UserAssocDataVector mUserAssoc;
    private PhoneDataVector mPhones;
    private EmailDataVector mEmails;

    /**
     * Constructor.
     */
    public UserJoinView ()
    {
    }

    /**
     * Constructor. 
     */
    public UserJoinView(UserData parm1, PropertyDataVector parm2, AddressDataVector parm3, UserAssocDataVector parm4, PhoneDataVector parm5, EmailDataVector parm6)
    {
        mUser = parm1;
        mProperties = parm2;
        mAddresses = parm3;
        mUserAssoc = parm4;
        mPhones = parm5;
        mEmails = parm6;
        
    }

    /**
     * Creates a new UserJoinView
     *
     * @return
     *  Newly initialized UserJoinView object.
     */
    public static UserJoinView createValue () 
    {
        UserJoinView valueView = new UserJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserJoinView object
     */
    public String toString()
    {
        return "[" + "User=" + mUser + ", Properties=" + mProperties + ", Addresses=" + mAddresses + ", UserAssoc=" + mUserAssoc + ", Phones=" + mPhones + ", Emails=" + mEmails + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UserJoin");
	root.setAttribute("Id", String.valueOf(mUser));

	Element node;

        node = doc.createElement("Properties");
        node.appendChild(doc.createTextNode(String.valueOf(mProperties)));
        root.appendChild(node);

        node = doc.createElement("Addresses");
        node.appendChild(doc.createTextNode(String.valueOf(mAddresses)));
        root.appendChild(node);

        node = doc.createElement("UserAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mUserAssoc)));
        root.appendChild(node);

        node = doc.createElement("Phones");
        node.appendChild(doc.createTextNode(String.valueOf(mPhones)));
        root.appendChild(node);

        node = doc.createElement("Emails");
        node.appendChild(doc.createTextNode(String.valueOf(mEmails)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UserJoinView copy()  {
      UserJoinView obj = new UserJoinView();
      obj.setUser(mUser);
      obj.setProperties(mProperties);
      obj.setAddresses(mAddresses);
      obj.setUserAssoc(mUserAssoc);
      obj.setPhones(mPhones);
      obj.setEmails(mEmails);
      
      return obj;
    }

    
    /**
     * Sets the User property.
     *
     * @param pUser
     *  UserData to use to update the property.
     */
    public void setUser(UserData pUser){
        this.mUser = pUser;
    }
    /**
     * Retrieves the User property.
     *
     * @return
     *  UserData containing the User property.
     */
    public UserData getUser(){
        return mUser;
    }


    /**
     * Sets the Properties property.
     *
     * @param pProperties
     *  PropertyDataVector to use to update the property.
     */
    public void setProperties(PropertyDataVector pProperties){
        this.mProperties = pProperties;
    }
    /**
     * Retrieves the Properties property.
     *
     * @return
     *  PropertyDataVector containing the Properties property.
     */
    public PropertyDataVector getProperties(){
        return mProperties;
    }


    /**
     * Sets the Addresses property.
     *
     * @param pAddresses
     *  AddressDataVector to use to update the property.
     */
    public void setAddresses(AddressDataVector pAddresses){
        this.mAddresses = pAddresses;
    }
    /**
     * Retrieves the Addresses property.
     *
     * @return
     *  AddressDataVector containing the Addresses property.
     */
    public AddressDataVector getAddresses(){
        return mAddresses;
    }


    /**
     * Sets the UserAssoc property.
     *
     * @param pUserAssoc
     *  UserAssocDataVector to use to update the property.
     */
    public void setUserAssoc(UserAssocDataVector pUserAssoc){
        this.mUserAssoc = pUserAssoc;
    }
    /**
     * Retrieves the UserAssoc property.
     *
     * @return
     *  UserAssocDataVector containing the UserAssoc property.
     */
    public UserAssocDataVector getUserAssoc(){
        return mUserAssoc;
    }


    /**
     * Sets the Phones property.
     *
     * @param pPhones
     *  PhoneDataVector to use to update the property.
     */
    public void setPhones(PhoneDataVector pPhones){
        this.mPhones = pPhones;
    }
    /**
     * Retrieves the Phones property.
     *
     * @return
     *  PhoneDataVector containing the Phones property.
     */
    public PhoneDataVector getPhones(){
        return mPhones;
    }


    /**
     * Sets the Emails property.
     *
     * @param pEmails
     *  EmailDataVector to use to update the property.
     */
    public void setEmails(EmailDataVector pEmails){
        this.mEmails = pEmails;
    }
    /**
     * Retrieves the Emails property.
     *
     * @return
     *  EmailDataVector containing the Emails property.
     */
    public EmailDataVector getEmails(){
        return mEmails;
    }


    
}
