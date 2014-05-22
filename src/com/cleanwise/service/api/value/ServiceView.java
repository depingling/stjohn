
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ServiceView
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
 * <code>ServiceView</code> is a ViewObject class for UI.
 */
public class ServiceView
extends ValueObject
{
   
    private static final long serialVersionUID = -3198997160706373450L;
    private int mServiceId;
    private String mServiceName;
    private String mStatusCd;

    /**
     * Constructor.
     */
    public ServiceView ()
    {
        mServiceName = "";
        mStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public ServiceView(int parm1, String parm2, String parm3)
    {
        mServiceId = parm1;
        mServiceName = parm2;
        mStatusCd = parm3;
        
    }

    /**
     * Creates a new ServiceView
     *
     * @return
     *  Newly initialized ServiceView object.
     */
    public static ServiceView createValue () 
    {
        ServiceView valueView = new ServiceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ServiceView object
     */
    public String toString()
    {
        return "[" + "ServiceId=" + mServiceId + ", ServiceName=" + mServiceName + ", StatusCd=" + mStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Service");
	root.setAttribute("Id", String.valueOf(mServiceId));

	Element node;

        node = doc.createElement("ServiceName");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceName)));
        root.appendChild(node);

        node = doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ServiceView copy()  {
      ServiceView obj = new ServiceView();
      obj.setServiceId(mServiceId);
      obj.setServiceName(mServiceName);
      obj.setStatusCd(mStatusCd);
      
      return obj;
    }

    
    /**
     * Sets the ServiceId property.
     *
     * @param pServiceId
     *  int to use to update the property.
     */
    public void setServiceId(int pServiceId){
        this.mServiceId = pServiceId;
    }
    /**
     * Retrieves the ServiceId property.
     *
     * @return
     *  int containing the ServiceId property.
     */
    public int getServiceId(){
        return mServiceId;
    }


    /**
     * Sets the ServiceName property.
     *
     * @param pServiceName
     *  String to use to update the property.
     */
    public void setServiceName(String pServiceName){
        this.mServiceName = pServiceName;
    }
    /**
     * Retrieves the ServiceName property.
     *
     * @return
     *  String containing the ServiceName property.
     */
    public String getServiceName(){
        return mServiceName;
    }


    /**
     * Sets the StatusCd property.
     *
     * @param pStatusCd
     *  String to use to update the property.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
    }
    /**
     * Retrieves the StatusCd property.
     *
     * @return
     *  String containing the StatusCd property.
     */
    public String getStatusCd(){
        return mStatusCd;
    }


    
}
