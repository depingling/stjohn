/**
 * ArrayOfGetTaxResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * A wrapper object used by Axis to encapsulate  an array of zero or more {@link GetTaxResult} objects
 * for SOAP transmission via the Web.
 * Note: The internally kept GetTaxResult array is not guaranteed to be properly initialized (this is
 * generally true for Axis ArrayOfX wrapper objects); one should always test that it is not null
 * before using.
 *
 * <pre>
 * <b>Example:</b>
 * [Java]
 * ReconcileTaxHistoryRequest request = new ReconcileTaxHistoryRequest();
 * request.setCompanyCode("DEFAULT");
 * request.setReconciled(false);
 * ReconcileTaxHistoryResult result = taxSvc.reconcileTaxHistory(request);
 * GetTaxResult taxResults[] = result.getGetTaxResults().getGetTaxResult();
 * Assert.assertEquals(SeverityLevel.Success, result.getResultCode());
 *
 * for (int ii = 0; taxResults != null && ii < taxResults.length; ii++)
 * {
 *     GetTaxResult taxResult = taxResults[ii];
 * }
 * </pre>
 *
 * @see ReconcileTaxHistoryRequest
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ArrayOfGetTaxResult  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.GetTaxResult[] getTaxResult;

    /**
     * Initializes a new instance of the class with an empty array of {@link GetTaxResult} objects.
     */
    public ArrayOfGetTaxResult() {
        getTaxResult = new GetTaxResult[0];
    }

    /**
     * Initializes a new instance of the class and and its internal array
     * of {@link GetTaxResult} objects.
     *
     * @param getTaxResult
     */
    public ArrayOfGetTaxResult(
            com.avalara.avatax.services.tax.GetTaxResult[] getTaxResult) {
        this.getTaxResult = getTaxResult;
    }


    /**
     * Retrieves the raw array of {@link GetTaxResult} objects encapsulated in
     * this object.
     *
     * @return GetTaxResult warning this may be null, depending on how the object
     * was initialized.
     */
    public com.avalara.avatax.services.tax.GetTaxResult[] getGetTaxResult() {
        return getTaxResult;
    }


    /**
     * Allows one to programatically set the raw array of {@link GetTaxResult} objects
     * encapsulated by this object.
     *
     * @param getTaxResult
     */
    public void setGetTaxResult(com.avalara.avatax.services.tax.GetTaxResult[] getTaxResult) {
        this.getTaxResult = getTaxResult;
    }

    /**
     * Retrieves the ith {@link GetTaxResult} object (counting from 0) from the array
     * of GetTaxResults encapsulated in this object. Should only be used if its known
     * that {@link #getGetTaxResult} returns a non-null value and that i < number of GetTaxResults
     * actually in that array.
     *
     * @param i integer from 0 to (number of GetTaxResults -1)
     *
     * @return GetTaxResult
     */
    public com.avalara.avatax.services.tax.GetTaxResult getGetTaxResult(int i) {
        return this.getTaxResult[i];
    }

    /**
     * Allows one to replace the ith {@link GetTaxResult} object (counting from 0) within the array
     * of GetTaxResults encapsulated in this object. Should only be used if its known
     * that {@link #getGetTaxResult} returns a non-null value and that i < number of GetTaxResults
     * actually in that array.
     *
     * @param i integer from 0 to (number of GetTaxResults -1)
     * @param _value GetTaxResult object to place in the indicated position of the
     * GetTaxResult array
     */
    public void setGetTaxResult(int i, com.avalara.avatax.services.tax.GetTaxResult _value) {
        this.getTaxResult[i] = _value;
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
        if (!(obj instanceof ArrayOfGetTaxResult)) return false;
        ArrayOfGetTaxResult other = (ArrayOfGetTaxResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.getTaxResult==null && other.getGetTaxResult()==null) ||
                        (this.getTaxResult!=null &&
                                java.util.Arrays.equals(this.getTaxResult, other.getGetTaxResult())));
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
        if (getGetTaxResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetTaxResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGetTaxResult(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ArrayOfGetTaxResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfGetTaxResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getTaxResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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

    // BEGIN Extensions

    /**
     * Gets the size of the array.
     * @return size
     */
    public int size()
    {
        if (getTaxResult == null)
        {
            return 0;
        }
        else
        {
            return getTaxResult.length;
        }
    }

    // END Extensions
}
