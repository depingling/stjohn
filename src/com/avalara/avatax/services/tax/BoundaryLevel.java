/**
 * BoundaryLevel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Jurisdiction boundary precision level found for address;
 * This depends on the accuracy of the address as well as the
 * precision level of the state provided jurisdiction boundaries.
 * @see TaxLine
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class BoundaryLevel implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BoundaryLevel(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    private static final java.lang.String _Address = "Address";
    private static final java.lang.String _Zip9 = "Zip9";
    private static final java.lang.String _Zip5 = "Zip5";

    /**
     * Street address precision
     */
    public static final BoundaryLevel Address = new BoundaryLevel(_Address);

    /**
     * 9-digit zip precision
     */
    public static final BoundaryLevel Zip9 = new BoundaryLevel(_Zip9);

    /**
     * 5-digit zip precision
     */
    public static final BoundaryLevel Zip5 = new BoundaryLevel(_Zip5);

    public java.lang.String getValue() { return _value_;}
    public static BoundaryLevel fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        BoundaryLevel enumeration = (BoundaryLevel)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static BoundaryLevel fromString(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }


    /**
     * Determines whether the specified Object is equal to the current Object.
     * Note: In current implementation all Java Strings members of the two
     * objects must be exactly alike, including in case, for equal to return true.
     * @param obj
     * @return true or false, indicating if the two objects are equal.
     */
    public boolean equals(java.lang.Object obj) {return (obj == this);}


    /**
     *  Serves as a hash function for a particular type,
     * suitable for use in hashing algorithms and data
     * structures like a hash table.
     * @return  hash code for this GetTaxRequest object
     * @see java.lang.Object#hashCode
     */
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}

    /**
     * Get Axis Custom Serializer; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.EnumSerializer(
                        _javaType, _xmlType);
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
                new org.apache.axis.encoding.ser.EnumDeserializer(
                        _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(BoundaryLevel.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BoundaryLevel"));
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
