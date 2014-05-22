/**
 * ServiceMode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

/**
 * Specifies the type of tax override.
 * This is only supported by AvaLocal servers. It provides the ability to controls whether tax is calculated locally or remotely when using an AvaLocal server.
 * The default is Automatic which calculates locally unless remote is necessary for non-local addresses
 * See {@link GetTaxRequest}.
 * @author amitr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class ServiceMode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ServiceMode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Automatic = "Automatic";
    public static final java.lang.String _Local = "Local";
    public static final java.lang.String _Remote = "Remote";

    /**
     * Automated handling by local and/or remote server.
     */
    public static final ServiceMode Automatic = new ServiceMode(_Automatic);


    /**
     * AvaLocal server only. Lines requiring remote will not be calculated.
     */
    public static final ServiceMode Local = new ServiceMode(_Local);

    /**
     * All lines are calculated by AvaTax remote server.
     */
    public static final ServiceMode Remote = new ServiceMode(_Remote);


    public java.lang.String getValue() { return _value_;}
    public static ServiceMode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ServiceMode enumeration = (ServiceMode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ServiceMode fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
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
        new org.apache.axis.description.TypeDesc(ServiceMode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ServiceMode"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
