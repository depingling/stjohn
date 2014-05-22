/**
 * ArrayOfValidAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * A Axis Wrapper for an array containing zero or more {@link ValidAddress} objects.
 * <pre>
 * <b>Example:</b>
 * [Java]
 *  ValidateRequest request = new ValidateRequest();
 *  Address address = new Address();
 *  address.setLine1("900 Winslow Way");
 *  address.setLine2("Suite 130");
 *  address.setCity("Bainbridge Is");
 *  address.setRegion("WA");
 *  address.setPostalCode("98110-2450");
 *  request.setAddress(address);
 *  request.setTextCase(TextCase.Upper);
 *
 *  ValidateResult result;
 *  result = svc.validate(request);
 *
 *  if (SeverityLevel.Success.equals(result.getResultCode()))
 *  {
 *      ArrayOfValidAddress arrValids = result.getValidAddresses();
 *      if (arrValids != null && arrValids.getValidAddress() != null &&
 *              arrValids.getValidAddress().length > 0)
 *      {
 *          ValidAddress validAddress = result.getValidAddresses().getValidAddress(0);
 *      }
 *  }
 * </pre>
 *
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ArrayOfValidAddress  implements java.io.Serializable {
    private com.avalara.avatax.services.address.ValidAddress[] validAddress;

    /**
     * Initializes a new instance of the class an empty array
     * of {@link ValidAddress} objects.
     */
    public ArrayOfValidAddress() {
        validAddress = new ValidAddress[0];
    }

    /**
     * Initializes a new instance of the class and initializes the internal array
     * of {@link ValidAddress} objects.
     */
    public ArrayOfValidAddress(
            com.avalara.avatax.services.address.ValidAddress[] validAddress) {
        this.validAddress = validAddress;
    }


    /**
     * Retrieves the raw array of {@link ValidAddress} objects encapsulated in
     * this object.
     *
     * @return validAddress warning this may be null, depending on how the object
     * was initialized.
     */
    public com.avalara.avatax.services.address.ValidAddress[] getValidAddress() {
        return validAddress;
    }


    /**
     * Allows one to programatically set the raw array of {@link ValidAddress} objects
     * encapsulated by this object.
     *
     * @param validAddress
     */
    public void setValidAddress(com.avalara.avatax.services.address.ValidAddress[] validAddress) {
        if (validAddress != null)
        {
            this.validAddress = validAddress;
        }
        else
        {
            this.validAddress = new ValidAddress[0];
        }
    }

    /**
     * Retrieves the ith {@link ValidAddress} object (counting from 0) from the array
     * of ValidAddresses encapsulated in this object. Should only be used if its known
     * that {@link #getValidAddress} returns a non-null value and that i < number of
     * Valid Addresses actually in that array.
     *
     * @param i integer from 0 to (number of messages -1)
     * @return
     */
    public com.avalara.avatax.services.address.ValidAddress getValidAddress(int i) {
        return this.validAddress[i];
    }

    /**
     * Allows one to replace the ith {@link ValidAddress} object (counting from 0) within the array
     * of ValidAddresses encapsulated in this object. Should only be used if its known
     * that {@link #getValidAddress} returns a non-null value and that i < number of ValidAddresses
     * actually in that array.
     *
     * @param i integer from 0 to (number of ValidAddresses -1)
     * @param _value ValidAddress object to place in the indicated position of the
     * ValidAddress array
     */
    public void setValidAddress(int i, com.avalara.avatax.services.address.ValidAddress _value) {
        this.validAddress[i] = _value;
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
        if (!(obj instanceof ArrayOfValidAddress)) return false;
        ArrayOfValidAddress other = (ArrayOfValidAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.validAddress==null && other.getValidAddress()==null) ||
                        (this.validAddress!=null &&
                                java.util.Arrays.equals(this.validAddress, other.getValidAddress())));
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
        if (getValidAddress() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValidAddress());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValidAddress(), i);
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
            new org.apache.axis.description.TypeDesc(ArrayOfValidAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfValidAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidAddress"));
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
        if (validAddress == null)
        {
            return 0;
        }
        else
        {
            return validAddress.length;
        }
    }

    // END Extensions
}
