/**
 * BaseAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

public class BaseAddress  implements java.io.Serializable {
    private java.lang.String addressCode;
    private java.lang.String line1;
    private java.lang.String line2;
    private java.lang.String line3;
    private java.lang.String city;
    private java.lang.String region;
    private java.lang.String postalCode;
    private java.lang.String country;
    private int taxRegionId;

    public BaseAddress() {
    }

    private BaseAddress(
            java.lang.String addressCode,
            java.lang.String line1,
            java.lang.String line2,
            java.lang.String line3,
            java.lang.String city,
            java.lang.String region,
            java.lang.String postalCode,
            java.lang.String country,
            int taxRegionId) {
        this.addressCode = addressCode;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.taxRegionId = taxRegionId;
    }


    /**
     * Gets the addressCode value for this BaseAddress.
     *
     * @return addressCode
     */
    public java.lang.String getAddressCode() {
        return addressCode;
    }


    /**
     * Sets the addressCode value for this BaseAddress.
     *
     * @param addressCode
     */
    public void setAddressCode(java.lang.String addressCode) {
        this.addressCode = addressCode;
    }


    /**
     * Gets the line1 value for this BaseAddress.
     *
     * @return line1
     */
    public java.lang.String getLine1() {
        return line1;
    }


    /**
     * Sets the line1 value for this BaseAddress.
     *
     * @param line1
     */
    public void setLine1(java.lang.String line1) {
        this.line1 = line1;
    }


    /**
     * Gets the line2 value for this BaseAddress.
     *
     * @return line2
     */
    public java.lang.String getLine2() {
        return line2;
    }


    /**
     * Sets the line2 value for this BaseAddress.
     *
     * @param line2
     */
    public void setLine2(java.lang.String line2) {
        this.line2 = line2;
    }


    /**
     * Gets the line3 value for this BaseAddress.
     *
     * @return line3
     */
    public java.lang.String getLine3() {
        return line3;
    }


    /**
     * Sets the line3 value for this BaseAddress.
     *
     * @param line3
     */
    public void setLine3(java.lang.String line3) {
        this.line3 = line3;
    }


    /**
     * Gets the city value for this BaseAddress.
     *
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this BaseAddress.
     *
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the region value for this BaseAddress.
     *
     * @return region
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * Sets the region value for this BaseAddress.
     *
     * @param region
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }


    /**
     * Gets the postalCode value for this BaseAddress.
     *
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this BaseAddress.
     *
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the country value for this BaseAddress.
     *
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this BaseAddress.
     *
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }

    /**
     * Gets the taxRegionId value for this BaseAddress.
     *
     * @return taxRegionId
     */
    public int getTaxRegionId() {
        return taxRegionId;
    }


    /**
     * Sets the taxRegionId value for this BaseAddress.
     *
     * @param taxRegionId
     */
    public void setTaxRegionId(int taxRegionId) {
        this.taxRegionId = taxRegionId;
    }
    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseAddress)) return false;
        BaseAddress other = (BaseAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.addressCode==null && other.getAddressCode()==null) ||
                        (this.addressCode!=null &&
                                this.addressCode.equals(other.getAddressCode()))) &&
                ((this.line1==null && other.getLine1()==null) ||
                        (this.line1!=null &&
                                this.line1.equals(other.getLine1()))) &&
                ((this.line2==null && other.getLine2()==null) ||
                        (this.line2!=null &&
                                this.line2.equals(other.getLine2()))) &&
                ((this.line3==null && other.getLine3()==null) ||
                        (this.line3!=null &&
                                this.line3.equals(other.getLine3()))) &&
                ((this.city==null && other.getCity()==null) ||
                        (this.city!=null &&
                                this.city.equals(other.getCity()))) &&
                ((this.region==null && other.getRegion()==null) ||
                        (this.region!=null &&
                                this.region.equals(other.getRegion()))) &&
                ((this.postalCode==null && other.getPostalCode()==null) ||
                        (this.postalCode!=null &&
                                this.postalCode.equals(other.getPostalCode()))) &&
                ((this.country==null && other.getCountry()==null) ||
                        (this.country!=null &&
                                this.country.equals(other.getCountry()))) &&
                this.taxRegionId == other.getTaxRegionId();
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
        if (getAddressCode() != null) {
            _hashCode += getAddressCode().hashCode();
        }
        if (getLine1() != null) {
            _hashCode += getLine1().hashCode();
        }
        if (getLine2() != null) {
            _hashCode += getLine2().hashCode();
        }
        if (getLine3() != null) {
            _hashCode += getLine3().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(BaseAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AddressCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Region"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxRegionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxRegionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
