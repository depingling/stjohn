/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

/**
 * Contains address data; Can be passed to {@link AddressSvcSoap#validate}
 * using {@link ValidateRequest}; Also part of the {@link com.avalara.avatax.services.tax.GetTaxRequest}
 * result returned from the {@link com.avalara.avatax.services.tax.TaxSvcSoap#getTax} tax calculation service.
 * <p>
 * <b>Example:</b>
 * <pre>
 * [Java]
 *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *  AddressSvc AddressSvc = new AddressSvcLocator(config);
 *
 *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 *  // Set the profile
 *  Profile profile = new Profile();
 *  profile.setClient("AddressSvcTest,4.0.0.0");
 *  port.setProfile(profile);
 *
 *  // Set security
 *  Security security = new Security();
 *  security.setAccount("account");
 *  security.setLicense("license number");
 *  port.setSecurity(security);
 *
 *  ValidateRequest request = new ValidateRequest();
 *  Address address = new Address();
 *  address.setLine1("900 Winslow Way");
 *  address.setLine2("Suite 130");
 *  address.setCity("Bainbridge Is");
 *  address.setRegion("WA");
 *  address.setPostalCode("98110-2450");
 *  request.setAddress(address);
 *  request.setTextCase(TextCase.Upper);
 *
 *  ValidateResult result;
 *  result = port.validate(request);
 *  Address[] addresses = result.getValidAddresses().getValidAddress();
 *
 * </pre>
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class Address  implements java.io.Serializable {
    private java.lang.String addressCode;
    private java.lang.String line1;
    private java.lang.String line2;
    private java.lang.String line3;
    private java.lang.String city;
    private java.lang.String region;
    private java.lang.String postalCode;
    private java.lang.String country;
    private int taxRegionId;

    /**
     * Initializes a new instance of the class.
     */
    public Address() {
    }

    /**
     * Initializes a new instance of the class.
     *
     * @param addressCode
     * @param line1
     * @param line2
     * @param line3
     * @param city
     * @param region
     * @param postalCode
     * @param country
     */
    private Address(
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
     * Programmatically determined value used
     * internally by the adapter. Defaults to the hash code of this
     * <code>Address</code> object.
     *
     * @return addressCode
     */
    public java.lang.String getAddressCode() {
        return addressCode;
    }


    /**
     * Programmatically determined value used
     * internally by the adapter. Defaults to the hash code of this
     * <code>Address</code> object.
     *
     * @param addressCode
     */
    public void setAddressCode(java.lang.String addressCode) {
        this.addressCode = addressCode;
    }


    /**
     * Address line 1 value.
     *
     * @return Address line 1 value
     */
    public java.lang.String getLine1() {
        return line1;
    }


    /**
     * Address line 1.
     *
     * @param line1   address line 1
     */
    public void setLine1(java.lang.String line1) {
        this.line1 = line1;
    }


    /**
     * Address line 2.
     *
     * @return line2 - address line 2
     */
    public java.lang.String getLine2() {
        return line2;
    }


    /**
     * Address line 2.
     *
     * @param line2 Address line 2.
     */
    public void setLine2(java.lang.String line2) {
        this.line2 = line2;
    }


    /**
     * Address line 3.
     *
     * @return line3 - address line 3
     */
    public java.lang.String getLine3() {
        return line3;
    }


    /**
     * Address line 3.
     *
     * @param line3 Address line 3
     */
    public void setLine3(java.lang.String line3) {
        this.line3 = line3;
    }


    /**
     * City name.
     *
     * @return city - City name
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * City name.
     *
     * @param city city name
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * State or province name or abbreviation.
     *
     * @return region - state or province name or abbreviation
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * State or province name or abbreviation.
     *
     * @param region - state or province name or abbreviation
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }


    /**
     * Postal or ZIP code.
     *
     * @return postalCode - Postal or ZIP code
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Postal or ZIP code.
     *
     * @param postalCode  - Postal or ZIP code
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Country name.
     *
     * @return country - country name
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Country name.
     *
     * @param country  - country name
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
     * <p>
     * TaxRegionId provides the ability to override the tax region assignment for an address.
     * </p>
     * @param taxRegionId
     */

    public void setTaxRegionId(int taxRegionId) {
        this.taxRegionId = taxRegionId;
    }

    private java.lang.Object __equalsCalc = null;

    //TODO: The address .equals function does not agree with the documentation; it does not case-insenstively compare strings as written

    /**
     * Overrides the default implementation of {@link java.lang.Object#equals}.
     * <p>
     * Equality is determined by first comparing referential equality
     * (they are the same object). Returns true if the two objects
     * are referentially equal.  If they are not, then it tests that
     * the object passed into the Equals method is an Address object.
     * Returns false if the compare object is not of type Address.
     * Otherwise, it will compare the two objects on a field by field
     * basis, such that Line1 is compared to Line1 of the compare
     * object, Line2 to Line2 of the compare object, and so on. All
     * fields must be identical (case-insensitive) in order for the
     * two objects to be considered equal.
     * <p>
     * <b>Example:</b> The following will return <b>true</b>:
     * <pre>
     * [Java]
     * Address address1 = new Address();
     * address1.Line1 = "900 Winslow Way";
     * address1.Region = "WA";
     * address1.PostalCode = "98110";
     *
     * Address address2 = address1;
     * bool isEqual = address1.Equals(address2);

     * </pre>
     * <p> The following will also return <b>true</b>:
     * <pre>
     * [Java]
     * Address address1 = new Address();
     * address1.Line1 = "900 Winslow Way";
     * address1.Region = "WA";
     * address1.PostalCode = "98110";
     *
     * Address address2 = new Address();
     * address2.Line1 = "900 WINSLOW WAY";
     * address2.Region = "wa";
     * address2.PostalCode = "98110";
     *
     * bool isEqual = address1.Equals(address2);
     * </pre>
     *
     * @param obj  The object to compare.
     *
     * @return True if <b>obj</b> is a reference to this object or if
     * both objects contain the same values (case-insensitive).<br>
     * False if they do not contain the same values or <b>obj</b>
     * is not of type Address.
     */
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
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
                                this.country.equals(other.getCountry())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    /**
     * Gets the default hash code for the object (as returned by
     * {@link java.lang.Object#hashCode})
     * @return - A default hash code.
     */
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
            new org.apache.axis.description.TypeDesc(Address.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Address"));
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
    }

    /**
     * Return TypeDesc metadata object for use with Axis.
     * @see org.apache.axis.description.TypeDesc
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer for this object for use with Axis.
     * @see org.apache.axis.encoding.Serializer
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
     * Get Custom Deserializer for this object for use with Axis.
     * @see org.apache.axis.encoding.Deserializer
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
