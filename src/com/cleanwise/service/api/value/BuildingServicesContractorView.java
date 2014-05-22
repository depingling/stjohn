
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BuildingServicesContractorView
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
 * <code>BuildingServicesContractorView</code> is a ViewObject class for UI.
 */
public class BuildingServicesContractorView
extends ValueObject
{
   
    private static final long serialVersionUID = -7510745525249468088L;
    private BusEntityData mBusEntityData;
    private PhoneData mFaxNumber;

    /**
     * Constructor.
     */
    public BuildingServicesContractorView ()
    {
    }

    /**
     * Constructor. 
     */
    public BuildingServicesContractorView(BusEntityData parm1, PhoneData parm2)
    {
        mBusEntityData = parm1;
        mFaxNumber = parm2;
        
    }

    /**
     * Creates a new BuildingServicesContractorView
     *
     * @return
     *  Newly initialized BuildingServicesContractorView object.
     */
    public static BuildingServicesContractorView createValue () 
    {
        BuildingServicesContractorView valueView = new BuildingServicesContractorView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BuildingServicesContractorView object
     */
    public String toString()
    {
        return "[" + "BusEntityData=" + mBusEntityData + ", FaxNumber=" + mFaxNumber + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BuildingServicesContractor");
	root.setAttribute("Id", String.valueOf(mBusEntityData));

	Element node;

        node = doc.createElement("FaxNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mFaxNumber)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BuildingServicesContractorView copy()  {
      BuildingServicesContractorView obj = new BuildingServicesContractorView();
      obj.setBusEntityData(mBusEntityData);
      obj.setFaxNumber(mFaxNumber);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityData property.
     *
     * @param pBusEntityData
     *  BusEntityData to use to update the property.
     */
    public void setBusEntityData(BusEntityData pBusEntityData){
        this.mBusEntityData = pBusEntityData;
    }
    /**
     * Retrieves the BusEntityData property.
     *
     * @return
     *  BusEntityData containing the BusEntityData property.
     */
    public BusEntityData getBusEntityData(){
        return mBusEntityData;
    }


    /**
     * Sets the FaxNumber property.
     *
     * @param pFaxNumber
     *  PhoneData to use to update the property.
     */
    public void setFaxNumber(PhoneData pFaxNumber){
        this.mFaxNumber = pFaxNumber;
    }
    /**
     * Retrieves the FaxNumber property.
     *
     * @return
     *  PhoneData containing the FaxNumber property.
     */
    public PhoneData getFaxNumber(){
        return mFaxNumber;
    }


    
}
