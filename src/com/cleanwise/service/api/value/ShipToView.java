
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShipToView
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
 * <code>ShipToView</code> is a ViewObject class for UI.
 */
public class ShipToView
extends ValueObject
{
   
    private static final long serialVersionUID = -8808763113537230695L;
    private int mCatalogId;
    private String mCatalogName;
    private int mDistId;
    private String mDistErp;
    private String mDistName;
    private int mAcctId;
    private String mAcctErp;
    private String mAcctName;
    private int mSiteId;
    private String mSiteErp;
    private String mSiteName;
    private String mName1;
    private String mName2;
    private String mAddress1;
    private String mAddress2;
    private String mAddress3;
    private String mAddress4;
    private String mCity;
    private String mStateProvinceCd;
    private String mCountryCd;
    private String mCountyCd;
    private String mPostalCode;

    /**
     * Constructor.
     */
    public ShipToView ()
    {
        mCatalogName = "";
        mDistErp = "";
        mDistName = "";
        mAcctErp = "";
        mAcctName = "";
        mSiteErp = "";
        mSiteName = "";
        mName1 = "";
        mName2 = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mStateProvinceCd = "";
        mCountryCd = "";
        mCountyCd = "";
        mPostalCode = "";
    }

    /**
     * Constructor. 
     */
    public ShipToView(int parm1, String parm2, int parm3, String parm4, String parm5, int parm6, String parm7, String parm8, int parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22)
    {
        mCatalogId = parm1;
        mCatalogName = parm2;
        mDistId = parm3;
        mDistErp = parm4;
        mDistName = parm5;
        mAcctId = parm6;
        mAcctErp = parm7;
        mAcctName = parm8;
        mSiteId = parm9;
        mSiteErp = parm10;
        mSiteName = parm11;
        mName1 = parm12;
        mName2 = parm13;
        mAddress1 = parm14;
        mAddress2 = parm15;
        mAddress3 = parm16;
        mAddress4 = parm17;
        mCity = parm18;
        mStateProvinceCd = parm19;
        mCountryCd = parm20;
        mCountyCd = parm21;
        mPostalCode = parm22;
        
    }

    /**
     * Creates a new ShipToView
     *
     * @return
     *  Newly initialized ShipToView object.
     */
    public static ShipToView createValue () 
    {
        ShipToView valueView = new ShipToView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShipToView object
     */
    public String toString()
    {
        return "[" + "CatalogId=" + mCatalogId + ", CatalogName=" + mCatalogName + ", DistId=" + mDistId + ", DistErp=" + mDistErp + ", DistName=" + mDistName + ", AcctId=" + mAcctId + ", AcctErp=" + mAcctErp + ", AcctName=" + mAcctName + ", SiteId=" + mSiteId + ", SiteErp=" + mSiteErp + ", SiteName=" + mSiteName + ", Name1=" + mName1 + ", Name2=" + mName2 + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", CountryCd=" + mCountryCd + ", CountyCd=" + mCountyCd + ", PostalCode=" + mPostalCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ShipTo");
	root.setAttribute("Id", String.valueOf(mCatalogId));

	Element node;

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("DistErp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErp)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("AcctId");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctId)));
        root.appendChild(node);

        node = doc.createElement("AcctErp");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctErp)));
        root.appendChild(node);

        node = doc.createElement("AcctName");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctName)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("SiteErp");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteErp)));
        root.appendChild(node);

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node = doc.createElement("Name1");
        node.appendChild(doc.createTextNode(String.valueOf(mName1)));
        root.appendChild(node);

        node = doc.createElement("Name2");
        node.appendChild(doc.createTextNode(String.valueOf(mName2)));
        root.appendChild(node);

        node = doc.createElement("Address1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress1)));
        root.appendChild(node);

        node = doc.createElement("Address2");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress2)));
        root.appendChild(node);

        node = doc.createElement("Address3");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress3)));
        root.appendChild(node);

        node = doc.createElement("Address4");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress4)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node = doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        node = doc.createElement("CountyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountyCd)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ShipToView copy()  {
      ShipToView obj = new ShipToView();
      obj.setCatalogId(mCatalogId);
      obj.setCatalogName(mCatalogName);
      obj.setDistId(mDistId);
      obj.setDistErp(mDistErp);
      obj.setDistName(mDistName);
      obj.setAcctId(mAcctId);
      obj.setAcctErp(mAcctErp);
      obj.setAcctName(mAcctName);
      obj.setSiteId(mSiteId);
      obj.setSiteErp(mSiteErp);
      obj.setSiteName(mSiteName);
      obj.setName1(mName1);
      obj.setName2(mName2);
      obj.setAddress1(mAddress1);
      obj.setAddress2(mAddress2);
      obj.setAddress3(mAddress3);
      obj.setAddress4(mAddress4);
      obj.setCity(mCity);
      obj.setStateProvinceCd(mStateProvinceCd);
      obj.setCountryCd(mCountryCd);
      obj.setCountyCd(mCountyCd);
      obj.setPostalCode(mPostalCode);
      
      return obj;
    }

    
    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the CatalogName property.
     *
     * @param pCatalogName
     *  String to use to update the property.
     */
    public void setCatalogName(String pCatalogName){
        this.mCatalogName = pCatalogName;
    }
    /**
     * Retrieves the CatalogName property.
     *
     * @return
     *  String containing the CatalogName property.
     */
    public String getCatalogName(){
        return mCatalogName;
    }


    /**
     * Sets the DistId property.
     *
     * @param pDistId
     *  int to use to update the property.
     */
    public void setDistId(int pDistId){
        this.mDistId = pDistId;
    }
    /**
     * Retrieves the DistId property.
     *
     * @return
     *  int containing the DistId property.
     */
    public int getDistId(){
        return mDistId;
    }


    /**
     * Sets the DistErp property.
     *
     * @param pDistErp
     *  String to use to update the property.
     */
    public void setDistErp(String pDistErp){
        this.mDistErp = pDistErp;
    }
    /**
     * Retrieves the DistErp property.
     *
     * @return
     *  String containing the DistErp property.
     */
    public String getDistErp(){
        return mDistErp;
    }


    /**
     * Sets the DistName property.
     *
     * @param pDistName
     *  String to use to update the property.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
    }
    /**
     * Retrieves the DistName property.
     *
     * @return
     *  String containing the DistName property.
     */
    public String getDistName(){
        return mDistName;
    }


    /**
     * Sets the AcctId property.
     *
     * @param pAcctId
     *  int to use to update the property.
     */
    public void setAcctId(int pAcctId){
        this.mAcctId = pAcctId;
    }
    /**
     * Retrieves the AcctId property.
     *
     * @return
     *  int containing the AcctId property.
     */
    public int getAcctId(){
        return mAcctId;
    }


    /**
     * Sets the AcctErp property.
     *
     * @param pAcctErp
     *  String to use to update the property.
     */
    public void setAcctErp(String pAcctErp){
        this.mAcctErp = pAcctErp;
    }
    /**
     * Retrieves the AcctErp property.
     *
     * @return
     *  String containing the AcctErp property.
     */
    public String getAcctErp(){
        return mAcctErp;
    }


    /**
     * Sets the AcctName property.
     *
     * @param pAcctName
     *  String to use to update the property.
     */
    public void setAcctName(String pAcctName){
        this.mAcctName = pAcctName;
    }
    /**
     * Retrieves the AcctName property.
     *
     * @return
     *  String containing the AcctName property.
     */
    public String getAcctName(){
        return mAcctName;
    }


    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
    }


    /**
     * Sets the SiteErp property.
     *
     * @param pSiteErp
     *  String to use to update the property.
     */
    public void setSiteErp(String pSiteErp){
        this.mSiteErp = pSiteErp;
    }
    /**
     * Retrieves the SiteErp property.
     *
     * @return
     *  String containing the SiteErp property.
     */
    public String getSiteErp(){
        return mSiteErp;
    }


    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
    }


    /**
     * Sets the Name1 property.
     *
     * @param pName1
     *  String to use to update the property.
     */
    public void setName1(String pName1){
        this.mName1 = pName1;
    }
    /**
     * Retrieves the Name1 property.
     *
     * @return
     *  String containing the Name1 property.
     */
    public String getName1(){
        return mName1;
    }


    /**
     * Sets the Name2 property.
     *
     * @param pName2
     *  String to use to update the property.
     */
    public void setName2(String pName2){
        this.mName2 = pName2;
    }
    /**
     * Retrieves the Name2 property.
     *
     * @return
     *  String containing the Name2 property.
     */
    public String getName2(){
        return mName2;
    }


    /**
     * Sets the Address1 property.
     *
     * @param pAddress1
     *  String to use to update the property.
     */
    public void setAddress1(String pAddress1){
        this.mAddress1 = pAddress1;
    }
    /**
     * Retrieves the Address1 property.
     *
     * @return
     *  String containing the Address1 property.
     */
    public String getAddress1(){
        return mAddress1;
    }


    /**
     * Sets the Address2 property.
     *
     * @param pAddress2
     *  String to use to update the property.
     */
    public void setAddress2(String pAddress2){
        this.mAddress2 = pAddress2;
    }
    /**
     * Retrieves the Address2 property.
     *
     * @return
     *  String containing the Address2 property.
     */
    public String getAddress2(){
        return mAddress2;
    }


    /**
     * Sets the Address3 property.
     *
     * @param pAddress3
     *  String to use to update the property.
     */
    public void setAddress3(String pAddress3){
        this.mAddress3 = pAddress3;
    }
    /**
     * Retrieves the Address3 property.
     *
     * @return
     *  String containing the Address3 property.
     */
    public String getAddress3(){
        return mAddress3;
    }


    /**
     * Sets the Address4 property.
     *
     * @param pAddress4
     *  String to use to update the property.
     */
    public void setAddress4(String pAddress4){
        this.mAddress4 = pAddress4;
    }
    /**
     * Retrieves the Address4 property.
     *
     * @return
     *  String containing the Address4 property.
     */
    public String getAddress4(){
        return mAddress4;
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


    
}
