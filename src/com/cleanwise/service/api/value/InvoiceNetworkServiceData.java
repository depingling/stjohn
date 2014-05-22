package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

public class InvoiceNetworkServiceData extends ValueObject {
    // Do not remove or modify next line. It would break object DB saving
    private static final long serialVersionUID = -1568780926338800220L;

    private String distCenterNo;

    private String distCenterName;

    private String accountNumber;

    private String accountName;

    private String invoiceNumber;

    private String invoiceDate;

    private String invoiceType;

    private String poNumber;

    private String lineNumber;

    private String quantity;

    private String quantityUnitOfMeasure;

    private String unitPrice;

    private String unitPriceOfMeasure;

    private String extendedWeight;

    private String extendedPrice;

    private String productNumber;

    private String productName;

    private String pack;

    private String packSize;

    private String brand;

    private String manufacturerName;

    private String manufacturerProductNo;

    private String randomWeightIndicator;

    private String splitCaseIndicator;

    private String catchWeightIndicator;

    private String splitCaseUOM;

    private String taxAmount;

    private String upc;

    private String freightCharges;

    private String discounts;

    private String deviatedPrice;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCatchWeightIndicator() {
        return catchWeightIndicator;
    }

    public void setCatchWeightIndicator(String catchWeightIndicator) {
        this.catchWeightIndicator = catchWeightIndicator;
    }

    public String getDeviatedPrice() {
        return deviatedPrice;
    }

    public void setDeviatedPrice(String deviatedPrice) {
        this.deviatedPrice = deviatedPrice;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public String getDistCenterNo() {
        return distCenterNo;
    }

    public void setDistCenterNo(String distCenterNo) {
        this.distCenterNo = distCenterNo;
    }

    public String getDistCenterName() {
        return distCenterName;
    }

    public void setDistCenterName(String ditrCenterName) {
        this.distCenterName = ditrCenterName;
    }

    public String getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(String extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public String getExtendedWeight() {
        return extendedWeight;
    }

    public void setExtendedWeight(String extendedWeight) {
        this.extendedWeight = extendedWeight;
    }

    public String getFreightCharges() {
        return freightCharges;
    }

    public void setFreightCharges(String freightCharges) {
        this.freightCharges = freightCharges;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerProductNo() {
        return manufacturerProductNo;
    }

    public void setManufacturerProductNo(String manufacturerProductNo) {
        this.manufacturerProductNo = manufacturerProductNo;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnitOfMeasure() {
        return quantityUnitOfMeasure;
    }

    public void setQuantityUnitOfMeasure(String quantityUnitOfMeasure) {
        this.quantityUnitOfMeasure = quantityUnitOfMeasure;
    }

    public String getRandomWeightIndicator() {
        return randomWeightIndicator;
    }

    public void setRandomWeightIndicator(String randomWeightIndicator) {
        this.randomWeightIndicator = randomWeightIndicator;
    }

    public String getSplitCaseIndicator() {
        return splitCaseIndicator;
    }

    public void setSplitCaseIndicator(String splitCaseIndicator) {
        this.splitCaseIndicator = splitCaseIndicator;
    }

    public String getSplitCaseUOM() {
        return splitCaseUOM;
    }

    public void setSplitCaseUOM(String splitCaseUOM) {
        this.splitCaseUOM = splitCaseUOM;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceOfMeasure() {
        return unitPriceOfMeasure;
    }

    public void setUnitPriceOfMeasure(String unitPriceOfMeasure) {
        this.unitPriceOfMeasure = unitPriceOfMeasure;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String toString() {
        return "[" + "DistCenterNo=" + distCenterNo + ", DitrCenterName="
                + distCenterName + ", AccountNumber=" + accountNumber
                + ", AccountName=" + accountName + ", InvoiceNumber="
                + invoiceNumber + ", InvoiceDate=" + invoiceDate
                + ", InvoiceType=" + invoiceType + ", PoNumber=" + poNumber
                + ", LineNumber=" + lineNumber + ", Quantity=" + quantity
                + ", QuantityUnitOfMeasure=" + quantityUnitOfMeasure
                + ", UnitPrice=" + unitPrice + ", UnitPriceOfMeasure="
                + unitPriceOfMeasure + ", ExtendedWeight=" + extendedWeight
                + ", ExtendedPrice=" + extendedPrice + ", ProductNumber="
                + productNumber + ", ProductName=" + productName + ", Pack="
                + pack + ", PackSize=" + packSize + ", Brand=" + brand
                + ", ManufacturerName=" + manufacturerName
                + ", ManufacturerProductNo=" + manufacturerProductNo
                + ", RandomWeightIndicator=" + randomWeightIndicator
                + ", SplitCaseIndicator=" + splitCaseIndicator
                + ", CatchWeightIndicator=" + catchWeightIndicator
                + ", SplitCaseUOM=" + splitCaseUOM + ", TaxAmount=" + taxAmount
                + ", UPC=" + upc + ", FreightCharges=" + freightCharges
                + ", Discounts=" + discounts + ", DeviatedPrice="
                + deviatedPrice + "]";
    }
}
