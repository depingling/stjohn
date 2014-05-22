/**
 * GetTaxHistoryResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Result data returned from {@link TaxSvcSoap#getTaxHistory} for a previously calculated tax document.
 * @see GetTaxHistoryRequest
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class GetTaxHistoryResult  extends com.avalara.avatax.services.tax.BaseResult  implements java.io.Serializable {

    /**
     *  The original {@link GetTaxRequest} for the document.
     */
    private com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest;

    /**
     * The original {@link GetTaxResult} for the document.
     */
    private com.avalara.avatax.services.tax.GetTaxResult getTaxResult;

    /**
     * Initializes a new instance of the class.
     */
    public GetTaxHistoryResult() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param getTaxRequest
     * @param getTaxResult
     */
    private GetTaxHistoryResult(
            com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest,
            com.avalara.avatax.services.tax.GetTaxResult getTaxResult) {
        this.getTaxRequest = getTaxRequest;
        this.getTaxResult = getTaxResult;
    }


    /**
     * Gets the original {@link GetTaxRequest} for the document.
     *
     * @return getTaxRequest
     */
    public com.avalara.avatax.services.tax.GetTaxRequest getGetTaxRequest() {
        return getTaxRequest;
    }


    /**
     * Sets the original {@link GetTaxRequest} for the document.
     *
     * @param getTaxRequest
     */
    public void setGetTaxRequest(com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest) {
        this.getTaxRequest = getTaxRequest;
    }


    /**
     * Gets the original {@link GetTaxResult} for the document.
     *
     * @return getTaxResult
     */
    public com.avalara.avatax.services.tax.GetTaxResult getGetTaxResult() {
        return getTaxResult;
    }


    /**
     * Sets the original {@link GetTaxResult} for the document.
     *
     * @param getTaxResult
     */
    public void setGetTaxResult(com.avalara.avatax.services.tax.GetTaxResult getTaxResult) {
        this.getTaxResult = getTaxResult;
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
        if (!(obj instanceof GetTaxHistoryResult)) return false;
        GetTaxHistoryResult other = (GetTaxHistoryResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
                ((this.getTaxRequest==null && other.getGetTaxRequest()==null) ||
                        (this.getTaxRequest!=null &&
                                this.getTaxRequest.equals(other.getGetTaxRequest()))) &&
                ((this.getTaxResult==null && other.getGetTaxResult()==null) ||
                        (this.getTaxResult!=null &&
                                this.getTaxResult.equals(other.getGetTaxResult())));
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
        if (getGetTaxRequest() != null) {
            _hashCode += getGetTaxRequest().hashCode();
        }
        if (getGetTaxResult() != null) {
            _hashCode += getGetTaxResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(GetTaxHistoryResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getTaxRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getTaxResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
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
