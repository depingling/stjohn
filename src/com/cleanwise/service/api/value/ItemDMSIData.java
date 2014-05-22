
package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

public class ItemDMSIData extends ValueObject implements Cloneable
{
    private static final long serialVersionUID = 6695301648752772305L;

    private String mItemName;
    private String mManufacturerName;
    private String mManufacturerSku;
    private int mPack;    
    private String mUOM;
    private String mColor;
    private int mCustomerReferenceCode;
    private String mDistributorName;  
    private String mDistributorSku;    
    private int mDistributorPack;
    private String mDistributorUOM;
    private String mPrice;
    private String mCategory;
    
    public ItemDMSIData ()
    {
        mItemName = "";
        mManufacturerName = "";
        mManufacturerSku = "";
        mPack = 0;    
        mUOM = "";
        mColor = "";
        mCustomerReferenceCode = 0;
        mDistributorName = ""; 
        mDistributorSku = "";   
        mDistributorPack = 0;
        mDistributorUOM = "";
        mPrice = "";
        mCategory = "";
    }

    public ItemDMSIData(String parm1, String parm2, String parm3, int parm4, String parm5, String parm6, int parm7, String parm8, int parm9, String parm10, String parm11, String parm12)
    {
    	mItemName = parm1;
    	mManufacturerName = parm2;
    	mManufacturerSku = parm3;
    	mPack = parm4;
    	mUOM = parm5;
    	mColor = parm6;
    	mCustomerReferenceCode = parm7;
    	mDistributorSku = parm8;
    	mDistributorPack = parm9;
    	mDistributorUOM = parm10;
    	mPrice = parm11;
    	mCategory = parm12;
    }

    /**
     * Creates a new ItemData
     *
     * @return
     *  Newly initialized ItemData object.
     */
    public static ItemDMSIData createValue ()
    {
        ItemDMSIData valueData = new ItemDMSIData();

        return valueData;
    }

    public String getCategory() {
		return mCategory;
	}

	public void setCategory(String category) {
		mCategory = category;
	}

	public String getColor() {
		return mColor;
	}

	public void setColor(String color) {
		mColor = color;
	}

	public int getCustomerReferenceCode() {
		return mCustomerReferenceCode;
	}

	public void setCustomerReferenceCode(int customerReferenceCode) {
		mCustomerReferenceCode = customerReferenceCode;
	}

	public int getDistributorPack() {
		return mDistributorPack;
	}

	public void setDistributorPack(int distributorPack) {
		mDistributorPack = distributorPack;
	}

	public String getDistributorName() {
		return mDistributorName;
	}

	public void setDistributorName(String distributorName) {
		mDistributorName = distributorName;
	}

	public String getDistributorSku() {
		return mDistributorSku;
	}

	public void setDistributorSku(String distributorSku) {
		mDistributorSku = distributorSku;
	}

	public String getDistributorUOM() {
		return mDistributorUOM;
	}

	public void setDistributorUOM(String distributorUOM) {
		mDistributorUOM = distributorUOM;
	}

	public String getItemName() {
		return mItemName;
	}

	public void setItemName(String itemName) {
		mItemName = itemName;
	}

	public String getManufacturerName() {
		return mManufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		mManufacturerName = manufacturerName;
	}

	public String getManufacturerSku() {
		return mManufacturerSku;
	}

	public void setManufacturerSku(String manufacturerSku) {
		mManufacturerSku = manufacturerSku;
	}

	public int getPack() {
		return mPack;
	}

	public void setPack(int pack) {
		mPack = pack;
	}

	public String getPrice() {
		return mPrice;
	}

	public void setPrice(String price) {
		mPrice = price;
	}

	public String getUOM() {
		return mUOM;
	}

	public void setUOM(String muom) {
		mUOM = muom;
	}

   
}
