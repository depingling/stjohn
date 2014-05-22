package com.cleanwise.service.api.value;

/**
 * Title:        PollockItemView
 * Description:  This is a ViewObject class for UI.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;

/**
 * <code>PollockItemView</code> is a ViewObject class for UI.
 */
public class PollockItemView
extends ValueObject
{
   
    private static final long serialVersionUID = 6123314562696354531L;
	private String versiionNumber;
	private String actionCd;
	private String assetCd;
	private String storeID;
	private String storeName;
	private String distSku;
	private String mfgSku;
	private String manufacturerName;
	private String distributorName;
	private String packCd;
	private String uomCd;
	private String categoryName;
	private String subcat1Cd;
	private String subcat2Cd;
	private String subcat3Cd;
	private String multiProductName;
	private String itemSize;
	private String longDesc;
	private String shortDesc;
	private String productUpc;
	private String packUpc;
	private String unspscCode;
	private String colorCd;
	private String shippingWeight;
	private String weightUnit;
	private String nsnCd;
	private String shippingCubicSize;
	private String hazmatCd;
	private String certifiedCompanies;
	private String imageCd;
	private String msdsCd;
	private String specificationCd;
	private String assetName;
	private String modelNumber;
	private String associateDoc1;
	private String associateDoc2;
	private String associateDoc3;
	private String sortOrderMain;
	private String subCat1Order;
	private String subCat2Order;
	private String subCat3Order;


    /**
     * Constructor.
     */
    public PollockItemView ()
    {
		versiionNumber = "";
		actionCd = "";
		assetCd = "";
		storeID = "";
		storeName = "";
		distSku = "";
		mfgSku = "";
		manufacturerName = "";
		distributorName = "";
		packCd = "";
		uomCd = "";
		categoryName = "";
		subcat1Cd = "";
		subcat2Cd = "";
		subcat3Cd = "";
		multiProductName = "";
		itemSize = "";
		longDesc = "";
		shortDesc = "";
		productUpc = "";
		packUpc = "";
		unspscCode = "";
		colorCd = "";
		shippingWeight = "";
		weightUnit = "";
		nsnCd = "";
		shippingCubicSize = "";
		hazmatCd = "";
		certifiedCompanies = "";
		imageCd = "";
		msdsCd = "";
		specificationCd = "";
		assetName = "";
		modelNumber = "";
		associateDoc1 = "";
		associateDoc2 = "";
		associateDoc3 = "";
		sortOrderMain = "";
		subCat1Order = "";
		subCat2Order = "";
		subCat3Order = "";
    }

    /**
     * Constructor. 
     */
    public PollockItemView(String parm1, String parm2, String parm3, String parm4,String parm5,String parm6,String parm7,String parm8,String parm9,String parm10,String parm11,String parm12,String parm13,String parm14,String parm15,String parm16,String parm17,String parm18,String parm19,String parm20,String parm21,String parm22,String parm23,String parm24,String parm25,String parm26,String parm27,String parm28,String parm29,String parm30,String parm31,String parm32,String parm33,String parm34,String parm35,String parm36,String parm37,String parm38,String parm39,String parm40,String parm41)
    {
        versiionNumber = parm1;
		actionCd = parm2;
		assetCd = parm3;
		storeID = parm4;
		storeName = parm5;
		distSku = parm6;
		mfgSku = parm7;
		manufacturerName = parm8;
		distributorName = parm9;
		packCd = parm10;
		uomCd = parm11;
		categoryName = parm12;
		subcat1Cd = parm13;
		subcat2Cd = parm14;
		subcat3Cd = parm15;
		multiProductName = parm16;
		itemSize = parm17;
		longDesc = parm18;
		shortDesc = parm19;
		productUpc = parm20;
		packUpc = parm21;
		unspscCode = parm22;
		colorCd = parm23;
		shippingWeight = parm24;
		weightUnit = parm25;
		nsnCd = parm26;
		shippingCubicSize = parm27;
		hazmatCd = parm28;
		certifiedCompanies = parm29;
		imageCd = parm30;
		msdsCd = parm31;
		specificationCd = parm32;
		assetName = parm33;
		modelNumber = parm34;
		associateDoc1 = parm35;
		associateDoc2 = parm36;
		associateDoc3 = parm37;
		sortOrderMain = parm38;
		subCat1Order  = parm39;
		subCat2Order  = parm40;
		subCat3Order  = parm41;
        
    }

    /**
     * Creates a new PollockItemView
     *
     * @return
     *  Newly initialized PollockItemView object.
     */
    public static PollockItemView createValue () 
    {
        PollockItemView valueView = new PollockItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PollockItemView object
     */
    public String toString()
    {
        return "[" + "versiionNumber=" + versiionNumber + "actionCd=" + actionCd + "assetCd=" + assetCd + "storeID=" + storeID + "storeName=" +
			storeName + "distSku=" + distSku + "mfgSku=" + mfgSku + "manufacturerName=" + manufacturerName + "distributorName=" +
			distributorName + "packCd=" + packCd + "uomCd=" + uomCd + "categoryName="  + categoryName + "subcat1Cd=" + subcat1Cd +
			"subcat2Cd=" + subcat2Cd + "subcat3Cd=" + subcat3Cd + "multiProductName=" + multiProductName + "itemSize=" + itemSize +
			"longDesc=" + longDesc + "shortDesc=" + shortDesc + "productUpc=" + productUpc + "packUpc=" + packUpc + "unspscCode=" +
			unspscCode + "colorCd=" + colorCd + "shippingWeight=" + shippingWeight + "weightUnit=" + weightUnit + "nsnCd=" + nsnCd +
			"shippingCubicSize=" + shippingCubicSize + "hazmatCd=" + hazmatCd + "certifiedCompanies=" + certifiedCompanies +
			"imageCd=" + imageCd + "msdsCd=" + msdsCd + "specificationCd=" + specificationCd + "assetName=" + assetName +
			"modelNumber=" + modelNumber + "associateDoc1=" + associateDoc1 + "associateDoc2=" + associateDoc2 + 
			"associateDoc3=" + associateDoc3 + "sortOrderMain=" + sortOrderMain + "subCat1Order =" + subCat1Order +
			"subCat2Order =" + subCat2Order + "subCat3Order =" + subCat3Order + "]";
    }


    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PollockItemView copy()  {
      PollockItemView obj = new PollockItemView();
		obj.setversiionNumber(versiionNumber);
		obj.setactionCd(actionCd);
		obj.setassetCd(assetCd);
		obj.setstoreID(storeID);
		obj.setstoreName(storeName);
		obj.setdistSku(distSku);
		obj.setmfgSku(mfgSku);
		obj.setmanufacturerName(manufacturerName);
		obj.setdistributorName(distributorName);
		obj.setpackCd(packCd);
		obj.setuomCd(uomCd);
		obj.setcategoryName(categoryName);
		obj.setsubcat1Cd(subcat1Cd);
		obj.setsubcat2Cd(subcat2Cd);
		obj.setsubcat3Cd(subcat3Cd);
		obj.setmultiProductName(multiProductName);
		obj.setitemSize(itemSize);
		obj.setlongDesc(longDesc);
		obj.setshortDesc(shortDesc);
		obj.setproductUpc(productUpc);
		obj.setpackUpc(packUpc);
		obj.setunspscCode(unspscCode);
		obj.setcolorCd(colorCd);
		obj.setshippingWeight(shippingWeight);
		obj.setweightUnit(weightUnit);
		obj.setnsnCd(nsnCd);
		obj.setshippingCubicSize(shippingCubicSize);
		obj.sethazmatCd(hazmatCd);
		obj.setcertifiedCompanies(certifiedCompanies);
		obj.setimageCd(imageCd);
		obj.setmsdsCd(msdsCd);
		obj.setspecificationCd(specificationCd);
		obj.setassetName(assetName);
		obj.setmodelNumber(modelNumber);
		obj.setassociateDoc1(associateDoc1);
		obj.setassociateDoc2(associateDoc2);
		obj.setassociateDoc3(associateDoc3);
		obj.setsortOrderMain(sortOrderMain);
		obj.setsubCat1Order(subCat1Order);
		obj.setsubCat2Order(subCat2Order);
		obj.setsubCat3Order(subCat3Order);
      
      return obj;
    }

    
   public void setversiionNumber(String versiionNumber){
	this.versiionNumber = versiionNumber;
	}

	public String getversiionNumber() {
	return versiionNumber;
	}


		
	public void setactionCd(String actionCd){
	this.actionCd = actionCd;
	}

	public String getactionCd(){
	return actionCd;
	}


	public void setassetCd(String assetCd){
	this.assetCd = assetCd;
	}


	public String getassetCd() {
	return assetCd;
	}



	public void setstoreID(String storeID){ 
	this.storeID = storeID;
	}

	public String getstoreID() {
	return storeID;
	}





	public void setstoreName(String storeName){
	this.storeName = storeName;
	}

	public String getstoreName() {
	return storeName;
	}





	public void setdistSku(String distSku){
	this.distSku = distSku;
	}

	public String getdistSku() {
	return distSku;
	}




	public void setmfgSku(String mfgSku){ 
	this.mfgSku = mfgSku;
	}

	public String getmfgSku() {
	return mfgSku;
	}




	public void setmanufacturerName(String manufacturerName){ 
	this.manufacturerName = manufacturerName;
	}

	public String getmanufacturerName() {
	return manufacturerName;
	}




	public void setdistributorName(String distributorName){ 
	this.distributorName = distributorName;
	}

	public String getdistributorName() {
	return distributorName;
	}




	public void setpackCd(String packCd){ 
	this.packCd = packCd;
	}

	public String getpackCd() {
	return packCd;
	}




	public void setuomCd(String uomCd){
	this.uomCd = uomCd;
	}

	public String getuomCd() {
	return uomCd;
	}





	public void setcategoryName(String categoryName){
	this.categoryName = categoryName;
	}

	public String getcategoryName() {
	return categoryName;
	}




	public void setsubcat1Cd(String subcat1Cd){ 
	this.subcat1Cd = subcat1Cd;
	}

	public String getsubcat1Cd() {
	return subcat1Cd;
	}




	public void setsubcat2Cd(String subcat2Cd){
	this.subcat2Cd = subcat2Cd;
	}

	public String getsubcat2Cd() {
	return subcat2Cd;
	}




	public void setsubcat3Cd(String subcat3Cd){ 
	this.subcat3Cd = subcat3Cd;
	}

	public String getsubcat3Cd() {
	return subcat3Cd;
	}




	public void setmultiProductName(String multiProductName){ 
	this.multiProductName = multiProductName;
	}

	public String getmultiProductName() {
	return multiProductName;
	}




	public void setitemSize(String itemSize){ 
	this.itemSize = itemSize;
	}

	public String getitemSize() {
	return itemSize;
	}




	public void setlongDesc(String longDesc){
	this.longDesc = longDesc;
	}

	public String getlongDesc() {
	return longDesc;
	}




	public void setshortDesc(String shortDesc){ 
	this.shortDesc = shortDesc ;
	}

	public String getshortDesc() {
	return shortDesc;
	}




	public void setproductUpc(String productUpc){
	this.productUpc = productUpc;
	}

	public String getproductUpc() {
	return productUpc;
	}




	public void setpackUpc(String packUpc){
	this.packUpc = packUpc;
	}

	public String getpackUpc() {
	return packUpc;
	}




	public void setunspscCode(String unspscCode){ 
	this.unspscCode = unspscCode;
	}

	public String getunspscCode() {
	return unspscCode;
	}




	public void setcolorCd(String colorCd){ 
	this.colorCd = colorCd;
	}

	public String getcolorCd() {
	return colorCd;
	}




	public void setshippingWeight(String shippingWeight){
	this.shippingWeight = shippingWeight;
	}

	public String getshippingWeight() {
	return shippingWeight;
	}




	public void setweightUnit(String weightUnit){
	this.weightUnit = weightUnit;
	}

	public String getweightUnit() {
	return weightUnit;
	}




	public void setnsnCd(String nsnCd){
	this.nsnCd = nsnCd;
	}

	public String getnsnCd() {
	return nsnCd;
	}




	public void setshippingCubicSize(String shippingCubicSize){
	this.shippingCubicSize = shippingCubicSize;
	}

	public String getshippingCubicSize() {
	return shippingCubicSize;
	}



	public void sethazmatCd(String hazmatCd){ 
	this.hazmatCd = hazmatCd;
	}

	public String gethazmatCd() {
	return hazmatCd;
	}



	public void setcertifiedCompanies(String certifiedCompanies){
	this.certifiedCompanies = certifiedCompanies;
	}

	public String getcertifiedCompanies() {
	return certifiedCompanies;
	}



	public void setimageCd(String imageCd){
	this.imageCd = imageCd;
	}

	public String getimageCd() {
	return imageCd;
	}



	public void setmsdsCd(String msdsCd){
	this.msdsCd = msdsCd;
	}

	public String getmsdsCd() {
	return msdsCd;
	}



	public void setspecificationCd(String specificationCd){
	this.specificationCd = specificationCd;
	}

	public String getspecificationCd() {
	return specificationCd;
	}



	public void setassetName(String assetName){
	this.assetName = assetName;
	}

	public String getassetName() {
	return assetName;
	}



	public void setmodelNumber(String modelNumber){
	this.modelNumber = modelNumber;
	}

	public String getmodelNumber() {
	return modelNumber;
	}



	public void setassociateDoc1(String associateDoc1){
	this.associateDoc1 = associateDoc1;
	}

	public String getassociateDoc1() {
	return associateDoc1;
	}



	public void setassociateDoc2(String associateDoc2){
	this.associateDoc2 = associateDoc2;
	}

	public String getassociateDoc2() {
	return associateDoc2;
	}



	public void setassociateDoc3(String associateDoc3){
	this.associateDoc3 = associateDoc3;
	}

	public String getassociateDoc3() {
	return associateDoc3;
	}
	
	
	public void setsortOrderMain(String sortOrderMain){
	this.sortOrderMain = sortOrderMain;
	}

	public String getsortOrderMain() {
	return sortOrderMain;
	}
	
	
	public void setsubCat1Order(String subCat1Order){
	this.subCat1Order = subCat1Order;
	}

	public String getsubCat1Order() {
	return subCat1Order;
	}
	
	public void setsubCat2Order(String subCat2Order){
	this.subCat2Order = subCat2Order;
	}

	public String getsubCat2Order() {
	return subCat2Order;
	}
	
	public void setsubCat3Order(String subCat3Order){
	this.subCat3Order = subCat3Order;
	}

	public String getsubCat3Order() {
	return subCat3Order;
	}
	    
}
