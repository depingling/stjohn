/**
 * GetTaxResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Result data returned from {@link TaxSvcSoap#getTax}.
 * @see GetTaxRequest
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class GetTaxResult  extends com.avalara.avatax.services.tax.BaseResult  implements java.io.Serializable {

    private java.lang.String docId;

    private com.avalara.avatax.services.tax.DocumentType docType;

    private java.lang.String docCode;

    private java.util.Date docDate;

    private com.avalara.avatax.services.tax.DocStatus docStatus;

    private boolean reconciled;

    private java.util.Calendar timestamp;

    private java.math.BigDecimal totalAmount;

    private java.math.BigDecimal totalDiscount;

    private java.math.BigDecimal totalExemption;

    private java.math.BigDecimal totalTaxable;

    private java.math.BigDecimal totalTax;
     private java.math.BigDecimal totalTaxCalculated;
    private int hashCode;
    /**
     *  Tax broken down by individual {@link TaxLine}.
     */
    private com.avalara.avatax.services.tax.ArrayOfTaxLine taxLines;
    private com.avalara.avatax.services.tax.ArrayOfTaxAddress taxAddresses;
    private boolean locked;
    private int adjustmentReason;
    private java.lang.String adjustmentDescription;
    private int version;
    private java.util.Date taxDate;
    private com.avalara.avatax.services.tax.ArrayOfTaxDetail taxSummary;
    /**
     * Initializes a new instance of the class.
     */
    public GetTaxResult() {

    }

    /**
     * Initializes a new instance of the class.
     *
     * @param docId
     * @param docType
     * @param docCode
     * @param docDate
     * @param docStatus
     * @param reconciled
     * @param timestamp
     * @param totalAmount
     * @param totalDiscount
     * @param totalExemption
     * @param totalTaxable
     * @param totalTax
     * @param taxLines
     */
    private GetTaxResult(
            java.lang.String docId,
            com.avalara.avatax.services.tax.DocumentType docType,
            java.lang.String docCode,
            java.util.Date docDate,
            com.avalara.avatax.services.tax.DocStatus docStatus,
            boolean reconciled,
            java.util.Calendar timestamp,
            java.math.BigDecimal totalAmount,
            java.math.BigDecimal totalDiscount,
            java.math.BigDecimal totalExemption,
            java.math.BigDecimal totalTaxable,
            java.math.BigDecimal totalTax,
            int hashCode,
            com.avalara.avatax.services.tax.ArrayOfTaxLine taxLines,
            boolean locked,
            int adjustmentReason,
            java.lang.String adjustmentDescription,
            int version,
            java.util.Date taxDate ,
            com.avalara.avatax.services.tax.ArrayOfTaxAddress taxAddresses    )
    {
        this.docId = docId;
        this.docType = docType;
        this.docCode = docCode;
        this.docDate = docDate;
        this.docStatus = docStatus;
        this.reconciled = reconciled;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.totalExemption = totalExemption;
        this.totalTaxable = totalTaxable;
        this.totalTax = totalTax;
        this.hashCode = hashCode;
        this.taxLines = taxLines;
        this.locked = locked;
        this.adjustmentReason = adjustmentReason;
        this.adjustmentDescription = adjustmentDescription;
        this.version = version;
        this.taxDate = taxDate;
        this.taxAddresses=taxAddresses;
    }


    /**
     * Gets the Document ID.
     * <p>
     * This is a unique AvaTax identifier for this document which can be used for PostTax,
     * CommitTax, CancelTax, and GetTaxHistory.
     * </p>
     *
     * @return Document ID
     */
    public java.lang.String getDocId() {
        return docId;
    }


    /**
     * Sets the Document ID.
     * <p>
     * This is a unique AvaTax identifier for this document which can be used for PostTax,
     * CommitTax, CancelTax, and GetTaxHistory.
     * </p>
     *
     * @param docId
     */
    public void setDocId(java.lang.String docId) {
        this.docId = docId;
    }


    /**
     * Gets the Document Type.
     * <p>
     * See {@link GetTaxRequest#getDocType} on <b>GetTaxRequest</b> for more information about this member.
     * </p>
     *
     * @return Document Type
     */
    public com.avalara.avatax.services.tax.DocumentType getDocType() {
        return docType;
    }


    /**
     * Sets the the Document Type.
     * <p>
     * See {@link GetTaxRequest#getDocType} on <b>GetTaxRequest</b> for more information about this member.
     * </p>
     *
     * @param docType
     */
    public void setDocType(com.avalara.avatax.services.tax.DocumentType docType) {
        this.docType = docType;
    }


    /**
     * Gets the internal reference code used by the client application;  This is used for
     * operations such as Post and GetTaxHistory.
     * <p>
     * See {@link GetTaxRequest#getDocCode} on <b>GetTaxRequest</b> for more information
     * about this member.
     * </p>
     *
     * @return docCode
     */
    public java.lang.String getDocCode() {
        return docCode;
    }


    /**
     * Sets the internal reference code used by the client application;  This is used for
     * operations such as Post and GetTaxHistory.
     * <p>
     * See {@link GetTaxRequest#getDocCode} on <b>GetTaxRequest</b> for more information
     * about this member.
     * </p>
     *
     * @param docCode
     */
    public void setDocCode(java.lang.String docCode) {
        this.docCode = docCode;
    }


    /**
     * Gets the date on the invoice, purchase order, etc.
     * <p>
     * See {@link GetTaxRequest#getDocDate} on <b>GetTaxRequest</b> for more information about this member.
     * </p>
     *
     * @return docDate
     */
    public java.util.Date getDocDate() {
        return docDate;
    }


    /**
     * Sets the date on the invoice, purchase order, etc.
     * <p>
     * See {@link GetTaxRequest#getDocDate} on <b>GetTaxRequest</b> for more information about this member.
     * </p>
     *
     * @param docDate
     */
    public void setDocDate(java.util.Date docDate) {
        this.docDate = docDate;
    }


    /**
     * Gets the document's status after the tax calculation.
     *
     * @return docStatus
     */
    public com.avalara.avatax.services.tax.DocStatus getDocStatus() {
        return docStatus;
    }


    /**
     * Sets the document's status after the tax calculation.
     *
     * @param docStatus
     */
    public void setDocStatus(com.avalara.avatax.services.tax.DocStatus docStatus) {
        this.docStatus = docStatus;
    }


    /**
     * True if the document has been reconciled;  Only committed documents can be reconciled.
     * <p>
     * For information on committing documents, see the <b>TaxSvc</b>'s
     * {@link TaxSvcSoap#commitTax} method. For information
     * on reconciling documents, see the {@link TaxSvcSoap#reconcileTaxHistory} method.
     * </p>
     *
     * @return reconciled
     */
    public boolean isReconciled() {
        return reconciled;
    }


    /**
     * True if the document has been reconciled;  Only committed documents can be reconciled.
     * <p>
     * For information on committing documents, see the <b>TaxSvc</b>'s
     * {@link TaxSvcSoap#commitTax} method. For information
     * on reconciling documents, see the {@link TaxSvcSoap#reconcileTaxHistory} method.
     * </p>
     *
     * @param reconciled
     */
    public void setReconciled(boolean reconciled) {
        this.reconciled = reconciled;
    }


    /**
     * Date of the last status change on the document (i.e. Save date, Post date, Commit date, Cancel date).
     *
     * @return timestamp
     */
    public java.util.Calendar getTimestamp() {
        return timestamp;
    }


    /**
     * Date of the last status change on the document (i.e. Save date, Post date, Commit date, Cancel date).
     *
     * @param timestamp
     */
    public void setTimestamp(java.util.Calendar timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Gets the sum of all line {@link Line#getAmount} values.
     *
     * @return totalAmount
     */
    public java.math.BigDecimal getTotalAmount() {
        return totalAmount;
    }


    /**
     * Sets the sum of all line {@link Line#getAmount} values.
     *
     * @param totalAmount
     */
    public void setTotalAmount(java.math.BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    /**
     * Gets the sum of all <b>TaxLine</b> {@link TaxLine#getDiscount} amounts; Typically it
     * will equal the requested Discount, but, but it is possible that no lines were marked as discounted.
     *
     * @return totalDiscount
     */
    public java.math.BigDecimal getTotalDiscount() {
        return totalDiscount;
    }


    /**
     * Sets the sum of all <b>TaxLine</b> {@link TaxLine#getDiscount} amounts;
     * Typically it will equal the requested Discount, but, but it is possible that no lines were marked as discounted.
     *
     * @param totalDiscount
     */
    public void setTotalDiscount(java.math.BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }


    /**
     * Gets the sum of all <b>TaxLine</b> {@link TaxLine#getExemption} amounts.
     *
     * @return totalExemption
     * @deprecated See {@link TaxDetail#getExemption}.
     */
    public java.math.BigDecimal getTotalExemption() {
        return totalExemption;
    }


    /**
     * Sets the sum of all <b>TaxLine</b> {@link TaxLine#getExemption} amounts.
     *
     * @param totalExemption
     * @deprecated See {@link TaxDetail#setExemption}.
     */
    public void setTotalExemption(java.math.BigDecimal totalExemption) {
        this.totalExemption = totalExemption;
    }


    /**
     * Gets the amount the tax is based on;  This is the total of all {@link Line} <b>Base</b> amounts;
     * Typically it will be equal to the document
     * {@link GetTaxResult#getTotalAmount} - {@link GetTaxRequest#getDiscount} - {@link #getTotalExemption}.
     *
     * @return totalTaxable
     * @deprecated See {@link TaxDetail#getTaxable}.
     */
    public java.math.BigDecimal getTotalTaxable() {
        return totalTaxable;
    }


    /**
     * Sets the amount the tax is based on;  This is the total of all {@link Line} <b>Base</b> amounts;
     * Typically it will be equal to the document
     * {@link GetTaxResult#getTotalAmount} - {@link GetTaxRequest#getDiscount} - {@link #getTotalExemption}.
     *
     * @param totalTaxable
     * @deprecated See {@link TaxDetail#setTaxable}.
     */
    public void setTotalTaxable(java.math.BigDecimal totalTaxable) {
        this.totalTaxable = totalTaxable;
    }


    /**
     *  Gets the total tax for the document.
     *
     * @return totalTax
     */
    public java.math.BigDecimal getTotalTax() {
        return totalTax;
    }


    /**
     * Sets the total tax for the document.
     *
     * @param totalTax
     */
    public void setTotalTax(java.math.BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    /**
     * Gets the totalTaxCalculated value for this GetTaxResult.
     *
     * @return totalTaxCalculated
     */
    public java.math.BigDecimal getTotalTaxCalculated() {
        return totalTaxCalculated;
    }


    /**
     * Sets the totalTaxCalculated value for this GetTaxResult.
     *
     * @param totalTaxCalculated
     */
    public void setTotalTaxCalculated(java.math.BigDecimal totalTaxCalculated) {
        this.totalTaxCalculated = totalTaxCalculated;
    }


    /**
     * Gets the hashCode value for this GetTaxResult.
     * <p>
     * This should be computed by an SDK developer using some standard algorithm out of the content of the object. This value gets stored in the system and can be retrieved for the cross checking [Internal Reconciliation purpose].
     * See sample code for more details
     * </p>
     * @return hashCode
     */
    public int getHashCode() {
        return hashCode;
    }


    /**
     * Sets the hashCode value for this GetTaxResult.
     * <p>
     * This should be computed by an SDK developer using some standard algorithm out of the content of the object. This value gets stored in the system and can be retrieved for the cross checking [Internal Reconciliation purpose].
     * </p>
     * See sample code for more details
     * @param hashCode
     */
    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    /**
     * Gets the Tax broken down by individual {@link TaxLine}.
     *
     * @return taxLines
     */
    public com.avalara.avatax.services.tax.ArrayOfTaxLine getTaxLines() {
        return taxLines;
    }


    /**
     * Sets the Tax broken down by individual {@link TaxLine}.
     *
     * @param taxLines
     */
    public void setTaxLines(com.avalara.avatax.services.tax.ArrayOfTaxLine taxLines) {
        this.taxLines = taxLines;
    }

     /**
     * Gets the taxAddresses value for this GetTaxResult.
     *
     * @return taxAddresses
     */
    public com.avalara.avatax.services.tax.ArrayOfTaxAddress getTaxAddresses() {
        return taxAddresses;
    }


    /**
     * Sets the taxAddresses value for this GetTaxResult.
     *
     * @param taxAddresses
     */
    public void setTaxAddresses(com.avalara.avatax.services.tax.ArrayOfTaxAddress taxAddresses) {
        this.taxAddresses = taxAddresses;
    }

    private java.lang.Object __equalsCalc = null;
    /**
     * Gets the locked value for this GetTaxResult.
     * <p>
     * Flag indicating if a Document has been locked by Avalara's MRS process.
     * Locked documents can not be modified and can not be cancelled because they have been reported on Tax Return. 
     * </p>
     * @return locked
     */
    public boolean getLocked() {
        return locked;
    }


    /**
     * Sets the locked value for this GetTaxResult.
     *
     * @param locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    /**
     * Gets the {@link AdjustTaxRequest#adjustmentReason} value for this GetTaxResult.
     * @return adjustmentReason
     */
    public int getAdjustmentReason() {
        return adjustmentReason;
    }


    /**
     * Sets the adjustmentReason value for this GetTaxResult.
     *
     * @param adjustmentReason
     */
    public void setAdjustmentReason(int adjustmentReason) {
        this.adjustmentReason = adjustmentReason;
    }


    /**
     * Gets the {@link AdjustTaxRequest#adjustmentDescription} value for this GetTaxResult.
     *
     * @return adjustmentDescription
     */
    public java.lang.String getAdjustmentDescription() {
        return adjustmentDescription;
    }


    /**
     * Sets the adjustmentDescription value for this GetTaxResult.
     *
     * @param adjustmentDescription
     */
    public void setAdjustmentDescription(java.lang.String adjustmentDescription) {
        this.adjustmentDescription = adjustmentDescription;
    }


    /**
     * Gets the version value for this GetTaxResult.
     *
     * @return version
     */
    public int getVersion() {
        return version;
    }


    /**
     * Sets the version value for this GetTaxResult.
     *
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * Gets the taxDate value for this GetTaxResult.
     *
     * @return taxDate
     */
    public java.util.Date getTaxDate() {
        return taxDate;
    }


    /**
     * Sets the taxDate value for this GetTaxResult.
     *
     * @param taxDate
     */
    public void setTaxDate(java.util.Date taxDate) {
        this.taxDate = taxDate;
    }
    /**
     * Gets the taxSummary value for this GetTaxResult.
     * TaxSummary is an array that gets returned if the {@link DetailLevel} in GetTaxRequest is set to summary.
     * It contains summary of TaxDetails for the entire document grouped by Country, JurisType, JurisCode, and Rate.
     * @return taxSummary
     */
    public com.avalara.avatax.services.tax.ArrayOfTaxDetail getTaxSummary() {
        return taxSummary;
    }


    /**
     * Sets the taxSummary value for this GetTaxResult.
     * 
     * @param taxSummary
     */
    public void setTaxSummary(com.avalara.avatax.services.tax.ArrayOfTaxDetail taxSummary) {
        this.taxSummary = taxSummary;
    }
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetTaxResult)) return false;
        GetTaxResult other = (GetTaxResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.docId==null && other.getDocId()==null) ||
                        (this.docId!=null &&
                                this.docId.equals(other.getDocId()))) &&
                ((this.docType==null && other.getDocType()==null) ||
                        (this.docType!=null &&
                                this.docType.equals(other.getDocType()))) &&
                ((this.docCode==null && other.getDocCode()==null) ||
                        (this.docCode!=null &&
                                this.docCode.equals(other.getDocCode()))) &&
                ((this.docDate==null && other.getDocDate()==null) ||
                        (this.docDate!=null &&
                                this.docDate.equals(other.getDocDate()))) &&
                ((this.docStatus==null && other.getDocStatus()==null) ||
                        (this.docStatus!=null &&
                                this.docStatus.equals(other.getDocStatus()))) &&
                this.reconciled == other.isReconciled() &&
                ((this.timestamp==null && other.getTimestamp()==null) ||
                        (this.timestamp!=null &&
                                this.timestamp.equals(other.getTimestamp()))) &&
                ((this.totalAmount==null && other.getTotalAmount()==null) ||
                        (this.totalAmount!=null &&
                                this.totalAmount.equals(other.getTotalAmount()))) &&
                ((this.totalDiscount==null && other.getTotalDiscount()==null) ||
                        (this.totalDiscount!=null &&
                                this.totalDiscount.equals(other.getTotalDiscount()))) &&
                ((this.totalExemption==null && other.getTotalExemption()==null) ||
                        (this.totalExemption!=null &&
                                this.totalExemption.equals(other.getTotalExemption()))) &&
                ((this.totalTaxable==null && other.getTotalTaxable()==null) ||
                        (this.totalTaxable!=null &&
                                this.totalTaxable.equals(other.getTotalTaxable()))) &&
                ((this.totalTax==null && other.getTotalTax()==null) ||
                        (this.totalTax!=null &&
                                this.totalTax.equals(other.getTotalTax()))) &&
                ((this.totalTaxCalculated==null && other.getTotalTaxCalculated()==null) ||
                        (this.totalTaxCalculated!=null &&
                                this.totalTaxCalculated.equals(other.getTotalTaxCalculated()))) &&
                this.hashCode == other.getHashCode() &&
                ((this.taxLines==null && other.getTaxLines()==null) ||
                        (this.taxLines!=null &&
                                this.taxLines.equals(other.getTaxLines()))) &&
                ((this.taxAddresses==null && other.getTaxAddresses()==null) ||
                        (this.taxAddresses!=null &&
                                this.taxAddresses.equals(other.getTaxAddresses()))) &&
                this.locked == other.getLocked() &&
                this.adjustmentReason == other.getAdjustmentReason() &&
                ((this.adjustmentDescription==null && other.getAdjustmentDescription()==null) ||
                        (this.adjustmentDescription!=null &&
                                this.adjustmentDescription.equals(other.getAdjustmentDescription()))) &&
                this.version == other.getVersion() &&
                ((this.taxDate==null && other.getTaxDate()==null) ||
                        (this.taxDate!=null &&
              this.taxDate.equals(other.getTaxDate()))) &&
            ((this.taxSummary==null && other.getTaxSummary()==null) || 
             (this.taxSummary!=null &&
              this.taxSummary.equals(other.getTaxSummary())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    /**
     *  Serves as a hash function for a particular type,
     * suitable for use in hashing algorithms and data
     * structures like a hash table.
     * @return  hash code for this GetTaxRequest object
     * @see java.lang.Object#hashCode
     */
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDocId() != null) {
            _hashCode += getDocId().hashCode();
        }
        if (getDocType() != null) {
            _hashCode += getDocType().hashCode();
        }
        if (getDocCode() != null) {
            _hashCode += getDocCode().hashCode();
        }
        if (getDocDate() != null) {
            _hashCode += getDocDate().hashCode();
        }
        if (getDocStatus() != null) {
            _hashCode += getDocStatus().hashCode();
        }
        _hashCode += (isReconciled() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        if (getTotalAmount() != null) {
            _hashCode += getTotalAmount().hashCode();
        }
        if (getTotalDiscount() != null) {
            _hashCode += getTotalDiscount().hashCode();
        }
        if (getTotalExemption() != null) {
            _hashCode += getTotalExemption().hashCode();
        }
        if (getTotalTaxable() != null) {
            _hashCode += getTotalTaxable().hashCode();
        }
        if (getTotalTax() != null) {
            _hashCode += getTotalTax().hashCode();
        }
         if (getTotalTaxCalculated() != null) {
            _hashCode += getTotalTaxCalculated().hashCode();
        }
        _hashCode += getHashCode();
        if (getTaxLines() != null) {
            _hashCode += getTaxLines().hashCode();
        }
        if (getTaxAddresses() != null) {
            _hashCode += getTaxAddresses().hashCode();
        }
        _hashCode += (getLocked() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getAdjustmentReason();
        if (getAdjustmentDescription() != null) {
            _hashCode += getAdjustmentDescription().hashCode();
        }
        _hashCode += getVersion();
        if (getTaxDate() != null) {
            _hashCode += getTaxDate().hashCode();
        }
        if (getTaxSummary() != null) {
            _hashCode += getTaxSummary().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(GetTaxResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocumentType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reconciled");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Reconciled"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDiscount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalDiscount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalExemption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalExemption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTaxable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalTaxable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalTax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTaxCalculated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalTaxCalculated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hashCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "HashCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxLines");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxLines"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxLine"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxAddresses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxAddresses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locked");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Locked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustmentReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustmentReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustmentDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustmentDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }


    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }


    /**
     * Get Axis Custom Serializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }


    /**
     * Get Axis Custom Deserializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
