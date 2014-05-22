
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityTerrView
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
 * <code>BusEntityTerrView</code> is a ViewObject class for UI.
 */
public class BusEntityTerrView
extends ValueObject
{
   
    private static final long serialVersionUID = 5242487058171293052L;
    private int mBusEntityId;
    private int mPostalCodeId;
    private String mPostalCode;
    private String mCountyCd;
    private String mStateProvinceCd;
    private String mStateProvinceName;
    private String mCountryCd;
    private boolean mCheckedFl;
    private boolean mNoModifiyFl;
    private String mBusEntityTerrFreightCd;
    private String mCity;

    /**
     * Constructor.
     */
    public BusEntityTerrView ()
    {
        mPostalCode = "";
        mCountyCd = "";
        mStateProvinceCd = "";
        mStateProvinceName = "";
        mCountryCd = "";
        mBusEntityTerrFreightCd = "";
        mCity = "";
    }

    /**
     * Constructor. 
     */
    public BusEntityTerrView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, boolean parm8, boolean parm9, String parm10, String parm11)
    {
        mBusEntityId = parm1;
        mPostalCodeId = parm2;
        mPostalCode = parm3;
        mCountyCd = parm4;
        mStateProvinceCd = parm5;
        mStateProvinceName = parm6;
        mCountryCd = parm7;
        mCheckedFl = parm8;
        mNoModifiyFl = parm9;
        mBusEntityTerrFreightCd = parm10;
        mCity = parm11;
        
    }

    /**
     * Creates a new BusEntityTerrView
     *
     * @return
     *  Newly initialized BusEntityTerrView object.
     */
    public static BusEntityTerrView createValue () 
    {
        BusEntityTerrView valueView = new BusEntityTerrView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BusEntityTerrView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", PostalCodeId=" + mPostalCodeId + ", PostalCode=" + mPostalCode + ", CountyCd=" + mCountyCd + ", StateProvinceCd=" + mStateProvinceCd + ", StateProvinceName=" + mStateProvinceName + ", CountryCd=" + mCountryCd + ", CheckedFl=" + mCheckedFl + ", NoModifiyFl=" + mNoModifiyFl + ", BusEntityTerrFreightCd=" + mBusEntityTerrFreightCd + ", City=" + mCity + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BusEntityTerr");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("PostalCodeId");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCodeId)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("CountyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountyCd)));
        root.appendChild(node);

        node = doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node = doc.createElement("StateProvinceName");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceName)));
        root.appendChild(node);

        node = doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        node = doc.createElement("CheckedFl");
        node.appendChild(doc.createTextNode(String.valueOf(mCheckedFl)));
        root.appendChild(node);

        node = doc.createElement("NoModifiyFl");
        node.appendChild(doc.createTextNode(String.valueOf(mNoModifiyFl)));
        root.appendChild(node);

        node = doc.createElement("BusEntityTerrFreightCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTerrFreightCd)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BusEntityTerrView copy()  {
      BusEntityTerrView obj = new BusEntityTerrView();
      obj.setBusEntityId(mBusEntityId);
      obj.setPostalCodeId(mPostalCodeId);
      obj.setPostalCode(mPostalCode);
      obj.setCountyCd(mCountyCd);
      obj.setStateProvinceCd(mStateProvinceCd);
      obj.setStateProvinceName(mStateProvinceName);
      obj.setCountryCd(mCountryCd);
      obj.setCheckedFl(mCheckedFl);
      obj.setNoModifiyFl(mNoModifiyFl);
      obj.setBusEntityTerrFreightCd(mBusEntityTerrFreightCd);
      obj.setCity(mCity);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the PostalCodeId property.
     *
     * @param pPostalCodeId
     *  int to use to update the property.
     */
    public void setPostalCodeId(int pPostalCodeId){
        this.mPostalCodeId = pPostalCodeId;
    }
    /**
     * Retrieves the PostalCodeId property.
     *
     * @return
     *  int containing the PostalCodeId property.
     */
    public int getPostalCodeId(){
        return mPostalCodeId;
    }


    /**
     * Sets the PostalCode property.
     *
     * @param pPostalCode
     *  String to use to update the property.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
    }
    /**
     * Retrieves the PostalCode property.
     *
     * @return
     *  String containing the PostalCode property.
     */
    public String getPostalCode(){
        return mPostalCode;
    }


    /**
     * Sets the CountyCd property.
     *
     * @param pCountyCd
     *  String to use to update the property.
     */
    public void setCountyCd(String pCountyCd){
        this.mCountyCd = pCountyCd;
    }
    /**
     * Retrieves the CountyCd property.
     *
     * @return
     *  String containing the CountyCd property.
     */
    public String getCountyCd(){
        return mCountyCd;
    }


    /**
     * Sets the StateProvinceCd property.
     *
     * @param pStateProvinceCd
     *  String to use to update the property.
     */
    public void setStateProvinceCd(String pStateProvinceCd){
        this.mStateProvinceCd = pStateProvinceCd;
    }
    /**
     * Retrieves the StateProvinceCd property.
     *
     * @return
     *  String containing the StateProvinceCd property.
     */
    public String getStateProvinceCd(){
        return mStateProvinceCd;
    }


    /**
     * Sets the StateProvinceName property.
     *
     * @param pStateProvinceName
     *  String to use to update the property.
     */
    public void setStateProvinceName(String pStateProvinceName){
        this.mStateProvinceName = pStateProvinceName;
    }
    /**
     * Retrieves the StateProvinceName property.
     *
     * @return
     *  String containing the StateProvinceName property.
     */
    public String getStateProvinceName(){
        return mStateProvinceName;
    }


    /**
     * Sets the CountryCd property.
     *
     * @param pCountryCd
     *  String to use to update the property.
     */
    public void setCountryCd(String pCountryCd){
        this.mCountryCd = pCountryCd;
    }
    /**
     * Retrieves the CountryCd property.
     *
     * @return
     *  String containing the CountryCd property.
     */
    public String getCountryCd(){
        return mCountryCd;
    }


    /**
     * Sets the CheckedFl property.
     *
     * @param pCheckedFl
     *  boolean to use to update the property.
     */
    public void setCheckedFl(boolean pCheckedFl){
        this.mCheckedFl = pCheckedFl;
    }
    /**
     * Retrieves the CheckedFl property.
     *
     * @return
     *  boolean containing the CheckedFl property.
     */
    public boolean getCheckedFl(){
        return mCheckedFl;
    }


    /**
     * Sets the NoModifiyFl property.
     *
     * @param pNoModifiyFl
     *  boolean to use to update the property.
     */
    public void setNoModifiyFl(boolean pNoModifiyFl){
        this.mNoModifiyFl = pNoModifiyFl;
    }
    /**
     * Retrieves the NoModifiyFl property.
     *
     * @return
     *  boolean containing the NoModifiyFl property.
     */
    public boolean getNoModifiyFl(){
        return mNoModifiyFl;
    }


    /**
     * Sets the BusEntityTerrFreightCd property.
     *
     * @param pBusEntityTerrFreightCd
     *  String to use to update the property.
     */
    public void setBusEntityTerrFreightCd(String pBusEntityTerrFreightCd){
        this.mBusEntityTerrFreightCd = pBusEntityTerrFreightCd;
    }
    /**
     * Retrieves the BusEntityTerrFreightCd property.
     *
     * @return
     *  String containing the BusEntityTerrFreightCd property.
     */
    public String getBusEntityTerrFreightCd(){
        return mBusEntityTerrFreightCd;
    }


    /**
     * Sets the City property.
     *
     * @param pCity
     *  String to use to update the property.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
    }
    /**
     * Retrieves the City property.
     *
     * @return
     *  String containing the City property.
     */
    public String getCity(){
        return mCity;
    }


    
}
