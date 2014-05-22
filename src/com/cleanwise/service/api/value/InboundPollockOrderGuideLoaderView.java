
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InboundPollockOrderGuideLoaderView
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
 * <code>InboundPollockOrderGuideLoaderView</code> is a ViewObject class for UI.
 */
public class InboundPollockOrderGuideLoaderView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mEditType;
    private String mRecordType;
    private String mRecordValue1;
    private String mRecordValue2;
    private String mRecordValue3;

    /**
     * Constructor.
     */
    public InboundPollockOrderGuideLoaderView ()
    {
        mEditType = "";
        mRecordType = "";
        mRecordValue1 = "";
        mRecordValue2 = "";
        mRecordValue3 = "";
    }

    /**
     * Constructor. 
     */
    public InboundPollockOrderGuideLoaderView(String parm1, String parm2, String parm3, String parm4, String parm5)
    {
        mEditType = parm1;
        mRecordType = parm2;
        mRecordValue1 = parm3;
        mRecordValue2 = parm4;
        mRecordValue3 = parm5;
        
    }

    /**
     * Creates a new InboundPollockOrderGuideLoaderView
     *
     * @return
     *  Newly initialized InboundPollockOrderGuideLoaderView object.
     */
    public static InboundPollockOrderGuideLoaderView createValue () 
    {
        InboundPollockOrderGuideLoaderView valueView = new InboundPollockOrderGuideLoaderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InboundPollockOrderGuideLoaderView object
     */
    public String toString()
    {
        return "[" + "EditType=" + mEditType + ", RecordType=" + mRecordType + ", RecordValue1=" + mRecordValue1 + ", RecordValue2=" + mRecordValue2 + ", RecordValue3=" + mRecordValue3 + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InboundPollockOrderGuideLoader");
	root.setAttribute("Id", String.valueOf(mEditType));

	Element node;

        node = doc.createElement("RecordType");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordType)));
        root.appendChild(node);

        node = doc.createElement("RecordValue1");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordValue1)));
        root.appendChild(node);

        node = doc.createElement("RecordValue2");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordValue2)));
        root.appendChild(node);

        node = doc.createElement("RecordValue3");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordValue3)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InboundPollockOrderGuideLoaderView copy()  {
      InboundPollockOrderGuideLoaderView obj = new InboundPollockOrderGuideLoaderView();
      obj.setEditType(mEditType);
      obj.setRecordType(mRecordType);
      obj.setRecordValue1(mRecordValue1);
      obj.setRecordValue2(mRecordValue2);
      obj.setRecordValue3(mRecordValue3);
      
      return obj;
    }

    
    /**
     * Sets the EditType property.
     *
     * @param pEditType
     *  String to use to update the property.
     */
    public void setEditType(String pEditType){
        this.mEditType = pEditType;
    }
    /**
     * Retrieves the EditType property.
     *
     * @return
     *  String containing the EditType property.
     */
    public String getEditType(){
        return mEditType;
    }


    /**
     * Sets the RecordType property.
     *
     * @param pRecordType
     *  String to use to update the property.
     */
    public void setRecordType(String pRecordType){
        this.mRecordType = pRecordType;
    }
    /**
     * Retrieves the RecordType property.
     *
     * @return
     *  String containing the RecordType property.
     */
    public String getRecordType(){
        return mRecordType;
    }


    /**
     * Sets the RecordValue1 property.
     *
     * @param pRecordValue1
     *  String to use to update the property.
     */
    public void setRecordValue1(String pRecordValue1){
        this.mRecordValue1 = pRecordValue1;
    }
    /**
     * Retrieves the RecordValue1 property.
     *
     * @return
     *  String containing the RecordValue1 property.
     */
    public String getRecordValue1(){
        return mRecordValue1;
    }


    /**
     * Sets the RecordValue2 property.
     *
     * @param pRecordValue2
     *  String to use to update the property.
     */
    public void setRecordValue2(String pRecordValue2){
        this.mRecordValue2 = pRecordValue2;
    }
    /**
     * Retrieves the RecordValue2 property.
     *
     * @return
     *  String containing the RecordValue2 property.
     */
    public String getRecordValue2(){
        return mRecordValue2;
    }


    /**
     * Sets the RecordValue3 property.
     *
     * @param pRecordValue3
     *  String to use to update the property.
     */
    public void setRecordValue3(String pRecordValue3){
        this.mRecordValue3 = pRecordValue3;
    }
    /**
     * Retrieves the RecordValue3 property.
     *
     * @return
     *  String containing the RecordValue3 property.
     */
    public String getRecordValue3(){
        return mRecordValue3;
    }


    
}
