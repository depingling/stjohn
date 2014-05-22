
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FacilityPropRefData
 * Description:  This is a ValueObject class wrapping the database table CLW_FACILITY_PROP_REF.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import org.w3c.dom.*;

/**
 * <code>FacilityPropRefData</code> is a ValueObject class wrapping of the database table CLW_FACILITY_PROP_REF.
 */
public class FacilityPropRefData extends ValueObject implements Cloneable
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 7641011736484062639L;
    
    private int mFacilityPropRefId;// SQL type:NUMBER, not null
    private int mDecLength;// SQL type:NUMBER
    private String mFacilityPropertyTypeCd;// SQL type:VARCHAR2, not null
    private String mJavaTypeCd;// SQL type:VARCHAR2
    private int mLength;// SQL type:NUMBER
    private String mPropertyName;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    private FacilityPropRefData ()
    {
        mFacilityPropertyTypeCd = "";
        mJavaTypeCd = "";
        mPropertyName = "";
    }

    /**
     * Constructor.
     */
    public FacilityPropRefData(int parm1, int parm2, String parm3, String parm4, int parm5, String parm6)
    {
        mFacilityPropRefId = parm1;
        mDecLength = parm2;
        mFacilityPropertyTypeCd = parm3;
        mJavaTypeCd = parm4;
        mLength = parm5;
        mPropertyName = parm6;
        
    }

    /**
     * Creates a new FacilityPropRefData
     *
     * @return
     *  Newly initialized FacilityPropRefData object.
     */
    public static FacilityPropRefData createValue ()
    {
        FacilityPropRefData valueData = new FacilityPropRefData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FacilityPropRefData object
     */
    public String toString()
    {
        return "[" + "FacilityPropRefId=" + mFacilityPropRefId + ", DecLength=" + mDecLength + ", FacilityPropertyTypeCd=" + mFacilityPropertyTypeCd + ", JavaTypeCd=" + mJavaTypeCd + ", Length=" + mLength + ", PropertyName=" + mPropertyName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FacilityPropRef");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFacilityPropRefId));

        node = doc.createElement("DecLength");
        node.appendChild(doc.createTextNode(String.valueOf(mDecLength)));
        root.appendChild(node);

        node = doc.createElement("FacilityPropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityPropertyTypeCd)));
        root.appendChild(node);

        node = doc.createElement("JavaTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mJavaTypeCd)));
        root.appendChild(node);

        node = doc.createElement("Length");
        node.appendChild(doc.createTextNode(String.valueOf(mLength)));
        root.appendChild(node);

        node = doc.createElement("PropertyName");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyName)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FacilityPropRefId field is not cloned.
    *
    * @return FacilityPropRefData object
    */
    public Object clone(){
        FacilityPropRefData myClone = new FacilityPropRefData();
        
        myClone.mDecLength = mDecLength;
        
        myClone.mFacilityPropertyTypeCd = mFacilityPropertyTypeCd;
        
        myClone.mJavaTypeCd = mJavaTypeCd;
        
        myClone.mLength = mLength;
        
        myClone.mPropertyName = mPropertyName;
        
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
     * Sets the DecLength field.
     *
     * @param pDecLength
     *  int to use to update the field.
     */
    public void setDecLength(int pDecLength){
        this.mDecLength = pDecLength;
        setDirty(true);
    }
    /**
     * Retrieves the DecLength field.
     *
     * @return
     *  int containing the DecLength field.
     */
    public int getDecLength(){
        return mDecLength;
    }

    /**
     * Sets the FacilityPropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pFacilityPropertyTypeCd
     *  String to use to update the field.
     */
    public void setFacilityPropertyTypeCd(String pFacilityPropertyTypeCd){
        this.mFacilityPropertyTypeCd = pFacilityPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityPropertyTypeCd field.
     *
     * @return
     *  String containing the FacilityPropertyTypeCd field.
     */
    public String getFacilityPropertyTypeCd(){
        return mFacilityPropertyTypeCd;
    }

    /**
     * Sets the JavaTypeCd field.
     *
     * @param pJavaTypeCd
     *  String to use to update the field.
     */
    public void setJavaTypeCd(String pJavaTypeCd){
        this.mJavaTypeCd = pJavaTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the JavaTypeCd field.
     *
     * @return
     *  String containing the JavaTypeCd field.
     */
    public String getJavaTypeCd(){
        return mJavaTypeCd;
    }

    /**
     * Sets the Length field.
     *
     * @param pLength
     *  int to use to update the field.
     */
    public void setLength(int pLength){
        this.mLength = pLength;
        setDirty(true);
    }
    /**
     * Retrieves the Length field.
     *
     * @return
     *  int containing the Length field.
     */
    public int getLength(){
        return mLength;
    }

    /**
     * Sets the PropertyName field. This field is required to be set in the database.
     *
     * @param pPropertyName
     *  String to use to update the field.
     */
    public void setPropertyName(String pPropertyName){
        this.mPropertyName = pPropertyName;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyName field.
     *
     * @return
     *  String containing the PropertyName field.
     */
    public String getPropertyName(){
        return mPropertyName;
    }


}
