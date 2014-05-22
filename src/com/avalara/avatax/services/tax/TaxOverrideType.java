
/**
 * Created by IntelliJ IDEA.
 * User: amit.raval
 * Date: Feb 13, 2008
 * Time: 2:39:35 PM
 * To change this template use File | Settings | File Templates.
 */

package com.avalara.avatax.services.tax;

/**
 * Specifies the type of tax override.
 * See {@link TaxOverride}.
 * @author amitr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class TaxOverrideType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TaxOverrideType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _None = "None";
    public static final java.lang.String _TaxAmount = "TaxAmount";
    public static final java.lang.String _Exemption = "Exemption";
    public static final java.lang.String _TaxDate = "TaxDate";

    /**
     * Default value.
     */
    public static final TaxOverrideType None = new TaxOverrideType(_None);

    /**
     * The TaxAmount overrides the total tax for the document.
     */
    public static final TaxOverrideType TaxAmount = new TaxOverrideType(_TaxAmount);

    /**
     * Exemption certificates are overridden making the document taxable.
     */
    public static final TaxOverrideType Exemption = new TaxOverrideType(_Exemption);

    /**
     * The TaxDate overrides the DocDate as the effective date used for tax calculation.
     */
    public static final TaxOverrideType TaxDate = new TaxOverrideType(_TaxDate);

    
    public java.lang.String getValue() { return _value_;}


    public static TaxOverrideType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TaxOverrideType enumeration = (TaxOverrideType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TaxOverrideType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TaxOverrideType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxOverrideType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
