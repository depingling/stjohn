/**
 * DocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;


/**
 * The document type specifies the category of the document and affects how the document
 * is treated after a tax calculation. Specified when constructing a {@link GetTaxRequest}.
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class DocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    private static final java.lang.String _SalesOrder = "SalesOrder";
    private static final java.lang.String _SalesInvoice = "SalesInvoice";
    private static final java.lang.String _PurchaseOrder = "PurchaseOrder";
    private static final java.lang.String _PurchaseInvoice = "PurchaseInvoice";
    private static final java.lang.String _ReturnOrder = "ReturnOrder";
    private static final java.lang.String _ReturnInvoice = "ReturnInvoice";

    /**
     *  Sales Order, estimate or quote. This is a temporary document type and is not saved in tax history.
     * {@link GetTaxResult} will return with a {@link DocStatus} of <b>Temporary</b>.
     */
    public static final DocumentType SalesOrder = new DocumentType(_SalesOrder);

    /**
     *  The document is a permanent invoice; document and tax calculation results are saved in the tax history.
     * {@link GetTaxResult} will return with a {@link DocStatus} of <b>Saved</b>
     */
    public static final DocumentType SalesInvoice = new DocumentType(_SalesInvoice);

    /**
     * Purchase order, estimate, or quote. This is a temporary document type and is not saved in tax history.
     * {@link GetTaxResult} will return with a {@link DocStatus} of <b>Temporary</b>.
     */
    public static final DocumentType PurchaseOrder = new DocumentType(_PurchaseOrder);

    /**
     *  The document is a permanent invoice; document and tax calculation results are saved in the tax history.
     * {@link GetTaxResult} will return with a {@link DocStatus} of <b>Saved</b>.
     */
    public static final DocumentType PurchaseInvoice = new DocumentType(_PurchaseInvoice);

    /**
     * Sales Return Order. This is a temporary document type and is not saved in tax history.
     * {@link GetTaxResult} will return with a {@link DocStatus} of <b>Temporary</b>.
     */
    public static final DocumentType ReturnOrder = new DocumentType(_ReturnOrder);

    /**
     *  The document is a permanent sales return invoice; document and tax calculation results are
     * saved in the tax history {@link GetTaxResult} will return with a {@link DocStatus}
     * of <b>Saved</b>.
     */
    public static final DocumentType ReturnInvoice = new DocumentType(_ReturnInvoice);

    public java.lang.String getValue() { return _value_;}
    public static DocumentType fromValue(java.lang.String value)
            throws java.lang.IllegalArgumentException {
        DocumentType enumeration = (DocumentType)
                _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DocumentType fromString(java.lang.String value)
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
            new org.apache.axis.description.TypeDesc(DocumentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocumentType"));
    }

    /**
     * Return Axis type metadata object; this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
