
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EventEmailView
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
 * <code>EventEmailView</code> is a ViewObject class for UI.
 */
public class EventEmailView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private EventData mEventData;
    private EventEmailData mEmailProperty;

    /**
     * Constructor.
     */
    public EventEmailView ()
    {
    }

    /**
     * Constructor. 
     */
    public EventEmailView(EventData parm1, EventEmailData parm2)
    {
        mEventData = parm1;
        mEmailProperty = parm2;
        
    }

    /**
     * Creates a new EventEmailView
     *
     * @return
     *  Newly initialized EventEmailView object.
     */
    public static EventEmailView createValue () 
    {
        EventEmailView valueView = new EventEmailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EventEmailView object
     */
    public String toString()
    {
        return "[" + "EventData=" + mEventData + ", EmailProperty=" + mEmailProperty + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EventEmail");
	root.setAttribute("Id", String.valueOf(mEventData));

	Element node;

        node = doc.createElement("EmailProperty");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailProperty)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EventEmailView copy()  {
      EventEmailView obj = new EventEmailView();
      obj.setEventData(mEventData);
      obj.setEmailProperty(mEmailProperty);
      
      return obj;
    }

    
    /**
     * Sets the EventData property.
     *
     * @param pEventData
     *  EventData to use to update the property.
     */
    public void setEventData(EventData pEventData){
        this.mEventData = pEventData;
    }
    /**
     * Retrieves the EventData property.
     *
     * @return
     *  EventData containing the EventData property.
     */
    public EventData getEventData(){
        return mEventData;
    }


    /**
     * Sets the EmailProperty property.
     *
     * @param pEmailProperty
     *  EventEmailData to use to update the property.
     */
    public void setEmailProperty(EventEmailData pEmailProperty){
        this.mEmailProperty = pEmailProperty;
    }
    /**
     * Retrieves the EmailProperty property.
     *
     * @return
     *  EventEmailData containing the EmailProperty property.
     */
    public EventEmailData getEmailProperty(){
        return mEmailProperty;
    }


    
}
