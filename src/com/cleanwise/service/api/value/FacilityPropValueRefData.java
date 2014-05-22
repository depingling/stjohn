
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FacilityPropValueRefData
 * Description:  This is a ValueObject class wrapping the database table CLW_FACILITY_PROP_VALUE_REF.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;
/**
 * <code>FacilityPropValueRefData</code> is a ValueObject class wrapping of the database table CLW_FACILITY_PROP_VALUE_REF.
 */
public class FacilityPropValueRefData extends ValueObject implements Cloneable
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 5044663663257706448L;
    
    private int mFacilityPropRefId;// SQL type:NUMBER, not null
    private int mFacilityPropValueRefId;// SQL type:NUMBER, not null
    private String mValue;// SQL type:VARCHAR2
    private String mJavaClass;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    private FacilityPropValueRefData ()
    {
        mValue = "";
        mJavaClass = "";
    }

    /**
     * Constructor.
     */
    public FacilityPropValueRefData(int parm1, int parm2, String parm3, String parm4)
    {
        mFacilityPropRefId = parm1;
        mFacilityPropValueRefId = parm2;
        mValue = parm3;
        mJavaClass = parm4;
        
    }

    /**
     * Creates a new FacilityPropValueRefData
     *
     * @return
     *  Newly initialized FacilityPropValueRefData object.
     */
    public static FacilityPropValueRefData createValue ()
    {
        FacilityPropValueRefData valueData = new FacilityPropValueRefData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FacilityPropValueRefData object
     */
    public String toString()
    {
        return "[" + "FacilityPropRefId=" + mFacilityPropRefId + ", FacilityPropValueRefId=" + mFacilityPropValueRefId + ", Value=" + mValue + ", JavaClass=" + mJavaClass + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FacilityPropValueRef");
        
        Element node;

        node = doc.createElement("FacilityPropRefId");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityPropRefId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mFacilityPropValueRefId));

        node = doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node = doc.createElement("JavaClass");
        node.appendChild(doc.createTextNode(String.valueOf(mJavaClass)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FacilityPropValueRefId field is not cloned.
    *
    * @return FacilityPropValueRefData object
    */
    public Object clone(){
        FacilityPropValueRefData myClone = new FacilityPropValueRefData();
        
        myClone.mFacilityPropRefId = mFacilityPropRefId;
        
        myClone.mValue = mValue;
        
        myClone.mJavaClass = mJavaClass;
        
        return myClone;
    }

    
    /**
     * Sets the FacilityPropRefId field. This field is required to be set in the database.
     *
     * @param pFacilityPropRefId
     *  int to use to update the field.
     */
    public void setFacilityPropRefId(int pFacilityPropRefId){
        this.mFacilityPropRefId = pFacilityPropRefId;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityPropRefId field.
     *
     * @return
     *  int containing the FacilityPropRefId field.
     */
    public int getFacilityPropRefId(){
        return mFacilityPropRefId;
    }

    /**
     * Sets the FacilityPropValueRefId field. This field is required to be set in the database.
     *
     * @param pFacilityPropValueRefId
     *  int to use to update the field.
     */
    public void setFacilityPropValueRefId(int pFacilityPropValueRefId){
        this.mFacilityPropValueRefId = pFacilityPropValueRefId;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityPropValueRefId field.
     *
     * @return
     *  int containing the FacilityPropValueRefId field.
     */
    public int getFacilityPropValueRefId(){
        return mFacilityPropValueRefId;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
    }

    /**
     * Sets the JavaClass field.
     *
     * @param pJavaClass
     *  String to use to update the field.
     */
    public void setJavaClass(String pJavaClass){
        this.mJavaClass = pJavaClass;
        setDirty(true);
    }
    /**
     * Retrieves the JavaClass field.
     *
     * @return
     *  String containing the JavaClass field.
     */
    public String getJavaClass(){
        return mJavaClass;
    }


}
