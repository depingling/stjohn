/**
 * CancelCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * A cancel code is set on a {@link CancelTaxRequest} and specifies the reason the
 * tax calculation is being canceled (or in the case of posting, returned to its prior state).
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class CancelCode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CancelCode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    private static final java.lang.String _Unspecified = "Unspecified";
    private static final java.lang.String _PostFailed = "PostFailed";
    private static final java.lang.String _DocDeleted = "DocDeleted";
    private static final java.lang.String _DocVoided = "DocVoided";
    private static final java.lang.String _AdjustmentCancelled = "AdjustmentCancelled";
    /**
     *  Unspecified reason
     */
    public static final CancelCode Unspecified = new CancelCode(_Unspecified);

    /**
     * Specifies the post operation failed when attempting to post an invoice within
     * a client's application, for example, to the client's General Ledger; The
     * document's status will be changed to <b>Saved</b>.
     */
    public static final CancelCode PostFailed = new CancelCode(_PostFailed);

    /**
     * Specifies the document was deleted within the client's application and
     * should be removed from the AvaTax records; If the document within AvaTax
     * is already committed, the document status will be changed to <b>Cancelled</b>
     * and retained for historical records;  If the document was not committed,
     * (was <b>Saved</b> or <b>Posted</b>) the document will be deleted within AvaTax.
     */
    public static final CancelCode DocDeleted = new CancelCode(_DocDeleted);


    /**
     * Specifies the document was voided within the client's application and
     * should be removed from the AvaTax records; If the document within AvaTax
     * is already committed, the document status will be changed to <b>Cancelled</b>
     * and retained for historical records;  If the document was not committed,
     * (was <b>Saved</b> or <b>Posted</b>) the document will be deleted within AvaTax.
     */
    public static final CancelCode DocVoided = new CancelCode(_DocVoided);

    /**
     * Specifies the document was adjusted within the client's application and
     * should be removed from the AvaTax records; If the document within AvaTax
     * is already committed, the document status will be changed to <b>Cancelled</b>
     * and retained for historical records;  If the document was not committed,
     * (was <b>Saved</b> or <b>Posted</b>) the document will be deleted within AvaTax.
     */
    public static final CancelCode AdjustmentCancelled = new CancelCode(_AdjustmentCancelled);

    public java.lang.String getValue() { return _value_;}
    public static CancelCode fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        CancelCode enumeration = (CancelCode)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CancelCode fromString(java.lang.String value)
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
            new org.apache.axis.description.TypeDesc(CancelCode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelCode"));
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
