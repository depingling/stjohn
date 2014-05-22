
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EstimatorFacilityJoinView
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
 * <code>EstimatorFacilityJoinView</code> is a ViewObject class for UI.
 */
public class EstimatorFacilityJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = -2701281377357269748L;
    private EstimatorFacilityData mEstimatorFacility;
    private FacilityFloorDataVector mFacilityFloors;
    private FacilityPropertyDataVector mFacilityProperties;

    /**
     * Constructor.
     */
    public EstimatorFacilityJoinView ()
    {
    }

    /**
     * Constructor. 
     */
    public EstimatorFacilityJoinView(EstimatorFacilityData parm1, FacilityFloorDataVector parm2, FacilityPropertyDataVector parm3)
    {
        mEstimatorFacility = parm1;
        mFacilityFloors = parm2;
        mFacilityProperties = parm3;
        
    }

    /**
     * Creates a new EstimatorFacilityJoinView
     *
     * @return
     *  Newly initialized EstimatorFacilityJoinView object.
     */
    public static EstimatorFacilityJoinView createValue () 
    {
        EstimatorFacilityJoinView valueView = new EstimatorFacilityJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EstimatorFacilityJoinView object
     */
    public String toString()
    {
        return "[" + "EstimatorFacility=" + mEstimatorFacility + ", FacilityFloors=" + mFacilityFloors + ", FacilityProperties=" + mFacilityProperties + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EstimatorFacilityJoin");
	root.setAttribute("Id", String.valueOf(mEstimatorFacility));

	Element node;

        node = doc.createElement("FacilityFloors");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityFloors)));
        root.appendChild(node);

        node = doc.createElement("FacilityProperties");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityProperties)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EstimatorFacilityJoinView copy()  {
      EstimatorFacilityJoinView obj = new EstimatorFacilityJoinView();
      obj.setEstimatorFacility(mEstimatorFacility);
      obj.setFacilityFloors(mFacilityFloors);
      obj.setFacilityProperties(mFacilityProperties);
      
      return obj;
    }

    
    /**
     * Sets the EstimatorFacility property.
     *
     * @param pEstimatorFacility
     *  EstimatorFacilityData to use to update the property.
     */
    public void setEstimatorFacility(EstimatorFacilityData pEstimatorFacility){
        this.mEstimatorFacility = pEstimatorFacility;
    }
    /**
     * Retrieves the EstimatorFacility property.
     *
     * @return
     *  EstimatorFacilityData containing the EstimatorFacility property.
     */
    public EstimatorFacilityData getEstimatorFacility(){
        return mEstimatorFacility;
    }


    /**
     * Sets the FacilityFloors property.
     *
     * @param pFacilityFloors
     *  FacilityFloorDataVector to use to update the property.
     */
    public void setFacilityFloors(FacilityFloorDataVector pFacilityFloors){
        this.mFacilityFloors = pFacilityFloors;
    }
    /**
     * Retrieves the FacilityFloors property.
     *
     * @return
     *  FacilityFloorDataVector containing the FacilityFloors property.
     */
    public FacilityFloorDataVector getFacilityFloors(){
        return mFacilityFloors;
    }


    /**
     * Sets the FacilityProperties property.
     *
     * @param pFacilityProperties
     *  FacilityPropertyDataVector to use to update the property.
     */
    public void setFacilityProperties(FacilityPropertyDataVector pFacilityProperties){
        this.mFacilityProperties = pFacilityProperties;
    }
    /**
     * Retrieves the FacilityProperties property.
     *
     * @return
     *  FacilityPropertyDataVector containing the FacilityProperties property.
     */
    public FacilityPropertyDataVector getFacilityProperties(){
        return mFacilityProperties;
    }


    
}
