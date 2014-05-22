/**
 * SeverityLevel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Severity of the result {@link Message}.
 * <pre>
 * <b>Example:</b>
 * [Java]
 * if (getTaxResult.getResultCode().equals(SeverityLevel.Success))
 * {
 *     Message[] messages = getTaxResult.getMessages().getMessage();
 *     int numMessages = (messages == null ? 0 : messages.length);
 *     for (int i = 0; i < numMessages; i++)
 *     {
 *         Message message = messages[i];
 *     }
 * }
 *
 *</pre>
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class SeverityLevel implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SeverityLevel(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    /**
     * Operation succeeded.
     */
    private static final java.lang.String _Success = "Success";

    /**
     * Warnings occured, operation succeeded.
     */
    private static final java.lang.String _Warning = "Warning";

    /**
     * Errors occured, operation failed.
     */
    private static final java.lang.String _Error = "Error";

    /**
     * Unexpected exceptions occurred, operation failed.
     */
    private static final java.lang.String _Exception = "Exception";

    /**
     * Operation succeeded.
     */
    public static final SeverityLevel Success = new SeverityLevel(_Success);

    /**
     * Warnings occured, operation succeeded.
     */
    public static final SeverityLevel Warning = new SeverityLevel(_Warning);

    /**
     * Errors occured, operation failed.
     */
    public static final SeverityLevel Error = new SeverityLevel(_Error);

    /**
     * Unexpected exceptions occurred, operation failed.
     */
    public static final SeverityLevel Exception = new SeverityLevel(_Exception);

    public java.lang.String getValue() { return _value_;}
    public static SeverityLevel fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        SeverityLevel enumeration = (SeverityLevel)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SeverityLevel fromString(java.lang.String value)
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
            new org.apache.axis.description.TypeDesc(SeverityLevel.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "SeverityLevel"));
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
