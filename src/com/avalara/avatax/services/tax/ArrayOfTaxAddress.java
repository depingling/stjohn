/**
 * ArrayOfTaxAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

public class ArrayOfTaxAddress  implements java.io.Serializable {
    private com.avalara.avatax.services.tax.TaxAddress[] taxAddress;

    public ArrayOfTaxAddress() {
        this.taxAddress=new TaxAddress[0];
    }

    public ArrayOfTaxAddress(
           com.avalara.avatax.services.tax.TaxAddress[] taxAddress) {
           this.taxAddress = taxAddress;
    }


    /**
     * Gets the taxAddress value for this ArrayOfTaxAddress.
     * 
     * @return taxAddress
     */
    public com.avalara.avatax.services.tax.TaxAddress[] getTaxAddress() {
        return taxAddress;
    }


    /**
     * Sets the taxAddress value for this ArrayOfTaxAddress.
     * 
     * @param taxAddress
     */
    public void setTaxAddress(com.avalara.avatax.services.tax.TaxAddress[] taxAddress) {
        if (taxAddress != null)
        {
            this.taxAddress = taxAddress;
        }
        else
        {
            this.taxAddress = new TaxAddress[0];
        }
    }

    public com.avalara.avatax.services.tax.TaxAddress getTaxAddress(int i) {
        return this.taxAddress[i];
    }

    public void setTaxAddress(int i, com.avalara.avatax.services.tax.TaxAddress _value) {
        this.taxAddress[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfTaxAddress)) return false;
        ArrayOfTaxAddress other = (ArrayOfTaxAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.taxAddress==null && other.getTaxAddress()==null) || 
             (this.taxAddress!=null &&
              java.util.Arrays.equals(this.taxAddress, other.getTaxAddress())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTaxAddress() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTaxAddress());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTaxAddress(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfTaxAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  com.avalara.avatax.services.base.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
