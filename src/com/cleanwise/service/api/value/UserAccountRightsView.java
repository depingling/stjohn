
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserAccountRightsView
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

import com.cleanwise.service.api.value.*;


/**
 * <code>UserAccountRightsView</code> is a ViewObject class for UI.
 */
public class UserAccountRightsView
extends ValueObject
{
   
    private static final long serialVersionUID = -1173177252708577612L;
    private BusEntityData mAccountData;
    private PropertyData mUserSettings;

    /**
     * Constructor.
     */
    public UserAccountRightsView ()
    {
    }

    /**
     * Constructor. 
     */
    public UserAccountRightsView(BusEntityData parm1, PropertyData parm2)
    {
        mAccountData = parm1;
        mUserSettings = parm2;
        
    }

    /**
     * Creates a new UserAccountRightsView
     *
     * @return
     *  Newly initialized UserAccountRightsView object.
     */
    public static UserAccountRightsView createValue () 
    {
        UserAccountRightsView valueView = new UserAccountRightsView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserAccountRightsView object
     */
    public String toString()
    {
        return "[" + "AccountData=" + mAccountData + ", UserSettings=" + mUserSettings + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UserAccountRights");
	root.setAttribute("Id", String.valueOf(mAccountData));

	Element node;

        node = doc.createElement("UserSettings");
        node.appendChild(doc.createTextNode(String.valueOf(mUserSettings)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UserAccountRightsView copy()  {
      UserAccountRightsView obj = new UserAccountRightsView();
      obj.setAccountData(mAccountData);
      obj.setUserSettings(mUserSettings);
      
      return obj;
    }

    
    /**
     * Sets the AccountData property.
     *
     * @param pAccountData
     *  BusEntityData to use to update the property.
     */
    public void setAccountData(BusEntityData pAccountData){
        this.mAccountData = pAccountData;
    }
    /**
     * Retrieves the AccountData property.
     *
     * @return
     *  BusEntityData containing the AccountData property.
     */
    public BusEntityData getAccountData(){
        return mAccountData;
    }


    /**
     * Sets the UserSettings property.
     *
     * @param pUserSettings
     *  PropertyData to use to update the property.
     */
    public void setUserSettings(PropertyData pUserSettings){
        this.mUserSettings = pUserSettings;
    }
    /**
     * Retrieves the UserSettings property.
     *
     * @return
     *  PropertyData containing the UserSettings property.
     */
    public PropertyData getUserSettings(){
        return mUserSettings;
    }


    
}
