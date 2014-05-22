package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;


public class ItemTxtData extends ValueObject implements Cloneable {
    
    private String nscItemNumber_;
    private String productDescription_;
    private String manufacturer_;
    private String mfgSku_;
    private String defaultUom_;
    private String primaryUom_;
    private String priceUom_;
    private String priceUomConv_;
    private double listPrice1_;
    private String itemDescription1_;
    private String itemDescription2_;
    private String company_;
    private String itemClass_;
    private String itemSubClass_;
    private String extendetItemDescription_;
    private int standartPack_;
    private String catalogName_;
    private String category_;
    private String categorySequence_;
    private String subCategory_;
    private String subCategorySequence_;
    private String nscSku_;
    private String upc_;
    private String customerItemNumber_;
    private String mfgItemNumber_;
    private String origProductDescription_;


    
    public ItemTxtData( String nscItemNumber,
                        String productDescription,
                        String manufacturer,
                        String mfgSku,
                        String defaultUom,
                        String primaryUom,
                        String priceUom,
                        String priceUomConv,
                        double listPrice1,
                        String itemDescription1,
                        String itemDescription2,
                        String company,
                        String itemClass,
                        String itemSubClass,
                        String extendetItemDescription,
                        int standartPack,
                        String catalogName,
                        String category,
                        String categorySequence,
                        String subCategory,
                        String subCategorySequence,
                        String nscSku,
                        String upc,
                        String customerItemNumber,
                        String mfgItemNumber,
                        String origProductDescription ) {
                            
        nscItemNumber_ = nscItemNumber;
        productDescription_ = productDescription;
        manufacturer_ = manufacturer;
        mfgSku_ = mfgSku;
        defaultUom_ = defaultUom;
        primaryUom_ = primaryUom;
        priceUom_ = priceUom;
        priceUomConv_ = priceUomConv;
        listPrice1_ = listPrice1;
        itemDescription1_ = itemDescription1;
        itemDescription2_ = itemDescription2;
        company_ = company;
        itemClass_ = itemClass;
        itemSubClass_ = itemSubClass;
        extendetItemDescription_ = extendetItemDescription;
        standartPack_ = standartPack;
        catalogName_ = catalogName;
        category_ = category;
        categorySequence_ = categorySequence;
        subCategory_ = subCategory;
        subCategorySequence_ = subCategorySequence;
        nscSku_ = nscSku;
        upc_ = upc;
        customerItemNumber_ = customerItemNumber;
        mfgItemNumber_ = mfgItemNumber;
        origProductDescription_ = origProductDescription;
    }
    
    public ItemTxtData() {
        nscItemNumber_ = "";
        productDescription_ = "";
        manufacturer_ = "";
        mfgSku_ = "";
        defaultUom_ = "";
        primaryUom_ = "";
        priceUom_ = "";
        priceUomConv_ = "";
        listPrice1_ = 0.0;
        itemDescription1_ = "";
        itemDescription2_ = "";
        company_ = "";
        itemClass_ = "";
        itemSubClass_ = "";
        extendetItemDescription_ = "";
        standartPack_ = 1;
        catalogName_ = "";
        category_ = "";
        categorySequence_ = "";
        subCategory_ = "";
        subCategorySequence_ = "";
        nscSku_ = "";
        upc_ = "";
        customerItemNumber_ = "";
        mfgItemNumber_ = "";
        origProductDescription_ = "";
    }
    
    public Object clone() throws CloneNotSupportedException {
        ItemTxtData myClone = new ItemTxtData();
        
        myClone.nscItemNumber_ = nscItemNumber_;
        myClone.productDescription_ = productDescription_;
        myClone.manufacturer_ = manufacturer_;
        myClone.mfgSku_ = mfgSku_;
        myClone.defaultUom_ = defaultUom_;
        myClone.primaryUom_ = primaryUom_;
        myClone.priceUom_ = priceUom_;
        myClone.priceUomConv_ = priceUomConv_;
        myClone.listPrice1_ = listPrice1_;
        myClone.itemDescription1_ = itemDescription1_;
        myClone.itemDescription2_ = itemDescription2_;
        myClone.company_ = company_;
        myClone.itemClass_ = itemClass_;
        myClone.itemSubClass_ = itemSubClass_;
        myClone.extendetItemDescription_ = extendetItemDescription_;
        myClone.standartPack_ = standartPack_;
        myClone.catalogName_ = catalogName_;
        myClone.category_ = category_;
        myClone.categorySequence_ = categorySequence_;
        myClone.subCategory_ = subCategory_;
        myClone.subCategorySequence_ = subCategorySequence_;
        myClone.nscSku_ = nscSku_;
        myClone.upc_ = upc_;
        myClone.customerItemNumber_ = customerItemNumber_;
        myClone.mfgItemNumber_ = mfgItemNumber_;
        myClone.origProductDescription_ = origProductDescription_;
        
        return myClone;
    }

    public String getCatalogName() {
        return catalogName_;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName_ = catalogName;
    }

    public String getCategorySequence() {
        return categorySequence_;
    }
    public void setCategorySequence(String categorySequence) {
        this.categorySequence_ = categorySequence;
    }

    public String getCategory() {
        return category_;
    }
    public void setCategory(String category) {
        this.category_ = category;
    }

    public String getCompany() {
        return company_;
    }
    public void setCompany(String company) {
        this.company_ = company;
    }

    public String getCustomerItemNumber() {
        return customerItemNumber_;
    }
    public void setCustomerItemNumber(String customerItemNumber) {
        this.customerItemNumber_ = customerItemNumber;
    }

    public String getDefaultUom() {
        return defaultUom_;
    }
    public void setDefaultUom(String defaultUom) {
        this.defaultUom_ = defaultUom;
    }

    public String getExtendetItemDescription() {
        return extendetItemDescription_;
    }
    public void setExtendetItemDescription(String extendetItemDescription) {
        this.extendetItemDescription_ = extendetItemDescription;
    }

    public String getItemClass() {
        return itemClass_;
    }
    public void setItemClass(String itemClass) {
        this.itemClass_ = itemClass;
    }

    public String getItemDescription1() {
        return itemDescription1_;
    }
    public void setItemDescription1(String itemDescription1) {
        this.itemDescription1_ = itemDescription1;
    }

    public String getItemDescription2() {
        return itemDescription2_;
    }
    public void setItemDescription2(String itemDescription2) {
        this.itemDescription2_ = itemDescription2;
    }

    public String getItemSubClass() {
        return itemSubClass_;
    }
    public void setItemSubClass(String itemSubClass) {
        this.itemSubClass_ = itemSubClass;
    }

    public double getListPrice1() {
        return listPrice1_;
    }
    public void setListPrice1(String listPrice1) {
        try{
            this.listPrice1_ = Double.parseDouble(listPrice1);
        }catch(NumberFormatException ex){
            
        }
    }

    public String getManufacturer() {
        return manufacturer_;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer_ = manufacturer;
    }

    public String getMfgItemNumber() {
        return mfgItemNumber_;
    }
    public void setMfgItemNumber(String mfgItemNumber) {
        this.mfgItemNumber_ = mfgItemNumber;
    }

    public String getMfgSku() {
        return mfgSku_;
    }
    public void setMfgSku(String mfgSku) {
        this.mfgSku_ = mfgSku;
    }

    public String getNscItemNumber() {
        return nscItemNumber_;
    }
    public void setNscItemNumber(String nscItemNumber) {
        this.nscItemNumber_ = nscItemNumber;
    }

    public String getNscSku() {
        return nscSku_;
    }
    public void setNscSku(String nscSku) {
        this.nscSku_ = nscSku;
    }

    public String getOrigProductDescription() {
        return origProductDescription_;
    }
    public void setOrigProductDescription(String origProductDescription) {
        this.origProductDescription_ = origProductDescription;
    }

    public String getPriceUomConv() {
        return priceUomConv_;
    }
    public void setPriceUomConv(String priceUomConv) {
        this.priceUomConv_ = priceUomConv;
    }

    public String getPriceUom() {
        return priceUom_;
    }
    public void setPriceUom(String priceUom) {
        this.priceUom_ = priceUom;
    }

    public String getPrimaryUom() {
        return primaryUom_;
    }
    public void setPrimaryUom(String primaryUom) {
        this.primaryUom_ = primaryUom;
    }

    public String getProductDescription() {
        return productDescription_;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription_ = productDescription;
    }

    public int getStandartPack() {
        return standartPack_;
    }
    
    public void setStandartPack(String standartPack) {
        try{
            this.standartPack_ = Integer.parseInt(standartPack);
        }catch(NumberFormatException ex){
            
        }
    }

    public String getSubCategorySequence() {
        return subCategorySequence_;
    }
    public void setSubCategorySequence(String subCategorySequence) {
        this.subCategorySequence_ = subCategorySequence;
    }

    public String getSubCategory() {
        return subCategory_;
    }
    public void setSubCategory(String subCategory) {
        this.subCategory_ = subCategory;
    }

    public String getUpc() {
        return upc_;
    }
    public void setUpc(String upc) {
        this.upc_ = upc;
    }

}
