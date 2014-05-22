
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CleaningScheduleJoinView
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
 * <code>CleaningScheduleJoinView</code> is a ViewObject class for UI.
 */
public class CleaningScheduleJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = -4121722498695094966L;
    private CleaningScheduleData mSchedule;
    private CleaningSchedStructJoinViewVector mStructure;
    private CleaningSchedFilterDataVector mFilter;
    private String mFrequency;
    private String mTimePeriodCd;
    private String mFloorMachine;

    /**
     * Constructor.
     */
    public CleaningScheduleJoinView ()
    {
        mFrequency = "";
        mTimePeriodCd = "";
        mFloorMachine = "";
    }

    /**
     * Constructor. 
     */
    public CleaningScheduleJoinView(CleaningScheduleData parm1, CleaningSchedStructJoinViewVector parm2, CleaningSchedFilterDataVector parm3, String parm4, String parm5, String parm6)
    {
        mSchedule = parm1;
        mStructure = parm2;
        mFilter = parm3;
        mFrequency = parm4;
        mTimePeriodCd = parm5;
        mFloorMachine = parm6;
        
    }

    /**
     * Creates a new CleaningScheduleJoinView
     *
     * @return
     *  Newly initialized CleaningScheduleJoinView object.
     */
    public static CleaningScheduleJoinView createValue () 
    {
        CleaningScheduleJoinView valueView = new CleaningScheduleJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CleaningScheduleJoinView object
     */
    public String toString()
    {
        return "[" + "Schedule=" + mSchedule + ", Structure=" + mStructure + ", Filter=" + mFilter + ", Frequency=" + mFrequency + ", TimePeriodCd=" + mTimePeriodCd + ", FloorMachine=" + mFloorMachine + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CleaningScheduleJoin");
	root.setAttribute("Id", String.valueOf(mSchedule));

	Element node;

        node = doc.createElement("Structure");
        node.appendChild(doc.createTextNode(String.valueOf(mStructure)));
        root.appendChild(node);

        node = doc.createElement("Filter");
        node.appendChild(doc.createTextNode(String.valueOf(mFilter)));
        root.appendChild(node);

        node = doc.createElement("Frequency");
        node.appendChild(doc.createTextNode(String.valueOf(mFrequency)));
        root.appendChild(node);

        node = doc.createElement("TimePeriodCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTimePeriodCd)));
        root.appendChild(node);

        node = doc.createElement("FloorMachine");
        node.appendChild(doc.createTextNode(String.valueOf(mFloorMachine)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CleaningScheduleJoinView copy()  {
      CleaningScheduleJoinView obj = new CleaningScheduleJoinView();
      obj.setSchedule(mSchedule);
      obj.setStructure(mStructure);
      obj.setFilter(mFilter);
      obj.setFrequency(mFrequency);
      obj.setTimePeriodCd(mTimePeriodCd);
      obj.setFloorMachine(mFloorMachine);
      
      return obj;
    }

    
    /**
     * Sets the Schedule property.
     *
     * @param pSchedule
     *  CleaningScheduleData to use to update the property.
     */
    public void setSchedule(CleaningScheduleData pSchedule){
        this.mSchedule = pSchedule;
    }
    /**
     * Retrieves the Schedule property.
     *
     * @return
     *  CleaningScheduleData containing the Schedule property.
     */
    public CleaningScheduleData getSchedule(){
        return mSchedule;
    }


    /**
     * Sets the Structure property.
     *
     * @param pStructure
     *  CleaningSchedStructJoinViewVector to use to update the property.
     */
    public void setStructure(CleaningSchedStructJoinViewVector pStructure){
        this.mStructure = pStructure;
    }
    /**
     * Retrieves the Structure property.
     *
     * @return
     *  CleaningSchedStructJoinViewVector containing the Structure property.
     */
    public CleaningSchedStructJoinViewVector getStructure(){
        return mStructure;
    }


    /**
     * Sets the Filter property.
     *
     * @param pFilter
     *  CleaningSchedFilterDataVector to use to update the property.
     */
    public void setFilter(CleaningSchedFilterDataVector pFilter){
        this.mFilter = pFilter;
    }
    /**
     * Retrieves the Filter property.
     *
     * @return
     *  CleaningSchedFilterDataVector containing the Filter property.
     */
    public CleaningSchedFilterDataVector getFilter(){
        return mFilter;
    }


    /**
     * Sets the Frequency property.
     *
     * @param pFrequency
     *  String to use to update the property.
     */
    public void setFrequency(String pFrequency){
        this.mFrequency = pFrequency;
    }
    /**
     * Retrieves the Frequency property.
     *
     * @return
     *  String containing the Frequency property.
     */
    public String getFrequency(){
        return mFrequency;
    }


    /**
     * Sets the TimePeriodCd property.
     *
     * @param pTimePeriodCd
     *  String to use to update the property.
     */
    public void setTimePeriodCd(String pTimePeriodCd){
        this.mTimePeriodCd = pTimePeriodCd;
    }
    /**
     * Retrieves the TimePeriodCd property.
     *
     * @return
     *  String containing the TimePeriodCd property.
     */
    public String getTimePeriodCd(){
        return mTimePeriodCd;
    }


    /**
     * Sets the FloorMachine property.
     *
     * @param pFloorMachine
     *  String to use to update the property.
     */
    public void setFloorMachine(String pFloorMachine){
        this.mFloorMachine = pFloorMachine;
    }
    /**
     * Retrieves the FloorMachine property.
     *
     * @return
     *  String containing the FloorMachine property.
     */
    public String getFloorMachine(){
        return mFloorMachine;
    }


    
}
