/**
 * GetTaxHistoryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Data to pass to {@link TaxSvcSoap#getTaxHistory}.
 * <p>
 * A document can be indicated solely by the {@link GetTaxHistoryRequest#getDocId} if it is known.
 * Otherwise the request must specify all of {@link GetTaxHistoryRequest#getCompanyCode},
 * {@link GetTaxHistoryRequest#getDocCode}, and
 * {@link GetTaxHistoryRequest#getDocType} in order to uniquely identify the document.
 * </p>
 * @see GetTaxHistoryResult
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class GetTaxHistoryRequest  implements java.io.Serializable {

    private java.lang.String docId;

    private java.lang.String companyCode;

    private com.avalara.avatax.services.tax.DocumentType docType;

    private java.lang.String docCode;

    private com.avalara.avatax.services.tax.DetailLevel detailLevel;

    /**
     * Initializes a new instance of the class.
     */
    public GetTaxHistoryRequest() {
        docType = DocumentType.SalesOrder;
        detailLevel = DetailLevel.Document;
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param docId
     * @param companyCode
     * @param docType
     * @param docCode
     * @param detailLevel
     */
    private GetTaxHistoryRequest(
            java.lang.String docId,
            java.lang.String companyCode,
            com.avalara.avatax.services.tax.DocumentType docType,
            java.lang.String docCode,
            com.avalara.avatax.services.tax.DetailLevel detailLevel) {
        this.docId = docId;
        this.companyCode = companyCode;
        this.docType = docType;
        this.docCode = docCode;
        this.detailLevel = detailLevel;
    }


    /**
     *  A unique Document ID.
     * <p>
     * This is a unique AvaTax identifier for this document.  If known, the
     * <b>CompanyCode</b>, <b>DocCode</b>, and <b>DocType</b> are not needed.
     *
     * @see GetTaxResult#getDocId
     * @return docId
     */
    public java.lang.String getDocId() {
        return docId;
    }


    /**
     *  A unique Document ID.
     * <p>
     * This is a unique AvaTax identifier for this document.  If known, the
     * <b>CompanyCode</b>, <b>DocCode</b>, and <b>DocType</b> are not needed.
     *
     * @see GetTaxResult#getDocId
     *
     * @param docId
     */
    public void setDocId(java.lang.String docId) {
        this.docId = docId;
    }


    /**
     * Gets the client application company reference code.
     * <br>If docId is specified, this is not needed.
     * @return companyCode
     */
    public java.lang.String getCompanyCode() {
        return companyCode;
    }


    /**
     * Sets the client application company reference code.
     * <br>If docId is specified, this is not needed.
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
     * Specifies the level of detail to return.
     *
     * @return detailLevel
     */
    public com.avalara.avatax.services.tax.DetailLevel getDetailLevel() {
        return detailLevel;
    }


    /**
     * Specifies the level of detail to return.
     *
     * @param detailLevel
     */
    public void setDetailLevel(com.avalara.avatax.services.tax.DetailLevel detailLevel) {
        this.detailLevel = detailLevel;
    }

    private java.lang.Object __equalsCalc = null;
    /**
     * Determines whether the specified Object is equal to the current Object.
     * Note: In current implementation all Java Strings members of the two
     * objects must be exactly alike, including in case, for equal to return true.
     * @param obj
     * @return true or false, indicating if the two objects are equal.
     */
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetTaxHistoryRequest)) return false;
        GetTaxHistoryRequest other = (GetTaxHistoryRequest) obj;
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
                ((this.detailLevel==null && other.getDetailLevel()==null) ||
                        (this.detailLevel!=null &&
                                this.detailLevel.equals(other.getDetailLevel())));
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
        if (getDetailLevel() != null) {
            _hashCode += getDetailLevel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(GetTaxHistoryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryRequest"));
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
        elemField.setFieldName("detailLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DetailLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DetailLevel"));
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
                new  com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
