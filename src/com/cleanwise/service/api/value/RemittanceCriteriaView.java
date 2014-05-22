
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RemittanceCriteriaView
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
 * <code>RemittanceCriteriaView</code> is a ViewObject class for UI.
 */
public class RemittanceCriteriaView
extends ValueObject
{
   
    private static final long serialVersionUID = -1395149465452033914L;
    private int mRemittanceId;
    private int mRemittanceStoreId;
    private String mRemittanceHandlingCode;
    private java.math.BigDecimal mRemittanceTotalPaymentAmount;
    private String mRemittanceCreditDebit;
    private String mRemittancePaymentType;
    private String mRemittancePayerBank;
    private String mRemittancePayerBankAccount;
    private String mRemittancePayerId;
    private String mRemittancePayeeBank;
    private String mRemittancePayeeBankAccount;
    private Date mRemittancePaymentPostDate;
    private String mRemittancePaymentReferenceNumberType;
    private String mRemittancePaymentReferenceNumber;
    private Date mRemittanceCheckDate;
    private String mRemittancePayeeErpAccount;
    private Date mRemittanceTransactionDate;
    private String mRemittanceTransactionReference;
    private String mRemittanceStatusCd;
    private Date mRemittanceStartAddDate;
    private Date mRemittanceEndAddDate;
    private Date mRemittanceAddDate;
    private String mRemittanceAddBy;
    private Date mRemittanceModDate;
    private String mRemittanceModBy;
    private int mRemittanceDetailId;
    private String mRemittanceDetailSiteReference;
    private String mRemittanceDetailInvoiceNumber;
    private String mRemittanceDetailInvoiceType;
    private java.math.BigDecimal mRemittanceDetailDiscountAmount;
    private java.math.BigDecimal mRemittanceDetailNetAmount;
    private java.math.BigDecimal mRemittanceDetailOrigInvoiceAmount;
    private String mRemittanceDetailCustomerPoNumber;
    private String mRemittanceDetailCustomerSupplierNumber;
    private String mRemittanceDetailStatusCd;
    private Date mRemittanceDetailAddDate;
    private String mRemittanceDetailAddBy;
    private Date mRemittanceDetailModDate;
    private String mRemittanceDetailModBy;
    private Boolean mRemittanceInErrorState;
    private Boolean mRemittanceDetailInErrorState;
    private Date mRemittancePropertyAddDate;
    private String mRemittancePropertyAddBy;
    private Date mRemittancePropertyModDate;
    private String mRemittancePropertyModBy;
    private String mRemittancePropertyRemittancePropertyTypeCd;
    private String mRemittancePropertyRemittancePropertyStatusCd;
    private String mRemittancePropertyClwValue;
    private int mRemittancePropertyId;

    /**
     * Constructor.
     */
    public RemittanceCriteriaView ()
    {
        mRemittanceHandlingCode = "";
        mRemittanceCreditDebit = "";
        mRemittancePaymentType = "";
        mRemittancePayerBank = "";
        mRemittancePayerBankAccount = "";
        mRemittancePayerId = "";
        mRemittancePayeeBank = "";
        mRemittancePayeeBankAccount = "";
        mRemittancePaymentReferenceNumberType = "";
        mRemittancePaymentReferenceNumber = "";
        mRemittancePayeeErpAccount = "";
        mRemittanceTransactionReference = "";
        mRemittanceStatusCd = "";
        mRemittanceAddBy = "";
        mRemittanceModBy = "";
        mRemittanceDetailSiteReference = "";
        mRemittanceDetailInvoiceNumber = "";
        mRemittanceDetailInvoiceType = "";
        mRemittanceDetailCustomerPoNumber = "";
        mRemittanceDetailCustomerSupplierNumber = "";
        mRemittanceDetailStatusCd = "";
        mRemittanceDetailAddBy = "";
        mRemittanceDetailModBy = "";
        mRemittancePropertyAddBy = "";
        mRemittancePropertyModBy = "";
        mRemittancePropertyRemittancePropertyTypeCd = "";
        mRemittancePropertyRemittancePropertyStatusCd = "";
        mRemittancePropertyClwValue = "";
    }

    /**
     * Constructor. 
     */
    public RemittanceCriteriaView(int parm1, int parm2, String parm3, java.math.BigDecimal parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, Date parm12, String parm13, String parm14, Date parm15, String parm16, Date parm17, String parm18, String parm19, Date parm20, Date parm21, Date parm22, String parm23, Date parm24, String parm25, int parm26, String parm27, String parm28, String parm29, java.math.BigDecimal parm30, java.math.BigDecimal parm31, java.math.BigDecimal parm32, String parm33, String parm34, String parm35, Date parm36, String parm37, Date parm38, String parm39, Boolean parm40, Boolean parm41, Date parm42, String parm43, Date parm44, String parm45, String parm46, String parm47, String parm48, int parm49)
    {
        mRemittanceId = parm1;
        mRemittanceStoreId = parm2;
        mRemittanceHandlingCode = parm3;
        mRemittanceTotalPaymentAmount = parm4;
        mRemittanceCreditDebit = parm5;
        mRemittancePaymentType = parm6;
        mRemittancePayerBank = parm7;
        mRemittancePayerBankAccount = parm8;
        mRemittancePayerId = parm9;
        mRemittancePayeeBank = parm10;
        mRemittancePayeeBankAccount = parm11;
        mRemittancePaymentPostDate = parm12;
        mRemittancePaymentReferenceNumberType = parm13;
        mRemittancePaymentReferenceNumber = parm14;
        mRemittanceCheckDate = parm15;
        mRemittancePayeeErpAccount = parm16;
        mRemittanceTransactionDate = parm17;
        mRemittanceTransactionReference = parm18;
        mRemittanceStatusCd = parm19;
        mRemittanceStartAddDate = parm20;
        mRemittanceEndAddDate = parm21;
        mRemittanceAddDate = parm22;
        mRemittanceAddBy = parm23;
        mRemittanceModDate = parm24;
        mRemittanceModBy = parm25;
        mRemittanceDetailId = parm26;
        mRemittanceDetailSiteReference = parm27;
        mRemittanceDetailInvoiceNumber = parm28;
        mRemittanceDetailInvoiceType = parm29;
        mRemittanceDetailDiscountAmount = parm30;
        mRemittanceDetailNetAmount = parm31;
        mRemittanceDetailOrigInvoiceAmount = parm32;
        mRemittanceDetailCustomerPoNumber = parm33;
        mRemittanceDetailCustomerSupplierNumber = parm34;
        mRemittanceDetailStatusCd = parm35;
        mRemittanceDetailAddDate = parm36;
        mRemittanceDetailAddBy = parm37;
        mRemittanceDetailModDate = parm38;
        mRemittanceDetailModBy = parm39;
        mRemittanceInErrorState = parm40;
        mRemittanceDetailInErrorState = parm41;
        mRemittancePropertyAddDate = parm42;
        mRemittancePropertyAddBy = parm43;
        mRemittancePropertyModDate = parm44;
        mRemittancePropertyModBy = parm45;
        mRemittancePropertyRemittancePropertyTypeCd = parm46;
        mRemittancePropertyRemittancePropertyStatusCd = parm47;
        mRemittancePropertyClwValue = parm48;
        mRemittancePropertyId = parm49;
        
    }

    /**
     * Creates a new RemittanceCriteriaView
     *
     * @return
     *  Newly initialized RemittanceCriteriaView object.
     */
    public static RemittanceCriteriaView createValue () 
    {
        RemittanceCriteriaView valueView = new RemittanceCriteriaView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RemittanceCriteriaView object
     */
    public String toString()
    {
        return "[" + "RemittanceId=" + mRemittanceId + ", RemittanceStoreId=" + mRemittanceStoreId + ", RemittanceHandlingCode=" + mRemittanceHandlingCode + ", RemittanceTotalPaymentAmount=" + mRemittanceTotalPaymentAmount + ", RemittanceCreditDebit=" + mRemittanceCreditDebit + ", RemittancePaymentType=" + mRemittancePaymentType + ", RemittancePayerBank=" + mRemittancePayerBank + ", RemittancePayerBankAccount=" + mRemittancePayerBankAccount + ", RemittancePayerId=" + mRemittancePayerId + ", RemittancePayeeBank=" + mRemittancePayeeBank + ", RemittancePayeeBankAccount=" + mRemittancePayeeBankAccount + ", RemittancePaymentPostDate=" + mRemittancePaymentPostDate + ", RemittancePaymentReferenceNumberType=" + mRemittancePaymentReferenceNumberType + ", RemittancePaymentReferenceNumber=" + mRemittancePaymentReferenceNumber + ", RemittanceCheckDate=" + mRemittanceCheckDate + ", RemittancePayeeErpAccount=" + mRemittancePayeeErpAccount + ", RemittanceTransactionDate=" + mRemittanceTransactionDate + ", RemittanceTransactionReference=" + mRemittanceTransactionReference + ", RemittanceStatusCd=" + mRemittanceStatusCd + ", RemittanceStartAddDate=" + mRemittanceStartAddDate + ", RemittanceEndAddDate=" + mRemittanceEndAddDate + ", RemittanceAddDate=" + mRemittanceAddDate + ", RemittanceAddBy=" + mRemittanceAddBy + ", RemittanceModDate=" + mRemittanceModDate + ", RemittanceModBy=" + mRemittanceModBy + ", RemittanceDetailId=" + mRemittanceDetailId + ", RemittanceDetailSiteReference=" + mRemittanceDetailSiteReference + ", RemittanceDetailInvoiceNumber=" + mRemittanceDetailInvoiceNumber + ", RemittanceDetailInvoiceType=" + mRemittanceDetailInvoiceType + ", RemittanceDetailDiscountAmount=" + mRemittanceDetailDiscountAmount + ", RemittanceDetailNetAmount=" + mRemittanceDetailNetAmount + ", RemittanceDetailOrigInvoiceAmount=" + mRemittanceDetailOrigInvoiceAmount + ", RemittanceDetailCustomerPoNumber=" + mRemittanceDetailCustomerPoNumber + ", RemittanceDetailCustomerSupplierNumber=" + mRemittanceDetailCustomerSupplierNumber + ", RemittanceDetailStatusCd=" + mRemittanceDetailStatusCd + ", RemittanceDetailAddDate=" + mRemittanceDetailAddDate + ", RemittanceDetailAddBy=" + mRemittanceDetailAddBy + ", RemittanceDetailModDate=" + mRemittanceDetailModDate + ", RemittanceDetailModBy=" + mRemittanceDetailModBy + ", RemittanceInErrorState=" + mRemittanceInErrorState + ", RemittanceDetailInErrorState=" + mRemittanceDetailInErrorState + ", RemittancePropertyAddDate=" + mRemittancePropertyAddDate + ", RemittancePropertyAddBy=" + mRemittancePropertyAddBy + ", RemittancePropertyModDate=" + mRemittancePropertyModDate + ", RemittancePropertyModBy=" + mRemittancePropertyModBy + ", RemittancePropertyRemittancePropertyTypeCd=" + mRemittancePropertyRemittancePropertyTypeCd + ", RemittancePropertyRemittancePropertyStatusCd=" + mRemittancePropertyRemittancePropertyStatusCd + ", RemittancePropertyClwValue=" + mRemittancePropertyClwValue + ", RemittancePropertyId=" + mRemittancePropertyId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("RemittanceCriteria");
	root.setAttribute("Id", String.valueOf(mRemittanceId));

	Element node;

        node = doc.createElement("RemittanceStoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceStoreId)));
        root.appendChild(node);

        node = doc.createElement("RemittanceHandlingCode");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceHandlingCode)));
        root.appendChild(node);

        node = doc.createElement("RemittanceTotalPaymentAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceTotalPaymentAmount)));
        root.appendChild(node);

        node = doc.createElement("RemittanceCreditDebit");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceCreditDebit)));
        root.appendChild(node);

        node = doc.createElement("RemittancePaymentType");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePaymentType)));
        root.appendChild(node);

        node = doc.createElement("RemittancePayerBank");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePayerBank)));
        root.appendChild(node);

        node = doc.createElement("RemittancePayerBankAccount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePayerBankAccount)));
        root.appendChild(node);

        node = doc.createElement("RemittancePayerId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePayerId)));
        root.appendChild(node);

        node = doc.createElement("RemittancePayeeBank");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePayeeBank)));
        root.appendChild(node);

        node = doc.createElement("RemittancePayeeBankAccount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePayeeBankAccount)));
        root.appendChild(node);

        node = doc.createElement("RemittancePaymentPostDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePaymentPostDate)));
        root.appendChild(node);

        node = doc.createElement("RemittancePaymentReferenceNumberType");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePaymentReferenceNumberType)));
        root.appendChild(node);

        node = doc.createElement("RemittancePaymentReferenceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePaymentReferenceNumber)));
        root.appendChild(node);

        node = doc.createElement("RemittanceCheckDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceCheckDate)));
        root.appendChild(node);

        node = doc.createElement("RemittancePayeeErpAccount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePayeeErpAccount)));
        root.appendChild(node);

        node = doc.createElement("RemittanceTransactionDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceTransactionDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceTransactionReference");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceTransactionReference)));
        root.appendChild(node);

        node = doc.createElement("RemittanceStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceStatusCd)));
        root.appendChild(node);

        node = doc.createElement("RemittanceStartAddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceStartAddDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceEndAddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceEndAddDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceAddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceAddDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceAddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceAddBy)));
        root.appendChild(node);

        node = doc.createElement("RemittanceModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceModDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceModBy)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailId)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailSiteReference");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailSiteReference)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailInvoiceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailInvoiceNumber)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailInvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailInvoiceType)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailDiscountAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailDiscountAmount)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailNetAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailNetAmount)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailOrigInvoiceAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailOrigInvoiceAmount)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailCustomerPoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailCustomerPoNumber)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailCustomerSupplierNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailCustomerSupplierNumber)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailStatusCd)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailAddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailAddDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailAddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailAddBy)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailModDate)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailModBy)));
        root.appendChild(node);

        node = doc.createElement("RemittanceInErrorState");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceInErrorState)));
        root.appendChild(node);

        node = doc.createElement("RemittanceDetailInErrorState");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailInErrorState)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyAddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyAddDate)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyAddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyAddBy)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyModDate)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyModBy)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyRemittancePropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyRemittancePropertyTypeCd)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyRemittancePropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyRemittancePropertyStatusCd)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyClwValue");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyClwValue)));
        root.appendChild(node);

        node = doc.createElement("RemittancePropertyId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public RemittanceCriteriaView copy()  {
      RemittanceCriteriaView obj = new RemittanceCriteriaView();
      obj.setRemittanceId(mRemittanceId);
      obj.setRemittanceStoreId(mRemittanceStoreId);
      obj.setRemittanceHandlingCode(mRemittanceHandlingCode);
      obj.setRemittanceTotalPaymentAmount(mRemittanceTotalPaymentAmount);
      obj.setRemittanceCreditDebit(mRemittanceCreditDebit);
      obj.setRemittancePaymentType(mRemittancePaymentType);
      obj.setRemittancePayerBank(mRemittancePayerBank);
      obj.setRemittancePayerBankAccount(mRemittancePayerBankAccount);
      obj.setRemittancePayerId(mRemittancePayerId);
      obj.setRemittancePayeeBank(mRemittancePayeeBank);
      obj.setRemittancePayeeBankAccount(mRemittancePayeeBankAccount);
      obj.setRemittancePaymentPostDate(mRemittancePaymentPostDate);
      obj.setRemittancePaymentReferenceNumberType(mRemittancePaymentReferenceNumberType);
      obj.setRemittancePaymentReferenceNumber(mRemittancePaymentReferenceNumber);
      obj.setRemittanceCheckDate(mRemittanceCheckDate);
      obj.setRemittancePayeeErpAccount(mRemittancePayeeErpAccount);
      obj.setRemittanceTransactionDate(mRemittanceTransactionDate);
      obj.setRemittanceTransactionReference(mRemittanceTransactionReference);
      obj.setRemittanceStatusCd(mRemittanceStatusCd);
      obj.setRemittanceStartAddDate(mRemittanceStartAddDate);
      obj.setRemittanceEndAddDate(mRemittanceEndAddDate);
      obj.setRemittanceAddDate(mRemittanceAddDate);
      obj.setRemittanceAddBy(mRemittanceAddBy);
      obj.setRemittanceModDate(mRemittanceModDate);
      obj.setRemittanceModBy(mRemittanceModBy);
      obj.setRemittanceDetailId(mRemittanceDetailId);
      obj.setRemittanceDetailSiteReference(mRemittanceDetailSiteReference);
      obj.setRemittanceDetailInvoiceNumber(mRemittanceDetailInvoiceNumber);
      obj.setRemittanceDetailInvoiceType(mRemittanceDetailInvoiceType);
      obj.setRemittanceDetailDiscountAmount(mRemittanceDetailDiscountAmount);
      obj.setRemittanceDetailNetAmount(mRemittanceDetailNetAmount);
      obj.setRemittanceDetailOrigInvoiceAmount(mRemittanceDetailOrigInvoiceAmount);
      obj.setRemittanceDetailCustomerPoNumber(mRemittanceDetailCustomerPoNumber);
      obj.setRemittanceDetailCustomerSupplierNumber(mRemittanceDetailCustomerSupplierNumber);
      obj.setRemittanceDetailStatusCd(mRemittanceDetailStatusCd);
      obj.setRemittanceDetailAddDate(mRemittanceDetailAddDate);
      obj.setRemittanceDetailAddBy(mRemittanceDetailAddBy);
      obj.setRemittanceDetailModDate(mRemittanceDetailModDate);
      obj.setRemittanceDetailModBy(mRemittanceDetailModBy);
      obj.setRemittanceInErrorState(mRemittanceInErrorState);
      obj.setRemittanceDetailInErrorState(mRemittanceDetailInErrorState);
      obj.setRemittancePropertyAddDate(mRemittancePropertyAddDate);
      obj.setRemittancePropertyAddBy(mRemittancePropertyAddBy);
      obj.setRemittancePropertyModDate(mRemittancePropertyModDate);
      obj.setRemittancePropertyModBy(mRemittancePropertyModBy);
      obj.setRemittancePropertyRemittancePropertyTypeCd(mRemittancePropertyRemittancePropertyTypeCd);
      obj.setRemittancePropertyRemittancePropertyStatusCd(mRemittancePropertyRemittancePropertyStatusCd);
      obj.setRemittancePropertyClwValue(mRemittancePropertyClwValue);
      obj.setRemittancePropertyId(mRemittancePropertyId);
      
      return obj;
    }

    
    /**
     * Sets the RemittanceId property.
     *
     * @param pRemittanceId
     *  int to use to update the property.
     */
    public void setRemittanceId(int pRemittanceId){
        this.mRemittanceId = pRemittanceId;
    }
    /**
     * Retrieves the RemittanceId property.
     *
     * @return
     *  int containing the RemittanceId property.
     */
    public int getRemittanceId(){
        return mRemittanceId;
    }


    /**
     * Sets the RemittanceStoreId property.
     *
     * @param pRemittanceStoreId
     *  int to use to update the property.
     */
    public void setRemittanceStoreId(int pRemittanceStoreId){
        this.mRemittanceStoreId = pRemittanceStoreId;
    }
    /**
     * Retrieves the RemittanceStoreId property.
     *
     * @return
     *  int containing the RemittanceStoreId property.
     */
    public int getRemittanceStoreId(){
        return mRemittanceStoreId;
    }


    /**
     * Sets the RemittanceHandlingCode property.
     *
     * @param pRemittanceHandlingCode
     *  String to use to update the property.
     */
    public void setRemittanceHandlingCode(String pRemittanceHandlingCode){
        this.mRemittanceHandlingCode = pRemittanceHandlingCode;
    }
    /**
     * Retrieves the RemittanceHandlingCode property.
     *
     * @return
     *  String containing the RemittanceHandlingCode property.
     */
    public String getRemittanceHandlingCode(){
        return mRemittanceHandlingCode;
    }


    /**
     * Sets the RemittanceTotalPaymentAmount property.
     *
     * @param pRemittanceTotalPaymentAmount
     *  java.math.BigDecimal to use to update the property.
     */
    public void setRemittanceTotalPaymentAmount(java.math.BigDecimal pRemittanceTotalPaymentAmount){
        this.mRemittanceTotalPaymentAmount = pRemittanceTotalPaymentAmount;
    }
    /**
     * Retrieves the RemittanceTotalPaymentAmount property.
     *
     * @return
     *  java.math.BigDecimal containing the RemittanceTotalPaymentAmount property.
     */
    public java.math.BigDecimal getRemittanceTotalPaymentAmount(){
        return mRemittanceTotalPaymentAmount;
    }


    /**
     * Sets the RemittanceCreditDebit property.
     *
     * @param pRemittanceCreditDebit
     *  String to use to update the property.
     */
    public void setRemittanceCreditDebit(String pRemittanceCreditDebit){
        this.mRemittanceCreditDebit = pRemittanceCreditDebit;
    }
    /**
     * Retrieves the RemittanceCreditDebit property.
     *
     * @return
     *  String containing the RemittanceCreditDebit property.
     */
    public String getRemittanceCreditDebit(){
        return mRemittanceCreditDebit;
    }


    /**
     * Sets the RemittancePaymentType property.
     *
     * @param pRemittancePaymentType
     *  String to use to update the property.
     */
    public void setRemittancePaymentType(String pRemittancePaymentType){
        this.mRemittancePaymentType = pRemittancePaymentType;
    }
    /**
     * Retrieves the RemittancePaymentType property.
     *
     * @return
     *  String containing the RemittancePaymentType property.
     */
    public String getRemittancePaymentType(){
        return mRemittancePaymentType;
    }


    /**
     * Sets the RemittancePayerBank property.
     *
     * @param pRemittancePayerBank
     *  String to use to update the property.
     */
    public void setRemittancePayerBank(String pRemittancePayerBank){
        this.mRemittancePayerBank = pRemittancePayerBank;
    }
    /**
     * Retrieves the RemittancePayerBank property.
     *
     * @return
     *  String containing the RemittancePayerBank property.
     */
    public String getRemittancePayerBank(){
        return mRemittancePayerBank;
    }


    /**
     * Sets the RemittancePayerBankAccount property.
     *
     * @param pRemittancePayerBankAccount
     *  String to use to update the property.
     */
    public void setRemittancePayerBankAccount(String pRemittancePayerBankAccount){
        this.mRemittancePayerBankAccount = pRemittancePayerBankAccount;
    }
    /**
     * Retrieves the RemittancePayerBankAccount property.
     *
     * @return
     *  String containing the RemittancePayerBankAccount property.
     */
    public String getRemittancePayerBankAccount(){
        return mRemittancePayerBankAccount;
    }


    /**
     * Sets the RemittancePayerId property.
     *
     * @param pRemittancePayerId
     *  String to use to update the property.
     */
    public void setRemittancePayerId(String pRemittancePayerId){
        this.mRemittancePayerId = pRemittancePayerId;
    }
    /**
     * Retrieves the RemittancePayerId property.
     *
     * @return
     *  String containing the RemittancePayerId property.
     */
    public String getRemittancePayerId(){
        return mRemittancePayerId;
    }


    /**
     * Sets the RemittancePayeeBank property.
     *
     * @param pRemittancePayeeBank
     *  String to use to update the property.
     */
    public void setRemittancePayeeBank(String pRemittancePayeeBank){
        this.mRemittancePayeeBank = pRemittancePayeeBank;
    }
    /**
     * Retrieves the RemittancePayeeBank property.
     *
     * @return
     *  String containing the RemittancePayeeBank property.
     */
    public String getRemittancePayeeBank(){
        return mRemittancePayeeBank;
    }


    /**
     * Sets the RemittancePayeeBankAccount property.
     *
     * @param pRemittancePayeeBankAccount
     *  String to use to update the property.
     */
    public void setRemittancePayeeBankAccount(String pRemittancePayeeBankAccount){
        this.mRemittancePayeeBankAccount = pRemittancePayeeBankAccount;
    }
    /**
     * Retrieves the RemittancePayeeBankAccount property.
     *
     * @return
     *  String containing the RemittancePayeeBankAccount property.
     */
    public String getRemittancePayeeBankAccount(){
        return mRemittancePayeeBankAccount;
    }


    /**
     * Sets the RemittancePaymentPostDate property.
     *
     * @param pRemittancePaymentPostDate
     *  Date to use to update the property.
     */
    public void setRemittancePaymentPostDate(Date pRemittancePaymentPostDate){
        this.mRemittancePaymentPostDate = pRemittancePaymentPostDate;
    }
    /**
     * Retrieves the RemittancePaymentPostDate property.
     *
     * @return
     *  Date containing the RemittancePaymentPostDate property.
     */
    public Date getRemittancePaymentPostDate(){
        return mRemittancePaymentPostDate;
    }


    /**
     * Sets the RemittancePaymentReferenceNumberType property.
     *
     * @param pRemittancePaymentReferenceNumberType
     *  String to use to update the property.
     */
    public void setRemittancePaymentReferenceNumberType(String pRemittancePaymentReferenceNumberType){
        this.mRemittancePaymentReferenceNumberType = pRemittancePaymentReferenceNumberType;
    }
    /**
     * Retrieves the RemittancePaymentReferenceNumberType property.
     *
     * @return
     *  String containing the RemittancePaymentReferenceNumberType property.
     */
    public String getRemittancePaymentReferenceNumberType(){
        return mRemittancePaymentReferenceNumberType;
    }


    /**
     * Sets the RemittancePaymentReferenceNumber property.
     *
     * @param pRemittancePaymentReferenceNumber
     *  String to use to update the property.
     */
    public void setRemittancePaymentReferenceNumber(String pRemittancePaymentReferenceNumber){
        this.mRemittancePaymentReferenceNumber = pRemittancePaymentReferenceNumber;
    }
    /**
     * Retrieves the RemittancePaymentReferenceNumber property.
     *
     * @return
     *  String containing the RemittancePaymentReferenceNumber property.
     */
    public String getRemittancePaymentReferenceNumber(){
        return mRemittancePaymentReferenceNumber;
    }


    /**
     * Sets the RemittanceCheckDate property.
     *
     * @param pRemittanceCheckDate
     *  Date to use to update the property.
     */
    public void setRemittanceCheckDate(Date pRemittanceCheckDate){
        this.mRemittanceCheckDate = pRemittanceCheckDate;
    }
    /**
     * Retrieves the RemittanceCheckDate property.
     *
     * @return
     *  Date containing the RemittanceCheckDate property.
     */
    public Date getRemittanceCheckDate(){
        return mRemittanceCheckDate;
    }


    /**
     * Sets the RemittancePayeeErpAccount property.
     *
     * @param pRemittancePayeeErpAccount
     *  String to use to update the property.
     */
    public void setRemittancePayeeErpAccount(String pRemittancePayeeErpAccount){
        this.mRemittancePayeeErpAccount = pRemittancePayeeErpAccount;
    }
    /**
     * Retrieves the RemittancePayeeErpAccount property.
     *
     * @return
     *  String containing the RemittancePayeeErpAccount property.
     */
    public String getRemittancePayeeErpAccount(){
        return mRemittancePayeeErpAccount;
    }


    /**
     * Sets the RemittanceTransactionDate property.
     *
     * @param pRemittanceTransactionDate
     *  Date to use to update the property.
     */
    public void setRemittanceTransactionDate(Date pRemittanceTransactionDate){
        this.mRemittanceTransactionDate = pRemittanceTransactionDate;
    }
    /**
     * Retrieves the RemittanceTransactionDate property.
     *
     * @return
     *  Date containing the RemittanceTransactionDate property.
     */
    public Date getRemittanceTransactionDate(){
        return mRemittanceTransactionDate;
    }


    /**
     * Sets the RemittanceTransactionReference property.
     *
     * @param pRemittanceTransactionReference
     *  String to use to update the property.
     */
    public void setRemittanceTransactionReference(String pRemittanceTransactionReference){
        this.mRemittanceTransactionReference = pRemittanceTransactionReference;
    }
    /**
     * Retrieves the RemittanceTransactionReference property.
     *
     * @return
     *  String containing the RemittanceTransactionReference property.
     */
    public String getRemittanceTransactionReference(){
        return mRemittanceTransactionReference;
    }


    /**
     * Sets the RemittanceStatusCd property.
     *
     * @param pRemittanceStatusCd
     *  String to use to update the property.
     */
    public void setRemittanceStatusCd(String pRemittanceStatusCd){
        this.mRemittanceStatusCd = pRemittanceStatusCd;
    }
    /**
     * Retrieves the RemittanceStatusCd property.
     *
     * @return
     *  String containing the RemittanceStatusCd property.
     */
    public String getRemittanceStatusCd(){
        return mRemittanceStatusCd;
    }


    /**
     * Sets the RemittanceStartAddDate property.
     *
     * @param pRemittanceStartAddDate
     *  Date to use to update the property.
     */
    public void setRemittanceStartAddDate(Date pRemittanceStartAddDate){
        this.mRemittanceStartAddDate = pRemittanceStartAddDate;
    }
    /**
     * Retrieves the RemittanceStartAddDate property.
     *
     * @return
     *  Date containing the RemittanceStartAddDate property.
     */
    public Date getRemittanceStartAddDate(){
        return mRemittanceStartAddDate;
    }


    /**
     * Sets the RemittanceEndAddDate property.
     *
     * @param pRemittanceEndAddDate
     *  Date to use to update the property.
     */
    public void setRemittanceEndAddDate(Date pRemittanceEndAddDate){
        this.mRemittanceEndAddDate = pRemittanceEndAddDate;
    }
    /**
     * Retrieves the RemittanceEndAddDate property.
     *
     * @return
     *  Date containing the RemittanceEndAddDate property.
     */
    public Date getRemittanceEndAddDate(){
        return mRemittanceEndAddDate;
    }


    /**
     * Sets the RemittanceAddDate property.
     *
     * @param pRemittanceAddDate
     *  Date to use to update the property.
     */
    public void setRemittanceAddDate(Date pRemittanceAddDate){
        this.mRemittanceAddDate = pRemittanceAddDate;
    }
    /**
     * Retrieves the RemittanceAddDate property.
     *
     * @return
     *  Date containing the RemittanceAddDate property.
     */
    public Date getRemittanceAddDate(){
        return mRemittanceAddDate;
    }


    /**
     * Sets the RemittanceAddBy property.
     *
     * @param pRemittanceAddBy
     *  String to use to update the property.
     */
    public void setRemittanceAddBy(String pRemittanceAddBy){
        this.mRemittanceAddBy = pRemittanceAddBy;
    }
    /**
     * Retrieves the RemittanceAddBy property.
     *
     * @return
     *  String containing the RemittanceAddBy property.
     */
    public String getRemittanceAddBy(){
        return mRemittanceAddBy;
    }


    /**
     * Sets the RemittanceModDate property.
     *
     * @param pRemittanceModDate
     *  Date to use to update the property.
     */
    public void setRemittanceModDate(Date pRemittanceModDate){
        this.mRemittanceModDate = pRemittanceModDate;
    }
    /**
     * Retrieves the RemittanceModDate property.
     *
     * @return
     *  Date containing the RemittanceModDate property.
     */
    public Date getRemittanceModDate(){
        return mRemittanceModDate;
    }


    /**
     * Sets the RemittanceModBy property.
     *
     * @param pRemittanceModBy
     *  String to use to update the property.
     */
    public void setRemittanceModBy(String pRemittanceModBy){
        this.mRemittanceModBy = pRemittanceModBy;
    }
    /**
     * Retrieves the RemittanceModBy property.
     *
     * @return
     *  String containing the RemittanceModBy property.
     */
    public String getRemittanceModBy(){
        return mRemittanceModBy;
    }


    /**
     * Sets the RemittanceDetailId property.
     *
     * @param pRemittanceDetailId
     *  int to use to update the property.
     */
    public void setRemittanceDetailId(int pRemittanceDetailId){
        this.mRemittanceDetailId = pRemittanceDetailId;
    }
    /**
     * Retrieves the RemittanceDetailId property.
     *
     * @return
     *  int containing the RemittanceDetailId property.
     */
    public int getRemittanceDetailId(){
        return mRemittanceDetailId;
    }


    /**
     * Sets the RemittanceDetailSiteReference property.
     *
     * @param pRemittanceDetailSiteReference
     *  String to use to update the property.
     */
    public void setRemittanceDetailSiteReference(String pRemittanceDetailSiteReference){
        this.mRemittanceDetailSiteReference = pRemittanceDetailSiteReference;
    }
    /**
     * Retrieves the RemittanceDetailSiteReference property.
     *
     * @return
     *  String containing the RemittanceDetailSiteReference property.
     */
    public String getRemittanceDetailSiteReference(){
        return mRemittanceDetailSiteReference;
    }


    /**
     * Sets the RemittanceDetailInvoiceNumber property.
     *
     * @param pRemittanceDetailInvoiceNumber
     *  String to use to update the property.
     */
    public void setRemittanceDetailInvoiceNumber(String pRemittanceDetailInvoiceNumber){
        this.mRemittanceDetailInvoiceNumber = pRemittanceDetailInvoiceNumber;
    }
    /**
     * Retrieves the RemittanceDetailInvoiceNumber property.
     *
     * @return
     *  String containing the RemittanceDetailInvoiceNumber property.
     */
    public String getRemittanceDetailInvoiceNumber(){
        return mRemittanceDetailInvoiceNumber;
    }


    /**
     * Sets the RemittanceDetailInvoiceType property.
     *
     * @param pRemittanceDetailInvoiceType
     *  String to use to update the property.
     */
    public void setRemittanceDetailInvoiceType(String pRemittanceDetailInvoiceType){
        this.mRemittanceDetailInvoiceType = pRemittanceDetailInvoiceType;
    }
    /**
     * Retrieves the RemittanceDetailInvoiceType property.
     *
     * @return
     *  String containing the RemittanceDetailInvoiceType property.
     */
    public String getRemittanceDetailInvoiceType(){
        return mRemittanceDetailInvoiceType;
    }


    /**
     * Sets the RemittanceDetailDiscountAmount property.
     *
     * @param pRemittanceDetailDiscountAmount
     *  java.math.BigDecimal to use to update the property.
     */
    public void setRemittanceDetailDiscountAmount(java.math.BigDecimal pRemittanceDetailDiscountAmount){
        this.mRemittanceDetailDiscountAmount = pRemittanceDetailDiscountAmount;
    }
    /**
     * Retrieves the RemittanceDetailDiscountAmount property.
     *
     * @return
     *  java.math.BigDecimal containing the RemittanceDetailDiscountAmount property.
     */
    public java.math.BigDecimal getRemittanceDetailDiscountAmount(){
        return mRemittanceDetailDiscountAmount;
    }


    /**
     * Sets the RemittanceDetailNetAmount property.
     *
     * @param pRemittanceDetailNetAmount
     *  java.math.BigDecimal to use to update the property.
     */
    public void setRemittanceDetailNetAmount(java.math.BigDecimal pRemittanceDetailNetAmount){
        this.mRemittanceDetailNetAmount = pRemittanceDetailNetAmount;
    }
    /**
     * Retrieves the RemittanceDetailNetAmount property.
     *
     * @return
     *  java.math.BigDecimal containing the RemittanceDetailNetAmount property.
     */
    public java.math.BigDecimal getRemittanceDetailNetAmount(){
        return mRemittanceDetailNetAmount;
    }


    /**
     * Sets the RemittanceDetailOrigInvoiceAmount property.
     *
     * @param pRemittanceDetailOrigInvoiceAmount
     *  java.math.BigDecimal to use to update the property.
     */
    public void setRemittanceDetailOrigInvoiceAmount(java.math.BigDecimal pRemittanceDetailOrigInvoiceAmount){
        this.mRemittanceDetailOrigInvoiceAmount = pRemittanceDetailOrigInvoiceAmount;
    }
    /**
     * Retrieves the RemittanceDetailOrigInvoiceAmount property.
     *
     * @return
     *  java.math.BigDecimal containing the RemittanceDetailOrigInvoiceAmount property.
     */
    public java.math.BigDecimal getRemittanceDetailOrigInvoiceAmount(){
        return mRemittanceDetailOrigInvoiceAmount;
    }


    /**
     * Sets the RemittanceDetailCustomerPoNumber property.
     *
     * @param pRemittanceDetailCustomerPoNumber
     *  String to use to update the property.
     */
    public void setRemittanceDetailCustomerPoNumber(String pRemittanceDetailCustomerPoNumber){
        this.mRemittanceDetailCustomerPoNumber = pRemittanceDetailCustomerPoNumber;
    }
    /**
     * Retrieves the RemittanceDetailCustomerPoNumber property.
     *
     * @return
     *  String containing the RemittanceDetailCustomerPoNumber property.
     */
    public String getRemittanceDetailCustomerPoNumber(){
        return mRemittanceDetailCustomerPoNumber;
    }


    /**
     * Sets the RemittanceDetailCustomerSupplierNumber property.
     *
     * @param pRemittanceDetailCustomerSupplierNumber
     *  String to use to update the property.
     */
    public void setRemittanceDetailCustomerSupplierNumber(String pRemittanceDetailCustomerSupplierNumber){
        this.mRemittanceDetailCustomerSupplierNumber = pRemittanceDetailCustomerSupplierNumber;
    }
    /**
     * Retrieves the RemittanceDetailCustomerSupplierNumber property.
     *
     * @return
     *  String containing the RemittanceDetailCustomerSupplierNumber property.
     */
    public String getRemittanceDetailCustomerSupplierNumber(){
        return mRemittanceDetailCustomerSupplierNumber;
    }


    /**
     * Sets the RemittanceDetailStatusCd property.
     *
     * @param pRemittanceDetailStatusCd
     *  String to use to update the property.
     */
    public void setRemittanceDetailStatusCd(String pRemittanceDetailStatusCd){
        this.mRemittanceDetailStatusCd = pRemittanceDetailStatusCd;
    }
    /**
     * Retrieves the RemittanceDetailStatusCd property.
     *
     * @return
     *  String containing the RemittanceDetailStatusCd property.
     */
    public String getRemittanceDetailStatusCd(){
        return mRemittanceDetailStatusCd;
    }


    /**
     * Sets the RemittanceDetailAddDate property.
     *
     * @param pRemittanceDetailAddDate
     *  Date to use to update the property.
     */
    public void setRemittanceDetailAddDate(Date pRemittanceDetailAddDate){
        this.mRemittanceDetailAddDate = pRemittanceDetailAddDate;
    }
    /**
     * Retrieves the RemittanceDetailAddDate property.
     *
     * @return
     *  Date containing the RemittanceDetailAddDate property.
     */
    public Date getRemittanceDetailAddDate(){
        return mRemittanceDetailAddDate;
    }


    /**
     * Sets the RemittanceDetailAddBy property.
     *
     * @param pRemittanceDetailAddBy
     *  String to use to update the property.
     */
    public void setRemittanceDetailAddBy(String pRemittanceDetailAddBy){
        this.mRemittanceDetailAddBy = pRemittanceDetailAddBy;
    }
    /**
     * Retrieves the RemittanceDetailAddBy property.
     *
     * @return
     *  String containing the RemittanceDetailAddBy property.
     */
    public String getRemittanceDetailAddBy(){
        return mRemittanceDetailAddBy;
    }


    /**
     * Sets the RemittanceDetailModDate property.
     *
     * @param pRemittanceDetailModDate
     *  Date to use to update the property.
     */
    public void setRemittanceDetailModDate(Date pRemittanceDetailModDate){
        this.mRemittanceDetailModDate = pRemittanceDetailModDate;
    }
    /**
     * Retrieves the RemittanceDetailModDate property.
     *
     * @return
     *  Date containing the RemittanceDetailModDate property.
     */
    public Date getRemittanceDetailModDate(){
        return mRemittanceDetailModDate;
    }


    /**
     * Sets the RemittanceDetailModBy property.
     *
     * @param pRemittanceDetailModBy
     *  String to use to update the property.
     */
    public void setRemittanceDetailModBy(String pRemittanceDetailModBy){
        this.mRemittanceDetailModBy = pRemittanceDetailModBy;
    }
    /**
     * Retrieves the RemittanceDetailModBy property.
     *
     * @return
     *  String containing the RemittanceDetailModBy property.
     */
    public String getRemittanceDetailModBy(){
        return mRemittanceDetailModBy;
    }


    /**
     * Sets the RemittanceInErrorState property.
     *
     * @param pRemittanceInErrorState
     *  Boolean to use to update the property.
     */
    public void setRemittanceInErrorState(Boolean pRemittanceInErrorState){
        this.mRemittanceInErrorState = pRemittanceInErrorState;
    }
    /**
     * Retrieves the RemittanceInErrorState property.
     *
     * @return
     *  Boolean containing the RemittanceInErrorState property.
     */
    public Boolean getRemittanceInErrorState(){
        return mRemittanceInErrorState;
    }


    /**
     * Sets the RemittanceDetailInErrorState property.
     *
     * @param pRemittanceDetailInErrorState
     *  Boolean to use to update the property.
     */
    public void setRemittanceDetailInErrorState(Boolean pRemittanceDetailInErrorState){
        this.mRemittanceDetailInErrorState = pRemittanceDetailInErrorState;
    }
    /**
     * Retrieves the RemittanceDetailInErrorState property.
     *
     * @return
     *  Boolean containing the RemittanceDetailInErrorState property.
     */
    public Boolean getRemittanceDetailInErrorState(){
        return mRemittanceDetailInErrorState;
    }


    /**
     * Sets the RemittancePropertyAddDate property.
     *
     * @param pRemittancePropertyAddDate
     *  Date to use to update the property.
     */
    public void setRemittancePropertyAddDate(Date pRemittancePropertyAddDate){
        this.mRemittancePropertyAddDate = pRemittancePropertyAddDate;
    }
    /**
     * Retrieves the RemittancePropertyAddDate property.
     *
     * @return
     *  Date containing the RemittancePropertyAddDate property.
     */
    public Date getRemittancePropertyAddDate(){
        return mRemittancePropertyAddDate;
    }


    /**
     * Sets the RemittancePropertyAddBy property.
     *
     * @param pRemittancePropertyAddBy
     *  String to use to update the property.
     */
    public void setRemittancePropertyAddBy(String pRemittancePropertyAddBy){
        this.mRemittancePropertyAddBy = pRemittancePropertyAddBy;
    }
    /**
     * Retrieves the RemittancePropertyAddBy property.
     *
     * @return
     *  String containing the RemittancePropertyAddBy property.
     */
    public String getRemittancePropertyAddBy(){
        return mRemittancePropertyAddBy;
    }


    /**
     * Sets the RemittancePropertyModDate property.
     *
     * @param pRemittancePropertyModDate
     *  Date to use to update the property.
     */
    public void setRemittancePropertyModDate(Date pRemittancePropertyModDate){
        this.mRemittancePropertyModDate = pRemittancePropertyModDate;
    }
    /**
     * Retrieves the RemittancePropertyModDate property.
     *
     * @return
     *  Date containing the RemittancePropertyModDate property.
     */
    public Date getRemittancePropertyModDate(){
        return mRemittancePropertyModDate;
    }


    /**
     * Sets the RemittancePropertyModBy property.
     *
     * @param pRemittancePropertyModBy
     *  String to use to update the property.
     */
    public void setRemittancePropertyModBy(String pRemittancePropertyModBy){
        this.mRemittancePropertyModBy = pRemittancePropertyModBy;
    }
    /**
     * Retrieves the RemittancePropertyModBy property.
     *
     * @return
     *  String containing the RemittancePropertyModBy property.
     */
    public String getRemittancePropertyModBy(){
        return mRemittancePropertyModBy;
    }


    /**
     * Sets the RemittancePropertyRemittancePropertyTypeCd property.
     *
     * @param pRemittancePropertyRemittancePropertyTypeCd
     *  String to use to update the property.
     */
    public void setRemittancePropertyRemittancePropertyTypeCd(String pRemittancePropertyRemittancePropertyTypeCd){
        this.mRemittancePropertyRemittancePropertyTypeCd = pRemittancePropertyRemittancePropertyTypeCd;
    }
    /**
     * Retrieves the RemittancePropertyRemittancePropertyTypeCd property.
     *
     * @return
     *  String containing the RemittancePropertyRemittancePropertyTypeCd property.
     */
    public String getRemittancePropertyRemittancePropertyTypeCd(){
        return mRemittancePropertyRemittancePropertyTypeCd;
    }


    /**
     * Sets the RemittancePropertyRemittancePropertyStatusCd property.
     *
     * @param pRemittancePropertyRemittancePropertyStatusCd
     *  String to use to update the property.
     */
    public void setRemittancePropertyRemittancePropertyStatusCd(String pRemittancePropertyRemittancePropertyStatusCd){
        this.mRemittancePropertyRemittancePropertyStatusCd = pRemittancePropertyRemittancePropertyStatusCd;
    }
    /**
     * Retrieves the RemittancePropertyRemittancePropertyStatusCd property.
     *
     * @return
     *  String containing the RemittancePropertyRemittancePropertyStatusCd property.
     */
    public String getRemittancePropertyRemittancePropertyStatusCd(){
        return mRemittancePropertyRemittancePropertyStatusCd;
    }


    /**
     * Sets the RemittancePropertyClwValue property.
     *
     * @param pRemittancePropertyClwValue
     *  String to use to update the property.
     */
    public void setRemittancePropertyClwValue(String pRemittancePropertyClwValue){
        this.mRemittancePropertyClwValue = pRemittancePropertyClwValue;
    }
    /**
     * Retrieves the RemittancePropertyClwValue property.
     *
     * @return
     *  String containing the RemittancePropertyClwValue property.
     */
    public String getRemittancePropertyClwValue(){
        return mRemittancePropertyClwValue;
    }


    /**
     * Sets the RemittancePropertyId property.
     *
     * @param pRemittancePropertyId
     *  int to use to update the property.
     */
    public void setRemittancePropertyId(int pRemittancePropertyId){
        this.mRemittancePropertyId = pRemittancePropertyId;
    }
    /**
     * Retrieves the RemittancePropertyId property.
     *
     * @return
     *  int containing the RemittancePropertyId property.
     */
    public int getRemittancePropertyId(){
        return mRemittancePropertyId;
    }


    
}
