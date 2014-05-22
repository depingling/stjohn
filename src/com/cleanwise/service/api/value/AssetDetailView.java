
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetDetailView
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
 * <code>AssetDetailView</code> is a ViewObject class for UI.
 */
public class AssetDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -6183155425667293444L;
    private AssetDetailData mAssetDetailData;
    private AssetAssocDataVector mAssocDataVector;
    private BusEntityDataVector mAssetStoreAssoc;
    private BusEntityDataVector mAssetSiteAssoc;
    private WarrantyDataVector mAssetWarrantyAssoc;
    private WorkOrderDataVector mAssetWorkOrderAssoc;
    private AssetContentViewVector mContents;
    private AddressData mLocation;
    private ItemDataVector mAssetServiceAssoc;

    /**
     * Constructor.
     */
    public AssetDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public AssetDetailView(AssetDetailData parm1, AssetAssocDataVector parm2, BusEntityDataVector parm3, BusEntityDataVector parm4, WarrantyDataVector parm5, WorkOrderDataVector parm6, AssetContentViewVector parm7, AddressData parm8, ItemDataVector parm9)
    {
        mAssetDetailData = parm1;
        mAssocDataVector = parm2;
        mAssetStoreAssoc = parm3;
        mAssetSiteAssoc = parm4;
        mAssetWarrantyAssoc = parm5;
        mAssetWorkOrderAssoc = parm6;
        mContents = parm7;
        mLocation = parm8;
        mAssetServiceAssoc = parm9;
        
    }

    /**
     * Creates a new AssetDetailView
     *
     * @return
     *  Newly initialized AssetDetailView object.
     */
    public static AssetDetailView createValue () 
    {
        AssetDetailView valueView = new AssetDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetDetailView object
     */
    public String toString()
    {
        return "[" + "AssetDetailData=" + mAssetDetailData + ", AssocDataVector=" + mAssocDataVector + ", AssetStoreAssoc=" + mAssetStoreAssoc + ", AssetSiteAssoc=" + mAssetSiteAssoc + ", AssetWarrantyAssoc=" + mAssetWarrantyAssoc + ", AssetWorkOrderAssoc=" + mAssetWorkOrderAssoc + ", Contents=" + mContents + ", Location=" + mLocation + ", AssetServiceAssoc=" + mAssetServiceAssoc + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AssetDetail");
	root.setAttribute("Id", String.valueOf(mAssetDetailData));

	Element node;

        node = doc.createElement("AssocDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDataVector)));
        root.appendChild(node);

        node = doc.createElement("AssetStoreAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetStoreAssoc)));
        root.appendChild(node);

        node = doc.createElement("AssetSiteAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetSiteAssoc)));
        root.appendChild(node);

        node = doc.createElement("AssetWarrantyAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetWarrantyAssoc)));
        root.appendChild(node);

        node = doc.createElement("AssetWorkOrderAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetWorkOrderAssoc)));
        root.appendChild(node);

        node = doc.createElement("Contents");
        node.appendChild(doc.createTextNode(String.valueOf(mContents)));
        root.appendChild(node);

        node = doc.createElement("Location");
        node.appendChild(doc.createTextNode(String.valueOf(mLocation)));
        root.appendChild(node);

        node = doc.createElement("AssetServiceAssoc");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetServiceAssoc)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AssetDetailView copy()  {
      AssetDetailView obj = new AssetDetailView();
      obj.setAssetDetailData(mAssetDetailData);
      obj.setAssocDataVector(mAssocDataVector);
      obj.setAssetStoreAssoc(mAssetStoreAssoc);
      obj.setAssetSiteAssoc(mAssetSiteAssoc);
      obj.setAssetWarrantyAssoc(mAssetWarrantyAssoc);
      obj.setAssetWorkOrderAssoc(mAssetWorkOrderAssoc);
      obj.setContents(mContents);
      obj.setLocation(mLocation);
      obj.setAssetServiceAssoc(mAssetServiceAssoc);
      
      return obj;
    }

    
    /**
     * Sets the AssetDetailData property.
     *
     * @param pAssetDetailData
     *  AssetDetailData to use to update the property.
     */
    public void setAssetDetailData(AssetDetailData pAssetDetailData){
        this.mAssetDetailData = pAssetDetailData;
    }
    /**
     * Retrieves the AssetDetailData property.
     *
     * @return
     *  AssetDetailData containing the AssetDetailData property.
     */
    public AssetDetailData getAssetDetailData(){
        return mAssetDetailData;
    }


    /**
     * Sets the AssocDataVector property.
     *
     * @param pAssocDataVector
     *  AssetAssocDataVector to use to update the property.
     */
    public void setAssocDataVector(AssetAssocDataVector pAssocDataVector){
        this.mAssocDataVector = pAssocDataVector;
    }
    /**
     * Retrieves the AssocDataVector property.
     *
     * @return
     *  AssetAssocDataVector containing the AssocDataVector property.
     */
    public AssetAssocDataVector getAssocDataVector(){
        return mAssocDataVector;
    }


    /**
     * Sets the AssetStoreAssoc property.
     *
     * @param pAssetStoreAssoc
     *  BusEntityDataVector to use to update the property.
     */
    public void setAssetStoreAssoc(BusEntityDataVector pAssetStoreAssoc){
        this.mAssetStoreAssoc = pAssetStoreAssoc;
    }
    /**
     * Retrieves the AssetStoreAssoc property.
     *
     * @return
     *  BusEntityDataVector containing the AssetStoreAssoc property.
     */
    public BusEntityDataVector getAssetStoreAssoc(){
        return mAssetStoreAssoc;
    }


    /**
     * Sets the AssetSiteAssoc property.
     *
     * @param pAssetSiteAssoc
     *  BusEntityDataVector to use to update the property.
     */
    public void setAssetSiteAssoc(BusEntityDataVector pAssetSiteAssoc){
        this.mAssetSiteAssoc = pAssetSiteAssoc;
    }
    /**
     * Retrieves the AssetSiteAssoc property.
     *
     * @return
     *  BusEntityDataVector containing the AssetSiteAssoc property.
     */
    public BusEntityDataVector getAssetSiteAssoc(){
        return mAssetSiteAssoc;
    }


    /**
     * Sets the AssetWarrantyAssoc property.
     *
     * @param pAssetWarrantyAssoc
     *  WarrantyDataVector to use to update the property.
     */
    public void setAssetWarrantyAssoc(WarrantyDataVector pAssetWarrantyAssoc){
        this.mAssetWarrantyAssoc = pAssetWarrantyAssoc;
    }
    /**
     * Retrieves the AssetWarrantyAssoc property.
     *
     * @return
     *  WarrantyDataVector containing the AssetWarrantyAssoc property.
     */
    public WarrantyDataVector getAssetWarrantyAssoc(){
        return mAssetWarrantyAssoc;
    }


    /**
     * Sets the AssetWorkOrderAssoc property.
     *
     * @param pAssetWorkOrderAssoc
     *  WorkOrderDataVector to use to update the property.
     */
    public void setAssetWorkOrderAssoc(WorkOrderDataVector pAssetWorkOrderAssoc){
        this.mAssetWorkOrderAssoc = pAssetWorkOrderAssoc;
    }
    /**
     * Retrieves the AssetWorkOrderAssoc property.
     *
     * @return
     *  WorkOrderDataVector containing the AssetWorkOrderAssoc property.
     */
    public WorkOrderDataVector getAssetWorkOrderAssoc(){
        return mAssetWorkOrderAssoc;
    }


    /**
     * Sets the Contents property.
     *
     * @param pContents
     *  AssetContentViewVector to use to update the property.
     */
    public void setContents(AssetContentViewVector pContents){
        this.mContents = pContents;
    }
    /**
     * Retrieves the Contents property.
     *
     * @return
     *  AssetContentViewVector containing the Contents property.
     */
    public AssetContentViewVector getContents(){
        return mContents;
    }


    /**
     * Sets the Location property.
     *
     * @param pLocation
     *  AddressData to use to update the property.
     */
    public void setLocation(AddressData pLocation){
        this.mLocation = pLocation;
    }
    /**
     * Retrieves the Location property.
     *
     * @return
     *  AddressData containing the Location property.
     */
    public AddressData getLocation(){
        return mLocation;
    }


    /**
     * Sets the AssetServiceAssoc property.
     *
     * @param pAssetServiceAssoc
     *  ItemDataVector to use to update the property.
     */
    public void setAssetServiceAssoc(ItemDataVector pAssetServiceAssoc){
        this.mAssetServiceAssoc = pAssetServiceAssoc;
    }
    /**
     * Retrieves the AssetServiceAssoc property.
     *
     * @return
     *  ItemDataVector containing the AssetServiceAssoc property.
     */
    public ItemDataVector getAssetServiceAssoc(){
        return mAssetServiceAssoc;
    }


    
}
