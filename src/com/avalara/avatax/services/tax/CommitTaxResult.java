/**
 * CommitTaxResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Result data returned from {@link TaxSvcSoap#commitTax}.
 * @see CommitTaxRequest
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class CommitTaxResult  extends com.avalara.avatax.services.tax.BaseResult  implements java.io.Serializable {

    private java.lang.String docId;

    /**
     * Initializes a new instance of the class.
     */
    public CommitTaxResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param docId
     */
    private CommitTaxResult(
            java.lang.String docId) {
        this.docId = docId;
    }


    /**
     *  A unique document ID.
     * <p>
     * This is a unique AvaTax identifier for this document.  If known, the
     * <b>CompanyCode</b>, <b>DocCode</b>, and <b>DocType</b> are not needed.
     * @see GetTaxResult#getDocId
     *
     * @return docId
     */
    public java.lang.String getDocId() {
        return docId;
    }


    /**
     *  A unique document ID.
     * <p>
     * This is a unique AvaTax identifier for this document.  If known, the
     * <b>CompanyCode</b>, <b>DocCode</b>, and <b>DocType</b> are not needed.
     * @see GetTaxResult#getDocId
     *
     * @param docId
     */
    public void setDocId(java.lang.String docId) {
        this.docId = docId;
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
        if (!(obj instanceof CommitTaxResult)) return false;
        CommitTaxResult other = (CommitTaxResult) obj;
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
                                this.docId.equals(other.getDocId())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(CommitTaxResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
                new  com.avalara.avatax.services.base.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
