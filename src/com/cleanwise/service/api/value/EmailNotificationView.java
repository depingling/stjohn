
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EmailNotificationView
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
 * <code>EmailNotificationView</code> is a ViewObject class for UI.
 */
public class EmailNotificationView
extends ValueObject
{
   
   public interface TYPE_CD {
   		public static final String PENDING_ORDER_NOTIFICATION="PENDING_ORDER_NOTIFICATION";
   }
   
    private static final long serialVersionUID = -1L;
    private EmailView mEmail;
    private String mType;

    /**
     * Constructor.
     */
    public EmailNotificationView ()
    {
        mType = "";
    }

    /**
     * Constructor. 
     */
    public EmailNotificationView(EmailView parm1, String parm2)
    {
        mEmail = parm1;
        mType = parm2;
        
    }

    /**
     * Creates a new EmailNotificationView
     *
     * @return
     *  Newly initialized EmailNotificationView object.
     */
    public static EmailNotificationView createValue () 
    {
        EmailNotificationView valueView = new EmailNotificationView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EmailNotificationView object
     */
    public String toString()
    {
        return "[" + "Email=" + mEmail + ", Type=" + mType + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EmailNotification");
	root.setAttribute("Id", String.valueOf(mEmail));

	Element node;

        node = doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EmailNotificationView copy()  {
      EmailNotificationView obj = new EmailNotificationView();
      obj.setEmail(mEmail);
      obj.setType(mType);
      
      return obj;
    }

    
    /**
     * Sets the Email property.
     *
     * @param pEmail
     *  EmailView to use to update the property.
     */
    public void setEmail(EmailView pEmail){
        this.mEmail = pEmail;
    }
    /**
     * Retrieves the Email property.
     *
     * @return
     *  EmailView containing the Email property.
     */
    public EmailView getEmail(){
        return mEmail;
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


    
}
