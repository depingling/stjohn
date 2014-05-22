/**
 * JurisdictionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * A Jurisdiction Type describes the jurisdiction for which a particular tax is applied to a document.
 * <p>
 * Jurisdiction is determined by the {@link GetTaxRequest#getDestinationAddress} of a <b>GetTaxRequest</b>.
 * Multiple jurisdictions might be applied to a single {@link Line} during a tax calcuation.
 * Details are available in the {@link TaxDetail} of the {@link GetTaxResult}.
 * </p>
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class JurisdictionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected JurisdictionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    private static final java.lang.String _Composite = "Composite";
    private static final java.lang.String _Country = "Country";
    private static final java.lang.String _State = "State";
    private static final java.lang.String _County = "County";
    private static final java.lang.String _City = "City";
    private static final java.lang.String _Special = "Special";

    /**
     *  Unspecified Jurisdiction
     */
    public static final JurisdictionType Composite = new JurisdictionType(_Composite);

    /**
     *  Country
     */
    public static final JurisdictionType Country = new JurisdictionType(_Country);

    /**
     *  State
     */
    public static final JurisdictionType State = new JurisdictionType(_State);

    /**
     * County
     */
    public static final JurisdictionType County = new JurisdictionType(_County);

    /**
     *  City
     */
    public static final JurisdictionType City = new JurisdictionType(_City);

    /**
     *  Special Tax Jurisdiction
     */
    public static final JurisdictionType Special = new JurisdictionType(_Special);

    public java.lang.String getValue() { return _value_;}
    public static JurisdictionType fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        JurisdictionType enumeration = (JurisdictionType)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static JurisdictionType fromString(java.lang.String value)
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
            new org.apache.axis.description.TypeDesc(JurisdictionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "JurisdictionType"));
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
