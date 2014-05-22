/**
 * ArrayOfTaxLine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * A wrapper object used by Axis to encapsulate  an array of zero or more {@link TaxLine} objects
 * for SOAP transmission via the Web.
 * Note: The internally kept TaxLine array is not guaranteed to be properly initialized (this is
 * generally true for Axis ArrayOfX wrapper objects); one should always test that it is not null
 * before using.
 *
 * <pre>
 * <b>Example:</b>
 * [Java]
 * if (taxResult.getTaxLines() !=null &&
 *         taxResult.getTaxLines().getTaxLine() != null &&
 *         taxResult.getTaxLines().getTaxLine().length > 0)
 * {
 *
 *     TaxLine taxLine = taxResult.getTaxLines().getTaxLine(0);
 * }
 *
 * </pre>
 *
 * @see GetTaxResult
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ArrayOfTaxLine  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.TaxLine[] taxLine;

    /**
     * Initializes a new instance of the class with an empty array
     * of {@link TaxLine} objects.
     */
    public ArrayOfTaxLine() {
        taxLine = new TaxLine[0];
    }

    /**
     * Initializes a new instance of the classand and its internal array
     * of {@link TaxLine} objects.
     *
     * @param taxLine
     */
    public ArrayOfTaxLine(
            com.avalara.avatax.services.tax.TaxLine[] taxLine) {
        this.taxLine = taxLine;
    }


    /**
     * Retrieves the raw array of {@link TaxLine} objects encapsulated in
     * this object.
     *
     * @return TaxLine warning this may be null, depending on how the object
     * was initialized.
     */
    public com.avalara.avatax.services.tax.TaxLine[] getTaxLine() {
        return taxLine;
    }


    /**
     * Allows one to programatically set the raw array of {@link TaxLine} objects
     * encapsulated by this object.
     *
     * @param taxLine
     */
    public void setTaxLine(com.avalara.avatax.services.tax.TaxLine[] taxLine) {
        if (taxLine != null)
        {
            this.taxLine = taxLine;
        }
        else
        {
            this.taxLine = new TaxLine[0];
        }
    }

    /**
     * Retrieves the ith {@link TaxLine} object (counting from 0) from the array
     * of TaxLines encapsulated in this object. Should only be used if its known
     * that {@link #getTaxLine} returns a non-null value and that i < number of TaxLines
     * actually in that array.
     *
     * @param i integer from 0 to (number of TaxLines -1)
     *
     * @return TaxLine
     */
    public com.avalara.avatax.services.tax.TaxLine getTaxLine(int i) {
        return this.taxLine[i];
    }

    /**
     * Allows one to replace the ith {@link TaxLine} object (counting from 0) within the array
     * of TaxLines encapsulated in this object. Should only be used if its known
     * that {@link #getTaxLine} returns a non-null value and that i < number of TaxLines
     * actually in that array.
     *
     * @param i integer from 0 to (number of TaxLines -1)
     * @param _value TaxLine object to place in the indicated position of the
     * BaseAddress array
     */
    public void setTaxLine(int i, com.avalara.avatax.services.tax.TaxLine _value) {
        this.taxLine[i] = _value;
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
        if (!(obj instanceof ArrayOfTaxLine)) return false;
        ArrayOfTaxLine other = (ArrayOfTaxLine) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.taxLine==null && other.getTaxLine()==null) ||
                        (this.taxLine!=null &&
                                java.util.Arrays.equals(this.taxLine, other.getTaxLine())));
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
        if (getTaxLine() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTaxLine());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTaxLine(), i);
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
            new org.apache.axis.description.TypeDesc(ArrayOfTaxLine.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxLine"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxLine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxLine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxLine"));
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
        if (taxLine == null)
        {
            return 0;
        }
        else
        {
            return taxLine.length;
        }
    }

    /**
     * Retrieves the {@link TaxLine} object identified by "no" from the array
     * of Lines encapsulated in this object.
     *
     * @param no
     *
     * @return TaxLine if found, else null
     */
    public com.avalara.avatax.services.tax.TaxLine getTaxLine(String no)
    {
        TaxLine result = null;
        if (taxLine != null)
        {
            for (int ii = 0; ii < taxLine.length && result == null; ii++)
            {
                if (no.equals(taxLine[ii].getNo()))
                {
                    result = taxLine[ii];
                }
            }
        }

        return result;
    }

    // END Extensions
}
