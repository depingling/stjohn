
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyDetailView
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
 * <code>WarrantyDetailView</code> is a ViewObject class for UI.
 */
public class WarrantyDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -9079866676285728515L;
    private WarrantyData mWarrantyData;
    private WarrantyAssocViewVector mWarrantyAssocViewVector;
    private WarrantyStatusHistDataVector mStatusHistory;
    private WarrantyContentViewVector mContents;
    private AssetWarrantyViewVector mAssetWarrantyViewVector;
    private WorkOrderItemWarrantyViewVector mWorkOrderItemWarrantyViewVector;
    private WarrantyNoteDataVector mWarrantyNotes;

    /**
     * Constructor.
     */
    public WarrantyDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public WarrantyDetailView(WarrantyData parm1, WarrantyAssocViewVector parm2, WarrantyStatusHistDataVector parm3, WarrantyContentViewVector parm4, AssetWarrantyViewVector parm5, WorkOrderItemWarrantyViewVector parm6, WarrantyNoteDataVector parm7)
    {
        mWarrantyData = parm1;
        mWarrantyAssocViewVector = parm2;
        mStatusHistory = parm3;
        mContents = parm4;
        mAssetWarrantyViewVector = parm5;
        mWorkOrderItemWarrantyViewVector = parm6;
        mWarrantyNotes = parm7;
        
    }

    /**
     * Creates a new WarrantyDetailView
     *
     * @return
     *  Newly initialized WarrantyDetailView object.
     */
    public static WarrantyDetailView createValue () 
    {
        WarrantyDetailView valueView = new WarrantyDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyDetailView object
     */
    public String toString()
    {
        return "[" + "WarrantyData=" + mWarrantyData + ", WarrantyAssocViewVector=" + mWarrantyAssocViewVector + ", StatusHistory=" + mStatusHistory + ", Contents=" + mContents + ", AssetWarrantyViewVector=" + mAssetWarrantyViewVector + ", WorkOrderItemWarrantyViewVector=" + mWorkOrderItemWarrantyViewVector + ", WarrantyNotes=" + mWarrantyNotes + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WarrantyDetail");
	root.setAttribute("Id", String.valueOf(mWarrantyData));

	Element node;

        node = doc.createElement("WarrantyAssocViewVector");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyAssocViewVector)));
        root.appendChild(node);

        node = doc.createElement("StatusHistory");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusHistory)));
        root.appendChild(node);

        node = doc.createElement("Contents");
        node.appendChild(doc.createTextNode(String.valueOf(mContents)));
        root.appendChild(node);

        node = doc.createElement("AssetWarrantyViewVector");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetWarrantyViewVector)));
        root.appendChild(node);

        node = doc.createElement("WorkOrderItemWarrantyViewVector");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItemWarrantyViewVector)));
        root.appendChild(node);

        node = doc.createElement("WarrantyNotes");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyNotes)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WarrantyDetailView copy()  {
      WarrantyDetailView obj = new WarrantyDetailView();
      obj.setWarrantyData(mWarrantyData);
      obj.setWarrantyAssocViewVector(mWarrantyAssocViewVector);
      obj.setStatusHistory(mStatusHistory);
      obj.setContents(mContents);
      obj.setAssetWarrantyViewVector(mAssetWarrantyViewVector);
      obj.setWorkOrderItemWarrantyViewVector(mWorkOrderItemWarrantyViewVector);
      obj.setWarrantyNotes(mWarrantyNotes);
      
      return obj;
    }

    
    /**
     * Sets the WarrantyData property.
     *
     * @param pWarrantyData
     *  WarrantyData to use to update the property.
     */
    public void setWarrantyData(WarrantyData pWarrantyData){
        this.mWarrantyData = pWarrantyData;
    }
    /**
     * Retrieves the WarrantyData property.
     *
     * @return
     *  WarrantyData containing the WarrantyData property.
     */
    public WarrantyData getWarrantyData(){
        return mWarrantyData;
    }


    /**
     * Sets the WarrantyAssocViewVector property.
     *
     * @param pWarrantyAssocViewVector
     *  WarrantyAssocViewVector to use to update the property.
     */
    public void setWarrantyAssocViewVector(WarrantyAssocViewVector pWarrantyAssocViewVector){
        this.mWarrantyAssocViewVector = pWarrantyAssocViewVector;
    }
    /**
     * Retrieves the WarrantyAssocViewVector property.
     *
     * @return
     *  WarrantyAssocViewVector containing the WarrantyAssocViewVector property.
     */
    public WarrantyAssocViewVector getWarrantyAssocViewVector(){
        return mWarrantyAssocViewVector;
    }


    /**
     * Sets the StatusHistory property.
     *
     * @param pStatusHistory
     *  WarrantyStatusHistDataVector to use to update the property.
     */
    public void setStatusHistory(WarrantyStatusHistDataVector pStatusHistory){
        this.mStatusHistory = pStatusHistory;
    }
    /**
     * Retrieves the StatusHistory property.
     *
     * @return
     *  WarrantyStatusHistDataVector containing the StatusHistory property.
     */
    public WarrantyStatusHistDataVector getStatusHistory(){
        return mStatusHistory;
    }


    /**
     * Sets the Contents property.
     *
     * @param pContents
     *  WarrantyContentViewVector to use to update the property.
     */
    public void setContents(WarrantyContentViewVector pContents){
        this.mContents = pContents;
    }
    /**
     * Retrieves the Contents property.
     *
     * @return
     *  WarrantyContentViewVector containing the Contents property.
     */
    public WarrantyContentViewVector getContents(){
        return mContents;
    }


    /**
     * Sets the AssetWarrantyViewVector property.
     *
     * @param pAssetWarrantyViewVector
     *  AssetWarrantyViewVector to use to update the property.
     */
    public void setAssetWarrantyViewVector(AssetWarrantyViewVector pAssetWarrantyViewVector){
        this.mAssetWarrantyViewVector = pAssetWarrantyViewVector;
    }
    /**
     * Retrieves the AssetWarrantyViewVector property.
     *
     * @return
     *  AssetWarrantyViewVector containing the AssetWarrantyViewVector property.
     */
    public AssetWarrantyViewVector getAssetWarrantyViewVector(){
        return mAssetWarrantyViewVector;
    }


    /**
     * Sets the WorkOrderItemWarrantyViewVector property.
     *
     * @param pWorkOrderItemWarrantyViewVector
     *  WorkOrderItemWarrantyViewVector to use to update the property.
     */
    public void setWorkOrderItemWarrantyViewVector(WorkOrderItemWarrantyViewVector pWorkOrderItemWarrantyViewVector){
        this.mWorkOrderItemWarrantyViewVector = pWorkOrderItemWarrantyViewVector;
    }
    /**
     * Retrieves the WorkOrderItemWarrantyViewVector property.
     *
     * @return
     *  WorkOrderItemWarrantyViewVector containing the WorkOrderItemWarrantyViewVector property.
     */
    public WorkOrderItemWarrantyViewVector getWorkOrderItemWarrantyViewVector(){
        return mWorkOrderItemWarrantyViewVector;
    }


    /**
     * Sets the WarrantyNotes property.
     *
     * @param pWarrantyNotes
     *  WarrantyNoteDataVector to use to update the property.
     */
    public void setWarrantyNotes(WarrantyNoteDataVector pWarrantyNotes){
        this.mWarrantyNotes = pWarrantyNotes;
    }
    /**
     * Retrieves the WarrantyNotes property.
     *
     * @return
     *  WarrantyNoteDataVector containing the WarrantyNotes property.
     */
    public WarrantyNoteDataVector getWarrantyNotes(){
        return mWarrantyNotes;
    }


    
}
