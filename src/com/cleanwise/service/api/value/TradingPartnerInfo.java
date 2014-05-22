package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerInfo
 * Description:  This is a ValueObject class wrapping
 * describing a trading partner.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import com.cleanwise.service.api.value.*;

import org.w3c.dom.*;

public class TradingPartnerInfo extends ValueObject {

        boolean CheckUOM;

    /**
     * Get the value of CheckUOM.
     * @return value of CheckUOM.
     */
    public boolean isCheckUOM() {
  return CheckUOM;
    }

    /**
     * Set the value of CheckUOM.
     * @param v  Value to assign to CheckUOM.
     */
    public void setCheckUOM(boolean  v) {
  this.CheckUOM = v;
    }
    boolean CheckAddress;

    /**
     * Get the value of CheckAddress.
     * @return value of CheckAddress.
     */
    public boolean isCheckAddress() {
  return CheckAddress;
    }

    /**
     * Set the value of CheckAddress.
     * @param v  Value to assign to CheckAddress.
     */
    public void setCheckAddress(boolean  v) {
  this.CheckAddress = v;
    }

    boolean ValidateRefOrderNum;

    /**
     * Get the value of ValidateRefOrderNum.
     * @return value of ValidateRefOrderNum.
     */
    public boolean isValidateRefOrderNum() {
  return ValidateRefOrderNum;
    }

    /**
     * Set the value of ValidateRefOrderNum.
     * @param v  Value to assign to ValidateRefOrderNum.
     */
    public void setValidateRefOrderNum(boolean  v) {
  this.ValidateRefOrderNum = v;
    }
    boolean ValidateCustomerSkuNum;

    /**
     * Get the value of ValidateCustomerSkuNum.
     * @return value of ValidateCustomerSkuNum.
     */
    public boolean isValidateCustomerSkuNum() {
  return ValidateCustomerSkuNum;
    }

    /**
     * Set the value of ValidateCustomerSkuNum.
     * @param v  Value to assign to ValidateCustomerSkuNum.
     */
    public void setValidateCustomerSkuNum(boolean  v) {
  this.ValidateCustomerSkuNum = v;
    }
    boolean ValidateCustomerItemDesc;

    /**
     * Get the value of ValidateCustomerItemDesc.
     * @return value of ValidateCustomerItemDesc.
     */
    public boolean isValidateCustomerItemDesc() {
  return ValidateCustomerItemDesc;
    }

    /**
     * Set the value of ValidateCustomerItemDesc.
     * @param v  Value to assign to ValidateCustomerItemDesc.
     */
    public void setValidateCustomerItemDesc(boolean  v) {
  this.ValidateCustomerItemDesc = v;
    }


    TradingPartnerData mTradingPartnerData;

    /** Holds value of property purchaseOrderFaxNumber. */
    private PhoneData purchaseOrderFaxNumber;

    /** Holds value of property toContactName. */
    private String toContactName;

    /** Holds value of property fromContactName. */
    private String fromContactName;

    /** Holds value of property purchaseOrderFreightTerms. */
    private String purchaseOrderFreightTerms;

    /** Holds value of property purchaseOrderDueDays. */
    private String purchaseOrderDueDays;
    
    /** Holds value of property emailFrom. */
    private String emailFrom;    
    
    /** Holds value of property emailTo. */
    private String emailTo;    
    
    /** Holds value of property emailSubject. */
    private String emailSubject;    

    /** Holds value of property emailBodyTemplate. */
    private String emailBodyTemplate;     


    /**
     * Get the value of TradingPartnerData.
     * @return value of TradingPartnerData.
     */
    public TradingPartnerData getTradingPartnerData() {
  return mTradingPartnerData;
    }

    /**
     * Set the value of TradingPartnerData.
     * @param v  Value to assign to TradingPartnerData.
     */
    public void setTradingPartnerData(TradingPartnerData  v) {
  this.mTradingPartnerData = v;
    }

    /** Getter for property purchaseOrderFaxNumber.
     * @return Value of property purchaseOrderFaxNumber.
     */
    public PhoneData getPurchaseOrderFaxNumber() {
        if (this.purchaseOrderFaxNumber == null){
            this.purchaseOrderFaxNumber = PhoneData.createValue();
        }
        return this.purchaseOrderFaxNumber;
    }

    /** Setter for property purchaseOrderFaxNumber.
     * @param purchaseOrderFaxNumber New value of property purchaseOrderFaxNumber.
     */
    public void setPurchaseOrderFaxNumber(PhoneData purchaseOrderFaxNumber) {
        this.purchaseOrderFaxNumber = purchaseOrderFaxNumber;
    }

    /** Getter for property toContactName.
     * @return Value of property toContactName.
     */
    public String getToContactName() {
        return this.toContactName;
    }
      
    /** Setter for property toContactName.
     * @param toContactName New value of property toContactName.
     */
    public void setToContactName(String toContactName) {
        this.toContactName = toContactName;
    }
    
    /** Getter for property emailFrom.
     * @return Value of property emailFrom.
     */
    public String getEmailFrom() {
        return this.emailFrom;
    }      
    
    /** Getter for property emailTo.
     * @return Value of property emailTo.
     */
    public String getEmailTo() {
        return this.emailTo;
    }    
    
    /** Getter for property emailSubject.
     * @return Value of property emailSubject.
     */
    public String getEmailSubject() {
        return this.emailSubject;
    }     
    
    /** Getter for property emailBodyTemplate.
     * @return Value of property emailBodyTemplate.
     */    
    public String getEmailBodyTemplate() {
        return this.emailBodyTemplate;
    }    

    /** Setter for property emailFrom.
     * @param V New value of property emailFrom.
     */
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }
    
    /** Setter for property emailTo.
     * @param V New value of property C.
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }    
    
    /** Setter for property emailSubject.
     * @param V New value of property emailSubject.
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }      
    
    /** Setter for property emailBodyTemplate.
     * @param V New value of property emailBodyTemplate.
     */
    public void setEmailBodyTemplate(String emailBodyTemplate) {
        this.emailBodyTemplate = emailBodyTemplate;
    }    

    /** Getter for property fromContactName.
     * @return Value of property fromContactName.
     */
    public String getFromContactName() {
        return this.fromContactName;
    }

    /** Setter for property fromContactName.
     * @param fromContactName New value of property fromContactName.
     */
    public void setFromContactName(String fromContactName) {
        this.fromContactName = fromContactName;
    }


     /** Getter for property purchaseOrderFreightTerms.
     * @return Value of property purchaseOrderFreightTerms.
     */
    public String getPurchaseOrderFreightTerms() {
        return this.purchaseOrderFreightTerms;
    }

    /** Setter for property purchaseOrderFreightTerms.
     * @param purchaseOrderFreightTerms New value of property purchaseOrderFreightTerms.
     */
    public void setPurchaseOrderFreightTerms(String purchaseOrderFreightTerms) {
        this.purchaseOrderFreightTerms = purchaseOrderFreightTerms;
    }

     /** Getter for property purchaseOrderDueDays.
     * @return Value of property purchaseOrderDueDays.
     */
    public String getPurchaseOrderDueDays() {
        return this.purchaseOrderDueDays;
    }

    /** Setter for property purchaseOrderDueDays.
     * @param purchaseOrderDueDays New value of property purchaseOrderDueDays.
     */
    public void setPurchaseOrderDueDays(String purchaseOrderDueDays) {
        this.purchaseOrderDueDays = purchaseOrderDueDays;
    }

        boolean ProcessInvoiceCredit;

        /** Holds value of property initializeExistingPos. */
        private boolean initializeExistingPos;

    /**
     * Get the value of processInvoiceCredit.
     * @return value of processInvoiceCredit.
     */
    public boolean isProcessInvoiceCredit() {
  return ProcessInvoiceCredit;
    }

    /**
     * Set the value of processInvoiceCredit.
     * @param v  Value to assign to processInvoiceCredit.
     */
    public void setProcessInvoiceCredit(boolean  v) {
  this.ProcessInvoiceCredit = v;
    }

    /** Getter for property initializeExistingPos.
     * @return Value of property initializeExistingPos.
     *
     */
    public boolean isInitializeExistingPos() {
        return this.initializeExistingPos;
    }

    /** Setter for property initializeExistingPos.
     * @param initializeExistingPos New value of property initializeExistingPos.
     *
     */
    public void setInitializeExistingPos(boolean initializeExistingPos) {
        this.initializeExistingPos = initializeExistingPos;
    }

    private BusEntityDataVector busEntities = new BusEntityDataVector();
    /** Getter for property busEntities.
     * @return Value of property busEntities.
     *
     */
    public BusEntityDataVector getBusEntities() {
        return this.busEntities;
    }


    /** Setter for property busEntities.
     * @param busEntities New value of property busEntities.
     *
     */
    public void setBusEntities(BusEntityDataVector busEntities) {
        this.busEntities = busEntities;
    }

    private TradingPartnerAssocDataVector tpAssocies = new TradingPartnerAssocDataVector();
    /** Getter for property busEntities.
     * @return Value of property busEntities.
     *
     */
    public TradingPartnerAssocDataVector getTPAssocies() {
        return this.tpAssocies;
    }


    /** Setter for property busEntities.
     * @param busEntities New value of property busEntities.
     *
     */
    public void setTPAssocies(TradingPartnerAssocDataVector tpAssocies) {
        this.tpAssocies = tpAssocies;
    }


    boolean Allow856Flag;
    String Allow856Email;

    public boolean isAllow856() {
      return Allow856Flag;
    }

    public void setAllow856Flag(boolean  v) {
      this.Allow856Flag = v;
    }

    public String getAllow856Email() {
        return Allow856Email;
    }

    public void setAllow856Email(String v) {
        Allow856Email = v;
    }

    boolean useInboundAmountForCostAndPrice;

    /**
     * Get the value of costAndPrice.
     * @return value of costAndPrice.
     */
    public boolean isUseInboundAmountForCostAndPrice() {
    	return useInboundAmountForCostAndPrice;
    }

    /**
     * Set the value of costAndPrice.
     * @param v  Value to assign to costAndPrice.
     */
    public void setUseInboundAmountForCostAndPrice(boolean  v) {
    	this.useInboundAmountForCostAndPrice = v;
    }

    boolean usePoLineNumForInvoiceMatch;

    /**
     * Get the value of usePoLineNumForInvoiceMatch.
     * @return value of usePoLineNumForInvoiceMatch.
     */
    public boolean isUsePoLineNumForInvoiceMatch() {
    	return usePoLineNumForInvoiceMatch;
    }

    /**
     * Set the value of usePoLineNumForInvoiceMatch.
     * @param v  Value to assign to usePoLineNumForInvoiceMatch.
     */
    public void setUsePoLineNumForInvoiceMatch(boolean  v) {
    	this.usePoLineNumForInvoiceMatch = v;
    }
    
    boolean relaxValidateInboundDuplInvoiceNum;

    /**
     * Get the value of relaxValidateInboundDuplInvoiceNum.
     * @return value of relaxValidateInboundDuplInvoiceNum.
     */
    public boolean isRelaxValidateInboundDuplInvoiceNum() {
    	return relaxValidateInboundDuplInvoiceNum;
    }

    /**
     * Set the value of relaxValidateInboundDuplInvoiceNum.
     * @param v  Value to assign to relaxValidateInboundDuplInvoiceNum.
     */
    public void setRelaxValidateInboundDuplInvoiceNum(boolean  v) {
    	this.relaxValidateInboundDuplInvoiceNum = v;
    }
    
    
    public TradingPartnerInfo() {
  mTradingPartnerData = TradingPartnerData.createValue();
  ValidateCustomerItemDesc = false;
  ValidateCustomerSkuNum = false;
  ValidateRefOrderNum = false;
  CheckAddress = false;
  CheckUOM = false;
  ProcessInvoiceCredit = false;
  Allow856Flag = false;
  usePoLineNumForInvoiceMatch = false;
  relaxValidateInboundDuplInvoiceNum = false;
    }

}
