
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ServiceTicketOrderRequestView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 * @version      1.0
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>ServiceTicketOrderRequestView</code> is a ViewObject class for UI.
 */
public class ServiceTicketOrderRequestView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private IdVector mServiceTickets;

    /**
     * Constructor.
     */
    public ServiceTicketOrderRequestView ()
    {
    }

    /**
     * Constructor. 
     */
    public ServiceTicketOrderRequestView(IdVector parm1)
    {
        mServiceTickets = parm1;
        
    }

    /**
     * Creates a new ServiceTicketOrderRequestView
     *
     * @return
     *  Newly initialized ServiceTicketOrderRequestView object.
     */
    public static ServiceTicketOrderRequestView createValue () 
    {
        ServiceTicketOrderRequestView valueView = new ServiceTicketOrderRequestView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ServiceTicketOrderRequestView object
     */
    public String toString()
    {
        return "[" + "ServiceTickets=" + mServiceTickets + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ServiceTicketOrderRequest");
	root.setAttribute("Id", String.valueOf(mServiceTickets));

	Element node;

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ServiceTicketOrderRequestView copy()  {
      ServiceTicketOrderRequestView obj = new ServiceTicketOrderRequestView();
      obj.setServiceTickets(mServiceTickets);
      
      return obj;
    }

    
    /**
     * Sets the ServiceTickets property.
     *
     * @param pServiceTickets
     *  IdVector to use to update the property.
     */
    public void setServiceTickets(IdVector pServiceTickets){
        this.mServiceTickets = pServiceTickets;
    }
    /**
     * Retrieves the ServiceTickets property.
     *
     * @return
     *  IdVector containing the ServiceTickets property.
     */
    public IdVector getServiceTickets(){
        return mServiceTickets;
    }


    
}
