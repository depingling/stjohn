/**
 * DetailLevel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;


/**
 * Specifies the level of tax detail to return to the client application following a tax calculation.
 * See {@link GetTaxRequest} and {@link GetTaxHistoryRequest}.
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class DetailLevel implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DetailLevel(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    private static final java.lang.String _Summary = "Summary";
    private static final java.lang.String _Document = "Document";
    private static final java.lang.String _Line = "Line";
    private static final java.lang.String _Tax = "Tax";
    private static final java.lang.String _Diagnostic = "Diagnostic";

    /**
     * Reserved for future use.
     */
    public static final DetailLevel Summary = new DetailLevel(_Summary);

    /**
     *  Document ({@link GetTaxResult}) level details; {@link ArrayOfTaxLine} will not be returned.
     */
    public static final DetailLevel Document = new DetailLevel(_Document);

    /**
     *  Line level details (includes Document details). {@link ArrayOfTaxLine} will
     * be returned but {@link ArrayOfTaxDetail} will not be returned.
     */
    public static final DetailLevel Line = new DetailLevel(_Line);

    /**
     *  Tax jurisdiction level details (includes Document, {@link ArrayOfTaxLine},
     * and {@link ArrayOfTaxDetail})
     */
    public static final DetailLevel Diagnostic = new DetailLevel(_Diagnostic);

    public static final DetailLevel Tax = new DetailLevel(_Tax);

    public java.lang.String getValue() { return _value_;}
    public static DetailLevel fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        DetailLevel enumeration = (DetailLevel)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DetailLevel fromString(java.lang.String value)
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
            new org.apache.axis.description.TypeDesc(DetailLevel.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DetailLevel"));
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
