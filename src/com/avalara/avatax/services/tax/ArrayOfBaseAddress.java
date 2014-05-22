/**
 * ArrayOfBaseAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

import com.avalara.avatax.services.base.Utility;

import java.util.ArrayList;


/**
 * A wrapper object used by Axis to encapsulate  an array of zero or more {@link BaseAddress} objects
 * for SOAP transmission via the Web as part of a {@link GetTaxRequest} object.
 * <pre>
 * <b>Example:</b>
 * [Java]
 * ArrayOfBaseAddress addrArray = getTaxRequest.getAddresses();
 * if (addrArray != null)
 * {
 *     BaseAddress[] addresses = addrArray.getBaseAddress();
 *     if (addresses != null)
 *     {
 *         for (int i = 0, n= addresses.length; i < n; i++)
 *         {
 *         }
 *     }
 * }
 *
 * </pre>
 *
 * @see GetTaxRequest#getAddresses()
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ArrayOfBaseAddress  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.BaseAddress[] baseAddress;

    /**
     * Initializes a new instance of the class with an empty array of {@link BaseAddress} objects.
     */
    public ArrayOfBaseAddress() {
        baseAddress = new BaseAddress[0];
    }

    /**
     * Initializes a new instance of the classand and its internal array
     * of {@link BaseAddress} objects.
     * @param baseAddress
     */
    public ArrayOfBaseAddress(
            com.avalara.avatax.services.tax.BaseAddress[] baseAddress) {
        this.baseAddress = baseAddress;
    }


    /**
     * Retrieves the raw array of {@link BaseAddress} objects encapsulated in
     * this object.
     *
     * @return baseAddress warning this may be null, depending on how the object
     * was initialized.
     */
    public com.avalara.avatax.services.tax.BaseAddress[] getBaseAddress() {
        return baseAddress;
    }


    /**
     * Allows one to programatically set the raw array of {@link BaseAddress} objects
     * encapsulated by this object.
     *
     * @param baseAddress
     */
    public void setBaseAddress(com.avalara.avatax.services.tax.BaseAddress[] baseAddress) {
        if (baseAddress != null)
        {
            this.baseAddress = baseAddress;
        }
        else
        {
            this.baseAddress = new BaseAddress[0];
        }
    }

    /**
     * Retrieves the ith {@link BaseAddress} object (counting from 0) from the array
     * of BaseAddresses encapsulated in this object. Should only be used if its known
     * that {@link #getBaseAddress} returns a non-null value and that i < number of BaseAddresses
     * actually in that array.
     *
     * @param i integer from 0 to (number of BaseAddresses -1)
     *
     * @return BaseAddress
     */
    public com.avalara.avatax.services.tax.BaseAddress getBaseAddress(int i) {
        return this.baseAddress[i];
    }

    /**
     * Allows one to replace the ith {@link BaseAddress} object (counting from 0) within the array
     * of BaseAddresses encapsulated in this object. Should only be used if its known
     * that {@link #getBaseAddress} returns a non-null value and that i < number of BaseAddresses
     * actually in that array.
     *
     * @param i integer from 0 to (number of BaseAddresses -1)
     * @param _value BaseAddress object to place in the indicated position of the
     * BaseAddress array
     */
    public void setBaseAddress(int i, com.avalara.avatax.services.tax.BaseAddress _value) {
        this.baseAddress[i] = _value;
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
        if (!(obj instanceof ArrayOfBaseAddress)) return false;
        ArrayOfBaseAddress other = (ArrayOfBaseAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.baseAddress==null && other.getBaseAddress()==null) ||
                        (this.baseAddress!=null &&
                                java.util.Arrays.equals(this.baseAddress, other.getBaseAddress())));
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
        if (getBaseAddress() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBaseAddress());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBaseAddress(), i);
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
            new org.apache.axis.description.TypeDesc(ArrayOfBaseAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfBaseAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseAddress"));
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
     * Initializes a new instance of the class and and its internal array size.
     * of {@link BaseAddress} objects.
     * @param size
     */
    public ArrayOfBaseAddress(int size)
    {
        this.baseAddress = new BaseAddress[size];
    }

    /**
     * Gets the size of the array.
     * @return size
     */
    public int size()
    {
        if (baseAddress == null)
        {
            return 0;
        }
        else
        {
            return baseAddress.length;
        }
    }

    /**
     * Adds an item to the array, resizing it as needed.
     * @param address
     * @return Array position of the added item.
     */
    public int add(BaseAddress address)
    {
        int pos = 0;
        if (baseAddress == null)
        {
            baseAddress = new BaseAddress[1];
        }
        else
        {
            while (pos < baseAddress.length && baseAddress[pos] != null)
            {
                ++pos;
            }
            if (pos == baseAddress.length)
            {
                baseAddress = (BaseAddress[])Utility.resizeArray(baseAddress, pos+1);
            }
        }
        baseAddress[pos] = address;

        return pos;
    }

    /**
     * Retrieves the {@link BaseAddress} object identified by addressCode from the array
     * of BaseAddresses encapsulated in this object.
     *
     * @param addressCode
     *
     * @return BaseAddress if found, else null
     */
    public com.avalara.avatax.services.tax.BaseAddress getBaseAddress(String addressCode)
    {
        BaseAddress result = null;
        if (baseAddress != null)
        {
            for (int ii = 0; ii < baseAddress.length && result == null; ii++)
            {
                if (addressCode.equals(baseAddress[ii].getAddressCode()))
                {
                    result = baseAddress[ii];
                }
            }
        }

        return result;
    }
    // END Extensions
}
