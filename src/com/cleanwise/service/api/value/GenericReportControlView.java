
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportControlView
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
 * <code>GenericReportControlView</code> is a ViewObject class for UI.
 */
public class GenericReportControlView
extends ValueObject
{
   
    private static final long serialVersionUID = 637815308072238562L;
    private String mName;
    private String mType;
    private String mMandatoryFl;
    private String mLabel;
    private String mAdditionalLabel1;
    private String mAdditionalLabel2;
    private String mAdditionalLabel3;
    private String mPriority;
    private String mDefault;
    private String mValue;
    private String mSrcString;
    private String mControlTypeCd;
    private PairViewVector mChoiceValues;
    private Boolean mIgnore;
    private Boolean mInvisible;

    /**
     * Constructor.
     */
    public GenericReportControlView ()
    {
        mName = "";
        mType = "";
        mMandatoryFl = "";
        mLabel = "";
        mAdditionalLabel1 = "";
        mAdditionalLabel2 = "";
        mAdditionalLabel3 = "";
        mPriority = "";
        mDefault = "";
        mValue = "";
        mSrcString = "";
        mControlTypeCd = "";
    }

    /**
     * Constructor. 
     */
    public GenericReportControlView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, PairViewVector parm13, Boolean parm14, Boolean parm15)
    {
        mName = parm1;
        mType = parm2;
        mMandatoryFl = parm3;
        mLabel = parm4;
        mAdditionalLabel1 = parm5;
        mAdditionalLabel2 = parm6;
        mAdditionalLabel3 = parm7;
        mPriority = parm8;
        mDefault = parm9;
        mValue = parm10;
        mSrcString = parm11;
        mControlTypeCd = parm12;
        mChoiceValues = parm13;
        mIgnore = parm14;
        mInvisible = parm15;
        
    }

    /**
     * Creates a new GenericReportControlView
     *
     * @return
     *  Newly initialized GenericReportControlView object.
     */
    public static GenericReportControlView createValue () 
    {
        GenericReportControlView valueView = new GenericReportControlView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportControlView object
     */
    public String toString()
    {
        return "[" + "Name=" + mName + ", Type=" + mType + ", MandatoryFl=" + mMandatoryFl + ", Label=" + mLabel + ", AdditionalLabel1=" + mAdditionalLabel1 + ", AdditionalLabel2=" + mAdditionalLabel2 + ", AdditionalLabel3=" + mAdditionalLabel3 + ", Priority=" + mPriority + ", Default=" + mDefault + ", Value=" + mValue + ", SrcString=" + mSrcString + ", ControlTypeCd=" + mControlTypeCd + ", ChoiceValues=" + mChoiceValues + ", Ignore=" + mIgnore + ", Invisible=" + mInvisible + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GenericReportControl");
	root.setAttribute("Id", String.valueOf(mName));

	Element node;

        node = doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        node = doc.createElement("MandatoryFl");
        node.appendChild(doc.createTextNode(String.valueOf(mMandatoryFl)));
        root.appendChild(node);

        node = doc.createElement("Label");
        node.appendChild(doc.createTextNode(String.valueOf(mLabel)));
        root.appendChild(node);

        node = doc.createElement("AdditionalLabel1");
        node.appendChild(doc.createTextNode(String.valueOf(mAdditionalLabel1)));
        root.appendChild(node);

        node = doc.createElement("AdditionalLabel2");
        node.appendChild(doc.createTextNode(String.valueOf(mAdditionalLabel2)));
        root.appendChild(node);

        node = doc.createElement("AdditionalLabel3");
        node.appendChild(doc.createTextNode(String.valueOf(mAdditionalLabel3)));
        root.appendChild(node);

        node = doc.createElement("Priority");
        node.appendChild(doc.createTextNode(String.valueOf(mPriority)));
        root.appendChild(node);

        node = doc.createElement("Default");
        node.appendChild(doc.createTextNode(String.valueOf(mDefault)));
        root.appendChild(node);

        node = doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node = doc.createElement("SrcString");
        node.appendChild(doc.createTextNode(String.valueOf(mSrcString)));
        root.appendChild(node);

        node = doc.createElement("ControlTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mControlTypeCd)));
        root.appendChild(node);

        node = doc.createElement("ChoiceValues");
        node.appendChild(doc.createTextNode(String.valueOf(mChoiceValues)));
        root.appendChild(node);

        node = doc.createElement("Ignore");
        node.appendChild(doc.createTextNode(String.valueOf(mIgnore)));
        root.appendChild(node);

        node = doc.createElement("Invisible");
        node.appendChild(doc.createTextNode(String.valueOf(mInvisible)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GenericReportControlView copy()  {
      GenericReportControlView obj = new GenericReportControlView();
      obj.setName(mName);
      obj.setType(mType);
      obj.setMandatoryFl(mMandatoryFl);
      obj.setLabel(mLabel);
      obj.setAdditionalLabel1(mAdditionalLabel1);
      obj.setAdditionalLabel2(mAdditionalLabel2);
      obj.setAdditionalLabel3(mAdditionalLabel3);
      obj.setPriority(mPriority);
      obj.setDefault(mDefault);
      obj.setValue(mValue);
      obj.setSrcString(mSrcString);
      obj.setControlTypeCd(mControlTypeCd);
      obj.setChoiceValues(mChoiceValues);
      obj.setIgnore(mIgnore);
      obj.setInvisible(mInvisible);
      
      return obj;
    }

    
    /**
     * Sets the Name property.
     *
     * @param pName
     *  String to use to update the property.
     */
    public void setName(String pName){
        this.mName = pName;
    }
    /**
     * Retrieves the Name property.
     *
     * @return
     *  String containing the Name property.
     */
    public String getName(){
        return mName;
    }


    /**
     * Sets the Type property.
     *
     * @param pType
     *  String to use to update the property.
     */
    public void setType(String pType){
        this.mType = pType;
    }
    /**
     * Retrieves the Type property.
     *
     * @return
     *  String containing the Type property.
     */
    public String getType(){
        return mType;
    }


    /**
     * Sets the MandatoryFl property.
     *
     * @param pMandatoryFl
     *  String to use to update the property.
     */
    public void setMandatoryFl(String pMandatoryFl){
        this.mMandatoryFl = pMandatoryFl;
    }
    /**
     * Retrieves the MandatoryFl property.
     *
     * @return
     *  String containing the MandatoryFl property.
     */
    public String getMandatoryFl(){
        return mMandatoryFl;
    }


    /**
     * Sets the Label property.
     *
     * @param pLabel
     *  String to use to update the property.
     */
    public void setLabel(String pLabel){
        this.mLabel = pLabel;
    }
    /**
     * Retrieves the Label property.
     *
     * @return
     *  String containing the Label property.
     */
    public String getLabel(){
        return mLabel;
    }


    /**
     * Sets the AdditionalLabel1 property.
     *
     * @param pAdditionalLabel1
     *  String to use to update the property.
     */
    public void setAdditionalLabel1(String pAdditionalLabel1){
        this.mAdditionalLabel1 = pAdditionalLabel1;
    }
    /**
     * Retrieves the AdditionalLabel1 property.
     *
     * @return
     *  String containing the AdditionalLabel1 property.
     */
    public String getAdditionalLabel1(){
        return mAdditionalLabel1;
    }


    /**
     * Sets the AdditionalLabel2 property.
     *
     * @param pAdditionalLabel2
     *  String to use to update the property.
     */
    public void setAdditionalLabel2(String pAdditionalLabel2){
        this.mAdditionalLabel2 = pAdditionalLabel2;
    }
    /**
     * Retrieves the AdditionalLabel2 property.
     *
     * @return
     *  String containing the AdditionalLabel2 property.
     */
    public String getAdditionalLabel2(){
        return mAdditionalLabel2;
    }


    /**
     * Sets the AdditionalLabel3 property.
     *
     * @param pAdditionalLabel3
     *  String to use to update the property.
     */
    public void setAdditionalLabel3(String pAdditionalLabel3){
        this.mAdditionalLabel3 = pAdditionalLabel3;
    }
    /**
     * Retrieves the AdditionalLabel3 property.
     *
     * @return
     *  String containing the AdditionalLabel3 property.
     */
    public String getAdditionalLabel3(){
        return mAdditionalLabel3;
    }


    /**
     * Sets the Priority property.
     *
     * @param pPriority
     *  String to use to update the property.
     */
    public void setPriority(String pPriority){
        this.mPriority = pPriority;
    }
    /**
     * Retrieves the Priority property.
     *
     * @return
     *  String containing the Priority property.
     */
    public String getPriority(){
        return mPriority;
    }


    /**
     * Sets the Default property.
     *
     * @param pDefault
     *  String to use to update the property.
     */
    public void setDefault(String pDefault){
        this.mDefault = pDefault;
    }
    /**
     * Retrieves the Default property.
     *
     * @return
     *  String containing the Default property.
     */
    public String getDefault(){
        return mDefault;
    }


    /**
     * Sets the Value property.
     *
     * @param pValue
     *  String to use to update the property.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
    }
    /**
     * Retrieves the Value property.
     *
     * @return
     *  String containing the Value property.
     */
    public String getValue(){
        return mValue;
    }


    /**
     * Sets the SrcString property.
     *
     * @param pSrcString
     *  String to use to update the property.
     */
    public void setSrcString(String pSrcString){
        this.mSrcString = pSrcString;
    }
    /**
     * Retrieves the SrcString property.
     *
     * @return
     *  String containing the SrcString property.
     */
    public String getSrcString(){
        return mSrcString;
    }


    /**
     * Sets the ControlTypeCd property.
     *
     * @param pControlTypeCd
     *  String to use to update the property.
     */
    public void setControlTypeCd(String pControlTypeCd){
        this.mControlTypeCd = pControlTypeCd;
    }
    /**
     * Retrieves the ControlTypeCd property.
     *
     * @return
     *  String containing the ControlTypeCd property.
     */
    public String getControlTypeCd(){
        return mControlTypeCd;
    }


    /**
     * Sets the ChoiceValues property.
     *
     * @param pChoiceValues
     *  PairViewVector to use to update the property.
     */
    public void setChoiceValues(PairViewVector pChoiceValues){
        this.mChoiceValues = pChoiceValues;
    }
    /**
     * Retrieves the ChoiceValues property.
     *
     * @return
     *  PairViewVector containing the ChoiceValues property.
     */
    public PairViewVector getChoiceValues(){
        return mChoiceValues;
    }


    /**
     * Sets the Ignore property.
     *
     * @param pIgnore
     *  Boolean to use to update the property.
     */
    public void setIgnore(Boolean pIgnore){
        this.mIgnore = pIgnore;
    }
    /**
     * Retrieves the Ignore property.
     *
     * @return
     *  Boolean containing the Ignore property.
     */
    public Boolean getIgnore(){
        return mIgnore;
    }


    /**
     * Sets the Invisible property.
     *
     * @param pInvisible
     *  Boolean to use to update the property.
     */
    public void setInvisible(Boolean pInvisible){
        this.mInvisible = pInvisible;
    }
    /**
     * Retrieves the Invisible property.
     *
     * @return
     *  Boolean containing the Invisible property.
     */
    public Boolean getInvisible(){
        return mInvisible;
    }


    
}
