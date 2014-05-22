
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EventProcessView
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
 * <code>EventProcessView</code> is a ViewObject class for UI.
 */
public class EventProcessView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private EventData mEventData;
    private EventPropertyDataVector mProperties;
    private int mParentEventId;

    /**
     * Constructor.
     */
    public EventProcessView ()
    {
    }

    /**
     * Constructor. 
     */
    public EventProcessView(EventData parm1, EventPropertyDataVector parm2, int parm3)
    {
        mEventData = parm1;
        mProperties = parm2;
        mParentEventId = parm3;
        
    }

    /**
     * Creates a new EventProcessView
     *
     * @return
     *  Newly initialized EventProcessView object.
     */
    public static EventProcessView createValue () 
    {
        EventProcessView valueView = new EventProcessView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EventProcessView object
     */
    public String toString()
    {
        return "[" + "EventData=" + mEventData + ", Properties=" + mProperties + ", ParentEventId=" + mParentEventId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EventProcess");
	root.setAttribute("Id", String.valueOf(mEventData));

	Element node;

        node = doc.createElement("Properties");
        node.appendChild(doc.createTextNode(String.valueOf(mProperties)));
        root.appendChild(node);

        node = doc.createElement("ParentEventId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentEventId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EventProcessView copy()  {
      EventProcessView obj = new EventProcessView();
      obj.setEventData(mEventData);
      obj.setProperties(mProperties);
      obj.setParentEventId(mParentEventId);
      
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
     * Sets the Properties property.
     *
     * @param pProperties
     *  EventPropertyDataVector to use to update the property.
     */
    public void setProperties(EventPropertyDataVector pProperties){
        this.mProperties = pProperties;
    }
    /**
     * Retrieves the Properties property.
     *
     * @return
     *  EventPropertyDataVector containing the Properties property.
     */
    public EventPropertyDataVector getProperties(){
        return mProperties;
    }


    /**
     * Sets the ParentEventId property.
     *
     * @param pParentEventId
     *  int to use to update the property.
     */
    public void setParentEventId(int pParentEventId){
        this.mParentEventId = pParentEventId;
    }
    /**
     * Retrieves the ParentEventId property.
     *
     * @return
     *  int containing the ParentEventId property.
     */
    public int getParentEventId(){
        return mParentEventId;
    }


    
}
