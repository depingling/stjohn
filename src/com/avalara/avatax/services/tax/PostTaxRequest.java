/**
 * PostTaxRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Data to pass to {@link TaxSvcSoap#postTax}.
 * <p>
 * A document can be indicated solely by the {@link PostTaxRequest#getDocId} if it is known.
 * Otherwise the request must specify all of {@link PostTaxRequest#getCompanyCode},
 * {@link PostTaxRequest#getDocCode}, and
 * {@link PostTaxRequest#getDocType} in order to uniquely identify the document.
 * </p>
 * @see PostTaxResult
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class PostTaxRequest  implements java.io.Serializable {


    private java.lang.String docId;

    private java.lang.String companyCode;

    private com.avalara.avatax.services.tax.DocumentType docType;

    private java.lang.String docCode;

    private java.util.Date docDate;

    private java.math.BigDecimal totalAmount;

    private java.math.BigDecimal totalTax;
    private int hashCode;
    private boolean commit;

    public PostTaxRequest() {
        this.docType = DocumentType.SalesOrder;
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param docId
     * @param companyCode
     * @param docType
     * @param docCode
     * @param docDate
     * @param totalAmount
     * @param totalTax
     */
    private PostTaxRequest(
            java.lang.String docId,
            java.lang.String companyCode,
            com.avalara.avatax.services.tax.DocumentType docType,
            java.lang.String docCode,
            java.util.Date docDate,
            java.math.BigDecimal totalAmount,
            java.math.BigDecimal totalTax,
            int hashCode,
            boolean commit) {
        this.docId = docId;
        this.companyCode = companyCode;
        this.docType = docType;
        this.docCode = docCode;
        this.docDate = docDate;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.hashCode = hashCode;
        this.commit = commit;
    }


    /**
     *  A unique document ID.
     * <p>
     * This is a unique AvaTax identifier for this document.  If known, the
     * <b>CompanyCode</b>, <b>DocCode</b>, and <b>DocType</b> are not needed.
     *
     * @return docId
     * @see GetTaxResult#getDocId
     */
    public java.lang.String getDocId() {
        return docId;
    }


    /**
     *  A unique document ID.
     * <p>
     * This is a unique AvaTax identifier for this document.  If known, the
     * <b>CompanyCode</b>, <b>DocCode</b>, and <b>DocType</b> are not needed.
     *
     * @param docId
     * @see GetTaxResult#getDocId
     */
    public void setDocId(java.lang.String docId) {
        this.docId = docId;
    }


    /**
     * Gets the client application company reference code.
     * <br>If docId is specified, this is not needed.
     *
     * @return companyCode
     */
    public java.lang.String getCompanyCode() {
        return companyCode;
    }


    /**
     * Sets the client application company reference code.
     * <br>If docId is specified, this is not needed.
     *
     * @param companyCode
     */
    public void setCompanyCode(java.lang.String companyCode) {
        this.companyCode = companyCode;
    }


    /**
     * The original document's type, such as Sales Invoice or Purchase Invoice.
     *
     * @return docType
     */
    public com.avalara.avatax.services.tax.DocumentType getDocType() {
        return docType;
    }


    /**
     * The original document's type, such as Sales Invoice or Purchase Invoice.
     *
     * @param docType
     */
    public void setDocType(com.avalara.avatax.services.tax.DocumentType docType) {
        this.docType = docType;
    }


    /**
     * Gets the Document Code, that is the internal reference code used by the client application.
     * <br>If docId is specified, this is not needed.
     *
     * @return docCode
     */
    public java.lang.String getDocCode() {
        return docCode;
    }


    /**
     * Sets the Document Code, that is the internal reference code used by the client application.
     * <br>If docId is specified, this is not needed.
     *
     * @param docCode
     */
    public void setDocCode(java.lang.String docCode) {
        this.docCode = docCode;
    }


    /**
     * Gets the Document Date, i.e. the date on the invoice, purchase order, etc.
     *
     * @return docDate
     */
    public java.util.Date getDocDate() {
        return docDate;
    }


    /**
     * Sets the Document Date, i.e. the date on the invoice, purchase order, etc.
     *
     * @param docDate
     */
    public void setDocDate(java.util.Date docDate) {
        this.docDate = docDate;
    }


    /**
     * The total amount (not including tax) for the document.
     * <p>
     * This is used for verification and reconciliation. This should be the <b>TotalAmount</b>
     * returned by {@link GetTaxResult} when tax
     * was calculated for this document; otherwise the web service will return an error.
     * <p>
     * @see PostTaxRequest#getTotalTax
     *
     * @return totalAmount
     */
    public java.math.BigDecimal getTotalAmount() {
        return totalAmount;
    }


    /**
     *  The total amount (not including tax) for the document.
     * <p>
     * This is used for verification and reconciliation. This should be the <b>TotalAmount</b>
     * returned by {@link GetTaxResult} when tax
     * was calculated for this document; otherwise the web service will return an error.
     * <p>
     * @see PostTaxRequest#getTotalTax
     *
     * @param totalAmount
     */
    public void setTotalAmount(java.math.BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    /**
     * The total tax for the document.
     * <p>
     * This is used for verification and reconciliation. This should be the <b>TotalTax</b> returned by
     * {@link GetTaxResult} when tax
     * was calculated for this document; otherwise the web service will return an error.
     * </p>
     * @see PostTaxRequest#getTotalAmount
     *
     * @return totalTax
     */
    public java.math.BigDecimal getTotalTax() {
        return totalTax;
    }


    /**
     * The total tax for the document.
     * <p>
     * This is used for verification and reconciliation. This should be the <b>TotalTax</b> returned by
     * {@link GetTaxResult} when tax
     * was calculated for this document; otherwise the web service will return an error.
     * </p>
     * @see PostTaxRequest#getTotalAmount
     *
     * @param totalTax
     */
    public void setTotalTax(java.math.BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    /**
     * Sets the hashCode value for this PostTaxRequest.
     *  <p>
     * This should be computed by an SDK developer using some standard algorithm out of the content of the object. This value gets stored in the system and can be retrieved for the cross checking [Internal Reconciliation purpose].
     * </p>
     * See sample code for more details
     * @param hashCode
     */
    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    private java.lang.Object __equalsCalc = null;
    /**
     * Gets the commit value for this PostTaxRequest.
     *
     * @return commit
     */
    public boolean getCommit() {
        return commit;
    }
    /* Sets the commit value for this PostTaxRequest.
    * <p>
    * This has been defaulted to false.
    * If this has been set to true AvaTax will commit the document on this call.
    * Seller's system who wants to Post and Commit the document on one call should use this flag.
    * </p>
    * Also See {@link GetTaxRequest#setCommit}
    * @param commit
    */
    public void setCommit(boolean commit) {
        this.commit = commit;
    }
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PostTaxRequest)) return false;
        PostTaxRequest other = (PostTaxRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.docId==null && other.getDocId()==null) ||
                        (this.docId!=null &&
                                this.docId.equals(other.getDocId()))) &&
                ((this.companyCode==null && other.getCompanyCode()==null) ||
                        (this.companyCode!=null &&
                                this.companyCode.equals(other.getCompanyCode()))) &&
                ((this.docType==null && other.getDocType()==null) ||
                        (this.docType!=null &&
                                this.docType.equals(other.getDocType()))) &&
                ((this.docCode==null && other.getDocCode()==null) ||
                        (this.docCode!=null &&
                                this.docCode.equals(other.getDocCode()))) &&
                ((this.docDate==null && other.getDocDate()==null) ||
                        (this.docDate!=null &&
                                this.docDate.equals(other.getDocDate()))) &&
                ((this.totalAmount==null && other.getTotalAmount()==null) ||
                        (this.totalAmount!=null &&
                                this.totalAmount.equals(other.getTotalAmount()))) &&
                ((this.totalTax==null && other.getTotalTax()==null) ||
                        (this.totalTax!=null &&
                                this.totalTax.equals(other.getTotalTax()))) &&
                this.commit == other.getCommit();
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
        int _hashCode = 1;
        if (getDocId() != null) {
            _hashCode += getDocId().hashCode();
        }
        if (getCompanyCode() != null) {
            _hashCode += getCompanyCode().hashCode();
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
        if (getTotalAmount() != null) {
            _hashCode += getTotalAmount().hashCode();
        }
        if (getTotalTax() != null) {
            _hashCode += getTotalTax().hashCode();
        }
        _hashCode += (getCommit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(PostTaxRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CompanyCode"));
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
        elemField.setFieldName("totalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TotalAmount"));
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
        elemField.setFieldName("hashCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "HashCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Commit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }


    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return Type Description
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }


    /**
     * Get Axis Custom Serializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @param mechType
     * @param _javaType
     * @param _xmlType
     * @return Serializer
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
     *
     * @param mechType
     * @param _javaType
     * @param _xmlType
     * @return Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
