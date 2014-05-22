/**
 * DocStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;


/**
 * The document's status is returned in the {@link GetTaxResult} (except for <b>DocStatus.Any</b>)
 * and indicates the state of the document in tax history.
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class DocStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DocStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    private static final java.lang.String _Temporary = "Temporary";
    private static final java.lang.String _Saved = "Saved";
    private static final java.lang.String _Posted = "Posted";
    private static final java.lang.String _Committed = "Committed";
    private static final java.lang.String _Cancelled = "Cancelled";
    private static final java.lang.String _Adjusted = "Adjusted";
    private static final java.lang.String _Any = "Any";

    /**
     * A temporary document not saved ({@link DocumentType} was <b>SalesOrder</b>, <b>PurchaseOrder</b>, <b>ReturnOrder</b>)
     */
    public static final DocStatus Temporary = new DocStatus(_Temporary);

    /**
     * A saved document ({@link DocumentType} was <b>SalesInvoice</b>, <b>PurchaseInvoice</b>, <b>ReturnInvoice</b>)
     * ready to be posted.
     */
    public static final DocStatus Saved = new DocStatus(_Saved);

    /**
     * A posted document (not committed). See {@link TaxSvcSoap#postTax} for information on posting a document.
     */
    public static final DocStatus Posted = new DocStatus(_Posted);

    /**
     * A posted document that has been committed.  See {@link TaxSvcSoap#commitTax} for information on committing a document.
     */
    public static final DocStatus Committed = new DocStatus(_Committed);

    /**
     * A committed document that has been cancelled.  See {@link TaxSvcSoap#cancelTax} for information on cancelling a document.
     */
    public static final DocStatus Cancelled = new DocStatus(_Cancelled);
    public static final DocStatus Adjusted = new DocStatus(_Adjusted);
    /**
     * Any status (used for searching)
     */
    public static final DocStatus Any = new DocStatus(_Any);

    public java.lang.String getValue() { return _value_;}
    public static DocStatus fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        DocStatus enumeration = (DocStatus)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DocStatus fromString(java.lang.String value)
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
            new org.apache.axis.description.TypeDesc(DocStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocStatus"));
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
