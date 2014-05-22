
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetView
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
 * <code>AssetView</code> is a ViewObject class for UI.
 */
public class AssetView
extends ValueObject
{
   
   public interface SITE_INFO {
   		public static final String SITE_IDS="SITE_IDS";
   		public static final String SITE_NAMES="SITE_NAMES";
   }
   
    private static final long serialVersionUID = -1L;
    private int mAssetId;
    private int mParentId;
    private String mAssetTypeCd;
    private String mAssetCategory;
    private String mAssetName;
    private String mAssetNumber;
    private String mSerialNumber;
    private String mModelNumber;
    private String mDateInService;
    private HashMap mSiteInfo;
    private String mStatus;
    private String mManufName;
    private String mManufSku;
    private String mManufType;
    private int mManufId;
    private int mMatchedAssetId;

    /**
     * Constructor.
     */
    public AssetView ()
    {
        mAssetTypeCd = "";
        mAssetCategory = "";
        mAssetName = "";
        mAssetNumber = "";
        mSerialNumber = "";
        mModelNumber = "";
        mDateInService = "";
        mStatus = "";
        mManufName = "";
        mManufSku = "";
        mManufType = "";
    }

    /**
     * Constructor. 
     */
    public AssetView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, HashMap parm10, String parm11, String parm12, String parm13, String parm14, int parm15, int parm16)
    {
        mAssetId = parm1;
        mParentId = parm2;
        mAssetTypeCd = parm3;
        mAssetCategory = parm4;
        mAssetName = parm5;
        mAssetNumber = parm6;
        mSerialNumber = parm7;
        mModelNumber = parm8;
        mDateInService = parm9;
        mSiteInfo = parm10;
        mStatus = parm11;
        mManufName = parm12;
        mManufSku = parm13;
        mManufType = parm14;
        mManufId = parm15;
        mMatchedAssetId = parm16;
        
    }

    /**
     * Creates a new AssetView
     *
     * @return
     *  Newly initialized AssetView object.
     */
    public static AssetView createValue () 
    {
        AssetView valueView = new AssetView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetView object
     */
    public String toString()
    {
        return "[" + "AssetId=" + mAssetId + ", ParentId=" + mParentId + ", AssetTypeCd=" + mAssetTypeCd + ", AssetCategory=" + mAssetCategory + ", AssetName=" + mAssetName + ", AssetNumber=" + mAssetNumber + ", SerialNumber=" + mSerialNumber + ", ModelNumber=" + mModelNumber + ", DateInService=" + mDateInService + ", SiteInfo=" + mSiteInfo + ", Status=" + mStatus + ", ManufName=" + mManufName + ", ManufSku=" + mManufSku + ", ManufType=" + mManufType + ", ManufId=" + mManufId + ", MatchedAssetId=" + mMatchedAssetId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Asset");
	root.setAttribute("Id", String.valueOf(mAssetId));

	Element node;

        node = doc.createElement("ParentId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentId)));
        root.appendChild(node);

        node = doc.createElement("AssetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetTypeCd)));
        root.appendChild(node);

        node = doc.createElement("AssetCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetCategory)));
        root.appendChild(node);

        node = doc.createElement("AssetName");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetName)));
        root.appendChild(node);

        node = doc.createElement("AssetNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetNumber)));
        root.appendChild(node);

        node = doc.createElement("SerialNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mSerialNumber)));
        root.appendChild(node);

        node = doc.createElement("ModelNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mModelNumber)));
        root.appendChild(node);

        node = doc.createElement("DateInService");
        node.appendChild(doc.createTextNode(String.valueOf(mDateInService)));
        root.appendChild(node);

        node = doc.createElement("SiteInfo");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteInfo)));
        root.appendChild(node);

        node = doc.createElement("Status");
        node.appendChild(doc.createTextNode(String.valueOf(mStatus)));
        root.appendChild(node);

        node = doc.createElement("ManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufName)));
        root.appendChild(node);

        node = doc.createElement("ManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufSku)));
        root.appendChild(node);

        node = doc.createElement("ManufType");
        node.appendChild(doc.createTextNode(String.valueOf(mManufType)));
        root.appendChild(node);

        node = doc.createElement("ManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mManufId)));
        root.appendChild(node);

        node = doc.createElement("MatchedAssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mMatchedAssetId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AssetView copy()  {
      AssetView obj = new AssetView();
      obj.setAssetId(mAssetId);
      obj.setParentId(mParentId);
      obj.setAssetTypeCd(mAssetTypeCd);
      obj.setAssetCategory(mAssetCategory);
      obj.setAssetName(mAssetName);
      obj.setAssetNumber(mAssetNumber);
      obj.setSerialNumber(mSerialNumber);
      obj.setModelNumber(mModelNumber);
      obj.setDateInService(mDateInService);
      obj.setSiteInfo(mSiteInfo);
      obj.setStatus(mStatus);
      obj.setManufName(mManufName);
      obj.setManufSku(mManufSku);
      obj.setManufType(mManufType);
      obj.setManufId(mManufId);
      obj.setMatchedAssetId(mMatchedAssetId);
      
      return obj;
    }

    
    /**
     * Sets the AssetId property.
     *
     * @param pAssetId
     *  int to use to update the property.
     */
    public void setAssetId(int pAssetId){
        this.mAssetId = pAssetId;
    }
    /**
     * Retrieves the AssetId property.
     *
     * @return
     *  int containing the AssetId property.
     */
    public int getAssetId(){
        return mAssetId;
    }


    /**
     * Sets the ParentId property.
     *
     * @param pParentId
     *  int to use to update the property.
     */
    public void setParentId(int pParentId){
        this.mParentId = pParentId;
    }
    /**
     * Retrieves the ParentId property.
     *
     * @return
     *  int containing the ParentId property.
     */
    public int getParentId(){
        return mParentId;
    }


    /**
     * Sets the AssetTypeCd property.
     *
     * @param pAssetTypeCd
     *  String to use to update the property.
     */
    public void setAssetTypeCd(String pAssetTypeCd){
        this.mAssetTypeCd = pAssetTypeCd;
    }
    /**
     * Retrieves the AssetTypeCd property.
     *
     * @return
     *  String containing the AssetTypeCd property.
     */
    public String getAssetTypeCd(){
        return mAssetTypeCd;
    }


    /**
     * Sets the AssetCategory property.
     *
     * @param pAssetCategory
     *  String to use to update the property.
     */
    public void setAssetCategory(String pAssetCategory){
        this.mAssetCategory = pAssetCategory;
    }
    /**
     * Retrieves the AssetCategory property.
     *
     * @return
     *  String containing the AssetCategory property.
     */
    public String getAssetCategory(){
        return mAssetCategory;
    }


    /**
     * Sets the AssetName property.
     *
     * @param pAssetName
     *  String to use to update the property.
     */
    public void setAssetName(String pAssetName){
        this.mAssetName = pAssetName;
    }
    /**
     * Retrieves the AssetName property.
     *
     * @return
     *  String containing the AssetName property.
     */
    public String getAssetName(){
        return mAssetName;
    }


    /**
     * Sets the AssetNumber property.
     *
     * @param pAssetNumber
     *  String to use to update the property.
     */
    public void setAssetNumber(String pAssetNumber){
        this.mAssetNumber = pAssetNumber;
    }
    /**
     * Retrieves the AssetNumber property.
     *
     * @return
     *  String containing the AssetNumber property.
     */
    public String getAssetNumber(){
        return mAssetNumber;
    }


    /**
     * Sets the SerialNumber property.
     *
     * @param pSerialNumber
     *  String to use to update the property.
     */
    public void setSerialNumber(String pSerialNumber){
        this.mSerialNumber = pSerialNumber;
    }
    /**
     * Retrieves the SerialNumber property.
     *
     * @return
     *  String containing the SerialNumber property.
     */
    public String getSerialNumber(){
        return mSerialNumber;
    }


    /**
     * Sets the ModelNumber property.
     *
     * @param pModelNumber
     *  String to use to update the property.
     */
    public void setModelNumber(String pModelNumber){
        this.mModelNumber = pModelNumber;
    }
    /**
     * Retrieves the ModelNumber property.
     *
     * @return
     *  String containing the ModelNumber property.
     */
    public String getModelNumber(){
        return mModelNumber;
    }


    /**
     * Sets the DateInService property.
     *
     * @param pDateInService
     *  String to use to update the property.
     */
    public void setDateInService(String pDateInService){
        this.mDateInService = pDateInService;
    }
    /**
     * Retrieves the DateInService property.
     *
     * @return
     *  String containing the DateInService property.
     */
    public String getDateInService(){
        return mDateInService;
    }


    /**
     * Sets the SiteInfo property.
     *
     * @param pSiteInfo
     *  HashMap to use to update the property.
     */
    public void setSiteInfo(HashMap pSiteInfo){
        this.mSiteInfo = pSiteInfo;
    }
    /**
     * Retrieves the SiteInfo property.
     *
     * @return
     *  HashMap containing the SiteInfo property.
     */
    public HashMap getSiteInfo(){
        return mSiteInfo;
    }


    /**
     * Sets the Status property.
     *
     * @param pStatus
     *  String to use to update the property.
     */
    public void setStatus(String pStatus){
        this.mStatus = pStatus;
    }
    /**
     * Retrieves the Status property.
     *
     * @return
     *  String containing the Status property.
     */
    public String getStatus(){
        return mStatus;
    }


    /**
     * Sets the ManufName property.
     *
     * @param pManufName
     *  String to use to update the property.
     */
    public void setManufName(String pManufName){
        this.mManufName = pManufName;
    }
    /**
     * Retrieves the ManufName property.
     *
     * @return
     *  String containing the ManufName property.
     */
    public String getManufName(){
        return mManufName;
    }


    /**
     * Sets the ManufSku property.
     *
     * @param pManufSku
     *  String to use to update the property.
     */
    public void setManufSku(String pManufSku){
        this.mManufSku = pManufSku;
    }
    /**
     * Retrieves the ManufSku property.
     *
     * @return
     *  String containing the ManufSku property.
     */
    public String getManufSku(){
        return mManufSku;
    }


    /**
     * Sets the ManufType property.
     *
     * @param pManufType
     *  String to use to update the property.
     */
    public void setManufType(String pManufType){
        this.mManufType = pManufType;
    }
    /**
     * Retrieves the ManufType property.
     *
     * @return
     *  String containing the ManufType property.
     */
    public String getManufType(){
        return mManufType;
    }


    /**
     * Sets the ManufId property.
     *
     * @param pManufId
     *  int to use to update the property.
     */
    public void setManufId(int pManufId){
        this.mManufId = pManufId;
    }
    /**
     * Retrieves the ManufId property.
     *
     * @return
     *  int containing the ManufId property.
     */
    public int getManufId(){
        return mManufId;
    }


    /**
     * Sets the MatchedAssetId property.
     *
     * @param pMatchedAssetId
     *  int to use to update the property.
     */
    public void setMatchedAssetId(int pMatchedAssetId){
        this.mMatchedAssetId = pMatchedAssetId;
    }
    /**
     * Retrieves the MatchedAssetId property.
     *
     * @return
     *  int containing the MatchedAssetId property.
     */
    public int getMatchedAssetId(){
        return mMatchedAssetId;
    }


    
}
