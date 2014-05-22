package com.cleanwise.service.api.value;

/**
 * Title:        PollockCatalogView
 * Description:  This is a ViewObject class for UI.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>PollockCatalogView</code> is a ViewObject class for UI.
 */
public class PollockCatalogView
extends ValueObject
{
   
    private static final long serialVersionUID = 62224343662224531L;
    private String versionCd;
	private String storeId;
	private String storeName;
	private String accountName;
	private String accountRefNum;
	private String catalogKey;
	private String catalogName;
	private String orderGuideName;
	private String locale;
	private String distSku;
	private String distributor;
	private String pack;
	private String uom;
	private String cost;
	private String price;
	private String customerSku;
	private String serviceCode;
	private String categoryName;
	private String categoryOrder;
	private String subCat1;
	private String subCat1Order;
	private String subCat2;
	private String subCat2Order;
	private String subCat3;
	private String subCat3Order;
	private String orderGuide;
	private String costCenterKey;
	private String freightTableKey;


    /**
     * Constructor.
     */
    public PollockCatalogView ()
    {
		versionCd = "";
		storeId = "";
		storeName = "";
		accountName = "";
		accountRefNum = "";
		catalogKey = "";
		catalogName = "";
		orderGuideName = "";
		locale = "";
		distSku = "";
		distributor = "";
		pack = "";
		uom = "";
		cost = "";
		price = "";
		customerSku = "";
		serviceCode = "";
		categoryName = "";
		categoryOrder = "";
		subCat1 = "";
		subCat1Order = "";
		subCat2 = "";
		subCat2Order = "";
		subCat3 = "";
		subCat3Order = "";
		orderGuide = "";
		costCenterKey = "";
		freightTableKey = "";
    }

    /**
     * Constructor. 
     */
    public PollockCatalogView(String parm1, String parm2, String parm3, String parm4,String parm5,String parm6,String parm7,String parm8,String parm9,String parm10,String parm11,String parm12, String parm13, String parm14, String parm15,String parm16,String parm17,String parm18,String parm19,String parm20,String parm21,String parm22,String parm23,String parm24,String parm25,String parm26,String parm27,String parm28)
    {
        versionCd = parm1;
		storeId = parm2;
		storeName = parm3;
		accountName = parm4;
		accountRefNum = parm5;
		catalogKey = parm6;
		catalogName = parm7;
		orderGuideName = parm8;
		locale = parm9;
		distSku = parm10;
		distributor = parm11;
		pack = parm12;
		uom = parm13;
		cost = parm14;
		price = parm15;
		customerSku = parm16;
		serviceCode = parm17;
		categoryName = parm18;
		categoryOrder = parm19;
		subCat1 = parm20;
		subCat1Order = parm21;
		subCat2 = parm22;
		subCat2Order = parm23;
		subCat3 = parm24;
		subCat3Order = parm25;
		orderGuide = parm26;
		costCenterKey = parm27;
		freightTableKey = parm28;

        
    }

    /**
     * Creates a new PollockCatalogView
     *
     * @return
     *  Newly initialized PollockCatalogView object.
     */
    public static PollockCatalogView createValue () 
    {
        PollockCatalogView valueView = new PollockCatalogView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PollockCatalogView object
     */
    public String toString()
    {
        return "[" + "versionNumber=" + versionCd + "storeId=" + storeId + "storeName=" +
			storeName + "accountRefNum=" + accountRefNum + "accountName=" + accountName +  "catalogKey=" + catalogKey + "catalogName=" + catalogName +
			"orderGuideName=" + orderGuideName + "locale=" + locale + "distSku=" + distSku + "distributor=" + distributor + 
			"pack=" + pack + "uom=" + uom + "cost=" + cost + "price=" + price + "customerSku=" + customerSku + "serviceCode=" + serviceCode +
			"categoryName=" + categoryName + "categoryOrder=" + categoryOrder + "subCat1=" + subCat1 + "subCat1Order=" + subCat1Order +
			"subCat2=" + subCat2 + "subCat2Order=" + subCat2Order + "subCat3=" + subCat3 + "subCat3Order=" + subCat3Order +
			"orderGuide=" + orderGuide + "costCenterKey=" + costCenterKey + "freightTableKey=" + freightTableKey + "]";
    }


    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PollockCatalogView copy()  {
      PollockCatalogView obj = new PollockCatalogView();
		obj.setversionCd(versionCd);
		obj.setstoreId(storeId);
		obj.setstoreName(storeName);
		obj.setaccountName(accountName);
		obj.setaccountRefNum(accountRefNum);
		obj.setcatalogKey(catalogKey);
		obj.setcatalogName(catalogName);
		obj.setorderGuideName(orderGuideName);
		obj.setlocale(locale);
		obj.setdistSku(distSku);
		obj.setdistributor(distributor);
		obj.setpack(pack);
		obj.setuom(uom);
		obj.setcost(cost);
		obj.setprice(price);
		obj.setcustomerSku(customerSku);
		obj.setserviceCode(serviceCode);
		obj.setcategoryName(categoryName);
		obj.setcategoryOrder(categoryOrder);
		obj.setsubCat1(subCat1);
		obj.setsubCat1Order(subCat1Order);
		obj.setsubCat2(subCat2);
		obj.setsubCat2Order(subCat2Order);
		obj.setsubCat3(subCat3);
		obj.setsubCat3Order(subCat3Order);
		obj.setorderGuide(orderGuide);
		obj.setcostCenterKey(costCenterKey);
		obj.setfreightTableKey(freightTableKey);

		
      
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

	public String getstoreId() {
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
	
	public void setcatalogKey(String catalogKey){
	this.catalogKey = catalogKey;
	}
	
	public String getcatalogKey(){
	return catalogKey;
	}
	
	public void setcatalogName(String catalogName){
		this.catalogName = catalogName;
	}
	
	public String getcatalogName(){
		return catalogName;
	}
		
		
	public void setorderGuideName(String orderGuideName){
		this.orderGuideName = orderGuideName;
	}
		
	public String getorderGuideName(){
	return orderGuideName;
	}	
		
	public void setlocale(String locale){
		this.locale = locale;
	}
	
    public String getlocale(){
	return locale;
	}

			
	public void setdistSku(String distSku){
		this.distSku = distSku;
	}
		
	public String getdistSku(){
	return distSku;
	}	

	public void setdistributor(String distributor){
		this.distributor = distributor;
	}
	
	public String getdistributor(){
	return distributor;
	}

	
	public void setpack(String pack){
		this.pack = pack;
	}
		
	public String getpack(){
	return pack;
	}	
	
	
	public void setuom(String uom){
		this.uom = uom;
	}
	
	public String getuom(){
	return uom;
	}
		
		
	public void setcost(String cost){
		this.cost = cost;
	}
		
	public String getcost(){
	return cost;
	}
	
	
	
	public void setprice(String price){
		this.price = price;
	}
		
	public String getprice(){
	return price;
	}

	
	public void setcustomerSku(String customerSku){
		this.customerSku = customerSku;
	}
		
	public String getcustomerSku(){
	return customerSku;
	}


	public void setserviceCode(String serviceCode){
		this.serviceCode = serviceCode;
	}
		
	public String getserviceCode(){
	return serviceCode;
	}
	
	
	public void setcategoryName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public String getcategoryName(){
	return categoryName;
	}
	
	
	public void setcategoryOrder(String categoryOrder){
		this.categoryOrder = categoryOrder;
	}
		
	public String getcategoryOrder(){
	return categoryOrder;
	}
	
	
	public void setsubCat1(String subCat1){
		this.subCat1 = subCat1;
	}
	
	public String getsubCat1(){
	return subCat1;
	}
	
	public void setsubCat1Order(String subCat1Order){
		this.subCat1Order = subCat1Order;
	}
	
	public String getsubCat1Order(){
	return subCat1Order;
	}
		
	
	public void setsubCat2(String subCat2){
		this.subCat2 = subCat2;
	}
		
	
	public String getsubCat2(){
	return subCat2;
	}
	
	
	public void setsubCat2Order(String subCat2Order){
		this.subCat2Order = subCat2Order;
	}
		
	public String getsubCat2Order(){
	return subCat2Order;
	}
	
	
	public void setsubCat3(String subCat3){
		this.subCat3 = subCat3;
	}
	
	public String getsubCat3(){
	return subCat3;
	}
	
	public void setsubCat3Order(String subCat3Order){
		this.subCat3Order = subCat3Order;
	}
	
	public String getsubCat3Order(){
	return subCat3Order;
	}
		
	public void setorderGuide(String orderGuide){
		this.orderGuide = orderGuide;
	}
	
	public String getorderGuide(){
	return orderGuide;
	}
		
	public void setcostCenterKey(String costCenterKey){
		this.costCenterKey = costCenterKey;
	}
		
	public String getcostCenterKey(){
	return costCenterKey;
	}
	
	public void setfreightTableKey(String freightTableKey){
		this.freightTableKey = freightTableKey;
	}
	
	
	public String getfreightTableKey(){
	return freightTableKey;
	}
	
	
}
