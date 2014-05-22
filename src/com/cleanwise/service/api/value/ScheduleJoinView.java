
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ScheduleJoinView
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
 * <code>ScheduleJoinView</code> is a ViewObject class for UI.
 */
public class ScheduleJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = 3611782106511337283L;
    private ScheduleData mSchedule;
    private ScheduleDetailDataVector mScheduleDetail;

    /**
     * Constructor.
     */
    public ScheduleJoinView ()
    {
    }

    /**
     * Constructor. 
     */
    public ScheduleJoinView(ScheduleData parm1, ScheduleDetailDataVector parm2)
    {
        mSchedule = parm1;
        mScheduleDetail = parm2;
        
    }

    /**
     * Creates a new ScheduleJoinView
     *
     * @return
     *  Newly initialized ScheduleJoinView object.
     */
    public static ScheduleJoinView createValue () 
    {
        ScheduleJoinView valueView = new ScheduleJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ScheduleJoinView object
     */
    public String toString()
    {
        return "[" + "Schedule=" + mSchedule + ", ScheduleDetail=" + mScheduleDetail + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ScheduleJoin");
	root.setAttribute("Id", String.valueOf(mSchedule));

	Element node;

        node = doc.createElement("ScheduleDetail");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleDetail)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ScheduleJoinView copy()  {
      ScheduleJoinView obj = new ScheduleJoinView();
      obj.setSchedule(mSchedule);
      obj.setScheduleDetail(mScheduleDetail);
      
      return obj;
    }

    
    /**
     * Sets the Schedule property.
     *
     * @param pSchedule
     *  ScheduleData to use to update the property.
     */
    public void setSchedule(ScheduleData pSchedule){
        this.mSchedule = pSchedule;
    }
    /**
     * Retrieves the Schedule property.
     *
     * @return
     *  ScheduleData containing the Schedule property.
     */
    public ScheduleData getSchedule(){
        return mSchedule;
    }


    /**
     * Sets the ScheduleDetail property.
     *
     * @param pScheduleDetail
     *  ScheduleDetailDataVector to use to update the property.
     */
    public void setScheduleDetail(ScheduleDetailDataVector pScheduleDetail){
        this.mScheduleDetail = pScheduleDetail;
    }
    /**
     * Retrieves the ScheduleDetail property.
     *
     * @return
     *  ScheduleDetailDataVector containing the ScheduleDetail property.
     */
    public ScheduleDetailDataVector getScheduleDetail(){
        return mScheduleDetail;
    }


    
}
