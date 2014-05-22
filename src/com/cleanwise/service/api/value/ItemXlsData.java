package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

public class ItemXlsData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 3735419189606578513L;

    private String      cust; //
    private String      customerName;
    private String      generalDescription;
    private String      nscNumber;//
    private String      upc;
    private String      supplier;//
    private String      description;
    private String      supplierName;
    private String      mfgNumber;
    private String      casePack;//
    private String      uom;
    private String      sellingPrice;
    private String      contractItem;
    private String      previousSellingPrice;
    private String      customerPartNumber;//
    private String      changed;
    private String      comment;

    public void setCust(String cust) {
        this.cust = cust;
    }
    public String getCust() {
        return cust;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }
    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setNscNumber(String nscNumber) {
        this.nscNumber = nscNumber;
    }
    public String getNscNumber() {
        return nscNumber;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }
    public String getUpc() {
        return upc;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getSupplier() {
        return supplier;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getSupplierName() {
        return supplierName;
    }

    public void setMfgNumber(String mfgNumber) {
        this.mfgNumber = mfgNumber;
    }
    public String getMfgNumber() {
        return mfgNumber;
    }

    public void setCasePack(String casePack) {
        this.casePack = casePack;
    }
    public String getCasePack() {
        return casePack;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
    public String getUom() {
        return uom;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setContractItem(String contractItem) {
        this.contractItem = contractItem;
    }
    public String getContractItem() {
        return contractItem;
    }

    public void setPreviousSellingPrice(String previousSellingPrice) {
        this.previousSellingPrice = previousSellingPrice;
    }
    public String getPreviousSellingPrice() {
        return previousSellingPrice;
    }

    public void setCustomerPartNumber(String customerPartNumber) {
        this.customerPartNumber = customerPartNumber;
    }
    public String getCustomerPartNumber() {
        return customerPartNumber;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }
    public String getChanged() {
        return changed;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }

}
