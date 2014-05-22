
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ServiceDetailView
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
 * <code>ServiceDetailView</code> is a ViewObject class for UI.
 */
public class ServiceDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -8292743896099718086L;
    private ItemData mItemData;
    private ItemDataVector mCategories;
    private ItemMappingDataVector mItemMapping;
    private ItemMetaDataVector mItemMeta;
    private BusEntityDataVector mDistBusEntity;
    private BusEntityDataVector mStoreBusEntity;
    private BusEntityDataVector mManufBusEntity;
    private CatalogStructureDataVector mCatalogStructure;

    /**
     * Constructor.
     */
    public ServiceDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public ServiceDetailView(ItemData parm1, ItemDataVector parm2, ItemMappingDataVector parm3, ItemMetaDataVector parm4, BusEntityDataVector parm5, BusEntityDataVector parm6, BusEntityDataVector parm7, CatalogStructureDataVector parm8)
    {
        mItemData = parm1;
        mCategories = parm2;
        mItemMapping = parm3;
        mItemMeta = parm4;
        mDistBusEntity = parm5;
        mStoreBusEntity = parm6;
        mManufBusEntity = parm7;
        mCatalogStructure = parm8;
        
    }

    /**
     * Creates a new ServiceDetailView
     *
     * @return
     *  Newly initialized ServiceDetailView object.
     */
    public static ServiceDetailView createValue () 
    {
        ServiceDetailView valueView = new ServiceDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ServiceDetailView object
     */
    public String toString()
    {
        return "[" + "ItemData=" + mItemData + ", Categories=" + mCategories + ", ItemMapping=" + mItemMapping + ", ItemMeta=" + mItemMeta + ", DistBusEntity=" + mDistBusEntity + ", StoreBusEntity=" + mStoreBusEntity + ", ManufBusEntity=" + mManufBusEntity + ", CatalogStructure=" + mCatalogStructure + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ServiceDetail");
	root.setAttribute("Id", String.valueOf(mItemData));

	Element node;

        node = doc.createElement("Categories");
        node.appendChild(doc.createTextNode(String.valueOf(mCategories)));
        root.appendChild(node);

        node = doc.createElement("ItemMapping");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMapping)));
        root.appendChild(node);

        node = doc.createElement("ItemMeta");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMeta)));
        root.appendChild(node);

        node = doc.createElement("DistBusEntity");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBusEntity)));
        root.appendChild(node);

        node = doc.createElement("StoreBusEntity");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreBusEntity)));
        root.appendChild(node);

        node = doc.createElement("ManufBusEntity");
        node.appendChild(doc.createTextNode(String.valueOf(mManufBusEntity)));
        root.appendChild(node);

        node = doc.createElement("CatalogStructure");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogStructure)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ServiceDetailView copy()  {
      ServiceDetailView obj = new ServiceDetailView();
      obj.setItemData(mItemData);
      obj.setCategories(mCategories);
      obj.setItemMapping(mItemMapping);
      obj.setItemMeta(mItemMeta);
      obj.setDistBusEntity(mDistBusEntity);
      obj.setStoreBusEntity(mStoreBusEntity);
      obj.setManufBusEntity(mManufBusEntity);
      obj.setCatalogStructure(mCatalogStructure);
      
      return obj;
    }

    
    /**
     * Sets the ItemData property.
     *
     * @param pItemData
     *  ItemData to use to update the property.
     */
    public void setItemData(ItemData pItemData){
        this.mItemData = pItemData;
    }
    /**
     * Retrieves the ItemData property.
     *
     * @return
     *  ItemData containing the ItemData property.
     */
    public ItemData getItemData(){
        return mItemData;
    }


    /**
     * Sets the Categories property.
     *
     * @param pCategories
     *  ItemDataVector to use to update the property.
     */
    public void setCategories(ItemDataVector pCategories){
        this.mCategories = pCategories;
    }
    /**
     * Retrieves the Categories property.
     *
     * @return
     *  ItemDataVector containing the Categories property.
     */
    public ItemDataVector getCategories(){
        return mCategories;
    }


    /**
     * Sets the ItemMapping property.
     *
     * @param pItemMapping
     *  ItemMappingDataVector to use to update the property.
     */
    public void setItemMapping(ItemMappingDataVector pItemMapping){
        this.mItemMapping = pItemMapping;
    }
    /**
     * Retrieves the ItemMapping property.
     *
     * @return
     *  ItemMappingDataVector containing the ItemMapping property.
     */
    public ItemMappingDataVector getItemMapping(){
        return mItemMapping;
    }


    /**
     * Sets the ItemMeta property.
     *
     * @param pItemMeta
     *  ItemMetaDataVector to use to update the property.
     */
    public void setItemMeta(ItemMetaDataVector pItemMeta){
        this.mItemMeta = pItemMeta;
    }
    /**
     * Retrieves the ItemMeta property.
     *
     * @return
     *  ItemMetaDataVector containing the ItemMeta property.
     */
    public ItemMetaDataVector getItemMeta(){
        return mItemMeta;
    }


    /**
     * Sets the DistBusEntity property.
     *
     * @param pDistBusEntity
     *  BusEntityDataVector to use to update the property.
     */
    public void setDistBusEntity(BusEntityDataVector pDistBusEntity){
        this.mDistBusEntity = pDistBusEntity;
    }
    /**
     * Retrieves the DistBusEntity property.
     *
     * @return
     *  BusEntityDataVector containing the DistBusEntity property.
     */
    public BusEntityDataVector getDistBusEntity(){
        return mDistBusEntity;
    }


    /**
     * Sets the StoreBusEntity property.
     *
     * @param pStoreBusEntity
     *  BusEntityDataVector to use to update the property.
     */
    public void setStoreBusEntity(BusEntityDataVector pStoreBusEntity){
        this.mStoreBusEntity = pStoreBusEntity;
    }
    /**
     * Retrieves the StoreBusEntity property.
     *
     * @return
     *  BusEntityDataVector containing the StoreBusEntity property.
     */
    public BusEntityDataVector getStoreBusEntity(){
        return mStoreBusEntity;
    }


    /**
     * Sets the ManufBusEntity property.
     *
     * @param pManufBusEntity
     *  BusEntityDataVector to use to update the property.
     */
    public void setManufBusEntity(BusEntityDataVector pManufBusEntity){
        this.mManufBusEntity = pManufBusEntity;
    }
    /**
     * Retrieves the ManufBusEntity property.
     *
     * @return
     *  BusEntityDataVector containing the ManufBusEntity property.
     */
    public BusEntityDataVector getManufBusEntity(){
        return mManufBusEntity;
    }


    /**
     * Sets the CatalogStructure property.
     *
     * @param pCatalogStructure
     *  CatalogStructureDataVector to use to update the property.
     */
    public void setCatalogStructure(CatalogStructureDataVector pCatalogStructure){
        this.mCatalogStructure = pCatalogStructure;
    }
    /**
     * Retrieves the CatalogStructure property.
     *
     * @return
     *  CatalogStructureDataVector containing the CatalogStructure property.
     */
    public CatalogStructureDataVector getCatalogStructure(){
        return mCatalogStructure;
    }


    
}
