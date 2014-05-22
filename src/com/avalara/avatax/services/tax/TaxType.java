/**
 * TaxType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 *  A Tax Type. See {@link TaxDetail}.
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class TaxType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TaxType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    /**
     *  Sales tax.
     */
    private static final java.lang.String _Sales = "Sales";

    /**
     *  Use tax.
     */
    private static final java.lang.String _Use = "Use";

    /**
     * Consumer Use Tax.
     */
    private static final java.lang.String _ConsumerUse = "ConsumerUse";

    /**
     * Output Tax.
     */
    public static final java.lang.String _Output = "Output";

    /**
     * Input Tax.
     */
    public static final java.lang.String _Input = "Input";

    /**
     * Nonrecoverable Tax.
     */
    public static final java.lang.String _Nonrecoverable = "Nonrecoverable";

    /**
     * Fee
     */
    public static final java.lang.String _Fee = "Fee";

    /**
     * Rental
     */
    public static final java.lang.String _Rental = "Rental";

    /**
     *  Sales tax.
     */
    public static final TaxType Sales = new TaxType(_Sales);

    /**
     *  Use tax.
     */
    public static final TaxType Use = new TaxType(_Use);

    /**
     * Consumer Use Tax.
     */
    public static final TaxType ConsumerUse = new TaxType(_ConsumerUse);

     /**
     * Output Tax.
     */
    public static final TaxType Output = new TaxType(_Output);

    /**
     * Input Tax.
     */
    public static final TaxType Input = new TaxType(_Input);

    /**
     * Nonrecoverable Tax.
     */
    public static final TaxType Nonrecoverable = new TaxType(_Nonrecoverable);

    /**
     * Fee
     */
    public static final TaxType Fee = new TaxType(_Fee);

    /**
     * Rental
     */
    public static final TaxType Rental = new TaxType(_Rental);


    public java.lang.String getValue() { return _value_;}
    public static TaxType fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        TaxType enumeration = (TaxType)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TaxType fromString(java.lang.String value)
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
                new org.apache.axis.encoding.ser.EnumSerializer(
                        _javaType, _xmlType);
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
                new org.apache.axis.encoding.ser.EnumDeserializer(
                        _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TaxType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxType"));
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

}
