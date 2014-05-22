package com.cleanwise.service.api.value;

/**
 * Title:        PollockOrderGuideView
 * Description:  This is a ViewObject class for UI.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>PollockOrderGuideView</code> is a ViewObject class for UI.
 */
public class PollockOrderGuideView
extends ValueObject
{
   
    private static final long serialVersionUID = 61234333333354531L;
	private String versionCd;
	private String storeId;
	private String storeName;
	private String accountRefNum;
	private String accountName;
	private String siteName;
	private String siteRefNum;
	private String catalogId;
	private String orderGuideName;
	private String orderGuideType;
	private String distSku;


    /**
     * Constructor.
     */
    public PollockOrderGuideView ()
    {
		versionCd = "";
		storeId = "";
		storeName = "";
		accountRefNum = "";
		accountName = "";
		siteName = "";
		siteRefNum = "";
		catalogId = "";
		orderGuideName = "";
		orderGuideType = "";
		distSku = "";
    }

    /**
     * Constructor. 
     */
    public PollockOrderGuideView(String parm1, String parm2, String parm3, String parm4,String parm5,String parm6,String parm7,String parm8,String parm9,String parm10,String parm11)
    {
        versionCd = parm1;
		storeId = parm2;
		storeName = parm3;
		accountRefNum = parm4;
		accountName = parm5;
		siteName = parm6;
		siteRefNum = parm7;
		catalogId = parm8;
		orderGuideName = parm9;
		orderGuideType = parm10;
		distSku = parm11;

        
    }

    /**
     * Creates a new PollockOrderGuideView
     *
     * @return
     *  Newly initialized PollockOrderGuideView object.
     */
    public static PollockOrderGuideView createValue () 
    {
        PollockOrderGuideView valueView = new PollockOrderGuideView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PollockOrderGuideView object
     */
    public String toString()
    {
        return "[" + "versionNumber=" + versionCd + "storeID=" + storeId + "storeName=" +
			storeName + "accountRefNum=" + accountRefNum + "accountName=" + accountName + "siteName=" + siteName + "siteRefNum=" + siteRefNum +
			"catalogId=" + catalogId + "orderGuideName=" + orderGuideName + "orderGuideType=" + orderGuideType + "distSku=" + distSku + "]";
    }


    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PollockOrderGuideView copy()  {
      PollockOrderGuideView obj = new PollockOrderGuideView();
		obj.setversionCd(versionCd);
		obj.setstoreId(storeId);
		obj.setstoreName(storeName);
		obj.setaccountRefNum(accountRefNum);
		obj.setaccountName(accountName);
		obj.setsiteName(siteName);
		obj.setsiteRefNum(siteRefNum);
		obj.setcatalogId(catalogId);
		obj.setorderGuideName(orderGuideName);
		obj.setorderGuideType(orderGuideType);
		obj.setdistSku(distSku);

		
      
      return obj;
    }

    
   public void setversionCd(String versionCd){
	this.versionCd = versionCd;
	}

	public String getversionCd() {
	return versionCd;
	}


	public void setstoreId(String storeId){ 
	this.storeId = storeId;
	}

	public String getstoreID() {
	return storeId;
	}


	public void setstoreName(String storeName){
	this.storeName = storeName;
	}

	public String getstoreName() {
	return storeName;
	}

	
	public String getaccountRefNum() { 
	return accountRefNum;
	}
	
	
	public void setaccountRefNum(String accountRefNum){
	this.accountRefNum = accountRefNum;
	}
	
	
	public String getaccountName() { 
	return accountName;
	}
	
	public void setaccountName(String accountName){
	this.accountName = accountName;
	}
	
	public String getsiteName() { 
	return siteName;
	}
	
	public void setsiteName(String siteName){
	this.siteName = siteName;
	}
	
	
	public String getsiteRefNum() { 
	return siteRefNum;
	}
	
	public void setsiteRefNum(String siteRefNum){
	this.siteRefNum = siteRefNum;
	}
	
	public String getcatalogId(){
	return catalogId;
	}
	
	public void setcatalogId(String catalogId){
	this.catalogId = catalogId;
	}
	
	public String getorderGuideName(){
	return orderGuideName;
	}
	
	public void setorderGuideName(String orderGuideName){
	this.orderGuideName = orderGuideName;
	}
	
	
	public String getorderGuideType(){
	return orderGuideType;
	}
	
	public void setorderGuideType(String orderGuideType){
	this.orderGuideType = orderGuideType;
	}
	
	public String getdistSku(){
	return distSku;
	}
	
	public void setdistSku(String distSku){
	this.distSku = distSku;
	}
   
}
