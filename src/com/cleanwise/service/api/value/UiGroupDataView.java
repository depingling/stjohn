
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiGroupDataView
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
 * <code>UiGroupDataView</code> is a ViewObject class for UI.
 */
public class UiGroupDataView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private GroupData mGroupData;
    private BusEntityDataVector mGroupAssociations;
    private UserDataVector mUsers;

    /**
     * Constructor.
     */
    public UiGroupDataView ()
    {
    }

    /**
     * Constructor. 
     */
    public UiGroupDataView(GroupData parm1, BusEntityDataVector parm2, UserDataVector parm3)
    {
        mGroupData = parm1;
        mGroupAssociations = parm2;
        mUsers = parm3;
        
    }

    /**
     * Creates a new UiGroupDataView
     *
     * @return
     *  Newly initialized UiGroupDataView object.
     */
    public static UiGroupDataView createValue () 
    {
        UiGroupDataView valueView = new UiGroupDataView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiGroupDataView object
     */
    public String toString()
    {
        return "[" + "GroupData=" + mGroupData + ", GroupAssociations=" + mGroupAssociations + ", Users=" + mUsers + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UiGroupData");
	root.setAttribute("Id", String.valueOf(mGroupData));

	Element node;

        node = doc.createElement("GroupAssociations");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupAssociations)));
        root.appendChild(node);

        node = doc.createElement("Users");
        node.appendChild(doc.createTextNode(String.valueOf(mUsers)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UiGroupDataView copy()  {
      UiGroupDataView obj = new UiGroupDataView();
      obj.setGroupData(mGroupData);
      obj.setGroupAssociations(mGroupAssociations);
      obj.setUsers(mUsers);
      
      return obj;
    }

    
    /**
     * Sets the GroupData property.
     *
     * @param pGroupData
     *  GroupData to use to update the property.
     */
    public void setGroupData(GroupData pGroupData){
        this.mGroupData = pGroupData;
    }
    /**
     * Retrieves the GroupData property.
     *
     * @return
     *  GroupData containing the GroupData property.
     */
    public GroupData getGroupData(){
        return mGroupData;
    }


    /**
     * Sets the GroupAssociations property.
     *
     * @param pGroupAssociations
     *  BusEntityDataVector to use to update the property.
     */
    public void setGroupAssociations(BusEntityDataVector pGroupAssociations){
        this.mGroupAssociations = pGroupAssociations;
    }
    /**
     * Retrieves the GroupAssociations property.
     *
     * @return
     *  BusEntityDataVector containing the GroupAssociations property.
     */
    public BusEntityDataVector getGroupAssociations(){
        return mGroupAssociations;
    }


    /**
     * Sets the Users property.
     *
     * @param pUsers
     *  UserDataVector to use to update the property.
     */
    public void setUsers(UserDataVector pUsers){
        this.mUsers = pUsers;
    }
    /**
     * Retrieves the Users property.
     *
     * @return
     *  UserDataVector containing the Users property.
     */
    public UserDataVector getUsers(){
        return mUsers;
    }


    
}
